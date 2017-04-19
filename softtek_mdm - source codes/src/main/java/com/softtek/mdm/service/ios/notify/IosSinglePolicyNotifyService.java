package com.softtek.mdm.service.ios.notify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;

/**
 * 处理在发送设备规则相关指令给iOS设备之前，对MDM Server的状态进行询问，是否是空闲，以便后面发送指令
 * @author jane.hui
 *
 */
@Service(value="iosSinglePolicyNotifyService")
public class IosSinglePolicyNotifyService extends AbstractIosPush {

	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
	private CommandDao commandDao;
	
	/**
	 * 在正式发送询问通知之前，获取相关的TokenUpdateInfo
	 */
	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		//获取用户ids列表
		Object userId =  map.get("userId");
		Object policyId = map.get("policyId");
		if(userId != null) {
			List<Integer> userIds = new ArrayList<Integer>();
			List<String> udids=deviceBasicInfoDao.findUdidsByUserIds(userIds, "ios");
			if(CollectionUtils.isEmpty(udids)){
				return null;
			}
			List<TokenUpdateInfo> tokenList = tokenUpdateInfoDao.queryTokenInfoByUdidList(udids);
			if(CollectionUtils.isEmpty(tokenList)){
				return null;
			}
		    if(policyId != null) {
		    	Integer intPolicyId = Integer.valueOf(policyId.toString());
		    	List<Command> cmdList=new ArrayList<Command>();
				for(TokenUpdateInfo token : tokenList){
					cmdList.add(generateCmd(token.getUdid(),intPolicyId));
				}
				// 基本udid在一条两条左右
				if(!CollectionUtils.isEmpty(cmdList)){
					commandDao.insertBatchCommand(cmdList);
				}
		    }
		}
		return null;
	}

	/**
	 * 创建Command对象
	 * @param cmd
	 * @param udid
	 * @param 额外数据
	 * @return
	 */
	private Command generateCmd(String udid,int extra){
		Command command = new Command();
		command.setId(CommUtil.generate32UUID());
		command.setDeviceId(udid);
		command.setCommandId(extra);
		command.setType(Constant.ICommandType.DevicePolicy);
		command.setDoit(Constant.Result.NONE);
		command.setCreateDate(new Date());
		command.setUpdateDate(new Date());
		command.setClassName(Constant.IIosClassName.IosDeviceServiceImpl);
		return command;
	}
}
