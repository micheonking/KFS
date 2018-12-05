package myApp.client.vi.emp.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Emp10_ManagerContactModelProperties extends PropertyAccess<Emp10_ManagerContactModel> {

	ModelKeyProvider<Emp10_ManagerContactModel> keyId();

	ValueProvider<Emp10_ManagerContactModel,	Long>	managerId();
	ValueProvider<Emp10_ManagerContactModel,	String>	managerName();
	ValueProvider<Emp10_ManagerContactModel,	String>	managerEmail();
	ValueProvider<Emp10_ManagerContactModel,	String>	managerMobile();
	ValueProvider<Emp10_ManagerContactModel,	String>	managerTelephone();
	ValueProvider<Emp10_ManagerContactModel,	String>	managerCompanyName();
	ValueProvider<Emp10_ManagerContactModel,	Long>	empId();

}
