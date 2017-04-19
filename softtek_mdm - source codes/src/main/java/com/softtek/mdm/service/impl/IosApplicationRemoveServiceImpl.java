package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.AppDao;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.model.App;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.institution.DevicePolicyController;

import jodd.util.StringUtil;

/**
 * ios应用service实现类
 * @author jane.hui
 *
 */
@Service("IosApplicationRemoveServiceImpl")
public class IosApplicationRemoveServiceImpl extends AbsServerUrl {

	private static Logger logger = Logger.getLogger(DevicePolicyController.class);
	
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	@Qualifier("iosApplicationNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Autowired
	private AppDao appDao;
	
	/**
	 * 服务器空闲，可以发送指令
	 */
	@Override
	public String operateIdleStatus(Map<String, Object> map) {
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		//发送--未处理的并且类型是Application的指令
		//注意命令只能发送一条，剩余的命令应该在成功或者失败或者notnow的时候进行继续apns发送查询
		Command cmd= getFirstCommand(udid, Constant.Result.NONE, Constant.ICommandType.RemoveApplicaiton);
		if(cmd!=null){
			//请求MDM Server获取安装的应用列表信息
			if(PlistUtil.RequestType.InstallApplication.equals(cmd.getCommand())){
				App app = appDao.selectByPrimaryKey(cmd.getCommandId());
				//更改状态数据
				cmd.setDoit(Constant.Result.PENDING);
				cmd.setUpdateDate(new Date());
				cmd.setResult(Constant.getStatus.PENDING);
				int result = commandDao.updateByPrimaryKey(cmd);
				if(result>0){
					String plist = PlistUtil.getRemoveApplicationPlist(app.getTrackId(), CommUtil.generate32UUID());
					if(StringUtil.isEmpty(plist)){
						return BaseDTO.ERROR;
					}
					return plist;
				}
			}
		}
		return BaseDTO.SUCCESS;
	}

	/**
	 * 当设备处于Acknowleged状态时
	 */
	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		try{
			String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
			Command command = commandDao.selectByPrimaryKey(commandUUID);
			command.setId(commandUUID);
			command.setDoit(Constant.Result.ACKNOWLEDGED);
			command.setResult(Constant.getStatus.ACKNOWLEDGED);
			command.setUpdateDate(new Date());
			int result = commandDao.updateByPrimaryKeySelective(command);
			if(result==0){
				return BaseDTO.ERROR;
			}
/*			App app = appDao.selectByPrimaryKey(command.getCommandId());
			if(app!=null&&app.getAppCount()!=null&&app.getAppCount()!=0){
				Integer count = app.getAppCount() - 1;
				app.setAppCount(count);
				int size = appDao.updateByPrimaryKeySelective(app);
				if(size==0) {
					return BaseDTO.ERROR;
				}
			}*/
			//request MDM Server whether is idle or not
			String udid = (String) map.get(Constant.MobileConfig.UDID);
			iosNotifyed(udid);
			return BaseDTO.SUCCESS;
		} catch(Exception e){
			logger.error("ios device policy operate Acknowledged status is error,error message is " + e.getMessage());
			return BaseDTO.ERROR;
		}
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
}