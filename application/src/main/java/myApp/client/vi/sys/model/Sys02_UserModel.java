package myApp.client.vi.sys.model;

import myApp.client.utils.GridDataModel;

public class Sys02_UserModel implements GridDataModel {

	private Long userId ;
	private Long companyId;
	private String korName; 
	private String email; 
	private String telNo01; 
	private String telNo02; 
	private String faxNo; 
	private String loginId; 
	private String passwd; 
	private String note; 
	
	private Sys01_CompanyModel companyModel = new Sys01_CompanyModel(); 

	public Sys02_UserModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setUserId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getUserId();
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getKorName() {
		return korName;
	}
	public void setKorName(String korName) {
		this.korName = korName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelNo01() {
		return telNo01;
	}
	public void setTelNo01(String telNo01) {
		this.telNo01 = telNo01;
	}
	public String getTelNo02() {
		return telNo02;
	}
	public void setTelNo02(String telNo02) {
		this.telNo02 = telNo02;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Sys01_CompanyModel getCompanyModel() {
		return companyModel;
	}
	public void setCompanyModel(Sys01_CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}

