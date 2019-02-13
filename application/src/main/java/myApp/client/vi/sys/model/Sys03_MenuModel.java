package myApp.client.vi.sys.model;

import java.util.Date;
import java.util.List;

import myApp.client.utils.GridDataModel;

public class Sys03_MenuModel implements GridDataModel { 

	private Long    menuId ;
	private String  menuName ;
	private Long    seq ;
	private Long    prntId ;
	private String  useYn ;
	private String  rmk ;
	private String  className ;
	private String  insUsrNo ;
	private Date    insDate ;
	private String  updUsrNo ;
	private Date    updDate ;

	private boolean roleMenuYn;
	private List<GridDataModel> childList;
	private Sys06_RoleMenuModel roleMenuModel = new Sys06_RoleMenuModel(); 

	@Override
	public void setKeyId(Long id) {
		this.menuId = id;
	}

	@Override
	public Long getKeyId() {
		return this.getMenuId(); 
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getPrntId() {
		return prntId;
	}

	public void setPrntId(Long prntId) {
		this.prntId = prntId;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public List<GridDataModel> getChildList() {
		return childList;
	}

	public void setChildList(List<GridDataModel> childList) {
		this.childList = childList;
	}

	public Sys06_RoleMenuModel getRoleMenuModel() {
		return roleMenuModel;
	}

	public void setRoleMenuModel(Sys06_RoleMenuModel roleMenuModel) {
		this.roleMenuModel = roleMenuModel;
	}
	
	public Boolean getUseYnFlag() {
		return "Y".equals(useYn) ; 
	} 

	public void setUseYnFlag(Boolean useYnFlag) {
		String useYn;
		if (useYnFlag) {
			this.useYn = "Y" ;
		} else {
			this.useYn = "N" ;
		}
	}

	public boolean getRoleMenuYn() {
		return roleMenuYn;
	}

	public void setRoleMenuYn(boolean roleMenuYn) {
		this.roleMenuYn = roleMenuYn;
	}

}
