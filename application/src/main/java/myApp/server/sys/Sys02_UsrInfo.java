package myApp.server.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys01_CmpInfoModel;
import myApp.client.vi.sys.model.Sys02_UsrInfoModel;
import myApp.server.utils.db.UpdateDataModel;

public class Sys02_UsrInfo {
	
	public void getLoginAdminInfo(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String cmpCode	 = request.getStringParam("cmpCode");
		String otpNumber = request.getStringParam("otpNumber");
		
		if ((otpNumber == null)||("".equals(otpNumber))) {
			result.fail(-1, "OTP인증번호를 입력하여 주십시오.");
			return;
		}
		
		Sys01_CmpInfoModel cmpModel = sqlSession.selectOne("sys01_cmpInfo.selectByCmpCode", cmpCode);
		if (otpNumber.equals(cmpModel.getTmpPwd())) {
			result.setModel(1, "pass user!", null);
		} else {
			result.fail(-1, "OTP인증번호가 틀립니다.");
		}
		return ;
	}
	
	public void getLoginInfo(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String otpNumber = request.getStringParam("otpNumber");
		if ((otpNumber == null)||("".equals(otpNumber))) {
			result.fail(-1, "OTP인증번호를 입력하여 주십시오.");
			return;
		}
		
		Sys02_UsrInfoModel usrInfo = sqlSession.selectOne("sys02_usrInfo.selectByUsrNo", request.getParam()) ;
		if(usrInfo != null) {
			result.setModel(1, "login OK", usrInfo);
		} else {
			result.fail(-1, "등록된 사용자 정보가 아닙니다. 입력정보를 확인하여 주십시요!");
		}
		return ; 
	}

	public void selectByUsrName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String usrName = request.getStringParam("usrName");
		
		if(usrName == null){
			usrName = "";
		}
		usrName = "%" + usrName + "%"; 

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmpCd"  , LoginUser.getCmpCode());
		param.put("usrName", usrName); 

		List<GridDataModel> list = sqlSession.selectList("sys02_usrInfo.selectByUserName", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByCmpCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<GridDataModel> list = sqlSession.selectList("sys02_usrInfo.selectByCmpCode", LoginUser.getCmpCode());
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys02_UsrInfoModel> updateModel = new UpdateDataModel<Sys02_UsrInfoModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), "sys02_usrInfo", result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Sys02_UsrInfoModel> updateModel = new UpdateDataModel<Sys02_UsrInfoModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), "sys02_usrInfo", result);
	}
}
