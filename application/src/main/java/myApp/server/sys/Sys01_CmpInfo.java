package myApp.server.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys01_CmpInfoModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys01_CmpInfo {

	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList("sys01_cmpInfo.selectByAll");
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByCmpName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String cmpName = "%" + request.getStringParam("cmpName") + "%";
		List<GridDataModel> list = sqlSession.selectList("sys01_company.selectByCmpName",  cmpName);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByMenuId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = "sys01_cmpInfo.selectByMenuId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getLongParam("menuId")) ;
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CmpInfoModel> updateModel = new UpdateDataModel<Sys01_CmpInfoModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), "sys01_cmpInfo", result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CmpInfoModel> updateModel = new UpdateDataModel<Sys01_CmpInfoModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), "sys01_cmpInfo", result);
	}

}
