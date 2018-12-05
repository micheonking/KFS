package myApp.client.vi.bbs.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Bbs02_BoardModelProperties extends PropertyAccess<Bbs02_BoardModel> {
	
	ModelKeyProvider<Bbs02_BoardModel> keyId();

	ValueProvider<Bbs02_BoardModel,	Long>	boardId();
	ValueProvider<Bbs02_BoardModel,	String>	typeCode();
	ValueProvider<Bbs02_BoardModel,	String>	titleName();
	ValueProvider<Bbs02_BoardModel,	Date>	setdate();
	ValueProvider<Bbs02_BoardModel,	Long>	cnt();
	ValueProvider<Bbs02_BoardModel,	String>	fileName();
	ValueProvider<Bbs02_BoardModel,	String>	filePath();
	ValueProvider<Bbs02_BoardModel,	String>	writer();
	ValueProvider<Bbs02_BoardModel,	String>	contents();
	
}
