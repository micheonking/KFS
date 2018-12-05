package myApp.server.emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp10_AddrBookModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp10_AddrBook {

	private String mapperName = "emp10_addr_book";

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}
	
	public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("empId", request.getStringParam("empId"));
		
		System.out.println("empId 입니다.. "+ request.getLongParam("empId"));
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByEmpId", request.getLongParam("empId"));
		System.out.println("list.size()..."+list.size());
		
		result.setRetrieveResult(list.size(), mapperName + ".selectByEmpId", list);
//		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("empId"   , request.getLongParam("empId"));
		param.put("searchText"   , "%" + request.getStringParam("searchText") + "%");
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByText", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp10_AddrBookModel> updateModel = new UpdateDataModel<Emp10_AddrBookModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp10_AddrBookModel> updateModel = new UpdateDataModel<Emp10_AddrBookModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
