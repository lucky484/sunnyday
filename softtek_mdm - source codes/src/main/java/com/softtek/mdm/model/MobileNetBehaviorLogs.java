
package com.softtek.mdm.model;

import java.util.List;

/**
 * 手机端上网日志类对象
 * date: Apr 18, 2016 3:06:50 PM
 *
 * @author brave.chen
 */
public class MobileNetBehaviorLogs
{
	/**
	 * 上网行为日志集合类
	 */
	private List<NetBehaviorLogInfoModel> netBehaviorLogInfoList;

	public List<NetBehaviorLogInfoModel> getNetBehaviorLogInfoList()
	{
		return netBehaviorLogInfoList;
	}

	public void setNetBehaviorLogInfoList(List<NetBehaviorLogInfoModel> netBehaviorLogInfoList)
	{
		this.netBehaviorLogInfoList = netBehaviorLogInfoList;
	}

}

