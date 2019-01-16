package myApp.server.hom;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.vi.hom.company.model.Hom03_OperatingModel;
import myApp.server.utils.db.DatabaseFactory;

public class HTMLEntrustInvestment implements javax.servlet.Servlet {

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
		
		List<String> rowList = new ArrayList<String>(); 
		
		SqlSession sqlSession = DatabaseFactory.openSession(); 
		
		String rowString = "";
		
		String rowText1 = "투자일임이란 자문사가 고객으로부터 금융투자상품에 대한 투자 판단을 위임 받아, 고객을 대신하여 금융투자상품을 운용하는 것을 의미합니다.<br><br>"
				+	" 투자일임계약에 따라 자문사는 고객의 투자일임자산에 대해 투자일임서비스를 제공하고, 고객은 자문사로부터 제공받은 투자일임서비스에 대한 투자일임수수료를 지급합니다."
				;
//		String rowText2 = "<div style='backgroud:url(img/invest/bg.jpg) no-repeat center top;'"
//				+	"</div>"
//				;
//		<div style="background-image: url(../images/test-background.gif); height: 200px; width: 400px; border: 1px solid black;"> </div>
//		String rowText2 = "<div style=\"backgroud-image:url(img/invest/bg.jpg); backgroud-color:red; no-repeat; \">"
//				+	"여기요</div>"
//				;
		String rowText2 = "<div><br><img src='img/invest/bg1.png' width='550' height='180' no-repeat center top;></img><br><br><br></div>";

		String rowText3 = "고객의 투자자금 및 금융투자상품을 운용하기 전 고객의 투자목적, 투자경험, 위험성향, 투자예정기간 등을 파악한 후 이에 상응하는 세심한 투자일임서비스를 제공합니다.<br><br>"
				+	" 풍부한 경험과 전문성을 바탕으로 투자일임자산에 대해 지속적인 모니터링, 리밸런싱 등 종합적인 리스크관리 서비스를 제공합니다.<br><br>"
				+	" 고객 본인 명의로 된 계좌에서 금융투자상품을 운용하기 때문에 언제든지 고객이 직접 계좌현황을 확인할 수 있으므로 금융사고위험으로부터 안전함과 동시에 운용의 투명성 또한 보장됩니다."
				;


		String rowText21 = "<h2 style='font-weight: 600; color: #015ca3;'><br>투자일임 프로세스</h2>";
		
		String rowText22 = "<div><img src='img/h3_line.jpg' no-repeat></img><br><br></div>";
		String rowText23 = "<div><br><img src='img/invest/investprocess.png' width='660' height='120' no-repeat center top;></img><br><br></div>";
		
		String rowText24 = "<strong style='margin-bottom: 5px; font-size: 16px; font-weight: 600; color: #333; line-height: 25px;'>STEP ① 위탁계좌 개설 및 투자권유</strong><br>"
				+	" 증권회사를 방문하여 위탁계좌를 개설하고 투자일임서비스에 관한 투자권유내용을 확인합니다.<br><br>"
				+	" <strong style='margin-bottom: 5px; font-size: 16px; font-weight: 600; color: #333; line-height: 25px;'>STEP ② 투자일임 계약</strong><br>"
				+	" <span style='margin-bottom: 5px; font-size: 16px; font-weight: 600; color: #0078c9; line-height: 25px;'>"
				+	"Case1. 한국채권투자자문 홈페이지를 통해 투자일임계약을 맺는 경우</span>"
				+	" - 한국채권투자자문 홈페이지를 방문하여 투자일임계약서를 작성합니다.<br>"
				+	" ※ 이 경우에도, 계약서를 제외한 기타서류(개인정보동의서, 확인서, CMS출금동의서) 작성을 위해"
				+   " <span style='color: red; font-weight: 600;'>증권회사 방문은 반드시 필요합니다.</span>(위탁계좌 개설 시 작성)<br><br>"
				+	" <span style='margin-bottom: 5px; font-size: 16px; font-weight: 600; color: #0078c9; line-height: 25px;'>"
				+	"Case2. 증권회사를 방문하여 투자일임계약을 맺는 경우 </span><br>"
				+	" 증권회사를 방문하여 투자일임계약서 및 기타서류(개인정보동의서, 확인서, CMS출금동의서)를 모두 작성합니다.<br><br>"
				+	" <strong style='margin-bottom: 5px; font-size: 16px; font-weight: 600; color: #333; line-height: 25px;'>STEP ③ 계약금액 및 수수료 입금 </strong><br>"
				+	" 투자일임계약을 맺은 투자일임계좌에 계약금액 및 수수료를 입금합니다.<br><br>"
				+	" <strong style='margin-bottom: 5px; font-size: 16px; font-weight: 600; color: #333; line-height: 25px;'>STEP ④ 투자일임자산 운용 시작 </strong><br>"
				+	" 한국채권투자자문이 고객의 투자일임계좌에 대한 운용을 시작합니다.<br><br><br><br><br><br>"
				;

		rowString += this.tdData(rowText1, 90, 0);
		rowList.add(this.tr(rowString)) ; 
		
		rowString = "";
		rowString += this.tdData(rowText2, 90, 0);
		rowList.add(this.tr(rowString)) ; 
		
		rowString = "";
		rowString += this.tdData(rowText3, 90, 0);
		rowList.add(this.tr(rowString)) ; 

		rowString = "";
		rowString += this.tdData(rowText21, 90, 0);
		rowList.add(this.tr(rowString)) ; 

		rowString = "";
		rowString += this.tdData(rowText22, 90, 0);
		rowList.add(this.tr(rowString)) ; 

		rowString = "";
		rowString += this.tdData(rowText23, 90, 0);
		rowList.add(this.tr(rowString)) ; 

		rowString = "";
		rowString += this.tdData(rowText24, 90, 0);
		rowList.add(this.tr(rowString)) ; 

		sqlSession.close();
		
		String tableString = "<table style='width:99%; margin:0px; font-size:14px; border-collapse:collapse; padding:0px;'>";
		
		for(String rowString1 : rowList) {
			tableString += rowString1; 
		}
		
		tableString += "</table>" ; 
		
		String returnHtml = tableString ; 
		
		return returnHtml; 
	}

	
	private String tr(String data) {
		return "<tr style='height:auto;'>" + data + "</tr>"; 
	}

	private String tdData(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='width:" + width + "px;" + rowChange + "padding:0px 0px 0px 0px; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
	}

	private String tdData1(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='width:" + width + "px;" + rowChange + "padding:0px 0px 0px 0px; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
	}

	private String tdCenter(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='text-align:center; width:" + width + "px;" + rowChange + "padding:0px 0px 0px 0px; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
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

	private String tdColSpan(int colSpan, String data, int width, String align, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ffffff' colSpan =" + colSpan
				+ " style='text-align:" + align + "; width:" + width
				+ "px;" + rowChange + "padding:0px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}

	
	private String tdColSpanGrey(int colSpan, String data, int width, String align, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' colSpan =" + colSpan
				+ " style='text-align:" + align + "; width:" + width
				+ "px;" + rowChange + "padding:0px; height:auto; word-wrap:break-word;' >" + data 
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
