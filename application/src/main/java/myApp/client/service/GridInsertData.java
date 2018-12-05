package myApp.client.service;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.GridDataModel;

public class GridInsertData<T extends GridDataModel> implements InterfaceServiceCall{
	
	private ListStore<T> listStore ; 
	// private T insertModel ; 
	private InterfaceCallback callBack; 
	
	public void insertData(ListStore<T> listStore, String serviceName, List<T> list ){

		this.listStore = listStore;
		List<GridDataModel> dataList = new ArrayList<GridDataModel>(); 
		
		dataList.addAll(list); 

		ServiceRequest request = new ServiceRequest(serviceName);
		request.setList(dataList);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}

		for (GridDataModel model: result.getResult()) {
			// 저장된 후 add한다. 
			listStore.add((T) model);
		}
		
		if(this.callBack != null){
			this.callBack.execute();
		}
	}
	
	public void addCallback(InterfaceCallback callBack){
		this.callBack = callBack;
	}
}
