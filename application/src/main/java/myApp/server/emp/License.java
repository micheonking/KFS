package myApp.server.emp;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.LicenseModel;
import myApp.server.utils.db.UpdateDataModel;

public class License {

	private String mapperName  = "emp02_license"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long licenseId = request.getLongParam("licenseId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", licenseId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByUserId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long userId = request.getLongParam("userId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByUserId", userId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByToday(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByToday", companyId);
		result.setRetrieveResult(1, "select ok", list);
	}

	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<LicenseModel> updateModel = new UpdateDataModel<LicenseModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<LicenseModel> updateModel = new UpdateDataModel<LicenseModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}


}
