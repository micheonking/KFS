package myApp.client.vi.sys;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
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
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.client.vi.sys.model.Sys10_FileModelProperties;

public class Sys10_Lookup_MultiFile extends VerticalLayoutContainer {

	private Grid<Sys10_FileModel> grid = this.buildGrid();

	private FormPanel 	fileDownloadForm = new FormPanel(); 
	private FormPanel 	fileDeleteForm   = new FormPanel();

	private FileUpdownForm fileUpdownForm = new FileUpdownForm();

	private TextButton uploadButton = new TextButton("파일등록");
	
	private Long	parentId;
	private String	editYn = "N";
	private	int		height = 105;

	public Sys10_Lookup_MultiFile() {
		makeLayout();
	}
	
	public Sys10_Lookup_MultiFile(Long parentId, String editYn, int height) {
		this.parentId = parentId;
		this.editYn = editYn;
		this.height = height;
		makeLayout();
	}
	
	private void makeLayout() {

		uploadButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				fileUpdownForm.click();
			}
		});
		this.add(uploadButton);
		grid.setHeight(height);
		this.add(grid);

		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {

				if (("".equals(fileUpdownForm.getFileName())) || (fileUpdownForm.getFileName() == null)) {
					return;
				}

				String actionUrl = "FileUpload?parentId=" + parentId;
				fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
					@Override
					public void execute() {
						retrieve(); 
					}
				});
			}
		});
		this.add(fileUpdownForm.getForm());

		fileDownloadForm.setVisible(false);
		this.add(fileDownloadForm);
		
		fileDeleteForm.setVisible(false);
		fileDeleteForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				retrieve();
			}
		});
		this.add(fileDeleteForm);

		if("Y".equals(editYn)) {
		} else {
			uploadButton.hide();
			grid.setHeight(height + 35);
			grid.getColumnModel().getColumn(5).setHidden(true);
			grid.getColumnModel().getColumn(2).setWidth(370);
		}
	}

    private Grid<Sys10_FileModel> buildGrid(){
		
		Sys10_FileModelProperties properties = GWT.create(Sys10_FileModelProperties.class);		
		GridBuilder<Sys10_FileModel> gridBuilder = new GridBuilder<Sys10_FileModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.fileName(), 300, "파일명");
		gridBuilder.addDouble(properties.size(), 100, "파일크기(KB)");

		ActionCell<String> downloadCell = new ActionCell<String>("Down", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				String actionUrl = "FileDownload?fileType=file&fileId=" + grid.getSelectionModel().getSelectedItem().getFileId(); 
				fileDownloadForm.setEncoding(Encoding.MULTIPART);
				fileDownloadForm.setMethod(Method.POST);
				fileDownloadForm.setAction(actionUrl);
				fileDownloadForm.submit(); // form을 submit하려면 Layout에 붙어 있어야 한다.
			}
		});
		gridBuilder.addCell(properties.downloadCell(), 80, "파일받기", downloadCell);

		ActionCell<String> deleteCell = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				String actionUrl = "FileDelete?fileId=" + grid.getSelectionModel().getSelectedItem().getFileId();
		    	fileDeleteForm.setEncoding(Encoding.MULTIPART);
				fileDeleteForm.setMethod(Method.POST);
				fileDeleteForm.setAction(actionUrl);
				fileDeleteForm.submit(); // form을 submit하려면 Layout에 붙어 있어야 한다. 
			}
		});
		gridBuilder.addCell(properties.deleteCell(), 80, "파일삭제", deleteCell);

		return gridBuilder.getGrid(); 
	}

    public void retrieve() {
    	retrieve(parentId);
    }

    public void retrieve(Long parentId) {
    	this.parentId = parentId;
   		GridRetrieveData<Sys10_FileModel> service = new GridRetrieveData<Sys10_FileModel>(grid.getStore());
   	    service.addParam("parentId", parentId);
   	    service.retrieve("sys.Sys10_File.selectByParentId");
    }
    
	public void changeViewLayout() {
		uploadButton.hide();
		grid.setHeight(height + 35);
		grid.getColumnModel().getColumn(5).setHidden(true);
		grid.getColumnModel().getColumn(2).setWidth(370);
		grid.getView().refresh(true);
	}


}
