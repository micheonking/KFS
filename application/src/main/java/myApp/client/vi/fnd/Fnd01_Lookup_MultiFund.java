
package myApp.client.vi.fnd;

import java.util.ArrayList;
import java.util.List;

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
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.fnd.model.Fnd00_FundModelProperties;

public class Fnd01_Lookup_MultiFund extends Window implements InterfaceServiceCall {
	
	private TextField	  searchText = new TextField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode","%", "전체");
	private CheckBox closeYnCheckBox = new CheckBox();
	private InterfaceCallbackResult callback;
	private Grid<Fnd00_FundModel> grid = this.buildGrid();
	private List<Long> selectedFundList = new ArrayList<Long>();  

	public void open (List<Long> selectedFundList, InterfaceCallbackResult callback) {
		this.selectedFundList = selectedFundList ; 
		open(callback);
	}
	
	public void open (InterfaceCallbackResult callback) {
		
		this.callback = callback;

		this.setModal(true);
		this.setHeading("펀드찾기");
		this.setResizable(false);
		this.setPixelSize(800, 500);
		
		ButtonBar searchBar = new ButtonBar(); 
		
		FieldLabel searchField = new FieldLabel(searchText, "검색");
		searchField.setLabelWidth(30);
		searchField.setWidth(250);
		searchField.setLabelSeparator(" ");
		searchBar.add(searchField);
		
		searchText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) { // enter key event 
					retrieve();
				}
			}
		});
		
		FieldLabel searchComboBox = new FieldLabel(fundTypeComboBox, "펀드유형");
		fundTypeComboBox.setValue("전체");
		searchComboBox.setLabelWidth(50);
		searchComboBox.setWidth(200);
		searchComboBox.setLabelSeparator(" ");
		searchBar.add(searchComboBox);

		closeYnCheckBox.setBoxLabel("해지펀드포함");
		closeYnCheckBox.setValue(false);
		searchBar.add(closeYnCheckBox);
		
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
		vlc.add(this.grid, new VerticalLayoutData(1,1));
		
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
		
		this.show();
		this.retrieve();
	}
	
	private Grid<Fnd00_FundModel> buildGrid() {
		Fnd00_FundModelProperties properties = GWT.create(Fnd00_FundModelProperties.class);
		GridBuilder<Fnd00_FundModel> gridBuilder = new GridBuilder<Fnd00_FundModel>(properties.keyId());
		
		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.fundCode(), 70, "펀드코드");
		gridBuilder.addText(properties.fundName(), 350, "펀드명");
		gridBuilder.addText(properties.fundTypeName(), 100, "펀드유형");
		gridBuilder.addDate(properties.startDate(), 90, "설정일");
		gridBuilder.addText(properties.empName1(), 70, "운용역");

		return gridBuilder.getGrid();
	}

	private void retrieve() {
		
		grid.mask("Loading");
		
		GridRetrieveData<Fnd00_FundModel> service = new GridRetrieveData<Fnd00_FundModel>(grid.getStore());
		service.addParam("companyId" , LoginUser.getCompanyId());
		service.addParam("orgId" 	 , LoginUser.getOrgCodeId());
		service.addParam("searchText", searchText.getText());
		service.addParam("fundType"  , fundTypeComboBox.getCode());
		service.addParam("closeYn"	 , closeYnCheckBox.getValue().toString());
		service.addParam("fundIdList", this.selectedFundList);
		service.retrieve("fnd.Fnd00_Fund.selectBySearchText", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}

	private void confirm() {
		List<Fnd00_FundModel> selectList = grid.getSelectionModel().getSelectedItems();
		
		if(selectList != null) {
			this.callback.execute(selectList);
			hide();
		}
		else {
			new SimpleMessage("조회할 펀드를 선택하여 주세요."); 
			return; 
		}
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

	public void setMulti() {
		grid.getSelectionModel().setSelectionMode(SelectionMode.MULTI);
	}

}

//for(int i = 0; i < list.size(); i++) {
//if(i + 1 == list.size()) {
//	fundIdList += list.get(i);
//} else {
//	fundIdList += list.get(i) + ",";
//}
//}
