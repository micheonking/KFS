package myApp.client.vi.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Sys12_CalendarModelProperties extends PropertyAccess<Sys12_CalendarModel> {
	
	ModelKeyProvider<Sys12_CalendarModel> keyId();
	
	ValueProvider<Sys12_CalendarModel, Long  > calendarId();
	ValueProvider<Sys12_CalendarModel, Long  > companyId();
	ValueProvider<Sys12_CalendarModel, Date  > day();
	ValueProvider<Sys12_CalendarModel, String> weekday();
	ValueProvider<Sys12_CalendarModel, String> workingYn();
	ValueProvider<Sys12_CalendarModel, String> offReason();
	ValueProvider<Sys12_CalendarModel, String> note();
	
	ValueProvider<Sys12_CalendarModel, Boolean> workingYnFlag();
}
