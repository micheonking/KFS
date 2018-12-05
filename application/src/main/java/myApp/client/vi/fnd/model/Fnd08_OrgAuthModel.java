package myApp.client.vi.fnd.model;
 
import myApp.client.utils.GridDataModel;
 
public class Fnd08_OrgAuthModel implements GridDataModel {
 
	private	Long	orgAuthId;
	private	Long	fundCodeId;
	private	Long	orgCodeId;
	private	String	authYn;
	private	String	note ;
	private Boolean authYnCheck; 
	
	@Override
	public void setKeyId(Long id) {
		this.setOrgAuthId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getOrgAuthId();
	}

	public Long getOrgAuthId() {
		return orgAuthId;
	}

	public void setOrgAuthId(Long orgAuthId) {
		this.orgAuthId = orgAuthId;
	}

	public Long getFundCodeId() {
		return fundCodeId;
	}

	public void setFundCodeId(Long fundCodeId) {
		this.fundCodeId = fundCodeId;
	}

	public Long getOrgCodeId() {
		return orgCodeId;
	}

	public void setOrgCodeId(Long orgCodeId) {
		this.orgCodeId = orgCodeId;
	}

	public String getAuthYn() {
		return authYn;
	}

	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Boolean getAuthYnCheck() {
		return "true".equals(this.getAuthYn());
	}

	public void setAuthYnCheck(Boolean authYnCheck) {
		this.authYnCheck = authYnCheck; 
		this.authYn = this.authYnCheck.toString();
	}
	
}
