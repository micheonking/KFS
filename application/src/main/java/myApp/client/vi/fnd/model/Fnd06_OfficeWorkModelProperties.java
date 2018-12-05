package myApp.client.vi.fnd.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd06_OfficeWorkModelProperties extends PropertyAccess<Fnd06_OfficeWorkModel> {

	ModelKeyProvider<Fnd06_OfficeWorkModel> keyId();

	ValueProvider<Fnd06_OfficeWorkModel,	Long>		officeWorkId(); 
	ValueProvider<Fnd06_OfficeWorkModel,	Long>		companyId();
	ValueProvider<Fnd06_OfficeWorkModel,	String>		officeWorkCode();
	ValueProvider<Fnd06_OfficeWorkModel,	String>		officeWorkName();
	ValueProvider<Fnd06_OfficeWorkModel,	Boolean>	officeWorkUseYn();
	ValueProvider<Fnd06_OfficeWorkModel,	String>		officeWorkNote();

}
