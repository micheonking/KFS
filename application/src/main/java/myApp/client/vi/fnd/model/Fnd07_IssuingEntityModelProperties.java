package myApp.client.vi.fnd.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd07_IssuingEntityModelProperties extends PropertyAccess<Fnd07_IssuingEntityModel> {

	ModelKeyProvider<Fnd07_IssuingEntityModel> keyId();


	ValueProvider<Fnd07_IssuingEntityModel,	Long>	issuingEntityId();
	ValueProvider<Fnd07_IssuingEntityModel,	Long>	companyId();
	ValueProvider<Fnd07_IssuingEntityModel,	String>	issuingEntityCode();
	ValueProvider<Fnd07_IssuingEntityModel,	String>	issuingEntityName();
	ValueProvider<Fnd07_IssuingEntityModel,	String>	issuingEntityAttachCode();
	ValueProvider<Fnd07_IssuingEntityModel,	Long>	issuingEntityFaceValue();
	ValueProvider<Fnd07_IssuingEntityModel,	String>	issuingEntitySettleMonth();
	ValueProvider<Fnd07_IssuingEntityModel,	String>	issuingEntityNationCode();
	ValueProvider<Fnd07_IssuingEntityModel,	Boolean	>	issuingEntityUseYn();
	ValueProvider<Fnd07_IssuingEntityModel,	String>	issuingEntityNote();
	
	ValueProvider<Fnd07_IssuingEntityModel,	String	>	kukaName();

}
