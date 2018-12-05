package myApp.client.service;

import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.utils.GridDataModel;

public class DBUtil implements InterfaceServiceCall{
	
	GridDataModel insertModel ;
	InterfaceCallback callBack ; 

	public DBUtil(){
	} 

	public void setSeq(GridDataModel insertModel, InterfaceCallback callback) {
		
		this.insertModel = insertModel;
		this.callBack = callback; 
		
		ServiceRequest request = new ServiceRequest("getSeq");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {

		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}

		this.insertModel.setKeyId(result.getSeq());
		this.callBack.execute();
		
	}
}
