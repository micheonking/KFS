package myApp.client.vi.emp.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Emp10_ManagerContactModel implements GridDataModel {

	private	Long	managerId;
	private	String	managerName;
	private	String	managerEmail;
	private	String	managerMobile;
	private	String	managerTelephone;
	private	String	managerCompanyName;
	private	Long	empId;

	@Override
	public void setKeyId(Long id) {
		this.setManagerId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getManagerId();
	}

	public	Long getManagerId() {
		return	managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getManagerMobile() {
		return managerMobile;
	}

	public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}

	public String getManagerTelephone() {
		return managerTelephone;
	}

	public void setManagerTelephone(String managerTelephone) {
		this.managerTelephone = managerTelephone;
	}

	public String getManagerCompanyName() {
		return managerCompanyName;
	}

	public void setManagerCompanyName(String managerCompanyName) {
		this.managerCompanyName = managerCompanyName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

}
