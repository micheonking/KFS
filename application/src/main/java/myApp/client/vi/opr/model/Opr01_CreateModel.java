package myApp.client.vi.opr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Opr01_CreateModel implements GridDataModel {

	private Long	createId;
	private String	year;
	private String	termCd;
	private String	termNm;
	private Long	createEmpId;
	private String	title;
	private Date	startDate;
	private Date	dueDate;
	private String	note;
	private Long	companyId;
	private Long	apprId;
	
	private Date	closeDate;
	private String	aprNm;
	private	Date	aprDate;
	private Long	regId;

	private Emp00_InfoModel empInfoModel = new Emp00_InfoModel();

	@Override
	public void setKeyId(Long id) {
		this.setCreateId(id);
	}

	@Override
	public Long getKeyId() {
		return getCreateId();
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTermCd() {
		return termCd;
	}

	public void setTermCd(String termCd) {
		this.termCd = termCd;
	}

	public String getTermNm() {
		return termNm;
	}

	public void setTermNm(String termNm) {
		this.termNm = termNm;
	}

	public Long getCreateEmpId() {
		return createEmpId;
	}

	public void setCreateEmpId(Long createEmpId) {
		this.createEmpId = createEmpId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Long getApprId() {
		return apprId;
	}

	public void setApprId(Long apprId) {
		this.apprId = apprId;
	}

	public String getAprNm() {
		return aprNm;
	}

	public void setAprNm(String aprNm) {
		this.aprNm = aprNm;
	}
	
	public Date getAprDate() {
		return aprDate;
	}

	public void setAprDate(Date aprDate) {
		this.aprDate = aprDate;
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public Emp00_InfoModel getEmpInfoModel() {
		return empInfoModel;
	}

	public void setEmpInfoModel(Emp00_InfoModel empInfoModel) {
		this.empInfoModel = empInfoModel;
	}
}
