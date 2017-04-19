package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceLegalListDao;
import com.softtek.mdm.dao.DeviceRuleDao;
import com.softtek.mdm.dao.DeviceRuleDepartmentDao;
import com.softtek.mdm.dao.DeviceRuleItemRecordDao;
import com.softtek.mdm.dao.DeviceRuleItemRelationDao;
import com.softtek.mdm.dao.DeviceRuleOperationItemRecordDao;
import com.softtek.mdm.dao.DeviceRuleOperationItemRelationDao;
import com.softtek.mdm.dao.DeviceRuleUserDao;
import com.softtek.mdm.dao.DeviceRuleVirtualGroupDao;
import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.DeviceRuleDepartmentModel;
import com.softtek.mdm.model.DeviceRuleItemRecordModel;
import com.softtek.mdm.model.DeviceRuleItemRelationModel;
import com.softtek.mdm.model.DeviceRuleMatchModel;
import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRecordModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.model.DeviceRuleUserModel;
import com.softtek.mdm.model.DeviceRuleVirtualGroupModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.DeviceRuleDepartmentService;
import com.softtek.mdm.service.DeviceRuleItemRecordService;
import com.softtek.mdm.service.DeviceRuleItemRelationService;
import com.softtek.mdm.service.DeviceRuleOperationItemRecordService;
import com.softtek.mdm.service.DeviceRuleOperationItemRelationService;
import com.softtek.mdm.service.DeviceRuleService;
import com.softtek.mdm.service.DeviceRuleUserService;
import com.softtek.mdm.service.DeviceRuleVirtualGroupService;
import com.softtek.mdm.util.IosPushUtil;
@Service
public class DeviceRuleServiceImpl implements DeviceRuleService {

	@Autowired
	private DeviceRuleDao deviceRuleDao;
	@Autowired
	private DeviceRuleDepartmentDao deviceRuleDepartmentDao;
	@Autowired
	private DeviceRuleVirtualGroupDao deviceRuleVirtualGroupDao;
	@Autowired
	private DeviceRuleUserDao deviceRuleUserDao;
	@Autowired
	private DeviceRuleItemRelationDao deviceRuleItemRelationDao;
	@Autowired
	private DeviceRuleOperationItemRelationDao deviceRuleOperationItemRelationDao;
	@Autowired
	private DeviceLegalListDao deviceLegalListDao;
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	@Autowired
	private DeviceRuleDepartmentService deviceRuleDepartmentService;
	@Autowired
	private DeviceRuleVirtualGroupService deviceRuleVirtualGroupService;
	@Autowired
	private DeviceRuleUserService deviceRuleUserService;
	@Autowired
	private DeviceRuleItemRelationService deviceRuleItemRelationService;
	@Autowired
	private DeviceRuleItemRecordService deviceRuleItemRecordService;
	@Autowired
	private DeviceRuleOperationItemRecordService deviceRuleOperationItemRecordService;
	@Autowired
	private DeviceRuleOperationItemRelationService deviceRuleOperationItemRelationService;
	@Autowired
	private DeviceRuleItemRecordDao deviceRuleItemRecordDao;
	@Autowired
	private DeviceRuleOperationItemRecordDao deviceRuleOperationItemRecordDao;
	
	@Override
	public int save(DeviceRuleModel entity) {
		return deviceRuleDao.save(entity);
	}
	
	@Override
	public Page findWithPagination(Map<String, Object> maps) {
		Integer recordsTotal=deviceRuleDao.findCountByParams(maps);
		List<DeviceRuleModel> list=(List<DeviceRuleModel>) deviceRuleDao.findByMap(maps);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				//获取所有该规则下的设备检测结果（不包含之前的）
				//获取设备规则下的用户id集合
				List<Integer> uids=deviceRuleDao.findUserIdsByPk(list.get(i).getId());
				if(uids!=null&&uids.size()>0){
					List<Integer> dids=deviceBasicInfoDao.findDeviceIdsByUserIds(uids);
					if(dids!=null&&dids.size()>0){
						Map<String, Object> map=new HashMap<String,Object>();
						map.put("rid", list.get(i).getId());
						map.put("dids", dids);
						List<DeviceLegalListModel> legalList=deviceLegalListDao.findByRuleIdWithRuleIdAndDeviceIds(map);
						List<Integer> deviceIds=new ArrayList<Integer>();
						Integer allCount=0;
						Integer legalCount=0;
						Integer illegalCount=0;
						if(legalList!=null&&legalList.size()>0){
							for(int j=0;j<legalList.size();j++){
								/*allCount=legalList.size();
								if(legalList.get(j).getStatus()>0){
									illegalCount++;
								}*/
								allCount=dids.size();
								if(legalList.get(j).getStatus()>0&&legalList.get(j).getDeviceBasicInfo()!=null){
									Integer device_id=legalList.get(j).getDeviceBasicInfo().getId();
									if(!deviceIds.contains(device_id)){
										deviceIds.add(device_id);
										illegalCount++;
									}
									
								}
							}
						}
						legalCount=allCount-illegalCount;
						String isLegalStr=illegalCount.toString()+"/"+legalCount.toString()+"/"+allCount.toString();
						list.get(i).getOrganization().setName(isLegalStr);
					}else{
						list.get(i).getOrganization().setName("0/0/0");
					}
				}
			}
		}
		Page page=new Page();
		page.setData(list);
		page.setRecordsFiltered(recordsTotal);
		page.setRecordsTotal(recordsTotal);
		return page;
	}

	@Override
	public int findDeviceRoleCount(Integer orgid) {
		return deviceRuleDao.findCountByOrgId(orgid);
	}

	@Override
	public DeviceRuleModel findOne(Integer id) {
		if(id!=null){
			return deviceRuleDao.findOne(id);
		}
		return null;
	}

	@Override
	public int update(DeviceRuleModel entity) {
		if (entity!=null) {
			return deviceRuleDao.update(entity);
		}
		return 0;
	}

	@Override
	public int truncateWithPk(Integer id) {
		if(id!=null){
			deviceRuleDepartmentDao.truncateWithRuleId(id);
			deviceRuleVirtualGroupDao.truncateWithRuleId(id);
			deviceRuleUserDao.truncateWithRuleId(id);
			deviceRuleItemRelationDao.truncateWithRuleId(id);
			deviceRuleOperationItemRelationDao.truncateWithRuleId(id);
			return deviceRuleDao.truncateWithPk(id);
		}
		
		return 0;
	}

	@Override
	public List<Integer> findUserIdsByPk(Integer id) {
		if (id!=null) {
			
			List<Integer> idList = deviceRuleDao.findUserIdsByPk(id);
			return removeNullFromList(idList);
		}
		return null;
	}
	
	@Override
	public List<Integer> findRuleIdsByDeviceId(Integer did) {
		if(did!=null){
			return deviceRuleDao.findRuleIdsByDeviceId(did);
		}
		return null;
	}

	@Override
	public List<DeviceRuleModel> findRecordsByOrgId(Integer orgId) {
		if(orgId!=null){
			return deviceRuleDao.findRecordsByOrgId(orgId);
		}
		return null;
	}

	@Override
	public List<DeviceRuleModel> findAllActive() {
		return deviceRuleDao.findAllActive();
	}

	@Override
	public String save(Map<String, Object> map) {

		OrganizationModel organization = (OrganizationModel) map.get("organization");
		ManagerModel manager = (ManagerModel)map.get("manager");
		// 规则匹配
		DeviceRuleMatchModel deviceRuleMatch = new DeviceRuleMatchModel();
		String matchId = (String) map.get("matchId");
		deviceRuleMatch.setId(StringUtil.isBlank(matchId) == true ? 2 : Integer.parseInt(matchId));

		// 规则基本信息
		DeviceRuleModel deviceRule = new DeviceRuleModel();
		String ruleId = (String) map.get("ruleId");
		if (StringUtil.isBlank(ruleId)) {
			deviceRule.setIsactive(0);
			deviceRule.setCreateBy(manager.getId());
		} else {
			deviceRule.setId(Integer.parseInt(ruleId));
		}
		
		deviceRule.setName((String)map.get("detail_name"));
		deviceRule.setDescribe((String)map.get("detail_decribe"));
		String platform = (String)map.get("arrange_platform");
		deviceRule.setPlatform(StringUtil.isBlank(platform) == true ? 0 : Integer.parseInt(platform));
		deviceRule.setOrganization(organization);
		deviceRule.setUpdateBy(manager.getId());
		deviceRule.setDeviceRuleMatch(deviceRuleMatch);
		if (StringUtil.isBlank(ruleId)) {
			deviceRuleDao.save(deviceRule);
		} else {
			deviceRuleDao.update(deviceRule);
		}

		// 保存规则指定到部门
		String departmentIds = (String) map.get("arrange_departIds");
		if (!StringUtil.isBlank(ruleId)) {
			deviceRuleDepartmentService.truncateWithRuleId(deviceRule.getId());
		}
		List<DeviceRuleDepartmentModel> listDepartments=new ArrayList<DeviceRuleDepartmentModel>();
		if (!StringUtil.isBlank(departmentIds)) {
			String[] dids = StringUtil.split(departmentIds, ",");
			for (int i = 0; i < dids.length; i++) {
				DeviceRuleDepartmentModel department = new DeviceRuleDepartmentModel();
				//Integer pk = CommUtil.getPrimaryKey();
				//department.setId(pk + i);
				department.setDeviceRule(deviceRule);
				StructureModel structure = new StructureModel();
				structure.setId(Integer.parseInt(dids[i]));
				department.setStructure(structure);
				listDepartments.add(department);
				//deviceRuleDepartmentService.save(department);
			}
			if(listDepartments.size()>0){
				//批量保存listDepartments
				deviceRuleDepartmentDao.saveRecordsBatch(listDepartments);
			}
		}

		// 保存规则指定到虚拟组
		String vtlIds = (String) map.get("arrange_vtlIds");
		if (!StringUtil.isBlank(ruleId)) {
			deviceRuleVirtualGroupService.truncateWithRuleId(deviceRule.getId());
		}
		List<DeviceRuleVirtualGroupModel> listVirtual=new ArrayList<DeviceRuleVirtualGroupModel>();
		if (!StringUtil.isBlank(vtlIds)) {
			String[] vids = StringUtil.split(vtlIds, ",");
			for (int i = 0; i < vids.length; i++) {
				DeviceRuleVirtualGroupModel virtualGroup = new DeviceRuleVirtualGroupModel();
				//Integer pk = CommUtil.getPrimaryKey();
				//virtualGroup.setId(pk + i);
				virtualGroup.setDeviceRule(deviceRule);
				VirtualGroupModel virtual = new VirtualGroupModel();
				virtual.setId(Integer.parseInt(vids[i]));
				virtualGroup.setVirtualGroup(virtual);
				listVirtual.add(virtualGroup);
				//deviceRuleVirtualGroupService.save(virtualGroup);
			}
			if(listVirtual.size()>0){
				//批量保存规则到虚拟组
				deviceRuleVirtualGroupDao.saveRecordsBatch(listVirtual);
			}
		}

		// 保存规则指定到用户
		String userIds = (String) map.get("arrange_userIds");
		if (!StringUtil.isBlank(ruleId)) {
			deviceRuleUserService.truncateWithRuleId(deviceRule.getId());
		}
		List<DeviceRuleUserModel> listUsers=new ArrayList<DeviceRuleUserModel>();
		if (!StringUtil.isBlank(userIds)) {
			String[] uids = StringUtil.split(userIds, ",");
			for (int i = 0; i < uids.length; i++) {
				DeviceRuleUserModel user = new DeviceRuleUserModel();
				//Integer pk = CommUtil.getPrimaryKey();
				//user.setId(pk + i);
				user.setDeviceRule(deviceRule);
				UserModel u = new UserModel();
				u.setId(Integer.parseInt(uids[i]));
				user.setUser(u);
				listUsers.add(user);
				//deviceRuleUserService.save(user);
			}
			
			if(listUsers.size()>0){
				//批量保存规则到用户
				deviceRuleUserDao.saveRecordsBatch(listUsers);
			}
		}

		// 删除规则关联
		if (!StringUtil.isBlank(ruleId)) {
			deviceRuleItemRelationService.truncateWithRuleId(deviceRule.getId());
		}
		
		@SuppressWarnings("unchecked")
		List<DeviceRuleItemRecordModel> list=(List<DeviceRuleItemRecordModel>) map.get("list");
		@SuppressWarnings("unchecked")
		List<DeviceRuleItemRelationModel> listRelation=(List<DeviceRuleItemRelationModel>) map.get("listRelation");
		
		if(!CollectionUtils.isEmpty(list)){
			//list
			for(int i=0;i<list.size();i++){
				deviceRuleItemRecordDao.save(list.get(i));
				listRelation.get(i).setDeviceRuleItemRecord(list.get(i));
			}
			//deviceRuleItemRecordDao.saveRecordsBatch(list);
		}
		
		if(!CollectionUtils.isEmpty(listRelation)){
			for (int i = 0; i < listRelation.size(); i++) {
				listRelation.get(i).setDeviceRule(deviceRule);
			}
			//批量保存listRelation
			deviceRuleItemRelationDao.saveRecordsBatch(listRelation);
		}

		// 删除操作
		if (!StringUtil.isBlank(ruleId)) {
			deviceRuleOperationItemRelationService.truncateWithRuleId(deviceRule.getId());
		}
		
		@SuppressWarnings("unchecked")
		List<DeviceRuleOperationItemRecordModel> listItem=(List<DeviceRuleOperationItemRecordModel>) map.get("listItem");
		@SuppressWarnings("unchecked")
		List<DeviceRuleOperationItemRelationModel> listItemRelation=(List<DeviceRuleOperationItemRelationModel>) map.get("listItemRelation");
		
		if(!CollectionUtils.isEmpty(listItem)){
			//批量保存listItem
			for(int i=0;i<listItem.size();i++){
				deviceRuleOperationItemRecordDao.save(listItem.get(i));
				listItemRelation.get(i).setDeviceRuleOperationRecord(listItem.get(i));
			}
			//deviceRuleOperationItemRecordDao.saveRecordsBatch(listItem);
		}
		
		if(!CollectionUtils.isEmpty(listItemRelation)){
			for (int i = 0; i < listItemRelation.size(); i++) {
				listItemRelation.get(i).setDeviceRule(deviceRule);
			}
			//批量保存listItemRelation
			deviceRuleOperationItemRelationDao.saveRecordsBatch(listItemRelation);
		}
		return deviceRule.getName();
	}

	@Override
	public void copy(Map<String, Object> map) {
		DeviceRuleModel copy_rule = (DeviceRuleModel) map.get("copy_rule");
		deviceRuleDao.save(copy_rule);
		Integer rid=(Integer) map.get("rid");
		// 保存到指定部门
		List<DeviceRuleDepartmentModel> departmentlist = deviceRuleDepartmentService.findAllByRuleId(rid);
		if (departmentlist != null && departmentlist.size() > 0) {
			for (int i = 0; i < departmentlist.size(); i++) {
				DeviceRuleDepartmentModel temp = departmentlist.get(i);
				temp.setDeviceRule(copy_rule);
				//deviceRuleDepartmentService.save(temp);
			}
			deviceRuleDepartmentDao.saveRecordsBatch(departmentlist);
		}
		
		// 保存到指定虚拟组
		List<DeviceRuleVirtualGroupModel> virtualGrouplist = deviceRuleVirtualGroupService.findAllByRuleId(rid);
		if (virtualGrouplist != null && virtualGrouplist.size() > 0) {
			for (int i = 0; i < virtualGrouplist.size(); i++) {
				DeviceRuleVirtualGroupModel virtualGroup = virtualGrouplist.get(i);
				virtualGroup.setDeviceRule(copy_rule);
				//deviceRuleVirtualGroupService.save(virtualGroup);
			}
			deviceRuleVirtualGroupDao.saveRecordsBatch(virtualGrouplist);
		}
		
		// 保存到指定用户
		List<DeviceRuleUserModel> userlist = deviceRuleUserService.findAllByRuleId(rid);
		if (userlist != null && userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				DeviceRuleUserModel user = userlist.get(i);
				user.setDeviceRule(copy_rule);
				//deviceRuleUserService.save(user);
			}
			deviceRuleUserDao.saveRecordsBatch(userlist);
		}
		
		// 保存规则
		List<DeviceRuleItemRelationModel> itemRelationlist = deviceRuleItemRelationService.findAllByRuleId(rid);
		if (itemRelationlist != null && itemRelationlist.size() > 0) {
			for (int i = 0; i < itemRelationlist.size(); i++) {
				DeviceRuleItemRecordModel itemRecord = itemRelationlist.get(i).getDeviceRuleItemRecord();
				deviceRuleItemRecordService.save(itemRecord);
				DeviceRuleItemRelationModel itemRelation = itemRelationlist.get(i);
				itemRelation.setDeviceRule(copy_rule);
				itemRelation.setDeviceRuleItemRecord(itemRecord);
				deviceRuleItemRelationService.save(itemRelation);
			}
		}
		
		// 保存操作
		List<DeviceRuleOperationItemRelationModel> operationItemRelationlist = deviceRuleOperationItemRelationService
				.findAllByRuleId(rid);
		if (operationItemRelationlist != null && operationItemRelationlist.size() > 0) {
			for (int i = 0; i < operationItemRelationlist.size(); i++) {
				DeviceRuleOperationItemRecordModel operationItemRecord = operationItemRelationlist.get(i)
						.getDeviceRuleOperationRecord();
				deviceRuleOperationItemRecordService.save(operationItemRecord);
				DeviceRuleOperationItemRelationModel operationItemRelation = operationItemRelationlist.get(i);
				operationItemRelation.setDeviceRule(copy_rule);
				operationItemRelation.setDeviceRuleOperationRecord(operationItemRecord);
				deviceRuleOperationItemRelationService.save(operationItemRelation);
			}
		}
	}
	
	private List<Integer> removeNullFromList(List<Integer> idList){
		List<Integer> ids = new ArrayList<Integer>();
		if(!CollectionUtils.isEmpty(idList))
		{
			for (Integer id : idList)
			{
				if (null != id)
				{
					ids.add(id);
				}
			}
		}
		
		return ids;
	}

	@Override
	public void notifyToIos(Map<String, Object> map) {
		List<Integer> ids =(List<Integer>) map.get("userIds");
		Map<String, String> pushMap=new HashMap<>();
		pushMap.put("deviceRuleId", String.valueOf(map.get("deviceRuleId")));
		if(!CollectionUtils.isEmpty(ids)){
			List<String> deviceTokens=deviceBasicInfoDao.findDeviceTokenListByUserIds(ids);
			IosPushUtil.pushDataToIosTr(deviceTokens, null, 1, "", pushMap);
		}
	}
}
