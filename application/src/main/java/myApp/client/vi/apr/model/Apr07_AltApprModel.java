package myApp.client.vi.apr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr07_AltApprModel implements GridDataModel {
	private Long altApprId;
	private Long targetEmpId;
	private String targetEmpNo;
	private String targetEmpName;
	private String targetPositionName;
	private String targetTitleName;
	private String targetOrgName;
	private Date startDate;
	private Date closeDate;
	private Long altEmpId;
	private String altEmpNo;
	private String altEmpName;
	private String altPositionName;
	private String altTitleName;
	private String altOrgName;
	private String reasonCd;
	private String emgrPoint;
	private String note;

	private String actionCell;

	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();

	@Override
	public void setKeyId(Long id) {
		this.setAltApprId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAltApprId();
	}

	public Long getAltApprId() {
		return altApprId;
	}

	public void setAltApprId(Long altApprId) {
		this.altApprId = altApprId;
	}

	public Long getTargetEmpId() {
		return targetEmpId;
	}

	public void setTargetEmpId(Long targetEmpId) {
		this.targetEmpId = targetEmpId;
	}

	public String getTargetEmpNo() {
		return targetEmpNo;
	}

	public void setTargetEmpNo(String targetEmpNo) {
		this.targetEmpNo = targetEmpNo;
	}

	public String getTargetEmpName() {
		return targetEmpName;
	}

	public void setTargetEmpName(String targetEmpName) {
		this.targetEmpName = targetEmpName;
	}

	public String getTargetPositionName() {
		return targetPositionName;
	}

	public void setTargetPositionName(String targetPositionName) {
		this.targetPositionName = targetPositionName;
	}

	public String getTargetTitleName() {
		return targetTitleName;
	}

	public void setTargetTitleName(String targetTitleName) {
		this.targetTitleName = targetTitleName;
	}

	public String getTargetOrgName() {
		return targetOrgName;
	}

	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
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

	public Long getAltEmpId() {
		return altEmpId;
	}

	public void setAltEmpId(Long altEmpId) {
		this.altEmpId = altEmpId;
	}

	public String getAltEmpNo() {
		return altEmpNo;
	}

	public void setAltEmpNo(String altEmpNo) {
		this.altEmpNo = altEmpNo;
	}

	public String getAltEmpName() {
		return altEmpName;
	}

	public void setAltEmpName(String altEmpName) {
		this.altEmpName = altEmpName;
	}

	public String getAltPositionName() {
		return altPositionName;
	}

	public void setAltPositionName(String altPositionName) {
		this.altPositionName = altPositionName;
	}

	public String getAltTitleName() {
		return altTitleName;
	}

	public void setAltTitleName(String altTitleName) {
		this.altTitleName = altTitleName;
	}

	public String getAltOrgName() {
		return altOrgName;
	}

	public void setAltOrgName(String altOrgName) {
		this.altOrgName = altOrgName;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getEmgrPoint() {
		return emgrPoint;
	}

	public void setEmgrPoint(String emgrPoint) {
		this.emgrPoint = emgrPoint;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getActionCell() {
		return actionCell;
	}

	public void setActionCell(String actionCell) {
		this.actionCell = actionCell;
	}

	public Emp00_InfoModel getEmpInfoModel() {
		return empInfoModel;
	}

	public void setEmpInfoModel(Emp00_InfoModel empInfoModel) {
		this.empInfoModel = empInfoModel;
	}

	

}
