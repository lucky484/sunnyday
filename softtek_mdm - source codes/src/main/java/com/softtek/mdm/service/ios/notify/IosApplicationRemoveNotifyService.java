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
 * ios应用推送
 * @author jane.hui
 *
 */
@Service(value="iosApplicationRemoveNotifyService")
public class IosApplicationRemoveNotifyService extends AbstractIosPush {
	
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
	private CommandDao commandDao;
	
	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		//获取用户ids列表
		Object objUserId = map.get("userId");
        if(objUserId==null){
        	return null;
        }
        List<Integer> userList = new ArrayList<Integer>();
        userList.add(Integer.valueOf(objUserId.toString()));
		List<String> udids = deviceBasicInfoDao.findUdidsByUserIds(userList, "ios");
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
		Object objAppId = map.get("appId");
		Integer appId= Integer.valueOf(objAppId.toString());
		for(TokenUpdateInfo token : tokenList){
			List<Command> cmdList=new ArrayList<>();
			for (String cmd : cmds) {
				cmdList.add(generateCmd(cmd,token.getUdid(),appId));
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
	private Command generateCmd(String cmd,String udid,int extra){
		Command command = new Command();
		command.setId(CommUtil.generate32UUID());
		command.setCommand(cmd);
		command.setDeviceId(udid);
		command.setCommandId(extra);
		command.setType(Constant.ICommandType.RemoveApplicaiton);
		command.setDoit(Constant.Result.NONE);
		command.setCreateDate(new Date());
		command.setUpdateDate(new Date());
		command.setClassName(Constant.IIosClassName.IosApplicationRemoveServiceImpl);
		return command;
	}
 
}
