package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.UserVirtualGroupDao;
import com.softtek.mdm.dao.VirtualGroupDao;
import com.softtek.mdm.model.UserVirtualGroupModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.UserVirtualGroupService;
@Service
public class UserVirtualGroupServiceImpl implements UserVirtualGroupService {

	@Autowired
	private VirtualGroupDao virtualGroupDao;
	
	@Autowired
	private UserVirtualGroupDao userVirtualGroupDao;
	
	
	@Override
	public int save(UserVirtualGroupModel entity) {
		return userVirtualGroupDao.save(entity);
	}
	@Override
	public Collection<UserVirtualGroupModel> findByUser(Integer orgId, Integer id) {
		return userVirtualGroupDao.findByUser(orgId, id);
	}
	
	/**
	 * 
	 * @param orgId
	 * @param id
	 * @return
	 */
	public Collection<VirtualGroupModel> findVtlGroupsByUser(Integer orgId, Integer id){
		
		List<UserVirtualGroupModel> list=(List<UserVirtualGroupModel>) this.findByUser(orgId, id);
		Integer[] ids=new Integer[list.size()];
		for (int i=0;i<list.size();i++) {
			if(list.get(i).getVirtualGroup()!=null){
				ids[i]=list.get(i).getVirtualGroup().getId();
			}
		}
		if(ids.length>0){
			return virtualGroupDao.findByids(ids);
		}else{
			return null;
		}
		
	}
	@Override
	public int deleteByUser(Integer orgId, Integer id) {
		List<UserVirtualGroupModel> uvglist=(List<UserVirtualGroupModel>) userVirtualGroupDao.findByUser(orgId, id);
		List<Integer> ids=new ArrayList<Integer>();
		if(uvglist!=null){
			for (UserVirtualGroupModel uvg : uvglist) {
				if(!ids.contains(uvg.getId())){
					ids.add(uvg.getId());
				}
			}
		}
		
		if(ids.size()>0){
			return userVirtualGroupDao.deleteByIds(ids);
		}else{
			return 0;
		}
		
	}
	@Override
	public int insertMember(UserVirtualGroupModel entity) {
		return userVirtualGroupDao.insertMember(entity);
	}
	@Override
	public int deleteByid(Integer userid,Integer groupid,Integer colid) {
		// TODO Auto-generated method stub
		return userVirtualGroupDao.deleteByid(userid,groupid,colid);
	}
	@Override
	public int truncateWithUserId(Integer parseInt) {
		if(parseInt!=null){
			return userVirtualGroupDao.truncateWithUserId(parseInt);
		}
		return 0;
	}
	@Override
	public int truncateWithUserIds(List<Integer> ids) {
		if(ids!=null&&ids.size()>0){
			return userVirtualGroupDao.truncateWithUserIds(ids);
		}
		return 0;
	}
	@Override
	public int insertMembers(List<UserVirtualGroupModel> modelList)
	{
		return userVirtualGroupDao.insertMembers(modelList);
	}



}
