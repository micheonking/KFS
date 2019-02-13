package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys02_UsrInfoModelProperties extends PropertyAccess<Sys02_UsrInfoModel> {
	
	ModelKeyProvider<Sys02_UsrInfoModel> keyId();

	ValueProvider<Sys02_UsrInfoModel, Long  > usrInfoId() ;
	ValueProvider<Sys02_UsrInfoModel, String> usrNo() ;
	ValueProvider<Sys02_UsrInfoModel, String> usrName() ;
	ValueProvider<Sys02_UsrInfoModel, String> cmpCode() ;
	ValueProvider<Sys02_UsrInfoModel, String> dptCode() ;
	ValueProvider<Sys02_UsrInfoModel, String> email() ;    
	ValueProvider<Sys02_UsrInfoModel, String> telNo() ;    
	ValueProvider<Sys02_UsrInfoModel, String> useYn() ;    
	ValueProvider<Sys02_UsrInfoModel, String> tmpPwd() ;
	ValueProvider<Sys02_UsrInfoModel, String> rmk() ;
	ValueProvider<Sys02_UsrInfoModel, String> insUsrNo() ;
	ValueProvider<Sys02_UsrInfoModel, Date  > insDate() ;
	ValueProvider<Sys02_UsrInfoModel, String> updUsrNo() ;
	ValueProvider<Sys02_UsrInfoModel, Date  > updDate() ;
	
}
