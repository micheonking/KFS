package myApp.client.vi.apr;

import java.util.Date;
import java.util.List;
//import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
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
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr07_AltApprModel;
import myApp.client.vi.apr.model.Apr07_AltApprModelProperties;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr07_Tab_AltAppr extends BorderLayoutContainer implements InterfaceServiceCall{
//	private	final Logger logger = Logger.getLogger(this.getClass().getName());

	Apr07_AltApprModelProperties properties = GWT.create(Apr07_AltApprModelProperties.class);
	private Grid<Apr07_AltApprModel> grid = this.buildGrid();
	private LabelToolItem label01 = new LabelToolItem("▶ 대결자 등록");

	private TextButton deleteButton = new TextButton("삭제");
	private TextButton insertRowButton = new TextButton("입력");
	private TextButton saveButton = new TextButton("저장");
	
	public Apr07_Tab_AltAppr() {
		makeLayout();
		setInitData();
	}
	
	private void makeLayout() {
		ButtonBar buttonBar01 = new ButtonBar();
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
		
		buttonBar01.add(label01);
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
	
		//담당자선택
		LookupTriggerField lookupEmpInfo = new LookupTriggerField();
		lookupEmpInfo.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupEmpInfo();
			}
		});
		
		gridBuilder.addText(properties.altOrgName(),      200,  "부서명");
		gridBuilder.addText(properties.altTitleName(),    90,  "직책");
		//gridBuilder.addText(properties.altPositionName(), 90,  "직위");
		gridBuilder.addText(properties.altEmpNo(),        90,  "사번");
		gridBuilder.addText(properties.altEmpName(),      90,  "성명",        lookupEmpInfo);
		
		DateField minDate = new DateField();
		minDate.setMinValue(new Date());
		gridBuilder.addDate(properties.startDate(),       100, "시작일",      minDate);
		gridBuilder.addDate(properties.closeDate(),       100, "종료일",      minDate);
		gridBuilder.addText(properties.reasonCd(),        90,  "대결사유",     new TextField());
		gridBuilder.addText(properties.emgrPoint(),       120, "비상연락망", new TextField());
		//gridBuilder.addText(properties.note(),            90,  "비고",        new TextField());
		
		
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
	
	private void openLookupEmpInfo() {
		Emp00_Lookup_EmpInfo lookupEmpInfo = new Emp00_Lookup_EmpInfo(); 
		lookupEmpInfo.open(new Date(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {

				Emp00_InfoModel empInfoModel = (Emp00_InfoModel)result;
				Apr07_AltApprModel	selectModel = grid.getSelectionModel().getSelectedItem();

				grid.getStore().getRecord(selectModel).addChange(properties.altEmpId()		  , empInfoModel.getEmpId());
				grid.getStore().getRecord(selectModel).addChange(properties.altEmpNo()		  , empInfoModel.getEmpNo());
				grid.getStore().getRecord(selectModel).addChange(properties.altEmpName()	  , empInfoModel.getKorName());
				grid.getStore().getRecord(selectModel).addChange(properties.altOrgName()	  , empInfoModel.getOrgName());
				grid.getStore().getRecord(selectModel).addChange(properties.altTitleName()	  , empInfoModel.getTitleName());
				grid.getStore().getRecord(selectModel).addChange(properties.altPositionName() , empInfoModel.getPositionName());
				
			}
		});
	}
	
	private void setInitData() {
		GridRetrieveData<Apr07_AltApprModel> service = new GridRetrieveData<Apr07_AltApprModel>(grid.getStore());
		service.addParam("empId", LoginUser.getUserId()); 
		service.retrieve("apr.Apr07_AltAppr.selectByEmpId");
	}
	
	private void insertRow(){
		GridInsertRow<Apr07_AltApprModel> service = new GridInsertRow<Apr07_AltApprModel>(); 
		Apr07_AltApprModel altApprModel = new Apr07_AltApprModel();
		service.insertRow(grid, altApprModel, new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				// TODO Auto-generated method stub
				Apr07_AltApprModel	selectModel = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(selectModel).addChange(properties.targetEmpId(), LoginUser.getUserId());
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

			if (chkModel.getAltEmpId() == null) {
				new SimpleMessage("대결자를 지정해야 합니다.");
				return;
			}
			if (chkModel.getStartDate() == null) {
				new SimpleMessage("시작일을 지정해야 합니다.");
				return;
			}
			if (chkModel.getCloseDate() == null) {
				new SimpleMessage("종료일을 지정해야 합니다.");
				return;
			}
			if (chkModel.getReasonCd() == null) {
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

	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
		new SimpleMessage(result.getMessage());
	}
	
}