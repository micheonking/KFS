package myApp.client.vi.emp.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Emp04_AddTitleModelProperties extends PropertyAccess<Emp04_AddTitleModel> {
	
	ModelKeyProvider<Emp04_AddTitleModel> keyId();

	ValueProvider<Emp04_AddTitleModel, Long> addTitleId();
	ValueProvider<Emp04_AddTitleModel, Long> orgCodeId();
	ValueProvider<Emp04_AddTitleModel, String> orgName();
	ValueProvider<Emp04_AddTitleModel, Long> empId();
	ValueProvider<Emp04_AddTitleModel, Date> startDate();
	ValueProvider<Emp04_AddTitleModel, Date> closeDate();
	ValueProvider<Emp04_AddTitleModel, String> titleCode();
	ValueProvider<Emp04_AddTitleModel, String> titleName();
	ValueProvider<Emp04_AddTitleModel, Long> reason();
	ValueProvider<Emp04_AddTitleModel, String> note();
	
}
