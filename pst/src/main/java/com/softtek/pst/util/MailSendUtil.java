package com.softtek.pst.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.softtek.pst.model.Email;

public class MailSendUtil {
        
	

		public void sendEmail(Email email) throws IOException, MessagingException{
//			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			InputStream resourceAsStream = MailSendUtil.class.getClassLoader().getResourceAsStream("conf/mail.properties");
			 Properties properties = new Properties();
	         properties.load(resourceAsStream);
	         String protocol = properties.getProperty("protocol");
	         String host = properties.getProperty("host");
	         String port = properties.getProperty("port");
	         String username = properties.getProperty("username");
	         String password = properties.getProperty("password");
	         String auth = properties.getProperty("auth");
//	         String enable = properties.getProperty("enable");
	         JavaMailSenderImpl senderImpl = new JavaMailSenderImpl(); 
	         senderImpl.setHost(host);
	         MimeMessage mailMessage = senderImpl.createMimeMessage(); 
	         MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8"); 
	         Properties prop = new Properties() ;
	         prop.setProperty("mail.smtp.host",host);
//	         prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//	         prop.setProperty("mail.smtp.starttls.enable", enable);
	         prop.setProperty("mail.smtp.auth", auth);
//	         prop.setProperty("mail.smtp.socketFactory.fallback", "false");
	         prop.setProperty("mail.smtp.port",port);
//	         prop.setProperty("mail.smtp.socketFactory.port","465");
	         senderImpl.setJavaMailProperties(prop);
	         senderImpl.setProtocol(protocol);
	         senderImpl.setUsername(username);
	         senderImpl.setPassword(password);
	         messageHelper.setTo(strListToInternetAddresses(email.getTo()));
	         messageHelper.setFrom(username);
	         messageHelper.setSubject(email.getSubject());
	         messageHelper.setText(email.getContent());
	         senderImpl.send(mailMessage); 
		}
	    	
		private InternetAddress[] strListToInternetAddresses(List<String> list){
			if (list == null || list.isEmpty()){
				return null;
			}
			InternetAddress[] arr = new InternetAddress[list.size()];
			for (int i = 0; i < list.size(); i++){
				try{
					arr[i] = new InternetAddress(list.get(i));
				}catch (AddressException e){
					e.getMessage();
				}
			}
			return arr;
		}
	      	      
}
