/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: projectCodeDao.java
 * @Prject: pst
 * @Package: com.softtek.pst.dao
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 6, 2016 9:47:28 AM
 * @version: V1.0  
 */
package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.CRModel;

/**
 * @ClassName: projectCodeDao
 * @Description: CR的增删改查
 * @author: light.chen
 * @date: Apr 6, 2016 9:47:28 AM
 */
public interface CRDao {

	// 新增CR
	public int addCR(CRModel cr);

	// 删除CR
	public int deleteCR(long crId);

	// 修改CR
	public int updateCR(CRModel cr);

	// 查询CR总数
	public int getCRNumber(@Param(value = "projectId") long projectId, @Param(value = "search") String search);

	// 查询所有CR信息
	public List<CRModel> getCRs(@Param(value = "projectId") long projectId, @Param(value = "col") String column, @Param(value = "dir") String dir, @Param(value = "search") String search);

	public List<CRModel> getCRByProjectId(@Param(value = "projectId") long projectId);

	// 根据id查询具体CR信息
	public CRModel getCR(long crId);

}
