package myApp.client.vi.sys.model;

import myApp.client.utils.GridDataModel;

public class Sys04_RoleModel implements GridDataModel {
	private Long 	roleId;
	private Long 	companyId;
	private String 	roleName ;
	private String 	seq; 
	private String 	note;
	private Boolean userRoleYn = false ; 
	private Sys05_UserRoleModel userRoleModel = new Sys05_UserRoleModel();
	
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public void setKeyId(Long id) {
		this.setRoleId(id);
	}
	@Override
	public Long getKeyId() {
		return this.getRoleId(); 
	}

	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Sys05_UserRoleModel getUserRoleModel() {
		return userRoleModel;
	}
	public void setUserRoleModel(Sys05_UserRoleModel userRoleModel) {
		this.userRoleModel = userRoleModel;
	}
	
	public Boolean getUserRoleYn() {
		return this.userRoleYn; 
	}
	
	public void setUserRoleYn(Boolean userRoleYn ) {
		this.userRoleYn = userRoleYn; 
	}
}
