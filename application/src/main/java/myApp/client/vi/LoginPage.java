package myApp.client.vi;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
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

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.MainFrame;
import myApp.client.vi.sys.Sys00_Admin;
import myApp.client.vi.sys.model.Sys02_UsrInfoModel;

public class LoginPage implements InterfaceServiceCall {

	private CenterLayoutContainer container = new CenterLayoutContainer();
    Viewport viewport = new Viewport();

    private TextField cmpCode = new TextField();
    private TextField loginId = new TextField();
	private PasswordField otpNumber = new PasswordField();

	public void open() {
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		HBoxLayoutContainer hBoxLayout = new HBoxLayoutContainer();
		VBoxLayoutContainer vBoxLayout = new VBoxLayoutContainer();

//		HTML image = new HTML("<center><div><img src='img/KFIALogin.png' width='423' height='103'></center></div>"); 
//		vlc.add(image, new VerticalLayoutData(300, -1, new Margins(0, 0, 30, 0)));

		FieldLabel cmpFieldLabel = new FieldLabel(cmpCode, "회사");
		cmpFieldLabel.setLabelWidth(60);
		cmpFieldLabel.setWidth(264);
		cmpCode.setText("00000");

		FieldLabel loginFieldLabel = new FieldLabel(loginId, "아이디 ");
		loginFieldLabel.setLabelWidth(60);
		loginFieldLabel.setWidth(264);
		loginId.setText("ADMIN");
		loginId.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					login(); 
				}
			}
		}); 
		
		FieldLabel otpNumberFieldLabel = new FieldLabel(otpNumber, "OTP번호 ");
		otpNumberFieldLabel.setLabelWidth(60);
		otpNumberFieldLabel.setWidth(264);
		otpNumber.setText("11");
		otpNumber.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					login();
				}
			}
		});

		TextButton okButton = new TextButton("확인"); 
		okButton.setWidth(85);
		okButton.setHeight(110);
		okButton.setBorders(true);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				login();
			}
		});

		vBoxLayout.add(cmpFieldLabel, new BoxLayoutData(new Margins(1, 0, 0, 0)));
		vBoxLayout.add(loginFieldLabel, new BoxLayoutData(new Margins(1, 0, 0, 0)));
		vBoxLayout.add(otpNumberFieldLabel, new BoxLayoutData(new Margins(1, 0, 0, 0)));
		
		hBoxLayout.add(vBoxLayout, new BoxLayoutData(new Margins(0, 0, 0, 0)));
		hBoxLayout.add(okButton, new BoxLayoutData(new Margins(0, 0, 0, 3)));

		vlc.add(hBoxLayout, new VerticalLayoutData(700, -1, new Margins(0, 0, 0, 15)));

		Label loginDesc = new HTML("<font size='2'>※ 로그인ID는 사번을 사용합니다.</font>");
		vlc.add(loginDesc, new VerticalLayoutData(350, -1, new Margins(20, 0, 0, 15)));

		Label otpDesc = new HTML("<font size='2'>※ 핸드폰 OTP인증 앱설치 및 사용방법을 확인하세요.<br> </font>");
		Label otpandroid = new HTML("<font size='2'> ▶ <a href=\"#\">Android</a>");
		otpandroid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/KFS/BaroOTP_Android.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes");
			}
		});
		Label otpIphone = new HTML("<font size='2'> ▶ <a href=\"#\">IOS(iPhone)</a>");
		otpIphone.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/KFS/BaroOTP_iPhone.html", "optwin", "width=800,height=800,menubars=0,toolbars=0,location=0,scrollbars=yes");
			}
		});

		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
		hblc.add(otpDesc, new BoxLayoutData(new Margins(0, 10, 0, 0)));
		hblc.add(otpandroid, new BoxLayoutData(new Margins(0, 5, 0, 0)));
		hblc.add(otpIphone, new BoxLayoutData(new Margins(0, 5, 0, 0)));
		vlc.add(hblc, new VerticalLayoutData(1, -1, new Margins(0, 0, 0, 15)));

		Label browserDesc = new HTML("<font size='2'>※ 본 시스템은 크롬(Chrome)브라우즈에 최적화 되어 있습니다.<br>&nbsp;&nbsp;&nbsp;크롬(Chrome)을 내려 받아 사용하시기 바랍니다.<br></font>");
		vlc.add(browserDesc, new VerticalLayoutData(440, -1, new Margins(0, 0, 0, 15)));

		Label chromeDesc = new HTML("<font size='2'>&nbsp;&nbsp;&nbsp;▶<a href=\"#\"> CHROME 다운로드 (https://www.google.com/intl/ko_ALL/chrome/)</a></font>");
		chromeDesc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("https://www.google.com/intl/ko_ALL/chrome/", "chromewin", "width=1200,height=700,menubars=0,toolbars=0,location=0,scrollbars=yes");
			}
		});
		vlc.add(chromeDesc, new VerticalLayoutData(440, -1, new Margins(0, 0, 0, 15)));

		FormPanel formPanel = new FormPanel();
		formPanel.setWidth(500);
		formPanel.setHeight(420);

		formPanel.setWidget(vlc);
		formPanel.setBorders(false);

		container.add(formPanel);
		viewport.add(container);

		RootPanel.get().add(viewport);

		loginId.focus();
	}

	public void login(){
		if (loginId.getText() == null) {
			new SimpleMessage("확인", "로그인ID를 입력하여 주십시오.");
			return;
		}
		if (otpNumber.getText() == null) {
			new SimpleMessage("확인", "OTP인증번호를 입력하여 주십시오.");
			return;
		}

		if("ADMIN".equals(loginId.getText())) {
			LoginUser.setIsAdmin(true);
		} else {
			LoginUser.setIsAdmin(false);
		}
		ServiceRequest request = new ServiceRequest("sys.Sys02_UsrInfo.getLoginInfo");
		request.putStringParam("cmpCode", cmpCode.getText());
		request.putStringParam("usrNo", loginId.getText());
		request.putStringParam("otpNumber", otpNumber.getText());
		ServiceCall service = new ServiceCall(); 
		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() > 0 ) {
			Sys02_UsrInfoModel usrModel = (Sys02_UsrInfoModel) result.getResult(0);
			LoginUser.setUsrInfoModel(usrModel);
			openFrame();
		} else {
			// 로그인 정보를 찾을 수 없다.  
			new SimpleMessage("로그인 정보 확인", result.getMessage());
			return ; 
		}
	}
	
	private void openFrame() {

		this.viewport.remove(container);

		//관리자화면 OPEN
		if (LoginUser.getIsAdmin()) {
			viewport.add(new Sys00_Admin(), new MarginData(3, 3, 6, 3));
		}
		//시스템 OPEN
		else {
			MainFrame window = new MainFrame();
			viewport.add(window.getMainWindow());
		}
		RootPanel.get().add(viewport);
	}
}

