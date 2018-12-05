package myApp.client.vi.home.front;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

public class BodyAreaCenter extends BorderLayoutContainer {

	// 업무현황 버튼 3개
	public BodyAreaCenter() {

//		FirmHeadTitle titleLeft = new FirmHeadTitle();
		FirmHeadLeft west = new FirmHeadLeft();
		FirmHeadMiddle center = new FirmHeadMiddle();
		FirmHeadRight east = new FirmHeadRight();

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(0.33333, 210, new Margins(0, 0, 0, 0));

//		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
//		row00.add(titleLeft, rowLayout);
//		row00.add(new LabelToolItem(""), rowLayout);
//		row00.add(new LabelToolItem(""), rowLayout);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(west, rowLayout);
		row01.add(center, rowLayout);
		row01.add(east, rowLayout);

		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
//		layoutContainer.add(row00, new VerticalLayoutData(1, -1, new Margins(5, 0, 5, 0)));
		layoutContainer.add(row01, new VerticalLayoutData(1, -1, new Margins(20, 0, 5, 0)));
		this.add(layoutContainer);

	}
}
