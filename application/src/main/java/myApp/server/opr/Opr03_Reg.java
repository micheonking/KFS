package myApp.server.opr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.opr.model.Opr03_RegModel;
import myApp.server.utils.db.UpdateDataModel;

public class Opr03_Reg {

	private String mapperName = "opr03_reg";

	public void selectByCreateId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long createId  = request.getLongParam("createId");
		Long companyId = request.getLongParam("companyId");
		Long empId	   = request.getLongParam("empId");

		System.out.println("createId : " + createId);
		System.out.println("companyId : " + companyId);
		System.out.println("empId : " + empId);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("createId" , createId );
		param.put("companyId", companyId);
		param.put("empId"	 , empId);
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCreateId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectOnePreRegCheck(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long createId = request.getLongParam("createId");
		Long empId 	  = request.getLongParam("empId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("createId" , createId );
		param.put("empId"    , empId	);
		
		System.out.println("createId : " + createId);
		System.out.println("empId    : " + empId);

		String checkYn = sqlSession.selectOne(mapperName + ".selectOnePreRegCheck",  param);

		System.out.println("checkYn    : " + checkYn);

		if (checkYn == null) {
			checkYn = "true";
		}

		if (checkYn.equals("false")) {
			result.setStatus(0);
		} else {
			result.setStatus(1);
		}
	}

	public void selectOnePreCancelCheck(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long 	createId = request.getLongParam  ("createId");
		String 	seq  	 = request.getStringParam("seq");
		
		System.out.println("createId : " + createId);
		System.out.println("seq : " + seq);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("createId" , createId);
		param.put("seq"      , seq);

		Long count = sqlSession.selectOne(mapperName + ".selectOnePreCancelCheck",  param);
		if (count == 0) {
			result.setStatus(1);
		} else {
			result.setStatus(0);
		}
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Opr03_RegModel> updateModel = new UpdateDataModel<Opr03_RegModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Opr03_RegModel> updateModel = new UpdateDataModel<Opr03_RegModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
