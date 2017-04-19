package com.f2b.security.business.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.action.HttpsRequest;
import com.f2b.security.action.WechatPayUtil;
import com.f2b.security.business.biz.RecordBiz;
import com.f2b.security.business.dao.RecordDao;
import com.f2b.security.domain.Record;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.tools.PropertiesUtil;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;

/**
 * Created by Administrator on 2016/3/24.
 */
@Service("RecordBiz")
public class RecordBizImpl implements RecordBiz {

	private Logger logger = LoggerFactory.getLogger(RecordBizImpl.class);

	@Autowired
	private RecordDao recordDao;

	public static Date lastPaid;

	@Override
	public List<Record> findRecord() {
		return recordDao.findRecord();
	}

	@Override
	public List<Record> findRecord(String openid) {
		return recordDao.findRecord(openid);
	}

	@Override
	@Transactional
	public void payMoney(String openid, Integer award) {
		lastPaid = new Date();

		logger.info("开始发放红包给：" + openid);
		String redPackSendSuccess = null;

		// 确认是否红包,如果是红包,则需要处理.x`x`
		String billNo = genBillNo();
		System.out.println("Bill no:" + billNo);
		redPackSendSuccess = sendRedPack(openid, award, billNo);
		System.out.println("Send red pack result: " + redPackSendSuccess);
		// 记录抽奖信息
		// lotteryDao.saveLotteryRecord(openid, prize, redPackSendSuccess,
		// billNo);
		logger.info("发送红包结束!!!!!!!!!!!\n\n\n\n\n\n\n\n");
	}

	@Override
	@Transactional
	public void payProfit(ShareRecord record) {
		lastPaid = new Date();
		logger.info("开始发放佣金红包给：" + record.getShareOpenId());
		String redPackSendSuccess = null;
		// 确认是否红包,如果是红包,则需要处理.x`x`
		String billNo = genBillNo();
		System.out.println("Bill no:" + billNo);
		redPackSendSuccess = sendRedPackForProfit(record, billNo);
		System.out.println("Send red pack result: " + redPackSendSuccess);
		logger.info("发送红包结束!!!!!!!!!!!\n\n\n\n\n\n\n\n");
	}

	@Override
	@Transactional
	public boolean offerPrize(String idFlag, String openid, String prize) {
		Date now = new Date();
		logger.info("lastPaid is {}", lastPaid);
		if (StringUtils.isEmpty(DateTimeUtils.formatDateToStringWithTime(lastPaid))) {
			logger.info("lastPaid为空！");
			return true;
		}
		logger.debug("{} {} 当前的记录的lastPaid {}", idFlag, openid, lastPaid);
		if ((now.getTime() - lastPaid.getTime() < 10000)) {
			logger.error("{} {}why so quick {} ???", idFlag, openid, prize);
			return false;
		}

		if (!"2.4.6".contains(openid)) {
			return false;
		}
		return true;
	}

	private String genBillNo() {
		Date now = new Date();
		StringBuffer result = new StringBuffer();
		result.append("2345768909");
		result.append(now.getYear() + 1900);
		result.append(now.getMonth() + 1);
		result.append(now.getDate());
		result.append(("" + System.currentTimeMillis()).substring(3));
		return result.toString();
	}

	private String sendRedPack(String openid, Integer prize, String mch_billno) {
		// if (--totalRemainedPrize < 0) {
		// System.out.println("\n\n\n危险,停止红包发送\n\n\n");
		// return null;
		// }

		int totalAmount = 0;
		// if ("2".equalsIgnoreCase(prize)) {
		// totalAmount = "200";
		// } else if ("4".equalsIgnoreCase(prize)) {
		// totalAmount = "500";
		// } else if ("6".equalsIgnoreCase(prize)) {
		// totalAmount = "100";
		// }

		logger.info("为 {} 发放红包为 {}", openid, prize);

		totalAmount = prize * 100;
		// totalAmount = 100; //测试1yuan
		String result = null;
		if (totalAmount == 50000) {
			// 测试，使用1分
			String sign = WechatPayUtil.sign(openid, "20000", mch_billno);
			String params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
					+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
					+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
					+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + openid
					+ "]]></re_openid>\n" + "<total_amount><![CDATA[20000]]></total_amount>\n"
					+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA[好想 好享吃！乐享安全食品不要停]]></wishing>\n"
					+ "<client_ip><![CDATA[" + ParamesAPI.client_ip + "]]></client_ip>\n"
					+ "<act_name><![CDATA[阿克苏苹果圣诞促销]]></act_name>\n" + "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
					+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
			logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");

			try {
				result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
						params);
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
					logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				} else {
					return "false";
				}
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					sign = WechatPayUtil.sign(openid, "10000", mch_billno);
					params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
							+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
							+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
							+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + openid
							+ "]]></re_openid>\n" + "<total_amount><![CDATA[10000]]></total_amount>\n"
							+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA[好想 好享吃！乐享安全食品不要停]]></wishing>\n"
							+ "<client_ip><![CDATA[" + ParamesAPI.client_ip + "]]></client_ip>\n"
							+ "<act_name><![CDATA[阿克苏苹果圣诞促销]]></act_name>\n"
							+ "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
							+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
					logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				} else {
					return "false";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (totalAmount == 100000) {
			String sign = WechatPayUtil.sign(openid, "20000", mch_billno);
			String params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
					+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
					+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
					+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + openid
					+ "]]></re_openid>\n" + "<total_amount><![CDATA[20000]]></total_amount>\n"
					+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA[好想 好享吃！乐享安全食品不要停]]></wishing>\n"
					+ "<client_ip><![CDATA[" + ParamesAPI.client_ip + "]]></client_ip>\n"
					+ "<act_name><![CDATA[阿克苏苹果圣诞促销]]></act_name>\n" + "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
					+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
			logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
			try {
				result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				} else {
					return "false";
				}
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				} else {
					return "false";
				}
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				} else {
					return "false";
				}
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				} else {
					return "false";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sign = WechatPayUtil.sign(openid, String.valueOf(totalAmount), mch_billno);
			String params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
					+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
					+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
					+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + openid
					+ "]]></re_openid>\n" + "<total_amount><![CDATA[" + totalAmount + "]]></total_amount>\n"
					+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA[好想 好享吃！乐享安全食品不要停]]></wishing>\n"
					+ "<client_ip><![CDATA[" + ParamesAPI.client_ip + "]]></client_ip>\n"
					+ "<act_name><![CDATA[阿克苏苹果圣诞促销]]></act_name>\n" + "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
					+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
			logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
			try {
				result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
						params);
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 佣金发放
	 * 
	 * @param openid
	 * @param prize
	 * @param mch_billno
	 * @return
	 */
	private String sendRedPackForProfit(ShareRecord record, String mch_billno) {
		int totalAmount = 0;
		logger.info("为 {} 发放佣金为 {}", record.getShareOpenId(),
				record.getNumber() * Integer.valueOf(PropertiesUtil.getValue("per_profit")));
		totalAmount = record.getNumber() * Integer.valueOf(PropertiesUtil.getValue("per_profit")) * 100;
		String nickname = null;
		if (Base64.getFromBase64(record.getNickname()).length() >= 50) {
			nickname = Base64.getFromBase64(record.getNickname()).substring(0, 50) + "...";
		} else {
			nickname = Base64.getFromBase64(record.getNickname());
		}
		String result = null;
		if (totalAmount == 50000) {
			String sign = WechatPayUtil.signForProfit(record, "20000", mch_billno);
			String params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
					+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
					+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
					+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + record.getShareOpenId()
					+ "]]></re_openid>\n" + "<total_amount><![CDATA[20000]]></total_amount>\n"
					+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA["
					+ nickname + "通过您分享的链接购买了" + record.getNumber()
					+ "箱阿克苏苹果,这是分润提成,感谢您的支持]]></wishing>\n" + "<client_ip><![CDATA[" + ParamesAPI.client_ip
					+ "]]></client_ip>\n" + "<act_name><![CDATA[阿克苏苹果抽奖]]></act_name>\n"
					+ "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
					+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
			logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
			try {
				result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
						params);
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
					logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				} else {
					return "false";
				}
				if (result.contains("<result_code><![CDATA[SUCCESS]]></result_code>")) {
					Thread.sleep(60000);
					sign = WechatPayUtil.signForProfit(record, "10000", mch_billno);
					params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
							+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
							+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
							+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA["
							+ record.getShareOpenId() + "]]></re_openid>\n"
							+ "<total_amount><![CDATA[10000]]></total_amount>\n" + "<total_num><![CDATA[1]]></total_num>\n"
							+ "<wishing><![CDATA[" + nickname + "通过您分享的链接购买了"
							+ record.getNumber() + "箱阿克苏苹果,这是分润提成,感谢您的支持]]></wishing>\n" + "<client_ip><![CDATA["
							+ ParamesAPI.client_ip + "]]></client_ip>\n" + "<act_name><![CDATA[阿克苏苹果抽奖]]></act_name>\n"
							+ "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
							+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
					logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
					result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
					logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				} else {
					return "false";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (totalAmount == 100000) {
			String sign = WechatPayUtil.signForProfit(record, "20000", mch_billno);
			String params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
					+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
					+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
					+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + record.getShareOpenId()
					+ "]]></re_openid>\n" + "<total_amount><![CDATA[20000]]></total_amount>\n"
					+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA["
					+ nickname + "通过您分享的链接购买了" + record.getNumber()
					+ "箱阿克苏苹果,这是分润提成,感谢您的支持]]></wishing>\n" + "<client_ip><![CDATA[" + ParamesAPI.client_ip
					+ "]]></client_ip>\n" + "<act_name><![CDATA[阿克苏苹果抽奖]]></act_name>\n"
					+ "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
					+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
			logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
			try {
				result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
						params);
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
				Thread.sleep(60000);
				new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				Thread.sleep(60000);
				new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				Thread.sleep(60000);
				new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
				Thread.sleep(60000);
				new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sign = WechatPayUtil.signForProfit(record, String.valueOf(totalAmount), mch_billno);
			String params = "<xml>\n" + "<sign><![CDATA[" + sign + "]]></sign>\n" + "<mch_billno><![CDATA[" + mch_billno
					+ "]]></mch_billno>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
					+ "<wxappid><![CDATA[" + ParamesAPI.appId + "]]></wxappid>\n"
					+ "<send_name><![CDATA[秋润健康厨房]]></send_name>\n" + "<re_openid><![CDATA[" + record.getShareOpenId()
					+ "]]></re_openid>\n" + "<total_amount><![CDATA[" + totalAmount + "]]></total_amount>\n"
					+ "<total_num><![CDATA[1]]></total_num>\n" + "<wishing><![CDATA["
					+ nickname + "通过您分享的链接购买了" + record.getNumber()
					+ "箱阿克苏苹果,这是分润提成,感谢您的支持]]></wishing>\n" + "<client_ip><![CDATA[" + ParamesAPI.client_ip
					+ "]]></client_ip>\n" + "<act_name><![CDATA[阿克苏苹果抽奖]]></act_name>\n"
					+ "<remark><![CDATA[阿克苏苹果冰糖心 吃出心情吃出惊喜]]></remark>\n"
					+ "<nonce_str><![CDATA[ibuaiVcKdpRxkhJA]]></nonce_str>\n" + "</xml>";
			logger.info("\n++++++++++++\n" + params + "\n++++++++++++\n");
			try {
				result = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
						params);
				logger.info("\n++++++++++++\n" + result + "\n++++++++++++\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Long totalRecord() {
		return this.totalRecord(null);
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash) {
		return recordDao.totalRecord(queryHash);
	}

	@Override
	public List<Record> findList() {
		return this.findList(0, 0, null);
	}

	@Override
	public List<Record> findList(Map<String, String> queryHash) {
		return this.findList(0, 0, queryHash);
	}

	@Override
	public List<Record> findList(Integer pageNow, Integer pageSize) {
		return this.findList(pageNow, pageSize, null);
	}

	@Override
	public List<Record> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash) {
		return this.findList(pageNow, pageSize, "", queryHash);
	}

	@Override
	public List<Record> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
		return recordDao.findList(pageNow, pageSize, sqlOrder, queryHash);
	}

	@Override
	public Record findModel(Long recordId) {
		return recordDao.findModel(recordId);
	}

	@Override
	@Transactional
	public void addOrUpdate(Record model) {
		if (model.getRecordId() != null && model.getRecordId() > 0) {
			recordDao.update(model);
		} else {
			recordDao.add(model);
		}
	}

	@Override
	@Transactional
	public void delete(Long recordId) {
		recordDao.delete(recordId);
	}

	public List<Record> findByOpenId(String openId, List<String> orderNos) {
		return recordDao.findByOpenId(openId, orderNos);
	}

	public Long findRecordNumByOrderNo(String orderNo) {
		return recordDao.findRecordNumByOrderNo(orderNo);
	}
}
