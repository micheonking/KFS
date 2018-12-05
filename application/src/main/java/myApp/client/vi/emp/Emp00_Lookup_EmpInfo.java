package myApp.client.vi.emp;

import java.util.Date;

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

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.emp.model.Emp00_InfoModelProperties;

public class Emp00_Lookup_EmpInfo extends Window {
	
	private InterfaceCallbackResult callback ; 
	private Grid<Emp00_InfoModel> grid = this.buildGrid(); 
	private TextField korName = new TextField();
    private MyDateField baseDateField = new MyDateField();
	
	public void open(Date baseDate, InterfaceCallbackResult callback) {
		
		this.callback = callback;
		
		//기준일을 세팅
		if(baseDate  == null) {
			new SimpleMessage("기준일은 필수항목입니다."); 
			return ;  
		}
		else {
			baseDateField.setValue(baseDate);
		}
		
		this.setModal(true);
		this.setHeading("사원찾기");
		this.setResizable(false);
		this.setPixelSize(600, 400);
		
		ButtonBar searchBar = new ButtonBar();
		
		FieldLabel dateFieldLabel = new FieldLabel(baseDateField, "기준일");
		searchBar.add(dateFieldLabel);
		dateFieldLabel.setWidth(120);
		dateFieldLabel.setLabelWidth(40); 
		baseDateField.setEnabled(false);
		baseDateField.setHideTrigger(true);
		
		FieldLabel searchField = new FieldLabel(korName, "찾기");
		searchField.setLabelWidth(40);
		searchBar.add(searchField);
		korName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve(); 
				}
			}
		}); 
		
		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.setWidth(50);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		}); 
		searchBar.add(retrieveButton);
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(searchBar, new VerticalLayoutData(1, 46)); // , new Margins(0, 0, 0, 5)));

		vlc.add(this.grid, new VerticalLayoutData(1, 1));
		this.grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				confirm();
			}
		});

		this.add(vlc);
		
		TextButton oKButton = new TextButton("확인");
		oKButton.setWidth(50);
		oKButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				confirm(); 
			}
		}); 
		this.addButton(oKButton);

		TextButton cancelButton = new TextButton("취소");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler(){
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
	
	private Grid<Emp00_InfoModel> buildGrid(){
		
		Emp00_InfoModelProperties properties = GWT.create(Emp00_InfoModelProperties.class);
		GridBuilder<Emp00_InfoModel> gridBuilder = new GridBuilder<Emp00_InfoModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.orgName(), 200, "부서") ;
		gridBuilder.addText(properties.titleName(), 100, "직책") ;
		gridBuilder.addText(properties.empNo(), 80, "사번") ;
		gridBuilder.addText(properties.korName(), 100, "성명") ;		
		//gridBuilder.addText(properties.positionName(), 80, "직위") ;
	
		return gridBuilder.getGrid(); 
	}
	
	public void retrieve() {
		grid.mask("Loading");
		GridRetrieveData<Emp00_InfoModel> service = new GridRetrieveData<Emp00_InfoModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("korName", "%" + this.korName.getText() + "%");
		service.addParam("baseDate", this.baseDateField.getValue());
		service.retrieve("emp.Emp00_Info.selectByText", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}

	public void confirm() {
		Emp00_InfoModel empInfoModel = grid.getSelectionModel().getSelectedItem(); 
		if(empInfoModel == null) {
			new SimpleMessage("선택된 사원이 없습니다."); 
			return ; 
		}
		else {
			this.callback.execute(empInfoModel);
			this.hide(); 
		}
		
		//		Org01_CodeModel orgCodeModel = grid.getSelectionModel().getSelectedItem(); 
//		if(orgCodeModel != null){
//			// 선택한 반 정보를 돌려준다. interfaceLookupResult를 상속받은 클래스여야 한다. 
//			callback.execute(orgCodeModel);
//			this.hide(); 
//		}
//		else {
//			new SimpleMessage("선택된 조직이 없습니다.");
//		}
	}

}
