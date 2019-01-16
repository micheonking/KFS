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

import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.vi.hom.company.model.Hom01_OutlineModel;
import myApp.server.utils.db.DatabaseFactory;

public class HTMLOutline implements javax.servlet.Servlet {

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
		
		List<Hom01_OutlineModel> list = sqlSession.selectList("hom01_outline.selectByMaxDate",2000940L);	

		for(int i = 0 ; i<list.size(); i++) {
			
			Hom01_OutlineModel outlineModel = list.get(i); 
			
			String	rowString = "";
			rowString += this.tdCenterBold("설립일", 100, 0); 	
			rowString += this.tdData("한국펀드서비스주식회사", 250, 0);
			rowList.add(this.tr(rowString)) ; 

			Date ddate = outlineModel.getRegistDt();
//			String ymd = DateTimeFormat.getFormat("yyyy-MM-dd").format(ddate);
			
			rowString = "";
			rowString += this.tdCenterBold("설립일", 100, 1); 	
			rowString += this.tdData(ddate+"", 250, 1);
			rowList.add(this.tr(rowString)) ; 

			rowString = "";
			rowString += this.tdCenterBold("납입자본금", 100, 0); 	
			rowString += this.tdData(outlineModel.getPaidInCapital(), 250, 0);
			rowList.add(this.tr(rowString)) ; 

//			rowString = "";
//			rowString += this.tdCenterBold("자기자본", 100, 1); 	
//			rowString += this.tdData(outlineModel.getOwnersEquity(), 250, 1);
//			rowList.add(this.tr(rowString)) ; 
//
//			rowString = "";
//			rowString += this.tdCenterBold("계약자산규모", 100, 0); 	
//			rowString += this.tdData(outlineModel.getContractAssetSize(), 250, 0);
//			rowList.add(this.tr(rowString)) ; 
//
//			rowString = "";
//			rowString += this.tdCenterBold("인력", 100, 1); 	
//			rowString += this.tdData(outlineModel.getProfessionalPersonnel(), 250, 1);
//			rowList.add(this.tr(rowString)) ; 

			rowString = "";
			rowString += this.tdCenterBold("주요업무", 100, 0); 	
			rowString += this.tdData(outlineModel.getMainBusiness(), 250, 0);
			rowList.add(this.tr(rowString)) ; 

			rowString = "";
			rowString += this.tdCenterBold("대표이사", 100, 1); 	
			rowString += this.tdData(outlineModel.getCeoName(), 250, 1);
			rowList.add(this.tr(rowString)) ; 

			rowString = "";
			rowString += this.tdCenterBold("주주구성", 100, 0); 	
			rowString += this.tdData(outlineModel.getConstituteStockholder(), 250, 0);
			rowList.add(this.tr(rowString)) ; 

			rowString = "";
			rowString += this.tdCenterBold("주소", 100, 1); 	
			rowString += this.tdData("서울시 영등포구 의사당대로 143 금융투자협회빌딩 5층", 250, 1);
			rowList.add(this.tr(rowString)) ; 

		}
		
		sqlSession.close();
		
//		String tableString = "<table border=1 style='width:99%; margin:0px; font-size:12px; border:1px silver solid; border-top: 2px solid #023d69; border-collapse:collapse; padding:0px;'>";
//		String tableString = "<table border=1 style='width:99%; margin:0px; font-size:12px; border-bottom:2px silver solid; border-top: 2px solid #023d69; border-collapse:collapse; padding:0px;'>";
		String tableString = "<table border=1 style='width:99%; margin:0px; font-size:13px; border-botttom:2px silver solid; border-top: 2px solid #023d69; "
				+	"border-left: 1px solid white; border-right: 1px solid white; border-collapse:collapse; padding:0px;'>";

		for(String rowString1 : rowList) {
			tableString += rowString1; 
		}
		
		tableString += "</table>" ; 
		
		String returnHtml = header + tableString ; 
		
		return returnHtml; 
	}

	
//	tr:td 만들기	/////////////////////////////////////////////////////////////////////////////////////////	
	private String tr(String data) {
		return "<tr style='height:auto;'>" + data + "</tr>"; 
	}

	private String tdData(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='width:" + width + "px;" + rowChange + "padding:15px; height:auto; word-wrap:break-word; border-bottom:1px silver solid;' >" + data + "</td>" ; 
	}

	private String tdCenter(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='text-align:center; width:" + width + "px;" + rowChange + "padding:15px; height:auto; word-wrap:break-word; border-bottom: 1px solid #aaa; ' >" + data + "</td>" ; 
	}

	private String tdCenterBold(String data, int width, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td style='text-align:center; width:" + width + "px;" + rowChange + "padding:5px; font-weight:bold; height:auto; word-wrap:break-word;' >" + data + "</td>" ; 
	}

	private String tdRowSpan(int rowSpan, String data, int width, String align, int rowcount) {
		
		int rs = rowSpan; 
		if(rs<1) rs = 1 ;
		
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td rowspan=" + rs 
				+ " style='text-align:" + align + "; width:" + width 
				+ "px;" + rowChange + "padding:15px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}
	
	private String tdGrey(String data, int width, String align, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' style='text-align:" + align + "; width:" + width
				+ "px;" + rowChange + "padding:15px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}

	private String tdColSpanGrey(int colSpan, String data, int width, String align, int rowcount) {
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' colSpan =" + colSpan
				+ " style='text-align:" + align + "; width:" + width
				+ "px;" + rowChange + "padding:15px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}

	
	private String tdRowSpanGrey(int rowSpan, String data, int width, String align, int rowcount) {
		int rs = rowSpan; 
		if(rs<1) rs = 1 ;
	
		String rowChange = " ";
		if(rowcount == 1) rowChange = " background-color:#f5f5f5; "; 

		return "<td bgcolor='#ebebec' rowspan=" + rs 
				+ " style='text-align:" + align + "; width:" + width 
				+ "px;" + rowChange + "padding:15px; height:auto; word-wrap:break-word;' >" + data 
				+ "</td>" ; 
	}
//	tr:td 만들기	/////////////////////////////////////////////////////////////////////////////////////////	

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
