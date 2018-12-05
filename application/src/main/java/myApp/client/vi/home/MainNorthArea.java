package myApp.client.vi.home;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class MainNorthArea extends BorderLayoutContainer {

	public MainNorthArea() {

		VBoxLayoutContainer center = new VBoxLayoutContainer();
		center.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		HBoxLayoutContainer header = new HBoxLayoutContainer();
		header.setPadding(new Padding(2));
		header.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		// 홈페이지 상단 회사로고
		SafeHtml logoHtml = SafeHtmlUtils.fromTrustedString("<img src='img/logo.png' style='margin:0px 0px'> </img>");
		CellButtonBase mainButton = new CellButtonBase<>();
//		mainButton.setIconAlign(IconAlign.TOP);
//		mainButton.setIcon(ResourceIcon.INSTANCE.getLogo());
		mainButton.setSize("154", "44");
		mainButton.setHTML(logoHtml);
		mainButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				MainFramePage.openTabPage(MainFramePage.tabPanel, "");
			}
		});
		header.add(mainButton, new BoxLayoutData(new Margins(15, 280, 0, 2)));
		
		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:18px;'>회사소개</font> ");
		SafeHtml button2Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:18px;'>비지니스</font> ");
		SafeHtml button3Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:18px;'>솔루션</font> ");
		SafeHtml button4Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:18px;'>K-FS소식</font> ");

		BoxLayoutData boxLayoutData = new BoxLayoutData(new Margins(25, 0, 0, 25));
		TextButton textButton1 = new TextButton("");
		TextButton textButton2 = new TextButton("");
		TextButton textButton3 = new TextButton("");
		TextButton textButton4 = new TextButton("");
		
//		header.add(new Label(), boxLayoutData);
		textButton1.setHTML(button1Html);
		textButton1.setWidth(120);
		textButton1.setHeight(40);
		textButton1.setBorders(true);
//		textButton1.setIcon(ResourceIcon.INSTANCE.blank());
//		textButton1.setIconAlign(IconAlign.TOP);
		header.add(textButton1, boxLayoutData);
		textButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				MainFramePage.openTabPage(MainFramePage.tabPanel, "회사소개");
			}
		});
		textButton2.setHTML(button2Html);
		textButton2.setWidth(120);
		textButton2.setHeight(40);
		textButton2.setBorders(true);
//		textButton2.setIconAlign(IconAlign.BOTTOM);
//		textButton2.setIcon(ResourceIcon.INSTANCE.blank());
		header.add(textButton2, boxLayoutData);
		textButton2.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				MainFramePage.openTabPage(MainFramePage.tabPanel, "비지니스");
			}
		});
		textButton3.setHTML(button3Html);
		textButton3.setWidth(120);
		textButton3.setHeight(40);
		textButton3.setBorders(true);
//		textButton3.setIconAlign(IconAlign.BOTTOM);
//		textButton3.setIcon(ResourceIcon.INSTANCE.blank());
		header.add(textButton3, boxLayoutData);
		textButton3.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				MainFramePage.openTabPage(MainFramePage.tabPanel, "솔루션");
			}
		});
		textButton4.setHTML(button4Html);
		textButton4.setWidth(120);
		textButton4.setHeight(40);
		textButton4.setBorders(true);
//		textButton4.setIconAlign(IconAlign.BOTTOM);
//		textButton4.setIcon(ResourceIcon.INSTANCE.blank());
		header.add(textButton4, boxLayoutData);
		textButton4.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				MainFramePage.openTabPage(MainFramePage.tabPanel, "K-FS소식");
			}
		});

		center.add(header);
		
		ContentPanel cp = new ContentPanel();
		cp.setBodyStyle("backgroundColor:ffffff"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조

		cp.add(center);
		
		cp.forceLayout();
		cp.setHeaderVisible(false);
//		cp.setBorders(true);
		cp.setHeight(60);
		cp.getButtonBar().setHeight(0);
		
		this.add(cp);
	}

}