package myApp.client.vi.apr;

import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridInsertRow;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr05_RelateDocModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Apr05_Lookup_RelateDoc extends Window implements InterfaceServiceCall {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Tree<Dcr01_ClassTreeModel, String> tree = this.getMenuTree();
	private Apr05_Grid_RelateDocList gridPage = new Apr05_Grid_RelateDocList() ; // readOnly = true
	private Grid<Apr05_RelateDocModel> grid;
    private InterfaceCallbackResult callback = null;
    private String actionCode = "";
    private Long paramId;
    
	public Apr05_Lookup_RelateDoc() {
        init();
	}
	
	public Apr05_Lookup_RelateDoc(Long paramId, InterfaceCallbackResult callback) {
		this.callback = callback;
		init();
	}
	
	public Apr05_Lookup_RelateDoc(Grid<Apr05_RelateDocModel> grid, Long paramId, InterfaceCallbackResult callback) {
		this.grid = grid;
		this.callback = callback;
		this.paramId = paramId;
		init();
	}
	
	
	
	private void init() {
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(true);
		this.setWidth(1100);
		this.setHeight(700);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		this.setHeading("참조문서");
		
		BorderLayoutContainer blc = new BorderLayoutContainer();
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.add(new LabelToolItem("▶ 문서분류 ")); 

		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		buttonBar.add(retrieveButton); // 조회버튼
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		//vlc.add(buttonBar, new VerticalLayoutData(1, 50, new Margins(0, 0, 1, 0)));

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(this.tree);
		vlc.add(cp, new VerticalLayoutData(1, 1));
		
		BorderLayoutData westLayoutData = new BorderLayoutData(0.25);
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(0, 2, 0, 0));  
		blc.setWestWidget(vlc, westLayoutData);

		this.tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Dcr01_ClassTreeModel>() {
			@Override
			public void onSelection(SelectionEvent<Dcr01_ClassTreeModel> event) {
				if (event.getSelectedItem() != null) {
					Dcr01_ClassTreeModel classTreeModel = event.getSelectedItem();
					gridPage.retrieve(classTreeModel);
					//Dcr01_ClassTreeModel classTreeModel = event.getSelectedItem();
					//docList.retrieve(classTreeModel, managerYn);
				}
			}
		});

		blc.setCenterWidget(gridPage);//중앙 컨테이너 표시
//		gridDoc.makeLayout();
		this.add(blc);
		

		TextButton insertButton = new TextButton("확인");
		insertButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				insert();
			}
		});
		TextButton closeButton = new TextButton("닫기");
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		
		this.getButtonBar().add(insertButton);
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		retrieve();
	}
	
	private Tree<Dcr01_ClassTreeModel, String> getMenuTree() {

		TreeStore<Dcr01_ClassTreeModel> treeStore = new TreeStore<Dcr01_ClassTreeModel>(
				new ModelKeyProvider<Dcr01_ClassTreeModel>() {
					@Override
					public String getKey(Dcr01_ClassTreeModel classTreeModel) {
						return classTreeModel.getKeyId() + "";
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
				super.onDoubleClick(event); // tree node를 one-click으로 열기위해 사용한다.
			}
			@Override
			protected boolean hasChildren(Dcr01_ClassTreeModel model) {
				return super.hasChildren(model);
			}
		};
		return tree;
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		
		if(callback != null && actionCode.equals("insert")) {
			callback.execute(result);
			hide();
		} else if(true) {
			if (result.getStatus() < 0) {
				Info.display("메뉴조회 오류", result.getMessage());
			} else {
				tree.getStore().clear(); // 깨끗이 비운다.
				for (GridDataModel model : result.getResult()) {
					// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.
					Dcr01_ClassTreeModel menuModel = (Dcr01_ClassTreeModel) model;
					tree.getStore().add(menuModel);
					this.addChild(menuModel); // child menu & object setting
				}
			}
		}
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
		ServiceRequest request = new ServiceRequest("dcr.Dcr01_ClassTree.selectByCompanyIdRole");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putStringParam("typeCode", "%");
		request.putLongParam("orgId", LoginUser.getOrgCodeId());
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		
		tree.setIconProvider(new IconProvider<Dcr01_ClassTreeModel>() {
			@Override
			public ImageResource getIcon(Dcr01_ClassTreeModel model) {
				if("T".equals(model.getTypeCode())) {//등록가능한 메뉴일 경우
					return ResourceIcon.INSTANCE.textButton();
				} else {
					return ResourceIcon.INSTANCE.closeFolder();
				}
			}
		});
	}
	
	private void retrievePage() {
		gridPage.retrieve();
	}
	
	private void insert() {
		
		if(gridPage.grid.getSelectionModel().getSelectedItem() != null) {
			GridInsertRow<Apr05_RelateDocModel> service = new GridInsertRow<Apr05_RelateDocModel>();
			Apr05_RelateDocModel relateDocModel = gridPage.grid.getSelectionModel().getSelectedItem();
			relateDocModel.setFileId(relateDocModel.getFileModel().getFileId());
			relateDocModel.setApprId(paramId);
			service.insertRow(this.grid, relateDocModel);
			hide();
		} else {
			new SimpleMessage("참조문서를 선택해주세요.");
		}
		
		/*
		paramApprStepGrid.getStore().clear();
		for (Apr04_ApprStepModel data : apprStepGrid.getStore().getAll()) {
			paramApprStepGrid.getStore().add(data);
		}
		*/
	}
}