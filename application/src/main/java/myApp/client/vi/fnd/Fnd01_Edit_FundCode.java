package myApp.client.vi.fnd;

import java.util.List;
import java.util.Map;

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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.fnd.model.Fnd06_OfficeWorkModel;
import myApp.client.vi.org.Org01_Lookup_OrgCode;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Fnd01_Edit_FundCode extends ContentPanel implements Editor<Fnd01_FundCodeModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Fnd01_FundCodeModel, Fnd01_Edit_FundCode> {}
	EditDriver editDriver = GWT.create(EditDriver.class);

	private Grid<Fnd01_FundCodeModel> grid;
	private Label blankLabel = new Label();

	private	TextButton updateButton = new TextButton("저장");

	TextField fundCode = new TextField();
	DateField startDate = new DateField();
	DateField endDate = new DateField();
	TextField closeYn = new TextField();
	ComboBoxField closeName = new ComboBoxField("ClosedFundCode", closeYn);
	
	@Path("fundInfoModel.fundName")
	TextField fundName = new TextField();

	@Path("fundInfoModel.fundTypeCode")
	TextField fundTypeCode = new TextField();
	@Path("fundInfoModel.fundTypeName")
	ComboBoxField fundTypeName = new ComboBoxField("FundTypeCode", fundTypeCode);
	
	@Path("fundInfoModel.workDate")
	DateField workDate = new DateField();

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

	@Path("fundInfoModel.sunjasanAek")
	LongField sunjasanAek = new LongField();

	@Path("fundInfoModel.orgCodeId")
	LongField orgCodeId = new LongField();
	@Path("fundInfoModel.orgCodeName")
//	LookupTriggerField orgCodeName = new LookupTriggerField();
	TextField orgCodeName = new TextField();

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
	
	@Path("fundInfoModel.consignmentCd")
	TextField consignmentCd = new TextField();
	@Path("fundInfoModel.consignmentName")
	LookupTriggerField consignmentName = new LookupTriggerField();
	
	@Path("fundInfoModel.consignmentRate")
	DoubleField consignmentRate = new DoubleField();

	@Path("fundInfoModel.officeWorkCd")
	TextField officeWorkCd = new TextField();
	@Path("fundInfoModel.officeWorkName")
	LookupTriggerField officeWorkName = new LookupTriggerField();
	
	@Path("fundInfoModel.officeWorkRate")
	DoubleField officeworkRate = new DoubleField();

	public Fnd01_Edit_FundCode(Grid<Fnd01_FundCodeModel> grid) {

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
		officeworkRate.setFormat(NumberFormat.getFormat("#,###.#####"));
		seoljAek.setFormat(NumberFormat.getFormat("#,###.#####"));
		sunjasanAek.setFormat(NumberFormat.getFormat("#,###.#####"));

		// Column Size Define
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(250, -1);
		rowLayout.setMargins(new Margins(0, 20, 5, 0));

		// Double Column Size 
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(500, -1);
		rowLayout2.setMargins(new Margins(0, 20, 5, 0)); 

		// Triple Column Size 
		HorizontalLayoutData rowLayout3 = new HorizontalLayoutData(750, -1);
		rowLayout3.setMargins(new Margins(0, 20, 5, 0)); 

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
//		fundCode.setReadOnly(true);
		row00.add(new FieldLabel(fundCode, "펀드코드 "), rowLayout);
		row00.add(new FieldLabel(fundName, "펀드명 "), rowLayout2);
		row00.add(new FieldLabel(fundTypeName, "펀드유형 "), rowLayout);

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(sintakGbName, "신탁구분 "), rowLayout);
		row01.add(new FieldLabel(startDate, "설정일자 "), rowLayout);
		row01.add(new FieldLabel(endDate, "해지일자 "), rowLayout);
		row01.add(new FieldLabel(closeName, "해지여부 "), rowLayout);
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(seoljAek, "설정액 "), rowLayout);
		row02.add(new FieldLabel(sunjasanAek, "순자산 "), rowLayout);
		row02.add(blankLabel, rowLayout);
		row02.add(new FieldLabel(publicName, "공모여부 "), rowLayout);

		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(managementRate, "운용보수(bp) "), rowLayout);
//		orgCodeName.addTriggerClickHandler(new TriggerClickHandler(){
//			@Override
//			public void onTriggerClick(TriggerClickEvent event) {
//				openLookupOrg();
//			}
//		}); 
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
		
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		row05.add(new FieldLabel(consignmentRate, "수탁보수(bp) "), rowLayout);
		row05.add(new FieldLabel(consignmentName, "수탁사 "), new HorizontalLayoutData(300, -1));
		consignmentName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupConsignment();
			}
		});
		
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		row06.add(new FieldLabel(officeworkRate, "사무수탁보수(bp) "), rowLayout);
		row06.add(new FieldLabel(officeWorkName, "사무수탁사 "), new HorizontalLayoutData(300, -1));
		officeWorkName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupOfficeWork();
			}
		});

		VerticalLayoutContainer layout = new VerticalLayoutContainer(); 
		layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);
		
		layout.add(row00, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(30, 15, 15, 15)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(30, 15, 15, 15))); 
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row05, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row06, new VerticalLayoutData(1, -1, new Margins(15, 15, 15, 15)));

		// form setting 
		FormPanel form = new FormPanel(); 
		form.setWidget(layout);
		form.setLabelWidth(100); // 모든 field 적용 후 설정한다.

		return form;
	}

//	private void openLookupOrg() {
//		Org01_Lookup_OrgCode orgPopup= new Org01_Lookup_OrgCode();
//		orgPopup.open(workDate.getValue(), new InterfaceCallbackResult() {
//			@Override
//			public void execute(Object result) {
//				Org01_CodeModel findOrgCodeModel = (Org01_CodeModel)result;
//				orgCodeName.setValue(findOrgCodeModel.getOrgInfoModel().getKorName());
//				orgCodeId.setValue(findOrgCodeModel.getCodeId());
//			}
//		});
//	}
	
	private void openLookupConsignment() {
		Fnd03_Lookup_Consignment consignmentPopup = new Fnd03_Lookup_Consignment();
		consignmentPopup.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Fnd03_ConsignmentModel consignmentModel = (Fnd03_ConsignmentModel) result;
				consignmentCd.setValue(consignmentModel.getConsignmentCode());
				consignmentName.setValue(consignmentModel.getConsignmentName());
			}
		});
	}

	private void openLookupOfficeWork() {
		Fnd06_Lookup_OfficeWork officeWorkPopup = new Fnd06_Lookup_OfficeWork();
		officeWorkPopup.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Fnd06_OfficeWorkModel officeWorkModel = (Fnd06_OfficeWorkModel) result;
				officeWorkCd.setValue(officeWorkModel.getOfficeWorkCode());
				officeWorkName.setValue(officeWorkModel.getOfficeWorkName());
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

	public void retrieve(Map<String, Object> param) {
		if(param != null) { 
			Fnd01_FundCodeModel fundModel = (Fnd01_FundCodeModel)param.get("fundModel"); 
			editDriver.edit(fundModel);
		}
		else {
			editDriver.initialize(this);
		}
	}

	public void updateRow() {
		
		if (adjustSize) {
			
		}
		GridUpdate<Fnd01_FundCodeModel> service = new GridUpdate<Fnd01_FundCodeModel>(); 
		service.update(grid.getStore(), editDriver.flush(), "fnd.Fnd01_FundCode.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				ServiceResult serviceResult = (ServiceResult) result;
				if (serviceResult.getStatus() == 0) {
					new SimpleMessage("확인", serviceResult.getMessage());
					return;
				}
//				grid.getStore().update(editDriver.flush());
			}
		});
	}

	public void deleteRow() {
		GridDeleteData<Fnd01_FundCodeModel> service = new GridDeleteData<Fnd01_FundCodeModel>();
		List<Fnd01_FundCodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd01_FundCode.delete", new InterfaceCallback() {
			@Override
			public void execute() {
//				grid.getStore().update(editDriver.flush());
			}
		});
	}

	public void init() {
		editDriver.edit(new Fnd01_FundCodeModel());
	}

}
