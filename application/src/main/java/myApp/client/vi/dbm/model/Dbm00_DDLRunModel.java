package myApp.client.vi.dbm.model;

import myApp.client.utils.GridDataModel;

public class Dbm00_DDLRunModel implements GridDataModel {

	private	Long		id;
	private	Long		errorId;


	@Override
	public void setKeyId(Long id) {
		this.setId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getId(); 
	}

	public	Long getId() {
		return	id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public	Long getErrorId() {
		return	errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}
}

