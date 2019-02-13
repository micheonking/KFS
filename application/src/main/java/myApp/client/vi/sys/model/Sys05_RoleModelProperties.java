package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys05_RoleModelProperties extends PropertyAccess<Sys05_RoleModel> {
	
	ModelKeyProvider<Sys05_RoleModel> keyId();

	ValueProvider<Sys05_RoleModel, Long  > roleId() ;
	ValueProvider<Sys05_RoleModel, String> roleName() ;
	ValueProvider<Sys05_RoleModel, Long  > seq() ;
	ValueProvider<Sys05_RoleModel, String> cmpCode() ;
	ValueProvider<Sys05_RoleModel, String> rmk() ;
	ValueProvider<Sys05_RoleModel, String> insUsrNo() ;
	ValueProvider<Sys05_RoleModel, Date  > insDate() ;
	ValueProvider<Sys05_RoleModel, String> updUsrNo() ;
	ValueProvider<Sys05_RoleModel, Date  > updDate() ;
	
}
