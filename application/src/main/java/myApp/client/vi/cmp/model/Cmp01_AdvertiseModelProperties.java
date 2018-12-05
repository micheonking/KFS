package myApp.client.vi.cmp.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Cmp01_AdvertiseModelProperties extends PropertyAccess<Cmp01_AdvertiseModel> {

	ModelKeyProvider<Cmp01_AdvertiseModel> keyId();

	ValueProvider<Cmp01_AdvertiseModel,	Long>	advertiseId();
	ValueProvider<Cmp01_AdvertiseModel,	String>	advertiseNo();
	ValueProvider<Cmp01_AdvertiseModel,	Date>	screeningDate();
	ValueProvider<Cmp01_AdvertiseModel,	String>	advertiseType();
	ValueProvider<Cmp01_AdvertiseModel,	String>	mediaType();
	ValueProvider<Cmp01_AdvertiseModel,	String>	advertiseNote();
	ValueProvider<Cmp01_AdvertiseModel,	String>	managerEmpId();
	ValueProvider<Cmp01_AdvertiseModel,	String>	advertiseCount();
	ValueProvider<Cmp01_AdvertiseModel,	String>	empName();

}
