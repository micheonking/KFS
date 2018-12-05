package myApp.client.vi.fnd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
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

public class Fnd08_Tab_OrgAuth extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd01_FundCodeModelProperties properties = GWT.create(Fnd01_FundCodeModelProperties.class);
	private Grid<Fnd01_FundCodeModel> grid = this.buildGrid();
	private CheckBox closeYnCheckBox = new CheckBox();
	private LookupTriggerField lookupPlanFundField = new LookupTriggerField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode","%", "전체");

	private Fnd08_Page_OrgAuth orgAuthTree = new Fnd08_Page_OrgAuth();
	
	public Fnd08_Tab_OrgAuth() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
		FieldLabel fundTypeField = new FieldLabel(fundTypeComboBox, "펀드유형");
		fundTypeComboBox.setValue("전체");
		fundTypeField.setLabelWidth(56);
		fundTypeField.setWidth(200);
		searchBarBuilder.getSearchBar().add(fundTypeField);
//		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));

		lookupPlanFundField.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupFundCode();
			}
		}); 
		
		FieldLabel planFundNameLabel = new FieldLabel(lookupPlanFundField, "펀드찾기");
		planFundNameLabel.setLabelWidth(60);
		planFundNameLabel.setWidth(300);
		searchBarBuilder.getSearchBar().add(planFundNameLabel); 
		
		closeYnCheckBox.setBoxLabel("해지펀드포함");
		closeYnCheckBox.setValue(false);
		searchBarBuilder.addCheckBox(closeYnCheckBox, 100, 0);
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton(); 

		
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

//		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
//		vlc.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 50)); 
//		vlc.add(this.grid, new VerticalLayoutData(1, 1));
		
		BorderLayoutData westLayuoutData = new BorderLayoutData(0.5);
		westLayuoutData.setMargins(new Margins(0, 4, 0, 0));
		westLayuoutData.setSplit(true);
		westLayuoutData.setMaxSize(1000);

		this.setWestWidget(this.grid, westLayuoutData);

		this.setCenterWidget(orgAuthTree);
		
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Fnd01_FundCodeModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Fnd01_FundCodeModel> event) {
				if(event.getSelection().size() > 0){
					retrieveEditPage(); 
				}
			}
		});

		lookupPlanFundField.setEmptyText("전체");
	}
	
	public Grid<Fnd01_FundCodeModel> buildGrid(){
		
		GridBuilder<Fnd01_FundCodeModel> gridBuilder = new GridBuilder<Fnd01_FundCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText	(properties.fundCode()		,  70, "코드"			);
		gridBuilder.addText	(properties.fundName()		, 250, "펀드명"		);
		gridBuilder.addText	(properties.fundTypeName()	, 150, "펀드유형"		);
		gridBuilder.addDate	(properties.startDate()		,  90, "생성일"		);
		gridBuilder.addDate	(properties.endDate()		,  90, "예정일"		);
		gridBuilder.addText	(properties.closeName()		,  60, "해지여부"		);
		gridBuilder.addText	(properties.sintakGbName()	,  80, "신탁구분"		);
		gridBuilder.addText	(properties.publicName()	,  40, "공모"			);
		gridBuilder.addText	(properties.orgCodeName()	, 150, "운용부서"		);
		gridBuilder.addText	(properties.emp1Name()		,  90, "운용역1"		);
		gridBuilder.addText	(properties.emp2Name()		,  90, "운용역2"		);

		return gridBuilder.getGrid(); 
	}

	private void retrieveEditPage(){

		if(this.grid.getSelectionModel().getSelectedItem() != null) {
			Fnd01_FundCodeModel fundModel = this.grid.getSelectionModel().getSelectedItem();
			orgAuthTree.retrieve(fundModel.getFundCodeId());
		}
		else {
			orgAuthTree.retrieve(null);
		}
	}
	
	private void openLookupFundCode() {
		Fnd00_Lookup_FundCode lookupFundCode = new Fnd00_Lookup_FundCode();
		lookupFundCode.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Fnd00_FundModel findFundModel = (Fnd00_FundModel)result;
				lookupPlanFundField.setValue(findFundModel.getFundName());
			}
		});
	}

	@Override
	public void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Fnd01_FundCodeModel> service = new GridRetrieveData<Fnd01_FundCodeModel>(grid.getStore());

		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("closeYn", closeYnCheckBox.getValue().toString()); 
		service.addParam("startDate", LoginUser.getToday());
		service.addParam("fundName", lookupPlanFundField.getText());
		service.addParam("fundTypeCode", fundTypeComboBox.getCode());

		service.retrieve("fnd.Fnd01_FundCode.selectByFundName", new InterfaceCallback() {
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
	}

	@Override
	public void insertRow(){
		GridInsertRow<Fnd01_FundCodeModel> service = new GridInsertRow<Fnd01_FundCodeModel>(); 
		Fnd01_FundCodeModel fundModel = new Fnd01_FundCodeModel();
		fundModel.setCompanyId(LoginUser.getCompanyId());
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