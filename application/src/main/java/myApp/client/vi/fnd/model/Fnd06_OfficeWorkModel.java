package myApp.client.vi.fnd.model;

import myApp.client.utils.GridDataModel;

public class Fnd06_OfficeWorkModel implements GridDataModel {

	private	Long	companyId;
	private	Long	officeWorkId;
	private	String	officeWorkCode;
	private	String	officeWorkName;
	private	Boolean	officeWorkUseYn;
	private	String	officeWorkNote;

	@Override
	public void setKeyId(Long id) {
		this.setOfficeWorkId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getOfficeWorkId();
	}
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public	Long getOfficeWorkId() {
		return	officeWorkId;
	}

	public void setOfficeWorkId(Long officeWorkId) {
		this.officeWorkId = officeWorkId;
	}

	public String getOfficeWorkCode() {
		return officeWorkCode;
	}

	public void setOfficeWorkCode(String officeWorkCode) {
		this.officeWorkCode = officeWorkCode;
	}

	public String getOfficeWorkName() {
		return officeWorkName;
	}

	public void setOfficeWorkName(String officeWorkName) {
		this.officeWorkName = officeWorkName;
	}

	public Boolean getOfficeWorkUseYn() {
		return officeWorkUseYn;
	}

	public void setOfficeWorkUseYn(Boolean officeWorkUseYn) {
		this.officeWorkUseYn = officeWorkUseYn;
	}

	public String getOfficeWorkNote() {
		return officeWorkNote;
	}

	public void setOfficeWorkNote(String officeWorkNote) {
		this.officeWorkNote = officeWorkNote;
	}

}
