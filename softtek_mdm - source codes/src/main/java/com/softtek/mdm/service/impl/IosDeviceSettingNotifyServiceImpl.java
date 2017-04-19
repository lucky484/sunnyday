package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.service.IosDeviceSettingNotifyService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;

@Service("IosDeviceSettingNotifyServiceImpl")
public class IosDeviceSettingNotifyServiceImpl extends AbstractIosPush implements IosDeviceSettingNotifyService{
    
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
	private CommandDao commandDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		List<String> udidList = (List<String>) map.get("udidList");
		String c = (String) map.get("command");
		List<TokenUpdateInfo> tokenList = new ArrayList<TokenUpdateInfo>();
		tokenList = tokenUpdateInfoDao.queryTokenInfoByUdidList(udidList);
		Command command = null;
		for(TokenUpdateInfo tokenUpdateInfo : tokenList){
			command = new Command();
			command.setId(CommUtil.generate32UUID());
			command.setCommand(c);
			command.setDeviceId(tokenUpdateInfo.getUdid());
			command.setType(Constant.ICommandType.DeviceManage);
			command.setDoit(Constant.Result.NONE);
			command.setCreateDate(new Date());
			command.setUpdateDate(new Date());
			command.setClassName(Constant.IIosClassName.IosDeviceSettingServiceImpl);
			commandDao.insertSelective(command);			

		}
		return tokenList;
	}

}
