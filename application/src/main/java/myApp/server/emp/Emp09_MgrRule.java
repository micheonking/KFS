package myApp.server.emp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp09_MgrRuleModel;
import myApp.client.vi.sys.model.Sys09_CodeModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp09_MgrRule {

	private String mapperName = "emp09_mgr_rule";

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);

		System.out.println("bbb : " + param.get("companyId"));
		List<GridDataModel> list = sqlSession.selectList("sys09_code" + ".selectMgrRule", param);

		result.setRetrieveResult(1, "select ok", list);
	}

	public void updateMgrRule(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);

		// insert RoleYn = true
		List<GridDataModel> updateList = new ArrayList<GridDataModel>();
		List<GridDataModel> deleteList = new ArrayList<GridDataModel>();

		for (GridDataModel dataModel : request.getList()) {

			Sys09_CodeModel ruleModel = (Sys09_CodeModel) dataModel;

			System.out.println("과연 : "+ruleModel.getMgrRuleModel().getUseYn());
			
			Boolean useYn = ruleModel.getMgrRuleModel().getUseYn();
			if (useYn) {

				Emp09_MgrRuleModel insertModel = new Emp09_MgrRuleModel();
				Long seq = sqlSession.selectOne("getSeq");

				System.out.println("seq is " + seq);

				insertModel.setMgrRuleId(seq);
				insertModel.setTitleCd(ruleModel.getCode());
				insertModel.setUseYn(useYn);
				insertModel.setCompanyId(companyId);
				insertModel.setSeq(ruleModel.getSeq());
				updateList.add(insertModel); // sqlSession.insert("sys05_user_role.insert", insertModel);
			} else {
				Emp09_MgrRuleModel deleteModel = ruleModel.getMgrRuleModel();
				deleteList.add(deleteModel);

			}
		}

		UpdateDataModel<Emp09_MgrRuleModel> updateModel = new UpdateDataModel<Emp09_MgrRuleModel>();
		updateModel.deleteModel(sqlSession, deleteList, "emp09_mgr_rule", result); // delete
		updateModel.updateModel(sqlSession, updateList, "emp09_mgr_rule", result); // update

		List<GridDataModel> userRoleList = sqlSession.selectList("sys09_code" + ".selectMgrRule", param);
		result.setRetrieveResult(userRoleList.size(), "MgrRule Retrieve", userRoleList);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

}
