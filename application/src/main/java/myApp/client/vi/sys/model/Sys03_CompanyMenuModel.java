package myApp.client.vi.sys.model;

import myApp.client.utils.GridDataModel;

public class Sys03_CompanyMenuModel implements GridDataModel {

	private Long companyMenuId; 
	private Long companyId;
	private Long menuId;
	private String useYn;
	private String note; 
	
	@Override
	public void setKeyId(Long id) {
		this.setCompanyMenuId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getCompanyMenuId();
	}

	public Long getCompanyMenuId() {
		return companyMenuId;
	}

	public void setCompanyMenuId(Long companyMenuId) {
		this.companyMenuId = companyMenuId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

}
