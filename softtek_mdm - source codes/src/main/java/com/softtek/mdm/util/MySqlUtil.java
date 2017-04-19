package com.softtek.mdm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * mysql备份工具类
 * @author Jackson.zhu
 * @version 1.0
 *
 */
public class MySqlUtil {

	private static final Logger logger = Logger.getLogger(MySqlUtil.class);
	
	private String fileName;//备份的文件名称
	private String backupDir;//备份的目录
	private String database;//备份的数据库
	private String username;//备份的数据库用户名
	private String password;//备份的数据库密码
	
	public void setCfg(String fileName,String backupDir,String database,String username,String password) {
		this.fileName = fileName;
		this.backupDir = backupDir;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	private static MySqlUtil util = null;
	private MySqlUtil(){
		
	}
	public static MySqlUtil getInstance() {
		if(util==null) util = new MySqlUtil();
		return util;
	}
	
	public void backup() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String cmd = "cmd /c C:\\dump\\mysqldump -u"+username+" -p"+password+" "+database;
			Process proc = Runtime.getRuntime().exec(cmd);
			//使用转换流读取文件的时候最好要指定编码格式，防止乱码的出现
			br = new BufferedReader(new InputStreamReader(proc.getInputStream(),"UTF-8"));
			bw = new BufferedWriter(
					new FileWriter(backupDir+File.separator+fileName+".sql"));
			String str = null;
			while((str=br.readLine())!=null){
				bw.write(str);
				bw.newLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			try {
				if(bw!=null){
					bw.close();
				} 
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
	}
	
	
	
}
