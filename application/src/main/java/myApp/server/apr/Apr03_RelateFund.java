package myApp.server.apr;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.server.utils.db.UpdateDataModel;

public class Apr03_RelateFund {
	private String mapperName = "apr03_relate_fund";

	public void selectByApprId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectByApprId";
		Long apprId = request.getLongParam("apprId");
		System.out.println("apprId : " + apprId);
		List<GridDataModel> list = sqlSession.selectList(sqlId, apprId);
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByClassTreeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("classTreeId : " + request.getParam("classTreeId"));
		System.out.println("orgId       : " + request.getParam("orgId"));

		String sqlId = mapperName + ".selectByClassTreeId";
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getParam());
		System.out.println("size : " + list.size());
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByClassTreeId2(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		System.out.println("classTreeId : " + request.getLongParam("classTreeId"));
		System.out.println("startDate   : " + request.getDateParam("startDate"));
		System.out.println("endDate     : " + request.getDateParam("endDate"));
		System.out.println("regEmpName  : " + request.getStringParam("regEmpName"));
		System.out.println("orgName     : " + request.getStringParam("orgName"));
		System.out.println("orgId     : " + request.getStringParam("orgId"));
		System.out.println("admin     : " + request.getStringParam("admin"));
		
		String sqlId = mapperName + ".selectByClassTreeId2";
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getParam());
		result.setRetrieveResult(1, sqlId, list);
	}

	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
