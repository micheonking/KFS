package myApp.client.vi.apr;

import java.util.Date;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.ClientDateUtil;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr03_RelateFundModel;
import myApp.client.vi.apr.model.Apr03_RelateFundModelProperties;
import myApp.client.vi.dcr.Dcr10_Lookup_MailSender;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Apr01_Grid_DocList extends VerticalLayoutContainer {
	
	private Grid<Apr03_RelateFundModel> grid = this.buildGrid();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
	
	private ButtonBar buttonBar = new ButtonBar();
	private DateField startDate = new DateField();
	private DateField endDate = new DateField();
	private TextField regEmpName = new TextField();
	private TextField orgName = new TextField();
	public  TextButton insertButton = new TextButton("신규문서 등록(상신)");	
	private Boolean   isAdmin = false; 
	
//	private Boolean isAdmin = false;

	public Apr01_Grid_DocList(){
		init(false);
	}

	public Apr01_Grid_DocList(Boolean editable){
		init(editable);
	}

	public void init(Boolean editable) {
		this.buttonBar.setEnabled(false); // 초기에는 사용못한다. 
		this.buttonBar.add(new LabelToolItem("▶ 등록기간:")); 

		// check date 
		Date fromDate = ClientDateUtil.toDate(new Date());
		CalendarUtil.addMonthsToDate(fromDate, -12);

		this.startDate.setValue(fromDate);
		this.startDate.setWidth(100); 
		this.buttonBar.add(startDate);
		
		this.buttonBar.add(new LabelToolItem("~"));
		
		this.endDate.setValue(LoginUser.getToday());
		this.endDate.setWidth(100);
		this.buttonBar.add(endDate);
		
		this.buttonBar.add(new LabelToolItem("")); // 칸띄우기 
		this.buttonBar.add(new LabelToolItem("등록자:")); //, new BoxLayoutData(new Margins(0, 0, 0, 100)));

		this.regEmpName.setWidth(100);
		this.regEmpName.setEmptyText("등록자");
		this.buttonBar.add(regEmpName);
		
		regEmpName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) { // enter key event 
					retrieve();
				}
			}
		});

		this.orgName.setWidth(100);
		this.orgName.setEmptyText("주관부서");
		this.buttonBar.add(orgName);
		
		orgName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) { // enter key event 
					retrieve();
				}
			}
		});
		
		TextButton retrieveButton = new TextButton("검색");
		retrieveButton.setWidth(60);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		});
		this.buttonBar.add(retrieveButton); //필터적용버튼

		if(editable) { // 신규문서 등록이나 상신이 가능한 경우, 조회팝업에서는 등록버튼을 감춘다. 
			insertButton.setWidth(130);
			insertButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {
					insertApproval(); 
				}
			});
			this.buttonBar.add(insertButton); //신규문서 등록
		}

		this.add(this.buttonBar, new VerticalLayoutData(1, 49, new Margins(0, 0, 0, 0)));
		this.add(this.grid, new VerticalLayoutData(1, 1)); 
	}

     public Grid<Apr03_RelateFundModel> buildGrid(){
		
    	 Apr03_RelateFundModelProperties properties = GWT.create(Apr03_RelateFundModelProperties.class);
    	 GridBuilder<Apr03_RelateFundModel> gridBuilder = new GridBuilder<Apr03_RelateFundModel>(properties.keyId());
		
    	 gridBuilder.setChecked(SelectionMode.SINGLE);
    	 gridBuilder.addText(properties.fundCode(), 80, "펀드코드");
    	 gridBuilder.addText(properties.fundName(), 250, "연관펀드명");
    	 gridBuilder.addText(properties.title(), 250, "상신제목");
    	 
    	 ActionCell<String> detailCell = new ActionCell<String>("Detail", new ActionCell.Delegate<String>(){
    		 @Override
    		 public void execute(String arg0) {
    			 Apr03_RelateFundModel relateFundModel = grid.getSelectionModel().getSelectedItem();
    			 Apr01_Edit_ApprDoc apprEditor = new Apr01_Edit_ApprDoc();   
    			 apprEditor.retrieveApproval(relateFundModel.getApprModel(), isAdmin, new InterfaceCallback() {
    					@Override
    					public void execute() {
    						retrieve();
    					}
    				});
    		 }
    	 });
    	 gridBuilder.addCell(properties.detailCell(), 70, "상세보기", detailCell); //, new TextField()) ;
    	 
    	 ActionCell<String> sendMailCell = new ActionCell<String>("Send", new ActionCell.Delegate<String>(){
    		 @Override
    		 public void execute(String arg0) {
    			 sendMail();
    		 }
    	 });
    	 gridBuilder.addCell(properties.sendMailCell(), 70, "메일보내기", sendMailCell); //, new TextField()) ;

    	 gridBuilder.addDate(properties.regDate(), 90, "등록일");
    	 gridBuilder.addDate(properties.effectDate(), 90, "시행일");
    	 gridBuilder.addText(properties.empName(), 80, "등록자");
    	 gridBuilder.addText(properties.orgName(), 150, "등록부서");

    	 return gridBuilder.getGrid(); 
     }
	
	public void retrieve(Dcr01_ClassTreeModel classTreeModel, Boolean isAdmin) {
		
		this.classTreeModel = classTreeModel; 
		this.isAdmin = isAdmin;
		this.grid.getStore().clear();
		
		if("C".equals(classTreeModel.getTypeCode())){
			buttonBar.setEnabled(false);
		}
		else {
			buttonBar.setEnabled(true);
			this.retrieve();
			if(!isAdmin) {
				if("true".equals(classTreeModel.getOrgAuthModel().getUpdateYn())) {
					insertButton.enable();
				} 
				else {
					insertButton.disable();
				}
			}
		}
	}
     
	private void retrieve() {
		this.grid.mask("Loading");
		GridRetrieveData<Apr03_RelateFundModel> service = new GridRetrieveData<Apr03_RelateFundModel>(this.grid.getStore());
		service.addParam("classTreeId", this.classTreeModel.getClassTreeId());
		service.addParam("startDate", startDate.getValue());
		service.addParam("endDate", endDate.getValue());
		service.addParam("regEmpName", regEmpName.getText());
		service.addParam("orgName", orgName.getText());
		service.addParam("orgId", LoginUser.getOrgCodeId());
		if(isAdmin) {
			service.addParam("admin", "true");
		} else {
			service.addParam("admin", "false");
		}
		service.retrieve("apr.Apr03_RelateFund.selectByClassTreeId2", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
	
	public void insertApproval() {
		
		if(this.classTreeModel.getClassTreeId() == null) {
			new SimpleMessage("문서상신을 위한 문서분류를 먼저 선택하여야 합니다."); 
			return; 
		}

		Apr01_Edit_ApprDoc editor = new Apr01_Edit_ApprDoc();
		editor.insertApproval(classTreeModel, this.isAdmin, new InterfaceCallback() {
			@Override
			public void execute() {
				retrieve();
			}
		}); 
	}

	
//	
//	public void makeLayout() {
//		//grid.getColumnModel().getColumn(3).setHidden(true);
//		treeGrid.getColumnModel().getColumn(2).setWidth(200);
//		treeGrid.getView().refresh(true);
//	}
//	
//	public Dcr04_DocModel returnSelectedGrid() {
//		//return grid.getSelectionModel().getSelectedItem();
//		return treeGrid.getSelectionModel().getSelectedItem();
//	}
//	
//	private void addChildItem(Dcr04_DocModel parentItem) {
//		// 조회 시 하위 노드 생성 함수. 
//		if(parentItem.getChildList() != null){
//			
//			for(GridDataModel child : parentItem.getChildList()){
//			
//				Dcr04_DocModel childClassModel = (Dcr04_DocModel)child; 
//				this.treeGrid.getTreeStore().add(parentItem, childClassModel);
//				this.addChildItem(childClassModel);
//			}
//		}
//	}
//
//	@Override
//	public void getServiceResult(ServiceResult result) {
//		// TODO Auto-generated method stub
//		if(result.getStatus() < 0) {
//			Info.display("error", result.getMessage());
//		}
//		else {
//			this.treeGrid.getTreeStore().clear();
//			for(GridDataModel model: result.getResult()) {
//				Dcr04_DocModel item = (Dcr04_DocModel)model;
//				this.treeGrid.getTreeStore().add(item);
//				this.addChildItem(item);
//			}
//			
//			if(this.refreshData != null) {
//				treeGrid.setExpanded(this.refreshData, true);
//				treeGrid.getSelectionModel().select(treeGrid.getTreeStore().findModel(this.refreshData), false);
//				this.refreshData= null; // 초기화 시킨다. 
//			}
//		}
//		
//		
//		treeGrid.unmask();
//	}
//	
//	private void showDetail() {
//		Dcr04_DocModel dcrModel = treeGrid.getSelectionModel().getSelectedItem();
//		
//		if("결재".equals(dcrModel.getTypeName())) {
//			Dcr04_DocModel paramModel = new Dcr04_DocModel();
//			paramModel.setDocId(treeGrid.getSelectionModel().getSelectedItem().getKeyId());//문서분류 초기값 설정
//			paramModel.setDocTypeName(classTreeModel.getClassTreeName());//문서분류명 설정
//			paramModel.setRegEmpId(LoginUser.getUserId()); // 등록자ID 설정
//			paramModel.setRegEmpName(LoginUser.getUserName());//등록자명 설정
//			
//			Apr01_Edit_AprInsert editor = new Apr01_Edit_AprInsert(null, paramModel, "view");
//			editor.show();
//		} 
//		else {
//			dcrModel.setDocTypeName(classTreeModel.getClassTreeName());//문서분류명 설정
//			//Dcr04_Edit_Doc editor = new Dcr04_Edit_Doc(grid, dcrModel, "edit");
//			
//			Dcr04_Edit_Doc editor = new Dcr04_Edit_Doc(treeGrid, dcrModel);
//			if("true".equals(classTreeModel.getOrgAuthModel().getUpdateYn()) && dcrModel.getApprId() == null){
//				editor.editDcr(new InterfaceCallback() {
//					@Override
//					public void execute() {
//						retrieve();
//					}
//				});
//			} else {
//				editor.retrieveDcr();
//			}
//		}
//	}
//	
	private void sendMail() {
		Apr03_RelateFundModel relateFundModel = grid.getSelectionModel().getSelectedItem();
		if(!"30".equals(relateFundModel.getApprModel().getStatusCode())) {
			new SimpleMessage("완료되지 않은 문서입니다.");
		} else {
			//Dcr10_Lookup_MailSender mailSender = new Dcr10_Lookup_MailSender(dcrModel.getDocId(), "");
			Dcr10_Lookup_MailSender mailSender = new Dcr10_Lookup_MailSender(relateFundModel.getApprModel().getApprId(), "");
			mailSender.show();
		}
	}
}
