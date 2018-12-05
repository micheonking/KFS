package myApp.client.vi.apr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.sys.model.Sys10_FileModel;

public class Apr01_ApprModel implements GridDataModel {
	private Long apprId;
	private String title;
	private Date regDate;
	private String statusCode;
	private String statusName;
	private String content;
	private String note;
	private Long regEmpId;
	private String regEmpNm;
	private Date effectDate;
	private Long classTreeId;
	private Long stampId;
	private String stampName;
	private Long stampSeq;
	private String receiveName;
	private String referenceName;

	private String NextApprName;

	private String actionCell;

	private String recentApprovalName;
	private Date recentApprovalDate;
	private Date myApprovalDate;
	private String docTypeName;
	private String classTreeNameAppPath;
	private String inOutTypeCode;
	
	private String mailYn;
	
	private String fileName;

	private String apprButton;
	private String rejectButton;
	private String pdfButton;

	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();
	private Fnd00_FundModel fundModel = new Fnd00_FundModel();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
	private Apr10_StampModel stampModel = new Apr10_StampModel();

	@Override
	public void setKeyId(Long id) {
		this.setApprId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getApprId();
	}

	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getRegEmpId() {
		return regEmpId;
	}

	public void setRegEmpId(Long regEmpId) {
		this.regEmpId = regEmpId;
	}

	public String getRegEmpNm() {
		return regEmpNm;
	}

	public void setRegEmpNm(String regEmpNm) {
		this.regEmpNm = regEmpNm;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
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

	public String getRecentApprovalName() {
		return recentApprovalName;
	}

	public void setRecentApprovalName(String recentApprovalName) {
		this.recentApprovalName = recentApprovalName;
	}

	public Date getRecentApprovalDate() {
		return recentApprovalDate;
	}

	public void setRecentApprovalDate(Date recentApprovalDate) {
		this.recentApprovalDate = recentApprovalDate;
	}

	public Date getMyApprovalDate() {
		return myApprovalDate;
	}

	public void setMyApprovalDate(Date myApprovalDate) {
		this.myApprovalDate = myApprovalDate;
	}

	public String getDocTypeName() {
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	public String getApprButton() {
		return apprButton;
	}

	public void setApprButton(String apprButton) {
		this.apprButton = apprButton;
	}

	public String getRejectButton() {
		return rejectButton;
	}

	public void setRejectButton(String rejectButton) {
		this.rejectButton = rejectButton;
	}

	public String getNextApprName() {
		return NextApprName;
	}

	public void setNextApprName(String nextApprName) {
		NextApprName = nextApprName;
	}
	
	public String getClassTreeNameAppPath() {
		return classTreeNameAppPath;
	}

	public void setClassTreeNameAppPath(String classTreeNameAppPath) {
		this.classTreeNameAppPath = classTreeNameAppPath;
	}

	public Long getClassTreeId() {
		return classTreeId;
	}

	public void setClassTreeId(Long classTreeId) {
		this.classTreeId = classTreeId;
	}

	public Fnd00_FundModel getFundModel() {
		return fundModel;
	}

	public void setFundModel(Fnd00_FundModel fundModel) {
		this.fundModel = fundModel;
	}

	public Dcr01_ClassTreeModel getClassTreeModel() {
		return classTreeModel;
	}

	public void setClassTreeModel(Dcr01_ClassTreeModel classTreeModel) {
		this.classTreeModel = classTreeModel;
	}

	public String getRegEmpInfo() {
		return this.getEmpInfoModel().getKorName() + "(" + this.getEmpInfoModel().getOrgName() + ")";
	}

	public Long getStampId() {
		return stampId;
	}

	public void setStampId(Long stampId) {
		this.stampId = stampId;
	}

	public String getStampName() {
		return stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public Long getStampSeq() {
		return stampSeq;
	}

	public void setStampSeq(Long stampSeq) {
		this.stampSeq = stampSeq;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public Apr10_StampModel getStampModel() {
		return stampModel;
	}

	public void setStampModel(Apr10_StampModel stampModel) {
		this.stampModel = stampModel;
	}

	public String getMailYn() {
		return mailYn;
	}

	public void setMailYn(String mailYn) {
		this.mailYn = mailYn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInOutTypeCode() {
		return inOutTypeCode;
	}

	public void setInOutTypeCode(String inOutTypeCode) {
		this.inOutTypeCode = inOutTypeCode;
	}

	public String getPdfButton() {
		return pdfButton;
	}

	public void setPdfButton(String pdfButton) {
		this.pdfButton = pdfButton;
	}

}
