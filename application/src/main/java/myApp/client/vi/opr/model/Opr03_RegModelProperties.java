package myApp.client.vi.opr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Opr03_RegModelProperties extends PropertyAccess<Opr03_RegModel> {

	ModelKeyProvider<Opr03_RegModel> keyId();

	ValueProvider<Opr03_RegModel, Long	 > regId();
	ValueProvider<Opr03_RegModel, Long	 > createId();
	ValueProvider<Opr03_RegModel, Long	 > regEmpId();
	ValueProvider<Opr03_RegModel, String > seq();
	ValueProvider<Opr03_RegModel, String > docNm();
	ValueProvider<Opr03_RegModel, Date	 > regDate();
	ValueProvider<Opr03_RegModel, String > closeYn();
    ValueProvider<Opr03_RegModel, Boolean> closeYnFlag();
    ValueProvider<Opr03_RegModel, String > closeYnNm();
	ValueProvider<Opr03_RegModel, String > note();

	ValueProvider<Opr03_RegModel, String > uploadYn();
	ValueProvider<Opr03_RegModel, Long 	 > fileId();
	
	ValueProvider<Opr03_RegModel, String> actionCellUp();   
	ValueProvider<Opr03_RegModel, String> actionCellDown();   
	
    @Path("empInfoModel.transId")
	ValueProvider<Opr03_RegModel,  Long>	transId();

    @Path("empInfoModel.personId")
    ValueProvider<Opr03_RegModel,  Long> 	personId();

    @Path("empInfoModel.ctzNo")
    ValueProvider<Opr03_RegModel,  String> 	ctzNo();
	
    @Path("empInfoModel.korName")
    ValueProvider<Opr03_RegModel,  String> 	korName();
	
    @Path("empInfoModel.engName")
    ValueProvider<Opr03_RegModel,  String> 	engName();
	
    @Path("empInfoModel.birthday")
    ValueProvider<Opr03_RegModel,  Date> 	birthday();
	
    @Path("empInfoModel.mobileTelNo")
    ValueProvider<Opr03_RegModel,  String> 	mobileTelNo();
	
    @Path("empInfoModel.homeTelNo")
    ValueProvider<Opr03_RegModel,  String> 	homeTelNo();
	
    @Path("empInfoModel.emergencyTelNo")
    ValueProvider<Opr03_RegModel,  String> 	emergencyTelNo();
	
    @Path("empInfoModel.emailAddress")
    ValueProvider<Opr03_RegModel,  String> 	emailAddress();
	
    @Path("empInfoModel.nationCode")
    ValueProvider<Opr03_RegModel,  String> 	nationCode();
	
    @Path("empInfoModel.nationName")
    ValueProvider<Opr03_RegModel,  String> 	nationName();
	
    @Path("empInfoModel.genderCode")
    ValueProvider<Opr03_RegModel,  String> 	genderCode();
	
    @Path("empInfoModel.genderName")
    ValueProvider<Opr03_RegModel,  String> 	genderName();
	
    @Path("empInfoModel.empId")
    ValueProvider<Opr03_RegModel,  Long> 	empId();
	
    @Path("empInfoModel.empNo")
    ValueProvider<Opr03_RegModel,  String> 	empNo();
	
    @Path("empInfoModel.empKindCode")
    ValueProvider<Opr03_RegModel,  String> 	empKindCode();
	
    @Path("empInfoModel.empKindName")
    ValueProvider<Opr03_RegModel,  String> 	empKindName();
	
    @Path("empInfoModel.hireDate")
    ValueProvider<Opr03_RegModel,  Date> 	hireDate();
	
    @Path("empInfoModel.hireCode")
    ValueProvider<Opr03_RegModel,  String> 	hireCode();
	
    @Path("empInfoModel.hireName")
    ValueProvider<Opr03_RegModel,  String> 	hireName();
	
    @Path("empInfoModel.retireDate")
    ValueProvider<Opr03_RegModel,  Date> 	retireDate();
	
    @Path("empInfoModel.retireCode")
    ValueProvider<Opr03_RegModel,  String> 	retireCode();
	
    @Path("empInfoModel.retireName")
    ValueProvider<Opr03_RegModel,  String> 	retireName();
	
    @Path("empInfoModel.transDate")
    ValueProvider<Opr03_RegModel,  Date> 	transDate();
	
    @Path("empInfoModel.closeDate")
    ValueProvider<Opr03_RegModel,  Date> 	closeDate();
	
    @Path("empInfoModel.positionCode")
    ValueProvider<Opr03_RegModel,  String> 	positionCode();
	
    @Path("empInfoModel.positionName")
    ValueProvider<Opr03_RegModel,  String> 	positionName();
	
    @Path("empInfoModel.titleCode")
    ValueProvider<Opr03_RegModel,  String> 	titleCode();
	
    @Path("empInfoModel.titleName")
    ValueProvider<Opr03_RegModel,  String> 	titleName();
	
    @Path("empInfoModel.orgCode")
    ValueProvider<Opr03_RegModel,  String> 	orgCode();
	
    @Path("empInfoModel.orgName")
    ValueProvider<Opr03_RegModel,  String> 	orgName();    
    
}
