package myApp.client.vi.pln;

import java.util.Date;
import java.util.List;

import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.Apr01_Tab_DocManager;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.emp.model.Emp00_InfoModelProperties;

public class Pln00_Popup_ClassTree extends Window {
	
	private InterfaceCallbackResult callback ; 
	private TextField docName = new TextField();
	private Pln00_Lookup_ClassTree gridDocType = new Pln00_Lookup_ClassTree();
	
	public void open(Date baseDate, InterfaceCallbackResult callback) {

		this.callback = callback;
		
		this.setModal(true);
		this.setHeading("문서구분");
		this.setResizable(false);
		this.setPixelSize(1000, 600);
				
		TextButton oKButton = new TextButton("확인");
		oKButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				confirm(); 
			}
		});
		this.addButton(oKButton);
		
		TextButton cancelButton = new TextButton("닫기");
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.add(gridDocType);
		this.show();
	}
	
	public void confirm() {
		
		List<Dcr01_ClassTreeModel> selectList = gridDocType.getSelectList();
			this.callback.execute(selectList);
			this.hide(); 
		}
	

	}

