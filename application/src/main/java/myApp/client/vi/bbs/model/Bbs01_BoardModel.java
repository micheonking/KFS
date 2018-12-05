package myApp.client.vi.bbs.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys02_UserModel;

public class Bbs01_BoardModel implements GridDataModel {
	private	Long	boardId;
	private	String	typeCode;
	private	String	title;
	private	String	content;
	private	Long	writeUserId;
	private	Long	companyId;
	private	Date	writeDate;
	private	String	typeName;
	
	private	String	writeUserName;
	
	
	private Sys02_UserModel userModel = new Sys02_UserModel(); 
	
	@Override
	public void setKeyId(Long id) {
		this.setBoardId(id);
	}
	@Override
	public Long getKeyId() {
		return this.getBoardId(); 
	}
	
	public Long getBoardId() {
		return boardId;
	}
	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getWriteUserId() {
		return writeUserId;
	}
	public void setWriteUserId(Long writeUserId) {
		this.writeUserId = writeUserId;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Sys02_UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(Sys02_UserModel userModel) {
		this.userModel = userModel;
	}
	public String getWriteUserName() {
		return writeUserName;
	}
	public void setWriteUserName(String writeUserName) {
		this.writeUserName = writeUserName;
	}
}
