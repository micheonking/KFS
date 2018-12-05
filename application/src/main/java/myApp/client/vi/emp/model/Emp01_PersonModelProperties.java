package myApp.client.vi.emp.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Emp01_PersonModelProperties extends PropertyAccess<Emp01_PersonModel> {

	ModelKeyProvider<Emp01_PersonModel> keyId();
	ValueProvider<Emp01_PersonModel, Long>   personId();
	ValueProvider<Emp01_PersonModel, Long>   companyId();
	ValueProvider<Emp01_PersonModel, String> korName();
	ValueProvider<Emp01_PersonModel, String> engName();
	ValueProvider<Emp01_PersonModel, String> chnName();
	ValueProvider<Emp01_PersonModel, String> nationCode();
	ValueProvider<Emp01_PersonModel, String> nationName();
	ValueProvider<Emp01_PersonModel, String> ctzNo();
	ValueProvider<Emp01_PersonModel, String> genderCode();
	ValueProvider<Emp01_PersonModel, String> genderName();
	ValueProvider<Emp01_PersonModel, Date>   birthday();
	ValueProvider<Emp01_PersonModel, String> mobileTelno();
	ValueProvider<Emp01_PersonModel, String> homeTelno();
	ValueProvider<Emp01_PersonModel, String> emgrcyTelno();
	ValueProvider<Emp01_PersonModel, String> emailAddr();
	ValueProvider<Emp01_PersonModel, String> zipCode();
	ValueProvider<Emp01_PersonModel, String> zipAddr();
	ValueProvider<Emp01_PersonModel, String> addrDetail();
	ValueProvider<Emp01_PersonModel, String> note();
}
