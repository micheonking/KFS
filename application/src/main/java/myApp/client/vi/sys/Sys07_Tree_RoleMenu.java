package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
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
import myApp.client.vi.sys.model.Sys06_MenuModel;
import myApp.client.vi.sys.model.Sys06_MenuModelProperties;

public class Sys07_Tree_RoleMenu extends ContentPanel implements InterfaceServiceCall {
	
	private TreeGrid<Sys06_MenuModel> treeGrid = this.buildTreeGrid();
	private Long roleId;

	public Sys07_Tree_RoleMenu(){

		this.setHeaderVisible(false);
		this.add(this.treeGrid);
		
		TextButton refreshButton = new TextButton("조회");
		refreshButton.setWidth(60);
		refreshButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				 refresh(); 
			}
		}); 
		this.getButtonBar().add(refreshButton);
		
		TextButton expandAll = new TextButton("펼치기"); 
		expandAll.setWidth(60);
		expandAll.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				treeGrid.expandAll();
			}
		}); 
		this.getButtonBar().add(expandAll);
		
		TextButton collapseAll = new TextButton("감추기");
		collapseAll.setWidth(60);
		collapseAll.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				treeGrid.collapseAll();
			}
		}); 
		this.getButtonBar().add(collapseAll);
		
		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update();  
			}
		}); 
		this.getButtonBar().add(updateButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	public TreeGrid<Sys06_MenuModel> buildTreeGrid(){
		
		Sys06_MenuModelProperties properties = GWT.create(Sys06_MenuModelProperties.class);
		final GridBuilder<Sys06_MenuModel> gridBuilder = new GridBuilder<Sys06_MenuModel>(properties.keyId());  

		gridBuilder.addText(properties.menuName(), 300, "메뉴명"); //, new TextField());
		gridBuilder.addBoolean(properties.roleMenuYn(), 40, "권한") ;
		gridBuilder.addText(properties.seq(), 80, "조회순서"); //, new TextField()) ;
		gridBuilder.addText(properties.note(),500, "상세설명"); //, new TextField());
	
		// sort setting 
		gridBuilder.setSortInfo(properties.seq(), SortDir.ASC);
		
		return gridBuilder.getTreeGrid(1);  
	}
	public void refresh(){
		if(this.roleId != null){
			retrieve(this.roleId);
		}
	}
	
	public void retrieve(Long roleId){
		this.roleId = roleId;
		
		// 서버에서 전체 트리를 한번에 가져온다.  
		ServiceRequest request = new ServiceRequest("sys.Sys06_Menu.selectByRoleId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putLongParam("roleId", roleId);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void setChildItem(Sys06_MenuModel parentMenu) {

		if(parentMenu.getChildList() != null){

			List<GridDataModel> childList = parentMenu.getChildList();
			
			for(GridDataModel dataModel : childList){
				Sys06_MenuModel childMenu = (Sys06_MenuModel)dataModel;
				this.treeGrid.getTreeStore().add(parentMenu, childMenu);
				this.setChildItem(childMenu);
			}
		}
	}
	
	private void update(){
		TreeGridUpdate<Sys06_MenuModel> service = new TreeGridUpdate<Sys06_MenuModel>();
		service.addParam("roleId", this.roleId);
		service.update(treeGrid.getTreeStore(), "sys.Sys06_Menu.updateRoleMenu", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				@SuppressWarnings("unchecked")
				List<GridDataModel> list = (List<GridDataModel>)result; 
				for(GridDataModel data : list) {
					Sys06_MenuModel updateData = (Sys06_MenuModel)data;
					
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
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
		}
		else { 
			this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다. 
			
			for (GridDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				Sys06_MenuModel roleObject = (Sys06_MenuModel)model;
				this.treeGrid.getTreeStore().add(roleObject);
				this.setChildItem(roleObject); // child menu & object setting  
			}
		} 
	}
}
