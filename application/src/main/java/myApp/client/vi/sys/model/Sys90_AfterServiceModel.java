package myApp.client.vi.sys.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Sys90_AfterServiceModel implements GridDataModel {

	private	Long	afterServiceId;
	private	Date	happenStartDate;
	private	String	typeYn;
	private	String	happenReason;
	private	String	happenRemedy;
	private	String	worker;
	private	Date	dueDate;
	private	Boolean	closeYn;
	private	String	remarks;
	private	Long	regEmpId;
	private	Long	companyId;
	
	private	String	afterServiceName;
	private	String	closeName;
	private	String	regEmpName;

	@Override
	public void setKeyId(Long id) {
		this.setAfterServiceId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAfterServiceId();
	}

	public	Long getAfterServiceId() {
		return	afterServiceId;
	}

	public void setAfterServiceId(Long afterServiceId) {
		this.afterServiceId = afterServiceId;
	}


	public String getTypeYn() {
		return typeYn;
	}

	public void setTypeYn(String typeYn) {
		this.typeYn = typeYn;
	}

	public String getHappenReason() {
		return happenReason;
	}

	public void setHappenReason(String happenReason) {
		this.happenReason = happenReason;
	}

	public String getHappenRemedy() {
		return happenRemedy;
	}

	public void setHappenRemedy(String happenRemedy) {
		this.happenRemedy = happenRemedy;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(Boolean closeYn) {
		this.closeYn = closeYn;
	}

	public String getAfterServiceName() {
		return afterServiceName;
	}

	public void setAfterServiceName(String afterServiceName) {
		this.afterServiceName = afterServiceName;
	}

	public String getCloseName() {
		return closeName;
	}

	public void setCloseName(String closeName) {
		this.closeName = closeName;
	}

	public Long getRegEmpId() {
		return regEmpId;
	}

	public void setRegEmpId(Long regEmpId) {
		this.regEmpId = regEmpId;
	}

	public String getRegEmpName() {
		return regEmpName;
	}

	public void setRegEmpName(String regEmpName) {
		this.regEmpName = regEmpName;
	}

	public Date getHappenStartDate() {
		return happenStartDate;
	}

	public void setHappenStartDate(Date happenStartDate) {
		this.happenStartDate = happenStartDate;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
