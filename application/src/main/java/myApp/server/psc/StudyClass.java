package myApp.server.psc;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.psc.model.StudyClassModel;
import myApp.server.utils.db.UpdateDataModel;

public class StudyClass {

	private String mapperName  = "psc01_study_class"; 
	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", companyId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByName", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<StudyClassModel> updateModel = new UpdateDataModel<StudyClassModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<StudyClassModel> updateModel = new UpdateDataModel<StudyClassModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}


}
