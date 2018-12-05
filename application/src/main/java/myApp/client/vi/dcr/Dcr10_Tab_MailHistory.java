package myApp.client.vi.dcr;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates.XTemplate;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.field.MyDateField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.JSCaller;
import myApp.client.vi.LoginUser;
import myApp.client.vi.dcr.model.Dcr10_MailHistoryModel;
import myApp.client.vi.dcr.model.Dcr10_MailHistoryModelProperties;
import myApp.client.vi.pln.Pln03_Lookup_ResrchDetail.HTMLTemplate;

public class Dcr10_Tab_MailHistory extends BorderLayoutContainer implements InterfaceGridOperate {

    private Dcr10_MailHistoryModelProperties properties = GWT.create(Dcr10_MailHistoryModelProperties.class);
	private Grid<Dcr10_MailHistoryModel> grid = this.buildGrid();
	private MyDateField startDateField = new MyDateField();
	private MyDateField endDateField = new MyDateField();
	private TextField titleTextField = new TextField();

	//웹에디터 HTML 설정
	public interface HTMLTemplate extends XTemplates {
//		@XTemplate("<iframe id='htmlTemplate' frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0 src='naverEditor.jsp' width='100%' height='100%'/>")
//		SafeHtml getTemplate();
		@XTemplate("<iframe id='tempHtml' framespacing=0 marginheight=0 marginwidth=0 scrolling=auto vspace=0 src='htmlReceiver.jsp' width='99%' height='99%' style='border-top:1px solid #ebebec; border-right:1px solid #ebebec; border-bottom:1px solid #ebebec; border-left:0px;'/>")
		SafeHtml getTemplate(String html);
	}

	public Dcr10_Tab_MailHistory() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
		Date startDate = CalendarUtil.copyDate(LoginUser.getToday());
        CalendarUtil.setToFirstDayOfMonth(startDate);
        startDateField.setValue(startDate);
        
		Date endDate = CalendarUtil.copyDate(LoginUser.getToday());
		CalendarUtil.setToFirstDayOfMonth(endDate);
		CalendarUtil.addMonthsToDate(endDate, 1);
		CalendarUtil.addDaysToDate(endDate, -1);
        endDateField.setValue(endDate);

        searchBarBuilder.getSearchBar().add(new LabelToolItem("등록일자:")); 
        startDateField.setWidth(100);
        searchBarBuilder.getSearchBar().add(startDateField);
        endDateField.setWidth(100);
		searchBarBuilder.getSearchBar().add(endDateField);
		searchBarBuilder.getSearchBar().add(titleTextField);
		titleTextField.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) {
					retrieve();
				}
			}
		});
		
		searchBarBuilder.addRetrieveButton(); 

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 50, new Margins(0)));
		vlc.add(grid, new VerticalLayoutData(1,1));
		grid.addRowClickHandler(new RowClickHandler() {
			@Override
			public void onRowClick(RowClickEvent event) {
				Dcr10_MailHistoryModel mailModel = grid.getSelectionModel().getSelectedItem();
				setHtmlText(mailModel.getBodyText());
			}
		});

		BorderLayoutData northLayoutData = new BorderLayoutData();
		northLayoutData.setSplit(true);
		northLayoutData.setMargins(new Margins(0,0,2,0));
		northLayoutData.setSize(400);
		northLayoutData.setMaxSize(700);
		this.setNorthWidget(vlc,northLayoutData);
		
//		BorderLayoutData westLayoutData = new BorderLayoutData();
//		westLayoutData.setSplit(true);
//		westLayoutData.setSize(950);
//		westLayoutData.setMaxSize(1500);
//		westLayoutData.setMargins(new Margins(0,2,0,0));
//		this.setWestWidget(this.grid, westLayoutData);

		SearchBarBuilder searchBarBuilder2 = new SearchBarBuilder(this);
		LabelToolItem bodyTextTitle = new LabelToolItem("▶ 내용 (미리보기 화면으로 실제화면과 차이가 있을 수 있습니다.)");
		bodyTextTitle.setWidth(300);
		searchBarBuilder2.getSearchBar().add(bodyTextTitle);
		
		VerticalLayoutContainer centerVlc = new VerticalLayoutContainer();
		centerVlc.add(searchBarBuilder2.getSearchBar(), new VerticalLayoutData(1, 40, new Margins(0)));

		ContentPanel contenPanel = new ContentPanel();
		contenPanel.setHeaderVisible(false);
		contenPanel.setBorders(false);

		ContentPanel contenPanelIn = new ContentPanel();
		contenPanelIn.setHeaderVisible(false);
		contenPanelIn.setBorders(false);

		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate(""));
		JSCaller.setCallback(new InterfaceCallback() {
   			@Override
   			public void execute() {
   				Dcr10_MailHistoryModel mailModel = grid.getSelectionModel().getSelectedItem();
   				if (mailModel != null) {
   					setHtmlText(mailModel.getBodyText());
   				}
   			}
   		});
		
		contenPanelIn.setWidget(htmlLayoutContainer);
		centerVlc.add(contenPanelIn, new VerticalLayoutData(1, 1, new Margins(10,30,10,30)));
		contenPanel.setWidget(centerVlc);
		this.setCenterWidget(contenPanel);
		
		titleTextField.setValue("");
		retrieve();
	}
	
	public Grid<Dcr10_MailHistoryModel> buildGrid(){
		
		GridBuilder<Dcr10_MailHistoryModel> gridBuilder = new GridBuilder<Dcr10_MailHistoryModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

//		gridBuilder.addText	(properties.senderEmail(),		130,	"보내는사람"		);
		gridBuilder.addText	(properties.sendTimeString(),	150,	"보낸시간"			);
		gridBuilder.addText	(properties.receiverEmail(),	280,	"받는사람"			);
		gridBuilder.addText	(properties.referencerEmail(),	280,	"참조"			);
		gridBuilder.addText	(properties.titleText(),		400,	"제목"			);
		gridBuilder.addText	(properties.bodyText(),			250,	"내용(하단에 표시)" );

		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve(){
		grid.mask("Loading");
		GridRetrieveData<Dcr10_MailHistoryModel> service = new GridRetrieveData<Dcr10_MailHistoryModel>(grid.getStore());
		service.addParam("empId", LoginUser.getUserId());
		service.addParam("startDate", startDateField.getText());
		service.addParam("endDate", endDateField.getText());
		service.addParam("titleText", titleTextField.getText());
		service.retrieve("dcr.Dcr10_MailHistory.selectBySenderEmail", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
				if (grid.getStore().get(0) != null) {
					grid.getSelectionModel().select(0,false);
				}
			}
		});
	}

	@Override
	public void update(){
	}

	@Override
	public void insertRow(){
	}

	@Override
	public void deleteRow(){
	}

	// 에디터 내용 가져오기
//	private native String getHtmlText() /*-{
//		return $doc.getElementById("htmlTemplate").contentWindow.sendToServer();
//	}-*/;

	// 에디터 내용 보내주기
//	private native void setHtmlText(String str) /*-{
//    		$doc.getElementById("htmlTemplate").contentWindow.setHtmlText(str);
//	}-*/;
	
	// 에디터 내용 보내주기
	private native void setHtmlText(String str) /*-{
    		$doc.getElementById("tempHtml").contentWindow.setHtml(str);
	}-*/;

	// 에디터 읽기 전용으로 설정
//	private native void readOnly(String str) /*-{
//	        $doc.getElementById("htmlTemplate").contentWindow.readOnly(str);
//    }-*/;

	// 에디터 높이 조정
//	private native void changeEditorHeight(int height) /*-{
//	        $doc.getElementById("htmlTemplate").contentWindow.changeHeight(height);
//    }-*/;

}