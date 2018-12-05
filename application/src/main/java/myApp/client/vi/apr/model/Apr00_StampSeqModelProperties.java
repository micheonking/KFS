package myApp.client.vi.apr.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Apr00_StampSeqModelProperties extends PropertyAccess<Apr00_StampSeqModel> {
	
	ModelKeyProvider<Apr00_StampSeqModel> keyId();

	ValueProvider<Apr00_StampSeqModel, Long> apprId(); 
	ValueProvider<Apr00_StampSeqModel, String> stampSeq();
	ValueProvider<Apr00_StampSeqModel, Long> stampId();
	ValueProvider<Apr00_StampSeqModel, Date> apprDate(); 
	ValueProvider<Apr00_StampSeqModel, String> regEmpName(); 
	ValueProvider<Apr00_StampSeqModel, String> apprEmpName(); 
	ValueProvider<Apr00_StampSeqModel, String> title();
	ValueProvider<Apr00_StampSeqModel, String> stampName(); 
	ValueProvider<Apr00_StampSeqModel, String> receiveName(); 
	ValueProvider<Apr00_StampSeqModel, String> note();

}
