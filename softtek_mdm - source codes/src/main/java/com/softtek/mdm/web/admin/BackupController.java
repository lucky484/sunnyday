package com.softtek.mdm.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.service.BackupService;


@Controller
@RequestMapping("/admin/backup")
public class BackupController {
	
	@Autowired
	private BackupService backupService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Link(family="admin",label="web.admin.backup.label",belong="web.admin.backup.belong")
	@RequestMapping(value="/manage",method=RequestMethod.GET)
	public String backupList(HttpServletRequest request,HttpServletResponse response){
		return "admin/backup/manage/index";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Link(family="admin",label="web.admin.backup.label",belong="web.admin.backup.belong")
	@RequestMapping(value="/setting",method=RequestMethod.GET)
	public String backupSetting(HttpServletRequest request,HttpServletResponse response,Model model){
		Integer flag=System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1?0:1;
		model.addAttribute("flag", flag);
		return "admin/backup/setting/index";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateBackupSetting",method=RequestMethod.POST)
	public void updateBackupSetting(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 这个类的处理逻辑是这样的，先把前台传过来的数据update到数据库,之后删除当前的定时任务，并根据前台传过来的时间添加一个新的定时任务
		 * 这个定时任务的作用主要是：首先备份数据库采用mysqldump命令，之后备份静态资源，包括文件 图片 apk等。统一打成tar.gz格式，为了兼容linux上的压缩格式。
		 * 并且把达成的压缩包上传到前台指定的目录中去。
		 */
		String autoBackup = request.getParameter("autoBackup");
		String backupType = request.getParameter("backupType");
		String backUp = request.getParameter("backUp");
		String backupTime = request.getParameter("backupTime");
		String backupPath = request.getParameter("backupPath");
		String cronExpression = request.getParameter("cronExpression");
		String instant = request.getParameter("flag");
		backupService.backup(autoBackup,backupType,backUp,backupTime,backupPath,cronExpression,instant);
	}
}
