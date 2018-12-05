package myApp.client.vi.fnd;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.ComboBoxField;
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
import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModel;
import myApp.client.vi.fnd.model.Fnd07_IssuingEntityModelProperties;

public class Fnd07_Tab_IssuingEntity extends BorderLayoutContainer implements InterfaceGridOperate {

    private Fnd07_IssuingEntityModelProperties properties = GWT.create(Fnd07_IssuingEntityModelProperties.class);
	private Grid<Fnd07_IssuingEntityModel> grid = this.buildGrid();
	private TextField		codeNameField		= new TextField();
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	public Fnd07_Tab_IssuingEntity() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(codeNameField, "발행기관명:", 400, 80, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addDeleteButton(); 
		searchBarBuilder.addInsertButton("파일UpLoad!", 100);
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 

		BorderLayoutData centerLayoutData = new BorderLayoutData(0.4);
		centerLayoutData .setMargins(new Margins(0, 4, 0, 0));
		centerLayoutData .setSplit(true);
		centerLayoutData .setMaxSize(1000);

		
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,1));
		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		
		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				grid.mask("Loading");
				String actionUrl = "IssuingEntityUpload?companyId=" + LoginUser.getCompanyId();
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
		
		this.setCenterWidget(vlc, centerLayoutData);

		codeNameField.setEmptyText("전체");
	}

	public Grid<Fnd07_IssuingEntityModel> buildGrid(){
		
		GridBuilder<Fnd07_IssuingEntityModel> gridBuilder = new GridBuilder<Fnd07_IssuingEntityModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText		(properties.issuingEntityCode(),	90,	"발행기관코드",	new	TextField());
		gridBuilder.addText		(properties.issuingEntityName(),	250,"발행기관명",	new	TextField());
		
		
		gridBuilder.addText		(properties.issuingEntityAttachCode(),		80,	"소속구분",		new	TextField());
		gridBuilder.addLong		(properties.issuingEntityFaceValue(),			80,	"액면가",		new	LongField());
		gridBuilder.addText		(properties.issuingEntitySettleMonth(),		80,	"결산월",		new	TextField());
		
		ComboBoxField nationComboBox = new ComboBoxField("NationCode");
		nationComboBox.addCollapseHandler(new CollapseHandler() {

			@Override
			public void onCollapse(CollapseEvent event) {
//				grid.getSelectionModel().getSelectedItem().setIssuingEntityNationCode(issuingEntityNationCode);(nationComboBox.getCode());
//				grid.getView().refresh(true);
//			
			}
			
			
			
		});
		
		gridBuilder.addText		(properties.kukaName(),		80,	"국가코드",	nationComboBox);
		gridBuilder.addBoolean	(properties.issuingEntityUseYn(),		70,	"사용여부");
		gridBuilder.addText		(properties.issuingEntityNote(),			300,	"비고",			new	TextField());
		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Fnd07_IssuingEntityModel> service = new GridRetrieveData<Fnd07_IssuingEntityModel>(grid.getStore());
		service.addParam("issuingEntityName", codeNameField.getText()); 
		service.retrieve("fnd.Fnd07_IssuingEntity.selectByCodeName", new InterfaceCallback() {
			
			@Override
			public void execute() {
				grid.unmask();
				
			}
		});
	}

	@Override
	public void update(){
		GridUpdate<Fnd07_IssuingEntityModel> service = new GridUpdate<Fnd07_IssuingEntityModel>(); 
		service.update(grid.getStore(), "fnd.Fnd07_IssuingEntity.update"); 
	}

	@Override
	public void insertRow(){
		fileUpdownForm.click();
		
//		GridInsertRow<Fnd07_IssuingEntityModel> service = new GridInsertRow<Fnd07_IssuingEntityModel>(); 
//		Fnd07_IssuingEntityModel fundModel = new Fnd07_IssuingEntityModel();
//		service.insertRow(grid, fundModel);
	}

	@Override
	public void deleteRow(){
		GridDeleteData<Fnd07_IssuingEntityModel> service = new GridDeleteData<Fnd07_IssuingEntityModel>();
		List<Fnd07_IssuingEntityModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "fnd.Fnd07_IssuingEntity.delete");
	}
}
