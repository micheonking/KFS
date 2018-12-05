package myApp.server.utils.db;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;

public class UpdateDataModel<T extends GridDataModel> {
	
	public UpdateDataModel(){
	}
	
	private Map<String, Object> getDataMap(GridDataModel dataModel) { 
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for(java.lang.reflect.Field field:dataModel.getClass().getDeclaredFields()){
			field.setAccessible(true); 
			try {
				dataMap.put(field.getName(), field.get(dataModel));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataMap; 
	}
	
	// MSSQL column info 
	public void updateModel(SqlSession sqlSession, List<GridDataModel> list, String tableName, ServiceResult result ) {

		Map<String, String> resultMap = new HashMap<String, String>();

		// BD마다 스키마가 다르다. 
		// 오라클, MS-SQL, Tibero 등. 
		List<ColumnModel> columnList = sqlSession.selectList("dbConfig.getColumnListTibero", tableName.toUpperCase());

		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
		for(ResultMapping mapping : sqlSession.getConfiguration().getResultMap(tableName + ".mapper").getResultMappings()){
			if(mapping.getColumn() != null){
				resultMap.put(mapping.getColumn().toUpperCase().replaceAll(" ",  ""), mapping.getProperty());
			}
		}
		
		for(GridDataModel dataModel: list){
			IsNewData isNewData = new IsNewData(); // 신규 입력인지 변경인지를 확인한다. 
			try {  
				if(isNewData.isNewData(sqlSession, tableName, dataModel.getKeyId())) { 
					String insertSql = getInsertSql(dataModel, tableName, resultMap, columnList);
					System.out.println(insertSql);
					SqlRunner runner = new SqlRunner(sqlSession.getConnection());
					runner.insert(insertSql);
				}
				else {
					String updateSql = getUpdateSql(dataModel, tableName, resultMap, columnList); 
					System.out.println(updateSql);
					SqlRunner runner = new SqlRunner(sqlSession.getConnection());
					runner.update(updateSql);
				}					
			} 
			catch (RuntimeSqlException | SQLException e) {
				
				System.out.println("sql exception 처리가 안됨. 다시 해봅시다. "); 
				
				result.fail(-1, "update fail : \n" + e.getMessage());
			    result.setMessage(e.getMessage());
				e.printStackTrace();
				return; 
			}
			
			T updateModel = sqlSession.selectOne(tableName + ".selectById", dataModel.getKeyId());
			updateList.add(updateModel) ; 
		}
		
		result.setRetrieveResult(1, "update success!", updateList); 
	}
	
	
	private String getDataString(Object data){
		String dataString; 
		if(data !=  null){
			if(data.getClass().getTypeName().indexOf("Date") > 0) {
				Date date = (Date)data;
				// ORACLE & TIBERO 
				dataString = "to_date('" +  new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date) + "', 'yyyymmdd hh24:mi:ss')";
				// MSSQL
				//dataString = "convert(date, '" +  new SimpleDateFormat("yyyyMMdd").format(date) + "')";
			}
			else if(data.getClass().getTypeName().indexOf("String") > 0) {
				dataString = "'" + data.toString() + "'"; 
			}
			else if(data.getClass().getTypeName().indexOf("Boolean") > 0) {
				dataString = "'" + data.toString() + "'"; 
			}
			else {
				dataString = data.toString();
			}
		}
		else {
			dataString = "null"; 
		}
		return dataString + "\n" ; 
	}
	
	private String getUpdateSql(GridDataModel updateModel, String tableName, Map<String, String> resultMap, List<ColumnModel> columnList){

		String keyColumnName = tableName.toUpperCase() + "_ID" ; // Primary Key 칼럼은 반드시 table_name + _id 이어야 한다.
		Map<String, Object> dataMap = getDataMap(updateModel);
		
		return new SQL() {{
			UPDATE(tableName); 
			for(ColumnModel columnModel: columnList){
				String propertyName = resultMap.get(columnModel.getColumnName());
				if(propertyName != null){
					String dataString = getDataString(dataMap.get(propertyName));
					if(columnModel.getColumnName().equals(keyColumnName)) { 
						WHERE(columnModel.getColumnName() + " = " +  updateModel.getKeyId()); 
					}
					else {
						SET(columnModel.getColumnName() + " = " +  dataString); 
					}
				}
			}
		}}.toString();
	}
	
	private String getInsertSql(GridDataModel insertModel, String tableName, Map<String, String> resultMap, List<ColumnModel> columnList){

		Map<String, Object>  dataMap = getDataMap(insertModel);
		
		return new SQL() {{
			
			INSERT_INTO(tableName);
			for(ColumnModel columnModel: columnList){
				
				String propertyName = resultMap.get(columnModel.getColumnName());
				if(propertyName != null){
					VALUES(columnModel.getColumnName(), getDataString(dataMap.get(propertyName)));
				}
			}
		}}.toString();
	}

	public void deleteModel(SqlSession sqlSession, List<GridDataModel> list, String tableName, ServiceResult result ){

		for(GridDataModel dataModel: list){

			String deleteSql 
				= new SQL() {{
				    DELETE_FROM(tableName);
				    WHERE(tableName + "_id = " +  dataModel.getKeyId()); 
				  }}.toString();
			
			try {  
				System.out.println(deleteSql);
				SqlRunner runner = new SqlRunner(sqlSession.getConnection());
				runner.delete(deleteSql);
			} catch (SQLException e) {
				result.fail(-1, "delete fail : \n" + e.getMessage());
			    result.setMessage("delete fail");
				e.printStackTrace();
				return; 
			}
			
			result.setRetrieveResult(1, "delete sucess!", list);
		}
	}
}
