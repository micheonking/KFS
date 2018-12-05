package myApp.client.vi.dcr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.LoginUser;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Dcr03_Tab_OrgAuth extends BorderLayoutContainer implements InterfaceServiceCall {
	
	private Dcr03_TabPage_OrgAuth orgAuthPage = new Dcr03_TabPage_OrgAuth();
	
	private Tree<Org01_CodeModel, String> getMenuTree(){
		
		ValueProvider<Org01_CodeModel, String> treeValueProvider = new ValueProvider<Org01_CodeModel, String>() {
			@Override
			public String getValue(Org01_CodeModel orgCodeModel) {
				return orgCodeModel.getOrgInfoModel().getKorName();
			}
			@Override
			public void setValue(Org01_CodeModel orgCodeModel, String value) {
			}
			@Override
			public String getPath() {
				return "path";
			}
		}; 

		TreeStore<Org01_CodeModel> treeStore = new TreeStore<Org01_CodeModel>(new ModelKeyProvider<Org01_CodeModel> () {
			@Override
			public String getKey(Org01_CodeModel orgCodeModel) {
				return orgCodeModel.getKeyId() + "";
			}
		});
			
		Tree<Org01_CodeModel, String> tree = new Tree<Org01_CodeModel, String>(treeStore, treeValueProvider) {
			@Override
			protected boolean hasChildren(Org01_CodeModel model) {
				return super.hasChildren(model); 
			}
		}; 
		
		return tree; 
	}

	private Tree<Org01_CodeModel, String> orgCodeTree = this.getMenuTree();
	private TabPanel tabPanel = new TabPanel();
	
	public Dcr03_Tab_OrgAuth() {

		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.setWidth(50);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		}); 
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.add(retrieveButton); //조회버튼
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(buttonBar, new VerticalLayoutData(1, 44, new Margins(0, 0, 2, 0)));

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(this.orgCodeTree);
		vlc.add(cp, new VerticalLayoutData(1, 1));
		
		BorderLayoutData westLayoutData = new BorderLayoutData(0.3);
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(0, 2, 0, 0));  
		this.setWestWidget(vlc, westLayoutData);
		
		BorderLayoutData centerLayoutData = new BorderLayoutData();
		centerLayoutData.setMargins(new Margins(3, 0, 0, 0)); // tabPage Top Margin
		this.tabPanel.setTabMargin(10);
		this.tabPanel.add(new Dcr03_TabPage_OrgAuth(), "문서권한");
		this.tabPanel.add(new ContentPanel(), "펀드권한");
		//this.setCenterWidget(tabPanel, centerLayoutData);
		this.setCenterWidget(orgAuthPage, centerLayoutData);
		
		this.orgCodeTree.getStyle().setLeafIcon(ResourceIcon.INSTANCE.textButton());
		this.orgCodeTree.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Org01_CodeModel>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Org01_CodeModel> event) {
				
				if(event.getSelection().size() > 0){
					Org01_CodeModel orgCode = event.getSource().getSelectedItem();  
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("orgCodeId", orgCode.getCodeId()); 

					//InterfaceTabPage tabPage = (InterfaceTabPage)tabPanel.getActiveWidget();
					//tabPage.retrieve(param);
					orgAuthPage.retrieve(param);
				}
				else {
					//InterfaceTabPage tabPage = (InterfaceTabPage)tabPanel.getActiveWidget();
					//tabPage.init();
					orgAuthPage.treeClear();
				}
			}
		}) ; 
		this.retrieve();
	}
	
	
	private void retrieve() {
		ServiceRequest request = new ServiceRequest("org.Org01_Code.selectByCompanyId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putDateParam("baseDate", LoginUser.getToday());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	private void addChild(Org01_CodeModel parentMenu) {
		if(parentMenu.getChildList() != null){
			List<GridDataModel> childList = parentMenu.getChildList(); 
			for(GridDataModel child : childList){
				Org01_CodeModel childObject = (Org01_CodeModel)child;
				orgCodeTree.getStore().add(parentMenu, childObject);
				this.addChild(childObject);
			}
		}
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {

		if(result.getStatus() < 0) {
			Info.display("메뉴조회 오류", result.getMessage());
		}
		else { 
			orgCodeTree.getStore().clear(); // 깨끗이 비운다. 
			for (GridDataModel model: result.getResult()) {
				Org01_CodeModel codeModel = (Org01_CodeModel)model;   
				orgCodeTree.getStore().add(codeModel);
				this.addChild(codeModel); // child menu & object setting
				orgCodeTree.setExpanded(codeModel, true);
			}
		} 
		
	}


}
