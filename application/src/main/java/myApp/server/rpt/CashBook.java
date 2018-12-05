package myApp.server.rpt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class CashBook { 
	
	String mapperName = "rpt02_CashBook"; 
	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		System.out.println("rpt02_CashBook param: " + request.getLongParam("companyId") ); 

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("beginDate", request.getDateParam("beginDate"));
		param.put("endDate", request.getDateParam("endDate"));
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", param);

//		System.out.println("rpt02_CashBook param: " + param.values() ); 
		System.out.println("rpt02_CashBook size: " + list.size() );
		
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByCompanyIdResult(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("beginDate", request.getDateParam("beginDate"));
		param.put("endDate", request.getDateParam("endDate"));

		sqlSession.selectList(mapperName + ".selectByCompanyIdResult", param);

		@SuppressWarnings("unchecked")
		List<GridDataModel> list = (List<GridDataModel>)param.get("result"); 

		System.out.println("rpt02_CashBook size list: " + list.size() );

		result.setRetrieveResult(1, "select ok", list);

	}

	public void selectByCompanyIdProc(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("beginDate", request.getDateParam("beginDate"));
		param.put("endDate", request.getDateParam("endDate"));

		sqlSession.selectList(mapperName + ".selectByCompanyIdProc", param);

		@SuppressWarnings("unchecked")
		List<GridDataModel> list = (List<GridDataModel>)param.get("result"); 

		System.out.println("rpt02_CashBook size list: " + list.size() );

		result.setRetrieveResult(1, "select ok", list);

	}

}
