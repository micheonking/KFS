package myApp.client.vi.bbs;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
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

public class Bbs01_Edit_Board extends ContentPanel implements Editor<Bbs01_BoardModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Bbs01_BoardModel, Bbs01_Edit_Board> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	Grid<Bbs01_BoardModel> grid;
	Bbs01_BoardModel bbs01_BoardModel = new Bbs01_BoardModel(); 
	TextField typeName = new TextField();
	TextField typeCode = new TextField();
//	ComboBoxField typeName = new ComboBoxField("BoardTypeCode", typeCode);
	TextField title  = new TextField();
	HtmlEditor content = new HtmlEditor();
	TextField writeUserName = new TextField();
	@Path("userModel.korName")
	TextField korName = new TextField();
	DateField writeDate = new DateField();

	Sys10_Lookup_MultiFile fileUploadForm = new Sys10_Lookup_MultiFile(null,"N",100); // 파일업로드
	
	public Bbs01_Edit_Board(Grid<Bbs01_BoardModel> grid) {
		this.setHeaderVisible(false);
		this.grid = grid;
		editDriver.initialize(this);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(this.getEditor());
	}

	private FormPanel getEditor() {
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(230, -1); // 한컬럼의 크기(라벨 + 필드)
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(55, -1);
		rowLayout.setMargins(new Margins(0, 20, 0, 0)); // 컬럼간의 간격조정

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(title, "제목"), new HorizontalLayoutData(1, -1, new Margins(0, 20, 0, 0)));
		title.setReadOnly(true);
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(typeName, "구분"), rowLayout);
		typeName.setWidth(150);
//		typeName.addCollapseHandler(new CollapseHandler(){
//			@Override
//			public void onCollapse(CollapseEvent event) {
//				typeCode.setValue(typeName.getCode());
//			}
//    	});   
		typeName.setReadOnly(true);
		row02.add(new FieldLabel(writeUserName, "작성자"), rowLayout);
		writeUserName.setReadOnly(true);
		row02.add(new FieldLabel(writeDate, "작성일"), rowLayout);
		writeDate.setReadOnly(true);
//		writeDate.setHideTrigger(true);
		
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(content, "내용"), new HorizontalLayoutData(1, 1, new Margins(0, 20, 0, 0)));

		//		파일 업로드 
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		row04.add(new LabelToolItem("파일:"),rowLayout2 );
		row04.add(fileUploadForm, new HorizontalLayoutData(1,1));
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row03, new VerticalLayoutData(1, 0.7, new Margins(16, 16, 0, 16)));
		layout.add(row04, new VerticalLayoutData(1, 0.7, new Margins(16, 16, 0, 16)));
		
		layout.setScrollMode(ScrollMode.AUTOY);

		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer(); 
		hlc.add(layout, new HorizontalLayoutData(0.6, 1)); 
		hlc.add(new SimplePanel(), new HorizontalLayoutData(1, 1, new Margins(15, 15, 15, 0)));
		
//		// form setting
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(50); // 모든 field 적용 후 설정한다.
		return form;
	}

	public void edit(Bbs01_BoardModel bbs01_BoardModel){
		this.bbs01_BoardModel = bbs01_BoardModel; 
		editDriver.edit(this.bbs01_BoardModel);
//		gridFileBuilder.retrieve(bbs01_BoardModel.getBoardId());
		this.setEnabled(true);
	}
	

	public void retrieve(Map<String, Object> param) {
		
		if(param != null) { 
			Bbs01_BoardModel boardModel = (Bbs01_BoardModel)param.get("boardModel"); 
			editDriver.edit(boardModel);
			fileUploadForm.retrieve(boardModel.getBoardId());
//			Info.display("",""+boardModel.getBoardId());
		}
		else {
			editDriver.initialize(this);
		}
	}

	public void deleteRow() {
		GridDeleteData<Bbs01_BoardModel> service = new GridDeleteData<Bbs01_BoardModel>();
		List<Bbs01_BoardModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "bbs.Bbs01_Board.delete");
		this.setEnabled(false);
	}
	
	public void init() {
		editDriver.edit(new Bbs01_BoardModel());
	}


	
}