package myApp.client.vi.home;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.resource.ResourceIcon;
import myApp.client.vi.frm.Calendar;
import myApp.client.vi.frm.Monitoring_Page_Appr;
import myApp.client.vi.frm.Monitoring_Page_Outstanding;
import myApp.client.vi.frm.Monitoring_Page_ToDoList;
import myApp.client.vi.home.front.BodyAreaCenter;
import myApp.client.vi.home.front.BodyAreaNorth;
import myApp.client.vi.home.front.BodyAreaSouth;

public class TabFrontPage extends BorderLayoutContainer implements InterfaceGridOperate {
	
	
	public TabFrontPage() {

		BodyAreaNorth north = new BodyAreaNorth();
		BodyAreaCenter center = new BodyAreaCenter();
		BodyAreaSouth south = new BodyAreaSouth();
		
		// center-north 이미지 사진
		BorderLayoutData northLayoutData = new BorderLayoutData(380);
		northLayoutData.setSplit(false); // 크기조절
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
		northLayoutData.setSize(385);
//		northLayoutData.setMaxSize(700);
		this.setNorthWidget(north, northLayoutData);

		// center-center
		BorderLayoutData centerLayoutData = new BorderLayoutData(200);
		centerLayoutData.setSplit(false); // 크기조절
		centerLayoutData.setMargins(new Margins(0, 0, 0, 0));
//		centerLayoutData.setSize(100);
//		centerLayoutData.setMaxSize(700);
		this.setCenterWidget(center, centerLayoutData);

		// center-south
		BorderLayoutData southBorderLayoutData = new BorderLayoutData(220);
		southBorderLayoutData.setSplit(false); // 크기조절
		southBorderLayoutData.setMargins(new Margins(0, 0, 0, 0));
//		southBorderLayoutData.setSize(220);
//		southBorderLayoutData.setMaxSize(700);
		this.setSouthWidget(south, southBorderLayoutData);

//		BorderLayoutData centerLayoutData = new BorderLayoutData();
//		centerLayoutData.setSplit(true);
//		centerLayoutData.setMargins(new Margins(0));
		
//		ContentPanel cp = new ContentPanel();
//		cp.setHeaderVisible(false);
//		cp.add(vlc);
		
//		this.setCenterWidget(cp, centerLayoutData);
	}

	@Override
	public void retrieve() {
//		carlendar.retrieve();		//달력 조회
//		outstandingGrid.retrieve();	//미결함 조회
//		apprGird.retrieve();		//결재요청함 조회
//		toDoGrid.retrieve();		//To-Do List 조회
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
	}
}