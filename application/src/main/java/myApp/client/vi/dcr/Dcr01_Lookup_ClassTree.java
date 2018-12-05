
package myApp.client.vi.dcr;

import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Dcr01_Lookup_ClassTree extends Window implements InterfaceServiceCall {

	Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
	
	private Tree<Dcr01_ClassTreeModel, String> tree = this.getMenuTree();

	public void open (String Gubun, InterfaceCallbackResult callback) {

		this.setModal(true);
		this.setHeading("문서구분찾기");
		this.setResizable(false);
		this.setPixelSize(500, 500);

		ButtonBar searchBar = new ButtonBar(); 

		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.setWidth(50);
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		searchBar.add(retrieveButton);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(searchBar, new VerticalLayoutData(1,40));
		vlc.add(tree, new VerticalLayoutData(1,1));
		
		this.add(vlc);

		TextButton okButton = new TextButton("확인");
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				classTreeModel = tree.getSelectionModel().getSelectedItem();
				callback.execute(classTreeModel);
				hide(); 
			}
		});
		this.addButton(okButton);

		TextButton cancelButton = new TextButton("취소");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show();
		this.retrieve();
	}
	
	private Tree<Dcr01_ClassTreeModel, String> getMenuTree() {

		TreeStore<Dcr01_ClassTreeModel> treeStore = new TreeStore<Dcr01_ClassTreeModel>(
				new ModelKeyProvider<Dcr01_ClassTreeModel>() {
					@Override
					public String getKey(Dcr01_ClassTreeModel getModel) {
						return getModel.getKeyId() + "";
					}
				});
		ValueProvider<Dcr01_ClassTreeModel, String> treeValueProvider = new ValueProvider<Dcr01_ClassTreeModel, String>() {
			@Override
			public String getValue(Dcr01_ClassTreeModel menu) {
				return menu.getClassTreeName();
			}
			@Override
			public void setValue(Dcr01_ClassTreeModel menu, String value) {
			}
			@Override
			public String getPath() {
				return "path";
			}
		};
		Tree<Dcr01_ClassTreeModel, String> tree = new Tree<Dcr01_ClassTreeModel, String>(treeStore, treeValueProvider) {
			 @Override
			protected void onClick(Event event) {
				 super.onDoubleClick(event);
			 }
			 @Override
			protected boolean hasChildren(Dcr01_ClassTreeModel model) {
				 return super.hasChildren(model);
			 }
		};

		return tree;
	}

	private void addChild(Dcr01_ClassTreeModel parentMenu) {
		if (parentMenu.getChildList() != null) {
			for (GridDataModel child : parentMenu.getChildList()) {
				Dcr01_ClassTreeModel childTreeModel = (Dcr01_ClassTreeModel)child;  
				tree.getStore().add(parentMenu, childTreeModel);
				this.addChild(childTreeModel);
			}
		}
	}
	
	private void retrieve() {
		tree.mask("Loading");
		ServiceRequest request = new ServiceRequest("dcr.Dcr01_ClassTree.selectByParentIdResrchYn");
		request.putLongParam  ("companyId", LoginUser.getCompanyId());
		request.putLongParam  ("orgId"    , LoginUser.getOrgCodeId());
		request.putStringParam("typeCode", "%");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		tree.getStore().clear();
		for (GridDataModel model : result.getResult()) {
			Dcr01_ClassTreeModel treeModel = (Dcr01_ClassTreeModel) model;
			tree.getStore().add(treeModel);
			this.addChild(treeModel);
		}
		tree.unmask();
	}
}
