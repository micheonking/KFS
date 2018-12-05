package myApp.server.pln;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr03_RelateFundModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.pln.model.Pln02_PlanModel;
import myApp.server.apr.Apr04_ApprStep;
import myApp.server.utils.db.UpdateDataModel;

public class Pln02_Plan { 
	
	private	String mapperName = "pln02_plan"; 

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = this.mapperName + ".selectById"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getLongParam("planId")) ;
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByPlanFundId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

//		request.putStringParam("startDate",	request.getStringParam("startDate") );
//		request.putStringParam("endDate",	request.getStringParam("endDate") );
		request.putStringParam("closeYn",	request.getStringParam("closeYn") );
		request.putLongParam("fundId",		request.getLongParam("fundId") );

//		System.out.println("pln02_plan startDate param: " + request.getStringParam("startDate") ); 
//		System.out.println("pln02_plan endDate   param: " + request.getStringParam("endDate") ); 
		System.out.println("pln02_plan closeYn  param: " + request.getStringParam("closeYn") ); 
		System.out.println("pln02_plan fundId   param: " + request.getLongParam("fundId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByPlanFundId", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByFundId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("pln02_plan fundId param : " + request.getLongParam("fundId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundId", request.getLongParam("fundId"));
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByFundIdEmp(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("fundId : " + request.getLongParam("fundId") ); 
		System.out.println("orgId  : " + request.getLongParam("orgId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundIdEmp", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByEmpPlanFundId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

//		request.putStringParam("startDate",	request.getStringParam("startDate") );
//		request.putStringParam("endDate",	request.getStringParam("endDate") );
		request.putStringParam("closeYn",	request.getStringParam("closeYn") );
		request.putLongParam("empId",		request.getLongParam("empId") );
		request.putLongParam("fundId",		request.getLongParam("fundId") );

//		System.out.println("pln02_plan startDate param: " + request.getStringParam("startDate") ); 
//		System.out.println("pln02_plan endDate   param: " + request.getStringParam("endDate") ); 
		System.out.println("pln02_plan closeYn  param: " + request.getStringParam("closeYn") ); 
		System.out.println("pln02_plan empId   param: " + request.getLongParam("empId") ); 
		System.out.println("pln02_plan fundId   param: " + request.getLongParam("fundId") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByEmpPlanFundId", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByPlanFundName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putStringParam("startDate",		request.getStringParam("startDate") );
		request.putStringParam("endDate",		request.getStringParam("endDate") );
//		request.putStringParam("closeYn",		request.getStringParam("closeYn") );
//		request.putStringParam("fundName",	"%"+request.getStringParam("fundName")+"%" );

//		System.out.println("pln02_plan startDate param: " + request.getStringParam("startDate") ); 
//		System.out.println("pln02_plan endDate   param: " + request.getStringParam("endDate") ); 
//		System.out.println("pln02_plan closeYn  param: " + request.getStringParam("closeYn") ); 
//		System.out.println("pln02_plan fundName   param: " + request.getStringParam("fundName") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByPlanFundName", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByPlanPersonFund(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		request.putStringParam("startDate",		request.getStringParam("startDate") );
		request.putStringParam("endDate",		request.getStringParam("endDate") );
//		request.putStringParam("closeYn",		request.getStringParam("closeYn") );
//		request.putStringParam("fundName",	"%"+request.getStringParam("fundName")+"%" );
		
		System.out.println("pln02_plan startDate param: " + request.getStringParam("startDate") ); 
		System.out.println("pln02_plan endDate   param: " + request.getStringParam("endDate") ); 
//		System.out.println("pln02_plan fundName  param: " + request.getStringParam("closeYn") ); 
//		System.out.println("pln02_plan closeYn   param: " + request.getStringParam("fundName") ); 
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByPlanPersonFund", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByLookupFund(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

//		Long companyId = request.getLongParam("companyId");
//		String fundName = request.getStringParam("fundTypeCode"); 

//		request.putLongParam("companyId",companyId);
		System.out.println("companyId param: " + request.getLongParam("companyId") ); 
		System.out.println("fundName param: " + request.getStringParam("fundName") ); 
		System.out.println("fundTypeCode param: " + request.getStringParam("fundTypeCode") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByLookupFund", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void insertAppr(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long planId  	= request.getLongParam("planId");
		Long empId		= request.getLongParam("empId");
		Long orgId		= request.getLongParam("orgId");
		Long companyId	= request.getLongParam("companyId");
		
		System.out.println("=====(insertAppr)==================================================");
		System.out.println("planId    : " + planId);
		System.out.println("empId 	  : " + empId);
		System.out.println("orgId 	  : " + orgId);
		System.out.println("companyId : " + companyId);
		System.out.println("");

		//pln02_plan data Get
		Pln02_PlanModel planModel = sqlSession.selectOne("pln02_plan.selectById", planId);

		if (planModel.getApprId() == null) {

			// (1) 상신문서 생성 (apr01_appr insert)
			Apr01_ApprModel aprModel = new Apr01_ApprModel();
			aprModel.setApprId(sqlSession.selectOne("dbConfig.getSeq"));
//			aprModel.setTitle(planModel.getClassTreeModel().getClassTreeName());
			aprModel.setTitle(" ");
			aprModel.setStatusCode("99");	//99:임시저장
			aprModel.setRegEmpId(planModel.getEmpId());
			aprModel.setClassTreeId(planModel.getClassTreeId());
			aprModel.setInOutTypeCode("20");	//20:사내문서
			sqlSession.update("apr01_appr.insert", aprModel);

			System.out.println("aprModel.getApprId()     : " + aprModel.getApprId());
			System.out.println("1. apr01_appr insert~!! ok");

			// (2) 연관펀드 생성 (apr03_relate_fund insert)
			Apr03_RelateFundModel fundModel = new Apr03_RelateFundModel();
			fundModel.setRelateFundId(sqlSession.selectOne("dbConfig.getSeq"));
			fundModel.setApprId(aprModel.getApprId());
			fundModel.setFundId(planModel.getFundId());
			sqlSession.update("apr03_relate_fund.insert", fundModel);
			System.out.println("2. apr03_relate_fund insert~!! ok");

			// (3) 결재선 생성 (Apr04_ApprStep insert)
			Dcr01_ClassTreeModel classTreeModel = sqlSession.selectOne("dcr01_class_tree.selectById", planModel.getClassTreeId());
			Map<String, Object> apprStepParam = new HashMap<String, Object>();
			apprStepParam.put("companyId" 		, companyId);
			apprStepParam.put("empId"	  		, empId);
			apprStepParam.put("orgId"	  		, orgId);
			apprStepParam.put("apprId"	  		, aprModel.getApprId());
			apprStepParam.put("approvalTypeCode", classTreeModel.getApprovalTypeCode());
			Apr04_ApprStep apprStep = new Apr04_ApprStep();
			apprStep.createApprovalTypeCd(sqlSession, apprStepParam, result);

			List<GridDataModel> apprStepList = (List<GridDataModel>) result.getResult();
			UpdateDataModel<Apr04_ApprStepModel> updateModel = new UpdateDataModel<Apr04_ApprStepModel>(); 
			updateModel.updateModel(sqlSession, apprStepList, "apr04_appr_step", result);
			System.out.println("3. Apr04_ApprStep insert~!! ok");

			// (4) pln02_plan update
			planModel.setApprId(aprModel.getApprId());
			sqlSession.update("pln02_plan.updateApprId", planModel);
			System.out.println("4. pln02_plan update~!! ok");
		}

		Apr01_ApprModel apprModel = sqlSession.selectOne("apr01_appr.selectById", planModel.getApprId());
		result.setModel(1, "aprModel", apprModel);
	}

	public void deleteAppr(SqlSession sqlSession, Long apprId) {
		
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
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		for(GridDataModel data : request.getList()) {
			Pln02_PlanModel planModel = (Pln02_PlanModel)data; 
			if(planModel.getFundId() == null) {
				result.setRetrieveResult(-1, "펀드선택이 되지 않았습니다.", null);
				return; 
			}
		}
		UpdateDataModel<Pln02_PlanModel> updateModel = new UpdateDataModel<Pln02_PlanModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		for (GridDataModel data : request.getList()) {
			Pln02_PlanModel deleteModel = (Pln02_PlanModel) data;
			if (deleteModel.getApprId() != null) {
				deleteAppr(sqlSession, deleteModel.getApprId());
			}
		}
		UpdateDataModel<Pln02_PlanModel> updateModel = new UpdateDataModel<Pln02_PlanModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
}
