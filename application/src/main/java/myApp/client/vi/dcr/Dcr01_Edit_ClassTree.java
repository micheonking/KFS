package myApp.client.vi.dcr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.TextArea;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.TreeGridDelete;
import myApp.client.service.TreeGridUpdate;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;

public class Dcr01_Edit_ClassTree extends Window implements Editor<Dcr01_ClassTreeModel>{

	interface EditDriver extends SimpleBeanEditorDriver<Dcr01_ClassTreeModel, Dcr01_Edit_ClassTree> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	TreeGrid<Dcr01_ClassTreeModel> treeGrid ;
	InterfaceCallbackResult callback; 
	
	LongField companyId = new LongField();
	LongField classTreeId = new LongField();
	LongField parentTreeId = new LongField();

	TextField classTreeName = new TextField();
	TextField classTreeCode = new TextField();
	TextField seq = new TextField();
	TextArea  note      = new TextArea();//비고
	
	public void editData(TreeGrid<Dcr01_ClassTreeModel> treeGird, 
			Dcr01_ClassTreeModel classTreeModel, 
			String actionCode, 
			String titleName, 
			InterfaceCallbackResult callback) {

		this.setModal(true);
		this.setHeading(titleName);
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		this.setSize("440",  "330");

		this.treeGrid = treeGird;
		this.callback = callback; 
		
		editDriver.initialize(this);
		editDriver.edit(classTreeModel);
		
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		VerticalLayoutContainer layout = new VerticalLayoutContainer(); // 합치기
		
		row01.add(new FieldLabel(classTreeCode, "분류코드"), new HorizontalLayoutData(200, 40));
		row02.add(new FieldLabel(classTreeName, "분류명"), new HorizontalLayoutData(400, 40));
		row03.add(new FieldLabel(seq, "조회순서"), new HorizontalLayoutData(150, 40));
		row04.add(new FieldLabel(note, "상세설명"), new HorizontalLayoutData(400, 130));

		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(16)));
		
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		this.add(form);
		
		TextButton deleteButton = new TextButton("삭제");
		TextButton updateButton = new TextButton("저장");
		TextButton closeButton = new TextButton("닫기");
		
		if("edit".equals(actionCode)){
			deleteButton.setWidth(60);
			deleteButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {
					delete(); // 삭제는 Edit시에만 가능하다.  
				}
			}); 
			this.getButtonBar().add(deleteButton);
		}

		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
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

	public void update() {
		Dcr01_ClassTreeModel updateData = editDriver.flush(); 
		
		if(updateData.getClassTreeName() == null || updateData.getClassTreeName().equals("")) {
			new SimpleMessage("업무명은 반드시 등록하여야 합니다");
			return ; 
		}
		if(updateData.getSeq() == null || updateData.getSeq().equals("")) {
			new SimpleMessage("조회순서는 반드시 등록하여야 합니다");
			return ; 
		}
		
		TreeGridUpdate<Dcr01_ClassTreeModel> service = new TreeGridUpdate<Dcr01_ClassTreeModel>();
		service.update(this.treeGrid.getTreeStore(), updateData, "dcr.Dcr01_ClassTree.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				hide(); 
				callback.execute(updateData);
			}
		});
	}
	
	public void delete(){
		Dcr01_ClassTreeModel deleteData = editDriver.flush();
		Dcr01_ClassTreeModel parentData = this.treeGrid.getTreeStore().getParent(deleteData); 
		
		TreeGridDelete<Dcr01_ClassTreeModel> service = new TreeGridDelete<Dcr01_ClassTreeModel>();
		service.update(this.treeGrid.getTreeStore(), deleteData, "dcr.Dcr01_ClassTree.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				hide();
				callback.execute(parentData);
			}
		});
	}
}
