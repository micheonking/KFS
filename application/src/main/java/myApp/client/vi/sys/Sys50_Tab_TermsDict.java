package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys50_TermsDictModel;
import myApp.client.vi.sys.model.Sys50_TermsDictModelProperties;

public class Sys50_Tab_TermsDict extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private Grid<Sys50_TermsDictModel> grid = this.buildGrid();
	private TextField searchText = new TextField();
	
	public Sys50_Tab_TermsDict() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "검색 ", 300, 40, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		BorderLayoutData northLayoutData = new BorderLayoutData(50); 
		northLayoutData.setMargins(new Margins(0,0,0,0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData); 
		this.setCenterWidget(grid); 
		this.retrieve();
	}
	
	private Grid<Sys50_TermsDictModel> buildGrid(){
		Sys50_TermsDictModelProperties properties = GWT.create(Sys50_TermsDictModelProperties.class);
		GridBuilder<Sys50_TermsDictModel> gridBuilder = new GridBuilder<Sys50_TermsDictModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.korName(), 150, "용어명", new TextField());
		gridBuilder.addText(properties.shtName(), 100, "영문약어", new TextField());
		gridBuilder.addText(properties.fullName(), 150, "영문Full명", new TextField());
		gridBuilder.addText(properties.rmk(), 200, "비고", new TextField());
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Sys50_TermsDictModel> service = new GridRetrieveData<Sys50_TermsDictModel>(grid.getStore()); 
		service.addParam("searchText", searchText.getText());
		service.retrieve("sys.Sys50_TermsDict.selectBySearchText");
	}

	@Override
	public void update(){
		GridUpdate<Sys50_TermsDictModel> service = new GridUpdate<Sys50_TermsDictModel>();
		service.addParam("usrNo", LoginUser.getUsrNo());
		service.update(grid.getStore(), "sys.Sys50_TermsDict.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<Sys50_TermsDictModel> service = new GridInsertRow<Sys50_TermsDictModel>(); 
		service.insertRow(grid, new Sys50_TermsDictModel());
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Sys50_TermsDictModel> service = new GridDeleteData<Sys50_TermsDictModel>();
		List<Sys50_TermsDictModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.Sys50_TermsDict.delete");
	}
}