package com.f2b2c.eco.dao.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.CComment;

public interface CCommentDao extends CrudMapper<CComment, Integer> {

	int selectCountByGoodsNo(String goodsNo);

	List<CComment> selectByGoodsNo(Map<String, Object> map);

}