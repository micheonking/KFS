package myApp.client.vi.opr;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.fnd.Fnd01_Lookup_MultiFund;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.opr.model.Opr02_FundModel;
import myApp.client.vi.opr.model.Opr02_FundModelProperties;

public class Opr02_Tabpage_Fund_Manager extends VerticalLayoutContainer implements InterfaceGridOperate {

	Opr02_FundModelProperties properties = GWT.create(Opr02_FundModelProperties.class);

	private Grid<Opr02_FundModel> grid = this.buildGrid();
	private Long createId;
	
	public Opr02_Tabpage_Fund_Manager() {

		LabelToolItem title = new LabelToolItem("▶ 연관펀드");
		title.setWidth(80);

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.getSearchBar().add(title);
		searchBarBuilder.addDeleteButton();
		searchBarBuilder.addInsertButton();
	
		TextButton fundButton = new TextButton("펀드불러오기");
		fundButton.setWidth(100);
		fundButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				LookupFund();
			}
		});
		searchBarBuilder.getSearchBar().add(fundButton);

		searchBarBuilder.addUpdateButton();
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(-1, -1, new Margins(0,0,0,0)));
		this.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
	}

	protected void LookupFund() {
		if (this.createId == null) {
			new SimpleMessage("확인","운용보고서 선택 후 등록가능합니다.");
			return;
		}

		Opr02_Lookup_Fund lookupFund = new Opr02_Lookup_Fund();
		lookupFund.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				List<Opr02_FundModel> fundModelList = (List<Opr02_FundModel>) result;
				for(Opr02_FundModel fundModel : fundModelList) {

					Opr02_FundModel insertModel = new Opr02_FundModel();
					insertModel.setCreateId(createId);
					insertModel.setRegFundId(fundModel.getRegFundId());

					GridInsertRow<Opr02_FundModel> service = new GridInsertRow<Opr02_FundModel>(); 
					service.insertRow(grid, insertModel, new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							grid.getStore().getRecord(insertModel).addChange(properties.fundCode()		, fundModel.getFundModel().getFundCode());
							grid.getStore().getRecord(insertModel).addChange(properties.fundName()		, fundModel.getFundModel().getFundName());
							grid.getStore().getRecord(insertModel).addChange(properties.fundTypeName()	, fundModel.getFundModel().getFundTypeName());
							grid.getStore().getRecord(insertModel).addChange(properties.empName1()		, fundModel.getFundModel().getEmpName1());
							grid.getStore().getRecord(insertModel).addChange(properties.startDate()		, fundModel.getFundModel().getStartDate());
							grid.getStore().getRecord(insertModel).addChange(properties.publicName()	, fundModel.getFundModel().getPublicName());
							grid.getStore().getRecord(insertModel).addChange(properties.orgCodeName()	, fundModel.getFundModel().getOrgCodeName());
						}
					});
				}
			}
		});
	}

	private Grid<Opr02_FundModel> buildGrid() {

		GridBuilder<Opr02_FundModel> gridBuilder = new GridBuilder<Opr02_FundModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.fundCode(), 70, "펀드코드");
		gridBuilder.addText(properties.fundName(), 250, "펀드명");
		gridBuilder.addText(properties.fundTypeName(), 80, "펀드유형");
		gridBuilder.addText(properties.empName1(), 60, "운용역");
		gridBuilder.addDate(properties.startDate(), 80, "설정일");
		gridBuilder.addText(properties.publicName(), 80, "공모/사모");
		gridBuilder.addText(properties.orgCodeName(), 100, "운용부서");

		return gridBuilder.getGrid();
	}

	public void retrieve(long createId) {

		this.createId = createId;
		
		GridRetrieveData<Opr02_FundModel> service = new GridRetrieveData<Opr02_FundModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("orgId" 	, LoginUser.getOrgCodeId());
		service.addParam("createId" , this.createId);
		service.retrieve("opr.Opr02_Fund.selectByCreateId");
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertRow() {

		if (this.createId == null) {
			new SimpleMessage("확인","운용보고서 선택 후 등록가능합니다.");
			return;
		}
		
		Fnd01_Lookup_MultiFund popup_fund = new Fnd01_Lookup_MultiFund();
		
		List<Long> paramList = new ArrayList<Long>();
		for(int i=0; i < grid.getStore().size(); i++) {
			paramList.add(grid.getStore().get(i).getRegFundId());
		}

		popup_fund.open(paramList, new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				List<Fnd00_FundModel> fundModelList = (List<Fnd00_FundModel>) result;
				for(Fnd00_FundModel fundModel : fundModelList) {
					Opr02_FundModel insertModel = new Opr02_FundModel();
					insertModel.setCreateId(createId);
					insertModel.setRegFundId(fundModel.getFundId());
					GridInsertRow<Opr02_FundModel> service = new GridInsertRow<Opr02_FundModel>(); 
					service.insertRow(grid, insertModel, new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							grid.getStore().getRecord(insertModel).addChange(properties.fundCode()		, fundModel.getFundCode());
							grid.getStore().getRecord(insertModel).addChange(properties.fundName()		, fundModel.getFundName());
							grid.getStore().getRecord(insertModel).addChange(properties.fundTypeName()	, fundModel.getFundTypeName());
							grid.getStore().getRecord(insertModel).addChange(properties.empName1()		, fundModel.getEmpName1());
							grid.getStore().getRecord(insertModel).addChange(properties.startDate()		, fundModel.getStartDate());
							grid.getStore().getRecord(insertModel).addChange(properties.publicName()	, fundModel.getPublicName());
							grid.getStore().getRecord(insertModel).addChange(properties.orgCodeName()	, fundModel.getOrgCodeName());
						}
					});
				}
			}
		});
	}

	@Override
	public void deleteRow() {
		List<Opr02_FundModel> delList = grid.getSelectionModel().getSelectedItems() ;
		if (delList.size() == 0) {
			new SimpleMessage("삭제확인","선택된 펀드가 없습니다.");
			return;
		}

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 펀드를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					GridDeleteData<Opr02_FundModel> service = new GridDeleteData<Opr02_FundModel>();
					service.delete(grid.getStore(), delList, "opr.Opr02_Fund.delete");
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
	public void update() {
		GridUpdate<Opr02_FundModel> service = new GridUpdate<Opr02_FundModel>(); 
		service.update(grid.getStore(), "opr.Opr02_Fund.update", new InterfaceCallback() {
			@Override
			public void execute() {
				retrieve(createId);
			}
		});
	}
}
