package myApp.client.utils;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface GridDataModel extends IsSerializable{
	
	public void setKeyId(Long id);
	public Long getKeyId();
	
	public void setInsUsrNo(String usrNo);
	public void setInsDate(Date date);
	public void setUpdUsrNo(String usrNo);
	public void setUpdDate(Date date);
} 
