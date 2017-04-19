/**
 * 
 */
package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.T_QB_RY_ZTRYJBXXDao;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;
import com.hd.pfirs.service.T_QB_RY_ZTRYJBXXService;

/**
 * @ClassName: T_QB_RY_ZTRYJBXXServiceImpl
 * @Description: 在逃人员
 * @author light.chen
 * @date Dec 19, 2015 4:08:48 PM
 */
@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class T_QB_RY_ZTRYJBXXServiceImpl implements T_QB_RY_ZTRYJBXXService{
	
	@Autowired
	private T_QB_RY_ZTRYJBXXDao T_QB_RY_ZTRYJBXXDao;
	
	//获取在逃人员信息
	public T_QB_RY_ZTRYJBXX getT_QB_RY_ZTRYJBXX(){
		return T_QB_RY_ZTRYJBXXDao.getT_QB_RY_ZTRYJBXX();
	}
	
	public T_QB_RY_ZTRYJBXX getJiKong(){
		return T_QB_RY_ZTRYJBXXDao.getJiKong();
	}
    
	public T_QB_RY_ZTRYJBXX getIdCardInfoWarn(String cardCode){
		return T_QB_RY_ZTRYJBXXDao.getIdCardInfoWarn(cardCode);
	}
	
}
