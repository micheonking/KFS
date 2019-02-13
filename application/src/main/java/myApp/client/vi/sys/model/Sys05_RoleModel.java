package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys05_RoleModel implements GridDataModel { 

	private Long    roleId ;
	private String  roleName ;
	private Long    seq ;
	private String  cmpCode ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;

	@Override
	public void setKeyId(Long id) {
		this.roleId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getRoleId(); 
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
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
