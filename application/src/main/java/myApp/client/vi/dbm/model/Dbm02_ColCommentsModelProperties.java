package myApp.client.vi.dbm.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dbm02_ColCommentsModelProperties extends PropertyAccess<Dbm02_ColCommentsModel> {
	
	ModelKeyProvider<Dbm02_ColCommentsModel> keyId();
	
	ValueProvider<Dbm02_ColCommentsModel, Long>	columnId();
	ValueProvider<Dbm02_ColCommentsModel, String>	columnName();
	ValueProvider<Dbm02_ColCommentsModel, String>	dataType();
	ValueProvider<Dbm02_ColCommentsModel, String>	columnComment();
	ValueProvider<Dbm02_ColCommentsModel, String>	nullAble();
	ValueProvider<Dbm02_ColCommentsModel, String>	columnLength();
	ValueProvider<Dbm02_ColCommentsModel, String>	tableName();

}
