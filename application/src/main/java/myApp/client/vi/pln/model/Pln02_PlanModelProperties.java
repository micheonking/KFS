package myApp.client.vi.pln.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import myApp.client.vi.opr.model.Opr03_RegModel;

public interface Pln02_PlanModelProperties extends PropertyAccess<Pln02_PlanModel> {
//
	ModelKeyProvider<Pln02_PlanModel> keyId();
	
	ValueProvider<Pln02_PlanModel,	Long>	planId();
	ValueProvider<Pln02_PlanModel,	Long>	fundId();
	ValueProvider<Pln02_PlanModel,	Long>	classTreeId();
	ValueProvider<Pln02_PlanModel,	Date>	startDate();
	ValueProvider<Pln02_PlanModel,	Date>	dueDate();
	ValueProvider<Pln02_PlanModel,	Long>	apprId();
	ValueProvider<Pln02_PlanModel,	Long>	docId();
	ValueProvider<Pln02_PlanModel,	Long>	empId();
	ValueProvider<Pln02_PlanModel,	String>	note();
	ValueProvider<Pln02_PlanModel,	String>	updateYn();

	ValueProvider<Pln02_PlanModel,	String>	closeYn();
	ValueProvider<Pln02_PlanModel,	String>	deleteCell();
	ValueProvider<Pln02_PlanModel, 	String> actionCell();   

	@Path("empInfoModel.companyId")
	ValueProvider<Pln02_PlanModel, Long> companyId();
	
	@Path("empInfoModel.korName")
	ValueProvider<Pln02_PlanModel, String> korName();
	
	@Path("empInfoModel.positionName")
	ValueProvider<Pln02_PlanModel, String> positionName();
	
	@Path("empInfoModel.titleName")
	ValueProvider<Pln02_PlanModel, String> titleName();
	
	@Path("empInfoModel.orgName")
	ValueProvider<Pln02_PlanModel, String> orgName();
	
	@Path("empInfoModel.empNo")
	ValueProvider<Pln02_PlanModel, String> empNo();

	@Path("fundInfoModel.fundName")
	ValueProvider<Pln02_PlanModel, String> fundName();

	@Path("classTreeModel.classTreeName")
	ValueProvider<Pln02_PlanModel, String> classTreeName();

	@Path("classTreeModel.approvalTypeCode")
	ValueProvider<Pln02_PlanModel, String> approvalTypeCode();

	@Path("classTreeModel.approvalTypeName")
	ValueProvider<Pln02_PlanModel, String> approvalTypeName();

}
