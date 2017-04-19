package com.f2b.security.business.biz.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.FarmOrderBiz;
import com.f2b.security.business.biz.ShareRecordBiz;
import com.f2b.security.business.dao.FarmOrderDao;
import com.f2b.security.business.dao.ShareRecordDao;
import com.f2b.security.domain.FarmOrder;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.ExcelUtil;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

@Service("farmOrderBiz")
public class FarmOrderBizImpl implements FarmOrderBiz {

	private Logger logger = LoggerFactory.getLogger(FarmOrderBizImpl.class);

	@Autowired
	private FarmOrderDao farmOrderDao;
	@Autowired
	private ShareRecordBiz shareRecordBiz;
	@Autowired
	private ShareRecordDao shareRecordDao;

	@Override
	@Transactional
	public void addFarmOrder(FarmOrder model) {
		this.farmOrderDao.add(model);
	}

	/**
	 * 根据手机号查询订单
	 */
	@Override
	public List<FarmOrder> getOrderListByPhone(String phone) {
		return farmOrderDao.getOrderListByPhone(phone);
	}

	public List<FarmOrder> getOrderList(String openId) {
		return farmOrderDao.getOrderList(openId);
	}

	@Override
	public FarmOrder getOrderByNo(String orderNo, String openId) {
		return farmOrderDao.getOrderByNo(orderNo, openId);
	}

	/**
	 * 获取提子的销量
	 */
	@Override
	public Long totalGrapeVolume() {
		// TODO 获取提子的销量
		return farmOrderDao.totalGrapeVolume(null);
	}

	/**
	 * 根据sku查询订单
	 */
	@Override
	public List<FarmOrder> getDetailMap(String sku) {
		return farmOrderDao.getDetailMap(sku);
	}

	/**
	 * 根据sku查询当前所下的订单
	 * 
	 * @param sku
	 * @return
	 */
	public List<FarmOrder> getOrderListBySku(String sku){
		return farmOrderDao.getOrderListBySku(sku);
	}
	
	/**
	 * 跳转商品详情页
	 */
	@Override
	public List<FarmOrder> getFarmOrderPage(String produceName) {
		return farmOrderDao.getFarmOrderPage(produceName);
	}

	/**
	 * 订单管理 - 分页列表
	 */
	@Override
	public List<FarmOrder> findFarmOrder() {
		return this.findFarmOrder(0, 0, null);
	}

	@Override
	public List<FarmOrder> findFarmOrder(Map<String, String> queryHash) {
		return this.findFarmOrder(0, 0, queryHash);
	}

	@Override
	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize) {
		return this.findFarmOrder(pageNow, pageSize, null);
	}

	@Override
	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize, Map<String, String> queryHash) {
		return this.findFarmOrder(pageNow, pageSize, "", queryHash);
	}

	@Override
	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize, String sqlOrder,
			Map<String, String> queryHash) {
		return farmOrderDao.findFarmOrder(pageNow, pageSize, sqlOrder, queryHash);
	}

	/**
	 * 导出未发货
	 */
	@Override
	public void getFarmOrderExport1(HttpServletResponse response) {
		List<FarmOrder> excl = farmOrderDao.findFarmOrderExcl1();
		List dataset = excl;
		String[] headers = new String[] { "编号", "商品名", "订单号", "姓名", "联系电话", "总价", "数量", "地址", "运费", "单价", "下单时间",
				"订单状态(0:发货/-1:未发货)", "买家留言", "加密微信号" };
		ExcelUtil.exportExcel2("未发货订单详情", headers, dataset, "yyyy-MM-dd", response);
	}

	/**
	 * 导出全部
	 * 
	 * @param response
	 */
	public void getFarmOrderExport(HttpServletResponse response) {
		List<FarmOrder> excl = farmOrderDao.findFarmOrderExcl();
		List dataset = excl;
		String[] headers = new String[] { "编号", "商品名", "订单号", "姓名", "联系电话", "总价", "数量", "地址", "运费", "单价", "下单时间",
				"订单状态(0:发货/-1:未发货)", "买家留言", "加密微信号" };
		ExcelUtil.exportExcel2("全部订单详情", headers, dataset, "yyyy-MM-dd", response);
	}

	@Override
	public List<FarmOrder> getOrderListByOpenId(String openid) {
		return farmOrderDao.getOrderListByOpenId(openid);
	}
	public List<FarmOrder> getAllOrderListByOpenId(String openid) {
		return farmOrderDao.getAllOrderListByOpenId(openid);
	}
	public FarmOrder getOrderBySku(String sku) {
		return farmOrderDao.getOrderBySku(sku);
	}

	public void updateOrder(FarmOrder farmOrder) {
		farmOrderDao.update(farmOrder);
	}
	
	public List<FarmOrder> getAllSuccessPromoteOrder(String openId) {
		return farmOrderDao.getSuccessList(openId);
	}
	/**
	 * 修改订单
	 */
	@Override
	public void updateOrder(FarmOrder farmOrder, String openid, Object nickname, Object shareOpenid,
			AccessToken accessToken) {
		farmOrderDao.update(farmOrder);
		// // 当前购买者的openid
		List<ShareRecord> list = shareRecordDao.get(openid); // 当前购买者所有的分享人
		Set<String> set = new HashSet<String>();
		list.stream().forEach(r -> set.add(r.getShareOpenId()));
		for (String openId : set) {
			ShareRecord model = new ShareRecord();
			model.setOpenId(openid);
			model.setShareOpenId(openId);
			model.setShareNickName(WeixinUtil.getUserNickName(accessToken.getToken(), openId));
			model.setCreateDate(new Date());
			// 记录购买数量
			model.setNumber(Integer.valueOf(farmOrder.getWeight()));
			model.setSendRedPack(0);
			if (nickname == null) {
				model.setNickname(WeixinUtil.getUserNickName(accessToken.getToken(), openid));
			} else {
				model.setNickname((String) nickname);
			}
			shareRecordBiz.addOrUpdate(model);
		}
		String shareOpenId = null; // 分享人的openid
		if (shareOpenid != null) {
			// 购买者通过他人分享的链接进行购买
			shareOpenId = (String) shareOpenid;
			// 该分享人是首次分享给购买者
			if (!set.contains(shareOpenId)) {
				ShareRecord shareRecord = new ShareRecord();
				shareRecord.setOpenId(openid);
				// 获取分享人的昵称
				shareRecord.setShareNickName(WeixinUtil.getUserNickName(accessToken.getToken(), shareOpenId));
				shareRecord.setShareOpenId(shareOpenId);
				shareRecord.setCreateDate(new Date());
				// 记录购买数量
				shareRecord.setNumber(Integer.valueOf(farmOrder.getWeight()));
				shareRecord.setSendRedPack(0);
				if (nickname == null) {
					shareRecord.setNickname(WeixinUtil.getUserNickName(accessToken.getToken(), openid));
				} else {
					shareRecord.setNickname((String) nickname);
				}
				shareRecordBiz.addOrUpdate(shareRecord);
			}
		}
	}
	
	public FarmOrder getUnPayOrderByOpenid(String openid) {
		return farmOrderDao.getUnPayOrderByOpenid(openid);
	}
	
	public FarmOrder getById(long id) {
		return farmOrderDao.getById(id);
	}
}
