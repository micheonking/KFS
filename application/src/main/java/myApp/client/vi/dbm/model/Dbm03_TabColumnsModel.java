package myApp.client.vi.dbm.model;

import myApp.client.utils.GridDataModel;

public class Dbm03_TabColumnsModel implements GridDataModel {

	private	Long		rowNo;
	private	String		errorName;
	private	String		errorMsg;


	@Override
	public void setKeyId(Long id) {
		this.setRowNo(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRowNo(); 
	}

	public	Long getRowNo() {
		return	rowNo;
	}

	public void setRowNo(Long id) {
		this.rowNo = id;
	}

	public	String getErrorName() {
		return	errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public	String getErrorMsg() {
		return	errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



}