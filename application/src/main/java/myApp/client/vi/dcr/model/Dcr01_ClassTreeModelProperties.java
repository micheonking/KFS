package myApp.client.vi.dcr.model;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dcr01_ClassTreeModelProperties extends PropertyAccess<Dcr01_ClassTreeModel> {
	
	ModelKeyProvider<Dcr01_ClassTreeModel> 		keyId();	
	ValueProvider<Dcr01_ClassTreeModel, Long> classTreeId();
	ValueProvider<Dcr01_ClassTreeModel, Long> companyId();
	ValueProvider<Dcr01_ClassTreeModel, String> classTreeCode();
	ValueProvider<Dcr01_ClassTreeModel, String> classTreeName();
	ValueProvider<Dcr01_ClassTreeModel, Long> parentTreeId();
	ValueProvider<Dcr01_ClassTreeModel, String> typeCode();
	ValueProvider<Dcr01_ClassTreeModel, String> approvalTypeCode();
	ValueProvider<Dcr01_ClassTreeModel, String> approvalTypeName();
	ValueProvider<Dcr01_ClassTreeModel, String> itemTypeCode();
	ValueProvider<Dcr01_ClassTreeModel, String> itemTypeName();
	ValueProvider<Dcr01_ClassTreeModel, Long> sealCode();
	ValueProvider<Dcr01_ClassTreeModel, String> sealName();
	ValueProvider<Dcr01_ClassTreeModel, String> useYn();
	ValueProvider<Dcr01_ClassTreeModel, String> seq();
	ValueProvider<Dcr01_ClassTreeModel, String> note();
	
	ValueProvider<Dcr01_ClassTreeModel, Long> level();
	
	ValueProvider<Dcr01_ClassTreeModel, String> researchYn();
	ValueProvider<Dcr01_ClassTreeModel, Boolean> researchYnCheck();
	ValueProvider<Dcr01_ClassTreeModel, String> advertYn();
	ValueProvider<Dcr01_ClassTreeModel, Boolean> advertYnCheck();
	
	ValueProvider<Dcr01_ClassTreeModel, String> actionCell();
	
	@Path("orgAuthModel.retrieveYnCheck")
	ValueProvider<Dcr01_ClassTreeModel, Boolean> retrieveYnCheck();

	@Path("orgAuthModel.updateYnCheck")
	ValueProvider<Dcr01_ClassTreeModel, Boolean> updateYnCheck();

} 