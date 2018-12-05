package myApp.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.utils.SimpleMessage;

public class TreeGridUpdate<T> implements InterfaceServiceCall{
	
	TreeStore<T> treeStore ; 
	List<GridDataModel> updateList = new ArrayList<GridDataModel>();
	InterfaceCallbackResult callback ; 
	
	private Map<String, Object> param = new HashMap<String, Object>();
	
	public void addParam(String key, Object data){
		this.param.put(key, data); 
	}

	public void update(TreeStore<T> treeStore, String serviceName, InterfaceCallbackResult callback){
		this.callback = callback;
		this.update(treeStore, serviceName);
	}
		
	public void update(TreeStore<T> treeStore, String serviceName){
		this.treeStore = treeStore;
		
		if (treeStore.getModifiedRecords().size() > 0 ) {
			for(Store<T>.Record modified : treeStore.getModifiedRecords()){
				T updateModel = modified.getModel(); // 행별로 변경된 자료를 찾는다. 
				for (Change<T, ?> changes : modified.getChanges()) {
					 changes.modify(updateModel); // 컬럼별로 변경된 자료를 적용한다.
				 }
				 updateList.add((GridDataModel)updateModel); // 형을 상위로 변경해도 되는가? 
			}
			ServiceRequest request = new ServiceRequest(serviceName);
			request.getParam().putAll(this.param);
			request.setList(updateList);
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		} 
	}

	public void update(TreeStore<T> treeStore,  T updateModel, String serviceName, InterfaceCallbackResult callback ){
		this.callback = callback; 
		this.update(treeStore, updateModel, serviceName); 
	}

	public void update(TreeStore<T> treeStore, T updateModel, String serviceName){
		/* 
		 * 변경된 건을 찾지 않고 업데이트 한다. 
		 */
		this.treeStore = treeStore;
		updateList.add((GridDataModel)updateModel);

		ServiceRequest request = new ServiceRequest(serviceName);
		request.getParam().putAll(this.param);
		request.setList(updateList);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			new SimpleMessage("저장시 오류가 발생하였습니다.", result.getMessage()) ; //Info.display("error", result.getMessage());
			return ; 
		}
		else {
			if(this.callback != null) {
				this.callback.execute(result.getResult());
			}
		}
	}
}

















