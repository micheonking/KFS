package myApp.client.vi.apr.model;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;

public class Apr03_RelateFundModel implements GridDataModel {
	private Long relateFundId;
	private Long apprId;
	private Long fundId;
	private String deleteCell;

	private Fnd00_FundModel fundModel = new Fnd00_FundModel();
	private Apr01_ApprModel apprModel = new Apr01_ApprModel();
	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
	private Apr10_StampModel stampModel = new Apr10_StampModel();
	
	@Override
	public void setKeyId(Long id) {
		this.setRelateFundId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRelateFundId();
	}

	public Long getRelateFundId() {
		return relateFundId;
	}

	public void setRelateFundId(Long relateFundId) {
		this.relateFundId = relateFundId;
	}

	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getDeleteCell() {
		return deleteCell;
	}

	public void setDeleteCell(String deleteCell) {
		this.deleteCell = deleteCell;
	}

	public Fnd00_FundModel getFundModel() {
		return fundModel;
	}

	public void setFundModel(Fnd00_FundModel fundModel) {
		this.fundModel = fundModel;
	}

	public Apr01_ApprModel getApprModel() {
		return apprModel;
	}

	public void setApprModel(Apr01_ApprModel apprModel) {
		this.apprModel = apprModel;
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

	public Apr10_StampModel getStampModel() {
		return stampModel;
	}

	public void setStampModel(Apr10_StampModel stampModel) {
		this.stampModel = stampModel;
	}
}
