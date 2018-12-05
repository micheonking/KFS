package myApp.client.vi.fnd.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Fnd02_FundInfoModel implements GridDataModel {

	private	Long	fundInfoId;
	private	Long	fundCodeId;
	private	Date	workDate;
	private	String	fundName;
	private	String	fundTypeCode;
	private	String	sintakGb;
	private	String	publicYn;
	private	Long	seoljAek;
	private	Long	sunjasanAek;
	private	Long	orgCodeId;
	private	Long	emp1Id;
	private	Long	emp2Id;
	private	Double	managementRate;
	private	Double	salesRate;
	private	String	consignmentCd;
	private	Double	consignmentRate;
	private	String	officeWorkCd;
	private	Double	officeWorkRate;
	private	Date	planDate;
	private	String	planSalesDscr;
	private	String	planInvestorDscr;
	private	String	planNote;
	private	String	orgCode;
	private	String	orgCodeName;
	private	String	emp1Name;
	private	String	emp2Name;
	private	String	fundTypeName;
	private	String	sintakGbName;
	private	String	publicName;
	private	String	consignmentName;
	private	String	officeWorkName;
	private	Date	uploadWorkDate;

	@Override
	public void setKeyId(Long id) {
		this.setFundInfoId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getFundInfoId();
	}

	public	Long getFundInfoId() {
		return	fundInfoId;
	}

	public void setFundInfoId(Long fundInfoId) {
		this.fundInfoId = fundInfoId;
	}

	public Long getFundCodeId() {
		return fundCodeId;
	}

	public void setFundCodeId(Long fundCodeId) {
		this.fundCodeId = fundCodeId;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundTypeCode() {
		return fundTypeCode;
	}

	public void setFundTypeCode(String fundTypeCode) {
		this.fundTypeCode = fundTypeCode;
	}

	public String getSintakGb() {
		return sintakGb;
	}

	public void setSintakGb(String sintakGb) {
		this.sintakGb = sintakGb;
	}

	public String getPublicYn() {
		return publicYn;
	}

	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}

	public Long getSeoljAek() {
		return seoljAek;
	}

	public void setSeoljAek(Long seoljAek) {
		this.seoljAek = seoljAek;
	}

	public Long getSunjasanAek() {
		return sunjasanAek;
	}

	public void setSunjasanAek(Long sunjasanAek) {
		this.sunjasanAek = sunjasanAek;
	}

	public Long getOrgCodeId() {
		return orgCodeId;
	}

	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}

	public Long getEmp1Id() {
		return emp1Id;
	}

	public void setEmp1Id(Long emp1Id) {
		this.emp1Id = emp1Id;
	}

	public Long getEmp2Id() {
		return emp2Id;
	}

	public void setEmp2Id(Long emp2Id) {
		this.emp2Id = emp2Id;
	}

	public Double getManagementRate() {
		return managementRate;
	}

	public void setManagementRate(Double managementRate) {
		this.managementRate = managementRate;
	}

	public Double getSalesRate() {
		return salesRate;
	}

	public void setSalesRate(Double salesRate) {
		this.salesRate = salesRate;
	}

	public String getConsignmentCd() {
		return consignmentCd;
	}

	public void setConsignmentCd(String consignmentCd) {
		this.consignmentCd = consignmentCd;
	}

	public Double getConsignmentRate() {
		return consignmentRate;
	}

	public void setConsignmentRate(Double consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	public String getOfficeWorkCd() {
		return officeWorkCd;
	}

	public void setOfficeWorkCd(String officeWorkCd) {
		this.officeWorkCd = officeWorkCd;
	}

	public Double getOfficeWorkRate() {
		return officeWorkRate;
	}

	public void setOfficeWorkRate(Double officeWorkRate) {
		this.officeWorkRate = officeWorkRate;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getPlanSalesDscr() {
		return planSalesDscr;
	}

	public void setPlanSalesDscr(String planSalesDscr) {
		this.planSalesDscr = planSalesDscr;
	}

	public String getPlanInvestorDscr() {
		return planInvestorDscr;
	}

	public void setPlanInvestorDscr(String planInvestorDscr) {
		this.planInvestorDscr = planInvestorDscr;
	}

	public String getPlanNote() {
		return planNote;
	}

	public void setPlanNote(String planNote) {
		this.planNote = planNote;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCodeName() {
		return orgCodeName;
	}

	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
	}

	public String getEmp1Name() {
		return emp1Name;
	}

	public void setEmp1Name(String emp1Name) {
		this.emp1Name = emp1Name;
	}

	public String getEmp2Name() {
		return emp2Name;
	}

	public void setEmp2Name(String emp2Name) {
		this.emp2Name = emp2Name;
	}

	public String getFundTypeName() {
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public String getSintakGbName() {
		return sintakGbName;
	}

	public void setSintakGbName(String sintakGbName) {
		this.sintakGbName = sintakGbName;
	}

	public String getPublicName() {
		return publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	public String getConsignmentName() {
		return consignmentName;
	}

	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}

	public String getOfficeWorkName() {
		return officeWorkName;
	}

	public void setOfficeWorkName(String officeWorkName) {
		this.officeWorkName = officeWorkName;
	}

	public Date getUploadWorkDate() {
		return uploadWorkDate;
	}

	public void setUploadWorkDate(Date uploadWorkDate) {
		this.uploadWorkDate = uploadWorkDate;
	}

}
