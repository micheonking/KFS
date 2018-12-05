package myApp.client.vi.emp.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Emp04_AddTitleModel implements GridDataModel {

	private Long addTitleId;
	private Long orgCodeId;
	private String orgName;
	private Long empId;
	private Date startDate;
	private Date closeDate;
	private String titleCode;
	private String titleName;
	private Long reason;
	private String note;

	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();

	@Override
	public void setKeyId(Long id) {
		this.setAddTitleId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAddTitleId();
	}

	public Long getAddTitleId() {
		return addTitleId;
	}

	public void setAddTitleId(Long addTitleId) {
		this.addTitleId = addTitleId;
	}

	public Long getOrgCodeId() {
		return orgCodeId;
	}

	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public Long getReason() {
		return reason;
	}

	public void setReason(Long reason) {
		this.reason = reason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Emp00_InfoModel getEmpInfoModel() {
		return empInfoModel;
	}

	public void setEmpInfoModel(Emp00_InfoModel empInfoModel) {
		this.empInfoModel = empInfoModel;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

}
