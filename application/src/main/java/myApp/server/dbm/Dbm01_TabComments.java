package myApp.server.dbm;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModel;
import myApp.client.vi.dbm.model.Dbm02_ColCommentsModel;

public class Dbm01_TabComments { 
	
	private	String mapperName = "dbm01_tab_comments"; 
	
	public void selectByTableName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		System.out.println("dbm01_tab_comments param: " + request.getStringParam("tableName") ); 
		
		String tableName = request.getStringParam("tableName");
		
		List<GridDataModel> list = sqlSession.selectList(mapperName + ".selectByTableName", tableName) ;
		
		System.out.println("dbm01_tab_comments size: " + list.size() );
		
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByColumnName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String tableName = request.getStringParam("tableName"); 
		List<GridDataModel> List = sqlSession.selectList(mapperName + ".selectByColumnName", tableName) ;
		result.setRetrieveResult(1, "select ok", List);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		for(GridDataModel data : request.getList()) {
			Dbm01_TabCommentsModel tabComment = (Dbm01_TabCommentsModel)data;

			String commentsText = "comment on table " + tabComment.getTableName() + " is '" + tabComment.getTableComments()+ "'";

			System.out.println("dbm01_tab_comments param: " + commentsText);

			sqlSession.selectList( "dbm01_tab_comments.callByDDLRun", commentsText);
		}
		
		result.setRetrieveResult(request.getParam().size(), "dbm01_tab_comments.callByDDLRun", request.getList());
		
	}
}
