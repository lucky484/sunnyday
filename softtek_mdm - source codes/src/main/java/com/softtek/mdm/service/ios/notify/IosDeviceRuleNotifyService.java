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
 * @author color.wu
 *
 */
@Service(value="iosDeviceRuleNotifyService")
public class IosDeviceRuleNotifyService extends AbstractIosPush {

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
		@SuppressWarnings("unchecked")
		List<Integer> userIds = (List<Integer>) map.get("userIds");
		//根据userIds获取udid列表
		if(CollectionUtils.isEmpty(userIds)){
			return null;
		}
		List<String> udids=deviceBasicInfoDao.findUdidsByUserIds(userIds, "ios");
		if(CollectionUtils.isEmpty(udids)){
			return null;
		}
		List<TokenUpdateInfo> tokenList = tokenUpdateInfoDao.queryTokenInfoByUdidList(udids);
		if(CollectionUtils.isEmpty(tokenList)){
			return null;
		}
		@SuppressWarnings("unchecked")
		List<String> cmds=(List<String>) map.get("cmds");
		if(CollectionUtils.isEmpty(cmds)){
			return null;
		}
		Integer deviceRuleId=(Integer) map.get("deviceRuleId");
		for(TokenUpdateInfo token : tokenList){
			List<Command> cmdList=new ArrayList<Command>();
			for (String cmd : cmds) {
				Command c=generateCmd(token.getUdid(),deviceRuleId);
				c.setCommand(cmd);
				cmdList.add(c);
			}
			if(!CollectionUtils.isEmpty(cmdList)){
				commandDao.insertBatchCommand(cmdList);
			}
		}
		
		return tokenList;
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
		command.setType(Constant.ICommandType.DeviceRule);
		command.setDoit(Constant.Result.NONE);
		command.setCreateDate(new Date());
		command.setUpdateDate(new Date());
		command.setClassName(Constant.IIosClassName.IosDeviceRuleService);
		return command;
	}

	@Override
	public void afterNotify(Map<String, Object> map) {
		super.afterNotify(map);
	}
	
	

}
