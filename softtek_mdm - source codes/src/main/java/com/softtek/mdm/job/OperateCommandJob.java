package com.softtek.mdm.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.model.TokenUpdateInfo;

/**
 * 处理指令列表任务
 * @author jane.hui
 *
 */
@Service("operateCommandJob")
public class OperateCommandJob {

	@Autowired
	@Qualifier("iosDeviceServiceNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Autowired
	private CommandDao commandDao;
	
	public void job(){
		List<String> udidList = commandDao.selectCommandList();
		List<TokenUpdateInfo> tokens=abstractIosPush.getTokenUpdateInfos(udidList);
		abstractIosPush.iosNotify(tokens);
	}
}
