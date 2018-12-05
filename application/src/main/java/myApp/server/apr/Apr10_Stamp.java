package myApp.server.apr;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.server.utils.db.UpdateDataModel;

public class Apr10_Stamp {

	private String mapperName = "apr10_stamp";
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long stampId = request.getLongParam("stampId"); 
		System.out.println("zzz" + stampId);
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", stampId);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	
	
	public void selectByOrgId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashedMap<String, Object>();
		param.put("orgId", request.getLongParam("orgId"));
		param.put("companyId", request.getLongParam("companyId"));
		param.put("searchText", "%" + request.getStringParam("searchText") + "%");
		System.out.println("" + request.getLongParam("orgId"));
		System.out.println("" + request.getLongParam("companyId"));
		System.out.println("" + param.get("searchText"));
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByOrgId", param) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByOrgId", list);
	}

	public void selectByCodeName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("orgId : " + request.getLongParam("orgId"));
		
		Map<String, Object> param = new HashedMap<String, Object>();
		param.put("orgId", request.getLongParam("orgId"));
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeName", param) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByCodeName", list);
	}

	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
	}
	
	public void selectBySearchText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashedMap<String, Object>();
		param.put("searchText", "%" + request.getStringParam("searchText") + "%");
		param.put("companyId", request.getLongParam("companyId"));
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectBySearchText", param) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectBySearchText", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		System.out.println("인감인감인감인감인감");
		System.out.println("저장");
		UpdateDataModel<Apr10_StampModel> updateModel = new UpdateDataModel<Apr10_StampModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Apr10_StampModel> updateModel = new UpdateDataModel<Apr10_StampModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
