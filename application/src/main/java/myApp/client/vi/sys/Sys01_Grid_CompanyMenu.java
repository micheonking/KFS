package myApp.client.vi.sys;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.sys.model.Sys01_CmpInfoModel;
import myApp.client.vi.sys.model.Sys01_CmpInfoModelProperties;

public class Sys01_Grid_CompanyMenu extends ContentPanel {

	private Long menuId; 
	private Sys01_CmpInfoModelProperties properties = GWT.create(Sys01_CmpInfoModelProperties.class);
	private Grid<Sys01_CmpInfoModel> grid = this.buildGrid();
		
	public Sys01_Grid_CompanyMenu(){

		this.setHeaderVisible(false);
		this.add(grid);

		TextButton selectAllButton = new TextButton("전체선택"); 
		selectAllButton.setWidth(70);
		selectAllButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				for(Sys01_CmpInfoModel data : grid.getStore().getAll()) {
//					grid.getStore().getRecord(data).addChange(properties.menuYnChecked(), true);	
				}
			}
		}); 
		this.buttonBar.add(selectAllButton);

		TextButton deSelectAllButton = new TextButton("전체취소"); 
		deSelectAllButton.setWidth(70);
		deSelectAllButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				for(Sys01_CmpInfoModel data : grid.getStore().getAll()) {
//					grid.getStore().getRecord(data).addChange(properties.menuYnChecked(), false);	
				}
			}
		}); 
		this.buttonBar.add(deSelectAllButton);

		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(70);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		}); 
		this.buttonBar.add(updateButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}
	
	private Grid<Sys01_CmpInfoModel> buildGrid(){
		
		GridBuilder<Sys01_CmpInfoModel> gridBuilder = new GridBuilder<Sys01_CmpInfoModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.cmpName(), 200, "고객명", new TextField());
//		gridBuilder.addBoolean(properties.menuYnChecked(), 60, "등록");
		gridBuilder.addText(properties.bizNo(), 100, "사업자번호", new TextField());
		gridBuilder.addText(properties.telNo(), 100, "대표전화", new TextField());
		gridBuilder.addText(properties.rmk(), 400, "상세설명", new TextField());;
	
		return gridBuilder.getGrid(); 
	}

	public void retrieve(Long menuId){
		this.menuId = menuId; 
		GridRetrieveData<Sys01_CmpInfoModel> service = new GridRetrieveData<Sys01_CmpInfoModel>(grid.getStore()); 
		service.addParam("menuId", this.menuId);
		service.retrieve("sys.Sys01_Company.selectByMenuId");
	}
	
	private void update() {
		// 메뉴 사용여부를 Update한다. 
		GridUpdate<Sys01_CmpInfoModel> service = new GridUpdate<Sys01_CmpInfoModel>();
		service.addParam("menuId", this.menuId);
		service.update(this.grid.getStore(), "sys.Sys01_Company.updateByMenuYn"); 
	}
	
}
