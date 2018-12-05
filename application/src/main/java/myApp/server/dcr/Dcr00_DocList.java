package myApp.server.dcr;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr00_DocListModel;

public class Dcr00_DocList {
	private String mapperName = "dcr00_doc_list";

	public void selectSearchDoc2(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, String> param = new HashMap<String, String>();
		System.out.println("1");
		param.put("fileName", "%" + request.getStringParam("fileName") + "%");
		System.out.println("2");
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectSearchDoc2",param) ; 
		System.out.println("3");
		System.out.println("장진희 : " + list.size());
		for(int i = 0; i < list.size(); i++) {
			Dcr00_DocListModel a = (Dcr00_DocListModel) list.get(i);
			System.out.println("zzz : " + a.getDocId());
		}
		result.setRetrieveResult(1, "select ok", list);

	}

	public void selectSearchDoc(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLongParam("companyId");
		Long orgId = request.getLongParam("orgId");
		Date regDate = request.getDateParam("regDate");
		Date endRegDate = request.getDateParam("endRegDate");
		String sintak = request.getStringParam("sintak");
		String publicSubscribe = request.getStringParam("publicSubscribe");
		String empName = "%"+request.getStringParam("empName")+"%";
		Date fundStartDate = request.getDateParam("fundStartDate");
		Date fundEndDate = request.getDateParam("fundEndDate");
		String fundType = request.getStringParam("fundType");
		String fundClose = request.getStringParam("fundClose");
		String orgName = "%"+request.getStringParam("orgName")+"%";
		String classTreeName = request.getStringParam("classTreeName");
		String fundName = "%"+request.getStringParam("fundName")+"%";
		if (empName == null) {
			empName = "%";
		}
		if (orgName == null) {
			orgName = "%";
		}
		if (fundName == null) {
			fundName = "%";
		}

		System.out.println("=(selectSearchDoc)= companyId       :"+companyId);
		System.out.println("=(selectSearchDoc)= orgId           :"+orgId);
		System.out.println("=(selectSearchDoc)= regDate         :"+regDate);
		System.out.println("=(selectSearchDoc)= endRegDate      :"+endRegDate);
		System.out.println("=(selectSearchDoc)= sintak          :"+sintak);
		System.out.println("=(selectSearchDoc)= publicSubscribe :"+publicSubscribe);
		System.out.println("=(selectSearchDoc)= empName         :"+empName);
		System.out.println("=(selectSearchDoc)= fundStartDate   :"+fundStartDate);
		System.out.println("=(selectSearchDoc)= fundEndDate     :"+fundEndDate);
		System.out.println("=(selectSearchDoc)= fundType        :"+fundType);
		System.out.println("=(selectSearchDoc)= fundClose       :"+fundClose);
		System.out.println("=(selectSearchDoc)= orgName         :"+orgName);
		System.out.println("=(selectSearchDoc)= classTreeName   :"+classTreeName);
		System.out.println("=(selectSearchDoc)= fundName        :"+fundName);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("orgId", orgId);
		param.put("regDate", regDate);
		param.put("endRegDate", endRegDate);
		param.put("sintak", sintak);
		param.put("publicSubscribe", publicSubscribe);
		param.put("empName", empName);
		param.put("fundStartDate", fundStartDate);
		param.put("fundEndDate", fundEndDate);
		param.put("fundType", fundType);
		param.put("fundClose", fundClose);
		param.put("orgName", orgName);
		param.put("classTreeName", classTreeName);
		param.put("fundName", fundName);
		
//		for( String key : param.keySet() ){
//			System.out.println( "key : " + key + ", value : " + param.get(key)); 
//		}
	
		List<GridDataModel> list = sqlSession.selectList("dcr00_doc_list" + ".selectSearchDoc", param);
		System.out.println("list.size	: "+list.size());
		result.setRetrieveResult(1, "select ok", list);
	}
	
	
	
}
