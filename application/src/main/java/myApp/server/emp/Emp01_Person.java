package myApp.server.emp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp01_PersonModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp01_Person {
	
	private String mapperName = "emp01_person";

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp01_PersonModel> updateModel = new UpdateDataModel<Emp01_PersonModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp01_PersonModel> updateModel = new UpdateDataModel<Emp01_PersonModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
	public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String empId = mapperName + ".selectByEmpId";  
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("empId", request.getLongParam("empId"));
		 
		List<GridDataModel> list = sqlSession.selectList(empId, param);
		result.setRetrieveResult(1, empId, list);
	}
	
	public void changeMail(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sql = mapperName + ".changeMail";  
		String empId = mapperName + ".selectByEmpId";
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("empId", request.getLongParam("empId"));
		 
		Emp01_PersonModel tempModel = new Emp01_PersonModel();
		List<GridDataModel> tempList = sqlSession.selectList(empId, param);
		for(int i = 0; i < tempList.size(); i++) {
			tempModel = (Emp01_PersonModel) tempList.get(0);
		}
		
		param.put("personId", tempModel.getPersonId());
		param.put("mailAddress", request.getStringParam("mailAddress"));
		param.put("password", request.getStringParam("password"));
		sqlSession.update(sql, param);
	}
	
	
}
