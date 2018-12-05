package myApp.client.vi.fnd.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Fnd01_FundCodeModelProperties extends PropertyAccess<Fnd01_FundCodeModel> {

	ModelKeyProvider<Fnd01_FundCodeModel> keyId();

	ValueProvider<Fnd01_FundCodeModel,	Long>		fundCodeId();
	ValueProvider<Fnd01_FundCodeModel,	String>		fundCode();
	ValueProvider<Fnd01_FundCodeModel,	Date>		startDate();
	ValueProvider<Fnd01_FundCodeModel,	Date>		endDate();
	ValueProvider<Fnd01_FundCodeModel,	String>		closeYn();
	ValueProvider<Fnd01_FundCodeModel,	String>		planYn();
	ValueProvider<Fnd01_FundCodeModel,	Long>		realFundId();
	ValueProvider<Fnd01_FundCodeModel,	Long>		companyId();

	ValueProvider<Fnd01_FundCodeModel,	String>		closeName();
	ValueProvider<Fnd01_FundCodeModel,	String>		realFundCode();
	ValueProvider<Fnd01_FundCodeModel,	String>		realFundName();

	@Path("fundInfoModel.fundName")
	ValueProvider<Fnd01_FundCodeModel,	String>	fundName();

	@Path("fundInfoModel.fundTypeName")
	ValueProvider<Fnd01_FundCodeModel, String> fundTypeName();

	@Path("fundInfoModel.sintakGbName")
	ValueProvider<Fnd01_FundCodeModel,	String>	sintakGbName();

	@Path("fundInfoModel.publicName")
	ValueProvider<Fnd01_FundCodeModel,	String>	publicName();

	@Path("fundInfoModel.orgCodeName")
	ValueProvider<Fnd01_FundCodeModel, String> orgCodeName();

	@Path("fundInfoModel.emp1Name")
	ValueProvider<Fnd01_FundCodeModel, String> emp1Name();

	@Path("fundInfoModel.emp2Name")
	ValueProvider<Fnd01_FundCodeModel, String> emp2Name();

//
//	
//	@Path("fundInfoModel.sintakGb")
//	ValueProvider<Fnd01_FundCodeModel,	String>	sintakGb();
//
//	@Path("fundInfoModel.publicYn")
//	ValueProvider<Fnd01_FundCodeModel,	String>	publicYn();
//
//	@Path("fundInfoModel.seoljAek")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	seoljAek();
//
//	@Path("fundInfoModel.sunjasanAek")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	sunjasanAek();
//
//	@Path("fundInfoModel.orgCodeId")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	orgCodeId();
//
//	@Path("fundInfoModel.emp1Id")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	emp1Id();
//
//	@Path("fundInfoModel.emp2Id")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	emp2Id();
//
//	@Path("fundInfoModel.managementRate")
//	ValueProvider<Fnd01_FundCodeModel,	Double>	managementRate();
//
//	@Path("fundInfoModel.salesRate")
//	ValueProvider<Fnd01_FundCodeModel,	Double>	salesRate();
//
//	@Path("fundInfoModel.consignmentId")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	consignmentId();
//
//	@Path("fundInfoModel.consignmentRate")
//	ValueProvider<Fnd01_FundCodeModel,	Double>	consignmentRate();
//
//	@Path("fundInfoModel.officeWorkId")
//	ValueProvider<Fnd01_FundCodeModel,	Long>	officeWorkId();
//
//	@Path("fundInfoModel.officeWorkRate")
//	ValueProvider<Fnd01_FundCodeModel,	Double>	officeWorkRate();
//
//	@Path("fundInfoModel.planDate")
//	ValueProvider<Fnd01_FundCodeModel,	Date>	planDate();
//
//	@Path("fundInfoModel.planSalesDscr")
//	ValueProvider<Fnd01_FundCodeModel,	String>	planSalesDscr();
//
//	@Path("fundInfoModel.planInvestorDscr")
//	ValueProvider<Fnd01_FundCodeModel,	String>	planInvestorDscr();
//
//	@Path("fundInfoModel.planNote")
//	ValueProvider<Fnd01_FundCodeModel,	String>	planNote();


}
