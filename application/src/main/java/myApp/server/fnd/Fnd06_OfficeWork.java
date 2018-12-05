package myApp.server.fnd;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.fnd.model.Fnd06_OfficeWorkModel;
import myApp.server.utils.db.UpdateDataModel;

public class Fnd06_OfficeWork {

	private String mapperName = "fnd06_office_work";

	public void selectByCodeName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String officeWorkName = request.getStringParam("officeWorkName");
		if (officeWorkName == null) {
			officeWorkName = "%";
		}
		
		Map<String, String> param = new HashedMap<String, String>();
		param.put("officeWorkName"	, officeWorkName);
		param.put("officeWorkName","%" + request.getStringParam("officeWorkName")+"%");
		
		System.out.println("사무수탁사명"+request.getStringParam("officeWorkName"));
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeName", param) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByCodeName", list);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByOfficeWorkName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByOfficeWorkName", request.getParam()) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByOfficeWorkName", list);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Fnd06_OfficeWorkModel> updateModel = new UpdateDataModel<Fnd06_OfficeWorkModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
