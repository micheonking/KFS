package myApp.client.vi.hom.company.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Hom01_OutlineModel implements GridDataModel {

	private	Long	outlineId;
	private	Long	companyId;
	private	Date	registDt;
	private	String	paidInCapital;
	private	String	ownersEquity;
	private	String	contractAssetSize;
	private	String	professionalPersonnel;
	private	String	mainBusiness;
	private	String	ceoName;
	private	String	constituteStockholder;

	@Override
	public void setKeyId(Long id) {
		this.setOutlineId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getOutlineId();
	}

	public	Long getOutlineId() {
		return	outlineId;
	}

	public void setOutlineId(Long outlineId) {
		this.outlineId = outlineId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getRegistDt() {
		return registDt;
	}

	public void setRegistDt(Date registDt) {
		this.registDt = registDt;
	}

	public String getPaidInCapital() {
		return paidInCapital;
	}

	public void setPaidInCapital(String paidInCapital) {
		this.paidInCapital = paidInCapital;
	}

	public String getOwnersEquity() {
		return ownersEquity;
	}

	public void setOwnersEquity(String ownersEquity) {
		this.ownersEquity = ownersEquity;
	}

	public String getContractAssetSize() {
		return contractAssetSize;
	}

	public void setContractAssetSize(String contractAssetSize) {
		this.contractAssetSize = contractAssetSize;
	}

	public String getProfessionalPersonnel() {
		return professionalPersonnel;
	}

	public void setProfessionalPersonnel(String professionalPersonnel) {
		this.professionalPersonnel = professionalPersonnel;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getConstituteStockholder() {
		return constituteStockholder;
	}

	public void setConstituteStockholder(String constituteStockholder) {
		this.constituteStockholder = constituteStockholder;
	}

}
