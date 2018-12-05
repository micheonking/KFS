package myApp.client.vi.apr;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.client.vi.sys.model.Sys10_FileModelProperties;

public class Apr01_Grid_ApprFile extends ContentPanel {

	private Apr01_ApprModel apprModel ; 
	protected Grid<Sys10_FileModel> fileGrid = this.buildApprFileGrid();
	private Boolean editable = false;
	private Boolean readable = false;
		
	 
	private FormPanel fileDownloadForm = new FormPanel(); // file download form 
	private FormPanel fileDeleteForm = new FormPanel(); // file delete form 
	
	public Apr01_Grid_ApprFile() {
		
		this.setHeaderVisible(false);
		
		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
		hlc.add(new LabelToolItem("첨부파일:"), new HorizontalLayoutData(60, -1, new Margins(0)));
		hlc.add(this.fileGrid, new HorizontalLayoutData(1, 1, new Margins(0, 10, 0, 1)));
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(hlc, new VerticalLayoutData(1, 240));
		
		
		// file download form 
		this.fileDownloadForm.setVisible(false);
		vlc.add(this.fileDownloadForm); // form은 Layout에 붙어 있어야 한다.

		// file delete form 
		this.fileDeleteForm.setVisible(false);
		fileDeleteForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				retrieve(); 
			}
		}); 
		vlc.add(this.fileDeleteForm); // form은 Layout에 붙어 있어야 한다.
		
		this.add(vlc);
	}
	
	private void retrieve() {
   	    this.retrieve(this.apprModel, this.editable); 
	}
	
	public void retrieve(Apr01_ApprModel apprModel, Boolean editable) { // 첨부파일 조회
		this.apprModel = apprModel;
		this.editable = editable ; 
		
		
		// 삭제컬럼을 감추고 파일명을 넓혀준다.  
		if(!editable) {
			this.fileGrid.getColumnModel().setHidden(4, true);
			this.fileGrid.getColumnModel().setColumnWidth(2, 500);
		}
		
   		GridRetrieveData<Sys10_FileModel> service = new GridRetrieveData<Sys10_FileModel>(this.fileGrid.getStore());
   	    service.addParam("parentId", this.apprModel.getApprId());
   	    service.retrieve("sys.Sys10_File.selectByParentId", new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				List<Sys10_FileModel> tempList = fileGrid.getStore().getAll();
				for(int i = 0; 0 < tempList.size(); i++) {
					Sys10_FileModel tempModel = tempList.get(i);
					if("true".equals(tempModel.getTitleYn())) {
						fileGrid.getStore().remove(tempModel);
					}
				}
			}
		});
	}
	
	
	
    private Grid<Sys10_FileModel> buildApprFileGrid() {
    	Sys10_FileModelProperties properties = GWT.create(Sys10_FileModelProperties.class);
		GridBuilder<Sys10_FileModel> gridBuilder = new GridBuilder<Sys10_FileModel>(properties.keyId());  

		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.fileName(), 400, "첨부파일");

		ActionCell<String> downloadCell = new ActionCell<String>("Down", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				fileDownloadForm.setEncoding(Encoding.MULTIPART);
				fileDownloadForm.setMethod(Method.POST);
				String actionUrl = "FileDownload?fileType=file&fileId=" + fileGrid.getSelectionModel().getSelectedItem().getFileId(); 
				fileDownloadForm.setAction(actionUrl);
				fileDownloadForm.submit(); // form을 submit하려면 Layout에 붙어 있어야 한다.
			}
		});
		
		ActionCell<String> deleteCell = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				if(editable) {
					String actionUrl = "FileDelete?fileId=" + fileGrid.getSelectionModel().getSelectedItem().getFileId();
			    	fileDeleteForm.setEncoding(Encoding.MULTIPART);
					fileDeleteForm.setMethod(Method.POST);
					fileDeleteForm.setAction(actionUrl);
					fileDeleteForm.submit(); // form을 submit하려면 Layout에 붙어 있어야 한다. 
				} 
				else {
					new SimpleMessage("상신된 문서의 내용을 변경할 수 없습니다.");
				}
			}
		});
		
		gridBuilder.addCell(properties.downloadCell(), 80, "파일받기", downloadCell); 
		gridBuilder.addCell(properties.deleteCell(), 80, "파일삭제", deleteCell); 
		
		return gridBuilder.getGrid();  
    }
    
    public void hideDown() {
    	this.fileGrid.getColumnModel().setHidden(3, true);
    	this.fileGrid.getColumnModel().setColumnWidth(2, 580);
    }
    
    public void showDown() {
    	
    }
    
    public void showDelete() {
    	this.editable = true;
    	this.fileGrid.getColumnModel().setHidden(4, false);
    	this.fileGrid.getColumnModel().setColumnWidth(2, 400);
    }
}

