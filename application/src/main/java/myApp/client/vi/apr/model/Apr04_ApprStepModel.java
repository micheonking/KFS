package myApp.client.vi.apr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr04_ApprStepModel implements GridDataModel {
	private Long apprStepId;
	private Long apprId;
	private String stepTypeCode;
	private String stepTypeName;
	private String seq;
	private String stepStatusCode;
	private String stepStatusName;
	private Date apprDate;
	private String note;
	private String rejectReason;
	private Long stepEmpId;
	private Long apprEmpId;
	private String stepApprName;

	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();

	private String actionCell;

	@Override
	public void setKeyId(Long id) {
		this.setApprStepId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getApprStepId();
	}

	public Long getApprStepId() {
		return apprStepId;
	}

	public void setApprStepId(Long apprStepId) {
		this.apprStepId = apprStepId;
	}

	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}

	public String getStepTypeCode() {
		return stepTypeCode;
	}

	public void setStepTypeCode(String stepTypeCode) {
		this.stepTypeCode = stepTypeCode;
	}

	public String getStepTypeName() {
		return stepTypeName;
	}

	public void setStepTypeName(String stepTypeName) {
		this.stepTypeName = stepTypeName;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getStepStatusCode() {
		return stepStatusCode;
	}

	public void setStepStatusCode(String stepStatusCode) {
		this.stepStatusCode = stepStatusCode;
	}

	public String getStepStatusName() {
		return stepStatusName;
	}

	public void setStepStatusName(String stepStatusName) {
		this.stepStatusName = stepStatusName;
	}

	public Date getApprDate() {
		return apprDate;
	}

	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Long getStepEmpId() {
		return stepEmpId;
	}

	public void setStepEmpId(Long stepEmpId) {
		this.stepEmpId = stepEmpId;
	}

	public Long getApprEmpId() {
		return apprEmpId;
	}

	public void setApprEmpId(Long apprEmpId) {
		this.apprEmpId = apprEmpId;
	}

	public String getStepApprName() {
		//return this.empInfoModel.getKorName() + " " + this.empInfoModel.getPositionName() ;
		return stepApprName ;
	}

	public void setStepApprName(String stepApprName) {
		this.stepApprName = stepApprName;
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
