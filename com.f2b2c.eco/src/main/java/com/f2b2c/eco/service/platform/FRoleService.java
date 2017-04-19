package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FRoleModel;

/**
 * 
 * @author brave.chen
 *
 */
public interface FRoleService
{
	/**
	 * 查询所有角色对象
	 * @return 角色对象列表
	 */
	List<FRoleModel> queryAll();
	
	 FRoleModel findOneByUserId(String userId);

	Page<FRoleModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);
	
	 /**
	  * 查询当前用户能访问的所有的url
	  * @param userId
	  * @return
	  */
	 List<String> queryAllUrlByUserId(Integer userId);
	 
	 
	 List<String> queryAllUrl();
}
