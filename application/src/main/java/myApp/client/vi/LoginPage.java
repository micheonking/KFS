package myApp.client.vi;

import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.home.MainFramePage;
import myApp.client.vi.sys.model.Sys02_UserModel;

public class LoginPage implements InterfaceServiceCall {
	
//	private final Dialog loginDialog = new Dialog();
	private TextField firstName = new TextField();
	private PasswordField password= new PasswordField();
//	private TextField otpNumber = new TextField();
	private PasswordField otpNumber = new PasswordField();
    private CenterLayoutContainer container = new CenterLayoutContainer();
    Viewport viewport = new Viewport();
     
	public void open() {
		
//		FieldLabel loginFieldLabel = new FieldLabel(firstName, "로그인ID ");
//		loginFieldLabel.setLabelWidth(85);
//		firstName.setText("");
//		firstName.addKeyPressHandler(new KeyPressHandler() {
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if(event.getCharCode() == 13) {
//					login(); 
//				}
//			}
//		}); 
//		
////		FieldLabel passwdFieldLabel = new FieldLabel(password, "패스워드 ");
////		passwdFieldLabel.setLabelWidth(60);
////		passwdFieldLabel.setWidth(264);
////		password.setText("1234");
////		password.addKeyPressHandler(new KeyPressHandler() {
////			@Override
////			public void onKeyPress(KeyPressEvent event) {
////				if(event.getCharCode() == 13) {
////					login(); 
////				}
////			}
////		}); 
//		
//		FieldLabel otpNumberFieldLabel = new FieldLabel(otpNumber, "OTP 인증번호 ");
//		otpNumberFieldLabel.setLabelWidth(85);
//		otpNumberFieldLabel.setWidth(264);
//		otpNumber.addKeyPressHandler(new KeyPressHandler() {
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if(event.getCharCode() == 13) {
//					login();
//				}
//			}
//		});
//
//		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//		HTML image = new HTML("<center><div><img src='img/Login.jpg' width='423' height='103'></center></div>"); 
//		vlc.add(image, new VerticalLayoutData(300, -1, new Margins(0, 0, 10, 0)));
//		vlc.add(loginFieldLabel, new VerticalLayoutData(280, -1, new Margins(0, 0, 5, 15)));
//		
//		HBoxLayoutContainer hBoxLayout = new HBoxLayoutContainer(); 
////		hBoxLayout.add(passwdFieldLabel, new BoxLayoutData(new Margins(0, 0, 0, 0))); 
//		hBoxLayout.add(otpNumberFieldLabel, new BoxLayoutData(new Margins(0, 0, 0, 0)));
//		
//		TextButton okButton = new TextButton("OK"); 
//		okButton.setWidth(40);
//		okButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				login(); // 함수로 빼서 호출한다. 
//			}
//		});
//
//		hBoxLayout.add(okButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));
//
////		TextButton adminButton = new TextButton("admin"); 
////		adminButton.setWidth(50);
////		adminButton.addSelectHandler(new SelectHandler() {
////			@Override
////			public void onSelect(SelectEvent event) {
				firstName.setText("admin");
				login(); // 함수로 빼서 호출한다. 
////			}
////		});
////		hBoxLayout.add(adminButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));
//		
//		vlc.add(hBoxLayout, new VerticalLayoutData(400, -1, new Margins(0, 0, 0, 15)));
//		
////		vlc.add(otpNumberFieldLabel, new VerticalLayoutData(280, -1, new Margins(5, 0, 0, 15)));
//		
////		Label loginDesc = new HTML("<font size='2'>※ Login ID는 사원번호(예:300081)를 사용 바랍니다. <br>※ 오류 발생시 담당자에게 문의 바랍니다.<br></font>");
//		Label loginDesc = new HTML("<font size='2'>※ Login ID는 사원번호(예:300081)를 사용 바랍니다.</font>");
//		vlc.add(loginDesc, new VerticalLayoutData(350, -1, new Margins(20, 0, 0, 15)));
//		
//		Label otpDesc = new HTML("<font size='2'>※ 핸드폰 OTP인증번호 App설치 및 사용방법 </font>");
//		Label otpandroid = new HTML("<font size='2'> ▶ <a href=\"#\">Android</a>");
//		otpandroid.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open("/HIIS/BaroOTP_Android.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes"); 
//			}
//		});
//		Label  otpIphone  = new HTML("<font size='2'> ▶ <a href=\"#\">IOS(iPhone)</a>");
//		otpIphone.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open("/HIIS/BaroOTP_iPhone.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes"); 
//			}
//		});
//
//		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
//		hblc.add(otpDesc	, new BoxLayoutData(new Margins(0,10,0,0)));
//		hblc.add(otpandroid	, new BoxLayoutData(new Margins(0,5,0,0)));
//		hblc.add(otpIphone	, new BoxLayoutData(new Margins(0,5,0,0)));
//		vlc.add(hblc, new VerticalLayoutData(1, -1, new Margins(0,0,0,15)));
//
//		Label browserDesc = new HTML("<font size='2'>※ 본 시스템은 크롬(Chrome)브라우즈에 최적화 되어 있습니다.<br>&nbsp;&nbsp;&nbsp;크롬(Chrome)을 내려 받아 사용하시기 바랍니다.<br></font>");
//		vlc.add(browserDesc, new VerticalLayoutData(440, -1, new Margins(0, 0, 0, 15)));
//
//		Label chromeDesc = new HTML("<font size='2'>&nbsp;&nbsp;&nbsp;▶<a href=\"#\"> CHROME 다운로드 (https://www.google.com/intl/ko_ALL/chrome/)</a></font>");
//		chromeDesc.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open("https://www.google.com/intl/ko_ALL/chrome/", "chromewin", "width=1200,height=700,menubars=0,toolbars=0,location=0,scrollbars=yes"); 
//			}
//		});
//		vlc.add(chromeDesc, new VerticalLayoutData(440, -1, new Margins(0, 0, 0, 15)));

		FormPanel formPanel = new FormPanel();
//		formPanel.setLabelWidth(60); // 모든 field 적용 후 설정한다.
//    	formPanel.setWidth(423);
    	formPanel.setWidth(500);
//    	formPanel.setHeight(300);
    	formPanel.setHeight(350);

//	    formPanel.setWidget(vlc);
	    formPanel.setBorders(false);
	    
		container.add(formPanel); //, new MarginData(30));
		viewport.add(container);

		RootPanel.get().add(viewport);
		
		firstName.focus();
		
	}

	public void login(){

//		if (otpNumber.getValue() == null) {
//			new SimpleMessage("확인", "OTP인증번호를 입력하여 주십시오.");
//			return;
//		}
//
//		// admin id & pw 관리필요. 
//		if("admin".equals(firstName.getText())) {

			LoginUser.setIsAdmin(true);

			ServiceRequest request = new ServiceRequest("sys.Sys02_User.getLoginAdminInfo");
			request.putLongParam  ("companyId", (long)2000940);	//2000940:한국펀드서비스
			request.putStringParam("otpNumber", "1111");//otpNumber.getValue());
			ServiceCall service = new ServiceCall(); 
			service.execute(request, this);
//		} 
//		else {
//			// 로그인 정보를 찾는다. 
//			ServiceRequest request = new ServiceRequest("sys.Sys02_User.getLoginInfo");
//			request.putStringParam("loginId", firstName.getText());
//			request.putStringParam("passwd", password.getText());
//			request.putStringParam("otpNumber", otpNumber.getValue());
//			ServiceCall service = new ServiceCall(); 
//			service.execute(request, this);
//		}
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		
		if (LoginUser.getIsAdmin()) {
			if(result.getStatus() == 10 ) {
				this.viewport.remove(container);
				viewport.add(new MainFramePage(), new MarginData(0, 0, 0, 0));
				RootPanel.get().add(viewport);
			} else {
				new SimpleMessage("로그인 정보 확인", result.getMessage());
			}
			return;
		} else {
			if(result.getStatus() == 10 ) { // 일반사용자 접속
				// get userModel
				Emp00_InfoModel empModel = (Emp00_InfoModel) result.getResult(0);
				LoginUser.setEmpModel(empModel); 
			}
			else if(result.getStatus() == 20) { // 회사관리자 접속
				// get userModel
				Sys02_UserModel userModel = (Sys02_UserModel) result.getResult(0); 
				LoginUser.setUserModel(userModel); 
			}
			else { // 로그인 정보를 찾을 수 없다.  
				new SimpleMessage("로그인 정보 확인", result.getMessage());
				return ; 
			}
			openFrame();		
		}
	}
	
	private void openFrame(){
		// 일반 사용자이다. 회사 선택없이 로드인한다. 
		this.viewport.remove(container);
		MainFrame window = new MainFrame(); 
		viewport.add(window.getMainWindow());
		RootPanel.get().add(viewport);
	}
}

