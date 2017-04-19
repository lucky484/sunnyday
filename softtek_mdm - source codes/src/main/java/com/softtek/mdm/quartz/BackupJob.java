package com.softtek.mdm.quartz;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.softtek.mdm.util.MySqlUtil;
import com.softtek.mdm.util.PropertyUtil;

public class BackupJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		//获取备份数据库的全部信息，包括用户名 密码 数据库名称 备份的文件路径 备份的新文件名称
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		Properties prop = propertyUtil.load("spring/data-access");
		String username = prop.getProperty("jdbc_username");
		String password = prop.getProperty("jdbc_password");
		String url = prop.getProperty("jdbc_url");
		String database = StringUtils.split(url,"?")[0].substring(StringUtils.split(url,"?")[0].lastIndexOf("/")+1,StringUtils.split(url,"?")[0].length());
		
		/*String username = "root";
    	String password = "mdm_123";
    	String database="mdm_show";*/
		
		Long fileName = System.currentTimeMillis();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String backupDir = (String) dataMap.get("param");
		File dirPath = new File(backupDir);
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
		//1、第一步是备份数据库文件
		MySqlUtil util = MySqlUtil.getInstance();
		util.setCfg(String.valueOf(fileName), backupDir, database, username, password);
    	util.backup();
    	//2、第二步是拷贝文件，将数据库，静态文件，apk等拷贝到备份目录，等压缩完成之后删除，仅仅只保留压缩文件
		
    	
    	//第三步是将压缩文件上传到文件服务器的备份目录中去
    	//TarAndGzipUtil tagu = TarAndGzipUtil.getInstance();
		//tagu.tarFile("D:/upload/","D:/"+new Date().getTime()+".tar");
    	
	}

}
