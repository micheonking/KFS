package myApp.client.vi.sys.model;

import myApp.client.utils.GridDataModel;

public class Sys05_UserRoleModel implements GridDataModel {

	private Long userRoleId;
	private Long userId;
	private Long roleId;
	private String seq;
	private String note; 
	
	@Override
	public void setKeyId(Long id) {
		this.setUserRoleId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getUserRoleId();
	}
	
	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}
