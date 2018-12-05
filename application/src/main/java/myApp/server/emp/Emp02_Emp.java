package myApp.server.emp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp01_PersonModel;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp03_TransModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp02_Emp {
	
	private String mapperName = "emp02_emp";


	public void selectByText(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String sqlId = mapperName + ".selectByText";  
		Map<String, String> param = new HashMap<String, String>();
		
		param.put("companyId", request.getStringParam("companyId"));
		param.put("searchText", "%" + request.getStringParam("searchText") + "%");
		param.put("workTypeCode", request.getStringParam("workTypeCode")); 
		 
		System.out.println("worktypeCode is " + request.getStringParam("workTypeCode")) ; 
		
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		List<GridDataModel> personList = new ArrayList<GridDataModel>();
		List<GridDataModel> transList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			
			Emp02_EmpModel empModel = (Emp02_EmpModel)dataModel; 

			// find person by company & ctzNo
			Long companyId = empModel.getPersonModel().getCompanyId(); 
			String ctzNo = empModel.getPersonModel().getCtzNo(); 
			
			Map<String, Object> param = new HashMap<String, Object>(); 
			param.put("companyId", companyId); 
			param.put("ctzNo", ctzNo); 
			
			Emp01_PersonModel findPersonModel = sqlSession.selectOne("emp01_person.selectByCtzNo", param); 
//			Emp01_PersonModel personModel = new Emp01_PersonModel(); 
			Emp01_PersonModel personModel = empModel.getPersonModel();
			
			if(findPersonModel == null) {
//				personModel = empModel.getPersonModel();
				Long personId = sqlSession.selectOne("getSeq");
				personModel.setPersonId(personId);
				empModel.setPersonId(personId);
			}
			else {
//				personModel = findPersonModel;
//				empModel.setPersonId(findPersonModel.getPersonId());
			}
			 
//			if(personModel.getPersonId() == null) {
//				Long personId = sqlSession.selectOne("getSeq");
//				personModel.setPersonId(personId);
//				empModel.setPersonId(personId);
//			}

			personList.add(personModel); 
			
			Emp03_TransModel transModel = empModel.getTransModel();
			if(transModel.getEmpId() == null) {
				transModel.setEmpId(empModel.getEmpId());
			}
			
			if(transModel.getTransDate() == null) {
				Long transId = sqlSession.selectOne("getSeq");
				transModel.setTransId(transId);
				transModel.setTransDate(empModel.getHireDate()); // 입사발령 등록 
			}

			transList.add(transModel); 
		}

		// person update 
		UpdateDataModel<Emp01_PersonModel> personUpdate = new UpdateDataModel<Emp01_PersonModel>(); 
		personUpdate.updateModel(sqlSession, personList, "emp01_person", result);
		if(result.getStatus() < 0 ) {
			return ; 
		}
		
		// trans update 
		UpdateDataModel<Emp03_TransModel> transUpdate = new UpdateDataModel<Emp03_TransModel>(); 
		transUpdate.updateModel(sqlSession, transList, "emp03_trans", result);
		if(result.getStatus() < 0 ) {
			return ; 
		}
		
		// employee update 
		UpdateDataModel<Emp02_EmpModel> updateModel = new UpdateDataModel<Emp02_EmpModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		List<GridDataModel> personList = new ArrayList<GridDataModel>();
		List<GridDataModel> transList = new ArrayList<GridDataModel>();
		
		for(GridDataModel dataModel : request.getList()) {
			
			Emp02_EmpModel empModel = (Emp02_EmpModel)dataModel; 

			Emp01_PersonModel personModel = empModel.getPersonModel(); 
			personList.add(personModel); 
			
			Emp03_TransModel transModel = empModel.getTransModel(); 
			transList.add(transModel); 
		}

		UpdateDataModel<Emp01_PersonModel> personUpdate = new UpdateDataModel<Emp01_PersonModel>(); 
		personUpdate.deleteModel(sqlSession, personList, "emp01_person", result);
		if(result.getStatus() < 0 ) {
			return ; 
		}
		
		UpdateDataModel<Emp03_TransModel> transUpdate = new UpdateDataModel<Emp03_TransModel>(); 
		transUpdate.deleteModel(sqlSession, transList, "emp03_trans", result);
		if(result.getStatus() < 0 ) {
			return ; 
		}
		
		UpdateDataModel<Emp02_EmpModel> updateModel = new UpdateDataModel<Emp02_EmpModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
}
