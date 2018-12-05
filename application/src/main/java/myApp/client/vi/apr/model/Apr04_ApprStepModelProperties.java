package myApp.client.vi.apr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Apr04_ApprStepModelProperties extends PropertyAccess<Apr04_ApprStepModel> {
	
	ModelKeyProvider<Apr04_ApprStepModel> keyId();
	
	ValueProvider<Apr04_ApprStepModel, Long> apprStepId(); 
	ValueProvider<Apr04_ApprStepModel, Long> apprId(); 
	ValueProvider<Apr04_ApprStepModel, String> stepTypeCode(); 
	ValueProvider<Apr04_ApprStepModel, String> stepTypeName(); 
	ValueProvider<Apr04_ApprStepModel, String> seq(); 
	ValueProvider<Apr04_ApprStepModel, String> stepStatusCode(); 
	ValueProvider<Apr04_ApprStepModel, String> stepStatusName(); 
	ValueProvider<Apr04_ApprStepModel, Date> apprDate();
	ValueProvider<Apr04_ApprStepModel, String> note(); 
	ValueProvider<Apr04_ApprStepModel, String> rejectReason(); 
	ValueProvider<Apr04_ApprStepModel, Long> stepEmpId();
	ValueProvider<Apr04_ApprStepModel, Long> apprEmpId();
	
	ValueProvider<Apr04_ApprStepModel, String> stepApprName();
	
	ValueProvider<Apr04_ApprStepModel, String> actionCell();
	
	@Path("empInfoModel.companyId")
	ValueProvider<Apr04_ApprStepModel, Long> companyId();
	
	@Path("empInfoModel.korName")
	ValueProvider<Apr04_ApprStepModel, String> korName();
	
	@Path("empInfoModel.positionName")
	ValueProvider<Apr04_ApprStepModel, String> positionName();
	
	@Path("empInfoModel.titleName")
	ValueProvider<Apr04_ApprStepModel, String> titleName();
	
	@Path("empInfoModel.orgName")
	ValueProvider<Apr04_ApprStepModel, String> orgName();
	
	@Path("empInfoModel.empNo")
	ValueProvider<Apr04_ApprStepModel, String> empNo();
	
}
