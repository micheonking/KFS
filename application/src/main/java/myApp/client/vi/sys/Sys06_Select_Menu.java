package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.service.TreeGridUpdate;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.sys.model.Sys06_MenuModel;

public class Sys06_Select_Menu extends Window implements InterfaceServiceCall {
	
	private TreeGrid<Sys06_MenuModel> treeGrid ; 
	private Sys06_MenuModel menuModel, deleteModel; 
	private Tree<Sys06_MenuModel, String> menuTree ; 
	
	private TreeStore<Sys06_MenuModel> treeStore = new TreeStore<Sys06_MenuModel>(new ModelKeyProvider<Sys06_MenuModel> () {
		@Override
		public String getKey(Sys06_MenuModel menuModel) {
			return menuModel.getKeyId() + "";
		}
	});

	private ValueProvider<Sys06_MenuModel, String> treeValueProvider = new ValueProvider<Sys06_MenuModel, String>() {
		
		@Override
		public String getValue(Sys06_MenuModel menu) {
			return menu.getMenuName();
		}
		
		@Override
		public void setValue(Sys06_MenuModel menu, String value) {
		}
		
		@Override
		public String getPath() {
			return "path";
		}
	} ;  

	private Tree<Sys06_MenuModel, String> getMenuTree(){

		Tree<Sys06_MenuModel, String> tree = new Tree<Sys06_MenuModel, String>(treeStore, treeValueProvider) {
		
			@Override
			protected void onClick(Event event) { 
				super.onDoubleClick(event); // tree node를 one-click으로 열기위해 사용한다. 
			}
			
			protected boolean hasChildren(Sys06_MenuModel model) {
				return super.hasChildren(model); 
			}
		};

		return tree; 
	}

	public void open(TreeGrid<Sys06_MenuModel> treeGrid, Sys06_MenuModel menuModel) {

		this.treeGrid = treeGrid; 
		this.menuModel = menuModel; 
		this.deleteModel = menuModel;
		
		this.setBorders(false);
		this.setResizable(false);
		this.setModal(true);
		this.setHeading("이동시킬 상위메뉴를 선택하세요.");
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());

		menuTree = this.getMenuTree();
		menuTree.setSize("420", "350");
		menuTree.getStyle().setLeafIcon(ResourceIcon.INSTANCE.textButton());
		
		this.add(menuTree);
		
		ServiceRequest request = new ServiceRequest("sys.Sys06_Menu.selectByAll");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		
		
		TextButton okButton = new TextButton("확인");
		okButton.setWidth(60);
		okButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				moveTree();
			}
		}); 
		this.getButtonBar().add(okButton);
		
		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show(); 
	}
	
	
	private void moveTree() {
		
		Sys06_MenuModel targetModel = menuTree.getSelectionModel().getSelectedItem() ; 
		
		if(targetModel != null) {
			menuModel.setParentId(targetModel.getMenuId()); // parent Id를 변경한다. 	

			TreeGridUpdate<Sys06_MenuModel> service = new TreeGridUpdate<Sys06_MenuModel>(); 
			service.update(this.treeGrid.getTreeStore(), menuModel, "sys.Sys06_Menu.update", new InterfaceCallbackResult() {
				@Override
				public void execute(Object result) {
					
					// 이전 tree에서 삭제하고 
					Sys06_MenuModel parentModel = treeGrid.getTreeStore().getParent(deleteModel);
					treeGrid.getTreeStore().remove(deleteModel);
					treeGrid.setExpanded(parentModel, false); // 2번 해주어야 한다. 
					treeGrid.setExpanded(parentModel, true);

					// 신규 tree로 추가한다. 
//					List<Sys06_MenuModel> children = treeGrid.getTreeStore().getAllChildren(deleteModel) ; 
//					treeGrid.getTreeStore().replaceChildren(menuModel, children);
					
					treeGrid.getTreeStore().add(menuTree.getSelectionModel().getSelectedItem(), menuModel);
					treeGrid.setExpanded(menuTree.getSelectionModel().getSelectedItem(), true);
					hide();
					
				}
			});
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
			for (GridDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				Sys06_MenuModel menuModel = (Sys06_MenuModel)model;   
				menuTree.getStore().add(menuModel);
				this.addChild(menuModel); // child menu & object setting  
			}
		} 
	}

}
