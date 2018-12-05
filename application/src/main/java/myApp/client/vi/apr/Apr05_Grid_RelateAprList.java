package myApp.client.vi.apr;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr03_RelateFundModel;
import myApp.client.vi.apr.model.Apr03_RelateFundModelProperties;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Apr05_Grid_RelateAprList extends VerticalLayoutContainer {
	
	public Grid<Apr03_RelateFundModel> grid = this.buildGrid();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();

	public Apr05_Grid_RelateAprList(){
		init(false);
	}

	public Apr05_Grid_RelateAprList(Boolean editable){
		init(editable);
	}

	public void init(Boolean editable) {
		this.add(this.grid, new VerticalLayoutData(1, 1)); 
	}

     public Grid<Apr03_RelateFundModel> buildGrid(){
		
    	 Apr03_RelateFundModelProperties properties = GWT.create(Apr03_RelateFundModelProperties.class);
    	 GridBuilder<Apr03_RelateFundModel> gridBuilder = new GridBuilder<Apr03_RelateFundModel>(properties.keyId());

    	 gridBuilder.addText(properties.title(), 250, "상신제목");
    	 gridBuilder.addText(properties.fundName(), 250, "펀드명");
    	 gridBuilder.addDate(properties.regDate(), 100, "등록일");
    	 gridBuilder.addText(properties.empName(), 100, "등록자");

    	 return gridBuilder.getGrid(); 
     }
	
	public void retrieve(Dcr01_ClassTreeModel classTreeModel) {
		this.classTreeModel = classTreeModel; 
		this.grid.getStore().clear();
		this.retrieve();
	}
	
	public void retrieve() {
		this.grid.mask("Loading");
		GridRetrieveData<Apr03_RelateFundModel> service = new GridRetrieveData<Apr03_RelateFundModel>(this.grid.getStore());
		service.addParam("classTreeId", this.classTreeModel.getClassTreeId());
		service.addParam("orgId", LoginUser.getOrgCodeId());
		service.retrieve("apr.Apr03_RelateFund.selectByClassTreeId", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
}
