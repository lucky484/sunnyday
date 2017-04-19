package com.hd.client.util.clibrary;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * 用注册指定位置的c++动态库
 * @author Administrator
 *
 */
public interface CLibrary extends Library {
	/**
	 * 加载指定位置的dll动态库
	 */
	CLibrary getInstance = (CLibrary) com.sun.jna.Native.loadLibrary("HDstdapi", CLibrary.class);

	/**
	 * 初始化身份证设备
	 * @param Port
	 * @return
	 */
	int HD_InitComm(int Port);

	/**
	 * 建立安全通道认证
	 * @return
	 */
	int HD_Authenticate();

	/**
	 * 关闭身份证
	 * @return
	 */
	int HD_CloseComm();

	/**
	 * 
	 * @Description: 采集信息
	 * @param pBmpFile
	 * @param pBmpDate
	 * @param pName
	 * @param pSex
	 * @param pNation
	 * @param pBirth
	 * @param pAddress
	 * @param pCertNo
	 * @param pDepartment
	 * @param pEffectData
	 * @param pExpire
	 * @return: int
	 *  			错误码
	 */
	int HD_Read_BaseInfo(Pointer pBmpFile, Pointer pBmpDate, Pointer pName, Pointer pSex, Pointer pNation,
			Pointer pBirth, Pointer pAddress, Pointer pCertNo, Pointer pDepartment, Pointer pEffectData,
			Pointer pExpire);

	/**
	 * 
	 * @Description: 采集信息
	 * @param pBmpFile
	 * @param pBmpDate
	 * @param pName
	 * @param pSex
	 * @param pNation
	 * @param pBirth
	 * @param pAddress
	 * @param pCertNo
	 * @param pDepartment
	 * @param pEffectData
	 * @param pExpire
	 * @return: int
	 *  			错误码
	 */
	int HD_Read_BaseInfo(String pBmpFile, Pointer pBmpDate, Pointer pName, Pointer pSex, Pointer pNation,
			Pointer pBirth, Pointer pAddress, Pointer pCertNo, Pointer pDepartment, Pointer pEffectData,
			Pointer pExpire);

}
