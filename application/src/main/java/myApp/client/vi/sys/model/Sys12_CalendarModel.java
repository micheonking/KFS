package myApp.client.vi.sys.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Sys12_CalendarModel implements GridDataModel {

	private Long    calendarId;
	private Long    companyId;
	private Date    day;
	private String  weekday;
	private String  workingYn;
	private String  offReason;
	private String  note;
	
	private Boolean workingYnFlag;

	@Override
	public void setKeyId(Long id) {
		this.setCalendarId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getCalendarId();
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getWorkingYn() {
		return workingYn;
	}

	public void setWorkingYn(String workingYn) {
		this.workingYn = workingYn;
	}

	public String getOffReason() {
		return offReason;
	}

	public void setOffReason(String offReason) {
		this.offReason = offReason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getWorkingYnFlag() {
		this.workingYnFlag = "true".equals(this.workingYn);
		return workingYnFlag;
	}

	public void setWorkingYnFlag(Boolean workingYnFlag) {
		this.workingYnFlag = workingYnFlag;
		this.workingYn     = workingYnFlag.toString();
	}
	
}
