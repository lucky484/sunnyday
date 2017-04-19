package com.softtek.mdm.thread;

import java.util.List;
import org.apache.log4j.Logger;
import com.softtek.mdm.bean.IosPolicyVersionBean;
import com.softtek.mdm.util.IosPushUtil;

/**
 * ios推送版本号
 * @author jane.hui
 *
 */
public class PushIosVersionThread extends Thread
{
	/**
	 * 日志对象
	 */
	private static Logger logger = Logger.getLogger(PushIosVersionThread.class);
	
	/**
	 * 提示信息
	 */
	private String alert;
	
	/**
	 * 标记
	 */
	private int badge;
	
	/**
	 * 声音
	 */
	private String sound;
	
	/**
	 * 
	 */
	private List<IosPolicyVersionBean> list;
	
	/**
	 * 通知apns服务线程
	 * @param p12Path p12文件路径
	 * @param info token对象
	 */
	public PushIosVersionThread(String alert,int badge, String sound,List<IosPolicyVersionBean> list)
	{ 
		this.alert = alert;
		this.badge = badge;
		this.sound = sound;
		this.list = list;
	}
	
	@Override
	public void run()
	{
		try
		{
			IosPushUtil.pushIosPolicyVersion(alert, badge, sound, list);
		}
		catch (Exception e)
		{
			logger.error("something wrong when push by token, exception message is "
					+ e.getMessage());
		}
	}
}
