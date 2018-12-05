
package myApp.client.vi.sys;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys09_CodeModel;
import myApp.client.vi.sys.model.Sys09_CodeModelProperties;

public class Sys09_Lookup_Code extends Window {

	Sys09_CodeModel codeModel = new Sys09_CodeModel();

	private InterfaceCallbackResult callback;
	private String codeKind;

	private Sys09_CodeModelProperties properties = GWT.create(Sys09_CodeModelProperties.class);
	private Grid<Sys09_CodeModel> grid = this.buildGrid();

	public void open (String codeKind, InterfaceCallbackResult callback) {
		
		this.codeKind = codeKind;
		this.callback = callback;

		this.setModal(true);
		this.setHeading("코드찾기");
		this.setResizable(false);
		this.setPixelSize(500, 500);
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(grid, new VerticalLayoutData(1,1));
		grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				codeModel = grid.getSelectionModel().getSelectedItem();
				callback.execute(codeModel);
				hide(); 
			}
		});

		this.add(vlc);

		TextButton okButton = new TextButton("확인");
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				codeModel = grid.getSelectionModel().getSelectedItem();
				if (codeModel != null) {
					callback.execute(codeModel);
				}
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
	
	private Grid<Sys09_CodeModel> buildGrid() {
		
		GridBuilder<Sys09_CodeModel> gridBuilder = new GridBuilder<Sys09_CodeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.code(), 70, "코드");
		gridBuilder.addText(properties.name(), 350, "코드명");

		return gridBuilder.getGrid();
	}

	private void retrieve() {
		grid.mask("Loading");
		GridRetrieveData<Sys09_CodeModel> service = new GridRetrieveData<Sys09_CodeModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("kindCode" , this.codeKind);
		service.addParam("applyDate", LoginUser.getToday());
		
		service.retrieve("sys.Sys09_Code.selectByCodeKind", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
}
