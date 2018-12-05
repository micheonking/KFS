package myApp.server.cmp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.cmp.model.Cmp01_AdvertiseModel;
import myApp.server.utils.db.UpdateDataModel;

public class Cmp01_Advertise {

	private	String mapperName = "cmp01_advertise"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = this.mapperName + ".selectById"; 

		System.out.println("cmp01_advertise advertiseId param: " + request.getLongParam("advertiseId") ); 

		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getLongParam("advertiseId")) ;
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectByScreeningDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		request.putStringParam("startDate",	request.getStringParam("startDate") );
		request.putStringParam("endDate",	request.getStringParam("endDate") );

		if(request.getStringParam("advertiseNote").length() > 0) {
			request.putStringParam("advertiseNote",	"%"+request.getStringParam("advertiseNote")+"%" );
		} else {
			request.putStringParam("advertiseNote",	"%");
		}

		System.out.println("cmp01_advertise startDate param: " + request.getStringParam("startDate") ); 
		System.out.println("cmp01_advertise endDate   param: " + request.getStringParam("endDate") ); 
		System.out.println("cmp01_advertise advertiseNote param: " + request.getStringParam("advertiseNote") );

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByScreeningDate", request.getParam());
		System.out.println("list: " + list.size() );
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
//		for(GridDataModel data : request.getList()) {
//			Cmp01_AdvertiseModel planModel = (Cmp01_AdvertiseModel)data; 
//			
//			if(planModel.getFundId() == null) {
//				result.setRetrieveResult(-1, "펀드선택이 되지 않았습니다.", null);
//				return; 
//			}
//
//		}
//		
		UpdateDataModel<Cmp01_AdvertiseModel> updateModel = new UpdateDataModel<Cmp01_AdvertiseModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("cmp01_advertise advertiseId param: " + request.getLongParam("advertiseId") ); 

		UpdateDataModel<Cmp01_AdvertiseModel> updateModel = new UpdateDataModel<Cmp01_AdvertiseModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
