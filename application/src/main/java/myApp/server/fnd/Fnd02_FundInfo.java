package myApp.server.fnd;

import java.util.List;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd02_FundInfoModel;
import myApp.server.utils.db.UpdateDataModel;

public class Fnd02_FundInfo {

	private String mapperName = "fnd02_fund_info";
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		request.putLongParam("fundInfoId", request.getLongParam("fundInfoId"));
		System.out.println("fnd02_fund_info param: " + request.getStringParam("fundInfoId") ); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Fnd02_FundInfoModel> updateModel = new UpdateDataModel<Fnd02_FundInfoModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		System.out.println("fud02_fund_info fundInfoId param: " + request.getLongParam("fundInfoId") ); 
		System.out.println("fud02_fund_info fundCodeId param: " + request.getLongParam("fundCodeId") ); 
		UpdateDataModel<Fnd02_FundInfoModel> updateModel = new UpdateDataModel<Fnd02_FundInfoModel>();
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
