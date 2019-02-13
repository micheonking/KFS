package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys08_DptInfoModelProperties extends PropertyAccess<Sys08_DptInfoModel> {
	
	ModelKeyProvider<Sys08_DptInfoModel> keyId();

	ValueProvider<Sys08_DptInfoModel, Long  > dptInfoId() ;
	ValueProvider<Sys08_DptInfoModel, String> cmpCode() ;
	ValueProvider<Sys08_DptInfoModel, String> dptCode() ;
	ValueProvider<Sys08_DptInfoModel, String> dptName() ;
	ValueProvider<Sys08_DptInfoModel, String> useYn() ;
	ValueProvider<Sys08_DptInfoModel, String> rmk() ;
	ValueProvider<Sys08_DptInfoModel, String> insUsrNo() ;
	ValueProvider<Sys08_DptInfoModel, Date  > insDate() ;
	ValueProvider<Sys08_DptInfoModel, String> updUsrNo() ;
	ValueProvider<Sys08_DptInfoModel, Date  > updDate() ;
	
}
