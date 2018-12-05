package myApp.client.vi.emp;

import java.util.List;
import java.util.logging.Logger;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp01_PersonModel;

public class Emp10_Lookup_MailRetrieve extends Window {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private InterfaceCallbackResult callbackResult;

	private TextButton checkButton = new TextButton("검사");
	private TextButton confirmButton = new TextButton("저장");
	private TextButton closeButton = new TextButton("닫기");
	private TextField mailAddress = new TextField();
	private PasswordField password = new PasswordField();

	public Emp10_Lookup_MailRetrieve() {
		init();
	}

	public void open(InterfaceCallbackResult callbackResult) {
		this.callbackResult = callbackResult;
		this.show();
	}

	private void init() {
		makeLayout();
		makeButtonEvent();
		retrieve();
	}

	private void makeLayout() {
		this.setModal(true);
		this.setHeading("본인메일확인");
		this.setResizable(false);
		this.setPixelSize(380, 200);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		
		FieldLabel mailAddressField = new FieldLabel(mailAddress, "메일주소");
		mailAddressField.setLabelWidth(60);
		row01.add(mailAddressField);
		
		FieldLabel passwordField = new FieldLabel(password, "비밀번호");
		passwordField.setLabelWidth(60);
		row02.add(passwordField);

		
		
		//vlc.add(row01, new VerticalLayoutData(1, 1, new Margins(0, 0, 0, 0)));
		//vlc.add(row02, new VerticalLayoutData(1, 1, new Margins(0, 0, 0, 0)));

		//this.add(vlc, new VerticalLayoutData(1, 1, new Margins(0, 0, 0, 0)));
		
		vlc.add(mailAddressField, new VerticalLayoutData(1, -1, new Margins(10, 10, 0, 10)));
		vlc.add(passwordField, new VerticalLayoutData(1, -1, new Margins(5, 10, 0, 10)));
		
		this.add(vlc);
		
		//this.getButtonBar().add(checkButton);
		this.getButtonBar().add(confirmButton);
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	private void makeButtonEvent() {
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		confirmButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				ServiceCall service = new ServiceCall();
				ServiceRequest request = new ServiceRequest("emp.Emp01_Person.changeMail");
				request.addParam("empId", LoginUser.getUserId());
				request.addParam("mailAddress", mailAddress.getText());
				request.addParam("password", password.getText());
				service.execute(request, new InterfaceServiceCall() {
					@Override
					public void getServiceResult(ServiceResult result) {
						hide();
					}
				});	
			}
		});
		checkButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				checkMail();
			}
		});
	}

	private void retrieve() {
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("emp.Emp01_Person.selectByEmpId");
		request.addParam("empId", LoginUser.getUserId());
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				Emp01_PersonModel tempModel = new Emp01_PersonModel();
				List<GridDataModel> tempList = result.getResult();
				for(int i = 0; i < tempList.size(); i++) {
					 tempModel = (Emp01_PersonModel) tempList.get(0);
				}
				mailAddress.setValue(tempModel.getEmailAddr());
				password.setValue(tempModel.getPassword());
			}
		});	
	}
	
	private void checkMail() {
		ServiceCall service = new ServiceCall();
		ServiceRequest request = new ServiceRequest("utils.mail.SendMailSMTP.checkMail");
		
		request.putLongParam("empId", LoginUser.getUserId());
		request.putStringParam("mailAddress", mailAddress.getValue());
		request.putStringParam("password", password.getValue());
		
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
			}
		});
	}
}
