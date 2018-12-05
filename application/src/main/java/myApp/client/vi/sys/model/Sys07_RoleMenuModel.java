package myApp.client.vi.sys.model;

import myApp.client.utils.GridDataModel;

public class Sys07_RoleMenuModel implements GridDataModel {
	
	// sys07_role_menu columns...
	private Long 	roleMenuId; 
	private Long 	roleId; 
	private Long 	menuId;
	private String 	useYn; 
	private String 	note; 
	
	@Override
	public void setKeyId(Long id) {
		this.setRoleMenuId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRoleMenuId();
	}

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
