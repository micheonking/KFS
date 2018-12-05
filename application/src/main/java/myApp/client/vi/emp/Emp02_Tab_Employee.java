package myApp.client.vi.emp;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceTabPage;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp02_EmpModelProperties;

public class Emp02_Tab_Employee extends BorderLayoutContainer implements InterfaceGridOperate {
	
	private TextField searchText  = new TextField();
	
	private StringComboBox workTypeCode= new StringComboBox();
	
	private Grid<Emp02_EmpModel> grid = this.buildGrid();
	private Image image = new Image();
	private PlainTabPanel tabPanel = new PlainTabPanel();
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	public Emp02_Tab_Employee() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(searchText, "사원검색", 150, 60, true);
		
        workTypeCode.getStore().add("전체");
		workTypeCode.getStore().add("재직");
		workTypeCode.getStore().add("퇴직");
		workTypeCode.setTriggerAction(TriggerAction.ALL);
		workTypeCode.setText("재직");
		
		FieldLabel workTypeComboBox = new FieldLabel(workTypeCode, "재직구분"); 
		workTypeComboBox.setWidth(150);
		workTypeComboBox.setLabelWidth(60);
		searchBarBuilder.getSearchBar().add(workTypeComboBox);
		searchBarBuilder.addRetrieveButton();//조회버튼 
		searchBarBuilder.addInsertButton();

		// set SearchBar 
		BorderLayoutData northLayoutData = new BorderLayoutData(50); // default size is 49
		this.setNorthWidget(searchBarBuilder.getSearchBar(), northLayoutData);

		// set Grid 
		this.setCenterWidget(this.grid);

		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Emp02_EmpModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Emp02_EmpModel> event) {
				if(event.getSelection().size() > 0){
					retrieveTabpage(); 
				}
			} 
			
			
		}); 
		
		// set Image
		ContentPanel imagePanel = new ContentPanel();
		imagePanel.setBorders(true);
		imagePanel.setHeaderVisible(false);
	    image.setPixelSize(150, 200);
	    
	    image.setUrl("FileDownload?fileType=image"); // 디폴트 사진보여주기.
	    imagePanel.add(image);
	    
	    TextButton addImageButton = new TextButton("사진등록");
	    addImageButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				addUserImage(); 
			}
		});
	    imagePanel.addButton(addImageButton);
	    imagePanel.setButtonAlign(BoxLayoutPack.CENTER);
	    
	    imagePanel.getButtonBar().add(fileUpdownForm.getForm());
	    fileUpdownForm.getForm().setVisible(false);

	    // set Tab Panel 
		tabPanel.add(new Emp01_TabPage_Person(grid), "사원정보");

		Emp03_TabPage_Trans transPage = new Emp03_TabPage_Trans(new InterfaceCallback() {
			@Override
			public void execute() {
				retrieve(); 
			}
		}); 
		tabPanel.add(transPage, "발령내역");		
		
		tabPanel.addSelectionHandler(new SelectionHandler<Widget>(){
			@Override
			public void onSelection(SelectionEvent<Widget> arg0) {
				if(arg0 != null) {
					retrieveTabpage();
				} 
			}
		}); 
		// set South Layout 
		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
	    hlc.add(imagePanel, new HorizontalLayoutData(150, 250, new Margins(40, 10, 10, 10)));
		hlc.add(tabPanel, new HorizontalLayoutData(1, 1, new Margins(3, 0, 0, 0)));
		
		BorderLayoutData southLayoutData = new BorderLayoutData(450); 
		southLayoutData.setMargins(new Margins(2, 0, 0, 0)); 
		southLayoutData.setMaxSize(900);
		southLayoutData.setSplit(true);
		this.setSouthWidget(hlc, southLayoutData);
		
		retrieve();
	}
	
	public Grid<Emp02_EmpModel> buildGrid(){

		Emp02_EmpModelProperties properties = GWT.create(Emp02_EmpModelProperties.class);
		GridBuilder<Emp02_EmpModel> gridBuilder = new GridBuilder<Emp02_EmpModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.orgKorName(), 200, "부서명");
		gridBuilder.addText(properties.titleName(), 100, "직책");
		gridBuilder.addText(properties.empNo(), 80, "사번");
		gridBuilder.addText(properties.korName(), 100, "성명");
		gridBuilder.addText(properties.posName(), 100, "직위");
		gridBuilder.addText(properties.empKindName(), 80, "사원구분");
		gridBuilder.addText(properties.retireYn(), 60, "재직상태");
		gridBuilder.addText(properties.genderName(), 40, "성별");
		gridBuilder.addText(properties.ctzNo(), 100, "주민번호");
		gridBuilder.addText(properties.mobileTelno(), 120, "헨드폰번호");
		gridBuilder.addText(properties.emailAddr(), 150, "이메일주소");
		gridBuilder.addDate(properties.hireDate(), 80, "입사일");
		gridBuilder.addText(properties.hireName(), 80, "입사구분");
		gridBuilder.addDate(properties.retireDate(), 80, "퇴사일");
		gridBuilder.addText(properties.retireName(), 80, "퇴사구분");

		return gridBuilder.getGrid();
		
	}
	
	private void retrieveTabpage(){

		InterfaceTabPage selectedPage = (InterfaceTabPage)tabPanel.getActiveWidget();
		Emp02_EmpModel empModel = this.grid.getSelectionModel().getSelectedItem();

		if(empModel != null) {
			// 사진 보여주기 
			image.setUrl("FileDownload?fileType=image&parentId=" + empModel.getPersonId() + "&time=" +  System.currentTimeMillis());
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("empModel", empModel); 
			selectedPage.retrieve(param);
		}
		else {
			image.setUrl("FileDownload?fileType=image&time=" +  System.currentTimeMillis());
			selectedPage.init();
		}
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<Emp02_EmpModel> service = new GridRetrieveData<Emp02_EmpModel>(this.grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("searchText", searchText.getText());
		
		if("재직".equals(workTypeCode.getText())){
			service.addParam("workTypeCode", "WORK");
		} else if("퇴직".equals(workTypeCode.getText())) {
			service.addParam("workTypeCode", "RETIRE");
		}  
		else { 
			service.addParam("workTypeCode", "ALL");
		} 
		
		service.retrieve("emp.Emp02_Emp.selectByText", new InterfaceCallback() {
			@Override
			public void execute() {
				if(grid.getSelectionModel().getSelectedItems().size() < 1) { 
					
					// 조회한 후 선택된 행이 없으면 텝페이지를 초기화 한다. 
					image.setUrl("FileDownload?fileType=image&time=" +  System.currentTimeMillis());
					InterfaceTabPage selectedPage = (InterfaceTabPage)tabPanel.getActiveWidget();
					selectedPage.init();
				}
			}
		});
	}
	
	@Override
	public void insertRow() {
		
		Emp02_EmpModel empModel = new Emp02_EmpModel();
		
		DBUtil dbUtil = new DBUtil(); 
		dbUtil.setSeq(empModel, new InterfaceCallback() {
			@Override
			public void execute() {
				// 초기값 설정. 
				empModel.getPersonModel().setCompanyId(LoginUser.getCompanyId());
				empModel.getTransModel().setTransCode("40"); // 채용구분 초기값 설정 

				Emp02_Edit_Employee editor = new Emp02_Edit_Employee(grid, empModel);
				editor.show();
			}
		});
	}
	
	@Override
	public void update() {
	}
	@Override
	public void deleteRow(){
	}

	private void addUserImage(){
		Emp02_EmpModel empModel = this.grid.getSelectionModel().getSelectedItem();
		
		if(empModel != null){
			fileUpdownForm.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					String actionUrl = "FileUpload?fileType=image&parentId=" + empModel.getPersonId()+ "&time=" +  System.currentTimeMillis();
					fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
						@Override
						public void execute() {
//							Info.display("upload", "upload");
							image.setUrl("FileDownload?fileType=image&parentId=" + empModel.getPersonId() + "&time=" +  System.currentTimeMillis());
						}
					});
				}
			}); 

		    fileUpdownForm.click();
		}
		else {
//			Info.display("대상확인", "사진등록할 대상을 먼저 선택하여 주세요.");
			new SimpleMessage("대상확인", "사진등록할 대상을 먼저 선택하여 주세요.");
			return;
		}
	}
}