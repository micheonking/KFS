package myApp.server.utils.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	
	public static Date getDateParam(String dateString, String dateFormat){
		
    	SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
    	
    	Date date = null;
    	
		try {
			date = transFormat.parse(dateString);
		
		} catch (ParseException e) {
			date = new Date();  //오류인 경우 오늘일자를 보내준다. 
			e.printStackTrace();
		}

		return date; 
	}
	
	public static String getDateParam(Date date, String dateFormat){
		
    	SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
    	String returnString = transFormat.format(date); 
		return returnString; 
	}

	
	public static Date getDateParam(String dateString){
		return getDateParam(dateString, "yyyy-MM-dd"); 
	}
}
