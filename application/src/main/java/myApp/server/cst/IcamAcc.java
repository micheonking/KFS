package myApp.server.cst;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class IcamAcc {
	
	String	mapperName = "cst03_icam_acc";
	
	public void selectByMgCombo (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByMgCombo");  
		result.setRetrieveResult(1, "select OK",  list);
	}

	
}
