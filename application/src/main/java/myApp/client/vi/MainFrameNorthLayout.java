package myApp.client.vi;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.LoginUser;
import myApp.theme.tritium.custom.client.button.whiteGrey.WhiteGreyButtonCellAppearance;

public class MainFrameNorthLayout extends BorderLayoutContainer {

	Viewport viewport = new Viewport();

	public MainFrameNorthLayout() {

		HBoxLayoutContainer header = new HBoxLayoutContainer();
//		header.setPadding(new Padding(5));
		header.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);

//		BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
//		flex.setFlex(1);
		    
		// KFIA
		Image image = new Image();
		image.setResource(ResourceIcon.INSTANCE.getLogo());
		image.setPixelSize(330, 26);
		header.add(image, new BoxLayoutData(new Margins(10, 0, 0, 20)));

		BoxLayoutData boxLayoutData = new BoxLayoutData(new Margins(0, 0, 0, 0)); 
		boxLayoutData.setFlex(1);
		
		header.add(new Label(), boxLayoutData);
		 
//		String userInfo = LoginUser.getUserName() + " 님" ; ;
//		userInfo = "<p style='color:#808080; font-size:14px; font-weight:bold'>" +  userInfo + "</p>" ;  
//		
//		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(userInfo);
//		
//		LabelToolItem label = new LabelToolItem(safeEscapedHtml); 
//		
//		header.add(label, new BoxLayoutData(new Margins(14, 0, 0, 0)));
		// String userInfo = LoginUser.getCompanyId() + " " + LoginUser.getUserName();	
		String userInfo = LoginUser.getUserName() + " 님" ; ;
		userInfo = "<p style='color:#808080; font-size:14px; font-weight:normal'>" + userInfo + "</p>" ; 
		
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(userInfo);
		
		LabelToolItem label = new LabelToolItem(safeEscapedHtml); 
		
		header.add(label, new BoxLayoutData(new Margins(9, 0, 0, 0)));
		
		TextButton logoutButton = new TextButton(new TextButtonCell(new WhiteGreyButtonCellAppearance<>()));
		logoutButton.setText("");
		logoutButton.setIcon(ResourceIcon.INSTANCE.closeButton());
		
		logoutButton.setBorders(false);
//		logoutButton.addSelectHandler(new SelectHandler() {
		
//		SafeHtml logoutButtonHtml = SafeHtmlUtils.fromTrustedString("<div style='background-color: #1d7bbb;'><font color='#dddddd' style='font-size:16px; '>종료</font></div>" );
//		TextButton logoutButton = new TextButton("종료");
//		logoutButton.setHTML(logoutButtonHtml);
		logoutButton.setWidth(65);
		logoutButton.setHeight(30);
		logoutButton.setBorders(true);
		logoutButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
	
				Window.Location.reload();
	
			}
		});
		header.add(logoutButton, new BoxLayoutData(new Margins(7, 40, 0, 0)));
//		header.setHeight(90);

//		Image borderBar = new Image(ResourceIcon.INSTANCE.borderBox());

		ContentPanel cp = new ContentPanel();
		cp.setBodyStyle("backgroundColor:white; color:red"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조

		cp.add(header);
//		cp.add(header, new BoxLayoutData(new Margins(0, 0, 0, 0)));
//		cp.add(borderBar, new BoxLayoutData(new Margins(0, 0, 0, 0)));
		
		cp.forceLayout();
		cp.setHeaderVisible(false);
//		cp.setHeight(90);
		cp.getButtonBar().setHeight(0);
		cp.setBorders(true);
		
		this.add(cp);
	}
}