package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp09_MgrRuleModel;

public class Sys09_CodeModel implements GridDataModel {
	private Long codeId;
	private Long companyId;
	private Long codeKindId;
	private String code;
	private String name;
	private Date applyDate;
	private Date closeDate;

	private Date lastDate;
	private String closeYn;

	private String seq;
	private String note;

	private Emp09_MgrRuleModel mgrRuleModel = new Emp09_MgrRuleModel();

	@Override
	public void setKeyId(Long id) {
		this.setCodeId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getCodeId();
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getCodeKindId() {
		return codeKindId;
	}

	public void setCodeKindId(Long codeKindId) {
		this.codeKindId = codeKindId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getCloseDate() {
		// 이후 코드가 등록되어 있으면 이후 등록된 코드 적용일 -1 을 넘겨준다.
		if (this.closeDate != null) {
			return this.closeDate;
		} else {
			return this.lastDate;
		}
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Emp09_MgrRuleModel getMgrRuleModel() {
		return mgrRuleModel;
	}

	public void setMgrRuleModel(Emp09_MgrRuleModel mgrRuleModel) {
		this.mgrRuleModel = mgrRuleModel;
	}

}
