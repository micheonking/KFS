package myApp.client.vi.opr.model;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;

public class Opr02_FundModel implements GridDataModel {

	private Long	fundId;
	private Long	createId;
	private Long	regFundId;
	
	private Fnd00_FundModel fundModel = new Fnd00_FundModel();

	@Override
	public void setKeyId(Long id) {
		this.setFundId(id);
	}

	@Override
	public Long getKeyId() {
		return getFundId();
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getRegFundId() {
		return regFundId;
	}

	public void setRegFundId(Long regFundId) {
		this.regFundId = regFundId;
	}

	public Fnd00_FundModel getFundModel() {
		return fundModel;
	}

	public void setFundModel(Fnd00_FundModel fundModel) {
		this.fundModel = fundModel;
	}

}
