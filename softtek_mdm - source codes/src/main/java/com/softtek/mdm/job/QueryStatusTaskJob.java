package com.softtek.mdm.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.ParamSettingService;
import com.softtek.mdm.service.SysmanageDeviceLogService;

@Service("queryStatusTaskJob")
public class QueryStatusTaskJob {
     
	  @Autowired
	  private DeviceManagerService deviceManagerService;
	  
      @Autowired
	  private SysmanageDeviceLogService sysmanageDeviceLogService;
      
      @Autowired
      private MessageSource messageSource;
      
      @Autowired
      private ParamSettingService paramSettingService;
      
      private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	  public void job(){
        // 查询出要生成设备日志的设备信息
		  List<DeviceManagerModel> list = deviceManagerService.queryIsTakeOff();  
		  SystemParamSetModel systemParamSet = paramSettingService.querySysParamSetting();
		  SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
		  String userType = "";
		  
		  if(CollectionUtils.isEmpty(list)){
			  return ;
		  }
		  for(DeviceManagerModel deviceManager : list){
			  if(deviceManager.getTime() > systemParamSet.getOutManagerTime()){
                String eventType = sysmanageDeviceLogService.queryLogIsExite(deviceManager.getId());
                if (!"12".equals(eventType)) {
                    // 如果上次已经插入过这条设备的脱管日志 ，则不在插入
					  if(deviceManager.getUserType() == 4){
                        // 管理员
						 userType = messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					  }else{
                        // 普通用户
						userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					  }
                    // 将采集时间加上24小时作为脱管时间
					  Long date = deviceManager.getLast_collection_time().getTime() + 86400000;
					  Object[] args = {userType,deviceManager.getRealName(),sdf.format(date),deviceManager.getDevice_name()};
                    String operateContent = messageSource.getMessage("log.device.take.off.type", args,
                            LocaleContextHolder.getLocale());// 从国际化资源文件中读取操作内容
                    deviceLog.setEventType("12"); // 时间类型 12代表监控脱管
					  deviceLog.setDeviceId(deviceManager.getId());
					  deviceLog.setDeviceName(deviceManager.getDevice_name());
					  deviceLog.setUserId(deviceManager.getUserId());
					  deviceLog.setCreateUser(deviceManager.getUserId());
					  deviceLog.setUpdateUser(deviceManager.getUserId());
					  deviceLog.setOperateContent(operateContent);
					  deviceLog.setOrgId(deviceManager.getOrgId());
					  deviceLog.setOperateDate(new Date());
					  deviceLog.setCreateDate(new Date());
					  deviceLog.setUpdateDate(new Date());
                    sysmanageDeviceLogService.insertSelective(deviceLog); // 插入设备日志
				  }
			  }
		  }
	  }
}
