package myApp.client.vi.hom.beginning;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.StartPage;

public class CoverPageBodyRight extends ContentPanel {

	public CoverPageBodyRight() {

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(300, 125, new Margins(0, 0, 0, 0));
		
		//	<span style='font-weight:bold;font-size:1.2em;'>한국채권투자자문㈜ 대표이사 김형호 </span>
		//	1d7bbb	349da0	42339c
		SafeHtml label1Html = SafeHtmlUtils.fromTrustedString(	"<center>"
				+	"<div style='background-color: #42339c; line-height:130%; '>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<div><img src='img/icon_right.png' width='40' height='40'></div>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<span style='font-weight:normal; font-size:1.2em;'>"
				+	"<font color='#eeeeee'>일임형<br>약 6,294억</font></span>"
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

////		// 3번 버튼
////		CellButtonBase btn = new CellButtonBase<>();
//////		TextButton btn = new TextButton("솔루션");
//////		btn.setWidth(50);
////		btn.setIcon(ResourceIcon.INSTANCE.iconRight());
//////		btn.setText("솔루션");
////		btn.setIconAlign(IconAlign.TOP);
////		btn.addSelectHandler(new SelectHandler() {
////			@Override
////			public void onSelect(SelectEvent event) {
//////				MainHomePage openTab = new HomePage();
////				startPage.openTabPage(startPage.tabPanel, "솔루션");
////			}
////		});
//		
//		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
//		row00.add(labelToolItem1, rowLayout);
////		row00.add(new LabelToolItem(""), rowLayout);
////		row00.add(new LabelToolItem(""), rowLayout);
//
////		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
////		row01.add(new HTML("<center><font color='#606060' style='font-size:16px;'><p style='font-weight:bold;'>일임형<br>약 6,294억</p></font>"),rowLayout);
////		row01.add(new LabelToolItem(""), rowLayout);
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
