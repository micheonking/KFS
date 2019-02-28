package myApp.server.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys50_TermsDictModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys50_TermsDict {

	public void selectBySearchText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String searchText;
		if(request.getStringParam("searchText") == null ) {
			searchText = "%";
		} else {
			searchText = "%" + request.getStringParam("searchText") + "%";
		}
		
		List<GridDataModel> list = sqlSession.selectList("sys50_terms_dict.selectBySearchText",  searchText);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String usrNo = request.getStringParam("usrNo");
		UpdateDataModel<Sys50_TermsDictModel> updateModel = new UpdateDataModel<Sys50_TermsDictModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), "sys50_terms_dict", usrNo, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys50_TermsDictModel> updateModel = new UpdateDataModel<Sys50_TermsDictModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), "sys50_terms_dict", result);
	}

}
