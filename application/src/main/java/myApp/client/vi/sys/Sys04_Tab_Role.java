package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
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
import myApp.client.vi.sys.model.Sys04_RoleModel;
import myApp.client.vi.sys.model.Sys04_RoleModelProperties;

public class Sys04_Tab_Role extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private Sys04_RoleModelProperties properties = GWT.create(Sys04_RoleModelProperties.class);
	private Grid<Sys04_RoleModel> grid = this.buildGrid();
	private TextField roleName = new TextField();
	private Sys07_Tree_RoleMenu treeMenu = new Sys07_Tree_RoleMenu();
	
	public Sys04_Tab_Role() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(roleName, "권한명", 200, 50, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
		this.setBorders(false);
		this.add(searchBarBuilder.getSearchBar()); 
		this.add(this.grid, new VerticalLayoutData(1, 1));

		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Sys04_RoleModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Sys04_RoleModel> event) {
				Sys04_RoleModel role = grid.getSelectionModel().getSelectedItem();   
				treeMenu.retrieve(role.getRoleId());
			} 
		});
		
		this.retrieve();
	}
	
	public Grid<Sys04_RoleModel> buildGrid(){
		
		GridBuilder<Sys04_RoleModel> gridBuilder = new GridBuilder<Sys04_RoleModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.roleName(), 150, "권한명", new TextField());
		gridBuilder.addText(properties.seq(), 70, "조회순서", new TextField()) ;
//		gridBuilder.addBoolean(properties.managerYnBoolean(), 70, "ADMIN") ;
		gridBuilder.addText(properties.note(), 800, "권한설명", new TextField());
	
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		
		GridRetrieveData<Sys04_RoleModel> service = new GridRetrieveData<Sys04_RoleModel>(grid.getStore());
		String roleString = roleName.getValue() ; 
		
		if(roleString == null) {
			roleString = "";	
		}

		roleString = "%" + roleString + "%";
		
		service.addParam("roleName", roleString);
		service.addParam("companyId", LoginUser.getCompanyId());
		service.retrieve("sys.Sys04_Role.selectByName");
	}

	@Override
	public void update() {
		GridUpdate<Sys04_RoleModel> service = new GridUpdate<Sys04_RoleModel>(); 
		service.update(grid.getStore(), "sys.Sys04_Role.update"); 
	}

	@Override
	public void insertRow() {
		Sys04_RoleModel data =  new Sys04_RoleModel(); 
		data.setCompanyId(LoginUser.getCompanyId());

		GridInsertRow<Sys04_RoleModel> service = new GridInsertRow<Sys04_RoleModel>();
		service.insertRow(grid, data);
	}

	@Override
	public void deleteRow() {
		GridDeleteData<Sys04_RoleModel> service = new GridDeleteData<Sys04_RoleModel>();
		List<Sys04_RoleModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.Sys04_Role.delete");
	}
}