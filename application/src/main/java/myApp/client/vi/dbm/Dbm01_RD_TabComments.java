package myApp.client.vi.dbm;

import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModel;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModelProperties;

public class Dbm01_RD_TabComments extends BorderLayoutContainer implements InterfaceGridOperate {

    private Dbm01_TabCommentsModelProperties properties = GWT.create(Dbm01_TabCommentsModelProperties.class);
	private Dbm01_TabCommentsModel findTableModel = new Dbm01_TabCommentsModel(); 
	private Grid<Dbm01_TabCommentsModel> grid = this.buildGrid();
//	private Dbm01_TabCommentsModel tabCommentsModel = new Dbm01_TabCommentsModel(); 

	private Dbm01_RDPage_ColComments rdPagePerson = new Dbm01_RDPage_ColComments();
	private LookupTriggerField lookupTableField = new LookupTriggerField();
	
	public Dbm01_RD_TabComments() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
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
		westLayoutData.setMargins(new Margins(0, 4, 0, 0));
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		this.setWestWidget(this.grid, westLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData(0.5);
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(1000);
		centerLayoutData.setMargins(new Margins(0, 2, 0, 0));
		this.setCenterWidget(this.rdPagePerson,centerLayoutData);

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Dbm01_TabCommentsModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Dbm01_TabCommentsModel> event) {
				Dbm01_TabCommentsModel data = grid.getSelectionModel().getSelectedItem(); 
				
				if(data != null) {
					String tableName = data.getTableName();  
					rdPagePerson.retrieve(tableName);
				}
			}
		});
		lookupTableField.setEmptyText("전체");
	}
	
	public Grid<Dbm01_TabCommentsModel> buildGrid(){
		
		GridBuilder<Dbm01_TabCommentsModel> gridBuilder = new GridBuilder<Dbm01_TabCommentsModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.tableName()		,	160	,	"Table Name");
		gridBuilder.addText(properties.tableComments()	,	250	,	"Comments");
		gridBuilder.addText(properties.tableType()		,	70	,	"Type");
		gridBuilder.addText(properties.tablespaceName()	,	110	,	"Tablespace");

		return gridBuilder.getGrid();
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

	@Override
	public void retrieve(){
		GridRetrieveData<Dbm01_TabCommentsModel> service = new GridRetrieveData<Dbm01_TabCommentsModel>(grid.getStore());
		service.addParam("tableName", "%" + lookupTableField.getText() + "%"); 
		service.retrieve("dbm.Dbm01_TabComments.selectByTableName");
	}

	@Override
	public void insertRow(){
	}

	@Override
	public void deleteRow() {
	}

	@Override
	public void update() {
	}

}