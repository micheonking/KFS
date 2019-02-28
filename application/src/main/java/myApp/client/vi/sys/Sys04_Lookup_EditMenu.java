package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.TreeGridDelete;
import myApp.client.service.TreeGridUpdate;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys03_MenuModel;
import myApp.client.vi.sys.model.Sys04_CmpMenuModel;

public class Sys04_Lookup_EditMenu extends Window implements Editor<Sys04_CmpMenuModel>{

	interface EditDriver extends SimpleBeanEditorDriver<Sys04_CmpMenuModel, Sys04_Lookup_EditMenu> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	TreeGrid<Sys04_CmpMenuModel> treeGrid ;
	Sys04_CmpMenuModel cmpMenuModel = new Sys04_CmpMenuModel();

	TextField cmpCode   = new TextField();
	LongField cmpMenuId = new LongField();
	LongField menuId    = new LongField();
	LongField prntId    = new LongField();
	TextField seqStr    = new TextField(); // 정렬순서
	CheckBox  useYnFlag = new CheckBox();  // 사용유무
	TextArea  rmk 	    = new TextArea();  // 비고 
	TextField menuName 	= new TextField(); // 메뉴명

	@Path("menuModel.className")
	TextField className	= new TextField(); // 클래스명 

	private StringComboBox combobox = new StringComboBox();
	VerticalLayoutData layout  = new VerticalLayoutData(1, 40,new Margins(5, 20, 0, 15));
	VerticalLayoutData layout2 = new VerticalLayoutData(1,100,new Margins(5, 20, 0, 15));

	public void open(TreeGrid<Sys04_CmpMenuModel> treeGrid, Sys04_CmpMenuModel menuModel) {
		
		this.setHeading(" 메뉴 편집");
		this.setBorders(false);
		this.setResizable(false);
		this.setModal(true);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		this.treeGrid = treeGrid;
		this.cmpMenuModel = menuModel;
		editDriver.initialize(this);
		editDriver.edit(menuModel);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		ContentPanel form = new ContentPanel();
		form.setHeaderVisible(false);
		form.setSize("420", "270");

		//ComboBox SET
		combobox.getStore().add("메뉴");
		combobox.getStore().add("화면");
		combobox.setTriggerAction(TriggerAction.ALL);
		combobox.setEditable(false);
		combobox.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
				StringComboBox data = (StringComboBox)event.getSource();
				form.clear();
				if ("메뉴".equals(data.getText())) {
					form.setWidget(setFormMenu());
				} else {
					form.setWidget(setFormObject());
				}
			}
		});
		
		if(cmpMenuModel.getPrntId() == 0) {
			combobox.setText("메뉴");
			form.setWidget(setFormMenu());
		} else {
			combobox.setText("화면");
			form.setWidget(setFormObject());
		}

		FieldLabel comboField = new FieldLabel(combobox, "구분 ");
		comboField.setWidth(200);
		comboField.setLabelWidth(60);

		vlc.add(comboField, new VerticalLayoutData(1, -1, new Margins(15,20, 0,15)));
		vlc.add(form, new VerticalLayoutData(1,1,new Margins(0)));
		this.setWidget(vlc);

		//Button SET
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
	
	protected VerticalLayoutContainer setFormObject() {

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		FieldLabel menuNameField = new FieldLabel(menuName, "화면명");
		menuName.setReadOnly(true);
		menuNameField.setLabelWidth(60);
		menuNameField.setWidth(332);

		TextButton btn = new TextButton("찾기");
		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Sys03_Lookup_Menu loopupMenu = new Sys03_Lookup_Menu();
				loopupMenu.open(new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						Sys03_MenuModel menuModel = (Sys03_MenuModel)result;
						menuName.setValue(menuModel.getMenuName());
						className.setValue(menuModel.getClassName());
						menuId.setValue(menuModel.getMenuId());
					}
				});
			}
		});

		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
		hlc.add(menuNameField, new HorizontalLayoutData(-1,1,new Margins(0, 0, 0, 0)));
		hlc.add(btn, new HorizontalLayoutData(-1,1,new Margins(0, 0, 0, 3)));
		vlc.add(hlc, layout);

		FieldLabel classNameField = new FieldLabel(className, "클래스명");
		className.setReadOnly(true);
		classNameField.setLabelWidth(60);
		vlc.add(classNameField, layout);
		
		FieldLabel seqStrField = new FieldLabel(seqStr, "정렬순서");
		seqStrField.setLabelWidth(60);
		vlc.add(seqStrField, layout);

//		useYnFlag.setBoxLabel("사용여부");			//현재 CheckBox 오류남... 우선 막음 (20190228)
//		vlc.add(useYnFlag, new VerticalLayoutData(1,40,new Margins(5, 15, 0, 80)));

		FieldLabel rmkField = new FieldLabel(rmk, "비고");
		rmkField.setLabelWidth(60);
		vlc.add(rmkField, layout2);
		
		return vlc;
	}

	protected VerticalLayoutContainer setFormMenu() {

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		FieldLabel menuNameField = new FieldLabel(menuName, "메뉴명");
		menuName.setReadOnly(false);
		menuNameField.setLabelWidth(60);
		vlc.add(menuNameField, layout);
		
		FieldLabel seqStrField = new FieldLabel(seqStr, "정렬순서");
		seqStrField.setLabelWidth(60);
		vlc.add(seqStrField, layout);

//		useYnFlag.setBoxLabel("사용여부");			//현재 CheckBox 오류남... 우선 막음 (20190228)
//		vlc.add(useYnFlag, new VerticalLayoutData(1,40,new Margins(5, 15, 0, 80)));

		FieldLabel rmkField = new FieldLabel(rmk, "비고");
		rmkField.setLabelWidth(60);
		vlc.add(rmkField, layout2);
		
		return vlc;
	}

	private void update(){
		
		if(seqStr.getText() == "") {
			new SimpleMessage("확인", "정렬순서를 입력하여 주십시오.");
			return;
		}

		this.cmpMenuModel = editDriver.flush();
		if(combobox.getText().equals("화면")) {
			if(this.cmpMenuModel.getMenuId() == null) {
				new SimpleMessage("확인", "화면을 선택하여 주십시오.");
				return;
			}
		} else {
			if(this.cmpMenuModel.getMenuName() == null) {
				new SimpleMessage("확인", "메뉴명을 입력하여 주십시오.");
				return;
			}
		}
		
		TreeGridUpdate<Sys04_CmpMenuModel> service = new TreeGridUpdate<Sys04_CmpMenuModel>();
		service.addParam("usrNo", LoginUser.getUsrNo());
		service.update(this.treeGrid.getTreeStore(), cmpMenuModel, "sys.Sys04_CmpMenu.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				hide(); 
			}
		});
	}
	
	private void delete(){
		TreeGridDelete<Sys04_CmpMenuModel> service = new TreeGridDelete<Sys04_CmpMenuModel>();
		List<Sys04_CmpMenuModel> checkedList = treeGrid.getSelectionModel().getSelectedItems() ; 
		service.delete(treeGrid.getTreeStore(), checkedList, "sys.Sys04_CmpMenu.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				hide(); 
			}
		});
	}

}
