package myApp.client.vi.cst;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.LoginUser;
import myApp.client.vi.cst.model.Cst02_AccountModel;

public class ComboBoxFundCode extends StringComboBox implements InterfaceServiceCall {

	private static Map<String, Cst02_AccountModel> list = new HashMap<String, Cst02_AccountModel>();
//	public static String getCode() {
//		return getFundCode(); //getSeletedStartModel(this.getText());  
//	}
	
	private String selectCode = ""; 
	
	public Map<String, Cst02_AccountModel> getCodeList(){
		return this.list; 
	}
	
	public ComboBoxFundCode(String evalTypeCode){
		this.setTriggerAction(TriggerAction.ALL);
		this.retrieve(evalTypeCode);
		
		this.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
				selectCode = getText(); 
			}
		}); 
  	}  	

//	public static String getFundCode(){
//		Cst02_AccountModel cstAccountModel = list.get(selectCode);
//  		if(cstAccountModel != null){
//  			return cstAccountModel.getFundCode();   
//  		}
//  		else {
//  			return null; 
//  		}
//  	}

	public Cst02_AccountModel getSeletedStartModel(String startName){
		Cst02_AccountModel cstAccountModel = list.get(startName);
  		if(cstAccountModel != null){
  			return cstAccountModel;   
  		}
  		else {
  			return null; 
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
//public class ComboBoxFundCode extends StringComboBox implements InterfaceServiceCall {
//	
//	private Map<String, Cst02_AccountModel> codeMap = new HashMap<String, Cst02_AccountModel>();
//	private Cst02_AccountModel altCodeModel = new Cst02_AccountModel();
//	
////	private String kindCode = null; 
////	private Date applyDate = null; 
//	private InterfaceCallback callback;
//	
//	public ComboBoxFundCode(String FundCode){
//		this.makeComboBoxFundCode(FundCode);  
//  	}
//	public ComboBoxFundCode(String FundCode, InterfaceCallback callback){
//		this.callback = callback; 
//		this.makeComboBoxFundCode(FundCode);
//  	}  	
////	public ComboBoxFundCode(String FundCode, String code, String codeName){
////		// 전체(%)가 필요한 경우.
////		this.setAltCode(code, codeName);
////		this.makeComboBoxFundCode(FundCode);
////	}
////	public ComboBoxFundCode(String FundCode, String code, String codeName, InterfaceCallback callback){
////		// 전체(%)가 필요한 경우.
////		this.callback = callback; 
////		this.setAltCode(code, codeName);
////		this.makeComboBoxFundCode(FundCode);
////   	}  	
//	public ComboBoxFundCode(String FundCode, TextField targetField){
//		// 콤보선택 시 targetField값을 자동 설정한다. 
//		this.setTriggerField(targetField);
//		this.makeComboBoxFundCode(FundCode);
//   	}  	
//	public ComboBoxFundCode(String FundCode,  String codeName, TextField targetField){
//		this.setTriggerField(targetField);
//		this.setAltCode(FundCode, codeName);
//		this.makeComboBoxFundCode(FundCode);
//  	}  	
//	
//	private void setTriggerField(TextField targetField) {
//	    // 닫힐때 필드값을 변경한다. 
//		this.addCollapseHandler(new CollapseHandler(){
//			@Override
//			public void onCollapse(CollapseEvent event) {
//				targetField.setValue(getCode());
//			}
//    	}); 
//	}
//	private void setAltCode(String code, String codeName) {
//		this.altCodeModel.setAccountNo(code);
//		this.altCodeModel.setAccountName(codeName);
////		this.altCodeModel.setFundCode(fundCode);
//	}
//	private void makeComboBoxFundCode(String kindCode) {
////		this.kindCode= kindCode; 
//		this.setTriggerAction(TriggerAction.ALL);
//		this.setEditable(false); // edit을 못하게 한다. 
//		this.retrieve();
//	}
//
////	public void setCodeList(Date applyDate) {
////		this.applyDate = applyDate; 
////		this.retrieve();
////	}
////	
//	public String getCode(){
//  		Cst02_AccountModel code = codeMap.get(this.getCurrentValue()); 
//  		if(code != null){
//  			return code.getAccountNo(); 
//  		}
//  		else {
//  			return null; 
//  		}
//  	}
//	
////	public void retrieve(Date applyDate) {
////		if(!this.isExpanded()) {
////			this.applyDate = applyDate; 
////			this.callback = new InterfaceCallback() {
////				@Override
////				public void execute() {
////					expand(); 
////				}
////			}; 
////			this.retrieve();
////		}
////	}
////	
//	public void retrieve() {
//		ServiceRequest request = new ServiceRequest("cst.Cst02_Account.selectByAccountList");
//		request.putLongParam("userId", LoginUser.getUserId()); 
////		request.putStringParam("kindCode", this.kindCode);
////		request.putDateParam("applyDate", this.applyDate); // 기준일이 null이면 서버에서 오늘일자로 조회한다.
//		
//		ServiceCall service = new ServiceCall();
//		service.execute(request, this);
//	}
//	
//	@Override
//	public void getServiceResult(ServiceResult result) {
//		
//		this.getStore().clear();
//		this.codeMap.clear();
//		
//		if(result.getStatus() < 0){
//			Info.display("error", result.getMessage());
//			return ; 
//		}
//		
//		for (GridDataModel model: result.getResult()) {
//			Cst02_AccountModel code = (Cst02_AccountModel)model ;
//			
//			if(codeMap.get(code.getFundCode()) == null) {
//				// 코드명이 같은게 있으면 Skip한다. 
//				codeMap.put(code.getFundCode(), code);
//				this.add(code.getFundCode());
//			}
//		}
//		
//		if(this.callback != null) {
//			this.callback.execute();
//		}
//		
//	}
//}
