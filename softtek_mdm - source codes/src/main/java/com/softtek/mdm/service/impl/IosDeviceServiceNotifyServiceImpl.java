package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.service.IosDeviceServiceNotifyService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;

/**
 * 处理Ios设备策略通知Apns服务器
 * @author jane.hui
 *
 */
@Service("iosDeviceServiceNotifyService")
public class IosDeviceServiceNotifyServiceImpl extends AbstractIosPush implements IosDeviceServiceNotifyService {

	private static Logger logger = Logger.getLogger(IosDeviceServiceNotifyServiceImpl.class);
	
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
	private CommandDao commandDao;
	
	/**
	 * 推送指令
	 */
	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		try {
			Object objUserSet = map.get("userSet");
			Object objIosDevicePolicy = map.get("iosDevicePolicy");
			Object objJpushList = map.get("jpushList");
			// 将要删除的命令list
			List<Command> commandList = new ArrayList<Command>();
			// command 命令对象
			Command command = null;
			// 获取临时的TokenUpdateInfo
			List<TokenUpdateInfo> tempTokenList = null;
			// 获取所有的TokenUpdateInfo
			List<TokenUpdateInfo> tokenList = new ArrayList<TokenUpdateInfo>();
			// user id list
			List<Integer> userIdList = null;
			if(objJpushList!=null&&!Constant.EMPTY_STR.equals(objJpushList)){
				@SuppressWarnings("unchecked")
				List<IosDevicePolicy> list = (List<IosDevicePolicy>)objJpushList;
				for (IosDevicePolicy policy : list) {
					userIdList = new ArrayList<Integer>();
					String udid = Constant.EMPTY_STR;
					userIdList = policy.getUserIdList();
						if(userIdList.size()>0){
							tempTokenList = tokenUpdateInfoDao.selectTokenInfoByUserList(userIdList);
							for (TokenUpdateInfo tokenUpdateInfo : tempTokenList) {
								udid = tokenUpdateInfo.getUdid();
								command = new Command();
								command.setId(CommUtil.generate32UUID());
								command.setDeviceId(udid);
								command.setCommandId(policy.getId());
								command.setType(Constant.ICommandType.DevicePolicy);
								command.setDoit(Constant.Result.NONE);
								command.setCreateDate(new Date());
								command.setUpdateDate(new Date());
								command.setClassName(Constant.IIosClassName.IosDeviceServiceImpl);
								commandList.add(command);	
							}
							tokenList.addAll(tempTokenList);
					}
				}
			}
			userIdList = new ArrayList<Integer>();
			if(objUserSet!=null&&objIosDevicePolicy!=null){
				@SuppressWarnings("unchecked")
				Set<Integer> userSet = (Set<Integer>)objUserSet;
				userIdList.addAll(userSet);
				IosDevicePolicy iosDevicePolicy = (IosDevicePolicy)objIosDevicePolicy;
				if(userIdList.size()>0){
					tempTokenList = tokenUpdateInfoDao.selectTokenInfoByUserList(userIdList);
					for (TokenUpdateInfo tokenUpdateInfo : tempTokenList) {
						command = new Command();
						command.setId(CommUtil.generate32UUID());
						command.setDeviceId(tokenUpdateInfo.getUdid());
						command.setCommandId(iosDevicePolicy.getId());
						command.setType(Constant.ICommandType.DevicePolicy);
						command.setDoit(Constant.Result.NONE);
						command.setCreateDate(new Date());
						command.setUpdateDate(new Date());
						command.setClassName(Constant.IIosClassName.IosDeviceServiceImpl);
						commandList.add(command);	
					}
					tokenList.addAll(tempTokenList);
				}	
		    }
			// 批量插入命令
			if(commandList.size()>0){
				commandDao.insertBatchCommand(commandList);
			}
		    return tokenList;
		} catch(Exception e){
			logger.error("ios device policy get tokenUpdateInfo is error,error message is "+e.getMessage());
			return null;
		}

	}
}