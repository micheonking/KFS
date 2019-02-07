package myApp.client.vi;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.MainFrame;
import myApp.client.vi.cst.model.Cst01_UserModel;
import myApp.client.vi.sys.Sys00_Admin;
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
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		HBoxLayoutContainer hBoxLayout = new HBoxLayoutContainer();
		VBoxLayoutContainer vBoxLayout = new VBoxLayoutContainer();

		HTML image = new HTML("<center><div><img src='img/KFIALogin.png' width='423' height='103'></center></div>"); 
		vlc.add(image, new VerticalLayoutData(300, -1, new Margins(0, 0, 30, 0)));

		FieldLabel loginFieldLabel = new FieldLabel(firstName, "로그인ID");
		loginFieldLabel.setLabelWidth(60);
		loginFieldLabel.setWidth(264);
		firstName.setText("");
		firstName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					login(); 
				}
			}
		}); 
		
		FieldLabel otpNumberFieldLabel = new FieldLabel(otpNumber, "OTP 번호");
		otpNumberFieldLabel.setLabelWidth(60);
		otpNumberFieldLabel.setWidth(264);
		otpNumber.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					login();
				}
			}
		});

//		SafeHtml okButtonHtml = SafeHtmlUtils.fromTrustedString("<div style='background-color: #ffffff;'><font color='#dddddd' style='font-size:16px; '>OK</font></div>" );
//		SafeHtml cancelButtonHtml = SafeHtmlUtils.fromTrustedString("<div style='background-color: #1d7bbb;'><font color='#dddddd' style='font-size:16px; '>Cancel</font></div>" );
		TextButton okButton = new TextButton("확인"); 
//		TextButton okButton = new TextButton("OK"); 
//		okButton.setHTML(okButtonHtml);
		okButton.setWidth(65);
		okButton.setHeight(80);
		okButton.setBorders(true);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				firstName.setText("miCheon@k-fs.co.kr");
				login(); // 함수로 빼서 호출한다.
			}
		});

		TextButton cancelButton = new TextButton("취소");
//		cancelButton.setHTML(cancelButtonHtml);
		cancelButton.setWidth(65);
		cancelButton.setHeight(80);
		cancelButton.setBorders(true);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

				Window.Location.reload();

			}
		});

		TextButton adminButton = new TextButton("관리자"); 
		adminButton.setWidth(65);
		adminButton.setHeight(80);
		adminButton.setBorders(true);
		adminButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				firstName.setText("admin");
				otpNumber.setText("1111");
				otpNumber.setValue("1111");

				login(); // 함수로 빼서 호출한다. 
			}
		});

		TextButton imsiButton = new TextButton("miCheon@k-fs.co.kr"); 
//		imsiButton.setWidth(65);
//		imsiButton.setHeight(80);
//		imsiButton.setBorders(true);
		imsiButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				firstName.setText("miCheon@k-fs.co.kr");
				otpNumber.setText("1111");
				otpNumber.setValue("1111");

//				firstName.setText("cst");
				login(); // 함수로 빼서 호출한다. 
			}
		});

		TextButton imsiButton1 = new TextButton("Cst"); 
//		imsiButton1.setWidth(65);
//		imsiButton1.setHeight(80);
//		imsiButton1.setBorders(true);
		imsiButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				firstName.setText("cst");
				otpNumber.setText("1111");
				otpNumber.setValue("1111");

				login(); // 함수로 빼서 호출한다. 
			}
		});

		vBoxLayout.add(loginFieldLabel, new BoxLayoutData(new Margins(1, 0, 0, 0)));
		vBoxLayout.add(otpNumberFieldLabel, new BoxLayoutData(new Margins(1, 0, 0, 0)));
		
		hBoxLayout.add(vBoxLayout, new BoxLayoutData(new Margins(0, 0, 0, 6)));
		hBoxLayout.add(okButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));
		hBoxLayout.add(adminButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));
		hBoxLayout.add(cancelButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));

		vlc.add(hBoxLayout, new VerticalLayoutData(700, -1, new Margins(0, 0, 0, 15)));

		vlc.add(imsiButton, new VerticalLayoutData(1, -1, new Margins(20, 0, 0, 15)));
		vlc.add(imsiButton1, new VerticalLayoutData(1, -1, new Margins(0, 0, 0, 15)));

		Label loginDesc = new HTML("<font size='2'>※ 로그인ID는 E-mail을 사용합니다.</font>");
		vlc.add(loginDesc, new VerticalLayoutData(350, -1, new Margins(20, 0, 0, 15)));

		Label otpDesc = new HTML("<font size='2'>※ 핸드폰 OTP인증 앱설치 및 사용방법을 확인하세요.<br> </font>");
		Label otpandroid = new HTML("<font size='2'> ▶ <a href=\"#\">Android</a>");
		otpandroid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/KFIA/BaroOTP_Android.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes");
			}
		});
		Label otpIphone = new HTML("<font size='2'> ▶ <a href=\"#\">IOS(iPhone)</a>");
		otpIphone.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/KFIA/BaroOTP_iPhone.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes");
			}
		});

		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
		hblc.add(otpDesc, new BoxLayoutData(new Margins(0, 10, 0, 0)));
		hblc.add(otpandroid, new BoxLayoutData(new Margins(0, 5, 0, 0)));
		hblc.add(otpIphone, new BoxLayoutData(new Margins(0, 5, 0, 0)));
		vlc.add(hblc, new VerticalLayoutData(1, -1, new Margins(0, 0, 0, 15)));

//		Label browserDesc = new HTML("<font size='2'>※ 본 시스템은 크롬(Chrome)브라우즈에 최적화 되어 있습니다.<br>&nbsp;&nbsp;&nbsp;크롬(Chrome)을 내려 받아 사용하시기 바랍니다.<br></font>");
		Label browserDesc = new HTML("<font size='2'>※ 본 시스템은 크롬(Chrome)브라우즈에 최적화되어 있습니다.<br></font>");
		vlc.add(browserDesc, new VerticalLayoutData(440, -1, new Margins(0, 0, 0, 15)));

//		Label chromeDesc = new HTML("<font size='2'>&nbsp;&nbsp;&nbsp;▶<a href=\"#\"> CHROME 다운로드 (https://www.google.com/intl/ko_ALL/chrome/)</a></font>");
//		chromeDesc.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open("https://www.google.com/intl/ko_ALL/chrome/", "chromewin", "width=1200,height=700,menubars=0,toolbars=0,location=0,scrollbars=yes");
//			}
//		});
//		vlc.add(chromeDesc, new VerticalLayoutData(440, -1, new Margins(0, 0, 0, 15)));

		FormPanel formPanel = new FormPanel();
		formPanel.setWidth(500);
//		formPanel.setHeight(350);
		formPanel.setHeight(420);

		formPanel.setWidget(vlc);
		formPanel.setBorders(false);

		container.add(formPanel); // , new MarginData(30));
		viewport.add(container);

		RootPanel.get().add(viewport);

		firstName.focus();

	}

	public void login(){

		if (otpNumber.getValue() == null) {
			new SimpleMessage("확인", "OTP인증번호를 입력하여 주십시오.");
			return;
		}

		// 로그인 정보를 찾는다.
		if("admin".equals(firstName.getText())) {
			LoginUser.setIsAdmin(true);
			ServiceRequest request = new ServiceRequest("sys.Sys02_User.getLoginAdminInfo");
			request.putLongParam  ("companyId", (long)2000940);	//2000940:한국펀드서비스
			request.putStringParam("otpNumber", otpNumber.getValue());
			ServiceCall service = new ServiceCall(); 
			service.execute(request, this);
		} else {
			ServiceRequest request = new ServiceRequest("sys.Sys02_User.getLoginInfo");
			request.putStringParam("loginId", firstName.getText());
			request.putStringParam("passwd", password.getText());
			request.putStringParam("otpNumber", otpNumber.getValue());
			ServiceCall service = new ServiceCall(); 
			service.execute(request, this);
		}
//		ServiceRequest request = new ServiceRequest("sys.Sys02_User.getLoginInfo");
//		request.putStringParam("loginId", firstName.getText());
//		request.putStringParam("passwd", password.getText());
//		request.putStringParam("otpNumber", otpNumber.getValue());
//		ServiceCall service = new ServiceCall();
//		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {

		if (LoginUser.getIsAdmin()) {
			if(result.getStatus() == 10 ) {
				this.viewport.remove(container);
				viewport.add(new Sys00_Admin(), new MarginData(3, 3, 6, 3));
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
			else if(result.getStatus() == 30) { // 회사관리자 접속
				// get userModel
				Cst01_UserModel cstUserModel = (Cst01_UserModel) result.getResult(0); 
//				Info.display("", ""+result.getStatus());
				LoginUser.setCstUserModel(cstUserModel); 
			}
			else { // 로그인 정보를 찾을 수 없다.  
				new SimpleMessage("로그인 정보 확인", result.getMessage());
				return ; 
			}
		}
//		if (result.getStatus() == 10) { // 일반사용자 접속
//			// get userModel
//			Emp00_InfoModel empModel = (Emp00_InfoModel) result.getResult(0);
//			LoginUser.setEmpModel(empModel);
//		} else if (result.getStatus() == 20) { // 회사관리자 접속
//			// get userModel
//			Sys02_UserModel userModel = (Sys02_UserModel) result.getResult(0);
//			LoginUser.setUserModel(userModel);
//		} else { // 로그인 정보를 찾을 수 없다.
//			new SimpleMessage("로그인 정보 확인", result.getMessage());
//			return;
//		}
		openFrame();
	}
	
	private void openFrame(){
		// 일반 사용자이다. 회사 선택없이 로드인한다. 
		this.viewport.remove(container);
		MainFrame window = new MainFrame();
//		viewport.setBorders(true);
		viewport.add(window.getMainWindow());
		RootPanel.get().add(viewport);
	}
}

