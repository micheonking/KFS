package myApp.client.vi.emp.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Emp01_PersonModel implements GridDataModel {
	private Long personId;
	private Long companyId;
	private String korName;
	private String engName;
	private String chnName;
	private String nationCode;
	private String nationName;
	private String ctzNo;
	private String genderCode;
	private String genderName;
	private Date birthday;
	private String mobileTelno;
	private String homeTelno;
	private String emgrcyTelno;
	private String emailAddr;
	private String zipCode;
	private String zipAddr;
	private String addrDetail;
	private String note;
	private String password;

	@Override
	public void setKeyId(Long id) {
		this.setPersonId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getPersonId();
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
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

	public String getCtzNo() {
		return ctzNo;
	}

	public void setCtzNo(String ctzNo) {
		this.ctzNo = ctzNo;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobileTelno() {
		return mobileTelno;
	}

	public void setMobileTelno(String mobileTelno) {
		this.mobileTelno = mobileTelno;
	}

	public String getHomeTelno() {
		return homeTelno;
	}

	public void setHomeTelno(String homeTelno) {
		this.homeTelno = homeTelno;
	}

	public String getEmgrcyTelno() {
		return emgrcyTelno;
	}

	public void setEmgrcyTelno(String emgrcyTelno) {
		this.emgrcyTelno = emgrcyTelno;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipAddr() {
		return zipAddr;
	}

	public void setZipAddr(String zipAddr) {
		this.zipAddr = zipAddr;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
