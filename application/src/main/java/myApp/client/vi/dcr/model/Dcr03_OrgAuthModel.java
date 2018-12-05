package myApp.client.vi.dcr.model;

import myApp.client.utils.GridDataModel;

public class Dcr03_OrgAuthModel implements GridDataModel {
	private Long 	orgAuthId;
	private Long 	orgCodeId;
	private Long 	classTreeId;
	private String 	retrieveYn;
	private Boolean retrieveYnCheck;
	private String 	updateYn;
	private Boolean updateYnCheck;
	
	private String 	note;
	
	@Override
	public void setKeyId(Long id) {
		this.setOrgAuthId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getOrgAuthId();
	}

	public Long getOrgAuthId() {
		return orgAuthId;
	}

	public void setOrgAuthId(Long orgAuthId) {
		this.orgAuthId = orgAuthId;
	}

	public Long getOrgCodeId() {
		return orgCodeId;
	}

	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}

	public Long getClassTreeId() {
		return classTreeId;
	}

	public void setClassTreeId(Long classTreeId) {
		this.classTreeId = classTreeId;
	}

	public String getRetrieveYn() {
		return retrieveYn;
	}

	public void setRetrieveYn(String retrieveYn) {
		this.retrieveYn = retrieveYn;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getRetrieveYnCheck() {
		return "true".equals(this.getRetrieveYn());
	}

	public void setRetrieveYnCheck(Boolean retrieveYnCheck) {
		this.retrieveYnCheck = retrieveYnCheck; 
		this.retrieveYn = this.retrieveYnCheck.toString();
	}

	public Boolean getUpdateYnCheck() {
		return "true".equals(this.getUpdateYn());
	}

	public void setUpdateYnCheck(Boolean updateYnCheck) {
		this.updateYnCheck = updateYnCheck; 
		this.updateYn = this.updateYnCheck.toString();
	}

	
	
}
