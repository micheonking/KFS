 package myApp.client.vi.bbs;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.bbs.model.Bbs01_BoardModel;
import myApp.client.vi.bbs.model.Bbs01_BoardModelProperties;
import myApp.client.vi.emp.Emp02_Edit_Employee;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.pln.Pln03_Lookup_ResrchDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class Bbs01_Tab_BoardManager extends BorderLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {
	
	private Bbs01_BoardModelProperties properties = GWT.create(Bbs01_BoardModelProperties.class);
	private Grid<Bbs01_BoardModel> grid = this.buildGrid();
	private TextField searchText = new TextField();  
	private Bbs01_Edit_Board editBoard = new Bbs01_Edit_Board(grid);
	
	TextField typeCode = new TextField();
	ComboBoxField typeNameComboBox = new ComboBoxField("BoardTypeCode", "%", "전체");
	
	public Bbs01_Tab_BoardManager() {
		this.setBorders(false); 
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

		typeNameComboBox.setValue("전체");
		searchBarBuilder.addComboBox (typeNameComboBox, "구분", 200, 60);
		
		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));
		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));
		searchBarBuilder.addTextField(searchText, "제목으로 찾기", 250, 100, true); 
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addDeleteButton();
		searchBarBuilder.addInsertButton();
		
		
		
//		searchText.setValue("%");
		BorderLayoutData northLayoutData = new BorderLayoutData(50); 
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData);
		this.setCenterWidget(grid);
		
		BorderLayoutData eastLayoutData = new BorderLayoutData(0.6);
		eastLayoutData.setMargins(new Margins(1,0,0,2));
		eastLayoutData.setSplit(true);
		eastLayoutData.setMaxSize(1000);
		
		this.setEastWidget(editBoard, eastLayoutData);
		
		
		
		
		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Bbs01_BoardModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Bbs01_BoardModel> event) {
				if(event.getSelection().size() > 0){
					retrieveEditPage();
//					Bbs01_BoardModel boardModel = grid.getSelectionModel().getSelectedItem(); 
//					editBoard.edit(boardModel); 
				}
			} 
		});
		retrieve();
		 
		 
	}
	
	public Grid<Bbs01_BoardModel> buildGrid(){
			
			GridBuilder<Bbs01_BoardModel> gridBuilder = new GridBuilder<Bbs01_BoardModel>(properties.keyId());  
			gridBuilder.setChecked(SelectionMode.SINGLE);
	
			gridBuilder.addText(properties.typeName(), 70, "구분");
			gridBuilder.addText(properties.title(), 300, "제목");
			gridBuilder.addText(properties.writeUserName(), 100, "작성자");
			
			ActionCell<String> editBoardCell = new ActionCell<String>("Edit", new ActionCell.Delegate<String>() {
				@Override
				public void execute(String object) {
					editBoard();
				}
			});
			
			gridBuilder.addCell(properties.writeUserName(), 50, "수정",editBoardCell);
			gridBuilder.addDate(properties.writeDate(), 80, "작성일");
	
			return gridBuilder.getGrid(); 
		}
	
	protected void editBoard() {
		Bbs01_BoardModel boardModel = grid.getSelectionModel().getSelectedItem();
		
		Bbs01_Lookup_BoardUpdate editBoard = new Bbs01_Lookup_BoardUpdate(grid, boardModel);	
		editBoard.show();
	}

	private void retrieveEditPage(){
	
	//	InterfaceTabPage selectedPage = (InterfaceTabPage)tabPanel.getActiveWidget();
		if(this.grid.getSelectionModel().getSelectedItem() != null) {
			Bbs01_BoardModel boardModel = this.grid.getSelectionModel().getSelectedItem();
	
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("boardModel", boardModel); 
			editBoard.retrieve(param);
		}
		else {
			editBoard.init(); 
		}
	}
	
	
	@Override
	public void retrieve(){
		
		GridRetrieveData<Bbs01_BoardModel> service = new GridRetrieveData<Bbs01_BoardModel>(grid.getStore());
		service.addParam("typeCode", typeNameComboBox.getCode()); 
		service.addParam("title", searchText.getText()); 
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("writeUserName",LoginUser.getUserId());
//		service.addParam("boardId", boardMo);
//		service.addParam("title", tit.getText());
//		service.addParam("codeName", codeNameField.getText());
		service.retrieve("bbs.Bbs01_Board.selectByCompanyId", new InterfaceCallback() {
			
			@Override
			public void execute() {
//				if(grid.getStore().get(0) != null) {
//					grid.getSelectionModel().select(0, false);
//				}
			}
		});
	}
	
	@Override
	public void update(){
//		editBoard.update();
		
		
	}
	
	@Override
	public void insertRow(){
		
		Bbs01_BoardModel boardModel = new Bbs01_BoardModel();
		
		DBUtil dbUtil = new DBUtil(); 
		dbUtil.setSeq(boardModel, new InterfaceCallback() {
			@Override
			public void execute() {
				Info.display("aa22222", "seqID : " + boardModel.getBoardId());
				
				boardModel.setWriteUserId(LoginUser.getUserId());
				boardModel.setWriteDate(LoginUser.getToday());
				boardModel.setWriteUserName(LoginUser.getUserName());
				boardModel.setCompanyId(LoginUser.getCompanyId());

				Bbs01_Lookup_BoardInsert editor = new Bbs01_Lookup_BoardInsert(grid, boardModel);
				editor.show();
			}
		});
	}
	
	@Override
	public void deleteRow(){
		

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 내용을 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
		
					GridDeleteData<Bbs01_BoardModel> service = new GridDeleteData<Bbs01_BoardModel>();
					List<Bbs01_BoardModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 

					service.delete(grid.getStore(), checkedList, "bbs.Bbs01_Board.delete", new InterfaceCallback() {
					
						public void execute() {
							hide();
							if(grid.getStore().get(0) != null) {
								grid.getSelectionModel().select(0, false);
							}			
						}
					});
					
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

	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
		
	}
	
	

}