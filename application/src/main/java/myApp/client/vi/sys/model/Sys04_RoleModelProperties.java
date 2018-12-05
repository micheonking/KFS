package myApp.client.vi.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys04_RoleModelProperties extends PropertyAccess<Sys04_RoleModel> {
	
	ModelKeyProvider<Sys04_RoleModel> keyId();
	
	ValueProvider<Sys04_RoleModel, Long> 	roleId();
	ValueProvider<Sys04_RoleModel, String>	roleName();
	ValueProvider<Sys04_RoleModel, Boolean>	userRoleYn();
	ValueProvider<Sys04_RoleModel, String> 	seq();
	ValueProvider<Sys04_RoleModel, String> 	note();
}