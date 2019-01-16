package myApp.client.vi.hom.company.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Hom03_OperatingModelProperties extends PropertyAccess<Hom03_OperatingModel> {
	
	ModelKeyProvider<Hom03_OperatingModel> keyId();

	ValueProvider<Hom03_OperatingModel,	Long>	operatingId();
	ValueProvider<Hom03_OperatingModel,	Long>	companyId();
	ValueProvider<Hom03_OperatingModel,	Long>	orgCodeId();
	ValueProvider<Hom03_OperatingModel,	String>	nameTitle();
	ValueProvider<Hom03_OperatingModel,	String>	chargeStockFirm();
	ValueProvider<Hom03_OperatingModel,	String>	majorCareer();
	ValueProvider<Hom03_OperatingModel,	String>	academicCertificate();
	ValueProvider<Hom03_OperatingModel,	String>	contactInfomation();
	ValueProvider<Hom03_OperatingModel,	String>	numericalOrder();
	ValueProvider<Hom03_OperatingModel,	String>	orgName();
	ValueProvider<Hom03_OperatingModel,	Integer>	chargeStockFirmCnt();
	ValueProvider<Hom03_OperatingModel,	Integer>	chargeStockFirmMax();

}
