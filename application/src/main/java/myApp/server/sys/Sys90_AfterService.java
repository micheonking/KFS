package myApp.server.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.sys.model.Sys90_AfterServiceModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys90_AfterService {

	private String mapperName  = "sys90_after_service"; 

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long boardId = request.getLongParam("boardId"); 
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectById", boardId);
		result.setRetrieveResult(list.size(), "select ok", list);
	}

	public void selectByTitle(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String typeYn = request.getStringParam("typeYn");
		if (typeYn == null) {
			typeYn = "%";
		}

		Date 	happenStartDate 	= request.getDateParam("happenStartDate");
		Date 	happenEndDate    	= request.getDateParam("happenEndDate");
		String	worker	   	= "%"+ request.getStringParam("worker")+ "%";
		String	happenReason= "%"+request.getStringParam("happenReason")+ "%";
		String	closeYn		= request.getStringParam("closeYn");
		Long	companyId	= request.getLongParam("companyId");

		System.out.println("구분코드 : " + typeYn);
		System.out.println("발생일자 : " + happenStartDate);
		System.out.println("종료일자 : " + happenEndDate);
		System.out.println("담당자   : " + worker);
		System.out.println("발생이유 : " + happenReason);
		System.out.println("완료여부 : " + closeYn);
		System.out.println("회사     : " + companyId);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeYn"		, typeYn);
		param.put("happenStartDate"	, happenStartDate);
		param.put("happenEndDate"		, happenEndDate);
		param.put("worker"		, worker);
		param.put("happenReason", happenReason);
		param.put("closeYn"		, closeYn);
		param.put("companyId"	, companyId);

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTitle", param);
		System.out.println("size : " + list.size());
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys90_AfterServiceModel> updateModel = new UpdateDataModel<Sys90_AfterServiceModel>(); 
		System.out.println("===== 여기오니~~~???"+request.getStringParam("typeYn"));
		System.out.println("왔는데 왜 저장"+request.getStringParam("worker"));
		System.out.println(""+request.getStringParam("happenReason"));
		System.out.println(""+request.getBooleanParam("closeYn"));
		System.out.println(""+updateModel);
		
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys90_AfterServiceModel> updateModel = new UpdateDataModel<Sys90_AfterServiceModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
}
