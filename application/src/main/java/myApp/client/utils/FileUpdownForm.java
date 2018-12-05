package myApp.client.utils;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceCallback;

public class FileUpdownForm {
	private final Logger   logger = Logger.getLogger(this.getClass().getName());
	private FormPanel form = new FormPanel();
	private FileUpload fileUpload = new FileUpload(); // GWT file upload widget 
	private InterfaceCallbackResult callbackResult;
	
	public FileUpdownForm() {
		
		fileUpload.setName("fileField"); // 이름은 필수로 주어야 한다. 

	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
		form.setSize("60", "100"); 
		form.add(fileUpload);
	}
	
	public void submit(String actionUrl, InterfaceCallback callback) {
		this.form.setAction(actionUrl);
		this.form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent event) {
				callback.execute();
			}
		}); 
		this.form.submit();
	}

	public void submit(String actionUrl, InterfaceCallbackResult callback) {
		callbackResult = callback;
		this.form.setAction(actionUrl);
		this.form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent event) {
				
				String result = event.getResults().substring(event.getResults().indexOf("|")+1);
				result = result.substring(0, result.indexOf("|"));
				
				callbackResult.execute(result);
				callbackResult = null;
			}
		}); 
		this.form.submit();
	}

	public FormPanel getForm() {
		return form; 
	}
	
	public void click() {
		fileUpload.click();
	}
	
	public void addChangeHandler(ChangeHandler changeHandler) {
		logger.info(""+changeHandler.toString());
		this.fileUpload.addChangeHandler(changeHandler); 
	}
	
	public String getFileName() {
		int idx = fileUpload.getFilename().lastIndexOf("\\") + 1;
		return fileUpload.getFilename().substring(idx); 
	}

	public void setInit() {
		this.getForm().reset();
	}
}
