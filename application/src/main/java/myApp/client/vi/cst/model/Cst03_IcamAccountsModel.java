package myApp.client.vi.cst.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Cst03_IcamAccountsModel implements GridDataModel {
	
	private Long	icamAccountId;
	private	String	fundCode;
	private	String	fundName;
	private	String	accNo;
	private	Date	seoljDate;
	private	String	seriesGb;
	private	Long	seoljYear;
	private	Long	sintakGigan;
	private	Long	basicPer;
	private	Long	bmPer;
	private	Long	successPer;
	private	String	unyongName;
	private	String	csGb;
	private	Long	seoljAek;
	private	String	mgCode;
	private	String	mgCodeName;
	private	String	mgName;
	private	String	mgjjName;
	private	Date	gyulDate;

	@Override
	public void setKeyId(Long id) {
		this.setIcamAccountId(id);
	}

	@Override
	public Long getKeyId() {
		
		return this.getIcamAccountId();
	}

	public Long getIcamAccountId() {
		return icamAccountId;
	}

	public void setIcamAccountId(Long icamAccountId) {
		this.icamAccountId = icamAccountId;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public Date getSeoljDate() {
		return seoljDate;
	}

	public void setSeoljDate(Date seoljDate) {
		this.seoljDate = seoljDate;
	}

	public String getSeriesGb() {
		return seriesGb;
	}

	public void setSeriesGb(String seriesGb) {
		this.seriesGb = seriesGb;
	}

	public Long getSeoljYear() {
		return seoljYear;
	}

	public void setSeoljYear(Long seoljYear) {
		this.seoljYear = seoljYear;
	}

	public Long getSintakGigan() {
		return sintakGigan;
	}

	public void setSintakGigan(Long sintakGigan) {
		this.sintakGigan = sintakGigan;
	}

	public Long getBasicPer() {
		return basicPer;
	}

	public void setBasicPer(Long basicPer) {
		this.basicPer = basicPer;
	}

	public Long getBmPer() {
		return bmPer;
	}

	public void setBmPer(Long bmPer) {
		this.bmPer = bmPer;
	}

	public Long getSuccessPer() {
		return successPer;
	}

	public void setSuccessPer(Long successPer) {
		this.successPer = successPer;
	}

	public String getUnyongName() {
		return unyongName;
	}

	public void setUnyongName(String unyongName) {
		this.unyongName = unyongName;
	}

	public String getCsGb() {
		return csGb;
	}

	public void setCsGb(String csGb) {
		this.csGb = csGb;
	}

	public Long getSeoljAek() {
		return seoljAek;
	}

	public void setSeoljAek(Long seoljAek) {
		this.seoljAek = seoljAek;
	}

	public String getMgCode() {
		return mgCode;
	}

	public void setMgCode(String mgCode) {
		this.mgCode = mgCode;
	}

	public String getMcCodeName() {
		return mgCodeName;
	}

	public void setMcCodeName(String mcCodeName) {
		this.mgCodeName = mcCodeName;
	}

	public String getMgName() {
		return mgName;
	}

	public void setMgName(String mgName) {
		this.mgName = mgName;
	}

	public String getMgjjName() {
		return mgjjName;
	}

	public void setMgjjName(String mgjjName) {
		this.mgjjName = mgjjName;
	}

	public Date getGyulDate() {
		return gyulDate;
	}

	public void setGyulDate(Date gyulDate) {
		this.gyulDate = gyulDate;
	}
	
	

	

}
