package myApp.client.vi.fnd;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.PDFViewer;
import myApp.client.vi.fnd.model.Fnd06_OfficeWorkModel;
import myApp.client.vi.fnd.model.Fnd06_OfficeWorkModelProperties;

public class Fnd06_Tab_OfficeWork extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd06_OfficeWorkModelProperties properties = GWT.create(Fnd06_OfficeWorkModelProperties.class);
	private Grid<Fnd06_OfficeWorkModel> grid = this.buildGrid();
	private TextField searchText = new TextField();
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	
	public Fnd06_Tab_OfficeWork() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "사무수탁사명:", 400, 80, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
//		searchBarBuilder.addInsertButton(); 
		searchBarBuilder.addDeleteButton(); 
		
		TextButton PDFButton = new TextButton("Print");
		PDFButton.setWidth(100);
		PDFButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				PDFViewer viewer = new PDFViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				viewer.open("className=fnd.Fnd06_OfficeWorkPDF&searchText=" + searchText);
				
			}
		});
		searchBarBuilder.getSearchBar().add(PDFButton);
		searchBarBuilder.addInsertButton("파일UpLoad", 100);
		
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,1));
		
		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				grid.mask("Loading");
				String actionUrl = "OfficeWorkUpload?companyId=" + LoginUser.getCompanyId();
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

	public Grid<Fnd06_OfficeWorkModel> buildGrid(){
		
		GridBuilder<Fnd06_OfficeWorkModel> gridBuilder = new GridBuilder<Fnd06_OfficeWorkModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	(properties.officeWorkCode(),	150,	"사무수탁사 코드",		new	TextField());
		gridBuilder.addText	(properties.officeWorkName(),	250,	"사무수탁사명",			new	TextField());
		gridBuilder.addBoolean(properties.officeWorkUseYn(),70,	"사용여부" );
		gridBuilder.addText	(properties.officeWorkNote(),	300,	"비고",				new	TextField());
		return gridBuilder.getGrid(); 
	}
	
	protected void PDF() {
		
	}
	
	@Override
	public void retrieve(){
		this.grid.mask("Loading");
		
		GridRetrieveData<Fnd06_OfficeWorkModel> service = new GridRetrieveData<Fnd06_OfficeWorkModel>(grid.getStore());
		service.addParam("officeWorkName", searchText.getText()); 
		service.addParam("companyId", LoginUser.getCompanyId());
		service.retrieve("fnd.Fnd06_OfficeWork.selectByCodeName",new InterfaceCallback() {
		
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
		GridUpdate<Fnd06_OfficeWorkModel> service = new GridUpdate<Fnd06_OfficeWorkModel>(); 
		service.update(grid.getStore(), "fnd.Fnd06_OfficeWork.update"); 
	}

	@Override
	public void insertRow(){
		fileUpdownForm.click();
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Fnd06_OfficeWorkModel> service = new GridDeleteData<Fnd06_OfficeWorkModel>();
		List<Fnd06_OfficeWorkModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd06_OfficeWork.delete");
	}

}
