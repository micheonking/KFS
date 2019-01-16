package myApp.client.vi.cst;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.service.DBUtil;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.client.vi.hom.model.Hom02_BoardModel;
import myApp.client.vi.cst.model.Cst01_UserModel;
import myApp.client.vi.cst.model.Cst02_AccountModel;
import myApp.client.vi.hom.StartPage;

public class Cst99_Tab_Join extends Window implements Editor<Cst01_UserModel>, InterfaceServiceCall {


	interface EditDriver extends SimpleBeanEditorDriver<Cst01_UserModel, Cst99_Tab_Join>{}
	
	EditDriver editDriver = GWT.create(EditDriver.class);
	Grid<Cst01_UserModel> grid;
	Cst01_UserModel userModel = new Cst01_UserModel();
	
	LongField userId = new LongField();
	TextField email = new TextField();
	TextField userName = new TextField();
	TextField phoneNo = new TextField();
	TextField mrdRole = new TextField();
	DateField startDt = new DateField();
	Cst99_Edit_account accountPage = new Cst99_Edit_account();
	
	private String		actionName;
	private InterfaceCallbackResult callback;
	
	public void open( Cst01_UserModel userModel2,InterfaceCallbackResult callback) {
		
		editDriver.initialize(this);
		this.callback = callback;
		this.userModel = userModel2;
		this.setHeaderVisible(false);
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
//		this.setSize("1000",  "920");
		this.grid = grid;
		this.setSize("600",  "700");
		editDriver.edit(userModel);
		
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(this.getEditor(userModel2));
		
		TextButton regiButton = new TextButton("등록"); 
		regiButton.setWidth(50);
		regiButton.addSelectHandler(new SelectHandler(){
			
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		}); 
		this.getButtonBar().add(regiButton);
		
		TextButton closeButton = new TextButton("닫기"); 
		closeButton.setWidth(50);
		closeButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 
		this.getButtonBar().add(closeButton);
		
//		seq 셍성해서 userId로 계좌추가 화면에 넣어주기.
		userModel  = editDriver.flush(); //전체를 담는다.
		DBUtil dbutil = new DBUtil();
		dbutil.setSeq(userModel, new InterfaceCallback() {
			@Override
			public void execute() {
				editDriver.edit(userModel);
				accountPage.setUserId(userModel.getUserId());
			}
		});
		this.show();
	}


	private FormPanel getEditor(Cst01_UserModel userModel2) {
		
		TextButton emailCheckButton = new TextButton("중복확인"); 
		emailCheckButton.setWidth(75);
		emailCheckButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				checkEmail(); 
			}
		});
		
		Image lineBar0 = new Image(ResourceIcon.INSTANCE.verticalTitle());
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new LabelToolItem("이메일"), new HorizontalLayoutData(50,-1, new Margins(20,0,10,50)));
		row01.add(email, new HorizontalLayoutData(230,-1, new Margins(15,0,10,55)));
		
//		row01.add(new FieldLabel(email, "email"), new HorizontalLayoutData(300 ,-1, new Margins(15, 0, 0, 50)));
		
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new LabelToolItem("이름"), new HorizontalLayoutData(50,-1, new Margins(10,0,10,60)));
		row03.add(userName, new HorizontalLayoutData(230,-1, new Margins(0,0,10,55)));
		
//		row03.add(new FieldLabel(userName,"이름"), new HorizontalLayoutData(300,-1, new Margins(0,0,10,50)));
		
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		row05.add(new LabelToolItem("휴대폰 번호 "), new HorizontalLayoutData(70,-1, new Margins(35,0,15,20)));
		row05.add(phoneNo, new HorizontalLayoutData(210, -1, new Margins(30,0,0,35)));
		
		Label otpDesc = new HTML("<font size='2'>※ 개인정보보호를 위해 인증수단으로 핸드폰 OTP인증번호를 사용합니다.<br> </font>");
		Label otpDesc2 = new HTML("<font size='2'>※ 핸드폰 OTP인증 앱설치 및 사용방법을 확인하세요.<br> </font>");
		
		Label otpandroid = new HTML("<font size='2'> ▶ <a href=\"#\">Android</a>");
		otpandroid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				 Window w = new Window();
				 w.setHeading("BaroOTP 설명서 (Android Ver)");
				 w.setModal(false);
				 w.setPixelSize(800, 700);
				 w.setMaximizable(true);
//				 w.setToolTip("The GXT product page...");
				 w.setWidget(new Frame("/BaroOTP_Android.html"));
				 w.show();
				 
//				Window.open("/KFIA/BaroOTP_Android.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes");
			}
		});
		Label otpIphone = new HTML("<font size='2'> ▶ <a href=\"#\">IOS(iPhone)</a>");
		otpIphone.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window w = new Window();
				w.setHeading("BaroOTP 설명서 (iphone Ver)");
				w.setPixelSize(800, 700);
				w.setWidget(new Frame("/BaroOTP_iPhone.html"));
				w.show();
			}
		});
		
		
		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
		row07.add(otpandroid);
		row07.add(otpIphone);
		
		
		Label emailLabel = new HTML("<font size='2'>※증권사 계좌 개설시 등록된 E-Mail을 입력하여 주십시오.<br> </font>");
		Label phonLabel = new HTML("<font size='2'>※증권사 계좌 개설시 등록된 핸드폰 번호를 입력하여 주십시오.<br> </font>");
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		row04.add(accountPage, new HorizontalLayoutData(800, 500, new Margins(80,0,0,60)));
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.add(StartPage.getTextContents("계좌등록 및 회원가입"));
		layout.add(lineBar0,new VerticalLayoutData(1.2,1.2, new Margins(0, 0, 10, 45)));		//상단 이미지
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(10))); 						//제목
		layout.add(emailCheckButton, new VerticalLayoutData(250, -1, new Margins(5,0,0,315)));	//중복확인
		layout.add(emailLabel, new VerticalLayoutData(1, -1, new Margins(0,0,0,110)));			//email 라벨 
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(10))); 						//작성일
		layout.add(row05, new VerticalLayoutData(1, -1, new Margins(10))); 						//휴대폰번호
		layout.add(phonLabel, new VerticalLayoutData(1, -1, new Margins(70,0,0,110))); 			//휴대폰번호 라벨
		layout.add(otpDesc, new VerticalLayoutData(1, -1, new Margins(20,0,0,110)));		    //otp 라벨
		layout.add(otpDesc2, new VerticalLayoutData(1, -1, new Margins(20,0,0,110))); 			//otp 라벨
		layout.add(row07, new VerticalLayoutData(1, -1, new Margins(0,0,0,110))); 				//otp 라벨
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(0,0,0,0))); 				//계좌추가 grid 
		
//		form setting 입니다.
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(50); // 모든 field 적용 후 설정한다.
		form.setSize("820", "784");
		
	return form;
	}

	protected void checkEmail() {
		this.actionName = "checkEmail";
		this.userModel  = this.editDriver.flush();
		if (this.userModel.getEmail().length() == 0) {
			new SimpleMessage("입력확인","ID(E-Mail) 을(를) 입력하여 주십시오.");
			return;
		}
		ServiceRequest request = new ServiceRequest("cst.Cst99_User.checkEmail");
		request.addParam("email", this.userModel.getEmail());
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		
	}

	protected void update() {
		userModel  = editDriver.flush();
		GridUpdate<Cst02_AccountModel> service = new GridUpdate<Cst02_AccountModel>();
		service.addParam("userModel", userModel);
//		service.addParam("mgCode", userModel.get);
//		service.addParam("accNo", userModel);
//		service.addParam(accountPage.mgCodeComboBox, data);
		Info.display("",""+ accountPage.grid.getStore().get(0).getMgCode());
		
		service.update(accountPage.grid.getStore(), "cst.Cst99_User.update") ;
		hide();
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		
		if ("checkEmail".equals(this.actionName)) {
			new SimpleMessage("확인",result.getMessage());
		}
	}
	
}
