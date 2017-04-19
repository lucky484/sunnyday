package com.softtek.mdm.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.softtek.mdm.model.EmailParamModel;

/**
 * 邮件发送工具类 date: Jun 16, 2016 1:51:45 PM
 *
 * @author brave.chen
 */
public interface EmailSendService
{	
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
	void sendAgainstRuleMail(String userName, String deviceName, String ruleName, String ruleContent, String userDefinedContent,List<String> to) throws AddressException, MessagingException;

	
	/**
	 * 发送用户批量导入通知邮件
	 *
	 * @author brave.chen
	 * @param displayName 姓名
	 * @param passWord 密码
	 * @param userName 用户名
	 */
	void sendBatchImportUserMail(String displayName, String passWord, String userName) throws AddressException, MessagingException;
	
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
	void sendPasswordBeyondDateMail(String adminType, String expirationDescription, String expirationMessage, 
			String expirationTime, String userName) throws AddressException, MessagingException;
	
	/**
	 * 发送邮件
	 *
	 * @author brave.chen
	 * @param email 邮件参数对象
	 */
	void sendMail(EmailParamModel email) throws AddressException, MessagingException;
}
