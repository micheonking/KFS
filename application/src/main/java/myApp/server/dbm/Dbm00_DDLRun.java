package myApp.server.dbm;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class Dbm00_DDLRun { 
	
	private	String mapperName = "dbm01_tab_comments"; 
	
	public void callByDDLRun(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		System.out.println("dbm00_ddlrun param: " + request.getStringParam("para") ); 
		
		String inPara = request.getStringParam("para");
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".callByDDLRun", inPara) ;
		
		System.out.println("dbm00_ddlrun size: " + list.size() );
		
//		result.setRetrieveResult(1, "select ok", list);
		result.setRetrieveResult(request.getParam().size(), "dbm01_tab_comments.callByDDLRun", request.getList());
	}

}
