package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DptManagerDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.DptManagerService;

import jodd.util.StringUtil;

@Service
public class DptManagerServiceImpl implements DptManagerService {
	@Autowired
	private DptManagerDao dptManagerDao;
	@Autowired
	private MessageSource messageSource;
	
    /**
     * 查询该部门管理员
     */
	public Page findAllByOrgID(Integer orgId,Integer start,Integer length) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("pageNum", start==null?0:start);
		map.put("pageSize", length==null?10:length);
		Page page=new Page();
//		Integer ii = dptManagerDao.findRecordsByPageCount(map);
		List<UserRoleDepartmentModel> list=(List<UserRoleDepartmentModel>) dptManagerDao.findRecordsByPage(map);
		Integer count=this.AllCountByOrgId(orgId);
		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return  page; 
	}
	
	@Override
	public Integer AllCountByOrgId(Integer orgId) {
		return dptManagerDao.AllCountByOrgId(orgId);
	}
	
	@Override
	public UserRoleDepartmentModel findOne(Integer id) {
		return dptManagerDao.findOne(id);
	}
	
    /**
     * 启用禁用管理员
     */
	@Override
	public Map<String, Object> update(UserRoleDepartmentModel userRoleDepartment,UserModel user) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (!StringUtil.isBlank(user.getId().toString())) {
			userRoleDepartment = dptManagerDao.findOne(user.getId());
			String managerName=userRoleDepartment.getUser().getRealname();
			userRoleDepartment.setUser(user);
			if (userRoleDepartment.getIslock() == 0) {
				userRoleDepartment.setIslock(1);
                // String
                // active=messageSource.getMessage("web.institution.dptmanagerserviceimpl.disable",null,
                // LocaleContextHolder.getLocale());
				map.put("managerName", managerName);
                // map.put("active", active);
				//request.setAttribute("managerName", managerName);
				//request.setAttribute("active", active);
			} else {
				userRoleDepartment.setIslock(0);
                // String
                // active=messageSource.getMessage("web.institution.dptmanagerserviceimpl.enable",null,
                // LocaleContextHolder.getLocale());
				map.put("managerName", managerName);
                // map.put("active", active);
				//request.setAttribute("managerName", managerName);
				//request.setAttribute("active", active);
			}
			int flag=dptManagerDao.update(userRoleDepartment);
			map.put("flag", flag);
			map.put("islock", userRoleDepartment.getIslock());
			}
		return map;
	}
	@Override
	public int deleteUrdByUrd(Integer id) {
		return dptManagerDao.deleteUrdByUrd(id);
	}

	@Override
	public int delete(Integer uid,Integer urd) {
		dptManagerDao.deleteOmByUserId(uid);
		dptManagerDao.deleteUdByUrd(urd);
		int flag = dptManagerDao.deleteUrdByUrd(urd);
		return flag;
	}

	@Override
	public int deleteUdByUrd(Integer id) {
		return dptManagerDao.deleteUdByUrd(id);
	}

	@Override
	public UserRoleDepartmentModel queryRoleByUid(Integer id) {
		return dptManagerDao.queryRoleByUid(id);
	}

	@Override
	public String findRoleNameById(Integer id) {
		// TODO Auto-generated method stub
		return dptManagerDao.findRoleNameById(id);
	}

	@Override
	public Page queryByParamsMap(Map<String, Object> map)
	{
		Page page=new Page();
		List<UserRoleDepartmentModel> list=(List<UserRoleDepartmentModel>) dptManagerDao.findRecordsByPage(map);
		Integer count=dptManagerDao.queryCountByParamsMap(map);
		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return  page; 
	}

	
	@Override
	public Integer queryAuthByUid(Integer id) {
		return dptManagerDao.queryAuthByUid(id);
	}
}
	