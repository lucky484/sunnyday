package com.softtek.mdm.service.ios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.AppListDao;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceLegalRecordDao;
import com.softtek.mdm.dao.DeviceRuleItemRelationDao;
import com.softtek.mdm.dao.DeviceRuleOperationItemRelationDao;
import com.softtek.mdm.model.AppList;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.DeviceLegalRecordModel;
import com.softtek.mdm.model.DeviceRuleItemRecordModel;
import com.softtek.mdm.model.DeviceRuleItemRelationModel;
import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceRuleItemRelationService;
import com.softtek.mdm.service.DeviceRuleService;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;

/**
 * 处理MDM Service 回调
 * @author color.wu
 *
 */
@Service(value="IosDeviceRuleService")
public class IosDeviceRuleService extends AbsServerUrl {

	@Autowired
	private CommandDao commandDao;
	@Autowired
	@Qualifier("iosDeviceRuleNotifyService")
	private AbstractIosPush abstractIosPush;
	@Autowired
	private DeviceRuleService deiceRuleService;
	@Autowired
	private DeviceRuleItemRelationDao deviceRuleItemRelationDao;
	@Autowired
	private DeviceRuleOperationItemRelationDao deviceRuleOperationItemRelationDao;
	@Autowired
	private AppListDao appListDao;
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	@Autowired
	private DeviceLegalListService deviceLegalListService;
	@Autowired
	private DeviceLegalRecordDao deviceLegalRecordDao;
	@Autowired
	private DeviceRuleItemRelationService deviceRuleItemRelationService;
	
	/**
	 * 服务器空闲，可以发送指令
	 */
	@Override
	public String operateIdleStatus(Map<String, Object> map) {
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		//发送--未处理的并且类型是设备规则的指令
		//注意命令只能发送一条，剩余的命令应该在成功或者失败或者notnow的时候进行继续想apns发送查询
		Command cmd=getFirstCommand(udid, Constant.Result.NONE, Constant.ICommandType.DeviceRule);
		if(cmd!=null){
			//请求MDM Server获取安装的应用列表信息
			if(PlistUtil.RequestType.InstalledApplicationList.equals(cmd.getCommand())){
				//更改状态数据
				cmd.setDoit(Constant.Result.PENDING);
				cmd.setUpdateDate(new Date());
				cmd.setResult(Constant.getStatus.PENDING);
				int result=commandDao.updateByPrimaryKey(cmd);
				if(result>0){
					String plistStr=PlistUtil.getInstalledApplicationListPlist(cmd.getId());
					if(StringUtils.isEmpty(plistStr)){
						return BaseDTO.ERROR;
					}
					return plistStr;
				}
			}
			
		}
		return BaseDTO.SUCCESS;
	}

	/**
	 * 服务器已经完成对指令的操作和响应
	 */
	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		String uuid = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		//获取刚发送的指令消息
		Command cmd=commandDao.selectByPrimaryKey(uuid);
		cmd.setDoit(Constant.Result.ACKNOWLEDGED);
		cmd.setUpdateDate(new Date());
		cmd.setResult(Constant.getStatus.ACKNOWLEDGED);
		int result=commandDao.updateByPrimaryKey(cmd);
		
		Integer deviceRuleId=cmd.getCommandId();
		if(cmd!=null&&PlistUtil.RequestType.InstalledApplicationList.equals(cmd.getCommand())){
			List<DeviceAppInfoModel> appList=(List<DeviceAppInfoModel>) map.get("appInfoList");
			@SuppressWarnings("unchecked")
			List<String> appIds=(List<String>) CollectionUtils.collect(appList, new Transformer() {
				@Override
				public Object transform(Object input) {
					DeviceAppInfoModel appInfo=(DeviceAppInfoModel)input;
					return appInfo.getAppid();
				}
			});
			String ruleJudge=ruleJudge(deviceRuleId,appIds,udid);
			if("true".equals(ruleJudge)||"false".equals(ruleJudge)){
				//保存设备是否违规操作记录
				DeviceLegalListModel deviceLegalList =new DeviceLegalListModel();
				DeviceBasicInfoModel deviceBasicInfo=deviceBasicInfoDao.findByUdid(udid);
				deviceLegalList.setDeviceBasicInfo(deviceBasicInfo);
				DeviceRuleModel deviceRule=deiceRuleService.findOne(deviceRuleId);
				deviceLegalList.setDeviceRule(deviceRule);
				deviceLegalList.setViolate_time(new Date());
				if("true".equals(ruleJudge)){
					deviceLegalList.setStatus(1);
					List<DeviceRuleItemRelationModel>  records=deviceRuleItemRelationService.findAllByRuleId(deviceRuleId);
					if(CollectionUtils.isNotEmpty(records)){
						DeviceRuleItemRecordModel temp=null;
						for(int i=0;i<records.size();i++){
							temp=records.get(i).getDeviceRuleItemRecord();
							if(null!=temp&&temp.getType()==1){
								break;
							}
						}
						List<DeviceLegalRecordModel> list= new ArrayList<DeviceLegalRecordModel>();
						DeviceLegalRecordModel legalRecord=new DeviceLegalRecordModel();
						legalRecord.setDeviceBasicInfo(deviceBasicInfo);
						legalRecord.setDeviceRule(deviceRule);
						legalRecord.setDeviceRuleItemRecord(temp);
						legalRecord.setViolate_time(new Date());
						list.add(legalRecord);
						deviceLegalRecordDao.saveRecordsBatch(list);
					}
				}else{
					deviceLegalList.setStatus(0);
				}
				deviceLegalListService.save(deviceLegalList);
			}
		}
		
		//发送查询服务器是否空闲指令
		iosNotifyed(udid);
		return BaseDTO.SUCCESS;
	}
	
	/**
	 * 检测规则并执行相关操作
	 * @param deviceRuleId 设备规则编号
	 * @param appIds MDM服务器返回的appid集合
	 * @return
	 */
	private String ruleJudge(Integer deviceRuleId,List<String> appIds,String udid){
		if(deviceRuleId!=null){
			//获取设备规则内容
			List<DeviceRuleItemRelationModel> drirations=deviceRuleItemRelationDao.findAllByRuleId(deviceRuleId);
			if(CollectionUtils.isEmpty(drirations)){
				return BaseDTO.ERROR;
			}
			for (DeviceRuleItemRelationModel itemRla : drirations) {
				//设置了黑白名单，判定是黑名单还是白名单
				if (1==itemRla.getDeviceRuleItemRecord().getType()) {
					//黑名单或者白名单的内容
					String value=itemRla.getDeviceRuleItemRecord().getValue();
					List<AppList> list=appListDao.selectAppsByCatalogListName(StringUtils.trimToEmpty(value));
					@SuppressWarnings("unchecked")
					List<String> listAppIds=(List<String>) CollectionUtils.collect(list, new Transformer() {
						@Override
						public Object transform(Object input) {
							AppList app=(AppList)input;
							return app.getAppId();
						}
					});
					if(CollectionUtils.isNotEmpty(listAppIds)&&CollectionUtils.isNotEmpty(appIds)){
						int sourceSize=listAppIds.size();
						if(0==itemRla.getDeviceRuleItemRecord().getCondition()){
							if(!String.valueOf(sourceSize).equals(String.valueOf(appIds.size()))){
								//进行操作指令，执行违规操作
								ruleOperation(deviceRuleId,udid);
								return "true";
							}
						}else{
							listAppIds.removeAll(appIds);
							if(String.valueOf(listAppIds.size()).equals(String.valueOf(sourceSize))){
								//进行操作指令，执行违规操作
								ruleOperation(deviceRuleId,udid);
								return "true";
							}
						}
						//未违规
						return "false";
					}
				}
			}
		}
		return BaseDTO.ERROR;
	}
	
	/**
	 * 操作规则 执行操作
	 * @param deviceRuleId
	 * @param udid
	 * @return
	 */
	private String ruleOperation(Integer deviceRuleId,String udid){
		List<DeviceRuleOperationItemRelationModel> operationItemRla=deviceRuleOperationItemRelationDao.findAllByRuleId(deviceRuleId);
		if(CollectionUtils.isNotEmpty(operationItemRla)){
			for (DeviceRuleOperationItemRelationModel operation : operationItemRla) {
				if(1==operation.getDeviceRuleOperationRecord().getType()){
					Map<String, String> pushMap=new HashMap<>();
					pushMap.put("deviceRuleId", String.valueOf(deviceRuleId));
					DeviceBasicInfoModel basicInfo=deviceBasicInfoDao.findByUdid(udid);
					String deviceToken=basicInfo.getDeviceToken();
					String alert=operation.getDeviceRuleOperationRecord().getValue();
					List<String> deviceTokens=new ArrayList<>();
					deviceTokens.add(deviceToken);
					Map<String,String> messageMap=new HashMap<String,String>();
					messageMap.put("message", alert);
					messageMap.put("lastDate", DateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
					messageMap.put("system", "0");
					IosPushUtil.pushDataToIosTo(deviceTokens, alert, 1, "", messageMap);
				}
			}
		}
		
		return BaseDTO.SUCCESS;
	}

	@Override
	public void operateErrorStatus(Map<String, Object> map) {
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		int result=updateCmdStatus(udid,Constant.Result.ERROR);
	}
	
	
	/**
	 * 获取第一个命令
	 * @param udid udid
	 * @param doit 状态
	 * @param type 类型
	 * @return
	 */
	private Command getFirstCommand(String udid,String doit,String type){
		List<Command> cmdList = commandDao.selectCommands(udid, doit,type);
		if(CollectionUtils.isEmpty(cmdList)){
			return null;
		}
		return cmdList.get(0);
	}
	
	/**
	 * 通知APNS 是否空闲以便执行命令
	 * @param udid
	 * @param doit
	 * @param type
	 */
	private void iosNotifyed(String udid){
		List<String> udids=new ArrayList<>();
		udids.add(udid);
		List<TokenUpdateInfo> tokens=abstractIosPush.getTokenUpdateInfos(udids);
		abstractIosPush.iosNotify(tokens);
	}
	
	/**
	 * 更新命令的状态
	 * @param udid
	 * @param status
	 * @return
	 */
	private int updateCmdStatus(String udid,String status){
		Command command = commandDao.selectByPrimaryKey(udid);
		command.setDoit(status);
		command.setUpdateDate(new Date());
		return commandDao.updateByPrimaryKeySelective(command);
	}
}
