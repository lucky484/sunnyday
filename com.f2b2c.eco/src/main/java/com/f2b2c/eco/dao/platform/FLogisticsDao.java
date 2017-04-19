package com.f2b2c.eco.dao.platform;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.platform.FLogisticsModel;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FLogisticsDao {

	/**
	 * 查物流方式
	 * @param name 物流名称
	 * @return
	 */
	List<FLogisticsModel> findWithPagination(Map<String, Object> paramMap);
	FLogisticsModel queryLogisticsById(Integer	id);
	
	/**
	 * 查物流方式总数
	 * @param paramMap 参数map
	 * @return
	 */
	int queryLogisticsCount(Map<String, Object> paramMap);
	
	/**
	 * 修改功能
	 * @param fLogisticsModel
	 * @return
	 */
	int update( FLogisticsModel model);
	
	/**
	 * 添加功能
	 * @param model
	 * @return
	 */
	int insert(FLogisticsModel model);

	/**
	 * 删除功能
	 * @param id
	 * @return
	 */
	int delete(Integer id);
}
