package myApp.server.dcr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr10_MailHistoryModel;
import myApp.server.utils.db.UpdateDataModel;

public class Dcr10_MailHistory {
	private String mapperName = "dcr10_mail_history";

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String sqlId = this.mapperName + ".selectById"; 

		System.out.println("dcr10_mail_history mailHistoryId param: " + request.getLongParam("mailHistoryId") ); 

		List<GridDataModel> list = sqlSession.selectList(sqlId, request.getLongParam("mailHistoryId")) ;
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByTitleText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		request.putStringParam("startDate",	request.getStringParam("startDate") );
		request.putStringParam("endDate",	request.getStringParam("endDate") );

		if(request.getStringParam("titleText").length() > 0) {
			request.putStringParam("titleText",	"%"+request.getStringParam("titleText")+"%" );
		} else {
			request.putStringParam("titleText",	"%");
		}

		System.out.println("dcr10_mail_history startDate param: " + request.getStringParam("startDate") ); 
		System.out.println("dcr10_mail_history endDate   param: " + request.getStringParam("endDate") ); 
		System.out.println("dcr10_mail_history titleText param: " + request.getStringParam("titleText") );

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTitleText", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectBySenderEmail(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		if(request.getStringParam("titleText").length() > 0) {
			request.putStringParam("titleText",	"%"+request.getStringParam("titleText")+"%" );
		} else {
			request.putStringParam("titleText",	"%");
		}

		System.out.println("dcr10_mail_history empId     param: " + request.getStringParam("empId") ); 
		System.out.println("dcr10_mail_history startDate param: " + request.getStringParam("startDate") ); 
		System.out.println("dcr10_mail_history endDate   param: " + request.getStringParam("endDate") ); 
		System.out.println("dcr10_mail_history titleText param: " + request.getStringParam("titleText") );

		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectBySenderEmail", request.getParam());
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
//		for(GridDataModel data : request.getList()) {
//			Dcr10_MailHistoryModel planModel = (Dcr10_MailHistoryModel)data; 
//			
//			if(planModel.getFundId() == null) {
//				result.setRetrieveResult(-1, "펀드선택이 되지 않았습니다.", null);
//				return; 
//			}
//
//		}
//		
		UpdateDataModel<Dcr10_MailHistoryModel> updateModel = new UpdateDataModel<Dcr10_MailHistoryModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		System.out.println("dcr10_mail_history mailHistoryId param: " + request.getLongParam("mailHistoryId") ); 

		UpdateDataModel<Dcr10_MailHistoryModel> updateModel = new UpdateDataModel<Dcr10_MailHistoryModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

	public void insertMailHistory(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", request.getLongParam("apprId"));
		param.put("senderEmail", request.getStringParam("senderEmail"));
		param.put("receiverEmail", request.getStringParam("receiverEmail"));
		param.put("referencerEmail", request.getStringParam("referencerEmail"));
		param.put("titleText", request.getStringParam("titleText"));
		param.put("bodyText", request.getStringParam("bodyText"));
		
		sqlSession.insert("dcr10_mail_history.insertMailHistory", param);
		
		/*
		List<GridDataModel> mailHistoryList = new ArrayList<GridDataModel>();
		mailHistoryList.add(request.getModelParam("mailHistoryModel"));
		
		UpdateDataModel<Dcr10_MailHistoryModel> updateModel = new UpdateDataModel<Dcr10_MailHistoryModel>(); 
		updateModel.updateModel(sqlSession, mailHistoryList, mapperName, result);
		*/
	}
	
}
