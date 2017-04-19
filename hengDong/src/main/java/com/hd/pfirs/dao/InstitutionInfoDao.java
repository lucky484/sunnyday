package com.hd.pfirs.dao;

import java.util.List;

import com.hd.pfirs.model.InstitutionInfoModel;

/**
 * 组织机构信息dao
 * @author brave.chen
 * @since 2016-01-26
 */
public interface InstitutionInfoDao
{

	/**
	 * 根据组织编号获取系统工作人员信息
	 * 
	 * @param institutionCode 组织编号
	 */
	public InstitutionInfoModel getInstitutionInfoById(String institutionCode);

	/**
	 * 获取组织机构信息对象
	 * @return 组织机构信息对象列表
	 */
	public List<InstitutionInfoModel> getInstitutionInfos();

}
