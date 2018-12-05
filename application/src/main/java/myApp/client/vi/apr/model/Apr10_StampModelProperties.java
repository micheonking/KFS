package myApp.client.vi.apr.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import myApp.client.vi.opr.model.Opr03_RegModel;

public interface Apr10_StampModelProperties extends PropertyAccess<Apr10_StampModel> {

	ModelKeyProvider<Apr10_StampModel> keyId();

	ValueProvider<Apr10_StampModel,	Long>	stampId();
	ValueProvider<Apr10_StampModel,	Long>	orgId();
	ValueProvider<Apr10_StampModel,	String>	stampName();
	ValueProvider<Apr10_StampModel,	Date>	startDate();
	ValueProvider<Apr10_StampModel,	Date>	closeDate();
	ValueProvider<Apr10_StampModel,	String>	useName();
	ValueProvider<Apr10_StampModel,	String>	note();
	ValueProvider<Apr10_StampModel,	Long>	empId();

	ValueProvider<Apr10_StampModel,	String>	imageCell();
	ValueProvider<Apr10_StampModel,	String>	stampId_string();
	ValueProvider<Apr10_StampModel,	String>	orgName();
	ValueProvider<Apr10_StampModel,	Long>	stampFileId();
	ValueProvider<Apr10_StampModel,	Long>	aprEmpId();
	ValueProvider<Apr10_StampModel,	String>	aprEmpName();

	ValueProvider<Apr10_StampModel, String> actionCellUp();// 그리드에 버튼을 넣기 
}
