package myApp.server.fnd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.server.utils.db.UpdateDataModel;

public class Fnd03_Consignment {

	private String mapperName = "fnd03_consignment";

	public void selectByCodeName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String consignmentName = request.getStringParam("consignmentName");
		if (consignmentName == null) {
			consignmentName = "%";
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("consignmentName"	, consignmentName);
		param.put("consignmentName", "%" + request.getStringParam("consignmentName") + "%");
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeName",param) ; 
		result.setRetrieveResult(1, "select ok", list);
	
		System.out.println("수탁사검색란"+request.getStringParam("consignmentName") + "%");
		System.out.println("회사"+request.getStringParam("companyId"));
	}
	
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Fnd03_ConsignmentModel> updateModel = new UpdateDataModel<Fnd03_ConsignmentModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
	
	//수탁사 가져오기
	public void selectByConsignmentName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByConsignmentName", request.getParam()) ; 
		result.setRetrieveResult(list.size(), mapperName + ".selectByConsignmentName", list);
	}

}
