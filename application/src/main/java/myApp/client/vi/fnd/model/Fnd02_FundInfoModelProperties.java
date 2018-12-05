package myApp.client.vi.fnd.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd02_FundInfoModelProperties extends PropertyAccess<Fnd02_FundInfoModel> {

	ModelKeyProvider<Fnd02_FundInfoModel> keyId();

	ValueProvider<Fnd02_FundInfoModel,	Long>	fundInfoId();
	ValueProvider<Fnd02_FundInfoModel,	Long>	fundCodeId();
	ValueProvider<Fnd02_FundInfoModel,	Date>	workDate();
	ValueProvider<Fnd02_FundInfoModel,	String>	fundName();
	ValueProvider<Fnd02_FundInfoModel,	String>	fundTypeCode();
	ValueProvider<Fnd02_FundInfoModel,	String>	sintakGb();
	ValueProvider<Fnd02_FundInfoModel,	String>	publicYn();
	ValueProvider<Fnd02_FundInfoModel,	Long>	seoljAek();
	ValueProvider<Fnd02_FundInfoModel,	Long>	sunjasanAek();
	ValueProvider<Fnd02_FundInfoModel,	Long>	orgCodeId();
	ValueProvider<Fnd02_FundInfoModel,	Long>	emp1Id();
	ValueProvider<Fnd02_FundInfoModel,	Long>	emp2Id();
	ValueProvider<Fnd02_FundInfoModel,	Double>	managementRate();
	ValueProvider<Fnd02_FundInfoModel,	Double>	salesRate();
	ValueProvider<Fnd02_FundInfoModel,	String>	consignmentCd();
	ValueProvider<Fnd02_FundInfoModel,	Double>	consignmentRate();
	ValueProvider<Fnd02_FundInfoModel,	String>	officeWorkCd();
	ValueProvider<Fnd02_FundInfoModel,	Double>	officeWorkRate();
	ValueProvider<Fnd02_FundInfoModel,	Date>	planDate();
	ValueProvider<Fnd02_FundInfoModel,	String>	planSalesDscr();
	ValueProvider<Fnd02_FundInfoModel,	String>	planInvestorDscr();
	ValueProvider<Fnd02_FundInfoModel,	String>	planNote();
	ValueProvider<Fnd02_FundInfoModel,	String>	orgCode();
	ValueProvider<Fnd02_FundInfoModel,	String>	orgCodeName();
	ValueProvider<Fnd02_FundInfoModel,	String>	emp1Name();
	ValueProvider<Fnd02_FundInfoModel,	String>	emp2Name();
	ValueProvider<Fnd02_FundInfoModel,	String>	fundTypeName();
	ValueProvider<Fnd02_FundInfoModel,	String>	sintakGbName();
	ValueProvider<Fnd02_FundInfoModel,	String>	publicName();
	ValueProvider<Fnd02_FundInfoModel,	String>	consignmentName();
	ValueProvider<Fnd02_FundInfoModel,	String>	officeWorkName();
	ValueProvider<Fnd02_FundInfoModel,	Date>	uploadWorkDate();

}
