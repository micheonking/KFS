package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys07_UsrRoleModelProperties extends PropertyAccess<Sys07_UsrRoleModel> {
	
	ModelKeyProvider<Sys07_UsrRoleModel> keyId();

	ValueProvider<Sys07_UsrRoleModel, Long  > usrRoleId() ;
	ValueProvider<Sys07_UsrRoleModel, String> cmpCode() ;
	ValueProvider<Sys07_UsrRoleModel, String> usrNo() ;
	ValueProvider<Sys07_UsrRoleModel, Long  > roleId() ;
	ValueProvider<Sys07_UsrRoleModel, String> rmk() ;
	ValueProvider<Sys07_UsrRoleModel, String> insUsrNo() ;
	ValueProvider<Sys07_UsrRoleModel, Date  > insDate() ;
	ValueProvider<Sys07_UsrRoleModel, String> updUsrNo() ;
	ValueProvider<Sys07_UsrRoleModel, Date  > updDate() ;
	
}
