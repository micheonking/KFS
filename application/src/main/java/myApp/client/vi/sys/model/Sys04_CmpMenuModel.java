package myApp.client.vi.sys.model;

import java.util.Date;
import java.util.List;

import myApp.client.utils.GridDataModel;

public class Sys04_CmpMenuModel implements GridDataModel { 

	private Long    cmpMenuId ;
	private String  cmpCode ;
	private Long    menuId ;
	private String  menuName ;
	private Long    prntId ;
	private Long    seq ;
	private String  useYn ;
	private String  rmk ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;

	private String	seqStr;
	private boolean useYnFlag;
	private Sys03_MenuModel menuModel = new Sys03_MenuModel();
	private List<GridDataModel> childList;

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

	public Long getPrntId() {
		return prntId;
	}

	public void setPrntId(Long prntId) {
		this.prntId = prntId;
	}
	
	public Sys03_MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(Sys03_MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public boolean getUseYnFlag() {
		if("Y".equals(getUseYn())) {
			return true;
		} else {
			return false;
		}
	}

	public void setUseYnFlag(boolean useYnFlag) {
		if(useYnFlag) {
			this.useYn = "Y";
		} else {
			this.useYn = "N";
		}
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public List<GridDataModel> getChildList() {
		return childList;
	}

	public void setChildList(List<GridDataModel> childList) {
		this.childList = childList;
	}

	public String getSeqStr() {
		if(getSeq() == null) {
			return "";
		} else {
			return ""+getSeq();
		}
	}

	public void setSeqStr(String seqStr) {
		this.seq = Long.parseLong(seqStr);
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
}
