package myApp.client.vi.pln;

import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
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
import myApp.client.vi.pln.model.Pln02_PlanModel;
import myApp.client.vi.pln.model.Pln02_PlanModelProperties;

public class Pln02_TabPage_PlanEmp extends VerticalLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {

    Pln02_PlanModelProperties properties = GWT.create(Pln02_PlanModelProperties.class);
    
	private Grid<Pln02_PlanModel> grid = this.buildGrid();

	private Long   fundId;
	private String actionName;

	public Pln02_TabPage_PlanEmp() {

		LabelToolItem title = new LabelToolItem("▶ 펀드별 일정목록");
		title.setWidth(120);

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		searchBarBuilder.getSearchBar().add(title);
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(-1, 50, new Margins(0,0,0,0)));
		this.add(grid, new VerticalLayoutData(1,1, new Margins(0)));
	}
	
	public Grid<Pln02_PlanModel> buildGrid(){
		
		GridBuilder<Pln02_PlanModel> gridBuilder = new GridBuilder<Pln02_PlanModel>(properties.keyId());  

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	(properties.classTreeName(),	200,	"문서분류"		);
		gridBuilder.addText	(properties.approvalTypeName(),	100,	"최종결재"		);
		gridBuilder.addText	(properties.orgName(),			100,	"부서"		);
//		gridBuilder.addText	(properties.titleName(),		 80,	"직위"		);
		gridBuilder.addText	(properties.korName(),			 80,	"담당자"		);
		gridBuilder.addDate	(properties.startDate(),		 90,	"시작일"		);
		gridBuilder.addDate	(properties.dueDate(),			 90,	"완료예정일"	);
		gridBuilder.addText	(properties.closeYn(),			100,	"진행상태"		);

		ActionCell<String> aprButton = new ActionCell<String>("Detail", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				Pln02_PlanModel planModel = grid.getSelectionModel().getSelectedItem();

				//담당자 미지정
				if (planModel.getEmpId() == null) {
					new SimpleMessage("확인", "담당자 미지정 문서입니다.");
					return;
				}

				//담당자가 아닌경우..
				if (!planModel.getEmpId().equals(LoginUser.getUserId())) {
					if (!"완료".equals(planModel.getCloseYn())) {
						new SimpleMessage("확인", "완료전인 문서는 확인 불가합니다.");
						return;
					}
				}

				aprLookup(planModel.getPlanId());
			}
		});
		gridBuilder.addCell	(properties.actionCell(),  60, "결재요청", aprButton);
		gridBuilder.addText	(properties.note()      , 400, "기타사항", new TextField());

		gridBuilder.addBeforeStartEditHandler(new BeforeStartEditHandler<Pln02_PlanModel>() {
			@Override
			public void onBeforeStartEdit(BeforeStartEditEvent<Pln02_PlanModel> event) {
				Pln02_PlanModel planModel = grid.getSelectionModel().getSelectedItem();
				if (planModel.getUpdateYn().equals("true")) {
					event.setCancelled(false);
				} else {
					event.setCancelled(true);
				}
			}
		});
		
		return gridBuilder.getGrid(); 
	}

	private void aprLookup(Long planId) {
		
		actionName = "insertAppr";

		ServiceRequest request = new ServiceRequest("pln.Pln02_Plan.insertAppr");
		request.putLongParam("planId"	, planId);
		request.putLongParam("empId"	, LoginUser.getUserId());
		request.putLongParam("orgId"	, LoginUser.getOrgCodeId());
		request.putLongParam("companyId", LoginUser.getCompanyId());

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public void retrieve(Long fundId){
		this.fundId = fundId;
		GridRetrieveData<Pln02_PlanModel> service = new GridRetrieveData<Pln02_PlanModel>(grid.getStore());
		service.addParam("fundId", fundId);
		service.addParam("orgId" , LoginUser.getOrgCodeId());
		service.retrieve("pln.Pln02_Plan.selectByFundIdEmp");
	}

	@Override
	public void insertRow(){
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

		Pln02_PlanModel plnModel = grid.getSelectionModel().getSelectedItem();
		if ((plnModel.getEmpId() == null) || (plnModel.getEmpId() != LoginUser.getUserId())) {
			new SimpleMessage("확인", "담당자가 아닌경우 삭제불가합니다.");
			return;
		}
		
		switch (plnModel.getCloseYn()) {
		case "상신":
		case "결재중":
		case "완료":
		case "반려":
			new SimpleMessage("삭제확인","상신중 또는 완료된 문서는 삭제불가합니다.");
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

	@Override
	public void getServiceResult(ServiceResult result) {
		if (actionName.equals("insertAppr")) {
			if (result.getStatus() > 0) {
				Apr01_ApprModel aprModel = (Apr01_ApprModel) result.getResult(0);
				Apr01_Edit_ApprDoc lookupApr = new Apr01_Edit_ApprDoc();
				lookupApr.retrieveApproval(aprModel, false, new InterfaceCallback() {
					@Override
					public void execute() {
						retrieve(fundId);
					}
				});
			}
		}
	}

}