package myApp.client.vi.frm;

import java.util.Date;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.ClientDateUtil;
import myApp.client.vi.LoginUser;
import myApp.client.vi.MenuOpener;
import myApp.client.vi.MenuTree;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr01_ApprModelProperties;

public class Monitoring_Page_ToDoList extends VerticalLayoutContainer implements InterfaceGridOperate {

	Apr01_ApprModelProperties properties = GWT.create(Apr01_ApprModelProperties.class);
	Apr01_ApprModel resrchModel = new Apr01_ApprModel();
	
	private Grid<Apr01_ApprModel> grid = this.buildGrid();

	LabelToolItem labelItem = new LabelToolItem("▶ To Do List");
	
	private Date date;

	public Monitoring_Page_ToDoList() {

		ButtonBar bar = new ButtonBar();
		labelItem.setWidth(300);
		bar.add(labelItem);
		this.add(bar);

		this.add(grid, new VerticalLayoutData(1,1,new Margins(0)));
		retrieve();
	}

	private Grid<Apr01_ApprModel> buildGrid() {

		GridBuilder<Apr01_ApprModel> gridBuilder = new GridBuilder<Apr01_ApprModel>(properties.keyId());
//		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.note()	 , 80, "업무구분");
		gridBuilder.addDate(properties.regDate() , 80, "등록일");
		gridBuilder.addText(properties.title()	 ,300, "제목");
		gridBuilder.addText(properties.regEmpNm(),100, "담당자");

		ActionCell<String> goPageButton = new ActionCell<String>("바로가기", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				MenuOpener openTab = new MenuOpener();
				Apr01_ApprModel aprModel = grid.getSelectionModel().getSelectedItem();
				if (aprModel.getNote().equals("일정관리")) {
					openTab.openTab(MenuTree.tabPanel, "Pln03_Tab_Resrch.class", "리서치 · 접촉보고");
				}
				else if (aprModel.getNote().equals("운용보고")) {
					openTab.openTab(MenuTree.tabPanel, "Opr01_Tab_Create.class", "운용보고서");
				}
				else if (aprModel.getNote().equals("상품기획")) {
					openTab.openTab(MenuTree.tabPanel, "Pln02_Tab_EmpPlan.class", "상품기획문서등록");
				}
				else if (aprModel.getNote().equals("상신문서")) {
					openTab.openTab(MenuTree.tabPanel, "Apr01_Tab_AprHistory.class", "결재내역 조회");
				}
			}
		});
		gridBuilder.addCell(properties.actionCell(), 80, "바로가기", goPageButton);

		gridBuilder.setRowNumHidden(true); 
		
		return gridBuilder.getGrid();
	}
	
	public void retrieve(Date date) {
		this.date = date;
		retrieve();
	}

	@Override
	public void retrieve() {

		if (date == null) {
			date = LoginUser.getToday();
		}

		String sDate = ClientDateUtil.getToday(date);
		labelItem.setLabel("▶ To Do List (기준일 : " + sDate + ")");

		GridRetrieveData<Apr01_ApprModel> service = new GridRetrieveData<Apr01_ApprModel>(grid.getStore());
		service.addParam("companyId"	, LoginUser.getCompanyId());
		service.addParam("empId"		, LoginUser.getUserId());
		service.addParam("orgCd"		, LoginUser.getOrgCode());
		service.addParam("selectDate"	, date);
		service.retrieve("apr.Apr01_Appr.selectToDoList");
		date = null;
//		grid.getView().refresh(true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
	}
	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
	}
}