package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys01_CmpInfoModel implements GridDataModel { 

	private Long    cmpInfoId ;
	private String  cmpCode ;
	private String  cmpName ;
	private String  bizNo ;
	private String  telNo ;
	private String  addr ;
	private String  useYn ;
	private String  tmpPwd ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;
	
	private Sys04_CmpMenuModel cmpMenuModel = new Sys04_CmpMenuModel();

	@Override
	public void setKeyId(Long id) {
		this.cmpInfoId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getCmpInfoId(); 
	}

	public Long getCmpInfoId() {
		return cmpInfoId;
	}

	public void setCmpInfoId(Long cmpInfoId) {
		this.cmpInfoId = cmpInfoId;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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

	public Sys04_CmpMenuModel getCmpMenuModel() {
		return cmpMenuModel;
	}

	public void setCmpMenuModel(Sys04_CmpMenuModel cmpMenuModel) {
		this.cmpMenuModel = cmpMenuModel;
	}

	public Boolean getUseYnChecked() {
		return this.useYn.equals("Y"); 
	}
	
	public void setUseYnChecked(Boolean useYnChecked) {
		
		String useYn;
		if(useYnChecked) {
			useYn = "Y";
		} else {
			useYn = "N";
		}
		this.setUseYn(useYn);
	}

	public String getTmpPwd() {
		return tmpPwd;
	}

	public void setTmpPwd(String tmpPwd) {
		this.tmpPwd = tmpPwd;
	}

}
