package myApp.client.vi.sys;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys08_CodeKindModel;
import myApp.client.vi.sys.model.Sys09_CodeModel;
import myApp.client.vi.sys.model.Sys09_CodeModelProperties;

public class Sys09_Grid_Code extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private Grid<Sys09_CodeModel> grid = this.buildGrid();
	private Long codeKindId;
	private Long codeCompanyId;
	
	private boolean editable = false ; 
	private TextButton updateButton, insertButton, deleteButton;
	
	public Sys09_Grid_Code() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addRetrieveButton(); 
		this.updateButton = searchBarBuilder.addUpdateButton();
		this.insertButton = searchBarBuilder.addInsertButton();
		this.deleteButton = searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 50));
		this.add(this.grid, new VerticalLayoutData(1, 1));
		
	}
	
	private Grid<Sys09_CodeModel> buildGrid(){
		
		Sys09_CodeModelProperties properties = GWT.create(Sys09_CodeModelProperties.class);	
		GridBuilder<Sys09_CodeModel> gridBuilder = new GridBuilder<Sys09_CodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.code(), 60, "코드", new TextField());
		gridBuilder.addText(properties.name(), 120, "코드명", new TextField()) ;
		gridBuilder.addText(properties.seq(), 80, "조회순서", new TextField());
		gridBuilder.addDate(properties.applyDate(), 100, "적용일", new DateField()) ;
		gridBuilder.addDate(properties.closeDate(), 100, "종료일", new DateField()) ;
		
//		gridBuilder.addDate(properties.lastDate(), 100, "마감일", new DateField()) ;
		gridBuilder.addText(properties.note(), 400, "상세설명", new TextField());

		// grid return 하기전에 설정해야만 한다. 
		gridBuilder.addBeforeStartEditHandler(new BeforeStartEditHandler<Sys09_CodeModel>(){
			@Override
			public void onBeforeStartEdit(BeforeStartEditEvent<Sys09_CodeModel> event) {
				
				if(editable) {
					if(event.getEditCell().getCol() == 6) { // 종료일(6번째 칼럼) 
						Sys09_CodeModel codeModel = grid.getSelectionModel().getSelectedItem();
						if(codeModel.getLastDate() == null ){
							event.setCancelled(false);
						}
						else {
							event.setCancelled(true);
						}
					}
				}
				else {
					event.setCancelled(true);
				}
			}
		}); 
		
		return gridBuilder.getGrid(); 
	}
	
	public void retrieveCode(Sys08_CodeKindModel codeKind){
		
		if(codeKind.getSysYnFlag()) { // 시스템여부 TRUE
			this.codeCompanyId = Long.parseLong("0"); 
			if(LoginUser.getIsAdmin()) { // Admin은 Editing이 가능하다. 
				this.editable= true;
			}
			else {
				this.editable = false; 
			}
		}
		else { // 시스템여부 아님. 
			this.codeCompanyId = LoginUser.getCompanyId(); 
			if(LoginUser.getIsAdmin()) { // 일반사용자는 Edting이 가능하다. 
				this.editable = false;
			}
			else {
				this.editable = true;
			}
		}

		this.updateButton.setEnabled(this.editable);
		this.insertButton.setEnabled(this.editable);
		this.deleteButton.setEnabled(this.editable);
		
		this.codeKindId = codeKind.getCodeKindId();
		

		this.retrieve();
	}

	@Override
	public void insertRow(){
		GridInsertRow<Sys09_CodeModel> service = new GridInsertRow<Sys09_CodeModel>();

		// 초기값 설정. 
		Sys09_CodeModel codeModel = new Sys09_CodeModel();
		codeModel.setCompanyId(LoginUser.getCompanyId()); // admin's company id is 0
		codeModel.setCodeKindId(this.codeKindId);
		codeModel.setApplyDate(new Date());
		
		service.insertRow(grid, codeModel);
	}
	@Override
	public void update(){
		GridUpdate<Sys09_CodeModel> service = new GridUpdate<Sys09_CodeModel>(); 
		service.update(this.grid.getStore(), "sys.Sys09_Code.update"); 
	}
	@Override
	public void deleteRow(){
		GridDeleteData<Sys09_CodeModel> service = new GridDeleteData<Sys09_CodeModel>();
		List<Sys09_CodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(this.grid.getStore(), checkedList, "sys.Sys09_Code.delete");
	}

	@Override
	public void retrieve() {
		grid.getStore().clear();

		GridRetrieveData<Sys09_CodeModel> service = new GridRetrieveData<Sys09_CodeModel>(grid.getStore());
		service.addParam("codeKindId", this.codeKindId);
		service.addParam("companyId", this.codeCompanyId);  
		service.retrieve("sys.Sys09_Code.selectByCodeKindId");
	} 
	
}