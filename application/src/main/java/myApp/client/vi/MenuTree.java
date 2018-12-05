package myApp.client.vi;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys06_MenuModel;

public class MenuTree implements InterfaceServiceCall{
	
	private Tree<Sys06_MenuModel, String> menuTree; // = this.getMenuTree();  
	public static TabPanel tabPanel;  
	
	public MenuTree(TabPanel tabPanel){
		this.tabPanel = tabPanel; 
		tabPanel.setCloseContextMenu(true);
		this.menuTree = this.buildMenuTree(); 
		this.retrieveByUserId();
	} 

	public Tree<Sys06_MenuModel, String> getMenuTree(){
		return this.menuTree; 
	}
	
	private Tree<Sys06_MenuModel, String> buildMenuTree(){
		
		TreeStore<Sys06_MenuModel> menuTreeStore = new TreeStore<Sys06_MenuModel>(new ModelKeyProvider<Sys06_MenuModel> () {
			@Override
			public String getKey(Sys06_MenuModel menuModel) {
				return menuModel.getMenuId() + "";
			}
		});
		
		ValueProvider<Sys06_MenuModel, String> treeMenuValueProvider = new ValueProvider<Sys06_MenuModel, String>() {
			@Override
			public String getValue(Sys06_MenuModel menuModel) {
				return menuModel.getMenuName();
			}
			@Override
			public void setValue(Sys06_MenuModel object, String value) {
			}
			@Override
			public String getPath() {
				return "path";
			}
		} ; 

		menuTree = new Tree<Sys06_MenuModel, String>(menuTreeStore, treeMenuValueProvider) {
			@Override
			protected void onClick(Event event) { // onDoubleClick event도 있으나...
				TreeNode<Sys06_MenuModel> node = findNode(event.getEventTarget().<Element> cast());

				if(node == null) {
					return; // 선택된 메뉴가 없다. 
				}
		        
				if(node.getModel().getMenuId() != null && node.getModel().getChildList().size() == 0 ){
					String className = node.getModel().getClassName();  
					String pageName = node.getModel().getMenuName();
					
					MenuOpener openTab = new MenuOpener();
					openTab.openTab(tabPanel, className, pageName);
				}
				
		        super.onDoubleClick(event); // tree node를 one-click으로 열기위해 사용한다. 
			}
		};

		menuTree.getStyle().setLeafIcon(ResourceIcon.INSTANCE.textButton());
		return menuTree; 
	}
	
	
	public void retrieveByUserId(){
		if(LoginUser.getIsMansger()) {
			ServiceRequest request = new ServiceRequest("sys.Sys06_Menu.selectByCompanyId");
			request.putLongParam("companyId", LoginUser.getCompanyId());
			
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
		else {
			ServiceRequest request = new ServiceRequest("sys.Sys06_Menu.selectByUserId");
			request.putLongParam("companyId", LoginUser.getCompanyId());
			request.putLongParam("userId", LoginUser.getUserId());
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
	}

	private void addChild(Sys06_MenuModel parentMenu) {
		if(parentMenu.getChildList() != null){
			List<GridDataModel> childList = parentMenu.getChildList();
			
			for(GridDataModel child : childList){
				Sys06_MenuModel childObject = (Sys06_MenuModel)child;
				menuTree.getStore().add(parentMenu, childObject);
				this.addChild(childObject);
			}
		}
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("메뉴조회 오류", result.getMessage());
		}
		else { 
			menuTree.getStore().clear(); // 깨끗이 비운다. 
			
			for (GridDataModel dataModel: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				Sys06_MenuModel menuModel = (Sys06_MenuModel)dataModel;   
				menuTree.getStore().add(menuModel);
				this.addChild(menuModel); // child menu & object setting  
			}
		} 
	}
}
