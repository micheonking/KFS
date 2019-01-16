package myApp.client.vi.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.hom.model.Hom02_BoardModel;
import myApp.client.vi.hom.model.Hom02_BoardModelProperties;
import myApp.client.vi.home.company.TabAboutUsCeo;
import myApp.client.vi.home.company.TabAboutUsCompany;
import myApp.client.vi.home.company.TabAboutUsGroup;
import myApp.client.vi.home.company.TabAboutUsHistory;
import myApp.client.vi.home.company.TabAboutUsMap;

public class TabOurCompany extends BorderLayoutContainer implements InterfaceGridOperate {

	Hom02_BoardModelProperties properties = GWT.create(Hom02_BoardModelProperties.class);

	private Grid<Hom02_BoardModel> grid = this.buildGrid();

	private TabAboutUsCeo tabAboutUsCeo  = new TabAboutUsCeo();
	private TabAboutUsCompany tabAboutUsCompany  = new TabAboutUsCompany();
	private TabAboutUsHistory tabAboutUsHistory  = new TabAboutUsHistory();
	private TabAboutUsGroup tabAboutUsGroup  = new TabAboutUsGroup();
	private TabAboutUsMap tabAboutUsMap  = new TabAboutUsMap();
	
	ContentPanel contentPanel  = new ContentPanel();

	public TabOurCompany() {

		this.setBorders(false);

		VBoxLayoutContainer centerVBox = new VBoxLayoutContainer();
		centerVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		HBoxLayoutContainer menuHBar = new HBoxLayoutContainer();
		menuHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

//		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
//		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
//		gridVBox.setWidth(800);

//		SafeHtml gongjiHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#606060' size='5'><p style='font-weight:bold;'>CEO 인사말</p></font>");
//		LabelToolItem lableToolItem1 = new LabelToolItem(gongjiHtml);
//		gridVBox.add(lableToolItem1,new BoxLayoutData(new Margins(100, 600, 0, 40)));
//
//		Image lineBar0 = new Image(ResourceIcon.INSTANCE.lineBar());
//		gridVBox.add(lineBar0,new BoxLayoutData(new Margins(0, 0, 0, 0)));


//		BorderLayoutData centerBorLayoutData = new BorderLayoutData(1);
//		centerBorLayoutData.setMargins(new Margins(0, 0, 0, 0));
//		centerBorLayoutData.setSplit(true);
//		centerBorLayoutData.setMaxSize(1000);

		BoxLayoutData boxLayoutData = new BoxLayoutData();

		VBoxLayoutContainer menuVBox = new VBoxLayoutContainer();
		menuVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		Image lineImage0 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage1 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage2 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage3 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage4 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage5 = new Image(ResourceIcon.INSTANCE.verticalBar());

//		SafeHtml labelHtml = SafeHtmlUtils.fromTrustedString("<left><font color='#909090' style='font-size:24px;font-weight:bold;'><br><br><br>회사소개　　　　　　　　</font>");
//		LabelToolItem lableToolItem = new LabelToolItem(labelHtml);
//		lableToolItem.setSize("200", "131");
//		lableToolItem.setLabel(labelHtml);

//		MainFramePage.openTabPage(MainFramePage.tabPanel, "회사소개");

		menuVBox.add(MainFramePage.FuncLabelToolItem("회사소개"));
		menuVBox.add(lineImage0, new BoxLayoutData());


		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:16px;'>ㆍCEO 인사말　　　　　</font> ");
		SafeHtml button2Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:16px;'>ㆍ회사개요　　　　　　</font> ");
		SafeHtml button3Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:16px;'>ㆍ회사연혁　　　　　　</font> ");
		SafeHtml button4Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:16px;'>ㆍ조직도　　　　　　　</font> ");
		SafeHtml button5Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:16px;'>ㆍ찾아오시는길　　　　</font> ");
		CellButtonBase mainButton1 = new CellButtonBase<>();
		mainButton1.setSize("200", "40");
		mainButton1.setHTML(button1Html);
//		mainButton1.setBorders(true);
		mainButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getAboutUsCeo();
			}
		});
		menuVBox.add(mainButton1, new BoxLayoutData(new Margins(1, 3, 1, 3)));
		menuVBox.add(lineImage1, new BoxLayoutData(new Margins(0, 0, 0, 0)));

		CellButtonBase mainButton2 = new CellButtonBase<>();
		mainButton2.setSize("200", "40");
		mainButton2.setHTML(button2Html);
//		mainButton2.setBorders(true);
		mainButton2.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getAboutUsCompany();
			}
		});
		menuVBox.add(mainButton2, new BoxLayoutData(new Margins(1, 3, 1, 3)));
		menuVBox.add(lineImage2, new BoxLayoutData(new Margins(0, 0, 0, 0)));

		CellButtonBase mainButton3 = new CellButtonBase<>();
		mainButton3.setSize("200", "40");
		mainButton3.setHTML(button3Html);
//		mainButton3.setBorders(true);
		mainButton3.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getAboutUsHistory();
			}
		});
		menuVBox.add(mainButton3, new BoxLayoutData(new Margins(1, 3, 1, 3)));
		menuVBox.add(lineImage3, new BoxLayoutData(new Margins(0, 0, 0, 0)));

		CellButtonBase mainButton4 = new CellButtonBase<>();
		mainButton4.setSize("200", "40");
		mainButton4.setHTML(button4Html);
//		mainButton4.setBorders(true);
		mainButton4.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getAboutUsGroup();
			}
		});
		menuVBox.add(mainButton4, new BoxLayoutData(new Margins(1, 3, 1, 3)));
		menuVBox.add(lineImage4, new BoxLayoutData(new Margins(0, 0, 0, 0)));

		CellButtonBase mainButton5 = new CellButtonBase<>();
		mainButton5.setSize("200", "40");
		mainButton5.setHTML(button5Html);
//		mainButton5.setBorders(true);
		mainButton5.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getAboutUsMap();
			}
		});
		menuVBox.add(mainButton5, new BoxLayoutData(new Margins(1, 3, 1, 3)));
		menuVBox.add(lineImage5, new BoxLayoutData(new Margins(0, 0, 0, 0)));

		menuVBox.setWidth(210);
		menuVBox.setHeight(1000);

		menuHBar.add(menuVBox, boxLayoutData);

//		menuVBox.setBorders(true);
//		menuHBar.setBorders(true);
//		gridVBox.setBorders(true);
//		totalHBar.setBorders(true);
//		centerVBox.setBorders(true);
//		this.grid.setBorders(true);

//		gridVBox.add(this.getCeo(), boxLayoutData);
//		menuHBar.add(this.grid, boxLayoutData);
//		menuHBar.setHeight(1000);
		totalHBar.add(menuHBar);
		
		totalHBar.add(getAboutUsCeo());
		centerVBox.add(totalHBar);
//		totalHBar.setHeight(1000);
//		centerVBox.setHeight(1000);
		this.setCenterWidget(centerVBox);
//		this.setCenterWidget(this.grid, centerBorLayoutData);

//		getCeo();
	}

	private ContentPanel getAboutUsCeo() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(800);
		contentPanel.setHeight(1000);
		contentPanel.setWidget(tabAboutUsCeo);

		return contentPanel;
	}

	private ContentPanel getAboutUsCompany() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(800);
		contentPanel.setHeight(1000);
		contentPanel.setWidget(tabAboutUsCompany);

		return contentPanel;
	}
	
	private ContentPanel getAboutUsHistory() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(800);
		contentPanel.setHeight(1000);
		contentPanel.setWidget(tabAboutUsHistory);

		return contentPanel;
	}

	private ContentPanel getAboutUsMap() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(800);
		contentPanel.setHeight(1000);
		contentPanel.setWidget(tabAboutUsMap);

		return contentPanel;
	}
	
	private ContentPanel getAboutUsGroup() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(800);
		contentPanel.setHeight(1000);
		contentPanel.setWidget(tabAboutUsGroup);

		return contentPanel;
	}

	public Grid<Hom02_BoardModel> buildGrid() {
		GridBuilder<Hom02_BoardModel> gridBuilder = new GridBuilder<Hom02_BoardModel>(properties.keyId());
//		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.titleName(), 500, "제목");
		gridBuilder.addDate(properties.settleDate(), 110, "작성일");
		gridBuilder.addLong(properties.cnt(), 50, "조회수");

//		gridBuilder.setMenuDisable(true);
//		gridBuilder.rowNum.setHidden(true);
		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Hom02_BoardModel> service = new GridRetrieveData<Hom02_BoardModel>(grid.getStore());
		service.addParam("typeCode", "notice");
		service.addParam("setCount", (long)1000);
		service.retrieve("bbs.Hom02_Board.selectByTypeCode");
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
