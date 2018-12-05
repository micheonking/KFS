package myApp.client.vi.opr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Opr02_FundModelProperties extends PropertyAccess<Opr02_FundModel> {

	ModelKeyProvider<Opr02_FundModel> keyId();

	ValueProvider<Opr02_FundModel, Long	 > fundId();
	ValueProvider<Opr02_FundModel, Long	 > createId();
	ValueProvider<Opr02_FundModel, Long	 > regFundId();

	// 펀드정보
	@Path("fundModel.fundCode")
    ValueProvider<Opr02_FundModel, String> fundCode();

	@Path("fundModel.fundName")
	ValueProvider<Opr02_FundModel, String> fundName();

	@Path("fundModel.fundTypeCode")
	ValueProvider<Opr02_FundModel, String> fundTypeCode();

	@Path("fundModel.startDate")
	ValueProvider<Opr02_FundModel, Date  > startDate();

	@Path("fundModel.endDate")
	ValueProvider<Opr02_FundModel, Date  > endDate();

	@Path("fundModel.closeYn")
	ValueProvider<Opr02_FundModel, String> closeYn();

	@Path("fundModel.planYn")
	ValueProvider<Opr02_FundModel, String> planYn();

	@Path("fundModel.realFundId")
	ValueProvider<Opr02_FundModel, Long  > realFundId();

	@Path("fundModel.companyId")
	ValueProvider<Opr02_FundModel, Long  > companyId();

	@Path("fundModel.fundInfoId")
	ValueProvider<Opr02_FundModel, Long  > fundInfoId();

	@Path("fundModel.workDate")
	ValueProvider<Opr02_FundModel, Date  > workDate();

	@Path("fundModel.sintakGb")
	ValueProvider<Opr02_FundModel, String> sintakGb();

	@Path("fundModel.publicYn")
	ValueProvider<Opr02_FundModel, String> publicYn();

	@Path("fundModel.seoljAek")
	ValueProvider<Opr02_FundModel, Long  > seoljAek();

	@Path("fundModel.sunjasanAek")
	ValueProvider<Opr02_FundModel, Long  > sunjasanAek();

	@Path("fundModel.orgCodeId")
	ValueProvider<Opr02_FundModel, Long  > orgCodeId();

	@Path("fundModel.emp1Id")
	ValueProvider<Opr02_FundModel, Long  > emp1Id();

	@Path("fundModel.emp2Id")
	ValueProvider<Opr02_FundModel, Long  > emp2Id();

	@Path("fundModel.managementRate")
	ValueProvider<Opr02_FundModel, Long  > managementRate();

	@Path("fundModel.salesRate")
	ValueProvider<Opr02_FundModel, Long  > salesRate();

	@Path("fundModel.consignmentId")
	ValueProvider<Opr02_FundModel, Long  > consignmentId();

	@Path("fundModel.consignmentRate")
	ValueProvider<Opr02_FundModel, Long  > consignmentRate();

	@Path("fundModel.officeWorkId")
	ValueProvider<Opr02_FundModel, Long  > officeWorkId();

	@Path("fundModel.officeWorkRate")
	ValueProvider<Opr02_FundModel, Long  > officeWorkRate();

	@Path("fundModel.planDate")
	ValueProvider<Opr02_FundModel, Date  > planDate();

	@Path("fundModel.planSalesDscr")
	ValueProvider<Opr02_FundModel, String> planSalesDscr();

	@Path("fundModel.planInvestorDscr")
	ValueProvider<Opr02_FundModel, String> planInvestorDscr();

	@Path("fundModel.planNote")
	ValueProvider<Opr02_FundModel, String> planNote();

	@Path("fundModel.fundTypeName")
	ValueProvider<Opr02_FundModel, String> fundTypeName();

	@Path("fundModel.sintakGbName")
	ValueProvider<Opr02_FundModel, String> sintakGbName();

	@Path("fundModel.publicName")
	ValueProvider<Opr02_FundModel, String> publicName();

	@Path("fundModel.closeName")
	ValueProvider<Opr02_FundModel, String> closeName();

	@Path("fundModel.planName")
	ValueProvider<Opr02_FundModel, String> planName();

	@Path("fundModel.orgCodeName")
	ValueProvider<Opr02_FundModel, String> orgCodeName();

	@Path("fundModel.empName1")
	ValueProvider<Opr02_FundModel, String> empName1();

	@Path("fundModel.empName2")
	ValueProvider<Opr02_FundModel, String> empName2();

	@Path("fundModel.consignmentName")
	ValueProvider<Opr02_FundModel, String> consignmentName();
	
}
