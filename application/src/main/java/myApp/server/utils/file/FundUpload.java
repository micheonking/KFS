package myApp.server.utils.file;
 
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd02_FundInfoModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.fnd.model.Fnd06_OfficeWorkModel;
import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.db.IsNewData;

public class FundUpload implements javax.servlet.Servlet {
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		System.out.println("fundUpload Start~!");
		
		HttpServletRequest request =  (HttpServletRequest)arg0 ;
		HttpServletResponse response = (HttpServletResponse)arg1; 
		response.setCharacterEncoding("UTF-8"); 

		try {
			this.uploadFile(request, response);
		} 
		catch (FileUploadException e) {
			setResult(response, "파일 업로드시 오류가 발생하였습니다. ");
			e.printStackTrace();
		}
	}

	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws FileUploadException {

		Long   companyId = Long.parseLong(request.getParameter("companyId")); // 반드시 클라이언트에서 붙여서 보내주어야 한다.
		String closeYn   = request.getParameter("closeYn");
//		String workDateStr = request.getParameter("workDate").replaceAll("-","");

		System.out.println("closeYn : " + closeYn);

		SqlSession sqlSession = DatabaseFactory.openSession();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		IsNewData isNewData = new IsNewData(); 

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB file size 
		ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);
		FileItem fileItem = serveltFileUpload.parseRequest(request).get(0);

		try {
			Date workDate = null;
			//Fnd02_Fund_Info 작업일자 Data Delete
//			Date workDate  = transFormat.parse(workDateStr);
//			sqlSession.delete("fnd02_fund_info.deleteByWorkDate", workDate);
//			System.out.println("fnd02_fund_info delete!!!! => " + workDate);

			// 파일로 저장하지 않고 stream으로 파일을 읽는다. 굳이 저장할 필요가 없잖아..
			InputStream inputStream = fileItem.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream,"MS949"));

			String strLine;
			int i=0;
			while ((strLine = bufferedReader.readLine()) != null) {
				i++;
				System.out.println("i : " + i + " / strLine : " + strLine);
				
				Fnd01_FundCodeModel fundCodeModel = new Fnd01_FundCodeModel();
				Fnd02_FundInfoModel fundInfoModel = new Fnd02_FundInfoModel();

				String [] data = strLine.split("\\|");
				for(int j=0; j<data.length; j++) {
					
					System.out.println("["+j+"] "+ data[j]);

					switch(j) {
					case 0: 	//작업일자
//						if (!workDateStr.equals(data[j])) {
//							sqlSession.rollback();
//							sqlSession.close();
//							System.out.println("작업일자와 파일("+data[j]+")의 기준일자가 다름.- rollback");
//							setResult(response, "|작업일자와 파일의 기준일자가 다릅니다. 확인하여 주십시오.|");
//							return; 
//						}
//						fundInfoModel.setWorkDate(transFormat.parse(data[j]));
						workDate = transFormat.parse(data[j]);
						System.out.println("workDate : " + workDate);
						break;
					case 1:		//펀드코드
						String fundCode = data[j];

						//(FundCode SET)
						fundCodeModel = sqlSession.selectOne("fnd01_fund_code.selectByCd", fundCode);
						if (fundCodeModel == null) {
							fundCodeModel = new Fnd01_FundCodeModel();
							fundCodeModel.setKeyId(isNewData.getSeq(sqlSession));
							fundCodeModel.setCompanyId(companyId);
							fundCodeModel.setPlanYn("false");
						}
						fundCodeModel.setFundCode(fundCode);
						
						//(FundInfo SET)
						Map<String, Object> fundInfoParam = new HashMap<String, Object>();
						fundInfoParam.put("fundCodeId", fundCodeModel.getKeyId());
						
						System.out.println("fundCodeId : " + fundCodeModel.getKeyId());
						
						fundInfoParam.put("workDate"  , workDate);
						fundInfoModel = sqlSession.selectOne("fnd02_fund_info.selectByFundCodeId", fundInfoParam);
						if (fundInfoModel == null) {
							fundInfoModel = new Fnd02_FundInfoModel();
							fundInfoModel.setKeyId(isNewData.getSeq(sqlSession));
							fundInfoModel.setFundCodeId(fundCodeModel.getKeyId());
							fundInfoModel.setWorkDate(workDate);
						}
						fundInfoModel.setUploadWorkDate(workDate);
						break;
					case 2:		//펀드명
						fundInfoModel.setFundName(data[j]);
						break;
					case 3: 	//유형코드
						fundInfoModel.setFundTypeCode(data[j]);
						break;
					case 4:		//최초설정일 
						fundCodeModel.setStartDate(transFormat.parse(data[j]));
						break;
					case 5:		//해지일
						if (data[j] == null || data[j].equals("")) {
							fundCodeModel.setEndDate(null);
						} else {
							fundCodeModel.setEndDate(transFormat.parse(data[j]));
						}
						break;
					case 6:		//해지구분코드
						if(data[j].equals("1")) {
							fundCodeModel.setCloseYn("false");
						} else {
							fundCodeModel.setCloseYn("true");
						}
						break;
					case 7:		//신탁구분
						fundInfoModel.setSintakGb(data[j]);
						break;
					case 8:		//공사모구분
						if(data[j].equals("1")) {
							fundInfoModel.setPublicYn("true");
						} else {
							fundInfoModel.setPublicYn("false");
						}
						break;
					case 9:		//설정액
						fundInfoModel.setSeoljAek(Long.parseLong(data[j]));
						break;
					case 10:	//순자산
						fundInfoModel.setSunjasanAek(Long.parseLong(data[j]));
						break;
					case 11:	//부서코드
						fundInfoModel.setOrgCode(data[j]);
						break;
					case 12:	//부서명
						Map<String, Object> orgParam = new HashMap<String, Object>();
						orgParam.put("companyId", companyId);
						orgParam.put("baseDate", fundInfoModel.getWorkDate());
						orgParam.put("korName", data[j]);
						Org01_CodeModel orgModel = sqlSession.selectOne("org01_code.selectOneByKorName", orgParam);
						if (orgModel != null) {
							fundInfoModel.setOrgCodeId(orgModel.getCodeId());
						}
						fundInfoModel.setOrgCodeName(data[j]);
						break;
					case 13:	//운용역1
						System.out.println("운용역1 SET!!! : " + data[j]);
						Emp00_InfoModel empModel1= sqlSession.selectOne("emp00_info.selectByLoginId", data[j]);
						if (empModel1 != null) {
							fundInfoModel.setEmp1Id(empModel1.getEmpId());
						}
						System.out.println("운용역1 SET!!! fundInfoModel : " + fundInfoModel.getEmp1Id());
						break;
					case 14:	//운용역2
						System.out.println("운용역2 SET!!! : " + data[j]);
						Emp00_InfoModel empModel2 = sqlSession.selectOne("emp00_info.selectByLoginId", data[j]);
						if (empModel2 != null) {
							fundInfoModel.setEmp2Id(empModel2.getEmpId());
						}
						System.out.println("운용역2 SET!!! fundInfoModel : " + fundInfoModel.getEmp2Id());
						break;
					case 15:	//운용보수
						fundInfoModel.setManagementRate(Double.parseDouble(data[j]));
						break;
					case 16:	//판매보수
						fundInfoModel.setSalesRate(Double.parseDouble(data[j]));
						break;
					case 17:	//수탁사코드
						fundInfoModel.setConsignmentCd(data[j]);
						break;
					case 18:	//수탁보수
						fundInfoModel.setConsignmentRate(Double.parseDouble(data[j]));
						break;
					case 19:	//사무수탁사코드
						fundInfoModel.setOfficeWorkCd(data[j]);
						break;
					case 20:	//사무수탁보수
						fundInfoModel.setOfficeWorkRate(Double.parseDouble(data[j]));
						break;
					}
				}

				System.out.println("closeYn : " + closeYn + " / fundCodeModel.getCloseYn() : " + fundCodeModel.getCloseYn());
				if ((closeYn.equals("false")) && (fundCodeModel.getCloseYn().equals("true"))) {
					//해지펀드 불포함 조회조건이면서, 해지펀드인 경우 Skip!!
					System.out.println("해지펀드임!! 미반영");
				} else {
					//해지펀드인 경우.. 해지일이 WorkDate보다 과거인 경우 Skip!!
					if ((fundCodeModel.getCloseYn().equals("true")) && (fundCodeModel.getEndDate().compareTo(workDate) == -1)) {
						System.out.println("과거에 해지된 펀드임!! 미반영");
					} else {
						System.out.println("infoID : " + fundInfoModel.getFundInfoId());
						System.out.println("workDate : " + fundInfoModel.getWorkDate());
						sqlSession.insert("fnd01_fund_code.insertUpdate",fundCodeModel);
						sqlSession.insert("fnd02_fund_info.insertUpdate",fundInfoModel);
					}
				}
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