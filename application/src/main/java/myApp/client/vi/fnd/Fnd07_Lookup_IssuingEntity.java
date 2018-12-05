
package myApp.client.vi.fnd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModel;
import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModelProperties;

public class Fnd07_Lookup_IssuingEntity extends Window {

	Fnd07_IssuingEntityModel issuingEntityModel = new Fnd07_IssuingEntityModel();

	private TextField searchText = new TextField();
//	private InterfaceCallbackResult callback;

	private Fnd07_IssuingEntityModelProperties properties = GWT.create(Fnd07_IssuingEntityModelProperties.class);
	private Grid<Fnd07_IssuingEntityModel> grid = this.buildGrid();

	public void open (InterfaceCallbackResult callback) {
		
//		this.callback = callback;

		this.setModal(true);
		this.setHeading("기관찾기");
		this.setResizable(false);
		this.setPixelSize(500, 500);
		
		ButtonBar searchBar = new ButtonBar(); 
		
		FieldLabel searchField = new FieldLabel(searchText, "검색");
		searchField.setLabelWidth(30);
		searchField.setWidth(250);
		searchField.setLabelSeparator(" ");
		searchBar.add(searchField);
		
		searchText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});

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
		vlc.add(grid, new VerticalLayoutData(1,1));
		grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				issuingEntityModel = grid.getSelectionModel().getSelectedItem();
				callback.execute(issuingEntityModel);
				hide(); 
			}
		});
		
		this.add(vlc);

		TextButton okButton = new TextButton("확인");
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				issuingEntityModel = grid.getSelectionModel().getSelectedItem();
				callback.execute(issuingEntityModel);
				hide(); 
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
	
	private Grid<Fnd07_IssuingEntityModel> buildGrid() {
		
		GridBuilder<Fnd07_IssuingEntityModel> gridBuilder = new GridBuilder<Fnd07_IssuingEntityModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.issuingEntityCode(), 70, "기관코드");
		gridBuilder.addText(properties.issuingEntityName(), 350, "기관명");

		return gridBuilder.getGrid();
	}

	private void retrieve() {
		grid.mask("Loading");
		GridRetrieveData<Fnd07_IssuingEntityModel> service = new GridRetrieveData<Fnd07_IssuingEntityModel>(grid.getStore());
		service.addParam("searchText", searchText.getText());
		service.retrieve("fnd.Fnd07_IssuingEntity.selectBysearchText", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
}
