package com.softtek.mdm.service.ios.notify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;

@Service(value="iosPackageAppUpdateNotifyService")
public class IosPackageAppUpdateNotifyService extends AbstractIosPush{

	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	@Autowired
	private CommandDao commandDao;
	
	@Override
	public List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map) {
		//通知所有版本呢都低于当前版本的iOS手机，去询问服务器是否需要更新
		String orgStr=(String) map.get("belongOrgs");
		String version=(String)map.get("version");
		String downloadUrl=(String) map.get("downloadUrl");
		
		if(StringUtils.trimToNull(orgStr)==null){
			//对所有的机构中的iOS中版本低于当前的版本的进行MDM推送
		}
		if(orgStr.endsWith(",")){
			orgStr=orgStr.substring(0, orgStr.length()-1);
		}
		String[] orgArr=orgStr.split(",");
		List<String> orgIds=new ArrayList<>(orgArr.length);
		Arrays.asList(orgIds);
		
		map.clear();
		map.put("orgIds", orgIds);
		map.put("platform","ios");
		map.put("version",version);
		
		List<DeviceBasicInfoModel> list=deviceBasicInfoDao.findNeedUpdate(map);
		if(list!=null){
			@SuppressWarnings("unchecked")
			List<String> udids=(List<String>) CollectionUtils.collect(list, new Transformer() {
				@Override
				public Object transform(Object input) {
					DeviceBasicInfoModel device=(DeviceBasicInfoModel) input;
					return device.getUdid();
				}
			});
			List<TokenUpdateInfo> tokenList = tokenUpdateInfoDao.queryTokenInfoByUdidList(udids);
			if(CollectionUtils.isEmpty(tokenList)){
				return null;
			}
			List<Command> cmdList=new ArrayList<Command>();
			for(int i=0;i<tokenList.size();i++){
				TokenUpdateInfo token=tokenList.get(i);
				Command cmd=generateCmd(token.getUdid(),0);
				//将manifest文件的下载地址放入其中，或者放入部分
				cmd.setResult(downloadUrl);
				cmdList.add(cmd);
				if(i>0&&i%10==0){
					commandDao.insertBatchCommand(cmdList);
					cmdList.clear();
				}
			}
			return tokenList;
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
		command.setType(Constant.ICommandType.AppUpdate);
		command.setDoit(Constant.Result.NONE);
		command.setCreateDate(new Date());
		command.setUpdateDate(new Date());
		command.setClassName(Constant.IIosClassName.IosPackageAppUpdateService);
		command.setCommand(PlistUtil.RequestType.ManagedApplicationList);
		return command;
	}


}
