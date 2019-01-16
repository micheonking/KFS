package myApp.client.vi.hom.beginning;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.StartPage;

public class CoverPageBodyMiddle extends ContentPanel {

	private Image horizontalBar1 = new Image(ResourceIcon.INSTANCE.horizontalBar());
	private Image horizontalBar2 = new Image(ResourceIcon.INSTANCE.horizontalBar());

	public CoverPageBodyMiddle() {

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(300, 125, new Margins(0, 0, 0, 0));

		//	<span style='font-weight:bold;font-size:1.2em;'>한국채권투자자문㈜ 대표이사 김형호 </span>
		//	1d7bbb	349da0	42339c
		SafeHtml label1Html = SafeHtmlUtils.fromTrustedString(	"<center>"
				+	"<div style='background-color: #349da0; line-height:130%; '>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<div><img src='img/icon_middle.png' width='40' height='40'></div>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<span style='font-weight:normal; font-size:1.2em;'>"
				+	"<font color='#eeeeee'>자문형<br>약 2조 5,132억</font></span>"
				+	"<span style='font-size:0.1em;'><br><br></span>"
				+	"</div>"
				);
		LabelToolItem labelToolItem1 = new LabelToolItem(label1Html);
		labelToolItem1.setWidth(300);
		labelToolItem1.setHeight(130);
		labelToolItem1.setBorders(true);

//		VBoxLayoutContainer boxVBox = new VBoxLayoutContainer();
//		boxVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
//
//		boxVBox.add(labelToolItem1);

		this.add(labelToolItem1);

////		// 2번 버튼
////		CellButtonBase btn = new CellButtonBase<>();
//////		TextButton btn = new TextButton("비지니스");
//////		btn.setWidth(50);
////		btn.setIcon(ResourceIcon.INSTANCE.iconMiddle());
//////		btn.setText("비지니스");
////		btn.setIconAlign(IconAlign.TOP);
////		btn.addSelectHandler(new SelectHandler() {
////			@Override
////			public void onSelect(SelectEvent event) {
//////				MainHomePage openTab = new MainHomePage();
////				startPage.openTabPage(startPage.tabPanel, "비지니스");
////			}
////		});
//		horizontalBar1.setVisibleRect(getAbsoluteLeft(), getAbsoluteTop(), 3, 142);
//		horizontalBar2.setVisibleRect(getAbsoluteLeft(), getAbsoluteTop(), 3, 142);
//
////		HorizontalLayoutData tempLayout = new HorizontalLayoutData(0.33333, -1, new Margins(0, 0, 0, 0));
////		HorizontalLayoutContainer tempCol1 = new HorizontalLayoutContainer();
////		tempCol1.add(new LabelToolItem(""), tempLayout);
////		tempCol1.add(horizontalBar1, tempLayout);
////		tempCol1.add(new LabelToolItem(""), tempLayout);
////		HorizontalLayoutContainer tempCol2 = new HorizontalLayoutContainer();
////		tempCol2.add(new LabelToolItem(""), tempLayout);
////		tempCol2.add(new LabelToolItem(""), tempLayout);
////		tempCol2.add(horizontalBar2, tempLayout);
//
//		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
////		row00.add(tempCol1, rowLayout);
//		row00.add(labelToolItem1, rowLayout);
////		row00.add(tempCol2, rowLayout);
//
////		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
////		row01.add(new LabelToolItem(""), rowLayout);
////		row01.add(new HTML("<center><font color='#606060' style='font-size:16px;'><p style='font-weight:bold;'>자문형<br>약 2조 5,132억</p></font>"),rowLayout);
////		row01.add(new LabelToolItem(""), rowLayout);
//
//		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
//		layoutContainer.add(row00, new VerticalLayoutData(1, -1, new Margins(5, 0, 5, 0)));
////		layoutContainer.add(row01, new VerticalLayoutData(1, -1, new Margins(90, 0, 5, 0)));
//
//		this.setBorders(true);
//		this.add(layoutContainer);
	}
}
