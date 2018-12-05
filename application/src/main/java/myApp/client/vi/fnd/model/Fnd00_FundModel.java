package myApp.client.vi.fnd.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Fnd00_FundModel implements GridDataModel {

	private Long    fundId;
	private String  fundCode;
	private String  fundName;
	private String  fundTypeCode;
	private Date    startDate;
	private Date    endDate;
	private String  closeYn;
	private String  planYn;
	private Long    realFundId;
	private Long    companyId;
	private Long    fundInfoId;
	private Date    workDate;
	private String  sintakGb;
	private String  publicYn;
	private Long    seoljAek;
	private Long    sunjasanAek;
	private Long    orgCodeId;
	private Long    emp1Id;
	private Long    emp2Id;
	private Long    managementRate;
	private Long    salesRate;
	private String  consignmentCd;
	private Long    consignmentRate;
	private String  officeWorkCd;
	private Long    officeWorkRate;
	private Date    planDate;
	private String  planSalesDscr;
	private String  planInvestorDscr;
	private String  planNote;

	private String  fundTypeName;
	private String  sintakGbName;
	private String  publicName;
	private String  closeName;
	private String  planName;
	private String  orgCodeName;
	private String  empName1;
	private String  empName2;
	private String  consignmentName;

	@Override
	public void setKeyId(Long id) {
		this.setFundId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getFundId();
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	public String getPlanYn() {
		return planYn;
	}

	public void setPlanYn(String planYn) {
		this.planYn = planYn;
	}

	public Long getRealFundId() {
		return realFundId;
	}

	public void setRealFundId(Long realFundId) {
		this.realFundId = realFundId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getFundInfoId() {
		return fundInfoId;
	}

	public void setFundInfoId(Long fundInfoId) {
		this.fundInfoId = fundInfoId;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
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

	public Long getManagementRate() {
		return managementRate;
	}

	public void setManagementRate(Long managementRate) {
		this.managementRate = managementRate;
	}

	public Long getSalesRate() {
		return salesRate;
	}

	public void setSalesRate(Long salesRate) {
		this.salesRate = salesRate;
	}

	public String getConsignmentCd() {
		return consignmentCd;
	}

	public void setConsignmentCd(String consignmentCd) {
		this.consignmentCd = consignmentCd;
	}

	public Long getConsignmentRate() {
		return consignmentRate;
	}

	public void setConsignmentRate(Long consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	public String getOfficeWorkCd() {
		return officeWorkCd;
	}

	public void setOfficeWorkCd(String officeWorkCd) {
		this.officeWorkCd = officeWorkCd;
	}

	public Long getOfficeWorkRate() {
		return officeWorkRate;
	}

	public void setOfficeWorkRate(Long officeWorkRate) {
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

	public String getCloseName() {
		return closeName;
	}

	public void setCloseName(String closeName) {
		this.closeName = closeName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getOrgCodeName() {
		return orgCodeName;
	}

	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
	}

	public String getEmpName1() {
		return empName1;
	}

	public void setEmpName1(String empName1) {
		this.empName1 = empName1;
	}

	public String getEmpName2() {
		return empName2;
	}

	public void setEmpName2(String empName2) {
		this.empName2 = empName2;
	}

	public String getConsignmentName() {
		return consignmentName;
	}

	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}
	
}
