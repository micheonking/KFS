package myApp.client.vi.psc;

import java.util.List;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.utils.InterfaceLookupResult;
import myApp.client.vi.psc.model.ClassStudentModel;
import myApp.client.vi.psc.model.ClassStudentModelProperties;
import myApp.client.vi.psc.model.StudentModel;
import myApp.client.vi.psc.model.StudyClassModel;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Page_StudentClass extends ContentPanel implements InterfaceLookupResult{

	private ClassStudentModelProperties properties = GWT.create(ClassStudentModelProperties.class);	
	private Grid<ClassStudentModel> grid = this.buildGrid(); 
	private StudentModel studentModel ;

	public Page_StudentClass(){
		
		this.setBorders(true);
		this.setHeading("반 배정 상세정보");
		this.setHeaderVisible(true);
		
		this.add(this.grid);

		TextButton insertButton = new TextButton("등록");
		insertButton.setWidth(60);
		insertButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertRow(); 
			}
		}); 
		
		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		}); 

		TextButton deleteButton = new TextButton("삭제"); 
		deleteButton.setWidth(60);
		deleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				deleteRow();
			}
		}); 

		this.getButtonBar().add(insertButton);
		this.getButtonBar().add(updateButton);
		this.getButtonBar().add(deleteButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}
	
	 
	public void retrieve(StudentModel studentModel){
		this.studentModel = studentModel; 
		GridRetrieveData<ClassStudentModel> service = new GridRetrieveData<ClassStudentModel>(this.grid.getStore());
		service.addParam("studentId", studentModel.getStudentId());
		service.retrieve("psc.ClassStudent.selectByStudentId");
	}
	
	public void update(){
		GridUpdate<ClassStudentModel> service = new GridUpdate<ClassStudentModel>(); 
		service.update(this.grid.getStore(), "psc.ClassStudent.update"); 
	}
	
	public void insertRow(){
		GridInsertRow<ClassStudentModel> service = new GridInsertRow<ClassStudentModel>(); 
		ClassStudentModel ClassStudentModel = new ClassStudentModel();
		ClassStudentModel.setStudentId(this.studentModel.getStudentId());
		service.insertRow(grid, ClassStudentModel);
	}
	
	public void deleteRow(){
		GridDeleteData<ClassStudentModel> service = new GridDeleteData<ClassStudentModel>();
		List<ClassStudentModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(this.grid.getStore(), checkedList, "psc.ClassStudent.delete");
	}
	
	public Grid<ClassStudentModel> getGrid(){
		return this.grid; 
	}

	public Grid<ClassStudentModel> buildGrid(){
		
		GridBuilder<ClassStudentModel> gridBuilder = new GridBuilder<ClassStudentModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addDate(properties.assignDate(), 100, "배정일", new DateField());

		// 반이름 찾기 팝업 
		LookupTriggerField lookupStudyClass = new LookupTriggerField(); 
		lookupStudyClass.setEditable(false);
		lookupStudyClass.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				new Lookup_StudyClass(getThis()).show();
			}
		}); 
		gridBuilder.addText(properties.studyClassName(), 120, "반이름", lookupStudyClass);

		gridBuilder.addText(properties.studyClassTypeName(), 100, "반구분", new TextField());
		gridBuilder.addText(properties.note(), 400, "비고", new TextField());

		return gridBuilder.getGrid();  
	}

	@Override
	public void setLookupResult(Object result) {
		ClassStudentModel classStudentModel = grid.getSelectionModel().getSelectedItem();
		
		StudyClassModel studyClassModel = (StudyClassModel)result; 
		
		Long studyClassId = studyClassModel.getStudyClassId(); 
		String studyClassName = studyClassModel.getStudyClassName();
		String studyClassTypeName = studyClassModel.getStudyClassTypeName();
		
		// addChange setting 
		this.grid.getStore().getRecord(classStudentModel).addChange(properties.studyClassId(), studyClassId); 
		this.grid.getStore().getRecord(classStudentModel).addChange(properties.studyClassName(), studyClassName);
		this.grid.getStore().getRecord(classStudentModel).addChange(properties.studyClassTypeName(), studyClassTypeName);
	}

	public InterfaceLookupResult getThis() {
		return this;
	}
}
