package myApp.client.vi.bbs;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.ComboBoxField;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.client.vi.sys.Lookup_MultiFile;
import myApp.client.vi.sys.Sys10_Lookup_MultiFile;

public class Bbs01_Lookup_BoardUpdate extends Window implements Editor<Bbs01_BoardModel> {
	
	interface EditDriver extends SimpleBeanEditorDriver<Bbs01_BoardModel, Bbs01_Lookup_BoardUpdate>{}
	EditDriver editDriver = GWT.create(EditDriver.class);
	Grid<Bbs01_BoardModel> grid;
	ContentPanel contentPanel  = new ContentPanel();

	Bbs01_BoardModel boardModel = new Bbs01_BoardModel();
	TextField typeCode = new TextField();
	ComboBoxField typeName = new ComboBoxField("BoardTypeCode", typeCode);

	TextField title  = new TextField();
	HtmlEditor content = new HtmlEditor();
	TextField writeUserName = new TextField();
	DateField writeDate = new DateField();
	LongField writeUserId = new LongField();
    
	public Bbs01_Lookup_BoardUpdate(Grid<Bbs01_BoardModel> grid, Bbs01_BoardModel boardModel) {
		
		this.boardModel = boardModel;//넘겨준페이지에서받을라면 있어야한다.
		this.setHeading("게시판 등록");
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.setSize("1100",  "720");
		this.grid = grid;
		editDriver.initialize(this);
		
		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		}); 
		
		this.addButton(updateButton);
		
		TextButton closeButton = new TextButton("닫기"); 
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 
	
		this.getButtonBar().add(closeButton);
		
		TextButton deleteButton = new TextButton("삭제"); 
		deleteButton.setWidth(60);
		deleteButton.addSelectHandler(new SelectHandler(){
			
			@Override
			public void onSelect(SelectEvent event) {
				delete(); 
			}
		}); 
		
		this.getButtonBar().add(deleteButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(this.getEditor());
		editDriver.edit(boardModel);
	}

	private FormPanel getEditor() {
		
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(210, -1); // 한컬럼의 크기(라벨 + 필드)
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(55, -1);
		rowLayout.setMargins(new Margins(0, 20, 0, 0)); // 컬럼간의 간격조정

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(title, "제목"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 0)));
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(typeName, "구분"), rowLayout);
		typeName.setWidth(150);
		typeName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				typeCode.setValue(typeName.getCode());
			}
    	});     
		
		writeUserName.setReadOnly(true);
		row02.add(new FieldLabel(writeUserName, "작성자"), rowLayout);
		
		row02.add(new FieldLabel(writeDate, "작성일"), rowLayout);
		
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(content, "내용"), new HorizontalLayoutData(1, 1, new Margins(0, 10, 0, 0)));
		
//		첨부파일 기능
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		row04.add(new LabelToolItem("첨부파일"),rowLayout2);
		
//		Info.display("bbb","boardModel.getBoardId() : " + boardModel.getBoardId());
		Sys10_Lookup_MultiFile fileForm = new Sys10_Lookup_MultiFile(boardModel.getBoardId(), "Y", 120) ;//파일업로드
		row04.add(fileForm, new HorizontalLayoutData(1000, 1));

		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row03, new VerticalLayoutData(1, 0.7, new Margins(16, 16, 0, 16)));
		layout.add(row04, new VerticalLayoutData(1, 0.3, new Margins(3, 16, 5, 16)));
		//		form setting
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(50); // 모든 field 적용 후 설정한다.
		form.setSize("820", "384");
		
	return form;
}

	protected void update() {
		GridUpdate<Bbs01_BoardModel> service = new GridUpdate<Bbs01_BoardModel>();
		service.update( grid.getStore(), editDriver.flush(),"bbs.Bbs01_Board.update", new InterfaceCallback() {
			@Override
			public void execute() {
//				grid.getStore().update(editDriver.flush());
				hide();
			}
		});
	}
	
	
	protected void delete() {
		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 내용을 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					GridDeleteData<Bbs01_BoardModel> service = new GridDeleteData<Bbs01_BoardModel>();
					List<Bbs01_BoardModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
					service.delete(grid.getStore(), checkedList, "bbs.Bbs01_Board.delete", new InterfaceCallback() {
					
						public void execute() {
								hide();
							if(grid.getStore().get(0) != null) {
								grid.getSelectionModel().select(0, false);
							}			
						}
					});
					
					break;
				case NO:
				default:
					break;
				}
			}
		});
		msgBox.setWidth(300);
		msgBox.show();
	}

	
}
