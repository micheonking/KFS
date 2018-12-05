package myApp.client.vi.emp.model;

import java.util.Date;

import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.vi.org.model.Org02_InfoModel;
import myApp.client.utils.GridDataModel;

public class Emp02_EmpModel implements GridDataModel {
	private Long 	empId;
	private Long 	personId;
	private String 	empNo;
	private String 	empKindCode;
	private String 	empKindName;
	private Date 	hireDate;
	private String 	hireCode;
	private String 	hireName;
	private String 	hireNote;
	private Date 	retireDate;
	private String 	retireCode;
	private String 	retireName;
	private String 	retireNote;
	private String 	retireYn;
	private String 	note;
	private String	cycleTime;
	private String	empId_String;
	
	private Emp01_PersonModel personModel = new Emp01_PersonModel();
	private Emp03_TransModel transModel = new Emp03_TransModel();
	private Emp04_AddTitleModel pridModel = new Emp04_AddTitleModel();
	private Org01_CodeModel orgCodeModel = new Org01_CodeModel();
	private Org02_InfoModel orgInfoModel = new Org02_InfoModel();

	@Override
	public void setKeyId(Long id) {
		this.setEmpId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getEmpId();
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpKindCode() {
		return empKindCode;
	}

	public void setEmpKindCode(String empKindCode) {
		this.empKindCode = empKindCode;
	}

	public String getEmpKindName() {
		return empKindName;
	}

	public void setEmpKindName(String empKindName) {
		this.empKindName = empKindName;
	}

	
	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getHireCode() {
		return hireCode;
	}

	public void setHireCode(String hireCode) {
		this.hireCode = hireCode;
	}

	public String getHireName() {
		return hireName;
	}

	public void setHireName(String hireName) {
		this.hireName = hireName;
	}

	public String getHireNote() {
		return hireNote;
	}

	public void setHireNote(String hireNote) {
		this.hireNote = hireNote;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	public String getRetireCode() {
		return retireCode;
	}

	public void setRetireCode(String retireCode) {
		this.retireCode = retireCode;
	}

	public String getRetireName() {
		return retireName;
	}

	public void setRetireName(String retireName) {
		this.retireName = retireName;
	}

	public String getRetireNote() {
		return retireNote;
	}

	public void setRetireNote(String retireNote) {
		this.retireNote = retireNote;
	}

	public String getRetireYn() {
		return retireYn;
	}

	public void setRetireYn(String retireYn) {
		this.retireYn = retireYn;
	}

	public String getNote() {
		return note;
	}

	public String getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(String cycleTime) {
		this.cycleTime = cycleTime;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Emp01_PersonModel getPersonModel() {
		return personModel;
	}

	public void setPersonModel(Emp01_PersonModel personModel) {
		this.personModel = personModel;
	}

	public Emp03_TransModel getTransModel() {
		return transModel;
	}

	public void setTransModel(Emp03_TransModel transModel) {
		this.transModel = transModel;
	}

	public Emp04_AddTitleModel getPridModel() {
		return pridModel;
	}

	public void setPridModel(Emp04_AddTitleModel pridModel) {
		this.pridModel = pridModel;
	}

	public Org01_CodeModel getOrgCodeModel() {
		return orgCodeModel;
	}

	public void setOrgCodeModel(Org01_CodeModel orgCodeModel) {
		this.orgCodeModel = orgCodeModel;
	}

	public Org02_InfoModel getOrgInfoModel() {
		return orgInfoModel;
	}

	public void setOrgInfoModel(Org02_InfoModel orgInfoModel) {
		this.orgInfoModel = orgInfoModel;
	}

	public String getEmpId_String() {
		return empId_String ;
	}

	public void setEmpId_String(String empId_String) {
		this.empId_String = empId_String;
	}
}
