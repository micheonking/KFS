package myApp.client.vi.opr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Opr03_RegModel implements GridDataModel {

	private Long	regId;
	private Long	createId;
	private Long	regEmpId;
	private String	seq;
	private String	docNm;
	private Date	regDate;
	private String	closeYn;
	private Boolean closeYnFlag;
	private String	closeYnNm;
	private String	note;
	
	private String	uploadYn;
	private Long	fileId;

	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();
	
	@Override
	public void setKeyId(Long id) {
		this.setRegId(id);
	}

	@Override
	public Long getKeyId() {
		return getRegId();
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getRegEmpId() {
		return regEmpId;
	}

	public void setRegEmpId(Long regEmpId) {
		this.regEmpId = regEmpId;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getDocNm() {
		return docNm;
	}

	public void setDocNm(String docNm) {
		this.docNm = docNm;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	public Boolean getCloseYnFlag() {
		this.closeYnFlag = "true".equals(this.closeYn);
		return closeYnFlag; 
	}

	public void setCloseYnFlag(Boolean closeYnFlag) {
		this.closeYnFlag = closeYnFlag;
		this.closeYn     = closeYnFlag.toString();
	}
	
	public String getCloseYnNm() {
		
		if ("true".equals(this.closeYn)) {
			this.closeYnNm = "완료";
		} else {
			this.closeYnNm = null;
		}
		return closeYnNm;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUploadYn() {
		return uploadYn;
	}

	public void setUploadYn(String uploadYn) {
		this.uploadYn = uploadYn;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Emp00_InfoModel getEmpInfoModel() {
		return empInfoModel;
	}

	public void setEmpInfoModel(Emp00_InfoModel empInfoModel) {
		this.empInfoModel = empInfoModel;
	}

}
