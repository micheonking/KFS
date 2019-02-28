package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys50_TermsDictModelProperties extends PropertyAccess<Sys50_TermsDictModel> {
	
	ModelKeyProvider<Sys50_TermsDictModel> keyId();

	ValueProvider<Sys50_TermsDictModel, Long  > termsDictId();
	ValueProvider<Sys50_TermsDictModel, String> shtName();
	ValueProvider<Sys50_TermsDictModel, String> fullName();
	ValueProvider<Sys50_TermsDictModel, String> korName();
	ValueProvider<Sys50_TermsDictModel, String> rmk();
	ValueProvider<Sys50_TermsDictModel, String> insUsrNo();
	ValueProvider<Sys50_TermsDictModel, Date  > insDate();
	ValueProvider<Sys50_TermsDictModel, String> updUsrNo();
	ValueProvider<Sys50_TermsDictModel, Date  > updDate();
}
