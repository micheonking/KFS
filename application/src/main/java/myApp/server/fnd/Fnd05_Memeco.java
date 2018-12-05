package myApp.server.fnd;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class Fnd05_Memeco {

	private String mapperName = "fnd05_memeco";

	public void selectByCodeName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeName", request.getParam()) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByCodeName", list);
	}

}
