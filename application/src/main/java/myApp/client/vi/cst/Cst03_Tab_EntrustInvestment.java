package myApp.client.vi.cst;

import java.util.Date;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.utils.ClientDateUtil;
import myApp.client.vi.LoginUser;

public class Cst03_Tab_EntrustInvestment extends BorderLayoutContainer implements InterfaceGridOperate {
	
//	private MyDateField startDate = new MyDateField();
//	private ComboBox1YearMonth yearMonthComboBox = new ComboBox1YearMonth("");
	private ComboBox1FundCode fundCodeComboBox = new ComboBox1FundCode("");
	private TextField yearTextField = new TextField();
	private Date toDate = new Date();

	private Radio radio1 = new Radio();
	private CheckBox radio2 = new Radio();
	private CheckBox radio3 = new Radio();
	private CheckBox radio4 = new Radio();
	
	VerticalLayoutContainer rdLayoutContainer = new VerticalLayoutContainer();

	public interface RDTemplate extends XTemplates {
	    @XTemplate("<iframe id='reportDesinger' border=0 src='{pageName}' width='100%' height='100%' /> ")
	    SafeHtml getTemplate(String pageName);
	}

	public Cst03_Tab_EntrustInvestment() {
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		FieldLabel fundCodeField = new FieldLabel(fundCodeComboBox, "계좌 ");	//	펀드코드 선택
		fundCodeField.setLabelWidth(60);
		fundCodeField.setWidth(400);
		searchBarBuilder.getSearchBar().add(fundCodeField);
		
		FieldLabel yearFieldLabel = new FieldLabel(yearTextField, "년도 ");	//	년월
		yearTextField.setText(DateTimeFormat.getFormat("yyyy").format(LoginUser.getToday()));
		yearFieldLabel.setLabelWidth(60);
		yearFieldLabel.setWidth(120);
		searchBarBuilder.getSearchBar().add(yearFieldLabel);

//		searchBarBuilder.getSearchBar().add(new LabelToolItem("기준일 :"));
//		Date date = LoginUser.getToday();
//		startDate.setValue(date);
//		startDate.setWidth(110);
//		searchBarBuilder.getSearchBar().add(startDate);

		searchBarBuilder.getSearchBar().add(new Label("　"));

		radio1.setBoxLabel("1분기");
		radio2.setBoxLabel("2분기");
		radio3.setBoxLabel("3분기");
		radio4.setBoxLabel("4분기");

		String month = DateTimeFormat.getFormat("MM").format(LoginUser.getToday());
		switch (month) {
		case "01":  radio4.setValue(true);radio1.setValue(false);radio2.setValue(false);radio3.setValue(false);
				break;
		case "02":  radio4.setValue(true);radio1.setValue(false);radio2.setValue(false);radio3.setValue(false);
		        break;
		case "03":  radio4.setValue(true);radio1.setValue(false);radio2.setValue(false);radio3.setValue(false);
		        break;
		case "04":  radio1.setValue(true);radio2.setValue(false);radio3.setValue(false);radio4.setValue(false);
				break;
		case "05":  radio1.setValue(true);radio2.setValue(false);radio3.setValue(false);radio4.setValue(false);
				break;
		case "06":  radio1.setValue(true);radio2.setValue(false);radio3.setValue(false);radio4.setValue(false);
				break;
		case "07":  radio2.setValue(true);radio1.setValue(false);radio3.setValue(false);radio4.setValue(false);
				break;
		case "08":  radio2.setValue(true);radio1.setValue(false);radio3.setValue(false);radio4.setValue(false);
				break;
		case "09":  radio2.setValue(true);radio1.setValue(false);radio3.setValue(false);radio4.setValue(false);
				break;
		case "10":  radio3.setValue(true);radio1.setValue(false);radio2.setValue(false);radio4.setValue(false);
				break;
		case "11":  radio3.setValue(true);radio1.setValue(false);radio2.setValue(false);radio4.setValue(false);
				break;
		case "12":  radio3.setValue(true);radio1.setValue(false);radio2.setValue(false);radio4.setValue(false);
				break;
		}

		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.add(radio1);
		toggleGroup.add(radio2);
		toggleGroup.add(radio3);
		toggleGroup.add(radio4);

		HorizontalPanel hToggleGroup = new HorizontalPanel();
		hToggleGroup.add(radio1);
		hToggleGroup.add(radio2);
		hToggleGroup.add(radio3);
		hToggleGroup.add(radio4);

		searchBarBuilder.getSearchBar().add(hToggleGroup);

		searchBarBuilder.addRetrieveButton();

		this.setBorders(false);

		BorderLayoutData northLayoutData = new BorderLayoutData(50);
		northLayoutData.setMargins(new Margins(0, 8, 0, 0));
//		northLayoutData.setSplit(true);
		northLayoutData.setMaxSize(1000);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData();
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);
		centerLayoutData.setMargins(new Margins(8, 10, 10, 0));
		this.setCenterWidget(rdLayoutContainer,centerLayoutData);

		retrieve();
//		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50));
//
//		this.setCenterWidget(rdLayoutContainer);
	}

	private void setReportDesigner() {
		
		String toDateString = "";
		if (radio1.getValue() == true) {
			toDateString = yearTextField.getText() + "-03-01";
		}
		if (radio2.getValue() == true) {
			toDateString = yearTextField.getText() + "-06-01";
		}
		if (radio3.getValue() == true) {
			toDateString = yearTextField.getText() + "-09-01";
		}
		if (radio4.getValue() == true) {
			toDateString = yearTextField.getText() + "-12-01";
		}

		toDate = ClientDateUtil.toDate(toDateString);
		CalendarUtil.addMonthsToDate(toDate, 1);
		CalendarUtil.addDaysToDate(toDate, -1);
	
		String pageName = "http://172.20.200.252:8283/ReportingServer/html5/RDhtml/";
		String ymd = DateTimeFormat.getFormat("yyyy-MM-dd").format(toDate);
		String fundCode = fundCodeComboBox.getCurrentFundCode(); 

		RDTemplate rdTemplate = GWT.create(RDTemplate.class);

		if(fundCode != "null") {
			pageName = pageName + "web_sics050.html?ymd=" + ymd + "&fund_cd=" + fundCode;
		}
		else {
			pageName = pageName + "sample.html";
		}
		
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(rdTemplate.getTemplate(pageName));
		rdLayoutContainer.clear();
		rdLayoutContainer.add(htmlLayoutContainer, new VerticalLayoutData(1, 1));
		this.setCenterWidget(rdLayoutContainer);
	}

	@Override
	public void retrieve() {
		setReportDesigner();
	}
	@Override
	public void update() {
	}
	@Override
	public void insertRow() {
	}
	@Override
	public void deleteRow() {
	}

}