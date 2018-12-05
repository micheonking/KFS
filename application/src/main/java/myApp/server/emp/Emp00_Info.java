package myApp.server.emp;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp01_PersonModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp00_Info {
	
	private String mapperName = "emp00_info";

	public void selectByText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectByText";
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getParam());
		result.setRetrieveResult(list.size(), sqlId, list);
	}

	public void selectByOrgCodeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectByOrgCodeId" ;
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("orgCodeId", request.getLongParam("orgCodeId"));
		
		System.out.println("orgCodeId is " + request.getLongParam("orgCodeId"));
		
		Date baseDate = request.getDateParam("baseDate");  
		if( baseDate != null) { 
			System.out.println("baseDate is " + baseDate); 
			param.put("baseDate", baseDate);
		}
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectTeamLeaderByOrgId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectTeamLeaderByOrgId" ;
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("orgId", request.getLongParam("orgId"));
		
		Date baseDate = request.getDateParam("baseDate");  
		if( baseDate != null) { 
			System.out.println("baseDate is " + baseDate); 
			param.put("baseDate", baseDate);
		}
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectComplianceOfficer(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectComplianceOfficer" ;
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", request.getLongParam("companyId"));
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
		
		
	}

	public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectByEmpId" ;
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("empId", request.getLongParam("empId"));
		param.put("orgId", request.getLongParam("orgId"));
		System.out.println("" + request.getLongParam("empId"));
		System.out.println("" + request.getLongParam("orgId"));
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectByEmpId2(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectByEmpId2" ;
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("empId", request.getLongParam("empId"));
		System.out.println("" + request.getLongParam("empId"));
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp01_PersonModel> updateModel = new UpdateDataModel<Emp01_PersonModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp01_PersonModel> updateModel = new UpdateDataModel<Emp01_PersonModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
