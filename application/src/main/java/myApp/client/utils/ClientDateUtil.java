package myApp.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class ClientDateUtil {
	
	public ClientDateUtil() {
	}

	// 요일 구하기
	public String getDayOfWeek(String str) {
		if (str.length() < 8) {
			return null;
		}
		Date date = new Date();
		date = DateTimeFormat.getFormat("yyyyMMdd").parse(str);
		int dayOfWeek = Integer.parseInt(DateTimeFormat.getFormat("c").format(date));
		switch (dayOfWeek) {
		case 0:
			str = "일";
			break;
		case 1:
			str = "월";
			break;
		case 2:
			str = "화";
			break;
		case 3:
			str = "수";
			break;
		case 4:
			str = "목";
			break;
		case 5:
			str = "금";
			break;
		case 6:
			str = "토";
			break;
		}
		return str;
	}

	public static String getYear() {
		return DateTimeFormat.getFormat("yyyy").format(new Date());
	}

	public static Date toDate(String str) {
		return toDate("yyyy-MM-dd", str);
	}

	public static Date toDate(Date date) {
		String strToDay = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		date = DateTimeFormat.getFormat("yyyy-MM-dd").parse(strToDay);
		return date;
	}

	public static Date toDate(String format, String str) {
		Date date = DateTimeFormat.getFormat(format).parse(str);
		return date;
	}

	public static Date getToday() {
		Date date = new Date();
		String strToDay = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		date = DateTimeFormat.getFormat("yyyy-MM-dd").parse(strToDay);
		return date;
	}
	
	public static String getToday(Date date) {
		String strToDay = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		return strToDay;
	}

}
