package myApp.client.vi.hom.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Hom02_BoardModel implements GridDataModel {
	private	Long	boardId;
	private	String	typeCode;
	private	String	titleName;
	private	Date	settleDate;
	private	Long	cnt;
	private	String	fileName;
	private	String	filePath;
	private	String	writer;
	private	String	contents;

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
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSetdate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
