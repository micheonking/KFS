package myApp.client.vi.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Sys02_UserModelProperties  extends PropertyAccess<Sys02_UserModel> {
	ModelKeyProvider<Sys02_UserModel> keyId();
		
	ValueProvider<Sys02_UserModel, Long> 		userId();
	ValueProvider<Sys02_UserModel, Long> 		companyId();
	ValueProvider<Sys02_UserModel, String> 	korName(); 
	ValueProvider<Sys02_UserModel, String> 	email(); 
	ValueProvider<Sys02_UserModel, String> 	telNo01(); 
	ValueProvider<Sys02_UserModel, String> 	telNo02(); 
	ValueProvider<Sys02_UserModel, String> 	faxNo(); 
	ValueProvider<Sys02_UserModel, String> 	loginId();
	ValueProvider<Sys02_UserModel, String> 	passwd();
	ValueProvider<Sys02_UserModel, String> 	note();
}
