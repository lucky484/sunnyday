package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.UserTimeModel;

public interface UserTimeService {
   
	public int updateUserTime(UserTimeModel userTimeModel);
	
	public int queryUserTimeByIdAndDate(Integer deviceId,String date);
	
	 public int insertUserTime(UserTimeModel userTimeModel);
	 
    public UserTimeModel queryUserTime(Integer orgId, String startTime, String endTime, List<Integer> depardIds);
	 
	 Page queryAllUserTime(Map<String,Object> map);
}
