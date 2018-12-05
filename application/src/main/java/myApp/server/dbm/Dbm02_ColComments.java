package myApp.server.dbm;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dbm.model.Dbm02_ColCommentsModel;

public class Dbm02_ColComments { 
	
	String mapperName = "dbm02_col_comments"; 
	
	public void selectByTableName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		System.out.println("dbm02_col_comments param: " + request.getStringParam("tableName") ); 
		
		String tableName = request.getStringParam("tableName");
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTableName", tableName) ;
		
		System.out.println("dbm02_col_comments size: " + list.size() );
		
		result.setRetrieveResult(1, "select ok", list);
	}
    //
	
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
//		List<GridDataModel> list = request.getList(); 
		for(GridDataModel data : request.getList()) {
			Dbm02_ColCommentsModel colComment = (Dbm02_ColCommentsModel)data;

			String commentsText = "comment on column " + colComment.getTableName() + "." + colComment.getColumnName() + " is '" + colComment.getColumnComment()+ "'";

			System.out.println("dbm01_tab_comments param: " + commentsText);

			sqlSession.selectList( "dbm01_tab_comments.callByDDLRun", commentsText);

//			ServiceRequest request = new ServiceRequest("dbm.Dbm00_DDLRun.callByDDLRun");
//			request.putStringParam("para", "comment on column " + data.getTableName() + "." + columnName + " is '" + comments + "'");
//			
//			Info.display("execute", "comment on column " + data.getTableName() + "." + columnName + " is '" + comments + "'");
//			ServiceCall service = new ServiceCall();
//			service.execute(request, this);
		}
		
		result.setRetrieveResult(request.getParam().size(), "dbm01_tab_comments.callByDDLRun", request.getList());
		
	}
}