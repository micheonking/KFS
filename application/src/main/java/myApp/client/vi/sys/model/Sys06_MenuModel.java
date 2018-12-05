package myApp.client.vi.sys.model;

import java.util.List;

import myApp.client.utils.GridDataModel;
import myApp.client.utils.TreeGridDataModel;

public class Sys06_MenuModel implements TreeGridDataModel {
	private Long 	menuId;
	private Long 	parentId ;
	private String 	useYn = "true";
	private String 	menuName; 
	private String 	className;
	private String 	seq;
	private String 	note;
	private String	editCell; 
	private String	moveCell;
	private boolean roleMenuYn; 
	
	private List<GridDataModel> childList;
	private Sys07_RoleMenuModel roleMenuModel = new Sys07_RoleMenuModel(); 
	
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Sys06_MenuModel(){
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public List<GridDataModel> getChildList() {
		return childList;
	}
	
	public void setChildList(List<GridDataModel> childList) {
		this.childList = childList;
	}

	@Override
	public void setKeyId(Long id) {
		this.setMenuId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMenuId();
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getEditCell() {
		return editCell;
	}

	public void setEditCell(String editCell) {
		this.editCell = editCell;
	}

	public Boolean getUseYnFlag() {
		return "true".equals(useYn) ; 
	} 
	public void setUseYnFlag(Boolean useYnFlag) {
		this.useYn = useYnFlag.toString();
	}

	public String getMoveCell() {
		return moveCell;
	}

	public void setMoveCell(String moveCell) {
		this.moveCell = moveCell;
	}

	@Override
	public Long getParentKeyId() {
		return this.getParentId();
	}
	
	public Sys07_RoleMenuModel getRoleMenuModel() {
		return roleMenuModel;
	}

	public void setRoleMenuModel(Sys07_RoleMenuModel roleMenuModel) {
		this.roleMenuModel = roleMenuModel;
	}

	public boolean getRoleMenuYn() {
		return roleMenuYn;
	}

	public void setRoleMenuYn(boolean roleMenuYn) {
		this.roleMenuYn = roleMenuYn;
	}


}
