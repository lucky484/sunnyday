package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.market.CComment;

public interface CCommentService {

	int selectCountByGoodsNo(String goodsNo);

	List<CComment> selectByGoodsNo(Map<String, Object> map);

	int insert(CComment model, String orderDetailsId);

}
