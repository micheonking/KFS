package myApp.client.field;

import com.sencha.gxt.widget.core.client.form.DateField;

public class MyDateField extends DateField {
	public MyDateField() {
		this.getDatePicker().setStartDay(0);
	}
}