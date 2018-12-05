package myApp.client.vi.sys;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.AbstractLookupWindow;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.client.vi.sys.model.Sys01_CompanyModelProperties;

public class Lookup_Company extends AbstractLookupWindow {

	private Sys01_CompanyModelProperties properties = GWT.create(Sys01_CompanyModelProperties.class); // 계약정보로 대체되어야 한다.
	private Grid<Sys01_CompanyModel> grid = this.buildGrid(); 
	
	public Lookup_Company(){
		
		this.setInit("기관을 선택해 주세요.", 600, 350);
		this.add(this.grid); 
		this.grid.addRowDoubleClickHandler(new RowDoubleClickHandler(){
			@Override
			public void onRowDoubleClick(RowDoubleClickEvent event) {
				confirm();
			}
		}); 

		this.retrieve(); 
	}
	
	private Grid<Sys01_CompanyModel> buildGrid(){
		GridBuilder<Sys01_CompanyModel> gridBuilder = new GridBuilder<Sys01_CompanyModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.companyName(), 150, "회사명") ;
		gridBuilder.addText(properties.telNo01(), 100, "대표전화") ;
		gridBuilder.addText(properties.note(), 400, "비고");
		return gridBuilder.getGrid(); 
	}
	
	
	@Override
	public void retrieve(){
		GridRetrieveData<Sys01_CompanyModel> service = new GridRetrieveData<Sys01_CompanyModel>(grid.getStore());
		service.retrieve("sys.Sys01_Company.selectByAll");
	}

	@Override
	public void cancel() {
		this.hide(); 
	}

	@Override
	public void confirm() {
		Sys01_CompanyModel companyModel = this.grid.getSelectionModel().getSelectedItem();

		if(companyModel != null){
			this.getCallback().setLookupResult(companyModel);
			this.hide();
		}
		else {
			new SimpleMessage("유치원 확인", "유치원이 선택되지 않았습니다.");
			return; 
		}
		return ; 
	}

}
