package myApp.client.grid;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;

import myApp.client.field.LookupTriggerField;

import com.sencha.gxt.widget.core.client.form.TextField;

public class SearchBarBuilder {

	InterfaceGridOperate target; 
	ButtonBar searchBar = new ButtonBar(); 
	
	public SearchBarBuilder(InterfaceGridOperate target){
		this.target = target; 
	}
	
	public ButtonBar getSearchBar(){
		this.searchBar.setPadding(new Padding(5, 0, 0, 0));
		return this.searchBar; 
	}
	
	public void addLookupTriggerField(LookupTriggerField field, String labelName, int width, int labelWidth){
		
		FieldLabel triggerField = new FieldLabel(field,  labelName); 
		triggerField.setWidth(width);
		triggerField.setLabelWidth(labelWidth);
		triggerField.setLabelAlign(LabelAlign.LEFT);
		triggerField.setLabelPad(0);
		
		this.searchBar.add(triggerField);
	}
	
	public void addText(String text){
		Label label = new Label(text); 
		label.setLayoutData(new Margins(0, 10, 0, 0));
		searchBar.add(new Label(text));
	}

	public FieldLabel addComboBox(ComboBoxField field, String labelName, int width, int labelWidth){
		FieldLabel fieldLabel = new FieldLabel(field, labelName); 
		fieldLabel.setWidth(width);
		fieldLabel.setLabelWidth(labelWidth);
		fieldLabel.setLabelAlign(LabelAlign.LEFT);
		searchBar.add(fieldLabel);
		
		return fieldLabel; 
	}
	
	public FieldLabel addTextField(TextField field, String labelName, int width, int labelWidth, boolean useEnterKey){
		
		FieldLabel fieldLabel = new FieldLabel(field, labelName); 
		fieldLabel.setWidth(width);
		fieldLabel.setLabelWidth(labelWidth);
		fieldLabel.setLabelAlign(LabelAlign.LEFT);
		
		if(useEnterKey){
			field.addKeyDownHandler(new KeyDownHandler(){
				@Override
				public void onKeyDown(KeyDownEvent arg0) {
					if(arg0.getNativeKeyCode() == 13){ // enter key down 
						target.retrieve(); 
					}
				}
			}); 
		}
		searchBar.add(fieldLabel);
		
		return fieldLabel; 
	}

	public FieldLabel addTextField(TextField textField, String labelName, int width, int labelWidth){
		return this.addTextField(textField, labelName, width, labelWidth, false);
	}
	
	public FieldLabel addCheckBox(CheckBox Field, int width, int labelWidth){

		FieldLabel fieldLabel = new FieldLabel(Field, ""); 
		fieldLabel.setWidth(width);
		fieldLabel.setLabelWidth(labelWidth);
		fieldLabel.setLabelAlign(LabelAlign.LEFT);
		fieldLabel.setLabelSeparator(" ");
		searchBar.add(fieldLabel);
		
		return fieldLabel; 
	}
	
	public FieldLabel addDateField(DateField field, String labelName, int width, int labelWidth, boolean useEnterKey){
		
		FieldLabel fieldLabel = new FieldLabel(field, labelName); 
		fieldLabel.setWidth(width);
		fieldLabel.setLabelWidth(labelWidth);
		fieldLabel.setLabelAlign(LabelAlign.LEFT);
		
		if(useEnterKey){
			field.addKeyDownHandler(new KeyDownHandler(){
				@Override
				public void onKeyDown(KeyDownEvent arg0) {
					if(arg0.getNativeKeyCode() == 13){ // enter key down 
						target.retrieve(); 
					}
				}
			}); 
		}
		searchBar.add(fieldLabel);
		
		return fieldLabel; 
	}

	public FieldLabel addDateField(DateField field, String labelName, int width, int labelWidth){
		return this.addDateField(field, labelName, width, labelWidth, false);
	}
	
	public TextButton addRetrieveButton(){
		TextButton button = new TextButton("조회");
		button.setWidth(50);
		button.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				target.retrieve();
			}
		}); 
		searchBar.add(button);
		return button; 
	}
	
	public TextButton addUpdateButton(){
//		TextButton button = new TextButton("저장");
//		button.setWidth(50);
//		button.addSelectHandler(new SelectHandler(){
//			@Override
//			public void onSelect(SelectEvent event) {
//				target.update();
//			}
//		}); 
//		searchBar.add(button);
//		return button; 
		
		return addUpdateButton("저장", 50);
	}

	public TextButton addUpdateButton(String buttonName, int buttonSize){
		TextButton button = new TextButton(buttonName);
		button.setWidth(buttonSize);
		button.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				target.update();
			}
		}); 
		searchBar.add(button);
		return button; 
	}

	public TextButton addInsertButton(){
//		TextButton button = new TextButton("입력");
//		button.setWidth(50);
//		button.addSelectHandler(new SelectHandler(){
//			@Override
//			public void onSelect(SelectEvent event) {
//				target.insertRow();
//			}
//		}); 
//		searchBar.add(button);
//		
//		return button; 
		return addInsertButton("입력", 50);
	}

	public TextButton addInsertButton(String buttonName, int buttonSize){
		TextButton button = new TextButton(buttonName);
		button.setWidth(buttonSize);
		button.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				target.insertRow();
			}
		}); 
		searchBar.add(button);
		
		return button; 
	}

	public TextButton addDeleteButton(){
		TextButton button = new TextButton("삭제");
		button.setWidth(50);
		button.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				target.deleteRow();
			}
		}); 
		searchBar.add(button);
		return button; 
	}
}
