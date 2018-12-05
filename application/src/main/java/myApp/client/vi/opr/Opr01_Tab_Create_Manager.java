package myApp.client.vi.opr;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.Apr01_Edit_ApprDoc;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.opr.model.Opr01_CreateModelProperties;
import myApp.client.vi.pln.model.Pln02_PlanModel;

public class Opr01_Tab_Create_Manager extends BorderLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {
	
	private TextField 		searchYear  	 = new TextField();
	private TextField 		searchTitle 	 = new TextField();
	private ComboBoxField 	termCdComboBox 	 = new ComboBoxField("TermCodeOpr","%", "전체");
	private CheckBox 		progressCheckBox = new CheckBox();
	private CheckBox 		closeCheckBox 	 = new CheckBox();
	private CheckBox 		aprCheckBox 	 = new CheckBox();
	
	private String			actionName;
	private int				rownum;
	
	private Grid<Opr01_CreateModel> grid = this.buildGrid();

	private Opr02_Tabpage_Fund_Manager fundGrid = new Opr02_Tabpage_Fund_Manager();
	private Opr03_Tabpage_Reg_Manager  regGrid  = new Opr03_Tabpage_Reg_Manager();
	
	public Opr01_Tab_Create_Manager() {
		
		this.rownum = 0;
		
		//SearchBar SET!!
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		searchYear.addValidator(new MaxLengthValidator(4));
		searchYear.setText(LoginUser.getYear());
		searchBarBuilder.addTextField(searchYear    , "년도"   , 100, 30, true);
		searchBarBuilder.addComboBox (termCdComboBox, "기간구분", 150, 60      );
		termCdComboBox.setValue("전체");
		searchBarBuilder.addTextField(searchTitle   , "제목"   , 300, 30, true);

		progressCheckBox.setBoxLabel("진행중");
		progressCheckBox.setValue(true);
		searchBarBuilder.addCheckBox(progressCheckBox, 70, 0);

		closeCheckBox.setBoxLabel("등록완료");
		closeCheckBox.setValue(true);
		searchBarBuilder.addCheckBox(closeCheckBox, 80, 0);

		LabelToolItem blnk = new LabelToolItem("");
		blnk.setWidth(5);
		searchBarBuilder.getSearchBar().add(blnk);

		LabelToolItem title = new LabelToolItem("결재완료:");
		title.setWidth(50);
		searchBarBuilder.getSearchBar().add(title);
		aprCheckBox.setBoxLabel("완료건 포함");
		aprCheckBox.setValue(false);
		searchBarBuilder.addCheckBox(aprCheckBox, 100, 0);

		searchBarBuilder.addRetrieveButton();
		searchBarBuilder.addDeleteButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		
		vlc.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 50, new Margins(0,0,0,0)));
		vlc.add(grid, new VerticalLayoutData(1,1));
		grid.addRowClickHandler(new RowClickHandler() {
			@Override
			public void onRowClick(RowClickEvent event) {
				rownum = event.getRowIndex();
				retrievePage();
			}
		});

		BorderLayoutData northLayoutData = new BorderLayoutData();
		northLayoutData.setSplit(true);
		northLayoutData.setMargins(new Margins(0,0,2,0));
		northLayoutData.setSize(400);
		northLayoutData.setMaxSize(700);
		this.setNorthWidget(vlc,northLayoutData);

		BorderLayoutData westLayoutData = new BorderLayoutData();
		westLayoutData.setSplit(true);
		westLayoutData.setSize(800);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(0,2,0,0));
		this.setWestWidget(this.fundGrid, westLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData();
		centerLayoutData.setSplit(true);
		centerLayoutData.setSize(400);
		centerLayoutData.setMaxSize(700);

		centerLayoutData.setMargins(new Margins(0,0,0,0));
		this.setCenterWidget(this.regGrid, centerLayoutData);
		
		retrieve();
	}

	private Grid<Opr01_CreateModel> buildGrid() {

		Opr01_CreateModelProperties properties = GWT.create(Opr01_CreateModelProperties.class);
		GridBuilder<Opr01_CreateModel> gridBuilder = new GridBuilder<Opr01_CreateModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	  (properties.title()		, 200, "제목"	 	, new TextField());
		gridBuilder.addText	  (properties.year()		,  50, "년도"	 	, new TextField());

		ComboBoxField termCdComboBox = new ComboBoxField("TermCodeOpr");
		termCdComboBox.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
				grid.getSelectionModel().getSelectedItem().setTermCd(termCdComboBox.getCode());
				grid.getView().refresh(true);
			}
		});
		gridBuilder.addText	  (properties.termNm()		, 100, "기간구분"	, termCdComboBox);
		gridBuilder.addDate	  (properties.startDate()	, 100, "시작일"	, new DateField());
		gridBuilder.addDate	  (properties.dueDate()	 	, 100, "완료예정일", new DateField());
		gridBuilder.addText	  (properties.korName()	 	, 100, "담당자"	);
//		gridBuilder.addText	  (properties.positionName(), 100, "직위" 	);
		gridBuilder.addText	  (properties.orgName()	 	, 100, "부서"	 	);
		gridBuilder.addDate	  (properties.closeDate()	, 100, "등록완료일");
		gridBuilder.addText   (properties.aprNm()       ,  80, "결재상태"	);
		gridBuilder.addDate	  (properties.aprDate()		, 100, "결재완료일");

		ActionCell<String> aprButton = new ActionCell<String>("Detail", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				Opr01_CreateModel createModel = grid.getSelectionModel().getSelectedItem();
				String aprNm = createModel.getAprNm().replace(" ", "");
				if ((aprNm == null)||(aprNm == "")||("임시저장".equals(aprNm))) {
					if (LoginUser.getUserId() != createModel.getCreateEmpId()) {
						new SimpleMessage("확인","상신전입니다.");
						return;
					}
					if (createModel.getCloseDate() == null) {
						new SimpleMessage("확인","등록 미완료건은 상신 불가합니다.");
						return;
					}
				}
//				if (aprNm != null) {
//					if (!aprNm.equals("반려")) {
//						new SimpleMessage("확인","대기 및 진행중이거나 이미 완료된건은 상신 불가합니다.");
//						return;
//					}
//				}
				insertAppr(createModel.getCreateId());
			}
		});
		gridBuilder.addCell	  (properties.actionCell()	,  60, "결재요청"	, aprButton);
		gridBuilder.addText	  (properties.note()		, 300, "비고"	 	, new TextField());

//		gridBuilder.addBeforeStartEditHandler(new BeforeStartEditHandler<Opr01_CreateModel>() {
//			@Override
//			public void onBeforeStartEdit(BeforeStartEditEvent<Opr01_CreateModel> event) {
//				Opr01_CreateModel createModel = grid.getSelectionModel().getSelectedItem();
//				String aprNm = createModel.getAprNm().replace(" ", "");
//				if ((aprNm == null)||(aprNm == "")||("임시저장".equals(aprNm))) {
//					event.setCancelled(false);
//				} else {
//					event.setCancelled(true);
//				}
//			}
//		});
		
		return gridBuilder.getGrid();
	}
	
	private void insertAppr(Long createId) {
		actionName = "insertAppr";
		ServiceRequest request = new ServiceRequest("opr.Opr01_Create.insertAppr");
		request.putLongParam("createId"	, createId	);
		request.putLongParam("empId"	, LoginUser.getUserId());
		request.putLongParam("orgId"	, LoginUser.getOrgCodeId());
		request.putLongParam("companyId", LoginUser.getCompanyId());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	@Override
	public void retrieve() {

		this.grid.mask("Loading");
		
		GridRetrieveData<Opr01_CreateModel> service = new GridRetrieveData<Opr01_CreateModel>(grid.getStore());

		service.addParam("companyId" , LoginUser.getCompanyId());
		service.addParam("userId"	 , LoginUser.getUserId());
		service.addParam("year"		 , searchYear.getText());
		service.addParam("termCd"	 , termCdComboBox.getCode());
		service.addParam("title"	 , searchTitle.getText());
		service.addParam("progressYn", progressCheckBox.getValue());
		service.addParam("closeYn"	 , closeCheckBox.getValue());
		service.addParam("aprYn"	 , aprCheckBox.getValue());
		
		service.retrieve("opr.Opr01_Create.selectByTitle", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(rownum, false);
					retrievePage();
				}	
			}
		});
	}

	@Override
	public void update(){
		if (chkPreUpdate() < 0) {
			return;
		}
		GridUpdate<Opr01_CreateModel> service = new GridUpdate<Opr01_CreateModel>(); 
		service.update(grid.getStore(), "opr.Opr01_Create.update"); 
	}

	private int chkPreUpdate() {
		
		for(Store<Opr01_CreateModel>.Record modified : grid.getStore().getModifiedRecords()) {

			Opr01_CreateModel chkModel = modified.getModel(); 

			for (Change<Opr01_CreateModel, ?> changes : modified.getChanges()) {
				// 컬럼별로 변경된 자료를 적용한다. 
				changes.modify(chkModel);
			}
			
			if ((chkModel.getTitle() == null) || (chkModel.getTitle().equals(""))) {
				new SimpleMessage("입력확인","제목을 입력하여 주십시오.");
				return -1;
			}
			if ((chkModel.getYear() == null) || (chkModel.getYear().equals(""))) {
				new SimpleMessage("입력확인","년도를 입력하여 주십시오.");
				return -1;
			}
			if ((chkModel.getTermCd() == null) || (chkModel.getTermCd().equals(""))) {
				new SimpleMessage("입력확인","기간구분을 선택하여 주십시오.");
				return -1;
			}
			if (chkModel.getStartDate() == null) {
				new SimpleMessage("입력확인","시작일을 입력하여 주십시오.");
				return -1;
			}
		}
		return 1;
	}

	@Override
	public void insertRow(){

		Opr01_CreateModel insertModel = new Opr01_CreateModel();

		insertModel.setCompanyId(LoginUser.getCompanyId());						//회사ID
		insertModel.setYear(LoginUser.getYear());								//년도
		insertModel.setStartDate(LoginUser.getToday());							//시작일
		insertModel.setCreateEmpId(LoginUser.getUserId());						//담당자ID(등록자)
		insertModel.getEmpInfoModel().setKorName(LoginUser.getUserName());		//담당자명(등록자)
		insertModel.getEmpInfoModel().setPositionCode(LoginUser.getPosCode());	//직위코드
		insertModel.getEmpInfoModel().setPositionName(LoginUser.getPosName());	//직위명
		insertModel.getEmpInfoModel().setOrgCode(LoginUser.getOrgCode());		//부서코드
		insertModel.getEmpInfoModel().setOrgName(LoginUser.getOrgKorName());	//부서명

		GridInsertRow<Opr01_CreateModel> service = new GridInsertRow<Opr01_CreateModel>(); 
		service.insertRow(grid, insertModel);
	}

	@Override
	public void deleteRow(){

		if (grid.getSelectionModel().getSelectedItems().size() == 0) {
			new SimpleMessage("삭제확인","선택된 보고서가 없습니다.");
			return;
		}

		Opr01_CreateModel delModel = grid.getSelectionModel().getSelectedItem();
		switch (delModel.getAprNm()) {
			case "상신":
			case "결재중":
			case "완료":
			case "반려":
				new SimpleMessage("삭제확인","상신중 또는 완료된 보고서는 삭제불가합니다.");
				return;
		}

		if ((delModel.getCreateEmpId()== null) || (delModel.getCreateEmpId() != LoginUser.getUserId())) {
			new SimpleMessage("확인", "담당자가 아닌경우 삭제불가합니다.");
			return;
		}

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 보고서를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					GridDeleteData<Opr01_CreateModel> service = new GridDeleteData<Opr01_CreateModel>();
					service.delete(grid.getStore(), delModel, "opr.Opr01_Create.delete");
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

	protected void retrievePage() {

		Opr01_CreateModel createModel = this.grid.getSelectionModel().getSelectedItem();
		long createId = createModel.getCreateId();

		fundGrid.retrieve(createId);	//연관펀드 조회
		regGrid.retrieve (createId);	//보고서작성순서 조회
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if (actionName.equals("insertAppr")) {
			if (result.getStatus() > 0) {
				Apr01_ApprModel aprModel = (Apr01_ApprModel) result.getResult(0);
				Apr01_Edit_ApprDoc lookupApr = new Apr01_Edit_ApprDoc();
				lookupApr.retrieveApproval(aprModel, false, new InterfaceCallback() {
					@Override
					public void execute() {
						retrieve();
					}
				});
			}
		}
	}

}
