package myApp.client.utils;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

import myApp.client.service.InterfaceCallback;

public class SimpleMessage {

	public SimpleMessage(String title, String message, ImageResource icon){
		MessageBox  messageBox = new MessageBox (title, message); // , result.getMessage());
		messageBox.setIcon(icon);
		messageBox.show(); 
	}
	
	public SimpleMessage(String title, String message){
		MessageBox  messageBox = new MessageBox (title, ""); // , result.getMessage());
		messageBox.setWidth(300);
		messageBox.setIcon(MessageBox.ICONS.question());
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(message);
		messageBox.setMessage(safeEscapedHtml);
		messageBox.show(); 
	}

	public SimpleMessage(String title, String message, int width){

		MessageBox  messageBox = new MessageBox (title, ""); // , result.getMessage());
		messageBox.setWidth(width);
		messageBox.setIcon(MessageBox.ICONS.question());
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(message);
		messageBox.setMessage(safeEscapedHtml);
		messageBox.show(); 
	}

	public SimpleMessage(String message, int width, InterfaceCallback callback){

		MessageBox  messageBox = new MessageBox ("확인", ""); // , result.getMessage());
		messageBox.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent event) {
				callback.execute();
			}
		});
		messageBox.setWidth(width);
		messageBox.setIcon(MessageBox.ICONS.question());
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(message);
		messageBox.setMessage(safeEscapedHtml);
		messageBox.show(); 
	}

	public SimpleMessage(String message){
		AlertMessageBox alert = new AlertMessageBox("확인", ""); // , result.getMessage());
		alert.setWidth(300);
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(message);
		alert.setMessage(safeEscapedHtml);
		alert.show();
	}

	public SimpleMessage(String message, InterfaceCallback callback){
		AlertMessageBox alert = new AlertMessageBox("확인", message); // , result.getMessage());
		alert.setWidth(300);
		alert.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent event) {
				callback.execute();
			}
		}); 
		alert.show();
	}


}
