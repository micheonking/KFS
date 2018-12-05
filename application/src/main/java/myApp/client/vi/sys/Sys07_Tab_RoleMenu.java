package myApp.client.vi.sys;

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
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys04_RoleModel;
import myApp.client.vi.sys.model.Sys04_RoleModelProperties;

public class Sys07_Tab_RoleMenu extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private TextField roleName = new TextField();	
	private Grid<Sys04_RoleModel> roleGrid = this.buildGrid();
	private Sys07_Tree_RoleMenu roleMenuTree = new Sys07_Tree_RoleMenu();
	
	public Sys07_Tab_RoleMenu() {
		
		this.setBorders(false);
		
		// 조회조건 
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(roleName, "권한명", 150, 50, true); 
		searchBarBuilder.addRetrieveButton(); 
		
		// 조회조건 
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49 
		northLayoutData.setMargins(new Margins(0,0,0,0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData); 

		BorderLayoutData westLayoutData = new BorderLayoutData(0.4); // default size is 49 
		westLayoutData.setSplit(true);
		westLayoutData.setMargins(new Margins(0,3,0,0));
		westLayoutData.setMaxSize(1000); // TODO: BorderLayoutContainer set max size
		
		// Role 조회 
		this.setWestWidget(this.roleGrid, westLayoutData);
//		this.setWestWidget(roleContainer, westLayoutData);

		this.roleGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Sys04_RoleModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Sys04_RoleModel> event) {
				Sys04_RoleModel role = roleGrid.getSelectionModel().getSelectedItem();   
				roleMenuTree.retrieve(role.getRoleId());
			} 
		}); 
		
		// 트리메뉴
		this.setCenterWidget(roleMenuTree); 
		this.retrieve();
	}
	
	public Grid<Sys04_RoleModel> buildGrid(){
		
		Sys04_RoleModelProperties properties = GWT.create(Sys04_RoleModelProperties.class);	
		GridBuilder<Sys04_RoleModel> gridBuilder = new GridBuilder<Sys04_RoleModel>(properties.keyId());  
		
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.roleName(), 150, "권한명"); //, new TextField());
		gridBuilder.addText(properties.seq(), 40, "순서"); //, new TextField()) ;
		gridBuilder.addText(properties.note(), 500, "상세설명"); //, new TextField());
		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void retrieve() {
		GridRetrieveData<Sys04_RoleModel> service = new GridRetrieveData<Sys04_RoleModel>(roleGrid.getStore());
		String name = this.roleName.getText();  
		service.addParam("roleName", "%" + name + "%");
		service.addParam("companyId", LoginUser.getCompanyId());
		service.retrieve("sys.Sys04_Role.selectByName");
	}

	@Override
	public void update() {
	}

	@Override
	public void insertRow() {
	}

	@Override
	public void deleteRow() {
	}
}