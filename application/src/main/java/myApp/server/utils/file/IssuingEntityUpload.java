package myApp.server.utils.file;
 
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.ibatis.session.SqlSession;

import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.db.IsNewData;

public class IssuingEntityUpload implements javax.servlet.Servlet {
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		System.out.println("IssuingEntityUpload Start~!");
		
		HttpServletRequest request =  (HttpServletRequest)arg0 ;
		HttpServletResponse response = (HttpServletResponse)arg1; 
		response.setCharacterEncoding("UTF-8"); 

		try {
			this.uploadFile(request, response);
		} 
		catch (FileUploadException | SQLException e) {
			setResult(response, "파일 업로드시 오류가 발생하였습니다. ");
			e.printStackTrace();
		}
	}

	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, SQLException {

		Long   companyId = Long.parseLong(request.getParameter("companyId")); // 반드시 클라이언트에서 붙여서 보내주어야 한다.
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		IsNewData isNewData = new IsNewData(); 
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB file size 
		ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);
		FileItem fileItem = serveltFileUpload.parseRequest(request).get(0);

		try {
			// 파일로 저장하지 않고 stream으로 파일을 읽는다. 굳이 저장할 필요가 없잖아..
			InputStream inputStream = fileItem.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream,"MS949"));

			String strLine;
			int i=0;
			while ((strLine = bufferedReader.readLine()) != null) {
				i++;
				System.out.println("i : " + i + " / strLine : " + strLine);
				
				Fnd07_IssuingEntityModel  issuingEntityModel = new Fnd07_IssuingEntityModel();
				
				String [] data = strLine.split("\\|");
				for(int j=0; j<data.length; j++) {

					System.out.println(j + " => " + data[j]); 

					switch(j) {
					case 0:		
						issuingEntityModel = sqlSession.selectOne("fnd07_issuing_entity.selectByCodeName", data[j]);
						
						if (issuingEntityModel == null) {
							issuingEntityModel = new Fnd07_IssuingEntityModel();
							issuingEntityModel.setKeyId(isNewData.getSeq(sqlSession));
							issuingEntityModel.setCompanyId(companyId);
							issuingEntityModel.setIssuingEntityUseYn(false);
						}//발행기관 코드
						issuingEntityModel.setIssuingEntityCode(data[j]);
						break;
						
					case 1:	//발행기관 명
						issuingEntityModel.setIssuingEntityName(data[j]);
						break;
					case 2: //소속구분
						issuingEntityModel.setIssuingEntityAttachCode(data[j]);
						break;
					case 3: //액면가
						issuingEntityModel.setIssuingEntityFaceValue(Long.parseLong(data[j]));
//						issuingEntityModel.setIssuingEntityFaceValue(data[j]);
						break;
						
					case 4: //결산월
						issuingEntityModel.setIssuingEntitySettleMonth(data[j]);
						break;
						
					case 5: //국가코드
						issuingEntityModel.setIssuingEntityNationCode(data[j]);
						break;
						
					}
						
				}
				sqlSession.insert("fnd07_issuing_entity.insertUpdate",issuingEntityModel);
			}
		}
		catch (org.apache.ibatis.exceptions.PersistenceException e) {
			e.printStackTrace();
			sqlSession.rollback();
			sqlSession.close();
			System.out.println("PersistenceException - rollback");
			String msg = e.getMessage().substring(e.getMessage().lastIndexOf("#") + 1) ; 
			setResult(response, "|파일 업로드시 오류가 발생하였습니다. \r\n[" + msg + "]|");
			return; 
		}
		catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			sqlSession.close();
			System.out.println("Exception - rollback");
			setResult(response, "|파일 업로드시 오류가 발생하였습니다.(Exception)|");
			return; 
		}

		sqlSession.commit();
		sqlSession.close();
		System.out.println("정상종료 - commit");
		setResult(response, "|파일을 성공적으로 등록하였습니다.|");
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