package myApp.client.vi.cst.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Cst02_AccountModelProperties extends PropertyAccess<Cst02_AccountModel> {

	ModelKeyProvider<Cst02_AccountModel> keyId();

	ValueProvider<Cst02_AccountModel,	Long>	accountId();
	ValueProvider<Cst02_AccountModel,	Long>	userId();
	ValueProvider<Cst02_AccountModel,	String>	mgCode();
	ValueProvider<Cst02_AccountModel,	String>	mgName();
	ValueProvider<Cst02_AccountModel,	String>	accountNo();
	ValueProvider<Cst02_AccountModel,	String>	fundCode();
	ValueProvider<Cst02_AccountModel,	String>	accountName();
	ValueProvider<Cst02_AccountModel,	String>	accountBranch();
	ValueProvider<Cst02_AccountModel,	String>	branchManager();
	ValueProvider<Cst02_AccountModel,	String>	managerContact();

}
