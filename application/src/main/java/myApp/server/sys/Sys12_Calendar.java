package myApp.server.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.google.gwt.dev.util.collect.HashMap;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys12_Calendar {

	private String mapperName = "sys12_calendar"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long calendarId = request.getLongParam("calendarId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById",  calendarId);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectByYear(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long   companyId = request.getLongParam("companyId");
		String year  	 = request.getStringParam("year");
		String month 	 = request.getStringParam("month");
		if (month == null) {
			month = "%";
		}
		
		System.out.println("companyId : " + companyId);
		System.out.println("year : " + year);
		System.out.println("month : " + month);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("year"	 , year);
		param.put("month"	 , month);

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByYear",  param);
		
		System.out.println("list.size() : " + list.size());
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CompanyModel> updateModel = new UpdateDataModel<Sys01_CompanyModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CompanyModel> updateModel = new UpdateDataModel<Sys01_CompanyModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
