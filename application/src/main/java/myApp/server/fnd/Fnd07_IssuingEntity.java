package myApp.server.fnd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModel;
import myApp.server.utils.db.UpdateDataModel;

public class Fnd07_IssuingEntity {

	private String mapperName = "fnd07_issuing_entity";

	public void selectByCodeName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String issuingEntityName = request.getStringParam("consignmentName");
		if (issuingEntityName == null) {
			issuingEntityName = "%";
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("issuingEntityName"	, issuingEntityName);
		param.put("issuingEntityName", "%" + request.getStringParam("issuingEntityName") + "%");
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeName", param) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByCodeName", list);
	}

	public void selectBysearchText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String searchText = "%" + request.getStringParam("searchText") + "%";
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectBysearchText", searchText); 
 		result.setRetrieveResult(list.size(), "select ok", list);
	}
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Fnd07_IssuingEntityModel> updateModel = new UpdateDataModel<Fnd07_IssuingEntityModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
	
}
