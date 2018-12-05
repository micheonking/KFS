package myApp.server.apr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.server.utils.db.UpdateDataModel;

public class Apr11_AdvertBook {

	private String mapperName = "apr11_advert_book";
	
	public void selectByApprDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		
		System.out.println(param.get("companyId"));
		System.out.println(param.get("startDate"));
		System.out.println(param.get("endDate"));
		
		String sqlId = mapperName + ".selectByApprDate"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		
		result.setRetrieveResult(1, sqlId, list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
