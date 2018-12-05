package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Sys90_AfterServiceModelProperties extends PropertyAccess<Sys90_AfterServiceModel> {

	ModelKeyProvider<Sys90_AfterServiceModel> keyId();

	ValueProvider<Sys90_AfterServiceModel,	Long>	afterServiceId();
	ValueProvider<Sys90_AfterServiceModel,	Date>	happenStartDate();
	ValueProvider<Sys90_AfterServiceModel,	String>	typeYn();
	ValueProvider<Sys90_AfterServiceModel,	String>	happenReason();
	ValueProvider<Sys90_AfterServiceModel,	String>	happenRemedy();
	ValueProvider<Sys90_AfterServiceModel,	String>	worker();
	ValueProvider<Sys90_AfterServiceModel,	Date>	dueDate();
	ValueProvider<Sys90_AfterServiceModel,	Boolean>	closeYn();
	ValueProvider<Sys90_AfterServiceModel,	String>	remarks();
	ValueProvider<Sys90_AfterServiceModel,	Long>	regEmpId();
	ValueProvider<Sys90_AfterServiceModel,	Long>	companyId();
	
	ValueProvider<Sys90_AfterServiceModel,	String>	afterServiceName();
	ValueProvider<Sys90_AfterServiceModel,	String>	closeName();
	ValueProvider<Sys90_AfterServiceModel,	String>	regEmpName();

}
