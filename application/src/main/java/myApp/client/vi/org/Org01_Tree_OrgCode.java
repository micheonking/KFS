package myApp.client.vi.org;

import java.util.Date;
import java.util.List;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.LoginUser;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Org01_Tree_OrgCode implements InterfaceServiceCall {
	
	private Tree<Org01_CodeModel, String> orgTree;
	private TreeStore<Org01_CodeModel> treeStore = new TreeStore<Org01_CodeModel>(
			new ModelKeyProvider<Org01_CodeModel>() {
				@Override
				public String getKey(Org01_CodeModel orgModel) {
					return orgModel.getKeyId() + "";
				}
			});
	private ValueProvider<Org01_CodeModel, String> treeValueProvider = new ValueProvider<Org01_CodeModel, String>() {
		@Override
		public String getValue(Org01_CodeModel orgModel) {
			return orgModel.getOrgInfoModel().getKorName();
		}
		@Override
		public void setValue(Org01_CodeModel menu, String value) {
		}
		@Override
		public String getPath() {
			return "path";
		}
	};


	public Org01_Tree_OrgCode() {
		this.orgTree = new Tree<Org01_CodeModel, String>(treeStore, treeValueProvider) {
			@Override
			protected void onClick(Event event) {
				super.onDoubleClick(event); // tree node를 one-click으로 열기위해 사용한다.
			}
			@Override
			protected boolean hasChildren(Org01_CodeModel model) {
				return super.hasChildren(model);
			}
		};
	}

	public Tree<Org01_CodeModel, String> getTree() {
		return this.orgTree; 
	}

	public void retrieve(Date baseDate) {
		ServiceRequest request = new ServiceRequest("org.Org01_Code.selectByCompanyId");
		request.putLongParam("companyId", LoginUser.getCompanyId());
		request.putDateParam("baseDate", baseDate);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addChild(Org01_CodeModel parent) {
		if (parent.getChildList() != null) {
			List<GridDataModel> childList = parent.getChildList();
			for (GridDataModel child : childList) {
				Org01_CodeModel childItem = (Org01_CodeModel) child;
				orgTree.getStore().add(parent, childItem);
				this.addChild(childItem);
			}
		}
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {

		if (result.getStatus() < 0) {
			Info.display("메뉴조회 오류", result.getMessage());
		} 
		else {
			orgTree.getStore().clear(); // 깨끗이 비운다.
			
			for (GridDataModel model : result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.
				Org01_CodeModel orgModel = (Org01_CodeModel) model;
				orgTree.getStore().add(orgModel);
				this.addChild(orgModel); // child menu & object setting
				this.orgTree.setExpanded(orgModel, true);//첫번째 레벨만 펼치기
			}
		}
	}


}