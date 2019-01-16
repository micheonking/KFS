package myApp.server.cst;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr03_RelateFundModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.pln.model.Pln02_PlanModel;
import myApp.server.apr.Apr04_ApprStep;
import myApp.server.utils.db.UpdateDataModel;

public class Cst99_Account { 
	
	private	String mapperName = "cst02_account"; 

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = this.mapperName + ".selectById"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getLongParam("userId")) ;
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByAccountList(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("userId", request.getLongParam("userId") );
		System.out.println("userId   param: " + request.getLongParam("userId") ); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByAccountList", request.getLongParam("userId"));
		System.out.println("listSize "+ list.size());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByAccountName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("userId", request.getLongParam("userId") );
		request.putStringParam("accountName", request.getStringParam("accountName") );

		System.out.println("userId   param: " + request.getLongParam("userId") ); 
		System.out.println("accountName  param: " + request.getStringParam("accountName") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByAccountName", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByUserId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("userId   param: " + request.getLongParam("userId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByUserId", request.getLongParam("userId"));
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
//		for(GridDataModel data : request.getList()) {
//			Pln02_PlanModel planModel = (Pln02_PlanModel)data; 
//			if(planModel.getFundId() == null) {
//				result.setRetrieveResult(-1, "펀드선택이 되지 않았습니다.", null);
//				return; 
//			}
//		}
		UpdateDataModel<Pln02_PlanModel> updateModel = new UpdateDataModel<Pln02_PlanModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//		for (GridDataModel data : request.getList()) {
//			Pln02_PlanModel deleteModel = (Pln02_PlanModel) data;
//			if (deleteModel.getApprId() != null) {
//				deleteAppr(sqlSession, deleteModel.getApprId());
//			}
//		}
		UpdateDataModel<Pln02_PlanModel> updateModel = new UpdateDataModel<Pln02_PlanModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
}
