package myApp.client.vi.rpt;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceLookupResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.PDFViewer;
import myApp.client.vi.rpt.model.TrialBalanceModel;
import myApp.client.vi.rpt.model.TrialBalanceModelProperties;
import myApp.client.vi.sys.Lookup_Company;
import myApp.client.vi.sys.model.Sys01_CompanyModel;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class Tab_TrialBalance extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private TrialBalanceModelProperties properties = GWT.create(TrialBalanceModelProperties.class);
	private Grid<TrialBalanceModel> grid = this.buildGrid();
	
	private Sys01_CompanyModel companyModel ; //= LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	private TextField yearMonth = new TextField(); 
	private DateField beginDate = new DateField(); 
	private DateField endDate = new DateField(); 
	
	public Tab_TrialBalance() {
		
		this.setBorders(false); 

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(lookupCompanyField, "유치원 ", 250, 50);
		searchBarBuilder.addTextField(yearMonth, "해당월 ", 130, 50, true); 
		searchBarBuilder.addDateField(beginDate, "시작일자 ", 165, 60, true); 
		searchBarBuilder.addDateField(endDate, "종료일자 ", 165, 60, true); 
		
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addDeleteButton();

		//	초기값 변경
		Date today = new Date();
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM");
		yearMonth.setValue(fmt.format(today));
		yearMonth.setValue("2015-03");

		Date bDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-03-01");
	    beginDate.setValue(bDate);
		Date eDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-03-31");
		endDate.setValue(eDate);

	    TextButton retrievePDFButton = new TextButton("PDF출력");
	    retrievePDFButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				PDFViewer viewer = new PDFViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				String requestString = "className=rpt.TrialBalancePDF"; 
				requestString = requestString + "&companyId=" + companyModel.getCompanyId(); 
				requestString = requestString + "&yearMonth=" + yearMonth.getText(); 
				requestString = requestString + "&beginDate=" + beginDate.getText(); 
				requestString = requestString + "&endDate=" + endDate.getText();
				
				
				viewer.open(requestString);
				
			}
		});
	    searchBarBuilder.getSearchBar().add(retrievePDFButton); 

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	private LookupTriggerField getLookupCompanyField(){
		
		final Lookup_Company lookupCompany = new Lookup_Company();
		lookupCompany.setCallback(new InterfaceLookupResult(){
			@Override
			public void setLookupResult(Object result) {
				companyModel = (Sys01_CompanyModel)result;
				lookupCompanyField.setValue(companyModel.getCompanyName());
			}
		});
		
		LookupTriggerField lookupCompanyField = new LookupTriggerField(); 
		lookupCompanyField.setEditable(false);
		lookupCompanyField.setText(this.companyModel.getCompanyName());
		lookupCompanyField.addTriggerClickHandler(new TriggerClickHandler(){
   	 		@Override
			public void onTriggerClick(TriggerClickEvent event) {
   	 			lookupCompany.show();
			}
   	 	}); 
		return lookupCompanyField; 
	}
	
	@Override
	public void retrieve(){
		
		System.out.println("Tab_TrialBalance Strart 1 : " + companyModel ); 

		Long companyId = this.companyModel.getCompanyId();
		if(companyId  == null){
			new SimpleMessage("조회할 유치원이 먼저 선택하여야 합니다.");
			return ; 
		}
		
		GridRetrieveData<TrialBalanceModel> service = new GridRetrieveData<TrialBalanceModel>(grid.getStore());
		service.addParam("companyId", companyId);
		service.addParam("yearMonth", yearMonth.getValue());
		service.addParam("beginDate", beginDate.getValue());
		service.addParam("endDate", endDate.getValue());
		service.retrieve("rpt.TrialBalance.selectByCompanyIdProc");
	}
	
	@Override
	public void update(){
		//
	}
	
	@Override
	public void insertRow(){
		//
	}
	
	@Override
	public void deleteRow(){
		//
	}
	
	public Grid<TrialBalanceModel> buildGrid(){
			
		GridBuilder<TrialBalanceModel> gridBuilder = new GridBuilder<TrialBalanceModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addLong(properties.inBalanceSum()		,	120	,	"잔액");	//	new TextField()); 
		gridBuilder.addLong(properties.inAccumulatedSum()	,	120	,	"누계");	//	new TextField()); 
		gridBuilder.addLong(properties.inAmounts()			,	120	,	"월계");	//	new TextField()); 
		gridBuilder.addLong(properties.inBudgetAmount()		,	120	,	"예산");	//	new TextField()); 
		gridBuilder.addText(properties.accountPrtNm()		,	300	,	"계정과목");	//	new TextField()); 
		gridBuilder.addLong(properties.outBudgetAmount()	,	120	,	"예산");	//	new TextField()); 
		gridBuilder.addLong(properties.outAmonut()			,	120	,	"월계");	//	new TextField()); 
		gridBuilder.addLong(properties.outAccumulatedSum()	,	120	,	"누계");	//	new TextField()); 
		gridBuilder.addLong(properties.outAalanceSum()		,	120	,	"잔액");	//	new TextField()); 

		return gridBuilder.getGrid();  
	}
}