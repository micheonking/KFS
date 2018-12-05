package myApp.server.bbs;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.bbs.model.Bbs02_BoardModel;
import myApp.server.utils.db.UpdateDataModel;

public class Bbs02_Board {

	private String mapperName  = "bbs02_board"; 

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long boardId = request.getLongParam("boardId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", boardId);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectByTypeCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		request.putStringParam("typeCode", "%"+request.getStringParam("typeCode")+"%");
		request.putLongParam("setCount", request.getLongParam("setCount"));

		System.out.println("typeCode param: " + request.getStringParam("typeCode"));
		System.out.println("setCount param: " + request.getLongParam("setCount"));
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTypeCode", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

//	public void selectByTypeCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//		Map<String, Object> param = new HashMap<String, Object>();
//
//		System.out.println("typeCode : " + param.get("typeCode"));
//		
//		param.put("typeCode", request.getStringParam("typeCode"));
//		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTypeCode", param);
//		result.setRetrieveResult(1, "select ok", list);
//	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Bbs02_BoardModel> updateModel = new UpdateDataModel<Bbs02_BoardModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<Bbs02_BoardModel> updateModel = new UpdateDataModel<Bbs02_BoardModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}


}
