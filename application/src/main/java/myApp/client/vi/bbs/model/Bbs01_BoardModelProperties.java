package myApp.client.vi.bbs.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Bbs01_BoardModelProperties extends PropertyAccess<Bbs01_BoardModel> {
	
	ModelKeyProvider<Bbs01_BoardModel> keyId();

	ValueProvider<Bbs01_BoardModel,	Long>	boardId();
	ValueProvider<Bbs01_BoardModel,	String>	typeCode();
	ValueProvider<Bbs01_BoardModel,	String>	title();
	ValueProvider<Bbs01_BoardModel,	String>	content();
	ValueProvider<Bbs01_BoardModel,	Long>	writeUserId();
	ValueProvider<Bbs01_BoardModel,	Long>	companyId();
	ValueProvider<Bbs01_BoardModel,	Date>	writeDate();
	ValueProvider<Bbs01_BoardModel,	String>	note();
	ValueProvider<Bbs01_BoardModel,	String>	typeName();
	
	@Path("userModel.korName")
	ValueProvider<Bbs01_BoardModel, String>	korName();

	ValueProvider<Bbs01_BoardModel, String> writeUserName();

}
