package myApp.client.vi.dcr.model;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;

public class Dcr04_SearchDocModel implements GridDataModel {
	private Long seqId;

	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
//	private Dcr04_DocModel docModel = new Dcr04_DocModel();
	private Fnd00_FundModel fundModel = new Fnd00_FundModel();

	@Override
	public void setKeyId(Long id) {
		this.setSeqId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getSeqId();
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Dcr01_ClassTreeModel getClassTreeModel() {
		return classTreeModel;
	}

	public void setClassTreeModel(Dcr01_ClassTreeModel classTreeModel) {
		this.classTreeModel = classTreeModel;
	}

//	public Dcr04_DocModel getDocModel() {
//		return docModel;
//	}
//
//	public void setDocModel(Dcr04_DocModel docModel) {
//		this.docModel = docModel;
//	}

	public Fnd00_FundModel getFundModel() {
		return fundModel;
	}

	public void setFundModel(Fnd00_FundModel fundModel) {
		this.fundModel = fundModel;
	}

}
