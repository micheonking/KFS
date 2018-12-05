package myApp.client.vi.dbm.model;

import myApp.client.utils.GridDataModel;

public class Dbm01_TabCommentsModel implements GridDataModel {

	private Long	tableId; 
	private	String	tableName ;
	private	String	tableComments;
	private	String	tablespaceName;
	private	String	tableType;

	@Override
	public void setKeyId(Long id) {
		this.setTableId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getTableId();
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComments() {
		return tableComments;
	}

	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}

	public String getTablespaceName() {
		return tablespaceName;
	}

	public void setTablespaceName(String tablespaceName) {
		this.tablespaceName = tablespaceName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

}

