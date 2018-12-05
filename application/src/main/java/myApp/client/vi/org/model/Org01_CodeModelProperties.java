package myApp.client.vi.org.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Org01_CodeModelProperties extends PropertyAccess<Org01_CodeModel> {
	
	ModelKeyProvider<Org01_CodeModel> 		keyId();	
	ValueProvider<Org01_CodeModel, Long> 	codeId();
	ValueProvider<Org01_CodeModel, Long> 	companyId();
	ValueProvider<Org01_CodeModel, String> 	orgCode();
	ValueProvider<Org01_CodeModel, Date> 	openDate();
	ValueProvider<Org01_CodeModel, Date> 	closeDate();
	ValueProvider<Org01_CodeModel, String> 	openReason();
	ValueProvider<Org01_CodeModel, String> 	closeReason();
	ValueProvider<Org01_CodeModel, String> 	actionCell();	// 그리드에 버튼을 넣기 위한 선언이다.
	ValueProvider<Org01_CodeModel, String> 	moveCell();	// 그리드에 버튼을 넣기 위한 선언이다.
	
	@Path("orgInfoModel.korName")
	ValueProvider<Org01_CodeModel, String>  korName();
	@Path("orgInfoModel.shortName")
	ValueProvider<Org01_CodeModel, String>  shortName();
	@Path("orgInfoModel.engName")
	ValueProvider<Org01_CodeModel, String>  engName();
	@Path("orgInfoModel.levelCode")
	ValueProvider<Org01_CodeModel, String>  levelCode();
	@Path("orgInfoModel.levelName")
	ValueProvider<Org01_CodeModel, String>  levelName();
	@Path("orgInfoModel.sortOrder")
	ValueProvider<Org01_CodeModel, String>  sortOrder();
	@Path("orgInfoModel.modDate")
	ValueProvider<Org01_CodeModel, Date> 	modDate();
	@Path("orgInfoModel.modReason")
	ValueProvider<Org01_CodeModel, String>  modReason();
	@Path("orgInfoModel.modDetail")
	ValueProvider<Org01_CodeModel, String>  modDetail();
	@Path("orgInfoModel.note")
	ValueProvider<Org01_CodeModel, String>  note();
	
	// 펀드조회권한 여부  
	@Path("orgAuthModel.authYnCheck")
	ValueProvider<Org01_CodeModel, Boolean> authYnCheck();

	@Path("orgAuthModel.authYn")
	ValueProvider<Org01_CodeModel, String> authYn();

	
} 