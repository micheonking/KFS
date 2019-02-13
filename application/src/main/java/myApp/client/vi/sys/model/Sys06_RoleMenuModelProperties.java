package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys06_RoleMenuModelProperties extends PropertyAccess<Sys06_RoleMenuModel> {
	
	ModelKeyProvider<Sys06_RoleMenuModel> keyId();

	ValueProvider<Sys06_RoleMenuModel, Long  > roleMenuId() ;
	ValueProvider<Sys06_RoleMenuModel, Long  > roleId() ;
	ValueProvider<Sys06_RoleMenuModel, Long  > menuId() ;
	ValueProvider<Sys06_RoleMenuModel, String> useYn() ;
	ValueProvider<Sys06_RoleMenuModel, String> rmk() ;
	ValueProvider<Sys06_RoleMenuModel, String> insUsrNo() ;
	ValueProvider<Sys06_RoleMenuModel, Date  > insDate() ;
	ValueProvider<Sys06_RoleMenuModel, String> updUsrNo() ;
	ValueProvider<Sys06_RoleMenuModel, Date  > updDate() ;
	
}
