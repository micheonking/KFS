package myApp.client.vi.fnd;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.fnd.model.Fnd05_MemecoModel;
import myApp.client.vi.fnd.model.Fnd05_MemecoModelProperties;

public class Fnd05_Tab_Memeco extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd05_MemecoModelProperties properties = GWT.create(Fnd05_MemecoModelProperties.class);
	private Grid<Fnd05_MemecoModel> grid = this.buildGrid();
	private TextField codeNameField = new TextField();

	public Fnd05_Tab_Memeco() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(codeNameField, "매매기관명:", 400, 80, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addInsertButton(); 
		searchBarBuilder.addDeleteButton(); 
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData .setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData .setSplit(true);
		centerLayoutData .setMaxSize(1000);

		this.setCenterWidget(this.grid, centerLayoutData);

		codeNameField.setValue("%");
	}

	public Grid<Fnd05_MemecoModel> buildGrid(){
		
		GridBuilder<Fnd05_MemecoModel> gridBuilder = new GridBuilder<Fnd05_MemecoModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText	(properties.memecoCode(),	80,	"매매처코드",		new	TextField());
		gridBuilder.addText	(properties.memecoName(),	250,	"매매처명",			new	TextField());
		gridBuilder.addBoolean(properties.useYn(),		70,	"사용여부");
		gridBuilder.addText	(properties.note(),			300,	"비고",				new	TextField());
		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<Fnd05_MemecoModel> service = new GridRetrieveData<Fnd05_MemecoModel>(grid.getStore());
		service.addParam("codeName", codeNameField.getText()); 
		service.retrieve("fnd.Fnd05_Memeco.selectByCodeName");
	}

	@Override
	public void update(){
		GridUpdate<Fnd05_MemecoModel> service = new GridUpdate<Fnd05_MemecoModel>(); 
		service.update(grid.getStore(), "fnd.Fnd05_Memeco.update"); 
	}

	@Override
	public void insertRow(){
		GridInsertRow<Fnd05_MemecoModel> service = new GridInsertRow<Fnd05_MemecoModel>(); 
		Fnd05_MemecoModel fundModel = new Fnd05_MemecoModel();
		service.insertRow(grid, fundModel);
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Fnd05_MemecoModel> service = new GridDeleteData<Fnd05_MemecoModel>();
		List<Fnd05_MemecoModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd05_Memeco.delete");
	}

}
