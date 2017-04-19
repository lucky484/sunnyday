package com.softtek.mdm.service;

import java.util.Collection;
import java.util.List;

import com.softtek.mdm.model.UserVirtualGroupModel;
import com.softtek.mdm.model.VirtualGroupModel;

public interface UserVirtualGroupService {

	int save(UserVirtualGroupModel entity);
	
	Collection<UserVirtualGroupModel> findByUser(Integer orgId,Integer id);
	
	Collection<VirtualGroupModel> findVtlGroupsByUser(Integer orgId, Integer id);
	
	int deleteByUser(Integer orgId, Integer id);
	/**
	 * 虚拟组成员插入
	 * @param uservirtualgroup
	 * @return
	 * by_josen 2016-03-24 18:03
	 */  
	int insertMember(UserVirtualGroupModel uservirtualgroup);
	/**
	 * 虚拟组成员删除
	 * @param entity
	 * @return
	 * by_josen 2016-03-24 18:03
	 */  
	int deleteByid(Integer userid,Integer groupid,Integer colid);

	int truncateWithUserId(Integer parseInt);

	int truncateWithUserIds(List<Integer> ids);

	int insertMembers(List<UserVirtualGroupModel> modelList);
}
