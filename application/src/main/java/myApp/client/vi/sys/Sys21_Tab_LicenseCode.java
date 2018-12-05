package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.sys.model.Sys21_LicenseCodeModel;
import myApp.client.vi.sys.model.Sys21_LicenseCodeModelProperties;

public class Sys21_Tab_LicenseCode extends VerticalLayoutContainer implements InterfaceGridOperate  {

	private Sys21_LicenseCodeModelProperties properties = GWT.create(Sys21_LicenseCodeModelProperties.class);
	private Grid<Sys21_LicenseCodeModel> grid = this.buildGrid();
	private TextField licenseName = new TextField();
	
	public Sys21_Tab_LicenseCode() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(licenseName, "자격면허명", 180, 70, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(this.grid, new VerticalLayoutData(1, 1));
		
	}

	private Grid<Sys21_LicenseCodeModel> buildGrid(){
		
		GridBuilder<Sys21_LicenseCodeModel> gridBuilder = new GridBuilder<Sys21_LicenseCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.licenseCode(), 120, "자격면허코드", new TextField());
		gridBuilder.addText(properties.licenseName(), 300, "자격면허명", new TextField()) ;
		gridBuilder.addText(properties.issueOrgName(), 200, "발급기관명", new TextField()) ;
		gridBuilder.addDate(properties.applyDate(), 100, "적용일", new DateField()) ;
		
		gridBuilder.addBoolean(properties.closeYnFlag(), 100, "사용여부") ;
		gridBuilder.addText(properties.note(), 400, "비고", new TextField());
	
		return gridBuilder.getGrid(); 
	}
	
	
	@Override
	public void retrieve() {
		GridRetrieveData<Sys21_LicenseCodeModel> service = new GridRetrieveData<Sys21_LicenseCodeModel>(grid.getStore());
		service.addParam("licenseName", licenseName.getValue());
		service.retrieve("sys.LicenseCode.selectByName");
	}

	@Override
	public void update() {
		GridUpdate<Sys21_LicenseCodeModel> service = new GridUpdate<Sys21_LicenseCodeModel>(); 
		service.update(grid.getStore(), "sys.LicenseCode.update"); 
	}

	@Override
	public void insertRow() {
		GridInsertRow<Sys21_LicenseCodeModel> service = new GridInsertRow<Sys21_LicenseCodeModel>(); 
		service.insertRow(grid, new Sys21_LicenseCodeModel());
	}

	@Override
	public void deleteRow() {
		GridDeleteData<Sys21_LicenseCodeModel> service = new GridDeleteData<Sys21_LicenseCodeModel>();
		List<Sys21_LicenseCodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.LicenseCode.delete");
	}
}