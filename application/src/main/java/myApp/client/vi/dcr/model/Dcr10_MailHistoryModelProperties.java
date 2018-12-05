package myApp.client.vi.dcr.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Dcr10_MailHistoryModelProperties extends PropertyAccess<Dcr10_MailHistoryModel> {

	ModelKeyProvider<Dcr10_MailHistoryModel> keyId();

	ValueProvider<Dcr10_MailHistoryModel,	Long>	mailHistoryId();
	ValueProvider<Dcr10_MailHistoryModel,	Long>	docId();
	ValueProvider<Dcr10_MailHistoryModel,	Long>	aprId();
	ValueProvider<Dcr10_MailHistoryModel,	String>	senderEmail();
	ValueProvider<Dcr10_MailHistoryModel,	Date>	sendTime();
	ValueProvider<Dcr10_MailHistoryModel,	String>	receiverEmail();
	ValueProvider<Dcr10_MailHistoryModel,	String>	referencerEmail();
	ValueProvider<Dcr10_MailHistoryModel,	String>	attachedFile();
	ValueProvider<Dcr10_MailHistoryModel,	String>	pdfFile();
	ValueProvider<Dcr10_MailHistoryModel,	String>	sealFile();
	ValueProvider<Dcr10_MailHistoryModel,	String>	titleText();
	ValueProvider<Dcr10_MailHistoryModel,	String>	bodyText();
	
	ValueProvider<Dcr10_MailHistoryModel,	String>	sendTimeString();

}
