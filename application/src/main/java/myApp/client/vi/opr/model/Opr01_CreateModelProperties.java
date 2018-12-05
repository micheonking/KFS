package myApp.client.vi.opr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import myApp.client.vi.emp.model.Emp00_InfoModel;

public interface Opr01_CreateModelProperties extends PropertyAccess<Opr01_CreateModel> {

	ModelKeyProvider<Opr01_CreateModel> keyId();
	ValueProvider<Opr01_CreateModel, Long	> createId();
    ValueProvider<Opr01_CreateModel, String	> year();
    ValueProvider<Opr01_CreateModel, String	> termCd();
    ValueProvider<Opr01_CreateModel, String	> termNm();
    ValueProvider<Opr01_CreateModel, Long	> createEmpId();
    ValueProvider<Opr01_CreateModel, Long	> companyId();
    ValueProvider<Opr01_CreateModel, Long	> apprId();
    ValueProvider<Opr01_CreateModel, String	> title();
    ValueProvider<Opr01_CreateModel, Date	> startDate();
    ValueProvider<Opr01_CreateModel, Date	> dueDate();
    ValueProvider<Opr01_CreateModel, String	> note();

    ValueProvider<Opr01_CreateModel, Date	> closeDate();
    ValueProvider<Opr01_CreateModel, String	> aprNm();
    ValueProvider<Opr01_CreateModel, Date	> aprDate();
    ValueProvider<Opr01_CreateModel, Long	> regId();

    ValueProvider<Opr01_CreateModel, String > actionCell();	// 그리드에 버튼을 넣기 위한 선언이다.

    @Path("empInfoModel.transId")
	ValueProvider<Opr01_CreateModel,  Long>		transId();

    @Path("empInfoModel.personId")
    ValueProvider<Opr01_CreateModel,  Long> 	personId();

    @Path("empInfoModel.ctzNo")
    ValueProvider<Opr01_CreateModel,  String> 	ctzNo();
	
    @Path("empInfoModel.korName")
    ValueProvider<Opr01_CreateModel,  String> 	korName();
	
    @Path("empInfoModel.engName")
    ValueProvider<Opr01_CreateModel,  String> 	engName();
	
    @Path("empInfoModel.birthday")
    ValueProvider<Opr01_CreateModel,  Date> 	birthday();
	
    @Path("empInfoModel.mobileTelNo")
    ValueProvider<Opr01_CreateModel,  String> 	mobileTelNo();
	
    @Path("empInfoModel.homeTelNo")
    ValueProvider<Opr01_CreateModel,  String> 	homeTelNo();
	
    @Path("empInfoModel.emergencyTelNo")
    ValueProvider<Opr01_CreateModel,  String> 	emergencyTelNo();
	
    @Path("empInfoModel.emailAddress")
    ValueProvider<Opr01_CreateModel,  String> 	emailAddress();
	
    @Path("empInfoModel.nationCode")
    ValueProvider<Opr01_CreateModel,  String> 	nationCode();
	
    @Path("empInfoModel.nationName")
    ValueProvider<Opr01_CreateModel,  String> 	nationName();
	
    @Path("empInfoModel.genderCode")
    ValueProvider<Opr01_CreateModel,  String> 	genderCode();
	
    @Path("empInfoModel.genderName")
    ValueProvider<Opr01_CreateModel,  String> 	genderName();
	
    @Path("empInfoModel.empId")
    ValueProvider<Opr01_CreateModel,  Long> 	empId();
	
    @Path("empInfoModel.empNo")
    ValueProvider<Opr01_CreateModel,  String> 	empNo();
	
    @Path("empInfoModel.empKindCode")
    ValueProvider<Opr01_CreateModel,  String> 	empKindCode();
	
    @Path("empInfoModel.empKindName")
    ValueProvider<Opr01_CreateModel,  String> 	empKindName();
	
    @Path("empInfoModel.hireDate")
    ValueProvider<Opr01_CreateModel,  Date> 	hireDate();
	
    @Path("empInfoModel.hireCode")
    ValueProvider<Opr01_CreateModel,  String> 	hireCode();
	
    @Path("empInfoModel.hireName")
    ValueProvider<Opr01_CreateModel,  String> 	hireName();
	
    @Path("empInfoModel.retireDate")
    ValueProvider<Opr01_CreateModel,  Date> 	retireDate();
	
    @Path("empInfoModel.retireCode")
    ValueProvider<Opr01_CreateModel,  String> 	retireCode();
	
    @Path("empInfoModel.retireName")
    ValueProvider<Opr01_CreateModel,  String> 	retireName();
	
    @Path("empInfoModel.transDate")
    ValueProvider<Opr01_CreateModel,  Date> 	transDate();
	
//    @Path("empInfoModel.closeDate")
//    ValueProvider<Opr01_CreateModel,  Date> 	closeDate();
	
    @Path("empInfoModel.positionCode")
    ValueProvider<Opr01_CreateModel,  String> 	positionCode();
	
    @Path("empInfoModel.positionName")
    ValueProvider<Opr01_CreateModel,  String> 	positionName();
	
    @Path("empInfoModel.titleCode")
    ValueProvider<Opr01_CreateModel,  String> 	titleCode();
	
    @Path("empInfoModel.titleName")
    ValueProvider<Opr01_CreateModel,  String> 	titleName();
	
    @Path("empInfoModel.orgCode")
    ValueProvider<Opr01_CreateModel,  String> 	orgCode();
	
    @Path("empInfoModel.orgName")
    ValueProvider<Opr01_CreateModel,  String> 	orgName();

}
