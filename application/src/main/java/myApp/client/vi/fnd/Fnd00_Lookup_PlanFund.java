package myApp.client.vi.fnd;

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
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

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

public class Fnd00_Lookup_PlanFund extends Window implements InterfaceServiceCall {

	private TextField fundNameText = new TextField();
	private Grid<Fnd00_FundModel> grid = this.buildGrid();
	private InterfaceCallbackResult callback;
	private CheckBox closeYnCheckBox = new CheckBox();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode","%", "전체", new InterfaceCallback() {
		@Override
		public void execute() {
			retrieve(); 
		}
	});

	public void open (InterfaceCallbackResult callback) {
		
		this.callback = callback;

		this.setModal(true);
		this.setHeading("펀드찾기");
		this.setResizable(false);
		this.setPixelSize(800, 500);
		
		ButtonBar searchBar = new ButtonBar(); 

		FieldLabel fundTypeField = new FieldLabel(fundTypeComboBox, "펀드유형");
		fundTypeComboBox.setValue("전체");
		fundTypeField.setLabelWidth(56);
		fundTypeField.setWidth(200);
		searchBar.add(fundTypeField);
		
		searchBar.add(new LabelToolItem(""));
		
		closeYnCheckBox.setBoxLabel("종료펀드포함");
		closeYnCheckBox.setValue(false);
		searchBar.add(closeYnCheckBox);
		
		FieldLabel searchField = new FieldLabel(fundNameText, "기획펀드명");
		fundNameText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		searchField.setLabelWidth(76);
		searchField.setWidth(290);
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
		fundNameText.setEmptyText("전체");
	}
	
	private Grid<Fnd00_FundModel> buildGrid() {
		Fnd00_FundModelProperties properties = GWT.create(Fnd00_FundModelProperties.class);
		GridBuilder<Fnd00_FundModel> gridBuilder = new GridBuilder<Fnd00_FundModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.fundCode(), 70, "펀드코드");
		gridBuilder.addText(properties.fundName(), 250, "기획펀드명");
		gridBuilder.addText(properties.fundTypeName(), 100, "기획펀드유형");
		gridBuilder.addText(properties.orgCodeName(), 120, "운용부서");
		gridBuilder.addDate(properties.startDate(), 90, "설정일");
		gridBuilder.addText(properties.closeName(), 70, "해지구분");
//		gridBuilder.addText(properties.closeYn(), 80, "해지구분");
//		gridBuilder.addDate(properties.endDate(), 90, "해지일");
//		gridBuilder.addText(properties.planYn(), 120, "상품구분");

		return gridBuilder.getGrid();
	}

	private void retrieve() {

//		Info.display("", fundTypeComboBox.getCode());
		GridRetrieveData<Fnd00_FundModel> service = new GridRetrieveData<Fnd00_FundModel>(grid.getStore());
		service.addParam("companyId" , LoginUser.getCompanyId());
		service.addParam("closeYn", closeYnCheckBox.getValue().toString());
		
		String fundName = fundNameText.getText() ; 
		if(fundName != null) {
			fundName = "%" + fundName + "%";  
		}
		else {
			fundName = "%"; 
		}
		service.addParam("fundName", fundName);
		service.addParam("fundTypeCode", fundTypeComboBox.getCode());
		service.retrieve("fnd.Fnd00_Fund.selectByLookupFundPlan");
	}

	private void confirm() {
		if(this.callback != null) {
			Fnd00_FundModel selectFundModel = this.grid.getSelectionModel().getSelectedItem(); 
			if(selectFundModel != null) {
				this.callback.execute(selectFundModel);
			}
			else {
				new SimpleMessage("조회할 펀드를 선택하여 주세요."); 
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
