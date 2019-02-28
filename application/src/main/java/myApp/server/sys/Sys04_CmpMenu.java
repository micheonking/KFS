package myApp.server.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys04_CmpMenuModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys04_CmpMenu { 
	
	public void selectByCmpCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		List<GridDataModel> menuList = sqlSession.selectList("sys04_cmp_menu.selectByParentId", request.getParam());

		for(GridDataModel child : menuList){
			Sys04_CmpMenuModel menuModel = (Sys04_CmpMenuModel)child;
			List<GridDataModel> childList = getChildByParentId(sqlSession, menuModel.getCmpMenuId(), menuModel.getCmpCode());  
			menuModel.setChildList(childList); 	
		}
		result.setRetrieveResult(menuList.size(), "sys04_cmp_menu.selectByParentId", menuList);
	}

	private List<GridDataModel> getChildByParentId(SqlSession sqlSession, Long parentId, String cmpCode){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmpCode", cmpCode);
		param.put("prntId" , parentId);
		List<GridDataModel> menuList = sqlSession.selectList("sys04_cmp_menu.selectByParentId", param);

		for(GridDataModel child : menuList){
			Sys04_CmpMenuModel menuModel = (Sys04_CmpMenuModel)child;
			List<GridDataModel> childList = getChildByParentId(sqlSession, menuModel.getCmpMenuId(), menuModel.getCmpCode());  
			menuModel.setChildList(childList); 	
		}
		return menuList ; 
	}

	public void insertInitMenu(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String cmpCode = request.getStringParam("cmpCode");
		Long cnt = sqlSession.selectOne("sys04_cmp_menu.countCmpMenu", cmpCode);
		System.out.println(cmpCode + " cnt : " + cnt);
		if(cnt>0) {
			result.setStatus(-1);
			result.setMessage("이미 메뉴가 존재합니다.");
			return;
		}
		sqlSession.insert("sys04_cmp_menu.insertInitMenu", cmpCode);
		result.setStatus(1);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		for (GridDataModel model : request.getList()) {
			Sys04_CmpMenuModel menuModel = (Sys04_CmpMenuModel)model;
			System.out.println("cmpMenuId : " + menuModel.getCmpMenuId());
			System.out.println("cmpCd : " + menuModel.getCmpCode());
			System.out.println("menuId : " + menuModel.getMenuId());
			System.out.println("prntId : " + menuModel.getPrntId());
		}

		String usrNo = request.getStringParam("usrNo");
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), "sys04_cmp_menu", usrNo, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), "sys04_cmp_menu", result);
	}
}
