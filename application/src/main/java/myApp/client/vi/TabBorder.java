package myApp.client.vi;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.vi.frm.Calendar;
import myApp.client.vi.frm.Monitoring_Page_Appr;
import myApp.client.vi.frm.Monitoring_Page_Outstanding;
import myApp.client.vi.frm.Monitoring_Page_ToDoList;

public class TabBorder extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private Calendar					carlendar		= new Calendar();
	private Monitoring_Page_Outstanding outstandingGrid = new Monitoring_Page_Outstanding();
	private Monitoring_Page_Appr		apprGird		= new Monitoring_Page_Appr();
	private Monitoring_Page_ToDoList	toDoGrid		= new Monitoring_Page_ToDoList();

	public TabBorder() {

//		SearchBarBuilder searchbarBuilder = new SearchBarBuilder(this);
//		searchbarBuilder.addRetrieveButton();
		
//		BorderLayoutData northLayoutData = new BorderLayoutData();
//		northLayoutData.setSplit(false);
//		northLayoutData.setMargins(new Margins(0,0,2,0));
//		northLayoutData.setSize(50);
//		this.setNorthWidget(searchbarBuilder.getSearchBar(), northLayoutData);

		BorderLayoutData westLayoutData = new BorderLayoutData();
		westLayoutData.setSplit(true);
		westLayoutData.setMargins(new Margins(0,0,0,0));
		westLayoutData.setSize(950);
		carlendar = new Calendar(toDoGrid, outstandingGrid, apprGird);
		this.setWestWidget(this.carlendar, westLayoutData);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(this.toDoGrid       , new VerticalLayoutData(1,0.3, new Margins(55,20,0,0)));
		vlc.add(this.outstandingGrid, new VerticalLayoutData(1,0.3, new Margins(30,20,0,0)));
		vlc.add(this.apprGird       , new VerticalLayoutData(1,0.3, new Margins(30,20,0,0)));

		BorderLayoutData centerLayoutData = new BorderLayoutData();
		centerLayoutData.setSplit(true);
		centerLayoutData.setMargins(new Margins(0));
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(vlc);
		
		this.setCenterWidget(cp, centerLayoutData);
	}

	@Override
	public void retrieve() {
		carlendar.retrieve();		//달력 조회
		outstandingGrid.retrieve();	//미결함 조회
		apprGird.retrieve();		//결재요청함 조회
		toDoGrid.retrieve();		//To-Do List 조회
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