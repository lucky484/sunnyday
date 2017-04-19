package com.softtek.mdm.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softtek.mdm.dao.VirtualCollectionDao;
import com.softtek.mdm.dao.VirtualGroupDao;
import com.softtek.mdm.model.VirtualCollectionModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.VirtualCollectionService;

import jodd.util.StringUtil;
@Service
public class VirtualCollectionServiceImpl implements VirtualCollectionService {

	@Autowired
	private VirtualCollectionDao virtualCollectionDao;
	@Autowired
	private VirtualGroupDao virtualGroupDao;
	
	@Override
	public String save(VirtualCollectionModel virtualCollection,VirtualGroupModel virtualGroup,Integer create_by) {
		virtualGroup.setName(virtualGroup.getVirtualCollection().getMark());
		// 具体根据什么来分割，可以自己替换修改
		virtualCollection.setCreateBy(create_by);
		virtualGroup.setCreateBy(create_by);
		Integer id =virtualCollectionDao.save(virtualCollection);
		//获得 插入时返回刚插入记录的主键值
		Integer colid = virtualCollection.getId();
		String name = "";
		// 确定集合已经插入数据库
		if (id > 0) {
			virtualGroup.getVirtualCollection().setId(colid);
			String[] strArr = StringUtil.split(virtualGroup.getName(), "support");
			if (strArr.length > 1) {
				for (String str : strArr) {
					String[] temp = StringUtil.split(str, "softtek");
					if (temp.length == 2) {
						virtualGroup.setName(temp[0]);
						name += temp[0] + ",";
						virtualGroup.setWeight(Integer.parseInt(temp[1]));
						virtualGroupDao.save(virtualGroup);
					}
				}
				//name为所有插入虚拟组用","拼接  记录日志
				name = name.substring(0, name.length() - 1);
			}
		}
		return name;
	}
	/**
	 * 根据id 删除虚拟组集合
	 * 同时删除虚拟组和用户虚拟组表达相关数据
	 */
	@Transactional
	public void deleteByCid(int id) {
		 virtualCollectionDao.deleteCollection(id);
		 virtualCollectionDao.deleteVirtualGroup(id);
		 virtualCollectionDao.deleteMember(id);
	}
	
	@Override
	public boolean isExists(VirtualCollectionModel entity) {
		return virtualCollectionDao.isExists(entity);
	}
	
	/**
	 * 根据组织架构id获取相应的列表
	 * @param id
	 * @return
	 */
	public Collection<VirtualCollectionModel> findAllByOrgID(Integer id){
		return virtualCollectionDao.findAllByOrgID(id);
	}
	
	/**
	 * 更新虚拟组集合
	 * @param 
	 * @return
	 */
	public String update(VirtualCollectionModel virtualCollection,VirtualGroupModel virtualGroup) {
		virtualCollectionDao.update(virtualCollection);
		String[] strArr = StringUtil.split(virtualGroup.getName(), "support");
		String namegp = "";
		if (strArr != null && strArr.length > 1) {
			for (String str : strArr) {
				String[] temp = StringUtil.split(str, "softtek");
				if (temp.length == 2) {
					virtualGroup.setName(temp[0]);
					namegp += temp[0] + ",";
					virtualGroup.setWeight(Integer.parseInt(temp[1]));
					boolean isext = virtualGroupDao.isExists(virtualGroup);
					if (isext == true) {
						virtualGroupDao.updateWeightByName(virtualGroup);
					} else {
						virtualGroupDao.save(virtualGroup);
					}
				}
			}
			namegp = namegp.substring(0, namegp.length() - 1);
		}
		return namegp;
	}
	@Override
	public int existsEditName(VirtualCollectionModel entity) {
		return virtualCollectionDao.existsEditName(entity);
	}
	@Override
	public Map<String, Object> findAllByOrgIdMap(Integer orgId) {
		Map<String,Object> map=new HashMap<String, Object>();
		Collection<VirtualCollectionModel> vtlcollist=virtualCollectionDao.findAllByOrgID(orgId);
		Collection<VirtualGroupModel> list = virtualGroupDao.findByOrgId(orgId);
		map.put("vtlcollist",vtlcollist);
		map.put("list",list);
		return map;
	}
}
