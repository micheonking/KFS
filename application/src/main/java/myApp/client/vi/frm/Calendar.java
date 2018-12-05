package myApp.client.vi.frm;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceCallback;
import myApp.client.utils.ClientDateUtil;
import myApp.client.utils.JSCaller;
import myApp.client.vi.LoginUser;
import myApp.client.vi.MenuOpener;

public class Calendar extends BorderLayoutContainer  {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private String empId = String.valueOf(LoginUser.getUserId());
	private String orgCd = String.valueOf(LoginUser.getOrgCode());

	private Monitoring_Page_ToDoList 	toDoGrid;
	private Monitoring_Page_Outstanding outstandingGrid;
	private Monitoring_Page_Appr		apprGird;
	
	//웹에디터 HTML 설정
	public interface HTMLTemplate extends XTemplates {
		@XTemplate("<iframe id='calendarTemplate' frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0 src='fullcalendar.jsp' width='100%' height='100%'/>")
		SafeHtml getTemplate();
	}

	public Calendar() {
		init();
	}

	public Calendar(Monitoring_Page_ToDoList toDoGrid, Monitoring_Page_Outstanding outstandingGrid, Monitoring_Page_Appr apprGird) {
		this.toDoGrid = toDoGrid;
		this.outstandingGrid = outstandingGrid;
		this.apprGird = apprGird;
		init();
	}

	private void init() {
		makeLayout();    
		JSCaller.setCallback(new InterfaceCallback() {
   			@Override
   			public void execute() { 
   				retrieve();
   				JSCaller.setCallback(null);
   			}
   		});
		JSCaller.setCallback2(new InterfaceCallback() {
   			@Override
   			public void execute() {
   				
   				String selectDate = JSCaller.returnParameter;
   				
   				if (selectDate == null) {
   					allRetrieve();
   				} else {
   					toDoListRetrieve(selectDate);
   				}
   			}
   		});
	}
	
	private void makeLayout() {
		
		HTMLTemplate htmlTemplate = GWT.create(HTMLTemplate.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(htmlTemplate.getTemplate());
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(htmlLayoutContainer);
		
		this.add(cp);
	}
	
	private void toDoListRetrieve(String date) {
		Date selectDate = ClientDateUtil.toDate(date);
		toDoGrid.retrieve(selectDate);
	}
	
	private void allRetrieve() {
		this.retrieve();
		toDoGrid.retrieve();
		outstandingGrid.retrieve();
		apprGird.retrieve();
	}
	
	public void retrieve() {
		getViewDate(empId, orgCd);
	}

	// 달력조회
	private native String getViewDate(String empId, String orgCd) /*-{
		return $doc.getElementById("calendarTemplate").contentWindow.getViewDate(empId, orgCd);
	}-*/;

		
}