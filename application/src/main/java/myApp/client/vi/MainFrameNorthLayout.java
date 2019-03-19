package myApp.client.vi;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.LoginUser;
import myApp.theme.tritium.custom.client.button.white.WhiteButtonCellAppearance;
import myApp.theme.tritium.custom.client.button.whiteGrey.WhiteGreyButtonCellAppearance;

public class MainFrameNorthLayout extends BorderLayoutContainer {

	Viewport viewport = new Viewport();

	public MainFrameNorthLayout() {

		HBoxLayoutContainer header = new HBoxLayoutContainer();
		header.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);

		// KFS
		Image image = new Image();
		image.setResource(ResourceIcon.INSTANCE.getLogo());
		image.setPixelSize(120, 40);
		header.add(image, new BoxLayoutData(new Margins(2, 0, 0, 10)));

		BoxLayoutData boxLayoutData = new BoxLayoutData(new Margins(0, 0, 0, 0)); 
		boxLayoutData.setFlex(1);
		
		header.add(new Label(), boxLayoutData);
		 
		String userInfo = LoginUser.getUserName() + " 님" ; ;
		userInfo = "<p style='color:#ffffff; font-size:14px; font-weight:normal'>" + userInfo + "</p>" ; 
		
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(userInfo);
		
		LabelToolItem label = new LabelToolItem(safeEscapedHtml); 
		
		header.add(label, new BoxLayoutData(new Margins(9, 0, 0, 0)));
		
		TextButton logoutButton = new TextButton(new TextButtonCell(new WhiteGreyButtonCellAppearance<>()));
		logoutButton.setText("");
		logoutButton.setIcon(ResourceIcon.INSTANCE.reload32Button());
		
		logoutButton.setBorders(false);
		logoutButton.setWidth(35);
		logoutButton.setHeight(32);
		logoutButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
	
				Window.Location.reload();
	
			}
		});
		header.add(logoutButton, new BoxLayoutData(new Margins(7, 40, 0, 0)));

		ContentPanel cp = new ContentPanel();
		cp.setBodyStyle("backgroundColor:#024059; color:red"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조

		cp.add(header, new BoxLayoutData(new Margins(4, 4, 4, 4)));
		
		cp.forceLayout();
		cp.setHeaderVisible(false);
		cp.getButtonBar().setHeight(0);
		cp.setBorders(true);
		
		this.add(cp);
	}
}