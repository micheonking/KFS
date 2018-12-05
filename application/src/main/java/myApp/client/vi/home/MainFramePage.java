package myApp.client.vi.home;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.HideMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class MainFramePage extends BorderLayoutContainer {

	private MainNorthArea mainNorthArea = new MainNorthArea();
	private MainSouthArea mainSouthArea = new MainSouthArea();
	public static PlainTabPanel tabPanel = new PlainTabPanel();

	public MainFramePage() {

//		this.setHeading("시스템 관리자");
		this.setBorders(false);

		BorderLayoutData northLayoutData = new BorderLayoutData(70);
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setNorthWidget(mainNorthArea, northLayoutData);
		northLayoutData.setSplit(true);
		
		BorderLayoutData southLayoutData = new BorderLayoutData(70);
		southLayoutData.setMargins(new Margins(0, 0, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
		this.setSouthWidget(mainSouthArea, southLayoutData);
//		southLayoutData.setSplit(true);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData(250);
		centerLayoutData.setMargins(new Margins(0, 0, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
		centerLayoutData.setSplit(true);

//		PlainTabPanel tabPanel = new PlainTabPanel();
		tabPanel.setBorders(false);
		tabPanel.setBodyBorder(true);

		tabPanel.setHideMode(HideMode.DISPLAY);
		tabPanel.setTabScroll(false);
		
		tabPanel.add(new TabFrontPage(), "");

		TabOurCompany tabAboutUs = new TabOurCompany();
		tabPanel.add(tabAboutUs, "회사소개");

		TabOurBusiness tabBusiness = new TabOurBusiness();
		tabPanel.add(tabBusiness, "비지니스");
		
		TabOurSolution tabSolution = new TabOurSolution();
		tabPanel.add(tabSolution, "솔루션");
		
		TabOurNews tabNews = new TabOurNews();
		tabPanel.add(tabNews, "K-FS소식");
		
//		this.add(tabPanel, new MarginData(3));
		this.setCenterWidget(tabPanel, centerLayoutData);
		
	}
	
//	public ContentPanel getmainNorthArea() {
//
////		// 홈페이지 상단 회사로고
////		HTML northimage = new HTML("<left><div> <img src='img/logo.png' style='margin:10px 10px'> </div>");
//
////		HorizontalLayoutData rowLayout = new HorizontalLayoutData(1, 1, new Margins(0, 0, 0, 0));
////		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
////		row00.add(northimage, rowLayout);
//
//
//		HBoxLayoutContainer header = new HBoxLayoutContainer();
//		header.setPadding(new Padding(5));
//		header.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
//
//		// 홈페이지 상단 회사로고
//		String logoString = "<img onclick=\"location.reload();\" src=\"img/logo.png\"> </img>" ;// style='margin:10px 10px'> </img>" ;
//		SafeHtml logoHtml = SafeHtmlUtils.fromTrustedString(logoString);
//		LabelToolItem logoLabel = new LabelToolItem(logoHtml);
//		header.add(logoLabel, new BoxLayoutData(new Margins(10, 600, 0, 20)));
//
//		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<font style='font-size:16px;'>회사소개</font> ");
//		SafeHtml button2Html = SafeHtmlUtils.fromTrustedString("<font style='font-size:16px;'>비지니스</font> ");
//		SafeHtml button3Html = SafeHtmlUtils.fromTrustedString("<font style='font-size:16px;'>솔루션</font> ");
//		SafeHtml button4Html = SafeHtmlUtils.fromTrustedString("<font style='font-size:16px;'>K-FS소식</font> ");
//
//		BoxLayoutData boxLayoutData = new BoxLayoutData(new Margins(20, 1, 0, 50));
//		TextButton textButton1 = new TextButton("");
//		TextButton textButton2 = new TextButton("");
//		TextButton textButton3 = new TextButton("");
//		TextButton textButton4 = new TextButton("");
//		
////		header.add(new Label(), boxLayoutData);
//		textButton1.setHTML(button1Html);
//		textButton1.setWidth(90);
//		header.add(textButton1, boxLayoutData);
//		textButton1.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				openTabPage(tabPanel, "회사소개");
//			}
//		});
//		textButton2.setHTML(button2Html);
//		textButton2.setWidth(90);
//		header.add(textButton2, boxLayoutData);
//		textButton2.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				openTabPage(tabPanel, "비지니스");
//			}
//		});
//		textButton3.setHTML(button3Html);
//		textButton3.setWidth(90);
//		header.add(textButton3, boxLayoutData);
//		textButton3.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				openTabPage(tabPanel, "솔루션");
//			}
//		});
//		textButton4.setHTML(button4Html);
//		textButton4.setWidth(90);
//		header.add(textButton4, boxLayoutData);
//		textButton4.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				openTabPage(tabPanel, "K-FS소식");
//			}
//		});
//
//		ContentPanel cp = new ContentPanel();
//		cp.setBodyStyle("backgroundColor:ffffff"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조
//
//		cp.add(header);
////		cp.forceLayout();
//		cp.setHeaderVisible(false);
////		cp.setBorders(true);
////		cp.setHeight(60);
////		cp.getButtonBar().setHeight(0);
//		
////		VerticalLayoutContainer layout = new VerticalLayoutContainer();
////		layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);
////		layout.add(row00, new VerticalLayoutData(1, 1)); // , new Margins(22, 40, 0, 5)));
////
////		this.add(cp);
//		return cp;
//
//	}


	public static void openTabPage(TabPanel tabPanel, String pageName) {
		//
		Widget tabPage = tabPanel.findItem(pageName, true);
		tabPanel.setActiveWidget(tabPage);

	}
	
	public static HorizontalLayoutContainer FuncLabelToolItem(String textHtml) {
		// TODO Auto-generated constructor stub
		SafeHtml labelHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#909090' style='font-size:24px;font-weight:bold;'>"+ textHtml +"</font>");
		LabelToolItem labelToolItem = new LabelToolItem(labelHtml);
//		labelToolItem.setSize("200", "130");
//		labelToolItem.setLayoutData(new Margins(200, 0, 0, 0));

		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
		HorizontalLayoutData hld = new HorizontalLayoutData();
		Margins margins = new Margins();
		margins.setTop(60);
		hld.setMargins(margins);
		hlc.setWidth(210);
		hlc.setHeight(134);
		hlc.add(labelToolItem, hld);
		
		return hlc;
	}

	public static HBoxLayoutContainer FuncTextContents(String textHtml) {
		SafeHtml labelHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#015ca3' font-family='Nanum Gothic' size='5'><p style='font-weight:bold;'>"+ textHtml +"</p></font>");
		LabelToolItem labelToolItem = new LabelToolItem(labelHtml);
		
		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
		hblc.setHBoxLayoutAlign(HBoxLayoutAlign.BOTTOM);
		hblc.add(labelToolItem, new BoxLayoutData(new Margins(95, 600, 2, 40)));
		
		return hblc;
	}
}