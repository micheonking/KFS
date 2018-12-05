package myApp.client.vi.bbs;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.client.vi.sys.model.Sys10_FileModelProperties;

public class Grid_FileDownload {

	private Sys10_FileModelProperties properties = GWT.create(Sys10_FileModelProperties.class);
	private Grid<Sys10_FileModel> grid = this.buildGrid(); 
	public FormPanel form = new FormPanel();
	
	public Grid<Sys10_FileModel> buildGrid(){
		
		
		GridBuilder<Sys10_FileModel> gridBuilder = new GridBuilder<Sys10_FileModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addDateTime(properties.regDate(), 160, "등록일", null);
		gridBuilder.addText(properties.fileName(), 360, "파일명");
		gridBuilder.addDouble(properties.size(), 80, "크기"); 
		
		ActionCell<String> downloadCell = new ActionCell<String>("다운로드", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				Sys10_FileModel fileModel = grid.getSelectionModel().getSelectedItem(); 
				String url = "FileDownload?fileId=" + fileModel.getFileId();
				Window.open(url, "download File", "status=0,toolbar=0,menubar=0,location=0");
			}
		});
		gridBuilder.addCell(properties.downloadCell(), 100, "다운로드", downloadCell) ;

		ActionCell<String> deleteCell = new ActionCell<String>("삭제", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				deleteFile(); 
			}
		});
		gridBuilder.addCell(properties.downloadCell(), 80, "삭제", deleteCell) ;
		return gridBuilder.getGrid(); 
	}
	
	public void deleteFile(){
		GridDeleteData<Sys10_FileModel> service = new GridDeleteData<Sys10_FileModel>();
		List<Sys10_FileModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(this.grid.getStore(), checkedList, "sys.File.delete");
	}
	
	public void retrieve(Long parentId){
		GridRetrieveData<Sys10_FileModel> service = new GridRetrieveData<Sys10_FileModel>(grid.getStore());
		service.addParam("parentId", parentId);
		service.retrieve("sys.File.selectByParentId");
	} 
	
	public Grid<Sys10_FileModel> getGrid(){
		this.grid.setBorders(false);
		return this.grid; 
	}

}
