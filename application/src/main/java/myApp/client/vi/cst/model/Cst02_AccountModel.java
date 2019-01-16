package myApp.client.vi.cst.model;

import myApp.client.utils.GridDataModel;

public class Cst02_AccountModel implements GridDataModel {

	private Long accountId;
	private Long userId;
	private String mgCode;
	private String mgName;
	private String accountNo;
	private String fundCode;
	private String accountName;
	private String accountBranch;
	private String branchManager;
	private String managerContact;

	@Override
	public void setKeyId(Long id) {
		this.setAccountId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAccountId();
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public void setMgCode(String mgCode) {
		this.mgCode = mgCode;
	}

	public String getMgName() {
		return mgName;
	}

	public void setMgName(String mgName) {
		this.mgName = mgName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
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

}
