package myApp.server.utils.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp01_PersonModel;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.file.MyAppProperties;

public class SendMailSMTP {
	// 전역변수 사용안함.

	public void mailSender(String receptor, String ccReceptor, String header, String content, List<String> fileList, String fileKey, Long empId, ServiceResult result) throws UnsupportedEncodingException {

	    Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "mail.hdfund.co.kr");		//	SMTP서버호스트
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.timeout", "5000");
		properties.put("mail.smtp.connectiontimeout", "5000");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");					//	MAIL포트 (465/587)

		try {
			
			SqlSession sqlSession = DatabaseFactory.openSession();
		    String sql = "emp01_person.selectByEmpId";  
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("empId", empId);
			 
			List<GridDataModel> list = sqlSession.selectList(sql, param);
			Emp01_PersonModel tempModel = new Emp01_PersonModel();
			for(int i = 0; i < list.size(); i++) {
				tempModel = (Emp01_PersonModel) list.get(0);
			}

//			String username = "test@hdfund.co.kr";
//			String password = "hdhd0725";
			String username = tempModel.getEmailAddr();
			String password = tempModel.getPassword();
			
		    sqlSession.close();
		    
			
			// session 생성 및 MimeMessage생성
		    Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			String FROM_NAME = "";
			//String CHAR_SET = "text/html; charset=utf-8";

			// 편지보낸시간 설정
			message.setSentDate(new Date());

			// 송신자 설정
			InternetAddress from = new InternetAddress();
//			from = new InternetAddress(new String(FROM_NAME.getBytes(CHAR_SET), "8859_1") + "<miCheon@k-fs.co.kr>");
			from = new InternetAddress(FROM_NAME + "<" + username + ">");
			//from = new InternetAddress(FROM_NAME + "<" + "test@hdfund.co.kr" + ">");

			message.setFrom(from);
			//System.out.println(from);

			// 수신자 설정
			String[] str1 = receptor.split(";");
			String[] str2 = ccReceptor.split(";");
			for(int i = 0; i < str1.length; i++) {
				String temp = str1[i];
				if(temp.indexOf("<") >= 0 && temp.indexOf(">") >= 0) {
					temp = temp.substring(temp.indexOf("<") + 1, temp.indexOf(">"));
				} else {
				}
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(temp));
			}
			for(int i = 0; i < str2.length; i++) {
				String temp = str2[i];
				if(temp != null && !"".equals(temp)) {
					if(temp.indexOf("<") >= 0 && temp.indexOf(">") >= 0) {
						temp = temp.substring(temp.indexOf("<") + 1, temp.indexOf(">"));
					} else {
					}
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(temp));
				}
			}
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
			
			// 제목 설정
			message.setSubject(header, "utf-8");
			//message.setSubject(MimeUtility.encodeText(header, "EUC-KR", "B"));
			

			//본문 시작
			MimeMultipart multipart = new MimeMultipart();
			// first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = content + "<br/><img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html; charset=utf-8");
            //messageBodyPart.setContent(htmlText, "text/html; charset=EUC-KR");

            //본문 첨부
            multipart.addBodyPart(messageBodyPart);

            //이미지 첨부
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(getFilePath() + "logo.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID","<image>");

            //파일첨부 시작
		    
            sqlSession = DatabaseFactory.openSession();
            param.put("fileList" , fileList);
            List<Sys10_FileModel> fileModelList = sqlSession.selectList("sys10_file.selectByFileId", param);
		    sqlSession.close();

		    if(fileModelList.size() > 0) {
				System.out.println("Attached File : " + fileModelList.size());
				for(Sys10_FileModel fileModel : fileModelList) {
					File f = new File((fileModel.getServerPath()) + getFolderPath()  + fileModel.getFileId());
					System.out.println("Attached File : " + (fileModel.getServerPath()) + getFolderPath()  + fileModel.getFileId());
					if (f.isFile()) {
						MimeBodyPart filePart = new MimeBodyPart();

						DataSource source = new FileDataSource((fileModel.getServerPath()) + getFolderPath()  + fileModel.getFileId());
						filePart.setDataHandler(new DataHandler(source));
						//filePart.setFileName(fileModel.getFileName());
						//String tempStr = MimeUtility.encodeText(fileModel.getFileName(), "EUC-KR", "B");
						
						String tempStr = fileModel.getFileName();
						String preFileName = "";
						String subFileName = "." + fileModel.getExt();
						preFileName = tempStr.substring(0, tempStr.indexOf("."));
						tempStr = preFileName + subFileName;
						
						filePart.setDisposition("attachment; filename=\"" + MimeUtility.encodeText(tempStr, "EUC-KR", "B") + "\"");
						filePart.setHeader("Content-Type", "application/pdf;name=\"" + MimeUtility.encodeText(tempStr, "EUC-KR", "B") + "\"");
						multipart.addBodyPart(filePart);
					}
				}
			}
		    //파일첨부 끝

            multipart.addBodyPart(messageBodyPart);
            //본문 끝

            message.setContent(multipart);
			System.out.println("메일 발송을 준비합니다.");

			// 7) send message
			Transport.send(message);
			System.out.println("메일 발송을 완료하였습니다.");

		} catch (AddressException addr_e) { // 예외처리 주소를 입력하지 않을 경우
			//JOptionPane.showMessageDialog(null, "메일을 입력해주세요", "메일주소입력", JOptionPane.ERROR_MESSAGE);
			addr_e.printStackTrace();
			result.setMessage("받는 메일주소가 올바르지 않습니다");
			result.setStatus(-1);
		} catch (MessagingException message_e) { // 메시지에 이상이 있을 경우
			//JOptionPane.showMessageDialog(null, "메일을 제대로 입력해주세요.", "오류발생", JOptionPane.ERROR_MESSAGE);
			
			System.out.println("메일보내기 오류발생!!!!! MessagingException ========> [" + message_e.getMessage() + "]");
			
//			if (message_e.getMessage().equals("Exception reading response")) {
//				System.out.println("Exception reading response 발생!! 메일발송완료 확인필요.");
//			} else {
//				message_e.printStackTrace();
//			result.setMessage("메일 주소 및 비밀번호를 확인해주세요");
//				result.setMessage("메일발송 중 에러가 발생했습니다. " + message_e.getMessage());
//				result.setStatus(-1);
//			}
		} catch (Exception e) {
			
			System.out.println("Exception ========> [" + e.getMessage() + "]");

			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("메일발송 중 에러가 발생했습니다");
			result.setStatus(-1);
		}
	}

	public void sendMailSession(SqlSession sqlSession, ServiceRequest request, ServiceResult result) throws UnsupportedEncodingException {

		String receptor = request.getStringParam("receptor");
		String ccReceptor = request.getStringParam("ccReceptor");
		String header = request.getStringParam("header");
		String content = request.getStringParam("content");
		String fileKey = request.getStringParam("fileKey");
		Long empId = request.getLongParam("empId");

		Map<String, Object> param = request.getParam();

		@SuppressWarnings("unchecked")
		List<String> fileList = (List<String>) param.get("fileList");
		mailSender(receptor, ccReceptor, header, content, fileList, fileKey, empId, result);
		
	}
	
	public void checkMail(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long empId = request.getLongParam("empId");
		
		sqlSession = DatabaseFactory.openSession();
	    String sql = "emp01_person.selectByEmpId";  
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("empId", empId);
		 
		List<GridDataModel> list = sqlSession.selectList(sql, param);
		Emp01_PersonModel tempModel = new Emp01_PersonModel();
		for(int i = 0; i < list.size(); i++) {
			tempModel = (Emp01_PersonModel) list.get(0);
		}
//		String username = tempModel.getEmailAddr();
//		String password = tempModel.getPassword();
		
	    String username = "test@hdfund.co.kr";
	    String password = "hdhd0725";
	    sqlSession.close();
	    
	    Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "mail.hdfund.co.kr");		//	SMTP서버호스트
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.timeout", "5000");
		properties.put("mail.smtp.connectiontimeout", "5000");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");					//	MAIL포트
	    properties.setProperty("mail.store.protocol", "imaps");
		// session 생성 및 MimeMessage생성
	    Session session = Session.getDefaultInstance(properties, null);

	    
	    try {
			Store store = session.getStore("imaps");
			store.connect("mail.hdfund.co.kr", "<"+username+">", password);
			
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("222");
	}
	
	private String getFilePath(){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty(); 
	}

	private String getFolderPath() {
		MyAppProperties a = new MyAppProperties("folderPath");
		return a.getProperty();
	}
	
}
