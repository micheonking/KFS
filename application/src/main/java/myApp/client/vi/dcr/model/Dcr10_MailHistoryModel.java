package myApp.client.vi.dcr.model;

import java.util.Date;

import myApp.client.utils.GridDataModel;

public class Dcr10_MailHistoryModel implements GridDataModel {

	private Long mailHistoryId;
	private Long docId;
	private Long aprId;
	private String senderEmail;
	private Date sendTime;
	private String receiverEmail;
	private String referencerEmail;
	private String attachedFile;
	private String pdfFile;
	private String sealFile;
	private String titleText;
	private String bodyText;
	
	private String sendTimeString;

	@Override
	public void setKeyId(Long id) {
		this.setMailHistoryId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMailHistoryId();
	}

	public Long getMailHistoryId() {
		return mailHistoryId;
	}

	public void setMailHistoryId(Long mailHistoryId) {
		this.mailHistoryId = mailHistoryId;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Long getAprId() {
		return aprId;
	}

	public void setAprId(Long aprId) {
		this.aprId = aprId;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getReferencerEmail() {
		return referencerEmail;
	}

	public void setReferencerEmail(String referencerEmail) {
		this.referencerEmail = referencerEmail;
	}

	public String getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}

	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}

	public String getSealFile() {
		return sealFile;
	}

	public void setSealFile(String sealFile) {
		this.sealFile = sealFile;
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getSendTimeString() {
		return sendTimeString;
	}

	public void setSendTimeString(String sendTimeString) {
		this.sendTimeString = sendTimeString;
	}

}
