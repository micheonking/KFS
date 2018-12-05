package myApp.client.vi.dcr;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModelProperties;

public class Dcr01_Tab_ClassTree extends BorderLayoutContainer implements InterfaceServiceCall{
	private TreeGrid<Dcr01_ClassTreeModel> treeGrid = this.buildTreeGrid();
	private Dcr01_Grid_DocType gridDocType;
	private Dcr01_ClassTreeModel refreshData; 
	
	public Dcr01_Tab_ClassTree(){
		
		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.setWidth(70);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		});
		TextButton insertRootButton = new TextButton("업무등록");
		insertRootButton.setWidth(70);
		insertRootButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertDcrClass(Long.parseLong("0"), "루트등록", "insert"); 
			}
		});
		TextButton insertChildButton = new TextButton("하위등록");
		insertChildButton.setWidth(70);
		insertChildButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertChildDcrClass(); 
			}
		});
		
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.setSpacing(5);
		buttonBar.add(retrieveButton); //조회버튼
		buttonBar.add(insertRootButton); //업무등록
		buttonBar.add(insertChildButton); //하위분류등록
		
		this.setNorthWidget(buttonBar, new BorderLayoutData(50));
		
		BorderLayoutData westLayoutData = new BorderLayoutData(0.4);
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(0, 2, 0, 0)); 

		this.setWestWidget(this.treeGrid, westLayoutData);//왼쪽 컨테이너 표시
		this.treeGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Dcr01_ClassTreeModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Dcr01_ClassTreeModel> event) {
				if(event.getSelection().size() > 0){
					retrievePage(); 
				}
			} 
		});

		this.gridDocType = new Dcr01_Grid_DocType();
		this.setCenterWidget(this.gridDocType); 
		
		retrieve();
	}
	
	public TreeGrid<Dcr01_ClassTreeModel> buildTreeGrid(){
		
		Dcr01_ClassTreeModelProperties properties = GWT.create(Dcr01_ClassTreeModelProperties.class);
		GridBuilder<Dcr01_ClassTreeModel> gridBuilder = new GridBuilder<Dcr01_ClassTreeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.classTreeName(), 300, "분류명"); //, new TextField());
		gridBuilder.addText(properties.classTreeCode(), 70, "분류코드"); //, new TextField());
		ActionCell<String> editOrgInfoCell = new ActionCell<String>("Edit", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				editItem(); 
			}
		});
		gridBuilder.addCell(properties.actionCell(), 50, "수정", editOrgInfoCell); //, new TextField()) ;
		gridBuilder.addText(properties.seq(), 70, "조회순서"); //, new TextField());
		gridBuilder.addText(properties.note(), 200, "비고"); //, new TextField());

		return gridBuilder.getTreeGrid(2);
	}
	

	public void retrieve() {
		this.treeGrid.mask("Loading");
		ServiceRequest request = new ServiceRequest("dcr.Dcr01_ClassTree.selectByCompanyId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putStringParam("typeCode", "C");
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		
	}

	private void addChildItem(Dcr01_ClassTreeModel parentItem) {
		// 조회 시 하위 노드 생성 함수. 
		if(parentItem.getChildList() != null){
			
			for(GridDataModel child : parentItem.getChildList()){
			
				Dcr01_ClassTreeModel childClassModel = (Dcr01_ClassTreeModel)child; 
				this.treeGrid.getTreeStore().add(parentItem, childClassModel);
				this.addChildItem(childClassModel);
			}
		}
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0) {
			Info.display("error", result.getMessage());
		}
		else {
			this.treeGrid.getTreeStore().clear();
			for(GridDataModel model: result.getResult()) {
				Dcr01_ClassTreeModel item = (Dcr01_ClassTreeModel)model;
				this.treeGrid.getTreeStore().add(item);
				this.addChildItem(item);
			}
			
			if(this.refreshData != null) {
				treeGrid.setExpanded(this.refreshData, true);
				treeGrid.getSelectionModel().select(treeGrid.getTreeStore().findModel(this.refreshData), false);
				this.refreshData= null; // 초기화 시킨다. 
			}
		}
		this.treeGrid.unmask();
	}

	private void insertChildDcrClass() {
		Dcr01_ClassTreeModel parentModel = treeGrid.getSelectionModel().getSelectedItem();//선택된 문서분류를 가져온다.
		if(parentModel == null) {
			new SimpleMessage("하위분류가 등록될 상위분류를 먼저 선택하여야 합니다.");
			return;
		} else if(gridDocType.grid.getStore().getAll().size() > 0){
			new SimpleMessage("등록된 문서분류가 있습니다.");
			return;
		} else {
			//상위분류 ID를 가져온다.
			insertDcrClass(parentModel.getClassTreeId(), "하위분류 등록", "insert");
		}
	}
	
	private void insertDcrClass(Long parentId, String title, String actionCode){
		// 업무등록, 업무는 하나만 등록이 가능하다. 
		Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();

		DBUtil dbUtil = new DBUtil();
		dbUtil.setSeq(classTreeModel, new InterfaceCallback() {
			@Override
			public void execute() {
				classTreeModel.setCompanyId(LoginUser.getCompanyId()); 
				classTreeModel.setParentTreeId(parentId); // 상위업무ID 설정, ROOT일경우에는 0 이다.
				classTreeModel.setTypeCode("C"); // 문서분류구분은 "C"이다.  
				
				Dcr01_Edit_ClassTree editClassTree = new Dcr01_Edit_ClassTree();
				editClassTree.editData(treeGrid, classTreeModel, actionCode, title, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						refreshData = (Dcr01_ClassTreeModel)result ; 
						retrieve(); // 재조회한다.  
					}
				});
			}
		});
	}

	private void editItem(){
		Dcr01_ClassTreeModel classTreeModel = treeGrid.getSelectionModel().getSelectedItem();
		Dcr01_Edit_ClassTree popup_Classification = new Dcr01_Edit_ClassTree();
		popup_Classification.editData(treeGrid, classTreeModel, "edit", "문서분류수정", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				refreshData = (Dcr01_ClassTreeModel)result ; 
				retrieve(); // 재조회한다.  
			}
		});
	}
	
	private void retrievePage(){
		Dcr01_ClassTreeModel classTreeModel = this.treeGrid.getSelectionModel().getSelectedItem();
		gridDocType.retrieve(classTreeModel);
	}
}