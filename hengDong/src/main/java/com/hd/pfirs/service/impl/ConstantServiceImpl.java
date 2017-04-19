/**
 * 
 */
package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.ConstantDao;
import com.hd.pfirs.model.Constant;
import com.hd.pfirs.service.ConstantService;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 25, 2015 10:10:40 AM
 */
@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class ConstantServiceImpl implements ConstantService{
	
	@Autowired
	private ConstantDao constantDao;

	@Override
	public Constant getConstant(String id) {
		return constantDao.getConstant(id);
	}

}
