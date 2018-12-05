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
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.ClientDateUtil;
import myApp.client.vi.apr.model.Apr00_StampSeqModel;
import myApp.client.vi.apr.model.Apr00_StampSeqModelProperties;

public class Apr00_Tab_StampSeq extends BorderLayoutContainer {

	private Grid<Apr00_StampSeqModel> grid = this.buildGrid();
	private MyDateField startDate = new MyDateField();
	private MyDateField endDate = new MyDateField();
	
	public Apr00_Tab_StampSeq() {
		
		ButtonBar buttonBar = new ButtonBar();
		
		TextButton retrieveButton = new TextButton("조회"); 
		
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});

		Date fromDate = ClientDateUtil.toDate(new Date());
		CalendarUtil.addMonthsToDate(fromDate, -1);
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
		
		buttonBar.add(retrieveButton);

		// set SearchBar 
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49
		this.setNorthWidget(buttonBar, northLayoutData);

		// set Grid 
		this.setCenterWidget(this.grid);

//		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Apr01_ApprModel>(){
//			@Override
//			public void onSelectionChanged(SelectionChangedEvent<Apr01_ApprModel> event) {
//				if(event.getSelection().size() > 0){
//				}
//			} 
//		}); 
//		
//		setInitData();
		retrieve();
	}
	
	public Grid<Apr00_StampSeqModel> buildGrid(){

		Apr00_StampSeqModelProperties properties = GWT.create(Apr00_StampSeqModelProperties.class);
		GridBuilder<Apr00_StampSeqModel> gridBuilder = new GridBuilder<Apr00_StampSeqModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.stampSeq(), 100, "대장번호");
		gridBuilder.addDate(properties.apprDate(), 100, "신청일자");
		gridBuilder.addText(properties.regEmpName(), 60, "신청자");
		gridBuilder.addText(properties.apprEmpName(), 60, "확인자");
		gridBuilder.addText(properties.title(), 300, "내용");
		gridBuilder.addText(properties.stampName(), 140, "사용인감");
		gridBuilder.addText(properties.receiveName(), 200, "제출처");
		//gridBuilder.addText(properties.note(), 300, "비고");
		return gridBuilder.getGrid();
	}
	
	private void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Apr00_StampSeqModel> service = new GridRetrieveData<Apr00_StampSeqModel>(this.grid.getStore());
		service.addParam("startDate", this.startDate.getValue());
		service.addParam("endDate", this.endDate.getValue());
		service.retrieve("apr.Apr00_StampSeq.selectByApprDate");
		grid.unmask();
	}
}