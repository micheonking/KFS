package myApp.client.vi.org.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Org02_InfoModelProperties extends PropertyAccess<Org02_InfoModel> {
	
	ModelKeyProvider<Org02_InfoModel> 		keyId();	
	ValueProvider<Org02_InfoModel, Long> 	infoId();
	ValueProvider<Org02_InfoModel, Date> 	modDate();
	ValueProvider<Org02_InfoModel, String>  modReason();
	ValueProvider<Org02_InfoModel, String>  modDetail();
	ValueProvider<Org02_InfoModel, Long> 	parentCodeId();
	ValueProvider<Org02_InfoModel, Long> 	codeId();
	ValueProvider<Org02_InfoModel, String>  korName();
	ValueProvider<Org02_InfoModel, String>  shortName();
	ValueProvider<Org02_InfoModel, String>  engName();
	ValueProvider<Org02_InfoModel, String>  levelCode();
	ValueProvider<Org02_InfoModel, String>  levelName();
	ValueProvider<Org02_InfoModel, String>  sortOrder();
	ValueProvider<Org02_InfoModel, String>  note();
}

