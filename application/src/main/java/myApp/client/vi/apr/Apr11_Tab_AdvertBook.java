package myApp.client.vi.apr;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.ClientDateUtil;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr11_AdvertBookModel;
import myApp.client.vi.apr.model.Apr11_AdvertBookModelProperties;

public class Apr11_Tab_AdvertBook extends BorderLayoutContainer {

	private Grid<Apr11_AdvertBookModel> grid = this.buildGrid();
	private MyDateField startDate = new MyDateField();
	private MyDateField endDate = new MyDateField();
	
	public Apr11_Tab_AdvertBook() {
		
		ButtonBar buttonBar = new ButtonBar();

		Date fromDate = ClientDateUtil.toDate(new Date());
		CalendarUtil.addMonthsToDate(fromDate, -12);
		CalendarUtil.addDaysToDate(fromDate, 1);
		startDate.setValue(fromDate);
		
		FieldLabel startDateLabel = new FieldLabel(startDate, "조회구간");
		startDateLabel.setWidth(170);
		startDateLabel.setLabelWidth(60);
		buttonBar.add(startDateLabel);

		endDate.setValue(new Date());
		FieldLabel endDateLabel = new FieldLabel(endDate, "~  ");
		buttonBar.add(endDateLabel);
		endDateLabel.setWidth(120);
		endDateLabel.setLabelWidth(10);
		endDateLabel.setLabelSeparator("");
		
		TextButton retrieveButton = new TextButton("조회"); 
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		buttonBar.add(retrieveButton);

		TextButton updateButton = new TextButton("저장"); 
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		});
		buttonBar.add(updateButton);
		
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49
		this.setNorthWidget(buttonBar, northLayoutData);
		this.setCenterWidget(this.grid);
		retrieve();
	}
	
	public Grid<Apr11_AdvertBookModel> buildGrid(){

		Apr11_AdvertBookModelProperties properties = GWT.create(Apr11_AdvertBookModelProperties.class);
		GridBuilder<Apr11_AdvertBookModel> gridBuilder = new GridBuilder<Apr11_AdvertBookModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.advertSeq(), 100, "심사번호");
		gridBuilder.addDate(properties.apprDate(), 100, "심사일자");
		gridBuilder.addText(properties.advertTypeName(), 100, "광고종류", new TextField());
		gridBuilder.addText(properties.mediaName(), 100, "매체종류", new TextField());
		gridBuilder.addText(properties.title(), 400, "광고내용");
		gridBuilder.addText(properties.empName(), 80, "담당자");
		gridBuilder.addLong(properties.count(), 60, "수량", new LongField());
		gridBuilder.addText(properties.note(), 300, "비고", new TextField());
		return gridBuilder.getGrid();
	}
	
	private void retrieve(){
		
		grid.mask("Loading");
		if(this.startDate.getValue() == null) {
			new SimpleMessage("조회조건의 시작일은 필수값입니다.");
			return ; 
		}
		
		if(this.endDate.getValue() == null) {
			new SimpleMessage("조회조건의 종료일은 필수값입니다.");
			return ; 
		}
		
		GridRetrieveData<Apr11_AdvertBookModel> service = new GridRetrieveData<Apr11_AdvertBookModel>(this.grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("startDate", this.startDate.getValue());
		service.addParam("endDate", this.endDate.getValue());
		service.retrieve("apr.Apr11_AdvertBook.selectByApprDate"); 
		grid.unmask();
	}
	
	private void update() {
		GridUpdate<Apr11_AdvertBookModel> service = new GridUpdate<Apr11_AdvertBookModel>();
		service.update(grid.getStore(), "apr.Apr11_AdvertBook.update", new InterfaceCallback() {
			@Override
			public void execute() {
				//grid.getView().refresh(true);
			}
		});
	}
	
}