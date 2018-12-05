package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys01_CompanyModel implements GridDataModel { 

	private Long 	companyId ; 	
	private String 	companyName ;
	private String 	bizNo ;
	private String 	telNo01 ;
	private String 	telNo02 ;
	private String 	faxNo01 ;
	private String 	zipCode ;
	private String 	zipAddress ;
	private String 	zipDetail ;
	private String 	locationName ;
	private Date 	annvDate ;
	private String 	note ;
	private String 	menuYn;
	
	private Sys03_CompanyMenuModel companyMenuModel = new Sys03_CompanyMenuModel();  
	
	@Override
	public void setKeyId(Long id) {
		this.companyId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getCompanyId(); 
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
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

	public String getFaxNo01() {
		return faxNo01;
	}

	public void setFaxNo01(String faxNo01) {
		this.faxNo01 = faxNo01;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipAddress() {
		return zipAddress;
	}

	public void setZipAddress(String zipAddress) {
		this.zipAddress = zipAddress;
	}

	public String getZipDetail() {
		return zipDetail;
	}

	public void setZipDetail(String zipDetail) {
		this.zipDetail = zipDetail;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getAnnvDate() {
		return annvDate;
	}

	public void setAnnvDate(Date annvDate) {
		this.annvDate = annvDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMenuYn() {
		return menuYn;
	}

	public void setMenuYn(String menuYn) {
		this.menuYn = menuYn;
	}
	
	public Boolean getMenuYnChecked() {
		return this.menuYn.equals("true"); 
	}
	
	public void setMenuYnChecked(Boolean menuYnChecked) {
		this.setMenuYn(menuYnChecked.toString());
	}

	public Sys03_CompanyMenuModel getCompanyMenuModel() {
		return companyMenuModel;
	}

	public void setCompanyMenuModel(Sys03_CompanyMenuModel companyMenuModel) {
		this.companyMenuModel = companyMenuModel;
	}
	
	
	
}
