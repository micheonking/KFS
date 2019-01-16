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
import myApp.client.vi.LoginUser;
import myApp.client.vi.cst.model.Cst02_AccountModel;

public class ComboBox1FundCode extends StringComboBox implements InterfaceServiceCall {

	private Map<String, Cst02_AccountModel> list = new HashMap<String, Cst02_AccountModel>();
	
	public Map<String, Cst02_AccountModel> getCodeList(){
		return this.list; 
	}
	
	public ComboBox1FundCode(String evalTypeCode){
		this.setTriggerAction(TriggerAction.ALL);
		this.retrieve(evalTypeCode);
  	}  	

	public Cst02_AccountModel getSeletedStartModel(String startName){
		Cst02_AccountModel startModel = list.get(startName);
  		if(startModel != null){
  			return startModel;   
  		}
  		else {
  			return null; 
  		}
  	}
	
	public String getCurrentFundCode(){

//		Info.display("this text", this.getText());

		Cst02_AccountModel currentModel = list.get(this.getText());
		
  		if(currentModel != null){
//  			Info.display("ZZZZZZ", "KKK");
  			return currentModel.getFundCode();   
  		}
  		else {
  			return "null"; 
  		}
  	}
	
	public void retrieve(String evalTypeCode) {
		ServiceRequest request = new ServiceRequest("cst.Cst02_Account.selectByAccountList");
		request.putLongParam("userId", LoginUser.getUserId());
//		request.putStringParam("evalTypeCode", evalTypeCode); // 콤보에서는 모두 조회한다. 평가 기간이 없으면 조회되지 않는다. 
//		request.putStringParam("baseYear", "%");

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		boolean isFirst = true;  
		this.getStore().clear();		
		
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		for (GridDataModel data : result.getResult()) {
			Cst02_AccountModel cstAccountModel = (Cst02_AccountModel)data ;
			String comboText = cstAccountModel.getAccountName()
					+ "[" + cstAccountModel.getAccountNo()
					+ "-" + cstAccountModel.getFundCode()
					+ "]" ; 
			this.add(comboText);
			list.put(comboText, cstAccountModel);
			
			// 초기값 설정. 
			if(isFirst) {
				this.setText(comboText);
				isFirst = false; 
			}
		}
	}
}