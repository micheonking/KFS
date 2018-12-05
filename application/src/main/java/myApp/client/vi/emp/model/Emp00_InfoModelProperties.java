package myApp.client.vi.emp.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Emp00_InfoModelProperties extends PropertyAccess<Emp00_InfoModel> {

	ModelKeyProvider<Emp00_InfoModel> keyId();
	
	ValueProvider<Emp00_InfoModel, Long>	transId();
	ValueProvider<Emp00_InfoModel, Long> 	companyId();
	ValueProvider<Emp00_InfoModel, Long> 	personId();
	ValueProvider<Emp00_InfoModel, String> 	ctzNo();
	ValueProvider<Emp00_InfoModel, String> 	korName();
	ValueProvider<Emp00_InfoModel, String> 	engName();
	ValueProvider<Emp00_InfoModel, Date> 	birthday();
	ValueProvider<Emp00_InfoModel, String> 	mobileTelNo();
	ValueProvider<Emp00_InfoModel, String> 	homeTelNo();
	ValueProvider<Emp00_InfoModel, String> 	emergencyTelNo();
	ValueProvider<Emp00_InfoModel, String> 	emailAddress();
	ValueProvider<Emp00_InfoModel, String> 	nationCode();
	ValueProvider<Emp00_InfoModel, String> 	nationName();
	ValueProvider<Emp00_InfoModel, String> 	genderCode();
	ValueProvider<Emp00_InfoModel, String> 	genderName();
	ValueProvider<Emp00_InfoModel, Long> 	empId();
	ValueProvider<Emp00_InfoModel, String> 	empNo();
	ValueProvider<Emp00_InfoModel, String> 	empKindCode();
	ValueProvider<Emp00_InfoModel, String> 	empKindName();
	ValueProvider<Emp00_InfoModel, Date> 	hireDate();
	ValueProvider<Emp00_InfoModel, String> 	hireCode();
	ValueProvider<Emp00_InfoModel, String> 	hireName();
	ValueProvider<Emp00_InfoModel, Date> 	retireDate();
	ValueProvider<Emp00_InfoModel, String> 	retireCode();
	ValueProvider<Emp00_InfoModel, String> 	retireName();
	ValueProvider<Emp00_InfoModel, Date> 	transDate();
	ValueProvider<Emp00_InfoModel, Date> 	closeDate();
	ValueProvider<Emp00_InfoModel, String> 	positionCode();
	ValueProvider<Emp00_InfoModel, String> 	positionName();
	ValueProvider<Emp00_InfoModel, String> 	titleCode();
	ValueProvider<Emp00_InfoModel, String> 	titleName();
	ValueProvider<Emp00_InfoModel, Long> 	orgCodeId();
	ValueProvider<Emp00_InfoModel, String> 	orgCode();
	ValueProvider<Emp00_InfoModel, String> 	orgName();
	ValueProvider<Emp00_InfoModel,String> 	empId_String();
	ValueProvider<Emp00_InfoModel,String> 	personId_String();
	
	ValueProvider<Emp00_InfoModel, String> 	buttonCell();
	
}
