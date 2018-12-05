package myApp.client.vi.fnd.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd02_FundInfoModel;

public class Fnd01_FundCodeModel implements GridDataModel {

	private	Long	fundCodeId;
	private	String	fundCode;
	private	Date	startDate;
	private	Date	endDate;
	private	String	closeYn;
	private	String	planYn;
	private	Long	realFundId;
	private	Long	companyId;
	private	String	closeName;
	private	String	realFundCode;
	private	String	realFundName;

	private Fnd02_FundInfoModel fundInfoModel = new Fnd02_FundInfoModel();

	@Override
	public void setKeyId(Long id) {
		this.setFundCodeId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getFundCodeId();
	}

	public	Long getFundCodeId() {
		return	fundCodeId;
	}

	public void setFundCodeId(Long fundCodeId) {
		this.fundCodeId = fundCodeId;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	public String getPlanYn() {
		return planYn;
	}

	public void setPlanYn(String planYn) {
		this.planYn = planYn;
	}

	public Long getRealFundId() {
		return realFundId;
	}

	public void setRealFundId(Long realFundId) {
		this.realFundId = realFundId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCloseName() {
		return closeName;
	}

	public void setCloseName(String closeName) {
		this.closeName = closeName;
	}

	public String getRealFundCode() {
		return realFundCode;
	}

	public void setRealFundCode(String realFundCode) {
		this.realFundCode = realFundCode;
	}

	public String getRealFundName() {
		return realFundName;
	}

	public void setRealFundName(String realFundName) {
		this.realFundName = realFundName;
	}

	public Fnd02_FundInfoModel getFundInfoModel() {
		return fundInfoModel;
	}

	public void setFundInfoModel(Fnd02_FundInfoModel fundInfoModel) {
		this.fundInfoModel = fundInfoModel;
	}

}
