package myApp.server.opr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.opr.model.Opr02_FundModel;
import myApp.server.utils.db.UpdateDataModel;

public class Opr02_Fund {

	
	private String mapperName = "opr02_fund";

	public void selectByCreateId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long createId  = request.getLongParam("createId");
		Long orgId	   = request.getLongParam("orgId");
		Long companyId = request.getLongParam("companyId");

		System.out.println("createId : " + createId);
		System.out.println("orgId : " + orgId);
		System.out.println("companyId : " + companyId);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("createId" , createId );
		param.put("orgId" 	 , orgId );
		param.put("companyId", companyId);
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCreateId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void insert(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		List<GridDataModel> list = request.getList();
		List<GridDataModel> insertList = new ArrayList<GridDataModel>();
		
		Long createId = request.getLongParam("createId");
		
		for(GridDataModel data : list) {
			
			Fnd00_FundModel fndModel = (Fnd00_FundModel) data ;
			Opr02_FundModel	oprFundModel = new Opr02_FundModel();
			
			Long seq = sqlSession.selectOne("dbConfig.getSeq"); 
			
			oprFundModel.setFundId(seq);
			oprFundModel.setCreateId(createId);
			oprFundModel.setRegFundId(fndModel.getFundId());
			
			insertList.add((GridDataModel)oprFundModel);
		}
		
		UpdateDataModel<Opr02_FundModel> updater = new UpdateDataModel<Opr02_FundModel>();
		updater.updateModel(sqlSession, insertList, mapperName, result);
//		try {
//			updater.updateModel(sqlSession, insertList, mapperName, result);
//		} catch (RuntimeSqlException e) {
//			result.setMessage(e.getMessage());
//			result.setStatus(-1); 
//			return;
//		}
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Opr02_FundModel> updateModel = new UpdateDataModel<Opr02_FundModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Opr02_FundModel> updateModel = new UpdateDataModel<Opr02_FundModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}

