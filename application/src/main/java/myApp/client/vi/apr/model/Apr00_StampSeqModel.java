package myApp.client.vi.apr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Apr00_StampSeqModel implements GridDataModel {
	
	private Long apprId;
	private String stampSeq;
	private Long stampId;
	private Date apprDate; 
	private String regEmpName; 
	private String apprEmpName; 
	private String title ;
	private String stampName; 
	private String receiveName; 
	private String note;
 	
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

	public String getStampSeq() {
		return stampSeq;
	}

	public void setStampSeq(String stampSeq) {
		this.stampSeq = stampSeq;
	}

	public Long getStampId() {
		return stampId;
	}

	public void setStampId(Long stampId) {
		this.stampId = stampId;
	}

	public Date getApprDate() {
		return apprDate;
	}

	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}

	public String getRegEmpName() {
		return regEmpName;
	}

	public void setRegEmpName(String regEmpName) {
		this.regEmpName = regEmpName;
	}

	public String getApprEmpName() {
		return apprEmpName;
	}

	public void setApprEmpName(String apprEmpName) {
		this.apprEmpName = apprEmpName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStampName() {
		return stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
