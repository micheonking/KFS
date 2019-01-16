package myApp.client.vi.cst.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Cst99_UserModelProperties extends PropertyAccess<Cst99_UserModel> {

	ModelKeyProvider<Cst99_UserModel> keyId();

	ValueProvider<Cst99_UserModel,	Long>	userId();
	ValueProvider<Cst99_UserModel,	String>	email();
	ValueProvider<Cst99_UserModel,	String>	userName();
	ValueProvider<Cst99_UserModel,	String>	phoneNo();
	ValueProvider<Cst99_UserModel,	String>	zipNo();
	ValueProvider<Cst99_UserModel,	String>	zipAddress();
	ValueProvider<Cst99_UserModel,	String>	refreshTime();
	ValueProvider<Cst99_UserModel,	Date>	startDt();
	ValueProvider<Cst99_UserModel,	Date>	endDt();
	ValueProvider<Cst99_UserModel,	String>	mrdRole();
	ValueProvider<Cst99_UserModel,	Long>	companyId();

}
