package myApp.client.vi.sys.model;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys05_UserRoleModelProperties extends PropertyAccess<Sys05_UserRoleModel>{
	
	ModelKeyProvider<Sys05_UserRoleModel> keyId();

	ValueProvider<Sys05_UserRoleModel, Long> userRoleId();
	ValueProvider<Sys05_UserRoleModel, Long> userId();
	ValueProvider<Sys05_UserRoleModel, Long> roleId();
	ValueProvider<Sys05_UserRoleModel, String> seq();
	ValueProvider<Sys05_UserRoleModel, String> note();
	
	@Path("roleModel.roleName")
	ValueProvider<Sys05_UserRoleModel, String> roleName();
}
