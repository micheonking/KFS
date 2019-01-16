package myApp.client.vi.cst;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.cst.model.Cst01_UserModel;
import myApp.client.vi.cst.model.Cst99_UserModelProperties;

public class Cst99_Tab_account extends BorderLayoutContainer implements Editor<Cst01_UserModel>,InterfaceGridOperate {
	

    private Cst99_UserModelProperties properties = GWT.create(Cst99_UserModelProperties.class);

	private Cst99_Edit_account editAccount = new Cst99_Edit_account();
	
	Cst01_UserModel userModel = new Cst01_UserModel();
	TextField email = new TextField();
	TextField userName = new TextField();
	TextField phoneNo = new TextField();
	DateField startDt = new DateField();
	TextButton joinButton = new TextButton();

	
	
	public Cst99_Tab_account() {

		this.setBorders(false);
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addRetrieveButton();
		
		
		TextButton updateButton = new TextButton("회원가입");
		updateButton.setWidth(80);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				Cst99_Tab_Join joinPage = new Cst99_Tab_Join();
				joinPage.open(userModel, new InterfaceCallbackResult() {
					
					@Override
					public void execute(Object result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}); 
		
		searchBarBuilder.getSearchBar().add(updateButton);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50));
		this.setCenterWidget(this.getEditor(), new BorderLayoutData(50));

		BorderLayoutData southLayoutData = new BorderLayoutData(320);	//	전체화면의 60%
		southLayoutData.setMargins(new Margins(2, 0, 0, 0)); 
		southLayoutData.setMaxSize(900);
		southLayoutData.setSplit(true);
		this.setSouthWidget(editAccount, southLayoutData);
		
	}

	private Widget getEditor() {
		
		HorizontalLayoutData rowLayout  = new HorizontalLayoutData(500, -1, new Margins(10, 20, 5, 0));	// Column Size Define

		HorizontalLayoutContainer row00 = new HorizontalLayoutContainer();
		row00.add(new FieldLabel(email,"E-mail"),new HorizontalLayoutData(500, -1, new Margins(10, 20, 5, 0)));
		
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		row01.add(new FieldLabel(userName,"성명"),new HorizontalLayoutData(500, -1, new Margins(30, 20, 5, 0)));
		
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(phoneNo,"전화번호"),new HorizontalLayoutData(500, -1, new Margins(50, 20, 5, 0)));
		
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(startDt,"가입일자"),new HorizontalLayoutData(500, -1, new Margins(70, 20, 5, 0)));
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer(); 
		layout.setScrollMode(ScrollSupport.ScrollMode.AUTO);
		
		layout.add(row00, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(15)));
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(15)));
		
		ContentPanel form = new ContentPanel();
		form.setHeaderVisible(false);
		form.setWidget(layout);
		return form;
	}

	@Override
	public void retrieve(){
//		GridRetrieveData<Cst99_UserModel> service = new GridRetrieveData<Cst99_UserModel>(grid.getStore());
//		service.addParam("companyId"	, LoginUser.getCompanyId());
//
//		service.retrieve("cst.Cst01_User");
		Info.display("","userId"+LoginUser.getUserId());
		
	}
	@Override
	public void update(){
		
//		GridUpdate<Bbs01_BoardModel> service = new GridUpdate<Bbs01_BoardModel>();
//		service.update( grid.getStore(), editDriver.flush(),"bbs.Bbs01_Board.update");
//		
//		Info.display("","userId"+LoginUser.getUserId());
	}

	@Override
	public void insertRow(){
	}

	@Override
	public void deleteRow(){
	}

}