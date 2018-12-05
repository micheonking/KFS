package myApp.client.vi.org.model;

import java.util.Date;
import java.util.List;

import myApp.client.utils.GridDataModel;
import myApp.client.utils.TreeGridDataModel;
import myApp.client.vi.fnd.model.Fnd08_OrgAuthModel;

public class Org01_CodeModel implements TreeGridDataModel {

	private Long codeId;
	private Long companyId;
	private String orgCode;
	private Date openDate;
	private Date closeDate;
	private String openReason;
	private String closeReason;
	private String actionCell;
	private String moveCell;

	private Org02_InfoModel orgInfoModel = new Org02_InfoModel();
	private Fnd08_OrgAuthModel orgAuthModel = new Fnd08_OrgAuthModel();
	private List<GridDataModel> childList;

	@Override
	public Long getParentKeyId() {
		return this.getOrgInfoModel().getParentCodeId();
	}

	@Override
	public void setKeyId(Long id) {
		this.setCodeId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getCodeId();
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getOpenReason() {
		return openReason;
	}

	public void setOpenReason(String openReason) {
		this.openReason = openReason;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	// public String getNote() {
	// return note;
	// }
	//
	// public void setNote(String note) {
	// this.note = note;
	// }

	public Org02_InfoModel getOrgInfoModel() {
		return orgInfoModel;
	}

	public void setOrgInfoModel(Org02_InfoModel orgInfoModel) {
		this.orgInfoModel = orgInfoModel;
	}

	public String getActionCell() {
		return actionCell;
	}

	public void setActionCell(String actionCell) {
		this.actionCell = actionCell;
	}

	public List<GridDataModel> getChildList() {
		return childList;
	}

	public void setChildList(List<GridDataModel> childList) {
		this.childList = childList;
	}

	public String getMoveCell() {
		return moveCell;
	}

	public void setMoveCell(String moveCell) {
		this.moveCell = moveCell;
	}

	public Fnd08_OrgAuthModel getOrgAuthModel() {
		return orgAuthModel;
	}

	public void setOrgAuthModel(Fnd08_OrgAuthModel orgAuthModel) {
		this.orgAuthModel = orgAuthModel;
	}

}
