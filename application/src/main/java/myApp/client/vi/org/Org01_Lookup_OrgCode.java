package myApp.client.vi.org;

import java.util.Date;

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
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.vi.org.model.Org01_CodeModelProperties;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;

/*
 * 다른거 `1차 변경.....신.,...
 * 다른거 2차 변경....신....
 */


public class Org01_Lookup_OrgCode extends Window {
	
	private InterfaceCallbackResult callback ; 
	private Grid<Org01_CodeModel> grid = this.buildGrid(); 
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
		this.setHeading("조직찾기");
		this.setResizable(false);
		this.setPixelSize(700, 500);
		
		ButtonBar searchBar = new ButtonBar();
		
		FieldLabel dateFieldLabel = new FieldLabel(baseDateField, "기준일");
		searchBar.add(dateFieldLabel);
		dateFieldLabel.setWidth(120);
		dateFieldLabel.setLabelWidth(40); 
		baseDateField.setEnabled(false);
		baseDateField.setHideTrigger(true);
		
		FieldLabel searchField = new FieldLabel(korName, "조직명");
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
		vlc.add(searchBar, new VerticalLayoutData(1, 40)); // , new Margins(0, 0, 0, 5)));

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
	
	private Grid<Org01_CodeModel> buildGrid(){
		
		Org01_CodeModelProperties properties = GWT.create(Org01_CodeModelProperties.class);
		GridBuilder<Org01_CodeModel> gridBuilder = new GridBuilder<Org01_CodeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.orgCode(), 60, "조직코드") ;
		gridBuilder.addText(properties.korName(), 200, "조직명") ;
		gridBuilder.addText(properties.levelName(), 60, "조직구분") ;
		gridBuilder.addDate(properties.modDate(), 80, "최근변경일") ;
		gridBuilder.addText(properties.modReason(), 300, "변경사유") ;
	
		return gridBuilder.getGrid(); 
	}
	
	public void retrieve() {
		GridRetrieveData<Org01_CodeModel> service = new GridRetrieveData<Org01_CodeModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("korName", this.korName.getValue());
		service.addParam("baseDate", this.baseDateField.getValue());
		service.retrieve("org.Org01_Code.selectByKorName");
	}

	public void confirm() {
		Org01_CodeModel orgCodeModel = grid.getSelectionModel().getSelectedItem(); 
		if(orgCodeModel != null){
			// 선택한 반 정보를 돌려준다. interfaceLookupResult를 상속받은 클래스여야 한다. 
			callback.execute(orgCodeModel);
			this.hide(); 
		}
		else {
			new SimpleMessage("선택된 조직이 없습니다.");
		}
	}

}
