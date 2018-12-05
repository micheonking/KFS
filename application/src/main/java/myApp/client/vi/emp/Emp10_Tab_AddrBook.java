package myApp.client.vi.emp;
 
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp10_AddrBookModel;
import myApp.client.vi.emp.model.Emp10_AddrBookModelProperties;
 
public class Emp10_Tab_AddrBook extends BorderLayoutContainer implements InterfaceGridOperate {
 
    private Emp10_AddrBookModelProperties properties = GWT.create(Emp10_AddrBookModelProperties.class);
    private Grid<Emp10_AddrBookModel> grid = this.buildGrid();
 
    public Emp10_Tab_AddrBook() {
 
        this.setBorders(false); 
        
        SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
        searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton(); 
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
        this.setBorders(false); 
        this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(50)); 
 
        BorderLayoutData centerBorLayoutData = new BorderLayoutData(1);
        centerBorLayoutData .setMargins(new Margins(0, 4, 0, 0));
        centerBorLayoutData .setSplit(true);
        centerBorLayoutData .setMaxSize(1000);
 
        this.setCenterWidget(this.grid, centerBorLayoutData);
    }
 
    public Grid<Emp10_AddrBookModel> buildGrid(){
        
        GridBuilder<Emp10_AddrBookModel> gridBuilder = new GridBuilder<Emp10_AddrBookModel>(properties.keyId());  
        gridBuilder.setChecked(SelectionMode.SINGLE);
        
		gridBuilder.addText	(properties.personName(),		100,	"담당자명",		new	TextField());
		gridBuilder.addText	(properties.email(),			200,	"이메일",		new	TextField());
		gridBuilder.addText	(properties.mobile(),			120,	"핸드폰번호",	new	TextField());
		gridBuilder.addText	(properties.officeTel1(),		120,	"사무실 번호",	new	TextField());
		gridBuilder.addText	(properties.companyName(),		150,	"회사이름",		new	TextField());
		gridBuilder.addText	(properties.orgName(),			120,	"부서",			new	TextField());
		gridBuilder.addText	(properties.posName(),			60,	"직위",			new	TextField());
		gridBuilder.addText	(properties.titleName(),		70,	"직책",			new	TextField());
		gridBuilder.addText	(properties.note(),		300,	"비고",			new	TextField());

        
        return gridBuilder.getGrid(); 
    }
    
	@Override
	public void retrieve(){
    	GridRetrieveData<Emp10_AddrBookModel> service = new GridRetrieveData<Emp10_AddrBookModel>(grid.getStore());
		service.addParam("empId", LoginUser.getUserId()); 
        service.retrieve("emp.Emp10_AddrBook.selectByEmpId");
	}

	@Override
	public void update(){
		GridUpdate<Emp10_AddrBookModel> service = new GridUpdate<Emp10_AddrBookModel>();
		service.addParam("empId", LoginUser.getUserId()); 
		service.update(grid.getStore(), "emp.Emp10_AddrBook.update"); 
	}

	@Override
	public void insertRow(){
		GridInsertRow<Emp10_AddrBookModel> service = new GridInsertRow<Emp10_AddrBookModel>(); 
		Emp10_AddrBookModel addrBookModel = new Emp10_AddrBookModel();
		addrBookModel.setEmpId(LoginUser.getUserId());
		service.insertRow(grid, addrBookModel); 
	}
	

	@Override
	public void deleteRow(){
		GridDeleteData<Emp10_AddrBookModel> service = new GridDeleteData<Emp10_AddrBookModel>();
		List<Emp10_AddrBookModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "emp.Emp10_AddrBook.delete");
	}

}
 
 