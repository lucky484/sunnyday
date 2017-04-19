package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.StructureDao;
import com.softtek.mdm.dao.UserDepartmentDao;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.StructureService;

/**
 * ��֯�ܹ�ҵ������
 * 
 * @author color.wu
 *
 */
@Service
public class StructureServiceImpl implements StructureService {

	@Autowired
	private StructureDao structureDao;

	@Autowired
	private UserDepartmentDao userDepartmentDao;

	private String departmentName = "";

	@Override
	public Collection<StructureModel> findAllByOrgID(Integer id) {
		return structureDao.findAllByOrgID(id);
	}

	@Override
	public int save(StructureModel entity, UserDepartmentModel userDepartmentModel) {
		int result = structureDao.save(entity);
		if (userDepartmentModel.getUserRoleDepartment() != null) {
			userDepartmentModel.setStructure(entity);
			userDepartmentDao.save(userDepartmentModel);
		}
		return result;
	}

	@Override
	/**
	 * ���ݵ�ǰ��id��ȡ��ǰ�ڵ�����༶���ڵ��������
	 */
	public StructureModel getParents(Integer id) {
		StructureModel current = structureDao.findOne(id);
		if (current != null) {
			// ��ǰ�Ǹ��ڵ㣬ֱ�ӷ���
			if (current.getParent() == null) {
				return current;
			} else {
				// �������в���
				StructureModel temp = current;
				while (temp.getParent() != null) {
					StructureModel ste = structureDao.findOne(temp.getParent().getId());
					temp.setParent(ste);
					temp = temp.getParent();
				}
				return current;
			}
		}
		return null;
	}

	public int update(StructureModel structure) {

		return structureDao.update(structure);
	}

	@Override
	public StructureModel queryParentById(int id) {

		return structureDao.queryParentById(id);
	}

	@Override
	public void updateParentIdById(int id, int parentId) {
		structureDao.updateParentIdById(id, parentId);
	}

	@Override
	public int deleteStructureById(UserRoleDepartmentModel uRD, List<Integer> list) {
		if (uRD != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", uRD.getId());
			map.put("list", list);
			structureDao.updateUserDepartment(map);
		}
		return structureDao.deleteStructureById(list);
	}

	@Override
	public StructureModel findOne(Integer id) {
		return structureDao.findOne(id);
	}

	@Override
	public int updatePolicyByList(Integer policyId, List<Integer> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("policyId", policyId);
		map.put("list", list);
		return structureDao.updatePolicyByList(map);
	}

	@Override
	public Integer queryPolicyById(StructureModel structure) {
		return structureDao.queryPolicyById(structure);
	}

	public String buildDepartmentName(Integer parentId) {
		departmentName = "";
		queryParentNameById(parentId);
		if (departmentName != null && !"".equals(departmentName)) {
			return departmentName.substring(0, departmentName.length() - 1);
		}
		return departmentName;
	}

	/**
	 * ���ݲ��ŵ�id��ѯ�ϼ����ŵ�id�Ͳ�������
	 */
	@Override
	public void queryParentNameById(Integer parentId) {

		StructureModel structure = structureDao.queryParentNameById(parentId);
		if (structure != null) {
			if (structure.getParent() != null) {
				departmentName += structure.getName() + "/";
				queryParentNameById(structure.getParent().getId());
			}
		}
	}

	/**
	 * ���ݲ������Ʋ�ѯ�Ĳ����Ƿ��Ѿ�����
	 */
	@Override
	public int queryCountByName(Map<String, Object> map) {

		return structureDao.queryCountByName(map);
	}

	@Override
	public int queryUserById(List<Integer> list) {

		return structureDao.queryUserById(list);
	}

	/**
	 * ���ݲ��ŵ�id��ѯ���ϼ����ŵĲ���
	 */
	@Override
	public int queryParentPolicyById(Integer id) {

		return structureDao.queryParentPolicyById(id);
	}

	@Override
	public List<StructureModel> findByIds(List<Integer> ids) {
		if(CollectionUtils.isNotEmpty(ids)){
			return (List<StructureModel>) structureDao.findByIds(ids);
		}else{
			return null;
		}
	}

	/**
	 * 根据上级部门的id查询下一级部门的id
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<StructureModel> queryAllchildren(Integer id) {
		return structureDao.queryAllchildren(id);
	}

	public void queryAllChildrenId(Integer id, List<StructureModel> list, List<Integer> idList) {

		for (StructureModel s : list) {
			if (s.getParent() != null) {
				if (id.equals(s.getParent().getId())) {
					idList.add(s.getId());
					queryAllChildrenId(s.getId(), list, idList);
				}
			}
		}
		return;
	}

	/**
	 * 找到当前部门下的所有子部门的策略id
	 */
	public void queryAllChildrenPolicyId(Integer id, List<StructureModel> list, List<StructureModel> idList) {
		for (StructureModel s : list) {
			if (s.getParent() != null) {
				if (id.equals(s.getParent().getId())) {
					idList.add(s);
					queryAllChildrenPolicyId(s.getId(), list, idList);
				}
			}
		}
		return;
	}

	@Override
	public int queryPolicyIdById(Integer id) {

		return structureDao.queryPolicyIdById(id);
	}

	@Override
	public String queryNameById(Integer id) {

		return structureDao.queryNameById(id);
	}

	@Override
	public Integer queryOrgId(Integer id) {

		return structureDao.queryOrgId(id);
	}

	@Override
	public String queryParentByparentId(Integer departmentId) {

		return structureDao.queryParentByparentId(departmentId);
	}

	@Override
	public List<String> queryChildName(Integer id) {

		return structureDao.queryChildName(id);
	}

	@Override
	public List<StructureModel> findAllByMap(Map<String, Object> map) {
		return structureDao.findAllByMap(map);
	}

	/**
	 * 
	 */
	@Override
	public List<StructureModel> getChildren(Integer parentId,Integer orgId) {
		if(parentId!=null){
			return structureDao.getChildren(parentId);
		}
		if(orgId!=null){
			StructureModel root=structureDao.getRoot(orgId);
			if(root!=null){
				List<StructureModel> list=new ArrayList<StructureModel>();
				List<StructureModel> children=structureDao.getChildren(root.getId());
				if(CollectionUtils.isNotEmpty(children)){
					list.addAll(children);
				}
				return list;
			}
		}
		return null;
	}

}
