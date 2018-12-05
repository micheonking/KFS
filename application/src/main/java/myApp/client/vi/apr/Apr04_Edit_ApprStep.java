package myApp.client.vi.apr;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.grid.GridBuilder;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModelProperties;
import myApp.client.vi.apr.model.Apr07_AltApprModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.emp.model.Emp00_InfoModelProperties;
import myApp.client.vi.org.Org01_Tree_OrgCode;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Apr04_Edit_ApprStep extends Window {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private Grid<Emp00_InfoModel> empGrid = this.buildEmpGrid();
	private Grid<Apr04_ApprStepModel> apprStepGrid = this.buildApprStepGrid();
	private Org01_Tree_OrgCode buildTree = new Org01_Tree_OrgCode();
	private Long apprId = (long)0;
	private Long stampEmpId = null;
	private Long stampId = null;
	private Long complianceOfficer = null;
	
	public void setStampEmpId(Long stampEmpId) {
		this.stampEmpId = stampEmpId;
	}
	
	public void setStampId(Long stampId) {
		this.stampId = stampId;
	}
	
	public Apr04_Edit_ApprStep(Grid<Apr04_ApprStepModel> paramApprStepGrid, Long apprId) {
		
        this.apprId = apprId;
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.setSize("960", "600");
		this.setHeading("결재선 설정");
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());

		// org tree setting 
		Tree<Org01_CodeModel, String> orgTree = buildTree.getTree();
		orgTree.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Org01_CodeModel>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Org01_CodeModel> event) {
				Org01_CodeModel orgCodeModel = event.getSource().getSelectedItem(); // event.getSelection().get(0);
				if (orgCodeModel != null) {
					GridRetrieveData<Emp00_InfoModel> service = new GridRetrieveData<Emp00_InfoModel>(empGrid.getStore());
					service.addParam("orgCodeId", orgCodeModel.getCodeId());
					service.retrieve("emp.Emp00_Info.selectByOrgCodeId");
				}
			}
		});
		
		VerticalLayoutContainer treeContainer = new VerticalLayoutContainer();
		treeContainer.add(orgTree, new VerticalLayoutData(1, 1, new Margins(10)));
		treeContainer.setScrollMode(ScrollMode.AUTOY);

		ContentPanel treePanel = new ContentPanel();
		treePanel.setHeaderVisible(false);
		treePanel.add(treeContainer);
		
		BorderLayoutContainer blc = new BorderLayoutContainer();
		
		BorderLayoutData westLayoutData = new BorderLayoutData(240);
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(0, 1, 0, 0));
		blc.setWestWidget(treePanel, westLayoutData); // 조직트리 보이기. 
		
		VerticalLayoutContainer empContainer = new VerticalLayoutContainer();
		empContainer.add(new LabelToolItem("▶ 결재자 조회"), new VerticalLayoutData(1, 26, new Margins(10, 0, 0, 10)));
		empContainer.add(this.empGrid, new VerticalLayoutData(1, 220, new Margins(10)));
		
		empContainer.add(new LabelToolItem("▶ 결재선 등록"), new VerticalLayoutData(1, 26, new Margins(10, 0, 0, 10)));
		empContainer.add(this.apprStepGrid, new VerticalLayoutData(1, 240, new Margins(10)));
		
		ContentPanel empPanel = new ContentPanel();
		empPanel.setHeaderVisible(false);
		empPanel.add(empContainer);
		blc.setCenterWidget(empPanel);
		this.add(blc);
		
		// button bar setting 
		TextButton confirmButton = new TextButton("확인");
		confirmButton.setWidth(60);
		confirmButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				paramApprStepGrid.getStore().clear();
				for (Apr04_ApprStepModel data : apprStepGrid.getStore().getAll()) {
					paramApprStepGrid.getStore().add(data);
				}
				hide();
			}
		});

		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});

		this.buttonBar.add(confirmButton);
		this.buttonBar.add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);

		for(Apr04_ApprStepModel apprStepModel : paramApprStepGrid.getStore().getAll()) {
			this.apprStepGrid.getStore().add(apprStepModel); 	
		}
		
		buildTree.retrieve(new Date()); 
		selectComplianceOfficer();
	}

	public Apr04_Edit_ApprStep() {
		// TODO Auto-generated constructor stub
	}

	public Grid<Emp00_InfoModel> buildEmpGrid() {

		Emp00_InfoModelProperties properties = GWT.create(Emp00_InfoModelProperties.class);
		GridBuilder<Emp00_InfoModel> gridBuilder = new GridBuilder<Emp00_InfoModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.MULTI);

		gridBuilder.addText(properties.orgName(), 200, "부서명");
		gridBuilder.addText(properties.titleName(), 80, "직책");
		//gridBuilder.addText(properties.positionName(), 60, "직위");
		gridBuilder.addText(properties.korName(), 80, "성명");
		
		ActionCell<String> approveCell = new ActionCell<String>("결재", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				insertApprovalEmp("10"); 
			}
		});
		gridBuilder.addCell(properties.buttonCell(), 80, "결재", approveCell); //, new TextField()) ;

		ActionCell<String> consentButton = new ActionCell<String>("합의", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				insertApprovalEmp("20"); 
			}
		});
		gridBuilder.addCell(properties.buttonCell(), 80, "합의", consentButton); //, new TextField()) ;

		ActionCell<String> referenceButton = new ActionCell<String>("참조", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				insertApprovalEmp("30"); 
			}
		});
		gridBuilder.addCell(properties.buttonCell(), 80, "참조", referenceButton); //, new TextField()) ;
		
		return gridBuilder.getGrid();
	}

	public Grid<Apr04_ApprStepModel> buildApprStepGrid() {

		Apr04_ApprStepModelProperties properties = GWT.create(Apr04_ApprStepModelProperties.class);
		GridBuilder<Apr04_ApprStepModel> gridBuilder = new GridBuilder<Apr04_ApprStepModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.stepTypeName(), 40, "구분");
		gridBuilder.addText(properties.orgName(), 150, "부서명");
		gridBuilder.addText(properties.titleName(), 80, "직책");
		//gridBuilder.addText(properties.positionName(), 60, "직위");
		//gridBuilder.addText(properties.seq(), 60, "SEQ");
		//gridBuilder.addLong(properties.stepEmpId(), 60, "EmpId");
		gridBuilder.addText(properties.korName(), 80, "성명");

		ActionCell<String> upButton = new ActionCell<String>("▲Up", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				changeRow("up"); 
			}
		});
		gridBuilder.addCell(properties.actionCell(), 70, "위로", upButton); //, new TextField()) ;

		ActionCell<String> downButton = new ActionCell<String>("▼Down", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				changeRow("down"); 
			}
		});
		gridBuilder.addCell(properties.actionCell(), 70, "아래로", downButton); //, new TextField()) ;

		ActionCell<String> deleteButton = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				changeRow("delete"); 
			}
		});
		gridBuilder.addCell(properties.actionCell(), 70, "삭제", deleteButton); //, new TextField()) ;
		
		return gridBuilder.getGrid();
	}

	public void update() {
	}

	public void insertApprovalEmp(String stepTypeCode) {
		List<Emp00_InfoModel> list = empGrid.getSelectionModel().getSelectedItems();
		
		for(int i = 0; i < list.size(); i++) {
			int flag = insertApprovalEmp(stepTypeCode, list.get(i));
			if(flag == -1) {
				
				break;
			} else {
			}
		}
	}
	
	public int insertApprovalEmp(String stepTypeCode, Emp00_InfoModel empInfoModel) {
		//Emp00_InfoModel empInfoModel = empGrid.getSelectionModel().getSelectedItem(); 
		int seq = 2; 
		
		for(int i = 0; i < apprStepGrid.getStore().getAll().size(); i++) {
			Apr04_ApprStepModel apprStepModel = apprStepGrid.getStore().get(i);
			if(empInfoModel.getEmpId().equals(apprStepModel.getStepEmpId())) {
				new SimpleMessage("이미 결재 설정이 되어 있는 사람입니다.");
				return -1; 
			}
			else {
				if(stepTypeCode.equals(apprStepModel.getStepTypeCode())){
					seq++ ; // 같은 구분이 있으면 하나씩 늘린다.  
				}
			}
//			logger.info(i + "번째  getStepEmpId : " + apprStepModel.getStepEmpId());
//			logger.info(i + "번째 getEmpInfoModel, getEmpId : " + apprStepModel.getEmpInfoModel().getEmpId());
//			logger.info(i + "번째 getEmpId : " + empInfoModel.getEmpId());
		}
		
		Apr04_ApprStepModel apprStepModel = new Apr04_ApprStepModel(); 
		apprStepModel.setStepEmpId(empInfoModel.getEmpId());

		apprStepModel.setApprId(this.apprId);
		apprStepModel.setEmpInfoModel(empInfoModel);
		
		apprStepModel.setStepTypeCode(stepTypeCode);

		if("10".equals(stepTypeCode)) {
			apprStepModel.setStepTypeName("결재");
			apprStepModel.setStepStatusCode("10");
			apprStepModel.setStepStatusName("대기");
		}
		else if("20".equals(stepTypeCode)) {
			apprStepModel.setStepTypeName("합의");
			apprStepModel.setStepStatusCode("10");
			apprStepModel.setStepStatusName("대기");
		}
		else if("30".equals(stepTypeCode)) {
			apprStepModel.setStepTypeName("참조");
			apprStepModel.setStepStatusCode("");
			apprStepModel.setStepStatusName("");
		}
		else {
			apprStepModel.setStepTypeName("????");
		}
		
		apprStepModel.setSeq((seq * 10) + "");
		
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("apr.Apr04_ApprStep.changeByApproval");
		request.addParam("empId", empInfoModel.getEmpId());
		
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				if(result.getResult() != null) {
					if(result.getResult().size() > 0) {
						Apr07_AltApprModel altApprModel = (Apr07_AltApprModel) result.getResult(0);
						String str = "";
//						str += altApprModel.getTargetPositionName() + " ";
						str += altApprModel.getTargetTitleName() + " ";
						str += altApprModel.getTargetEmpName() + "(";
//						str += altApprModel.getAltPositionName() + " ";
						str += altApprModel.getAltTitleName() + " ";
						str += altApprModel.getAltEmpName() + ")";
						apprStepModel.setStepApprName(str);
					} else {
//						apprStepModel.setStepApprName(empInfoModel.getPositionName() + " " + empInfoModel.getKorName());
						apprStepModel.setStepApprName(empInfoModel.getTitleName() + " " + empInfoModel.getKorName());
					}
				} else {
//					apprStepModel.setStepApprName(empInfoModel.getPositionName() + " " + empInfoModel.getKorName());
					apprStepModel.setStepApprName(empInfoModel.getTitleName() + " " + empInfoModel.getKorName());
				}
				GridInsertRow<Apr04_ApprStepModel> service = new GridInsertRow<Apr04_ApprStepModel>();
				service.insertRow(apprStepGrid, apprStepModel, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						getSeq(apprStepGrid);//결재순번 재정렬
						apprStepGrid.getView().refresh(true);
					}
				}) ;
			}
		});
		
		return 0;
		/*
		GridInsertRow<Apr04_ApprStepModel> service = new GridInsertRow<Apr04_ApprStepModel>();
		service.insertRow(apprStepGrid, apprStepModel) ; 
		*/
	}

	public void changeRow(String actionCode) {
		
		Apr04_ApprStepModel record = this.apprStepGrid.getSelectionModel().getSelectedItem();

		int rowIndex = this.apprStepGrid.getStore().indexOf(record);
		
		if("00".equals(record.getStepTypeCode())) {
			new SimpleMessage("상신자는 수정할 수 없습니다");
			return;
		}
		
		if ("down".equals(actionCode)) {
			if (rowIndex + 1 < this.apprStepGrid.getStore().size()) {
				this.apprStepGrid.getStore().remove(record);
				this.apprStepGrid.getStore().add(rowIndex + 1, record);
				rowIndex = rowIndex + 1;
			}
		} 
		else if ("up".equals(actionCode)) {
			if (rowIndex > 1) {
				this.apprStepGrid.getStore().remove(record);
				this.apprStepGrid.getStore().add(rowIndex - 1, record);
				rowIndex = rowIndex - 1;
			}
		} 
		else if ("delete".equals(actionCode)) {
			
//			logger.info("결재선 삭제클릭!! stampId : " + stampId);
//			logger.info("결재선 삭제클릭!! stampEmpId : " + stampEmpId);
//			logger.info("결재선 삭제클릭!! record.getStepEmpId().toString() : " + record.getStepEmpId().toString());
//			logger.info("결재선 삭제클릭!! complianceOfficer.toString() : " + complianceOfficer.toString());
			
			if(this.stampId != null && record.getStepEmpId().toString().equals(complianceOfficer.toString())) {
				new SimpleMessage("인감사용으로 삭제할 수 없습니다");
				return;
			}
			if(stampEmpId != null) {
				if(stampEmpId.toString().equals(record.getStepEmpId().toString())) {
					new SimpleMessage("인감사용으로 삭제할 수 없습니다");
					return;
				}
			}
			this.apprStepGrid.getStore().remove(record);
		}

		getSeq(this.apprStepGrid);
		this.apprStepGrid.getView().refresh(true);
		this.apprStepGrid.getSelectionModel().select(rowIndex, true);

	}

	//준법감시인 세팅
	private void selectComplianceOfficer() {
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("emp.Emp00_Info.selectComplianceOfficer");
		request.addParam("companyId", LoginUser.getCompanyId());
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				result.getResult();
				if(result.getResult().size() > 0) {
					Emp00_InfoModel empInfoModel = (Emp00_InfoModel) result.getResult(0);
					complianceOfficer = empInfoModel.getEmpId();
				}
			}
		});
	}
	
	public void getSeq(Grid<Apr04_ApprStepModel> grid) {
		for (int i = 0; i < grid.getStore().size(); i++) {
			int tempSeq = i + 1;
			tempSeq = tempSeq * 10;
			grid.getStore().get(i).setSeq(String.valueOf(tempSeq));
		}
	}

}