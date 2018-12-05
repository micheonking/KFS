package myApp.client.vi.emp;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp02_EmpModelProperties;
import myApp.client.vi.emp.model.Emp10_AddrBookModel;
import myApp.client.vi.emp.model.Emp10_AddrBookModelProperties;
import myApp.client.vi.emp.model.Emp10_ManagerContactModel;

public class Emp10_Lookup_MailAddress extends Window {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Emp10_AddrBookModelProperties properties = GWT.create(Emp10_AddrBookModelProperties.class);
	private Grid<Emp10_AddrBookModel> grid = this.buildGrid();
	private Grid<Emp10_ManagerContactModel> grid2;
	private Grid<Emp02_EmpModel> empGrid = this.buildEmpGrid();
	private InterfaceCallbackResult callbackResult;

	private PlainTabPanel tabPanel = new PlainTabPanel();
	private int selectedTabIndex = 0;
	
	private TextButton receiverButton = new TextButton("검색");
	private TextButton confirmButton = new TextButton("확인");
	private TextButton closeButton = new TextButton("닫기");
	private TextField searchText = new TextField();

	public Emp10_Lookup_MailAddress() {
		init();
	}

	public Emp10_Lookup_MailAddress(Grid<Emp10_ManagerContactModel> grid) {
		this.grid2 = grid;
		init();
	}

	public void open(InterfaceCallbackResult callbackResult) {
		this.callbackResult = callbackResult;
		this.show();
		retrieve();
		retrieveEmpGrid();
	}

	public Grid<Emp10_AddrBookModel> buildGrid() {

		GridBuilder<Emp10_AddrBookModel> gridBuilder = new GridBuilder<Emp10_AddrBookModel>(
				properties.keyId());
		gridBuilder.setChecked(SelectionMode.MULTI);

		gridBuilder.addText(properties.companyName(), 100, "companyName");
		gridBuilder.addText(properties.personName(), 100, "personName");
		gridBuilder.addText(properties.email(), 100, "email");
		gridBuilder.addText(properties.mobile(), 100, "mobile");
		gridBuilder.addText(properties.officeTel1(), 100, "officeTel1");

		return gridBuilder.getGrid();
	}

	public Grid<Emp02_EmpModel> buildEmpGrid(){

		Emp02_EmpModelProperties properties = GWT.create(Emp02_EmpModelProperties.class);
		GridBuilder<Emp02_EmpModel> gridBuilder = new GridBuilder<Emp02_EmpModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.MULTI);
		
		gridBuilder.addText(properties.orgKorName(), 200, "부서명");
		gridBuilder.addText(properties.korName(), 100, "성명");
		gridBuilder.addText(properties.posName(), 100, "직위");
		gridBuilder.addText(properties.emailAddr(), 150, "이메일주소");

		return gridBuilder.getGrid();
	}
	private void init() {
		makeLayout();
		makeButtonEvent();
	}

	private void makeLayout() {
		this.setModal(true);
		this.setHeading("주소록");
		this.setResizable(false);
		this.setPixelSize(710, 490);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		HorizontalLayoutData termLayout = new HorizontalLayoutData(50, -1, new Margins(0, 5, 5, 5)); // 여백Size
		HorizontalLayoutData labelLayout = new HorizontalLayoutData(50, -1, new Margins(2, 0, 0, 0)); // 라벨Size
		HorizontalLayoutData rowLayout1 = new HorizontalLayoutData(700, -1, new Margins(0, 0, 5, 0)); // 컬럼하나Size

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();

		row01.add(new LabelToolItem("검색어 : "), labelLayout);
			
		row01.add(searchText);
		row01.add(receiverButton, termLayout);

		vlc.add(row01, new VerticalLayoutData(1, 32, new Margins(0, 0, 0, 0)));
		vlc.add(getTabPanel(), new VerticalLayoutData(1, 1, new Margins(0, 0, 0, 0)));

		this.add(vlc, new VerticalLayoutData(1, 1, new Margins(15, 15, 0, 15)));

		this.getButtonBar().add(confirmButton);
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	private void makeButtonEvent() {
		searchText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		});
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		confirmButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				//callbackResult.execute(grid);
				List<Emp10_AddrBookModel> list = grid.getSelectionModel().getSelectedItems();
				String tempStr = "";
				for(Emp10_AddrBookModel model : list) {
					tempStr += "" + model.getPersonName() + " ";
					tempStr += "<" + model.getEmail() + ">";
					tempStr += ";";
				}
				List<Emp02_EmpModel> list2 = empGrid.getSelectionModel().getSelectedItems();
				for(Emp02_EmpModel model : list2) {
					tempStr += "" + model.getPersonModel().getKorName() + " ";
					tempStr += "<" + model.getPersonModel().getEmailAddr() + ">";
					tempStr += ";";
				}
				
				callbackResult.execute(tempStr);
				hide();
			}
		});
		receiverButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();	
			}
		});
	}

	private PlainTabPanel getTabPanel() {
		tabPanel.add(grid, "주소록");
		tabPanel.add(empGrid, "사내");		
		tabPanel.addSelectionHandler(new SelectionHandler<Widget>(){
			@Override
			public void onSelection(SelectionEvent<Widget> arg0) {
				if(arg0 != null) {
					if(tabPanel.getActiveWidget() == grid) {
						selectedTabIndex = 0;
					}
					if(tabPanel.getActiveWidget() == empGrid) {
						selectedTabIndex = 1;
					}
				} 
			}
		});
		return tabPanel;
	}

	
	private void retrieve() {
		if(selectedTabIndex == 0) {
			retrieveAddressGrid();
		} else if(selectedTabIndex == 1) {
			retrieveEmpGrid();
		}
	}
	
	private void retrieveAddressGrid() {
		GridRetrieveData<Emp10_AddrBookModel> service = new GridRetrieveData<Emp10_AddrBookModel>(
				this.grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("empId", LoginUser.getUserId());
		service.addParam("searchText", searchText.getText());
		
		service.retrieve("emp.Emp10_AddrBook.selectByText", new InterfaceCallback() {
			
			@Override
			public void execute() {
				/*
				if(grid2 != null) {
					if(grid2.getStore().getAll().size() > 0) {
						List<Emp10_ManagerContactModel> tempList = grid2.getSelectionModel().getSelectedItems();
						grid.getSelectionModel().setSelection(tempList);
						grid.getSelectionModel().refresh();
					}
				}
				*/
			}
		});
	}
	
	private void retrieveEmpGrid() {
		GridRetrieveData<Emp02_EmpModel> service = new GridRetrieveData<Emp02_EmpModel>(this.empGrid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("searchText", searchText.getText());
		service.addParam("workTypeCode", "WORK");
		
		service.retrieve("emp.Emp02_Emp.selectByText", new InterfaceCallback() {
			@Override
			public void execute() {
			}
		});
	}
}
