package myApp.client.vi.dbm.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dbm00_DDLRunModelProperties extends PropertyAccess<Dbm00_DDLRunModel> {
	
	ModelKeyProvider<Dbm00_DDLRunModel> keyId();

	ValueProvider<Dbm00_DDLRunModel, Long>	id();
	ValueProvider<Dbm00_DDLRunModel, Long>	errorId();

}
