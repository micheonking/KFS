package myApp.client.vi.fnd.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd08_OrgAuthModelProperties extends PropertyAccess<Fnd08_OrgAuthModel> {
	
	ModelKeyProvider<Fnd08_OrgAuthModel> keyId();
	
	ValueProvider<Fnd08_OrgAuthModel, Long>		orgAuthId();
	ValueProvider<Fnd08_OrgAuthModel, Long>		fundCodeId();
	ValueProvider<Fnd08_OrgAuthModel, Long>		orgCodeId();
	ValueProvider<Fnd08_OrgAuthModel, String>	authYn();
	ValueProvider<Fnd08_OrgAuthModel, Boolean>	authYnCheck();
	ValueProvider<Fnd08_OrgAuthModel, String>	note() ;
	
}
