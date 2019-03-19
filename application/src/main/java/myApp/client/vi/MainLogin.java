package myApp.client.vi;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceLookupResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.cst.model.Cst01_UserModel;
import myApp.client.vi.sys.model.Sys02_UserModel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class MainLogin implements InterfaceServiceCall, InterfaceLookupResult {
	
//	private final Dialog loginDialog = new Dialog();
	private TextField firstName = new TextField();
	private PasswordField password= new PasswordField();
    private CenterLayoutContainer container = new CenterLayoutContainer();
    Viewport viewport = new Viewport();
    
	private MainLogin getThis(){
		return this;
	}

	public void open() { 

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		//----------------------------------------
		//	이미지 ADD
		//----------------------------------------
		HTML image = new HTML("<center><div><img src='img/KFIALogin.png' width='333' height='101'></center></div>"); 
		vlc.add(image, new VerticalLayoutData(300, -1, new Margins(0, 0, 10, 0)));
		
		//----------------------------------------
		//	로그인필드 ADD
		//----------------------------------------
		FieldLabel loginFieldLabel = new FieldLabel(firstName, "ID (E-Mail) ");
		loginFieldLabel.setLabelWidth(85);
		loginFieldLabel.setLabelSeparator(" ");
		firstName.setText("yiChun@k-fs.co.kr");
		vlc.add(loginFieldLabel, new VerticalLayoutData(315, -1, new Margins(0, 0, 5, 15)));

		//----------------------------------------
		//	패스워드필드 ADD
		//----------------------------------------
		FieldLabel passwdFieldLabel = new FieldLabel(password, "OTP 인증번호 ");  
		passwdFieldLabel.setLabelWidth(85);
		//passwdFieldLabel.setWidth(264);
		passwdFieldLabel.setLabelSeparator(" ");
		password.setText("1111");
		vlc.add(passwdFieldLabel, new VerticalLayoutData(315, -1, new Margins(0, 0, 5, 15)));

		//----------------------------------------
		//	로그인버튼 ADD
		//----------------------------------------
		TextButton loginButton = new TextButton("로그인"); 
		loginButton.setWidth(300);
		loginButton.setHeight(45);
		loginButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getService(); // 함수로 빼서 호출한다. 
			}
		});
		vlc.add(loginButton, new VerticalLayoutData(315, -1, new Margins(15, 0, 5, 15)));

		//----------------------------------------
		//	체크박스 | 회원가입 | 회원정보수정 ADD
		//----------------------------------------
		CheckBox timeAddCheckBox = new CheckBox();
		timeAddCheckBox.setBoxLabel("로그인 상태유지");
		timeAddCheckBox.setValue(true);

		Label memberJoin = new Label("회원가입");
		memberJoin.setStyleName("Index_Join_label");
		memberJoin.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				Lookup_JoinUser lookupWindow = new Lookup_JoinUser(getThis(), "addUser");
//				lookupWindow.addUser(); 
//				lookupWindow.show();
			}
		});
		memberJoin.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(final MouseOverEvent event) {
				Info.display("MouseOver","회원가입 MouseOver");
			}
		});

//		Label gb = new Label("|");
//		gb.setStyleName("Index_Join_label");

//		Label memberModi = new Label("회원정보수정");
//		memberModi.setStyleName("Index_Join_label");
//		memberModi.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				Info.display("onClink","회원정보수정 POPUP");
//			}
//		});
//
//		memberModi.addMouseOverHandler(new MouseOverHandler() {
//			@Override
//			public void onMouseOver(MouseOverEvent event) {
//				Info.display("MouseOver","회원정보수정 MouseOver");
//			}
//		});

		HBoxLayoutContainer hBoxLayout = new HBoxLayoutContainer(); 
		hBoxLayout.add(timeAddCheckBox, new BoxLayoutData(new Margins(0, 0, 0, 15))); 
		hBoxLayout.add(memberJoin, new BoxLayoutData(new Margins(4, 0, 0, 135))); 
//		hBoxLayout.add(gb        , new BoxLayoutData(new Margins(3, 3, 0, 3))); 
//		hBoxLayout.add(memberModi, new BoxLayoutData(new Margins(4, 0, 0, 0))); 
		vlc.add(hBoxLayout, new VerticalLayoutData(350, -1, new Margins(0, 0, 0, 0)));

		//----------------------------------------
		//	OTP매뉴얼 링크 ADD
		//----------------------------------------
//		Label optDesc  = new HTML("<font size='2'><span style='color:#E7537A'>(<b>OTP인증번호</b> App 설치 및 사용방법)</span>");
//		Label optAndroid = new HTML("<font size='2'><a href=\"/BaroOTP_Android.html\" target=\"_blank\" onclick=\"window.open(this.href,'otpWin','width=800,height=800');return false;\">▶ Android</a></font>");
//		Label optIphone = new HTML("<font size='2'><a href=\"/BaroOTP_iPhone.html\" target=\"_blank\" onclick=\"window.open(this.href,'otpWin','width=800,height=800');return false;\">▶ IOS(iPhone)</a></font>");

		Label optDesc = new Label("(OTP인증번호 APP 설치 및 사용방법)");
		optDesc.setStyleName("Otp_label");

		Label optAndroid = new Label("▶Android");
		optAndroid.setStyleName("Otp_label");
		optAndroid.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/BaroOTP_Android.html", "optwin", "enabled"); 
			}
		});

		Label optIphone  = new Label("▶IOS(iPhone)");
		optIphone.setStyleName("Otp_label");
		optIphone.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/BaroOTP_iPhone.html", "optwin", "enabled"); 
			}
		});

		HBoxLayoutContainer hBoxLayout2 = new HBoxLayoutContainer();
		hBoxLayout2.add(optDesc , new BoxLayoutData(new Margins(0, 0, 0, 0)));
		hBoxLayout2.add(optAndroid, new BoxLayoutData(new Margins(0, 0, 0, 5)));
		hBoxLayout2.add(optIphone , new BoxLayoutData(new Margins(0, 0, 0, 5)));
		vlc.add(hBoxLayout2, new VerticalLayoutData(450, -1, new Margins(15, 0, 0, 15)));

		FormPanel formPanel = new FormPanel();
    	formPanel.setBorders(false);
//		formPanel.setLabelWidth(60); // 모든 field 적용 후 설정한다.
    	formPanel.setWidth(400);
    	formPanel.setHeight(380);
		
	    formPanel.setWidget(vlc);
//	    formPanel.setBorders(true);
	    
		container.add(formPanel); //, new MarginData(30));
		viewport.add(container);

		RootPanel.get().add(viewport);
	}


	public void getService(){
		ServiceRequest request = new ServiceRequest("sys.User.getLoginInfo");
//		request.add("loginId", firstName.getText());
//		request.add("passwd", password.getText());

		ServiceCall service = new ServiceCall(); 
		service.execute(request, this);
	}
	
	private void openFrame(){
		// 일반 사용자이다. 회사 선택없이 로드인한다. 
		this.viewport.remove(container);
		
		MainFrame window = new MainFrame(); 
		viewport.add(window.getMainWindow());
		RootPanel.get().add(viewport);
	}

	@Override
	public void setLookupResult(Object result) {
		// 로그인 E-Mail 값을 돌려받는다.
		Cst01_UserModel cst01UserModel = (Cst01_UserModel)result;
		this.firstName.setValue(cst01UserModel.getEmail());
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
		if(result.getStatus() > 0){
			Cst01_UserModel user = (Cst01_UserModel) result.getResult(0); 
			LoginCstUser.setLoginUser(user); 
			openFrame(); 
		}
		else {
			new SimpleMessage("로그인 정보 확인", result.getMessage()); 
		}
	}

}

