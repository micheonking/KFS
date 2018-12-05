package myApp.client.vi.apr;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.HeaderGroupMap;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.ClientDateUtil;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr07_AltApprModel;
import myApp.client.vi.apr.model.Apr07_AltApprModelProperties;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr07_Tab_AltApprMgr extends BorderLayoutContainer {
//	private	final Logger logger = Logger.getLogger(this.getClass().getName());
	private Date toDay = new Date();
	
	Apr07_AltApprModelProperties properties = GWT.create(Apr07_AltApprModelProperties.class);
	private Grid<Apr07_AltApprModel> grid = this.buildGrid();
	private LabelToolItem label01 = new LabelToolItem("▶ 대결자 등록");
	private LabelToolItem label02 = new LabelToolItem("등록기간:");
	private LabelToolItem label03 = new LabelToolItem("원결재자:");

	private TextButton retrieveButton = new TextButton("조회");
	private TextButton deleteButton = new TextButton("삭제");
	private TextButton insertRowButton = new TextButton("입력");
	private TextButton saveButton = new TextButton("저장");
	private MyDateField startDate = new MyDateField();
	private MyDateField endDate = new MyDateField();
	private TextField searchText  = new TextField();
	
	public Apr07_Tab_AltApprMgr() {
		makeLayout();
		setInitData();
	}
	
	private void makeLayout() {
		ButtonBar buttonBar01 = new ButtonBar();
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		deleteButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				delete();
			}
		});
		insertRowButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				insertRow();
			}
		});
		saveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		});
		searchText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		}); 
		
		
		buttonBar01.add(label01);
		buttonBar01.add(label02);
		startDate.setWidth(100);
		endDate.setWidth(100);
		buttonBar01.add(startDate);
		
		buttonBar01.add(endDate);
		buttonBar01.add(label03);
		buttonBar01.add(searchText);
		buttonBar01.add(retrieveButton);
		buttonBar01.add(deleteButton);
		buttonBar01.add(insertRowButton);
		buttonBar01.add(saveButton);
		
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49
		this.setNorthWidget(buttonBar01, northLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData(0.5);
		centerLayoutData.setMargins(new Margins(3, 0, 0, 0)); // tabPage Top Margin
		this.setCenterWidget(getCenterLayout(), centerLayoutData);
		
	}

	private Grid<Apr07_AltApprModel> buildGrid(){
				
		GridBuilder<Apr07_AltApprModel> gridBuilder = new GridBuilder<Apr07_AltApprModel>(properties.keyId());  

		gridBuilder.setChecked(SelectionMode.SINGLE);
	
		gridBuilder.addHeaderGroupMap(new HeaderGroupMap(0, 2, new HeaderGroupConfig("원결재자", 1, 4)));
		gridBuilder.addHeaderGroupMap(new HeaderGroupMap(0, 6, new HeaderGroupConfig("대결자", 1, 4)));
		
		//원결재자선택
		LookupTriggerField lookupEmpInfo1 = new LookupTriggerField();
		lookupEmpInfo1.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupEmpInfo(1);
			}
		});
		
		//대결자선택
		LookupTriggerField lookupEmpInfo2 = new LookupTriggerField();
		lookupEmpInfo2.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupEmpInfo(2);
			}
		});
		
		gridBuilder.addText(properties.targetOrgName(),      150,  "부서");
		gridBuilder.addText(properties.targetTitleName(),    90,  "직책");
		//gridBuilder.addText(properties.targetPositionName(), 90,  "직위");
		gridBuilder.addText(properties.targetEmpNo(),        90,  "사번");
		gridBuilder.addText(properties.targetEmpName(),      90,  "성명", lookupEmpInfo1);
		gridBuilder.addText(properties.altOrgName(),         150,  "부서");
		gridBuilder.addText(properties.altTitleName(),       90,  "직책");
		//gridBuilder.addText(properties.altPositionName(),    90,  "직위");
		gridBuilder.addText(properties.altEmpNo(),           90,  "사번");
		gridBuilder.addText(properties.altEmpName(),         90,  "성명",   lookupEmpInfo2);
		gridBuilder.addDate(properties.startDate(),          100, "시작일",      new DateField());
		gridBuilder.addDate(properties.closeDate(),          100, "종료일",      new DateField());
		gridBuilder.addText(properties.reasonCd(),           90,  "대결사유",     new TextField());
		gridBuilder.addText(properties.emgrPoint(),          120, "본인비상연락망", new TextField());
		gridBuilder.addText(properties.note(),               90,  "비고",        new TextField());
		
		
		//gridBuilder.addText(properties.apprEmpName(), 80, "결재자");
		
		return gridBuilder.getGrid(); 
	}
	
	private BorderLayoutContainer getCenterLayout() {
		BorderLayoutContainer blc = new BorderLayoutContainer();
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		VerticalLayoutData dcrGridLayout = new VerticalLayoutData();
		dcrGridLayout.setHeight(1);
		vlc.add(grid, dcrGridLayout);
		
		
		blc.add(vlc);
		
		return blc;
	}
	
	private void openLookupEmpInfo(int flag) {
		
		Emp00_Lookup_EmpInfo lookupEmpInfo = new Emp00_Lookup_EmpInfo(); 
		lookupEmpInfo.open(new Date(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {

				Emp00_InfoModel empInfoModel = (Emp00_InfoModel)result;
				Apr07_AltApprModel	selectModel = grid.getSelectionModel().getSelectedItem();
				if(flag == 1) {
					grid.getStore().getRecord(selectModel).addChange(properties.targetEmpId()		 , empInfoModel.getEmpId());
					grid.getStore().getRecord(selectModel).addChange(properties.targetEmpNo()		 , empInfoModel.getEmpNo());
					grid.getStore().getRecord(selectModel).addChange(properties.targetEmpName()	     , empInfoModel.getKorName());
					grid.getStore().getRecord(selectModel).addChange(properties.targetOrgName()	     , empInfoModel.getOrgName());
					grid.getStore().getRecord(selectModel).addChange(properties.targetTitleName()	 , empInfoModel.getTitleName());
					grid.getStore().getRecord(selectModel).addChange(properties.targetPositionName() , empInfoModel.getPositionName());
				} else if(flag == 2) {
					grid.getStore().getRecord(selectModel).addChange(properties.altEmpId()		  , empInfoModel.getEmpId());
					grid.getStore().getRecord(selectModel).addChange(properties.altEmpNo()		  , empInfoModel.getEmpNo());
					grid.getStore().getRecord(selectModel).addChange(properties.altEmpName()	  , empInfoModel.getKorName());
					grid.getStore().getRecord(selectModel).addChange(properties.altOrgName()	  , empInfoModel.getOrgName());
					grid.getStore().getRecord(selectModel).addChange(properties.altTitleName()	  , empInfoModel.getTitleName());
					grid.getStore().getRecord(selectModel).addChange(properties.altPositionName() , empInfoModel.getPositionName());
				}
			}
		});
	}
	
	private void setInitData() {
		toDay = ClientDateUtil.toDate(toDay);
		Date endDate = CalendarUtil.copyDate(toDay);
		CalendarUtil.addDaysToDate(endDate, -7);
		this.endDate.setValue(toDay);
		this.startDate.setValue(endDate);
	}
	
	private void retrieve() {
		GridRetrieveData<Apr07_AltApprModel> service = new GridRetrieveData<Apr07_AltApprModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("empId", LoginUser.getUserId());
		service.addParam("startDate", startDate.getValue());
		service.addParam("endDate", endDate.getValue());
		service.addParam("searchText", searchText.getText());
		service.retrieve("apr.Apr07_AltAppr.selectByText", new InterfaceCallback() {
			@Override
			public void execute() {
			}
		});
		
	}
	
	private void insertRow(){
		GridInsertRow<Apr07_AltApprModel> service = new GridInsertRow<Apr07_AltApprModel>(); 
		Apr07_AltApprModel altApprModel = new Apr07_AltApprModel();
		service.insertRow(grid, altApprModel, new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
			}
		});
		
	}
	
	private void update(){
		for(Store<Apr07_AltApprModel>.Record modified : grid.getStore().getModifiedRecords()) {
			Apr07_AltApprModel chkModel = modified.getModel(); 
			for (Change<Apr07_AltApprModel, ?> changes : modified.getChanges()) {
				// 컬럼별로 변경된 자료를 적용한다. 
				changes.modify(chkModel);
			}
			if(chkModel.getTargetEmpId() == null) {
				new SimpleMessage("원결재자를 지정해야 합니다.");
				return;
			}
			if(chkModel.getAltEmpId() == null) {
				new SimpleMessage("대결자를 지정해야 합니다.");
				return;
			}
			if(chkModel.getStartDate() == null) {
				new SimpleMessage("시작일을 지정해야 합니다.");
				return;
			}
			if(chkModel.getCloseDate() == null) {
				new SimpleMessage("종료일을 지정해야 합니다.");
				return;
			}
			if(chkModel.getReasonCd() == null) {
				new SimpleMessage("대결사유를 입력해야 합니다.");
				return;
			}
		}
		
		
		GridUpdate<Apr07_AltApprModel> service = new GridUpdate<Apr07_AltApprModel>(); 
		service.update(grid.getStore(), "apr.Apr07_AltAppr.update"); 
	}
	
	private void delete(){
		GridDeleteData<Apr07_AltApprModel> service = new GridDeleteData<Apr07_AltApprModel>();
		List<Apr07_AltApprModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "apr.Apr07_AltAppr.delete");
	}
}