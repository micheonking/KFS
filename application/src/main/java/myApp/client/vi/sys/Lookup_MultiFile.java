package myApp.client.vi.sys;

import java.util.logging.Logger;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.client.vi.sys.model.Sys10_FileModelProperties;

public class Lookup_MultiFile extends VerticalLayoutContainer { //implements InterfaceServiceCall {
	private	final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Grid<Sys10_FileModel> grid = this.buildGrid();
	
	private Long 	fileId ; 
	private FormPanel formPanel = new FormPanel();
//	private TextField fileIdField = new TextField();
	private String editYn = "N";
	private final FileUploadField file = new FileUploadField();
	
	public Lookup_MultiFile() {
		
	}

	public void retrieve(Long fileId) {
		this.fileId = fileId;
		GridRetrieveData<Sys10_FileModel> service = new GridRetrieveData<Sys10_FileModel>(this.grid.getStore());
		service.addParam("parentId", this.fileId);
		service.retrieve("sys.Sys10_File.selectByParentId");
	}

	private void retrieve() {
		
		// TODO Auto-generated method stub
	    /*
	    ServiceRequest request = new ServiceRequest("sys.Sys10_File.selectByParentId");
	    request.putLongParam("parentId", fileId);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		*/
		GridRetrieveData<Sys10_FileModel> service = new GridRetrieveData<Sys10_FileModel>(this.grid.getStore());
		service.addParam("parentId", this.fileId);
		service.retrieve("sys.Sys10_File.selectByParentId");
	}
	
	
	public Lookup_MultiFile(Long fileId, String editYn, int height){
		this.editYn = editYn;
		makeLayout(fileId, height);
	}
	
	public Lookup_MultiFile(Long fileId, String editYn){
		this.editYn = editYn;
		makeLayout(fileId, 105);
	}
	
	private void makeLayout(Long fileId, int height) {
		this.fileId = fileId; 
		retrieve();//파일명 조회
		
		file.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {
				//Info.display("File Changed", "You selected " + file.getValue());
				submit();
			}
		});

		file.setName("uploadedfile"); // file name 
		file.setAllowBlank(false);
		
//		fileIdField.setName("fileId");
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(file, new VerticalLayoutData(300, -1, new Margins(2,0,2,2)));

		VerticalLayoutData vld1 = new VerticalLayoutData(1, -1);
		grid.setHeight(height);
		vlc.add(grid, vld1);
//		formPanel.setBorders(true);
		formPanel.setEncoding(Encoding.MULTIPART);
		formPanel.setMethod(Method.POST);
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				uploadMessage(event.getResults().toString());
				retrieve();
			}
		});
		
		formPanel.add(vlc);

		VerticalLayoutData vld = new VerticalLayoutData(); 
		vld.setMargins( new Margins(0, 0, 0, 0));
		this.add(formPanel, vld);
		
		retrieve();

		if("Y".equals(editYn)) {
		} else {
			file.hide();
			grid.setHeight(height + 35);
			grid.getColumnModel().getColumn(5).setHidden(true);
			grid.getColumnModel().getColumn(2).setWidth(370);
			
			//SafeStyles a = SafeStylesUtils.fromTrustedString("pointer-events:none;");
			//grid.getColumnModel().getColumn(3).setColumnStyle(a);
		}
		
	}
	
    private Grid<Sys10_FileModel> buildGrid(){
		
		Sys10_FileModelProperties properties = GWT.create(Sys10_FileModelProperties.class);		
		GridBuilder<Sys10_FileModel> gridBuilder = new GridBuilder<Sys10_FileModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.fileName(), 300, "파일명");
		ActionCell<String> downloadFileCell = new ActionCell<String>("Down", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				download();
			}
		});
		gridBuilder.addCell(properties.deleteCell(), 70, "받기", downloadFileCell); //, new TextField()) ;
		gridBuilder.addDouble(properties.size(), 80, "크기(KB)");
		ActionCell<String> deleteFileCell = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				deleteFile();
			}
		});
		gridBuilder.addCell(properties.deleteCell(), 70, "삭제", deleteFileCell); //, new TextField()) ;
		gridBuilder.addDate(properties.regDate(), 100, "등록일");
		return gridBuilder.getGrid(); 
	}
	
	//등록버튼
	private void submit(){
		if(fileId != null){
			//String actionUrl = "FileUpload?uploadType=image&"; 
			String actionUrl = "FileUpload?uploadType=file&fileId=" + this.fileId.toString();
			actionUrl += "&fileType=multi";
			formPanel.setAction(actionUrl);
			formPanel.submit();
			formPanel.reset();
		}
	}
	
	//받기버튼
	private void download() {
		Long fileId = grid.getSelectionModel().getSelectedItem().getFileId();
		String actionUrl = "FileDownload?fileId=" + fileId;
		actionUrl += "&fileType=file";
		formPanel.setAction(actionUrl);
		formPanel.submit();
	}
	
	//삭제버튼
	private void deleteFile() {
		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인","해당 파일을 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
					Long fileId = grid.getSelectionModel().getSelectedItem().getFileId();
					//String actionUrl = "FileUpload?uploadType=image&"; 
					String actionUrl = "FileUpload?uploadType=file&fileId=" + fileId;
					actionUrl += "&fileType=deleteFile";
					formPanel.setAction(actionUrl);
					formPanel.submit();
					formPanel.reset();
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
	
	public void uploadMessage(String message){
		// 이미지가 버퍼에 있어 다시 불러오지 않는 경우  System.currentTimeMillis() 을 붙여 
		// 시스템이 다은 URL호출이라 인지할 수 있도록 한다. 
		MessageBox box = new MessageBox("파일등록(" + this.fileId + ")", new HTML(message).getText());
        box.setIcon(MessageBox.ICONS.info());
        box.show();
	}
	
	public void changeViewLayout() {
		file.hide();
		grid.setHeight(height + 35);
		grid.getColumnModel().getColumn(5).setHidden(true);
		grid.getColumnModel().getColumn(2).setWidth(370);
		grid.getView().refresh(true);
	}

//	@Override
//	public void getServiceResult(ServiceResult result) {
//		// TODO Auto-generated method stub
//		if(result.getResult().size() > 0) {
//			retrieve();
//		}
//	}
}
