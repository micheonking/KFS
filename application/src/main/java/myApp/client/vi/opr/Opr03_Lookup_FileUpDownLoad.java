package myApp.client.vi.opr;

import javax.sound.sampled.Port.Info;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.Lookup_SingleFile;

public class Opr03_Lookup_FileUpDownLoad extends Window implements InterfaceServiceCall {

	private Long regId;
	private String editMode;
	private InterfaceCallbackResult callback;
	private FileUpdownForm fileForm = new FileUpdownForm();

	TextField  fileNameField  = new TextField();
	TextButton upLoadButton   = new TextButton("파일등록");
	TextButton downLoadButton = new TextButton("다운로드");
	
	ContentPanel contentPanel  = new ContentPanel();

	public void open (long regId, String editMode, InterfaceCallbackResult callback) {
		
		this.regId    = regId;
		this.editMode = editMode;
		this.callback = callback;

		this.setModal(true);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		this.setHeading(" File Upload/Download");
		
		if (editMode.equals("Y")) {
			this.setPixelSize(400, 140);
		} else {
			this.setPixelSize(330, 140);
		}
		
		contentPanel.setHeaderVisible(false);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(this.getEditor(), new VerticalLayoutData(1, -1, new Margins(20)));
		
//		fileForm.setParentId(regId);
		fileForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				fileNameField.setValue(fileForm.getFileName());
			}
		});
		
		vlc.add(fileForm.getForm());
		fileForm.getForm().setVisible(false);

		this.add(vlc);
		
		TextButton okButton = new TextButton("확인");
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				confirm();
			}
		});
		this.addButton(okButton);

		TextButton cancelButton = new TextButton("취소");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show();
	}

	private ContentPanel getEditor() {
		
		upLoadButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				fileForm.click();
			}
		});
		
		downLoadButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// file download
				String actionUrl = "FileDownload?fileId=" + regId;
				FormPanel formPanel = new FormPanel();
				formPanel.setEncoding(Encoding.MULTIPART);
				formPanel.setMethod(Method.POST);
				formPanel.setAction(actionUrl);
				formPanel.submit();
			}
		});

		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
		fileNameField.setWidth(200);
		fileNameField.setReadOnly(true);
		hblc.add(fileNameField, new BoxLayoutData(new Margins(0,5,0,0)));
		hblc.add(upLoadButton, new BoxLayoutData(new Margins(0,2,0,0)));
		hblc.add(downLoadButton);
		
		if (editMode.equals("Y")) {
			upLoadButton.setVisible(true);
		} else {
			upLoadButton.setVisible(false);
		}

		contentPanel.setWidget(hblc);
		return contentPanel;
	}

	private void confirm() {
		if (editMode.equals("Y")) {
			ServiceRequest request = new ServiceRequest("sys.Sys10_File.selectOneFileName");
			request.putLongParam("parentId", this.regId);
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		} else {
			this.hide();
		}
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() > 0 ) {
			String	fileName = result.getMessage(); 
			callback.execute(fileName);
			this.hide(); 
		}
		else {
			new SimpleMessage("error"); 
		}
	}
}
