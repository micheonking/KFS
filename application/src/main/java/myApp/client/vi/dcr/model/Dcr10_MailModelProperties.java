package myApp.client.vi.dcr.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dcr10_MailModelProperties extends PropertyAccess<Dcr10_MailHistoryModel> {
	
	ModelKeyProvider<Dcr10_MailHistoryModel> keyId();
	
	ValueProvider<Dcr10_MailHistoryModel, Long  > calendarId();
	ValueProvider<Dcr10_MailHistoryModel, Long  > companyId();
	ValueProvider<Dcr10_MailHistoryModel, Date  > day();
	ValueProvider<Dcr10_MailHistoryModel, String> weekday();
	ValueProvider<Dcr10_MailHistoryModel, String> workingYn();
	ValueProvider<Dcr10_MailHistoryModel, String> offReason();
	ValueProvider<Dcr10_MailHistoryModel, String> note();
	
	ValueProvider<Dcr10_MailHistoryModel, Boolean> workingYnFlag();
}
