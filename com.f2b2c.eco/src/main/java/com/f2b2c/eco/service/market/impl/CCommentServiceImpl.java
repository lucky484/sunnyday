package com.f2b2c.eco.service.market.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b2c.eco.dao.market.BSalesOrderDetailsDao;
import com.f2b2c.eco.dao.market.CCommentDao;
import com.f2b2c.eco.model.market.CComment;
import com.f2b2c.eco.service.market.CCommentService;

/**
 * 订单实现类
 * 
 * @author jane.hui
 *
 */
@Service("ccommentService")
@Transactional
public class CCommentServiceImpl implements CCommentService {

	@Autowired
	private CCommentDao cCommentDao;
	@Autowired
	private BSalesOrderDetailsDao bSalesOrderDetailsDao;
	@Override
	public int selectCountByGoodsNo(String goodsNo) {
		return cCommentDao.selectCountByGoodsNo(goodsNo);
	}

	/**
     * 添加商品评价
     */
	@Override
	public List<CComment> selectByGoodsNo(Map<String, Object> map) {
		return cCommentDao.selectByGoodsNo(map);
	}

	@Override
	public int insert(CComment model, String orderDetailsId) {
        // 更新订单详情为已经评价
		int a = bSalesOrderDetailsDao.updateComment(orderDetailsId);
        // 插入评价
		int b = cCommentDao.insert(model);
		return a + b;
	}
}