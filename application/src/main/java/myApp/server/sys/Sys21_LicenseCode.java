package myApp.server.sys;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys21_LicenseCodeModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys21_LicenseCode {

	private String mapperName  = "sys21_license_code"; 
	
	public void selectByName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String licenseName = request.getStringParam("licenseName"); 
		if(licenseName != null){
			licenseName = "%" + licenseName + "%";
		}
		else {
			licenseName = "%"; 
		}
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByName", licenseName);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByApplyDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyDate", Calendar.getInstance().getTime()); // system date 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeKindId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys21_LicenseCodeModel> updateModel = new UpdateDataModel<Sys21_LicenseCodeModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<Sys21_LicenseCodeModel> updateModel = new UpdateDataModel<Sys21_LicenseCodeModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
}
