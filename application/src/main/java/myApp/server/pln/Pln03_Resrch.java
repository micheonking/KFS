package myApp.server.pln;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.pln.model.Pln03_ResrchModel;
import myApp.server.utils.db.UpdateDataModel;

public class Pln03_Resrch { 
	
	private	String mapperName = "pln03_resrch"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long resrchId = request.getLongParam("resrchId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", resrchId);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectBySearchText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Date	startDate  = request.getDateParam("resrchStartDate");
		Date	closeDate  = request.getDateParam("resrchCloseDate");
		String	searchText = "%" + request.getStringParam("searchText") + "%" ;
		String	closeYn	   = request.getStringParam("closeYn");
		Long	regEmpId   = request.getLongParam("regEmpId");
		Long	orgId	   = request.getLongParam("orgId");

		System.out.println("startDate : " + startDate.toString());
		System.out.println("closeDate : " + closeDate.toString());
		System.out.println("searchText : " + searchText);
		System.out.println("closeYn : " + closeYn);
		System.out.println("regEmpId : " + regEmpId);
		System.out.println("orgId : " + orgId);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startDate"	, startDate);
		param.put("closeDate"	, closeDate);
		param.put("searchText"	, searchText);
		param.put("closeYn"		, closeYn);
		param.put("regEmpId"	, regEmpId);
		param.put("orgId"		, orgId);

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectBySearchText", param);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectDetailInit(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long empId = request.getLongParam("resrchId");
		Long orgId = request.getLongParam("orgId");
		
		System.out.println("empId : " + empId);
		System.out.println("orgId : " + orgId);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empId", empId);
		param.put("orgId", orgId);

		Pln03_ResrchModel resrchModel = sqlSession.selectOne(mapperName + ".selectDetailInit", param);
		
		System.out.println("resrchId : " + resrchModel.getResrchId());
		System.out.println("resrchStartDate : " + resrchModel.getResrchStartDate());
		
		result.setModel(1, "select ok", resrchModel);
	}

	public void selectOneApprEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long empId 	 = request.getLongParam("regEmpId");
		Date regDate = request.getDateParam("regDate");

		Pln03_ResrchModel data = sqlSession.selectOne(mapperName + ".selectOneApprEmpId", empId);
		Long cfrmEmpId = data.getCfrmEmpId();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empId"   , cfrmEmpId	);
		param.put("baseDate", regDate	);
		Emp00_InfoModel cfrmEmpInfo = sqlSession.selectOne("emp00_info.selectOneEmpInfo", param);

		Pln03_ResrchModel resrchModel = new Pln03_ResrchModel();
		resrchModel.setCfrmEmpId(cfrmEmpId);
		//resrchModel.setCfrmEmpNm(cfrmEmpInfo.getKorName() + " " + cfrmEmpInfo.getPositionName() + "(" + cfrmEmpInfo.getOrgName() + ")");
		resrchModel.setCfrmEmpNm(cfrmEmpInfo.getKorName() + " (" + cfrmEmpInfo.getOrgName() + ")");

		result.setModel(1, "select ok", resrchModel);
	}

	public void closeUpdate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resrchId" , request.getLongParam  ("resrchId"));
		param.put("closeYn"	 , request.getStringParam("closeYn"));
		param.put("closeDate", request.getDateParam  ("closeDate"));
		
		System.out.println("resrchId : " + request.getLongParam  ("resrchId"));
		System.out.println("closeYn : " + request.getStringParam  ("closeYn"));
		System.out.println("closeDate : " + request.getDateParam  ("closeDate"));
		
		sqlSession.update(mapperName + ".closeUpdate", param);
		result.setStatus(1);
	}

	public void apprUpdate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resrchId" , request.getLongParam("resrchId"));
		param.put("apprEmpId", request.getStringParam("apprEmpId"));
		param.put("apprDate" , request.getDateParam("apprDate"));
		param.put("cfrmEmpId", request.getStringParam("cfrmEmpId"));
		param.put("cfrmDate" , request.getDateParam("cfrmDate"));
		sqlSession.update(mapperName + ".apprUpdate", param);
		result.setStatus(1);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Pln03_ResrchModel resrchModel = (Pln03_ResrchModel)request.getModelParam("resrchModel");

//		Dcr04_DocModel docModel = new Dcr04_DocModel();
//		docModel.setDocId(resrchModel.getResrchId());
//		docModel.setDocTypeId(resrchModel.getClassTreeId());
//		docModel.setDocName(resrchModel.getClassTreeNm());
//		docModel.setRegEmpId(resrchModel.getRegEmpId());
//		docModel.setRegDate(resrchModel.getRegDate());
//		docModel.setApprId(resrchModel.getResrchId());
//
//		sqlSession.update("dcr04_doc.insert", docModel);
		sqlSession.update(mapperName + ".insert", resrchModel);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Pln03_ResrchModel> updateModel = new UpdateDataModel<Pln03_ResrchModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
