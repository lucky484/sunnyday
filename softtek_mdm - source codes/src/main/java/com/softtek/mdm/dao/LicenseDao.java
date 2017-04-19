package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.LicenseInfoModel;

/**
 * 许可证dao
 * date: May 24, 2016 4:09:41 PM
 *
 * @author brave.chen
 */
public interface LicenseDao
{
	/**
	 * 更新license信息
	 *
	 * @author brave.chen
	 * @param map
	 */
	void updateLicenseInfo(LicenseInfoModel model);
	
	/**
	 * 查询license信息
	 * @author brave.chen
	 * @return license对象信息
	 */
	LicenseInfoModel queryLicenseInfo();

	/**
	 * 添加license记录
	 * @author brave.chen
	 * @param model license对象模型
	 */
	void addLicenseInfoModel(LicenseInfoModel model);

	/**
	 * 查询限制结果
	 *
	 * @author brave.chen
	 * @param orgIds 机构列表
	 * @return 限制结果
	 */
	int queryOrgsLimit(List<Integer> orgIds);

	/**
	 * 获取机构下剩余可以激活的人数
	 *
	 * @author brave.chen
	 * @param orgId 机构ID
	 * @return 剩余人数
	 */
	int getOrgRemainCount(int orgId);
}

