package myApp.client.vi.apr.model;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr11_AdvertBookModel implements GridDataModel {

	private Long advertBookId;  
	private Long companyId; 
	private Long apprId;        
	private String advertSeq;     
	private String advertTypeName; 
	private String mediaName; 
	private Long count; 
	private String note; 
	
	private Apr01_ApprModel apprModel = new Apr01_ApprModel(); 
	private Emp00_InfoModel empModel = new Emp00_InfoModel(); 
	private Apr04_ApprStepModel apprStepModel = new Apr04_ApprStepModel();  
	
	@Override
	public void setKeyId(Long id) {
		this.setAdvertBookId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getAdvertBookId();
	}

	public Long getAdvertBookId() {
		return advertBookId;
	}

	public void setAdvertBookId(Long advertBookId) {
		this.advertBookId = advertBookId;
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

	public String getAdvertSeq() {
		return advertSeq;
	}

	public void setAdvertSeq(String advertSeq) {
		this.advertSeq = advertSeq;
	}

	public String getAdvertTypeName() {
		return advertTypeName;
	}

	public void setAdvertTypeName(String advertTypeName) {
		this.advertTypeName = advertTypeName;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Apr01_ApprModel getApprModel() {
		return apprModel;
	}

	public void setApprModel(Apr01_ApprModel apprModel) {
		this.apprModel = apprModel;
	}

	public Emp00_InfoModel getEmpModel() {
		return empModel;
	}

	public void setEmpModel(Emp00_InfoModel empModel) {
		this.empModel = empModel;
	}

	public Apr04_ApprStepModel getApprStepModel() {
		return apprStepModel;
	}

	public void setApprStepModel(Apr04_ApprStepModel apprStepModel) {
		this.apprStepModel = apprStepModel;
	}
	
	
	
}
