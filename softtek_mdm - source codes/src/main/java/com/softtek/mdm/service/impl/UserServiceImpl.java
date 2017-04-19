package com.softtek.mdm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeletedUserDao;
import com.softtek.mdm.dao.ManagerDao;
import com.softtek.mdm.dao.StructureDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.dao.UserDepartmentDao;
import com.softtek.mdm.dao.UserRoleDepartmentDao;
import com.softtek.mdm.dao.UserVirtualGroupDao;
import com.softtek.mdm.dao.VirtualGroupDao;
import com.softtek.mdm.model.DeletedUserModel;
import com.softtek.mdm.model.ExcelInsertUserModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserExportModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.model.UserVirtualGroupModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.UserDepartmentService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.service.UserVirtualGroupService;
import com.softtek.mdm.util.CommUtil;

import jodd.util.StringUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private StructureDao structureDao;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private VirtualGroupDao virtualGroupDao;
	@Autowired
	private UserVirtualGroupDao userVirtualGroupDao;
	@Autowired
	private UserVirtualGroupService userVirtualGroupService;
	@Autowired
	private UserRoleDepartmentDao userRoleDepartmentDao;
	@Autowired
	private UserDepartmentDao userDepartmentDao;
	@Autowired
	private ManagerDao managerDao;
	@Autowired
	private UserDepartmentService userDepartmentService;
	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	@Autowired
	private DeletedUserDao deletedUserDao;
	@Autowired
	private LicenseService licenseService;
	
	
	
	@Override
	public UserModel findUser(String name,Integer orgId) {
		return userDao.findUser(name,orgId);
	}

	@Override
	public int save(UserModel entity) {
		return userDao.save(entity);
	}

	@Override
	/**
     * 查询该部门下的人员
     */
	public Page findUserBysIds(Integer orgId, Integer structureId,Integer start,Integer length) {
        // 查询所有的部门
		List<StructureModel> stelist=(List<StructureModel>) structureDao.findAllByOrgID(orgId);
		
		List<Integer> idList=new ArrayList<Integer>();
        idList.add(structureId);// 加入当前部门id
        // 对部门进行处理，将查询所有子部门的id放入数组
		scanNodes(idList,stelist,structureId);
		
		Map<String, Object> maps=new HashMap<String, Object>();
		maps.put("orgId", orgId);
		maps.put("idlist", idList);
		Integer recordsTotal=userDao.findAllCount(maps);
		
		maps.put("pageNum", start);
		maps.put("pageSize", length);
		Page page=new Page();
		List<UserModel> userList=(List<UserModel>)userDao.findUserBysIds(maps);
		for (UserModel usr : userList) {
			Integer deviceCount=deviceBasicInfoService.findCountByUser(usr);
            // 使用用户的备注来保存用户的设备数目
			usr.setMark(deviceCount.toString());
			if(usr.getPolicy()!=null){
				PolicyModel policy=policyService.findOne(usr.getPolicy().getId());
				usr.setPolicy(policy);
			}
			ManagerModel isManager=managerService.findOneByName(usr.getUsername());
			if(isManager!=null){
                // 用性别来保存是否是管理员
				usr.setGender(1);
			}else{
				usr.setGender(0);
			}
		}
		page.setData(userList);
		page.setRecordsTotal(recordsTotal);
		page.setRecordsFiltered(recordsTotal);
		return page;
	}

	/**
     * 递归扫描子节点
     * 
     * @param list
     * @param stelist
     * @param structureId
     * @return
     */
	private List<Integer> scanNodes(List<Integer> list,List<StructureModel> stelist,Integer structureId){
		for (StructureModel ste : stelist) {
            // 当前节点的父节点是该部门，表明当前节点是该部门的子节点
			if(ste.getParent()!=null){
				if(StringUtil.equals(structureId.toString(), ste.getParent().getId().toString())){
					if(!list.contains(ste.getId())){
						list.add(ste.getId());
						scanNodes(list,stelist,ste.getId());
					}
					
				}
			}
		}
		return list;
	}

	@Override
	public UserModel findOne(Integer id) {
		return userDao.findOne(id);
	}

	@Override
	public int update(UserModel entity) {
		return userDao.update(entity);
	}

	@Override
	public int deleteWithPk(Integer id) {
		return userDao.deleteWithPk(id);
	}

	@Override
	public int deleteWithPKs(List<Integer> ids) {
		if(ids.size()>0){
			return userDao.deleteWithPKs(ids);
		}else{
			return 0;
		}
	}

	@Override
	public int updateActiveBatch(Integer[] ids) {
		if (ids.length>0) {
			return userDao.updateActiveBatch(ids);
		}
		return 0;
	}

	@Override
	public Integer checkUser(String username,Integer orgId) {
		return userDao.checkUser(username,orgId);
	}

	@Override
	public int findCountByMap(Integer steId,Integer orgId) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("orgId", orgId);
		map.put("steId", steId);
		int count=userDao.findCountByMap(map);
		return count;
	}
	
	@Override
	public int findCountByMaps(Integer steId, Integer orgId, UserModel user) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("steId", steId);
		map.put("user", user);
		int count=userDao.findCountByMaps(map);
		return count;
	}
	
	

	@Override
	public List<UserModel> findUsersByMap(Integer steId,Integer orgId) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("orgId", orgId);
		map.put("steId", steId);
		List<UserModel> list=(List<UserModel>) userDao.findUsersByMap(map);
		return list;
	}

	@Override
	public Page findUserBysIds(List<StructureModel> list, Integer orgId,Integer structureId, Integer start, Integer length) {
		List<StructureModel> stelist=list;
		
		List<Integer> idList=new ArrayList<Integer>();
        idList.add(structureId);// 加入当前部门id
        // 对部门进行处理，将查询所有子部门的id放入数组
		scanNodes(idList,stelist,structureId);
		
		Map<String, Object> maps=new HashMap<String, Object>();
		maps.put("orgId", orgId);
		maps.put("idlist", idList);
		Integer recordsTotal=userDao.findAllCount(maps);
		
		maps.put("pageNum", start);
		maps.put("pageSize", length);
		Page page=new Page();
		List<UserModel> userList=(List<UserModel>)userDao.findUserBysIds(maps);
		for (UserModel usr : userList) {
			Integer deviceCount=deviceBasicInfoService.findCountByUser(usr);
            // 使用用户的备注来保存用户的设备数目
			usr.setMark(deviceCount.toString());
			if(usr.getPolicy()!=null){
				PolicyModel policy=policyService.findOne(usr.getPolicy().getId());
				usr.setPolicy(policy);
			}
			ManagerModel isManager=managerService.findOneByName(usr.getUsername());
			if(isManager!=null){
                // 用性别来保存是否是管理员
				usr.setGender(1);
			}else{
				usr.setGender(0);
			}
		}
		page.setData(userList);
		page.setRecordsTotal(recordsTotal);
		page.setRecordsFiltered(recordsTotal);
		return page;
	}

	@Override
	public Page findUserBysExpression(List<StructureModel> list, Map<String, Object> paramMap) {
		List<StructureModel> stelist=list;
		
		List<Integer> idList=new ArrayList<Integer>();
		Integer structureId = (Integer) paramMap.get("structureId");
        idList.add(structureId);// 加入当前部门id
        // 对部门进行处理，将查询所有子部门的id放入数组
		scanNodes(idList,stelist,structureId);
		paramMap.put("idlist", idList);
		paramMap.put("rootid", structureId);
		Page page=new Page();
		List<UserModel> userList=(List<UserModel>)userDao.findUserByMap(paramMap);
		//List<UserModel> userList=(List<UserModel>)userDao.findUsersInfoByMap(paramMap);
		for (UserModel usr : userList) {
			Integer deviceCount=deviceBasicInfoService.findCountByUser(usr);
            // 使用用户的备注来保存用户的设备数目
			usr.setMark(deviceCount.toString());
			if(usr.getPolicy()!=null){
				PolicyModel policy=policyService.findOne(usr.getPolicy().getId());
				usr.setPolicy(policy);
			}
			ManagerModel isManager=managerService.findOneByOrgAndName(usr.getOrganization().getId(), usr.getUsername());;
			if(isManager!=null){
                // 用性别来保存是否是管理员
				usr.setGender(1);
			}else{
				usr.setGender(0);
			}
		}
		page.setData(userList);
		Integer recordsTotal=userDao.findCountWithMap(paramMap);
		//Integer recordsTotal=userDao.findUserInfoCountWithMap(paramMap);
		page.setRecordsTotal(recordsTotal);
		page.setRecordsFiltered(recordsTotal);
		return page;
	}

	@Override
	public int findCountByDid(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<Integer> ids=(List<Integer>) map.get("ids");
		if(ids!=null&&ids.size()>0){
			return userDao.findCountByDid(map);
		}
		return 0;
	}

	@Override
	public void updatePolicyById(UserModel user) {
		
		userDao.updatePolicyById(user);
	}

	@Override
	public int findCountByOrgId(Integer orgId,List<Integer> idlist) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orgId", orgId);
			map.put("idlist", idlist);
			return userDao.findCountByOrgId(map);
	}

	@Override
	public List<UserModel> findUserNamesByMaps(Integer orgId, List<Integer> steIds) {
		Map<String, Object> map=new HashMap<String,Object>();
		if(steIds!=null&&steIds.size()>0){
			map.put("orgId", orgId);
			map.put("list", steIds);
			return userDao.findUserNamesByMaps(map);
		}
		return null;
	}
	@Override
	public UserModel queryUserInfoById(Integer id) {
		
		return userDao.queryUserInfoById(id);
	}

	@Override
	public int create(Map<String, Object> map) {
		UserModel user=(UserModel) map.get("user");
		userDao.save(user);
		String vtls=(String) map.get("vtls");
		if (!StringUtil.isBlank(vtls)) {
			String[] vtl = StringUtil.split(vtls, ",");
			List<UserVirtualGroupModel> uvgList=new ArrayList<UserVirtualGroupModel>();
			for (String v : vtl) {
				UserVirtualGroupModel userVirtualGroup=new UserVirtualGroupModel();
				
				userVirtualGroup.setOrganization(user.getOrganization());
				VirtualGroupModel virtualGroup =new VirtualGroupModel();
				virtualGroup = virtualGroupDao.findOne(Integer.parseInt(v));
				userVirtualGroup.setVirtualGroup(virtualGroup);
				userVirtualGroup.setUser(user);
				userVirtualGroup.setVirtualCollection(virtualGroup.getVirtualCollection());
				uvgList.add(userVirtualGroup);
			}
            // 批量插入
			return userVirtualGroupDao.saveRecordsBatch(uvgList);
		}
		return 0;
	}

	@Override
	public int update(Map<String, Object> map) {
		UserModel user=(UserModel) map.get("user");
		OrganizationModel organization = (OrganizationModel) map.get("organization");
        // 如果用户修改了密码，则更新用户密码
		if (user!=null) {
			if(!StringUtils.isBlank(user.getPassword())){
				Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
				user.setPassword(md5PasswordEncoder.encodePassword(user.getPassword().trim(), null));
				ManagerModel manager = managerService.findOneByOrgAndName(organization.getId(), user.getUsername());
				if (manager != null) {
					manager.setPassword(user.getPassword().trim());
					managerService.update(manager);
				}
			}
			userDao.update(user);
			
            // 更新用户的虚拟组
			if (!StringUtil.isBlank(user.getId().toString())) {
				userVirtualGroupDao.truncateWithUserId(user.getId());
			}
			String vtls=(String) map.get("vtls");
			if (!vtls.equals("")) {
				List<UserVirtualGroupModel> uvgList=new ArrayList<UserVirtualGroupModel>();
				String[] vtlIds = StringUtil.split(vtls, ",");
				if (vtlIds != null && vtlIds.length > 0) {
					for (int i = 0; i < vtlIds.length; i++) {
						UserVirtualGroupModel userVirtualGroup=new UserVirtualGroupModel();
						userVirtualGroup.setOrganization(organization);
						VirtualGroupModel virtualGroup =new VirtualGroupModel();
						virtualGroup = virtualGroupDao.findOne(Integer.parseInt(vtlIds[i]));
						userVirtualGroup.setVirtualGroup(virtualGroup);
						userVirtualGroup.setVirtualCollection(virtualGroup.getVirtualCollection());
						userVirtualGroup.setUser(user);
						uvgList.add(userVirtualGroup);
						//userVirtualGroupService.save(userVirtualGroup);
					}
                    // 批量插入
					return userVirtualGroupDao.saveRecordsBatch(uvgList);
				}
			}
		}
		return 0;
	}

	@Override
	public int updateUser(UserModel newUser) {
		return userDao.updateUser(newUser);
	}

	@Override
	public int deleteUserRelationInfo(Integer id,Integer orgId) {
		UserModel user=userDao.findOne(id);
		DeletedUserModel deletedUser=new DeletedUserModel();
		deletedUser.setUser(user);
		deletedUserDao.save(deletedUser);
		int flag = userDao.truncateWithPk(id);
		managerService.truncateWithUserId(id);
		deviceBasicInfoService.trucateWithUserId(id);
		userVirtualGroupService.truncateWithUserId(id);
		userVirtualGroupService.deleteByUser(orgId, id);
		return flag;
	}

	@Override
	public int deleteUserBatch(List<Integer> ids, Integer orgId) {
		if(ids!=null&&ids.size()>0){
			for (Integer id : ids) {
				userVirtualGroupService.deleteByUser(orgId,id);
				UserModel user=userDao.findOne(id);
				DeletedUserModel deletedUser=new DeletedUserModel();
				deletedUser.setUser(user);
				deletedUserDao.save(deletedUser);
			}
		}
		int flag = userDao.truncateWithPKs(ids);
		managerService.truncateWithUserIds(ids);
		userVirtualGroupService.truncateWithUserIds(ids);
		deviceBasicInfoService.trucateWithUserIds(ids);
		return flag;
	}

	@Override
	public int promotion(Map<String, Object> map) {
		UserRoleDepartmentModel userRoleDepartment=(UserRoleDepartmentModel) map.get("userRoleDepartment");
		userRoleDepartmentDao.save(userRoleDepartment);
		@SuppressWarnings("unchecked")
		List<UserDepartmentModel> userDepartmentList=(List<UserDepartmentModel>) map.get("userDepartmentList");
		if(userDepartmentList!=null && userDepartmentList.size()>0){
			for(int i=0;i<userDepartmentList.size();i++){
				userDepartmentList.get(i).setUserRoleDepartment(userRoleDepartment);
			}
		}
		userDepartmentDao.saveRecordsBatch(userDepartmentList);
		ManagerModel manager = (ManagerModel) map.get("manager");
		managerDao.save(manager);
		return 0;
	}

	@Override
	public void truncateDepartToCustomer(int mid, int uid) {
		managerService.truncateWithPk(mid);
		userDepartmentService.truncateWithUserId(uid);
		userRoleDepartmentService.truncateWithUserId(uid);
	}

	@Override
	public List<UserExportModel> exportUsersById(List<Integer> idList) {
		Map<String, Object> map=new HashMap<String,Object>();
		if(idList!=null&&idList.size()>0){
			map.put("idList", idList);
			return userDao.exportUsersById(map);
		}
		return null;
	}

	@Override
	public List<String> findAllMember(Integer orgid) {
		return userDao.findAllMember(orgid);
	}

	@Override
	public int importUsers(List<ExcelInsertUserModel> excelList) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("list", excelList);
		return userDao.importUsers(map);
	}

	@Override
	public int deleteAllByMap(List<StructureModel> list,Map<String, Object> map) {
		List<StructureModel> stelist=list;
		
		List<Integer> idList=new ArrayList<Integer>();
		Integer structureId = (Integer) map.get("structureId");
        idList.add(structureId);// 加入当前部门id
        // 对部门进行处理，将查询所有子部门的id放入数组
		scanNodes(idList,stelist,structureId);
		map.put("idlist", idList);
		List<Integer> userList=(List<Integer>) userDao.findIdsByMap(map);
		Integer orgId=(Integer) map.get("orgId");
		int count=0;
		for (Integer id : userList) {
			int flag=deleteUserRelationInfo(id, orgId);
			count=flag>0?++count:count;
		}
		return count;
	}

	@Override
	public int activeAllByMap(List<StructureModel> list, Map<String, Object> map) {
		List<StructureModel> stelist=list;
		
		List<Integer> idList=new ArrayList<Integer>();
		Integer structureId = (Integer) map.get("structureId");
		Integer orgId=(Integer) map.get("orgId");
        idList.add(structureId);// 加入当前部门id
        // 对部门进行处理，将查询所有子部门的id放入数组
		scanNodes(idList,stelist,structureId);
		map.put("idlist", idList);
		List<UserModel> userList=(List<UserModel>) userDao.findRecordsByMap(map);
		if(userList==null||userList.size()==0){
			return 0;
		}
		int count=0;
		for (UserModel user : userList) {
			if(user.getIs_active()==0){
				count++;
			}
		}
		if(licenseService.isActiveNumBeyondOrgLimit(count, orgId)){
			return -1;
		}
		count=0;
		for (UserModel u : userList) {
			u.setIs_active(1);
			int flag=userDao.update(u);
			count=flag>0?++count:count;
		}
		return count;
	}

	@Override
	public List<UserModel> getUsersListsByMap(Map<String, Object> paramMap) {
        List<UserModel> list = userDao.getExportUsersListsByMap(paramMap);
        UserModel todayUserModel = userDao.getTodayUsersListsByMap(paramMap);
        String endTime = (String) paramMap.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(endTime);
                Date now = new Date();
                String nowStr = sdf.format(now);
                Date nowDate = sdf.parse(nowStr);
                if (date.getTime() >= nowDate.getTime()) {
                    list.add(0, todayUserModel);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            list.add(0, todayUserModel);
        }
        return list;
    }

	@Override
	public List<UserModel> getTotalUsersListsByMap(Map<String, Object> paramMap) {
		
		List<UserModel> lists = userDao.getExportTotalUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		return userLists;
	}

	@Override
	public List<UserModel> getActiveUsersListsByMap(Map<String, Object> paramMap) {
		
		List<UserModel> lists = userDao.getExportActiveUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		return userLists;
	}

	@Override
	public List<UserModel> getInActiveUsersListsByMap(Map<String, Object> paramMap) {
		
		List<UserModel> lists = userDao.getExportInActiveUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		return userLists;
	}

	@Override
	public List<UserModel> getDeleteUsersListsByMap(Map<String, Object> paramMap) {
		
		List<UserModel> lists = userDao.getExportDeleteUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		return userLists;
	}
	
	@SuppressWarnings({"unchecked"})
	@Override
	public List<UserModel> getUserModels(String userIds)
	{
		List<UserModel> userModelList = new ArrayList<UserModel>();
		if (StringUtils.isNotEmpty(StringUtils.trim(userIds)))
		{
			String [] arr = userIds.split(",");
			List<String> userIdList = org.springframework.util.CollectionUtils.arrayToList(arr);
			
			userModelList = userDao.getUserModels(userIdList);
		}
		return userModelList;
	}

	@Override
	public List<Integer> findIdsBy(List<StructureModel> list,Integer departId) {
		List<Integer> idList=new ArrayList<Integer>();
        idList.add(departId);// 加入当前部门id
        // 对部门进行处理，将查询所有子部门的id放入数组
		scanNodes(idList,list,departId);
		return idList;
	}
	
	@Override
	public UserModel checkPassword(String userName, Integer orgId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("orgId", orgId);
		return userDao.checkPassword(map);
	}

	@Override
	public int updateDeviceLoginCount(Integer deviceLoginCount,Integer id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("deviceLoginCount", deviceLoginCount);
		map.put("id", id);
		return userDao.updateDeviceLoginCount(map);
	}

    @Override
    public List<UserModel> getChartUsersListsByMap(Map<String, Object> paramMap) {
        List<UserModel> list = userDao.getChartUsersListsByMap(paramMap);
        UserModel todayUserModel = userDao.getTodayUsersListsByMap(paramMap);
        String endTime = (String) paramMap.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(endTime);
                Date now = new Date();
                String nowStr = sdf.format(now);
                Date nowDate = sdf.parse(nowStr);
                if (date.getTime() >= nowDate.getTime()) {
                    list.add(list.size(), todayUserModel);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            list.add(list.size(), todayUserModel);
        }
        return list;
    }

	@Override
	public UserModel attemp(String account, String pass, Integer orgId) {
		if(StringUtils.isNotEmpty(account)&&StringUtils.isNotEmpty(pass)&&orgId!=null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("username", account);
			map.put("password", CommUtil.md5PasswordEncoderEncode(pass));
			map.put("orgId", orgId);
			return userDao.attemp(map);
		}
		return null;
	}

}
