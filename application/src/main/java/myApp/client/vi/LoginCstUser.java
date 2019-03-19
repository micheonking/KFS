package myApp.client.vi;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

import myApp.client.vi.cst.model.Cst01_UserModel;
import myApp.client.vi.sys.model.Sys02_UserModel;

public class LoginCstUser {

	// isAdmin -- 전체 시스템 관리자 
	// isManager -- 회사에 속한 관리자   

	private static Cst01_UserModel loginUser;
	
	public static Cst01_UserModel getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(Cst01_UserModel loginCstUser) {
		LoginCstUser.loginUser = loginCstUser;
	}
	
//	public static Boolean isAdmin(){
//		return "true".equals(loginUser.getAdminYn()); 
//	}
	
	public static String getYear(){
		return DateTimeFormat.getFormat( "yyyy" ).format( new Date()) ; 
	}
	
	public static String getToday(){
		return DateTimeFormat.getFormat( "yyyy-mm-dd" ).format(new Date()) ; 
	}

	
//	public static Sys02_UserModel userModel = new Sys02_UserModel(); // admin user 
//	public static Cst01_UserModel cst01UserModel = new Cst01_UserModel(); 
//
//	public static Boolean isAdmin = false ; 
//	public static Boolean isManager = false ; 
//
//	
//	public static void setIsAdmin(Boolean adminYn) {
//		isAdmin = adminYn ; 
//	}
//	
//	public static Boolean getIsAdmin() {
//		return isAdmin ; 
//	}
//	
//	public static void setIsManager(Boolean managerYn) {
//		isManager = managerYn ; 
//	}
//	
//	public static Boolean getIsMansger() {
//		return isManager ; 
//	}
//	
//	public static void setUserModel(Sys02_UserModel userInfo) {
//		setIsManager(true); // 회사관리자이다.   
//		userModel = userInfo; 
//	}
//
//	public static void setEmpModel(Cst01_UserModel cst01UserModel2) {
//		cst01UserModel = cst01UserModel2; 
//	}
//
// 	public static long getCompanyId() {
// 		
// 		if(isAdmin) {
// 			return 0 ; // admin은 사용자 정보가 없다.  
// 		} 
// 		else if(cst01UserModel.getEmail() != null ) {
// 			return 0 ;  // 고객도 사용자 정보가 없다.
// 		}
// 		else if(userModel.getCompanyId() != null) {
// 			return userModel.getCompanyId();	
// 		}
// 		else {
// 			return -1; 
// 		}
//	}
//	
// 	public static long getUserId() {
//
// 		if(isAdmin) {
// 			return 0 ; // admin은 사용자 정보가 없다.  
// 		}
// 		else if(isManager) { // 회사관리자. 
// 			return userModel.getUserId();
// 		} 
// 		else if(cst01UserModel.getEmail() != null) { // 일반사용자. 
// 				return cst01UserModel.getUserId() ; 
// 		}
// 		else { // 오류발생. 
// 			return -1 ; 
// 		}
//	}
//
// 	public static String getUserName() {
// 		if(cst01UserModel.getUserId() != null ) {
// 			return cst01UserModel.getUserName();  
// 		}
// 		else {
// 			return userModel.getKorName(); 	
// 		}
//	}
// 	
// 	public static Cst01_UserModel getEmpModel() {
// 		return cst01UserModel; 
// 	}
// 	
//	public static String getYear(){
//		return DateTimeFormat.getFormat( "yyyy" ).format( new Date()) ; 
//	}
//	
//	public static String getTodayString(){
//		return DateTimeFormat.getFormat( "yyyy-MM-dd" ).format(new Date()) ; 
//	}
//	
//	public static Date getToday(){
//		return DateTimeFormat.getFormat( "yyyy-MM-dd" ).parse(getTodayString()) ; 
//	}
//
//	public static void setLoginUser(Cst01_UserModel loginUser) {
//		// TODO Auto-generated method stub
//		LoginUser.loginUser = loginUser;
//		LoginUser.setLoginCompany(loginUser.getCompanyModel());
//	}
//
////	public static void setLoginUser(Cst01_UserModel loginUser) {
////		LoginUser.loginUser = loginUser;
////		LoginUser.setLoginCompany(loginUser.getCompanyModel());
////	}
//	
//	public static void setLoginCompany(CompanyModel companyModel){
//		loginCompany = companyModel; 
//	}

}
