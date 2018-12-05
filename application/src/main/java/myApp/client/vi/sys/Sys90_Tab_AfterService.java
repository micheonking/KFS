package myApp.client.vi.sys;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys90_AfterServiceModel;
import myApp.client.vi.sys.model.Sys90_AfterServiceModelProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class Sys90_Tab_AfterService extends BorderLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {
	
	private Sys90_AfterServiceModelProperties properties = GWT.create(Sys90_AfterServiceModelProperties.class);
	private Grid<Sys90_AfterServiceModel> grid = this.buildGrid();
	private TextField 	searchText    =	new TextField();
	private TextField 	workerSearchText = new TextField();
//	private MyDateField happenDate 	  =	new MyDateField();
	private DateField happenStartDate 	  =	new DateField();
	private DateField happenEndDate 	  =	new DateField();
	private CheckBox 	closeCheckBox = new CheckBox();
	private ComboBoxField typeNameComboBox = new ComboBoxField("AfterServiceCode", "%", "전체");
	
	private Sys90_Edit_Board editBoard = new Sys90_Edit_Board(grid);
	
	TextField typeCode = new TextField();
	
	public Sys90_Tab_AfterService() {
		this.setBorders(false); 
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
		searchBarBuilder.getSearchBar().add(new LabelToolItem("발생일 : "));
		
//		searchBarBuilder.addDateField(happenDate, "발생일", 240, 100, true); 
		
		Date date = LoginUser.getToday();
		CalendarUtil.addDaysToDate(date, -30);
		happenStartDate.setValue(date);
		happenStartDate.setWidth(100);
		searchBarBuilder.getSearchBar().add(happenStartDate);
		
		
		searchBarBuilder.getSearchBar().add(new LabelToolItem("~"));
		happenEndDate.setValue(LoginUser.getToday());
		happenEndDate.setWidth(100);
		searchBarBuilder.getSearchBar().add(happenEndDate);
//		searchBarBuilder.addDateField(happenEndDate, "~", 240, 100, true); 
		
		typeNameComboBox.setValue("전체");
		searchBarBuilder.addComboBox (typeNameComboBox, "구분", 200, 60);
//		searchBarBuilder.getSearchBar().add(typeNameField);
//		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));//간격조정
//		searchBarBuilder.getSearchBar().add(new LabelToolItem(""));//간격조정
		searchBarBuilder.addTextField(workerSearchText, "담당자", 240, 100, true); 
		searchBarBuilder.addTextField(searchText, "문자열 찾기", 240, 100, true); 
		
		closeCheckBox.setBoxLabel("완료건 포함");
		closeCheckBox.setValue(false);
		searchBarBuilder.addCheckBox(closeCheckBox, 100, 0);
		
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addDeleteButton(); 
//		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addInsertButton(); 
		


		BorderLayoutData northLayoutData = new BorderLayoutData(50);
		northLayoutData.setMargins(new Margins(0, 0, 0, 0));
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData);
		this.setCenterWidget(grid);
		
		BorderLayoutData eastLayoutData = new BorderLayoutData(0.6);
		eastLayoutData.setMargins(new Margins(1,0,0,2));
		eastLayoutData.setSplit(true);
		eastLayoutData.setMaxSize(1000);
		
		this.setEastWidget(editBoard, eastLayoutData);
		
		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Sys90_AfterServiceModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Sys90_AfterServiceModel> event) {
				if(event.getSelection().size() > 0){
					retrieveEditPage();
					
//					Sys90_AfterServiceModel afterServiceModel = grid.getSelectionModel().getSelectedItem(); 
//					editBoard.edit(afterServiceModel); 
				}
			} 
		});
		retrieve();
		 
	}
	
	public Grid<Sys90_AfterServiceModel> buildGrid(){
			
			GridBuilder<Sys90_AfterServiceModel> gridBuilder = new GridBuilder<Sys90_AfterServiceModel>(properties.keyId());  
			gridBuilder.setChecked(SelectionMode.SINGLE);
	
			gridBuilder.addDate(properties.happenStartDate(), 90, "발생일");
			gridBuilder.addText(properties.afterServiceName(), 50, "구분" );
			gridBuilder.addText(properties.happenReason(), 160, "개선 및 오류내용");
			gridBuilder.addText(properties.worker(), 55, "담당자");
//			gridBuilder.addText(properties.happenRemedy(), 80, "처리방안");
			gridBuilder.addDate(properties.dueDate(), 90, "완료예정일");
			gridBuilder.addText(properties.closeName(), 80, "완료여부");
	
			return gridBuilder.getGrid(); 
		}
	
//	protected void editBoard() {
//		Sys90_AfterServiceModel afterServiceModel = grid.getSelectionModel().getSelectedItem();
//		
//		Sys90_Edit_Board editBoard = new Sys90_Edit_Board(grid, afterServiceModel);	
//		editBoard.show();
//	}

	private void retrieveEditPage(){
	
		if(this.grid.getSelectionModel().getSelectedItem() != null) {
			Sys90_AfterServiceModel afterServiceModel = this.grid.getSelectionModel().getSelectedItem();
	
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("afterServiceModel", afterServiceModel); 
			editBoard.retrieve(param);
		}
		else {
			editBoard.init();  
		}
	}
	
	@Override
	public void retrieve(){
		
		GridRetrieveData<Sys90_AfterServiceModel> service = new GridRetrieveData<Sys90_AfterServiceModel>(grid.getStore());
//		service.addParam("afterServiceId",.getCode());
		service.addParam("happenStartDate", happenStartDate.getValue());
		service.addParam("happenEndDate", happenEndDate.getValue());
		service.addParam("typeYn", typeNameComboBox.getCode());
		service.addParam("worker", workerSearchText.getText());
		service.addParam("happenReason", searchText.getText()); 
		service.addParam("closeYn", closeCheckBox.getValue().toString());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("regEmpName", LoginUser.getUserId());
		service.retrieve("sys.Sys90_AfterService.selectByTitle", new InterfaceCallback() {
			
			@Override
			public void execute() {
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
				}
			}
		});
	}
	
	@Override
	public void update(){
//		editBoard.update();
		
		
	}
	
	@Override
	public void insertRow(){

			GridInsertRow<Sys90_AfterServiceModel> service = new GridInsertRow<Sys90_AfterServiceModel>(); 
			Sys90_AfterServiceModel afterServiceModel = new Sys90_AfterServiceModel();
			service.insertRow(grid, afterServiceModel);
		}
//		
//		Sys90_AfterServiceModel boardModel = new Sys90_AfterServiceModel();
//		
//		DBUtil dbUtil = new DBUtil(); 
//		dbUtil.setSeq(boardModel, new InterfaceCallback() {
//			@Override
//			public void execute() {
//			}
//		});
//	}
	
	@Override
	public void deleteRow(){
		

		final ConfirmMessageBox msgBox = new ConfirmMessageBox("삭제확인", "선택한 내용을 삭제하시겠습니까?");
		msgBox.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				switch (event.getHideButton()) {
				case YES:
		
					GridDeleteData<Sys90_AfterServiceModel> service = new GridDeleteData<Sys90_AfterServiceModel>();
					List<Sys90_AfterServiceModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
					service.delete(grid.getStore(), checkedList, "sys.Sys90_AfterService.delete", new InterfaceCallback() {
					
						public void execute() {
							if(grid.getStore().get(0) != null) {
								grid.getSelectionModel().select(0, false);
							}			
						}
					});
					
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