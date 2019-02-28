package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys03_MenuModelProperties extends PropertyAccess<Sys03_MenuModel> {
	
	ModelKeyProvider<Sys03_MenuModel> keyId();

	ValueProvider<Sys03_MenuModel, Long  > menuId() ;
	ValueProvider<Sys03_MenuModel, String> menuName() ;
	ValueProvider<Sys03_MenuModel, Long  > seq() ;
	ValueProvider<Sys03_MenuModel, Long  > prntId() ;
	ValueProvider<Sys03_MenuModel, String> rmk() ;
	ValueProvider<Sys03_MenuModel, String> className() ;
	ValueProvider<Sys03_MenuModel, String> insUsrNo() ;
	ValueProvider<Sys03_MenuModel, Date  > insDate() ;
	ValueProvider<Sys03_MenuModel, String> updUsrNo() ;
	ValueProvider<Sys03_MenuModel, Date  > updDate() ;
	
	ValueProvider<Sys03_MenuModel, String>  seqStr() ;
	ValueProvider<Sys03_MenuModel, String> 	editCell();
	ValueProvider<Sys03_MenuModel, String> 	moveCell();
}
