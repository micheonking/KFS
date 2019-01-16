package myApp.client.vi.hom.entrust;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.XTemplate;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.StartPage;
import myApp.client.vi.hom.company.YourWay.SimpleHTMLTemplate;

public class EntrustInvestment extends ContentPanel {
	SimpleHTMLTemplate htmlTemplate = GWT.create(SimpleHTMLTemplate.class);
	
	private String actionCode = "retrieve" ;
	private HtmlLayoutContainer htmlLayoutContainer ; 
	
	public interface SimpleHTMLTemplate extends XTemplates {
		@XTemplate(source="HTMLEntrustInvestment.html") 
		SafeHtml setParam(String actionCode);
	}
	
	public EntrustInvestment() {
		this.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resize();
			}
		});
		resize();
	}
	
	private void resize() {
//	public EntrustInvestment() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		Margins getTextMargins = new Margins(0, 0, 15, 0);
		Margins totalHBarMargins = new Margins(5, 0, 5, 30);
		Margins lineBar0Margins = new Margins(10, 0, 20, 30);

		Image lineBar0 = new Image(ResourceIcon.INSTANCE.verticalBar());

		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		this.htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.setParam(this.actionCode));

		htmlLayoutContainer.setWidth(StartPage.CURRENTWIDTH);
		StartPage.CURRENTHEIGHT = Window.getClientHeight() - StartPage.SNOTE_HEIGHT;
		htmlLayoutContainer.setHeight(StartPage.CURRENTHEIGHT);

		totalHBar.add(htmlLayoutContainer, new BoxLayoutData(totalHBarMargins));
		
		gridVBox.add(StartPage.getTextContents("찾아오시는길"), new BoxLayoutData(getTextMargins));
		gridVBox.add(lineBar0, new BoxLayoutData(lineBar0Margins));
		gridVBox.add(totalHBar, new BoxLayoutData(new Margins(1, 0, 1, 0)));

		this.add(gridVBox);

	}

//	public interface HTMLTemplate extends XTemplates {
//		// 웹에디터 HTML 설정
//		@XTemplate(source = "entrustInvest.html")
//		SafeHtml getTemplate();
//	}
//
//	public EntrustInvestment() {
//
//		this.setHeaderVisible(false);
//
//		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
//		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
////		gridVBox.setWidth(800);
////		gridVBox.setHeight(1000);
//
//		Margins getTextMargins = new Margins(0, 0, 15, 0);
//		Margins totalHBarMargins = new Margins(5, 0, 5, 30);
//		Margins lineBar0Margins = new Margins(10, 0, 20, 30);
//
//		Image lineBar0 = new Image(ResourceIcon.INSTANCE.verticalBar());
//
//		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
//		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
//
//		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
//		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate());
//
//		htmlLayoutContainer.setWidth(StartPage.CURRENTWIDTH);
//		StartPage.CURRENTHEIGHT = Window.getClientHeight()-300;//StartPage.startPageFooter.getAbsoluteTop() + 70;
////		Info.display("",""+StartPage.CURRENTHEIGHT);
//		htmlLayoutContainer.setHeight(StartPage.CURRENTHEIGHT); //600 - StartPage.CURRENTHEIGHT);
//
//		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
//		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
//		totalHBar.add(htmlLayoutContainer, new BoxLayoutData(totalHBarMargins));
//
//		gridVBox.add(StartPage.getTextContents("투자일임"), new BoxLayoutData(getTextMargins));
//		gridVBox.add(lineBar0, new BoxLayoutData(lineBar0Margins));
//		// gridVBox.add(mapImage,new BoxLayoutData(lineBar0Margins));
//		gridVBox.add(totalHBar);
//
//		this.add(gridVBox);
//	}
}
