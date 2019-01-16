package myApp.client.vi.home.company;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.MainFramePage;

public class TabAboutUsMap extends ContentPanel {
	
	public interface HTMLTemplate extends XTemplates {
		//웹에디터 HTML 설정
		@XTemplate(source="raod.html")
		SafeHtml getTemplate();
	}
	
	public TabAboutUsMap() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
//		gridVBox.setWidth(800);
//		gridVBox.setHeight(1000);

		
		Image lineBar0 = new Image(ResourceIcon.INSTANCE.lineBar());

		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
		
		
		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer content = new HtmlLayoutContainer(htmlTemplate.getTemplate());

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
		totalHBar.add(content, new BoxLayoutData(new Margins(20, 0, 5, 45)));
		
		gridVBox.add(MainFramePage.FuncTextContents("찾아오시는길"));
		gridVBox.add(lineBar0,new BoxLayoutData(new Margins(0, 0, 0, 40)));
		gridVBox.add(totalHBar);
		this.add(gridVBox);
	}
}
