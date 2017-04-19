package com.softtek.mdm.status;

/**
 * 用于标识所有公共SESSION中的key
 * @author color.wu
 *
 */
public enum SessionStatus{
	/**
	 * 当前登录用户所管理的部门（登录用户是机构管理员和部门管理员）对应对象List<StructureModel>
	 */
	SOFTTEK_DEPARTMENT("softtek_department"),
	/**
	 * 机构管理员和部门管理员（超级管理员）ManagerModel
	 */
	SOFTTEK_MANAGER("softtek_manager"),
	/**
	 * 普通用户
	 */
	SOFTTEK_CUSTOMER("softtek_customer"),
	/**
	 * 移动设备终端（暂未使用）
	 */
	SOFTTEK_TERMINAL("softtek_terminal"),
	/**
	 * 组织机构
	 */
	SOFTTEK_ORGANIZATION("softtek_organization"),
	
	/**
	 * 操作日志参数
	 */
	SOFTTEK_ARGS("softtek_args"),
	
	/**
	 * 机构管理员：当前机构管理员、当前机构管理的创建的部门管理员、同一机构下的其他机构管理员、同一机构下的其他机构管理员创建的部门管理员
	 * 部门管理员：当前机构管理员、当前部门管理员（目前暂未使用）
	 */
	SOFTTEK_MANAGERLIST("softtek_manager_list");

	private String displayName;
	
	/**
	 * 枚举构造方法.
	 * 
	 * @param name String
	 */
	private SessionStatus(final String name) {
		this.displayName = name;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}
