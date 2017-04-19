package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceAppInfoDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceLegalRecordDao;
import com.softtek.mdm.dao.ParamSettingDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.AndroidDeviceUsers;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.DeviceLegalRecordModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.queue.MqttQueue;
import com.softtek.mdm.service.AndroidDevicePolicyService;
import com.softtek.mdm.service.AndroidDeviceUsersService;
import com.softtek.mdm.service.DeviceAppInfoService;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceNetworkStatusService;
import com.softtek.mdm.service.DeviceRuleService;
import com.softtek.mdm.service.DeviceSaftyService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.status.DeviceRuleStatus;
import com.softtek.mdm.status.DeviceRuleValueStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.thread.MqttPushThread;
import com.softtek.mdm.util.IosPushUtil;

import net.sf.json.JSONObject;

@Service
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	@Autowired
	private DeviceLegalListService deviceLegalListService;
	@Autowired
	private DeviceSaftyService deviceSaftyService;
	@Autowired
	private DeviceRuleService deviceRuleService;
	@Autowired
	private AndroidDeviceUsersService androidDeviceUsersService;
	@Autowired
	private AndroidDevicePolicyService androidDevicePolicyService;
	@Autowired
	private DeviceLegalRecordDao deviceLegalRecordDao;
	@Autowired
	private DeviceNetworkStatusService deviceNetworkStatusService;
	@Autowired
	private DeviceAppInfoService deviceAppInfoService;
	@Autowired
	private DeviceAppInfoDao deviceAppInfoDao;
	@Autowired
	private ParamSettingDao paramSettingDao;
	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	public int updateInfo(Map<String, Object> map) {
		UserModel user = (UserModel) map.get("user");
		int flag = userDao.update(user);
		DeviceBasicInfoModel temp = (DeviceBasicInfoModel) map.get("deviceBasicInfo");
		deviceBasicInfoDao.update(temp);
		return flag;
	}

	@Override
	public int report(Map<String, Object> map) {
		DeviceBasicInfoModel temp = (DeviceBasicInfoModel) map.get("deviceBasicInfo");
		if (temp != null) {
			
			deviceBasicInfoDao.update(temp);
			temp=deviceBasicInfoDao.findOne(temp.getId());
		}
		DeviceLegalListModel deviceLegalList = (DeviceLegalListModel) map.get("deviceLegalList");
		if (deviceLegalList != null) {
			// 将当前的设备规则更新
			DeviceRuleModel deviceRule = deviceRuleService.findOne(deviceLegalList.getDeviceRule().getId());
			SystemParamSetModel param = paramSettingDao.querySysParamSetting();
			DateTime date = DateTime.now();
			deviceRule.setUpdateTime(date.toDate());
			deviceRule.setNext_check_time(date.plusMinutes(param.getIllegalInfoCollectTime()).toDate());
			deviceRuleService.update(deviceRule);

			// 保存list之前将之前的同一个deviceruleid同一个设备的信息删除
			deviceLegalListService.deleteRecordsByRuleIdAndDeviceId(deviceLegalList.getDeviceRule().getId(),
					deviceLegalList.getDeviceBasicInfo().getId());
			// 保存新的违规或者合规记录
			deviceLegalListService.save(deviceLegalList);
			// 更新用户设备安全是否违规
			if (deviceLegalList.getStatus() > 0) {
				
				
				DeviceSaftyModel deviceSafty = deviceSaftyService
						.findOneWithDeviceId(deviceLegalList.getDeviceBasicInfo().getId());
				if (deviceSafty != null) {
					DeviceSaftyModel tem = new DeviceSaftyModel();
					tem.setId(deviceSafty.getId());
					tem.setViolate(1);
					deviceSaftyService.update(tem);
				}
			}
		}

		@SuppressWarnings("unchecked")
		List<DeviceRuleOperationItemRelationModel> operationList = (List<DeviceRuleOperationItemRelationModel>) map
				.get("operationList");
		@SuppressWarnings("unchecked")
		List<DeviceLegalRecordModel> deviceLegalRecordList = (List<DeviceLegalRecordModel>) map
				.get("deviceLegalRecordList");
		if (CollectionUtils.isNotEmpty(operationList)&&deviceLegalList.getStatus() > 0) {
			for (DeviceRuleOperationItemRelationModel operation : operationList) {
				String alert=operation.getDeviceRuleOperationRecord().getValue();
				//iOS设备推送暂时不使用，iOS使用了receiveData
				/*if("ios".equals(temp.getDevice_type())){
					if (operation.getDeviceRuleOperationRecord().getType().equals(DeviceRuleStatus.INFORM.getDisplayValue())){
						List<String> deviceTokens=new ArrayList<String>();
						deviceTokens.add(temp.getDeviceToken());
						Map<String,String> messageMap=new HashMap<String,String>();
						messageMap.put("message", alert);
						messageMap.put("lastDate", DateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
						messageMap.put("system", "0");
						IosPushUtil.pushDataToIosTo(deviceTokens, alert, 1, "", messageMap);
					}
					continue;
				}*/
				
				//下面对android设备进行处理
				
				if (operation.getDeviceRuleOperationRecord().getType()
						.equals(DeviceRuleStatus.INFORM.getDisplayValue())) {
					//违规通知
					
					JSONObject object=new JSONObject();
					String userIdStr=String.valueOf(temp.getUser().getId());
					object.put("userId", userIdStr);
					object.put("deviceId", String.valueOf(temp.getId()));
					object.put("message_title", alert);
					object.put("message", alert);
					object.put("identity", String.valueOf(1));
					object.put("createDateStr", DateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
					
					Map<String, String> data=new HashMap<String,String>();
					data.put("deviceRuleMessage", object.toString());
					
					List<Integer> ids=new ArrayList<Integer>();
					ids.add(Integer.valueOf(userIdStr));
					List<KeyValue> kvs=MqttQueue.generateDatas(ids, data);
					MqttPushThread mqttPush=new MqttPushThread(kvs);
					taskExecutor.execute(mqttPush);
				}
				
				
				if (operation.getDeviceRuleOperationRecord().getType()
						.equals(DeviceRuleStatus.STRATEGY_CHANGE.getDisplayValue())) {
					// 变更策略
					// 获取规则下的用户id列表
					List<Integer> ids = deviceRuleService
							.findUserIdsByPk(deviceLegalRecordList.get(0).getDeviceRule().getId());
					if (CollectionUtils.isNotEmpty(ids)) {
						// 用户设备策略安卓/iOS
						if (operation.getDeviceRuleOperationRecord().getCondition()
								.equals(DeviceRuleValueStatus.OS_ANDROID.getDisplayValue())) {
							for (int i = 0; i < ids.size(); i++) {
								AndroidDeviceUsers auser = androidDeviceUsersService.findOneByUserIdLast(ids.get(i));
								Map<String, Object> maps = new HashMap<String, Object>();
								DeviceRuleModel deviceRule = deviceRuleService
										.findOne(deviceLegalRecordList.get(0).getDeviceRule().getId());
								
								maps.put("orgId", deviceRule.getOrganization().getId());
								maps.put("name", operation.getDeviceRuleOperationRecord().getValue());
								
								AndroidDevicePolicy androidDevicePolicy = androidDevicePolicyService
										.findPolicyByMap(maps);
								if (androidDevicePolicy != null) {
									if (auser != null) {
										auser.setAndroidDevicePolicyId(androidDevicePolicy.getId());
										androidDeviceUsersService.updateByPrimaryKeySelective(auser);
									} else {
										auser = new AndroidDeviceUsers();
										auser.setAndroidDevicePolicyId(androidDevicePolicy.getId());
										auser.setUsersId(ids.get(i));
										androidDeviceUsersService.insert(auser);
									}
								}
							}
							// 更新用户安卓设备策略

						}
					}
				}
			}
		}
		if(null!=deviceLegalRecordList){
			deviceLegalRecordDao.saveRecordsBatch(deviceLegalRecordList);
		}
		return 1;
	}

	@Override
	public int baseInfo(Map<String, Object> map) {
		DeviceBasicInfoModel basic = (DeviceBasicInfoModel) map.get("basic");
		DeviceNetworkStatusModel deviceNetworkStatus = (DeviceNetworkStatusModel) map.get("deviceNetworkStatus");
		DeviceSaftyModel deviceSafty = (DeviceSaftyModel) map.get("deviceSafty");
		@SuppressWarnings("unchecked")
		List<DeviceAppInfoModel> deviceAppInfoList = (List<DeviceAppInfoModel>) map.get("deviceAppInfoList");
		// 新增基本信息
		int result = deviceBasicInfoDao.save(basic);
		if (result == 1) {
			// 保存网络信息
			if (deviceNetworkStatus != null) {
				deviceNetworkStatus.setDevice_id(basic.getId());
				deviceNetworkStatusService.save(deviceNetworkStatus);
			}

			if (deviceAppInfoList != null && deviceAppInfoList.size() > 0) {
				deviceAppInfoDao.saveRecordsBatch(deviceAppInfoList);
			}

			if (deviceSafty != null) {
				deviceSafty.setDevice_id(basic.getId());
				deviceSaftyService.save(deviceSafty);
			}

		}

		return result;
	}

	public int update(Map<String, Object> map) {
		// DeviceBasicInfoModel deviceBasicInfo =(DeviceBasicInfoModel)
		// map.get("deviceBasicInfo");
		DeviceBasicInfoModel basic = (DeviceBasicInfoModel) map.get("basic");
		DeviceNetworkStatusModel deviceNetworkStatus = (DeviceNetworkStatusModel) map.get("deviceNetworkStatus");
		DeviceSaftyModel deviceSafty = (DeviceSaftyModel) map.get("deviceSafty");
		@SuppressWarnings("unchecked")
		List<DeviceAppInfoModel> deviceAppInfoList = (List<DeviceAppInfoModel>) map.get("deviceAppInfoList");
		int result = deviceBasicInfoDao.update(basic);
		if (result == 1) {

			// 已存在，删除网络信息
			deviceNetworkStatusService.truncateWithDeviceId(basic.getId());

			// 已存在，删除应用信息
			deviceAppInfoService.truncateWithDeviceId(basic.getId());

			// 删除安全信息
			deviceSaftyService.truncateWithDeviceId(basic.getId());

			// 保存网络信息
			if (deviceNetworkStatus != null) {
				deviceNetworkStatus.setDevice_id(basic.getId());
				deviceNetworkStatusService.save(deviceNetworkStatus);
			}

			if (deviceAppInfoList != null && deviceAppInfoList.size() > 0) {
				deviceAppInfoDao.saveRecordsBatch(deviceAppInfoList);
			}

			if (deviceSafty != null) {
				deviceSafty.setDevice_id(basic.getId());
				deviceSaftyService.save(deviceSafty);
			}
		}
		return result;
	}
}
