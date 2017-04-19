package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.softtek.mdm.model.UserTimeModel;

public interface UserTimeDao {
   
	   public int insertUserTime(UserTimeModel userTimeModel);
	   
	   public int updateUserTime(UserTimeModel userTimeModel);
	   
	   public int queryUserTimeByIdAndDate(@Param(value="deviceId") Integer deviceId,@Param(value="date") String date);
	   
    public UserTimeModel queryUserTime(@Param(value = "orgId") Integer orgId,
            @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime,
            @Param(value = "groupId") List<Integer> groupId);
	   
	   public List<UserTimeModel> queryAllUserTime(Map<String,Object> map);
	   
	   public int queryAllUserTimeCount(Map<String,Object> map);
}
