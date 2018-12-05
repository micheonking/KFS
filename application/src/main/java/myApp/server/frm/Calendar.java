package myApp.server.frm;
 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.server.utils.db.DatabaseFactory;

public class Calendar implements javax.servlet.Servlet {

	private String startDate;
	private String closeDate;
	private String empId;
	private String orgCd;

	JsonArray array = new JsonArray();

	private void Calendar(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8"); // encoding 해주어야 한글메세지가 보인다.
		
		startDate = request.getParameter("startDate");
		closeDate = request.getParameter("endDate");
		empId	  = request.getParameter("empId");
		orgCd	  = request.getParameter("orgCd");
		
		Gson gson = new Gson();

		//일정담기
		addCalendar();
		
		String j = gson.toJson(array);
		array = new JsonArray();
		
	    response.setContentType("application/x-json; charset=UTF-8");
	    response.getWriter().print(j);
	}

	private void addCalendar() {

		SqlSession sqlSession = DatabaseFactory.openSession();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId"		, Long.parseLong(empId));
		System.out.println("" + orgCd);
		if(orgCd == null || "null".equals(orgCd)) {
			param.put("orgCd"		, Long.parseLong("0"));
		} else {
			param.put("orgCd"		, Long.parseLong(orgCd));
			
		}
		param.put("startDate"	, startDate);
		param.put("closeDate"	, closeDate);

		System.out.println("userId : " + empId);
		System.out.println("orgCd : " + orgCd);
		System.out.println("startDate : " + startDate);
		System.out.println("closeDate : " + closeDate);

		List<GridDataModel> list = sqlSession.selectList("apr01_appr.selectCalendar", param);
		
		System.out.println("====================================================.." + list.size());
		
		for(GridDataModel data : list) {
			
			Apr01_ApprModel apprModel = (Apr01_ApprModel) data;
			JsonObject json = new JsonObject();
			
			String title = apprModel.getTitle();
			json.addProperty("title", title);

			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = apprModel.getRegDate();
			String s_date = transFormat.format(date);
			
			json.addProperty("start", s_date);
			json.addProperty("end"  , s_date);
			
			if (apprModel.getNote().equals("일정관리")) {
				json.addProperty("color", "#fbd2d9");
			}
			else if (apprModel.getNote().equals("상품기획")) {
				json.addProperty("color", "#fffcad");
			}
			else if (apprModel.getNote().equals("운용보고")) {
				json.addProperty("color", "#d1eef9");
			}
			else if (apprModel.getNote().equals("상신문서")) {
				json.addProperty("color", "#c7e2ca");
			}
			json.addProperty("textColor", "#7f7878");
			
			array.add(json);
		}

		sqlSession.close();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.Calendar((HttpServletRequest)arg0, (HttpServletResponse)arg1);
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}