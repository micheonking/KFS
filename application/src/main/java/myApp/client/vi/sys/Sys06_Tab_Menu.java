package myApp.client.vi.sys;

import java.awt.BorderLayout;
import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.client.vi.sys.model.Sys01_CompanyModelProperties;
import myApp.client.vi.sys.model.Sys06_MenuModel;
import myApp.client.vi.sys.model.Sys06_MenuModelProperties;

public class Sys06_Tab_Menu extends BorderLayoutContainer implements InterfaceServiceCall {
	
	private TreeGrid<Sys06_MenuModel> treeGrid = this.buildTreeGrid();
	private Sys01_Grid_CompanyMenu grid = new Sys01_Grid_CompanyMenu(); 
	
	public Sys06_Tab_Menu(){

		ButtonBar buttonBar = new ButtonBar();

		TextButton retrieveButton = new TextButton("메뉴 조회"); 
		retrieveButton.setWidth(70);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		}); 
		buttonBar.add(retrieveButton);

		TextButton createRoot = new TextButton("루트메뉴 등록"); 
		createRoot.setWidth(100);
		createRoot.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				// root menu는 parentId가 0 이다. 
				insertMenu(Long.parseLong("0")); 
			}
		}); 
		buttonBar.add(createRoot);

		TextButton addSubMenu = new TextButton("하위메뉴 등록");
		addSubMenu.setWidth(100);
		addSubMenu.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertChildMenu(); 
			}
		}); 
		buttonBar.add(addSubMenu);
		this.setNorthWidget(buttonBar, new BorderLayoutData(50));

		
		BorderLayoutData westLayoutData = new BorderLayoutData(0.5);
		westLayoutData.setSplit(true);
		westLayoutData.setMargins(new Margins(0,4,0,0));
		westLayoutData.setMaxSize(1000);
		this.setWestWidget(this.treeGrid, westLayoutData);
		this.treeGrid.getTreeStore().setAutoCommit(true);
		this.treeGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Sys06_MenuModel>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Sys06_MenuModel> event) {
				Sys06_MenuModel data = event.getSource().getSelectedItem(); 
				if(data != null) {
					grid.retrieve(data.getMenuId()); 
				}
			}
		}); 
		
		this.setCenterWidget(this.grid);
		retrieve();
	}
	
	public TreeGrid<Sys06_MenuModel> buildTreeGrid(){
		
		Sys06_MenuModelProperties properties = GWT.create(Sys06_MenuModelProperties.class);
		final GridBuilder<Sys06_MenuModel> gridBuilder = new GridBuilder<Sys06_MenuModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.menuName(), 250, "메뉴명"); //, new TextField());

		ActionCell<String> editCell = new ActionCell<String>("Edit", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				editMenu(); 
			}
		});
		gridBuilder.addCell(properties.editCell(), 60, "수정", editCell) ;


		ActionCell<String> moveCell = new ActionCell<String>("Move", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				moveMenu(); 
			}
		});
		gridBuilder.addCell(properties.moveCell(), 60, "이동", moveCell) ;

		gridBuilder.addBoolean(properties.useYnFlag(), 40, "사용") ;
		gridBuilder.addText(properties.seq(), 60, "순서"); //, new TextField()) ;
		gridBuilder.addText(properties.className(), 200, "클래스명"); //, new TextField()) ;
		gridBuilder.addText(properties.note(),500, "상세설명"); //, new TextField());
		gridBuilder.setSortInfo(properties.seq(), SortDir.ASC);
		
		return gridBuilder.getTreeGrid(2);  
	}

	private void moveMenu() {
		Sys06_Select_Menu selectMenu = new Sys06_Select_Menu();
		selectMenu.open(treeGrid, treeGrid.getSelectionModel().getSelectedItem());
	}
	
	private void editMenu() {
		Sys06_Edit_Menu editMenu = new Sys06_Edit_Menu(); 
		editMenu.editMenu(treeGrid, treeGrid.getSelectionModel().getSelectedItem());
	}
	public void retrieve(){
		// 서버에서 전체 트리를 한번에 가져온다.  
		ServiceRequest request = new ServiceRequest("sys.Sys06_Menu.selectByAll");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void setChildMenuItem(Sys06_MenuModel parentMenu) {
		if(parentMenu.getChildList() != null){
			List<GridDataModel> childList = parentMenu.getChildList(); 
			for(GridDataModel child : childList){
				Sys06_MenuModel childObject = (Sys06_MenuModel)child;
				this.treeGrid.getTreeStore().add(parentMenu, childObject);
				this.setChildMenuItem(childObject);
			}
		}
	}
	
	private void insertMenu(Long parentId){

		Sys06_MenuModel menuModel = new Sys06_MenuModel();
		
		DBUtil dbUtil = new DBUtil(); 
		dbUtil.setSeq(menuModel, new InterfaceCallback() {
			@Override
			public void execute() {
				menuModel.setParentId(parentId);
				menuModel.setNote("insert menu");
				Sys06_Edit_Menu editMenu = new Sys06_Edit_Menu(); 
				editMenu.editMenu(treeGrid, menuModel);
			}
		});
	}

	private void insertChildMenu(){
		Sys06_MenuModel parentModel = treeGrid.getSelectionModel().getSelectedItem(); // 선택된 Menu를 가져온다.

		if(parentModel == null){
			new SimpleMessage("선택된 상위 메뉴가 없습니다. "); 
			return ; 
		}
		
		if(parentModel.getClassName() != null){
			// objectID가 있으면 하위메뉴를 등록할 수 없다. 
			new SimpleMessage("오브젝트에는 하위 메뉴를 등록할 수 없습니다."); 
			return ; 
		}
		
		treeGrid.setExpanded(parentModel, true);
		this.insertMenu(parentModel.getMenuId());
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
		}
		else { 
			this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다. 
			
			for (GridDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				Sys06_MenuModel roleObject = (Sys06_MenuModel)model;   
				this.treeGrid.getTreeStore().add(roleObject);
				this.setChildMenuItem(roleObject); // child menu & object setting  
			}
		} 
		
	}
}
