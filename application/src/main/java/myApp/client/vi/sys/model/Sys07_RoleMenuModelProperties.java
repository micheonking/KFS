package myApp.client.vi.sys.model;

//import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys07_RoleMenuModelProperties extends PropertyAccess<Sys07_RoleMenuModel> {
	
	ModelKeyProvider<Sys07_RoleMenuModel> keyId();	
	ValueProvider<Sys07_RoleMenuModel, Long> 	roleMenuId();
	ValueProvider<Sys07_RoleMenuModel, Long> 	roleId();
	ValueProvider<Sys07_RoleMenuModel, Long> 	menuId();
	ValueProvider<Sys07_RoleMenuModel, String> 	note();
} 