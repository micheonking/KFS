package myApp.client.vi.cmp;

import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.LoginUser;
import myApp.client.vi.cmp.model.Cmp01_AdvertiseModel;
import myApp.client.vi.cmp.model.Cmp01_AdvertiseModelProperties;

public class Cmp01_Tab_Advertise extends BorderLayoutContainer implements InterfaceGridOperate {

    private Cmp01_AdvertiseModelProperties properties = GWT.create(Cmp01_AdvertiseModelProperties.class);
//	private Cmp01_AdvertiseModel cmp01_AdvertiseModel = new Cmp01_AdvertiseModel(); 
	private Grid<Cmp01_AdvertiseModel> grid = this.buildGrid();
	private MyDateField startDateField = new MyDateField();
	private MyDateField endDateField = new MyDateField();
	private TextField advertiseNoteField = new TextField();

//	private Cmp01_TabPage_Advertise cmp01_TabPage_Advertise = new Cmp01_TabPage_Advertise();

	public Cmp01_Tab_Advertise() {
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
		Date startDate = CalendarUtil.copyDate(LoginUser.getToday());	//        CalendarUtil.addDaysToDate(starDate, -7);
        CalendarUtil.setToFirstDayOfMonth(startDate);
        startDateField.setValue(startDate);
        
		Date endDate = CalendarUtil.copyDate(LoginUser.getToday());
		CalendarUtil.setToFirstDayOfMonth(endDate);
		CalendarUtil.addMonthsToDate(endDate, 1);
		CalendarUtil.addDaysToDate(endDate, -1);
        endDateField.setValue(endDate);
        
        searchBarBuilder.getSearchBar().add(new LabelToolItem("등록일자:")); 
        startDateField.setWidth(100);
        searchBarBuilder.getSearchBar().add(startDateField);
        endDateField.setWidth(100);
		searchBarBuilder.getSearchBar().add(endDateField);

		searchBarBuilder.addTextField(advertiseNoteField, "광고제목찾기", 400, 80, true); 

		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
//		searchBarBuilder.addInsertButton(); 
//		searchBarBuilder.addDeleteButton(); 
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData.setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);

		this.setCenterWidget(this.grid, centerLayoutData);

//		BorderLayoutData southLayoutData = new BorderLayoutData(0.6);	//	전체화면의 60%
//		southLayoutData.setMargins(new Margins(2, 0, 0, 0)); 
//		southLayoutData.setMaxSize(900);
//		southLayoutData.setSplit(true);
//		this.setSouthWidget(cmp01_TabPage_Advertise, southLayoutData);
//		
//		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Cmp01_AdvertiseModel>(){
//			@Override
//			public void onSelectionChanged(SelectionChangedEvent<Cmp01_AdvertiseModel> event) {
//				Cmp01_AdvertiseModel data = grid.getSelectionModel().getSelectedItem(); 
//				
//				if(data != null) {
//					cmp01_TabPage_Advertise.retrieve(data.getFundCodeId());
//				}
//			}
//		});

		advertiseNoteField.setValue("");
	}
	
	public Grid<Cmp01_AdvertiseModel> buildGrid(){
		
		GridBuilder<Cmp01_AdvertiseModel> gridBuilder = new GridBuilder<Cmp01_AdvertiseModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

//		gridBuilder.addLong	(properties.advertiseId(),		100,	"광고심사ID"		,	new	LongField());
		gridBuilder.addText	(properties.advertiseNo(),		100,	"심사번호"			);	//,	new	LongField());
		gridBuilder.addDate	(properties.screeningDate(),	100,	"심사일자"			,	new	DateField());
		gridBuilder.addText	(properties.advertiseType(),	200,	"광고종류"			,	new	TextField());
		gridBuilder.addText	(properties.mediaType(),		200,	"매체종류"			,	new	TextField());
		gridBuilder.addText	(properties.advertiseNote(),	400,	"광고내용"			,	new	TextField());
//		gridBuilder.addText	(properties.managerEmpId(),		100,	"담당자"			);	//,	new	TextField());
		gridBuilder.addText	(properties.empName(),			100,	"담당자"			);	//,	new	TextField());
		gridBuilder.addText	(properties.advertiseCount(),	100,	"수량"				,	new	TextField());

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
//
//	}

	@Override
	public void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Cmp01_AdvertiseModel> service = new GridRetrieveData<Cmp01_AdvertiseModel>(grid.getStore());

		service.addParam("startDate", startDateField.getText());
		service.addParam("endDate", endDateField.getText());
		service.addParam("advertiseNote", advertiseNoteField.getText());

		service.retrieve("cmp.Cmp01_Advertise.selectByScreeningDate", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}

	@Override
	public void update(){
		GridUpdate<Cmp01_AdvertiseModel> service = new GridUpdate<Cmp01_AdvertiseModel>(); 
		service.update(grid.getStore(), "cmp.Cmp01_Advertise.update"); 
	}

	@Override
	public void insertRow(){
		GridInsertRow<Cmp01_AdvertiseModel> service = new GridInsertRow<Cmp01_AdvertiseModel>(); 
		Cmp01_AdvertiseModel fundModel = new Cmp01_AdvertiseModel();
		service.insertRow(grid, fundModel);
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Cmp01_AdvertiseModel> service = new GridDeleteData<Cmp01_AdvertiseModel>();
		List<Cmp01_AdvertiseModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "cmp.Cmp01_Advertise.delete");
	}
}