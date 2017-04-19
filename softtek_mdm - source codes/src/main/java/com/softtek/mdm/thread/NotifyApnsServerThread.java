package com.softtek.mdm.thread;

import org.apache.log4j.Logger;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.util.CommUtil;

public class NotifyApnsServerThread extends Thread
{
	/**
	 * 日志对象
	 */
	private static Logger logger = Logger.getLogger(NotifyApnsServerThread.class);
	
	/**
	 * p12文件路径
	 */
	private String p12Path;
	
	/**
	 * token对象
	 */
	private TokenUpdateInfo info;
	
	/**
	 * 通知apns服务线程
	 * @param p12Path p12文件路径
	 * @param info token对象
	 */
	public NotifyApnsServerThread(String p12Path, TokenUpdateInfo info)
	{ 
		this.p12Path = p12Path;
		this.info = info;
	}
	
	@Override
	public void run()
	{
		ApnsService service = null;
		try
		{
			service = APNS.newService().withCert(p12Path, CommUtil.CERTIFICATE_PASSWORD).withProductionDestination().build();
			String mdmPayload = APNS.newPayload().customField("mdm", info.getPushMagic()).build();
			service.push(info.getToken(), mdmPayload);
		}
		catch (Exception e)
		{
			logger.error("something wrong when push by token info id, id is " + info.getId() + "exception message is "
					+ e.getMessage());
		}
	}
}
