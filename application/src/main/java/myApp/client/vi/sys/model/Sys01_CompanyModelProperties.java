package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys01_CompanyModelProperties extends PropertyAccess<Sys01_CompanyModel> {
	
	ModelKeyProvider<Sys01_CompanyModel> keyId();
	
	ValueProvider<Sys01_CompanyModel, Long >	companyId() ; 	
	ValueProvider<Sys01_CompanyModel, String> 	companyName() ;
	ValueProvider<Sys01_CompanyModel, String>	bizNo() ;
	ValueProvider<Sys01_CompanyModel, String>	telNo01() ;
	ValueProvider<Sys01_CompanyModel, String>	telNo02() ;
	ValueProvider<Sys01_CompanyModel, String>	faxNo01() ;
	ValueProvider<Sys01_CompanyModel, String>	zipCode() ;
	ValueProvider<Sys01_CompanyModel, String>	zipAddress() ;
	ValueProvider<Sys01_CompanyModel, String>	zipDetail() ;
	ValueProvider<Sys01_CompanyModel, String>	locationName() ;
	ValueProvider<Sys01_CompanyModel, Date>		annvDate() ;
	ValueProvider<Sys01_CompanyModel, String>	note() ;
	
	ValueProvider<Sys01_CompanyModel, Boolean>	menuYnChecked() ; // 회사별 메뉴사용여부 체크.
	
}
