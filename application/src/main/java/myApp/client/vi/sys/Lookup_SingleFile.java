package myApp.client.vi.sys;

import com.gargoylesoftware.htmlunit.javascript.host.event.Event;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.FileUpload;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.resource.ResourceIcon;
import myApp.client.utils.InterfaceCallbackResult;

public class Lookup_SingleFile extends Window { // implements InterfaceServiceCall {

	private Long 	parentId ; 
	private InterfaceCallbackResult callback; 
	private String 	fileName = ""; 
	
	FileUpload fu = new FileUpload();
	
	
	public Lookup_SingleFile(Long parentId, InterfaceCallbackResult callback){

		this.parentId = parentId; 
		this.callback = callback; 
		
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.setPixelSize(450, 420);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		this.setHeading("파일등록");
		
//		final FileUploadField fileUploadField = new FileUploadField();
//		fileUploadField.setName("uploadedfile"); // file name 
//		
//		fileUploadField.addChangeHandler(new ChangeHandler() {
//			@Override
//			public void onChange(ChangeEvent arg0) {
//				
//				int idx = fileUploadField.getValue().lastIndexOf("\\") + 1;
//				if(idx <= 0) {
//				} else {
//					fileName = fileUploadField.getValue().substring(idx); 
//				}
//				
//				Info.display("You selected file", fileName);
//			}
//		});
//
//		FieldLabel fileLable = new FieldLabel(fileUploadField, "파일선택");  
//		fileLable.setLabelWidth(60);
		
		 
		fu.setWidth("100");
		
		
		
		
		
		TextField fileIdField = new TextField();
		fileIdField.setText(parentId + ""); // 서버에 Parnet ID를 넘겨주어야 한다.  
		fileIdField.setName("parentId");
		fileIdField.setVisible(false);
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		
		vlc.add(fu, new VerticalLayoutData(1, -1, new Margins(15, 10, 10, 10)));
		
//		vlc.add(fileLable, new VerticalLayoutData(1, -1, new Margins(15, 10, 10, 10)));
		vlc.add(fileIdField); 
		FormPanel formPanel = new FormPanel();
		
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				// callback execute 
				
			}
		}); 
		
		
		formPanel.setEncoding(Encoding.MULTIPART);
		formPanel.setMethod(Method.POST);
		formPanel.add(vlc);
		this.add(formPanel);

		TextButton fileButton = new TextButton("파일찾기");
		fileButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
//				fu.fireEvent(event);
				fu.click();
			}
			
		}); 
		
		
		TextButton submitButton = new TextButton("파일등록");
		submitButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (!formPanel.isValid()) {
					return;
				}
				String actionUrl = "FileUpload?uploadType=file&fileId=" + parentId;
				actionUrl += "&fileType=single";
				formPanel.setAction(actionUrl);
				formPanel.submit();
//				formPanel.reset();
			}
		});

		TextButton closeButton = new TextButton("닫기");
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		});
		
		this.addButton(fileButton);
		this.addButton(submitButton);
		this.addButton(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		
	}
	
	
//	public void uploadMessage(String message){
//		// 이미지가 버퍼에 있어 다시 불러오지 않는 경우  System.currentTimeMillis() 을 붙여 
//		// 시스템이 다은 URL호출이라 인지할 수 있도록 한다. 
//		MessageBox box = new MessageBox("파일등록(" + this.fileId + ")", new HTML(message).getText());
//        box.setIcon(MessageBox.ICONS.info());
//        box.show();
//	}
	
//	private void retrieve() {
//	    ServiceRequest request = new ServiceRequest("sys.Sys10_File.selectByParentId");
//	    request.putLongParam("parentId", fileId);
//		ServiceCall service = new ServiceCall();
//		service.execute(request, this);
//	}
//
//	@Override
//	public void getServiceResult(ServiceResult result) {
//		// TODO Auto-generated method stub
//		if(result.getResult().size() > 0) {
//			Sys10_FileModel tempFileModel = new Sys10_FileModel();
//			tempFileModel = (Sys10_FileModel) result.getResult().get(0);
////			this.fileIdField.setText(tempFileModel.getFileName() + "." + tempFileModel.getExt());
//		}
//	}
}
