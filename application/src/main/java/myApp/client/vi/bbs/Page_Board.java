package myApp.client.vi.bbs;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.LoginUser;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.client.vi.bbs.model.Bbs01_BoardModelProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Page_Board extends BorderLayoutContainer implements InterfaceGridOperate {

	private Bbs01_BoardModelProperties properties = GWT.create(Bbs01_BoardModelProperties.class);
	private Grid<Bbs01_BoardModel> grid = this.buildGrid();
	private TextField searchText = new TextField();
	private Popup_Board popupBoard = new Popup_Board();
	private DateField  writeDate = new DateField();
	
	
	public Page_Board(){
		
		this.setBorders(false);
		
//		this.setHeading("공지 및 게시판");
//		this.setHeaderVisible(true);
//		this.add(grid);

		grid.addCellClickHandler(new CellClickHandler(){

			@Override
			public void onCellClick(CellClickEvent event) {
				if(event.getCellIndex() == 1){
					Bbs01_BoardModel boardModel = grid.getSelectionModel().getSelectedItem();
					Popup_Board popupBoard = new Popup_Board();
					popupBoard.open(boardModel); 
				}
			}
		}); 
		
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "제목찾기", 300, 60, true) ; 
		searchBarBuilder.addRetrieveButton(); 
		
		BorderLayoutData northLaoutData = new BorderLayoutData();
		northLaoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(),new BorderLayoutData(50));
		this.setCenterWidget(grid);

//		this.retrieve();
		
	}

	
	private Grid<Bbs01_BoardModel> buildGrid(){
//		
//		GridBuilder<Bbs01_BoardModel> gridBuilder = new GridBuilder<Bbs01_BoardModel>(properties.keyId());  
//		// gridBuilder.setChecked(SelectionMode.SINGLE);
//
////		gridBuilder.addText(properties.typeName(), 80, "구분");
//
//		ColumnConfig<Bbs01_BoardModel, String> title = gridBuilder.addText(properties.title(), 400, "제목");
//
//		/* 
//		 * UNDERLINE_SETTING   
//		 */
//		
//		SafeStylesBuilder sb = new SafeStylesBuilder(); 
//		sb.textDecoration(com.google.gwt.dom.client.Style.TextDecoration.UNDERLINE );
//		sb.trustedColor("DARKBLUE");
//		sb.cursor(Style.Cursor.POINTER);
//
//		title.setColumnStyle(sb.toSafeStyles());
//		
//		gridBuilder.addDate(properties.writeDate(), 90, "작성일");		
//		gridBuilder.addText(properties.korName(), 90, "작성자");
		
		
		GridBuilder<Bbs01_BoardModel> gridBuilder = new GridBuilder<Bbs01_BoardModel>(properties.keyId()); 
		gridBuilder.addText(properties.title(),400,"제목");		
		gridBuilder.addDate(properties.writeDate(),150,"작성일");
		gridBuilder.addText(properties.korName(),100,"작성자");		
		return gridBuilder.getGrid();  
	}
	
	
	@Override
	public void retrieve() {
		GridRetrieveData<Bbs01_BoardModel> service = new GridRetrieveData<Bbs01_BoardModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("title", searchText.getValue());
		service.retrieve("bbs.Bbs01_Board.selectByCompanyId");
	}
	
	
	@Override
	public void update(){
//		GridUpdateData<RegisterModel> service = new GridUpdateData<RegisterModel>(); 
//		service.update(grid.getStore(), "psc.Register.update"); 
	}
	@Override
	public void insertRow(){
//		GridInsertRow<RegisterModel> service = new GridInsertRow<RegisterModel>(); 
//		RegisterModel registerModel = new RegisterModel();
//		registerModel.setStudentId(this.studentModel.getStudentId());
//		service.insertRow(grid, registerModel);
	}
	@Override
	public void deleteRow(){
//		GridDeleteData<RegisterModel> service = new GridDeleteData<RegisterModel>();
//		List<RegisterModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
//		service.delete(grid.getStore(), checkedList, "psc.Register.delete");
	}
//	public Grid<RegisterModel> getGrid(){
//		return this.grid; 
//	}

}
