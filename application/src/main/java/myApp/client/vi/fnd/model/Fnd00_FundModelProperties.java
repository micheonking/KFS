package myApp.client.vi.fnd.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd00_FundModelProperties extends PropertyAccess<Fnd00_FundModel> {

	ModelKeyProvider<Fnd00_FundModel> keyId();

	ValueProvider<Fnd00_FundModel, Long  > fundId();
	ValueProvider<Fnd00_FundModel, String> fundCode();
	ValueProvider<Fnd00_FundModel, String> fundName();
	ValueProvider<Fnd00_FundModel, String> fundTypeCode();
	ValueProvider<Fnd00_FundModel, Date  > startDate();
	ValueProvider<Fnd00_FundModel, Date  > endDate();
	ValueProvider<Fnd00_FundModel, String> closeYn();
	ValueProvider<Fnd00_FundModel, String> planYn();
	ValueProvider<Fnd00_FundModel, Long  > realFundId();
	ValueProvider<Fnd00_FundModel, Long  > companyId();
	ValueProvider<Fnd00_FundModel, Long  > fundInfoId();
	ValueProvider<Fnd00_FundModel, Date  > workDate();
	ValueProvider<Fnd00_FundModel, String> sintakGb();
	ValueProvider<Fnd00_FundModel, String> publicYn();
	ValueProvider<Fnd00_FundModel, Long  > seoljAek();
	ValueProvider<Fnd00_FundModel, Long  > sunjasanAek();
	ValueProvider<Fnd00_FundModel, Long  > orgCodeId();
	ValueProvider<Fnd00_FundModel, Long  > emp1Id();
	ValueProvider<Fnd00_FundModel, Long  > emp2Id();
	ValueProvider<Fnd00_FundModel, Long  > managementRate();
	ValueProvider<Fnd00_FundModel, Long  > salesRate();
	ValueProvider<Fnd00_FundModel, String> consignmentCd();
	ValueProvider<Fnd00_FundModel, Long  > consignmentRate();
	ValueProvider<Fnd00_FundModel, String> officeWorkCd();
	ValueProvider<Fnd00_FundModel, Long  > officeWorkRate();
	ValueProvider<Fnd00_FundModel, Date  > planDate();
	ValueProvider<Fnd00_FundModel, String> planSalesDscr();
	ValueProvider<Fnd00_FundModel, String> planInvestorDscr();
	ValueProvider<Fnd00_FundModel, String> planNote();

	ValueProvider<Fnd00_FundModel, String> fundTypeName();
	ValueProvider<Fnd00_FundModel, String> sintakGbName();
	ValueProvider<Fnd00_FundModel, String> publicName();
	ValueProvider<Fnd00_FundModel, String> closeName();
	ValueProvider<Fnd00_FundModel, String> planName();
	ValueProvider<Fnd00_FundModel, String> orgCodeName();
	ValueProvider<Fnd00_FundModel, String> empName1();
	ValueProvider<Fnd00_FundModel, String> empName2();
	ValueProvider<Fnd00_FundModel, String> consignmentName();

}
