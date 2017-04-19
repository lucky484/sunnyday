package com.hd.client.service;

import com.hd.client.model.IdCardInfo;

/**
 * @ClassName: IDcardWriterService
 * @Description: 这是一个定时将身份证信息写入数据库的类
 * @author: ligang.yang
 * @date: 2016年1月9日 下午5:00:49
 */
public interface IDcardWriterService {
	/**
	 * 
	 * @Description: 写入数据到数据库
	 * @param card
	 * @return: void
	 */
	public void writeOut2DB(IdCardInfo card);

	/**
	 * 
	 * @Description: 16为id
	 * @param msg
	 * @return
	 * @return: String
	 */
	public String write16IdMsg(byte[] msg);

}
