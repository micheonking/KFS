package myApp.client.vi.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys06_MenuModelProperties extends PropertyAccess<Sys06_MenuModel> {
	
	ModelKeyProvider<Sys06_MenuModel> keyId();	
	ValueProvider<Sys06_MenuModel, Long> 	menuId();
	ValueProvider<Sys06_MenuModel, Long> 	parentId();
	ValueProvider<Sys06_MenuModel, String> 	menuName();
	ValueProvider<Sys06_MenuModel, String> 	className();
	ValueProvider<Sys06_MenuModel, Boolean> useYnFlag();
	ValueProvider<Sys06_MenuModel, String> 	seq();
	ValueProvider<Sys06_MenuModel, String> 	note();
	ValueProvider<Sys06_MenuModel, String> 	editCell();
	ValueProvider<Sys06_MenuModel, String> 	moveCell();
	ValueProvider<Sys06_MenuModel, Boolean> roleMenuYn();
	
}



