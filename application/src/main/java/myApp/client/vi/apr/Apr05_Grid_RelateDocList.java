package myApp.client.vi.apr;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.apr.model.Apr05_RelateDocModel;
import myApp.client.vi.apr.model.Apr05_RelateDocModelProperties;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Apr05_Grid_RelateDocList extends VerticalLayoutContainer {
	
	public Grid<Apr05_RelateDocModel> grid = this.buildGrid();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();

	public Apr05_Grid_RelateDocList(){
		init(false);
	}

	public Apr05_Grid_RelateDocList(Boolean editable){
		init(editable);
	}

	public void init(Boolean editable) {
		this.add(this.grid, new VerticalLayoutData(1, 1)); 
	}

     public Grid<Apr05_RelateDocModel> buildGrid(){
		
    	 Apr05_RelateDocModelProperties properties = GWT.create(Apr05_RelateDocModelProperties.class);
    	 GridBuilder<Apr05_RelateDocModel> gridBuilder = new GridBuilder<Apr05_RelateDocModel>(properties.keyId());

    	 gridBuilder.addText(properties.fileName(), 200, "문서명");
    	 gridBuilder.addText(properties.title(), 200, "상신제목");
    	 gridBuilder.addText(properties.fundName(), 200, "연관펀드명");
    	 gridBuilder.addDate(properties.regDate(), 80, "등록일");
    	 gridBuilder.addText(properties.regEmpInfo(), 80, "등록자");

    	 return gridBuilder.getGrid(); 
     }
	
	public void retrieve(Dcr01_ClassTreeModel classTreeModel) {
		this.classTreeModel = classTreeModel; 
		this.grid.getStore().clear();
		
		this.retrieve();
	}
     
	public void retrieve() {
		this.grid.mask("Loading");
		GridRetrieveData<Apr05_RelateDocModel> service = new GridRetrieveData<Apr05_RelateDocModel>(this.grid.getStore());
		service.addParam("classTreeId", this.classTreeModel.getClassTreeId());
		service.retrieve("apr.Apr05_RelateDoc.selectByClassTreeId", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		});
	}
}
