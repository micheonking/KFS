package myApp.client.vi.sys;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp02_EmpModelProperties;
import myApp.client.vi.sys.model.Sys04_RoleModel;
import myApp.client.vi.sys.model.Sys04_RoleModelProperties;

/* 
 * 사원정보에 대한 Role을 설정한다.
 * User는 Sys05_Tab_UserRole에서 별도로 설정한다. 
 */

public class Sys05_Tab_EmpRole extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private TextField searchText = new TextField();
	//private ComboBoxField workTypeComboBox = new ComboBoxField(null);
	private StringComboBox workTypeCode= new StringComboBox();
	
	private Grid<Emp02_EmpModel> userGrid = this.buildUserGrid();
	private Grid<Sys04_RoleModel> userRoleGrid = this.buildRoleGrid();
	
	
	public Sys05_Tab_EmpRole() {
		
		this.setBorders(false);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "사원검색", 200, 56, true); 
		workTypeCode.getStore().add("전체");
		workTypeCode.getStore().add("재직");
		workTypeCode.getStore().add("퇴직");
		workTypeCode.setTriggerAction(TriggerAction.ALL);
		workTypeCode.setText("전체");
		
		FieldLabel workTypeComboBox = new FieldLabel(workTypeCode, "재직구분"); 
		workTypeComboBox.setWidth(150);
		workTypeComboBox.setLabelWidth(60);
		searchBarBuilder.getSearchBar().add(workTypeComboBox);
		searchBarBuilder.addRetrieveButton();
		
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
		cp.add(this.userRoleGrid);
		
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
		gridBuilder.addDate(properties.retireDate(), 100, "퇴직일");
		gridBuilder.addText(properties.mobileTelno(), 120, "헨드폰번호");
		gridBuilder.addText(properties.emailAddr(), 150, "이메일주소");

		return gridBuilder.getGrid(); 
	}

	public Grid<Sys04_RoleModel> buildRoleGrid(){
		Sys04_RoleModelProperties properties = GWT.create(Sys04_RoleModelProperties.class);
		GridBuilder<Sys04_RoleModel> gridBuilder = new GridBuilder<Sys04_RoleModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.roleName(), 200, "권한명") ;
		gridBuilder.addBoolean(properties.userRoleYn(), 40, "권한") ;
		gridBuilder.addText(properties.seq(), 80, "순서" ) ;
		gridBuilder.addText(properties.note(), 600, "비고");

		return gridBuilder.getGrid(); 
	}

	
	public void retrieveUserRole(){
		
		Emp02_EmpModel empModel = userGrid.getSelectionModel().getSelectedItem() ; 
		
		if(empModel != null){
			GridRetrieveData<Sys04_RoleModel> service = new GridRetrieveData<Sys04_RoleModel>(userRoleGrid.getStore());
			service.addParam("userId", empModel.getEmpId());
			service.addParam("companyId", LoginUser.getCompanyId());
			service.retrieve("sys.Sys04_Role.selectByUserId");
		}
		else {
			userRoleGrid.getStore().clear(); // clear data 
		}
	}

	
	@Override // retrieve user 
	public void retrieve() {
		
		GridRetrieveData<Emp02_EmpModel> service = new GridRetrieveData<Emp02_EmpModel>(this.userGrid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("searchText", searchText.getText());
		if("재직".equals(workTypeCode.getText())){
			service.addParam("workTypeCode", "WORK");
		} else if("퇴직".equals(workTypeCode.getText())) {
			service.addParam("workTypeCode", "RETIRE");
		}  
		else { 
			service.addParam("workTypeCode", "ALL");
		} 
		
		service.retrieve("emp.Emp02_Emp.selectByText", new InterfaceCallback() {
			@Override
			public void execute() {
//				if(userGrid.getSelectionModel().getSelectedItems().size() < 1) { 
//					// 조회한 후 선택된 행이 없으면 텝페이지를 초기화 한다. 
//				} else {
				userGrid.getSelectionModel().select(0, false);
			}
		});
	}

	@Override
	public void update() {
		
		Emp02_EmpModel empModel = userGrid.getSelectionModel().getSelectedItem() ; 
		
		if(empModel != null) {
			GridUpdate<Sys04_RoleModel> service = new GridUpdate<Sys04_RoleModel>(); 
			service.addParam("userId", empModel.getEmpId());
			service.addParam("companyId", LoginUser.getCompanyId());
			service.update(userRoleGrid.getStore(), "sys.Sys04_Role.updateUserRole"); 
		}
		else {
			new SimpleMessage("선택된 사원이 없습니다."); 
			return ; 
		}
	}

	@Override
	public void insertRow() {
	}

	@Override
	public void deleteRow() {
	}
}
