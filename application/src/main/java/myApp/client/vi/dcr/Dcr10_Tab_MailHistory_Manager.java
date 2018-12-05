package myApp.client.vi.dcr;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr10_MailHistoryModel;
import myApp.client.vi.dcr.model.Dcr10_MailHistoryModelProperties;

public class Dcr10_Tab_MailHistory_Manager extends BorderLayoutContainer implements InterfaceGridOperate {

    private Dcr10_MailHistoryModelProperties properties = GWT.create(Dcr10_MailHistoryModelProperties.class);
	private Grid<Dcr10_MailHistoryModel> grid = this.buildGrid();
	private MyDateField startDateField = new MyDateField();
	private MyDateField endDateField = new MyDateField();
	private TextField titleTextField = new TextField();

	public Dcr10_Tab_MailHistory_Manager() {
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

//		searchTextLabel.setLabelWidth(76);
//		searchTextLabel.setWidth(400);
		searchBarBuilder.getSearchBar().add(titleTextField);
		titleTextField.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13) {
					retrieve();
				}
			}
		});

		searchBarBuilder.addRetrieveButton(); 
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData.setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);

		this.setCenterWidget(this.grid, centerLayoutData);

		titleTextField.setValue("");
		retrieve();
	}
	
	public Grid<Dcr10_MailHistoryModel> buildGrid(){
		
		GridBuilder<Dcr10_MailHistoryModel> gridBuilder = new GridBuilder<Dcr10_MailHistoryModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

//		gridBuilder.addLong	(properties.mailHistoryId(),	100,	"메일History ID"	,	new	LongField());
//		gridBuilder.addLong	(properties.docId(),			100,	"문서ID"			,	new	LongField());
//		gridBuilder.addLong	(properties.aprId(),			100,	"결재ID"			,	new	LongField());
		gridBuilder.addText	(properties.senderEmail(),		150,	"보내는사람"		);
		gridBuilder.addText	(properties.sendTimeString(),	130,	"보낸시간"			);
		gridBuilder.addText	(properties.receiverEmail(),	200,	"받는사람"			);
		gridBuilder.addText	(properties.referencerEmail(),	200,	"참조"			);
//		gridBuilder.addText	(properties.attachedFile(),		100,	"첨부파일"			,	new	TextField());
//		gridBuilder.addText	(properties.pdfFile(),			100,	"PDF파일"			,	new	TextField());
//		gridBuilder.addText	(properties.sealFile(),			100,	"인감파일"			,	new	TextField());
		gridBuilder.addText	(properties.titleText(),		250,	"제목"			);
		gridBuilder.addText	(properties.bodyText(),			250,	"내용"			);

		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Dcr10_MailHistoryModel> service = new GridRetrieveData<Dcr10_MailHistoryModel>(grid.getStore());

		service.addParam("startDate", startDateField.getText());
		service.addParam("endDate", endDateField.getText());
		service.addParam("titleText", titleTextField.getText());

		service.retrieve("dcr.Dcr10_MailHistory.selectByTitleText", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
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