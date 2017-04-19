package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.service.IosDeviceInfomationService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;

@Service("iosDeviceInfomationService")
public class IosDeviceInfomationServiceImpl extends AbstractIosPush implements IosDeviceInfomationService{
     
	@Autowired
	private CommandDao commandDao;
	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		TokenUpdateInfo tokenUpdateInfo = (TokenUpdateInfo) map.get("tokenUpdateInfo");
		Command command =generateCmd(PlistUtil.RequestType.DeviceInformation,tokenUpdateInfo.getUdid(),null);
		commandDao.insertSelective(command);			
		List<TokenUpdateInfo> list = new ArrayList<TokenUpdateInfo>();
		list.add(tokenUpdateInfo);
		return list;
	}

	
	/**
	 * 创建Command对象
	 * @param cmd
	 * @param udid
	 * @param 额外数据
	 * @return
	 */
	private Command generateCmd(String cmd,String udid,Integer extra){
		Command command = new Command();
		command.setId(CommUtil.generate32UUID());
		command.setCommand(cmd);
		command.setDeviceId(udid);
		command.setCommandId(extra);
		command.setType(Constant.ICommandType.DeviceManage);
		command.setDoit(Constant.Result.NONE);
		command.setCreateDate(new Date());
		command.setUpdateDate(new Date());
		command.setClassName(Constant.IIosClassName.IosDeviceInfoPlistServiceImpl);
		return command;
	}
}
