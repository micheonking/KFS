package myApp.client.vi.apr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Apr03_RelateFundModelProperties extends PropertyAccess<Apr01_ApprModel> {
	
	ModelKeyProvider<Apr03_RelateFundModel> keyId();
	
	ValueProvider<Apr03_RelateFundModel, Long> relateFundId(); 
	ValueProvider<Apr03_RelateFundModel, Long> apprId(); 
	ValueProvider<Apr03_RelateFundModel, Long> fundId();
	ValueProvider<Apr03_RelateFundModel, String> detailCell();
	ValueProvider<Apr03_RelateFundModel, String> sendMailCell();
	ValueProvider<Apr03_RelateFundModel, String> deleteCell();
	
	// 펀드정보
	@Path("fundModel.fundCode")
	ValueProvider<Apr03_RelateFundModel, String> fundCode();

	@Path("fundModel.fundName")
    ValueProvider<Apr03_RelateFundModel, String> fundName();

	@Path("fundModel.fundTypeName")
	ValueProvider<Apr03_RelateFundModel, String> fundTypeName();

	@Path("apprModel.title")
	ValueProvider<Apr03_RelateFundModel, String> title();

	@Path("apprModel.regDate")
	ValueProvider<Apr03_RelateFundModel, Date> regDate();
	
	@Path("apprModel.effectDate")
	ValueProvider<Apr03_RelateFundModel, Date> effectDate();
	
	@Path("empInfoModel.korName")
	ValueProvider<Apr03_RelateFundModel, String> empName();
	
	@Path("empInfoModel.orgName")
	ValueProvider<Apr03_RelateFundModel, String> orgName();
	
}
