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
import myApp.client.vi.apr.Apr01_Edit_ApprDoc;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.dcr.model.Dcr00_DocListModel;
import myApp.client.vi.dcr.model.Dcr00_DocListModelProperties;

public class Dcr00_Tab_DocList extends BorderLayoutContainer implements InterfaceServiceCall {

    private Dcr00_DocListModelProperties properties = GWT.create(Dcr00_DocListModelProperties.class);
	private Grid<Dcr00_DocListModel> grid = this.buildGrid();
	private TextField searchText = new TextField();
	
	private Date toDay = new Date();
	private MyDateField regDate = new MyDateField();
	private MyDateField endRegDate = new MyDateField();
	private ComboBoxField sintakComboBox = new ComboBoxField("SintakCode", "Y","전체");
	private StringComboBox publicSubscribeComboBox = new StringComboBox();
	private TextField empName = new TextField();
	private MyDateField fundStartDate = new MyDateField();
	private MyDateField fundEndDate = new MyDateField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode", "Y","전체");
	private StringComboBox fundCloseComboBox = new StringComboBox();
	private TextField orgName = new TextField();
	private TextField classTreeName = new TextField();
	private TextField fundName = new TextField();
	private TextButton retrieveButton = new TextButton("검색");
	
	public Dcr00_Tab_DocList() {
		init();
	}
	
	private void init() {
		this.setCenterWidget(getCenter());//중앙 컨테이너 표시
		toDay = ClientDateUtil.toDate(toDay);
		Date endDate = CalendarUtil.copyDate(toDay);
		CalendarUtil.addMonthsToDate(endDate, -1);
		this.endRegDate.setValue(toDay);
		this.regDate.setValue(endDate);
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
		this.regDate.setWidth(100); 
		buttonBar.add(this.regDate);
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
		empName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(empName);

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
		this.fundStartDate.setWidth(100); 
		buttonBar.add(this.fundStartDate);
		buttonBar.add(new LabelToolItem("~"));
		this.fundEndDate.setWidth(100);
		buttonBar.add(fundEndDate);
		
		buttonBar.add(new LabelToolItem("유형구분:"));
		fundTypeComboBox.setValue("전체");
		buttonBar.add(fundTypeComboBox);
		
		buttonBar.add(new LabelToolItem("해지구분:"));
		fundCloseComboBox.getStore().add("전체");
		fundCloseComboBox.getStore().add("생펀드");
		fundCloseComboBox.getStore().add("해지펀드");
		fundCloseComboBox.setTriggerAction(TriggerAction.ALL);
		fundCloseComboBox.setText("전체");
		buttonBar.add(fundCloseComboBox);
		
		buttonBar.add(new LabelToolItem("등록부서:"));
		orgName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(orgName);
		
		return buttonBar;
	}
	
	private ButtonBar getButtonBar3() {
		ButtonBar buttonBar = new ButtonBar();
		
		buttonBar.add(new LabelToolItem("문서구분:"));
		classTreeName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(classTreeName);
		
		buttonBar.add(new LabelToolItem("펀드검색:"));
		fundName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		buttonBar.add(fundName);
		
		return buttonBar;
	}
	

	public Grid<Dcr00_DocListModel> buildGrid(){
		
		GridBuilder<Dcr00_DocListModel> gridBuilder = new GridBuilder<Dcr00_DocListModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText		(properties.parentTreeName(),	130,	"문서분류");
		gridBuilder.addText		(properties.classTreeName(),	160,	"문서구분");
//		gridBuilder.addText		(properties.fundTypeName(),		100,	"문서구분");
		gridBuilder.addText		(properties.fundCode(),		60,		"펀드코드");
		gridBuilder.addText		(properties.fundName(),		360,	"펀드명");
		gridBuilder.addText		(properties.aprTitle(),		200,	"상신명");
		ActionCell<String> editDocCell = new ActionCell<String>("View", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				Apr01_ApprModel apprModel = grid.getSelectionModel().getSelectedItem().getApprModel(); 
				Apr01_Edit_ApprDoc editor = new Apr01_Edit_ApprDoc();
				editor.retrieveApproval(apprModel, false, new InterfaceCallback() {
					@Override
					public void execute() {
						retrieve();
					}
				});
			}
		});
		gridBuilder.addCell(properties.actionCell(), 100, "상세보기", editDocCell);
		gridBuilder.addText		(properties.fileName(),		260,	"파일명");
		gridBuilder.addDate		(properties.regDate(),		90,	"등록일");
		gridBuilder.addDate(properties.effectDate(),        90, "시행일");
		gridBuilder.addText		(properties.empName(),		55,	"등록자");
		
		return gridBuilder.getGrid(); 
	}
	
	public void retrieve() {
		GridRetrieveData<Dcr00_DocListModel> service = new GridRetrieveData<Dcr00_DocListModel>(this.grid.getStore());
		setRetrieveParam(service);
		
		this.grid.mask("Loading");
		service.retrieve("dcr.Dcr00_DocList.selectSearchDoc", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
	private void setRetrieveParam(GridRetrieveData<Dcr00_DocListModel> service) {
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("orgId", LoginUser.getOrgCodeId());
		service.addParam("regDate", regDate.getValue());
		service.addParam("endRegDate", endRegDate.getValue());

		if("전체".equals(sintakComboBox.getText())) {
			service.addParam("sintak", "%");
		} else {
			service.addParam("sintak", sintakComboBox.getCode());
		}

		if("공모".equals(publicSubscribeComboBox.getText())){
			service.addParam("publicSubscribe", "true");
		} else if("사모".equals(publicSubscribeComboBox.getText())) {
			service.addParam("publicSubscribe", "false");
		} else {
			service.addParam("publicSubscribe", "%");
		}

		service.addParam("empName", "%" + empName.getText() + "%");
		service.addParam("fundStartDate", fundStartDate.getValue());
		service.addParam("fundEndDate", fundEndDate.getValue());
		
		if("전체".equals(fundTypeComboBox.getText())) {
			service.addParam("fundType", "%");
		} else {
			service.addParam("fundType", fundTypeComboBox.getCode());
		}
		
		if("생펀드".equals(fundCloseComboBox.getText())){
			service.addParam("fundClose", "false");
		} else if("해지펀드".equals(fundCloseComboBox.getText())) {
			service.addParam("fundClose", "true");
		} else {
			service.addParam("fundClose", "%");
		}
		
		service.addParam("orgName", "%" + orgName.getText() + "%");
		service.addParam("classTreeName", "%" + classTreeName.getText() + "%");
		service.addParam("fundName", "%" + fundName.getText() + "%");
		service.addParam("classTreeName", "%" + classTreeName.getText() + "%");
	}
	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
		
	}

}
