package myApp.client.vi.pln.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Pln03_ResrchModelProperties extends PropertyAccess<Pln03_ResrchModel> {

	ModelKeyProvider<Pln03_ResrchModel> keyId();

	ValueProvider<Pln03_ResrchModel, Long  >  resrchId();
	ValueProvider<Pln03_ResrchModel, Long  >  classTreeId();
	ValueProvider<Pln03_ResrchModel, Long  >  issuingEntityId();
	ValueProvider<Pln03_ResrchModel, String>  visitReason();
	ValueProvider<Pln03_ResrchModel, Long  >  regEmpId();
	ValueProvider<Pln03_ResrchModel, Date  >  regDate();
	ValueProvider<Pln03_ResrchModel, Date  >  closeDate();
	ValueProvider<Pln03_ResrchModel, String>  closeYn();
	ValueProvider<Pln03_ResrchModel, Date  >  resrchStartDate();
	ValueProvider<Pln03_ResrchModel, Date  >  resrchCloseDate();
	ValueProvider<Pln03_ResrchModel, Long  >  cfrmEmpId();
	ValueProvider<Pln03_ResrchModel, Date  >  cfrmDate();
	ValueProvider<Pln03_ResrchModel, String>  note();
	ValueProvider<Pln03_ResrchModel, String>  targetTypeCode();

	ValueProvider<Pln03_ResrchModel, String>  classTreeNm();
	ValueProvider<Pln03_ResrchModel, String>  parentTreeNm();
	ValueProvider<Pln03_ResrchModel, String>  issuingEntityNm();
	ValueProvider<Pln03_ResrchModel, String>  docYn();
	ValueProvider<Pln03_ResrchModel, String>  aprYn();
	ValueProvider<Pln03_ResrchModel, String>  regEmpNm();
	ValueProvider<Pln03_ResrchModel, String>  cfrmEmpNm();
	ValueProvider<Pln03_ResrchModel, String>  targetTypeName();
	
    ValueProvider<Pln03_ResrchModel, String > actionCell();	// 그리드에 버튼을 넣기 위한 선언이다.
}
