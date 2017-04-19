package com.softtek.mdm.service;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.model.EmailModelContent;
import com.softtek.mdm.model.EmailParamModel;
import com.softtek.mdm.model.SystemParamSetModel;

/**
 * 参数设置服务类
 * date: Jun 2, 2016 3:45:48 PM
 *
 * @author brave.chen
 */
public interface ParamSettingService
{
	/**
	 * 新增系统参数对象记录
	 *
	 * @author brave.chen
	 * @param systemParamSetModel 系统参数对象模型
	 */
	void addSysParamSetting(SystemParamSetModel systemParamSetModel);
	
	/**
	 * 修改系统参数配置
	 *
	 * @author brave.chen
	 * @param systemParamSetModel 系统参数对象模型
	 */
	void updateSysParamSetting(SystemParamSetModel systemParamSetModel);
	
	/**
	 * 查询系统参数对象记录
	 *
	 * @author brave.chen
	 * @return systemParamSetModel 系统参数对象模型
	 */
	SystemParamSetModel querySysParamSetting();

	/**
	 * 更新消息通知参数（有更新，没有插入）
	 *
	 * @author brave.chen
	 * @param model 邮件对象
	 */
	void updateMessageAdviceParam(EmailParamModel model);
	
	/**
	 * 更新邮件模板内容对象列表
	 *
	 * @author brave.chen
	 * @param emailModelContents
	 */
	void updateEmailModelsContent(List<EmailModelContent> emailModelContents);

	/**
	 * 查询邮件通知模板对象列表
	 *
	 * @author brave.chen
	 * @return 邮件模板对象列表
	 */
	List<EmailModelContent> queryEmailModels();

	/**
	 * 
	 * 获取消息通知邮件参数对象
	 *
	 * @author brave.chen
	 * @return 邮件对象
	 */
	EmailParamModel queryEmailParams();

	/**
	 * 保存消息通知邮件对象
	 *
	 * @author brave.chen
	 * @param model 邮件参数对象
	 */
	void saveMessageAdviceParam(EmailParamModel model);

	int queryCopyIsDefault();

	int queryLogoIsDefault();

	String queryDefaultCopyright();

	String queryCopyright();

	void modifystyle(MultipartFile files, ServletContext s1, String ischangelogo, String logopath, String copyright,String path);

	int resetstyle();
	
	/**
	 * 根据类型获取邮件模板对象
	 *
	 * @author brave.chen
	 * @param type 类型
	 * @return 邮件模板对象
	 */
	EmailModelContent queryEmailModelContentByType(int type);
}

