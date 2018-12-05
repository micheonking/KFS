package myApp.server.utils.file;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;

import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.db.IsNewData;

public class FileUpload_20180820 implements javax.servlet.Servlet {
	
	private String getUploadPath(Long fileId){
		return "E:\\WebFiles\\" + (fileId/100) ;	
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		HttpServletRequest request =  (HttpServletRequest)arg0 ;
		HttpServletResponse response = (HttpServletResponse)arg1; 
		response.setCharacterEncoding("UTF-8"); 
		
		String fileType = request.getParameter("fileType"); // image or file 

		if(fileType != null) {
			System.out.println("action Code is " + fileType);	
		}
		else {
			System.out.println("파일 처리구분이 정의되지 않았습니다.");
			setResult(response, "파일 처리구분이 정의되지 않았습니다.");
			return; 
		}

		if("multi".equals(fileType)) {
			try {
				this.saveFile(request, response);
			} catch (FileUploadException | SQLException e) {
				setResult(response, "파일 업로드시 오류가 발생하였습니다. ");
				e.printStackTrace();
			}
		}
		
		if("image".equals(fileType)){
			System.out.println("image upload start");
			try {
				this.uploadFile(request, response);
			} catch (FileUploadException | SQLException e) {
				setResult(response, "사진 업로드시 오류가 발생하였습니다. ");
				e.printStackTrace();
			}
		}

		if("file".equals(fileType)){
			try {
				this.uploadFile(request, response);
			} catch (FileUploadException | SQLException e) {
				setResult(response, "파일 업로드시 오류가 발생하였습니다. ");
				e.printStackTrace();
			}
		}
		
		if("deleteFile".equals(fileType)){
			this.deleteFile(request, response);
		} 
	}

	
	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, SQLException {
		
		String parentId = request.getParameter("parentId"); // 반드시 클라이언트에서 붙여서 보내주어야 한다. 
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB file size 
        ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);

		FileItem fileItem = null ;
			
		List<FileItem> itemList = serveltFileUpload.parseRequest(request);
			
        for(FileItem item : itemList) {
        	if("fileField".equals(item.getFieldName())) {
        		fileItem = item;
        	}
        }
        if(fileItem == null) {
        	setResult(response, "등록할 파일이 전달되지 않았습니다.");
        	return ; 
        }

        // update or insert file model 
        Sys10_FileModel fileModel = updateFileModel(parentId, fileItem, request) ;
        
        if(fileModel != null) {
			try {
				File subDir  = new File(fileModel.getServerPath());
				
		        if(!subDir.exists()) {
		        	subDir.mkdir(); // 해당 폴더가 없으면 신규로 만든다.   
		        }
		
		        File file = new File(fileModel.getServerPath(), fileModel.getFileId().toString());
			    file.deleteOnExit(); // Update할것. 
		        fileItem.write(file);
			}
			catch (FileUploadException e) {
				e.printStackTrace();
				setResult(response, "Error encountered while parsing the request");
				return ; 
			} 
			catch (Exception e) {
				e.printStackTrace();
				setResult(response, "Error encountered while uploading file");
				return; 
			}
	 
	        setResult(response, "파일을 성공적으로 등록하였습니다.");
        }
	} 
	
	private void saveFile(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException, FileUploadException, SQLException {

		SqlSession sqlSession = DatabaseFactory.openSession();
		
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
			ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);
			FileItem fileItem = serveltFileUpload.parseRequest(request).get(0); // 처음 하나의 파일만 가져온다.  
		
			Long fileId = Long.parseLong(request.getParameter("fileId"));
			String parentId = request.getParameter("fileId");
			
			if(!"single".equals(request.getParameter("fileType"))) {
				IsNewData isNewData = new IsNewData(); 
				fileId = isNewData.getSeq(sqlSession);
			}

			Sys10_FileModel fileModel = new Sys10_FileModel();
			String fileName = fileItem.getName();	// 파일명 file upload시에만 사용된다.
			String fileExt = "";//파일 확장자명
			int fileNameSize = fileName.lastIndexOf(".");
			if(fileNameSize <= 0) {
			} else {
				fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			}
			
			fileModel.setFileId(fileId);
			fileModel.setParentId(Long.parseLong(parentId));
			fileModel.setFileName(fileName); 
			fileModel.setRegDate(new Date()); // 데이터베이스 시간으로 변경해야 한다.  
			fileModel.setServerPath(this.getUploadPath(fileId)); //100개씩 잘라 보관한다.
			
			Double size = Double.parseDouble((fileItem.getSize()/1024) + ""); 
			fileModel.setSize(size); 
			
			fileModel.setDelDate(null); 
			fileModel.setNote(null);
			fileModel.setExt(fileExt);
			
			sqlSession.insert("sys10_file.insert", fileModel); 
			 
			File subDir  = new File(this.getUploadPath(fileId));
	        if(!subDir.exists()) {
	        	subDir.mkdir(); 
	        }

	        File file = new File(this.getUploadPath(fileId), fileId.toString());
    	    file.deleteOnExit(); // 있으면 먼저 지워라.
            fileItem.write(file);
            
            sqlSession.commit();
            sqlSession.close();
            
            setResult(response, "파일을 성공적으로 등록하였습니다.");
            
		} 
		catch (FileUploadException e) {
			sqlSession.rollback();
			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while parsing the request");
		} 
		catch (Exception e) {
			sqlSession.rollback();
			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while uploading file");
		}
	}
	
	private Sys10_FileModel updateFileModel(String id, FileItem fileItem, HttpServletRequest request) {
		
		String actionCode = "update";
		Long parentId = Long.parseLong(id); 
		
		// 등록된 파일인지를 먼저 찾는다. 
        SqlSession sqlSession = DatabaseFactory.openSession();
		Sys10_FileModel fileModel = sqlSession.selectOne("sys10_file.selectByParentId", parentId);
		if(fileModel == null) {
			Long fileId = Long.parseLong(request.getParameter("fileId"));
			fileModel = sqlSession.selectOne("sys10_file.selectByParentId", fileId);
		}
		
		if(fileModel == null) {
			actionCode = "insert"; 
			fileModel = new Sys10_FileModel();

			IsNewData isNewData = new IsNewData(); 
			Long fileId = isNewData.getSeq(sqlSession);
		
			fileModel.setFileId(fileId);
			fileModel.setParentId(parentId);
			
			fileModel.setServerPath(this.getUploadPath(fileModel.getFileId())); //하나의 폴더안에 100개씩 잘라 보관한다.
			fileModel.setParentId(parentId);
		}
 
		
		String fileName = fileItem.getName() ; 
		fileModel.setFileName(fileName);

		String fileExtName = fileName.substring(fileItem.getName().lastIndexOf(".") + 1, fileName.length());
		fileModel.setExt(fileExtName);
		
		Double size = Double.parseDouble((fileItem.getSize()/1024) + ""); 
		fileModel.setSize(size); 
			
		fileModel.setRegDate(new Date()); // 시스템 시간. 
		fileModel.setDelDate(null); 
		fileModel.setNote("");
			
		if("insert".equals(actionCode)) {
			sqlSession.insert("sys10_file.insert", fileModel);
		}
		else {
			sqlSession.update("sys10_file.update", fileModel);
		}
		
        sqlSession.commit();
		sqlSession.close();
		
		return fileModel; 
	}
	
	private void deleteFile(HttpServletRequest request, HttpServletResponse response) {
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		String fileId = request.getParameter("fileId");
		
		try{
			sqlSession.delete("sys10_file.delete", fileId); 

	        File file = new File(this.getUploadPath(Long.parseLong(fileId)), fileId);
    	    file.delete();
            
            sqlSession.commit();
            sqlSession.close();
            setResult(response, "파일을 성공적으로 삭제하였습니다.");
		}
		catch(RuntimeSqlException e) {
    			sqlSession.rollback();
    			sqlSession.close();
    			e.printStackTrace();
    			setResult(response, "file data insert/update error");
    			return ; 
		}
		catch(Exception e) {
			sqlSession.rollback();
			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while deleting file");
			return; 
		}
	}
	
	private void setResult(HttpServletResponse response, String message){
		try {
			PrintWriter out = response.getWriter();
			out.println(message);
			out.flush();
		}
		catch(Exception e){
			System.out.println(e.toString()); 
		}
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
	}
	
	
}