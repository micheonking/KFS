package myApp.client.vi.pln;

import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.pln.model.Pln02_PlanModel;
import myApp.client.vi.pln.model.Pln02_PlanModelProperties;

public class Pln02_TabPage_Plan extends BorderLayoutContainer implements InterfaceGridOperate {

    private Pln02_PlanModelProperties properties = GWT.create(Pln02_PlanModelProperties.class);
	private Grid<Pln02_PlanModel> grid = this.buildGrid();
	private Long fundId;

	public Pln02_TabPage_Plan() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addInsertButton(); 
		searchBarBuilder.addDeleteButton(); 
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData.setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);
		this.setCenterWidget(this.grid, centerLayoutData);
	}
	
	public Grid<Pln02_PlanModel> buildGrid(){
		
		GridBuilder<Pln02_PlanModel> gridBuilder = new GridBuilder<Pln02_PlanModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		//문서분류선택
		LookupTriggerField lookupClassTree = new LookupTriggerField();
		lookupClassTree.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openlookUpClassTree();
			}
		}); 
		gridBuilder.addText	(properties.classTreeName(),	250,	"문서분류",	lookupClassTree);
		gridBuilder.addText	(properties.approvalTypeName(),	100,	"최종결재"		);
		gridBuilder.addText	(properties.orgName(),			100,	"부서"		);
//		gridBuilder.addText	(properties.titleName(),		100,	"직위"		);

		//담당자선택
		LookupTriggerField lookupEmpInfo = new LookupTriggerField();
		lookupEmpInfo.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupEmpInfo();
			}
		});
		gridBuilder.addText	(properties.korName(),			90,		"담당자"		,	lookupEmpInfo);
		gridBuilder.addDate	(properties.startDate(),		100,	"시작일"		,	new DateField());
		gridBuilder.addDate	(properties.dueDate(),			100,	"완료예정일"	,	new DateField());
		gridBuilder.addText	(properties.closeYn(),			100,	"진행상태"		);	//	"[완료여부]"
		gridBuilder.addText	(properties.note(),				400,	"기타사항"		,	new TextField());
		
		gridBuilder.addBeforeStartEditHandler(new BeforeStartEditHandler<Pln02_PlanModel>() {
			@Override
			public void onBeforeStartEdit(BeforeStartEditEvent<Pln02_PlanModel> event) {
				Pln02_PlanModel planModel = grid.getSelectionModel().getSelectedItem();
				if ("완료".equals(planModel.getCloseYn())) {
					event.setCancelled(true);
				} else {
					event.setCancelled(false);
				}
			}
		});

		return gridBuilder.getGrid(); 
	}

	private void openlookUpClassTree() {
		Pln00_Popup_ClassTree lookupClassTree = new Pln00_Popup_ClassTree();
		lookupClassTree.open(new Date(), new InterfaceCallbackResult(){

			@Override
			public void execute(Object result) {
				@SuppressWarnings("unchecked")
				List<Dcr01_ClassTreeModel> list = (List<Dcr01_ClassTreeModel>)result; 
				Dcr01_ClassTreeModel returnModel = list.get(0); 
				Pln02_PlanModel	selectModel = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(selectModel).addChange(properties.classTreeId(), returnModel.getClassTreeId());
				grid.getStore().getRecord(selectModel).addChange(properties.classTreeName(), returnModel.getClassTreeName());
				grid.getStore().getRecord(selectModel).addChange(properties.approvalTypeName(), returnModel.getApprovalTypeName());
			}
		});
	}
	
	private void openLookupEmpInfo() {
		Emp00_Lookup_EmpInfo lookupEmpInfo = new Emp00_Lookup_EmpInfo(); 
		lookupEmpInfo.open(new Date(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Emp00_InfoModel empInfoModel = (Emp00_InfoModel)result;
				Pln02_PlanModel	selectModel = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(selectModel).addChange(properties.empId()		, empInfoModel.getEmpId());
				grid.getStore().getRecord(selectModel).addChange(properties.korName()	, empInfoModel.getKorName());
				grid.getStore().getRecord(selectModel).addChange(properties.orgName()	, empInfoModel.getOrgName());
				grid.getStore().getRecord(selectModel).addChange(properties.titleName()	, empInfoModel.getTitleName());
			}
		});
	}

	public void retrieve(Long fundId){
		this.fundId = fundId;
		GridRetrieveData<Pln02_PlanModel> service = new GridRetrieveData<Pln02_PlanModel>(grid.getStore());
		service.addParam("fundId", fundId);
		service.retrieve("pln.Pln02_Plan.selectByFundId");
	}

	@Override
	public void insertRow(){
		Pln00_Popup_ClassTree lookupClassTree = new Pln00_Popup_ClassTree();
		lookupClassTree.open(new Date(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				@SuppressWarnings("unchecked")
				List<Dcr01_ClassTreeModel> selectList =(List<Dcr01_ClassTreeModel>)result;
				
				for (Dcr01_ClassTreeModel selectModel : selectList) {
					
					GridInsertRow<Pln02_PlanModel> service = new GridInsertRow<Pln02_PlanModel>(); 
					Pln02_PlanModel planModel = new Pln02_PlanModel();
					service.insertRow(grid, planModel, new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							GridDataModel insertData= (GridDataModel)result; 
							Pln02_PlanModel findModel =  grid.getStore().findModelWithKey(insertData.getKeyId() + "");
							grid.getStore().getRecord(findModel).addChange(properties.classTreeId(), selectModel.getClassTreeId());
							grid.getStore().getRecord(findModel).addChange(properties.classTreeName(), selectModel.getClassTreeName());
							grid.getStore().getRecord(findModel).addChange(properties.approvalTypeName(), selectModel.getApprovalTypeName());
							grid.getStore().getRecord(findModel).addChange(properties.fundId(), fundId);
							grid.getStore().getRecord(findModel).addChange(properties.startDate(), LoginUser.getToday());
							findModel.setStartDate(LoginUser.getToday());
							grid.getStore().getRecord(findModel).addChange(properties.startDate(), LoginUser.getToday());
							grid.getSelectionModel().getSelectedItem().setStartDate(LoginUser.getToday());
						}
					});
				}
			}
		});
	}

	@Override
	public void update(){
		GridUpdate<Pln02_PlanModel> service = new GridUpdate<Pln02_PlanModel>(); 
		service.update(grid.getStore(), "pln.Pln02_Plan.update"); 
	}

	@Override
	public void deleteRow() {

		if (grid.getSelectionModel().getSelectedItems().size() == 0) {
			new SimpleMessage("삭제확인","선택된 문서가 없습니다.");
			return;
		}

		Pln02_PlanModel planModel = grid.getSelectionModel().getSelectedItem();
//		String aprStatus = planModel.getCloseYn();
//		if ("상신".equals(aprStatus) || "결재중".equals(aprStatus) || "완료".equals(aprStatus)) {
//			new SimpleMessage("확인", "상신진행중 또는 완료된 문서는 삭제불가합니다.");
//			return;
//		}
		switch (planModel.getCloseYn()) {
		case "상신":
		case "결재중":
		case "완료":
		case "반려":
			new SimpleMessage("삭제확인","상신중 또는 완료된 보고서는 삭제불가합니다.");
			return;
		}

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 문서를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					GridDeleteData<Pln02_PlanModel> service = new GridDeleteData<Pln02_PlanModel>();
					List<Pln02_PlanModel> deleteList = grid.getSelectionModel().getSelectedItems() ;
					service.delete(grid.getStore(), deleteList, "pln.Pln02_Plan.delete");
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
	public void retrieve(){
	}

}