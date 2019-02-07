package myApp.client.vi;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

import myApp.client.vi.cst.model.Cst01_UserModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.sys.model.Sys02_UserModel;

public class LoginUser {

	// isAdmin -- 전체 시스템 관리자 
	// isManager -- 회사에 속한 관리자   
	
	public static Sys02_UserModel userModel = new Sys02_UserModel(); // admin user 
	public static Emp00_InfoModel empModel = new Emp00_InfoModel(); 
	public static Cst01_UserModel cstUserModel = new Cst01_UserModel(); 

	public static Boolean isAdmin = false ; 
	public static Boolean isManager = false ; 

	
	public static void setIsAdmin(Boolean adminYn) {
		isAdmin = adminYn ; 
	}
	
	public static Boolean getIsAdmin() {
		return isAdmin ; 
	}
	
	public static void setIsManager(Boolean managerYn) {
		isManager = managerYn ; 
	}
	
	public static Boolean getIsMansger() {
		return isManager ; 
	}
	
	public static void setUserModel(Sys02_UserModel userInfo) {
		setIsManager(true); // 회사관리자이다.   
		userModel = userInfo; 
	}

	public static void setEmpModel(Emp00_InfoModel empModel2) {
		empModel = empModel2; 
	}

	public static void setCstUserModel(Cst01_UserModel cstUserModel2) {
		cstUserModel = cstUserModel2; 
	}

 	public static long getCompanyId() {
 		
 		if(isAdmin) {
 			return 0 ; // admin은 사용자 정보가 없다.  
 		} 
 		else if(empModel.getEmpId() != null ) {
 			return empModel.getCompanyId() ; 
 		}
 		else if(cstUserModel.getCompanyId() != null) {
 			return cstUserModel.getCompanyId();	
 		}
 		else if(userModel.getCompanyId() != null) {
 			return userModel.getCompanyId();	
 		}
 		else {
 			return -1; 
 		}
	}
	
 	public static long getUserId() {

 		if(isAdmin) {
 			return 0 ; // admin은 사용자 정보가 없다.  
 		}
 		else if(isManager) { // 회사관리자. 
 			return userModel.getUserId();
 		} 
 		else if(cstUserModel.getUserId() != null) {
 			return cstUserModel.getUserId();	
 		}
 		else if(empModel.getEmpId() != null) { // 일반사용자. 
 				return empModel.getEmpId() ; 
 		}
 		else { // 오류발생. 
 			return -1 ; 
 		}
	}

 	public static String getUserName() {
 		if(empModel.getEmpId() != null ) {
 			return empModel.getKorName() ;  
 		}
 		else if(cstUserModel.getUserId() != null) {
 			return cstUserModel.getUserName();	
 		}
 		else {
 			return userModel.getKorName(); 	
 		}
	}
 	
 	public static Cst01_UserModel getCstUserModel() {
 		return cstUserModel; 
 	}
 	
 	public static Emp00_InfoModel getEmpModel() {
 		return empModel; 
 	}
 	
 	//부서코드
 	public static String getOrgCode() {
 		return empModel.getOrgCode();
 	}
 	
 	//부서코드
 	public static Long getOrgCodeId() {
 		return empModel.getOrgCodeId();
 	}

 	//부서명
 	public static String getOrgKorName() {
 		return empModel.getOrgName();  
	}
 	
 	//직위코드
 	public static String getPosCode() {
 		return empModel.getPositionCode();
 	}
 	
 	//직위명
 	public static String getPosName() {
 		return empModel.getPositionName();
 	}
	
	public static String getYear(){
		return DateTimeFormat.getFormat( "yyyy" ).format( new Date()) ; 
	}
	
	public static String getTodayString(){
		return DateTimeFormat.getFormat( "yyyy-MM-dd" ).format(new Date()) ; 
	}
	
	public static Date getToday(){
		return DateTimeFormat.getFormat( "yyyy-MM-dd" ).parse(getTodayString()) ; 
	}
	
}
