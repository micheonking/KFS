package myApp.client.vi.sys.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys04_CmpMenuModelProperties extends PropertyAccess<Sys04_CmpMenuModel> {
	
	ModelKeyProvider<Sys04_CmpMenuModel> keyId();

	ValueProvider<Sys04_CmpMenuModel, Long  > cmpMenuId() ;
	ValueProvider<Sys04_CmpMenuModel, String> cmpCode() ;
	ValueProvider<Sys04_CmpMenuModel, Long  > menuId() ;
	ValueProvider<Sys04_CmpMenuModel, String> menuName() ;
	ValueProvider<Sys04_CmpMenuModel, Long  > prntId() ;
	ValueProvider<Sys04_CmpMenuModel, Long  > seq() ;
	ValueProvider<Sys04_CmpMenuModel, String> useYn() ;
	ValueProvider<Sys04_CmpMenuModel, String> rmk() ;
	ValueProvider<Sys04_CmpMenuModel, String> insUsrNo() ;
	ValueProvider<Sys04_CmpMenuModel, Date  > insDate() ;
	ValueProvider<Sys04_CmpMenuModel, String> updUsrNo() ;
	ValueProvider<Sys04_CmpMenuModel, Date  > updDate() ;

	ValueProvider<Sys04_CmpMenuModel, String>  seqStr() ;
	ValueProvider<Sys04_CmpMenuModel, Boolean> useYnFlag();
	ValueProvider<Sys04_CmpMenuModel, String> 	editCell();
	ValueProvider<Sys04_CmpMenuModel, String> 	moveCell();

	@Path("menuModel.className")
	ValueProvider<Sys04_CmpMenuModel, String> className();

}
