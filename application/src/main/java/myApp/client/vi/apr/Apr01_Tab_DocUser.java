package myApp.client.vi.apr;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.data.shared.IconProvider;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Apr01_Tab_DocUser extends Apr01_Tab_DocManager{
	
	public Apr01_Tab_DocUser() {
		managerYn = false;
	}
	
	
	@Override
	public void retrieve() {
		tree.mask("Loading");
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
	
}