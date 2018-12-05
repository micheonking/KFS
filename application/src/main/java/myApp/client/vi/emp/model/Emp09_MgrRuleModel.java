package myApp.client.vi.emp.model;

import myApp.client.utils.GridDataModel;

public class Emp09_MgrRuleModel implements GridDataModel {
	private Long mgrRuleId;
	private Long companyId;
	private String titleCd;
	private Boolean useYn;
	private String note;
	private String seq;

	@Override
	public void setKeyId(Long id) {
		this.setMgrRuleId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMgrRuleId();
	}

	public Long getMgrRuleId() {
		return mgrRuleId;
	}

	public void setMgrRuleId(Long mgrRuleId) {
		this.mgrRuleId = mgrRuleId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTitleCd() {
		return titleCd;
	}

	public void setTitleCd(String titleCd) {
		this.titleCd = titleCd;
	}

	public Boolean getUseYn() {
		return useYn;
	}

	public void setUseYn(Boolean useYn) {
		this.useYn = useYn;
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
