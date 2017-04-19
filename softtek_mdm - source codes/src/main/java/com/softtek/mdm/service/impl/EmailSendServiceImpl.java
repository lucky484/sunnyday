package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.softtek.mdm.email.EmailSendThread;
import com.softtek.mdm.model.EmailModelContent;
import com.softtek.mdm.model.EmailParamModel;
import com.softtek.mdm.service.EmailSendService;
import com.softtek.mdm.service.ParamSettingService;

/**
 * 邮件发送工具类 date: Jun 16, 2016 1:51:45 PM
 *
 * @author brave.chen
 */
@Service
public class EmailSendServiceImpl implements EmailSendService
{
	/**
	 * 线程池对象
	 */
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 参数设置服务类
	 */
	@Autowired
	private ParamSettingService paramSettingService;
	
	/**
	 * 发送违规通知邮件
	 *
	 * @author brave.chen
	 * @param mapuserName 用户名
	 * @param deviceName 设备名称
	 * @param ruleName 规则名称
	 * @param ruleContent 规则内容
	 * @param userDefinedContent 用户自定义内容
	 */
	@Override
	public void sendAgainstRuleMail(String userName, String deviceName, String ruleName, String ruleContent,
			String userDefinedContent, List<String> to) throws AddressException, MessagingException {
		EmailModelContent emailModelContent = paramSettingService.queryEmailModelContentByType(0);
		EmailParamModel emailParamModel = getEmailParamModel(emailModelContent);
		emailParamModel.setTo(to);
		String text = emailParamModel.getText();
		if (StringUtils.isNotEmpty(text))
		{
			text = text.replace("$userName$", StringUtils.trimToEmpty(userName));
			text = text.replace("$deviceName$", StringUtils.trimToEmpty(deviceName));
			text = text.replace("$ruleName$", StringUtils.trimToEmpty(ruleName));
			text = text.replace("$ruleContent$", StringUtils.trimToEmpty(ruleContent));
			text = text.replace("$userDefinedContent$", StringUtils.trimToEmpty(userDefinedContent));
		}
		emailParamModel.setText(text);
		
		sendMail(emailParamModel);
	}
	
	/**
	 * 发送用户批量导入通知邮件
	 *
	 * @author brave.chen
	 * @param displayName 姓名
	 * @param passWord 密码
	 * @param userName 用户名
	 */
	@Override
	public void sendBatchImportUserMail(String displayName, String passWord, String userName) throws AddressException, MessagingException
	{
		EmailModelContent emailModelContent = paramSettingService.queryEmailModelContentByType(0);
		EmailParamModel emailParamModel = getEmailParamModel(emailModelContent);
		String text = emailParamModel.getText();
		if (StringUtils.isNotEmpty(text))
		{
			text = text.replace("$displayName$", StringUtils.trimToEmpty(displayName));
			text = text.replace("$passWord$", StringUtils.trimToEmpty(passWord));
			text = text.replace("$userName$", StringUtils.trimToEmpty(userName));
		}
		emailParamModel.setText(text);
		
		sendMail(emailParamModel);
	}
	
	/**
	 * 发送用户批量导入通知邮件
	 *
	 * @author brave.chen
	 * @param adminType 管理员类型（企业管理员等）
	 * @param expirationDescription 失效描述
	 * @param expirationMessage 提示语
	 * @param expirationTime 失效时间
	 * @param userName  用户名
	 */
	@Override
	public void sendPasswordBeyondDateMail(String adminType, String expirationDescription, String expirationMessage, 
			String expirationTime, String userName) throws AddressException, MessagingException
	{
		EmailModelContent emailModelContent = paramSettingService.queryEmailModelContentByType(0);
		EmailParamModel emailParamModel = getEmailParamModel(emailModelContent);
		String text = emailParamModel.getText();
		if (StringUtils.isNotEmpty(text))
		{
			text = text.replace("$adminType$", StringUtils.trimToEmpty(adminType));
			text = text.replace("$expirationDescription$", StringUtils.trimToEmpty(expirationDescription));
			text = text.replace("$expirationMessage$", StringUtils.trimToEmpty(expirationMessage));
			text = text.replace("$expirationTime$", StringUtils.trimToEmpty(expirationTime));
			text = text.replace("$userName$", StringUtils.trimToEmpty(userName));
		}
		
		emailParamModel.setText(text);
		
		sendMail(emailParamModel);
	}
	
	private EmailParamModel getEmailParamModel(EmailModelContent emailModelContent)
	{
		EmailParamModel  emailParamModel  = paramSettingService.queryEmailParams();
		emailParamModel.setHost(emailParamModel.getHost());
		emailParamModel.setPort(emailParamModel.getPort());
		emailParamModel.setUsername(emailParamModel.getUsername());
		emailParamModel.setPassword(emailParamModel.getPassword());
		List<String> toList = new ArrayList<String>();
		toList.add(emailParamModel.getSender());
		emailParamModel.setTo(toList);
		emailParamModel.setIsSSL(0);
		emailParamModel.setSubject(emailModelContent.getTheme());
		emailParamModel.setText(emailModelContent.getContent());
		
		return emailParamModel;
	}
	
	@Override
	public void sendMail(EmailParamModel email) throws AddressException, MessagingException
	{
		EmailSendThread thread = new EmailSendThread(email);
		taskExecutor.execute(thread);
	}
}
