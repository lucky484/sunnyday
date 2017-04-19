/**
 * Project Name:F2B
 * File Name:OrderScheduled.java
 * Package Name:com.f2b.security.business.biz.impl
 * Date:Aug 19, 201610:33:15 AM
 * Copyright (c) 2016,Curry.Su@softtek.com All Rights Reserved
 *
 */

package com.f2b.security.business.biz.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b.security.action.HttpsRequest;
import com.f2b.security.action.WechatPayUtil;
import com.f2b.security.business.dao.FarmOrderDao;
import com.f2b.security.business.dao.ShareRecordDao;
import com.f2b.security.domain.FarmOrder;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;

/**
 * Description: 类描述 date: Aug 19, 2016 10:33:15 AM
 *
 * @author jacob.shen
 * @version 1.0
 * @since JDK 1.8.0
 */
@Service("checkOrderSuccessScheduled")
public class CheckOrderSuccessScheduled {
	private Logger logger = Logger.getLogger(CheckOrderSuccessScheduled.class);

	@Autowired
	private FarmOrderDao farmOrderDao;

	@Autowired
	private ShareRecordDao shareRecordDao;

	/**
	 * 
	 * Description:更新当日订单状态
	 *
	 * @author jacob.shen
	 */
	public void updateStatus() {
		logger.info("-------------定时任务，定期检查未付款的订单状态，看是否已付款(start)-----------------");
		List<FarmOrder> orderList = farmOrderDao.getAllUnPayOrder();
		for (FarmOrder order : orderList) {
			logger.info("当前订单是:" + order.getSku());
			// 主动调接口查询订单状态
			String sign = WechatPayUtil.signForQuery(order.getSku());
			String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId + "]]></appid>\n" + "<mch_id><![CDATA["
					+ ParamesAPI.partner + "]]></mch_id>\n"
					+ "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
					+ "<out_trade_no><![CDATA[" + order.getSku() + "]]></out_trade_no>\n" + "<sign><![CDATA[" + sign
					+ "]]></sign>\n</xml>";
			String queryResult = null;
			try {
				queryResult = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 支付成功,但系统显示未付款
			if (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>")) {
				logger.info("调用微信接口查询到已支付成功,但是数据库中却是未支付状态，更新状态");
				order.setUpdateDate(new Date());
				order.setStatus(-1);
				farmOrderDao.update(order);
				logger.info("开始添加佣金信息");
				List<ShareRecord> list = shareRecordDao.get(order.getOpenId()); // 当前购买者所有的分享人,现在理论只能有一个，最早的一个
				if (!list.isEmpty()) {
					logger.info("以前有分享人，所以拿最早的那个分享人，添加分享记录");
					ShareRecord share = list.get(list.size() - 1); // 只拿首次分享给他的人
					ShareRecord model = new ShareRecord();
					model.setOpenId(order.getOpenId());
					model.setShareOpenId(share.getShareOpenId());
					model.setShareNickName(share.getShareNickName());
					model.setCreateDate(new Date());
					// 记录购买数量
					model.setNumber(Integer.valueOf(order.getWeight()));
					model.setSendRedPack(0);
					model.setNickname(order.getNickname());
					model.setSku(order.getSku());
					shareRecordDao.add(model);
				} else {
					logger.info("以前没有分享记录");
					if (order.getShareOpenId() != null) {
						// 购买者通过他人分享的链接进行购买
						ShareRecord shareRecord = new ShareRecord();
						shareRecord.setOpenId(order.getOpenId());
						// 获取分享人的昵称
						shareRecord.setShareNickName(order.getShareNickname());
						shareRecord.setShareOpenId(order.getShareOpenId());
						shareRecord.setCreateDate(new Date());
						// 记录购买数量
						shareRecord.setNumber(Integer.valueOf(order.getWeight()));
						shareRecord.setSendRedPack(0);
						shareRecord.setNickname(order.getNickname());
						shareRecord.setSku(order.getSku());
						shareRecordDao.add(shareRecord);
					}
				}
			}
		}
		logger.info("-------------定时任务，定期检查未付款的订单状态，看是否已付款(end)-----------------");
	}

	/**
	 * 
	 * Description:得到前一天
	 *
	 * @author curry.su
	 * @param date
	 * @return
	 */
	private Date getPerDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 
	 * Description:检查scheduled执行时间是否超过零点，超过0点2小时以上，表示执行时间还未到第二日，所以更新当日数据，否则，
	 * 更新前一日
	 *
	 * @author curry.su
	 * @param date
	 * @return
	 */
	private Date checkDate(Date date) {

		CheckOrderSuccessScheduled orderScheduled = new CheckOrderSuccessScheduled();
		try {

			String hour = new SimpleDateFormat("HH").format(date);
			if (Integer.valueOf(hour) < 2) {
				return orderScheduled.getPerDay(date);
			} else {
				return date;
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		CheckOrderSuccessScheduled orderScheduled = new CheckOrderSuccessScheduled();
		try {
			Date date = sdf.parse("20160819 23:59:01");
			String hour = new SimpleDateFormat("HH").format(date);
			if (Integer.valueOf(hour) < 2) {
				System.out.println(sdf.format(orderScheduled.getPerDay(date)));
			} else {
				System.out.println(sdf.format(date));
			}
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
