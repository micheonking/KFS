package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys02_UsrInfoModel implements GridDataModel { 

	private Long    usrInfoId ;
	private String  usrNo ;
	private String  usrName ;
	private String  cmpCode ;
	private String  dptCode ;
	private String  email ;
	private String  telNo ;
	private String  tmpPwd ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;
	
	private Sys01_CmpInfoModel cmpInfoModel = new Sys01_CmpInfoModel();
	private Sys08_DptInfoModel dptInfoModel = new Sys08_DptInfoModel();

	@Override
	public void setKeyId(Long id) {
		this.usrInfoId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getUsrInfoId(); 
	}

	public Long getUsrInfoId() {
		return usrInfoId;
	}

	public void setUsrInfoId(Long usrInfoId) {
		this.usrInfoId = usrInfoId;
	}

	public String getUsrNo() {
		return usrNo;
	}

	public void setUsrNo(String usrNo) {
		this.usrNo = usrNo;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public String getDptCode() {
		return dptCode;
	}

	public void setDptCode(String dptCode) {
		this.dptCode = dptCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
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

	public String getTmpPwd() {
		return tmpPwd;
	}

	public void setTmpPwd(String tmpPwd) {
		this.tmpPwd = tmpPwd;
	}

	public Sys01_CmpInfoModel getCmpInfoModel() {
		return cmpInfoModel;
	}

	public void setCmpInfoModel(Sys01_CmpInfoModel cmpInfoModel) {
		this.cmpInfoModel = cmpInfoModel;
	}

	public Sys08_DptInfoModel getDptInfoModel() {
		return dptInfoModel;
	}

	public void setDptInfoModel(Sys08_DptInfoModel dptInfoModel) {
		this.dptInfoModel = dptInfoModel;
	}
}
