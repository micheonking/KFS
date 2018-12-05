package myApp.client.vi.sys;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp02_EmpModelProperties;
import myApp.client.vi.sys.model.Sys05_UserRoleModel;
import myApp.client.vi.sys.model.Sys05_UserRoleModelProperties;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class Sys05_Tab_UserRole extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private TextField searchText = new TextField();
	private ComboBoxField workTypeComboBox = new ComboBoxField(null);
	
	private Grid<Emp02_EmpModel> userGrid = this.buildUserGrid();
	private Grid<Sys05_UserRoleModel> roleGrid = this.buildRoleGrid();
	
	
	public Sys05_Tab_UserRole() {
		
		this.setBorders(false);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "사원검색", 200, 56, true); 
		searchBarBuilder.addComboBox(workTypeComboBox, "재직구분", 150, 60);
		workTypeComboBox.add("전체");
		workTypeComboBox.add("재직");
		workTypeComboBox.add("퇴직");
		workTypeComboBox.setText("재직");
		
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();

		// 조회조건 
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49 
		northLayoutData.setMargins(new Margins(0,0,0,0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData); 

		// 사용자정보
		this.setCenterWidget(this.userGrid);
		userGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Emp02_EmpModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Emp02_EmpModel> event) {
				retrieveUserRole();  
			}
		});
		
		// Role 정보
		ContentPanel cp = new ContentPanel() ;
		cp.setHeaderVisible(false);
		cp.add(this.roleGrid);
		
		TextButton updateButton = new TextButton("저장"); 
		updateButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		}) ; 
		cp.getButtonBar().add(updateButton);
		cp.setButtonAlign(BoxLayoutPack.CENTER);
  
		BorderLayoutData southLayoutData = new BorderLayoutData(0.4);
		southLayoutData.setSplit(true);
		southLayoutData.setMargins(new Margins(3,0,0,0));
		southLayoutData.setMaxSize(1000);
		
		this.setSouthWidget(cp, southLayoutData);
		
	}
	
	public Grid<Emp02_EmpModel> buildUserGrid(){
		
		Emp02_EmpModelProperties properties = GWT.create(Emp02_EmpModelProperties.class);
		GridBuilder<Emp02_EmpModel> gridBuilder = new GridBuilder<Emp02_EmpModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.orgKorName(), 200, "부서명");
		gridBuilder.addText(properties.titleName(), 100, "직책");
		gridBuilder.addText(properties.empNo(), 80, "사번");
		gridBuilder.addText(properties.korName(), 100, "성명");
		gridBuilder.addText(properties.posName(), 100, "직위");
		gridBuilder.addText(properties.empKindName(), 80, "사원구분");
		gridBuilder.addText(properties.retireYn(), 60, "재직상태");
		gridBuilder.addText(properties.mobileTelno(), 120, "헨드폰번호");
		gridBuilder.addText(properties.emailAddr(), 150, "이메일주소");

		return gridBuilder.getGrid(); 
	}

	public Grid<Sys05_UserRoleModel> buildRoleGrid(){
		Sys05_UserRoleModelProperties userRoleModelProperties = GWT.create(Sys05_UserRoleModelProperties.class);
		GridBuilder<Sys05_UserRoleModel> gridBuilder = new GridBuilder<Sys05_UserRoleModel>(userRoleModelProperties.keyId());  
		gridBuilder.setChecked(SelectionMode.MULTI);
		
//		gridBuilder.addText(userRoleProperties.roleName(), 250, "권한명") ;
//		gridBuilder.addText(userRoleProperties.seq(), 80, "순서", new TextField()) ;
//		gridBuilder.addText(userRoleProperties.note(), 400, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

	
	public void retrieveUserRole(){
//		Sys02_UserModel user = userGrid.getSelectionModel().getSelectedItem() ; 
//		
//		if(user != null){
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("UserModel", user); 
//
//			GridRetrieveData<Sys05_UserRoleModel> service = new GridRetrieveData<Sys05_UserRoleModel>(roleGrid.getStore());
//			service.addParam("userId", user.getUserId());
//			service.retrieve("sys.Sys05_UserRole.selectByUserId");
//
//		}
//		else {
//			roleGrid.getStore().clear(); // clear data 
//		}
	}

	
	@Override // retrieve user 
	public void retrieve() {
		
		GridRetrieveData<Emp02_EmpModel> service = new GridRetrieveData<Emp02_EmpModel>(this.userGrid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("searchText", searchText.getText());
		service.addParam("workTypeCode", workTypeComboBox.getValue());  
		service.retrieve("emp.Emp02_Emp.selectByText", new InterfaceCallback() {
			@Override
			public void execute() {
				if(userGrid.getSelectionModel().getSelectedItems().size() < 1) { 
					// 조회한 후 선택된 행이 없으면 텝페이지를 초기화 한다. 
				}
			}
		});
	}

	@Override
	public void update() {
		GridUpdate<Sys05_UserRoleModel> service = new GridUpdate<Sys05_UserRoleModel>(); 
		service.update(roleGrid.getStore(), "sys.Sys05_UserRole.update");  
	}

	@Override
	public void insertRow() {
	}

	@Override
	public void deleteRow() {
	}
}
