package myApp.server.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class Sys00_Common {

	public void selectCmpCombo(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = "sys00_common.selectCmpCombo"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId) ;
		result.setRetrieveResult(1, sqlId, list);
	}
}
