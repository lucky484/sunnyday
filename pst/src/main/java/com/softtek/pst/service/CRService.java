/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CRService.java
 * @Prject: pst
 * @Package: com.softtek.pst.service.impl
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 6, 2016 9:57:17 AM
 * @version: V1.0  
 */
package com.softtek.pst.service;

import com.softtek.pst.model.CRModel;
import com.softtek.pst.util.Page;

/**
 * @ClassName: CRService
 * @Description: CR的增删改查
 * @author: light.chen
 * @date: Apr 6, 2016 9:57:17 AM
 */
public interface CRService {
	
	//新增CR
	public int addCR(CRModel cr);
	//删除CR
	public int deleteCR(long crId);
    //修改CR
	public int updateCR(CRModel cr);
	//查询CR总数
//	public int getCRNumber(long projectId, String search);
	//查询所有CR信息
	public Page<CRModel> getCRs(long projectId, String column, String dir, String search);
//	public Page<CRModel> getAllCR(long projectId,Integer start, Integer length);
    //根据id查询具体CR信息
	public CRModel getCR(long crId);

}
