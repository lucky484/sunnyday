/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CRServiceImpl.java
 * @Prject: pst
 * @Package: com.softtek.pst.service.impl
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 6, 2016 9:58:39 AM
 * @version: V1.0  
 */
package com.softtek.pst.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.CRDao;
import com.softtek.pst.model.CRModel;
import com.softtek.pst.service.CRService;
import com.softtek.pst.util.Page;

/**
 * @ClassName: CRServiceImpl
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 6, 2016 9:58:39 AM
 */
@Service
public class CRServiceImpl implements CRService{
	@Autowired
	private CRDao crDao;

	/**
	 * @Title: addCR
	 * @Description: TODO
	 * @param cr
	 * @return
	 * @see com.softtek.pst.service.impl.CRService#addCR(com.softtek.pst.model.CRModel)
	 */
	public int addCR(CRModel cr) {
		return crDao.addCR(cr);
	}

	/**
	 * @Title: deleteCR
	 * @Description: TODO
	 * @param cr
	 * @return
	 * @see com.softtek.pst.service.impl.CRService#deleteCR(com.softtek.pst.model.CRModel)
	 */
	public int deleteCR(long crId) {
		return crDao.deleteCR(crId);
	}

	/**
	 * @Title: updateCR
	 * @Description: TODO
	 * @param cr
	 * @return
	 * @see com.softtek.pst.service.impl.CRService#updateCR(com.softtek.pst.model.CRModel)
	 */
	public int updateCR(CRModel cr) {
		return crDao.updateCR(cr);
	}

	/**
	 * @Title: getCR
	 * @Description: TODO
	 * @param projectCode
	 * @return
	 * @see com.softtek.pst.service.impl.CRService#getCR(int)
	 */
	public CRModel getCR(long crId) {
		return crDao.getCR(crId);
	}

	/**
	 * @Title: getAllCR
	 * @Description: TODO
	 * @return
	 * @see com.softtek.pst.service.impl.CRService#getAllCR()
	 */
	@Override
	public Page<CRModel> getCRs(long projectId, String column, String dir, String search) {
		Page<CRModel> page = new Page<>();
		page.setData(crDao.getCRs(projectId,column,dir,search));
		int total = crDao.getCRNumber(projectId,search);
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}
//	@Override
//	public Page<CRModel> getCRs(long projectId,Integer start, Integer length) {
//		Page<CRModel> page = new Page<>();
//		page.setData(crDao.getCRs(projectId,start, length));
//		int total = getCRNumber(projectId);
//		page.setRecordsTotal(total);
//		page.setRecordsFiltered(total);
//		return page;
//	}

}
