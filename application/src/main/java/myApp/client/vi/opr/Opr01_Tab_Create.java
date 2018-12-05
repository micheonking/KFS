package myApp.client.vi.opr;

import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.host.fetch.Request;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.opr.model.Opr01_CreateModelProperties;

public class Opr01_Tab_Create extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private TextField 		searchYear  	 = new TextField();
	private TextField 		searchTitle 	 = new TextField();
	private ComboBoxField 	termCdComboBox 	 = new ComboBoxField("TermCodeOpr","%", "전체");
	private CheckBox 		progressCheckBox = new CheckBox();
	private CheckBox 		closeCheckBox 	 = new CheckBox();
	
	private Grid<Opr01_CreateModel> grid = this.buildGrid();

	private Opr02_Tabpage_Fund fundGrid = new Opr02_Tabpage_Fund();
	private Opr03_Tabpage_Reg  regGrid  = new Opr03_Tabpage_Reg();
	
	public Opr01_Tab_Create() {
		open();
	}

	public void open() {

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

		searchBarBuilder.addRetrieveButton();

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		
		vlc.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 50, new Margins(0,0,0,0)));
		vlc.add(grid, new VerticalLayoutData(1,1));
		this.grid.addRowClickHandler(new RowClickHandler() {
			@Override
			public void onRowClick(RowClickEvent event) {
				retrievePage();
			}
		});

		BorderLayoutData northLayoutData = new BorderLayoutData();
		northLayoutData.setSplit(true);
		northLayoutData.setMargins(new Margins(0,0,2,0));
		northLayoutData.setSize(300);
		northLayoutData.setMaxSize(750);
		this.setNorthWidget(vlc,northLayoutData);

		BorderLayoutData westLayoutData = new BorderLayoutData();
		westLayoutData.setSplit(true);
		westLayoutData.setSize(465);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(0,2,0,0));
		this.setWestWidget(this.fundGrid, westLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData();
		centerLayoutData.setSplit(true);

		centerLayoutData.setMargins(new Margins(0,0,0,0));
		this.setCenterWidget(this.regGrid, centerLayoutData);
		
		retrieve();
	}

	private Grid<Opr01_CreateModel> buildGrid() {

		Opr01_CreateModelProperties properties = GWT.create(Opr01_CreateModelProperties.class);
		GridBuilder<Opr01_CreateModel> gridBuilder = new GridBuilder<Opr01_CreateModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	  (properties.title()		, 200, "제목"	 	);
		gridBuilder.addText	  (properties.year()		,  50, "년도"	 	);
		gridBuilder.addText	  (properties.termNm()		, 100, "기간구분"	);
		gridBuilder.addDate	  (properties.startDate()	, 100, "시작일"	);
		gridBuilder.addDate	  (properties.dueDate()	 	, 100, "완료예정일");
		gridBuilder.addText	  (properties.korName()	 	, 100, "담당자"	);
//		gridBuilder.addText	  (properties.positionName(), 100, "직위" 	);
		gridBuilder.addText	  (properties.orgName()	 	, 100, "부서"	 	);
		gridBuilder.addDate	  (properties.closeDate()	, 100, "등록완료일");
		gridBuilder.addText	  (properties.note()		, 300, "비고"	 	);

		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		grid.mask("Loading");
		GridRetrieveData<Opr01_CreateModel> service = new GridRetrieveData<Opr01_CreateModel>(grid.getStore());
		service.addParam("companyId" , LoginUser.getCompanyId());
		service.addParam("empId"	 , LoginUser.getUserId());
		service.addParam("year"		 , searchYear.getText());
		service.addParam("termCd"	 , termCdComboBox.getCode());
		service.addParam("title"	 , searchTitle.getText());
		service.addParam("progressYn", progressCheckBox.getValue());
		service.addParam("closeYn"	 , closeCheckBox.getValue());
		
		service.retrieve("opr.Opr01_Create.selectByTitle1", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
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

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 보고서를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					List<Opr01_CreateModel> delList = grid.getSelectionModel().getSelectedItems() ;
					GridDeleteData<Opr01_CreateModel> service = new GridDeleteData<Opr01_CreateModel>();
					service.delete(grid.getStore(), delList, "opr.Opr01_Create.delete");
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
}
