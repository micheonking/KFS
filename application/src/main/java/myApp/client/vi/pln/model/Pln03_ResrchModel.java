package myApp.client.vi.pln.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Pln03_ResrchModel implements GridDataModel {

	private Long    resrchId;
	private Long    classTreeId;
	private Long    issuingEntityId;
	private String  visitReason;
	private Long    regEmpId;
	private Date    regDate;
	private Date	closeDate;
	private String	closeYn;
	private Date    resrchStartDate;
	private Date    resrchCloseDate;
	private Long    cfrmEmpId;
	private Date    cfrmDate;
	private String  note;
	private String	targetTypeCode;
	
	private String	classTreeNm;
	private String	parentTreeNm;
	private String	issuingEntityNm;
	private String	docYn;
	private String	aprYn;
	private String	regEmpNm;
	private String	cfrmEmpNm;
	private String	targetTypeName;
	
	@Override
	public void setKeyId(Long id) {
		this.setResrchId(id);
	}

	@Override
	public Long getKeyId() {
		return getResrchId();
	}

	public Long getResrchId() {
		return resrchId;
	}
	public void setResrchId(Long resrchId) {
		this.resrchId = resrchId;
	}
	public Long getClassTreeId() {
		return classTreeId;
	}
	public void setClassTreeId(Long classTreeId) {
		this.classTreeId = classTreeId;
	}
	public Long getIssuingEntityId() {
		return issuingEntityId;
	}
	public void setIssuingEntityId(Long issuingEntityId) {
		this.issuingEntityId = issuingEntityId;
	}
	public String getVisitReason() {
		return visitReason;
	}
	public void setVisitReason(String visitReason) {
		this.visitReason = visitReason;
	}
	public Long getRegEmpId() {
		return regEmpId;
	}
	public void setRegEmpId(Long regEmpId) {
		this.regEmpId = regEmpId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	public Date getResrchStartDate() {
		return resrchStartDate;
	}

	public void setResrchStartDate(Date resrchStartDate) {
		this.resrchStartDate = resrchStartDate;
	}

	public Date getResrchCloseDate() {
		return resrchCloseDate;
	}

	public void setResrchCloseDate(Date resrchCloseDate) {
		this.resrchCloseDate = resrchCloseDate;
	}
	public Long getCfrmEmpId() {
		return cfrmEmpId;
	}
	public void setCfrmEmpId(Long cfrmEmpId) {
		this.cfrmEmpId = cfrmEmpId;
	}
	public Date getCfrmDate() {
		return cfrmDate;
	}
	public void setCfrmDate(Date cfrmDate) {
		this.cfrmDate = cfrmDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getTargetTypeCode() {
		return targetTypeCode;
	}

	public void setTargetTypeCode(String targetTypeCode) {
		this.targetTypeCode = targetTypeCode;
	}

	public String getClassTreeNm() {
		return classTreeNm;
	}
	public void setClassTreeNm(String classTreeNm) {
		this.classTreeNm = classTreeNm;
	}
	
	public String getParentTreeNm() {
		return parentTreeNm;
	}

	public void setParentTreeNm(String parentTreeNm) {
		this.parentTreeNm = parentTreeNm;
	}

	public String getIssuingEntityNm() {
		return issuingEntityNm;
	}
	public void setIssuingEntityNm(String issuingEntityNm) {
		this.issuingEntityNm = issuingEntityNm;
	}
	public String getDocYn() {
		return docYn;
	}
	public void setDocYn(String docYn) {
		this.docYn = docYn;
	}
	public String getAprYn() {
		return aprYn;
	}
	public void setAprYn(String aprYn) {
		this.aprYn = aprYn;
	}

	public String getRegEmpNm() {
		return regEmpNm;
	}
	public void setRegEmpNm(String regEmpNm) {
		this.regEmpNm = regEmpNm;
	}
	public String getCfrmEmpNm() {
		return cfrmEmpNm;
	}
	public void setCfrmEmpNm(String cfrmEmpNm) {
		this.cfrmEmpNm = cfrmEmpNm;
	}

	public String getTargetTypeName() {
		return targetTypeName;
	}

	public void setTargetTypeName(String targetTypeName) {
		this.targetTypeName = targetTypeName;
	}
	
}
