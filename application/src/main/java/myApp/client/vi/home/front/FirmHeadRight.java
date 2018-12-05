package myApp.client.vi.home.front;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.MainFramePage;

public class FirmHeadRight extends BorderLayoutContainer {

	public FirmHeadRight() {

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(0.33333, 100, new Margins(0, 0, 0, 0));
		// 3번 버튼
		CellButtonBase btn = new CellButtonBase<>();
//		TextButton btn = new TextButton("솔루션");
//		btn.setWidth(50);
		btn.setIcon(ResourceIcon.INSTANCE.iconRight());
//		btn.setText("솔루션");
		btn.setIconAlign(IconAlign.TOP);
		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				MainHomePage openTab = new HomePage();
				MainFramePage.openTabPage(MainFramePage.tabPanel, "솔루션");
			}
		});
		
		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		row00.add(btn, rowLayout);
		row00.add(new LabelToolItem(""), rowLayout);
		row00.add(new LabelToolItem(""), rowLayout);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new HTML("<center><font color='#606060' style='font-size:16px;'><p style='font-weight:bold;'>고객 전문성에 맞는<br>솔루션입니다</p></font>"),rowLayout);
		row01.add(new LabelToolItem(""), rowLayout);
		row01.add(new LabelToolItem(""), rowLayout);

		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
		layoutContainer.add(row00, new VerticalLayoutData(1, -1, new Margins(5, 0, 5, 0)));
		layoutContainer.add(row01, new VerticalLayoutData(1, -1, new Margins(90, 0, 5, 0)));
		this.add(layoutContainer);

	}
}
