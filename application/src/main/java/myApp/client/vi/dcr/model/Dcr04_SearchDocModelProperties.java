package myApp.client.vi.dcr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dcr04_SearchDocModelProperties extends PropertyAccess<Dcr04_SearchDocModel> {

	ModelKeyProvider<Dcr04_SearchDocModel>      keyId();
	ValueProvider<Dcr04_SearchDocModel, Long>   seqId();

	@Path("docModel.typeName")
	ValueProvider<Dcr04_SearchDocModel, String> typeName();
	
	@Path("docModel.docName")
	ValueProvider<Dcr04_SearchDocModel, String> docName();
	
	@Path("classTreeModel.classTreeName")
	ValueProvider<Dcr04_SearchDocModel, String> classTreeName();
	
	@Path("docModel.actionCell")
	ValueProvider<Dcr04_SearchDocModel, String> actionCell();
	
	@Path("docModel.regDate")
	ValueProvider<Dcr04_SearchDocModel, Date> regDate();
	
	@Path("docModel.regEmpName")
	ValueProvider<Dcr04_SearchDocModel, String> regEmpName();
	
	@Path("docModel.orgName")
	ValueProvider<Dcr04_SearchDocModel, String> orgName();
	
	@Path("fundModel.fundName")
	ValueProvider<Dcr04_SearchDocModel, String> fundName();
	
}