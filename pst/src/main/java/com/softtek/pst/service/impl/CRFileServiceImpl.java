/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CRFileServiceImpl.java
 * @Prject: pst
 * @Package: com.softtek.pst.service.impl
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 16, 2016 1:30:53 PM
 * @version: V1.0  
 */
package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.CRFileDao;
import com.softtek.pst.model.CRFile;
import com.softtek.pst.service.CRFileService;

/**
 * @ClassName: CRFileServiceImpl
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 16, 2016 1:30:53 PM
 */
@Service
public class CRFileServiceImpl implements CRFileService {

	@Autowired
	private CRFileDao crFileDao;

	/**
	 * @Title: addCRFile
	 * @Description: TODO
	 * @param crFile
	 * @return
	 * @see com.softtek.pst.service.CRFileService#addCRFile(com.softtek.pst.model.CRFile)
	 */
	@Override
	public int addCRFile(CRFile crFile) {
		return crFileDao.addCRFile(crFile);
	}

	/**
	 * @Title: getCRFile
	 * @Description: TODO
	 * @param crId
	 * @return
	 * @see com.softtek.pst.service.CRFileService#getCRFile(long)
	 */
	@Override
	public CRFile getCRFile(long crFileId) {
		return crFileDao.getCRFile(crFileId);
	}

	/**
	 * @Title: getCRFilebyCrId
	 * @Description: TODO
	 * @param crId
	 * @return
	 * @see com.softtek.pst.service.CRFileService#getCRFilebyCrId(long)
	 */
	@Override
	public List<CRFile> getCRFilebyCrId(long crId) {
		return crFileDao.getCRFilebyCrId(crId);
	}

	@Override
	public int deleteCRFileById(long crFileId) {
		return crFileDao.deleteCRFileById(crFileId);
	}

	@Override
	public int updateCRFile(CRFile crFile) {
		return crFileDao.updateCRFile(crFile);
	}

}
