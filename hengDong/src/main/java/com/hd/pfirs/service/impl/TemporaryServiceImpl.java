/**
 * 
 */
package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.TemporaryDao;
import com.hd.pfirs.model.Temporary;
import com.hd.pfirs.service.TemporaryService;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 24, 2015 3:12:15 PM
 */
@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class TemporaryServiceImpl implements TemporaryService{
	
	@Autowired
	private TemporaryDao temporaryDao;
	
	@Override
	public Temporary getTemporary(String id) {
		return temporaryDao.getTemporary(id);
	}

}
