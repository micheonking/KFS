package myApp.client.vi.sys;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.sys.model.Sys06_MenuModel;

public class Sys06_Edit_Menu2 extends Window implements Editor<Sys06_MenuModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Sys06_MenuModel, Sys06_Edit_Menu2> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	Sys06_MenuModel menuModel;
	
	Sys06_Tab_Menu tabMenu; 
	
	TextField menuName 	= new TextField(); // 메뉴명
	TextField className	= new TextField(); // 클래스명 
	TextField seq 		= new TextField(); // 정렬순서 
	CheckBox useYnFlag 	= new CheckBox(); // 메뉴감추기 
//	TextField hiddenYn 	= new TextField(); // 메뉴감추기 텍스트
	TextArea note 		= new TextArea(); // 비고 
	
	public void editMenu(Sys06_MenuModel menuModel) {

		if(menuModel == null) {
			this.menuModel = new Sys06_MenuModel(); 
		}
		else { 
			this.menuModel = menuModel; 
		} 
		
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());

		editDriver.initialize(this);
		editDriver.edit(this.menuModel);

		VerticalLayoutContainer layout = new VerticalLayoutContainer(); // 합치기

		layout.add(new FieldLabel(menuName, "메뉴명"), new VerticalLayoutData(1, 50, new Margins(15, 15, 0, 15)));
		layout.add(new FieldLabel(className, "클래스명"), new VerticalLayoutData(1, 40, new Margins(5, 15, 0, 15)));
		layout.add(new FieldLabel(seq, "정렬순서"), new VerticalLayoutData(1, 40, new Margins(5, 15, 0, 15)));
		
		useYnFlag.setBoxLabel("사용여부");
		layout.add(useYnFlag, new VerticalLayoutData(1, 40, new Margins(5, 15, 0, 80)));

		layout.add(new FieldLabel(note, "비고"), new VerticalLayoutData(1, 100, new Margins(0, 15, 0, 15)));

		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		form.setSize("420", "267");
		this.add(form);

		TextButton updateButton = new TextButton("저장");
		TextButton closeButton = new TextButton("닫기");

		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		}); 
		this.getButtonBar().add(updateButton);

		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 
		this.getButtonBar().add(closeButton);
		
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show(); 

	}
	
//	private void update() {
//	}
	
}
