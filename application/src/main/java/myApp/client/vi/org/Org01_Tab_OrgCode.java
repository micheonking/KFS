package myApp.client.vi.org;

import java.util.List;

import myApp.client.grid.GridBuilder;
import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.vi.org.model.Org01_CodeModelProperties;
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

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

public class Org01_Tab_OrgCode extends VerticalLayoutContainer implements InterfaceServiceCall {
	
	private TreeGrid<Org01_CodeModel> treeGrid = this.buildTreeGrid();
	private DateField baseDate = new DateField();
	private Org01_CodeModel updatedModel ; 
	
	public Org01_Tab_OrgCode(){

		baseDate.setValue(LoginUser.getToday()); //오늘일자로 설정한다.
		FieldLabel dateFiledLabel = new FieldLabel(baseDate, "기준일");
		dateFiledLabel.setWidth(160);
		dateFiledLabel.setLabelWidth(50);
		
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.add(dateFiledLabel);
		
		//dateField.set
		TextButton retrieveButton = new TextButton("조회"); 
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		}); 
		retrieveButton.setWidth(70);
		buttonBar.add(retrieveButton);
		
//		TextButton createRoot = new TextButton("회사등록"); 
//		createRoot.addSelectHandler(new SelectHandler(){
//			@Override
//			public void onSelect(SelectEvent event) {
//				insertOrgCode(null); 
//			}
//		}); 
//		createRoot.setWidth(70);
//		buttonBar.add(createRoot);

		TextButton addSubMenu = new TextButton("하위조직 등록");
		addSubMenu.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertChild(); 
			}
		}); 
		buttonBar.add(addSubMenu);
		
		this.add(buttonBar, new VerticalLayoutData(1, 50));
		this.add(this.treeGrid, new VerticalLayoutData(1, 1));
		this.retrieve();
	}
	
	public TreeGrid<Org01_CodeModel> buildTreeGrid(){
		
		Org01_CodeModelProperties properties = GWT.create(Org01_CodeModelProperties.class);
		GridBuilder<Org01_CodeModel> gridBuilder = new GridBuilder<Org01_CodeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.korName(), 300, "조직명"); //, new TextField());
		gridBuilder.addText(properties.orgCode(), 60, "조직코드"); //, new TextField());
		gridBuilder.addText(properties.levelName(), 60, "조직구분"); //, new TextField()) ;
		gridBuilder.addText(properties.sortOrder(), 60, "조회순서"); //, new TextField()) ;
		gridBuilder.addDate(properties.modDate(), 100, "최근변경일"); //, new TextField()) ;

		ActionCell<String> editOrgInfoCell = new ActionCell<String>("Edit", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				editOrgCode(); 
			}
		});
		gridBuilder.addCell(properties.actionCell(), 60, "수정", editOrgInfoCell); //, new TextField()) ;
		gridBuilder.addText(properties.modReason(), 400, "변경사유"); //, new TextField());
		gridBuilder.addDate(properties.openDate(), 100, "개설일"); //, new TextField()) ;
		gridBuilder.addDate(properties.closeDate(), 100, "폐쇄일"); //, new TextField()) ;
		
		return gridBuilder.getTreeGrid(2);  
	}

	private void retrieve(){
		ServiceRequest request = new ServiceRequest("org.Org01_Code.selectByCompanyId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putDateParam("baseDate", baseDate.getValue());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addChildItem(Org01_CodeModel parentItem) {
		// 조회 시 하위 노드 생성 함수. 
		if(parentItem.getChildList() != null){
			List<GridDataModel> childList = parentItem.getChildList(); 
			for(GridDataModel child : childList){
				Org01_CodeModel childObject = (Org01_CodeModel)child;
				this.treeGrid.getTreeStore().add(parentItem, childObject);
				this.addChildItem(childObject);
			}
		}
	}

	private void insertOrgCode(Org01_CodeModel parentModel){

		Long parentCodeId = Long.parseLong("0"); 
		
		if(parentModel != null) {
			parentCodeId = parentModel.getCodeId() ; 
		}
		
		Org01_CodeModel orgCodeModel = new Org01_CodeModel();
		orgCodeModel.setCompanyId(LoginUser.getCompanyId()); // 회사ID
		orgCodeModel.getOrgInfoModel().setParentCodeId(parentCodeId); // 상위조직ID 설정, ROOT일경우에는 0 이다.
		
		DBUtil dbUtil = new DBUtil(); 
		dbUtil.setSeq(orgCodeModel, new InterfaceCallback() {
			@Override
			public void execute() {
				Long seq = orgCodeModel.getKeyId() ; 
				orgCodeModel.setCodeId(seq); // 조직정보 ID, 조직코드와 같이 사용한다.
				orgCodeModel.getOrgInfoModel().setInfoId(seq);
				orgCodeModel.getOrgInfoModel().setCodeId(seq); // CodeId와 InfoId를 같이 맞춘다. 
				
				Org01_Edit_OrgCode editOrgCode = new Org01_Edit_OrgCode();
				editOrgCode.editData(treeGrid, parentModel, orgCodeModel, baseDate.getValue(), "insertData", new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						updatedModel = (Org01_CodeModel)result; 
						retrieve();
						
					} 
				});
			}
		});
	}

	private void insertChild(){
		Org01_CodeModel parentModel = treeGrid.getSelectionModel().getSelectedItem();
		
		if(parentModel == null) {
			new SimpleMessage("하위조직이 등록될 상위 조직을 먼저 선택하여야 합니다. ");
			return; 
		}
		else { 
			// 상위조직의 조직코드ID를 가져온다. 
			insertOrgCode(parentModel); 
		}
	}
	
	private void editOrgCode(){
		Org01_CodeModel editModel  = treeGrid.getSelectionModel().getSelectedItem() ;
		Org01_CodeModel parentModel = treeGrid.getTreeStore().getParent(editModel); 
				
		Org01_Edit_OrgCode editOrgCode = new Org01_Edit_OrgCode();
		editOrgCode.editData(treeGrid, parentModel, editModel, baseDate.getValue(), "editCode", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				updatedModel = (Org01_CodeModel)result; 
				retrieve();
			} 
		});
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		
		this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다.
		
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return; 
		}

		for (GridDataModel model: result.getResult()) {
			// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
			Org01_CodeModel item = (Org01_CodeModel)model;   
			this.treeGrid.getTreeStore().add(item);
			this.addChildItem(item); // child menu & object setting
		}

		for (GridDataModel model: result.getResult()) {
			Org01_CodeModel item = (Org01_CodeModel)model;
			this.treeGrid.setExpanded(item, true);
		}
		
		if(this.updatedModel != null) {
			treeGrid.setExpanded(updatedModel, true);
			treeGrid.getSelectionModel().select(treeGrid.getTreeStore().findModel(updatedModel), false);
			updatedModel = null; // 초기화 시킨다. 
		}
	}
}
