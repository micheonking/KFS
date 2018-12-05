package myApp.client.vi.apr;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;

import myApp.client.resource.ResourceIcon;
import myApp.client.utils.InterfaceCallbackResult;

public class Apr01_Edit_RejectMessage extends Window {

	private InterfaceCallbackResult callback;
	private TextArea rejectMessage = new TextArea();
	
	public Apr01_Edit_RejectMessage() {
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.setWidth(450);
		this.setHeight(250);
		this.setHeading("반려사유");
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(rejectMessage, new MarginData(10));

		this.add(cp);
	}
	
	public void setMessage(String msg) {
		
		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.show();
		this.rejectMessage.setText(msg);
	}
	
	
	public void editMessage(InterfaceCallbackResult callback) {
		
		this.callback = callback;
		
		TextButton rejectButton = new TextButton("반려");
		rejectButton.setWidth(60);
		rejectButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				reject();
			}
		});
		this.getButtonBar().add(rejectButton);
		
		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.show();
	}
	
	private void reject() {
		this.callback.execute(rejectMessage.getText());
		hide();
	}

}