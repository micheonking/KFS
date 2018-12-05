package myApp.server.fnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd02_FundInfoModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.server.utils.db.UpdateDataModel;

public class Fnd01_FundCode {

	private String mapperName = "fnd01_fund_code";
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("fundCodeId", request.getLongParam("fundCodeId"));

		System.out.println("fnd01_fund_info param: " + request.getStringParam("fundCodeId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByFundName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putStringParam("fundName", "%"+request.getStringParam("fundName")+"%");

		System.out.println("companyId param: " + request.getLongParam("companyId"));
		System.out.println("closeYn param: " + request.getStringParam("closeYn")); 
		System.out.println("startDate param: " + request.getStringParam("startDate"));
		System.out.println("fundName param: " + request.getStringParam("fundName"));
		System.out.println("fundTypeCode param: " + request.getStringParam("fundTypeCode"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundName", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByFundOrgAuth(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putStringParam("fundName", "%"+request.getStringParam("fundName")+"%");

		System.out.println("companyId param: " + request.getLongParam("companyId"));
		System.out.println("orgId param: " + request.getLongParam("orgId"));
		System.out.println("closeYn param: " + request.getStringParam("closeYn")); 
		System.out.println("startDate param: " + request.getStringParam("startDate"));
		System.out.println("fundName param: " + request.getStringParam("fundName"));
		System.out.println("fundTypeCode param: " + request.getStringParam("fundTypeCode"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundOrgAuth", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByWorkDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("companyId", request.getLongParam("companyId"));
		request.putStringParam("closeYn", request.getStringParam("closeYn"));
		request.putStringParam("workDate",	request.getStringParam("workDate"));
		request.putStringParam("fundName", "%"+request.getStringParam("fundName")+"%");
		request.putStringParam("fundTypeCode",	request.getStringParam("fundTypeCode"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByWorkDate", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByFundPlan(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String fundTypeCode = request.getStringParam("fundTypeCode");
		if (fundTypeCode == null) {
			fundTypeCode = "%";
		}
		
		System.out.println("companyId plan param : " + request.getLongParam("companyId"));
//		System.out.println("startDate plan param : " + request.getDateParam("startDate"));
//		System.out.println("endDate plan param : " + request.getDateParam("endDate"));
		System.out.println("fundTypeCode plan param : " + fundTypeCode);
		System.out.println("fundName plan param : " + "%"+request.getStringParam("fundName")+"%");
		System.out.println("closeYn plan param : " + request.getStringParam("closeYn")); 
		
		request.putLongParam  ("companyId"		, request.getLongParam("companyId"));
//		request.putDateParam  ("startDate"		, request.getDateParam("startDate"));
//		request.putDateParam  ("endDate"		, request.getDateParam("endDate"));
		request.putStringParam("fundTypeCode"	, fundTypeCode);
		request.putStringParam("fundName"		, "%"+request.getStringParam("fundName")+"%");
		request.putStringParam("closeYn"		, request.getStringParam("closeYn"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundPlan", request.getParam());
		
		System.out.println("size ==> " + list.size());
		
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectByFundPlanEmp(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String fundTypeCode = request.getStringParam("fundTypeCode");
		if (fundTypeCode == null) {
			fundTypeCode = "%";
		}
		
		System.out.println("companyId    : " + request.getLongParam("companyId"));
		System.out.println("orgId        : " + request.getLongParam("orgId"));
//		System.out.println("startDate    : " + request.getDateParam("startDate"));
//		System.out.println("endDate      : " + request.getDateParam("endDate"));
		System.out.println("fundTypeCode : " + fundTypeCode);
		System.out.println("fundName     : " + "%"+request.getStringParam("fundName")+"%");
		System.out.println("closeYn     : " + request.getStringParam("closeYn")); 
		
		request.putLongParam  ("companyId"		, request.getLongParam("companyId"));
		request.putLongParam  ("orgId"			, request.getLongParam("orgId"));
//		request.putDateParam  ("startDate"		, request.getDateParam("startDate"));
//		request.putDateParam  ("endDate"		, request.getDateParam("endDate"));
		request.putStringParam("fundTypeCode"	, fundTypeCode);
		request.putStringParam("fundName"		, "%"+request.getStringParam("fundName")+"%");
		request.putStringParam("closeYn"		, request.getStringParam("closeYn"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundPlanEmp", request.getParam());
		
		System.out.println("size ==> " + list.size());
		
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		// fund info
		List<GridDataModel> fundCodeList = new ArrayList<GridDataModel>();
		List<GridDataModel> fundInfoList = new ArrayList<GridDataModel>(); 
		
		for(GridDataModel dataModel : request.getList()) {
			
			Fnd01_FundCodeModel fundCodeModel = (Fnd01_FundCodeModel)dataModel;

			//필수입력체크
			String fundCode = fundCodeModel.getFundCode();
			if (("".equals(fundCode))||(fundCode == null)) {
				result.setStatus(0);
				result.setMessage("종목코드는 필수입력항목입니다.");
				return;
			}

			String fundName = fundCodeModel.getFundInfoModel().getFundName();
			if (("".equals(fundName))||(fundName == null)) {
				result.setStatus(0);
				result.setMessage("종목명은 필수입력항목입니다.");
				return;
			}
			
			String fundTypeCode = fundCodeModel.getFundInfoModel().getFundTypeCode();
			if (("".equals(fundTypeCode))||(fundTypeCode == null)) {
				result.setStatus(0);
				result.setMessage("펀드유형은 필수입력항목입니다.");
				return;
			}

			//펀드코드 중복조회
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fundCodeId", fundCodeModel.getFundCodeId());
			param.put("fundCode"  , fundCodeModel.getFundCode());
			Long cnt = sqlSession.selectOne(mapperName + ".selectOneFundCodeCheck", param);
			System.out.println("펀드코드 중복체크 : " + cnt);
			if (cnt > 0) {
				result.setStatus(0);
				result.setMessage("동일 펀드코드가 존재합니다.");
				return;
			}
			fundCodeList.add((GridDataModel)fundCodeModel);

			Fnd02_FundInfoModel fundInfoModel = fundCodeModel.getFundInfoModel(); 
			if(fundInfoModel.getFundInfoId() == null) {
				Long seq = sqlSession.selectOne("dbConfig.getSeq");
				fundInfoModel.setFundInfoId(seq);
				fundInfoModel.setFundCodeId(fundCodeModel.getFundCodeId());
			}
			fundInfoList.add((GridDataModel)fundInfoModel); 
		}

		//Fnd02_Fund_Into Update
		UpdateDataModel<GridDataModel> updateInfoModel = new UpdateDataModel<GridDataModel>();
		updateInfoModel.updateModel(sqlSession, fundInfoList, "fnd02_fund_info", result);
		System.out.println("Fnd02_Fund_Into Update&Insert 완료");
		
		//Fnd01_Fund_Code Update
		if (result.getStatus() > -1) {
			UpdateDataModel<GridDataModel> updateCodeModel = new UpdateDataModel<GridDataModel>(); 
			updateCodeModel.updateModel(sqlSession, fundCodeList, mapperName, result);
			System.out.println("Fnd01_Fund_Code Update&Insert 완료");
		}
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		List<GridDataModel> fundInfoList = new ArrayList<GridDataModel>();

		for(GridDataModel dataModel : request.getList()) {

			Fnd01_FundCodeModel fundCodeModel = (Fnd01_FundCodeModel)dataModel;
			Fnd02_FundInfoModel fundInfoModel = fundCodeModel.getFundInfoModel(); 

			fundInfoList.add((GridDataModel)fundInfoModel);
		}

		UpdateDataModel<Fnd02_FundInfoModel> updateFundInfoModel = new UpdateDataModel<Fnd02_FundInfoModel>(); 
		updateFundInfoModel.deleteModel(sqlSession, fundInfoList, "fnd02_fund_info", result);

		if(result.getStatus() > -1) {
			UpdateDataModel<Fnd01_FundCodeModel> updateModel = new UpdateDataModel<Fnd01_FundCodeModel>();
			updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
		}
	}

	public void selectByLookupFundCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("companyId", request.getLongParam("companyId"));
		request.putStringParam("closeYn", request.getStringParam("closeYn"));
		request.putStringParam("fundName", request.getStringParam("fundName"));
		request.putStringParam("fundTypeCode", request.getStringParam("fundTypeCode"));
		
		System.out.println("companyId param: " + request.getLongParam("companyId") ); 
		System.out.println("closeYn param: " + request.getStringParam("closeYn") ); 
		System.out.println("fundName param: " + request.getStringParam("fundName") ); 
		System.out.println("fundTypeCode param: " + request.getStringParam("fundTypeCode") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByLookupFundCode", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void getLastUploadDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String lastWorkDate = sqlSession.selectOne(mapperName + ".selectLastUploadDate", request.getParam("companyId"));
		
		System.out.println("lastWorkDate : " + lastWorkDate);
		
		result.setMessage(lastWorkDate);
	}
	
	
	
//	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//		UpdateDataModel<Fnd01_FundCodeModel> updateModel = new UpdateDataModel<Fnd01_FundCodeModel>(); 
//		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
//	}
//
//	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//
//		for(GridDataModel dataModel : request.getList()) {
//			Fnd02_FundInfoModel fundModel = (Fnd02_FundInfoModel)dataModel;
//			try {
//				sqlSession.delete("fnd01_FundCode.deleteByFundCodeId", fundModel.getFundCodeId());
//			}
//			catch(RuntimeSqlException e){
//				
//			}
//		}
//		
//		UpdateDataModel<Fnd01_FundCodeModel> updateModel = new UpdateDataModel<Fnd01_FundCodeModel>();
//		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
//
////		List<GridDataModel> deleteList = new ArrayList<GridDataModel>(); 
////		
////		for(GridDataModel dataModel : request.getList()) {
////			Fnd02_FundInfoModel fundModel = (Fnd02_FundInfoModel)dataModel;
////			List<GridDataModel> selectPlanList = sqlSession.selectList("pln02_plan.selectByFundId", fundModel.getFundId());  
////			
////			for(GridDataModel selectModel: selectPlanList) {
////				deleteList.add(selectModel); 
////			}
////		}
////		
////		UpdateDataModel<Pln02_PlanModel> deleteModel = new UpdateDataModel<Pln02_PlanModel>();
////		deleteModel.deleteModel(sqlSession, deleteList, "pln02_plan", result);
////		
////		if(result.getStatus() > -1) {
////			updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
////		}
//	}
}
