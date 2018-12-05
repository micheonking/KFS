package myApp.client.vi.emp.model;

import myApp.client.utils.GridDataModel;

public class Emp10_AddrBookModel implements GridDataModel {

	private Long addrBookId;
	private String personName;
	private String email;
	private String mobile;
	private String officeTel1;
	private String companyName;
	private String posName;
	private String titleName;
	private Long empId;
	private String orgName;
	private String note;

	@Override
	public void setKeyId(Long id) {
		this.setAddrBookId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAddrBookId();
	}

	public Long getAddrBookId() {
		return addrBookId;
	}

	public void setAddrBookId(Long addrBookId) {
		this.addrBookId = addrBookId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficeTel1() {
		return officeTel1;
	}

	public void setOfficeTel1(String officeTel1) {
		this.officeTel1 = officeTel1;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
