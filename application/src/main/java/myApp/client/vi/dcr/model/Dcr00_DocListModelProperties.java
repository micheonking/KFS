package myApp.client.vi.dcr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;



public interface Dcr00_DocListModelProperties extends PropertyAccess<Dcr00_DocListModel> {

	ModelKeyProvider<Dcr00_DocListModel> keyId();

	ValueProvider<Dcr00_DocListModel,	Long>	docId();
	ValueProvider<Dcr00_DocListModel,	String>	classTreeName();
	ValueProvider<Dcr00_DocListModel,	Long>	parentTreeId();
	ValueProvider<Dcr00_DocListModel,	String>	fundTypeName();
	ValueProvider<Dcr00_DocListModel,	String>	fundCode();
	ValueProvider<Dcr00_DocListModel,	String>	fundName();
	ValueProvider<Dcr00_DocListModel,	String>	fileName();
	ValueProvider<Dcr00_DocListModel,	Date>	regDate();
	ValueProvider<Dcr00_DocListModel,	Long>	regEmpId();
	ValueProvider<Dcr00_DocListModel,	String>	empName();
	ValueProvider<Dcr00_DocListModel,	String>	fundClose();
	ValueProvider<Dcr00_DocListModel,	String>	publicSubscribe();
	ValueProvider<Dcr00_DocListModel,	String>	sintak();
	ValueProvider<Dcr00_DocListModel,	String>	fundType();
	ValueProvider<Dcr00_DocListModel,	String>	fundStartDate();
	ValueProvider<Dcr00_DocListModel,	String>	fundEndDate();
	ValueProvider<Dcr00_DocListModel,	String>	aprTitle();
	ValueProvider<Dcr00_DocListModel,	String>	orgName();
	ValueProvider<Dcr00_DocListModel, String > actionCell();
	ValueProvider<Dcr00_DocListModel,	Long>	apprId();
	ValueProvider<Dcr00_DocListModel,	Long>	classTreeId();
	ValueProvider<Dcr00_DocListModel,	String>	parentTreeName();

	@Path("apprModel.effectDate")
	ValueProvider<Dcr00_DocListModel, Date> effectDate();
	
	
}
