package myApp.client.vi.apr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Apr05_RelateDocModelProperties extends PropertyAccess<Apr05_RelateDocModel> {
	
	ModelKeyProvider<Apr05_RelateDocModel> keyId();
	
	ValueProvider<Apr05_RelateDocModel, Long> relateDocId(); 
	ValueProvider<Apr05_RelateDocModel, Long> apprId(); 
	ValueProvider<Apr05_RelateDocModel, String> gubun();
	ValueProvider<Apr05_RelateDocModel, String> docTypeName();
	ValueProvider<Apr05_RelateDocModel, String> regEmpInfo();
	
	ValueProvider<Apr05_RelateDocModel, String> actionCell();
	
	@Path("aprModel.title")
	ValueProvider<Apr05_RelateDocModel, String> title();

	@Path("aprModel.regDate")
	ValueProvider<Apr05_RelateDocModel, Date> regDate();
	
	@Path("fileModel.fileId")
	ValueProvider<Apr05_RelateDocModel, Long> fileId();
	
	@Path("fileModel.fileName")
	ValueProvider<Apr05_RelateDocModel, String> fileName();
	
	@Path("fundModel.fundName")
	ValueProvider<Apr05_RelateDocModel, String> fundName();
	
	
}
