package myApp.client.vi.cst;

import java.util.Date;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.vi.LoginUser;
import myApp.client.vi.cst.Cst01_Tab_BaseInfo.RDTemplate;

public class Cst02_Tab_AssetSpecification extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private MyDateField startDate  	= new MyDateField();
	private ComboBox1FundCode fundCodeComboBox = new ComboBox1FundCode("");

	VerticalLayoutContainer rdLayoutContainer = new VerticalLayoutContainer();

	public interface RDTemplate extends XTemplates {
	    @XTemplate("<iframe id='reportDesinger' border=0 src='{pageName}' width='100%' height='100%' /> ")
	    SafeHtml getTemplate(String pageName);
	}

	public Cst02_Tab_AssetSpecification() {
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		FieldLabel fundCodeField = new FieldLabel(fundCodeComboBox, "계좌 ");	//	펀드코드 선택
		fundCodeField.setLabelWidth(60);
		fundCodeField.setWidth(400);
		searchBarBuilder.getSearchBar().add(fundCodeField);
		
		searchBarBuilder.getSearchBar().add(new LabelToolItem("기준일 :"));

		Date date = LoginUser.getToday();
		startDate.setValue(date);
		startDate.setWidth(110);
		searchBarBuilder.getSearchBar().add(startDate);

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
//		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(55));
//
//		this.setCenterWidget(rdLayoutContainer);
	}

	private void setReportDesigner() {
		
		String pageName = "http://172.20.200.252:8283/ReportingServer/html5/RDhtml/";
		String ymd = DateTimeFormat.getFormat("yyyy-MM-dd").format(startDate.getValue());
		String fundCode = fundCodeComboBox.getCurrentFundCode(); 

		RDTemplate rdTemplate = GWT.create(RDTemplate.class);

		if(fundCode != "null") {
			pageName = pageName + "web_cs_portfolio.html?ymd=" + ymd + "&fund_cd=" + fundCode;
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