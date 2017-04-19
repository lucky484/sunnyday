package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.TempCompInfotDao;
import com.hd.pfirs.model.TempCompInfo;
import com.hd.pfirs.service.TempCompInfotSerivce;

@Service
public class TempCompInfotSerivceImpl implements TempCompInfotSerivce{
    
	@Autowired
	private TempCompInfotDao tempCompInfotDao;
	
	@Override
	public List<TempCompInfo> getTempCompinfo() {
		List<TempCompInfo> list = tempCompInfotDao.getTempCompinfo();
		return list;
	}
    
	
}
