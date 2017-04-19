/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CRFileDao.java
 * @Prject: pst
 * @Package: com.softtek.pst.dao
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 16, 2016 1:28:17 PM
 * @version: V1.0  
 */
package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.CRFile;

/**
 * @ClassName: CRFileDao
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 16, 2016 1:28:17 PM
 */
public interface CRFileDao {

	public int addCRFile(CRFile crFile);

	public int updateCRFile(CRFile crFile);

	public int deleteCRFileById(long crFileId);

	public CRFile getCRFile(long crFileId);

	public List<CRFile> getCRFilebyCrId(long crId);

}
