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
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.ComboBoxField;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.client.vi.sys.Lookup_MultiFile;

public class Edit_BoardOriginal extends ContentPanel implements Editor<Bbs01_BoardModel> {


	interface EditDriver extends SimpleBeanEditorDriver<Bbs01_BoardModel, Bbs01_Edit_Board> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	
	 Grid<Bbs01_BoardModel> grid;
	
	Bbs01_BoardModel bbs01_BoardModel = new Bbs01_BoardModel(); 
		
	TextField typeCode = new TextField();
	ComboBoxField typeName = new ComboBoxField("BoardTypeCode", typeCode);
	
	TextField title  = new TextField();
	HtmlEditor content = new HtmlEditor();
	
	LongField writeUserId = new LongField();
	
	@Path("userModel.korName")
	TextField korName = new TextField();
	DateField writeDate = new DateField();

	Lookup_MultiFile fileUploadForm = new Lookup_MultiFile(); // file upload form. 옛날버전
	
//	 Grid_File gridFileBuilder = new Grid_File(); 
	
//	Grid<Sys10_FileModel> gridFile = gridFileBuilder.getGrid(); 
	
	
//	private Grid<Sys10_FileModel> gridFile = gridFileBuilder.getGrid(); 
	
	public Edit_BoardOriginal(Grid<Bbs01_BoardModel> grid) {
		this.setHeaderVisible(false);
		this.grid = grid;
//		editDriver.initialize(this);
		
		
//		editDriver.edit(this.bbs01_BoardModel);
//		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
//		searchBarBuilder.addInsertButton();
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addDeleteButton();
//		this.getButtonBar().add(searchBarBuilder.getSearchBar());
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(this.getEditor());
		
	}


	private FormPanel getEditor() {
		
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(210, -1); // 한컬럼의 크기(라벨 + 필드)
		rowLayout.setMargins(new Margins(0, 20, 0, 0)); // 컬럼간의 간격조정

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(title, "제목"), new HorizontalLayoutData(1, -1, new Margins(0, 20, 0, 0)));
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(typeName, "구분"), rowLayout);
		typeName.setWidth(150);
		typeName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				typeCode.setValue(typeName.getCode());
			}
    	});     

		row02.add(new FieldLabel(writeUserId, "작성자"), rowLayout);
//		korName.setReadOnly(true);
		row02.add(new FieldLabel(writeDate, "작성일"), rowLayout);
//		writeDate.setReadOnly(true);
//		writeDate.setHideTrigger(true);
		
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(content, "내용"), new HorizontalLayoutData(1, 1, new Margins(0, 20, 0, 0)));

		//		파일 업로드 
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(fileUploadForm, "파일업로드"), new HorizontalLayoutData(1, 1, new Margins(0, 20, 0, 0)));
//		
		final FileUploadField fileUploadFiled = new FileUploadField();
		fileUploadForm.add(new FieldLabel(fileUploadFiled, "파일"));
//
//		fileUploadForm.setEncoding(Encoding.MULTIPART);
//		fileUploadForm.setMethod(Method.POST);
//		fileUploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler(){
//			@Override
//			public void onSubmitComplete(SubmitCompleteEvent event) {
//				Info.display("upload", event.getResults().toString());
//				gridFileBuilder.retrieve(bbs01_BoardModel.getBoardId());
//			}
//		});
		row04.add(fileUploadForm, new HorizontalLayoutData(0.9, 1, new Margins(2, 2, 0, 0)));
//		
//		TextButton fileUploadButton = new TextButton("등록"); 
//		fileUploadButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				if (!fileUploadForm.isValid()) {
//					return;
//				}
//				
//				if("".equals(fileUploadFiled.getValue().trim())) { 
//					Info.display("파일확인", "먼저 업로드할 파일을 선택하여 주세요.");
//				}
//				else {
//					String actionUrl = "FileUpload?uploadType=file&"; 
//					actionUrl = actionUrl + "parentId=" + bbs01_BoardModel.getBoardId();
//					
//					fileUploadForm.setAction(actionUrl); // File upload servelt call - web.xml 참조
//					fileUploadForm.submit();
//					fileUploadFiled.reset();
//				}
//			}
//		});
//		row04.add(fileUploadButton, new HorizontalLayoutData(60, 28, new Margins(2, 2, 2, 1))); 
		
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		
//		row05.add(new FieldLabel(gridFile, "첨부"), new HorizontalLayoutData(1, 1, new Margins(0, 20, 0, 0)));
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row03, new VerticalLayoutData(1, 0.7, new Margins(16, 16, 0, 16)));
//		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(3, 16, 16, 16)));
		layout.add(row05, new VerticalLayoutData(1, 0.3, new Margins(20, 16, 0, 16)));
//		
		layout.setScrollMode(ScrollMode.AUTOY);
// 
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

		}
		else {
//			editDriver.initialize(this);
		}
		
	}
	

	public void update(){
//		// html editor에서 "'"값을 변경한다.
		this.bbs01_BoardModel = editDriver.flush() ; 
		//this.bbs01_BoardModel.setContent(this.bbs01_BoardModel.getContent().replaceAll("'", "''"));
		
		GridUpdate<Bbs01_BoardModel> service = new GridUpdate<Bbs01_BoardModel>(); 
//		service.update(this.grid.getStore(), this.bbs01_BoardModel, "bbs.Bbs01_Board.update",new InterfaceCallback() {
		service.update(grid.getStore(), editDriver.flush(),"bbs.Bbs01_Board.update", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.getStore().update(editDriver.flush());
			}
		}); 
	}

//	public void insertRow() {
//		this.bbs01_BoardModel = new Bbs01_BoardModel();
//		this.bbs01_BoardModel.setCompanyId(LoginUser.getCompanyId());
//		this.bbs01_BoardModel.setWriteUserId(LoginUser.getUserId());
//		
//		this.bbs01_BoardModel.setWriteDate(new Date());
//		
//		GridInsertRow<Bbs01_BoardModel> service = new GridInsertRow<Bbs01_BoardModel>();
//		service.insertRow(this.grid, bbs01_BoardModel);
//		this.setEnabled(true);
//	}
	

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