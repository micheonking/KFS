package myApp.client.vi.sys;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.model.Sys10_FileModel;

public class Lookup_SingleFile_middleshin extends VerticalLayoutContainer implements InterfaceServiceCall {
	private	final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Long 	fileId ; 
	private FormPanel formPanel = new FormPanel();
	private TextField fileIdField = new TextField();
	
	public Lookup_SingleFile_middleshin(Long fileId, String editYn){
		this.fileId = fileId; 
		retrieve();//파일명 조회
		
		final FileUploadField file = new FileUploadField();
		file.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {
				//Info.display("File Changed", "You selected " + file.getValue());
				submit();
			}
		});

		file.setName("uploadedfile"); // file name 
		file.setAllowBlank(false);

		FieldLabel fileSearchLable = new FieldLabel(file, "파일선택");  
		fileSearchLable.setWidth(200);
		fileSearchLable.setLabelWidth(60);
		
		//this.add(fileSearchLable, new VerticalLayoutData(50, 1)); 
		
		fileIdField.setName("fileId");

		FieldLabel fileIdLabel = new FieldLabel(fileIdField, "파일명") ;
		fileIdLabel.setEnabled(false);
		fileIdLabel.setLabelWidth(60);
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		if("Y".equals(editYn)) {
		    vlc.add(fileSearchLable, new VerticalLayoutData(1, -1, new Margins(0)));
		}
		vlc.add(fileIdLabel, new VerticalLayoutData(400, -1)); //, new VerticalLayoutData(1, -1, new Margins(20)));
		
//		formPanel.setAction("FileUpload"); // File upload servelt call - web.xml 참조 
		formPanel.setEncoding(Encoding.MULTIPART);
		formPanel.setMethod(Method.POST);
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// image.setUrl("FileDownload?file=" + userId);
				uploadMessage(event.getResults().toString());
				retrieve();
			}
		});
		
		formPanel.add(vlc);

		VerticalLayoutData vld = new VerticalLayoutData(); 
		vld.setMargins( new Margins(0, 0, 0, 0));
		this.add(formPanel, vld);
		
		TextButton submitButton = new TextButton("파일등록");
		submitButton.setWidth(80);
		
		submitButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (!formPanel.isValid()) {
					return;
				}
				submit();
			}
		});

		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		});
		
		TextButton fileDownloadButton = new TextButton("파일받기");
		fileDownloadButton.setWidth(80);
		fileDownloadButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if ("".equals(fileIdField.getText())) {
					new SimpleMessage("등록된 첨부파일이 없습니다.");
					return;
				}
				download();
			}
		});
		
		ContentPanel cp = new ContentPanel();
		cp.setBorders(false);
		cp.setHeaderVisible(false);
		if("N".equals(editYn)) {
		} else {
			//cp.addButton(submitButton);
		}
		cp.addButton(fileDownloadButton);
		//cp.addButton(closeButton);
		cp.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(cp);
	}
	
	//등록버튼
	private void submit(){
		if(fileId != null){
			//String actionUrl = "FileUpload?uploadType=image&"; 
			String actionUrl = "FileUpload?uploadType=file&fileId=" + this.fileId.toString();
			actionUrl += "&fileType=single";
			formPanel.setAction(actionUrl);
			formPanel.submit();
			formPanel.reset();
		}
	}
	
	//받기버튼
	private void download() {
		String actionUrl = "FileDownload?fileId=" + fileId;
		formPanel.setAction(actionUrl);
		formPanel.submit();
	}
	
	public void uploadMessage(String message){
		// 이미지가 버퍼에 있어 다시 불러오지 않는 경우  System.currentTimeMillis() 을 붙여 
		// 시스템이 다은 URL호출이라 인지할 수 있도록 한다. 
		MessageBox box = new MessageBox("파일등록(" + this.fileId + ")", new HTML(message).getText());
        box.setIcon(MessageBox.ICONS.info());
        box.show();
	}
	
	private void retrieve() {
		// TODO Auto-generated method stub
	    
	    ServiceRequest request = new ServiceRequest("sys.Sys10_File.selectByParentId");
	    request.putLongParam("parentId", fileId);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
		if(result.getResult().size() > 0) {
			Sys10_FileModel tempFileModel = new Sys10_FileModel();
			tempFileModel = (Sys10_FileModel) result.getResult().get(0);
			logger.info("" + tempFileModel.getFileName() + "." + tempFileModel.getExt());
			this.fileIdField.setText(tempFileModel.getFileName() + "." + tempFileModel.getExt());
		}
	}
}
