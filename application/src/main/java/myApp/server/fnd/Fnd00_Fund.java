package myApp.server.fnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class Fnd00_Fund {

	private String mapperName = "fnd00_fund";

	public void selectByLookupFundInfo(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByLookupFundInfo", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectBySearchText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long 	 companyId  = request.getLongParam("companyId");
		Long	 orgId		= request.getLongParam("orgId");
		String	 searchText = '%' + request.getStringParam("searchText") + '%';
		String	 fundType   = request.getStringParam("fundType");
		String	 closeYn    = request.getStringParam("closeYn");
		if (closeYn == null) {
			closeYn = "false";
		}

		@SuppressWarnings("unchecked")
		List<Long> selectedFundList = (List<Long>)request.getParam("fundIdList");
		
//		String   fundId = request.getStringParam("fundIdList");
		
		/*
		String[] fundIdList = fundId.split(",");
		*/	
		
//		List<Long> fundIdList = new ArrayList<Long>();
//		if(fundId.length() > 1) {
//			String[] temp = fundId.split(",");
//			for(int i = 0; i < temp.length; i++) {
//				fundIdList.add(Long.parseLong(temp[i]));
//			}
//		}
		
		if (fundType == null) {
			fundType = "%";
		}

		System.out.println("companyId : " + companyId);
		System.out.println("orgId : " + orgId);
		System.out.println("searchText : " + searchText);
		System.out.println("fundType : " + fundType);
		System.out.println("closeYn : " + closeYn);
		System.out.println("fundIdList : " + selectedFundList.toString());
		
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("companyId"  , companyId );
		param.put("orgId"  	   , orgId     );
		param.put("searchText" , searchText);
		param.put("fundType"   , fundType  );
		param.put("closeYn"    , closeYn   );
		param.put("fundIdList" , selectedFundList);
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectBySearchText", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByDocIdText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long 	companyId	 = request.getLongParam("companyId");
		Long 	docId		 = request.getLongParam("docId");
		String	searchText 	 = '%' + request.getStringParam("searchText") + '%';
		String	fundType   	 = request.getStringParam("fundType");
		if (fundType == null) {
			fundType = "%";
		}

		System.out.println("companyId : " + companyId);
		System.out.println("docId : " + docId);
		System.out.println("searchText : " + searchText);
		System.out.println("fundType : " + fundType);
		
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("companyId"  , companyId );
		param.put("docId"      , docId  );
		param.put("searchText" , searchText);
		param.put("fundType"   , fundType  );
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByDocIdText", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByLookupFundCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("companyId", request.getLongParam("companyId"));
		request.putStringParam("closeYn", request.getStringParam("closeYn"));
		request.putStringParam("fundName", request.getStringParam("fundName"));
		request.putStringParam("fundName", request.getStringParam("fundName"));
		request.putStringParam("fundTypeCode", request.getStringParam("fundTypeCode"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByLookupFundCode", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByLookupFundPlan(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("companyId", request.getLongParam("companyId"));
		request.putStringParam("closeYn", request.getStringParam("closeYn"));
		request.putStringParam("fundName", request.getStringParam("fundName"));
		request.putStringParam("fundTypeCode", request.getStringParam("fundTypeCode"));
		
		System.out.println("Plan->companyId param: " + request.getLongParam("companyId") ); 
		System.out.println("Plan->closeYn param: " + request.getStringParam("closeYn") ); 
		System.out.println("Plan->fundName param: " + request.getStringParam("fundName") ); 
		System.out.println("Plan->fundTypeCode param: " + request.getStringParam("fundTypeCode") ); 

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByLookupFundPlan", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

}
