package myApp.client.vi.pln;

import java.util.Date;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.pln.model.Pln03_ResrchModel;
import myApp.client.vi.pln.model.Pln03_ResrchModelProperties;

public class Pln03_Tab_Resrch extends VerticalLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {

	private MyDateField startDate  	= new MyDateField();
	private DateField closeDate  	= new DateField();
	private TextField searchText    = new TextField();
	private CheckBox  closeCheckBox = new CheckBox();
	
	private Grid<Pln03_ResrchModel> grid = this.buildGrid();

	public Pln03_Tab_Resrch() {
		
		SearchBarBuilder searchbarBuilder = new SearchBarBuilder(this);

		searchbarBuilder.getSearchBar().add(new LabelToolItem("등록일 : "));

		Date date = LoginUser.getToday();
		CalendarUtil.addDaysToDate(date, -30);	//조회시작일은 30일전으로
		startDate.setValue(date);
		startDate.setWidth(100);
		searchbarBuilder.getSearchBar().add(startDate);

		searchbarBuilder.getSearchBar().add(new LabelToolItem("~"));

		closeDate.setValue(LoginUser.getToday());
		closeDate.setWidth(100);
		searchbarBuilder.getSearchBar().add(closeDate);

		searchbarBuilder.getSearchBar().add(new LabelToolItem(" 대상검색 : "));
		searchbarBuilder.getSearchBar().add(searchText);
		searchText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13) {
					retrieve();
				}
			}
		});

		closeCheckBox.setBoxLabel("완료건 제외");
		closeCheckBox.setValue(false);
		searchbarBuilder.addCheckBox(closeCheckBox, 100, 0);
		
		searchbarBuilder.addRetrieveButton();
		searchbarBuilder.addInsertButton();
		searchbarBuilder.addDeleteButton();

		this.add(searchbarBuilder.getSearchBar(), new VerticalLayoutData(1, 50, new Margins(0,0,0,0)));
		this.add(grid, new VerticalLayoutData(1, 1));

		retrieve();
	}

	private Grid<Pln03_ResrchModel> buildGrid() {

		Pln03_ResrchModelProperties properties = GWT.create(Pln03_ResrchModelProperties.class);
		GridBuilder<Pln03_ResrchModel> gridBuilder = new GridBuilder<Pln03_ResrchModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.parentTreeNm()	, 100, "문서분류");
		gridBuilder.addText(properties.classTreeNm()	, 150, "문서구분");
		gridBuilder.addText(properties.issuingEntityNm(), 200, "조사대상");
		gridBuilder.addText(properties.regEmpNm()		, 200, "등록자");
		gridBuilder.addDate(properties.regDate()		,  80, "등록일"); 
		gridBuilder.addDate(properties.resrchStartDate(),  80, "조사시작일"); 
		gridBuilder.addDate(properties.resrchCloseDate(),  80, "조사완료일");
		
		ActionCell<String> lookupResrchDetail = new ActionCell<String>("View", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				Pln03_ResrchModel resrchModel = grid.getSelectionModel().getSelectedItem();
				Pln03_Lookup_ResrchDetail lookupResrch = new Pln03_Lookup_ResrchDetail();
				lookupResrch.open(resrchModel.getResrchId(), new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						retrieve();
					}
				});
			}
		});
		gridBuilder.addCell(properties.actionCell()		,  50, "상세", lookupResrchDetail);

		gridBuilder.addText(properties.docYn()			,  80, "문서등록");
		gridBuilder.addText(properties.aprYn()			,  80, "결재상태");
		gridBuilder.addDate(properties.cfrmDate()       ,  80, "완료일");
		gridBuilder.addText(properties.note()			, 250, "비고");

		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		
		grid.mask("Loading");
		
		GridRetrieveData<Pln03_ResrchModel> service = new GridRetrieveData<Pln03_ResrchModel>(grid.getStore());
		service.addParam("resrchStartDate"	, startDate.getValue());
		service.addParam("resrchCloseDate"	, closeDate.getValue());
		service.addParam("searchText"		, searchText.getText());
		service.addParam("closeYn"			, closeCheckBox.getValue());
		service.addParam("regEmpId"			, LoginUser.getUserId());
		service.addParam("orgId"			, LoginUser.getOrgCodeId());

		service.retrieve("pln.Pln03_Resrch.selectBySearchText", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertRow() {
		Pln03_Lookup_ResrchDetail lookupResrch = new Pln03_Lookup_ResrchDetail();
		lookupResrch.open(null, new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				retrieve();
			}
		});
	}

	@Override
	public void deleteRow() {

		Pln03_ResrchModel delModel = grid.getSelectionModel().getSelectedItem();
		if (delModel.getCfrmDate() != null) {
			new SimpleMessage("확인", "승인이 완료되어 삭제불가합니다.");
			return;
		}

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("확인", "삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					GridDeleteData<Pln03_ResrchModel> service = new GridDeleteData<Pln03_ResrchModel>();
					service.delete(grid.getStore(), delModel, "pln.Pln03_Resrch.delete");
					break;
				case NO:
				default:
					break;
				}
			}

		});
		msgBox.setWidth(300);
		msgBox.show();
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
	}
}
