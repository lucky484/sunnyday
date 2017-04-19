package com.f2b2c.eco.service.platform;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.f2b2c.eco.model.platform.FLogisticsModel;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FLogisticsService {

	/**
	 * 查物流方式
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	public Page<FLogisticsModel> queryLogisticsModel(Pageable pageable,Map<String, Object> paramMap);
	FLogisticsModel queryLogisticsById(Integer	id);
	/**
	 * 修改功能
	 * @param name
	 * @return
	 */
	int update( FLogisticsModel model);
	
	/**
	 * 添加功能
	 * @param id
	 * @param name
	 * @return
	 */
	public int insert(FLogisticsModel model);
	
	/**
	 * 删除功能
	 * @param id
	 * @return
	 */
	int delete(String id);
}
