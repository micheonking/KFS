package myApp.server.cst;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class CstInfo {
	
	String	mapperName = "cst02_acc";
	
	public void selectByFundCodeCombo (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putLongParam("userId", request.getLongParam("userId"));

		System.out.println("user_id : " + request.getLongParam("userId"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByFundCodeCombo", request.getParam());  
		result.setRetrieveResult(1, "select OK",  list);
	}

	public void selectByYearCombo (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putStringParam("fundCode", request.getStringParam("fundCode"));

		System.out.println("fundCode : " + request.getStringParam("fundCode"));

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByYearCombo", request.getParam());  
		result.setRetrieveResult(1, "select OK",  list);
	}

	public void getPreDate (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String preDate = sqlSession.selectOne(mapperName + ".getPreDate");
		System.out.println("preDate : [" + preDate + "]");
		
		result.setMessage(preDate);
		result.setStatus(1);
	}

}
