package myApp.server.psc;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.psc.model.TeacherModel;
import myApp.server.utils.db.UpdateDataModel;

public class Teacher {

	private String mapperName  = "psc02_teacher"; 
	
	public void selectByStudyClassId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long studyClassId = request.getLongParam("studyClassId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByStudyClassId", studyClassId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		System.out.println("teacher update"); 
		UpdateDataModel<TeacherModel> updateModel = new UpdateDataModel<TeacherModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<TeacherModel> updateModel = new UpdateDataModel<TeacherModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
