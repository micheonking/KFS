package myApp.client.vi.emp.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys02_UserModel;
import myApp.client.vi.sys.model.Sys21_LicenseCodeModel;

public class LicenseModel implements GridDataModel {
	private Long 	licenseId;
	private Long	userId;
	private String 	licenseCode ;
	private Date	issueDate ;
	private Date 	expirationDate ;
	private String 	note;
	
	private Sys02_UserModel userModel = new Sys02_UserModel(); 
	private Sys21_LicenseCodeModel licenseCodeModel = new Sys21_LicenseCodeModel();
	
	@Override
	public void setKeyId(Long id) {
		this.setLicenseId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getLicenseId(); 
	}

	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Sys02_UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(Sys02_UserModel userModel) {
		this.userModel = userModel;
	}

	public Sys21_LicenseCodeModel getLicenseCodeModel() {
		return licenseCodeModel;
	}

	public void setLicenseCodeModel(Sys21_LicenseCodeModel licenseCodeModel) {
		this.licenseCodeModel = licenseCodeModel;
	}
	
	public String getFindCell(){
		return null; 
	}
	
	public void setFindCell(String cell){
	}

}
