package myApp.client.vi.emp.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Emp10_AddrBookModelProperties extends PropertyAccess<Emp10_AddrBookModel> {

	ModelKeyProvider<Emp10_AddrBookModel> keyId();

	ValueProvider<Emp10_AddrBookModel,	Long>	addrBookId();
	ValueProvider<Emp10_AddrBookModel,	String>	personName();
	ValueProvider<Emp10_AddrBookModel,	String>	email();
	ValueProvider<Emp10_AddrBookModel,	String>	mobile();
	ValueProvider<Emp10_AddrBookModel,	String>	officeTel1();
	ValueProvider<Emp10_AddrBookModel,	String>	companyName();
	ValueProvider<Emp10_AddrBookModel,	String>	posName();
	ValueProvider<Emp10_AddrBookModel,	String>	titleName();
	ValueProvider<Emp10_AddrBookModel,	Long>	empId();
	ValueProvider<Emp10_AddrBookModel,	String>	orgName();
	ValueProvider<Emp10_AddrBookModel,	String>	note();

}
