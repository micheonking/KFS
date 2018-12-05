package myApp.client.vi.apr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.fnd.Fnd00_Lookup_FundCode;
import myApp.client.vi.fnd.model.Fnd00_FundModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModelProperties;
import myApp.client.vi.sys.Sys10_FundDocList;

public class Apr01_Tab_FundDocument extends BorderLayoutContainer implements InterfaceServiceCall {
	
	public Tree<Dcr01_ClassTreeModel, String> tree = this.getMenuTree();
	private Sys10_FundDocList docList  = new Sys10_FundDocList() ;
//	private LookupTriggerField lookupFundField = new LookupTriggerField();
	private ComboBoxField fundTypeComboBox = new ComboBoxField("FundTypeCode", "%", "전체");
	private CheckBox closeYnCheckBox = new CheckBox();
	private Grid<Fnd01_FundCodeModel> grid = this.buildGrid();
	private TextField fundSearchTextField 	 = new TextField();
	
	public Apr01_Tab_FundDocument() {
		
		ButtonBar buttonBar = new ButtonBar();
		
		fundTypeComboBox.setValue("전체");
		FieldLabel fundTypeComboBoxField = new FieldLabel(fundTypeComboBox, "펀드유형 ");
		fundTypeComboBoxField.setLabelWidth(56);
		fundTypeComboBoxField.setWidth(200);
		buttonBar.add(fundTypeComboBoxField);
		
		fundSearchTextField.setEmptyText("전체");
		fundSearchTextField.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) { // enter key event 
					retrieveFundList();
				}
			}
		});
		FieldLabel fundNameLabel = new FieldLabel(fundSearchTextField,"펀드 ");
		fundNameLabel.setLabelWidth(60);
		fundNameLabel.setWidth(320);
//		this.lookupFundField.addTriggerClickHandler(new TriggerClickHandler(){
//			@Override
//			public void onTriggerClick(TriggerClickEvent event) {
//				findFundCode(); 
//			}
//		}); 
		buttonBar.add(fundNameLabel); 
		
		closeYnCheckBox.setBoxLabel("해지펀드포함");
		closeYnCheckBox.setValue(false);
		buttonBar.add(closeYnCheckBox);

		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieveFundList();
			}
		});
		retrieveButton.setWidth(60);
		buttonBar.add(retrieveButton); // 조회버튼
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(buttonBar, new VerticalLayoutData(1, 40));
		vlc.add(this.grid, new VerticalLayoutData(1, 1)); 
		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Fnd01_FundCodeModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Fnd01_FundCodeModel> event) {
				if(event.getSelection().size() > 0){
					retrieveDocTree(); 
				}
			}
		});
		
		BorderLayoutData northLayoutData = new BorderLayoutData(250);
		northLayoutData.setMargins(new Margins(0, 0, 4, 0));
		northLayoutData.setSplit(true);
		northLayoutData.setMaxSize(1000);
		this.setNorthWidget(vlc, northLayoutData);
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(this.tree);
		
		BorderLayoutData westLayoutData = new BorderLayoutData(400);
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(2, 2, 0, 0));  
		this.setWestWidget(cp, westLayoutData);
		this.tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Dcr01_ClassTreeModel>() {
			@Override
			public void onSelection(SelectionEvent<Dcr01_ClassTreeModel> event) {
				if (event.getSelectedItem() != null) {
					retrieveDocList(); 
				}
			}
		});

		this.setCenterWidget(docList, new BorderLayoutData(1));//중앙 컨테이너 표시
	}
	
//	private void findFundCode() {
//		Fnd00_Lookup_FundCode lookupFundCode = new Fnd00_Lookup_FundCode();
//		lookupFundCode.open(new InterfaceCallbackResult() {
//			@Override
//			public void execute(Object result) {
//				Fnd00_FundModel fundModel = (Fnd00_FundModel)result;
//				lookupFundField.setValue(fundModel.getFundName());
//			}
//		});
//
//	}
	
	public Grid<Fnd01_FundCodeModel> buildGrid(){
		Fnd01_FundCodeModelProperties properties = GWT.create(Fnd01_FundCodeModelProperties.class);
		GridBuilder<Fnd01_FundCodeModel> gridBuilder = new GridBuilder<Fnd01_FundCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText	(properties.fundCode(), 70, "코드");
		gridBuilder.addText	(properties.fundName(), 250, "펀드명");
		gridBuilder.addText	(properties.fundTypeName(), 150, "펀드유형");
		gridBuilder.addDate	(properties.startDate(), 90, "생성일");
		gridBuilder.addDate	(properties.endDate(), 90, "예정일");
		gridBuilder.addText	(properties.closeName(), 60, "해지여부");
		gridBuilder.addText	(properties.sintakGbName(), 80, "신탁구분");
		gridBuilder.addText	(properties.publicName(), 40, "공모");
		gridBuilder.addText	(properties.orgCodeName(), 150, "운용부서");
		gridBuilder.addText	(properties.emp1Name(), 90, "운용역1");
		gridBuilder.addText	(properties.emp2Name(), 90, "운용역2");

		return gridBuilder.getGrid(); 
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
	

	private void addChild(Dcr01_ClassTreeModel parentMenu) {
//		Info.display("addChild 구성하기", ""+parentMenu.getChildList());
		if (parentMenu.getChildList() != null) {
			
			for (GridDataModel child : parentMenu.getChildList()) {
				Dcr01_ClassTreeModel childTreeModel = (Dcr01_ClassTreeModel)child;  
//				Info.display("for IN", childTreeModel.getClassTreeName());
				tree.getStore().add(parentMenu, childTreeModel);
				this.addChild(childTreeModel);
			}
			tree.setExpanded(parentMenu, true);
		}
	}

	public void retrieveFundList() {

		GridRetrieveData<Fnd01_FundCodeModel> service = new GridRetrieveData<Fnd01_FundCodeModel>(grid.getStore());

		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("orgId", LoginUser.getOrgCodeId());
		service.addParam("closeYn", closeYnCheckBox.getValue().toString()); 
		service.addParam("startDate", LoginUser.getToday());
		service.addParam("fundName", fundSearchTextField.getText());
		service.addParam("fundTypeCode", fundTypeComboBox.getCode());

		service.retrieve("fnd.Fnd01_FundCode.selectByFundOrgAuth", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
				}			
			}
		});
	}

	private void retrieveDocList() {
		Long fundCodeId = grid.getSelectionModel().getSelectedItem().getFundCodeId(); 
		Long classTreeId = tree.getSelectionModel().getSelectedItem().getClassTreeId(); 
		docList.retrieve(classTreeId, fundCodeId);
	}
	
	public void retrieveDocTree() {

		Fnd01_FundCodeModel fundModel = this.grid.getSelectionModel().getSelectedItem(); 
//		ServiceRequest request = new ServiceRequest("dcr.Dcr01_ClassTree.selectByFundCodeId");
		ServiceRequest request = new ServiceRequest("dcr.Dcr01_ClassTree.selectByFundCodeId2");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putLongParam("orgCodeId", LoginUser.getOrgCodeId());
		request.putLongParam("fundCodeId", fundModel.getFundCodeId());
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		
		tree.mask("Loading");
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
	
	@Override
	public void getServiceResult(ServiceResult result) {
		tree.unmask();
		tree.getStore().clear(); // 깨끗이 비운다.
		
		if (result.getStatus() < 0) {
			Info.display("메뉴조회 오류", result.getMessage());
			return ; 
		}
		
		for (GridDataModel model : result.getResult()) {
			// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.
			Dcr01_ClassTreeModel menuModel = (Dcr01_ClassTreeModel) model;
			
//			Info.display("name", menuModel.getClassTreeName());

			tree.getStore().add(menuModel);
			this.addChild(menuModel); // child menu & object setting
		}
		
	}
	
}