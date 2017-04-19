package com.f2b2c.eco.dao.market;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BNoticeModel;

public interface BNoticeDao extends CrudMapper<BGoodsModel, Integer>{

	List<BNoticeModel> findAllNotice();

	int findAllNoticePageCount();

	List<BNoticeModel> findAllNoticePage(@Param(value = "start") Integer start,
			@Param(value = "length") Integer length);

	/**
	 * 发布公告
	 */
	int insertBnotice(Map<String, Object> map);

	int delBnotice(Map<String, Object> map);

	BNoticeModel bnoticeDetail(Integer id);

}