package myApp.client.vi.sys;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys02_UserModel;
import myApp.client.vi.sys.model.Sys02_UserModelProperties;

/*
 * 회사별 관리자 정보를 등록한다. 
 * 관리자는 권한과 무관하게 회사를 관리하는 페이지를 제외하고 모든 메뉴를 열수 있다. 
 */

public class Sys02_Page_User extends ContentPanel implements InterfaceGridOperate {
	
	private Long companyId; 
	private Grid<Sys02_UserModel> grid = this.buildGrid();
	
	public Sys02_Page_User() {
		this.setHeaderVisible(false);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
//		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		this.getButtonBar().add(searchBarBuilder.getSearchBar());
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(grid); 
	}

	public Grid<Sys02_UserModel> buildGrid(){
		
		Sys02_UserModelProperties properties = GWT.create(Sys02_UserModelProperties.class);	
		GridBuilder<Sys02_UserModel> gridBuilder = new GridBuilder<Sys02_UserModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.korName(), 100, "이름", new TextField()) ;
		gridBuilder.addText(properties.loginId(), 100, "로그인아이디", new TextField()) ;

		ColumnConfig<Sys02_UserModel, String> password 
			= gridBuilder.addText(properties.passwd(), 100, "패스워드", new PasswordField());
		password.setCell(new AbstractCell<String>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context arg0, String arg1, SafeHtmlBuilder arg2) {
				arg2.appendHtmlConstant("********");   
			}
		});

		gridBuilder.addText(properties.email(), 150, "이메일주소", new TextField()) ;
		gridBuilder.addText(properties.telNo01(), 120, "전화번호(1)", new TextField()) ;
		gridBuilder.addText(properties.telNo02(), 120, "전화번호(2)", new TextField()) ;
		gridBuilder.addText(properties.note(), 400, "상세설명", new TextField());

		return gridBuilder.getGrid(); 
	}

	public void retrieve(Long companyId) {
		this.companyId = companyId; 
		this.retrieve(); 
	}
	
	@Override
	public void retrieve() {
		GridRetrieveData<Sys02_UserModel> service = new GridRetrieveData<Sys02_UserModel>(grid.getStore());
		service.addParam("companyId", this.companyId);
		service.retrieve("sys.Sys02_User.selectByName");
	}
	
	@Override
	public void update(){
		GridUpdate<Sys02_UserModel> service = new GridUpdate<Sys02_UserModel>(); 
		service.update(grid.getStore(), "sys.Sys02_User.update"); 
	}
	
	@Override
	public void insertRow(){
		if(this.companyId == null) {
			new SimpleMessage("회사를 먼저 선택하고 사용자를 등록하여야 합니다. "); 
			return ; 
		}
		GridInsertRow<Sys02_UserModel> service = new GridInsertRow<Sys02_UserModel>(); 
		Sys02_UserModel userModel = new Sys02_UserModel();
		userModel.setCompanyId(this.companyId); // 선택된 회사로 등록한다. 
		service.insertRow(grid, userModel);

	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<Sys02_UserModel> service = new GridDeleteData<Sys02_UserModel>();
		List<Sys02_UserModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "sys.Sys02_User.delete");
	}
}