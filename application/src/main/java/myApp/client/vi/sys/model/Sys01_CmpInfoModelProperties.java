package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys01_CmpInfoModelProperties extends PropertyAccess<Sys01_CmpInfoModel> {
	
	ModelKeyProvider<Sys01_CmpInfoModel> keyId();

	ValueProvider<Sys01_CmpInfoModel, Long  > cmpInfoId() ;
	ValueProvider<Sys01_CmpInfoModel, String> cmpCode() ;
	ValueProvider<Sys01_CmpInfoModel, String> cmpName() ;
	ValueProvider<Sys01_CmpInfoModel, String> bizNo() ;
	ValueProvider<Sys01_CmpInfoModel, String> telNo() ;
	ValueProvider<Sys01_CmpInfoModel, String> addr() ;
	ValueProvider<Sys01_CmpInfoModel, String> tmpPwd() ;
	ValueProvider<Sys01_CmpInfoModel, String> rmk() ;
	ValueProvider<Sys01_CmpInfoModel, String> insUsrNo() ;
	ValueProvider<Sys01_CmpInfoModel, Date  > insDate() ;
	ValueProvider<Sys01_CmpInfoModel, String> updUsrNo() ;
	ValueProvider<Sys01_CmpInfoModel, Date  > updDate() ;
	
	ValueProvider<Sys01_CmpInfoModel, Boolean>	useYnChecked() ;
}
