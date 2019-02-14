package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys01_CmpInfoModel;
import myApp.client.vi.sys.model.Sys01_CmpInfoModelProperties;

public class Sys01_Tab_CmpInfo extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private Grid<Sys01_CmpInfoModel> grid = this.buildGrid();
	private TextField cmpName = new TextField();
	
	public Sys01_Tab_CmpInfo() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(cmpName, "회사명", 200, 50, true); 
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
	
	private Grid<Sys01_CmpInfoModel> buildGrid(){
		Sys01_CmpInfoModelProperties properties = GWT.create(Sys01_CmpInfoModelProperties.class);
		GridBuilder<Sys01_CmpInfoModel> gridBuilder = new GridBuilder<Sys01_CmpInfoModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.cmpCode(), 100, "회사코드", new TextField());
		gridBuilder.addText(properties.cmpName(), 150, "회사명", new TextField());
		gridBuilder.addText(properties.bizNo(), 120, "사업자번호", new TextField());
		gridBuilder.addText(properties.telNo(), 100, "전화", new TextField());
		gridBuilder.addText(properties.tmpPwd(), 100, "임시비밀번호", new TextField());
		gridBuilder.addText(properties.addr(), 500, "주소", new TextField());
		gridBuilder.addText(properties.rmk(), 400, "비고", new TextField());
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Sys01_CmpInfoModel> service = new GridRetrieveData<Sys01_CmpInfoModel>(grid.getStore()); 
		service.addParam("cmpName", this.cmpName.getText());
		service.retrieve("sys.Sys01_CmpInfo.selectByCmpName");
	}

	@Override
	public void update(){
		GridUpdate<Sys01_CmpInfoModel> service = new GridUpdate<Sys01_CmpInfoModel>();
		service.addParam("usrNo", LoginUser.getUsrNo());
		service.update(grid.getStore(), "sys.Sys01_CmpInfo.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<Sys01_CmpInfoModel> service = new GridInsertRow<Sys01_CmpInfoModel>(); 
		service.insertRow(grid, new Sys01_CmpInfoModel());
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Sys01_CmpInfoModel> service = new GridDeleteData<Sys01_CmpInfoModel>();
		List<Sys01_CmpInfoModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.Sys01_CmpInfo.delete");
	}
}