
package myApp.client.vi.opr;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.opr.model.Opr01_CreateModelProperties;
import myApp.client.vi.opr.model.Opr02_FundModel;
import myApp.client.vi.opr.model.Opr02_FundModelProperties;

public class Opr02_Lookup_Fund extends Window {
	
	//grid1
	private TextField searchTitle = new TextField();
	private TextField searchYear  = new TextField();

	//grid2
	private InterfaceCallbackResult callback;
	
	private Opr01_CreateModelProperties properties = GWT.create(Opr01_CreateModelProperties.class);
	private Opr02_FundModelProperties properties2 = GWT.create(Opr02_FundModelProperties.class);
	private Grid<Opr01_CreateModel> grid = this.buildGrid();
	private Grid<Opr02_FundModel> grid2 = this.buildGrid2();

	public void open (InterfaceCallbackResult callback) {

		this.callback = callback;
		
		this.setModal(true);
		this.setHeading("펀드찾기");
		this.setResizable(false);
		this.setPixelSize(900, 500);

		ButtonBar searchBar = new ButtonBar(); 

		FieldLabel searchYearField = new FieldLabel(searchYear, "년도");
		searchYear.setValue(LoginUser.getYear());
		searchYear.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		searchYearField.setLabelWidth(30);
		searchYearField.setWidth(100);
		searchYearField.setLabelSeparator(" ");
		searchBar.add(searchYearField);
		
		FieldLabel searchField = new FieldLabel(searchTitle, "검색*");
		searchTitle.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		searchField.setLabelWidth(30);
		searchField.setWidth(250);
		searchField.setLabelSeparator(" ");
		searchBar.add(searchField);
		
		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.setWidth(50);
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		searchBar.add(retrieveButton);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(searchBar, new VerticalLayoutData(1,40));

		vlc.add(grid, new VerticalLayoutData(1,200));

		LabelToolItem label2 = new LabelToolItem("▶ 연관펀드");
		label2.setWidth(100);
		vlc.add(label2, new VerticalLayoutData(1,-1,new Margins(0,0,10,0)));
		vlc.add(grid2, new VerticalLayoutData(1,1));

		this.add(vlc);
		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Opr01_CreateModel>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Opr01_CreateModel> event) {
				if (event.getSelection().size() > 0) {
					retrieve2();
				}
			}
		});

		TextButton okButton = new TextButton("확인");
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				confirm();
			}
		});
		this.addButton(okButton);

		TextButton cancelButton = new TextButton("취소");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show();
		this.retrieve();
	}
	
	private Grid<Opr01_CreateModel> buildGrid() {
		
		GridBuilder<Opr01_CreateModel> gridBuilder = new GridBuilder<Opr01_CreateModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.title()			, 200, "제목*");
		gridBuilder.addText(properties.year()			,  70, "년도");
		gridBuilder.addText(properties.termNm()			,  80, "기간구분");
		gridBuilder.addText(properties.korName()		,  80, "담당자");
		gridBuilder.addText(properties.positionName()	,  70, "직위");
		gridBuilder.addText(properties.orgName()		, 150, "부서");
		gridBuilder.addDate(properties.startDate()		,  80, "시작일");
		gridBuilder.addDate(properties.closeDate()		,  80, "완료일");
		return gridBuilder.getGrid();
	}
	
	private Grid<Opr02_FundModel> buildGrid2() {
		
		GridBuilder<Opr02_FundModel> gridBuilder2 = new GridBuilder<Opr02_FundModel>(properties2.keyId());
		gridBuilder2.setChecked(SelectionMode.MULTI);
		gridBuilder2.addText(properties2.fundCode()		,  70, "펀드코드");
		gridBuilder2.addText(properties2.fundName()		, 250, "펀드명");
		gridBuilder2.addText(properties2.fundTypeName()	,  80, "펀드유형");
		gridBuilder2.addText(properties2.empName1()		,  70, "운용역");
		gridBuilder2.addDate(properties2.startDate()	,  80, "설정일");
		gridBuilder2.addText(properties2.publicName()	,  70, "공모/사모");
		gridBuilder2.addText(properties2.orgCodeName()	, 150, "운용부서");
		return gridBuilder2.getGrid();
	}
	
	private void retrieve() {
		grid.mask("Loading");
		GridRetrieveData<Opr01_CreateModel> service = new GridRetrieveData<Opr01_CreateModel>(grid.getStore());
		service.addParam("companyId" , LoginUser.getCompanyId());
		service.addParam("title"	 , searchTitle.getText());
		service.addParam("year"	 	 , searchYear.getText());
		service.retrieve("opr.Opr01_Create.selectByLookupTitle", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				grid.getSelectionModel().select(0, false);
			}
		});
	}

	private void retrieve2() {
		Long createId = grid.getSelectionModel().getSelectedItem().getCreateId();
		GridRetrieveData<Opr02_FundModel> service2 = new GridRetrieveData<Opr02_FundModel>(grid2.getStore());
		service2.addParam("companyId" , LoginUser.getCompanyId());
		service2.addParam("createId"  , createId );
		service2.addParam("orgId"  	  , LoginUser.getOrgCodeId());
		service2.retrieve("opr.Opr02_Fund.selectByCreateId");
	}

	private void confirm() {
		List<Opr02_FundModel> selectList = grid2.getSelectionModel().getSelectedItems();
		if(selectList != null) {
			this.callback.execute(selectList);
			hide();
		}
		else {
			new SimpleMessage("조회할 펀드를 선택하여 주세요."); 
			return; 
		}
	}

}
