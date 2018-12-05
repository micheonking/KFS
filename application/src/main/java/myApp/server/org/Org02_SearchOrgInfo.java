package myApp.server.org;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class Org02_SearchOrgInfo {
private String mapperName = "org02_info";

    private	final Logger logger = Logger.getLogger(this.getClass().getName());

	public void selectByCondition(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String companyId = request.getStringParam("companyId");
		String searchCondition = request.getStringParam("searchCondition");//검색어
		Date baseDate = request.getDateParam("baseDate");//기준일자
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId);
		param.put("searchCondition", "%" + searchCondition + "%");
		param.put("baseDate", baseDate);
		System.out.println("기준일자 : " + param.get("baseDate").toString());
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCondition", param);
		result.setRetrieveResult(1, "select ok", list);
	}
}
