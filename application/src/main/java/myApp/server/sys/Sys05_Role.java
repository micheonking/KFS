package myApp.server.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys05_RoleModel;
import myApp.client.vi.sys.model.Sys07_UsrRoleModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys05_Role { 
	
	String mapperName = "sys04_role"; 
	
	public void selectByName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByName", request.getParam()) ; 
		result.setRetrieveResult(list.size(), "sys04_role.selectByName", list);
	}

	public void selectByUserId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list  = sqlSession.selectList(mapperName + ".selectByUserId", request.getParam()) ;
		result.setRetrieveResult(1, "사용자별 메뉴권한 조회", list);
	}
	
	public void selectByNotAssigned(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long userId = request.getLongParam("userId"); 
		List<GridDataModel> userRoleList = sqlSession.selectList(mapperName + ".selectByNotAssigned", userId) ;
		result.setRetrieveResult(userRoleList.size(), "미등록 권한정보 조회", userRoleList);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys05_RoleModel> updateModel = new UpdateDataModel<Sys05_RoleModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void updateUserRole(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		// delete all by userId
		Long userId = request.getLongParam("userId"); 
		
		// insert userRoleYn = true 
		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
		List<GridDataModel> deleteList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			
			Sys05_RoleModel roleModel = (Sys05_RoleModel)dataModel; 
			
//			if(roleModel.getUseYn()) { 
				
				Sys07_UsrRoleModel insertModel = new Sys07_UsrRoleModel();
				
				Long seq = sqlSession.selectOne("getSeq"); // getSeq cache setting 확인. 
				insertModel.setUsrRoleId(seq);
//				insertModel.setUserId(userId);
				insertModel.setRoleId(roleModel.getRoleId());
				insertModel.setRmk(roleModel.getRoleName());
				updateList.add(insertModel); // sqlSession.insert("sys05_user_role.insert", insertModel); 

//			}
//			else {
//				Sys05_UserRoleModel deleteModel = roleModel.getUserRoleModel(); 
//				deleteList.add(deleteModel) ;
//			}
		}
		
		UpdateDataModel<Sys07_UsrRoleModel> updateModel = new UpdateDataModel<Sys07_UsrRoleModel>(); 
		if(updateList.size() >0) {
			updateModel.updateModel(sqlSession, updateList, "sys05_user_role", result); // update
		}
		
		if(deleteList.size()>0) {
			updateModel.deleteModel(sqlSession, deleteList, "sys05_user_role", result); // delete
		}
		
		Map<String, Long> param = new HashMap<String, Long>(); 
		param.put("userId", userId); 
		param.put("companyId", request.getLongParam("companyId"));
		
		List<GridDataModel> list  = sqlSession.selectList(mapperName + ".selectByUserId", param) ;
		result.setRetrieveResult(1, "사용자별 메뉴권한 조회", list);
	}
	
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys05_RoleModel> updateModel = new UpdateDataModel<Sys05_RoleModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}