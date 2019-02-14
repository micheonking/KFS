package myApp.client.vi;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

import myApp.client.vi.sys.model.Sys02_UsrInfoModel;


public class LoginUser {

	public static Boolean isAdmin = false ; 
	public static Sys02_UsrInfoModel usrModel = new Sys02_UsrInfoModel(); 

	public static void setIsAdmin(Boolean adminYn) {
		isAdmin = adminYn ; 
	}
	public static Boolean getIsAdmin() {
		return isAdmin ; 
	}

	public static void setUsrInfoModel(Sys02_UsrInfoModel usrInfo) {
		usrModel = usrInfo; 
	}

	public static Sys02_UsrInfoModel getUsrInfoModel() {
		return usrModel ; 
	}

 	public static String getCmpCode() {
 		if(isAdmin) {
 			return "00000" ;  
 		} 
 		else if(usrModel.getUsrNo() != null ) {
 			return usrModel.getCmpInfoModel().getCmpCode() ; 
 		}
 		else {
 			return null; 
 		}
	}

 	public static String getUsrNo() {
 		if(isAdmin) {
 			return "ADMIN" ;  
 		} 
 		else {
 			return usrModel.getUsrNo(); 
 		}
 		// 		return usrModel.getUsrNo(); 
	}

 	public static String getUsrName() {
 		return usrModel.getUsrName(); 
	}

 	public static String getDptCode() {
 		return usrModel.getDptInfoModel().getDptCode(); 
	}

 	public static String getDptName() {
 		return usrModel.getDptInfoModel().getDptName(); 
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
