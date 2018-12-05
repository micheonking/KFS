package myApp.client.vi.psc.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys02_UserModel;

public class TeacherModel implements GridDataModel {
	private Long 	teacherId;
	private Long	userId ;
	private Long	studyClassId;
	private String	teacherTypeCode;
	private String	teacherTypeName;
	private Date	applyDate;
	private String	closeYn = "false";
	private String 	note;
	
	private Sys02_UserModel userModel = new Sys02_UserModel(); 
	
	@Override
	public void setKeyId(Long id) {
		this.setTeacherId(id);
	}
	@Override
	public Long getKeyId() {
		return this.getTeacherId(); 
	}
	
	public Sys02_UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(Sys02_UserModel userModel) {
		this.userModel = userModel;
	}
	
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getStudyClassId() {
		return studyClassId;
	}
	public void setStudyClassId(Long studyClassId) {
		this.studyClassId = studyClassId;
	}
	public String getTeacherTypeCode() {
		return teacherTypeCode;
	}
	public void setTeacherTypeCode(String teacherTypeCode) {
		this.teacherTypeCode = teacherTypeCode;
	}
	public String getTeacherTypeName() {
		return teacherTypeName;
	}
	public void setTeacherTypeName(String teacherTypeName) {
		this.teacherTypeName = teacherTypeName;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getCloseYn() {
		if(closeYn == null){
			return "false"; 
		}
		else {
			return closeYn;
		}
	}
	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Boolean getCloseYnFlag(){
		return "true".equals(this.closeYn); 
	}
	public void setCloseYnFlag(Boolean closeYnFlag){
		this.setCloseYn(closeYnFlag.toString()); 
	}
	
	public String getFindCell(){
		// find icon cell 
		return "findCell"; 
	}
	public void setFindCell(String s){
	}
}
