package myApp.client.vi.emp.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Emp02_EmpModelProperties extends PropertyAccess<Emp02_EmpModel> {

	ModelKeyProvider<Emp02_EmpModel> keyId();
	ValueProvider<Emp02_EmpModel, Long> 	empId();
	ValueProvider<Emp02_EmpModel, Long> 	personId();
	ValueProvider<Emp02_EmpModel, String> 	empNo();
	ValueProvider<Emp02_EmpModel, String> 	empKindCode();
	ValueProvider<Emp02_EmpModel, String> 	empKindName();
	ValueProvider<Emp02_EmpModel, Date> 	hireDate();
	ValueProvider<Emp02_EmpModel, String> 	hireCode();
	ValueProvider<Emp02_EmpModel, String> 	hireName();
	ValueProvider<Emp02_EmpModel, String> 	hireNote();
	ValueProvider<Emp02_EmpModel, Date> 	retireDate();
	ValueProvider<Emp02_EmpModel, String> 	retireCode();
	ValueProvider<Emp02_EmpModel, String> 	retireName();
	ValueProvider<Emp02_EmpModel, String> 	retireNote();
	ValueProvider<Emp02_EmpModel, String> 	retireYn();
	ValueProvider<Emp02_EmpModel, String> 	note();
	ValueProvider<Emp02_EmpModel, String> 	cycleTime();
	ValueProvider<Emp02_EmpModel, String> 	empId_String();
	
	// 개인정보 
	@Path("personModel.companyId")
	ValueProvider<Emp02_EmpModel, Long> companyId();

	@Path("personModel.korName")
	ValueProvider<Emp02_EmpModel, String> korName();

	@Path("personModel.engName")
	ValueProvider<Emp02_EmpModel, String> engName();

	@Path("personModel.chnName")
	ValueProvider<Emp02_EmpModel, String> chnName();

	@Path("personModel.nationCode")
	ValueProvider<Emp02_EmpModel, String> nationCode();

	@Path("personModel.nationtName")
	ValueProvider<Emp02_EmpModel, String> nationName();

	@Path("personModel.ctzNo")
	ValueProvider<Emp02_EmpModel, String> ctzNo();

	@Path("personModel.genderCode")
	ValueProvider<Emp02_EmpModel, String> genderCode();

	@Path("personModel.genderName")
	ValueProvider<Emp02_EmpModel, String> genderName();

	@Path("personModel.birthday")
	ValueProvider<Emp02_EmpModel, Date> birthday();

	@Path("personModel.mobileTelno")
	ValueProvider<Emp02_EmpModel, String> mobileTelno();

	@Path("personModel.homeTelno")
	ValueProvider<Emp02_EmpModel, String> homeTelno();

	@Path("personModel.emgrcyTelno")
	ValueProvider<Emp02_EmpModel, String> emgrcyTelno();

	@Path("personModel.emailAddr")
	ValueProvider<Emp02_EmpModel, String> emailAddr();

	@Path("personModel.zipCode")
	ValueProvider<Emp02_EmpModel, String> zipCd();

	@Path("personModel.addrDetail")
	ValueProvider<Emp02_EmpModel, String> addrDetail();
	
	@Path("orgInfoModel.korName")
	ValueProvider<Emp02_EmpModel, String> orgKorName();

	
	// 발령정보
	@Path("transModel.transId")
	ValueProvider<Emp02_EmpModel, Long> transId();

	@Path("transModel.transDate")
	ValueProvider<Emp02_EmpModel, Date> transDate();

	@Path("transModel.transCode")
	ValueProvider<Emp02_EmpModel, String> transCode();
	
	@Path("transModel.transName")
	ValueProvider<Emp02_EmpModel, String> transName();

	@Path("transModel.orgCodeId")
	ValueProvider<Emp02_EmpModel, Long> orgCodeId();

	@Path("transModel.posCode")
	ValueProvider<Emp02_EmpModel, String> posCode();
	
	@Path("transModel.posName")
	ValueProvider<Emp02_EmpModel, String> posName();

	@Path("transModel.titleCode")
	ValueProvider<Emp02_EmpModel, String> titleCode();
	
	@Path("transModel.titleName")
	ValueProvider<Emp02_EmpModel, String> titleName();

//	@Path("transModel.gradeCode")
//	ValueProvider<Emp02_EmpModel, String> gradeCode();
//	
//	@Path("transModel.gradeName")
//	ValueProvider<Emp02_EmpModel, String> gradeName();

	@Path("transModel.transReason")
	ValueProvider<Emp02_EmpModel, String> transReason();

	@Path("transModel.transNote")
	ValueProvider<Emp02_EmpModel, String> transNote();


}
