package myApp.client.vi.cst;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.LoginUser;
import myApp.client.vi.cst.model.Cst02_AccountModel;
import myApp.client.vi.cst.model.Cst02_AccountModelProperties;

public class Cst99_Edit_account extends BorderLayoutContainer implements InterfaceGridOperate {

    private Cst02_AccountModelProperties properties = GWT.create(Cst02_AccountModelProperties.class);
	public Grid<Cst02_AccountModel> grid = this.buildGrid();
	private Long userId = 0L;
	
	
	public Cst99_Edit_account() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		TextButton addButton = new TextButton("계좌추가");
		addButton.setWidth(80);
		addButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				insertRow();
			}
		});
		searchBarBuilder.getSearchBar().add(addButton);

		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50));

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		this.setCenterWidget(vlc);

		BorderLayoutData southLayoutData = new BorderLayoutData(320);	//	전체화면의 60%
		southLayoutData.setMargins(new Margins(2, 0, 0, 0)); 
		southLayoutData.setMaxSize(900);
		southLayoutData.setSplit(true);
		
	}
	
	public Grid<Cst02_AccountModel> buildGrid(){
		
		GridBuilder<Cst02_AccountModel> gridBuilder = new GridBuilder<Cst02_AccountModel>(properties.keyId());  
		
		final MgComboBoxField mgComboBox = new MgComboBoxField();
		mgComboBox.addSelectionHandler(new SelectionHandler<String>() {
			@Override
			public void onSelection(SelectionEvent<String> event) {
//				Info.display("aaaa", mgComboBox.getCode());
				grid.getSelectionModel().getSelectedItem().setMgCode(mgComboBox.getCode());	//증권사코드SET
				
			}
		});

		gridBuilder.addText(properties.mgName(), 120, "증권사", mgComboBox);
//		gridBuilder.addText(properties.mgCode(), 120, "증권사");
//		gridBuilder.addLong	(properties.userId()	,  100, "userId" );
//		gridBuilder.addText	(properties.mgCode()		, 100, "증권사CD"	,new TextField());
		gridBuilder.addText	(properties.accountNo()		, 150, "계좌번호",new TextField());
		gridBuilder.addText	(properties.accountName()	, 150, "계좌명",new TextField());
		return gridBuilder.getGrid(); 
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public void retrieve(){
		
		GridRetrieveData<Cst02_AccountModel> service = new GridRetrieveData<Cst02_AccountModel>(grid.getStore());
		service.addParam("userId", LoginUser.getUserId());
		service.addParam("userId", userId);
		service.retrieve("cst.Cst02_Account.selectByAccountList2"); 

	}

	@Override
	public void update(){
	}

	@Override
	public void insertRow(){
		GridInsertRow<Cst02_AccountModel> service = new GridInsertRow<Cst02_AccountModel>(); 
		Cst02_AccountModel accountModel = new Cst02_AccountModel();
		accountModel.setUserId(userId);
		service.insertRow(grid, accountModel);
		
	}

	@Override
	public void deleteRow(){
		ConfirmMessageBox messageBox = new ConfirmMessageBox("삭제확인", "선택한 내용을 삭제하시겠습니까?");
		messageBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				if(event.getHideButton().toString() == "YES") {
//					deleteSelectedRow();
				}
			}
		});
		messageBox.setWidth(300);
		messageBox.show();
	}
}