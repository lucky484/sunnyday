package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DptManagerDao;
import com.softtek.mdm.dao.ManagerDao;
import com.softtek.mdm.dao.UserDepartmentDao;
import com.softtek.mdm.dao.UserRoleDepartmentDao;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.UserRoleDepartmentService;

import jodd.util.StringUtil;
@Service
public class UserRoleDepartmentServiceImpl implements UserRoleDepartmentService {

	@Autowired
	private UserRoleDepartmentDao userRoleDepartmentDao;
	@Autowired
	private UserDepartmentDao userDepartmentDao;
	@Autowired
	private DptManagerDao dptManagerDao;
	@Autowired
	private ManagerDao managerDao;
	@Override
	public int save(UserRoleDepartmentModel entity) {
		return userRoleDepartmentDao.save(entity);
	}

	@Override
	public int truncateWithUserId(Integer id) {
		UserRoleDepartmentModel userRoleDepartmentModel=userRoleDepartmentDao.findByUserId(id);
		if(userRoleDepartmentModel!=null){
			return userRoleDepartmentDao.truncateWithPk(userRoleDepartmentModel.getId());
		}
		return 0;
		
	}

	@Override
	public UserRoleDepartmentModel findOneByMaps(Integer orgId, Integer userId) {
		Map<String, Integer> map=new HashMap<String,Integer>();
		map.put("orgId", orgId);
		map.put("userId", userId);
		return userRoleDepartmentDao.findOneByMaps(map);
	}

	@Override
	public int truncateWithRoleId(Integer roleId) {
		List<UserRoleDepartmentModel> list=userRoleDepartmentDao.findWithRoleId(roleId);
		if(list.size()>0){
			for (UserRoleDepartmentModel userRoleDepartment : list) {
				userDepartmentDao.truncateWithFId(userRoleDepartment.getId());
				userRoleDepartmentDao.truncateWithPk(userRoleDepartment.getId());
			}
			return list.size();
		}
		return 0;
	}

	@Override
	public int findOrdIdByUid(Integer id) {
		// TODO Auto-generated method stub
		return userRoleDepartmentDao.findOrdIdByUid(id);
	}

	@Override
	public int truncateWithPk(Integer roleId) {
		// TODO Auto-generated method stub
		return userRoleDepartmentDao.truncateWithPk(roleId);
	}

	@Override
	public Map<String, Object> update(UserRoleDepartmentModel userRoleDepartment,String departmentIds,OrganizationModel organization,Integer auth) {
		Map<String,Object> map=new HashMap<String, Object>();
		Integer uid = userRoleDepartment.getUser().getId();
		int urdid = userRoleDepartmentDao.findOrdIdByUid(uid);
		if (urdid > 0) {
			userRoleDepartmentDao.truncateWithPk(urdid);
			userRoleDepartment.setOrganization(organization);
			userRoleDepartmentDao.save(userRoleDepartment);
		}
		if(auth!=null){
			map.put("uid", uid);
			map.put("auth",auth);
			managerDao.updateAuth(map);
		}
		int ud = userDepartmentDao.truncateWithUrdId(urdid);
		if (ud > 0) {
			String[] ids = StringUtil.split(departmentIds, ",");
			String departName="";
			for (String str : ids) {
				UserDepartmentModel userDepartment = new UserDepartmentModel();
				userDepartment.setUserRoleDepartment(userRoleDepartment);
				userDepartment.setOrganization(organization);
				StructureModel structure = new StructureModel();
				structure.setId(Integer.parseInt(str));
				departName+=userDepartmentDao.findDptNameById(Integer.parseInt(str))+",";
				userDepartment.setStructure(structure);
				userDepartmentDao.save(userDepartment); 
				//设置日志的管理员名称的值
			}
			String roleName=dptManagerDao.findRoleNameById(userRoleDepartment.getRole().getId());
			userRoleDepartment = dptManagerDao.findOne(uid);
			String managerName=userRoleDepartment.getUser().getRealname();
			departName=(String) departName.subSequence(0, departName.length()-1);
			map.put("managerName", managerName);
			map.put("departName", departName);
			map.put("roleName", roleName);
		}
		return map;
	}
}
