package myApp.client.vi.fnd;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModel;
import myApp.client.vi.fnd.model.Fnd03_ConsignmentModelProperties;

public class Fnd03_Tab_Consignment extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd03_ConsignmentModelProperties properties = GWT.create(Fnd03_ConsignmentModelProperties.class);
	private Grid<Fnd03_ConsignmentModel> grid = this.buildGrid();
	private TextField searchText = new TextField();
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();

	public Fnd03_Tab_Consignment() {

		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "수탁기관명:", 400, 80, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
//		searchBarBuilder.addInsertButton(); 
		searchBarBuilder.addDeleteButton(); 
		searchBarBuilder.addInsertButton("파일UpLoad!", 100);
		
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,1));
		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		
		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				grid.mask("Loading");
				String actionUrl = "BizCodeUpload?companyId=" + LoginUser.getCompanyId();
				fileUpdownForm.submit(actionUrl, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						fileUpdownForm.setInit();
						grid.unmask();
						new SimpleMessage("확인", result.toString());
						retrieve();
					}
				});
			}
		});

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData .setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData .setSplit(true);
		centerLayoutData .setMaxSize(1000);
		this.setCenterWidget(vlc, centerLayoutData);
	
		searchText.setEmptyText("전체");
	}

	public Grid<Fnd03_ConsignmentModel> buildGrid(){
		
		GridBuilder<Fnd03_ConsignmentModel> gridBuilder = new GridBuilder<Fnd03_ConsignmentModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText		(properties.consignmentCode(),		80,	"수탁사코드",		new	TextField());
		gridBuilder.addText		(properties.consignmentName(),		250,	"수탁사명",		new	TextField());
		gridBuilder.addBoolean	(properties.consignmentUseYn(),		70,	"사용여부");
		gridBuilder.addText		(properties.consignmentNote(),		300,	"비고",			new	TextField());
		
		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void retrieve(){
		this.grid.mask("Loading");
		
		GridRetrieveData<Fnd03_ConsignmentModel> service = new GridRetrieveData<Fnd03_ConsignmentModel>(grid.getStore());
		service.addParam("consignmentName", searchText.getText()); 
		service.addParam("companyId", LoginUser.getCompanyId());
		service.retrieve("fnd.Fnd03_Consignment.selectByCodeName",new InterfaceCallback() { 
			@Override
			public void execute() {
				grid.unmask();
				if(grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0, false);
				}	
			}
		});
	}

	@Override
	public void update(){
		GridUpdate<Fnd03_ConsignmentModel> service = new GridUpdate<Fnd03_ConsignmentModel>(); 
		service.update(grid.getStore(), "fnd.Fnd03_Consignment.update"); 
	}

	@Override
	public void insertRow(){
		fileUpdownForm.click();
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Fnd03_ConsignmentModel> service = new GridDeleteData<Fnd03_ConsignmentModel>();
		List<Fnd03_ConsignmentModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd03_Consignment.delete");
	
	
//		ConfirmMessageBox messageBox = new ConfirmMessageBox("삭제확인", "선택한 내용을 삭제하시겠습니까?");
//		messageBox.addDialogHideHandler(new DialogHideHandler() {
//			@Override
//			public void onDialogHide(DialogHideEvent event) {
//				if(event.getHideButton().toString() == "YES") {
//					deleteSelectedRow();
//				}
//			}
//		});
//		messageBox.setWidth(300);
//		messageBox.show();
	}

}
