package myApp.client.vi.dcr.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dcr03_OrgAuthModelProperties extends PropertyAccess<Dcr03_OrgAuthModel> {

	ModelKeyProvider<Dcr03_OrgAuthModel>      keyId();
	ValueProvider<Dcr03_OrgAuthModel, Long>   orgAuthId();
	ValueProvider<Dcr03_OrgAuthModel, Long>   orgCodeId();
	ValueProvider<Dcr03_OrgAuthModel, String> retrieveYn();
	ValueProvider<Dcr03_OrgAuthModel, String> updateYn();
	ValueProvider<Dcr03_OrgAuthModel, Long> docTypeId();
	ValueProvider<Dcr03_OrgAuthModel, String> note();

	// dummy columns 
	ValueProvider<Dcr03_OrgAuthModel, String> docTypeClass(); // 분류구분(C:문서분류, T:문서유형) 
	ValueProvider<Dcr03_OrgAuthModel, String> docTypeName(); // 분류명 
	ValueProvider<Dcr03_OrgAuthModel, String> docTypeSeq(); // 순서 
	ValueProvider<Dcr03_OrgAuthModel, String> docTypeDesc(); // 상세설명
	
}