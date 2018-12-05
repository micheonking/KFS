package myApp.client.vi.pln;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.DBUtil;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.ClientDateUtil;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.JSCaller;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr01_ApprModelProperties;
import myApp.client.vi.sys.Lookup_MultiFile;

public class Pln00_Popup_PlanDoc extends Window implements Editor<Apr01_ApprModel>, InterfaceServiceCall {

	int oprFlag = 0;//운영보고서 상신 구분용 

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private InterfaceCallback callback;
	 
	String ApprovalTypeCd = "";
	String viewMode = "";
	String actionCode = "N";
	Long fileId = null;
	FormPanel formPanel = new FormPanel();
	
	interface EditDriver extends SimpleBeanEditorDriver<Apr01_ApprModel, Pln00_Popup_PlanDoc> {
	}

	EditDriver editDriver = GWT.create(EditDriver.class);
//	Dcr04_DocModel docModel = new Dcr04_DocModel();
	Apr01_ApprModel aprModel = new Apr01_ApprModel();

	private Grid<Apr01_ApprModel> apprGrid = this.buildGrid();
//	private Grid<Dcr04_DocModel> docGrid = this.buildDocGrid();
			
	//웹에디터 HTML 설정
	public interface HTMLTemplate extends XTemplates {
		@XTemplate("<iframe id='htmlTemplate' frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0 src='naverEditor.jsp' width='100%' height='100%'/>")
		SafeHtml getTemplate();
	}

	LongField   apprId       = new LongField();      //문서번호
	TextField   title        = new TextField();      //문서제목
	TextField   docTypeName  = new TextField();      //문서분류명
	MyDateField regDate      = new MyDateField();    //상신일
	TextField   statusCode   = new TextField();      //결재상태
	TextField   content      = new TextField();      //문서내용
	TextField   note         = new TextField();      //비고
    LongField   regEmpId     = new LongField();      //등록자사번
    TextField   regText      = new TextField();      //등록자 필드

	//생성자
	public Pln00_Popup_PlanDoc(Grid<Apr01_ApprModel> grid, String viewMode) {
//		this.docModel = docModel;
//		this.docTypeName.setText(docModel.getDocTypeName());
//		this.aprModel.setApprId(docModel.getDocId());
////		this.ApprovalTypeCd = docModel.getActionCell();
		this.viewMode = viewMode;
		
		getInitForm();
	}

	//운영보고서 생성자
	public Pln00_Popup_PlanDoc(Long apprId, String apprLineType, String viewMode) {
		this.aprModel.setContent("");
		this.aprModel.setApprId(apprId);
		this.ApprovalTypeCd = apprLineType;
		this.viewMode = viewMode;
		this.oprFlag = 1;
		getInitForm();
		retrieve("retrieveOpr");
//		docGrid.getColumnModel().getColumn(7).setHidden(true);
//   		fundGrid.getColumnModel().getColumn(8).setHidden(true);
	}
	
	public Pln00_Popup_PlanDoc(Long apprId, String apprLineType, String viewMode, InterfaceCallback callback) {
		this.callback = callback;
		this.aprModel.setContent("");
		this.aprModel.setApprId(apprId);
		this.ApprovalTypeCd = apprLineType;
		this.viewMode = viewMode;
		this.oprFlag = 1;
		getInitForm();
		retrieve("retrieveOpr");
//		docGrid.getColumnModel().getColumn(7).setHidden(true);
//   		fundGrid.getColumnModel().getColumn(8).setHidden(true);
	}
	
	//초기화면설정
	private void getInitForm() {
		this.aprModel.setContent("");
		this.setModal(true);
		this.setBorders(false);
		this.setResizable(false);
		this.setWidth(750);
		this.setHeight(820);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());

		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update();
			}
		});
		

		TextButton closeButton = new TextButton("닫기");
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.getButtonBar().add(closeButton);
		this.getButtonBar().add(updateButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);

		BorderLayoutContainer blc = new BorderLayoutContainer();
//		BorderLayoutData northLayoutData = new BorderLayoutData(0.5);
		
		
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(this.getEditor());
		
		BorderLayoutData centerLayoutData = new BorderLayoutData(0.5);
		centerLayoutData.setSplit(true);
		centerLayoutData.setMaxSize(7000);
		centerLayoutData.setMargins(new Margins(0, 2, 0, 0));
		blc.setCenterWidget(cp, centerLayoutData);
		
//		BorderLayoutData centerLayoutData = new BorderLayoutData(0.5);
//		centerLayoutData.setMargins(new Margins(3, 0, 0, 0)); // tabPage Top Margin
//		blc.setCenterWidget(getCenterLayout(), centerLayoutData);
		/*this.add(this.getEditor());*/
		editDriver.initialize(this);
		editDriver.edit(aprModel);
		
		
		this.add(blc);
		
		if ("new".equals(viewMode)) {
			create();
		} else if ("edit".equals(viewMode)) {
//			edit();
		} else if("view".equals(viewMode)){
//			view();
		}	
		
//		this.docGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Dcr04_DocModel>(){
//			@Override
//			public void onSelectionChanged(SelectionChangedEvent<Dcr04_DocModel> event) {
//				if(event.getSelection().size() > 0){
////					retrieveFundGrid();
//				}
//			} 
//		});
	}
	
	//폼 화면 설정
	private FormPanel getEditor() {
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(250, -1); // 컬럼크기
		rowLayout.setMargins(new Margins(0, 5, 5, 0)); // 컬럼간의 간격조정
		HorizontalLayoutData rowLayout2 = new HorizontalLayoutData(400, -1); // 컬럼2개 병합
		rowLayout2.setMargins(new Margins(0, 5, 5, 0)); // 컬럼간의 간격조정
		HorizontalLayoutData htmlLayout = new HorizontalLayoutData(720, 360); // HTML Editor
		htmlLayout.setMargins(new Margins(0, 5, 5, 0)); // 컬럼간의 간격조정

		// HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();

		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(title, "상신제목"), rowLayout2);
        row01.add(new FieldLabel(regDate, "상신일"), rowLayout);
        regDate.setEnabled(false);
        if("new".equals(viewMode)) {
        	regText.setText(LoginUser.getUserName() + "(" +LoginUser.getOrgKorName() + ")");
        }
        regText.setEnabled(false);
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(docTypeName, "분류명"), rowLayout2);
		docTypeName.setEnabled(false);
		row02.add(new FieldLabel(regText, "등록자"), rowLayout);

		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new LabelToolItem("상신내용"));

		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate());
		row04.add(htmlLayoutContainer, htmlLayout);
		
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
//		TextButton insertButton = new TextButton("신규문서등록");
//		insertButton.setWidth(90);
//		insertButton.addSelectHandler(new SelectHandler(){
//			@Override
//			public void onSelect(SelectEvent event) {
//				lineSetting(); 
//			}
//		});
		if("new".equals(viewMode)) {
//			row06.add(insertButton);
		} else if("view".equals(viewMode)) {
			
		} else if("edit".equals(viewMode)) {
			
		}

		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
//		row07.add(buildDocGrid(), new HorizontalLayoutData(1, 105));

		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);

		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row06, new VerticalLayoutData(1, -1, new Margins(120, 15, 15, 15)));
		layout.add(row06, new VerticalLayoutData(1, -1, new Margins(350, 15, 15, 15)));
		layout.add(row07, new VerticalLayoutData(1, -1, new Margins(15)));

		// form setting
		formPanel.setWidget(layout);
		formPanel.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		formPanel.setSize("800", "800");
		return formPanel;
		
	}

	private BorderLayoutContainer getCenterLayout() {
		BorderLayoutContainer blc = new BorderLayoutContainer();
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		ButtonBar dcrButtonBar = new ButtonBar();
//		TextButton dcrInsertButton = new TextButton("신규문서 등록");
//		dcrInsertButton.setWidth(100);
//		dcrInsertButton.addSelectHandler(new SelectHandler(){
//			@Override
//			public void onSelect(SelectEvent event) {
//				insertDcrRow(); 
//			}
//		});
		
//		dcrButtonBar.add(dcrInsertButton);
		VerticalLayoutData dcrGridLayout = new VerticalLayoutData();
		dcrGridLayout.setHeight(200);
		vlc.add(dcrButtonBar);
//		vlc.add(docGrid, dcrGridLayout);
		
		
		VerticalLayoutData fundGridLayout = new VerticalLayoutData();
		fundGridLayout.setHeight(200);
//		vlc.add(fundGrid, fundGridLayout);
		return blc;
	}
	
    private Grid<Apr01_ApprModel> buildGrid(){
		Apr01_ApprModelProperties properties = GWT.create(Apr01_ApprModelProperties.class);		
		GridBuilder<Apr01_ApprModel> gridBuilder = new GridBuilder<Apr01_ApprModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		return gridBuilder.getGrid(); 
	}
    
    //문서 그리드
//    private Grid<Dcr04_DocModel> buildDocGrid() {
//    	Dcr04_DocModelProperties properties = GWT.create(Dcr04_DocModelProperties.class);		
//		GridBuilder<Dcr04_DocModel> gridBuilder = new GridBuilder<Dcr04_DocModel>(properties.keyId());  
//
//		gridBuilder.setChecked(SelectionMode.SINGLE);
//		
//		gridBuilder.addText(properties.docTypeName(), 100, "분류명");
//		gridBuilder.addText(properties.docName(), 250, "문서명");
//		ActionCell<String> editDocCell = new ActionCell<String>("View", new ActionCell.Delegate<String>(){
//			@Override
//			public void execute(String arg0) {
////				if("new".equals(viewMode) && oprFlag == 0) {
////					Dcr04_Edit_Doc editor = new Dcr04_Edit_Doc(docGrid, docGrid.getSelectionModel().getSelectedItem(), "new");
////					editor.show();
////				} else {
////					Dcr04_Edit_Doc editor = new Dcr04_Edit_Doc(docGrid, docGrid.getSelectionModel().getSelectedItem(), "view");
////					editor.show();
////				}
//			}
//		});
//		gridBuilder.addText(properties.docName(), 100, "첨부파일");
//		
//		ActionCell<String> downCell = new ActionCell<String>("Down", new ActionCell.Delegate<String>(){
//			@Override
//			public void execute(String arg0) {
//				if(docGrid.getSelectionModel().getSelectedItem().getFileModel().getFileId() != null) {
//					
//					formPanel.setEncoding(Encoding.MULTIPART);
//					formPanel.setMethod(Method.POST);
//					String actionUrl = "FileDownload?fileId=" + docGrid.getSelectionModel().getSelectedItem().getFileModel().getParentId();
//					
//					formPanel.setAction(actionUrl);
//					formPanel.submit();
//				} else {
//					new SimpleMessage("등록된 첨부파일이 없습니다");
//				}
//			}
//		});
//		ActionCell<String> deleteCell = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
//			@Override
//			public void execute(String arg0) {
//				if(docGrid.getSelectionModel().getSelectedItem() != null) {
//					deleteDcrRow();
//				} else {
//					new SimpleMessage("선택된 문서가 없습니다");
//				}
//			}
//		});
//		
////		gridBuilder.addCell(properties.downCell(), 80, "파일받기", downCell); //, new TextField()) ;
//		gridBuilder.addCell(properties.actionCell(), 60, "상세", editDocCell); //, new TextField()) ;
////		gridBuilder.addCell(properties.deleteCell(), 60, "삭제", deleteCell); //, new TextField()) ;
//		
//		return gridBuilder.getGrid(); 
//	}
    
    //상신화면일 경우    ///?????
    private void create() {
    	this.setHeading("e-mail 보내기");
    	statusCode.setValue("10");
    	regDate.setText(ClientDateUtil.getToday(new Date()));
    	regDate.setValue(ClientDateUtil.getToday());
//    	setIninLine(apprStepGrid);
    	regEmpId.setValue(LoginUser.getUserId());
    	JSCaller.setCallback(null);
    	JSCaller.setCallback(new InterfaceCallback() {
   			@Override
   			public void execute() {
   				changeEditorHeight(300);
   			}
   		});
    }
   	
    //문서정보 조회
    private void retrieve(String str) {
    	actionCode = str;
    	
    	ServiceCall service = new ServiceCall();
    	ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.selectById");
    	request.putLongParam("apprId", this.aprModel.getApprId());
    	service.execute(request, this);
    	
//    	GridRetrieveData<Dcr04_DocModel> service2 = new GridRetrieveData<Dcr04_DocModel>(docGrid.getStore());
//	    
//    	service2.addParam("companyId", LoginUser.getCompanyId());
//	    service2.addParam("empId", LoginUser.getUserId());
//	    service2.addParam("apprId", this.aprModel.getApprId());
//	    service2.addParam("docTypeId", this.docModel.getDocTypeId());
//	    service2.retrieve("dcr.Dcr04_Doc.selectByApprId");
//	    retrieveRelateDocGrid();
    }
    
    
    //상신하기
	private void update() {
		String htmlText = getHtmlText();
		content.setValue(htmlText);

		if(title.getText() == null || "".equals(title.getText())) {
			new SimpleMessage("제목은 반드시 입력하셔야 합니다."); 
			return;
		}
		
		if(regDate.getText() == null || "".equals(regDate.getText())) {
			new SimpleMessage("상신일은 반드시 입력하셔야 합니다."); 
			return;
		}
		
		if(content.getText() == null || "".equals(content.getText())) {
			new SimpleMessage("내용은 반드시 입력하셔야 합니다."); 
			return; 
		}

		actionCode = "insert";
		
		ServiceRequest request = new ServiceRequest("apr.Apr01_Appr.update");
		List<GridDataModel> apprList = new ArrayList<GridDataModel>();
		for(GridDataModel data : apprGrid.getStore().getAll()) {
			apprList.add(data);
		}
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("apprGrid", apprList);
		List<GridDataModel> apprStepList = new ArrayList<GridDataModel>();
//		for(GridDataModel data : apprStepGrid.getStore().getAll()) {
//			apprStepList.add(data);
//		}
		tempMap.put("apprStepGrid", apprStepList);
		
		request.setParam(tempMap);
		request.putModelParam("apprGrid", editDriver.flush());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	//신규문서 삭제
	private void deleteDcrRow() {
//	    GridDeleteData<Dcr04_DocModel> service = new GridDeleteData<Dcr04_DocModel>();
//	    List<Dcr04_DocModel> checkedList = this.docGrid.getSelectionModel().getSelectedItems() ; 
//	    service.delete(this.docGrid.getStore(), checkedList, "dcr.Dcr04_Doc.delete");
	}
	
	// 에디터 내용 가져오기
	private native String getHtmlText() /*-{
		return $doc.getElementById("htmlTemplate").contentWindow.sendToServer();
	}-*/;

	// 에디터 내용 보내주기
	private native void setHtmlText(String str) /*-{
    		$doc.getElementById("htmlTemplate").contentWindow.setHtmlText(str);
	}-*/;
	
	// 에디터 읽기 전용으로 설정(미적용)
	private native void readOnly(String str) /*-{
	        $doc.getElementById("htmlTemplate").contentWindow.readOnly(str);
    }-*/;
	
	// 에디터 높이 조정
	private native void changeEditorHeight(int height) /*-{
	        $doc.getElementById("htmlTemplate").contentWindow.changeHeight(height);
    }-*/;
	
	@Override
	public void getServiceResult(ServiceResult result) {
        if("appr".equals(actionCode)) {
        	if(callback != null) {
        		callback.execute();
        	}
        	hide();
		}
        if("reject".equals(actionCode)) {
        	if(callback != null) {
        	    callback.execute();
        	}
        	hide();
        }
        if("insert".equals(actionCode)) {
        	if(callback != null) {
        	    callback.execute();
        	}
        	hide();
        }
        if("retrieveOpr".equals(actionCode)) {
        	initData(result);
        }
		if("view".equals(viewMode)) {
			initData(result);
		} else if("edit".equals(viewMode)) {
			initData(result);
		}
	}
	
	//첫화면 초기값 설정
	private void initData(ServiceResult result) {
		if(actionCode.equals("retrieve")) {
			aprModel = (Apr01_ApprModel) result.getResult(0);
			title.setText(aprModel.getTitle());
			title.setValue(aprModel.getTitle());
			title.disable();
			regDate.setValue(aprModel.getRegDate());
			content.setText(aprModel.getContent());
			content.setValue(aprModel.getContent());
			setHtmlText(aprModel.getContent());
			readOnly(aprModel.getContent());
			regText.setText("(" + aprModel.getEmpInfoModel().getKorName() + ")" + aprModel.getEmpInfoModel().getOrgName());
			if(viewMode.equals("edit") || viewMode.equals("view")) {
//				setApprLine(apprStepGrid);
//				retrieveCount();
//				if(docGrid.getStore().size() > 0) {
//					docTypeName.setText(docGrid.getStore().get(0).getDocTypeName());
//				}
			}
		}
		if("retrieveOpr".equals(actionCode)) {
			aprModel = (Apr01_ApprModel) result.getResult(0);
			title.setText(aprModel.getTitle());
			title.setValue(aprModel.getTitle());			
		}
		
	}
	
	//신규문서 등록
	private void insertDcrRow() {
//		Dcr04_DocModel docModel = new Dcr04_DocModel();
//		docModel.setDocTypeId(this.docModel.getDocTypeId());
//		docModel.setApprId(this.apprId.getValue());
//		DBUtil dbUtil = new DBUtil(); 
//		dbUtil.setSeq(docModel, new InterfaceCallback() {
//			@Override
//			public void execute() {
//				docModel.setDocTypeName(docTypeName.getText());//문서분류명 설정
//				docModel.setRegEmpId(LoginUser.getUserId()); // 등록자ID 설정
//				docModel.setRegEmpName(LoginUser.getUserName());//등록자명 설정
//				docModel.setActionCell("신규문서 등록");
				
//				Dcr04_Edit_Doc editor = new Dcr04_Edit_Doc(docGrid, docModel, "new", new InterfaceCallback() {
//					@Override
//					public void execute() {
//						// TODO Auto-generated method stub
////						retrieveFundGrid();
//					}
//				});
//				editor.show();
				
//			}
//		});
	}
	
}