package myApp.server.cst;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.cst.model.AccModel;
import myApp.server.utils.db.UpdateDataModel;

public class Acc {
	
	private	String mapperName = "cst02_acc"; 
	public void selectByAccId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		request.putLongParam("Acc : ", request.getLongParam("accId"));

		System.out.println("Acc param: " + request.getLongParam("accId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByUserId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putStringParam("userId : ", "%"+request.getLongParam("userId"));

		System.out.println("userId param: " + request.getLongParam("userId")); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByUserId", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AccModel> updateModel = new UpdateDataModel<AccModel>();
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AccModel> updateModel = new UpdateDataModel<AccModel>();
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
