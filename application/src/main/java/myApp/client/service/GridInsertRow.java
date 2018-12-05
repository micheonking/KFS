package myApp.client.service;

import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;

/*  
 * grid에 PK 칼럼값을 불러와서 설정한다.
 * 실제 자료가 데이터베이스에 저장되지는 않는다. 
 */

public class GridInsertRow<T> implements InterfaceServiceCall{
	
	private Grid<T> grid ; 
	private GridDataModel insertModel ; 
	private Boolean insertRow = true; // 기본값은 현재행 아래에 Insert 
	private InterfaceCallbackResult callback;
	
	public GridInsertRow(){
	} 

	public void insertRow(Grid<T> grid, GridDataModel insertModel){
		// 현재 행 아래에 추가된 행은 넣는다. 
		this.grid = grid; 
		this.insertModel = insertModel; 
		//this.listStore = grid.getStore();
		
		ServiceRequest request = new ServiceRequest("getSeq");

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	public void insertRow(Grid<T> grid, GridDataModel insertModel, InterfaceCallbackResult callback){
		this.callback = callback; 
		this.insertRow(grid, insertModel);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void getServiceResult(ServiceResult result) {

		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		insertModel.setKeyId(result.getSeq());
		
		if(this.insertRow == true){
			GridDataModel currentModel = (GridDataModel)grid.getSelectionModel().getSelectedItem();
			
			if(currentModel != null) {
				int rowIndex = this.grid.getStore().indexOf((T)currentModel); 
				this.grid.getStore().add(rowIndex + 1,  (T)insertModel);
			}
			else {
				this.grid.getStore().add((T)insertModel);
			}
		}
		else {
			this.grid.getStore().add((T)insertModel);
		}
		
		grid.getSelectionModel().select(false, (T)insertModel);
		
		if(this.callback != null){
			callback.execute(insertModel);
		}
	}
	

}
