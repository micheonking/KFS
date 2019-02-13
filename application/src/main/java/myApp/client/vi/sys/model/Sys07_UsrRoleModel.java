package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys07_UsrRoleModel implements GridDataModel { 

	private Long    usrRoleId ;
	private String  cmpCode ;
	private String  usrNo ;
	private Long    roleId ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;

	@Override
	public void setKeyId(Long id) {
		this.usrRoleId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getUsrRoleId(); 
	}

	public Long getUsrRoleId() {
		return usrRoleId;
	}

	public void setUsrRoleId(Long usrRoleId) {
		this.usrRoleId = usrRoleId;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public String getUsrNo() {
		return usrNo;
	}

	public void setUsrNo(String usrNo) {
		this.usrNo = usrNo;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
