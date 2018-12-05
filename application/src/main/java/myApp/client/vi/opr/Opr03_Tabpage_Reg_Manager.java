package myApp.client.vi.opr;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.opr.model.Opr03_RegModel;
import myApp.client.vi.opr.model.Opr03_RegModelProperties;

public class Opr03_Tabpage_Reg_Manager extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	Opr03_RegModelProperties properties = GWT.create(Opr03_RegModelProperties.class);

	private Grid<Opr03_RegModel> grid = this.buildGrid();

	private Long createId;
	
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	public Opr03_Tabpage_Reg_Manager() {

		LabelToolItem title = new LabelToolItem("▶ 보고서 작성 순서");
		title.setWidth(125);

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.getSearchBar().add(title);
		searchBarBuilder.addDeleteButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(-1, -1, new Margins(0,0,0,0)));
		this.add(setGrid(), new VerticalLayoutData(1, 1, new Margins(0,0,0,0)));

	}

	private VerticalLayoutContainer setGrid() {
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,1));

		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {

				if (("".equals(fileUpdownForm.getFileName())) || (fileUpdownForm.getFileName() == null)) {
					return;
				}

				Opr03_RegModel regModel = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(regModel).addChange(properties.docNm(), fileUpdownForm.getFileName());
				grid.getStore().getRecord(regModel).addChange(properties.regDate(), LoginUser.getToday());

				String actionUrl = "FileUpload?fileType=file&parentId=" + regModel.getRegId();
				fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
					@Override
					public void execute() {
						update();
					}
				});
			}
		});
		
		return vlc;
	}

	private Grid<Opr03_RegModel> buildGrid() {

		GridBuilder<Opr03_RegModel> gridBuilder = new GridBuilder<Opr03_RegModel>(properties.keyId());

		//담당자 LookUp
		LookupTriggerField lookupEmpInfo = new LookupTriggerField();
		lookupEmpInfo.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				findEmp();
			}
		}); 

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	  (properties.seq()		 		,  40, "순서", new TextField());
		gridBuilder.addText	  (properties.korName()	 		,  70, "담당자", lookupEmpInfo);
//		gridBuilder.addText	  (properties.positionName()	,  50, "직위");
		gridBuilder.addText	  (properties.orgName()	 		, 120, "부서");
		gridBuilder.addText   (properties.docNm()			, 150, "운용보고서 첨부파일");

		ActionCell<String> fileUpButton = new ActionCell<String>("up", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				fileUpdownForm.click();
			}
		});
		gridBuilder.addCell	  (properties.actionCellUp()	,  60, "파일등록", fileUpButton);

		ActionCell<String> fileDownButton = new ActionCell<String>("down", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				String actionUrl = "FileDownload?fileType=file&fileId=" + grid.getSelectionModel().getSelectedItem().getFileId();
				fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
					@Override
					public void execute() {
					}
				});
			}
		});
		gridBuilder.addCell	  (properties.actionCellDown()	,  70, "파일받기", fileDownButton);
		
		gridBuilder.addDate	  (properties.regDate()	 		,  80, "등록일", new DateField());
		gridBuilder.addBoolean(properties.closeYnFlag() 	,  50, "완료");
		gridBuilder.addText	  (properties.note()			, 100, "비고", new TextField());

		return gridBuilder.getGrid();
	}

	private void findEmp() {

		Emp00_Lookup_EmpInfo lookupEmp = new Emp00_Lookup_EmpInfo(); 
		lookupEmp.open(LoginUser.getToday(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {

				Emp00_InfoModel empInfoModel = (Emp00_InfoModel)result;
				Opr03_RegModel  regModel     = grid.getSelectionModel().getSelectedItem();

				grid.getStore().getRecord(regModel).addChange(properties.regEmpId()		, empInfoModel.getEmpId());
				grid.getStore().getRecord(regModel).addChange(properties.korName()		, empInfoModel.getKorName());
				grid.getStore().getRecord(regModel).addChange(properties.positionCode()	, empInfoModel.getPositionCode());
				grid.getStore().getRecord(regModel).addChange(properties.positionName()	, empInfoModel.getPositionName());
				grid.getStore().getRecord(regModel).addChange(properties.orgCode()		, empInfoModel.getOrgCode());
				grid.getStore().getRecord(regModel).addChange(properties.orgName()		, empInfoModel.getOrgName());
			}
		});
	}

	public void retrieve(long createId) {

		this.createId = createId;
		
		GridRetrieveData<Opr03_RegModel> service = new GridRetrieveData<Opr03_RegModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("createId" , this.createId);
		service.addParam("empId", LoginUser.getUserId());
		service.retrieve("opr.Opr03_Reg.selectByCreateId");
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		
		if (chkPreUpdate() < 0) {
			return;
		}
		
		GridUpdate<Opr03_RegModel> service = new GridUpdate<Opr03_RegModel>(); 
		service.update(grid.getStore(), "opr.Opr03_Reg.update"); 
	}

	private int chkPreUpdate() {

		for(Store<Opr03_RegModel>.Record modified : grid.getStore().getModifiedRecords()) {

			Opr03_RegModel chkModel = modified.getModel(); 

			for (Change<Opr03_RegModel, ?> changes : modified.getChanges()) {
				// 컬럼별로 변경된 자료를 적용한다. 
				changes.modify(chkModel);
			}

			if ((chkModel.getSeq() == null) || (chkModel.getSeq().equals(""))) {
				new SimpleMessage("입력확인","순서를 입력하여 주십시오.");
				return -1;
			}
			if (chkModel.getRegEmpId() == null) {
				new SimpleMessage("입력확인","담당자를 입력하여 주십시오.");
				return -1;
			}
		}
		return 1;
	}

	@Override
	public void insertRow() {
		
		if (this.createId == null) {
			new SimpleMessage("확인","운용보고서 선택 후 입력가능합니다.");
			return;
		}
		
		Opr03_RegModel insertModel = new Opr03_RegModel();
		insertModel.setCreateId(this.createId);
		
		GridInsertRow<Opr03_RegModel> service = new GridInsertRow<Opr03_RegModel>(); 
		service.insertRow(grid, insertModel);
	}

	@Override
	public void deleteRow() {
		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 담당자를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					List<Opr03_RegModel> delList = grid.getSelectionModel().getSelectedItems() ;
					GridDeleteData<Opr03_RegModel> service = new GridDeleteData<Opr03_RegModel>();
					service.delete(grid.getStore(), delList, "opr.Opr03_Reg.delete");
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

}
