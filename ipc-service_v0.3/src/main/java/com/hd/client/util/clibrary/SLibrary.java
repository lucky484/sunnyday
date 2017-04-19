package com.hd.client.util.clibrary;

import com.sun.jna.Library;

/**
 * 用注册指定位置的c++动态库
 * 
 * @author Administrator
 *
 */
public interface SLibrary extends Library {
	/**
	 * 加载指定位置的dll动态库
	 */
	SLibrary getInstance = (SLibrary) com.sun.jna.Native.loadLibrary("SSSE32", SLibrary.class);

	/**
	 * 初始化身份证设备
	 * 
	 * @param Port
	 * @return
	 */
	int ICC_Reader_Open(String dev_Name);

	/**
	 * 建立安全通道认证
	 * 
	 * @return
	 */
	int ICC_Reader_Close(int ReaderHandle);

	/**
	 * 
	 * @Description: 读取16为id信息
	 * @param ReaderHandle
	 * @param getdata
	 * @return
	 * @return: int
	 */
	int PICC_Reader_ID_Person(int ReaderHandle, byte[] getdata);

}
