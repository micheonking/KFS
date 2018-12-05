package myApp.client.vi.emp;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys09_CodeModel;
import myApp.client.vi.sys.model.Sys09_CodeModelProperties;

public class Emp09_Tab_MgrRlue extends BorderLayoutContainer {

	private Grid<Sys09_CodeModel> grid = this.buildGrid();

	public Emp09_Tab_MgrRlue() {

		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		});

		ButtonBar buttonBar = new ButtonBar();
		buttonBar.setSpacing(5);
		buttonBar.add(updateButton); // 수정버튼

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(this.grid, new VerticalLayoutData(1, 1));// 그리드 표현

		cp.addButton(updateButton); // 수정버튼

		cp.setButtonAlign(BoxLayoutPack.CENTER);
		cp.getButtonBar().setHeight(60);

		this.add(cp);

		retrieve();

	}

	public Grid<Sys09_CodeModel> buildGrid() {

		Sys09_CodeModelProperties properties = GWT.create(Sys09_CodeModelProperties.class);
		GridBuilder<Sys09_CodeModel> gridBuilder = new GridBuilder<Sys09_CodeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.name(), 200, "직책명");
		gridBuilder.addText(properties.code(), 80, "코드");
		gridBuilder.addBoolean(properties.useYn(), 80, "권한여부");
		gridBuilder.addLong(properties.codeId(), 100, "codeId");
		gridBuilder.addLong(properties.mgrRuleId(), 100, "mgrRuleId");

		return gridBuilder.getGrid();
	}

	public void retrieve() {
		// TODO Auto-generated method stub
		grid.getStore().clear();
		GridRetrieveData<Sys09_CodeModel> service = new GridRetrieveData<Sys09_CodeModel>(this.grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.retrieve("emp.Emp09_MgrRule.selectByCompanyId");
	}
	
	public void update() {
		GridUpdate<Sys09_CodeModel> service = new GridUpdate<Sys09_CodeModel>(); 
		service.addParam("companyId", LoginUser.getCompanyId());
		service.update(grid.getStore(), "emp.Emp09_MgrRule.updateMgrRule"); 
	}

}