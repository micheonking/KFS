package myApp.client.vi.hom.beginning;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.vi.hom.Hom02_Tab_Board;
import myApp.client.vi.hom.Hom02_Tab_News;
import myApp.client.vi.hom.StartPage;

public class CoverPageFooter extends ContentPanel {
	
	private Hom02_Tab_Board west = new Hom02_Tab_Board();
	private Hom02_Tab_News east = new Hom02_Tab_News();
	private StartPage startPage;

	public CoverPageFooter(StartPage startPage) {

		this.startPage = startPage;
		this.setHeaderVisible(false);

		// Red Button
//		TextButtonCell redCell = new TextButtonCell(new RedButtonCellAppearance<>());
//		TextButton redButton = new TextButton("공지사항");
//		redButton.setText("공지사항");

		HorizontalLayoutData rowLayout1 = new HorizontalLayoutData(0.25, 30, new Margins(10, 380, 0, 13));	// Double Column Size
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(0.25, 160, new Margins(10, 0, 0, 13));	// Double Column Size

		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: #ffffff;'><left><font color='#606060' style='font-size:18px;'><p style='font-weight:bold;'>공지사항<br><br></p></font></div>");
		SafeHtml button2Html = SafeHtmlUtils.fromTrustedString("<div style='background-color: #ffffff;'><left><font color='#606060' style='font-size:18px;'><p style='font-weight:bold;'>보도자료<br><br></p></font></div>");
		TextButton textButton1 = new TextButton("");
		TextButton textButton2 = new TextButton("");
		textButton1.setHTML(button1Html);
		textButton1.setWidth(130);
		textButton1.setHeight(30);
		textButton1.setBorders(false);
		textButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				StartPage.openTabPage(StartPage.tabPanel, "KFIA소식");
//				startPage.changePage("4");
			}
		});
		textButton2.setHTML(button2Html);
		textButton2.setWidth(130);
		textButton1.setHeight(40);
		textButton2.setBorders(false);
		textButton2.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				StartPage.openTabPage(StartPage.tabPanel, "KFIA소식");
				Info.display("","1");
//				startPage.changePage("4");
			}
		});

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		row00.add(new LabelToolItem(""), rowLayout1);
		row00.add(textButton1, rowLayout1);
		row00.add(textButton2, rowLayout1);
		row00.add(new LabelToolItem(""), rowLayout1);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new LabelToolItem(""), rowLayout2);
		row01.add(west, rowLayout2);
		row01.add(east, rowLayout2);
		row01.add(new LabelToolItem(""), rowLayout2);

		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
		layoutContainer.add(row00, new VerticalLayoutData(1, 30, new Margins(0, 0, 0, 0)));
		layoutContainer.add(row01, new VerticalLayoutData(1, 160, new Margins(0, 0, 0, 0)));
		this.add(layoutContainer);
		
	}
}