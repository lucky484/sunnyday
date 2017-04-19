package com.f2b.sugar.func.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * java mail邮件发送实现类
 * Author: 叶利平  Date: 13-6-18, 下午1:49
 */
@SuppressWarnings("unused")
public class Email {
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;
	private static final Logger logger = LoggerFactory.getLogger(Email.class);

	/**
	 * sendMail
	 *
	 * @param subject 邮件主题
	 * @param content 邮件主题内容
	 * @param to      收件人Email地址
	 * @return true:发送成功，false:发送失败
	 */
	public boolean sendMail(String subject, String content, String to) {
		//获取JavaMailSender bean
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		//设置utf-8或GBK编码，否则邮件会有乱码
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(to);//接受者
			messageHelper.setFrom(simpleMailMessage.getFrom());//发送者
			messageHelper.setSubject(subject);//主题
			//邮件内容，注意加参数true，表示启用html格式
			messageHelper.setText(content, true);
			mailSender.send(mailMessage);
			logger.info("mail to " + to + ",content:" + content);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
			return false;
		}
		return true;
	}

	//Spring 依赖注入
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	//Spring 依赖注入
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
