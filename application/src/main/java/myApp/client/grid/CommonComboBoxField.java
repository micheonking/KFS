package myApp.client.grid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.sys.model.Sys00_CommonComboBoxModel;

public class CommonComboBoxField extends StringComboBox implements InterfaceServiceCall {
	
	private Map<String, Sys00_CommonComboBoxModel> codeMap = new HashMap<String, Sys00_CommonComboBoxModel>();
	private Sys00_CommonComboBoxModel altCodeModel = new Sys00_CommonComboBoxModel(); 
	private Map<String, Object> param = null; 
	private InterfaceCallback callback;
	private String serviceName;
	
	public CommonComboBoxField(String serviceName){
		this.makeComboBoxField(serviceName);
  	}
	public CommonComboBoxField(String serviceName, Map map){
		this.param = map;
		this.makeComboBoxField(serviceName);
  	}
	public CommonComboBoxField(String serviceName, Map map, InterfaceCallback callback){
		this.param = map;
		this.callback = callback;
		this.makeComboBoxField(serviceName);
  	}
	public CommonComboBoxField(String serviceName, InterfaceCallback callback){
		this.callback = callback;
		this.makeComboBoxField(serviceName);
  	}  	
	public CommonComboBoxField(String serviceName, String code, String codeName){
		// 전체(%)가 필요한 경우.
		this.setAltCode(code, codeName);
		this.makeComboBoxField(serviceName);
	}
	public CommonComboBoxField(String serviceName, String code, String codeName, InterfaceCallback callback){
		// 전체(%)가 필요한 경우.
		this.callback = callback; 
		this.setAltCode(code, codeName);
		this.makeComboBoxField(serviceName);
   	}
	public CommonComboBoxField(String serviceName, TextField targetField){
		// 콤보선택 시 targetField값을 자동 설정한다. 
		this.setTriggerField(targetField);
		this.makeComboBoxField(serviceName);
   	}  	
	public CommonComboBoxField(String serviceName,  String code, String codeName, TextField targetField){
		this.setTriggerField(targetField);
		this.setAltCode(code, codeName);
		this.makeComboBoxField(serviceName);
  	}  	
	
	private void setTriggerField(TextField targetField) {
	    // 닫힐때 필드값을 변경한다. 
		this.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				targetField.setValue(getCode());
			}
    	}); 
	}
	private void setAltCode(String code, String codeName) {
		this.altCodeModel.setCode(code);
		this.altCodeModel.setName(codeName);
	}

	private void makeComboBoxField(String serviceName) {
		this.serviceName= serviceName; 
		this.setTriggerAction(TriggerAction.ALL);
		this.setEditable(false); // edit을 못하게 한다. 
		this.retrieve();
	}

	public String getCode(){
		Sys00_CommonComboBoxModel code = codeMap.get(this.getCurrentValue()); 
  		if(code != null){
  			return code.getCode(); 
  		}
  		else {
  			return null; 
  		}
  	}
	
	public void retrieve(Map map) {
		if(!this.isExpanded()) {
			this.param = map; 
			this.callback = new InterfaceCallback() {
				@Override
				public void execute() {
					expand(); 
				}
			}; 
			this.retrieve();
		}
	}
	
	public void retrieve() {
		ServiceRequest request = new ServiceRequest(this.serviceName);
		if(this.param != null) {
			Iterator<String> keys = this.param.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				request.addParam(key, this.param.get(key));
			}
		}
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public void retrieve(String serviceName, Map param) {
		ServiceRequest request = new ServiceRequest(serviceName);
		if(param != null) {
			Iterator<String> keys = param.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				request.addParam(key, param.get(key));
			}
		}
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		
		this.getStore().clear();
		this.codeMap.clear();
		
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		
		// 추가코드가 있으면 먼저 넣는다. 
		if(this.altCodeModel.getName() != null) {
			codeMap.put(this.altCodeModel.getName(), this.altCodeModel);
			this.add(altCodeModel.getName());
		}

		int i=0;
		String initCodeName = null;
		for (GridDataModel model: result.getResult()) {
			Sys00_CommonComboBoxModel code = (Sys00_CommonComboBoxModel)model ;
			
			if(codeMap.get(code.getName()) == null) {
				// 코드명이 같은게 있으면 Skip한다. 
				codeMap.put(code.getName(), code);
				this.add(code.getName());
				
				if(i==0) {
					initCodeName = code.getName();
				}
				i++;
			}
		}
		
		if(this.altCodeModel.getName() == null) {
			this.setText(initCodeName);
		} else {
			this.setText(this.altCodeModel.getName());
		}
		
		if(this.callback != null) {
			this.callback.execute();
		}

	}
}
