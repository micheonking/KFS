package myApp.client.vi.cst;


import java.util.HashMap;
import java.util.Map;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.cst.model.Cst03_IcamAccountsModel;

public class MgComboBoxField extends StringComboBox implements InterfaceServiceCall {

	private Map<String, Cst03_IcamAccountsModel> mgCodeList = new HashMap<String, Cst03_IcamAccountsModel>();
	
	public MgComboBoxField(){
		ServiceRequest request = new ServiceRequest("cst.IcamAcc.selectByMgCombo");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		this.setTriggerAction(TriggerAction.ALL);
  	}  	
	
	public String getCode(){
		
		Info.display("combobox IN", "IN!!!!");
		Info.display("comboBox", "" + this.getCurrentValue());
		mgCodeList.get("");
		Cst03_IcamAccountsModel code = mgCodeList.get(this.getCurrentValue());
		
  		if(code != null){
  			Info.display("comboBox", "데이터 있음");
  			return code.getMgCode(); 
  		}
  		else {
  			Info.display("comboBox", "데이터 없음 null");
  			return null; 
  		}
  	}

	@Override
	public void getServiceResult(ServiceResult result) {
		// TODO Auto-generated method stub
		
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		for (GridDataModel model: result.getResult()) {
			Cst03_IcamAccountsModel mgCode = (Cst03_IcamAccountsModel)model ;
			mgCodeList.put(mgCode.getMcCodeName(), mgCode);
			this.add(mgCode.getMcCodeName());
		}
	}
	
}


