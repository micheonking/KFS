package myApp.client.vi.dcr;

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
import myApp.client.utils.InterfaceTabPage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModelProperties;

public class Dcr03_TabPage_OrgAuth extends ContentPanel implements InterfaceTabPage, InterfaceServiceCall{
	
	private Dcr01_ClassTreeModelProperties properties = GWT.create(Dcr01_ClassTreeModelProperties.class);
	private TreeGrid<Dcr01_ClassTreeModel> treeGrid = this.buildTreeGrid();
	private Long orgCodeId; 
	
	
	public Dcr03_TabPage_OrgAuth(){
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
					if(event.getCellIndex() == 3 || event.getCellIndex() == 4) {
						getColumn(event.getRowIndex(), event.getCellIndex());
					}
				}
			}
		}); 
	}

	private void getColumn(int rowIndex, int colIndex) {
		
		treeGrid.getSelectionModel().select(rowIndex, false);
		Dcr01_ClassTreeModel selectModel = treeGrid.getSelectionModel().getSelectedItem();

		if(selectModel != null) {
			
			treeGrid.setExpanded(selectModel, true, true);
			
			if(colIndex == 3) {
				Boolean retrieveYn = !treeGrid.getStore().getRecord(selectModel).getValue(properties.retrieveYnCheck());
				//Info.display("retrieveYn is ", retrieveYn.toString());
				
				if(retrieveYn) {
					Dcr01_ClassTreeModel parentModel = treeGrid.getTreeStore().getParent(selectModel);
					while(parentModel != null) {
						treeGrid.getStore().getRecord(parentModel).addChange(properties.retrieveYnCheck(), retrieveYn);
						parentModel = treeGrid.getTreeStore().getParent(parentModel);
					}
				}

				// child setting 
				for(Dcr01_ClassTreeModel childModel : treeGrid.getTreeStore().getAllChildren(selectModel)) {
					treeGrid.getStore().getRecord(childModel).addChange(properties.retrieveYnCheck(), retrieveYn);
				}
			}

			if(colIndex == 4) {
				Boolean updateYn = !treeGrid.getStore().getRecord(selectModel).getValue(properties.updateYnCheck());
				//Info.display("updateYn is ", updateYn.toString());
				
				if(updateYn) {
					Dcr01_ClassTreeModel parentModel = treeGrid.getTreeStore().getParent(selectModel);
					while(parentModel != null) {
						treeGrid.getStore().getRecord(parentModel).addChange(properties.updateYnCheck(), updateYn);
						parentModel = treeGrid.getTreeStore().getParent(parentModel);
					}
				}
				
				for(Dcr01_ClassTreeModel childModel : treeGrid.getTreeStore().getAllChildren(selectModel)) {
					treeGrid.getStore().getRecord(childModel).addChange(properties.updateYnCheck(), updateYn);
				}
			}
			
//			treeGrid.setAutoExpand(true);
		}
			
	}
	
	public TreeGrid<Dcr01_ClassTreeModel> buildTreeGrid(){
		GridBuilder<Dcr01_ClassTreeModel> gridBuilder = new GridBuilder<Dcr01_ClassTreeModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.classTreeName(), 300, "문서분류");
		gridBuilder.addBoolean(properties.retrieveYnCheck(), 40, "조회"); //, checkBoxCell); 
		gridBuilder.addBoolean(properties.updateYnCheck(), 40, "수정");
		gridBuilder.addText(properties.seq(), 70, "조회순서");
		
		
//		gridBuilder.addLong(properties.classTreeId(), 70, "KeyId");
		
		
		gridBuilder.addText(properties.note(), 400, "상세설명"); 
		return gridBuilder.getTreeGrid(2);
	}

	public void retrieve() {
		ServiceRequest request = new ServiceRequest("dcr.Dcr03_OrgAuth.selectByOrgCodeId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putLongParam("orgCodeId", this.orgCodeId);
		ServiceCall service = new ServiceCall();
		this.treeGrid.mask("Loading");
		service.execute(request, this);
	}

	private void addChildItem(Dcr01_ClassTreeModel parentItem) {
		// 조회 시 하위 노드 생성 함수. 
		if(parentItem.getChildList() != null){
			for(GridDataModel child : parentItem.getChildList()){
				
				Dcr01_ClassTreeModel childClassModel = (Dcr01_ClassTreeModel)child; 
				this.treeGrid.getTreeStore().add(parentItem, childClassModel);
				this.addChildItem(childClassModel);
			}
		}
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		this.treeGrid.unmask();
		if(result.getStatus() < 0) {
			Info.display("error", result.getMessage());
		}
		else {
			
			this.treeGrid.getTreeStore().clear();
			for(GridDataModel data : result.getResult()) {
				Dcr01_ClassTreeModel item = (Dcr01_ClassTreeModel)data;
				this.treeGrid.getTreeStore().add(item);
				this.addChildItem(item);
			}
		}
	}
	
	private void update(){
		TreeGridUpdate<Dcr01_ClassTreeModel> service = new TreeGridUpdate<Dcr01_ClassTreeModel>(); 
		service.addParam("orgCodeId", this.orgCodeId);
		service.update(this.treeGrid.getTreeStore(), "dcr.Dcr03_OrgAuth.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				@SuppressWarnings("unchecked")
				List<GridDataModel> list = (List<GridDataModel>)result; 
				for(GridDataModel data : list) {
					Dcr01_ClassTreeModel updateData = (Dcr01_ClassTreeModel)data;
					
					/* TreeGrid의 자기 아이템을 찾아서 다시 넣는다. 
					 * 이유는 알수 없다. 
					 * 안그러면 expand 안되는 오류가 발생함. 
					 */
					
					treeGrid.getTreeStore().update(treeGrid.getTreeStore().findModel(updateData));
				}
			}
		});
	}
	
	@Override
	public void retrieve(Map<String, Object> param) {
		this.orgCodeId = (Long)param.get("orgCodeId"); 
		this.retrieve();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void treeClear() {
		this.treeGrid.getStore().clear();
	}
}