/**
 * 
 */
package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.FugitivesDao;
import com.hd.pfirs.model.IdCardComPoliceModel;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;
import com.hd.pfirs.service.FugitivesService;

/**
 * @ClassName:
 * @Description:
 * @author
 * @date Dec 23, 2015 4:56:36 PM
 */
@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class FugitivesServiceImpl implements FugitivesService {

	@Autowired
	private FugitivesDao fugitivesDao;

	@Override
	public T_QB_RY_ZTRYJBXX getT_QB_RY_ZTRYJBXX(String id) {
		return fugitivesDao.getT_QB_RY_ZTRYJBXX(id);
	}

	@Override
	public String comAtCtlLib(String IdCardNo) {
		T_QB_RY_ZTRYJBXX T_QB_RY_ZTRYJBXX = fugitivesDao.queryT_QB_RY_ZTRYJBXXByCardNo(IdCardNo);
		if (T_QB_RY_ZTRYJBXX == null)
			return "";
		return T_QB_RY_ZTRYJBXX.getId();
	}

	@Override
	public List<IdCardComPoliceModel> comAtCtlLibs(String IdCardNo) {
		List<IdCardComPoliceModel> list = fugitivesDao.queryAtCtlLibsByCardNo(IdCardNo);
		if (list == null || list.isEmpty() || list.get(0) == null)
			return null;
		return list;
	}

	@Override
	public T_QB_RY_ZTRYJBXX getJiKong(String id) {
		return fugitivesDao.getJiKong(id);
	}

	@Override
	public List<IdCardComPoliceModel> queryIdCardCompareResult(String cardno) {
		// TODO Auto-generated method stub
		return fugitivesDao.queryIdCardCompareResult(cardno);
	}

	
}
