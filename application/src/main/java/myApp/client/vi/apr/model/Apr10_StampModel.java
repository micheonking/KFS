package myApp.client.vi.apr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr10_StampModel implements GridDataModel {

	private Long stampId;
	private Long orgId;
	private String stampName;
	private Date startDate;
	private Date closeDate;
	private String useName;
	private String note;
	private Long empId;

	private String stampId_string;
	private String orgName;
	private Long stampFileId;
	private Long aprEmpId;
	private String aprEmpName;

	@Override
	public void setKeyId(Long id) {
		this.setStampId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getStampId();
	}

	public Long getStampId() {
		return stampId;
	}

	public void setStampId(Long stampId) {
		this.stampId = stampId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getStampName() {
		return stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStampId_string() {
		return stampId_string;
	}

	public void setStampId_string(String stampId_string) {
		this.stampId_string = stampId_string;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getStampFileId() {
		return stampFileId;
	}

	public void setStampFileId(Long stampFileId) {
		this.stampFileId = stampFileId;
	}

	public Long getAprEmpId() {
		return aprEmpId;
	}

	public void setAprEmpId(Long aprEmpId) {
		this.aprEmpId = aprEmpId;
	}

	public String getAprEmpName() {
		return aprEmpName;
	}

	public void setAprEmpName(String aprEmpName) {
		this.aprEmpName = aprEmpName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}




}
