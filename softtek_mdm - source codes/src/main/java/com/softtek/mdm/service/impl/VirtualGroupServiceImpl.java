package com.softtek.mdm.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.dao.VirtualGroupDao;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.VirtualGroupService;
@Service
public class VirtualGroupServiceImpl implements VirtualGroupService {

	@Autowired
	private VirtualGroupDao virtualGroupDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public int save(VirtualGroupModel entity) {
		return virtualGroupDao.save(entity);
	}
    /**
     * 根据Id删除虚拟组
     */
	@Override
	public int delete(int id) {
		virtualGroupDao.deleteAllUsers(id);
		return virtualGroupDao.deleteWithPk(id);
	}
	
	@Override
	public Collection<VirtualGroupModel> findByCid(Integer id) {
		return virtualGroupDao.findByCid(id);
	}

	@Override
	public Collection<VirtualGroupModel> findByCids(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			return virtualGroupDao.findByCids(ids);
		}
		return null;
		
	}
	/**
	 * 根据机构id查找虚拟组
	 */
	@Override
	public Collection<VirtualGroupModel> findByOrgId(Integer orgId) {
		return virtualGroupDao.findByOrgId(orgId);
	}
	/**
	 * 根据机构id查找虚拟组
	 */
	@Override
	public Collection<VirtualGroupModel> findByOrgIdMember(Integer orgId,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("idlist", idlist);
		return virtualGroupDao.findByOrgIdMember(map);
	}
	@Override
	public int updateWeight(VirtualGroupModel entity) {
		return virtualGroupDao.updateWeightByName(entity);
	}
	@Override
	public int updateName(VirtualGroupModel entity) {
		// TODO Auto-generated method stub
		return virtualGroupDao.updateNameById(entity);
	}
	/**
	 * 查询虚拟组集合是否存在
	 */
	@Override
	public boolean isExists(VirtualGroupModel entity) {
		return virtualGroupDao.isExists(entity);
	}
	
	/**
	 * 根据虚拟组ID查询 用户
	 * @param id
	 * @return
	 */
	@Override
	public Collection<UserModel> queryExistMember(Integer id,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryExistMember(map);
	}
	/**
	 * 根据虚拟组ID查询不存在的用户
	 * @param id
	 * @return
	 */
	@Override
	public Collection<UserModel> queryNoExistMember(Integer orgid,Integer id,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("orgid", orgid);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryNoExistMember(map);
	}
	/**
	 * 根据虚拟组ID查询不存在的用户Radio
	 * @param id
	 * @return
	 */
	@Override
	public Collection<UserModel> queryNoExistMemberRadio(Integer orgid,Integer id,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("orgid", orgid);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryNoExistMemberRadio(map);
	}
	/**
	 * 根据组id和index查询指定的10条记录
	 * @param id
	 * @return
	 */
	@Override
	public Collection<UserModel> queryNoExistMemberPage(Integer orgid,Integer id,Integer index,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("orgid", orgid);
		map.put("start", index);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryNoExistMemberPage(map);
	}
	@Override
	public Collection<UserModel> queryExistMemberPage(Integer id, Integer index,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("start", index);
		map.put("name", name);
		map.put("idlist", idlist);
		return  userDao.queryExistMemberPage(map);
	}
	@Override
	public Collection<UserModel> queryNoExistMemberPageRadio(Integer orgid,Integer id, Integer index,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("orgid", orgid);
		map.put("start", index);
		map.put("name", name);
		map.put("idlist", idlist);
		return  userDao.queryNoExistMemberPageRadio(map);
	}
	/**
	 * 根据组id查询不存在用户页数
	 * @param id
	 * @return
	 */
	@Override
	public int queryNeMemberMaxPage(Integer orgid,Integer id,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("orgid",orgid);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryNeMemberMaxPage(map);
	}
	/**
	 * 根据组id查询不存在用户页数
	 * @param id
	 * @return
	 */
	@Override
	public int queryNeMemberMaxPageRadio(Integer orgid,Integer id,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("orgid", orgid);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryNeMemberMaxPageRadio(map);
	}
	/**
	 * 根据组id查询存在用户页数
	 * @param id
	 * @return
	 */
	@Override
	public int queryEMemberMaxPage(Integer id,String name,List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("idlist", idlist);
		return userDao.queryEMemberMaxPage(map);
	}
	@Override
	public Integer existsEditName(VirtualGroupModel virtualGroup) {
		return virtualGroupDao.existsEditName(virtualGroup);
	}
	@Override
	public VirtualGroupModel findOne(int parseInt) {
		return virtualGroupDao.findOne(parseInt);
	}

}
