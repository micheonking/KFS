package myApp.client.vi.cst;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.cst.model.Cst02_AccountModel;
import myApp.client.vi.dbm.Dbm01_Lookup_Tables;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModel;

public class Cst01_Tab_BaseInfo extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private Cst02_AccountModel cst02AccountModel = new Cst02_AccountModel();
	private LookupTriggerField lookupAccountField = new LookupTriggerField();
	private ComboBox1FundCode fundCodeComboBox = new ComboBox1FundCode("");
	VerticalLayoutContainer rdLayoutContainer = new VerticalLayoutContainer();

	public interface RDTemplate extends XTemplates {
	    @XTemplate("<iframe id='reportDesinger' border=0 src='{pageName}' width='100%' height='100%' /> ")
	    SafeHtml getTemplate(String pageName);
	}

	public Cst01_Tab_BaseInfo() {
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		FieldLabel fundCodeField = new FieldLabel(fundCodeComboBox, "계좌 ");	//	펀드코드 선택
		fundCodeField.setLabelWidth(60);
		fundCodeField.setWidth(400);
		searchBarBuilder.getSearchBar().add(fundCodeField);

		searchBarBuilder.addRetrieveButton();

		this.setBorders(false);

		BorderLayoutData northLayoutData = new BorderLayoutData(50);
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
//		northLayoutData.setSplit(true);
		northLayoutData.setMaxSize(1000);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData();
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);
		centerLayoutData.setMargins(new Margins(8, 0, 4, 0));
		this.setCenterWidget(rdLayoutContainer,centerLayoutData);

		retrieve();
	}

	private void setReportDesigner() {
		String pageName = "http://172.20.200.252:8283/ReportingServer/html5/RDhtml/";
		String fundCode = fundCodeComboBox.getCurrentFundCode();
		RDTemplate rdTemplate = GWT.create(RDTemplate.class);

		if(fundCode != "null") {
			pageName = pageName + "web_cs_info.html?fund_cd=" + fundCode;
		}
		else {
			pageName = pageName + "sample.html";
			HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(rdTemplate.getTemplate(pageName));

			rdLayoutContainer.clear();
			rdLayoutContainer.add(htmlLayoutContainer, new VerticalLayoutData(1, 1));
			this.setCenterWidget(rdLayoutContainer);

		}
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(rdTemplate.getTemplate(pageName));

		rdLayoutContainer.clear();
		rdLayoutContainer.add(htmlLayoutContainer, new VerticalLayoutData(1, 1));
		this.setCenterWidget(rdLayoutContainer);
	}

//	private void openLookupAccount() {
//		Cst02_LookupAccount lookupAccount = new Cst02_LookupAccount();
//		lookupAccount.open(new InterfaceCallbackResult() {
//			@Override
//			public void execute(Object result) {
//				cst02AccountModel = (Cst02_AccountModel)result;
//				lookupAccountField.setValue(cst02AccountModel.getAccountName());
//			}
//		});
//
//	}

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