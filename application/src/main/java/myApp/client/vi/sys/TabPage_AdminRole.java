package myApp.client.vi.sys;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.InterfaceLookupResult;
import myApp.client.utils.InterfaceTabPage;
import myApp.client.vi.sys.model.Sys02_UserModel;
import myApp.client.vi.sys.model.Sys04_RoleModel;
import myApp.client.vi.sys.model.Sys05_UserRoleModel;
import myApp.client.vi.sys.model.Sys05_UserRoleModelProperties;

public class TabPage_AdminRole extends ContentPanel implements InterfaceTabPage, InterfaceGridOperate, InterfaceLookupResult {
	
	 
	private Sys05_UserRoleModelProperties properties = GWT.create(Sys05_UserRoleModelProperties.class);
	private Grid<Sys05_UserRoleModel> grid = this.buildGrid();
	private Long userId = null;

	private TabPage_AdminRole getThis(){
		return this; 
	}
	
	public TabPage_AdminRole() {
		this.setHeaderVisible(false); 
		this.add(this.grid);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();

		this.getButtonBar().add(searchBarBuilder.getSearchBar()); 
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	public Grid<Sys05_UserRoleModel> buildGrid(){
		GridBuilder<Sys05_UserRoleModel> gridBuilder = new GridBuilder<Sys05_UserRoleModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.MULTI);
		
		gridBuilder.addText(properties.roleName(), 250, "권한명") ;
		gridBuilder.addText(properties.seq(), 80, "순서", new TextField()) ;
		gridBuilder.addText(properties.note(), 400, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

	@Override // InterfaceTabpage()
	public void init() {
		this.grid.getStore().clear();
	}

	
	@Override
	public void retrieve(Map<String, Object> param) {
		this.grid.getStore().clear();
		
		if(param != null){
			Sys02_UserModel userModel = (Sys02_UserModel)param.get("UserModel"); 
			this.userId = userModel.getUserId(); 
			this.retrieve();
		}
		else {
			this.userId = null; 
		}
	}

	@Override
	public void setLookupResult(Object result) {

		@SuppressWarnings("unchecked")
		List<Sys04_RoleModel> roleList = (List<Sys04_RoleModel>)result;  
		
		for(final Sys04_RoleModel roleModel  : roleList){
			
			final Sys05_UserRoleModel userRoleModel = new Sys05_UserRoleModel();
			userRoleModel.setUserId(this.userId);
			userRoleModel.setRoleId(roleModel.getRoleId());
			
			GridInsertRow<Sys05_UserRoleModel> service = new GridInsertRow<Sys05_UserRoleModel>();
			service.insertRow(this.grid, userRoleModel, new InterfaceCallbackResult(){
				@Override
				public void execute(Object result) {
					// role이 등록된것을 보여주기 위하여 따로 등록한다. 
					grid.getStore().getRecord(userRoleModel).addChange(properties.roleName(), roleModel.getRoleName());
				}
			});
		}
	}

	@Override
	public void retrieve() {
		if(this.userId != null){
			GridRetrieveData<Sys05_UserRoleModel> service = new GridRetrieveData<Sys05_UserRoleModel>(grid.getStore());
			service.addParam("userId", userId);
			service.retrieve("sys.UserRole.selectByUserId");
		}
	}

	@Override
	public void update() {
		GridUpdate<Sys05_UserRoleModel> service = new GridUpdate<Sys05_UserRoleModel>(); 
		service.update(grid.getStore(), "sys.UserRole.update"); 
	}

	@Override
	public void insertRow() {
		
		if(userId != null){
			new Lookup_AdminRole(this.getThis(), this.userId).show();
		}
	}

	@Override
	public void deleteRow() {
		GridDeleteData<Sys05_UserRoleModel> service = new GridDeleteData<Sys05_UserRoleModel>();
		List<Sys05_UserRoleModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.UserRole.delete");
	}
	
}