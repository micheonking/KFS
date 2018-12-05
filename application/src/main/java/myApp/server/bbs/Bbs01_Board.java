package myApp.server.bbs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.server.utils.db.UpdateDataModel;

public class Bbs01_Board {

	private String mapperName  = "bbs01_board"; 

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long boardId = request.getLongParam("boardId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", boardId);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//		System.out.println("조회 입니다.======================"+ request.getLongParam("companyId"));
//		Long companyId = request.getLongParam("companyId");
//		System.out.println(""+ request.getLongParam("companyId"));
//
//		String title = request.getStringParam("title");
//		if(title != null){
//			title = "%" + title + "%"; 
//		}
//		else {
//			title = "%"; 
//		}
//
//		Map<String, Object> param = new HashMap<String, Object>(); 
//		param.put("companyId", companyId);
//		
//		param.put("title", title); 
//		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", param);
		
		//1번경우
//		System.out.println("조회 입니다.======================"+ request.getLongParam("companyId"));
//		request.putLongParam("companyId", request.getLongParam("companyId"));
//		request.putLongParam("writeUserId", request.getLongParam("writeUserName"));
//		request.putLongParam("writeUserName", request.getLongParam("writeUserName"));
//		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId",request.getParam());
//		result.setRetrieveResult(1, "select ok", list);

	//2번경우
		
		String typeCode = request.getStringParam("typeCode");
		if (typeCode == null) {
			typeCode = "%" ;
		}
		
		String title = request.getStringParam("title");
		if (title == null) {
			title = "%" ;
		} else {
			title = "%" + title + "%" ;
		}

		System.out.println("typeCode : ["+typeCode+"]");
		System.out.println("title : ["+title+"]");
		System.out.println("companyId : ["+request.getLongParam("companyId")+"]");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeCode",  typeCode); 
		param.put("title", title);
		param.put("companyId", request.getLongParam("companyId"));
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", param);
		System.out.println("size : " + list.size());
		
		result.setRetrieveResult(1, "select ok", list);
	
	}

	public void selectByTypeCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId");
		String typeCode = request.getStringParam("typeCode");
		if(typeCode != null){
			typeCode = "%" + typeCode + "%"; 
		}
		else {
			typeCode = "%"; 
		}
		
		String title = request.getStringParam("title");
		if(title != null){
			title = "%" + title + "%"; 
		}
		else {
			title = "%"; 
		}
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId);
		param.put("typeCode", typeCode); 
		param.put("title", title); 
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTypeCode", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		System.out.println("board update============"); 
		System.out.println("board update"+request.getLongParam("boardId")); 
		
		UpdateDataModel<Bbs01_BoardModel> updateModel = new UpdateDataModel<Bbs01_BoardModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<Bbs01_BoardModel> updateModel = new UpdateDataModel<Bbs01_BoardModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}


}
