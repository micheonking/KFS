package myApp.client.vi.sys;

import java.util.List;

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
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.TreeGridDelete;
import myApp.client.service.TreeGridUpdate;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys03_MenuModel;

public class Sys03_Lookup_EditMenu extends Window implements Editor<Sys03_MenuModel>{

	interface EditDriver extends SimpleBeanEditorDriver<Sys03_MenuModel, Sys03_Lookup_EditMenu> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	
	TreeGrid<Sys03_MenuModel> treeGrid ; 
	TextField menuName 	= new TextField(); // 메뉴명
	TextField className	= new TextField(); // 클래스명 
	TextField seqStr	= new TextField(); // 정렬순서
	TextArea  rmk 		= new TextArea();  // 비고 

	public void open(TreeGrid<Sys03_MenuModel> treeGrid, Sys03_MenuModel menuModel) {
		
		this.setHeading("메뉴 편집");
		this.setBorders(false);
		this.setResizable(false);
		this.setModal(true);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		this.treeGrid = treeGrid;
		editDriver.initialize(this);
		editDriver.edit(menuModel);
		
		this.setForm();
		this.show();
	}
	
	public void setForm() {

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		vlc.add(new FieldLabel(menuName, "메뉴명"), new VerticalLayoutData(1, 50, new Margins(15, 15, 0, 15)));
		vlc.add(new FieldLabel(className, "클래스명"), new VerticalLayoutData(1, 40, new Margins(5, 15, 0, 15)));
		vlc.add(new FieldLabel(seqStr, "정렬순서"), new VerticalLayoutData(1, 40, new Margins(5, 15, 0, 15)));
		vlc.add(new FieldLabel(rmk, "비고"), new VerticalLayoutData(1, 100, new Margins(5, 15, 0, 15)));

		FormPanel form = new FormPanel();
		form.setWidget(vlc);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		form.setSize("420", "235");
		this.add(form);

		TextButton deleteButton = new TextButton("삭제");
		
		deleteButton.setWidth(60);
		deleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				delete();
			}
		}); 
		this.getButtonBar().add(deleteButton);

		
		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		}); 
		this.getButtonBar().add(updateButton);
		
		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 

		this.getButtonBar().add(closeButton);
		this.getButtonBar().setHeight(60);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.show(); 
	}

	private void update(){
		
		TreeGridUpdate<Sys03_MenuModel> service = new TreeGridUpdate<Sys03_MenuModel>();
		service.addParam("usrNo", LoginUser.getUsrNo());
		service.update(this.treeGrid.getTreeStore(), editDriver.flush(), "sys.Sys03_Menu.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				hide(); 
			}
		});
	}
	
	private void delete(){
		TreeGridDelete<Sys03_MenuModel> service = new TreeGridDelete<Sys03_MenuModel>();
		List<Sys03_MenuModel> checkedList = treeGrid.getSelectionModel().getSelectedItems() ; 
		service.delete(treeGrid.getTreeStore(), checkedList, "sys.Sys03_Menu.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				hide(); 
			}
		});
	}

}
