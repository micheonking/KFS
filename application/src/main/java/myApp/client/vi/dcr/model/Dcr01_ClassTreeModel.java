package myApp.client.vi.dcr.model;

import java.util.ArrayList;
import java.util.List;

import myApp.client.utils.GridDataModel;

public class Dcr01_ClassTreeModel implements GridDataModel {

	private Long classTreeId;
	private Long companyId;
	private String classTreeCode;
	private String classTreeName;
	private Long parentTreeId;
	private String typeCode;
	private String approvalTypeCode;
	private String approvalTypeName;
	private String itemTypeCode;
	private String itemTypeName;
	private Long sealCode;
	private String sealName;
	private String useYn;
	private String seq;
	private String note;
	
	private Long level;
	
	private String actionCell;

	private String researchYn; // 리서치
	private Boolean researchYnCheck;
	private String advertYn; // 광고심사대상.
	private Boolean advertYnCheck;

	private Dcr03_OrgAuthModel orgAuthModel = new Dcr03_OrgAuthModel();
	private List<GridDataModel> childList = new ArrayList<GridDataModel>();

	@Override
	public void setKeyId(Long id) {
		this.setClassTreeId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getClassTreeId();
	}

	public Long getClassTreeId() {
		return classTreeId;
	}

	public void setClassTreeId(Long classTreeId) {
		this.classTreeId = classTreeId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getClassTreeCode() {
		return classTreeCode;
	}

	public void setClassTreeCode(String classTreeCode) {
		this.classTreeCode = classTreeCode;
	}

	public String getClassTreeName() {
		return classTreeName;
	}

	public void setClassTreeName(String classTreeName) {
		this.classTreeName = classTreeName;
	}

	public Long getParentTreeId() {
		return parentTreeId;
	}

	public void setParentTreeId(Long parentTreeId) {
		this.parentTreeId = parentTreeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getApprovalTypeCode() {
		return approvalTypeCode;
	}

	public void setApprovalTypeCode(String approvalTypeCode) {
		this.approvalTypeCode = approvalTypeCode;
	}

	public String getApprovalTypeName() {
		return approvalTypeName;
	}

	public void setApprovalTypeName(String approvalTypeName) {
		this.approvalTypeName = approvalTypeName;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public Long getSealCode() {
		return sealCode;
	}

	public void setSealCode(Long sealCode) {
		this.sealCode = sealCode;
	}

	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Dcr03_OrgAuthModel getOrgAuthModel() {
		return orgAuthModel;
	}

	public void setOrgAuthModel(Dcr03_OrgAuthModel orgAuthModel) {
		this.orgAuthModel = orgAuthModel;
	}

	public String getResearchYn() {
		return researchYn;
	}

	public void setResearchYn(String researchYn) {
		this.researchYn = researchYn;
	}

	public Boolean getResearchYnCheck() {
		return "true".equals(this.researchYn);
		//return researchYnCheck;
	}

	public void setResearchYnCheck(Boolean researchYnCheck) {
		this.researchYnCheck = researchYnCheck;
		this.setResearchYn(researchYnCheck.toString());
	}

	public String getAdvertYn() {
		return advertYn;
	}

	public void setAdvertYn(String advertYn) {
		this.advertYn = advertYn;
	}

	public Boolean getAdvertYnCheck() {
		return "true".equals(this.advertYn);
		// return advertYnCheck;
	}

	public void setAdvertYnCheck(Boolean advertYnCheck) {
		this.advertYnCheck = advertYnCheck;
		this.setAdvertYn(advertYnCheck.toString());
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

}
