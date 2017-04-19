package com.softtek.mdm.service.ios;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;
/**
 * iPA包更新使用
 * 1.不强制更新，客户端通过RPC调用获取.manifest文件
 * 2.强制更新，通过MDM Server强制推送.manifest文件
 * @author color.wu
 *
 */
@Service("iosPackageAppUpdateService")
public class IosPackageAppUpdateService extends AbsServerUrl{

	@Autowired
	private CommandDao commandDao;
	@Autowired
	@Qualifier("iosPackageAppUpdateNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Override
	public String operateIdleStatus(Map<String, Object> map) {
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		//发送--未处理的并且类型是设备规则的指令
		//注意命令只能发送一条，剩余的命令应该在成功或者失败或者notnow的时候进行继续想apns发送查询
		Command cmd=getFirstCommand(udid, Constant.Result.NONE, Constant.ICommandType.AppUpdate);
		if(cmd!=null){
			String manifestURL=cmd.getResult();
			//请求MDM Server获取安装的应用列表信息
			if(PlistUtil.RequestType.ManagedApplicationList.equals(cmd.getCommand())){
				//更改状态数据
				cmd.setDoit(Constant.Result.PENDING);
				cmd.setUpdateDate(new Date());
				cmd.setResult(Constant.getStatus.PENDING);
				int result=commandDao.updateByPrimaryKey(cmd);
				if(result>0){
					String plistStr=PlistUtil.getManagedApplicationListPlist(cmd.getId(),manifestURL);
					if(StringUtils.isEmpty(plistStr)){
						return BaseDTO.ERROR;
					}
					return plistStr;
				}
			}
		}
		return null;
	}

	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		String uuid = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		//获取刚发送的指令消息
		Command cmd=commandDao.selectByPrimaryKey(uuid);
		cmd.setDoit(Constant.Result.ACKNOWLEDGED);
		cmd.setUpdateDate(new Date());
		cmd.setResult(Constant.getStatus.ACKNOWLEDGED);
		int result=commandDao.updateByPrimaryKey(cmd);
		if(result>0){
			return BaseDTO.SUCCESS;
		}
		return BaseDTO.ERROR;
	}
	
	
	
	@Override
	public void operateNotNowStatus(Map<String, Object> map) {
		String uuid = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		//获取刚发送的指令消息
		Command cmd=commandDao.selectByPrimaryKey(uuid);
		cmd.setDoit(Constant.Result.ACKNOWLEDGED);
		cmd.setUpdateDate(new Date());
		cmd.setResult(Constant.getStatus.NOTNOW);
		
		commandDao.updateByPrimaryKey(cmd);
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
}
