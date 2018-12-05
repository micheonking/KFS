package myApp.client.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface GridDataModel extends IsSerializable{
	
	public void setKeyId(Long id);
	public Long getKeyId();
} 
