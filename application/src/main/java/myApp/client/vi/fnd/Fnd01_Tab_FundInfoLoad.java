package myApp.client.vi.fnd;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
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
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModelProperties;

public class Fnd01_Tab_FundInfoLoad extends BorderLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {

    private Fnd01_FundCodeModelProperties properties = GWT.create(Fnd01_FundCodeModelProperties.class);
	private Grid<Fnd01_FundCodeModel> grid = this.buildGrid();
	private MyDateField workDateField = new MyDateField();
	private CheckBox closeYnCheckBox = new CheckBox();
//	private LookupTriggerField lookupPlanFundField = new LookupTriggerField();
	private TextField fundSearchTextField = new TextField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode","%", "전체");
	
	private String actionName;
//	private TextField lastWorkDate = new TextField();
	private LabelToolItem lastWorkDate = new LabelToolItem();
	
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();

	private Fnd01_Edit_FundCode editFundCodeForm = new Fnd01_Edit_FundCode(grid);
	
	public Fnd01_Tab_FundInfoLoad() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.getSearchBar().add(new LabelToolItem("작업일자 : ")); 
		workDateField.setValue(LoginUser.getToday());    
		workDateField.setWidth(100);
		searchBarBuilder.getSearchBar().add(workDateField);

		setLastWorkDate();
//		LabelToolItem lastWorkDateField = new LabelToolItem();
//		lastWorkDateField.setLabel("(최근작업일 : " + lastWorkDate + ")");
		lastWorkDate.setWidth(165);
		lastWorkDate.setHeight(20);
		searchBarBuilder.getSearchBar().add(lastWorkDate);
		
		FieldLabel fundTypeField = new FieldLabel(fundTypeComboBox, "펀드유형 ");
		fundTypeComboBox.setValue("전체");
		fundTypeField.setLabelWidth(56);
		fundTypeField.setWidth(200);
		searchBarBuilder.getSearchBar().add(fundTypeField);
//		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));

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
		
		FieldLabel planFundNameLabel = new FieldLabel(fundSearchTextField, "펀드 ");
		planFundNameLabel.setLabelWidth(56);
		planFundNameLabel.setWidth(400);
		searchBarBuilder.getSearchBar().add(planFundNameLabel); 
		
		closeYnCheckBox.setBoxLabel("해지펀드포함");
		closeYnCheckBox.setValue(false);
		searchBarBuilder.addCheckBox(closeYnCheckBox, 100, 0);
		
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		searchBarBuilder.addInsertButton("파일UpLoad", 100);
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,1));
		
		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				grid.mask("Loading");
				String actionUrl = "FundUpload?companyId=" + LoginUser.getCompanyId() + "&workDate=" + workDateField.getText();
				fileUpdownForm.submit(actionUrl, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						fileUpdownForm.setInit();
						grid.unmask();
						new SimpleMessage("확인", result.toString());
						setLastWorkDate();
						retrieve();
					}
				});
			}
		});

		BorderLayoutData centerLayoutData = new BorderLayoutData(1);
		centerLayoutData.setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);

		this.setCenterWidget(vlc, centerLayoutData);

		BorderLayoutData southLayoutData = new BorderLayoutData(320);	//	전체화면의 60%
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
	}
	
	private void setLastWorkDate() {
		actionName = "getLastUploadDate";
		ServiceRequest request = new ServiceRequest("fnd.Fnd01_FundCode.getLastUploadDate");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public Grid<Fnd01_FundCodeModel> buildGrid(){
		
		GridBuilder<Fnd01_FundCodeModel> gridBuilder = new GridBuilder<Fnd01_FundCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText	(properties.fundCode()		,  70, "코드"			);
		gridBuilder.addText	(properties.fundName()		, 350, "펀드명"		);
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

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fundModel", fundModel); 
			editFundCodeForm.retrieve(param);
		}
		else {
			editFundCodeForm.init(); 
		}
	}
	
//	private void openLookupFundCode() {
//		Fnd00_Lookup_FundCode lookupFundCode = new Fnd00_Lookup_FundCode();
//		lookupFundCode.open(workDateField.getText(), new InterfaceCallbackResult() {
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
		service.addParam("closeYn", closeYnCheckBox.getValue().toString()); 
		service.addParam("workDate", workDateField.getText());
		service.addParam("fundName", fundSearchTextField.getText());
		service.addParam("fundTypeCode", fundTypeComboBox.getCode());

		service.retrieve("fnd.Fnd01_FundCode.selectByWorkDate", new InterfaceCallback() {
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
//		editFundCodeForm.updateRow();
	}

	@Override
	public void insertRow(){
		fileUpdownForm.click();
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

	@Override
	public void getServiceResult(ServiceResult result) {
		if ("getLastUploadDate".equals(actionName)) {
			lastWorkDate.setLabel("(최근작업일 : " + result.getMessage() + ")"); 
		}
	}
}