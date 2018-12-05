package myApp.client.vi.apr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.LookupTriggerField;
import myApp.client.field.MyDateField;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.DBUtil;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.JSCaller;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.PDFViewer;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.apr.model.Apr05_RelateDocModel;
import myApp.client.vi.apr.model.Apr07_AltApprModel;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;

public class Apr01_Edit_ApprDoc extends Window implements Editor<Apr01_ApprModel>, InterfaceServiceCall {
	private final Logger   logger = Logger.getLogger(this.getClass().getName());
	
	private Long stepEmpId = 0L;//대결재자
	
	public LongField   apprId           = new LongField();      //문서번호
	public TextField   title            = new TextField();      //문서제목
	public MyDateField regDate          = new MyDateField();    //상신일
	public TextField   statusCode       = new TextField();      //결재상태
	public TextField   content          = new TextField();      //문서내용
	public TextField   note             = new TextField();      //비고
	public LongField   regEmpId         = new LongField();      //등록자사번
	public TextField   regEmpInfo       = new TextField();      //등록자 필드
	public MyDateField effectDate       = new MyDateField();    //시행일
//	@Path("classTreeModel.classTreeName")
//	public TextField   classTreeName    = new TextField();      //문서분류명
	public TextField   classTreeNameAppPath	= new TextField();	//문서분류명
	public LookupTriggerField stampName = new LookupTriggerField();//인감명
	public LongField   stampId          = new LongField();      //인감ID
	public TextField   receiveName      = new TextField();      //수신
	public TextField   referenceName    = new TextField();      //참조
	
	public  TextField  inOutTypeCode	= new TextField();		//문서구분(대외문서/사내문서)
	private Radio	   radioInType		= new Radio();			//문서구분(사내문서)
	private Radio	   radioOutType		= new Radio();			//문서구분(대외문서)
	private ToggleGroup toggleInOutType = new ToggleGroup();

	private LongField  stampFileId      = new LongField();      //인감파일ID
	
	interface EditDriver extends SimpleBeanEditorDriver<Apr01_ApprModel, Apr01_Edit_ApprDoc> {
	}
	
	EditDriver editDriver = GWT.create(EditDriver.class);

	interface HTMLTemplate extends XTemplates {
		//웹에디터 HTML 설정
		@XTemplate("<iframe id='htmlTemplate' frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=auto vspace=0 src='naverEditor.jsp' width='100%' height='100%'/>")
		SafeHtml getTemplate();
	}
	
	private Apr01_ApprModel apprModel = new Apr01_ApprModel();
	private FileUpdownForm fileUpdownForm = new FileUpdownForm(); // file upload form
	private Apr01_Grid_ApprFile apprFileList = new Apr01_Grid_ApprFile();
	private Apr03_Page_RelateFund apprFundList = new Apr03_Page_RelateFund();
	private Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
	
    TextButton  apprButton   = new TextButton("결재");//결재버튼
    TextButton  rejectButton = new TextButton("반려");//반려버튼
    TextButton	pdfButton	 = new TextButton("미리보기");
    
    private InterfaceCallback callback;
    private Boolean isAdmin = false; 
    private String  tempHTML = "";
    
	public Apr01_Edit_ApprDoc() {

		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
//		this.setSize("1400", "800"); 
		this.setSize("1400", "805");
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		this.setHeading("문서상세");

		editDriver.initialize(this);

		JSCaller.setCallback(new InterfaceCallback() {
   			@Override
   			public void execute() {
   				changeEditorHeight(300); // editor 높이 맞추기
   				if(apprModel.getInOutTypeCode() != null) {
					// 문서구분이 '10:대외문서'인 경우 Text 모드 
					if ("10".equals(apprModel.getInOutTypeCode())) {
						changeMode("TEXT");	//TEXT 모드 : TEXT
		   				if(apprModel.getContent() != null) {
		   					setText(apprModel.getContent());
		   				}
		   				pdfButton.setEnabled(true);
					}
					// 문서구분이 '20:사내문서'인 경우 Editor 모드
					else if ("20".equals(apprModel.getInOutTypeCode())) {
						changeMode("WYSIWYG");	//Editor 모드 : WYSIWYG 
		   				if(apprModel.getContent() != null) {
		   					setHtmlText(apprModel.getContent());
		   				}
		   				pdfButton.setEnabled(false);
					}
   				}
   			}
   		});
		
		BorderLayoutContainer borderLayoutContainer  = new BorderLayoutContainer();
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(this.getApprovalForm(), new VerticalLayoutData(1, 1, new Margins(0, 0, 0, 0)));
		vlc.add(this.apprFileList, new VerticalLayoutData(1, 220, new Margins(4, 0, 0, 14)));
//		this.apprFileList.setBorders(true);
		
		// file upload form setting
		this.fileUpdownForm.getForm().setVisible(false);
		this.fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String actionUrl = "FileUpload?parentId=" + apprModel.getApprId();
				fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
					@Override
					public void execute() {
						apprFileList.retrieve(apprModel, true); 
					}
				});
			}
		});
		vlc.add(fileUpdownForm.getForm()); // form은 Layout에 붙어 있어야 한다. 
		
		ContentPanel cp = new ContentPanel(); 
		cp.setHeaderVisible(false);
		cp.add(vlc);
		
		BorderLayoutData westLayoutData = new BorderLayoutData(780);
		westLayoutData.setMargins(new Margins(0, 2, 0, 0));
		borderLayoutContainer.setWestWidget(cp, westLayoutData);
		borderLayoutContainer.setCenterWidget(this.apprFundList); 
		this.add(borderLayoutContainer);
		
//		apprButton.setWidth(60);
//		apprButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//			}
//		});
//		
//		rejectButton.setWidth(60);
//		rejectButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//			}
//		});
 
		pdfButton.setWidth(80);
		pdfButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				makePreviewPDF();
			}
		});

		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	public void retrieveApproval(Apr01_ApprModel apprModel, Boolean isAdmin, InterfaceCallback callBack) {
		this.callback = callBack;
		//retrieveAltApproval(apprModel);
		retrieveApproval(apprModel, isAdmin);
	}
	
	//대결자 조회
	public void retrieveAltApproval(Apr01_ApprModel apprModel) {
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.selectById");
		request.addParam("apprId", this.apprModel.getApprId());
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				retrieveApproval(apprModel, false);
			}
		});
	}
	
	public void retrieveApproval(Apr01_ApprModel apprModel, Boolean isAdmin) {
		
		this.isAdmin = isAdmin;
		this.apprModel = apprModel;
		this.classTreeModel = apprModel.getClassTreeModel();
		editDriver.edit(this.apprModel);

		// retrieve file list : editable = false 
		this.apprFileList.retrieve(this.apprModel, false);
		this.apprFundList.setStampId(apprModel.getStampId());
		this.apprFundList.setStampEmpId(apprModel.getStampModel().getEmpId());
		
//		Info.display("인감ID", ""+apprModel.getStampId());
//		Info.display("인감관리자ID", ""+apprModel.getStampModel().getEmpId());
		
		this.apprFundList.retrieve(this.apprModel, false, new InterfaceCallback() {
			@Override
			public void execute() {
				//임시저장일 경우
				if(("40".equals(apprModel.getStatusCode()) || "99".equals(apprModel.getStatusCode())) && LoginUser.getUserId() == apprModel.getRegEmpId()) {
					apprFileList.showDelete();
					makeButton();
					makeDeleteButton();
					apprFundList.openButton();
				} else {
					Grid<Apr04_ApprStepModel> apprStepGrid = apprFundList.getApprStepGrid();
					boolean flag = false;
					if(LoginUser.getUserId() != apprStepGrid.getStore().get(0).getStepEmpId()){//상신자와 로그인 사번이 다를 경우 결재체크
						flag = true;
					}
					for(int i = 1; i < apprStepGrid.getStore().size(); i++) {//상신자 제외
						if("20".equals(apprStepGrid.getStore().get(i).getStepStatusCode())) {//누구한명이라도 결재했을 경우 결재체크
							flag = true;
						}
					}
					if("40".equals(apprModel.getStatusCode()) || "30".equals(apprModel.getStatusCode())) {//결재 완료(반려)된 문서는 상신취소 불가능
						flag = true;
					}
					if(flag) {
						setReadOnly();
						myTurnCheck(false);//결재체크
					} else {
						setReadOnly();
						myTurnCheck(true);//상신취소 가능
					}
				}
				if (isAdmin) {
					makeDeleteButton();
				}
			}
		});
		
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.selectById");
		request.addParam("apprId", this.apprModel.getApprId());
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				//Apr01_ApprModel apprModel = ;
				if(result != null) {
					Apr01_ApprModel tempModel = new Apr01_ApprModel();
					for(GridDataModel data : result.getResult()) {
						tempModel = (Apr01_ApprModel) data;
					}
					getAppr(tempModel);
				} else {
				}
			}
		});
	}
	
	private void setReadOnly() {
		title.setReadOnly(true);
		effectDate.setReadOnly(true);
		stampName.setReadOnly(true);
		receiveName.setReadOnly(true);
		referenceName.setReadOnly(true);
	}
	
	private void setEditable() {
		title.setReadOnly(false);
		effectDate.setReadOnly(false);
		stampName.setReadOnly(false);
		receiveName.setReadOnly(false);
		referenceName.setReadOnly(false);
	}
	
	private void getAppr(Apr01_ApprModel apprModel) {

		if (apprModel.getTitle() != null) {
			apprModel.setTitle(apprModel.getTitle().trim());
		}

		this.apprModel = apprModel;
		editDriver.edit(this.apprModel);
		
		setRadio(inOutTypeCode.getValue());
	}
	
	private void myTurnCheck(boolean flag) {
		
		Grid<Apr04_ApprStepModel> apprStepGrid = apprFundList.getApprStepGrid();
		Boolean isReference = false; 
		Boolean myConfirm = false; // 앞서 결재가 되었는가? 
		Boolean fileYn = false;//파일 다운로드 가능,불가능
		
		List<Apr04_ApprStepModel> list = apprStepGrid.getStore().getAll();  
		
		for(int i=0; i<list.size(); i++) {
			
			Apr04_ApprStepModel apprStepModel = list.get(i);
			
			if(apprStepModel.getStepEmpId() == LoginUser.getUserId() || apprStepModel.getApprEmpId() == LoginUser.getUserId()) {
				if(apprStepModel.getStepEmpId() != apprStepModel.getApprEmpId()) {
					stepEmpId = apprStepModel.getStepEmpId();//원결재자
				} else {
					stepEmpId = LoginUser.getUserId();
				}
				//결재단계에 있으면 파일 다운로드 가능
				fileYn = true;
				// 내꺼가 대기이다.
				if("10".equals(apprStepModel.getStepStatusCode())){
					// 결재 혹은 승인 대기 중
					/*
					if("30".equals(apprStepModel.getStepTypeCode()))  { // 결재구분이 참조(30)
						isReference = true;
					}
					else { // 결재구분이 결재(10) 혹은 합의(20) 인 경우
					 */
						
						if(i==0) {
							// 내가 처음 승인자이다. 
							myConfirm = true;
						}
						else {
							// 앞 승인자 확인. 
							Apr04_ApprStepModel preStepModel = list.get(i-1);
							if("20".equals(preStepModel.getStepStatusCode())){
								// 앞 승인자가 확인완료하였음. 
								myConfirm = true;
							}
						}
					//}
				}
				break; // 다음꺼는 안본다.  
			}
		}
		
		//대결자라서 결재라인에 2군데 이상 존재할 경우
		for(int i=0; i<list.size(); i++) {
			Apr04_ApprStepModel apprStepModel = list.get(i);
			if("10".equals(apprStepModel.getStepStatusCode())){
				if(apprStepModel.getApprEmpId() != LoginUser.getUserId()) {
					break;
				} else  {
					if(apprStepModel.getApprEmpId() == LoginUser.getUserId()) {
						if(apprStepModel.getStepEmpId() != apprStepModel.getApprEmpId()) {
							stepEmpId = apprStepModel.getStepEmpId();//원결재자
						} else {
							stepEmpId = LoginUser.getUserId();
						}
						
						myConfirm = true;
						break;
					}
				}
			}
		}
		if("40".equals(this.apprModel.getStatusCode())){//반려된 문서는 결재 무조건 무시
			myConfirm = false;
		}
				
		//결재완료된 문서는 다운로드 가능
		if("30".equals(apprModel.getStatusCode())) {
			fileYn = true;
		}
		if(fileYn == false) {
			apprFileList.hideDown();
			apprFundList.hideDown();
		}
		
		/*
		if(isReference) { // 참조자 확인 버튼 등록
			TextButton confirmButton = new TextButton("확인");
			confirmButton.setWidth(60);
			confirmButton.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					Info.display("confirm", "확인");
					
					ServiceRequest request = new ServiceRequest("apr.Apr04_ApprStep.confirmUpdate");
					request.addParam("apprId", apprModel.getApprId());
					request.addParam("apprEmpId", LoginUser.getUserId());
					request.addParam("stepEmpId", stepEmpId);//원결재자
					request.addParam("apprStatusCode", "20"); // 참조완료 
					confirm(request); 
				}
			});
			this.getButtonBar().add(confirmButton);
		}
		*/
		
		if(myConfirm) { // 내가 승인자이다. 
			TextButton confirmButton = new TextButton("승인");
			confirmButton.setWidth(60);
			confirmButton.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					ServiceRequest request = new ServiceRequest("apr.Apr04_ApprStep.confirmUpdate");
					request.addParam("apprId", apprModel.getApprId());
					request.addParam("apprEmpId", LoginUser.getUserId());
					request.addParam("stepEmpId", stepEmpId);//원결재자
					request.addParam("apprStatusCode", "20"); // 승인완료
					request.addParam("advertYn", apprModel.getClassTreeModel().getAdvertYn());
					request.addParam("companyId", LoginUser.getCompanyId());
					request.addParam("stampId", apprModel.getStampId());
					request.addParam("autoApprId", 0L);//자동결재할 원결재자
					List<Apr04_ApprStepModel> tempList = apprFundList.getApprStepGrid().getStore().getAll();
					for(int i = 0; i < tempList.size(); i++) {
						if(String.valueOf(LoginUser.getUserId()).equals(String.valueOf(tempList.get(i).getApprEmpId()))) {
							request.addParam("autoApprId", tempList.get(i).getStepEmpId());
							break;
						}
					}
					confirm(request);
				}
			});
			this.getButtonBar().add(confirmButton);
			
			TextButton rejectButton = new TextButton("반려");
			rejectButton.setWidth(60);
			rejectButton.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {

					Apr01_Edit_RejectMessage rejectMessage = new Apr01_Edit_RejectMessage(); 
					rejectMessage.editMessage(new InterfaceCallbackResult() {
						@Override
						public void execute(Object result) {
							
							String msg = (String)result; 
							ServiceRequest request = new ServiceRequest("apr.Apr04_ApprStep.confirmUpdate");
							request.addParam("apprId", apprModel.getApprId());
							request.addParam("apprEmpId", LoginUser.getUserId());
							request.addParam("stepEmpId", stepEmpId);//원결재자
							request.addParam("rejectMessage", msg); // 반려메세지 
							request.addParam("apprStatusCode", "30"); // 반려처리 
							request.addParam("companyId", LoginUser.getCompanyId());
							confirm(request); 
						}
					}); 
				}
			});
			this.getButtonBar().add(rejectButton);
		}
		
//		TextButton pdfButton = new TextButton("미리보기"); 
//		pdfButton.setWidth(80);
//		pdfButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				makePreviewPDF();
//			}
//		});
//		if ("10".equals(inOutTypeCode.getValue())) {
//			pdfButton.setEnabled(true);
//		} else {
//			pdfButton.setEnabled(false);
//		}
		this.getButtonBar().add(pdfButton);

		if(flag) {
			this.getButtonBar().add(makeCancelButton());
		}
		
		TextButton closeButton = new TextButton("닫기"); // 기본 닫기버튼은 생성해야 한다. 
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		});
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show();
	}
	
	private void confirm(ServiceRequest request) {
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	private void cancel() {
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("apr.Apr04_ApprStep.cancelUpdate");
		request.addParam("apprId", apprModel.getApprId());
		request.addParam("apprEmpId", LoginUser.getUserId());
		
		service.execute(request, this);
	}

	private void deleteChk() {
		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 문서를 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					delete();
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
	
	private void delete() {
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.deleteByApprId");
		request.putLongParam("apprId", apprModel.getApprId());
		service.execute(request, this);
	}
	
	public void insertApproval(Dcr01_ClassTreeModel classTreeModel, InterfaceCallback callBack ) {
		this.callback = callBack;
		this.classTreeModel = classTreeModel;
		this.insertApproval(classTreeModel); 
	}
	
	public void insertApproval(Dcr01_ClassTreeModel classTreeModel, Boolean isAdmin, InterfaceCallback callBack ) {
		this.isAdmin = isAdmin;
		this.callback = callBack; 
		this.insertApproval(classTreeModel); 
	}
	
	private TextButton makeCancelButton() {
		TextButton cancelButton = new TextButton("상신취소");
		cancelButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				cancel();
			}
		});
		return cancelButton;
	}

	private void makeDeleteButton() {
		TextButton DeleteButton = new TextButton("삭제");
		DeleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				deleteChk();
			}
		});
		
		this.getButtonBar().add(DeleteButton);
	}

	public void makeButton() {
		TextButton uploadFileButton = new TextButton("첨부파일 등록");
		uploadFileButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				fileUpdownForm.click();
			}
		});
		this.getButtonBar().add(uploadFileButton);
		
		TextButton updateButton = new TextButton("결재요청");
		updateButton.setWidth(80);
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		});
		TextButton saveButton = new TextButton("저장");
		saveButton.setWidth(80);
		saveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				save();
			}
		});
		this.getButtonBar().add(updateButton);
		this.getButtonBar().add(saveButton);

//		TextButton pdfButton = new TextButton("미리보기"); 
//		pdfButton.setWidth(80);
//		pdfButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				makePreviewPDF();
//			}
//		});
//		if ("10".equals(inOutTypeCode.getValue())) {
//			pdfButton.setEnabled(true);
//		} else {
//			pdfButton.setEnabled(false);
//		}
		this.getButtonBar().add(pdfButton);
		
		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (callback != null) {
					callback.execute();
				}
				hide(); 
			}
		});
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.show();
	}
	
	public void insertApproval(Dcr01_ClassTreeModel classTreeModel) {
		
		this.classTreeModel = classTreeModel;

		makeButton();
		if (isAdmin) {
			makeDeleteButton();
		}

		this.apprModel = new Apr01_ApprModel(); 
		this.apprModel.setClassTreeId(classTreeModel.getClassTreeId()); 
		this.apprModel.setClassTreeModel(classTreeModel);
		this.apprModel.setEmpInfoModel(LoginUser.getEmpModel());
		this.apprModel.setRegEmpId(LoginUser.getUserId()); // 등록자ID 설정
		this.apprModel.setRegDate(LoginUser.getToday());
		//this.apprModel.setStampId(classTreeModel.getSealCode());
		this.apprModel.setStatusCode("10"); // 처음 상신등록 상태...
//		this.apprModel.setInOutTypeCode("10");
		
		DBUtil dbUtil = new DBUtil(); 
		dbUtil.setSeq(apprModel, new InterfaceCallback() {
			@Override
			public void execute() {
				//editDriver.edit(apprModel);
				apprFundList.createApprStepLine(classTreeModel, apprModel, isAdmin, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						retrieveStamp(classTreeModel.getSealCode());
					}
				}); // 결재선 생성.
			}
		});
	}

	public void retrieveStamp(Long sealCode) {
		ServiceRequest request = new ServiceRequest("apr.Apr10_Stamp.selectById");
		//request.addParam("stampId", apprModel.getStampId());
		request.addParam("stampId", sealCode);

		ServiceCall service = new ServiceCall();
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				if(result.getResult().size() > 0) {
					Apr10_StampModel stampModel = (Apr10_StampModel) result.getResult(0);
					apprModel.setStampName(stampModel.getStampName());
					apprModel.setStampId(stampModel.getStampId());
					stampId.setValue(stampModel.getStampId());
					findStampEmpId(stampModel.getAprEmpId(), stampModel.getOrgId());
				}
				editDriver.edit(apprModel);
			}
		});
	}
	
	private FormPanel getApprovalForm() {

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();

		radioOutType.setBoxLabel("대외문서(공문)");
		radioOutType.setValue(false);
		radioInType.setBoxLabel("사내문서(품의·보고)");
		radioInType.setValue(false);

		toggleInOutType.add(radioOutType);
		toggleInOutType.add(radioInType);
		toggleInOutType.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
			@Override
			public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
				ToggleGroup group = (ToggleGroup) event.getSource();
				Radio radio = (Radio) group.getValue();
				if (radio.getBoxLabel().toString().indexOf("대외") > 0) {
					if (inOutTypeCode.getValue() != null) {
						final ConfirmMessageBox msgBox = new ConfirmMessageBox("확인", "전환하면 작성된 내용만 유지되고 편집효과와 이미지등 첨부내용이 모두 사라지게 됩니다. 전환하시겠습니까?");
						msgBox.addDialogHideHandler(new DialogHideHandler() {
							@Override
							public void onDialogHide(DialogHideEvent event) {
								switch (event.getHideButton()) {
								case YES:
									inOutTypeCode.setValue("10");
									changeMode("TEXT");	//TEXT 모드 : TEXT
									pdfButton.setEnabled(true);
									break;
								case NO:
									setRadio(inOutTypeCode.getValue());
									break;
								default:
									break;
								}
							}
						});
						msgBox.setWidth(300);
						msgBox.show();
					} else {
						inOutTypeCode.setValue("10");
						changeMode("TEXT");	//TEXT 모드 : TEXT
						pdfButton.setEnabled(true);
					}
				} else {
					inOutTypeCode.setValue("20");
					changeMode("WYSIWYG");	//Editor 모드 : WYSIWYG
					pdfButton.setEnabled(false);
				}
			}
		});

		HorizontalPanel radioPanelInOutType = new HorizontalPanel();
		radioPanelInOutType.add(radioOutType);
		radioPanelInOutType.add(radioInType);
		FieldLabel radioField = new FieldLabel(radioPanelInOutType, "문서구분"); 
		radioField.setLabelWidth(54);
		row00.add(radioField, new HorizontalLayoutData(300, -1, new Margins(0)));

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		FieldLabel titleField = new FieldLabel(title, "상신제목"); 
		titleField.setLabelWidth(54);
		row01.add(titleField, new HorizontalLayoutData(660, -1, new Margins(0)));
		
		TextButton copyButton = new TextButton("불러오기");
		copyButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				loadDoc();//다른문서 불러오기
				//fileUpdownForm.click();
			}
		});
		row01.add(copyButton, new HorizontalLayoutData(96, -1, new Margins(0,0,0,6))); 
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new LabelToolItem("상신내용:"), new HorizontalLayoutData(60, -1, new Margins(0,0,0,5)));
		
		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate());
		row02.add(htmlLayoutContainer, new HorizontalLayoutData(700, 350, new Margins(0,0,0,2)));
   		
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		FieldLabel effectDateField = new FieldLabel(effectDate, "시행일자"); 
		effectDateField.setLabelWidth(54);
		row04.add(effectDateField, new HorizontalLayoutData(170, -1, new Margins(0, 0, 0, 0)));

		regDate.setReadOnly(true); 
		FieldLabel regDateField = new FieldLabel(regDate, "상신일자"); 
		regDateField.setLabelWidth(54);
		row04.add(regDateField, new HorizontalLayoutData(180, -1, new Margins(0, 5, 5, 15)));
		
		regEmpInfo.setReadOnly(true);
		FieldLabel regTextField = new FieldLabel(regEmpInfo, "등록사원"); 
		regTextField.setLabelWidth(54);
		row04.add(regTextField, new HorizontalLayoutData(400, -1, new Margins(0, 5, 5, 15)));

		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
//		classTreeName.setReadOnly(true);
//		FieldLabel docTypeNameField = new FieldLabel(classTreeName, "문서분류"); 
		classTreeNameAppPath.setReadOnly(true);
		FieldLabel docTypeNameField = new FieldLabel(classTreeNameAppPath, "문서분류"); 
		docTypeNameField.setLabelWidth(54);
		row05.add(docTypeNameField, new HorizontalLayoutData(350, -1, new Margins(10, 5, 0, 0)));
		
		stampName.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				findStamp();
			}
		});
		stampName.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.preventDefault();
			}
		});
		
		FieldLabel lookupStampField = new FieldLabel(stampName, "인감종류");
		lookupStampField.setLabelWidth(67);
		row05.add(lookupStampField, new HorizontalLayoutData(400, -1, new Margins(10, 5, 0, 0)));
		//row05.add(lookupStampInfo, new HorizontalLayoutData(100, -1, new Margins(14, 0, 0, 0)));
		
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		FieldLabel receiveNameField = new FieldLabel(receiveName, "수신"); 
		receiveNameField.setLabelWidth(54);
		row06.add(receiveNameField, new HorizontalLayoutData(350, -1, new Margins(10, 5, 0, 0)));
		FieldLabel referenceNameField = new FieldLabel(referenceName, "참조"); 
		referenceNameField.setLabelWidth(67);
		row06.add(referenceNameField, new HorizontalLayoutData(400, -1, new Margins(10, 5, 0, 0)));
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		
		layout.add(row00, new VerticalLayoutData(1, 40, new Margins(15,15,0,15)));		//문서구분
		layout.add(row01, new VerticalLayoutData(1, 40, new Margins(5,15,0,15)));		//상신제목
		layout.add(row02, new VerticalLayoutData(1, 335, new Margins(0, 5, 0, 12)));	//상신내용
//		layout.add(row03, new VerticalLayoutData(1, 350, new Margins(15)));
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(5,15,15,15)));		//시행일자
		layout.add(row05, new VerticalLayoutData(1, -1, new Margins(5,15,15,15)));		//문서분류
		layout.add(row06, new VerticalLayoutData(1, -1, new Margins(15,15,15,15)));		//수신
		
		FormPanel formPanel = new FormPanel(); 
		formPanel.setWidget(layout);
		return formPanel;
	}
	
	//문서구분 라디오버튼 SET
	private void setRadio(String value) {
		toggleInOutType.reset();
		if ("10".equals(value)) {
			radioOutType.setValue(true);
			radioInType.setValue(false);
		} else {
			radioOutType.setValue(false);
			radioInType.setValue(true);
		}
	}

    //상신하기
	private void update() {

		if(inOutTypeCode.getValue() == null || "".equals(inOutTypeCode.getValue())) {
			new SimpleMessage("문서구분을 선택하여 주십시오."); 
			return;
		}

		if(title.getText() == null || "".equals(title.getText())) {
			new SimpleMessage("제목은 반드시 입력하셔야 합니다."); 
			return;
		}

		if(effectDate.getText() == null || "".equals(effectDate.getText())) {
			new SimpleMessage("시행일은 반드시 입력하셔야 합니다."); 
			return;
		}

		if (!isAdmin) {
			if (regDate.getValue().compareTo(effectDate.getValue()) == 1) {
				new SimpleMessage("시행일자는 상신일자와 같거나 커야합니다."); 
				return; 
			}
		}
		
		if(regDate.getText() == null || "".equals(regDate.getText())) {
			new SimpleMessage("상신일은 반드시 입력하셔야 합니다."); 
			return;
		}

		String htmlText = getHtmlText();
		content.setValue(htmlText);
		if(content.getText() == null || "".equals(content.getText())) {
			new SimpleMessage("내용은 반드시 입력하셔야 합니다."); 
			return; 
		}
		
		if(inOutTypeCode.getValue() == "10") {
			if (checkStamp()) {
				new SimpleMessage("대외문서인 경우 인감선택은 필수입니다.");
				return;
			}
		}
		
		ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.update");
		request.addParam("companyId", LoginUser.getCompanyId());
		// 결재내용 Update
		Apr01_ApprModel updateApprModel =  this.editDriver.flush() ; 
		updateApprModel.setContent(getHtmlText()); // html editor의 내용을 추가로 넣는다.
		/*
		if(apprFundList.getApprStepGrid().getStore().size() <= 1) {//결재자가 없는 경우 바로 문서완료
			updateApprModel.setStatusCode("30");
		} else {
			updateApprModel.setStatusCode("10");
		}
		*/
		updateApprModel.setStatusCode("30");//완료로 세팅
		for(int i = 0; i < apprFundList.getApprStepGrid().getStore().getAll().size(); i++) {
			//"10".equals(data.getStepTypeCode()
			Apr04_ApprStepModel data = apprFundList.getApprStepGrid().getStore().get(i);
			if("10".equals(data.getStepTypeCode()) || "20".equals(data.getStepTypeCode())){
				updateApprModel.setStatusCode("10");//결재,합의가 있을 경우 상신으로 세팅
			}
		}
		request.addParam("updateApprModel", updateApprModel);

		// 연관펀드 Update 
		List<GridDataModel> relateFundList = apprFundList.getRelateFundList(); 
		request.addParam("relateFundList", relateFundList);

		// 연관문서 Update 
		List<GridDataModel> relateDocList = apprFundList.getRelateDocList(); 
		request.addParam("relateDocList", relateDocList);

		// 결재선 Update 
		List<GridDataModel> apprStepList = new ArrayList<GridDataModel>(); 
		/*
		for(Apr04_ApprStepModel data : apprFundList.getArrpStepGrid().getStore().getAll()) {
			apprStepList.add((GridDataModel)data); 
		}
		*/
		for(int i = 0; i < apprFundList.getApprStepGrid().getStore().getAll().size(); i++) {
			Apr04_ApprStepModel data = apprFundList.getApprStepGrid().getStore().get(i);
			if(i == 0) {//상신자일 경우 결재처리
				data.setStepStatusCode("20");
				data.setApprDate(new Date());
				data.setApprEmpId(LoginUser.getUserId());
				apprStepList.add((GridDataModel)data);
			} else {
				data.setApprDate(null);//결재일 삭제
				if("10".equals(data.getStepTypeCode()) || "20".equals(data.getStepTypeCode())) {
					data.setStepStatusCode("10");//결재상태 대기
				}
				apprStepList.add((GridDataModel)data);
			}
		}
		apprFundList.getApprStepGrid().getStore().getAll(); 
		request.addParam("apprStepList", apprStepList);

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private boolean checkStamp() {
		if(stampId.getValue() == null || stampId.getValue() == 0 || "".equals(stampName.getText())) {
			return true;	//인감없음
		} else {
			return false;	//인감있음
		}
	}

	private String convertHTML(String htmlTag){
		htmlTag = htmlTag.replaceAll("%", "%25");
		htmlTag = htmlTag.replaceAll("&", "%26");
		htmlTag = htmlTag.replaceAll("=", "%3D");
		htmlTag = htmlTag.replaceAll(":", "%3A");
		//htmlTag = htmlTag.replaceAll(";", "%3B");
		htmlTag = htmlTag.replaceAll("#", "%23");
		//htmlTag = htmlTag.replaceAll("[", "%5B");
		//htmlTag = htmlTag.replaceAll("]", "%5D");
		
		return htmlTag;
	}
	//저장하기
	private void save() {

		if(inOutTypeCode.getValue() == null || "".equals(inOutTypeCode.getValue())) {
			new SimpleMessage("문서구분을 선택하여 주십시오."); 
			return;
		}
		if(title.getText() == null || "".equals(title.getText())) {
			new SimpleMessage("제목은 반드시 입력하셔야 합니다."); 
			return;
		}
		if(effectDate.getText() == null || "".equals(effectDate.getText())) {
			new SimpleMessage("시행일은 반드시 입력하셔야 합니다."); 
			return;
		}
		if (!isAdmin) {
			if (regDate.getValue().compareTo(effectDate.getValue()) == 1) {
				new SimpleMessage("시행일자는 상신일자와 같거나 커야합니다."); 
				return; 
			}
		}
		if(regDate.getText() == null || "".equals(regDate.getText())) {
			new SimpleMessage("상신일은 반드시 입력하셔야 합니다."); 
			return;
		}

		String htmlText = getHtmlText();
		content.setValue(htmlText);
		if(content.getText() == null || "".equals(content.getText())) {
			new SimpleMessage("내용은 반드시 입력하셔야 합니다."); 
			return; 
		}

		if(inOutTypeCode.getValue() == "10") {
			if (checkStamp()) {
				new SimpleMessage("대외문서인 경우 인감선택은 필수입니다.");
				return;
			}
		}

		ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.update");
		
		// 결재내용 Update
		Apr01_ApprModel updateApprModel =  this.editDriver.flush() ; 
		updateApprModel.setContent(getHtmlText()); // html editor의 내용을 추가로 넣는다.
		updateApprModel.setStatusCode("99");//임시저장
		request.addParam("updateApprModel", updateApprModel);

		// 연관펀드 Update 
		List<GridDataModel> relateFundList = apprFundList.getRelateFundList(); 
		request.addParam("relateFundList", relateFundList);

		// 연관문서 Update 
		List<GridDataModel> relateDocList = apprFundList.getRelateDocList(); 
		request.addParam("relateDocList", relateDocList);

		// 결재선 Update 
		List<GridDataModel> apprStepList = new ArrayList<GridDataModel>(); 
		/*
		for(Apr04_ApprStepModel data : apprFundList.getArrpStepGrid().getStore().getAll()) {
			apprStepList.add((GridDataModel)data); 
		}
		*/
		/*
		for(int i = 0; i < apprFundList.getApprStepGrid().getStore().getAll().size(); i++) {
			Apr04_ApprStepModel data = apprFundList.getApprStepGrid().getStore().get(i);
			apprStepList.add((GridDataModel)data);
		}
		*/
		for(int i = 0; i < apprFundList.getApprStepGrid().getStore().getAll().size(); i++) {
			Apr04_ApprStepModel data = apprFundList.getApprStepGrid().getStore().get(i);
			if(i == 0) {//상신자일 경우 결재처리
				data.setApprDate(null);//결재일 삭제
				data.setStepStatusCode("10");//결재상태 대기
				apprStepList.add((GridDataModel)data);
			} else {
				data.setApprDate(null);//결재일 삭제
				if("10".equals(data.getStepTypeCode()) || "20".equals(data.getStepTypeCode())) {
					data.setStepStatusCode("10");//결재상태 대기
				}
				apprStepList.add((GridDataModel)data);
			}
		}
		apprFundList.getApprStepGrid().getStore().getAll(); 
		request.addParam("apprStepList", apprStepList);

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if("makepdf".equals(result.getMessage())){
			makePDF();
			if(this.callback != null) {
				this.callback.execute();
			}
			hide();
		} else if(result.getStatus() > 0) {
			if(this.callback != null) {
				this.callback.execute();
			}
			hide(); 
		}
	}
	
	private void findStamp() {
		Apr10_Lookup_Stamp lookupStamp = new Apr10_Lookup_Stamp();
		lookupStamp.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Apr10_StampModel stampModel = (Apr10_StampModel) result;
				stampId.setValue(stampModel.getStampId());
				stampName.setValue(stampModel.getStampName());
				stampFileId.setValue(stampModel.getStampFileId());
				
				logger.info("stampModel.getStampId()   : " + stampModel.getStampId());
				logger.info("stampModel.getStampName() : " + stampModel.getStampName());

				//결재선 재생성
				reSetApprStep(stampModel);
			}
		});
	}

	//결재선 재생성
	private void reSetApprStep(Apr10_StampModel stampModel) {
		logger.info("결재선 재생성(reSetApprStep)!!!");
		apprFundList.getRelateDocGrid().getStore().clear();
		apprFundList.createApprStepLine(classTreeModel, apprModel, isAdmin, new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				//인감책임자 세팅
				if (!checkStamp()) {
					findStampEmpId(stampModel.getAprEmpId(), stampModel.getOrgId());
				}
			}
		});
	}
	
	//인감책임자 세팅
	private void findStampEmpId(Long stampEmpId, Long stampOrgId) {
		logger.info("인감책임자 셋팅 IN => " + stampEmpId);
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("emp.Emp00_Info.selectByEmpId2");
		request.addParam("empId", stampEmpId);
		request.addParam("orgId", stampOrgId);
		
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				Emp00_InfoModel empInfoModel = new Emp00_InfoModel();
				
				if(result.getResult().size() > 0) {
					empInfoModel = (Emp00_InfoModel) result.getResult(0);
					changeStep(apprFundList.getApprStepGrid(), "20", empInfoModel, true);
				}
				apprFundList.setStampEmpId(empInfoModel.getEmpId());
//				if(stampId.getValue() == null || stampId.getValue() == 0) {
				if (checkStamp()) {
					apprFundList.setStampId(null);
				} else {
					apprFundList.setStampId(stampId.getValue());
				}
				//준법감시인 세팅
				selectComplianceOfficer(stampEmpId);
			}
		});
	}
	
	//준법감시인 세팅
	private void selectComplianceOfficer(Long stampEmpId) {
		logger.info("준법감시인 셋팅 IN");
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("emp.Emp00_Info.selectComplianceOfficer");
		request.addParam("companyId", LoginUser.getCompanyId());
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				result.getResult();
				if(result.getResult().size() > 0) {
					Emp00_InfoModel empInfoModel = (Emp00_InfoModel) result.getResult(0);
					logger.info("인감책임자 [" + stampEmpId + "] - 준법감시인 [" + empInfoModel.getEmpId() + "]");
					if(stampEmpId.equals(empInfoModel.getEmpId())) {
						logger.info("중복!!");
					} else {
						logger.info("준법감시인 Insert");
						changeStep(apprFundList.getApprStepGrid(), "20", empInfoModel, false);
					}
				}
			}
		});
	}
	
	private void makePDF() {
		String fileName = "";
		if(apprFileList.fileGrid.getStore().getAll().size() == 1) {
			fileName = apprFileList.fileGrid.getStore().get(0).getFileName();
			int i = fileName.lastIndexOf(".");
			if(i < 0) {
			} else {
				fileName = fileName.substring(0, i);
			}
		} else if(apprFileList.fileGrid.getStore().getAll().size() >= 2) {
			fileName = apprFileList.fileGrid.getStore().get(0).getFileName();
			int i = fileName.lastIndexOf(".");
			if(i < 0) {
			} else {
				fileName = fileName.substring(0, i);
			}
			fileName += " 외 " + (apprFileList.fileGrid.getStore().getAll().size()-1) + "부";
		} else {
			fileName = "";
		}
		String htmlTag = getHtmlText();
		htmlTag = convertHTML(htmlTag);
		
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("utils.pdf.PDFCreate.createPDF");
		request.addParam("keyId", apprModel.getApprId());
		request.addParam("content", htmlTag);
		request.addParam("stampId", stampId.getValue());
		request.addParam("stampFileId", stampFileId.getValue());
		request.addParam("title", title.getValue());
		request.addParam("receiveName", receiveName.getValue());
		request.addParam("referenceName", referenceName.getValue());
		request.addParam("fileName", fileName);
		request.addParam("regDate", regDate.getText());
		request.addParam("effectDate", effectDate.getText());
		request.addParam("regEmpId", apprModel.getRegEmpId());
		request.addParam("regOrgId", apprModel.getEmpInfoModel().getOrgCodeId());
		
//		Info.display("getOrgCodeId=================", "!!! : " + apprModel.getEmpInfoModel().getOrgCodeId());
		
		//request.addParam("regName", value);

		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				hide();
			}
		});
		
	}
	
	private void makePreviewPDF() {
//		if(stampId.getValue() == null || stampId.getValue() == 0 || "".equals(stampName.getText())) {
		
		if ("".equals(inOutTypeCode.getValue()) || inOutTypeCode.getValue() == null) {
			new SimpleMessage("문서구분을 선택하여 주십시오.");
			return;
		}
		
		if (checkStamp()) {
			new SimpleMessage("선택된 인감이 없습니다.");
			return;
		}
		if("30".equals(this.apprModel.getStatusCode())) {
			viewPDF();
			return;
		}
		String fileName = "";

		if(apprFileList.fileGrid.getStore().getAll().size() > 0 ) {
			fileName = apprFileList.fileGrid.getStore().get(0).getFileName();
			int i = fileName.lastIndexOf(".");
			if(i < 0) {
			} else {
				fileName = fileName.substring(0, i);
			}
			
		}
		else if (apprFundList.getRelateDocGrid().getStore().getAll().size() > 0){
			Apr05_RelateDocModel relateDocModel = apprFundList.getRelateDocGrid().getStore().get(0) ; 
			
			fileName = relateDocModel.getFileModel().getFileName() ;
			int i = fileName.lastIndexOf(".");
			if(i < 0) {
			} else {
				fileName = fileName.substring(0, i);
			}
//			Info.display("fileName", relateDocModel.getFileModel().getFileName());
		}
		else {
			fileName = ""; 
		}
		
		int fileCount = 0 ; 
		
		fileCount = apprFileList.fileGrid.getStore().getAll().size() 
				 + apprFundList.getRelateDocGrid().getStore().getAll().size();   
		
		if(fileCount >= 2) {
			fileName = fileName + " 외 " + (fileCount - 1) + "부";
		} 

		PDFViewer viewer = new PDFViewer();
		String htmlTag = getHtmlText();
		htmlTag = convertHTML(htmlTag);
		htmlTag = URL.encode(htmlTag);
		fileName = URL.encode(fileName);
		
		String url = "className=apr.Apr10_StampPDF&content=" + htmlTag;
		
		url += "&keyId=" + apprModel.getApprId();
		url += "&stampId=" + stampId.getValue();
		url += "&stampFileId=" + stampFileId.getValue();
		url += "&title=" + title.getValue();
		url += "&receiveName=" + receiveName.getValue();
		url += "&referenceName=" + referenceName.getValue();
		url += "&fileName=" + fileName;
		url += "&regDate=" + regDate.getText();
		url += "&effectDate=" + effectDate.getText();
		url += "&regEmpId=" + apprModel.getRegEmpId();
		url += "&regOrgId=" + apprModel.getEmpInfoModel().getOrgCodeId();
		
		viewer.open(url);
	}
	
	private void loadDoc() {
		Long apprId = apprModel.getApprId(); 
		Apr05_Lookup_LoadDoc editor = new Apr05_Lookup_LoadDoc(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				setHtmlText((String)result);
			}
		});
		editor.show();
	}
	
	private void viewPDF() {
		PDFViewer viewer = new PDFViewer();
		String htmlTag = getHtmlText();
		htmlTag = convertHTML(htmlTag);
		String url = "className=apr.Apr10_StampPDF";
		
		url += "&keyId=" + apprModel.getApprId();
		url += "&type=readFile";
		
		viewer.open(url);
	}
	
	private void changeStep(Grid<Apr04_ApprStepModel> apprStepGrid, String stepTypeCode, Emp00_InfoModel empInfoModel, Boolean flag) {
		int seq = 2;
		int i = 0;
		
		logger.info("결재선 rowcount 1 : " + apprStepGrid.getStore().size());
		
//		for(int i = 0; i < apprStepGrid.getStore().getAll().size(); i++) {
		for(Apr04_ApprStepModel apprStepModel : apprStepGrid.getStore().getAll()) {
//			Apr04_ApprStepModel apprStepModel = apprStepGrid.getStore().get(i);

			logger.info(i + "번째 결재선 : " + apprStepModel.getStepEmpId() + " " + apprStepModel.getEmpInfoModel().getKorName());
			logger.info(i + "change 결재자 ======> " + empInfoModel.getEmpId() + " " + empInfoModel.getKorName());

			if(empInfoModel.getEmpId().equals(apprStepModel.getStepEmpId())) {
				//new SimpleMessage("이미 결재 설정이 되어 있는 사람입니다.");
				logger.info(i + "이미 결재 설정이 되어 있는 사람입니다..." + empInfoModel.getKorName());
				return;
			}
			else {
				if(stepTypeCode.equals(apprStepModel.getStepTypeCode())){
					seq++ ; // 같은 구분이 있으면 하나씩 늘린다.  
				}
			}
			i++;
		}

		logger.info("for문 종료");

		Apr04_ApprStepModel apprStepModel = new Apr04_ApprStepModel(); 
		apprStepModel.setStepEmpId(empInfoModel.getEmpId());
		apprStepModel.setApprId(this.apprModel.getApprId());
		apprStepModel.setEmpInfoModel(empInfoModel);
		apprStepModel.setStepTypeCode(stepTypeCode);
		apprStepModel.setStepTypeName("합의");
		apprStepModel.setStepStatusCode("10");
		apprStepModel.setStepStatusName("대기");
		apprStepModel.setSeq((seq * 10) + "");
		
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("apr.Apr04_ApprStep.changeByApproval");	//겸직자 설정
		request.addParam("empId", empInfoModel.getEmpId());
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				if(result.getResult() != null) {
					if(result.getResult().size() > 0) {
						Apr07_AltApprModel altApprModel = (Apr07_AltApprModel) result.getResult(0);
						String str = "";
//						str += altApprModel.getTargetPositionName() + " ";
						str += altApprModel.getTargetTitleName() + " ";
						str += altApprModel.getTargetEmpName() + "(";
//						str += altApprModel.getAltPositionName() + " ";
						str += altApprModel.getAltTitleName() + " ";
						str += altApprModel.getAltEmpName() + ")";
						apprStepModel.setStepApprName(str);
					} else {
//						apprStepModel.setStepApprName(empInfoModel.getPositionName() + " " + empInfoModel.getKorName());
						apprStepModel.setStepApprName(empInfoModel.getTitleName() + " " + empInfoModel.getKorName());
					}
				} else {
//					apprStepModel.setStepApprName(empInfoModel.getPositionName() + " " + empInfoModel.getKorName());
					apprStepModel.setStepApprName(empInfoModel.getTitleName() + " " + empInfoModel.getKorName());
				}
				GridInsertRow<Apr04_ApprStepModel> service = new GridInsertRow<Apr04_ApprStepModel>();
				service.insertRow(apprStepGrid, apprStepModel, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						if(flag) {
							logger.info("===================결재선 생성 후 : " + apprFundList.getRelateDocGrid().getStore().size());
							//selectComplianceOfficer();
						}
					}
				});
			}
		});
		return;
	}
	
	// 에디터 내용 가져오기
	private native String getHtmlText() /*-{
		return $doc.getElementById("htmlTemplate").contentWindow.sendToServer();
	}-*/;

	// 에디터 내용 보내주기(Editor모드)
	private native void setHtmlText(String str) /*-{
    	$doc.getElementById("htmlTemplate").contentWindow.setHtmlText(str);
	}-*/;

	// 에디터 내용 보내주기(Text모드)
	private native void setText(String str) /*-{
    	$doc.getElementById("htmlTemplate").contentWindow.setText(str);
	}-*/;

	// 에디터 읽기 전용으로 설정(미적용)
	private native void readOnly(String str) /*-{
	    $doc.getElementById("htmlTemplate").contentWindow.readOnly(str);
    }-*/;
	
	// 에디터 높이 조정
	private native void changeEditorHeight(int height) /*-{
	    $doc.getElementById("htmlTemplate").contentWindow.changeHeight(height);
    }-*/;

	// 에디터 모드 변경
	// - Editor 모드 : WYSIWYG 
	// - HTML 모드 : HTMLSrc 
	// - TEXT 모드 : TEXT 
	private native void changeMode(String str) /*-{
    	$doc.getElementById("htmlTemplate").contentWindow.changeMode(str);
	}-*/;
}
