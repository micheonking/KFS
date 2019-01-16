package myApp.client.vi.hom.company.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Hom01_OutlineModelProperties extends PropertyAccess<Hom01_OutlineModel> {

	ModelKeyProvider<Hom01_OutlineModel> keyId();

	ValueProvider<Hom01_OutlineModel,	Long>	outlineId();
	ValueProvider<Hom01_OutlineModel,	Long>	companyId();
	ValueProvider<Hom01_OutlineModel,	Date>	registDt();
	ValueProvider<Hom01_OutlineModel,	String>	paidInCapital();
	ValueProvider<Hom01_OutlineModel,	String>	ownersEquity();
	ValueProvider<Hom01_OutlineModel,	String>	contractAssetSize();
	ValueProvider<Hom01_OutlineModel,	String>	professionalPersonnel();
	ValueProvider<Hom01_OutlineModel,	String>	mainBusiness();
	ValueProvider<Hom01_OutlineModel,	String>	ceoName();
	ValueProvider<Hom01_OutlineModel,	String>	constituteStockholder();

}
