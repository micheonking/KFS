package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
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
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.opr.Opr02_Lookup_Fund;
import myApp.client.vi.pln.Pln03_Lookup_ResrchDetail;
import myApp.client.vi.pln.model.Pln03_ResrchModel;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.client.vi.sys.model.Sys01_CompanyModelProperties;

public class Sys01_Tab_Company extends BorderLayoutContainer implements InterfaceGridOperate {
	
	
	private Grid<Sys01_CompanyModel> grid = this.buildGrid();
	private TextField companyName = new TextField(); 
	private Sys02_Page_User pageUser = new Sys02_Page_User();  
	
	public Sys01_Tab_Company() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		searchBarBuilder.addTextField(companyName, "회사명", 200, 50, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49 
		northLayoutData.setMargins(new Margins(0,0,0,0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData); 
		
		this.setCenterWidget(grid); 
		
		BorderLayoutData southLayoutData = new BorderLayoutData(0.5);
		southLayoutData.setSplit(true);
		southLayoutData.setMargins(new Margins(8,0,0,0));
		southLayoutData.setMaxSize(1000);
		
		this.setSouthWidget(pageUser, southLayoutData);
		
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Sys01_CompanyModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Sys01_CompanyModel> event) {
				Sys01_CompanyModel companyModel = grid.getSelectionModel().getSelectedItem(); 
				if(companyModel != null) {
					pageUser.retrieve(companyModel.getCompanyId());
				}
			}
		});
		
		this.retrieve();
	}
	
	private Grid<Sys01_CompanyModel> buildGrid(){
		Sys01_CompanyModelProperties properties = GWT.create(Sys01_CompanyModelProperties.class);
		GridBuilder<Sys01_CompanyModel> gridBuilder = new GridBuilder<Sys01_CompanyModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.companyName(), 150, "고객명", new TextField());
		gridBuilder.addText(properties.bizNo(), 120, "사업자번호", new TextField());
		gridBuilder.addText(properties.telNo01(), 100, "전화1", new TextField());
		ActionCell<String> pwButton = new ActionCell<String>("변경", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				Sys01_CompanyModel passWordModel = grid.getSelectionModel().getSelectedItem();
				Sys01_Lookup_Company lookupPW = new Sys01_Lookup_Company();
				lookupPW.open(passWordModel.getTelNo02(), passWordModel.getCompanyId(), new InterfaceCallbackResult() {		
					@Override
					public void execute(Object result) {
						retrieve();
					}
				});
			}
		});				
		gridBuilder.addCell(properties.telNo02(), 100, "임시비밀번호",pwButton);
		gridBuilder.addText(properties.faxNo01(), 100, "팩스번호", new TextField());
		gridBuilder.addText(properties.zipCode(), 100, "우편번호", new TextField());
		gridBuilder.addText(properties.zipAddress(), 300, "주소", new TextField());;
		gridBuilder.addText(properties.note(), 400, "비고", new TextField());;
	
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		String name = companyName.getText();
		GridRetrieveData<Sys01_CompanyModel> service = new GridRetrieveData<Sys01_CompanyModel>(grid.getStore()); 
		service.addParam("companyName", name);
		service.retrieve("sys.Sys01_Company.selectByName");
	}

	@Override
	public void update(){
		GridUpdate<Sys01_CompanyModel> service = new GridUpdate<Sys01_CompanyModel>(); 
		service.update(grid.getStore(), "sys.Sys01_Company.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<Sys01_CompanyModel> service = new GridInsertRow<Sys01_CompanyModel>(); 
		Sys01_CompanyModel companyModel = new Sys01_CompanyModel();
		service.insertRow(grid, companyModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<Sys01_CompanyModel> service = new GridDeleteData<Sys01_CompanyModel>();
		List<Sys01_CompanyModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.Sys01_Company.delete");
	}
}