package myApp.client.vi.fnd.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Fnd03_ConsignmentModel  implements GridDataModel {
	private	Long	companyId;
	private	Long	consignmentId;
	private	String	consignmentCode;
	private	String	consignmentName;
	private	Boolean	consignmentUseYn;
	private	String	consignmentNote;

	@Override
	public void setKeyId(Long id) {
		this.setConsignmentId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getConsignmentId();
	}
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public	Long getConsignmentId() {
		return	consignmentId;
	}

	public void setConsignmentId(Long consignmentId) {
		this.consignmentId = consignmentId;
	}

	public String getConsignmentCode() {
		return consignmentCode;
	}

	public void setConsignmentCode(String consignmentCode) {
		this.consignmentCode = consignmentCode;
	}

	public String getConsignmentName() {
		return consignmentName;
	}

	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}

	public Boolean getConsignmentUseYn() {
		return consignmentUseYn;
	}

	public void setConsignmentUseYn(Boolean consignmentUseYn) {
		this.consignmentUseYn = consignmentUseYn;
	}

	public String getConsignmentNote() {
		return consignmentNote;
	}

	public void setConsignmentNote(String consignmentNote) {
		this.consignmentNote = consignmentNote;
	}

}
