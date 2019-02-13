package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys04_CmpMenuModel implements GridDataModel { 

	private Long    cmpMenuId ;
	private String  cmpCode ;
	private Long    menuId ;
	private String  useYn ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;

	@Override
	public void setKeyId(Long id) {
		this.cmpMenuId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getCmpMenuId(); 
	}

	public Long getCmpMenuId() {
		return cmpMenuId;
	}

	public void setCmpMenuId(Long cmpMenuId) {
		this.cmpMenuId = cmpMenuId;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
