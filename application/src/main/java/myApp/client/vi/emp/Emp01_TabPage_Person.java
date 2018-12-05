package myApp.client.vi.emp;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceTabPage;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;
 
public class Emp01_TabPage_Person extends ContentPanel implements Editor<Emp02_EmpModel>, InterfaceTabPage, InterfaceGridOperate {

	interface EditDriver extends SimpleBeanEditorDriver<Emp02_EmpModel, Emp01_TabPage_Person> {}
	EditDriver editDriver = GWT.create(EditDriver.class);

	private Grid<Emp02_EmpModel> grid;
	
	@Path("personModel.companyId")
	LongField companyId = new LongField();//회사
	
	@Path("personModel.korName")
	TextField korName = new TextField();//한글성명
	
	@Path("personModel.engName")
	TextField engName = new TextField();//영어성명
	
	@Path("personModel.nationCode")
	TextField nationCode = new TextField();//국적코드
	
	@Path("personModel.nationName")
	ComboBoxField nationName = new ComboBoxField("NationCode", nationCode);//국적명
	
	@Path("personModel.ctzNo")
	TextField ctzNo = new TextField();//주민번호
	
	@Path("personModel.genderCode")
	TextField genderCode = new TextField();//성별코드
	
	@Path("personModel.genderName")
	ComboBoxField genderName = new ComboBoxField("GenderCode", genderCode);//성별명
	
	@Path("personModel.birthday")
	DateField birthday = new DateField();//생일

	@Path("personModel.mobileTelno")
	TextField mobileTelno = new TextField();//휴대전화번호
	
	@Path("personModel.homeTelno")
	TextField homeTelno = new TextField();//자택전화번호
	
	@Path("personModel.emgrcyTelno")
	TextField emgrcyTelno = new TextField();//회사전화번호
	
	@Path("personModel.emailAddr")
	TextField emailAddr = new TextField();//이메일
	
	@Path("personModel.zipCode")
	TextField zipCode = new TextField();//우편번호
	
	@Path("personModel.zipAddr")
	TextField zipAddr = new TextField();//간략주소
	
	@Path("personModel.addrDetail")
	TextField addrDetail = new TextField();//상세주소

	LongField personId = new LongField(); // person ID
	TextField empNo = new TextField();//사번
	DateField hireDate = new DateField();//입사일
	
	TextField empKindCode = new TextField();//사원구분
	ComboBoxField empKindName = new ComboBoxField("EmpKindCode", empKindCode);//사원구분
	

	TextField hireCode = new TextField();//입사구분코드
	ComboBoxField hireName = new ComboBoxField("HireCode", hireCode);//입사구분명
	TextField hireNote = new TextField();//입사사유

	DateField retireDate = new DateField();//퇴사일
	TextField retireCode = new TextField();//퇴사구분코드
	ComboBoxField retireName = new ComboBoxField("RetireCode", retireCode);//퇴사구분명
	TextField retireNote = new TextField();//퇴사사유
	TextArea note = new TextArea();//기타정보
	//com.sencha.gxt.widget.core.client.form.TextArea note = new com.sencha.gxt.widget.core.client.form.TextArea();//기타정보
	
	public Emp01_TabPage_Person(Grid<Emp02_EmpModel> grid){
		
		this.grid = grid ; 
		editDriver.initialize(this);
		this.setHeaderVisible(false);

	    // button bar setting
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		
		this.getButtonBar().add(searchBarBuilder.getSearchBar());
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(this.getEditor(), new MarginData(10)); 
	}

	@Override // InterfaceTabpage()
	public void init() {
		editDriver.edit(new Emp02_EmpModel());
	}

	@Override // InterfaceTabpage()
	public void retrieve(Map<String, Object> param) {
		if(param != null) { 
			Emp02_EmpModel empModel = (Emp02_EmpModel)param.get("empModel"); 
			editDriver.edit(empModel);
		}
		else {
			editDriver.initialize(this);
		}
	}
	
	private FormPanel getEditor(){
		
	    HorizontalLayoutData rowLayout = new HorizontalLayoutData(220, -1); // 컬럼크기 
	    rowLayout.setMargins(new Margins(0, 20, 5, 0)); // 컬럼간의 간격조정 
	    HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(440, -1); // 컬럼2개 병합 
	    rowLayout2.setMargins(new Margins(0, 20, 5, 0)); // 컬럼간의 간격조정 
	    
    	HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
    	row00.add(new FieldLabel(empNo, "사원번호"), rowLayout);
    	row00.add(new FieldLabel(korName, "한글성명"), rowLayout);
    	row00.add(new FieldLabel(engName, "영문성명"), rowLayout2);
    	
	    HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
	    row01.add(new FieldLabel(nationName, "국적"), rowLayout);
	    row01.add(new FieldLabel(ctzNo, "주민번호"), rowLayout);
	    row01.add(new FieldLabel(genderName, "성별"), rowLayout);
	    row01.add(new FieldLabel(birthday, "생일"), rowLayout);
	    
	    HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
	    row02.add(new FieldLabel(empKindName, "사원구분"), rowLayout);
	    row02.add(new FieldLabel(emgrcyTelno, "회사전화"), rowLayout);//회사전화번호
	    empKindName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				empKindName.retrieve(hireDate.getValue());
			}
		}); 
	    
	    
    	HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
    	row03.add(new FieldLabel(hireDate, "입사일"), rowLayout);
    	row03.add(new FieldLabel(hireName, "입사구분"), rowLayout);
    	hireName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				hireName.retrieve(hireDate.getValue());
			}
		}); 

    	row03.add(new FieldLabel(hireNote, "입사사유"), rowLayout2);
    	
	    HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
	    row04.add(new FieldLabel(retireDate, "퇴사일"), rowLayout);
    	row04.add(new FieldLabel(retireName, "퇴사구분"), rowLayout);
    	retireName.addTriggerClickHandler(new TriggerClickHandler() {
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				retireName.retrieve(retireDate.getValue());
			}
		}); 
    	
    	
    	row04.add(new FieldLabel(retireNote, "퇴사사유"), rowLayout2);
    	
    	HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
    	row05.add(new FieldLabel(mobileTelno, "핸드폰"), rowLayout);//휴대전화번호
    	row05.add(new FieldLabel(homeTelno, "집전화"), rowLayout);//자택전화번호
    	
    	row05.add(new FieldLabel(emailAddr, "이메일"), rowLayout2);//이메일
	    
	    HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
	    row06.add(new FieldLabel(zipCode, "우편번호"), rowLayout);//우편번호
    	row06.add(new FieldLabel(zipAddr, "우편주소"), new HorizontalLayoutData(690,  -1, new Margins(0, 50, 0, 0)));//우편주소
	    
    	HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
    	row07.add(new FieldLabel(addrDetail, "상세주소"), new HorizontalLayoutData(860,  -1, new Margins(0, 0, 0, 220)));
    	
    	HorizontalLayoutContainer row08 = new HorizontalLayoutContainer();
    	row08.add(new FieldLabel(note, "기타정보"), new HorizontalLayoutData(860, 50));
	    
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
	    layout.add(row08, new VerticalLayoutData(1, -1, new Margins(15, 15, 80, 15)));
	    
		// form setting 
		FormPanel form = new FormPanel(); 
		form.setWidget(layout);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
	    return form;
	}

	@Override
	public void retrieve() {
	}
	@Override
	public void insertRow() {
	}

	@Override
	public void update(){
		
		GridUpdate<Emp02_EmpModel> service = new GridUpdate<Emp02_EmpModel>(); 
		service.update(grid.getStore(), editDriver.flush(), "emp.Emp02_Emp.update", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.getStore().update(editDriver.flush());
			}
		});
	}

	@Override
	public void deleteRow() {
		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 사원정보를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					GridDeleteData<Emp02_EmpModel> service = new GridDeleteData<Emp02_EmpModel>();
					List<Emp02_EmpModel> checkedList = grid.getSelectionModel().getSelectedItems();
					service.delete(grid.getStore(), checkedList, "emp.Emp02_Emp.delete");
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