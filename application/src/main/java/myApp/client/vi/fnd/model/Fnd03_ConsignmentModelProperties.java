package myApp.client.vi.fnd.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd03_ConsignmentModelProperties extends PropertyAccess<Fnd03_ConsignmentModel> {

	ModelKeyProvider<Fnd03_ConsignmentModel> keyId();
	ValueProvider<Fnd03_ConsignmentModel,	Long>	companyId();
	ValueProvider<Fnd03_ConsignmentModel,	Long>	consignmentId();
	ValueProvider<Fnd03_ConsignmentModel,	String>	consignmentCode();
	ValueProvider<Fnd03_ConsignmentModel,	String>	consignmentName();
	ValueProvider<Fnd03_ConsignmentModel,	Boolean>	consignmentUseYn();
	ValueProvider<Fnd03_ConsignmentModel,	String>	consignmentNote();

}
