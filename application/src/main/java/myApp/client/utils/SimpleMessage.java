package myApp.client.utils;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class SimpleMessage {

	public SimpleMessage(String title, String message, ImageResource icon){
		MessageBox  messageBox = new MessageBox (title, message); // , result.getMessage());
		messageBox.setIcon(icon);
		messageBox.show(); 
	}
	
	public SimpleMessage(String title, String message){
		MessageBox  messageBox = new MessageBox (title, message); // , result.getMessage());
		messageBox.setIcon(MessageBox.ICONS.question());
		messageBox.show(); 
	}
	
	public SimpleMessage(String message){
		AlertMessageBox alert = new AlertMessageBox("확인", message); // , result.getMessage());
		alert.show();
	}

}
