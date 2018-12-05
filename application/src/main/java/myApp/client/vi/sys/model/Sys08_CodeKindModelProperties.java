package myApp.client.vi.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys08_CodeKindModelProperties extends PropertyAccess<Sys08_CodeKindModel> {
	
	ModelKeyProvider<Sys08_CodeKindModel> keyId();
	
	ValueProvider<Sys08_CodeKindModel, Long> 		codeKindId();
	ValueProvider<Sys08_CodeKindModel, String> 	kindCode();
	ValueProvider<Sys08_CodeKindModel, String> 	kindName();
	ValueProvider<Sys08_CodeKindModel, Boolean> 	sysYnFlag();
	ValueProvider<Sys08_CodeKindModel, String> 	note();
}
