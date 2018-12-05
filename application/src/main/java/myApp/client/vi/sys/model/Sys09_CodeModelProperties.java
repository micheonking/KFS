package myApp.client.vi.sys.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys09_CodeModelProperties extends PropertyAccess<Sys09_CodeModel> {
	
	ModelKeyProvider<Sys09_CodeModel> keyId();
	
	ValueProvider<Sys09_CodeModel, Long> 	codeId();
	ValueProvider<Sys09_CodeModel, Long> 	companyId();
	ValueProvider<Sys09_CodeModel, Long> 	codeKindId();
	ValueProvider<Sys09_CodeModel, String> 	code();
	ValueProvider<Sys09_CodeModel, String> 	name();

	ValueProvider<Sys09_CodeModel, Date> 	applyDate();
	ValueProvider<Sys09_CodeModel, Date> 	closeDate();
	ValueProvider<Sys09_CodeModel, Date> 	lastDate();
	
	ValueProvider<Sys09_CodeModel, String> 	seq();
	ValueProvider<Sys09_CodeModel, String> 	note();
	
	@Path("mgrRuleModel.titleCd")
	ValueProvider<Sys09_CodeModel, String>  titleCd();

	@Path("mgrRuleModel.useYn")
	ValueProvider<Sys09_CodeModel, Boolean> useYn();

	@Path("mgrRuleModel.mgrRuleId")
	ValueProvider<Sys09_CodeModel, Long> mgrRuleId();

	
	
}
