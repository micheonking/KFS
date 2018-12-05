package myApp.client.vi.apr.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Apr07_AltApprModelProperties extends PropertyAccess<Apr07_AltApprModel> {
	
	ModelKeyProvider<Apr07_AltApprModel> keyId();
	
	ValueProvider<Apr07_AltApprModel, Long>   altApprId();
	ValueProvider<Apr07_AltApprModel, Long>   targetEmpId();
	ValueProvider<Apr07_AltApprModel, String> targetEmpNo();
	ValueProvider<Apr07_AltApprModel, String> targetEmpName();
	ValueProvider<Apr07_AltApprModel, String> targetPositionName();
	ValueProvider<Apr07_AltApprModel, String> targetTitleName();
	ValueProvider<Apr07_AltApprModel, String> targetOrgName();
	ValueProvider<Apr07_AltApprModel, Date>   startDate();
	ValueProvider<Apr07_AltApprModel, Date>   closeDate();
	ValueProvider<Apr07_AltApprModel, Long>   altEmpId();
	ValueProvider<Apr07_AltApprModel, String> altEmpNo();
	ValueProvider<Apr07_AltApprModel, String> altEmpName();
	ValueProvider<Apr07_AltApprModel, String> altPositionName();
	ValueProvider<Apr07_AltApprModel, String> altTitleName();
	ValueProvider<Apr07_AltApprModel, String> altOrgName();
	ValueProvider<Apr07_AltApprModel, String> reasonCd();
	ValueProvider<Apr07_AltApprModel, String> emgrPoint();
	ValueProvider<Apr07_AltApprModel, String> note(); 
	
	ValueProvider<Apr07_AltApprModel, String> actionCell();
	

	
}
