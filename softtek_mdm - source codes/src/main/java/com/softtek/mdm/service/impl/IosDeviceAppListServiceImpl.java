package com.softtek.mdm.service.impl;

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
import com.softtek.mdm.service.IosDeviceAppListService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;


@Service("IosDeviceAppListServiceImpl")
public class IosDeviceAppListServiceImpl extends AbstractIosPush implements IosDeviceAppListService{
	
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;

	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		
		String udid = (String) map.get("udid");
		List<TokenUpdateInfo> list = tokenUpdateInfoDao.selectTokenInfoByUdid(udid);
		Command command = new Command();
		command.setId(CommUtil.generate32UUID());
		command.setType(Constant.ICommandType.AppList);
		command.setCommand(PlistUtil.RequestType.InstalledApplicationList);
		command.setDeviceId(udid);
		command.setDoit(Constant.Result.NONE);
		command.setUpdateDate(new Date());
		command.setClassName(Constant.IIosClassName.IosDeviceAppListSaveServiceImpl);
		commandDao.insertSelective(command);			
		return list;
	}

}
