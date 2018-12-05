package myApp.client.vi.home.front;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.MainFramePage;

public class FirmHeadMiddle extends BorderLayoutContainer {

	private Image horizontalBar1 = new Image(ResourceIcon.INSTANCE.horizontalBar());
	private Image horizontalBar2 = new Image(ResourceIcon.INSTANCE.horizontalBar());

	public FirmHeadMiddle() {

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(0.33333, 100, new Margins(0, 0, 0, 0));
		// 2번 버튼
		CellButtonBase btn = new CellButtonBase<>();
//		TextButton btn = new TextButton("비지니스");
//		btn.setWidth(50);
		btn.setIcon(ResourceIcon.INSTANCE.iconMiddle());
//		btn.setText("비지니스");
		btn.setIconAlign(IconAlign.TOP);
		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				MainHomePage openTab = new MainHomePage();
				MainFramePage.openTabPage(MainFramePage.tabPanel, "비지니스");
			}
		});
		horizontalBar1.setVisibleRect(getAbsoluteLeft(), getAbsoluteTop(), 3, 142);
		horizontalBar2.setVisibleRect(getAbsoluteLeft(), getAbsoluteTop(), 3, 142);

		HorizontalLayoutData tempLayout = new HorizontalLayoutData(0.33333, -1, new Margins(0, 0, 0, 0));
		HorizontalLayoutContainer tempCol1 = new HorizontalLayoutContainer();
		tempCol1.add(new LabelToolItem(""), tempLayout);
		tempCol1.add(horizontalBar1, tempLayout);
		tempCol1.add(new LabelToolItem(""), tempLayout);
		HorizontalLayoutContainer tempCol2 = new HorizontalLayoutContainer();
		tempCol2.add(new LabelToolItem(""), tempLayout);
		tempCol2.add(new LabelToolItem(""), tempLayout);
		tempCol2.add(horizontalBar2, tempLayout);

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		row00.add(tempCol1, rowLayout);
		row00.add(btn, rowLayout);
		row00.add(tempCol2, rowLayout);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new LabelToolItem(""), rowLayout);
		row01.add(new HTML("<center><font color='#606060' style='font-size:16px;'><p style='font-weight:bold;'>회사별 상품 및 정책에 맞는<br>서비스를 제공합니다</p></font>"),rowLayout);
		row01.add(new LabelToolItem(""), rowLayout);

		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
		layoutContainer.add(row00, new VerticalLayoutData(1, -1, new Margins(5, 0, 5, 0)));
		layoutContainer.add(row01, new VerticalLayoutData(1, -1, new Margins(90, 0, 5, 0)));
		this.add(layoutContainer);

	}
}
