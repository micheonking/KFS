package myApp.client.vi.hom;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.util.Margins;
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
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.report.Notification;
import myApp.client.vi.hom.report.OfficialNotice;
import myApp.client.vi.hom.report.ReportNews;
import myApp.theme.tritium.custom.client.button.white.WhiteButtonCellAppearance;

public class TabReportNews extends ContentPanel {

	private Notification tabNotification  = new Notification();
	private OfficialNotice tabOfficialNotice  = new OfficialNotice();
	private ReportNews tabNews  = new ReportNews();
	
	ContentPanel contentPanel  = new ContentPanel();

	public TabReportNews() {
		this.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resize();
			}
		});
		resize();
	}
	
	private void resize() {

		this.setHeaderVisible(false);
		this.setBorders(false);

		VBoxLayoutContainer headerVBox = new VBoxLayoutContainer();
		headerVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		VBoxLayoutContainer centerVBox = new VBoxLayoutContainer();
		centerVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		HBoxLayoutContainer menuHBar = new HBoxLayoutContainer();
		menuHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		BoxLayoutData boxLayoutData = new BoxLayoutData();

		VBoxLayoutContainer menuVBox = new VBoxLayoutContainer();
		menuVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		Margins lineImageMargins = new Margins(0, 0, 0, 0);
		Margins buttonMargins = new Margins(1, 3, 1, 3);

		Image lineImage0 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage1 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage2 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage3 = new Image(ResourceIcon.INSTANCE.verticalBar());

		menuVBox.add(StartPage.getLabelToolItem("K-FS소식"));
		
		menuVBox.add(lineImage0, new BoxLayoutData(new Margins(20, 0, 0, 0)));
		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: transparent;'><font color='#606060' style='font-size:14px;'>ㆍ공지사항　　　　　　</font></div> ");
		SafeHtml button2Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: transparent;'><font color='#606060' style='font-size:14px;'>ㆍ공시사항　　　　　　</font></div> ");
		SafeHtml button3Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: transparent;'><font color='#606060' style='font-size:14px;'>ㆍ보도자료　　　　　　</font></div> ");

		TextButton menuButton1 = new TextButton(new TextButtonCell(new WhiteButtonCellAppearance<>()));
		TextButton menuButton2 = new TextButton(new TextButtonCell(new WhiteButtonCellAppearance<>()));
		TextButton menuButton3 = new TextButton(new TextButtonCell(new WhiteButtonCellAppearance<>()));

//		CellButtonBase menuButton1 = new CellButtonBase<>();
		menuButton1.setSize(StartPage.BTN_WIDTH, StartPage.BTN_HEIGHT);
		menuButton1.setHTML(button1Html);
//		menuButton1.setBorders(true);
		menuButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getNotification();
			}
		});
		menuVBox.add(menuButton1, new BoxLayoutData(buttonMargins));
		menuVBox.add(lineImage1, new BoxLayoutData(lineImageMargins));

//		CellButtonBase menuButton2 = new CellButtonBase<>();
		menuButton2.setSize(StartPage.BTN_WIDTH, StartPage.BTN_HEIGHT);
		menuButton2.setHTML(button2Html);
//		menuButton2.setBorders(true);
		menuButton2.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getOfficialNotice();
			}
		});
		menuVBox.add(menuButton2, new BoxLayoutData(buttonMargins));
		menuVBox.add(lineImage2, new BoxLayoutData(lineImageMargins));

//		CellButtonBase menuButton3 = new CellButtonBase<>();
		menuButton3.setSize(StartPage.BTN_WIDTH, StartPage.BTN_HEIGHT);
		menuButton3.setHTML(button3Html);
//		menuButton3.setBorders(true);
		menuButton3.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getNews();
			}
		});
		menuVBox.add(menuButton3, new BoxLayoutData(buttonMargins));
		menuVBox.add(lineImage3, new BoxLayoutData(lineImageMargins));
//		menuVBox.setWidth(StartPage.MENU_WIDTH);
//		menuVBox.setHeight(StartPage.CON_HEIGHT);
		menuHBar.add(menuVBox, boxLayoutData);

		totalHBar.add(menuHBar);
		
		switch (StartPage.newsPage) {
		case 1 :
			totalHBar.add(getNotification());
			break;
		case 2 :
			totalHBar.add(getOfficialNotice());
			break;
		case 3 :
			totalHBar.add(getNews());
			break;
		default :
			totalHBar.add(getNotification());
			break;
		}
//		totalHBar.add(getNotification());
		centerVBox.add(totalHBar);

		headerVBox.add(centerVBox);
		this.add(headerVBox);

	}

	private ContentPanel getNotification() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(StartPage.CON_WIDTH);
		contentPanel.setHeight(StartPage.CON_HEIGHT);
		contentPanel.setWidget(tabNotification);

		return contentPanel;
	}

	private ContentPanel getOfficialNotice() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(StartPage.CON_WIDTH);
		contentPanel.setHeight(StartPage.CON_HEIGHT);
		contentPanel.setWidget(tabOfficialNotice);

		return contentPanel;
	}
	
	private ContentPanel getNews() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(StartPage.CON_WIDTH);
		contentPanel.setHeight(StartPage.CON_HEIGHT);
		contentPanel.setWidget(tabNews);

		return contentPanel;
	}

}
