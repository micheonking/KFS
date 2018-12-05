package myApp.client.vi.sys;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys12_CalendarModel;
import myApp.client.vi.sys.model.Sys12_CalendarModelProperties;

public class Sys12_Tab_Calendar extends VerticalLayoutContainer implements InterfaceGridOperate {

	private TextField 		yearField  	  = new TextField();
	private	ComboBoxField	monthComboBox = new ComboBoxField("MonthsCode","%", "전체"); 
	
	private Grid<Sys12_CalendarModel> grid = this.buildGrid();

	public Sys12_Tab_Calendar() {
		
		SearchBarBuilder searchbarBuilder = new SearchBarBuilder(this);

		yearField.setValue(LoginUser.getYear());
		yearField.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		searchbarBuilder.addTextField(yearField, "년도 ", 100, 35);
		
		LabelToolItem blank = new LabelToolItem();
		blank.setWidth(10);
		searchbarBuilder.getSearchBar().add(blank);

		monthComboBox.setValue("전체");
		searchbarBuilder.addComboBox(monthComboBox, "월 ", 100, 30);

		LabelToolItem blank2 = new LabelToolItem();
		blank2.setWidth(10);
		searchbarBuilder.getSearchBar().add(blank2);

		searchbarBuilder.addRetrieveButton();
		searchbarBuilder.addUpdateButton();

		this.add(searchbarBuilder.getSearchBar(), new VerticalLayoutData(1, 50, new Margins(0,0,0,0)));
		this.add(grid, new VerticalLayoutData(1, 1));

		retrieve();
	}

	private Grid<Sys12_CalendarModel> buildGrid() {

		Sys12_CalendarModelProperties properties = GWT.create(Sys12_CalendarModelProperties.class);
		GridBuilder<Sys12_CalendarModel> gridBuilder = new GridBuilder<Sys12_CalendarModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addDate	  (properties.day()			 , 100, "일자");
		gridBuilder.addText	  (properties.weekday()		 , 100, "요일");
		gridBuilder.addBoolean(properties.workingYnFlag(),  70, "영업일");
		gridBuilder.addText	  (properties.offReason()	 , 200, "휴일사유", new TextField());
		gridBuilder.addText	  (properties.note()		 , 300, "비고"   , new TextField());

		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		
		grid.mask("Loading");
		
		GridRetrieveData<Sys12_CalendarModel> service = new GridRetrieveData<Sys12_CalendarModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("year"	, yearField.getValue());
		service.addParam("month", monthComboBox.getCode());

		service.retrieve("sys.Sys12_Calendar.selectByYear", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}

	@Override
	public void update() {
		GridUpdate<Sys12_CalendarModel> service = new GridUpdate<Sys12_CalendarModel>();
		service.update(grid.getStore(), "sys.Sys12_Calendar.update");
	}

	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
	}

}
