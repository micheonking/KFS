package myApp.client.vi.emp;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.Apr10_Move_OrgCode;
import myApp.client.vi.emp.model.Emp04_AddTitleModel;
import myApp.client.vi.emp.model.Emp04_AddTitleModelProperties;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Emp04_Lookup_AddTitle extends Window {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Emp04_AddTitleModelProperties properties = GWT.create(Emp04_AddTitleModelProperties.class);
	public  Grid<Emp04_AddTitleModel> grid = this.buildGrid();
	private InterfaceCallbackResult callbackResult;

	private Long empId = null;
	
	private TextButton deleteButton = new TextButton("삭제");
	private TextButton insertButton = new TextButton("입력");
	private TextButton updateButton = new TextButton("저장");
	private TextButton closeButton = new TextButton("닫기");
	
	
	
	public Emp04_Lookup_AddTitle(Long empId) {
		this.empId = empId;
		this.setModal(true);
		this.setHeading("겸직발령");
		this.setResizable(false);
		this.setPixelSize(710, 490);
		
		deleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				deleteRow(); 
			}
		});
		insertButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertRow(); 
			}
		});
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		});
		closeButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				callbackResult.execute(null);
				hide(); 
			}
		});
		
		this.addButton(deleteButton); //삭제버튼
		this.addButton(insertButton); //입력버튼
		this.addButton(updateButton); //수정버튼
		this.addButton(closeButton); //닫기버튼
		this.setButtonAlign(BoxLayoutPack.CENTER);

		this.add(this.grid, new VerticalLayoutData(1, 1));//그리드 표현
	}
	
	public void open(InterfaceCallbackResult callbackResult) {
		this.callbackResult = callbackResult;
		this.show();
		retrieve();
		//retrieveEmpGrid();
	}
	
	public Grid<Emp04_AddTitleModel> buildGrid(){
		
		GridBuilder<Emp04_AddTitleModel> gridBuilder = new GridBuilder<Emp04_AddTitleModel>(properties.keyId());  
		
		gridBuilder.setChecked(SelectionMode.SINGLE);
		LookupTriggerField lookupOrgInfo = new LookupTriggerField();
		lookupOrgInfo.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				findOrg();
			}
		});
		lookupOrgInfo.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.preventDefault();
			}
		});
		gridBuilder.addText(properties.orgName(),	250,	"부서",		lookupOrgInfo);
		
		final ComboBoxField empTitleComboBox = new ComboBoxField("EmpTitleCode");
		empTitleComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				Emp04_AddTitleModel data = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(data).addChange(properties.titleCode(), empTitleComboBox.getCode());
			}
		});
		gridBuilder.addText(properties.titleName(), 100, "직책명", empTitleComboBox);
		gridBuilder.addDate(properties.startDate(), 90,		"시작일", new DateField() );
		gridBuilder.addDate(properties.closeDate(), 90,		"종료일", new DateField() );

		return gridBuilder.getGrid(); 
	}

	public void retrieve() {
		GridRetrieveData<Emp04_AddTitleModel> service = new GridRetrieveData<Emp04_AddTitleModel>(this.grid.getStore());
		service.addParam("empId", empId);
		service.retrieve("emp.Emp04_AddTitle.selectByEmpId", new InterfaceCallback() {
			
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
	
	private void findOrg() {
		Apr10_Move_OrgCode lookupOrg = new Apr10_Move_OrgCode();
		lookupOrg.open(LoginUser.getToday(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Org01_CodeModel orgModel = (Org01_CodeModel)result;
				Emp04_AddTitleModel addTitleModel = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(addTitleModel).addChange(properties.orgName(),orgModel.getOrgInfoModel().getKorName());
				grid.getStore().getRecord(addTitleModel).addChange(properties.orgCodeId(),orgModel.getCodeId());
			}
		});
	}
	
	public void update() {
		/*
		List<Emp04_AddTitleModel> tempList = this.grid.getStore().getAll();
		for(int i = 0; i < tempList.size(); i++) {
			Emp04_AddTitleModel tempModel = tempList.get(i);
			if("".equals(tempModel.getTitleName())) {
				new SimpleMessage("직책을 선택해주세요");
				return;
			}
			Info.display(tempModel.getStartDate().getTime()+"","");
			if(0L == tempModel.getStartDate().getTime()) {
				new SimpleMessage("시작일을 선택해주세요");
				return;
			}
			if(null == tempModel.getCloseDate()) {
				new SimpleMessage("종료일을 선택해주세요");
				return;
			}
			if("".equals(tempModel.getOrgName())) {
				new SimpleMessage("부서를 선택해주세요");
				return;
			}
			
		}
		*/
		for(Store<Emp04_AddTitleModel>.Record modified : grid.getStore().getModifiedRecords()) {

			Emp04_AddTitleModel chkModel = modified.getModel(); 

			for (Change<Emp04_AddTitleModel, ?> changes : modified.getChanges()) {
				// 컬럼별로 변경된 자료를 적용한다. 
				changes.modify(chkModel);
			}
			
			if ((chkModel.getTitleName() == null) || (chkModel.getTitleName().equals(""))) {
				new SimpleMessage("입력확인","직책을 선택해주세요");
				return;
			}
			if ((chkModel.getStartDate() == null) || (chkModel.getStartDate().equals(""))) {
				new SimpleMessage("입력확인","시작일을 선택해주세요");
				return;
			}
			if ((chkModel.getCloseDate() == null) || (chkModel.getCloseDate().equals(""))) {
				new SimpleMessage("입력확인","종료일을 선택해주세요");
				return;
			}
			if (chkModel.getOrgName() == null || chkModel.getOrgName().equals("")) {
				new SimpleMessage("입력확인","부서를 선택해주세요");
				return;
			}
		}
		
		GridUpdate<Emp04_AddTitleModel> service = new GridUpdate<Emp04_AddTitleModel>();
		/*
		service.update(this.grid.getStore(), "emp.Emp04_AddTitle.update", new InterfaceCallback() {
			@Override
			public void execute() {
				retrieve();
			}
		});
		*/
		service.update(this.grid.getStore(), "emp.Emp04_AddTitle.update", new InterfaceCallbackResult() {
			
			@Override
			public void execute(Object result) {
				ServiceResult a = (ServiceResult) result;
				
				if(a.getStatus() > 0) {
				} else {
					new SimpleMessage(a.getMessage());
				}
			}
		});
	}
	
	public void insertRow() {
		GridInsertRow<Emp04_AddTitleModel> service = new GridInsertRow<Emp04_AddTitleModel>();
		Emp04_AddTitleModel classTreeModel = new Emp04_AddTitleModel();
		classTreeModel.setEmpId(empId);
		/*
		classTreeModel.setCompanyId(LoginUser.getCompanyId());
		classTreeModel.setParentTreeId(this.classTreeId);
		classTreeModel.setTypeCode("T"); // 문서구분은 'T' 이다.
		*/
		service.insertRow(grid, classTreeModel);
	}
	
	public void deleteRow() {
		GridDeleteData<Emp04_AddTitleModel> service = new GridDeleteData<Emp04_AddTitleModel>();
		List<Emp04_AddTitleModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(this.grid.getStore(), checkedList, "emp.Emp04_AddTitle.delete");
	}
}
