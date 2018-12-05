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
import com.sencha.gxt.widget.core.client.form.CheckBox;
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
import myApp.client.vi.sys.model.Sys06_MenuModel;

public class Sys06_Edit_Menu extends Window implements Editor<Sys06_MenuModel>{

	interface EditDriver extends SimpleBeanEditorDriver<Sys06_MenuModel, Sys06_Edit_Menu> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	
	TreeGrid<Sys06_MenuModel> treeGrid ; 
	TextField menuName 	= new TextField(); // 메뉴명
	TextField className	= new TextField(); // 클래스명 
	TextField seq 		= new TextField(); // 정렬순서 
	CheckBox useYnFlag 	= new CheckBox(); // 메뉴감추기 
	TextArea note 		= new TextArea(); // 비고 

	public void editMenu(TreeGrid<Sys06_MenuModel> treeGrid, Sys06_MenuModel menuModel) {
		
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

//	public void insertMenu(TreeGrid<Sys06_MenuModel> treeGrid, Long parentId) {
//		DBUtil dbUtil = new DBUtil(); 
//		dbUtil.setSeq(menuModel, new InterfaceCallback() {
//			@Override
//			public void execute() {
//				menuModel.setParentId(parentId);
//				menuModel.setNote("parent id is " + parentId);
//				editMenu(treeGrid, menuModel); 
//			}
//		});
//	}

	
	
	private void update(){
		TreeGridUpdate<Sys06_MenuModel> service = new TreeGridUpdate<Sys06_MenuModel>(); 
		service.update(this.treeGrid.getTreeStore(), editDriver.flush(), "sys.Sys06_Menu.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				hide(); 
			}
		});
	}
	
	private void delete(){
		TreeGridDelete<Sys06_MenuModel> service = new TreeGridDelete<Sys06_MenuModel>();
		List<Sys06_MenuModel> checkedList = treeGrid.getSelectionModel().getSelectedItems() ; 
		service.delete(treeGrid.getTreeStore(), checkedList, "sys.Sys06_Menu.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				hide(); 
			}
		});
	}
	
	public void setForm() {
		

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
		
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show(); 
	}

}
