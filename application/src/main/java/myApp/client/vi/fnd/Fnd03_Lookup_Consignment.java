
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
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModelProperties;

public class Fnd03_Lookup_Consignment extends Window {

	Fnd03_ConsignmentModel consignmentaModel = new Fnd03_ConsignmentModel();

	private TextField searchText = new TextField();
//	private InterfaceCallbackResult callback;

	private Fnd03_ConsignmentModelProperties properties = GWT.create(Fnd03_ConsignmentModelProperties.class);
	private Grid<Fnd03_ConsignmentModel> grid = this.buildGrid();

	public void open (InterfaceCallbackResult callback) {
		
//		this.callback = callback;

		this.setModal(true);
		this.setHeading("수탁사 검색");
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
				consignmentaModel = grid.getSelectionModel().getSelectedItem();
				callback.execute(consignmentaModel);
				hide(); 
			}
		});
		
		this.add(vlc);

		TextButton okButton = new TextButton("확인");
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				consignmentaModel = grid.getSelectionModel().getSelectedItem();
				callback.execute(consignmentaModel);
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
	
	private Grid<Fnd03_ConsignmentModel> buildGrid() {
		GridBuilder<Fnd03_ConsignmentModel> gridBuilder = new GridBuilder<Fnd03_ConsignmentModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.consignmentCode(), 120, "수탁사 코드");
		gridBuilder.addText(properties.consignmentName(), 300, "수탁사 명");
		return gridBuilder.getGrid();
	}

	private void retrieve() {
		GridRetrieveData<Fnd03_ConsignmentModel> service = new GridRetrieveData<Fnd03_ConsignmentModel>(grid.getStore());
		service.addParam("searchText", searchText.getText());
		service.retrieve("fnd.Fnd03_Consignment.selectByConsignmentName");
	}
}
