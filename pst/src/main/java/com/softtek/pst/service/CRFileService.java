/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CRFileService.java
 * @Prject: pst
 * @Package: com.softtek.pst.service
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 16, 2016 1:29:29 PM
 * @version: V1.0  
 */
package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.CRFile;

/**
 * @ClassName: CRFileService
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 16, 2016 1:29:29 PM
 */
public interface CRFileService {

	public int addCRFile(CRFile crFile);

	public int updateCRFile(CRFile crFile);

	public CRFile getCRFile(long crFileId);

	public int deleteCRFileById(long crFileId);

	public List<CRFile> getCRFilebyCrId(long crId);

}
