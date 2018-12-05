package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;

public class Sys10_FileModel implements GridDataModel {

	private Long fileId;
	private Long parentId;
	private String fileName;
	// private String fullFileName;
	private Date regDate = new Date();
	private String serverPath;
	private Double size;
	private Date delDate;
	private String note;
	private String ext;
	private String titleYn;
	private Apr01_ApprModel apprModel = new Apr01_ApprModel();

	private String downloadCell;
	private String deleteCell;

	@Override
	public void setKeyId(Long id) {
		this.setFileId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getFileId();
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// public String getFullFileName() {
	// return fullFileName;
	// }
	//
	// public void setFullFileName(String fullFileName) {
	// this.fullFileName = fullFileName;
	// }

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDownloadCell() {
		return "downloadCell";
	}

	public String getDeleteCell() {
		return "deleteCell";
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getTitleYn() {
		return titleYn;
	}

	public void setTitleYn(String titleYn) {
		this.titleYn = titleYn;
	}

	public void setDownloadCell(String downloadCell) {
		this.downloadCell = downloadCell;
	}

	public void setDeleteCell(String deleteCell) {
		this.deleteCell = deleteCell;
	}

	public Apr01_ApprModel getApprModel() {
		return apprModel;
	}

	public void setApprModel(Apr01_ApprModel apprModel) {
		this.apprModel = apprModel;
	}

}
