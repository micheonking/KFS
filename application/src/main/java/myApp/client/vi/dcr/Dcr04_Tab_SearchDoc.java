package myApp.client.vi.dcr;

import java.util.Date;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.ClientDateUtil;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr00_DocListModel;
import myApp.client.vi.dcr.model.Dcr00_DocListModelProperties;

public class Dcr04_Tab_SearchDoc extends BorderLayoutContainer implements InterfaceServiceCall {
	private Grid<Dcr00_DocListModel> grid = this.buildGrid();
	
	private Date toDay = new Date();
	private MyDateField startRegDate = new MyDateField();
	private MyDateField endRegDate = new MyDateField();
	private ComboBoxField sintakComboBox = new ComboBoxField("SintakCode", "%", "전체");
	private StringComboBox publicSubscribeComboBox = new StringComboBox();
	private TextField personText = new TextField();
	private MyDateField startFundDate = new MyDateField();
	private MyDateField endFundDate = new MyDateField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode", "%", "전체");
	private StringComboBox fundCloseComboBox = new StringComboBox();
	private TextField orgText = new TextField();
	private TextField docTypeText = new TextField();
	private TextField fundText = new TextField();
	private TextButton retrieveButton = new TextButton("검색");
	
	public Dcr04_Tab_SearchDoc() {
		init();
	}
	
	private void init() {
		this.setCenterWidget(getCenter());//중앙 컨테이너 표시
		toDay = ClientDateUtil.toDate(toDay);
		Date endDate = CalendarUtil.copyDate(toDay);
		CalendarUtil.addMonthsToDate(endDate, -1);
		this.endRegDate.setValue(toDay);
		this.startRegDate.setValue(endDate);
	}
	
	private BorderLayoutContainer getCenter() {
		BorderLayoutContainer blc = new BorderLayoutContainer();

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(getButtonBar1());
		vlc.add(getButtonBar2());
		vlc.add(getButtonBar3());
		
		vlc.add(grid, new VerticalLayoutData(1,1));
		
		blc.add(vlc);
		
		return blc;
	}
	
	private ButtonBar getButtonBar1() {
		ButtonBar buttonBar = new ButtonBar();
		
		buttonBar.add(new LabelToolItem("등록기간:"));
		this.startRegDate.setWidth(100); 
		buttonBar.add(this.startRegDate);
		buttonBar.add(new LabelToolItem("~"));
		this.endRegDate.setWidth(100);
		buttonBar.add(endRegDate);
		
		buttonBar.add(new LabelToolItem("신탁구분:"));
		sintakComboBox.setValue("전체");
		buttonBar.add(sintakComboBox);
		
		buttonBar.add(new LabelToolItem("공모구분:"));
		publicSubscribeComboBox.getStore().add("전체");
		publicSubscribeComboBox.getStore().add("공모");
		publicSubscribeComboBox.getStore().add("사모");
		publicSubscribeComboBox.setTriggerAction(TriggerAction.ALL);
		publicSubscribeComboBox.setText("전체");
		buttonBar.add(publicSubscribeComboBox);
		
		buttonBar.add(new LabelToolItem("등록담당:"));
		personText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(personText);

		buttonBar.add(retrieveButton);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		});
		
		return buttonBar;
	}

	private ButtonBar getButtonBar2() {
		ButtonBar buttonBar = new ButtonBar();
		
		buttonBar.add(new LabelToolItem("설정기간:"));
		this.startFundDate.setWidth(100); 
		buttonBar.add(this.startFundDate);
		buttonBar.add(new LabelToolItem("~"));
		this.endFundDate.setWidth(100);
		buttonBar.add(endFundDate);
		
		buttonBar.add(new LabelToolItem("유형구분:"));
		fundTypeComboBox.setValue("전체");
		buttonBar.add(fundTypeComboBox);
		
		buttonBar.add(new LabelToolItem("해지구분:"));
		fundCloseComboBox.getStore().add("전체");
		fundCloseComboBox.getStore().add("생펀드:");
		fundCloseComboBox.getStore().add("해지펀드:");
		fundCloseComboBox.setTriggerAction(TriggerAction.ALL);
		fundCloseComboBox.setText("전체");
		buttonBar.add(fundCloseComboBox);
		
		buttonBar.add(new LabelToolItem("등록부서:"));
		orgText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(orgText);
		
		return buttonBar;
	}
	
	private ButtonBar getButtonBar3() {
		ButtonBar buttonBar = new ButtonBar();
		
		buttonBar.add(new LabelToolItem("문서분류:"));
		docTypeText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(docTypeText);
		
		buttonBar.add(new LabelToolItem("펀드검색:"));
		fundText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(fundText);
		
		return buttonBar;
	}
	
	public Grid<Dcr00_DocListModel> buildGrid(){
		Dcr00_DocListModelProperties properties = GWT.create(Dcr00_DocListModelProperties.class);		
		GridBuilder<Dcr00_DocListModel> gridBuilder = new GridBuilder<Dcr00_DocListModel>(properties.keyId());  

		gridBuilder.setChecked(SelectionMode.SINGLE);
//		gridBuilder.addText(properties.fundTypeName(), 100, "문서분류");
		gridBuilder.addText(properties.fundTypeName(), 100, "문서구분");
		gridBuilder.addText(properties.fileName(), 250, "문서명");
		ActionCell<String> editDocCell = new ActionCell<String>("Detail", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
//				Dcr00_DocListModel tempModel = grid.getSelectionModel().getSelectedItem();
//				if("결재".equals(tempModel.getDocModel().getTypeName())){
//					Dcr04_DocModel dcrModel = new Dcr04_DocModel();
//					dcrModel.setDocId(tempModel.getDocModel().getDocId());//문서분류 초기값 설정
//					logger.info(dcrModel.getDocId()+"");
//					dcrModel.setDocTypeName(tempModel.getDocModel().getDocTypeName());//문서분류명 설정
//					dcrModel.setRegEmpId(tempModel.getDocModel().getRegEmpId()); // 등록자ID 설정
//					dcrModel.setRegEmpName(tempModel.getDocModel().getRegEmpName());//등록자명 설정
//					
//					dcrModel.setActionCell("");
//					
//					Apr01_Edit_AprInsert editor = new Apr01_Edit_AprInsert(null, dcrModel, "view");
//					editor.show();
//				} else {
//					Dcr04_DocModel dcrModel = tempModel.getDocModel();
//					dcrModel.setDocTypeName(tempModel.getDocModel().getDocTypeName());//문서분류명 설정
//					Dcr04_Edit_Doc editor = new Dcr04_Edit_Doc(null, dcrModel);
//					editor.show();
//					editor.retrieveDcr();
//				}
			}
		});
		gridBuilder.addText(properties.fundName(), 400, "펀드명");
		
//		gridBuilder.addCell(properties.actionCell(), 60, "상세", editDocCell); //, new TextField()) ;

		gridBuilder.addDate(properties.regDate(), 100, "등록일");
		gridBuilder.addText(properties.empName(), 80, "등록자");
		
		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		
	}

	public void retrieve() {
		GridRetrieveData<Dcr00_DocListModel> service = new GridRetrieveData<Dcr00_DocListModel>(this.grid.getStore());
		setRetrieveParam(service);
		
		this.grid.mask("Loading");
		service.retrieve("dcr.Dcr04_Doc.selectSearchDoc", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
	
	private void setRetrieveParam(GridRetrieveData<Dcr00_DocListModel> service) {
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("orgCode", LoginUser.getOrgCodeId());
		service.addParam("startRegDate", startRegDate.getValue());
		service.addParam("endRegDate", endRegDate.getValue());

		if("전체".equals(sintakComboBox.getText())) {
			service.addParam("sintak", "ALL");
		} else {
			service.addParam("sintak", sintakComboBox.getCode());
		}

		if("공모".equals(publicSubscribeComboBox.getText())){
			service.addParam("publicSubscribe", "true");
		} else if("사모".equals(publicSubscribeComboBox.getText())) {
			service.addParam("publicSubscribe", "false");
		} else {
			service.addParam("publicSubscribe", "ALL");
		}

		service.addParam("personText", "%" + personText.getText() + "%");
		service.addParam("startFundDate", startFundDate.getValue());
		service.addParam("endFundDate", endFundDate.getValue());
		
		if("전체".equals(fundTypeComboBox.getText())) {
			service.addParam("fundType", "ALL");
		} else {
			service.addParam("fundType", fundTypeComboBox.getCode());
		}
		
		if("생펀드".equals(fundCloseComboBox.getText())){
			service.addParam("fundClose", "false");
		} else if("해지펀드".equals(fundCloseComboBox.getText())) {
			service.addParam("fundClose", "true");
		} else {
			service.addParam("fundClose", "ALL");
		}
		
		service.addParam("orgText", "%" + orgText.getText() + "%");
		service.addParam("docTypeText", "%" + docTypeText.getText() + "%");
		service.addParam("fundText", "%" + fundText.getText() + "%");
	}
}