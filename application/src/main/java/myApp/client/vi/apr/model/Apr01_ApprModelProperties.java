package myApp.client.vi.apr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Apr01_ApprModelProperties extends PropertyAccess<Apr01_ApprModel> {
	
	ModelKeyProvider<Apr01_ApprModel> keyId();
	
	ValueProvider<Apr01_ApprModel, Long> apprId(); 
	ValueProvider<Apr01_ApprModel, String> title(); 
	ValueProvider<Apr01_ApprModel, Date> regDate(); 
	ValueProvider<Apr01_ApprModel, String> statusCode();
	ValueProvider<Apr01_ApprModel, String> statusName();
	ValueProvider<Apr01_ApprModel, String> content(); 
	ValueProvider<Apr01_ApprModel, String> note(); 
	ValueProvider<Apr01_ApprModel, Long> regEmpId(); 
	ValueProvider<Apr01_ApprModel, String> regEmpNm(); 
	ValueProvider<Apr01_ApprModel, Date> effectDate();
	ValueProvider<Apr01_ApprModel, Long> classTreeId();
	ValueProvider<Apr01_ApprModel, Long> stampId();
	ValueProvider<Apr01_ApprModel, String> stampName();
	ValueProvider<Apr01_ApprModel, String> receiveName();
	ValueProvider<Apr01_ApprModel, String> referenceName();
	
	ValueProvider<Apr01_ApprModel, String> actionCell();
	
	@Path("fundModel.fundName")
	ValueProvider<Apr01_ApprModel, String> fundName();
	
	@Path("empInfoModel.korName")
	ValueProvider<Apr01_ApprModel, String> empName();
	
	@Path("empInfoModel.orgName")
	ValueProvider<Apr01_ApprModel, String> orgName();
	
	@Path("classTreeModel.classTreeName")
	ValueProvider<Apr01_ApprModel, String> classTreeName();

	
	ValueProvider<Apr01_ApprModel, String> NextApprName(); 
	
	ValueProvider<Apr01_ApprModel, String> recentApprovalName();
	ValueProvider<Apr01_ApprModel, Date> recentApprovalDate();
	ValueProvider<Apr01_ApprModel, Date> myApprovalDate();
	ValueProvider<Apr01_ApprModel, String> docTypeName();
	
	ValueProvider<Apr01_ApprModel, String> classTreeNameAppPath();
	ValueProvider<Apr01_ApprModel, String> inOutTypeCode();
	
	ValueProvider<Apr01_ApprModel, String> mailYn();
	
	ValueProvider<Apr01_ApprModel, String> fileName();
	
	ValueProvider<Apr01_ApprModel, String> regText();
	ValueProvider<Apr01_ApprModel, String> apprButton();
	ValueProvider<Apr01_ApprModel, String> rejectButton();
	
	
	
}
