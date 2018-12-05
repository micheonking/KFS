package myApp.client.vi.emp.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Emp00_InfoModel implements GridDataModel {

	private Long transId;
	private Long companyId;
	private Long personId;
	private String ctzNo;
	private String korName;
	private String engName;
	private Date birthday;
	private String mobileTelNo;
	private String homeTelNo;
	private String emergencyTelNo;
	private String officeTel;
	private String emailAddress;
	private String nationCode;
	private String nationName;
	private String genderCode;
	private String genderName;
	private Long empId;
	private String empNo;
	private String empKindCode;
	private String empKindName;
	private Date hireDate;
	private String hireCode;
	private String hireName;
	private Date retireDate;
	private String retireCode;
	private String retireName;
	private Date transDate;
	private Date closeDate;
	private String positionCode; // 직위
	private String positionName;
	private String titleCode; // 직책
	private String titleName;
	private Long orgCodeId;
	private String orgCode;
	private String orgLevelCode;
	private String orgName;
	private String cycleTime;
	private String empId_String;
	private String personId_String;

	@Override
	public void setKeyId(Long id) {
		this.setTransId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getTransId();
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getCtzNo() {
		return ctzNo;
	}

	public void setCtzNo(String ctzNo) {
		this.ctzNo = ctzNo;
	}

	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobileTelNo() {
		return mobileTelNo;
	}

	public void setMobileTelNo(String mobileTelNo) {
		this.mobileTelNo = mobileTelNo;
	}

	public String getHomeTelNo() {
		return homeTelNo;
	}

	public void setHomeTelNo(String homeTelNo) {
		this.homeTelNo = homeTelNo;
	}

	public String getEmergencyTelNo() {
		return emergencyTelNo;
	}

	public void setEmergencyTelNo(String emergencyTelNo) {
		this.emergencyTelNo = emergencyTelNo;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpKindCode() {
		return empKindCode;
	}

	public void setEmpKindCode(String empKindCode) {
		this.empKindCode = empKindCode;
	}

	public String getEmpKindName() {
		return empKindName;
	}

	public void setEmpKindName(String empKindName) {
		this.empKindName = empKindName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getHireCode() {
		return hireCode;
	}

	public void setHireCode(String hireCode) {
		this.hireCode = hireCode;
	}

	public String getHireName() {
		return hireName;
	}

	public void setHireName(String hireName) {
		this.hireName = hireName;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	public String getRetireCode() {
		return retireCode;
	}

	public void setRetireCode(String retireCode) {
		this.retireCode = retireCode;
	}

	public String getRetireName() {
		return retireName;
	}

	public void setRetireName(String retireName) {
		this.retireName = retireName;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgLevelCode() {
		return orgLevelCode;
	}

	public void setOrgLevelCode(String orgLevelCode) {
		this.orgLevelCode = orgLevelCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgCodeId() {
		return orgCodeId;
	}

	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}

	public String getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(String cycleTime) {
		this.cycleTime = cycleTime;
	}

	public String getEmpId_String() {
		return empId_String;
	}

	public void setEmpId_String(String empId_String) {
		this.empId_String = empId_String;
	}

	public String getPersonId_String() {
		return personId_String;
	}

	public void setPersonId_String(String personId_String) {
		this.personId_String = personId_String;
	}

}
