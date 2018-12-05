package myApp.client.vi.home.front;

import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

public class BodyAreaNorth extends BorderLayoutContainer {

	// 메인 이미지 페이지
	public BodyAreaNorth() {

		BorderLayoutData northLayoutData = new BorderLayoutData(10);
		HTML image = new HTML("<center><div> <img src='img/visual.gif' style='border-bottom: 5px solid orange; ' width='1950' height='378'></div>");
		this.setNorthWidget(image, northLayoutData);

	}
}
