package myApp.client.vi.hom.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Hom02_BoardModelProperties extends PropertyAccess<Hom02_BoardModel> {

	ModelKeyProvider<Hom02_BoardModel> keyId();

	ValueProvider<Hom02_BoardModel,	Long>	boardId();
	ValueProvider<Hom02_BoardModel,	String>	typeCode();
	ValueProvider<Hom02_BoardModel,	String>	titleName();
	ValueProvider<Hom02_BoardModel,	Date>	settleDate();
	ValueProvider<Hom02_BoardModel,	Long>	cnt();
	ValueProvider<Hom02_BoardModel,	String>	fileName();
	ValueProvider<Hom02_BoardModel,	String>	filePath();
	ValueProvider<Hom02_BoardModel,	String>	writer();
	ValueProvider<Hom02_BoardModel,	String>	contents();
	
}
