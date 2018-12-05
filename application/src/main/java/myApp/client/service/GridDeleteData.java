package myApp.client.service;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.GridDataModel;

public class GridDeleteData<T> implements InterfaceServiceCall{
	InterfaceCallback callback; 
	ListStore<T> listStore ; 
	
	@SuppressWarnings("unchecked")
	public void delete(ListStore<T> listStore, List<T> checkedList, String serviceName){
		this.listStore = listStore; 
		ServiceRequest request = new ServiceRequest(serviceName);
		request.setList((List<GridDataModel>) checkedList);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public void delete(ListStore<T> listStore, List<T> checkedList, String serviceName, InterfaceCallback callback){
		this.callback = callback; 
		this.delete(listStore, checkedList, serviceName);
	}
	
	public void delete(ListStore<T> listStore, T deleteModel, String serviceName){
		List<T> deleteList = new ArrayList<T>(); 
		deleteList.add(deleteModel); 
		this.delete(listStore, deleteList, serviceName);
	}

	public void delete(ListStore<T> listStore, T deleteModel, String serviceName, InterfaceCallback callback){
		this.callback = callback; 
		this.delete(listStore, deleteModel, serviceName);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}

		for (GridDataModel model: result.getResult()) {
			listStore.remove(listStore.findModelWithKey(model.getKeyId() + ""));
		}
		
		if(this.callback != null) {
			callback.execute(); 
		}
	}
}
