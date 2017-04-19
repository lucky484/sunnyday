package com.f2b.security.business.biz;

import java.util.List;
import java.util.Map;

import com.f2b.security.domain.AdminUser;

/**
 * Author: val.jzp
 */
@SuppressWarnings("unused")
public interface AdminUserBiz {

	/**
	 * 登录
	 *
	 * @param agentName（登录名）
	 * @param password（密码）
	 */
	AdminUser login(String agentName, String password);

	// ******************************************************************************
	// ********************************* CRUD START *********************************
	// ******************************************************************************

	/**
	 * 获取总记录数
	 */
	public Long totalRecord();

	/**
	 * 获取总记录数
	 */
	public Long totalRecord(Map<String, String> queryHash);

	/**
	 * 列表不分页
	 */
	public List<AdminUser> findList();

	/**
	 * 列表不分页
	 */
	public List<AdminUser> findList(Map<String, String> queryHash);

	/**
	 * 分页列表
	 */
	public List<AdminUser> findList(Integer pageNow, Integer pageSize);

	/**
	 * 分页列表
	 */
	public List<AdminUser> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash);

	/**
	 * 分页列表
	 */
	public List<AdminUser> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

	/**
	 * id获取记录
	 */
	public AdminUser findModel(Long adminUserId);

	/**
	 * 增加或修改记录
	 */
	public void addOrUpdate(AdminUser model);

	/**
	 * 删除记录
	 */
	public void delete(Long adminUserId);

	// ******************************************************************************
	// ********************************** CRUD END **********************************
	// ******************************************************************************
}