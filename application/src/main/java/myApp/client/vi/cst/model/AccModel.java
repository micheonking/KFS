package myApp.client.vi.cst.model;

import myApp.client.utils.GridDataModel;

public class AccModel implements GridDataModel {

	private Long	accId;
	private Long	userId;
	private	String	mgCode;
	private String	accNo;
	private	String	fundCode;
	private String	accName;
	private String	accBranch;
	private String	branchManager;
	private String	managerContact;
	
	private String	mgCodeName;
	
	@Override
	public void setKeyId(Long id) {
		this.setAccId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAccId(); 
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMgCode() {
		return mgCode;
	}

	public void setMgcode(String mgCode) {
		this.mgCode = mgCode;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccBranch() {
		return accBranch;
	}

	public void setAccBranch(String accBranch) {
		this.accBranch = accBranch;
	}

	public String getBranchManager() {
		return branchManager;
	}

	public void setBranchManager(String branchManager) {
		this.branchManager = branchManager;
	}

	public String getManagerContact() {
		return managerContact;
	}

	public void setManagerContact(String managerContact) {
		this.managerContact = managerContact;
	}

	public String getMgCodeName() {
		return mgCodeName;
	}

	public void setMgCodeName(String mgCodeName) {
		this.mgCodeName = mgCodeName;
	}

}
