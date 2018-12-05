package myApp.client.vi.fnd;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.CellMouseDownEvent;
import com.sencha.gxt.widget.core.client.event.CellMouseDownEvent.CellMouseDownHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.service.TreeGridUpdate;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.vi.org.model.Org01_CodeModelProperties;

public class Fnd08_Page_OrgAuth extends ContentPanel implements InterfaceServiceCall{
	
	private Org01_CodeModelProperties properties = GWT.create(Org01_CodeModelProperties.class);
	private TreeGrid<Org01_CodeModel> treeGrid = this.buildTreeGrid();
	private Long fundCodeId; 
	
	public Fnd08_Page_OrgAuth(){
		this.setHeaderVisible(false);
		this.add(treeGrid);
		
		TextButton updateButton = new TextButton("저장"); 
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		}); 
		this.getButtonBar().add(updateButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		treeGrid.addCellMouseDownHandler(new CellMouseDownHandler() {
			@Override
			public void onCellMouseDown(CellMouseDownEvent event) {
				if(event.getRowIndex() >= 0) {
					if(event.getCellIndex() == 4) {
						getColumn(event.getRowIndex(), event.getCellIndex());
					}
				}
			}
		}); 
	}

	private void getColumn(int rowIndex, int colIndex) {
		
		treeGrid.getSelectionModel().select(rowIndex, false);
		Org01_CodeModel selectModel = treeGrid.getSelectionModel().getSelectedItem();

		if(selectModel != null) {
			
			treeGrid.setExpanded(selectModel, true, true);
			
			if(colIndex == 4) {
				Boolean authYn = !treeGrid.getStore().getRecord(selectModel).getValue(properties.authYnCheck());
				if(authYn) {
					Org01_CodeModel parentModel = treeGrid.getTreeStore().getParent(selectModel);
					while(parentModel != null) {
						treeGrid.getStore().getRecord(parentModel).addChange(properties.authYnCheck(), authYn);
						parentModel = treeGrid.getTreeStore().getParent(parentModel);
					}
				}
				// child setting 
				for(Org01_CodeModel childModel : treeGrid.getTreeStore().getAllChildren(selectModel)) {
					treeGrid.getStore().getRecord(childModel).addChange(properties.authYnCheck(), authYn);
				}
			}
		}
	}
	
	public TreeGrid<Org01_CodeModel> buildTreeGrid(){
		GridBuilder<Org01_CodeModel> gridBuilder = new GridBuilder<Org01_CodeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.korName(), 300, "조직명");
		gridBuilder.addText(properties.orgCode(), 60, "조직코드");
		gridBuilder.addBoolean(properties.authYnCheck(), 40, "권한"); //, checkBoxCell);
		gridBuilder.addText(properties.note(), 300, "비고");
		 
//		gridBuilder.addText(properties.note(), 400, "상세설명"); 
		return gridBuilder.getTreeGrid(2);
	}

	public void retrieve() {
		ServiceRequest request = new ServiceRequest("org.Org01_Code.selectByFundCodeId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putLongParam("fundCodeId", this.fundCodeId);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addChildItem(Org01_CodeModel parentItem) {
		// 조회 시 하위 노드 생성 함수. 
		if(parentItem.getChildList() != null){
			for(GridDataModel child : parentItem.getChildList()){
				
				Org01_CodeModel childClassModel = (Org01_CodeModel)child; 
				this.treeGrid.getTreeStore().add(parentItem, childClassModel);
				this.addChildItem(childClassModel);
			}
		}
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0) {
		}
		else {
			
			this.treeGrid.getTreeStore().clear();
			for(GridDataModel data : result.getResult()) {
				Org01_CodeModel item = (Org01_CodeModel)data;
				this.treeGrid.getTreeStore().add(item);
				this.addChildItem(item);
			}
			
			this.treeGrid.expandAll();
		}
	}
	
	private void update(){
		TreeGridUpdate<Org01_CodeModel> service = new TreeGridUpdate<Org01_CodeModel>(); 
		service.addParam("fundCodeId", this.fundCodeId);
		service.update(this.treeGrid.getTreeStore(), "org.Org01_Code.updateFundAuth", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				retrieve(); // 재조회한다.  
			}
		});
	}
	
	public void retrieve(Long fundCodeId) {
		this.fundCodeId = fundCodeId;  
		this.retrieve();
	}

}