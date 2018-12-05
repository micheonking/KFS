package myApp.client.vi.fnd;

import java.util.List;

import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.ComboBoxField;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.org.Org01_Lookup_OrgCode;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Fnd01_Edit_FundPlan extends ContentPanel implements Editor<Fnd01_FundCodeModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Fnd01_FundCodeModel, Fnd01_Edit_FundPlan> {}
	EditDriver editDriver = GWT.create(EditDriver.class);

	private Grid<Fnd01_FundCodeModel> grid;
	private Label blankLabel = new Label();
	private Label blankLabel2 = new Label();
	
	private	TextButton updateButton = new TextButton("저장");

//	LongField fundCodeId = new LongField();
	TextField fundCode = new TextField();

	@Path("fundInfoModel.fundName")
	TextField fundName = new TextField();
	@Path("fundInfoModel.fundTypeCode")
	TextField fundTypeCode = new TextField();
	@Path("fundInfoModel.fundTypeName")
	ComboBoxField fundTypeName = new ComboBoxField("FundTypeCode", fundTypeCode);
	
	
	DateField startDate = new DateField();
	DateField endDate = new DateField();
	TextField closeYn = new TextField();
	TextField planYn = new TextField();
	LongField realFundId = new LongField();
	LongField companyId = new LongField();
	ComboBoxField closeName = new ComboBoxField("PlnClosedFundCode", closeYn);
	LookupTriggerField realFundName = new LookupTriggerField();

	@Path("fundInfoModel.workDate")
	DateField workDate = new DateField();
	
	@Path("fundInfoModel.fundInfoId")
	LongField fundInfoId = new LongField();
	@Path("fundInfoModel.sintakGb")
	TextField sintakGb = new TextField();
	@Path("fundInfoModel.sintakGbName")
	ComboBoxField sintakGbName = new ComboBoxField("SintakCode", sintakGb);
	@Path("fundInfoModel.publicYn")
	TextField publicYn = new TextField();
	@Path("fundInfoModel.publicName")
	ComboBoxField publicName = new ComboBoxField("PublicCode", publicYn);
	@Path("fundInfoModel.seoljAek")
	LongField seoljAek = new LongField();
//	@Path("fundInfoModel.sunjasanAek")
//	LongField sunjasanAek = new LongField();
	@Path("fundInfoModel.orgCodeId")
	LongField orgCodeId = new LongField();
	@Path("fundInfoModel.orgCodeName")
	LookupTriggerField orgCodeName = new LookupTriggerField();
	@Path("fundInfoModel.emp1Id")
	LongField emp1Id = new LongField();
	@Path("fundInfoModel.emp1Name")
	LookupTriggerField emp1Name = new LookupTriggerField();
	@Path("fundInfoModel.emp2Id")
	LongField emp2Id = new LongField();
	@Path("fundInfoModel.emp2Name")
	LookupTriggerField emp2Name = new LookupTriggerField();
	@Path("fundInfoModel.managementRate")
	DoubleField managementRate = new DoubleField();
	@Path("fundInfoModel.salesRate")
	DoubleField salesRate = new DoubleField();
	@Path("fundInfoModel.consignmentRate")
	DoubleField consignmentRate = new DoubleField();
	@Path("fundInfoModel.officeWorkRate")
	DoubleField officeWorkRate = new DoubleField();
	@Path("fundInfoModel.planDate")
	DateField planDate = new DateField();
	@Path("fundInfoModel.planSalesDscr")
	TextField planSalesDscr = new TextField();
	@Path("fundInfoModel.planInvestorDscr")
	TextField planInvestorDscr = new TextField();
	@Path("fundInfoModel.planNote")
	TextArea planNote = new TextArea();
	
	public Fnd01_Edit_FundPlan(Grid<Fnd01_FundCodeModel> grid) {

		this.grid = grid ;
		editDriver.initialize(this);
		this.setHeaderVisible(false);
		
		this.add(this.getEditor(), new MarginData(10)); 

		updateButton.setWidth(50);
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				updateRow();
			}
		});
		this.addButton(updateButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	private FormPanel getEditor(){
		managementRate.setFormat(NumberFormat.getFormat("#,###.#####"));
		salesRate.setFormat(NumberFormat.getFormat("#,###.#####"));
		consignmentRate.setFormat(NumberFormat.getFormat("#,###.#####"));
		officeWorkRate.setFormat(NumberFormat.getFormat("#,###.#####"));
		seoljAek.setFormat(NumberFormat.getFormat("#,###"));
//		sunjasanAek.setFormat(NumberFormat.getFormat("#,###"));
		
		HorizontalLayoutData rowLayout  = new HorizontalLayoutData(250, -1, new Margins(0, 20, 5, 0));	// Column Size Define
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(500, -1, new Margins(0, 20, 5, 0));	// Double Column Size
		HorizontalLayoutData rowLayout3 = new HorizontalLayoutData(750, -1, new Margins(0, 20, 5, 0));	// Triple Column Size

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		row00.add(new FieldLabel(fundCode, "펀드코드 "), rowLayout);
		row00.add(new FieldLabel(fundName, "펀드명 "), rowLayout2);
		row00.add(new FieldLabel(fundTypeName, "펀드유형 "), rowLayout);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(sintakGbName, "신탁구분 "), rowLayout);
		row01.add(new FieldLabel(publicName, "공모여부 "), rowLayout);
		row01.add(new FieldLabel(startDate, "설정일 "), rowLayout);
		row01.add(new FieldLabel(endDate, "해지일 "), rowLayout);
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(seoljAek, "설정액 "), rowLayout);
//		row02.add(new FieldLabel(sunjasanAek, "순자산 "), rowLayout);
//		row02.add(blankLabel, rowLayout);

		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(managementRate, "운용보수(bp) "), rowLayout);

		orgCodeName.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupOrg();
			}
		}); 
		row03.add(new FieldLabel(orgCodeName, "운용부서 "), rowLayout);

		emp1Name.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupEmp1();
			}
		}); 
		row03.add(new FieldLabel(emp1Name, "운용역1 "), rowLayout);

		emp2Name.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupEmp2();
			}
		}); 
		row03.add(new FieldLabel(emp2Name, "운용역2 "), rowLayout);

		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		row04.add(new FieldLabel(salesRate, "판매보수(bp) "), rowLayout);
		row04.add(new FieldLabel(planSalesDscr, "판매사정보 "), rowLayout3);
		
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		row05.add(new FieldLabel(consignmentRate, "수탁보수(bp) "), rowLayout);
		row05.add(new FieldLabel(planInvestorDscr, "투자자정보 "), rowLayout3);
		
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		row06.add(new FieldLabel(officeWorkRate, "사무수탁보수(bp) "), rowLayout);
		
		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
		planNote.setHeight("80");
//		row07.add(new FieldLabel(planNote, "펀드개요 "), new HorizontalLayoutData(980, 1, new Margins(0,0,0,0)));
		row07.add(new LabelToolItem("펀드개요 : "), new HorizontalLayoutData(100, -1, new Margins(0,0,0,43)));
		row07.add(planNote, new HorizontalLayoutData(880, 80, new Margins(0,0,0,5)));

		HorizontalLayoutContainer row08 = new HorizontalLayoutContainer();
		realFundName.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupFundCode();
			}
		});
		realFundName.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if ((realFundName.getText() == null) || (realFundName.getText().equals(""))) {
					realFundName.setValue(null);
					realFundId.setValue(null);
					closeYn.setValue("false");
					closeName.setValue("기획펀드");
				}
			}
		});
		realFundName.setEmptyText("펀드로 확정된 상품만 등록합니다.");
		row08.add(new FieldLabel(realFundName, "확정펀드 "), rowLayout2);
		row08.add(new FieldLabel(closeName, "펀드상태 "), rowLayout);
		row08.add(blankLabel2, rowLayout);

		VerticalLayoutContainer layout = new VerticalLayoutContainer(); 
		layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);
		
		layout.add(row00, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(15))); 
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row05, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row06, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row07, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row08, new VerticalLayoutData(1, -1, new Margins(75, 15, 15, 15)));

		// form setting 
		FormPanel form = new FormPanel(); 
		form.setWidget(layout);
		form.setLabelWidth(100); // 모든 field 적용 후 설정한다.
		return form;
	}
	
	private void openLookupFundCode() {
		Fnd00_Lookup_FundCode lookupFundCode = new Fnd00_Lookup_FundCode();
		lookupFundCode.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Fnd00_FundModel findFundModel = (Fnd00_FundModel)result;
				realFundName.setValue(findFundModel.getFundName());
				realFundId.setValue(findFundModel.getFundId());
				closeYn.setValue("true");
				closeName.setValue("종료펀드");
			}
		});
	}

	private void openLookupOrg() {
		Org01_Lookup_OrgCode searchPopup= new Org01_Lookup_OrgCode();
		searchPopup.open(workDate.getValue(), new InterfaceCallbackResult() {
			
			@Override
			public void execute(Object result) {
				Org01_CodeModel findOrgCodeModel = (Org01_CodeModel)result;
				orgCodeName.setValue(findOrgCodeModel.getOrgInfoModel().getKorName());
				orgCodeId.setValue(findOrgCodeModel.getCodeId());
			}
		});
	}

	private void openLookupEmp1() {
		Emp00_Lookup_EmpInfo searchPopup= new Emp00_Lookup_EmpInfo();
		searchPopup.open(workDate.getValue(), new InterfaceCallbackResult() {
			
			@Override
			public void execute(Object result) {
				Emp00_InfoModel findEmpInfoModel = (Emp00_InfoModel)result;
				emp1Id.setValue(findEmpInfoModel.getEmpId());
				emp1Name.setValue(findEmpInfoModel.getKorName());
			}
		});
	}

	private void openLookupEmp2() {
		Emp00_Lookup_EmpInfo searchPopup= new Emp00_Lookup_EmpInfo();
		searchPopup.open(workDate.getValue(), new InterfaceCallbackResult() {
			
			@Override
			public void execute(Object result) {
				Emp00_InfoModel findEmpInfoModel = (Emp00_InfoModel)result;
				emp2Id.setValue(findEmpInfoModel.getEmpId());
				emp2Name.setValue(findEmpInfoModel.getKorName());
			}
		});
	}

	public void retrieve(Fnd01_FundCodeModel fundModel) {
		if(fundModel != null) { 
			editDriver.edit(fundModel);
		} else {
			editDriver.initialize(this);
		}
	}

	public void updateRow() {
		if(fundCode.getText() == null || "".equals(fundCode.getText())) {
			new SimpleMessage("펀드코드를 입력하세요."); 
			return; 
		}
		if(fundName.getText() == null || "".equals(fundName.getText())) {
			new SimpleMessage("펀드명을 입력하세요."); 
			return; 
		}
		if(fundTypeCode.getText() == null || "".equals(fundTypeCode.getText())) {
			new SimpleMessage("펀드유형을 입력하세요."); 
			return; 
		}
		if(sintakGb.getText() == null || "".equals(sintakGb.getText())) {
			new SimpleMessage("신탁구분을 입력하세요."); 
			return; 
		}
		if(closeYn.getText() == null || "".equals(closeYn.getText())) {
			new SimpleMessage("펀드상태를 입력하세요."); 
			return; 
		}
		if(publicYn.getText() == null || "".equals(publicYn.getText())) {
			new SimpleMessage("공모여부를 입력하세요."); 
			return; 
		
		}

		GridUpdate<Fnd01_FundCodeModel> service = new GridUpdate<Fnd01_FundCodeModel>(); 
		service.update(grid.getStore(), editDriver.flush(), "fnd.Fnd01_FundCode.update" , new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				ServiceResult serviceResult = (ServiceResult) result;
				if (serviceResult.getStatus() == 0) {
					new SimpleMessage("확인", serviceResult.getMessage());
					return;
				}
			}
		});
	}

	public void deleteRow() {
		GridDeleteData<Fnd01_FundCodeModel> service = new GridDeleteData<Fnd01_FundCodeModel>();
		List<Fnd01_FundCodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd01_FundCode.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.getStore().update(editDriver.flush());
			}
		});
	}

}
