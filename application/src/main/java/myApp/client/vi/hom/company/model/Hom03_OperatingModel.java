package myApp.client.vi.hom.company.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Hom03_OperatingModel implements GridDataModel {
	private	Long	operatingId;
	private	Long	companyId;
	private	Long	orgCodeId;
	private	String	nameTitle;
	private	String	chargeStockFirm;
	private	String	majorCareer;
	private	String	academicCertificate;
	private	String	contactInfomation;
	private	String	numericalOrder;
	private	String	orgName;
	private	Integer	chargeStockFirmCnt;
	private	Integer	chargeStockFirmMax;
	
	@Override
	public void setKeyId(Long id) {
		this.setOperatingId(id);
	}
	@Override
	public Long getKeyId() {
		return this.getOperatingId(); 
	}
	public Long getOperatingId() {
		return operatingId;
	}
	public void setOperatingId(Long operatingId) {
		this.operatingId = operatingId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getOrgCodeId() {
		return orgCodeId;
	}
	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}
	public String getNameTitle() {
		return nameTitle;
	}
	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}
	public String getChargeStockFirm() {
		return chargeStockFirm;
	}
	public void setChargeStockFirm(String chargeStockFirm) {
		this.chargeStockFirm = chargeStockFirm;
	}
	public String getMajorCareer() {
		return majorCareer;
	}
	public void setMajorCareer(String majorCareer) {
		this.majorCareer = majorCareer;
	}
	public String getAcademicCertificate() {
		return academicCertificate;
	}
	public void setAcademicCertificate(String academicCertificate) {
		this.academicCertificate = academicCertificate;
	}
	public String getContactInfomation() {
		return contactInfomation;
	}
	public void setContactInfomation(String contactInfomation) {
		this.contactInfomation = contactInfomation;
	}
	public String getNumericalOrder() {
		return numericalOrder;
	}
	public void setNumericalOrder(String numericalOrder) {
		this.numericalOrder = numericalOrder;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getChargeStockFirmCnt() {
		return chargeStockFirmCnt;
	}
	public void setChargeStockFirmCnt(Integer chargeStockFirmCnt) {
		this.chargeStockFirmCnt = chargeStockFirmCnt;
	}
	public Integer getChargeStockFirmMax() {
		return chargeStockFirmMax;
	}
	public void setChargeStockFirmMax(Integer chargeStockFirmMax) {
		this.chargeStockFirmMax = chargeStockFirmMax;
	}
}