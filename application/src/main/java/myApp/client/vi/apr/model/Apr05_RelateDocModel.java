package myApp.client.vi.apr.model;

import myApp.client.utils.GridDataModel;
//import myApp.client.vi.dcr.model.Dcr04_DocModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.sys.model.Sys10_FileModel;

public class Apr05_RelateDocModel implements GridDataModel {
	private Long relateDocId;
	private Long apprId;
	private Long fileId;
	private String gubun;
	private String docTypeName;
	private String regEmpInfo;

	private Emp00_InfoModel empModel = new Emp00_InfoModel();
	private Apr01_ApprModel aprModel = new Apr01_ApprModel();
	private Sys10_FileModel fileModel = new Sys10_FileModel();
	private Fnd00_FundModel fundModel = new Fnd00_FundModel();

	private String actionCell;

	@Override
	public void setKeyId(Long id) {
		this.setRelateDocId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRelateDocId();
	}

	public Long getRelateDocId() {
		return relateDocId;
	}

	public void setRelateDocId(Long relateDocId) {
		this.relateDocId = relateDocId;
	}

	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getDocTypeName() {
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	public String getRegEmpInfo() {
//		return this.empModel.getPositionName() + " " + this.empModel.getKorName();
		return this.empModel.getTitleName() + " " + this.empModel.getKorName();
	}

	public void setRegEmpInfo(String regEmpInfo) {
		this.regEmpInfo = regEmpInfo;
	}

	public Apr01_ApprModel getAprModel() {
		return aprModel;
	}

	public void setAprModel(Apr01_ApprModel aprModel) {
		this.aprModel = aprModel;
	}

	public String getActionCell() {
		return actionCell;
	}

	public void setActionCell(String actionCell) {
		this.actionCell = actionCell;
	}

	public Emp00_InfoModel getEmpModel() {
		return empModel;
	}

	public void setEmpModel(Emp00_InfoModel empModel) {
		this.empModel = empModel;
	}

	public Sys10_FileModel getFileModel() {
		return fileModel;
	}

	public void setFileModel(Sys10_FileModel fileModel) {
		this.fileModel = fileModel;
	}

	public Fnd00_FundModel getFundModel() {
		return fundModel;
	}

	public void setFundModel(Fnd00_FundModel fundModel) {
		this.fundModel = fundModel;
	}

}
