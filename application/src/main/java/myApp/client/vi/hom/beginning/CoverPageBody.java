package myApp.client.vi.hom.beginning;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.Hom02_Tab_Board;
import myApp.client.vi.hom.Hom02_Tab_News;
import myApp.client.vi.hom.StartPage;
import myApp.theme.tritium.custom.client.button.white.WhiteButtonCellAppearance;

import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;

public class CoverPageBody extends ContentPanel {

	private Hom02_Tab_Board westBoard = new Hom02_Tab_Board();
	private Hom02_Tab_News eastBoard = new Hom02_Tab_News();

	// 운용현황 라벨 3개
	public CoverPageBody(int WIDTH, int HEIGHT, StartPage startPage) {
		
		this.setHeaderVisible(false);
//		this.setBorders(true);

//		FirmHeadTitle titleLeft = new FirmHeadTitle();
//		CoverPageBodyLeft west = new CoverPageBodyLeft();
//		CoverPageBodyMiddle center = new CoverPageBodyMiddle();
//		CoverPageBodyRight east = new CoverPageBodyRight();

		VBoxLayoutContainer centerVBox = new VBoxLayoutContainer();
		centerVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		centerVBox.setWidth(WIDTH);
		centerVBox.setHeight(HEIGHT);

		SafeHtml label1Html = SafeHtmlUtils.fromTrustedString(	"<center>"
				+	"<div style='background-color: #1d7bbb; line-height:130%; border:5px solid silver'>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<div><img src='img/icon_left.png' width='32' height='40'></div>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<span style='font-weight:normal; font-size:1.1em;'>"
				+	"<font color='#eeeeee'>고객 성장이 당사의<br>성장입니다</font></span>"
				+	"<span style='font-size:0.1em;'><br><br></span>"
				+	"</div>"
				);
		LabelToolItem labelToolItem1 = new LabelToolItem(label1Html);
		labelToolItem1.setWidth(300);
//		labelToolItem1.setHeight(142);
//		labelToolItem1.setBorders(true);

		SafeHtml label2Html = SafeHtmlUtils.fromTrustedString(	"<center>"
				+	"<div style='background-color: #349da0; line-height:130%; border:5px solid silver'>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<div><img src='img/icon_middle.png' width='40' height='40'></div>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<span style='font-weight:normal; font-size:1.1em;'>"
				+	"<font color='#eeeeee'>회사별 상품 및 정책에 맞는<br>서비스를 제공합니다</font></span>"
				+	"<span style='font-size:0.1em;'><br><br></span>"
				+	"</div>"
				);
		LabelToolItem labelToolItem2 = new LabelToolItem(label2Html);
		labelToolItem2.setWidth(300);
//		labelToolItem2.setHeight(142);
//		labelToolItem2.setBorders(true);

		SafeHtml label3Html = SafeHtmlUtils.fromTrustedString(	"<center>"
				+	"<div style='background-color: #42339c; line-height:130%; border:5px solid silver'>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<div><img src='img/icon_right.png' width='40' height='40'></div>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<span style='font-weight:normal; font-size:1.1em;'>"
				+	"<font color='#eeeeee'>고객 전문성에 맞는<br>솔루션입니다</font></span>"
				+	"<span style='font-size:0.1em;'><br><br></span>"
				+	"</div>"
				);
		LabelToolItem labelToolItem3 = new LabelToolItem(label3Html);
		labelToolItem3.setWidth(300);
//		labelToolItem3.setHeight(142);
//		labelToolItem3.setBorders(true);

		HBoxLayoutContainer boxHBox = new HBoxLayoutContainer();
		boxHBox.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);

		boxHBox.add(labelToolItem1, new BoxLayoutData(new Margins(10, 5, 5, 1)));
		boxHBox.add(labelToolItem2, new BoxLayoutData(new Margins(10, 5, 5, 1)));
		boxHBox.add(labelToolItem3, new BoxLayoutData(new Margins(10, 5, 5, 1)));

		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-weight:bold; font-size:18px;'>운용현황</font> ");
//		Image verticalLine = new Image(ResourceIcon.INSTANCE.verticalLine());

		centerVBox.add(new LabelToolItem(button1Html), new BoxLayoutData(new Margins(25, 850, 0, 1)));
//		centerVBox.add(verticalLine, new BoxLayoutData(new Margins(7, 1, 1, 0)));
		centerVBox.add(boxHBox, new BoxLayoutData(new Margins(0, 1, 1, 1)));

//		this.add(centerVBox);
//		this.setWidth(WIDTH);
//		this.setHeight(HEIGHT);

//		HorizontalLayoutData rowLayout1 = new HorizontalLayoutData(0.25, 30, new Margins(10, 380, 0, 13));	// Double Column Size
//		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(0.25, 160, new Margins(10, 0, 0, 13));	// Double Column Size

		SafeHtml button4Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: transparent;'><left><font color='#606060' style='font-size:18px;'><p style='font-weight:bold;'>공지사항<br><br></p></font></div>");
		SafeHtml button5Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: transparent;'><left><font color='#606060' style='font-size:18px;'><p style='font-weight:bold;'>보도자료<br><br></p></font></div>");
	    TextButton textButton1 = new TextButton(new TextButtonCell(new WhiteButtonCellAppearance<>()));
	    TextButton textButton2 = new TextButton(new TextButtonCell(new WhiteButtonCellAppearance<>()));
		textButton1.setHTML(button4Html);
//		textButton1.setWidth(130);
//		textButton1.setHeight(60);
		textButton1.setBorders(false);
		textButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				StartPage.openTabPage(StartPage.tabPanel, "KFIA소식");
				StartPage.newsPage = 1;
//				Info.display("StartPage.newsPage : ",""+StartPage.newsPage);
				startPage.changePage("4");
			}
		});
		textButton2.setHTML(button5Html);
//		textButton2.setWidth(130);
//		textButton2.setHeight(120);
		textButton2.setBorders(false);
		textButton2.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				StartPage.openTabPage(StartPage.tabPanel, "KFIA소식");
				StartPage.newsPage = 3;
//				Info.display("StartPage.newsPage : ",""+StartPage.newsPage);
				startPage.changePage("4");
//				StartPage.changePage(xPosition, "4");
			}
		});

		HorizontalLayoutData rowLayout1 = new HorizontalLayoutData(0.5, 40, new Margins(0, 380, 0, 18));	// Double Column Size
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(0.5, 180, new Margins(0, 0, 0, 18));		// Double Column Size

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		row00.add(textButton1, rowLayout1);
		row00.add(textButton2, rowLayout1);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		westBoard.setWidth(970);
		eastBoard.setWidth(970);
		row01.add(westBoard, rowLayout2);
		row01.add(eastBoard, rowLayout2);

		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
		layoutContainer.add(row00, new VerticalLayoutData(1, 30, new Margins(0, 0, 0, 0)));
		layoutContainer.add(row01, new VerticalLayoutData(1, 160, new Margins(0, 0, 0, 0)));

		centerVBox.add(layoutContainer, new BoxLayoutData(new Margins(0, 1, 1, 1)));

		this.add(centerVBox);
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

	}
}
