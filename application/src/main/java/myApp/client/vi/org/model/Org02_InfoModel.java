package myApp.client.vi.org.model;

import java.util.Date;
import java.util.List;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.dcr.model.Dcr03_OrgAuthModel;

public class Org02_InfoModel implements GridDataModel {

	private Long infoId;
	private Date modDate;
	private String modReason;
	private String modDetail;
	private Long parentCodeId;
	private Long codeId;
	private String korName;
	private String shortName;
	private String engName;
	private String levelCode;
	private String levelName;
	private String sortOrder;
	private String note;
	private String parentInfoId;

	private Dcr03_OrgAuthModel dcrOrgAuthModel = new Dcr03_OrgAuthModel();
	private List<GridDataModel> childList;

	@Override
	public void setKeyId(Long id) {
		this.setInfoId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getInfoId();
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getModReason() {
		return modReason;
	}

	public void setModReason(String modReason) {
		this.modReason = modReason;
	}

	public String getModDetail() {
		return modDetail;
	}

	public void setModDetail(String modDetail) {
		this.modDetail = modDetail;
	}

	public Long getParentCodeId() {
		return parentCodeId;
	}

	public void setParentCodeId(Long parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParentInfoId() {
		return parentInfoId;
	}

	public void setParentInfoId(String parentInfoId) {
		this.parentInfoId = parentInfoId;
	}

	public List<GridDataModel> getChildList() {
		return childList;
	}

	public void setChildList(List<GridDataModel> childList) {
		this.childList = childList;
	}

	public Dcr03_OrgAuthModel getDcrOrgAuthModel() {
		return dcrOrgAuthModel;
	}

	public void setDcrOrgAuthModel(Dcr03_OrgAuthModel dcrOrgAuthModel) {
		this.dcrOrgAuthModel = dcrOrgAuthModel;
	}

}
