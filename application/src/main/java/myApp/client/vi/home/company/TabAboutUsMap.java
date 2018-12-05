package myApp.client.vi.home.company;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.MainFramePage;

public class TabAboutUsMap extends ContentPanel {
	
	public TabAboutUsMap() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		HTML mapImage = new HTML("<div><img src='img/map.jpg' width='703' height='364'></div>"); //약도사진
		
		Image lineBar0 = new Image(ResourceIcon.INSTANCE.lineBar());

		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
		
		Label content = new HTML("<div class=\"add\"><strong>주소 </strong><br>07332 서울특별시 영등포구 의사당대로 143 금투센터 5층</div>");
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		Label content1 = new HTML("<div class=\"subway\"><strong>지하철 이용안내</strong><br>9호선 샛강역 1번출구　/　5호선 여의도역 5번출구</div>");
		content1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		gridVBox.add(MainFramePage.FuncTextContents("찾아오는길"));
		gridVBox.add(lineBar0,new BoxLayoutData(new Margins(0, 0, 0, 40)));
		gridVBox.add(content,new BoxLayoutData(new Margins(20, 0, 0, 40)));
		gridVBox.add(mapImage,new BoxLayoutData(new Margins(0, 0, 0, 40)));
		gridVBox.add(content1,new BoxLayoutData(new Margins(0, 0, 0, 40)));

		this.add(gridVBox);

	}
}
