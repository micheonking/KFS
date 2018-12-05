package myApp.client.vi.fnd;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModelProperties;

public class Fnd01_Tab_FundPlan extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd01_FundCodeModelProperties properties = GWT.create(Fnd01_FundCodeModelProperties.class);

//    private MyDateField 		startDateField 		= new MyDateField();
//    private MyDateField 		endDateField 		= new MyDateField();
    private ComboBoxField 		fundTypeComboBox 	= new ComboBoxField("FundTypeCode","%", "전체");
//    private LookupTriggerField 	lookupPlanFundField	= new LookupTriggerField();
    private TextField 			fundSearchTextField = new TextField();
	private CheckBox 			closeYnCheckBox 	= new CheckBox();

	private Grid<Fnd01_FundCodeModel> grid = this.buildGrid();
	private Fnd01_Edit_FundPlan editFundCodeForm = new Fnd01_Edit_FundPlan(grid);
	
	public Fnd01_Tab_FundPlan() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
//		Date startDate = CalendarUtil.copyDate(LoginUser.getToday());
//		CalendarUtil.addDaysToDate(startDate, -30);
//		startDateField.setValue(startDate);    
//		startDateField.setWidth(100);

//		Date endDate = CalendarUtil.copyDate(LoginUser.getToday());
//        endDateField.setValue(endDate);
//        endDateField.setWidth(100);

//		searchBarBuilder.getSearchBar().add(new LabelToolItem("등록일자:")); 
//		searchBarBuilder.getSearchBar().add(startDateField);
//		searchBarBuilder.getSearchBar().add(endDateField);
//		searchBarBuilder.getSearchBar().add(new LabelToolItem());

		FieldLabel fundTypeField = new FieldLabel(fundTypeComboBox, "펀드유형 ");
		fundTypeComboBox.setValue("전체");
		fundTypeField.setLabelWidth(56);
		fundTypeField.setWidth(200);
		searchBarBuilder.getSearchBar().add(fundTypeField);
		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));

//		lookupPlanFundField.addTriggerClickHandler(new TriggerClickHandler(){
//			@Override
//			public void onTriggerClick(TriggerClickEvent event) {
//				Fnd00_Lookup_PlanFund lookupPlanFund = new Fnd00_Lookup_PlanFund();
//				lookupPlanFund.open(new InterfaceCallbackResult() {
//					@Override
//					public void execute(Object result) {
//						Fnd00_FundModel findFundModel = (Fnd00_FundModel)result;
//						lookupPlanFundField.setValue(findFundModel.getFundName());
//					}
//				});
//			}
//		});
		fundSearchTextField.setEmptyText("전체");
		fundSearchTextField.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) { // enter key event 
					retrieve();
				}
			}
		});

		FieldLabel planFundNameLabel = new FieldLabel(fundSearchTextField, "기획펀드 ");
		planFundNameLabel.setLabelWidth(76);
		planFundNameLabel.setWidth(400);
		searchBarBuilder.getSearchBar().add(planFundNameLabel); 
		
		closeYnCheckBox.setBoxLabel("종료펀드포함");
		closeYnCheckBox.setValue(false);
		searchBarBuilder.addCheckBox(closeYnCheckBox, 100, 0);
		
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addInsertButton(); 
		searchBarBuilder.addDeleteButton(); 
		
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(-1);
		centerLayoutData.setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);
		this.setCenterWidget(this.grid, centerLayoutData);

		BorderLayoutData southLayoutData = new BorderLayoutData(410);
		southLayoutData.setMargins(new Margins(2, 0, 0, 0)); 
		southLayoutData.setMaxSize(900);
		southLayoutData.setSplit(true);
		this.setSouthWidget(editFundCodeForm, southLayoutData);
		
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Fnd01_FundCodeModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Fnd01_FundCodeModel> event) {
				if(event.getSelection().size() > 0){
					retrieveEditPage(); 
				}
			}
		});

		retrieve();
	}
	
	public Grid<Fnd01_FundCodeModel> buildGrid(){
		
		GridBuilder<Fnd01_FundCodeModel> gridBuilder = new GridBuilder<Fnd01_FundCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	(properties.fundCode()		,  70, "펀드코드"	);
		gridBuilder.addText	(properties.fundName()		, 250, "펀드명"	);
		gridBuilder.addText	(properties.fundTypeName()	, 150, "펀드유형"	);
		gridBuilder.addDate	(properties.startDate()		,  90, "설정일"	);
		gridBuilder.addDate	(properties.endDate()		,  90, "해지일"	);
		gridBuilder.addText	(properties.closeName()		,  80, "종료구분"	);
		gridBuilder.addText	(properties.sintakGbName()	,  80, "신탁구분"	);
		gridBuilder.addText	(properties.publicName()	,  50, "공모"		);
		gridBuilder.addText	(properties.orgCodeName()	, 150, "운용부서"	);
		gridBuilder.addText	(properties.emp1Name()		,  90, "운용역1"	);
		gridBuilder.addText	(properties.emp2Name()		,  90, "운용역2"	);
		gridBuilder.addText (properties.realFundCode()	,  70, "확정펀드"	);
		gridBuilder.addText (properties.realFundName()	, 250, "확정펀드명");

		return gridBuilder.getGrid(); 
	}

	private void retrieveEditPage() {
		Fnd01_FundCodeModel fundModel = grid.getSelectionModel().getSelectedItem();
		if(fundModel != null) {
			editFundCodeForm.retrieve(fundModel);
		} else {
			editFundCodeForm.retrieve(new Fnd01_FundCodeModel());
		}
	}

	@Override
	public void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Fnd01_FundCodeModel> service = new GridRetrieveData<Fnd01_FundCodeModel>(grid.getStore());

		service.addParam("companyId"	, LoginUser.getCompanyId());
		service.addParam("closeYn"		, closeYnCheckBox.getValue().toString()); 
//		service.addParam("startDate"	, startDateField.getValue());
//		service.addParam("endDate"		, endDateField.getValue());
		service.addParam("fundName"		, fundSearchTextField.getText());
		service.addParam("fundTypeCode"	, fundTypeComboBox.getCode());

		service.retrieve("fnd.Fnd01_FundCode.selectByFundPlan", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
				}			
			}
		});
	}

	@Override
	public void update(){
		editFundCodeForm.updateRow();
	}

	@Override
	public void insertRow(){
		GridInsertRow<Fnd01_FundCodeModel> service = new GridInsertRow<Fnd01_FundCodeModel>(); 
		Fnd01_FundCodeModel fundModel = new Fnd01_FundCodeModel();
		fundModel.setCompanyId(LoginUser.getCompanyId());
		fundModel.setStartDate(LoginUser.getToday());
		fundModel.getFundInfoModel().setWorkDate(LoginUser.getToday());
		fundModel.setCloseName("기획펀드");
		fundModel.setCloseYn("false");
		fundModel.setPlanYn("true");
		service.insertRow(grid, fundModel);
	}

	@Override
	public void deleteRow(){
		ConfirmMessageBox messageBox = new ConfirmMessageBox("삭제확인", "선택한 내용을 삭제하시겠습니까?");
		messageBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				if(event.getHideButton().toString() == "YES") {
					deleteSelectedRow();
				}
			}
		});
		messageBox.setWidth(300);
		messageBox.show();
	}

	private void deleteSelectedRow() {
		GridDeleteData<Fnd01_FundCodeModel> service = new GridDeleteData<Fnd01_FundCodeModel>();
		List<Fnd01_FundCodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd01_FundCode.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
				}			
			}
		});
	}
}