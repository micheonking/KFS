package myApp.client.vi.emp;

import java.util.Map;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.data.shared.event.StoreAddEvent.StoreAddHandler;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent.StoreRemoveHandler;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent.StoreUpdateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.InterfaceTabPage;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.emp.model.Emp02_EmpModel;
import myApp.client.vi.emp.model.Emp03_TransModel;
import myApp.client.vi.emp.model.Emp03_TransModelProperties;
 
public class Emp03_TabPage_Trans extends ContentPanel implements InterfaceTabPage {
	
	private Grid<Emp03_TransModel> grid = this.buildGrid();
	private Emp02_EmpModel empModel; 
	
	public Emp03_TabPage_Trans(InterfaceCallback callback){
	
		this.setHeaderVisible(false);
		
		TextButton transInsertbutton = new TextButton() ;
		transInsertbutton.setText("일반발령 등록");
		transInsertbutton.setWidth(100);
		transInsertbutton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertTrans(); 
			}
		});
		TextButton pridInsertbutton = new TextButton();
		pridInsertbutton.setText("겸직발령 등록");
		pridInsertbutton.setWidth(100);
		pridInsertbutton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertAddTitle();
			}
		});

		this.getButtonBar().add(transInsertbutton);
		this.getButtonBar().add(pridInsertbutton);
		this.getButtonBar().setPack(BoxLayoutPack.CENTER);
		this.add(this.grid, new MarginData(3, 0, 0, 0)) ; // 상단을 약간 띈다. 
		
		this.grid.getStore().addStoreUpdateHandler(new StoreUpdateHandler<Emp03_TransModel>() {
			@Override
			public void onUpdate(StoreUpdateEvent<Emp03_TransModel> event) {
				callback.execute();
			}
		}); 
		
		this.grid.getStore().addStoreAddHandler(new StoreAddHandler<Emp03_TransModel>() {
			@Override
			public void onAdd(StoreAddEvent<Emp03_TransModel> event) {
				callback.execute();
			}
		}) ; 

		this.grid.getStore().addStoreRemoveHandler(new StoreRemoveHandler<Emp03_TransModel>() {
			
			@Override
			public void onRemove(StoreRemoveEvent<Emp03_TransModel> event) {
				callback.execute();
			}
		}); 
	}

	private void insertTrans() {
		
		if(this.empModel == null) {
			new SimpleMessage("발령을 등록할 사원을 먼저 선택하여 주세요. "); 
			return ; 
		}
		
		Emp03_TransModel transModel = new Emp03_TransModel();
		transModel.setEmpId(this.empModel.getEmpId()); // personId setting 
		
		DBUtil dbUtil = new DBUtil(); 
		dbUtil.setSeq(transModel, new InterfaceCallback() { // transId setting 
			@Override
			public void execute() {
				new Emp03_Edit_Trans(grid, empModel, transModel);
			}
		});
	}
	private void insertAddTitle() {
		
		if(this.empModel == null) {
			new SimpleMessage("발령을 등록할 사원을 먼저 선택하여 주세요. "); 
			return ; 
		}
		
		Emp04_Lookup_AddTitle addtitle = new Emp04_Lookup_AddTitle(this.empModel.getEmpId());
		
		addtitle.open(new InterfaceCallbackResult() {
			
			@Override
			public void execute(Object result) {
				retrieve();
			}
		});
	}
	
	public Grid<Emp03_TransModel> buildGrid(){
		Emp03_TransModelProperties properties = GWT.create(Emp03_TransModelProperties.class);
		GridBuilder<Emp03_TransModel> gridBuilder = new GridBuilder<Emp03_TransModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.transName(), 80, "발령구분");
		gridBuilder.addDate(properties.transDate(), 100, "발령일");
		gridBuilder.addDate(properties.addTitleCloseDate(), 100, "겸직종료일");
		
		ActionCell<String> editTransCell = new ActionCell<String>("Edit", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				if("겸직".equals(grid.getSelectionModel().getSelectedItem().getTransName())) {
					insertAddTitle();
				} else {
					new Emp03_Edit_Trans(grid, empModel, grid.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		gridBuilder.addCell(properties.actionCell(), 60, "수정", editTransCell); //, new TextField()) ;
		gridBuilder.addText(properties.orgKorName(), 200, "발령조직");
		gridBuilder.addText(properties.titleName(), 80, "발령직책");
		gridBuilder.addText(properties.posName(), 100, "발령직위");		
		gridBuilder.addText(properties.transReason(), 350, "발령상세사유");
		gridBuilder.addText(properties.transNote(), 200, "비고");

		return gridBuilder.getGrid();
	}

	@Override // InterfaceTabpage()
	public void init() {
		this.grid.getStore().clear();
	}
	
	@Override // InterfaceTabpage()
	public void retrieve(Map<String, Object> param) {
		
		if(param == null){
			this.init();
			return ; 
		}
		else {
			this.empModel = (Emp02_EmpModel)param.get("empModel");
			GridRetrieveData<Emp03_TransModel> service = new GridRetrieveData<Emp03_TransModel>(grid.getStore());
			service.addParam("empModel", this.empModel);
			service.retrieve("emp.Emp03_Trans.selectByEmpId");
		}
	}
	
	public void retrieve() {
		GridRetrieveData<Emp03_TransModel> service = new GridRetrieveData<Emp03_TransModel>(grid.getStore());
		service.addParam("empModel", this.empModel);
		service.retrieve("emp.Emp03_Trans.selectByEmpId");
	}
}