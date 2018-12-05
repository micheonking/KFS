package myApp.server.apr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class Apr00_StampSeq {

	private String mapperName = "apr00_stamp_seq";

	public void selectByApprDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("startDate", request.getDateParam("startDate"));
		param.put("endDate", request.getDateParam("endDate"));
		
		String sqlId = mapperName + ".selectByApprDate"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		
		result.setRetrieveResult(1, sqlId, list);
	}

}
