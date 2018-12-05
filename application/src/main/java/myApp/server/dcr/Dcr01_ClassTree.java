package myApp.server.dcr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.server.utils.db.UpdateDataModel;

public class Dcr01_ClassTree {

	private String mapperName = "dcr01_class_tree";

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long companyId = request.getLongParam("companyId"); 
		String typeCode = request.getStringParam("typeCode"); 
				
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId); 
		param.put("typeCode", typeCode); // C : 문서분류. T:문서구분
		param.put("parentId", Long.parseLong("0"));
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByParentId", param);
		
		for(GridDataModel child : dcrClassList){
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			List<GridDataModel> childList = getChildItem(sqlSession, companyId, dcrClassTreeModel.getClassTreeId(), typeCode);  
			dcrClassTreeModel.setChildList(childList); 	
		}
		result.setRetrieveResult(dcrClassList.size(), "문서분류 정보 조회", dcrClassList);
	}
	
	public void selectByParentIdResrchYn(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long companyId = request.getLongParam("companyId");
		Long orgId	   = request.getLongParam("orgId");
		String typeCode = request.getStringParam("typeCode"); 
				
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId); 
		param.put("parentId", Long.parseLong("0"));
		param.put("orgId", orgId);
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByParentIdResrchYn", param);
		List<GridDataModel> resultList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : list){
			Dcr01_ClassTreeModel classTreeModel = (Dcr01_ClassTreeModel)dataModel;
			this.getChildResearch(sqlSession, classTreeModel, orgId);
			
			if(classTreeModel.getChildList().size() > 0) {
				resultList.add(classTreeModel);
			}
		}
		
		result.setRetrieveResult(list.size(), "문서분류 정보 조회", resultList);
	}

	private void getChildResearch(SqlSession sqlSession, Dcr01_ClassTreeModel parentTreeModel, Long orgCodeId) {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", parentTreeModel.getCompanyId());
		param.put("parentId", parentTreeModel.getClassTreeId());
		param.put("orgId", orgCodeId);
		
		String sqlId = mapperName + ".selectByParentIdResrchYn" ; // 조직권한 적용한 리스트를 가져온다.
		List<GridDataModel> list  = sqlSession.selectList(sqlId, param);
		
		for(GridDataModel dataModel : list) {
			Dcr01_ClassTreeModel classTreeModel = (Dcr01_ClassTreeModel)dataModel;
			this.getChildResearch(sqlSession, classTreeModel, orgCodeId);

			if(classTreeModel.getChildList().size() > 0 ) {
				parentTreeModel.getChildList().add(classTreeModel) ; 
			}
			else if("true".equals(classTreeModel.getResearchYn())) {
				parentTreeModel.getChildList().add(classTreeModel) ;
			}
		}
	}
	
    public void selectByCompanyIdRole(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long companyId = request.getLongParam("companyId"); 
		String typeCode = request.getStringParam("typeCode"); 
		Long orgId = request.getLongParam("orgId");
				
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId); 
		param.put("typeCode", typeCode); // C : 문서분류. T:문서구분
		param.put("parentId", Long.parseLong("0"));
		param.put("orgId", orgId);
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByParentIdRole", param);
		
		for(GridDataModel child : dcrClassList){
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			List<GridDataModel> childList = getChildItemRole(sqlSession, companyId, dcrClassTreeModel.getClassTreeId(), typeCode, orgId);  
			dcrClassTreeModel.setChildList(childList); 	
		}
		
		result.setRetrieveResult(dcrClassList.size(), "문서분류 정보 조회", dcrClassList);
	}

	private List<GridDataModel> getChildItem(SqlSession sqlSession, Long companyId, Long parentId, String typeCode){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("parentId", parentId);
		param.put("typeCode", typeCode); // C : 문서분류
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByParentId", param);

		for(GridDataModel child : dcrClassList){
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			List<GridDataModel> childList = getChildItem(sqlSession, companyId, dcrClassTreeModel.getClassTreeId(), typeCode);  
			dcrClassTreeModel.setChildList(childList); 	
		}
		return dcrClassList ;
	}

	private List<GridDataModel> getChildItemResrchYn(SqlSession sqlSession, Long companyId, Long orgId, Long parentId, String typeCode){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("orgId"	 , orgId);
		param.put("parentId" , parentId);
		param.put("typeCode" , typeCode); // C : 문서분류
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByParentIdResrchYn", param);

		for(GridDataModel child : dcrClassList){
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			List<GridDataModel> childList = getChildItemResrchYn(sqlSession, companyId, orgId, dcrClassTreeModel.getClassTreeId(), typeCode);  
			dcrClassTreeModel.setChildList(childList); 	
		}
		return dcrClassList ;
	}

	private List<GridDataModel> getChildItemRole(SqlSession sqlSession, Long companyId, Long parentId, String typeCode, Long orgId){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("parentId", parentId);
		param.put("typeCode", typeCode); // C : 문서분류
		param.put("orgId", orgId);
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByParentIdRole", param);

		for(GridDataModel child : dcrClassList){
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			List<GridDataModel> childList = getChildItemRole(sqlSession, companyId, dcrClassTreeModel.getClassTreeId(), typeCode, orgId);  
			dcrClassTreeModel.setChildList(childList);
		}
		return dcrClassList ;
	}

	public void selectByFundId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long companyId = request.getLongParam("companyId");
		String typeCode = request.getStringParam("typeCode"); 
		Long fundId = request.getLongParam("fundId"); 
		Long orgId = request.getLongParam("orgId");
				
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId); 
		param.put("typeCode", typeCode); // C : 문서분류. T:문서구분
		param.put("parentId", Long.parseLong("0"));
		param.put("orgId", orgId);
		param.put("fundId", fundId);
		
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByFundIdRole", param);
		
		for(GridDataModel child : dcrClassList){
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			List<GridDataModel> childList = getChildFund(sqlSession, companyId, dcrClassTreeModel.getClassTreeId(), fundId, orgId);  
			dcrClassTreeModel.setChildList(childList); 	
		}
		
		result.setRetrieveResult(dcrClassList.size(), "문서분류 정보 조회", dcrClassList);
	}
	
	private List<GridDataModel> getChildFund(SqlSession sqlSession, Long companyId, Long parentId, Long fundId, Long orgId){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("parentId", parentId);
		param.put("fundId", fundId);
		param.put("orgId", orgId);
		/*
		System.out.println("companyId : " + companyId);
		System.out.println("parentId : " + parentId);
		System.out.println("fundId : " + fundId);
		System.out.println("orgId : " + orgId);
		*/
		List<GridDataModel> dcrClassList = sqlSession.selectList(mapperName + ".selectByFundIdRole", param);

		for(GridDataModel child : dcrClassList){
			
			Dcr01_ClassTreeModel dcrClassTreeModel = (Dcr01_ClassTreeModel)child;
			
			List<GridDataModel> childList = getChildFund(sqlSession, companyId, dcrClassTreeModel.getClassTreeId(), fundId, orgId);
			List<GridDataModel> childFundList = new ArrayList<GridDataModel>(); 
			
			for(GridDataModel findFundModel : childList) {
				
				int fundCount = sqlSession.selectOne("dcr04_doc" + ".selectByFundId", param);
				
				if(fundCount > 0) {
					childFundList.add(findFundModel); 
				}
			}
			
			dcrClassTreeModel.setChildList(childFundList);
		}
		
		List<GridDataModel> dcrFundClassList = new ArrayList<GridDataModel>();  
		
		for(GridDataModel childClassModel : dcrClassList) {
			Dcr01_ClassTreeModel childClassTreeeModel = (Dcr01_ClassTreeModel)childClassModel; 
			
			if(childClassTreeeModel.getChildList().size() > 0) {
				dcrFundClassList.add(childClassModel); 
			}
		}
		
		return dcrFundClassList ;
	}

	public void selectByClassTreeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 문서구분만 조회한다. 
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", request.getLongParam("companyId"));
		param.put("parentId", request.getLongParam("parentId"));
		param.put("typeCode", "T"); // T : 문서구분
		
		String sqlId = mapperName + ".selectByParentId" ;
		List<GridDataModel> list  = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(list.size(), sqlId, list);
	}
	
	public void selectByFundCodeId2(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("parent) orgCodeId    : " + request.getLongParam("orgCodeId"));
		System.out.println("parent) fundCodeId   : " + request.getLongParam("fundCodeId"));

		List<GridDataModel> resultList = new ArrayList<GridDataModel>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId"	, request.getLongParam("companyId"));
		param.put("orgCodeId"	, request.getLongParam("orgCodeId"));
		param.put("fundCodeId"	, request.getLongParam("fundCodeId"));
		param.put("parentTreeId", (long)0);

		String sqlId = mapperName + ".selectByFundCodeId2" ;
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		for(GridDataModel dataModel : list) {
			Dcr01_ClassTreeModel classTreeModel = (Dcr01_ClassTreeModel)dataModel;
			param.put("parentTreeId", classTreeModel.getClassTreeId());
			this.getChildFundCodeId2(sqlSession, classTreeModel, param);
			resultList.add(classTreeModel); 
		}
		result.setRetrieveResult(list.size(), sqlId, resultList);
	}

	public void getChildFundCodeId2(SqlSession sqlSession, Dcr01_ClassTreeModel parentTreeModel, Map<String, Object> param) {
		
		String sqlId = mapperName + ".selectByFundCodeId2" ;
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		for(GridDataModel dataModel : list) {
			Dcr01_ClassTreeModel classTreeModel = (Dcr01_ClassTreeModel)dataModel;

			System.out.println("[" + classTreeModel.getLevel()+ "] - " + classTreeModel.getClassTreeName());

			param.put("parentTreeId", classTreeModel.getClassTreeId());
			this.getChildFundCodeId2(sqlSession, classTreeModel, param);
			System.out.println("getChildList size : " + classTreeModel.getChildList().size());
			parentTreeModel.getChildList().add(classTreeModel);
		}
	}
	

	public void selectByFundCodeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		// 펀드코드로 문서분류를 조회한다. 
		// 하위 분류에 해당 펀드가 등록되어 있으면 같이 조회되어야 한다. 
		Long orgCodeId = request.getLongParam("orgCodeId");
		Long fundCodeId = request.getLongParam("fundCodeId");

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", request.getLongParam("companyId"));
		param.put("parentTreeId", Long.parseLong("0"));
		param.put("orgCodeId", orgCodeId);
		param.put("fundCodeId", fundCodeId);

		System.out.println("companyId : " + request.getLongParam("companyId"));
		System.out.println("orgCodeId : " + orgCodeId);
		System.out.println("fundCodeId : " + fundCodeId);

		String sqlId = mapperName + ".selectByFundCodeId" ; // 조직권한 적용한 리스트를 가져온다. 
		List<GridDataModel> list  = sqlSession.selectList(sqlId, param);
		List<GridDataModel> resultList = new ArrayList<GridDataModel>(); 
		
		for(GridDataModel dataModel : list) {
			Dcr01_ClassTreeModel classTreeModel = (Dcr01_ClassTreeModel)dataModel;
			this.getChildFundCodeId(sqlSession, classTreeModel, orgCodeId, fundCodeId);
			
			if(classTreeModel.getChildList().size() > 0 ) {
				resultList.add(classTreeModel); 
			}
		}

		result.setRetrieveResult(list.size(), sqlId, resultList);
	}
	
	public void getChildFundCodeId(SqlSession sqlSession, Dcr01_ClassTreeModel parentTreeModel, Long orgCodeId, Long fundCodeId) {

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", parentTreeModel.getCompanyId());
		param.put("parentTreeId", parentTreeModel.getClassTreeId());
		param.put("orgCodeId", orgCodeId);
		
		String sqlId = mapperName + ".selectByFundCodeId" ; // 조직권한 적용한 리스트를 가져온다.
		List<GridDataModel> list  = sqlSession.selectList(sqlId, param);
		
		for(GridDataModel dataModel : list) {
			Dcr01_ClassTreeModel classTreeModel = (Dcr01_ClassTreeModel)dataModel;

			this.getChildFundCodeId(sqlSession, classTreeModel, orgCodeId, fundCodeId);

			if(classTreeModel.getChildList().size() > 0 ) {
				parentTreeModel.getChildList().add(classTreeModel) ; 
			}
			else {
				Map<String, Long> fundParam = new HashMap<String, Long>(); 
				fundParam.put("fundCodeId", fundCodeId); 
				fundParam.put("classTreeId", classTreeModel.getClassTreeId());

				List<GridDataModel> fundList = sqlSession.selectList("apr03_relate_fund.selectByFundCodeId", fundParam); 
				if(fundList.size() > 0 ) {
					parentTreeModel.getChildList().add(classTreeModel) ; 
				}
				
				//기획펀드추가
				Map<String, Object> plnFundParam = new HashMap<String, Object>();
				plnFundParam.put("fundCodeId", fundCodeId);
				plnFundParam.put("companyId", parentTreeModel.getCompanyId());
				Long plnFundCodeId = sqlSession.selectOne("fnd01_fund_code.selectByRealFundId", plnFundParam);

				fundParam.put("fundCodeId", plnFundCodeId);
				List<GridDataModel> plnFundList = sqlSession.selectList("apr03_relate_fund.selectByFundCodeId", fundParam); 
				if(plnFundList.size() > 0 ) {
					parentTreeModel.getChildList().add(classTreeModel) ; 
				}
			}
		}
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
