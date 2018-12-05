package myApp.server.emp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp03_TransModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp03_Trans {

	private String mapperName = "emp03_trans";

    public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
    	
    	Emp02_EmpModel empModel = (Emp02_EmpModel)request.getModelParam("empModel");
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("empId", empModel.getEmpId());//사원번호
		param.put("hireDate", empModel.getHireDate());//입사일자
		
		Date retireDate = empModel.getRetireDate() ; //퇴사일자는 Null일수 있다. 
		
		if(retireDate == null) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				retireDate = formatter.parse("9999-12-31");
			} catch (ParseException e) {
				retireDate = new Date(); 
				e.printStackTrace();
			}
		}
		param.put("retireDate", retireDate);
		
		String sqlId = mapperName + ".selectByEmpId" ; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(list.size(), sqlId, list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp03_TransModel> updateModel = new UpdateDataModel<Emp03_TransModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Emp03_TransModel> updateModel = new UpdateDataModel<Emp03_TransModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
}
