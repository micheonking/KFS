package myApp.server.psc;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.psc.model.RegisterModel;
import myApp.server.utils.db.UpdateDataModel;

public class Register {

	private String mapperName  = "psc05_register"; 
	
	public void selectByStudentId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long studentId = request.getLongParam("studentId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByStudentId", studentId);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<RegisterModel> updateModel = new UpdateDataModel<RegisterModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<RegisterModel> updateModel = new UpdateDataModel<RegisterModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
