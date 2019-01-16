package myApp.client.vi.cst.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Cst01_UserModelProperties extends PropertyAccess<Cst01_UserModel> {

	ModelKeyProvider<Cst01_UserModel> keyId();

	ValueProvider<Cst01_UserModel,	Long>	userId();
	ValueProvider<Cst01_UserModel,	String>	email();
	ValueProvider<Cst01_UserModel,	String>	userName();
	ValueProvider<Cst01_UserModel,	String>	phoneNo();
	ValueProvider<Cst01_UserModel,	String>	zipNo();
	ValueProvider<Cst01_UserModel,	String>	zipAddress();
	ValueProvider<Cst01_UserModel,	String>	refreshTime();
	ValueProvider<Cst01_UserModel,	Date>	startDt();
	ValueProvider<Cst01_UserModel,	Date>	endDt();
	ValueProvider<Cst01_UserModel,	String>	mrdRole();
	ValueProvider<Cst01_UserModel,	Long>	companyId();

}
