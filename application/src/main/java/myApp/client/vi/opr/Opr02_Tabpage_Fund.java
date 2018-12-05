package myApp.client.vi.opr;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.LoginUser;
import myApp.client.vi.opr.model.Opr02_FundModel;
import myApp.client.vi.opr.model.Opr02_FundModelProperties;

public class Opr02_Tabpage_Fund extends VerticalLayoutContainer implements InterfaceGridOperate {

	private Grid<Opr02_FundModel> grid = this.buildGrid();
	private Long createId;
	
	public Opr02_Tabpage_Fund() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
		LabelToolItem title = new LabelToolItem("▶ 연관펀드");
		title.setWidth(80);
		searchBarBuilder.getSearchBar().add(title);
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(-1, 50, new Margins(0,0,0,0)));
		this.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
	}

	private Grid<Opr02_FundModel> buildGrid() {

		Opr02_FundModelProperties properties = GWT.create(Opr02_FundModelProperties.class);
		GridBuilder<Opr02_FundModel> gridBuilder = new GridBuilder<Opr02_FundModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
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
		service.addParam("orgId"	, LoginUser.getOrgCodeId());
		service.addParam("createId" , this.createId);
		service.retrieve("opr.Opr02_Fund.selectByCreateId");
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
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
