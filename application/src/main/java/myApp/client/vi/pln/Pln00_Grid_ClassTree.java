package myApp.client.vi.pln;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModelProperties;

public class Pln00_Grid_ClassTree extends BorderLayoutContainer {

	private Grid<Dcr01_ClassTreeModel> grid = this.buildGrid();
	private Long classTreeId;

	
	public Pln00_Grid_ClassTree(){
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);

		cp.add(this.grid, new VerticalLayoutData(1, 1));//그리드 표현

		BorderLayoutData northLayoutData = new BorderLayoutData(0.5);
		northLayoutData.setSplit(true);
		northLayoutData.setMaxSize(1000);
		northLayoutData.setMargins(new Margins(0, 0, 2, 0));
//		this.setNorthWidget(cp, northLayoutData);

		this.setCenterWidget(cp, northLayoutData); 
	}
	
	public Grid<Dcr01_ClassTreeModel> buildGrid(){
		
		Dcr01_ClassTreeModelProperties properties = GWT.create(Dcr01_ClassTreeModelProperties.class);		
		GridBuilder<Dcr01_ClassTreeModel> gridBuilder = new GridBuilder<Dcr01_ClassTreeModel>(properties.keyId());  
		
		gridBuilder.setChecked(SelectionMode.MULTI);
		gridBuilder.addText(properties.classTreeCode(), 80, "구분코드");
		gridBuilder.addText(properties.classTreeName(), 220, "문서구분명");
		gridBuilder.addText(properties.itemTypeName(), 100, "상품분류");
//		gridBuilder.addText(properties.approvalTypeName(), 100, "결재구분");
//		gridBuilder.addText(properties.sealName(), 100, "인감종류");
//		gridBuilder.addText(properties.seq(), 60, "정렬순서");
//		gridBuilder.addText(properties.note(), 240, "비고");

		return gridBuilder.getGrid(); 
	}
	public List<Dcr01_ClassTreeModel> getSelectList() {
		
		return this.grid.getSelectionModel().getSelectedItems();
		
	}

	public void retrieve(Dcr01_ClassTreeModel classTreeModel) {
		
		this.grid.getStore().clear();
		
		if(classTreeModel.getChildList().size() > 0) {
			// 하위 분류가 더 있다.
		}
		else {
			this.classTreeId = classTreeModel.getClassTreeId();
			
			GridRetrieveData<Dcr01_ClassTreeModel> service = new GridRetrieveData<Dcr01_ClassTreeModel>(this.grid.getStore());
			service.addParam("companyId", LoginUser.getCompanyId());
			service.addParam("parentId", classTreeModel.getClassTreeId());
			service.retrieve("dcr.Dcr01_ClassTree.selectByClassTreeId");
		}
	}
	
}
