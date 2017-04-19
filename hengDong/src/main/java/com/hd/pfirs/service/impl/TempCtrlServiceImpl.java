package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao.TempCtrlDao;
import com.hd.pfirs.model.TempCtrl;
import com.hd.pfirs.service.TempCtrlService;

/**
 * 
 * @author curry.su
 *
 */
@Service
public class TempCtrlServiceImpl implements TempCtrlService {

	@Autowired
	private TempCtrlDao tempCtrlDao;
	
	
	@Override
	public void insertTempCtrl(TempCtrl tempCtrl) {
		// TODO Auto-generated method stub
		tempCtrlDao.insertTempCtrl(tempCtrl);
	}

	

	@Override
	public List<TempCtrl> queryTempCtrl(String name, String sex, String idCardNo, int page,int fys) {
		// TODO Auto-generated method stub
		return tempCtrlDao.queryTempCtrl(name, sex, idCardNo, page,fys);
	}



	@Override
	public void delTempCtrl(long tempCompID) {
		// TODO Auto-generated method stub
		tempCtrlDao.delTempCtrl(tempCompID);
	}

	@Override
	public int getCountTepCtrl(String name,String sex,String idCardNo) {
		// TODO Auto-generated method stub
		return tempCtrlDao.getCountTepCtrl(name, sex, idCardNo);
	}



	@Override
	public void updateStatus(String status, long tempCompID) {
		// TODO Auto-generated method stub
		tempCtrlDao.updateStatus(status, tempCompID);
	}
	
	/**
	 * 得到主键
	 * @return
	 */
	@Override
	public long getTempCompID(){
		return tempCtrlDao.getTempCompID();
	};

}
