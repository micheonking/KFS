package myApp.server.hom;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.ibatis.session.SqlSession;

import myApp.server.utils.db.DatabaseFactory;

public class HTMLCeo implements javax.servlet.Servlet {

	private String actionCode = "retrieve"; 

	private String nullString(String data) {
		if("null".equals(data) || data == null) {
			return ""; 
		}
		else {
			String str = data.replaceAll("\r", "<br>"); 
			str = str.replaceAll("\n", "<br>");
			return  str; 
		}
	}
	
	private String getHtml() {
		
		String header = ""; //"대표전화 번호 " + "02-782-5100<br>";  
		
		List<String> rowList = new ArrayList<String>(); 
		
		SqlSession sqlSession = DatabaseFactory.openSession(); 
		
//		List<Long> companyList = sqlSession.selectList("hom03_operating.selectByComanyId");
		
		String rowString = "";
		
//		rowString += this.tdGrey("소속(본부)", 50, "center",1); 	
//		rowString += this.tdGrey("성명/직책", 70, "center",1); 
//		rowString += this.tdGrey("담당증권사", 100, "center",1);
//		rowString += this.tdGrey("주요경력", 150, "center",1);
//		rowString += this.tdGrey("학력/자격증", 120, "center",1);
//		rowString += this.tdGrey("연락처", 70, "center",1);
		
//		rowList.add(this.tr(rowString)) ; 
//
//		int	j = 0;
//		for(Long orgCodeId : companyList) {
//			
//			List<Hom03_OperatingModel> list = sqlSession.selectList("hom03_operating.selectByOrgCode", orgCodeId);	
//
//			for(int i = 0 ; i<list.size(); i++) {
//				
//				Hom03_OperatingModel operatingModel = list.get(i);
//				
//				rowString = "";	
//				
//				if(i == 0) {
////					rowString += this.tdRowSpanGrey(list.size(), operatingModel.getOrgName(), 50, "center");
//					rowString += this.tdRowSpan(list.size(), operatingModel.getOrgName(), 50, "center", j%2);
//				}
//
//				rowString += this.tdCenter(operatingModel.getNameTitle(), 60, j%2); 
//
//				if(operatingModel.getChargeStockFirmCnt() == operatingModel.getChargeStockFirmMax()) {
//					if(operatingModel.getChargeStockFirmMax() > 1) {
//						rowString += this.tdRowSpan(operatingModel.getChargeStockFirmMax(),nullString(operatingModel.getChargeStockFirm()), 100, "center", j%2);
//					}
//					else {
//						rowString += this.tdCenter(nullString(operatingModel.getChargeStockFirm()), 100, j%2);
//					}
//				}
////				else {
////					int kk = operatingModel.getChargeStockFirmMax();
////					rowString += this.tdRowSpan(kk,nullString(operatingModel.getChargeStockFirm()), 100, "left", j%2);
////				}
////				rowString += this.tdCenter(nullString(operatingModel.getChargeStockFirm()), 100, j%2);
//				rowString += this.tdData(operatingModel.getMajorCareer(), 150, j%2);
//				rowString += this.tdData(operatingModel.getAcademicCertificate(), 140, j%2);
//				rowString += this.tdCenter(nullString(operatingModel.getContactInfomation()), 70, j%2);
//				
//				j += 1;
//				rowList.add(this.tr(rowString)) ; 
//			}
//		}


		String ceoimage = "<div><img src='img/_KFSCeo.jpg' width='190' height='266'></div>";

		String content = "<font color='#606060' >"
				+	"<span style=\"font-weight:bold;font-size:1.2em;\">안녕하세요!</span><br>"
				+	"<br><span style=\"font-weight:normal;font-size:0.9em;\">"
				+	"한국펀드서비스(주)는 금융위원회에 일반사무관리회사 등록을 마치고 최고의  <br>"
				+	" 펀드서비스회사가 되도록 최선을 다하고 있습니다. <br>"
				+	"<br>"
				+	"2000년에 설립되어 금융서비스전문회사로서 일반사무관리 업무솔루션(ICAM), <br>"
				+	"경영지원시스템, 자산운용 정보통합관리시스템, 기금관리시스템, 신탁 및 <br>"
				+	"수탁시스템 등 금융업무 서비스, 컨설팅 및 개발을 위해 고객과 함께해 왔습니다. <br>"
				+	"<br>"
				+	"특히, 다양화 및 전문화되고 있는 투자일임자문사와 자산운용회사를 위해  <br>"
				+	"고객자산평가의 신뢰성 확보와 자산운용성과 지원을 위한 맞춤형 <br>"
				+	"일반사무관리업무시스템(ICAM)을 구축하여 운영 중입니다. <br>"
				+	"<br>"
				+	"한국펀드서비스(주)는 고객과 함께 동반성장하는 파트너가 되어 고객의 선택이  <br>"
				+	"자랑이 되는 회사가 되겠습니다! <br>"
				+	"<br>"
				+	"그 동안 보여주신 관심과 배례에 감사드리며, 앞으로도 많은 성원을 부탁드립니다. <br>"
				+	" <br>"
				+	" <br></span>"
				+	"<span style=\"font-weight:bold;font-size:1.2em;\">한국펀드서비스㈜ 대표이사 박동진</span>"
				;
		
		String education = "<font color='#606060' >"
					+	"<br>"
				 	+	"<img src='img/smallTitle.png'>"
				 	+	"<span style=\"font-weight:bold;\"> 학력 및 자격사항</span><br>"
				 	+	"<span style=\"font-size:0.8em;\">ㆍCISA, ISO9001 책임심사원</span><br>"
				 	+	"<span style=\"font-size:0.8em;\">ㆍ전문투자형 운용전문인력</span><br>"
				 	+	"<span style=\"font-size:0.8em;\">ㆍ홍익대학교 이학석사</span><br>"
				 	;
		
		String career = "<font color='#606060' >"
					+	"<br>"
					+	"<img src='img/smallTitle.png'>"
					+	"<span style=\"font-weight:bold;\"> 주요이력 </span><br>"
					+	"<span style=\"font-size:0.8em;\">現) 한국펀드서비스(주) 대표이사</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 한국채권투자자문 전략본부장</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 신한BNP자산운용 운용지원본부장</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 펀드넷 및 정보화 허브 자문위원</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 대한투자신탁(주)</span><br>"
					;
		
		rowString += this.tdRowSpan(3, content, 500, "left", 0);
		rowString += this.tdData1(ceoimage, 220, 0);
		rowList.add(this.tr(rowString)) ; 
		rowString = "";
		rowString += this.tdData1(education, 220, 0); 
		rowList.add(this.tr(rowString)) ;
		rowString = "";
		rowString += this.tdData1(career, 220, 0); 
		rowList.add(this.tr(rowString)) ; 
		sqlSession.close();
		
		String tableString = "<table style='width:99%; margin:0px; font-size:16px; border-collapse:collapse; padding:0px;'>";
		
		for(String rowString1 : rowList) {
			tableString += rowString1; 
		}
		
		tableString += "</table>" ; 
		
		String returnHtml = header + tableString ; 
		
		return returnHtml; 
	}

	
	private String tr(String data) {
		return "<tr style='height:auto;'>" + data + "</tr>"; 
	}

	private String tdData(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='width:" + width + "px;" + rowChange + "padding:0px 0px 0px 20px; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
	}

	private String tdData1(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='width:" + width + "px;" + rowChange + "padding:0px 0px 0px 20px; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
	}

	private String tdCenter(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='text-align:center; width:" + width + "px;" + rowChange + "padding:0px 0px 0px 20px; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
	}

	private String tdRowSpan(int rowSpan, String data, int width, String align, int rowcount) {
		
		int rs = rowSpan; 
		if(rs<1) rs = 1 ;
		
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td rowspan=" + rs 
				+ " style='text-align:" + align + "; width:" + width 
				+ "px;" + rowChange + "padding:0px 0px 0px 0px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}
	
	private String tdGrey(String data, int width, String align, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' style='text-align:" + align + "; width:" + width
				+ "px;" + rowChange + "padding:5px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}

	private String tdColSpanGrey(int colSpan, String data, int width, String align, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' colSpan =" + colSpan
				+ " style='text-align:" + align + "; width:" + width
				+ "px;" + rowChange + "padding:5px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}

	
	private String tdRowSpanGrey(int rowSpan, String data, int width, String align, int rowcount) {
		int rs = rowSpan; 
		if(rs<1) rs = 1 ;
	
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' rowspan=" + rs 
				+ " style='text-align:" + align + "; width:" + width 
				+ "px;" + rowChange + "padding:5px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}
	
		 
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		
		arg1.setContentType("text/html");
		arg1.setCharacterEncoding("UTF-8");
		
		this.actionCode = arg0.getParameter("actionCode");
		String empId = arg0.getParameter("empId");
		String evalTargetId = arg0.getParameter("evalTargetId");
		
		System.out.println("actionCode : " + actionCode);
		System.out.println("evalTargetId : " + evalTargetId);
		System.out.println("empId : "  + empId);
		
		String returnHtml = this.getHtml();
		
		PrintWriter out = arg1.getWriter();
		out.println(returnHtml);
		
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
