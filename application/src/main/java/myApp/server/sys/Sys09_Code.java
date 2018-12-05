package myApp.server.sys;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys09_CodeModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys09_Code {

	private String mapperName  = "sys09_code"; 
	
	public void selectByCodeKindId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeKindId", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByCodeKind(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long companyId = request.getLongParam("companyId"); 
		String kindCode= request.getStringParam("kindCode"); 
		Date applyDate = request.getDateParam("applyDate"); 
		
		if(applyDate ==  null) {
			applyDate = new Date(); 
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId); 
		param.put("kindCode", kindCode);
		param.put("applyDate", applyDate);  
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeKind", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByApplyDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codeKindId", request.getLongParam("codeKindId"));
		param.put("applyDate", Calendar.getInstance().getTime()); // system date 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeKindId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys09_CodeModel> updateModel = new UpdateDataModel<Sys09_CodeModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<Sys09_CodeModel> updateModel = new UpdateDataModel<Sys09_CodeModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}


}
