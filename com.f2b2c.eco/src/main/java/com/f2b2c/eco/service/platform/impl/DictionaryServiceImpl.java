package com.f2b2c.eco.service.platform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.DictionaryDao;
import com.f2b2c.eco.model.common.Dictionary;
import com.f2b2c.eco.service.platform.DictionaryService;

@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public String getProducer(String type) {
		
		return dictionaryDao.getProducer(type);
	}

	@Override
	public void setProducer(Dictionary dictionary) {
		
		dictionaryDao.setProducer(dictionary);
		
	}
	
	
	
	
}
