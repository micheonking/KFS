package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys08_CodeKindModel;
import myApp.client.vi.sys.model.Sys08_CodeKindModelProperties;

public class Sys08_CodeKind extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private Boolean isAdmin = LoginUser.getIsAdmin();
	private Grid<Sys08_CodeKindModel> grid = this.buildGrid();
	private TextField codeKindName = new TextField();
	private Sys09_Grid_Code gridCode = new Sys09_Grid_Code(); 
	
	public Sys08_CodeKind() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(codeKindName, "코드구분명", 180, 70, true); 
		searchBarBuilder.addRetrieveButton(); 
		
		if(this.isAdmin) {
			searchBarBuilder.addUpdateButton();
			searchBarBuilder.addInsertButton();
			searchBarBuilder.addDeleteButton();
		}
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 50));
		vlc.add(this.grid, new VerticalLayoutData(1, 1));
		
		BorderLayoutData westLayoutData = new BorderLayoutData(0.4);
		westLayoutData.setMargins(new Margins(0, 4, 0, 0)); 
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);  

		this.setWestWidget(vlc, westLayoutData);
		this.setCenterWidget(gridCode); 
		
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Sys08_CodeKindModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Sys08_CodeKindModel> event) {
				Sys08_CodeKindModel codeKind = grid.getSelectionModel().getSelectedItem(); 
				gridCode.retrieveCode(codeKind); //.getCodeKindId());
			}
		}); 
		
		this.retrieve();
	}
	
	private Grid<Sys08_CodeKindModel> buildGrid(){
		
		Sys08_CodeKindModelProperties properties = GWT.create(Sys08_CodeKindModelProperties.class);		
		GridBuilder<Sys08_CodeKindModel> gridBuilder = new GridBuilder<Sys08_CodeKindModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		if(this.isAdmin) {
			gridBuilder.addText(properties.kindCode(), 120, "구분코드", new TextField());
			gridBuilder.addText(properties.kindName(), 150, "코드구분명", new TextField()) ;
			gridBuilder.addBoolean(properties.sysYnFlag(), 60, "시스템") ;
			gridBuilder.addText(properties.note(), 400, "비고", new TextField());
		}
		else {
			// 일반사용자는 코드구분을 관리할 수 없다. 
			gridBuilder.addText(properties.kindCode(), 120, "구분코드"); 
			gridBuilder.addText(properties.kindName(), 150, "코드구분명"); 
			gridBuilder.addBoolean(properties.sysYnFlag(), 60, "시스템") ;
			gridBuilder.addText(properties.note(), 400, "비고"); 
		}
		
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Sys08_CodeKindModel> service = new GridRetrieveData<Sys08_CodeKindModel>(grid.getStore()); 
		service.retrieve("sys.Sys08_CodeKind.selectByAll");
	}

	@Override
	public void update() {
		GridUpdate<Sys08_CodeKindModel> service = new GridUpdate<Sys08_CodeKindModel>(); 
		service.update(grid.getStore(), "sys.Sys08_CodeKind.update"); 
	}

	@Override
	public void insertRow() {
		GridInsertRow<Sys08_CodeKindModel> service = new GridInsertRow<Sys08_CodeKindModel>(); 
		
		Sys08_CodeKindModel codeKindModel = new Sys08_CodeKindModel(); 
		codeKindModel.setSysYn("false");
		service.insertRow(grid, codeKindModel);
	}

	@Override
	public void deleteRow() {
		GridDeleteData<Sys08_CodeKindModel> service = new GridDeleteData<Sys08_CodeKindModel>();
		List<Sys08_CodeKindModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.Sys08_CodeKind.delete");
	}
}