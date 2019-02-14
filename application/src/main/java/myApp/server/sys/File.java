package myApp.server.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys01_CmpInfoModel;
import myApp.server.utils.db.UpdateDataModel;

public class File {

	private String mapperName = "sys10_file"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByAll");
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long fileId = request.getLongParam("fileId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByParentId",  fileId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByParentId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long parentId = request.getLongParam("parentId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByParentId",  parentId);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String usrNo = request.getStringParam("usrNo");
		UpdateDataModel<Sys01_CmpInfoModel> updateModel = new UpdateDataModel<Sys01_CmpInfoModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, usrNo, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CmpInfoModel> updateModel = new UpdateDataModel<Sys01_CmpInfoModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
