package com.hd.pfirs.service;

/**
 * 人证比对的轮循服务
 * 
 * @author ligang.yang
 *
 */
public interface IdCardComInfoService {

	/**
	 * 轮循服务(发送身份证信息去缉控库比对)
	 */
	public void sendIdCardInfo2Com();
}
