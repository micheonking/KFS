package myApp.server.dbm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dbm.model.Dbm03_TabColumnsModel;

public class Dbm03_TabColumns {
	String mapperName = "dbm03_tab_columns"; 

	public void selectByXML(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", request.getStringParam("tableName"));

		sqlSession.selectList(mapperName + ".selectByXML", param);

		@SuppressWarnings("unchecked")
		List<GridDataModel> list = (List<GridDataModel>)param.get("result"); 

		String xmlString = "";
		
		for(GridDataModel data : list) {
			
			Dbm03_TabColumnsModel col = (Dbm03_TabColumnsModel)data;
			xmlString = xmlString + col.getErrorMsg() + "\n"; 
		}

		System.out.println("dbm01_tab_comments param: " + xmlString);
		
		result.setRetrieveResult(1, xmlString, list);
	}

	public void selectByModel(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", request.getStringParam("tableName"));

		sqlSession.selectList(mapperName + ".selectByModel", param);

		@SuppressWarnings("unchecked")
		List<GridDataModel> list = (List<GridDataModel>)param.get("result"); 

		String modelString = "";
		
		for(GridDataModel data : list) {
			
			Dbm03_TabColumnsModel col = (Dbm03_TabColumnsModel)data;
			modelString = modelString + col.getErrorMsg() + "\n"; 
		}

		System.out.println("dbm01_tab_comments param: " + modelString);
		
		result.setRetrieveResult(1, modelString, list);
	}

	public void selectByProperties(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", request.getStringParam("tableName"));

		sqlSession.selectList(mapperName + ".selectByProperties", param);

		@SuppressWarnings("unchecked")
		List<GridDataModel> list = (List<GridDataModel>)param.get("result"); 

		String propertiesString = "";
		
		for(GridDataModel data : list) {
			
			Dbm03_TabColumnsModel col = (Dbm03_TabColumnsModel)data;
			propertiesString = propertiesString + col.getErrorMsg() + "\n"; 
		}

		System.out.println("dbm01_tab_comments param: " + propertiesString);
		
		result.setRetrieveResult(1, propertiesString, list);
	}

	public void selectByGridBuilder(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", request.getStringParam("tableName"));
		param.put("inOut", request.getLongParam("inOut"));

		sqlSession.selectList(mapperName + ".selectByGridBuilder", param);

		@SuppressWarnings("unchecked")
		List<GridDataModel> list = (List<GridDataModel>)param.get("result"); 

		String propertiesString = "";
		
		for(GridDataModel data : list) {
			
			Dbm03_TabColumnsModel col = (Dbm03_TabColumnsModel)data;
			propertiesString = propertiesString + col.getErrorMsg() + "\n"; 
		}

		System.out.println("dbm01_tab_comments param: " + propertiesString);
		
		result.setRetrieveResult(1, propertiesString, list);
	}

}

