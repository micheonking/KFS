package myApp.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;

public class GridUpdate<T> implements InterfaceServiceCall{
	
	private ListStore<T> listStore ;
	private Map<String, Object> param = new HashMap<String, Object>();
	private InterfaceCallback callback; 
	private InterfaceCallbackResult callbackResult; 
	private ServiceRequest request ; 
	
	public void addParam(String key, Object data){
		this.param.put(key, data); 
	}
	
	public void update(ListStore<T> listStore, String serviceName, InterfaceCallback callback){
		this.callback = callback ; 
		this.update(listStore, serviceName);
	}
	
	public void update(ListStore<T> listStore, String serviceName, InterfaceCallbackResult callbackResult){
		this.callbackResult = callbackResult ; 
		this.update(listStore, serviceName);
	}

	public void update(ListStore<T> listStore, String serviceName){
		
		// 수정 혹은 신규 입력된 DataModel을 찾아 저장한다.  
		this.listStore = listStore;

		if (listStore.getModifiedRecords().size() > 0 ) {
			List<GridDataModel> updateList = new ArrayList<GridDataModel>();
			for(Store<T>.Record modified : listStore.getModifiedRecords()){
				// 형별로 변경된 자료를 찾는다. 
				T updateModel = modified.getModel(); 
				for (Change<T, ?> changes : modified.getChanges()) {
					// 컬럼별로 변경된 자료를 적용한다. 
					changes.modify(updateModel);
				}
				updateList.add((GridDataModel)updateModel); // 형을 상위로 변경해도 되는가? 
			}

			request = new ServiceRequest(serviceName);
			request.setList(updateList);
			request.setParam(param);
//			request.getParam().putAll(param);

			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		} 
	}
	
	
	public void update(ListStore<T> listStore, T updateModel, String serviceName){
		// 단일건(updateModel)을 저장한다. - form update (tabpage_user, form_student 등) 
		this.listStore = listStore;
			
		List<GridDataModel> updateList = new ArrayList<GridDataModel>();
		updateList.add((GridDataModel)updateModel);  
		
		request = new ServiceRequest(serviceName);
		request.setList(updateList);
		request.getParam().putAll(param);
		ServiceCall service = new ServiceCall();
		
		service.execute(request, this);
	}

	public void update(ListStore<T> listStore, T updateModel, String serviceName, InterfaceCallback callback){
		this.callback = callback ;
		this.update(listStore, updateModel, serviceName);
	}
	
	public void update(ListStore<T> listStore, T updateModel, String serviceName, InterfaceCallbackResult callback){
		this.callbackResult = callback ;
		this.update(listStore, updateModel, serviceName);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void getServiceResult(ServiceResult result) {
		
		if(result.getStatus() < 0){
			Info.display("update error", result.getMessage());
			return ; 
		}
		
		for (GridDataModel model: result.getResult()) {
			if( listStore.findModel((T)model) != null ) {
				listStore.update((T) model); // 있으면 update
			}
			else {
				listStore.add((T) model); // 없으면 insert 
			}
		}
		
		if(this.callback != null) {
			this.callback.execute();
		}
		if(this.callbackResult != null) {
			this.callbackResult.execute(result);
		}
	}
}
