package com.softtek.mdm.email;

import java.security.Security;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.softtek.mdm.model.EmailParamModel;

/**
 * 邮件发送线程类
 * date: Jun 20, 2016 2:30:23 PM
 *
 * @author brave.chen
 */
public class EmailSendThread extends Thread
{
	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(EmailSendThread.class);
	
	
	/**
	 * 邮件参数对象
	 */
	private EmailParamModel emailParamModel;
	
	public EmailSendThread(EmailParamModel emailParamModel)
	{
		this.emailParamModel = emailParamModel;
	}

	@SuppressWarnings("restriction")
	@Override
	public void run()
	{
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", emailParamModel.getHost());
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", emailParamModel.getPort());
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(emailParamModel.getUsername(), emailParamModel.getPassword());
			}
		});

		Message msg = new MimeMessage(session);
		try
		{
			msg.setFrom(new InternetAddress(emailParamModel.getUsername()));
			msg.setRecipients(Message.RecipientType.TO, strListToInternetAddresses(emailParamModel.getTo()));
			msg.setSubject(emailParamModel.getSubject());

			msg.setContent(emailParamModel.getText(), "text/html;charset=utf-8");
			msg.setSentDate(new Date());
			Transport.send(msg);
		}
		catch (AddressException e)
		{
			logger.error("email address is not available,excepiton message is " + e.getMessage());
		}
		catch (MessagingException e)
		{
			logger.error("email send failed!Excepiton message is " + e.getMessage());
		}
	}
	
	private InternetAddress[] strListToInternetAddresses(List<String> list)
	{
		if (list == null || list.isEmpty())
		{
			return null;
		}
		InternetAddress[] arr = new InternetAddress[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			try
			{
				arr[i] = new InternetAddress(list.get(i));
			}
			catch (AddressException e)
			{
				e.getMessage();
			}
		}
		return arr;
	}
	
}

