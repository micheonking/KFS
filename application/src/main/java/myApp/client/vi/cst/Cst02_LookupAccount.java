
package myApp.client.vi.cst;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.cst.model.Cst02_AccountModel;
import myApp.client.vi.cst.model.Cst02_AccountModelProperties;

public class Cst02_LookupAccount extends Window implements InterfaceServiceCall {

	private TextField accountNameText = new TextField();
	private Grid<Cst02_AccountModel> grid = this.buildGrid();
	private InterfaceCallbackResult callback;

	public void open (InterfaceCallbackResult callback) {
		
		this.callback = callback;

		this.setModal(true);
		this.setHeading("Table Finder...");
		this.setResizable(false);
		this.setPixelSize(800, 500);
		
		ButtonBar searchBar = new ButtonBar(); 

		FieldLabel searchField = new FieldLabel(accountNameText, "계좌명");
		accountNameText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
//		searchField.setLabelWidth(40);
		searchField.setWidth(250);
		searchBar.add(searchField);
		
		TextButton retrieveButton = new TextButton("조회");
//		retrieveButton.setWidth(50);
		retrieveButton.setIcon(ResourceIcon.INSTANCE.search16Button());
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		searchBar.add(retrieveButton);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(searchBar, new VerticalLayoutData(1, 50));
		vlc.add(grid, new VerticalLayoutData(1, 1));
		
		this.add(vlc);

		TextButton okButton = new TextButton("확인");
		okButton.setIcon(ResourceIcon.INSTANCE.select16Button());
//		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				confirm();
			}
		});
		this.addButton(okButton);

		TextButton cancelButton = new TextButton("취소");
		cancelButton.setIcon(ResourceIcon.INSTANCE.cancel16Button());
//		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				confirm(); 
			}
		}); 
		
		this.show();
		this.retrieve();
	}
	
	private Grid<Cst02_AccountModel> buildGrid() {
		Cst02_AccountModelProperties properties = GWT.create(Cst02_AccountModelProperties.class);
		GridBuilder<Cst02_AccountModel> gridBuilder = new GridBuilder<Cst02_AccountModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.accountName(), 250, "계좌명");
		gridBuilder.addText(properties.accountNo(), 160, "계좌번호");
		gridBuilder.addText(properties.fundCode(), 90, "펀드코드");

		return gridBuilder.getGrid();
	}

	private void retrieve() {

		GridRetrieveData<Cst02_AccountModel> service = new GridRetrieveData<Cst02_AccountModel>(grid.getStore());

		String accountName = accountNameText.getText() ; 
		if(accountName != null) {
			accountName = "%" + accountName + "%";  
		}
		else {
			accountName = "%"; 
		}
		service.addParam("userId", LoginUser.getUserId());
		service.addParam("accountName", accountName);
		service.retrieve("cst.Cst02_Account.selectByAccountName");
	}

	private void confirm() {
		if(this.callback != null) {
			Cst02_AccountModel selectTabCommentsModel = this.grid.getSelectionModel().getSelectedItem(); 
			if(selectTabCommentsModel != null) {
				this.callback.execute(selectTabCommentsModel);
			}
			else {
				new SimpleMessage("조회할 Table을 선택하여 주세요."); 
				return; 
			}
		}
		this.hide(); 
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() > 0 ) {
			callback.execute(null);
			this.hide(); 
		}
		else {
			new SimpleMessage("error"); 
		}
	}
}
