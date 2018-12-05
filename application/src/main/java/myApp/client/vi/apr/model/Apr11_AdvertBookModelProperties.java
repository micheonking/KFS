package myApp.client.vi.apr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;



public interface Apr11_AdvertBookModelProperties extends PropertyAccess<Apr11_AdvertBookModel> {

	ModelKeyProvider<Apr11_AdvertBookModel> keyId();

	ValueProvider<Apr11_AdvertBookModel, Long> advertBookId();  
	ValueProvider<Apr11_AdvertBookModel, Long> companyId(); 
	ValueProvider<Apr11_AdvertBookModel, Long> apprId();        
	ValueProvider<Apr11_AdvertBookModel, String> advertSeq();     
	ValueProvider<Apr11_AdvertBookModel, String> advertTypeName(); 
	ValueProvider<Apr11_AdvertBookModel, String> mediaName(); 
	ValueProvider<Apr11_AdvertBookModel, Long> count(); 
	ValueProvider<Apr11_AdvertBookModel, String> note(); 
	
	@Path("apprStepModel.apprDate")
	ValueProvider<Apr11_AdvertBookModel, Date> apprDate();

	@Path("apprModel.title")
	ValueProvider<Apr11_AdvertBookModel, String> title();

	@Path("empModel.korName")
	ValueProvider<Apr11_AdvertBookModel, String> empName();
	
	ValueProvider<Apr11_AdvertBookModel, String> actionCell();// 그리드에 버튼을 넣기 
	
}
