package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys50_TermsDictModel implements GridDataModel { 

	private Long    termsDictId ;
	private String  shtName ;
	private String  fullName ;
	private String  korName ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;

	@Override
	public void setKeyId(Long id) {
		this.termsDictId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getTermsDictId(); 
	}

	public Long getTermsDictId() {
		return termsDictId;
	}

	public void setTermsDictId(Long termsDictId) {
		this.termsDictId = termsDictId;
	}

	public String getShtName() {
		return shtName;
	}

	public void setShtName(String shtName) {
		this.shtName = shtName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getInsUsrNo() {
		return insUsrNo;
	}

	public void setInsUsrNo(String insUsrNo) {
		this.insUsrNo = insUsrNo;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getUpdUsrNo() {
		return updUsrNo;
	}

	public void setUpdUsrNo(String updUsrNo) {
		this.updUsrNo = updUsrNo;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
}
