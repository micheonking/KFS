package myApp.server.dcr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.server.utils.db.UpdateDataModel;

public class Dcr04_Doc {
	private String mapperName = "dcr04_doc";

	public void selectByDocTypeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long docTypeId = request.getLongParam("docTypeId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("docTypeId", docTypeId);

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByDocTypeId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByApprId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long apprId = request.getLongParam("apprId");
		Long docTypeId = request.getLongParam("docTypeId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", apprId);

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByApprId", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectSearchDoc(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId");
		Long orgCode = request.getLongParam("orgCode");
		Date startRegDate = request.getDateParam("startRegDate");
		Date endRegDate = request.getDateParam("endRegDate");
		String sintak = request.getStringParam("sintak");
		String publicSubscribe = request.getStringParam("publicSubscribe");
		String personText = request.getStringParam("personText");
		Date startFundDate = request.getDateParam("startFundDate");
		Date endFundDate = request.getDateParam("endFundDate");
		String fundType = request.getStringParam("fundType");
		String fundClose = request.getStringParam("fundClose");
		String orgText = request.getStringParam("orgText");
		String docTypeText = request.getStringParam("docTypeText");
		String fundText = request.getStringParam("fundText");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("orgCode", orgCode);
		param.put("startRegDate", startRegDate);
		param.put("endRegDate", endRegDate);
		param.put("sintak", sintak);
		param.put("publicSubscribe", publicSubscribe);
		param.put("personText", personText);
		param.put("startFundDate", startFundDate);
		param.put("endFundDate", endFundDate);
		param.put("fundType", fundType);
		param.put("fundClose", fundClose);
		param.put("orgText", orgText);
		param.put("docTypeText", docTypeText);
		param.put("fundText", fundText);
		
		for( String key : param.keySet() ){
			System.out.println( "key : " + key + ", value : " + param.get(key)); 
		}
		
		System.out.println("companyId   :"+request.getLongParam("companyId"));
		System.out.println("orgCode     :"+request.getLongParam("orgCode"));
		System.out.println("startRegDate     :"+request.getLongParam("startRegDate"));
		System.out.println("endRegDate     :"+request.getLongParam("endRegDate"));
		System.out.println("sintak     :"+request.getLongParam("sintak"));
		System.out.println("personText     :"+request.getLongParam("personText"));
		System.out.println("publicSubscribe     :"+request.getLongParam("publicSubscribe"));
		System.out.println("startFundDate     :"+request.getLongParam("startFundDate"));
		System.out.println("endFundDate     :"+request.getLongParam("endFundDate"));
		System.out.println("fundType     :"+request.getLongParam("fundType"));
		System.out.println("fundClose     :"+request.getLongParam("fundClose"));
		System.out.println("orgText     :"+request.getLongParam("orgText"));
		System.out.println("docTypeText     :"+request.getLongParam("docTypeText"));
		System.out.println("fundText     :"+request.getLongParam("fundText"));

		List<GridDataModel> list = sqlSession.selectList("dcr00_doc_list" + ".selectSearchDoc", param);
		result.setRetrieveResult(1, "select ok", list);
	}

    public void selectByClassTreeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long docTypeId = request.getLongParam("docTypeId"); 
		Date startDate = request.getDateParam("startDate");
		Date endDate = request.getDateParam("endDate");
		String regEmpName = request.getStringParam("regEmpName");
		String orgName = request.getStringParam("orgName");
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("docTypeId", docTypeId); 
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("regEmpName", "%" + regEmpName + "%");
		param.put("orgName", "%" + orgName + "%");
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByClassTreeId", param);
		
		for(GridDataModel child : dcrClassList){
//			Dcr04_DocModel dcrClassTreeModel = (Dcr04_DocModel)child;
//			if(dcrClassTreeModel.getApprId() != null) {
//				Map<String, Object> param2 = new HashMap<String, Object>(); 
//				param2.put("docTypeId", dcrClassTreeModel.getDocTypeId()); 
//				param2.put("apprId", dcrClassTreeModel.getApprId());
//				param2.put("startDate", startDate);
//				param2.put("endDate", endDate);
//				param2.put("regEmpName", "%" + regEmpName + "%");
//				param2.put("orgName", "%" + orgName + "%");
//				List<GridDataModel> childList = sqlSession.selectList(mapperName + ".selectChild", param2);  
//				dcrClassTreeModel.setChildList(childList);
//			} else {
//			}
		}
		
		result.setRetrieveResult(dcrClassList.size(), "문서분류 정보 조회", dcrClassList);
	}
    
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
			
		List<GridDataModel> updateList = new ArrayList<GridDataModel>();

		for(GridDataModel dataModel : request.getList()) {
//			Dcr04_DocModel updateModel = (Dcr04_DocModel)dataModel; 
//			updateModel.setRegDate(new Date());
//			updateList.add(updateModel);
		}
		
//		UpdateDataModel<Dcr04_DocModel> updateModel = new UpdateDataModel<Dcr04_DocModel>();
//		updateModel.updateModel(sqlSession, updateList, mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
        //UpdateDataModel<Dcr02_DocTypeModel> updateModel = new UpdateDataModel<Dcr02_DocTypeModel>();
		//updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
