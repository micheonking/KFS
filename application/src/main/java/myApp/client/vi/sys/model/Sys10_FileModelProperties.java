package myApp.client.vi.sys.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys10_FileModelProperties extends PropertyAccess<Sys10_FileModel> {
	
	ModelKeyProvider<Sys10_FileModel> keyId();
	
	ValueProvider<Sys10_FileModel, Long> 	fileId();
	ValueProvider<Sys10_FileModel, Long> 	parentId();
	ValueProvider<Sys10_FileModel, String> 	fileName();
//	ValueProvider<Sys10_FileModel, String> 	fullFileName();
	ValueProvider<Sys10_FileModel, Date> 	regDate();
	ValueProvider<Sys10_FileModel, String> 	serverPath();
	ValueProvider<Sys10_FileModel, Double> 	size();
	ValueProvider<Sys10_FileModel, Date> 	delDate();
	ValueProvider<Sys10_FileModel, String> 	note();
	ValueProvider<Sys10_FileModel, String> 	ext();
	ValueProvider<Sys10_FileModel, String> 	titleYn();
	
	ValueProvider<Sys10_FileModel, String> 	downloadCell();
	ValueProvider<Sys10_FileModel, String> 	deleteCell();
	
	
	@Path("apprModel.title")
	ValueProvider<Sys10_FileModel,	String>	title();

	@Path("apprModel.regDate")
	ValueProvider<Sys10_FileModel, Date> apprDate();
	
	@Path("apprModel.effectDate")
	ValueProvider<Sys10_FileModel, Date> effectDate();
	
	ValueProvider<Sys10_FileModel, String> 	actionCell();	
	
}
