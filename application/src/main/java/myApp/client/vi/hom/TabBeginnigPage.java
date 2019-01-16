package myApp.client.vi.hom;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.vi.hom.beginning.CoverPageBody;
import myApp.client.vi.hom.beginning.CoverPageHeader;

public class TabBeginnigPage extends BorderLayoutContainer {
	
	protected final int WIN_WIDTH = Window.getClientWidth();
//	protected final int WIN_HEIGHT = Window.getClientHeight();
	protected final int IMG_WIDTH = 1950;
	protected final int IMG_HEIGHT = 378;
	StartPage startPage ; 
	
	public TabBeginnigPage(StartPage startPage) {
		this.startPage = startPage; 
		
		this.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resize();
			}
		});
	}
	
	private void resize() {

		int imageWidth = Window.getClientWidth();
		int imageHeight = (IMG_HEIGHT * Window.getClientWidth()) / IMG_WIDTH;
		
		CoverPageHeader north = new CoverPageHeader(imageWidth, imageHeight);
		CoverPageBody center = new CoverPageBody(imageWidth, imageHeight, startPage);
		
		// center-north 이미지 사진
		BorderLayoutData northLayoutData = new BorderLayoutData(imageHeight+2);
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setNorthWidget(north, northLayoutData);

		// center-center
		BorderLayoutData centerLayoutData = new BorderLayoutData(70);
		centerLayoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setCenterWidget(center, centerLayoutData);

//		// center-south
//		BorderLayoutData southBorderLayoutData = new BorderLayoutData();
//		southBorderLayoutData.setMargins(new Margins(0, 0, 0, 0));
//		this.setSouthWidget(south, southBorderLayoutData);
	}
}