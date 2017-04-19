package com.hd.client.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hd.client.util.IDCardUtil;

/**
 * Application Lifecycle Listener implementation class InitDevListener
 *
 */
@Service
@Scope("prototype")
public class InitIDDevServiceImpl implements InitializingBean {
	/**
	 * 注入读卡器设备工具
	 */
	@Autowired
	private IDCardUtil idCardUtil;

	/**
	 * 循环读取读卡器设备的数据
	 */
	@Override
	public void afterPropertiesSet() {
		idCardUtil.cyclePrintIDcardInfo();
	}

}
