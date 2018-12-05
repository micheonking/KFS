package myApp.client.vi.emp.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Emp03_TransModelProperties extends PropertyAccess<Emp03_TransModel> {

	ModelKeyProvider<Emp03_TransModel> keyId();
	ValueProvider<Emp03_TransModel, Long> transId();
	ValueProvider<Emp03_TransModel, Long> empId();
	ValueProvider<Emp03_TransModel, Date> transDate();
	ValueProvider<Emp03_TransModel, String> transCode();
	ValueProvider<Emp03_TransModel, String> transName();
	ValueProvider<Emp03_TransModel, Long> orgCodeId();
	ValueProvider<Emp03_TransModel, String> posCode();
	ValueProvider<Emp03_TransModel, String> posName();
	ValueProvider<Emp03_TransModel, String> titleCode();
	ValueProvider<Emp03_TransModel, String> titleName();
	ValueProvider<Emp03_TransModel, String> gradeCode();
	ValueProvider<Emp03_TransModel, String> gradeName();
	ValueProvider<Emp03_TransModel, String> transReason();
	ValueProvider<Emp03_TransModel, String> transNote();
	ValueProvider<Emp03_TransModel, Date> addTitleCloseDate();
	
	ValueProvider<Emp03_TransModel, String> actionCell();
	
	@Path("orgInfoModel.korName")
	ValueProvider<Emp03_TransModel, String> orgKorName();

}
