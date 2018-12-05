package myApp.client.vi.emp;
 
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp10_ManagerContactModel;
import myApp.client.vi.emp.model.Emp10_ManagerContactModelProperties;
 
public class Emp10_Tab_ManagerContact extends BorderLayoutContainer implements InterfaceGridOperate {
 
    private Emp10_ManagerContactModelProperties properties = GWT.create(Emp10_ManagerContactModelProperties.class);
    private Grid<Emp10_ManagerContactModel> grid = this.buildGrid();
 
    public Emp10_Tab_ManagerContact() {
 
        this.setBorders(false); 
        
        SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
        searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
        this.setBorders(false); 
        this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 
 
        BorderLayoutData centerBorLayoutData = new BorderLayoutData(1);
        centerBorLayoutData .setMargins(new Margins(0, 4, 0, 0));
        centerBorLayoutData .setSplit(true);
        centerBorLayoutData .setMaxSize(1000);
 
        this.setCenterWidget(this.grid, centerBorLayoutData);
    }
 
    public Grid<Emp10_ManagerContactModel> buildGrid(){
        
        GridBuilder<Emp10_ManagerContactModel> gridBuilder = new GridBuilder<Emp10_ManagerContactModel>(properties.keyId());  
        gridBuilder.setChecked(SelectionMode.SINGLE);
 
//        gridBuilder.addLong(properties.managerId(),    		200    ,    "managerId",			new	TextField());
//        gridBuilder.addLong(properties.empId(),    		200    ,    "managerId",			new	TextField());
        gridBuilder.addText(properties.managerCompanyName(),200    ,    "회사이름",				new	TextField());
        gridBuilder.addText(properties.managerName(),   	200    ,    "이름",					new	TextField());
        gridBuilder.addText(properties.managerEmail(), 		200    ,    "이메일",				new	TextField());
        gridBuilder.addText(properties.managerMobile(),		200    ,    "전화번호",				new	TextField());
        gridBuilder.addText(properties.managerTelephone(),  200    ,    "휴대전화 번호",		new	TextField());
        
        return gridBuilder.getGrid(); 
    }
    
	@Override
	public void retrieve(){
    	GridRetrieveData<Emp10_ManagerContactModel> service = new GridRetrieveData<Emp10_ManagerContactModel>(grid.getStore());
		service.addParam("empId", LoginUser.getUserId()); 
        service.retrieve("emp.Emp10_ManagerContact.selectByEmpId");
	}

	@Override
	public void update(){
		GridUpdate<Emp10_ManagerContactModel> service = new GridUpdate<Emp10_ManagerContactModel>();
		service.update(grid.getStore(), "emp.Emp10_ManagerContact.update"); 
	}

	@Override
	public void insertRow(){
		GridInsertRow<Emp10_ManagerContactModel> service = new GridInsertRow<Emp10_ManagerContactModel>(); 
		Emp10_ManagerContactModel managerContactModel = new Emp10_ManagerContactModel();
		service.insertRow(grid, managerContactModel);
	}
	

	@Override
	public void deleteRow(){
		GridDeleteData<Emp10_ManagerContactModel> service = new GridDeleteData<Emp10_ManagerContactModel>();
		List<Emp10_ManagerContactModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "emp.Emp10_ManagerContact.delete");
	}

}
 
 