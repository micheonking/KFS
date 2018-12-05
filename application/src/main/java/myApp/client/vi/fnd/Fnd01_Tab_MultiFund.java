
package myApp.client.vi.fnd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
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

public class Fnd01_Tab_MultiFund extends BorderLayoutContainer implements InterfaceServiceCall {

	private Long		  createId;
	private TextField	  searchText = new TextField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode","%", "전체");

	private Object obj;
	private InterfaceCallbackResult callback;

	private Fnd00_FundModelProperties properties = GWT.create(Fnd00_FundModelProperties.class);
	private Grid<Fnd00_FundModel> grid = this.buildGrid();

	private String gubun = "";
	
	public void open (long createId, String gubun, Object obj, InterfaceCallbackResult callback) {
		this.obj = obj;
		this.createId = createId;
		this.callback = callback;
		this.gubun    = gubun;

		//this.setPixelSize(800, 500);
		
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
		
		FieldLabel searchComboBox = new FieldLabel(fundTypeComboBox, "펀드유형");
		fundTypeComboBox.setValue("전체");
		searchComboBox.setLabelWidth(50);
		searchComboBox.setWidth(200);
		searchComboBox.setLabelSeparator(" ");
		searchBar.add(searchComboBox);
		
		fundTypeComboBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		
		TextButton retrieveFundButton = new TextButton("펀드조회");
		retrieveFundButton.setWidth(100);
		retrieveFundButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		searchBar.add(retrieveFundButton);
		
		TextButton retrieveDocButton = new TextButton("문서조회");
		retrieveDocButton.setWidth(100);
		retrieveDocButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				if(gubun.equals("Dcr04_Tab_FundDoc")) {
//					Dcr04_Tab_FundDoc aa = (Dcr04_Tab_FundDoc)obj;
//					aa.retrieve();
//				} else {
//					retrieve();
//				}
			}
		});
		searchBar.add(retrieveDocButton);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(searchBar, new VerticalLayoutData(1,40));
		vlc.add(grid, new VerticalLayoutData(1, 185));
		
		this.add(vlc);

		this.retrieve();
	}
	
	private Grid<Fnd00_FundModel> buildGrid() {
		
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
		service.addParam("createId"  , this.createId);
		service.addParam("searchText", searchText.getText());
		service.addParam("fundType"  , fundTypeComboBox.getCode());
		service.addParam("docId"  , this.createId);
		service.retrieve("fnd.Fnd00_Fund.selectByDocIdText", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
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
