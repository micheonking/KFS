package myApp.server.emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp10_ManagerContactModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp10_ManagerContact {

	private String mapperName = "emp10_manager_contact";

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}
	
	public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("empId", request.getStringParam("empId"));
		
		System.out.println("empId 입니다.. "+ request.getLongParam("empId"));
//		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByEmpId", request.getParam()) ; 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByEmpId", request.getLongParam("empId"));
		
		result.setRetrieveResult(list.size(), mapperName + ".selectByEmpId", list);
//		result.setRetrieveResult(1, "select ok", list);
		
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		System.out.println("update 왔다");
		UpdateDataModel<Emp10_ManagerContactModel> updateModel = new UpdateDataModel<Emp10_ManagerContactModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
		System.out.println("update끝"); 
		
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp10_ManagerContactModel> updateModel = new UpdateDataModel<Emp10_ManagerContactModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
