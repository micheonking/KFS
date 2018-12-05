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
import myApp.client.vi.dcr.model.Dcr03_OrgAuthModel;
import myApp.server.utils.db.UpdateDataModel;

public class Dcr03_OrgAuth {
	
	private String mapperName = "dcr03_org_auth";

	public void selectByOrgCodeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId");
		Long orgCodeId = request.getLongParam("orgCodeId");
		Long parentId = Long.parseLong("0");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("orgCodeId", orgCodeId);
		param.put("parentId", parentId);
		
		String sqlId = "dcr01_class_tree.selectByOrgCodeId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		
		for(GridDataModel data : list) {
			Dcr01_ClassTreeModel dataModel = (Dcr01_ClassTreeModel)data; 
			List<GridDataModel> childList 
				= this.getChildList(sqlSession, companyId, orgCodeId, dataModel.getClassTreeId()); 
			dataModel.setChildList(childList);
		}
		
		result.setRetrieveResult(1, sqlId, list);
	}

	public List<GridDataModel> getChildList(SqlSession sqlSession, Long companyId, Long orgCodeId, Long parentId) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("orgCodeId", orgCodeId);
		param.put("parentId", parentId);
		
		String sqlId = "dcr01_class_tree.selectByOrgCodeId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		
		for(GridDataModel data : list) {
			
			Dcr01_ClassTreeModel dataModel = (Dcr01_ClassTreeModel)data; 
			List<GridDataModel> childList 
				= this.getChildList(sqlSession, companyId, orgCodeId, dataModel.getClassTreeId()); 
			dataModel.setChildList(childList);
		}
		return list; 
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long orgCodeId = request.getLongParam("orgCodeId");
		System.out.println("print system : " + orgCodeId );
		
		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
		
		for(GridDataModel data : request.getList()) {
			
			Dcr01_ClassTreeModel classTreeModel =(Dcr01_ClassTreeModel)data;
			Dcr03_OrgAuthModel orgAuthModel = classTreeModel.getOrgAuthModel(); 
			
			if(orgAuthModel.getRetrieveYn() == null) {
				orgAuthModel.setRetrieveYn("false"); 
			}
			if(orgAuthModel.getUpdateYn() == null) {
				orgAuthModel.setUpdateYn("false"); 
			}
			
			if(orgAuthModel.getOrgAuthId() == null) {
				Long seq = sqlSession.selectOne("dbConfig.getSeq"); 
				orgAuthModel.setOrgAuthId(seq);
				orgAuthModel.setOrgCodeId(orgCodeId);
				orgAuthModel.setClassTreeId(classTreeModel.getClassTreeId());
			}

			updateList.add(orgAuthModel);
			classTreeModel.setOrgAuthModel(orgAuthModel);
		}
		
		UpdateDataModel<Dcr03_OrgAuthModel> updateModel = new UpdateDataModel<Dcr03_OrgAuthModel>();
		updateModel.updateModel(sqlSession, updateList, mapperName, result);
		result.setResult(request.getList()); // update가 잘되면 받은데로 돌려준다. 
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.
		// UpdateDataModel<Dcr02_DocTypeModel> updateModel = new
		// UpdateDataModel<Dcr02_DocTypeModel>();
		// updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
