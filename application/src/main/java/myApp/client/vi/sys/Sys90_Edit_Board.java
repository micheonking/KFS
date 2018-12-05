package myApp.client.vi.sys;

import java.util.Map;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.service.GridUpdate;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.model.Sys90_AfterServiceModel;

public class Sys90_Edit_Board extends ContentPanel implements Editor<Sys90_AfterServiceModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Sys90_AfterServiceModel, Sys90_Edit_Board> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	Grid<Sys90_AfterServiceModel> grid;
	TextField typeYn = new TextField();
	TextField regEmpName = new TextField();
	
	ComboBoxField afterServiceName = new ComboBoxField("AfterServiceCode",typeYn);
	TextArea happenReason = new TextArea();
	TextArea happenRemedy  = new TextArea();
	TextArea remarks  = new TextArea();
	TextField worker  = new TextField();
	DateField happenStartDate 	  =	new DateField();
	DateField dueDate 	  =	new DateField();
	CheckBox 	closeYn = new CheckBox();
	
	private TextButton updateButton = new TextButton("저장");
	
	public Sys90_Edit_Board(Grid<Sys90_AfterServiceModel> grid) {
		this.setHeaderVisible(false);
		this.grid = grid;
		editDriver.initialize(this);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		
		updateButton.setWidth(50);
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				updateRow();
			}
		});
		this.addButton(updateButton);
		
		this.add(this.getEditor());
	}

	

	private FormPanel getEditor() {
		HorizontalLayoutData rowLayout3 = new HorizontalLayoutData(220, -1, new Margins(0,0,0,0));	// 나머지 싱글 칼럼

		//여백
//		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
//		row00.add(new FieldLabel(worker, ""),termLayout2);
		
		//발생일자
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(happenStartDate, "발생일자"),rowLayout3);
		row01.add(new FieldLabel(afterServiceName, "구분"),rowLayout3);
		row01.add(new FieldLabel(regEmpName, "요청자"),rowLayout3);
		
//		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
//		happenReason.setHeight(100);
//		row01.add(new LabelToolItem("발생일자:"), new HorizontalLayoutData(100,-1, new Margins(0,0,0,3)));
//		row01.add(happenReason,rowLayout3); //가로넓이 , 세로 넓이		
		
		
		//여백
//		row01.add(new LabelToolItem(), termLayout);
//		row01.add(new LabelToolItem(), termLayout);
		 
		//구분
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
//		row02.add(new FieldLabel(afterServiceName, "구분"), rowLayout3);
		
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		happenReason.setHeight(100);
		row04.add(new LabelToolItem("개선 및 오류내용 :"), new HorizontalLayoutData(100,-1, new Margins(0,0,0,3)));
		row04.add(happenReason, new HorizontalLayoutData(750,250, new Margins(5,5,10,5))); //가로넓이 , 세로 넓이		
//		row04.add(new FieldLabel(happenReason, "개선 및 오류내용"), rowLayout3);
//		row04.add( row04, rowLayout);
		
//		//여백
//		HorizontalLayoutContainer row11 = new HorizontalLayoutContainer();
//		row11.add(new FieldLabel(worker, ""),termLayout);

		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
//		happenRemedy.setHeight(100);
		row05.add(new LabelToolItem("처리방안 :"), new HorizontalLayoutData(100,-1, new Margins(0,0,0,45)));
		row05.add(happenRemedy, new HorizontalLayoutData(750,250, new Margins(5,5,10,5))); //가로넓이 , 세로 넓이 
//		row04.add(new FieldLabel(happenRemedy, "처리방안"), rowLayout3);
		//여백
//		HorizontalLayoutContainer row12 = new HorizontalLayoutContainer();
//		row12.add(new FieldLabel(worker, ""),termLayout);
//		
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		row06.add(new FieldLabel(worker, "담당자 "), rowLayout3);
		row06.add(new FieldLabel(dueDate, "완료예정일 "), rowLayout3);
		row06.add(new FieldLabel(closeYn, "완료여부 "), rowLayout3);
		
		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
//		remarks.setHeight(100);
		row07.add(new LabelToolItem("비고 : "), new HorizontalLayoutData(100,-1, new Margins(0,0,0,68)));
		row07.add(remarks, new HorizontalLayoutData(750,100, new Margins(5,5,10,5))); //가로넓이 , 세로 넓이 
		
//		row06.add(new FieldLabel(remarksFeild, "비고"),rowLayout3);
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer();
//		layout.add(row00, new VerticalLayoutData(1,-1, new Margins(10, 15, 5, 15)));
		layout.add(row01, new VerticalLayoutData(1,-1, new Margins(20, 15, 5, 15)));
//		layout.add(row02, new VerticalLayoutData(1,-1, new Margins(30, 15, 5, 15)));
		layout.add(row04, new VerticalLayoutData(1,-1, new Margins(25, 15, 5, 15)));
		layout.add(row05, new VerticalLayoutData(1,-1, new Margins(240, 15, 5, 15)));
		layout.add(row06, new VerticalLayoutData(1,-1, new Margins(245, 15, 5, 15)));
		layout.add(row07, new VerticalLayoutData(1,-1, new Margins(25, 15, 5, 15)));
		
		// form setting
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(100); // 모든 field 적용 후 설정한다.
			return form;
	}

	public void retrieve(Map<String, Object> param) {

		if(param != null) { 
			Sys90_AfterServiceModel afterServiceModel = (Sys90_AfterServiceModel)param.get("afterServiceModel"); 
			editDriver.edit(afterServiceModel);
//			fileUploadForm.retrieve(boardModel.getBoardId());
//			Info.display("",""+boardModel.getBoardId());
		}
		else {
			editDriver.initialize(this);
		}
	}
	
	protected void updateRow() {
		Info.display("","여기");
		GridUpdate<Sys90_AfterServiceModel> service = new GridUpdate<Sys90_AfterServiceModel>();
		service.update(grid.getStore(), editDriver.flush(), "sys.Sys90_AfterService.update");
	}
		
	public void init() {
		
	}


}
