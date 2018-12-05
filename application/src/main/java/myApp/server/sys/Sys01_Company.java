package myApp.server.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.client.vi.sys.model.Sys03_CompanyMenuModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys01_Company {

	private String mapperName = "sys01_company"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByAll");
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String companyTypeCode = request.getStringParam("companyTypeCode");
		String companyName = request.getStringParam("companyName");
		
		if(companyName != null){
			companyName = "%" + companyName + "%";
		}
		else {
			companyName = "%"; 
		}
		
		if(companyTypeCode == null){
			companyTypeCode = "%"; 
		}
		
		Map<String, String> param = new HashMap<String, String>(); 
		param.put("companyTypeCode", companyTypeCode);
		param.put("companyName", companyName);
		
		List<GridDataModel> list = sqlSession.selectList("sys01_company.selectByName",  param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByMenuId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = this.mapperName + ".selectByMenuId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getLongParam("menuId")) ;
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void updatePw(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("companyId", request.getLongParam("companyId"));
		param.put("telNo02", request.getStringParam("newPassWord"));
		
		sqlSession.update("sys01_company.updatePw", param);
		
		result.setStatus(1); //1:정상
		
	}
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CompanyModel> updateModel = new UpdateDataModel<Sys01_CompanyModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys01_CompanyModel> updateModel = new UpdateDataModel<Sys01_CompanyModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
	public void updateByMenuYn(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		// delete all by userId
		Long menuId = request.getLongParam("menuId"); 
		
		// insert userRoleYn = true 
		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
		List<GridDataModel> deleteList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			
			Sys01_CompanyModel companyModel = (Sys01_CompanyModel)dataModel; 
			
			if(companyModel.getMenuYnChecked()) { 
				Sys03_CompanyMenuModel insertModel = new Sys03_CompanyMenuModel();
				Long seq = sqlSession.selectOne("getSeq"); // getSeq cache setting 확인. 
				insertModel.setCompanyMenuId(seq);
				insertModel.setCompanyId(companyModel.getCompanyId());
				insertModel.setMenuId(menuId);
				insertModel.setUseYn("true");
				updateList.add(insertModel); // sqlSession.insert("sys05_user_role.insert", insertModel); 
			}
			else {
				Sys03_CompanyMenuModel deleteModel = companyModel.getCompanyMenuModel();  
				deleteList.add(deleteModel) ;
			}
		}
		
		UpdateDataModel<Sys03_CompanyMenuModel> updateModel = new UpdateDataModel<Sys03_CompanyMenuModel>(); 
		
		if(updateList.size() >0) {
			updateModel.updateModel(sqlSession, updateList, "sys03_company_menu", result); // update
		}
		
		if(deleteList.size()>0) {
			updateModel.deleteModel(sqlSession, deleteList, "sys03_company_menu", result); // delete
		}
		
		this.selectByMenuId(sqlSession, request, result);
	}
	
	
}
