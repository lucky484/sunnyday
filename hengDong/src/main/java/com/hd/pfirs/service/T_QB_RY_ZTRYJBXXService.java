/**
 * 
 */
package com.hd.pfirs.service;

import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;

/**
 * @ClassName: T_QB_RY_ZTRYJBXXService
 * @Description: 在逃人员
 * @author light.chen
 * @date Dec 19, 2015 4:07:32 PM
 */

public interface T_QB_RY_ZTRYJBXXService {
	
	//获取在逃人员信息
	public T_QB_RY_ZTRYJBXX getT_QB_RY_ZTRYJBXX();
	
	public T_QB_RY_ZTRYJBXX getJiKong();
    
	public T_QB_RY_ZTRYJBXX getIdCardInfoWarn(String cardCode);
}
