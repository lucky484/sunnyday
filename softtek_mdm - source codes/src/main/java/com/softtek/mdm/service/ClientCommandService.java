package com.softtek.mdm.service;

import com.softtek.mdm.model.ClientCommandModel;

public interface ClientCommandService {
    
	
	  public int insertRemoveDevice(ClientCommandModel clientCommand);
	  
	  public int deleteDevice(String sn);
	  
	  public ClientCommandModel queryDeviceDelInfo(String sn);
	  
	  public int queryDeviceIsExits(String sn);
	  
	  public int updateRemoveDevice(String sn);
}
