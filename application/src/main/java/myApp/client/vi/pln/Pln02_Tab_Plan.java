package myApp.client.vi.pln;

import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.fnd.Fnd00_Lookup_PlanFund;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModelProperties;

public class Pln02_Tab_Plan extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd01_FundCodeModelProperties properties = GWT.create(Fnd01_FundCodeModelProperties.class);
	private Grid<Fnd01_FundCodeModel> grid = this.buildGrid();
//	private MyDateField startDateField = new MyDateField();
//	private MyDateField endDateField = new MyDateField();
	private CheckBox closeYnCheckBox = new CheckBox();
//	private LookupTriggerField lookupPlanFundField = new LookupTriggerField();
	private TextField fundSearchTextField = new TextField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode","%", "전체");

	private Pln02_TabPage_Plan pln02_TabPage_Plan = new Pln02_TabPage_Plan();

	public Pln02_Tab_Plan() {
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
//		Date startDate = CalendarUtil.copyDate(LoginUser.getToday());
//		CalendarUtil.addDaysToDate(startDate, -30);
//        startDateField.setValue(startDate);
        
//		Date endDate = CalendarUtil.copyDate(LoginUser.getToday());
//        endDateField.setValue(endDate);
        
//        searchBarBuilder.getSearchBar().add(new LabelToolItem("등록일자:")); 
//        startDateField.setWidth(100);
//        searchBarBuilder.getSearchBar().add(startDateField);
//        endDateField.setWidth(100);
//		searchBarBuilder.getSearchBar().add(endDateField);
//		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));

		FieldLabel fundTypeField = new FieldLabel(fundTypeComboBox, "펀드유형 ");
		fundTypeComboBox.setValue("전체");
		fundTypeField.setLabelWidth(56);
		fundTypeField.setWidth(200);
		searchBarBuilder.getSearchBar().add(fundTypeField);
		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));

		searchBarBuilder.getSearchBar().add(new LabelToolItem());
//		lookupPlanFundField.addTriggerClickHandler(new TriggerClickHandler(){
//			@Override
//			public void onTriggerClick(TriggerClickEvent event) {
//				openLookupFundCode();
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

		closeYnCheckBox.setBoxLabel("완료건포함");
		closeYnCheckBox.setValue(false);
		searchBarBuilder.addCheckBox(closeYnCheckBox, 90, 0);

		searchBarBuilder.addRetrieveButton(); 
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData.setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);

		this.setCenterWidget(this.grid, centerLayoutData);

		BorderLayoutData southLayoutData = new BorderLayoutData(0.6);	//	전체화면의 60%
		southLayoutData.setMargins(new Margins(2, 0, 0, 0)); 
		southLayoutData.setMaxSize(900);
		southLayoutData.setSplit(true);
		this.setSouthWidget(pln02_TabPage_Plan, southLayoutData);
		
		grid.addRowClickHandler(new RowClickHandler() {
			@Override
			public void onRowClick(RowClickEvent event) {
				Fnd01_FundCodeModel data = grid.getSelectionModel().getSelectedItem(); 
				if(data != null) {
					pln02_TabPage_Plan.retrieve(data.getFundCodeId());
				}
			}
		});

		retrieve();
	}
	
	public Grid<Fnd01_FundCodeModel> buildGrid(){
		
		GridBuilder<Fnd01_FundCodeModel> gridBuilder = new GridBuilder<Fnd01_FundCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	(properties.fundCode()		,  70, "코드"		);
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

		return gridBuilder.getGrid(); 
	}

//	private void openLookupFundCode() {
//		Fnd00_Lookup_PlanFund lookupPlanFund= new Fnd00_Lookup_PlanFund();
//		lookupPlanFund.open(new InterfaceCallbackResult() {
//			@Override
//			public void execute(Object result) {
//				Fnd00_FundModel findFundModel = (Fnd00_FundModel)result;
//				lookupPlanFundField.setValue(findFundModel.getFundName());
//			}
//		});
//	}

	@Override
	public void retrieve(){

		grid.mask("Loading");

		GridRetrieveData<Fnd01_FundCodeModel> service = new GridRetrieveData<Fnd01_FundCodeModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("closeYn"	, closeYnCheckBox.getValue().toString()); 
//		service.addParam("startDate", startDateField.getValue());
//		service.addParam("endDate"	, endDateField.getValue());
		service.addParam("fundName"	, fundSearchTextField.getText());
		service.addParam("fundTypeCode", fundTypeComboBox.getCode());
		
		service.retrieve("fnd.Fnd01_FundCode.selectByFundPlan", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
					pln02_TabPage_Plan.retrieve(grid.getSelectionModel().getSelectedItem().getFundCodeId());
				}			
			}
		});
	}

	@Override
	public void update(){
	}

	@Override
	public void insertRow(){
	}

	@Override
	public void deleteRow(){
	}
}