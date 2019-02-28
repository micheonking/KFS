package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.sys.model.Sys03_MenuModel;
import myApp.client.vi.sys.model.Sys03_MenuModelProperties;

public class Sys03_Tree_Menu extends VerticalLayoutContainer implements InterfaceServiceCall {
	
	private TreeGrid<Sys03_MenuModel> treeGrid = this.buildTreeGrid();
		
	public Sys03_Tree_Menu(){
		
		ButtonBar buttonBar = new ButtonBar();
		
		TextButton retrieveButton = new TextButton("메뉴 조회");
		retrieveButton.setWidth(90);
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		buttonBar.add(retrieveButton);
		
		TextButton createRootButton = new TextButton("루트메뉴 등록");
		createRootButton.setWidth(120);
		createRootButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// root menu는 parentId가 0 이다. 
				insertMenu(Long.parseLong("0"));
			}
		});
		buttonBar.add(createRootButton);

		TextButton createSubButton = new TextButton("하위메뉴 등록");
		createSubButton.setWidth(120);
		createSubButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// root menu는 parentId가 0 이다. 
				insertSubMenu();
			}
		});
		buttonBar.add(createSubButton);
		this.add(buttonBar, new VerticalLayoutData(1, 50));
		
		treeGrid.getTreeStore().setAutoCommit(true);
		this.add(treeGrid, new VerticalLayoutData(1,1));
		
		retrieve();
	}

	private TreeGrid<Sys03_MenuModel> buildTreeGrid() {
		
		Sys03_MenuModelProperties properties = GWT.create(Sys03_MenuModelProperties.class);
		final GridBuilder<Sys03_MenuModel> gridBuilder = new GridBuilder<Sys03_MenuModel>(properties.keyId());
		
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.menuName(), 250, "메뉴명");

		ActionCell<String> editCell = new ActionCell<String>("Edit", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String arg0) {
				Sys03_MenuModel menuModel = treeGrid.getSelectionModel().getSelectedItem();
				editMenu(menuModel);
			}
		});
		gridBuilder.addCell(properties.editCell(), 60, "수정", editCell);

		ActionCell<String> moveCell = new ActionCell<String>("Move", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String arg0) {
				moveMenu();
			}
		});
		gridBuilder.addCell(properties.moveCell(), 60, "이동", moveCell);

		gridBuilder.addText(properties.seqStr(), 60, "순서");
		gridBuilder.addText(properties.className(), 200, "클래스명");
		gridBuilder.addText(properties.rmk(), 200, "비고");
		
		return gridBuilder.getTreeGrid(2);
	}
	
	protected void moveMenu() {
		Sys03_Lookup_MoveMenu moveMenu = new Sys03_Lookup_MoveMenu();
		moveMenu.open(treeGrid, treeGrid.getSelectionModel().getSelectedItem());
	}

	protected void editMenu(Sys03_MenuModel menuModel) {
		Sys03_Lookup_EditMenu editMenu = new Sys03_Lookup_EditMenu();
		editMenu.open(treeGrid, menuModel);
	}

	protected void retrieve() {
		ServiceRequest request = new ServiceRequest("sys.Sys03_Menu.selectByAll");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	protected void insertMenu(long parentId) {
		Sys03_MenuModel menuModel = new Sys03_MenuModel();
		DBUtil dbUtil = new DBUtil();
		dbUtil.setSeq(menuModel, new InterfaceCallback() {
			@Override
			public void execute() {
				menuModel.setPrntId(parentId);
				editMenu(menuModel);
			}
		});
	}

	protected void insertSubMenu() {
		Sys03_MenuModel prntModel = treeGrid.getSelectionModel().getSelectedItem();
		if(prntModel == null) {
			new SimpleMessage("확인", "선택된 상위 메뉴가 없습니다. ");
			return;
		}
		
		if(prntModel.getClassName() != null) {
			new SimpleMessage("확인", "오브젝트에는 하위 메뉴를 등록할 수 없습니다.", 400);
			return;
		}
		
		treeGrid.setExpanded(prntModel, true);
		insertMenu(prntModel.getMenuId());
	}
	
	private void setSubMenuItem(Sys03_MenuModel parentMenu) {
		if(parentMenu.getChildList() != null) {
			List<GridDataModel> subList = parentMenu.getChildList();
			for(GridDataModel sub : subList) {
				Sys03_MenuModel subObject = (Sys03_MenuModel) sub;
				treeGrid.getTreeStore().add(parentMenu, subObject);
				setSubMenuItem(subObject);
			}
		}
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0) {
			Info.display("Error", result.getMessage());
		}
		else {
			treeGrid.getTreeStore().clear();
			for(GridDataModel model : result.getResult()) {
				Sys03_MenuModel object = (Sys03_MenuModel)model;
				treeGrid.getTreeStore().add(object);
				setSubMenuItem(object);
			}
		}
	}

}
