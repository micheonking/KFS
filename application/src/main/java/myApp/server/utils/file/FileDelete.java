package myApp.server.utils.file;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;

import myApp.server.utils.db.DatabaseFactory;

public class FileDelete implements javax.servlet.Servlet {
	
	/*
	private String getUploadPath(Long fileId){
		return "E:\\WebFiles\\" + (fileId/100) ;	
	}
	*/
	private String getUploadPath(Long fileId){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty() + (fileId/100); 
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		HttpServletRequest request =  (HttpServletRequest)arg0 ;
		HttpServletResponse response = (HttpServletResponse)arg1; 
		response.setCharacterEncoding("UTF-8"); 

		try {
			this.deleteFile(request, response);
		} 
		catch (FileUploadException | SQLException e) {
			setResult(response, "파일 업로드시 오류가 발생하였습니다. ");
			e.printStackTrace();
		}
	}
	
	private void deleteFile(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, SQLException {
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		String fileId = request.getParameter("fileId");
		
		try{
			sqlSession.delete("sys10_file.delete", fileId); 

	        File file = new File(this.getUploadPath(Long.parseLong(fileId)), fileId);
	        
    	    if(file.delete()) {
    	    	System.out.println("삭제 성공 fileId : " + fileId);
    	    } else {
    	    	System.out.println("삭제 실패 fileId : " + fileId);
    	    }
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

//private Sys10_FileModel insertFileModel(Long parentId, FileItem fileItem, HttpServletRequest request) {
//
//// 등록된 파일인지를 먼저 찾는다. 
//SqlSession sqlSession = DatabaseFactory.openSession();
////Sys10_FileModel fileModel = sqlSession.selectOne("sys10_file.selectByParentId", Long.parseLong(parentId));
////
////if(fileModel == null) {
////	Long fileId = Long.parseLong(request.getParameter("fileId"));
////	fileModel = sqlSession.selectOne("sys10_file.selectByParentId", fileId);
////}
////
////if(fileModel == null) {
////	actionCode = "insert"; 
//Sys10_FileModel  fileModel = new Sys10_FileModel();
//
//IsNewData isNewData = new IsNewData(); 
//Long fileId = isNewData.getSeq(sqlSession);
//
//fileModel.setFileId(fileId);
//fileModel.setParentId(parentId);
//fileModel.setServerPath(this.getUploadPath(fileModel.getFileId())); //하나의 폴더안에 100개씩 잘라 보관한다.
//fileModel.setParentId(parentId);
//
//String fileName = fileItem.getName() ; 
//fileModel.setFileName(fileName);
//
//String fileExtName = fileName.substring(fileItem.getName().lastIndexOf(".") + 1, fileName.length());
//fileModel.setExt(fileExtName);
//
//Double size = Double.parseDouble((fileItem.getSize()/1024) + ""); 
//fileModel.setSize(size); 
//	
//fileModel.setRegDate(new Date()); // 시스템 시간. 
//fileModel.setDelDate(null); 
//fileModel.setNote("");
//
//sqlSession.insert("sys10_file.insert", fileModel);
//
//sqlSession.commit();
//sqlSession.close();
//
//return fileModel; 
//}