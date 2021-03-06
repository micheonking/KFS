package myApp.client.vi.frm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.LoginUser;
import myApp.client.vi.MenuOpener;
import myApp.client.vi.MenuTree;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr01_ApprModelProperties;

public class Monitoring_Page_Appr extends VerticalLayoutContainer implements InterfaceGridOperate {

	Apr01_ApprModelProperties properties = GWT.create(Apr01_ApprModelProperties.class);
	Apr01_ApprModel resrchModel = new Apr01_ApprModel();
	
	private Grid<Apr01_ApprModel> grid = this.buildGrid();

	public Monitoring_Page_Appr() {

		TextButton goPageButton = new TextButton("바로가기");
		goPageButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				MenuOpener openTab = new MenuOpener();
				openTab.openTab(MenuTree.tabPanel, "Apr01_Tab_AplyHistory.class", "상신내역 조회");
			}
		});

		ButtonBar bar = new ButtonBar();
		bar.add(new LabelToolItem("▶ 결재요청함 (완료/반려건은 최대7일까지 표시)"));
		bar.add(new Label());
		bar.add(goPageButton);
		this.add(bar);

		this.add(grid, new VerticalLayoutData(1,1,new Margins(0)));
		retrieve();
	}

	private Grid<Apr01_ApprModel> buildGrid() {

		GridBuilder<Apr01_ApprModel> gridBuilder = new GridBuilder<Apr01_ApprModel>(properties.keyId());
		gridBuilder.addText(properties.statusName()	 , 80, "결재상태");
		gridBuilder.addDate(properties.regDate()	 , 80, "등록일");
		gridBuilder.addText(properties.title()		 ,300, "제목");
		gridBuilder.addText(properties.NextApprName(),100, "다음결재자");
		gridBuilder.setRowNumHidden(true); 
		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Apr01_ApprModel> service = new GridRetrieveData<Apr01_ApprModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("empId"	, LoginUser.getUserId());
		service.addParam("toDay"	, LoginUser.getToday());
		service.retrieve("apr.Apr01_Appr.selectMonitorngAppr");
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