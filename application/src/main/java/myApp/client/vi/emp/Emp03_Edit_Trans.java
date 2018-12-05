package myApp.client.vi.emp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.LookupTriggerField;
import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.vi.org.model.Org01_CodeModel;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp03_TransModel;
import myApp.client.vi.org.Org01_Lookup_OrgCode;

public class Emp03_Edit_Trans extends Window implements Editor<Emp03_TransModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Emp03_TransModel, Emp03_Edit_Trans> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	
	private Grid<Emp03_TransModel> grid;
	private Emp02_EmpModel 	empModel ; 
	 

	
	MyDateField transDate 	= new MyDateField();
	TextField transCode     = new TextField();
	ComboBoxField transName = new ComboBoxField("TransCode", transCode);
	
	@Path("orgInfoModel.korName")
	LookupTriggerField orgKorName = new LookupTriggerField() ;

	LongField orgCodeId   = new LongField();
	
	TextField posCode       = new TextField();	//직위코드
	ComboBoxField posName   = new ComboBoxField("EmpPosCode");//직위명
	TextField titleCode     = new TextField();	//직책코드
	ComboBoxField titleName = new ComboBoxField("EmpTitleCode");//직책명
	TextArea transReason = new TextArea();

	public Emp03_Edit_Trans(Grid<Emp03_TransModel> grid, Emp02_EmpModel empModel, Emp03_TransModel transModel) {
		
		this.setHeading("일반발령 등록");
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());

		this.grid = grid;
		this.empModel = empModel; 

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

		
		this.add(this.getEditor(), new MarginData(10));
		editDriver.initialize(this);
		editDriver.edit(transModel);
		
		this.show();
	}
	
	private FormPanel getEditor() {
		
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(220, -1); // 컬럼크기 
	    rowLayout.setMargins(new Margins(0, 40, 5, 0)); // 컬럼간의 간격조정 
	    HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(440, -1); // 컬럼2개 병합 
	    rowLayout2.setMargins(new Margins(0, 40, 5, 0)); // 컬럼간의 간격조정 
	    
    	HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
    	TextField empNo = new TextField();
    	empNo.setText(empModel.getEmpNo());
    	empNo.setEnabled(false);
    	row00.add(new FieldLabel(empNo, "사원번호"), rowLayout);

    	TextField korName = new TextField();
    	korName.setText(empModel.getPersonModel().getKorName());
    	korName.setEnabled(false);
    	row00.add(new FieldLabel(korName, "한글성명"), rowLayout);
    	
    	
    	HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
    	MyDateField hireDate = new MyDateField();
    	hireDate.setValue(empModel.getHireDate());
    	hireDate.setEnabled(false ); // .setEditable(false);
    	row01.add(new FieldLabel(hireDate, "입사일"), rowLayout);

    	TextField empKindName = new TextField();
    	empKindName.setText(empModel.getEmpKindName());
    	empKindName.setEnabled(false);
    	row01.add(new FieldLabel(empKindName, "사원구분"), rowLayout);

    	
	    HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
    	row02.add(new FieldLabel(transName, "발령구분"), rowLayout);
	    transName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				transCode.setValue(transName.getCode());
			}
    	});
    	row02.add(new FieldLabel(transDate, "발령일"), rowLayout);
	    
    	HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
    	row03.add(new FieldLabel(orgKorName, "발령부서"), rowLayout2);
    	orgKorName.addTriggerClickHandler(new TriggerClickHandler(){
    		@Override
    		public void onTriggerClick(TriggerClickEvent event) {
    			
    			transDate.finishEditing();
   	 			Org01_Lookup_OrgCode orgSearch  = new Org01_Lookup_OrgCode(); 
   	 			orgSearch.open(transDate.getValue(), new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						Org01_CodeModel codeModel  = (Org01_CodeModel)result; 
						orgKorName.setValue(codeModel.getOrgInfoModel().getKorName());
						orgCodeId.setValue(codeModel.getCodeId());
					}
   	 			});
    		}
    	});    	
//    	row04.add(new FieldLabel(orgCodeId, "부서코드"), rowLayout); // 일단 감춘다. 
    	
    	HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
    	row05.add(new FieldLabel(posName, "발령직위"), rowLayout);
	    posName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				posCode.setValue(posName.getCode());
			}
    	});
	    
	    TextField currentPosName = new TextField();
	    currentPosName.setEnabled(false); 
	    currentPosName.setText(empModel.getTransModel().getPosName());
	    row05.add(new FieldLabel(currentPosName, "현직위"), rowLayout);
	    		
	    HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
	    row06.add(new FieldLabel(titleName, "발령직책"), rowLayout);
	    titleName.addCollapseHandler(new CollapseHandler(){
	    	@Override
	    	public void onCollapse(CollapseEvent event) {
	    		titleCode.setValue(titleName.getCode());
	    	}
	    });
	    
	    TextField currentTitleName = new TextField();
	    currentTitleName.setEnabled(false);
	    currentTitleName.setText(empModel.getTransModel().getTitleName());
	    row06.add(new FieldLabel(currentTitleName, "현직책"), rowLayout);
	    
	    
    	HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
    	row07.add(new FieldLabel(transReason, "상세사유"), new HorizontalLayoutData(400, 100));
    	
	    
	    VerticalLayoutContainer layout = new VerticalLayoutContainer(); 
	    layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);
	    
	    layout.add(row00, new VerticalLayoutData(1, -1, new Margins(18)));
	    layout.add(row01, new VerticalLayoutData(1, -1, new Margins(18))); 
	    layout.add(row02, new VerticalLayoutData(1, -1, new Margins(40, 18, 18, 18)));
	    layout.add(row03, new VerticalLayoutData(1, -1, new Margins(18)));
	    layout.add(row05, new VerticalLayoutData(1, -1, new Margins(18)));
	    layout.add(row06, new VerticalLayoutData(1, -1, new Margins(18)));
	    layout.add(row07, new VerticalLayoutData(1, -1, new Margins(18)));
	    
		// form setting 
		FormPanel form = new FormPanel(); 
	    form.setWidget(layout);
	    form.setLabelWidth(60); // 모든 field 적용 후 설정한다. 
	    form.setSize("450", "360");
	    return form;
	}

	public void delete() {
		GridDeleteData<Emp03_TransModel> service = new GridDeleteData<Emp03_TransModel>();
		service.delete(grid.getStore(), editDriver.flush(), "emp.Emp03_Trans.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				hide();
			}
		});
	}
	
	public void update() {
		
		
		if(transDate.getText() == null || "".equals(transDate.getText())) {
			new SimpleMessage("발령일은 반드시 등록하여야 합니다."); 
			return; 
		}
		if( transDate.getValue().compareTo(empModel.getHireDate()) < 0) {
			new SimpleMessage("입사일 이전 발령일은 등록할 수 없습니다. "); 
			return; 
		}
		if(transCode.getText() == null || "".equals(transCode.getText())) {
			new SimpleMessage("발령구분은 반드시 등록하여야 합니다."); 
			return; 
		}
		if(orgCodeId.getText() == null || "".equals(orgCodeId.getText())) {
			new SimpleMessage("부서는 반드시 등록하여야 합니다."); 
			return; 
		}
		if(posName.getText() == null || "".equals(posName.getText())) {
			new SimpleMessage("직위는 반드시 등록하여야 합니다."); 
			return; 
		}
		if(titleName.getText() == null || "".equals(titleName.getText())) {
			new SimpleMessage("직책은 반드시 등록하여야 합니다."); 
			return; 
		}
		
		GridUpdate<Emp03_TransModel> service = new GridUpdate<Emp03_TransModel>();
		service.update(grid.getStore(), editDriver.flush(), "emp.Emp03_Trans.update", new InterfaceCallback() {
			@Override
			public void execute() {
				hide();
			}
		});
	}
}
