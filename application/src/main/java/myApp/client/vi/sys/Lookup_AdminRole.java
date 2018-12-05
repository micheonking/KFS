package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.AbstractLookupWindow;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceLookupResult;
import myApp.client.vi.sys.model.Sys04_RoleModel;
import myApp.client.vi.sys.model.Sys04_RoleModelProperties;

public class Lookup_AdminRole extends AbstractLookupWindow  {

	private Sys04_RoleModelProperties properties = GWT.create(Sys04_RoleModelProperties.class);
	private Grid<Sys04_RoleModel> grid = this.buildGrid(); 
	private InterfaceLookupResult lookupParent;
	private Long userId = null; 
	
	public Lookup_AdminRole(InterfaceLookupResult lookupParent, Long userId){
		
		this.lookupParent = lookupParent; 
		this.userId = userId;
		this.setInit("권한 선택", 600, 350); 

		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(grid, new VerticalLayoutData(1, 1));
		this.add(vlc);
		
		this.retrieve();
	}
	
	private Grid<Sys04_RoleModel> buildGrid(){
		GridBuilder<Sys04_RoleModel> gridBuilder = new GridBuilder<Sys04_RoleModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.roleName(), 150, "ROLE명") ;
		gridBuilder.addText(properties.note(), 400, "상세설명");
		return gridBuilder.getGrid(); 
	}
		
	@Override
	public void retrieve() {
		if(this.userId != null){
			GridRetrieveData<Sys04_RoleModel> service = new GridRetrieveData<Sys04_RoleModel>(grid.getStore());
			service.addParam("userId", userId);
			service.retrieve("sys.Sys04_Role.selectByNotAssigned");
		} 
	}

	@Override
	public void confirm() {
		List<Sys04_RoleModel> roleList = this.grid.getSelectionModel().getSelectedItems(); 
		if(roleList.size() < 1 ){
			Info.display("권한확인", "선택된 권한이 없습니다.");
			return ; 
		}
		else { 
			lookupParent.setLookupResult(roleList);
		} 
	}

	@Override
	public void cancel() {
	}
}
