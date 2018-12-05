
package myApp.client.vi.dbm;

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
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModel;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModelProperties;

public class Dbm01_Lookup_Tables extends Window implements InterfaceServiceCall {

	private TextField TableNameText = new TextField();
	private Grid<Dbm01_TabCommentsModel> grid = this.buildGrid();
	private InterfaceCallbackResult callback;
//	private ComboBoxField tableNameComboBox = new ComboBoxField("FundTypeCode","Y");
//	private ComboBoxField tableNameComboBox = new ComboBoxField("FundTypeCode","Y", new InterfaceCallback() {
//		@Override
//		public void execute() {
//			retrieve(); 
//		}
//	});

	public void open (InterfaceCallbackResult callback) {
		
		this.callback = callback;

		this.setModal(true);
		this.setHeading("Table Finder...");
		this.setResizable(false);
		this.setPixelSize(800, 500);
		
		ButtonBar searchBar = new ButtonBar(); 

		FieldLabel searchField = new FieldLabel(TableNameText, "Name");
		TableNameText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		searchField.setLabelWidth(40);
		searchField.setWidth(250);
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
		vlc.add(searchBar, new VerticalLayoutData(1, 50));
		vlc.add(grid, new VerticalLayoutData(1, 1));
		
		this.add(vlc);

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
		
		this.grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				confirm(); 
			}
		}); 
		
		this.show();
		this.retrieve();
	}
	
	private Grid<Dbm01_TabCommentsModel> buildGrid() {
		Dbm01_TabCommentsModelProperties properties = GWT.create(Dbm01_TabCommentsModelProperties.class);
		GridBuilder<Dbm01_TabCommentsModel> gridBuilder = new GridBuilder<Dbm01_TabCommentsModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.tableName(), 160, "Table Name");
		gridBuilder.addText(properties.tableComments(), 250, "Comments");
		gridBuilder.addText(properties.tableType(), 90, "Table Type");
		gridBuilder.addText(properties.tablespaceName(), 110, "Tablespace Name");

		return gridBuilder.getGrid();
	}

	private void retrieve() {

		GridRetrieveData<Dbm01_TabCommentsModel> service = new GridRetrieveData<Dbm01_TabCommentsModel>(grid.getStore());

		String tableName = TableNameText.getText() ; 
		if(tableName != null) {
			tableName = "%" + tableName + "%";  
		}
		else {
			tableName = "%"; 
		}
		service.addParam("tableName", tableName);
		service.retrieve("dbm.Dbm01_TabComments.selectByTableName");
	}

	private void confirm() {
		if(this.callback != null) {
			Dbm01_TabCommentsModel selectTabCommentsModel = this.grid.getSelectionModel().getSelectedItem(); 
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
