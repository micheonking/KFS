package myApp.client.grid;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent.ExpandHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.LoginUser;
import myApp.client.vi.sys.model.Sys09_CodeModel;

public class ComboBoxField extends StringComboBox implements InterfaceServiceCall {
	
	private Map<String, Sys09_CodeModel> codeMap = new HashMap<String, Sys09_CodeModel>();
	private Sys09_CodeModel altCodeModel = new Sys09_CodeModel(); 
	
	private String kindCode = null; 
	private Date applyDate = null; 
	private InterfaceCallback callback;
	
	public ComboBoxField(String KindCode){
		this.makeComboBoxField(KindCode);  
  	}
	public ComboBoxField(String KindCode, InterfaceCallback callback){
		this.callback = callback; 
		this.makeComboBoxField(KindCode);
  	}  	
	public ComboBoxField(String KindCode, String code, String codeName){
		// 전체(%)가 필요한 경우.
		this.setAltCode(code, codeName);
		this.makeComboBoxField(KindCode);
	}
	public ComboBoxField(String KindCode, String code, String codeName, InterfaceCallback callback){
		// 전체(%)가 필요한 경우.
		this.callback = callback; 
		this.setAltCode(code, codeName);
		this.makeComboBoxField(KindCode);
   	}  	
	public ComboBoxField(String KindCode, TextField targetField){
		// 콤보선택 시 targetField값을 자동 설정한다. 
		this.setTriggerField(targetField);
		this.makeComboBoxField(KindCode);
   	}  	
	public ComboBoxField(String KindCode,  String code, String codeName, TextField targetField){
		this.setTriggerField(targetField);
		this.setAltCode(code, codeName);
		this.makeComboBoxField(KindCode);
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
	private void makeComboBoxField(String kindCode) {
		this.kindCode= kindCode; 
		this.setTriggerAction(TriggerAction.ALL);
		this.setEditable(false); // edit을 못하게 한다. 
		this.retrieve();
	}

	public void setCodeList(Date applyDate) {
		this.applyDate = applyDate; 
		this.retrieve();
	}
	
	public String getCode(){
  		Sys09_CodeModel code = codeMap.get(this.getCurrentValue()); 
  		if(code != null){
  			return code.getCode(); 
  		}
  		else {
  			return null; 
  		}
  	}
	
	public void retrieve(Date applyDate) {
		if(!this.isExpanded()) {
			this.applyDate = applyDate; 
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
		ServiceRequest request = new ServiceRequest("sys.Sys09_Code.selectByCodeKind");
		request.putLongParam("companyId", LoginUser.getCompanyId()); 
		request.putStringParam("kindCode", this.kindCode);
		request.putDateParam("applyDate", this.applyDate); // 기준일이 null이면 서버에서 오늘일자로 조회한다.
		
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
		
		for (GridDataModel model: result.getResult()) {
			Sys09_CodeModel code = (Sys09_CodeModel)model ;
			
			if(codeMap.get(code.getName()) == null) {
				// 코드명이 같은게 있으면 Skip한다. 
				codeMap.put(code.getName(), code);
				this.add(code.getName());
			}
			
		}
		
		if(this.callback != null) {
			this.callback.execute();
		}
		
	}
}
