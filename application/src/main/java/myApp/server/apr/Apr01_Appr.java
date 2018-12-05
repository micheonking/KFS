package myApp.server.apr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.apr.model.Apr11_AdvertBookModel;
import myApp.server.utils.db.UpdateDataModel;

public class Apr01_Appr {
	private String mapperName = "apr01_appr";

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("apprId", request.getLongParam("apprId"));
		String sqlId = mapperName + ".selectById"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", request.getLongParam("companyId"));
		param.put("empId", request.getStringParam("empId"));
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		param.put("status", request.getStringParam("status"));
		String sqlId = mapperName + ".selectByEmpId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectApprListByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", request.getLongParam("companyId"));
		param.put("empId", request.getStringParam("empId"));
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		param.put("status", request.getStringParam("status"));
		param.put("aprYn", request.getStringParam("aprYn"));
		
		String sqlId = mapperName + ".selectApprListByEmpId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByClassTreeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String sqlId = mapperName + ".selectByClassTreeId";
		
		Long classTreeId = request.getLongParam("classTreeId");
		List<GridDataModel> list = sqlSession.selectList(sqlId, classTreeId);
		result.setRetrieveResult(1, sqlId, list);
		
	}

	public void selectByFundCodeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long classTreeId = request.getLongParam("classTreeId"); 
		Long fundCodeId = request.getLongParam("fundCodeId");
		Long orgId = request.getLongParam("orgId");
		
		System.out.println("classTreeId : " + classTreeId);
		System.out.println("fundCodeId  : " + fundCodeId);
		System.out.println("orgId       : " + orgId);
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("classTreeId", classTreeId); 
		param.put("fundCodeId", fundCodeId);
		param.put("orgId"     , orgId);
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundCodeId",  param);
		System.out.println("list count  : " + list.size());
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByRequestEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectByRequestEmpId";
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("regEmpId", request.getLongParam("regEmpId"));
		param.put("statusCode", request.getStringParam("statusCode"));
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		param.put("aprYn", request.getStringParam("aprYn"));
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectAplyListByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = mapperName + ".selectAplyListByEmpId"; 

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("empId", request.getStringParam("empId"));
		param.put("apprId", request.getStringParam("apprId"));
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		param.put("status", request.getStringParam("status"));
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		// 상신내용 업데이트  
        List<GridDataModel> apprList = new ArrayList<GridDataModel>();
        apprList.add(request.getModelParam("updateApprModel"));
        Apr01_ApprModel apprModel = (Apr01_ApprModel) request.getModelParam("updateApprModel");
        if(apprModel != null) {
        	if(apprModel.getStampId() != null) {
        		if(apprModel.getStampId() == 0) {
        			apprModel.setStampId(null);
        		}
        	}
        }
        if("30".equals(apprModel.getStatusCode())) {//상신 후 바로 결재완료인 경우
        	if(apprModel.getStampId() != null && apprModel.getStampId() != 0) {//인감이 있어서 인감대장번호 저장
        		Apr01_ApprModel tempModel = sqlSession.selectOne("apr01_appr.getStampSeq");
        		apprModel.setStampSeq(tempModel.getStampSeq());
        	}
        }
        UpdateDataModel<GridDataModel> updateApprModel = new UpdateDataModel<GridDataModel>();
		updateApprModel.updateModel(sqlSession, apprList, mapperName, result);

		if(result.getStatus() > 0) {
	        // 결재선 업데이트
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("apprId", apprModel.getApprId()); 
			sqlSession.delete("apr04_appr_step.deleteByApprId", param);
			
			@SuppressWarnings("unchecked")
			List<GridDataModel> apprStepList = (List<GridDataModel>)request.getParam("apprStepList");
			UpdateDataModel<GridDataModel> updateStepModel = new UpdateDataModel<GridDataModel>(); 
			updateStepModel.updateModel(sqlSession, apprStepList, "apr04_appr_step", result);
		}
		else {
			return ; 
		}
		
		if(result.getStatus() > 0) {
	        // 연관펀드 업데이트  
			@SuppressWarnings("unchecked")
			List<GridDataModel> relateFundList = (List<GridDataModel>)request.getParam("relateFundList");
			UpdateDataModel<GridDataModel> updateFundModel = new UpdateDataModel<GridDataModel>(); 
			updateFundModel.updateModel(sqlSession, relateFundList, "apr03_relate_fund", result);
		}
		else {
			return ; 
		}
		
		if(result.getStatus() > 0) {
	        // 연관문서 업데이트  
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("apprId", apprModel.getApprId()); 
			sqlSession.delete("apr05_relate_doc.deleteByApprId", param);
			
			@SuppressWarnings("unchecked")
			List<GridDataModel> relateDocList = (List<GridDataModel>)request.getParam("relateDocList");
			UpdateDataModel<GridDataModel> updateDocModel = new UpdateDataModel<GridDataModel>(); 
			updateDocModel.updateModel(sqlSession, relateDocList, "apr05_relate_doc", result);
		}
		else {
			return ; 
		}
		
		if("30".equals(apprModel.getStatusCode())) {//상신 후 바로 결재완료인 경우
			if("true".equals(apprModel.getClassTreeModel().getAdvertYn())) {//광고대장 insert
				Apr11_AdvertBookModel advertBookModel = sqlSession.selectOne("apr11_advert_book.getAdvertSeq");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("advertSeq", advertBookModel.getAdvertSeq());
				param.put("apprId", apprModel.getApprId());//결재문서번호
				param.put("companyId", request.getStringParam("companyId"));
				sqlSession.insert("apr11_advert_book.insertAdvertHistory", param);
			} else {
			}
//			if(apprModel.getStampId() != null && apprModel.getStampId() != 0) {//인감이 있어서 PDF 생성
			if(apprModel.getInOutTypeCode() == "10") {	//대외문서인 경우 PDF생성
				result.setRetrieveResult(1,  "makepdf", null);
			}
		}
	}
	
	public void appr(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
        Map<String, Object> tempMap = request.getParam();
        List<GridDataModel> apprStepList = (List<GridDataModel>) tempMap.get("apprStepGrid");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", tempMap.get("companyId"));
		param.put("empId", tempMap.get("empId"));
		param.put("apprId", tempMap.get("apprId"));
		
		
		UpdateDataModel<Apr04_ApprStepModel> updateStepModel = new UpdateDataModel<Apr04_ApprStepModel>(); 
		updateStepModel.updateModel(sqlSession, apprStepList, "apr04_appr_step", result);
		
		if(result.getStatus() > 0) {
			String sqlId = "apr04_appr_step" + ".selectApprStep";
			List<GridDataModel> list = sqlSession.selectList(sqlId, param);
			if(list.size() >= 1) {
				param.put("statusCd", "30");
				sqlSession.update(mapperName +".updateStepCd", param);
			} else {
				param.put("statusCd", "20");
				sqlSession.update(mapperName +".updateStepCd", param);
			}
		}
	}
	
	public void reject(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
        Map<String, Object> tempMap = request.getParam();
        List<GridDataModel> apprStepList = (List<GridDataModel>) tempMap.get("apprStepGrid");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", tempMap.get("companyId"));
		param.put("empId", tempMap.get("empId"));
		param.put("apprId", tempMap.get("apprId"));
		param.put("statusCd", "40");
		sqlSession.update(mapperName +".updateStepCd", param);
		
		UpdateDataModel<Apr04_ApprStepModel> updateStepModel = new UpdateDataModel<Apr04_ApprStepModel>(); 
		updateStepModel.updateModel(sqlSession, apprStepList, "apr04_appr_step", result);
	}
	
//	public void selectCount(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//        Map<String, Object> param = new HashMap<String, Object>();
//		param.put("companyId", request.getLongParam("companyId"));
//		param.put("empId", request.getStringParam("empId"));
//		param.put("apprId", request.getStringParam("apprId"));
//
//		String sqlId = "apr04_appr_step" + ".selectCount"; 
//		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
//		result.setRetrieveResult(1, sqlId, list);
//	}

	public void selectOutstanding(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam	 ("companyId"));
		param.put("empId"	 , request.getStringParam("empId"));
		param.put("toDay"	 , request.getDateParam	 ("toDay"));
		String sqlId = mapperName + ".selectOutstanding"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(list.size(), sqlId, list);
		System.out.println("미결함 list.size() : " + list.size());
	}

	public void selectMonitorngAppr(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam	 ("companyId"));
		param.put("empId"	 , request.getStringParam("empId"));
		param.put("toDay"	 , request.getDateParam	 ("toDay"));
		String sqlId = mapperName + ".selectMonitorngAppr"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(list.size(), sqlId, list);
		System.out.println("결재요청함 list.size() : " + list.size());
	}

	public void selectToDoList(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId" , request.getLongParam  ("companyId"));
		param.put("empId"	  , request.getLongParam  ("empId"));
		param.put("orgCd"	  , request.getStringParam("orgCd"));
		param.put("selectDate", request.getDateParam("selectDate"));
		
		System.out.println("selectDate ====>" + request.getDateParam("selectDate"));

		String sqlId = mapperName + ".selectToDoList"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(list.size(), sqlId, list);
		System.out.println("ToDo list.size() : " + list.size());
	}

	public void deleteByApprId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long apprId = request.getLongParam("apprId");

		// 문서삭제
		// (1) apr03_relate_fund delete
		sqlSession.delete("apr03_relate_fund.deleteByApprId", apprId);
		System.out.println("1. apr03_relate_fund delete~!! ok");

		// (2) sys10_file delete
		sqlSession.delete("sys10_file.deleteByParentId", apprId);
		System.out.println("2. sys10_file delete~!! ok");
		
		// (3) apr04_appr_step delete
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", apprId);
		sqlSession.delete("apr04_appr_step.deleteByApprId", param);
		System.out.println("3. apr04_appr_step delete~!! ok");

		// (4) apr01_appr delete
		sqlSession.delete("apr01_appr.deleteAppr", apprId);
		System.out.println("4. apr01_appr delete~!! ok");

		// 운용보고서 상신ID 초기화
		sqlSession.update("opr01_create.updateApprIdByApprId", apprId);
		System.out.println("운용보고서 상신ID 초기화 ok");
		
		// 기획펀드 상신ID 초기화
		sqlSession.update("pln02_plan.updateApprIdByApprId", apprId);
		System.out.println("기획펀드 상신ID 초기화 ok");
		
		// 광고대장 삭제
		sqlSession.delete("apr11_advert_book.deleteByApprId", apprId);
		System.out.println("광고대장 삭제 ok");

		result.setStatus(1);
	}

}
