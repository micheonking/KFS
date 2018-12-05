package myApp.client.vi.cmp.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Cmp01_AdvertiseModel implements GridDataModel {

	private	Long	advertiseId;
	private	String	advertiseNo;
	private	Date	screeningDate;
	private	String	advertiseType;
	private	String	mediaType;
	private	String	advertiseNote;
	private	String	managerEmpId;
	private	String	advertiseCount;
	private	String	empName;

	@Override
	public void setKeyId(Long id) {
		this.setAdvertiseId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAdvertiseId();
	}

	public	Long getAdvertiseId() {
		return	advertiseId;
	}

	public void setAdvertiseId(Long advertiseId) {
		this.advertiseId = advertiseId;
	}

	public String getAdvertiseNo() {
		return advertiseNo;
	}

	public void setAdvertiseNo(String advertiseNo) {
		this.advertiseNo = advertiseNo;
	}

	public Date getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(Date screeningDate) {
		this.screeningDate = screeningDate;
	}

	public String getAdvertiseType() {
		return advertiseType;
	}

	public void setAdvertiseType(String advertiseType) {
		this.advertiseType = advertiseType;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getAdvertiseNote() {
		return advertiseNote;
	}

	public void setAdvertiseNote(String advertiseNote) {
		this.advertiseNote = advertiseNote;
	}

	public String getManagerEmpId() {
		return managerEmpId;
	}

	public void setManagerEmpId(String managerEmpId) {
		this.managerEmpId = managerEmpId;
	}

	public String getAdvertiseCount() {
		return advertiseCount;
	}

	public void setAdvertiseCount(String advertiseCount) {
		this.advertiseCount = advertiseCount;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
