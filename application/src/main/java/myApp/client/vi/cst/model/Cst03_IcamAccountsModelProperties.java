package myApp.client.vi.cst.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Cst03_IcamAccountsModelProperties extends PropertyAccess<Cst03_IcamAccountsModel> {

	ModelKeyProvider<Cst03_IcamAccountsModel> keyId();

	ValueProvider<Cst03_IcamAccountsModel,	Long>	icamAccountId();
	ValueProvider<Cst03_IcamAccountsModel,	String>	fundCode();
	ValueProvider<Cst03_IcamAccountsModel,	String>	fundName();
	ValueProvider<Cst03_IcamAccountsModel,	String>	no();
	ValueProvider<Cst03_IcamAccountsModel,	Date>	seoljDate();
	ValueProvider<Cst03_IcamAccountsModel,	String>	gb();
	ValueProvider<Cst03_IcamAccountsModel,	Long>	seoljYear();
	ValueProvider<Cst03_IcamAccountsModel,	Long>	gigan();
	ValueProvider<Cst03_IcamAccountsModel,	Long>	basicPer();
	ValueProvider<Cst03_IcamAccountsModel,	Long>	bmPer();
	ValueProvider<Cst03_IcamAccountsModel,	Long>	successPer();
	ValueProvider<Cst03_IcamAccountsModel,	String>	unyongName();
	ValueProvider<Cst03_IcamAccountsModel,	String>	csGb();
	ValueProvider<Cst03_IcamAccountsModel,	Long>	seoljAek();
	ValueProvider<Cst03_IcamAccountsModel,	String>	mgCode();
	ValueProvider<Cst03_IcamAccountsModel,	String>	mgCodeName();
	ValueProvider<Cst03_IcamAccountsModel,	String>	mgName();
	ValueProvider<Cst03_IcamAccountsModel,	String>	mgjjName();
	ValueProvider<Cst03_IcamAccountsModel,	Date>	gyulDate();

}
