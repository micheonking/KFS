package myApp.client.vi.dbm;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModel;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModelProperties;

public class Dbm01_Tab_TabComments extends BorderLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall{

    private Dbm01_TabCommentsModelProperties properties = GWT.create(Dbm01_TabCommentsModelProperties.class);
	private Dbm01_TabCommentsModel findTableModel = new Dbm01_TabCommentsModel(); 
	private Grid<Dbm01_TabCommentsModel> grid = this.buildGrid();
//	private TextField tableNameField = new TextField();
	private LookupTriggerField lookupTableField = new LookupTriggerField();

	private Dbm02_TabPage_ColComments colGrid= new Dbm02_TabPage_ColComments(); 
	
	public Dbm01_Tab_TabComments() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
//		searchBarBuilder.addTextField(lookupTableField, "Table Name", 200, 80, true); 
		lookupTableField.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				openLookupTable();
			}
		}); 
		
		FieldLabel tableNameLabel = new FieldLabel(lookupTableField, "Table 찾기:");
		tableNameLabel.setLabelWidth(70);
		tableNameLabel.setWidth(350);
		searchBarBuilder.getSearchBar().add(tableNameLabel); 

		searchBarBuilder.addRetrieveButton();
		searchBarBuilder.addUpdateButton();
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50));

		BorderLayoutData westLayoutData = new BorderLayoutData(0.4);
		westLayoutData .setMargins(new Margins(0, 4, 0, 0));
		westLayoutData .setSplit(true);
		westLayoutData .setMaxSize(1000);

		this.setWestWidget(this.grid, westLayoutData);

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Dbm01_TabCommentsModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Dbm01_TabCommentsModel> event) {
				Dbm01_TabCommentsModel data = grid.getSelectionModel().getSelectedItem(); 
				
				if(data != null) {
					String tableName = data.getTableName();  
					colGrid.retrieve(tableName);
				}
			}
		});
		
		this.setCenterWidget(colGrid);
		lookupTableField.setEmptyText("전체");
	}

	private void openLookupTable() {
		Dbm01_Lookup_Tables lookupTable = new Dbm01_Lookup_Tables();
		lookupTable.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				findTableModel = (Dbm01_TabCommentsModel)result;
				lookupTableField.setValue(findTableModel.getTableName());
			}
		});

	}

	public Grid<Dbm01_TabCommentsModel> buildGrid(){
		
		GridBuilder<Dbm01_TabCommentsModel> gridBuilder = new GridBuilder<Dbm01_TabCommentsModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.tableName(),	160	,	"Table Name");
		
//		TextField textField = new TextField(); 
//		textField.addDomHandler(new KeyDownHandler() {
//		    @Override 
//		    public void onKeyDown(KeyDownEvent event) {
//		        if (KeyCodes.KEY_TAB == event.getNativeEvent().getKeyCode()) {
////		        	Info.display("keydown", textField.getText());
//		        	execute(textField.getText());
//		        }
//		    }
//
//		}, KeyDownEvent.getType() );
		
		gridBuilder.addText(properties.tableComments()	,	250	,	"Comments",	new TextField());
		gridBuilder.addText(properties.tableType()		,	70	,	"Type");
		gridBuilder.addText(properties.tablespaceName()	,	110	,	"Tablespace");

		return gridBuilder.getGrid(); 
	}
	
	public void execute(String comments){

		Dbm01_TabCommentsModel data = grid.getSelectionModel().getSelectedItem(); 
		
		if(data != null) {
			
			String tableName = data.getTableName();

			colGrid.retrieve(tableName);
			
			ServiceRequest request = new ServiceRequest("dbm.Dbm00_DDLRun.callByDDLRun");
			request.putStringParam("para", "comment on table " + tableName + " is '" + comments + "'");
			
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
	}

	@Override
	public void retrieve(){
		GridRetrieveData<Dbm01_TabCommentsModel> service = new GridRetrieveData<Dbm01_TabCommentsModel>(grid.getStore());
		service.addParam("tableName", "%" + lookupTableField.getText() + "%"); 
		service.retrieve("dbm.Dbm01_TabComments.selectByTableName");

	}

	@Override
	public void update() {
//		Dbm01_TabCommentsModel data = grid.getSelectionModel().getSelectedItem(); 
//		
//		execute(data.getTableName());		
		GridUpdate<Dbm01_TabCommentsModel> service = new GridUpdate<Dbm01_TabCommentsModel>(); 
		service.update(this.grid.getStore(), "dbm.Dbm01_TabComments.update"); 
	}

	@Override
	public void insertRow() {
	}

	@Override
	public void deleteRow() {
	}

	@Override
	public void getServiceResult(ServiceResult result) {

		if (result.getStatus() < 0) {
			Info.display("메뉴조회 오류", result.getMessage());
		} 
		else {
			grid.getStore().clear(); // 깨끗이 비운다.
			retrieve();
//			grid.getStore().commitChanges();
		}
	}

}
