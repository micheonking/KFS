package myApp.server.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys08_CodeKindModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys08_CodeKind { 
	String mapperName = "sys08_code_kind"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request,
			ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByAll");
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys08_CodeKindModel> updateModel = new UpdateDataModel<Sys08_CodeKindModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 하위코드가 있는지 체크 필요.   
		UpdateDataModel<Sys08_CodeKindModel> updateModel = new UpdateDataModel<Sys08_CodeKindModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
