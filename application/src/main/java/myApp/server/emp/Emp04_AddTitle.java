package myApp.server.emp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.emp.model.Emp04_AddTitleModel;
import myApp.server.utils.db.UpdateDataModel;

public class Emp04_AddTitle {

	private String mapperName = "emp04_add_title";

    public void selectByEmpId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

    	Long empId = request.getLongParam("empId"); 
		
		String sqlId = mapperName + ".selectByEmpId" ; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, empId);
		result.setRetrieveResult(list.size(), sqlId, list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = request.getList();
		String str = "";
		String sqlId = mapperName + ".selectByOrgId"; 

		int k = 0;
		for(int i = 0; i < list.size(); i++) {
			Emp04_AddTitleModel tempModel = (Emp04_AddTitleModel) list.get(i);
			List<GridDataModel> tempList = sqlSession.selectList(sqlId, tempModel.getOrgCodeId());
			
			System.out.println(tempModel.getOrgCodeId());
			for(int j = 0; j < tempList.size(); j++) {
				Emp04_AddTitleModel tempModel2 = (Emp04_AddTitleModel) tempList.get(j);
				if(tempModel.getOrgCodeId().toString().equals(tempModel2.getOrgCodeId().toString()) &&//조직이 같음
				   tempModel.getTitleCode().equals(tempModel2.getTitleCode()) &&//직책이 같음
				   !tempModel.getEmpId().toString().equals(tempModel2.getEmpId().toString())
				   ){
					System.out.println(j + " " + tempModel2.getStartDate().toString());
					System.out.println(j + " " + tempModel2.getCloseDate().toString());
					System.out.println(j + " " + tempModel.getStartDate().toString());
					System.out.println(j + " " + tempModel.getCloseDate().toString());
					if(compareDate(tempModel2.getStartDate(), tempModel2.getCloseDate(), tempModel.getStartDate(), tempModel.getCloseDate())) {
						k++;
						str = tempModel2.getOrgName() + " ";
						str += tempModel2.getTitleName() + "으로 "+ tempModel2.getEmpInfoModel().getTitleName() + " " + tempModel2.getEmpInfoModel().getKorName() + " 님이 지정되어 있습니다. 날짜를 확인해주세요.";
						break;
					}
				}
			}
			if(k > 0) {
				break;
			}
		}
		
		if(k > 0) {
			result.setMessage(str);
		} else {
			UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
			updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
		}
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<GridDataModel> updateModel = new UpdateDataModel<GridDataModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
	private Boolean compareDate(Date startDate, Date closeDate, Date paramStartDate, Date paramCloseDate) {
		Boolean result = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		
		if(startDate.compareTo(paramStartDate) * closeDate.compareTo(paramStartDate) > 0) {
			System.out.println("첫번째에서 걸림");
			result = false;	
		}
		
		if(startDate.compareTo(paramCloseDate) * closeDate.compareTo(paramCloseDate) > 0) {
			System.out.println("두번째에서 걸림");
			result = false;	
		}
		
		/*
		int start1 = startDate.compareTo(paramStartDate);
		int start2 = closeDate.compareTo(paramStartDate);
		int close1 = closeDate.compareTo(paramStartDate);
		int close2 = closeDate.compareTo(paramCloseDate);
		if(start1 >= 0 || start2 < 0) {
			result = false;
		} else {
			result = true;
		}
		 */	
		return result;
		
	}
}
