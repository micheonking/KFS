package myApp.client.field;

import com.sencha.gxt.widget.core.client.form.TriggerField;

public class LookupTriggerField extends TriggerField<String> {

	public LookupTriggerField() {
		super(new LookupTriggerCell());
	}

}