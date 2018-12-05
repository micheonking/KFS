package myApp.client.vi.org;

import java.util.Date;
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
import com.sencha.gxt.widget.core.client.tree.Tree.CheckCascade;

import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;

public class Org01_Move_OrgCode extends Window implements InterfaceServiceCall {
	
	private Tree<Org01_CodeModel, String> orgCodeTree ; 
	private InterfaceCallbackResult callback ; 
	private Date baseDate ; 
	
	private TreeStore<Org01_CodeModel> treeStore = new TreeStore<Org01_CodeModel>(new ModelKeyProvider<Org01_CodeModel> () {
		@Override
		public String getKey(Org01_CodeModel orgCodeModel) {
			return orgCodeModel.getKeyId() + "";
		}
	});


	public void open(Date baseDate, InterfaceCallbackResult callback) {
		
		this.setModal(true);
		this.setHeading("이동시킬 상위조직을 선택하세요.");
		this.setResizable(false);
		this.setPixelSize(400, 550);
		
		this.baseDate = baseDate; 
		this.callback = callback;
 
		orgCodeTree = this.getMenuTree();

//		orgCodeTree.setCheckable(true);
//		orgCodeTree.setCheckStyle(CheckCascade.TRI);
//		orgCodeTree.setAutoLoad(true);
		
		
		orgCodeTree.getStyle().setLeafIcon(ResourceIcon.INSTANCE.textButton());
		this.add(orgCodeTree);

		TextButton oKButton = new TextButton("확인");
		oKButton.setWidth(50);
		oKButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		}); 
		this.addButton(oKButton);

		TextButton cancelButton = new TextButton("취소");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler(){
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
	
	
	private void retrieve() {
		ServiceRequest request = new ServiceRequest("org.Org01_Code.selectByCompanyId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putDateParam("baseDate", baseDate);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void update() {
		Org01_CodeModel parentCodeModel = orgCodeTree.getSelectionModel().getSelectedItem(); 
		if(parentCodeModel == null) {
			new SimpleMessage("먼저 이동할 상위부서를 선택하세요."); 
			return ; 
		}
		else {
			this.callback.execute(parentCodeModel);
			hide(); 
		}
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
			}
		} 
		
		orgCodeTree.expandAll();
	}

	private ValueProvider<Org01_CodeModel, String> treeValueProvider = new ValueProvider<Org01_CodeModel, String>() {
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
	} ;  
	
	private Tree<Org01_CodeModel, String> getMenuTree(){
		Tree<Org01_CodeModel, String> tree = new Tree<Org01_CodeModel, String>(treeStore, treeValueProvider) {
			@Override
			protected boolean hasChildren(Org01_CodeModel model) {
				return super.hasChildren(model); 
			}
		};

		return tree; 
	}

}
