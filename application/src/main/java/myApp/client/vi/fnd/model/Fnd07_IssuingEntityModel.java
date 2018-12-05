package myApp.client.vi.fnd.model;
 
import myApp.client.utils.GridDataModel;
 
public class Fnd07_IssuingEntityModel implements GridDataModel {
 
	private	Long	issuingEntityId;
	private	Long	companyId;
	private	String	issuingEntityCode;
	private	String	issuingEntityName;
	private	String	issuingEntityAttachCode;
	private	Long	issuingEntityFaceValue;
	private	String	issuingEntitySettleMonth;
	private	String	issuingEntityNationCode;
	private	Boolean	issuingEntityUseYn;
	private	String	issuingEntityNote;

	private	String	kukaName;
 
	
	@Override
	public void setKeyId(Long id) {
		this.setIssuingEntityId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getIssuingEntityId();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getIssuingEntityId() {
		return issuingEntityId;
	}

	public void setIssuingEntityId(Long issuingEntityId) {
		this.issuingEntityId = issuingEntityId;
	}

	public String getIssuingEntityCode() {
		return issuingEntityCode;
	}

	public void setIssuingEntityCode(String issuingEntityCode) {
		this.issuingEntityCode = issuingEntityCode;
	}

	public String getIssuingEntityName() {
		return issuingEntityName;
	}

	public void setIssuingEntityName(String issuingEntityName) {
		this.issuingEntityName = issuingEntityName;
	}

	public String getIssuingEntityAttachCode() {
		return issuingEntityAttachCode;
	}

	public void setIssuingEntityAttachCode(String issuingEntityAttachCode) {
		this.issuingEntityAttachCode = issuingEntityAttachCode;
	}

	public Long getIssuingEntityFaceValue() {
		return issuingEntityFaceValue;
	}

	public void setIssuingEntityFaceValue(Long issuingEntityFaceValue) {
		this.issuingEntityFaceValue = issuingEntityFaceValue;
	}

	public String getIssuingEntitySettleMonth() {
		return issuingEntitySettleMonth;
	}

	public void setIssuingEntitySettleMonth(String issuingEntitySettleMonth) {
		this.issuingEntitySettleMonth = issuingEntitySettleMonth;
	}

	public String getIssuingEntityNationCode() {
		return issuingEntityNationCode;
	}

	public void setIssuingEntityNationCode(String issuingEntityNationCode) {
		this.issuingEntityNationCode = issuingEntityNationCode;
	}

	public Boolean	 getIssuingEntityUseYn() {
		return issuingEntityUseYn;
	}

	public void setIssuingEntityUseYn(Boolean	 issuingEntityUseYn) {
		this.issuingEntityUseYn = issuingEntityUseYn;
	}

	public String getIssuingEntityNote() {
		return issuingEntityNote;
	}

	public void setIssuingEntityNote(String issuingEntityNote) {
		this.issuingEntityNote = issuingEntityNote;
	}

	public String getKukaName() {
		return kukaName;
	}

	public void setKukaName(String kukaName) {
		this.kukaName = kukaName;
	}

}
