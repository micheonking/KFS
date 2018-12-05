package myApp.server.apr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr07_AltApprModel;
import myApp.server.utils.db.UpdateDataModel;

public class Apr07_AltAppr {

	private String mapperName = "apr07_alt_appr";

	public void selectByText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		param.put("searchText", "%"+request.getStringParam("searchText")+"%");
		
		String sqlId = mapperName + ".selectByText";
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empId", request.getLongParam("empId"));
		
		String sqlId = mapperName + ".selectByEmpId";
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void insertRoot(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void insertChild(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Apr07_AltApprModel> updateModel = new UpdateDataModel<Apr07_AltApprModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Apr07_AltApprModel> updateModel = new UpdateDataModel<Apr07_AltApprModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
