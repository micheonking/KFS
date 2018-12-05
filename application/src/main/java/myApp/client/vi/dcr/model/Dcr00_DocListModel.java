package myApp.client.vi.dcr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;

public class Dcr00_DocListModel implements GridDataModel {

	private Long docId;

	private String classTreeName;
	private Long parentTreeId;
	private String fundTypeName;
	private String fundName;
	private String fundCode;
	private String fileName;
	private Date regDate;
	private Long regEmpId;
	private String empName;
	private String fundClose;
	private String publicSubscribe;
	private String sintak;
	private String fundType;
	private String fundStartDate;
	private String fundEndDate;
	private String aprTitle;
	private String orgName;
	private String parentTreeName;

	private Long apprId;
	private Long classTreeId;

	private Apr01_ApprModel apprModel = new Apr01_ApprModel();

	@Override
	public void setKeyId(Long id) {
		// System.out.println("장진희 : " + id);
		this.setDocId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getDocId();
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

	public String getFundTypeName() {
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getRegEmpId() {
		return regEmpId;
	}

	public void setRegEmpId(Long regEmpId) {
		this.regEmpId = regEmpId;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getFundClose() {
		return fundClose;
	}

	public void setFundClose(String fundClose) {
		this.fundClose = fundClose;
	}

	public String getPublicSubscribe() {
		return publicSubscribe;
	}

	public void setPublicSubscribe(String publicSubscribe) {
		this.publicSubscribe = publicSubscribe;
	}

	public String getSintak() {
		return sintak;
	}

	public void setSintak(String sintak) {
		this.sintak = sintak;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getFundStartDate() {
		return fundStartDate;
	}

	public void setFundStartDate(String fundStartDate) {
		this.fundStartDate = fundStartDate;
	}

	public String getFundEndDate() {
		return fundEndDate;
	}

	public void setFundEndDate(String fundEndDate) {
		this.fundEndDate = fundEndDate;
	}

	public String getAprTitle() {
		return aprTitle;
	}

	public void setAprTitle(String aprTitle) {
		this.aprTitle = aprTitle;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}

	public Apr01_ApprModel getApprModel() {
		return apprModel;
	}

	public void setApprModel(Apr01_ApprModel apprModel) {
		this.apprModel = apprModel;
	}

	public Long getClassTreeId() {
		return classTreeId;
	}

	public void setClassTreeId(Long classTreeId) {
		this.classTreeId = classTreeId;
	}

	public String getParentTreeName() {
		return parentTreeName;
	}

	public void setParentTreeName(String parentTreeName) {
		this.parentTreeName = parentTreeName;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

}
