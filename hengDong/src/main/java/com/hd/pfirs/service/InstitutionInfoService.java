package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.InstitutionInfoModel;

/**
 * 
 * @author brave.chen
 * @since 2016-02-02
 */
public interface InstitutionInfoService
{

	/**
	 * 根据组织编号获取系统工作人员信息
	 * 
	 * @param institutionCode 组织编号
	 *            
	 */
	InstitutionInfoModel getInstitutionInfoById(String institutionCode);

	/**
	 * 获取组织机构信息对象
	 * @return 组织机构信息对象列表
	 */
	List<InstitutionInfoModel> getInstitutionInfos();

}
