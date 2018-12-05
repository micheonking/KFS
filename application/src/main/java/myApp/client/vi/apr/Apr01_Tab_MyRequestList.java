package myApp.client.vi.apr;

import java.util.Date;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.ClientDateUtil;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr01_ApprModelProperties;
import myApp.client.vi.apr.model.Apr03_RelateFundModel;
import myApp.client.vi.dcr.Dcr10_Lookup_MailSender;

public class Apr01_Tab_MyRequestList extends BorderLayoutContainer {
	private Date toDay = new Date();
	
	private ComboBoxField statusCode = new ComboBoxField("AprStatusCode", "%", "전체", new InterfaceCallback() {
		@Override
		public void execute() {
			retrieve();
		}
	}); 

	private Grid<Apr01_ApprModel> grid = this.buildGrid();
	
	private LabelToolItem label01 = new LabelToolItem("▶상신내역 조회");
	
	private LabelToolItem label02 = new LabelToolItem("등록기간:");
	private MyDateField startDate = new MyDateField();
	private MyDateField endDate = new MyDateField();
	private LabelToolItem label03 = new LabelToolItem("결재상태:");
	private LabelToolItem label04 = new LabelToolItem("결재완료:");
	private TextButton searchButton = new TextButton("검색");
	private CheckBox aprCheckBox = new CheckBox();
	
	public Apr01_Tab_MyRequestList() {
		ButtonBar buttonBar01 = new ButtonBar();
		searchButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		
		buttonBar01.add(label01, new BoxLayoutData(new Margins(200, 200, 200, 200)));
		buttonBar01.add(new LabelToolItem(""));
		buttonBar01.add(label02);
		buttonBar01.add(startDate);
		buttonBar01.add(endDate);
		buttonBar01.add(new LabelToolItem(""));
		buttonBar01.add(label03);
		buttonBar01.add(statusCode);
		buttonBar01.add(new LabelToolItem(""));
		buttonBar01.add(label04);
		aprCheckBox.setBoxLabel("완료/반려건 포함");
		aprCheckBox.setValue(false);
		buttonBar01.add(aprCheckBox);
		buttonBar01.add(searchButton);

		// set SearchBar 
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49
		this.setNorthWidget(buttonBar01, northLayoutData);

		// set Grid 
		this.setCenterWidget(this.grid);

		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Apr01_ApprModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Apr01_ApprModel> event) {
				if(event.getSelection().size() > 0){
				}
			} 
		}); 
		
		setInitData();
	}
	
	public Grid<Apr01_ApprModel> buildGrid(){

		Apr01_ApprModelProperties properties = GWT.create(Apr01_ApprModelProperties.class);
		GridBuilder<Apr01_ApprModel> gridBuilder = new GridBuilder<Apr01_ApprModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addDate(properties.regDate(), 90, "등록일");
		gridBuilder.addDate(properties.effectDate(), 90, "시행일");
		gridBuilder.addText(properties.statusName(), 80, "결재상태");
		gridBuilder.addText(properties.classTreeName(), 170, "문서분류");
		gridBuilder.addText(properties.title(), 250, "문서명");
		ActionCell<String> editDocCell = new ActionCell<String>("View", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				Apr01_ApprModel apprModel = grid.getSelectionModel().getSelectedItem(); 

				Apr01_Edit_ApprDoc editor = new Apr01_Edit_ApprDoc();
				editor.retrieveApproval(apprModel, false, new InterfaceCallback() {
					
					@Override
					public void execute() {
						retrieve();
					}
				});
			}
		});
		gridBuilder.addCell(properties.actionCell(), 80, "상세보기", editDocCell);

		ActionCell<String> sendMailCell = new ActionCell<String>("Send", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				sendMail();
			}
		});
		gridBuilder.addCell(properties.actionCell(), 80, "메일보내기", sendMailCell); //, new TextField()) ;
		gridBuilder.addText(properties.mailYn(), 70, "송신여부");
		gridBuilder.addText(properties.recentApprovalName(), 150, "최근결재자");
		gridBuilder.addDate(properties.recentApprovalDate(), 90, "최근결재일");
		gridBuilder.addText(properties.note(), 100, "비고");
		

		return gridBuilder.getGrid();
	}
	
	private void setInitData() {
		this.statusCode.setText("전체");
		toDay = ClientDateUtil.toDate(toDay);
		Date endDate = CalendarUtil.copyDate(toDay);
		CalendarUtil.addDaysToDate(endDate, -7);
		this.endDate.setValue(toDay);
		this.startDate.setValue(endDate);
	}
	
	private void retrieve(){
		this.grid.getStore().clear();
		this.grid.mask("Loading");
		GridRetrieveData<Apr01_ApprModel> service = new GridRetrieveData<Apr01_ApprModel>(this.grid.getStore());
		service.addParam("regEmpId", LoginUser.getUserId());
		//service.addParam("statusCode", statusName.getValue());
		service.addParam("statusCode", statusCode.getCode());
		service.addParam("startDate", startDate.getValue());
		service.addParam("endDate", endDate.getValue());
		service.addParam("aprYn", aprCheckBox.getValue());
		service.retrieve("apr.Apr01_Appr.selectByRequestEmpId", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		}); 
	}

	private void sendMail() {
		Apr01_ApprModel apprModel = grid.getSelectionModel().getSelectedItem();
		if(!"30".equals(apprModel.getStatusCode())) {
			new SimpleMessage("완료되지 않은 문서입니다.");
		} else {
			Dcr10_Lookup_MailSender mailSender = new Dcr10_Lookup_MailSender(apprModel.getApprId(), "");
			mailSender.show();
		}
	}
	
}