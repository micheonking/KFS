package myApp.client.vi.dcr;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.JSCaller;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr10_MailHistoryModel;
import myApp.client.vi.emp.Emp10_Lookup_MailAddress;
import myApp.client.vi.emp.Emp10_Lookup_MailRetrieve;
import myApp.client.vi.emp.model.Emp01_PersonModel;
import myApp.client.vi.emp.model.Emp10_ManagerContactModel;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.client.vi.sys.model.Sys10_FileModelProperties;

public class Dcr10_Lookup_MailSender extends Window {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Long apprId = 0L;
	private Long mailId = 0L;
	private Long keyId = 0L;
	private TextField sender = new TextField();
	private TextField receiver = new TextField();
	private TextField ccReceiver = new TextField();
	private TextField title = new TextField();
	private TextButton senderButton = new TextButton("본인메일확인");
	private TextButton receiverButton = new TextButton("주소록");
	private TextButton ccReceiverButton = new TextButton("주소록");
	private Grid<Emp10_ManagerContactModel> grid;
	private Grid<Emp10_ManagerContactModel> grid2;
	private Dcr10_MailHistoryModel mailModel = new Dcr10_MailHistoryModel();

	private TextButton confirmButton = new TextButton("보내기");
	private TextButton closeButton = new TextButton("닫기");
	
	private FormPanel fileDownloadForm = new FormPanel(); // file download form

	private Grid<Sys10_FileModel> fileGrid = this.buildGrid();
	
	// 웹에디터 HTML 설정
	public interface HTMLTemplate extends XTemplates {
		@XTemplate("<iframe id='htmlTemplate' frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0 src='naverEditor.jsp' width='100%' height='100%'/>")
		SafeHtml getTemplate();
	}
	

	public Dcr10_Lookup_MailSender() {
		init();
	}

	public Dcr10_Lookup_MailSender(Long apprId, String gubun) {
		this.apprId = apprId;
		init();
	}

	public Grid<Sys10_FileModel> buildGrid(){
		
		Sys10_FileModelProperties properties = GWT.create(Sys10_FileModelProperties.class);
	   	GridBuilder<Sys10_FileModel> gridBuilder = new GridBuilder<Sys10_FileModel>(properties.keyId());
			
	   	gridBuilder.setChecked(SelectionMode.SINGLE);
	   	gridBuilder.addText(properties.fileName(), 500, "첨부파일");
	   	
	   	ActionCell<String> downloadCell = new ActionCell<String>("Down", new ActionCell.Delegate<String>(){
		   	@Override
		   	public void execute(String arg0) {
		   		fileDownloadForm.setEncoding(Encoding.MULTIPART);
				fileDownloadForm.setMethod(Method.POST);
				String actionUrl = "FileDownload?fileType=file&fileId=" + fileGrid.getSelectionModel().getSelectedItem().getFileId(); 
				fileDownloadForm.setAction(actionUrl);
				fileDownloadForm.submit();
		   	}
	   	});
	   	gridBuilder.addCell(properties.downloadCell(), 70, "상세보기", downloadCell); //, new TextField()) ;
	   	ActionCell<String> deleteCell = new ActionCell<String>("delete", new ActionCell.Delegate<String>(){
		   	@Override
		   	public void execute(String arg0) {
		   		fileGrid.getStore().remove(fileGrid.getSelectionModel().getSelectedItem());
		   		fileGrid.getView().refresh(true);
		   	}
	   	});
	   	gridBuilder.addCell(properties.deleteCell(), 70, "삭제", deleteCell);
	   	
	   	return gridBuilder.getGrid(); 
    }
	
	private void init() {
		makeLayout();
		makeButtonEvent();
		retrieve();
		
		DBUtil dbUtil = new DBUtil();
		dbUtil.setSeq(mailModel, new InterfaceCallback() {
			@Override
			public void execute() {
				GridRetrieveData<Sys10_FileModel> service = new GridRetrieveData<Sys10_FileModel>(fileGrid.getStore());
		   	    service.addParam("apprId", apprId);
		   	    service.retrieve("sys.Sys10_File.selectApprRelateDoc");
			}
		});
		
	}

	private void makeLayout() {
		this.setModal(true);
		this.setHeading("메일 보내기");
		this.setResizable(false);
		this.setPixelSize(850, 800);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//		HorizontalLayoutData termLayout = new HorizontalLayoutData(50, -1, new Margins(0, 5, 5, 5)); // 여백Size
		HorizontalLayoutData labelLayout = new HorizontalLayoutData(90, -1, new Margins(2, 0, 0, 0)); // 라벨Size
		HorizontalLayoutData rowLayout1 = new HorizontalLayoutData(600, -1, new Margins(0, 5, 5, 0)); // 컬럼하나Size
		HorizontalLayoutData htmlLayout = new HorizontalLayoutData(700, 1, new Margins(0, 0, 5, 0)); // HTML Editor

		
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
		
		
		row01.add(new LabelToolItem("보내는 사람 : "), labelLayout);
		row01.add(sender, rowLayout1);
		row01.add(senderButton);
		sender.setReadOnly(true);

		row02.add(new LabelToolItem("받는 사람 : "), labelLayout);
		row02.add(receiver, rowLayout1);
		row02.add(receiverButton);
		
		row03.add(new LabelToolItem("참조 : "), labelLayout);
		row03.add(ccReceiver, rowLayout1);
		row03.add(ccReceiverButton);

		row04.add(new LabelToolItem("제목 : "), labelLayout);
		row04.add(title, rowLayout1);

		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate());
		row05.add(new LabelToolItem("내용 : "), labelLayout);
		row05.add(htmlLayoutContainer, htmlLayout);

		row06.add(new LabelToolItem("첨부파일 : "), labelLayout);
		row06.add(fileGrid);
		
		row07.add(new LabelToolItem(""), labelLayout);
		this.fileDownloadForm.setVisible(false);
		vlc.add(this.fileDownloadForm); // form은 Layout에 붙어 있어야 한다.
		vlc.add(row01, new VerticalLayoutData(1, 32, new Margins(0, 0, 0, 0)));
		vlc.add(row02, new VerticalLayoutData(1, 32, new Margins(0, 0, 0, 0)));
		vlc.add(row03, new VerticalLayoutData(1, 32, new Margins(0, 0, 0, 0)));
		vlc.add(row04, new VerticalLayoutData(1, 32, new Margins(0, 0, 0, 0)));
		vlc.add(row05, new VerticalLayoutData(1, 400, new Margins(0, 0, 0, 0)));
		vlc.add(row06, new VerticalLayoutData(1, 32, new Margins(0, 0, 0, 0)));
		//vlc.add(row06, new VerticalLayoutData(1, 160, new Margins(0, 0, 0, 0)));

		this.add(vlc, new VerticalLayoutData(1,1, new Margins(15,15,0,15)));
		this.getButtonBar().add(confirmButton);
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		JSCaller.setCallback(new InterfaceCallback() {
   			@Override
   			public void execute() {
   				changeEditorHeight(320);
   				noImage();
   			}
   		});
	}

	private void makeButtonEvent() {
		senderButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Emp10_Lookup_MailRetrieve mail = new Emp10_Lookup_MailRetrieve();
				mail.open(new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						retrieve();
					}
				});
			}
		});
		
		receiverButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Emp10_Lookup_MailAddress mail = new Emp10_Lookup_MailAddress(grid);
				mail.open(new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						setReceiver((String)result, receiver);
					}
				});
			}
		});
		
		ccReceiverButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Emp10_Lookup_MailAddress mail = new Emp10_Lookup_MailAddress(grid2);
				mail.open(new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						setReceiver((String)result, ccReceiver);
					}
				});
			}
		});
		
		confirmButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				sendMailSMTP();
			}
		});
		
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
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
				sender.setValue(tempModel.getEmailAddr());
			}
		});	
	}
	
	private void setReceiver(Grid<Emp10_ManagerContactModel> grid, TextField receiver) {
		List<Emp10_ManagerContactModel> list = grid.getSelectionModel().getSelectedItems();
		String tempStr = "";
		for(Emp10_ManagerContactModel model : list) {
			tempStr += model.getManagerName();
			tempStr += ",";
		}

		receiver.setValue(tempStr);
	}
	
	private void setReceiver(String str, TextField receiver) {
		String tempStr = receiver.getText();
		tempStr += str;

		receiver.setValue(tempStr);
	}

	private void sendMailSMTP() {
		if(title.getText() == null || "".equals(title.getText())) {
			new SimpleMessage("제목을 입력해주세요.");
			return;
		}
		
		ServiceRequest request = new ServiceRequest("utils.mail.SendMailSMTP.sendMailSession");
	
		List<String> fileList = new ArrayList<String>();
		
		List<Sys10_FileModel> tempList = fileGrid.getStore().getAll();
		for(int i = 0; i < tempList.size(); i++) {
			fileList.add(""+tempList.get(i).getFileId());
		}
		
		
		request.putLongParam("empId", LoginUser.getUserId());
		request.putStringParam("receptor", receiver.getText());
		request.putStringParam("ccReceptor", ccReceiver.getText());
		request.putStringParam("header", title.getText());
		
		request.putStringParam("content", getHtmlText());
		request.putStringParam("fileKey", String.valueOf(apprId));
		
		request.addParam("fileList", fileList);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				if(result.getStatus() == -1) {
					new SimpleMessage(result.getMessage());
				} else {
					addLog();
				}
			}
		});
	}

	private void addLog() {
		Dcr10_MailHistoryModel mailHistoryModel = new Dcr10_MailHistoryModel();
		//mailHistoryModel.setKeyId(keyId);
		//mailHistoryModel.setMailHistoryId(keyId);
		mailHistoryModel.setAprId(apprId);
		mailHistoryModel.setSenderEmail(sender.getText());
		mailHistoryModel.setReceiverEmail(receiver.getText());
		mailHistoryModel.setReferencerEmail(ccReceiver.getText());
		mailHistoryModel.setTitleText(title.getText());
		String htmlText = getHtmlText();
		mailHistoryModel.setBodyText(htmlText);
		
		
		ServiceRequest request = new ServiceRequest("dcr.Dcr10_MailHistory.insertMailHistory");
		request.putModelParam("mailHistoryModel", mailHistoryModel);
		request.putLongParam("apprId", apprId);
		request.putStringParam("senderEmail", sender.getText());
		request.putStringParam("receiverEmail", receiver.getText());
		request.putStringParam("referencerEmail", ccReceiver.getText());
		request.putStringParam("titleText", title.getText());
		request.putStringParam("bodyText", htmlText);

		ServiceCall service = new ServiceCall();
		service.execute(request, new InterfaceServiceCall() {
			@Override
			public void getServiceResult(ServiceResult result) {
				hide();
			}
		});
		
	}
	
	// 에디터 높이 조정
	private native void changeEditorHeight(int height) /*-{
		$doc.getElementById("htmlTemplate").contentWindow.changeHeight(height);
	}-*/;
	
	// 에디터 내용 가져오기
	private native String getHtmlText() /*-{
		return $doc.getElementById("htmlTemplate").contentWindow.sendToServer();
	}-*/;
	
	// 에디터 사진첨부 삭제
	private native String noImage() /*-{
		return $doc.getElementById("htmlTemplate").contentWindow.noImage();
	}-*/;

	
	
}
