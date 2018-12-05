package myApp.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.InterfaceCallbackResult;

public class GridRetrieveData<T> implements InterfaceServiceCall {
	private ListStore<T> listStore ; 
	private Map<String, Object> param = new HashMap<String, Object>(); 
	private InterfaceCallback callback = null;
	private InterfaceCallbackResult callbackResult = null;
	
	public GridRetrieveData(ListStore<T> listStore){
		this.listStore = listStore;
	} 
	
	public void addParam(String key, Object data){
		this.param.put(key, data); 
	}
	
	public void retrieve(String serviceName){
		
		// retrieve all 
		ServiceRequest request = new ServiceRequest(serviceName);
		request.getParam().putAll(this.param);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public void retrieve(String serviceName, InterfaceCallback callback){
		this.callback = callback;  
		this.retrieve(serviceName);
	}

	public void retrieve(String serviceName, InterfaceCallbackResult callback){
		this.callbackResult = callback;  
		this.retrieve(serviceName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		listStore.replaceAll((List<? extends T>) result.getResult());
		
		if(callback != null){
			callback.execute();
		}
		if(callbackResult != null){
			callbackResult.execute(result);
		}
	}
	
}
