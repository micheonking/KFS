package myApp.client.vi.emp;

//import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.TextArea;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.LookupTriggerField;
import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.org.Org01_Lookup_OrgCode;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Emp02_Edit_Employee extends Window implements Editor<Emp02_EmpModel> {

	interface EditDriver extends SimpleBeanEditorDriver<Emp02_EmpModel, Emp02_Edit_Employee> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	
//	private	final Logger logger = Logger.getLogger(this.getClass().getName());
	private Grid<Emp02_EmpModel> grid;

	@Path("personModel.companyId") 
	LongField companyId = new LongField();
	
	@Path("personModel.personId")
	LongField personId = new LongField();
	
	@Path("personModel.korName") 
	TextField korName = new TextField();
	
	@Path("personModel.engName")
	TextField engName = new TextField();
	
	@Path("personModel.chnName")
	TextField chnName = new TextField();
	
	@Path("personModel.nationCode")
	TextField nationCode = new TextField();
	
	@Path("personModel.nationName")
	ComboBoxField nationName = new ComboBoxField("NationCode", nationCode);
	
	@Path("personModel.ctzNo")
	TextField ctzNo = new TextField();
	
	@Path("personModel.genderCode")
	TextField genderCode = new TextField();
	
	@Path("personModel.genderName")
	ComboBoxField genderName = new ComboBoxField("GenderCode", genderCode);
	
	@Path("personModel.birthday")
	MyDateField birthday = new MyDateField();
	
	@Path("personModel.mobileTelno")
	TextField mobileTelno   = new TextField();//휴대전화번호
	
	@Path("personModel.homeTelno")
	TextField homeTelno     = new TextField();//자택전화번호
	
	@Path("personModel.emgrcyTelno")
	TextField emgrcyTelno = new TextField();//회사전화번호
	
	@Path("personModel.emailAddr")
	TextField emailAddr     = new TextField();//이메일
	
	@Path("personModel.zipCode")
	TextField zipCode         = new TextField();//우편번호
	
	@Path("personModel.zipAddr")
	TextField zipAddr       = new TextField();//간략주소
	
	@Path("personModel.addrDetail")
	TextField addrDetail    = new TextField();//상세주소
	
	LongField empId = new LongField();//empId                           
	TextField empNo = new TextField();//사번
	MyDateField hireDate = new MyDateField();//입사일
	TextField hireCode = new TextField();//입사구분코드
	ComboBoxField hireName = new ComboBoxField("HireCode", hireCode);//입사구분명
	TextField hireNote = new TextField();//입사사유
	TextField empKindCode = new TextField();//사원구분
	ComboBoxField empKindName = new ComboBoxField("EmpKindCode", empKindCode);//사원구분
	TextArea note      = new TextArea();//기타정보
	
	@Path("transModel.transCode")
	TextField transCode = new TextField();//발령코드
	
	@Path("transModel.orgCodeId")
	LongField orgCodeId = new LongField();//부서
	
	@Path("transModel.posCode")
	TextField posCode = new TextField();//직위코드
	
	@Path("transModel.posName")
	ComboBoxField posName = new ComboBoxField("EmpPosCode", posCode);//직위명
	
	@Path("transModel.titleCode")
	TextField titleCode = new TextField();//직책코드
	
	@Path("transModel.titleName")
	ComboBoxField titleName = new ComboBoxField("EmpTitleCode", titleCode);//직책명

	@Path("orgInfoModel.korName")
	LookupTriggerField orgKorName = new LookupTriggerField() ;

	public Emp02_Edit_Employee(Grid<Emp02_EmpModel> grid, Emp02_EmpModel empModel) {
		
		this.setHeading("신규사원입력");
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		this.grid = grid;

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
		
		this.add(this.getEditor());
		editDriver.initialize(this);
		editDriver.edit(empModel);
	}
	
	private FormPanel getEditor() {
		
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(200, -1); // 컬럼크기 
	    rowLayout.setMargins(new Margins(0, 20, 5, 0)); // 컬럼간의 간격조정 
	    HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(400, -1); // 컬럼2개 병합 
	    rowLayout2.setMargins(new Margins(0, 20, 5, 0)); // 컬럼간의 간격조정 
	    
    	HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
    	row00.add(new FieldLabel(empNo, "사원번호"), rowLayout);
    	row00.add(new FieldLabel(korName, "한글성명"), rowLayout);
    	row00.add(new FieldLabel(engName, "영문성명"), rowLayout2);
		
		//transCd.setEmptyText("안내문");
	    HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
	    row01.add(new FieldLabel(nationName, "국적"), rowLayout);
	    nationName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				nationCode.setValue(nationName.getCode());
			}
    	}); 
	    
	    row01.add(new FieldLabel(ctzNo, "주민번호"), rowLayout);
	    row01.add(new FieldLabel(genderName, "성별"), rowLayout);
	    genderName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				genderCode.setValue(genderName.getCode());
			}
    	});
	    row01.add(new FieldLabel(birthday, "생일"), rowLayout);
    	
    	HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
    	row02.add(new FieldLabel(empKindName, "사원구분"), rowLayout);
    	empKindName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				empKindName.retrieve(hireDate.getValue());
			}
		}); 
    	row02.add(new FieldLabel(emgrcyTelno, "회사전화"), rowLayout);//회사전화번호
	    
	    HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
	    row03.add(new FieldLabel(hireDate, "입사일"), rowLayout);
    	row03.add(new FieldLabel(hireName, "입사구분"), rowLayout);
    	hireName.setEditable(false);
    	hireName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				hireName.retrieve(hireDate.getValue());
			}
		}); 

    	row03.add(new FieldLabel(hireNote, "입사사유"), rowLayout2);
    	
    	HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
	    row04.add(new FieldLabel(posName, "직위"), rowLayout);
	    posName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				posName.retrieve(hireDate.getValue());
			}
		}); 

	    row04.add(new FieldLabel(titleName, "직책"), rowLayout);
	    titleName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				titleName.retrieve(hireDate.getValue());
			}
		}); 
	    
	    row04.add(new FieldLabel(orgKorName, "부서명"), rowLayout2);
	    orgKorName.setEditable(false);
	    orgKorName.addTriggerClickHandler(new TriggerClickHandler(){
   	 		@Override
			public void onTriggerClick(TriggerClickEvent event) {
   	 			hireDate.finishEditing();
   	 		    if(hireDate.getValue() == null) {
   				    new SimpleMessage("입사일을 먼저 선택하여야 합니다."); 
   				    return;
   	 		    }
   	 		    else {
	   	 			Org01_Lookup_OrgCode orgSearch  = new Org01_Lookup_OrgCode(); 
	   	 			orgSearch.open(hireDate.getValue(), new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							Org01_CodeModel codeModel = (Org01_CodeModel)result; 
							orgKorName.setValue(codeModel.getOrgInfoModel().getKorName());
							orgCodeId.setValue(codeModel.getCodeId());
						}
	   	 			});
   	 		    }   	 			
			}
   	 	});

    	HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
    	row05.add(new FieldLabel(mobileTelno, "핸드폰"), rowLayout);//휴대전화번호
    	row05.add(new FieldLabel(homeTelno, "집전화"), rowLayout);//자택전화번호
    	row05.add(new FieldLabel(emailAddr, "이메일"), rowLayout2);//이메일
	    
	    HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
	    row06.add(new FieldLabel(zipCode, "우편번호"), rowLayout);//우편번호
    	row06.add(new FieldLabel(zipAddr, "우편주소"), new HorizontalLayoutData(580,  -1));//우편주소
	    
    	HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
    	row07.add(new FieldLabel(addrDetail, "상세주소"), new HorizontalLayoutData(780,  -1));
    	
    	HorizontalLayoutContainer row08 = new HorizontalLayoutContainer();
//    	row08.add(new FieldLabel(note, "기타정보"), new HorizontalLayoutData(910, 80, new Margins(0, 50, 0, 0)));
    	row08.add(new FieldLabel(note, "기타정보"), new HorizontalLayoutData(780, 80));
	    
	    VerticalLayoutContainer layout = new VerticalLayoutContainer(); 
	    layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);
	    
	    layout.add(row00, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row01, new VerticalLayoutData(1, -1, new Margins(15))); 
	    layout.add(row02, new VerticalLayoutData(1, -1, new Margins(30, 15, 15, 15)));
	    layout.add(row03, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row04, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row05, new VerticalLayoutData(1, -1, new Margins(30, 15, 15, 15)));
	    layout.add(row06, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row07, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row08, new VerticalLayoutData(1, -1, new Margins(30, 15, 80, 15)));
	    
		// form setting 
		FormPanel form = new FormPanel(); 
		form.setWidget(layout);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		form.setSize("820", "384");
	    return form;
	}

	public void update() {
		if(empNo.getText() == null || "".equals(empNo.getText())) {
			new SimpleMessage("사번은 반드시 등록하여야 합니다."); 
			return; 
		}
		if(korName.getText() == null || "".equals(korName.getText())) {
			new SimpleMessage("한글성명은 반드시 등록하여야 합니다."); 
			return; 
		}
		if(nationCode.getText() == null || "".equals(nationCode.getText())) {
			new SimpleMessage("국적은 반드시 등록하여야 합니다."); 
			return; 
		}
		if(ctzNo.getText() == null || "".equals(ctzNo.getText())) {
			new SimpleMessage("주민번호는 반드시 등록하여야 합니다."); 
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
		if(hireDate.getText() == null || "".equals(hireDate.getText())) {
			new SimpleMessage("입사일은 반드시 등록하여야 합니다."); 
			return; 
		}
		if(hireCode.getText() == null || "".equals(hireCode.getText())) {
			new SimpleMessage("입사구분은 반드시 등록하여야 합니다."); 
			return; 
		}
		
		GridUpdate<Emp02_EmpModel> service = new GridUpdate<Emp02_EmpModel>();
		service.update(grid.getStore(), editDriver.flush(), "emp.Emp02_Emp.update", new InterfaceCallback() {
			@Override
			public void execute() {
				hide(); 
			}
		});
	}
}
