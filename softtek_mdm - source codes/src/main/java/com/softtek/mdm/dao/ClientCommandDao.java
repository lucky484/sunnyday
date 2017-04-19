package com.softtek.mdm.dao;

import com.softtek.mdm.model.ClientCommandModel;

public interface ClientCommandDao {
    
	  public int insertRemoveDevice(ClientCommandModel clientCommand);
	  
	  public int deleteDevice(String sn);
	  
	  public ClientCommandModel queryDeviceDelInfo(String sn);
	  
	  public int queryDeviceIsExits(String sn);
	  
	  public int updateRemoveDevice(String sn);
}
