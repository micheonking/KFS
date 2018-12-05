<%-------------------------------------------------------------------------------- 
  * 화면명 : Smart Editor 2.8 에디터 - 싱글 파일 업로드 처리
  * 파일명 : /SE2/sample/photo_uploader/file_uploader.jsp
 --------------------------------------------------------------------------------%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.UUID"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%
  // 로컬경로에 파일 저장하기 ============================================ 
  String return1 = ""; 
  String return2 = ""; 
  String return3 = ""; 
  String name = "";

%>
