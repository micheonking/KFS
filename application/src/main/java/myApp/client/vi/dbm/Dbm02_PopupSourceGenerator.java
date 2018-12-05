package myApp.client.vi.dbm;

import com.google.gwt.user.client.ui.TextArea;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class Dbm02_PopupSourceGenerator extends Window {

	public Dbm02_PopupSourceGenerator(String actionCode) {

//		this.setHeading("Generating...");

		TextButton closeButton = new TextButton("닫기");
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				Info.display("Properties Generator", actionCode);
				hide();
			}
		});
		this.getButtonBar().add(closeButton); 

		TextArea textArea = new TextArea();
		textArea.setText(actionCode);
		this.add(textArea);
		textArea.setPixelSize(1100, 550);

	}

}