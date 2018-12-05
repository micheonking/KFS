package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.AbstractLookupWindow;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceLookupResult;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.client.vi.sys.model.Sys01_CompanyModelProperties;

public class Lookup_AdminCompany extends AbstractLookupWindow {

	private Sys01_CompanyModelProperties properties = GWT.create(Sys01_CompanyModelProperties.class);
	private Grid<Sys01_CompanyModel> grid = this.buildGrid(); 
	private InterfaceLookupResult lookupParent; 
	private TextField companyName = new TextField(); 
	private Long userId; 
	
	public Lookup_AdminCompany(InterfaceLookupResult lookupParent, Long userId){
		this.lookupParent = lookupParent; 
		this.userId = userId;
		this.setInit("미등록 고객선택", 600, 350); 
		this.addLabel(companyName, "고객명", 150, 50, true) ;

		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(this.getSearchBar(), new VerticalLayoutData(1, 40)); // , new Margins(0, 0, 0, 5)));
		vlc.add(grid, new VerticalLayoutData(1, 1));
		this.add(vlc);
		
		this.retrieve();
	}
	
	private Grid<Sys01_CompanyModel> buildGrid(){
		GridBuilder<Sys01_CompanyModel> gridBuilder = new GridBuilder<Sys01_CompanyModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.companyName(), 250, "고객명") ;
		gridBuilder.addText(properties.bizNo(), 100, "사업자번호") ;
		gridBuilder.addText(properties.telNo01(), 100, "대표전화번호") ;
		gridBuilder.addText(properties.note(), 400, "비고");
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		GridRetrieveData<Sys01_CompanyModel> service = new GridRetrieveData<Sys01_CompanyModel>(grid.getStore());
		service.addParam("userId", userId);
		service.addParam("companyName", companyName.getValue());
		service.retrieve("sys.Sys01_Company.selectByNotAssignedCompany");
	}

	@Override
	public void confirm() {
		List<Sys01_CompanyModel> companyList = this.grid.getSelectionModel().getSelectedItems(); 
		if(companyList.size() < 1 ){
			Info.display("선택확인", "선택된 고객정보가 없습니다.");
			return ; 
		}
		else { 
			lookupParent.setLookupResult(companyList); // list company 
		} 
	}

	@Override
	public void cancel() {
	}
}
