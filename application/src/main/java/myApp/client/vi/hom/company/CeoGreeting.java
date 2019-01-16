package myApp.client.vi.hom.company;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.XTemplate;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.StartPage;
import myApp.client.vi.hom.company.OperatingOrganization.SimpleHTMLTemplate;

public class CeoGreeting extends ContentPanel {
	
	SimpleHTMLTemplate htmlTemplate = GWT.create(SimpleHTMLTemplate.class);
	
	private String actionCode = "retrieve" ;
	private HtmlLayoutContainer htmlLayoutContainer ; 
	
	public interface SimpleHTMLTemplate extends XTemplates {
		@XTemplate(source="HTMLCeo.html") 
		SafeHtml setParam(String actionCode);
	}
	
	public CeoGreeting() {
		this.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resize();
			}
		});
		resize();
	}
	
	private void resize() {
//	public CeoGreeting1() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		Margins getTextMargins = new Margins(0, 0, 15, 0);
		Margins totalHBarMargins = new Margins(5, 0, 5, 30);
		Margins lineBar0Margins = new Margins(10, 0, 20, 30);

		Image lineBar0 = new Image(ResourceIcon.INSTANCE.verticalTitle());
		
		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		this.htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.setParam(this.actionCode));

		htmlLayoutContainer.setWidth(StartPage.CURRENTWIDTH);
		StartPage.CURRENTHEIGHT = Window.getClientHeight() - StartPage.SNOTE_HEIGHT;
		htmlLayoutContainer.setHeight(StartPage.CURRENTHEIGHT);

		totalHBar.add(htmlLayoutContainer, new BoxLayoutData(totalHBarMargins));
		
		gridVBox.add(StartPage.getTextContents("CEO 인사말"), new BoxLayoutData(getTextMargins));
		gridVBox.add(lineBar0, new BoxLayoutData(lineBar0Margins));
		gridVBox.add(totalHBar, new BoxLayoutData(new Margins(1, 0, 1, 0)));

		this.add(gridVBox);

	}
}
