package myApp.client.vi.emp.model;

import java.util.Date;

import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.vi.org.model.Org02_InfoModel;
import myApp.client.utils.GridDataModel;

public class Emp03_TransModel implements GridDataModel {
	private Long 	transId;
	private Long 	empId;
	private Date 	transDate;
	private String 	transCode;
	private String 	transName;
	private Long 	orgCodeId;
	private String 	posCode;
	private String 	posName;
	private String 	titleCode;
	private String 	titleName;
	private String 	gradeCode;
	private String 	gradeName;
	private String 	transReason;
	private String 	transNote;

	private Date 	addTitleCloseDate;

	private String 	actionCell;

	private Org01_CodeModel orgCodeModel = new Org01_CodeModel();
	private Org02_InfoModel orgInfoModel = new Org02_InfoModel();

	@Override
	public void setKeyId(Long id) {
		this.setTransId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getTransId();
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public Long getOrgCodeId() {
		return orgCodeId;
	}

	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getTransReason() {
		return transReason;
	}

	public void setTransReason(String transReason) {
		this.transReason = transReason;
	}

	public String getTransNote() {
		return transNote;
	}

	public void setTransNote(String transNote) {
		this.transNote = transNote;
	}

	public String getActionCell() {
		return actionCell;
	}

	public void setActionCell(String actionCell) {
		this.actionCell = actionCell;
	}

	public Org01_CodeModel getOrgCodeModel() {
		return orgCodeModel;
	}

	public void setOrgCodeModel(Org01_CodeModel orgCodeModel) {
		this.orgCodeModel = orgCodeModel;
	}

	public Org02_InfoModel getOrgInfoModel() {
		return orgInfoModel;
	}

	public void setOrgInfoModel(Org02_InfoModel orgInfoModel) {
		this.orgInfoModel = orgInfoModel;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getAddTitleCloseDate() {
		return addTitleCloseDate;
	}

	public void setAddTitleCloseDate(Date addTitleCloseDate) {
		this.addTitleCloseDate = addTitleCloseDate;
	}
	
}
