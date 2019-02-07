package myApp.client.vi.hom;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class StartPage extends BorderLayoutContainer {

	protected final int WIN_WIDTH = Window.getClientWidth();
	protected final int WIN_HEIGHT = Window.getClientHeight();
	protected final int HEADER_HEIGHT = 70;
	protected final int FOOTER_HEIGHT = 50;
	protected final int CENTER_HEIGHT = 250;
	protected final static int TITLE_HEIGHT = 100;

	protected static final int MAX_WIDTH = 1024;

	protected static final int MENU_WIDTH = 180;
	protected static final int MENU_HEIGHT = 900;
	protected static final int CON_WIDTH = 770;
	protected static final int CON_HEIGHT = Window.getClientHeight() - 253;
	protected static final String WBTN_WIDTH = "80";
	protected static final String WBTN_HEIGHT = "30";
	protected static final String BTN_WIDTH = ""+MENU_WIDTH;
	protected static final String BTN_HEIGHT = "40";

	protected static final int NOTE_HEIGHT = 100;	//	내용부분 높이조절용 : 높이에서 뺄 Size...
	public static final int SNOTE_HEIGHT = 300;	//	내용부분 높이조절용 : 높이에서 뺄 Size...
	
	public static int newsPage = 1;
	public static int CURRENTWIDTH = 770;
	public static int CURRENTHEIGHT = 0;
	static String CURRENTPAGE = "0";

	private StartPageHeader startPageHeader = new StartPageHeader(this);
	private StartPageFooter startPageFooter = new StartPageFooter();
//	public static PlainTabPanel tabPanel = new PlainTabPanel();
	
	public StartPage() {

		this.setBorders(false);

		BorderLayoutData northLayoutData = new BorderLayoutData(HEADER_HEIGHT);
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setNorthWidget(startPageHeader, northLayoutData);
//		northLayoutData.setSplit(true);

		BorderLayoutData southLayoutData = new BorderLayoutData(FOOTER_HEIGHT);
		southLayoutData.setMargins(new Margins(0, 0, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
		this.setSouthWidget(startPageFooter, southLayoutData);
//		southLayoutData.setSplit(true);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData(CENTER_HEIGHT);
		centerLayoutData.setMargins(new Margins(0, 0, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
//		centerLayoutData.setSplit(true);

		this.changePage("0");

	}

	public void changePage(String pageName) {
		
		BorderLayoutData centerLayoutData = new BorderLayoutData(CENTER_HEIGHT);
		centerLayoutData.setMargins(new Margins(0, 0, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
		centerLayoutData.setSplit(true);

		switch (pageName) {
		case "1" :
			TabCompanyOpenning tabCompanyOpenning = new TabCompanyOpenning();
			this.setCenterWidget(tabCompanyOpenning, centerLayoutData);
			break;
		case "2" :
			TabEntrustInvestments tabEntrustInvestments = new TabEntrustInvestments();
			this.setCenterWidget(tabEntrustInvestments, centerLayoutData);
			break;
		case "3" :
			TabProductInformation tabProductInformation = new TabProductInformation();
			this.setCenterWidget(tabProductInformation, centerLayoutData);
			break;
		case "4" :
			TabReportNews tabReportNews = new TabReportNews();
			this.setCenterWidget(tabReportNews, centerLayoutData);
			break;
		default :
			TabBeginnigPage tabBeginnigPage = new TabBeginnigPage(this);
			this.setCenterWidget(tabBeginnigPage, centerLayoutData);
			break;
		}
		this.onLoad();
	}

//	public static ContentPanel getLabelToolItem(int xPosition, String textHtml) {
//
//		ContentPanel contentPanel = new ContentPanel();
//		contentPanel.setBodyStyle("backgroundColor:#023d69");
//		contentPanel.setHeaderVisible(false);
//		contentPanel.setBorders(false);
//		contentPanel.setWidth(Window.getClientWidth()); 
//		contentPanel.setHeight(TITLE_HEIGHT);
//
//		Label labelHtml = new HTML(	"<div style='background-color: #023d69; line-height:90%; '>"
//								+	"<span style='font-weight:bold; font-size:2.0em;'><font color='#ffffff'>" + textHtml
//								+	"&nbsp;&nbsp;</font></span></div>");
//
//		if (xPosition < 20 ) {
//			xPosition = 20;
//		}
//		contentPanel.add(labelHtml, new BoxLayoutData(new Margins(25, 0, 0, xPosition)));
//		
//		return contentPanel;
//	}

	public static HorizontalLayoutContainer getLabelToolItem(String textHtml) {
		// TODO Auto-generated constructor stub
		SafeHtml labelHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#909090' style='font-family:NanumGothic;font-size:2.1em;font-weight:bold;'>"+ textHtml +"</font>");
		LabelToolItem labelToolItem = new LabelToolItem(labelHtml);
//		labelToolItem.setSize("200", "130");
//		labelToolItem.setLayoutData(new Margins(200, 0, 0, 0));

		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
		HorizontalLayoutData hld = new HorizontalLayoutData();
		Margins margins = new Margins();
		margins.setTop(60);
		hld.setMargins(margins);
		hlc.setWidth(MENU_WIDTH);
		hlc.setHeight(TITLE_HEIGHT);
		hlc.add(labelToolItem, hld);
		
		return hlc;
	}
	public static HBoxLayoutContainer getTextContents(String textHtml) {
		SafeHtml labelHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#015ca3' style='font-family:NanumGothic;font-size:2.1em;font-weight:bold;'>"+ textHtml + "</font>");
		LabelToolItem labelToolItem = new LabelToolItem(labelHtml);
		
		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
		hblc.setHBoxLayoutAlign(HBoxLayoutAlign.BOTTOM);
		hblc.add(labelToolItem, new BoxLayoutData(new Margins(65, 600, 10, 30)));
		
		return hblc;
	}
//	public static HBoxLayoutContainer getTextContents(String textHtml) {
//		SafeHtml labelHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#015ca3' font-family='Nanum Gothic' size='5'><p style='font-weight:bold;'>"+ textHtml +"</p></font>");
//		LabelToolItem labelToolItem = new LabelToolItem(labelHtml);
//		
//		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
//		hblc.setHBoxLayoutAlign(HBoxLayoutAlign.BOTTOM);
//		hblc.add(labelToolItem, new BoxLayoutData(new Margins(95, 600, 2, 40)));
//		
//		return hblc;
//	}
}