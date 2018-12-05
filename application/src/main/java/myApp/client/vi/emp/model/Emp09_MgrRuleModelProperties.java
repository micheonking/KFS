package myApp.client.vi.emp.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Emp09_MgrRuleModelProperties extends PropertyAccess<Emp09_MgrRuleModel> {

	ModelKeyProvider<Emp09_MgrRuleModel> keyId();

	ValueProvider<Emp09_MgrRuleModel, Long>    mgrRuleId();
	ValueProvider<Emp09_MgrRuleModel, Long>    companyId();
	ValueProvider<Emp09_MgrRuleModel, String>  titleCd();
	ValueProvider<Emp09_MgrRuleModel, Boolean> useYn();
	ValueProvider<Emp09_MgrRuleModel, String>  note();
	ValueProvider<Emp09_MgrRuleModel, String>  seq();
	
}
