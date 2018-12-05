package myApp.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

import myApp.client.utils.GridDataModel;

public class ServiceRequest implements IsSerializable {

	private String serviceName;
	private Map<String, Object> param = new HashMap<String, Object>();
	private List<GridDataModel> list = new ArrayList<GridDataModel>();
	
	public ServiceRequest(){
	}
	
	public ServiceRequest(String serviceName){
		this.setServiceName(serviceName);
	}
	
	public List<GridDataModel> getList() {
		return list;
	}
	
	public void setList(List<GridDataModel> list) {
		this.list = list;
	}

	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Map<String, Object> getParam(){
		return this.param; 
	}
	
	public void putStringParam(String key, String value){
		this.param.put(key, value); 
	}

	public void putLongParam(String key, Long value){
		this.param.put(key, value); 
	}

	public void putBooleanParam(String key, Boolean value){
		this.param.put(key, value); 
	}

	public void putDateParam(String key, Date value){
		this.param.put(key, value); 
	}
	
	public void putModelParam(String key, GridDataModel value){
		this.param.put(key, value); 
	}

	public void setParam(Map<String, Object> param){
		this.param = param ;  
	}

	public void addParam(String key, Object value) {
		this.param.put(key, value); 
	}
	
	public Object getParam(String key) {
		return this.param.get(key); 
	}
	
	public String getStringParam(String key){
		if(this.param.get(key) !=  null ){
			return this.param.get(key).toString();
		}
		else {
			return null; 
		}
	}
	
	public Long getLongParam(String key){
		if(param.get(key) != null) {
			Long data = (Long)this.param.get(key);
			return data ; 
		}
		else {
			return null; 
		}
	}

	public Date getDateParam(String key){
		if(param.get(key) != null) {
			return (Date)this.param.get(key) ; 
		}
		else {
			return null; 
		}
	}

	
	public Boolean getBooleanParam(String key){
		if(param.get(key) != null) {
			return (Boolean)param.get(key);
		}
		else {
			return null; 
		}
	}
	
	public GridDataModel getModelParam(String key){
		return (GridDataModel)this.param.get(key);
	}

}
