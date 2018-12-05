package myApp.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sencha.gxt.data.shared.TreeStore;

import myApp.client.utils.GridDataModel;

public class TreeGridDelete<T> implements InterfaceServiceCall{
	
	TreeStore<T> treeStore ; 
	InterfaceCallback callback ;
	List<T> deleteList = new ArrayList<T>();
	String actionCode = "delete"; 
	
	private Map<String, Object> param = new HashMap<String, Object>();
	
	public void addParam(String key, Object data){
		this.param.put(key, data); 
	}

	@SuppressWarnings("unchecked")
	public void delete(TreeStore<T> treeStore, List<T> deleteList, String serviceName){
		this.treeStore = treeStore; 
		this.deleteList = deleteList; 
	
		ServiceRequest request = new ServiceRequest(serviceName);
		request.setParam(this.param);
		request.setList((List<GridDataModel>)deleteList);

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public void delete(TreeStore<T> treeStore, List<T> deleteList, String serviceName, InterfaceCallback callback){
		this.callback = callback ;
		this.delete(treeStore, deleteList, serviceName);
	}

	public void delete(TreeStore<T> treeStore,  T deleteModel, String serviceName, InterfaceCallback callback ){
		this.callback = callback; 
		this.delete(treeStore, deleteModel, serviceName); 
	}

	public void update(TreeStore<T> treeStore,  T deleteModel, String serviceName, InterfaceCallback callback ){
		/* 
		 * 이력관리가 필요한 자료에 한하여 update를 호출한다.   
		 */
		this.actionCode = "update"; 
		this.callback = callback; 
		this.delete(treeStore, deleteModel, serviceName); 
	}
	
	public void delete(TreeStore<T> treeStore, T deleteModel, String serviceName){
		this.deleteList.add(deleteModel);
		this.delete(treeStore, deleteList, serviceName);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		
		if(result.getStatus() < 0){
			return ; 
		}
		if(this.callback != null) {
			this.callback.execute();
		}
	}
}
