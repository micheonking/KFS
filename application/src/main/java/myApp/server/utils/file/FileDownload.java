package myApp.server.utils.file;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.server.utils.db.DatabaseFactory;

public class FileDownload implements javax.servlet.Servlet {
	
	//String defaultPath = "E:\\WebFiles\\" ;
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		
		HttpServletRequest request =  (HttpServletRequest)arg0 ;
		HttpServletResponse response = (HttpServletResponse)arg1; 
		response.setCharacterEncoding("UTF-8"); 

		String fileType = request.getParameter("fileType");
		System.out.println("download fileType is " + fileType); 
		
		if("image".equals(fileType)) {
			this.downloadImage(request, response); 
		}
		
		if("file".equals(fileType)) {
			this.downloadFile(request, response); 
		}
	}
	
	private void downloadImage(HttpServletRequest request, HttpServletResponse response) {
		 
		String parentId = request.getParameter("parentId");
		String fileId = request.getParameter("fileId");
		System.out.println("fileId : " + fileId);
		if(parentId == null && fileId == null){
			System.out.println("download nonamed image");
			//File file = new File(this.defaultPath + "nonamed.jpg"); 
			File file = new File(getUploadPath() + "nonamed.jpg");
			download(response, file, "image/jpg" , "nonamed.jpg") ; 
			return ; 
		}
        
		SqlSession sqlSession = DatabaseFactory.openSession();
		Sys10_FileModel fileModel;
		if(fileId == null){
			fileModel = sqlSession.selectOne("sys10_file.selectOneByParentId", Long.parseLong(parentId));
		} else {
			fileModel = sqlSession.selectOne("sys10_file.selectById", Long.parseLong(fileId));
		}
		sqlSession.close();
		
        if(fileModel == null) { // 
        	System.out.println("데이터베이스에 등록된 파일정보를 찾을 수 없습니다. : " + parentId);
			//File file = new File(this.defaultPath + "nonamed.jpg"); 
        	File file = new File(getUploadPath() + "nonamed.jpg");
			download(response, file, "image/jpg" , "nonamed.jpg") ; 
			return ; 
        }

    	//String filePath = this.defaultPath + (fileModel.getFileId()/100) + "\\"  + fileModel.getFileId() ;
    	String filePath = getUploadPath() + (fileModel.getFileId()/100) + getFolderPath()  + fileModel.getFileId() ;
		File file = new File(filePath);

		if(!file.exists()) {
			System.out.println("file not found file id is " + filePath); 
			//download(response, new File(this.defaultPath + "nonamed.jpg"), "image/jpg" , "nonamed.jpg") ; 
			download(response, new File(getUploadPath() + "nonamed.jpg"), "image/jpg" , "nonamed.jpg") ; 
			return ; 
 
		}
		else {
			download(response, file, "image/" + fileModel.getExt() , fileModel.getFileName()) ;
		}
	}

	
	private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileId = request.getParameter("fileId");
		File file = null; 
		System.out.println(fileId);
		if(fileId == null){
			setResult(response, "요청한 파일정보 부정확합니다.");
			return ; 
		}

		SqlSession sqlSession = DatabaseFactory.openSession();
		
		Sys10_FileModel fileModel;
		

		fileModel = sqlSession.selectOne("sys10_file.selectById", Long.parseLong(fileId));
		
		sqlSession.close();
		
        if(fileModel == null) {
        	sqlSession.close();
        	setResult(response, "등록된 파일정보를 찾을 수 없습니다.");
        	System.out.println("등록된 파일정보를 찾을 수 없습니다.");
        	return ; 
        }
        
        //file = new File(this.defaultPath + (fileModel.getFileId()/100) + "\\"  + fileModel.getFileId());
        file = new File(getUploadPath() + (fileModel.getFileId()/100) + getFolderPath()  + fileModel.getFileId());
        System.out.println(getUploadPath() + (fileModel.getFileId()/100) + getFolderPath()  + fileModel.getFileId());
        if (!file.exists()) {
        	setResult(response, "서버에 등록된 파일정보가 없습니다:" + fileModel.getFileName());
        	System.out.println("서버에 등록된 파일정보가 없습니다:" + fileModel.getFileName()); 
        	return; 
        }
        else {
        	
        	String encodedFilename  = "";
        	String fileName = fileModel.getFileName();
        	
        	// encoding 처리 
        	String browser = request.getHeader("User-Agent");
        	System.out.println("browser agent is " + browser); 
        	
            if (browser.indexOf("Trident") > -1){
            	encodedFilename  = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            } 
            else if (browser.indexOf("Chrome") > -1) {
            	StringBuffer sb = new StringBuffer();
            	for (int i = 0; i < fileName.length(); i++) {
            		char c = fileName.charAt(i);
            		if (c > '~') {
            			sb.append(URLEncoder.encode("" + c, "UTF-8"));
            		} else {
            			sb.append(c);
            		}
            	}
            	encodedFilename = sb.toString();
            } 
            else if (browser.indexOf("Firefox") > -1) {
                encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
            }
            
            else if (browser.indexOf("Opera") > -1) {
            	encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
            }
            else {
            	encodedFilename = fileName; 
            }

        	// 정상처리한다. 
        	download(response, file, "application/octet-stream" , encodedFilename) ;	

/* 브라우저에 따른 인코딩 처리
 * 
 *	if((match = agent.match(/MSIE ([0-9]+)/)) || (match = agent.match(/Trident.*rv:([0-9]+)/))) app = 'Internet Explorer';
 *	else if(match = agent.match(/Chrome\/([0-9]+)/)) app = 'Chrome';
 *	else if(match = agent.match(/Firefox\/([0-9]+)/)) app = 'Firefox';
 *	else if(match = agent.match(/Safari\/([0-9]+)/)) app = 'Safari';
 *	else if((match = agent.match(/OPR\/([0-9]+)/)) || (match = agent.match(/Opera\/([0-9]+)/))) app = 'Opera';
 *	else app = 'Unknown';
*/            
            
        }
	}

	private void download(HttpServletResponse response, File file, String contentType, String fileName) {

        response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("EUC-KR");
		response.setHeader("Content-Type", contentType); 
//       	response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		response.setHeader("Content-disposition", "attachment;filename=" + fileName.replace(",", "_"));
		response.setContentLength((int)file.length());
        
		try {
			BufferedInputStream  bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
	        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());

	        byte[] buf = new byte[1024];    

	        while (true) {
	            int length = bufferedInputStream.read(buf);
	            if (length == -1) {
	                break;
	            }
	            else {
	            	bufferedOutputStream.write(buf, 0, length);
	            }
	        }
	        bufferedOutputStream.close();
	        bufferedInputStream.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setResult(HttpServletResponse response, String message){
		try {
			PrintWriter out = response.getWriter();
			out.println(message);
			out.flush();
			System.out.println("end of file upload");
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
	
	private String getUploadPath(){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty(); 
	}
	private String getFolderPath() {
		MyAppProperties a = new MyAppProperties("folderPath");
		return a.getProperty();
	}
}

