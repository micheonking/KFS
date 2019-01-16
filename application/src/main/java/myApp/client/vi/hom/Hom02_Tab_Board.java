package myApp.client.vi.hom;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.MenuOpener;
import myApp.client.vi.MenuTree;
import myApp.client.vi.hom.model.Hom02_BoardModel;
import myApp.client.vi.hom.model.Hom02_BoardModelProperties;
import myApp.client.vi.hom.StartPage;
import myApp.client.vi.hom.report.Notification;

public class Hom02_Tab_Board extends BorderLayoutContainer implements InterfaceGridOperate {

	Hom02_BoardModelProperties properties = GWT.create(Hom02_BoardModelProperties.class);

	private Grid<Hom02_BoardModel> grid = this.buildGrid();

	private StartPage startPage;
	
	public 	CellButtonBase mainButton = new CellButtonBase<>();
	
	public Hom02_Tab_Board() {

		
		BorderLayoutData centerBorLayoutData = new BorderLayoutData(1);
		centerBorLayoutData.setMargins(new Margins(0, 0, 0, 0));
		centerBorLayoutData.setSplit(true);
		centerBorLayoutData.setMaxSize(1000);

        grid.setHideHeaders(true);
		grid.setColumnReordering(false);
		grid.getView().setStripeRows(false);
		grid.getView().setColumnLines(false); 
		grid.getView().setAdjustForHScroll(false);
//		grid.getView().setTrackMouseOver(false);	// 마우스 Over
		grid.getView().setEnableRowBody(false);
		grid.getView().setStripeRows(false);
		grid.setBorders(true);
		grid.getElement().setBorders(false);
		grid.getView().setShowDirtyCells(false);
		grid.getView().setAdjustForHScroll(false);
//		grid.setVisible(false);
		grid.setColumnResize(true);
		grid.setHeight(20);

		this.setCenterWidget(this.grid, centerBorLayoutData);
		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Hom02_BoardModel>(){
		
		@Override
		public void onSelectionChanged(SelectionChangedEvent<Hom02_BoardModel> event) {
//			popupPage();
//			int xPosition = mainButton.getAbsoluteLeft();
			startPage.changePage("4");
		}
		});
			
		retrieve();
	}

	private void popupPage() {

//		textButton4.addSelectHandler(new SelectHandler() {
//		@Override
//		public void onSelect(SelectEvent event) {
	Info.display("","asd");
//			int xPosition = mainButton.getAbsoluteLeft();
			startPage.changePage("4");
//		}
		
		Info.display("","123456");
		Notification notification = new Notification();
		notification.show();	
	}

	public Grid<Hom02_BoardModel> buildGrid() {
		GridBuilder<Hom02_BoardModel> gridBuilder = new GridBuilder<Hom02_BoardModel>(properties.keyId());
//		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.titleName(), 321, "제목");
		gridBuilder.addDate(properties.settleDate(), 90, "작성일");

//		gridBuilder.setMenuDisable(true);
//		gridBuilder.rowNum.setHidden(true);
		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Hom02_BoardModel> service = new GridRetrieveData<Hom02_BoardModel>(grid.getStore());
		service.addParam("typeCode", "notice");
		service.addParam("setCount", (long)4);
		service.retrieve("hom.Hom02_Board.selectByTypeCode2");
	}

	@Override
	public void update() {
		GridUpdate<Hom02_BoardModel> service = new GridUpdate<Hom02_BoardModel>();
		// service.addParam("boardId", LoginUser.getUserId());
		service.update(grid.getStore(), "hom.Hom02_Board.update");
		
	}

	@Override
	public void insertRow() {
		GridInsertRow<Hom02_BoardModel> service = new GridInsertRow<Hom02_BoardModel>();
		Hom02_BoardModel addrBookModel = new Hom02_BoardModel();
		// addrBookModel.setEmpId(LoginUser.getUserId());
		service.insertRow(grid, addrBookModel);
	}

	@Override
	public void deleteRow() {
		GridDeleteData<Hom02_BoardModel> service = new GridDeleteData<Hom02_BoardModel>();
		List<Hom02_BoardModel> checkedList = grid.getSelectionModel().getSelectedItems();
		service.delete(grid.getStore(), checkedList, "hom.Hom02_Board.delete");
	}

	protected void businessDown() {
		// TODO Auto-generated method stub
//        FirmHeadCenterView homePage = new FirmHeadCenterView();
//        homePage.open();		
	}

}
