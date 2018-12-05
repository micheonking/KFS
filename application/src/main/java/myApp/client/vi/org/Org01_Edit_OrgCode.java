package myApp.client.vi.org;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
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
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.grid.ComboBoxField;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.TreeGridDelete;
import myApp.client.service.TreeGridUpdate;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Org01_Edit_OrgCode extends Window implements Editor<Org01_CodeModel>{
	
	interface EditDriver extends SimpleBeanEditorDriver<Org01_CodeModel, Org01_Edit_OrgCode> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	
	TreeGrid<Org01_CodeModel> treeGrid ;
	Date baseDate ; // 조회조건의 기준일. 

	TextField orgCode 		= new TextField(); // 조직코드 
	DateField openDate 		= new DateField(); // 개설일
	TextField openReason 	= new TextField(); // 개설사유
	DateField closeDate 	= new DateField(); // 종료일
	TextField closeReason 	= new TextField(); // 종료사유

	@Path("orgInfoModel.parentCodeId")
	LongField parentCodeId 		= new LongField(); // 상위조직ID

	@Path("orgInfoModel.korName")
	TextField korName 		= new TextField(); // 조직한글명
	@Path("orgInfoModel.levelCode")
	TextField levelCode 	= new TextField(); // 조직레벨코드
	@Path("orgInfoModel.levelName")
	ComboBoxField levelName = new ComboBoxField("OrgLevelCode", levelCode); // 조직레벨명 & 콤보박스
	@Path("orgInfoModel.engName")
	TextField engName 		= new TextField(); // 영문명
	@Path("orgInfoModel.modDate")
	DateField modDate		= new DateField(); // 변경일
	@Path("orgInfoModel.modReason")
	TextField modReason 		= new TextField(); // 변경사유
	@Path("orgInfoModel.sortOrder")
	TextField sortOrder 	= new TextField(); // 정렬순서 
	@Path("orgInfoModel.note")
	TextField note = new TextField(); // 비고  

	InterfaceCallbackResult callback ;
	
	public void editData(TreeGrid<Org01_CodeModel> treeGrid,
			Org01_CodeModel parentModel,
			Org01_CodeModel editModel, 
			Date baseDate, 
			String actionCode, 
			InterfaceCallbackResult callback){
		
		this.treeGrid = treeGrid; 
		this.baseDate = baseDate; 
		this.callback = callback; // 저장 후에 실행한다. 
		
		this.setModal(true);
		this.setHeading("조직상세 정보");
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		String formHeight = ""; 
		
		editDriver.initialize(this);
		editDriver.edit(editModel);
		
		// 한컬럼의 크기(라벨 + 필드) & 컬럼간의 간격조정
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(200, -1, new Margins(0, 10, 0, 10)); 
		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer(); // 합치기 

		// 조직등급 콤보박스 설정. 
		levelName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				levelCode.setValue(levelName.getCode());
			}
    	}); 

		TextField parentOrgName = new TextField();
		parentOrgName.setReadOnly(true);
		row00.add(new FieldLabel(parentOrgName, "상위조직"), new HorizontalLayoutData(400, -1, new Margins(0, 0, 0, 10))); 
		layout.add(row00, new VerticalLayoutData(1, -1, new Margins(16)));
		if(parentModel != null) {
			parentOrgName.setValue(parentModel.getOrgInfoModel().getKorName());
		}
		else {
			parentOrgName.setValue("상위조직없음.");
		}

		TextButton parentLookupButton = new TextButton("상위조직변경");  
		row00.add(parentLookupButton, new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
		parentLookupButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Org01_Move_OrgCode lookupOrgCode = new Org01_Move_OrgCode(); 
				lookupOrgCode.open(baseDate, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						Org01_CodeModel parentOrgModel = (Org01_CodeModel)result;
						if(parentCodeId.getValue().equals(parentOrgModel.getCodeId())) {
							new SimpleMessage("동일한 조직을 상위조직 아래에 하위조직으로 등록할 수 없습니다. ") ; 
							return ; 
						}
						
						parentOrgName.setValue(parentOrgModel.getOrgInfoModel().getKorName());
						parentCodeId.setValue(parentOrgModel.getCodeId());
					}
				});   
			}
		}); 
		
		if("insertData".equals(actionCode)) {
			// 입력모드
			formHeight = "250";
			
			openDate.setValue(LoginUser.getToday()); // 오늘일자를 초기값으로 등록한다.(시간은 뺀다.)
			closeDate.setEnabled(false);
			closeReason.setEnabled(false);

			
			row01.add(new FieldLabel(openDate, "개설일"), rowLayout);
			row01.add(new FieldLabel(openReason, "개설사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row02.add(new FieldLabel(orgCode, "조직코드"), rowLayout);
			row02.add(new FieldLabel(korName, "조직명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row03.add(new FieldLabel(engName, "영문명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row04.add(new FieldLabel(levelName, "조직레벨"), rowLayout);
			row04.add(new FieldLabel(sortOrder, "정렬순서"), rowLayout);

			row05.add(new FieldLabel(closeDate, "종료일"), rowLayout);
			row05.add(new FieldLabel(closeReason, "종료사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row06.add(new FieldLabel(note, "비고"), new HorizontalLayoutData(1, 100, new Margins(0, 10, 0, 10)));
			
			layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row03, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row04, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row05, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row06, new VerticalLayoutData(1, -1, new Margins(16)));

 
		}
		else { 
			// 수정모드 
			formHeight = "300";
			
			orgCode.setEnabled(false);
			openDate.setEnabled(false);
			openReason.setEnabled(false);

			row01.add(new FieldLabel(modDate, "변경일"), rowLayout);
			row01.add(new FieldLabel(modReason, "변경사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row02.add(new FieldLabel(orgCode, "조직코드"), rowLayout);
			row02.add(new FieldLabel(korName, "조직명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row03.add(new FieldLabel(engName, "영문명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row04.add(new FieldLabel(levelName, "조직레벨"), rowLayout);
			row04.add(new FieldLabel(sortOrder, "정렬순서"), rowLayout);
			row05.add(new FieldLabel(openDate, "개설일"), rowLayout);
			row05.add(new FieldLabel(openReason, "개설사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row06.add(new FieldLabel(closeDate, "종료일"), rowLayout);
			row06.add(new FieldLabel(closeReason, "종료사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			row07.add(new FieldLabel(note, "비고"), new HorizontalLayoutData(1, 100, new Margins(0, 10, 0, 10)));

			// 합치기 
			layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row03, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row04, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row05, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row06, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row07, new VerticalLayoutData(1, -1, new Margins(16)));

			TextButton deleteButton = new TextButton("삭제");
			deleteButton.setWidth(60);
			deleteButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {
					delete(); 
				}
			}); 
			this.getButtonBar().add(deleteButton);
		}
		
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		form.setSize("560", formHeight);
		this.add(form);
		
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
		this.show(); 
	}
	
	public void update() {
		Org01_CodeModel orgCodeModel = editDriver.flush(); 
		
		if(orgCodeModel.getOpenDate() == null) {
			new SimpleMessage("개설일은 반드시 등록하여야 합니다");
			return ; 
		}
		if(orgCodeModel.getOpenReason() == null) {
			new SimpleMessage("개설사유는 반드시 등록하여야 합니다");
			return ; 
		}
		if(modDate.getValue() != null) {
			if(orgCodeModel.getOpenDate().compareTo(this.modDate.getValue()) > 0 ) {
				new SimpleMessage("변경일은 개설일과 동일하거나 개설일 이후 일자이어야 합니다.");
				return ;
			}
		}

		TreeGridUpdate<Org01_CodeModel> service = new TreeGridUpdate<Org01_CodeModel>();
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("baseDate", this.baseDate);
		service.update(this.treeGrid.getTreeStore(), orgCodeModel, "org.Org01_Code.update", new InterfaceCallbackResult() {
			@Override
			public void execute(Object data) {
				hide(); 
				callback.execute(orgCodeModel);
			}
		});
	}
	
	private void delete() {
		Org01_CodeModel deleteModel = editDriver.flush(); 
		Org01_CodeModel parentModel = treeGrid.getTreeStore().getParent(deleteModel); 

		TreeGridDelete<Org01_CodeModel> service = new TreeGridDelete<Org01_CodeModel>();
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("baseDate", this.baseDate);
		service.update(this.treeGrid.getTreeStore(), deleteModel, "org.Org01_Code.delete", new InterfaceCallback() {
			@Override
			public void execute() {
				hide();
				callback.execute(parentModel);
			}
		});
	}
}
