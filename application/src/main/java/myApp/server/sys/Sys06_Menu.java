package myApp.server.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys06_MenuModel;
import myApp.client.vi.sys.model.Sys07_RoleMenuModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys06_Menu { 
	
	String mapperName = "sys06_menu"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		List<GridDataModel> menuList 
			= sqlSession.selectList(mapperName + ".selectByParentId", Long.parseLong("0"));

		for(GridDataModel child : menuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			List<GridDataModel> childList = getChildByParentId(sqlSession, menuModel.getMenuId());  
			menuModel.setChildList(childList); 	
		}
		result.setRetrieveResult(menuList.size(), "sys06_menu.selectByParentId", menuList);
	}

	private List<GridDataModel> getChildByParentId(SqlSession sqlSession, Long parentId){
		
		List<GridDataModel> menuList = sqlSession.selectList(mapperName + ".selectByParentId", parentId);

		for(GridDataModel child : menuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			List<GridDataModel> childList = getChildByParentId(sqlSession, menuModel.getMenuId());  
			menuModel.setChildList(childList); 	
		}
		return menuList ; 
	}

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String sqlId = this.mapperName + ".selectByCompanyId";
		
		Long companyId = request.getLongParam("companyId"); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId); 
		param.put("parentId", Long.parseLong("0"));
		
		List<GridDataModel> menuList = sqlSession.selectList(sqlId, param);

		for(GridDataModel child : menuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			List<GridDataModel> childList = getChildByCompanyId(sqlSession, companyId, menuModel.getMenuId());  
			menuModel.setChildList(childList); 	
		}
		result.setRetrieveResult(menuList.size(), sqlId, menuList);
	}

	private List<GridDataModel> getChildByCompanyId(SqlSession sqlSession, Long companyId, Long parentId){
		
		String sqlId = this.mapperName + ".selectByCompanyId";

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId); 
		param.put("parentId", parentId);
		
		List<GridDataModel> menuList = sqlSession.selectList(sqlId, param);

		for(GridDataModel child : menuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			List<GridDataModel> childList = getChildByCompanyId(sqlSession, companyId, menuModel.getMenuId());  
			menuModel.setChildList(childList); 	
		}
		return menuList ; 
	}

	
	public void selectByRoleId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long roleId = request.getLongParam("roleId") ; 
		Long companyId = request.getLongParam("companyId") ;
		
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("roleId", roleId); 
		param.put("companyId", companyId);
		param.put("parentId", Long.parseLong("0"));
		
		List<GridDataModel> roleMenuList 
			= sqlSession.selectList(mapperName + ".selectByRoleId", param);

		for(GridDataModel child : roleMenuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			
			Map<String, Long> childParam = new HashMap<String, Long>();
			childParam.put("roleId", roleId); 
			childParam.put("companyId", companyId);
			childParam.put("parentId", menuModel.getMenuId());
			
			List<GridDataModel> childList = getChildByRoleId(sqlSession, childParam);  
			menuModel.setChildList(childList); 	
		}
		result.setRetrieveResult(roleMenuList.size(), "RoleMenu 조회", roleMenuList);
	}
	
	
	private List<GridDataModel> getChildByRoleId(SqlSession sqlSession, Map<String, Long> param){
		
		Long roleId = param.get("roleId"); 
		Long companyId = param.get("companyId");
		
		List<GridDataModel> roleMenuList = sqlSession.selectList(mapperName + ".selectByRoleId", param);
		
		for(GridDataModel child : roleMenuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			
			Map<String, Long> childParam = new HashMap<String, Long>();
			childParam.put("roleId", roleId); 
			childParam.put("companyId", companyId);
			childParam.put("parentId", menuModel.getMenuId());
			
			List<GridDataModel> childList = getChildByRoleId(sqlSession, childParam);  
			menuModel.setChildList(childList); 	
		}
		
		return roleMenuList ; 
	}


	public void selectByUserId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long userId = request.getLongParam("userId") ;
		Long companyId = request.getLongParam("companyId") ;
		
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("userId", userId); 
		param.put("companyId", companyId);
		param.put("parentId", Long.parseLong("0"));
		
		List<GridDataModel> rootMenuList = sqlSession.selectList(mapperName + ".selectByUserId", param);

		for(GridDataModel child : rootMenuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			
			Map<String, Long> childParam = new HashMap<String, Long>();
			childParam.put("userId", userId); 
			childParam.put("companyId", companyId);
			childParam.put("parentId", menuModel.getMenuId());
			
			List<GridDataModel> childList = getChildByUserId(sqlSession, childParam);  
			menuModel.setChildList(childList); 	
		}
		result.setRetrieveResult(rootMenuList.size(), "UserRoleMenu 조회", rootMenuList);
	}
	
	
	private List<GridDataModel> getChildByUserId(SqlSession sqlSession, Map<String, Long> param){
		
		List<GridDataModel> menuList = sqlSession.selectList(mapperName + ".selectByUserId", param);

		Long userId = param.get("userId");
		Long companyId = param.get("companyId"); 

		for(GridDataModel child : menuList){
			Sys06_MenuModel menuModel = (Sys06_MenuModel)child;
			
			Map<String, Long> childParam = new HashMap<String, Long>();
			childParam.put("userId", userId);
			childParam.put("companyId", companyId);
			childParam.put("parentId", menuModel.getMenuId());
			
			List<GridDataModel> childList = getChildByRoleId(sqlSession, childParam);  
			menuModel.setChildList(childList); 	
		}
		
		return menuList ; 
	}

	
	public void updateRoleMenu(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long roleId = request.getLongParam("roleId"); 
		
		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
		List<GridDataModel> deleteList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			
			Sys06_MenuModel menuModel = (Sys06_MenuModel)dataModel;
			
			if(menuModel.getRoleMenuYn()) {

				Sys07_RoleMenuModel roleMenuModel = new Sys07_RoleMenuModel();
			
				Long seq = sqlSession.selectOne("getSeq"); // getSeq cache setting 확인. 
				roleMenuModel.setRoleMenuId(seq);
				roleMenuModel.setMenuId(menuModel.getMenuId());
				roleMenuModel.setRoleId(roleId);
				roleMenuModel.setUseYn("true");
				roleMenuModel.setNote(menuModel.getMenuName());
				updateList.add(roleMenuModel);
			}
			else {
				deleteList.add(menuModel.getRoleMenuModel()); 	
			}
		}

		UpdateDataModel<GridDataModel> updateData = new UpdateDataModel<GridDataModel>(); 
		if(updateList.size() > 0) {
			updateData.updateModel(sqlSession, updateList, "sys07_role_menu", result);
		}
		if(deleteList.size() > 0) {
			updateData.deleteModel(sqlSession, deleteList, "sys07_role_menu", result);
		}

		// 처리가 완료되면(오류가 없으면...) 다시 넘겨준다.  
		// 다시 조회하면 트리가 깨진다. 
		result.setResult(request.getList());
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
