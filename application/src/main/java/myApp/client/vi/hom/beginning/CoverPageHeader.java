package myApp.client.vi.hom.beginning;

import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;

public class CoverPageHeader extends ContentPanel {

	// 메인 이미지 페이지
	public CoverPageHeader(int WIDTH, int HEIGHT) {

		this.setHeaderVisible(false);
		VBoxLayoutContainer headerVBox = new VBoxLayoutContainer();
		headerVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

//		BorderLayoutData northLayoutData = new BorderLayoutData(10);
		HTML image = new HTML("<center><div> <img src='img/WBHTVisual.gif' style='border-bottom: 5px solid orange; ' width='"+ WIDTH +"' height='"+ HEIGHT +"'></div>");
//		HTML image = new HTML("<center><div> <img src='img/WBHTVisual.gif' width='"+ WIDTH +"' height='"+ HEIGHT +"'></div>");
//		this.setNorthWidget(image, northLayoutData);
		headerVBox.add(image , new BoxLayoutData(new Margins(0,0,0,0)));
		this.add(headerVBox);

	}
}
