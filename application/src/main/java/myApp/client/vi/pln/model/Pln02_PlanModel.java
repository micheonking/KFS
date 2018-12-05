package myApp.client.vi.pln.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd02_FundInfoModel;

public class Pln02_PlanModel implements GridDataModel {
//
	private	Long	planId;
	private	Long	fundId;
	private	Long	classTreeId;
	private	Date	startDate;
	private	Date	dueDate;
	private	Long	apprId;
	private	Long	docId;
	private	Long	empId;
	private	String	note;
	private	String	updateYn;

	private	String	closeYn;
	private String	deleteCell;

	private Fnd02_FundInfoModel fundInfoModel = new Fnd02_FundInfoModel();
	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();

	@Override
	public void setKeyId(Long id) {
		this.setPlanId(id);
	}

	@Override
	public Long getKeyId() {
		return getPlanId();
	}
	
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public Long getClassTreeId() {
		return classTreeId;
	}
	public void setClassTreeId(Long classTreeId) {
		this.classTreeId = classTreeId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}
	public Long getDocId() {
		return docId;
	}
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public Emp00_InfoModel getEmpInfoModel() {
		return empInfoModel;
	}

	public void setEmpInfoModel(Emp00_InfoModel empInfoModel) {
		this.empInfoModel = empInfoModel;
	}

	public Dcr01_ClassTreeModel getClassTreeModel() {
		return classTreeModel;
	}

	public void setClassTreeModel(Dcr01_ClassTreeModel classTreeModel) {
		this.classTreeModel = classTreeModel;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	public String getDeleteCell() {
		return deleteCell;
	}

	public void setDeleteCell(String deleteCell) {
		this.deleteCell = deleteCell;
	}

	public Fnd02_FundInfoModel getFundInfoModel() {
		return fundInfoModel;
	}

	public void setFundInfoModel(Fnd02_FundInfoModel fundInfoModel) {
		this.fundInfoModel = fundInfoModel;
	}

}
