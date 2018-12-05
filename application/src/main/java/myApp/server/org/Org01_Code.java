package myApp.server.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr03_OrgAuthModel;
import myApp.client.vi.fnd.model.Fnd08_OrgAuthModel;
import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.vi.org.model.Org02_InfoModel;
import myApp.server.utils.db.UpdateDataModel;

public class Org01_Code {

	String mapperName = "org01_code"; 
	
//	Long companyId ;	// 재조회시 사용된다. 
//	Date baseDate ; 	// 재조회시 사용된다. 

	public void selectByKorName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String serviceName = mapperName + ".selectByKorName" ; 
		
		Long companyId = request.getLongParam("companyId");
		Date baseDate = request.getDateParam("baseDate");
		String korName = request.getStringParam("korName"); 
		
		if(korName == null) {
			korName = "%" ;
		}
		else {
			korName = "%" + korName + "%";  
		}
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId);
		param.put("baseDate", baseDate);
		param.put("korName", korName);
		
		List<GridDataModel> list = sqlSession.selectList(serviceName, param);
		result.setRetrieveResult(list.size(), serviceName, list);
	}
	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long companyId = request.getLongParam("companyId"); 
		Date baseDate = request.getDateParam("baseDate"); 
		
		System.out.println("company id is " + companyId); 
		System.out.println("base date is " + baseDate);
		
		
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("baseDate", baseDate);
		param.put("parentCodeId", Long.parseLong("0"));
		
		List<GridDataModel> orgList
			= sqlSession.selectList(mapperName + ".selectByParentId", param);

		for(GridDataModel child : orgList){
			Org01_CodeModel orgCodeModel = (Org01_CodeModel)child;
			List<GridDataModel> childList = getChildItem(sqlSession, companyId, baseDate, orgCodeModel.getCodeId());  
			orgCodeModel.setChildList(childList); 	
		}
		
		result.setRetrieveResult(orgList.size(), "조직정보 조회", orgList);
	}
	
	private List<GridDataModel> getChildItem(SqlSession sqlSession, Long companyId, Date baseDate, Long parentCodeId){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("baseDate", baseDate);
		param.put("parentCodeId", parentCodeId);
		
		List<GridDataModel> orgList = sqlSession.selectList(mapperName + ".selectByParentId", param);

		for(GridDataModel child : orgList){
			Org01_CodeModel orgCodeModel = (Org01_CodeModel)child;
			List<GridDataModel> childList = getChildItem(sqlSession, companyId, baseDate, orgCodeModel.getCodeId());  
			orgCodeModel.setChildList(childList); 	
		}

		return orgList ; 
	}
	
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Date baseDate = request.getDateParam("baseDate"); 
		
		List<GridDataModel> orgInfoList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {

			Org01_CodeModel orgCodeModel = (Org01_CodeModel)dataModel; 
			Org02_InfoModel orgInfoModel = orgCodeModel.getOrgInfoModel();  	

			// insert인지 update인지는 날자를 비교하여 확인한다. 
			Org02_InfoModel targetData = sqlSession.selectOne("org02_info.selectById", orgInfoModel.getInfoId());   

			if(targetData == null) {
				// 신규자료이다; OrtgInfoModel의 변경일을 오픈(개설)일로, 변경사유는 개설사유로 설정한다.  
				orgInfoModel.setModDate(orgCodeModel.getOpenDate());  
				orgInfoModel.setModReason(orgCodeModel.getOpenReason());
			}
			else { 
				if(!targetData.getModDate().equals(orgInfoModel.getModDate())){
					// 일자가 바뀌었다. Insert시키기 위해 새로운 키값을 설정한다.    
					Long seq = sqlSession.selectOne("dbConfig.getSeq"); 
					orgInfoModel.setInfoId(seq);
				}
			}
			orgInfoList.add(orgInfoModel); 
		}
		
		// Info부터 저장한다. 
		UpdateDataModel<Org02_InfoModel> updateInfoModel = new UpdateDataModel<Org02_InfoModel>(); 
		updateInfoModel.updateModel(sqlSession, orgInfoList, "org02_info", result);

		if(result.getStatus() < 0 ) {
			return ;  
		}

		// Code에 종료일이 들어올 수도 있으니 저장한다. 
		UpdateDataModel<Org01_CodeModel> updateCodeModel = new UpdateDataModel<Org01_CodeModel>();
		updateCodeModel.updateModel(sqlSession, request.getList(), mapperName, result);

		if(result.getStatus() < 0 ) {
			return ;  
		}
		
		List<GridDataModel> updateCodeList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			Map<String, Object> param = new HashMap<String, Object>();
			Org01_CodeModel updateModel = (Org01_CodeModel)dataModel; 
			param.put("codeId", updateModel.getCodeId()); 
			param.put("baseDate", baseDate); 
			
			GridDataModel resultData = sqlSession.selectOne(mapperName + ".selectByBaseDate", param);

			if(resultData != null) {
				updateCodeList.add(resultData);
			}
		}

		result.setRetrieveResult(updateCodeList.size(), "update org01_code", updateCodeList);
	}
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Date baseDate = request.getDateParam("baseDate"); 

		List<GridDataModel> orgCodeList = new ArrayList<GridDataModel>(); 
		List<GridDataModel> orgInfoList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {

			Org01_CodeModel codeModel = (Org01_CodeModel)dataModel; 
			long count = 0; 
			
			try {
				count = sqlSession.selectOne("org02_info.selectCountByCodeId", codeModel.getCodeId());	
			}
			catch (RuntimeSqlException e) {
				System.out.println("SQL 오류 발생. "); 
				return; 
			}

			if(count > 1) {
				// infoModel만 지운다. 
				orgInfoList.add(codeModel.getOrgInfoModel()); 
			}
			else {
				// 다 지운다. 
				orgCodeList.add(codeModel); 
			}
			
			UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
			updateModel.deleteModel(sqlSession, orgInfoList, "org02_info", result);
			updateModel.deleteModel(sqlSession, orgCodeList, "org01_code", result);
		}
		
		// update된 정보는 다시 보내준다.  
		List<GridDataModel> updateCodeList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			
			Map<String, Object> param = new HashMap<String, Object>();
			Org01_CodeModel updateModel = (Org01_CodeModel)dataModel; 
			param.put("codeId", updateModel.getCodeId()); 
			param.put("baseDate", baseDate); 
			Org01_CodeModel resultData = sqlSession.selectOne(mapperName + ".selectByBaseDate", param);

			if(resultData != null) {
				updateCodeList.add(resultData);
			}
		}
		result.setRetrieveResult(updateCodeList.size(), "delete org01_code", updateCodeList);
	}
	
	public void selectByFundCodeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long companyId = request.getLongParam("companyId");
		Long fundCodeId = request.getLongParam("fundCodeId");
		
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("parentCodeId", Long.parseLong("0")); // 상위조직코드
		param.put("fundCodeId", fundCodeId);
		
		List<GridDataModel> orgList
			= sqlSession.selectList(mapperName + ".selectByFundCodeId", param);

		for(GridDataModel child : orgList){
			Org01_CodeModel orgCodeModel = (Org01_CodeModel)child;
			List<GridDataModel> childList = getChildByFundCodeId(sqlSession, companyId, fundCodeId, orgCodeModel.getCodeId());  
			orgCodeModel.setChildList(childList); 	
		}
		
		result.setRetrieveResult(orgList.size(), "조직정보 조회", orgList);
	}
	
	private List<GridDataModel> getChildByFundCodeId(SqlSession sqlSession, Long companyId, Long fundCodeId, Long parentCodeId){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("parentCodeId", parentCodeId);
		param.put("fundCodeId", fundCodeId);
		
		List<GridDataModel> orgList = sqlSession.selectList(mapperName + ".selectByFundCodeId", param);

		for(GridDataModel child : orgList){
			Org01_CodeModel orgCodeModel = (Org01_CodeModel)child;
			List<GridDataModel> childList = getChildByFundCodeId(sqlSession, companyId, fundCodeId, orgCodeModel.getCodeId());  
			orgCodeModel.setChildList(childList); 	
		}

		return orgList ; 
	}
	
	public void updateFundAuth(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long fundCodeId = request.getLongParam("fundCodeId"); 
		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 

		for(GridDataModel dataModel : request.getList()) {
			Org01_CodeModel orgCodeModel  = (Org01_CodeModel)dataModel; 
			Fnd08_OrgAuthModel orgAuthModel = orgCodeModel.getOrgAuthModel(); 

			if(orgAuthModel.getOrgAuthId() == null) {
				Long seq = sqlSession.selectOne("dbConfig.getSeq"); 
				orgAuthModel.setOrgAuthId(seq);
				orgAuthModel.setOrgCodeId(orgCodeModel.getCodeId());
				orgAuthModel.setFundCodeId(fundCodeId);
			}
			
			if(orgAuthModel.getAuthYn() == null) {
				orgAuthModel.setAuthYn("false"); 
			}
			updateList.add(orgAuthModel);
		}
		
		UpdateDataModel<Fnd08_OrgAuthModel> updateDataModel = new UpdateDataModel<Fnd08_OrgAuthModel>();
		updateDataModel.updateModel(sqlSession, updateList, "fnd08_org_auth", result);
		result.setResult(request.getList()); // update가 잘되면 받은데로 돌려준다. 
		
	}
}
