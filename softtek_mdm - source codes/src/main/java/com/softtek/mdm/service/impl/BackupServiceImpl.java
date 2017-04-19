package com.softtek.mdm.service.impl;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softtek.mdm.quartz.BackupJob;
import com.softtek.mdm.quartz.QuartzManager;
import com.softtek.mdm.service.BackupService;
import com.softtek.mdm.util.MySqlUtil;
import com.softtek.mdm.util.PropertyUtil;

@Service
public class BackupServiceImpl implements BackupService {

	private static final Logger logger = Logger.getLogger(BackupServiceImpl.class);
	
	@Override
	public boolean backup(String autoBackup, String backupType, String backUp, String backupTime, String backupPath,
			String cronExpression,String instant) {
		boolean flag = false;
		//当前台传过来的自动备份选项的时候才执行这个定时任务
		if(StringUtils.equals(autoBackup,"1")){
			try {
				//1、首先移除这个任务类
				QuartzManager.removeJob("backupJob");
				//2、首先调用定时任务框架中的quartzManager创建一个定时任务
				BackupJob job = new BackupJob();
				QuartzManager.addJob("backupJob",job,cronExpression,backupPath); //根据前台传过来的时间定时任务
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		//表明从前台传递过来的是立即备份的指令
		if(StringUtils.equals(instant,"1")){
			//获取备份数据库的全部信息，包括用户名 密码 数据库名称 备份的文件路径 备份的新文件名称
			PropertyUtil propertyUtil = PropertyUtil.getInstance();
			Properties prop = propertyUtil.load("spring/data-access");
			String username = prop.getProperty("jdbc_username");
			String password = prop.getProperty("jdbc_password");
			String url = prop.getProperty("jdbc_url");
			String database = StringUtils.split(url,"?")[0].substring(StringUtils.split(url,"?")[0].lastIndexOf("/")+1,StringUtils.split(url,"?")[0].length());
			
			Long fileName = System.currentTimeMillis();
			File dirPath = new File(backupPath);
			if(!dirPath.exists()){
				dirPath.mkdirs();
			}
			//1、第一步是备份数据库文件
			MySqlUtil util = MySqlUtil.getInstance();
			util.setCfg(String.valueOf(fileName), backupPath, database, username, password);
	    	util.backup();
	    	//2、第二步是拷贝文件，将数据库，静态文件，apk等拷贝到备份目录，等压缩完成之后删除，仅仅只保留压缩文件
			
	    	
	    	//第三步是将压缩文件上传到文件服务器的备份目录中去
	    	//TarAndGzipUtil tagu = TarAndGzipUtil.getInstance();
			//tagu.tarFile("D:/upload/","D:/"+new Date().getTime()+".tar");
	    	
			
		}
		
		return flag;
	}

}
