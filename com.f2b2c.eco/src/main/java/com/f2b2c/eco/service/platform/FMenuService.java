package com.f2b2c.eco.service.platform;

import java.util.List;

import com.f2b2c.eco.model.platform.FMenuModel;

/**
 * 菜单服务接口
 * @author brave.chen
 *
 */
public interface FMenuService {
	/**
	 * 查询菜单对象列表
	 * 
	 * @return 获取菜单对象列表
	 */
	List<FMenuModel> queryFMenuModelList();

	public List<FMenuModel> queryMenuByUserId(Integer userId);
	
	public int queryIsExists(String url);
}
