/**
 * 
 */
package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.ZaiTaoAJLXDao;
import com.hd.pfirs.model.ZaiTaoAJLX;
import com.hd.pfirs.service.ZaiTaoAJLXService;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 25, 2015 11:06:43 AM
 */
@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class ZaiTaoAJLXServiceImpl implements ZaiTaoAJLXService{
	
	@Autowired
	private ZaiTaoAJLXDao ZaiTaoAJLXDao;

	@Override
	public ZaiTaoAJLX getZaiTaoAJLX(String code) {
		return ZaiTaoAJLXDao.getZaiTaoAJLX(code);
	}

	@Override
	public ZaiTaoAJLX getAJLX(String ysfzh) {
		return ZaiTaoAJLXDao.getAJLX(ysfzh);
	}

}
