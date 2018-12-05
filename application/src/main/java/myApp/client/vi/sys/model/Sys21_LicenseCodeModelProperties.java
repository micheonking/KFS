package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys21_LicenseCodeModelProperties extends PropertyAccess<Sys21_LicenseCodeModel> {
	
	ModelKeyProvider<Sys21_LicenseCodeModel> keyId();
	
	ValueProvider<Sys21_LicenseCodeModel, Long> 		licenseCodeId();
	ValueProvider<Sys21_LicenseCodeModel, String> 	licenseCode();
	ValueProvider<Sys21_LicenseCodeModel, String> 	licenseName();
	ValueProvider<Sys21_LicenseCodeModel, String> 	issueOrgName();
	ValueProvider<Sys21_LicenseCodeModel, Date> 		applyDate();
	ValueProvider<Sys21_LicenseCodeModel, Boolean>	closeYnFlag();
	ValueProvider<Sys21_LicenseCodeModel, String> 	note();
}
