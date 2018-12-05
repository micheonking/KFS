package myApp.client.vi.pln;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.ComboBoxField;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.JSCaller;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.Dcr01_Lookup_ClassTree;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.fnd.Fnd07_Lookup_IssuingEntity;
import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModel;
import myApp.client.vi.pln.model.Pln03_ResrchModel;
import myApp.client.vi.sys.Sys09_Lookup_Code;
import myApp.client.vi.sys.Sys10_Lookup_MultiFile;
import myApp.client.vi.sys.model.Sys09_CodeModel;

public class Pln03_Lookup_ResrchDetail extends Window implements Editor<Pln03_ResrchModel>, InterfaceServiceCall {

	Pln03_ResrchModel resrchModel = new Pln03_ResrchModel();
	Sys10_Lookup_MultiFile fileForm = new Sys10_Lookup_MultiFile(null, "Y", 120) ;

	interface EditDriver extends SimpleBeanEditorDriver<Pln03_ResrchModel, Pln03_Lookup_ResrchDetail> {
	}
	EditDriver editDriver = GWT.create(EditDriver.class);
	ContentPanel contentPanel  = new ContentPanel();
	
	//웹에디터 HTML 설정
	public interface HTMLTemplate extends XTemplates {
		@XTemplate("<iframe id='htmlTemplate' frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0 src='naverEditor.jsp' width='100%' height='100%'/>")
		SafeHtml getTemplate();
	}

	private String		actionName;
	private Long		resrchId;
	private String		winWidth  = "840";
    private String 		winHeight = "680";
    
    private	TextButton 	classTreeSearch		= new TextButton("찾기");
    private	TextButton	issuingEntitySearch	= new TextButton("찾기");
    private	TextButton	cfrmEmpSearch		= new TextButton("찾기");
    private	TextButton 	updateButton 		= new TextButton("저장");
    private	TextButton 	cancelButton 		= new TextButton("닫기");
    private	TextButton 	okButton        	= new TextButton("승인");
    private TextButton	apprButton			= new TextButton("승인요청");

    TextField 		regEmpNm		= new TextField();
	TextField		classTreeNm		= new TextField();
	TextField		issuingEntityNm	= new TextField();
	MyDateField		resrchStartDate	= new MyDateField();
	MyDateField		resrchCloseDate	= new MyDateField();
	TextField		cfrmEmpNm		= new TextField();
	DateField		cfrmDate		= new DateField();
	TextField		visitReason		= new TextField();
	TextField		note			= new TextField();
	TextField		targetTypeCode	= new TextField();
	ComboBoxField 	targetTypeName = new ComboBoxField("ResrchTargetTypeCode");

	private InterfaceCallbackResult callback;
	
	public void open (Long resrchId, InterfaceCallbackResult callback) {

		contentPanel.mask("Loading");
		
		this.resrchId = resrchId;
		this.callback = callback;
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.setPixelSize(Integer.parseInt(winWidth), Integer.parseInt(winHeight));
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		this.setHeading(" 리서치 일정등록");

		editDriver.initialize(this);

		JSCaller.setCallback(new InterfaceCallback() {
   			@Override
   			public void execute() {
   				retrieve();		
   			}
   		});

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(this.getEditor() , new VerticalLayoutData(1,1, new Margins(15,15,0,15)));
		this.add(vlc);
		this.setButton();
		this.show();
	}

	private void retrieve() {
		
		actionName = "retrieve";
		
		String	serviceName;
		Long 	resrchId;

		if (this.resrchId == null) {
			serviceName  = "pln.Pln03_Resrch.selectDetailInit";
			resrchId = LoginUser.getUserId();
		} else {
			serviceName  = "pln.Pln03_Resrch.selectById";
			resrchId = this.resrchId;
		}
		ServiceRequest request = new ServiceRequest(serviceName);
		request.putLongParam("resrchId", resrchId);
		request.putLongParam("orgId", LoginUser.getOrgCodeId());
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	private void update() {
		actionName = "update";
		resrchModel = editDriver.flush();
		if (preUpdate()) {
			ServiceRequest request = new ServiceRequest("pln.Pln03_Resrch.update");
			request.putModelParam("resrchModel", editDriver.flush());
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
	}

	private boolean preUpdate() {

		//조사기간(Start) 입력확인
		if (resrchModel.getResrchStartDate() == null) {
			new SimpleMessage("확인", "조사시작일자를 선택하여 주십시오.");
			return false;
		}
		//조사기간(Close) 입력확인
		if (resrchModel.getResrchCloseDate() == null) {
			new SimpleMessage("확인", "조사종료일자를 선택하여 주십시오.");
			return false;
		}
		//문서구분 입력확인
		if (resrchModel.getClassTreeId() == null) {
			new SimpleMessage("확인", "문서구분을 선택하여 주십시오.");
			return false;
		}
		//조사대상 입력확인
		if (targetTypeName.getCode() == null) {
			new SimpleMessage("확인", "조사대상구분을 선택하여 조십시오.");
			return false;
		} else {
			if (!targetTypeName.getCode().equals("30")) {
				if (resrchModel.getIssuingEntityId() == null) {
					new SimpleMessage("확인", "조사대상을 선택하여 주십시오.");
					return false;
				}
			}
		}

		//확인자 입력확인
		if (resrchModel.getCfrmEmpId() == null) {
			new SimpleMessage("확인", "확인자를 선택하여 주십시오.");
			return false;
		}

		//개요SET
		String htmlText = getHtmlText();
		visitReason.setValue(htmlText);

		return true;
	}

	private void appr_check() {
		
		resrchModel = editDriver.flush();
		String procType;
		String msg;

		//등록자와 로그인ID가 같은지 확인
		Long regEmpId = resrchModel.getRegEmpId();
		if (regEmpId == LoginUser.getUserId()) {
			if (resrchModel.getCloseDate() != null) {
				procType = "cancel";
				msg		 = "요청취소를 하시겠습니까?";
			} else {
				procType = "okey";
				msg		 = "승인요청을 하시겠습니까?";
			}
			final ConfirmMessageBox msgBox = new ConfirmMessageBox("확인", msg);
			msgBox.addDialogHideHandler(new DialogHideHandler() {
				@Override
				public void onDialogHide(DialogHideEvent event) {
					switch (event.getHideButton()) {
					case YES:
						appr(procType);
						break;
					case NO:
					default:
						break;
					}
				}
			});
			msgBox.setWidth(300);
			msgBox.show();
		} else {
			new SimpleMessage("확인", "권한이 없습니다.");
			return;
		}
	}

	private void confirm_check() {

		resrchModel = editDriver.flush();

		//확인자와 로그인ID가 같은지 확인
		Long cfrmEmpId = resrchModel.getCfrmEmpId();
		if (cfrmEmpId == LoginUser.getUserId()) {
			if (resrchModel.getCfrmDate() == null) {
				final ConfirmMessageBox msgBox = new ConfirmMessageBox("확인", "승인처리를 하시겠습니까?");
				msgBox.addDialogHideHandler(new DialogHideHandler() {
					@Override
					public void onDialogHide(DialogHideEvent event) {
						switch (event.getHideButton()) {
						case YES:
							confirm("okey");
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
		} else {
			new SimpleMessage("확인", "권한이 없습니다.");
			return;
		}
	}
	
	private void appr(String Gubun) {
		actionName = "appr";

		ServiceRequest request = new ServiceRequest("pln.Pln03_Resrch.closeUpdate");
		request.putLongParam("resrchId" , resrchModel.getResrchId());
		if (Gubun.equals("okey")) {
			resrchModel.setCloseDate(LoginUser.getToday());
			resrchModel.setCloseYn("true");
		} else {
			resrchModel.setCloseDate(null);
			resrchModel.setCloseYn("false");
		}
		request.putDateParam("closeDate", resrchModel.getCloseDate());
		request.putStringParam("closeYn", resrchModel.getCloseYn());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	
	private void confirm(String Gubun) {

		actionName = "confirm";

		ServiceRequest request = new ServiceRequest("pln.Pln03_Resrch.apprUpdate");
		request.putLongParam("resrchId", resrchModel.getResrchId());
		request.putLongParam("cfrmEmpId", resrchModel.getCfrmEmpId());
		if (Gubun.equals("okey")) {
			resrchModel.setCfrmDate(LoginUser.getToday());
//		} else {
//			resrchModel.setCfrmDate(null);
//		}
		request.putDateParam("cfrmDate", resrchModel.getCfrmDate());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		}
	}

	@Override
	public void getServiceResult(ServiceResult result) {

		if (actionName.equals("retrieve")) {
			resrchModel = (Pln03_ResrchModel)result.getResult(0);
			if (resrchModel.getVisitReason() != null) {
				setHtmlText(resrchModel.getVisitReason());
				if (!resrchModel.getRegEmpId().equals(LoginUser.getUserId())) {
					readOnly(resrchModel.getVisitReason());
				}
			}
			changeEditorHeight(200);
			editDriver.edit(resrchModel);
			apprButtonEnabled();
//			if (resrchModel.getCloseDate() == null) {
//				apprButton.setText("승인요청");
//			} else {
//				apprButton.setText("요청취소");
//			}
			//조사대상이 '30:시장조사'인 경우 팝업창 숨기기
			if (resrchModel.getTargetTypeCode().equals("30")) {
				issuingEntityNm.setVisible(false);
				issuingEntitySearch.setVisible(false);
			} else {
				issuingEntityNm.setVisible(true);
				issuingEntitySearch.setVisible(true);
			}
			setEnabled();
			contentPanel.unmask();

		} else if (actionName.equals("update")) {
			callback.execute(null);
			this.hide();

		}  else if (actionName.equals("appr")) {
			if (result.getStatus() == 1) {
				apprButtonEnabled();
//				if (resrchModel.getCloseDate() == null) {
//					apprButton.setText("승인요청");
//				} else {
//					apprButton.setText("요청취소");
//				}
			}

		}  else if (actionName.equals("confirm")) {
			if (result.getStatus() == 1) {
				cfrmDate.setValue(resrchModel.getCfrmDate());
//				if (resrchModel.getCfrmDate() == null) {
//					okButton.setText("승인");
//					okButton.setWidth(50);
//				} else {
//					okButton.setText("승인취소");
//					okButton.setWidth(80);
//				}
				okButton.setEnabled(false);
			}
		}
	}

	private void setEnabled() {
		if (resrchModel.getRegEmpId() == LoginUser.getUserId()) {
			if (resrchModel.getCfrmDate() == null) {	//등록자이면서 승인전인 경우..
				resrchStartDate.setReadOnly(false);
				resrchCloseDate.setReadOnly(false);
				note.setReadOnly(false);
				targetTypeName.setReadOnly(false);
				classTreeSearch.setEnabled(true);
				issuingEntitySearch.setEnabled(true);
				cfrmEmpSearch.setEnabled(true);
				updateButton.setEnabled(true);
				fileForm.retrieve(resrchModel.getResrchId());
				apprButtonEnabled();
				okButtonEnabled();
				return;
			}
		}

		apprButtonEnabled();
		okButtonEnabled();
		resrchStartDate.setReadOnly(true);
		resrchCloseDate.setReadOnly(true);
		note.setReadOnly(true);
		targetTypeName.setReadOnly(true);
		classTreeSearch.setEnabled(false);
		issuingEntitySearch.setEnabled(false);
		cfrmEmpSearch.setEnabled(false);
		updateButton.setEnabled(false);
		fileForm.changeViewLayout();
		fileForm.retrieve(resrchModel.getResrchId());
	}

	private void apprButtonEnabled() {

		if (resrchModel.getCloseDate() == null) {
			apprButton.setText("승인요청");
		} else {
			apprButton.setText("요청취소");
		}

		if (resrchModel.getRegEmpId() == LoginUser.getUserId()) {
			if (resrchModel.getCfrmDate() == null) {
				apprButton.setEnabled(true);
				return;
			}
		}
		apprButton.setEnabled(false);
	}

	private void okButtonEnabled() {
		if (resrchModel.getCfrmEmpId() == LoginUser.getUserId()) {
			if ((resrchModel.getCfrmDate() == null) && (resrchModel.getCloseDate() != null)) {
				okButton.setEnabled(true);
				return;
			}
		}
		okButton.setEnabled(false);
	}

	private ContentPanel getEditor() {

		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setSize(winWidth, winHeight);
		
		HorizontalLayoutData termLayout  = new HorizontalLayoutData( 50,  -1, new Margins(0,5,5,5));	//여백Size
		HorizontalLayoutData labelLayout = new HorizontalLayoutData( 90,  -1, new Margins(2,0,0,0));	//라벨Size
		HorizontalLayoutData rowLayout1  = new HorizontalLayoutData(200,  -1, new Margins(0,0,5,0));	//컬럼하나Size
		HorizontalLayoutData htmlLayout  = new HorizontalLayoutData(700, 360, new Margins(0,0,5,0));	//HTML Editor

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		
		//등록자
		row01.add(new LabelToolItem("등록자 : "), labelLayout);
		row01.add(regEmpNm, rowLayout1);
		regEmpNm.setReadOnly(true);

		row01.add(new LabelToolItem(), termLayout);	//항목사이 여백주기
		row01.add(new LabelToolItem(), termLayout);	//항목사이 여백주기
		
		//조사기간
		row01.add(new LabelToolItem("조사기간 : "), labelLayout);
		HBoxLayoutContainer dateBox = new HBoxLayoutContainer();
		resrchStartDate.setWidth(100);
		resrchCloseDate.setWidth(100);
		dateBox.add(resrchStartDate, new BoxLayoutData(new Margins(0,5,0,0)));
		dateBox.add(new LabelToolItem(" ~ "));
		dateBox.add(resrchCloseDate, new BoxLayoutData(new Margins(0,0,0,5)));
		row01.add(dateBox, new HorizontalLayoutData(250,  -1, new Margins(0,0,5,0)));
		
		//문서구분
		row02.add(new LabelToolItem("문서구분 : "), labelLayout);
		row02.add(classTreeNm, rowLayout1);
		classTreeNm.setReadOnly(true);
		
		classTreeSearch.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Dcr01_Lookup_ClassTree lookupDcr = new Dcr01_Lookup_ClassTree();
				lookupDcr.open("resrch", new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						Dcr01_ClassTreeModel dcrModel = (Dcr01_ClassTreeModel)result;
						resrchModel.setClassTreeId(dcrModel.getClassTreeId());
						resrchModel.setClassTreeNm(dcrModel.getClassTreeName());
						classTreeNm.setValue(resrchModel.getClassTreeNm());
					}
				});
			}
		});
		row02.add(classTreeSearch, termLayout);

		row02.add(new LabelToolItem(), termLayout);	//항목사이 여백주기

		//조사대상
		row02.add(new LabelToolItem("조사대상 : "), labelLayout);
		row02.add(targetTypeName, new HorizontalLayoutData(105, -1, new Margins(0,5,5,0)));
		targetTypeName.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
				if (targetTypeName.getCode().equals("30")) {
					issuingEntityNm.setVisible(false);
					issuingEntitySearch.setVisible(false);
				} else {
					issuingEntityNm.setVisible(true);
					issuingEntitySearch.setVisible(true);
				}
				targetTypeCode.setValue(targetTypeName.getCode());
				resrchModel.setIssuingEntityId(null);
				resrchModel.setIssuingEntityNm(null);
				issuingEntityNm.setValue(null);
			}
		});
		
		row02.add(issuingEntityNm, new HorizontalLayoutData(160, -1, new Margins(0,0,5,0)));
		issuingEntityNm.setReadOnly(true);

		issuingEntitySearch.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (targetTypeName.getCode() == null) {
					new SimpleMessage("확인", "조사대상구분 선택 후 찾기버튼을 클릭하여 주십시오.");
					return;
				}

				if (targetTypeName.getCode().equals("10")) {	//10:종목(발행처)
					Fnd07_Lookup_IssuingEntity lookupIssuingEntity = new Fnd07_Lookup_IssuingEntity();
					lookupIssuingEntity.open(new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							Fnd07_IssuingEntityModel issuingEntityModel = (Fnd07_IssuingEntityModel)result;
							resrchModel.setIssuingEntityId(issuingEntityModel.getIssuingEntityId());
							resrchModel.setIssuingEntityNm(issuingEntityModel.getIssuingEntityName());
							issuingEntityNm.setValue(resrchModel.getIssuingEntityNm());
						}
					});
				}
				else if (targetTypeName.getCode().equals("20")) {	//20:업종
					Sys09_Lookup_Code lookupCode = new Sys09_Lookup_Code();
					lookupCode.open("SectorTypeCode", new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							Sys09_CodeModel codeModel = (Sys09_CodeModel)result;
							resrchModel.setIssuingEntityId(codeModel.getCodeId());
							resrchModel.setIssuingEntityNm(codeModel.getName());
							issuingEntityNm.setValue(resrchModel.getIssuingEntityNm());
						}
					});
				}
			}
		});
		row02.add(issuingEntitySearch, termLayout);

		//개요(Html Editor)
		row03.add(new LabelToolItem("개요 : "), labelLayout);
		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate());
		row03.add(htmlLayoutContainer, htmlLayout);
		
		//비고
		row04.add(new LabelToolItem("비고 : "), labelLayout);
		row04.add(note, new HorizontalLayoutData(700, -1, new Margins(0,0,5,0)));

		//첨부파일
		row05.add(new LabelToolItem("첨부파일 : "), labelLayout);
		FieldSet filefieldSet = new FieldSet();
		filefieldSet.setCollapsible(false);
		filefieldSet.add(fileForm);
		row05.add(filefieldSet, new HorizontalLayoutData(700, -1));

		//확인자
		row06.add(new LabelToolItem("확인자 : "), labelLayout);
		row06.add(cfrmEmpNm, rowLayout1);
		cfrmEmpNm.setReadOnly(true);

		cfrmEmpSearch.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				actionName = "cfrm";
				findEmp();
			}
		});
		row06.add(cfrmEmpSearch, termLayout);
		
		row06.add(new LabelToolItem(), termLayout);	//항목사이 여백주기
		
		//확인완료일
		row06.add(new LabelToolItem("확인완료일자 : "), labelLayout);
		row06.add(cfrmDate, new HorizontalLayoutData(100, -1, new Margins(0,0,5,0)));
		cfrmDate.setReadOnly(true);

		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.add(row01, new VerticalLayoutData(1,  32, new Margins(0,0,0,0)));
		layout.add(row02, new VerticalLayoutData(1,  32, new Margins(0,0,0,0)));
		layout.add(row03, new VerticalLayoutData(1, 262, new Margins(0,0,0,0)));
		layout.add(row04, new VerticalLayoutData(1,  30, new Margins(0,0,0,0)));
		layout.add(row05, new VerticalLayoutData(1, 190, new Margins(0,0,0,0)));
		layout.add(row06, new VerticalLayoutData(1,  32, new Margins(0,0,0,0)));

		contentPanel.setWidget(layout);
		return contentPanel;
	}

	private void setButton() {
		//저장 버튼ADD
		updateButton.setWidth(50);
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		});
		this.addButton(updateButton);

		//승인요청 버튼ADD
		apprButton.setWidth(80);
		apprButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				appr_check();
			}
		});
		this.addButton(apprButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);

		//승인 버튼ADD
		okButton.setWidth(50);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				confirm_check();
			}
		});
		this.addButton(okButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);

		//닫기 버튼ADD
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				callback.execute(this);
				hide();
			}
		});
		this.addButton(cancelButton);
	}

	private void findEmp() {
		Emp00_Lookup_EmpInfo lookupEmp = new Emp00_Lookup_EmpInfo();
		lookupEmp.open(LoginUser.getToday(), new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Emp00_InfoModel empInfoModel = (Emp00_InfoModel)result;
				resrchModel.setCfrmEmpId(empInfoModel.getEmpId());
				//resrchModel.setCfrmEmpNm(empInfoModel.getKorName() + " " + empInfoModel.getPositionName() + "(" + empInfoModel.getOrgName() + ")");
				resrchModel.setCfrmEmpNm(empInfoModel.getKorName() + " (" + empInfoModel.getOrgName() + ")");
				cfrmEmpNm.setValue(resrchModel.getCfrmEmpNm());
				okButtonEnabled();
			}
		});
	}

	// 에디터 내용 가져오기
	private native String getHtmlText() /*-{
		return $doc.getElementById("htmlTemplate").contentWindow.sendToServer();
	}-*/;

	// 에디터 내용 보내주기
	private native void setHtmlText(String str) /*-{
    		$doc.getElementById("htmlTemplate").contentWindow.setHtmlText(str);
	}-*/;
	
	// 에디터 읽기 전용으로 설정
	private native void readOnly(String str) /*-{
	        $doc.getElementById("htmlTemplate").contentWindow.readOnly(str);
    }-*/;

	// 에디터 높이 조정
	private native void changeEditorHeight(int height) /*-{
	        $doc.getElementById("htmlTemplate").contentWindow.changeHeight(height);
    }-*/;

}
