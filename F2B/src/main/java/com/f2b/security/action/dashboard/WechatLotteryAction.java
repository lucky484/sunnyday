package com.f2b.security.action.dashboard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.action.HttpsRequest;
import com.f2b.security.action.WechatPayUtil;
import com.f2b.security.business.biz.AwardBiz;
import com.f2b.security.business.biz.FarmOrderBiz;
import com.f2b.security.business.biz.ReWardIndexBiz;
import com.f2b.security.business.biz.RecordBiz;
import com.f2b.security.business.biz.ShareRecordBiz;
import com.f2b.security.domain.FarmOrder;
import com.f2b.security.domain.Record;
import com.f2b.security.domain.RewardIndex;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.CommonSugar;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.tools.PropertiesUtil;
import com.f2b.sugar.tools.SignUtil;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WechatLotteryAction {

	private static final Logger logger = LoggerFactory.getLogger(WechatLotteryAction.class);

	private static int idFlagSeed = 1;

	// 25个奖项，轮回
	private int[] array = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 50, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
	// private int[] turnplate = { 5, 10, 50, 100, 500, 1000 };
	@Autowired
	private RecordBiz recordBiz;
	@Autowired
	private ShareRecordBiz shareRecordBiz;

	@Autowired
	private AwardBiz awardBiz;

	@Autowired
	private ReWardIndexBiz reWardIndexBiz;
	@Autowired
	private FarmOrderBiz farmOrderBiz;

	/**
	 * 入口
	 */
	@RequestMapping("/lottery")
	public ModelAndView lottery(HttpServletRequest request, HttpServletResponse response) {
		String orderNo = request.getParameter("orderNo");
		ModelAndView modelAndView = new ModelAndView();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("ip is {}", ip);

		// //部署服务器开启
		String code = request.getParameter("code");
		logger.info("code is {}", code);
		if (StringUtils.isEmpty(code)) {
			String url = null;
			try {
				url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId + "&redirect_uri="
						+ URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) + "/lottery.action?orderNo="
								+ request.getParameter("orderNo"), "utf-8")
						+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			try {
				response.sendRedirect(url);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			// logger.info("进来这人没code，不给他中奖");
			// modelAndView.setViewName("pages/lottery");
			// return modelAndView;
		}

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ParamesAPI.appId + "&secret="
				+ ParamesAPI.secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject result = WeixinUtil.httpRequest(url, "GET", null);

		String openid = "";
		try {
			openid = (String) result.get("openid");
		} catch (Exception e) {
			logger.info("code失效！");
		}
		logger.info("open id is {}", openid);
		if (StringUtils.isEmpty(openid)) {
			logger.info("openid must not null, so quit........");
			modelAndView.addObject("error", "抽奖异常，请确保已关注并下单");
			modelAndView.setViewName("pages/error");
			return modelAndView;
		} else {
			modelAndView.setViewName("pages/lottery");
			modelAndView.addObject("openid", openid);
			// 没有订单号
			if (StringUtils.isEmpty(orderNo)) {
				logger.info("orderNo is null");
				modelAndView.addObject("error", "对不起，请先购买后再来抽奖");
				modelAndView.setViewName("pages/error");
				return modelAndView;
			} else {
				logger.info("orderNo is{}", orderNo);
				// 调用查询订单接口查询这条订单是否支付成功
				FarmOrder order = farmOrderBiz.getOrderByNo(orderNo, openid);
				if (order != null && order.getStatus() == -2) {
					String sign = WechatPayUtil.signForQuery(orderNo);
					String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId + "]]></appid>\n"
							+ "<mch_id><![CDATA[" + ParamesAPI.partner + "]]></mch_id>\n"
							+ "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
							+ "<out_trade_no><![CDATA[" + orderNo + "]]></out_trade_no>\n" + "<sign><![CDATA[" + sign
							+ "]]></sign>\n</xml>";
					String queryResult = null;
					try {
						queryResult = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery",
								params);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 微信端支付成功
					if (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>")) {
						order.setStatus(-1);
						order.setUpdateDate(new Date());
						farmOrderBiz.updateOrder(order);
					} else {
						logger.info("对不起，没有查询到您的订单，请先购买后再来抽奖");
						modelAndView.addObject("error", "对不起，没有查询到您的订单，请先购买后再来抽奖");
						modelAndView.setViewName("pages/error");
						return modelAndView;
					}
				}

				Map<String, String> map = new HashMap<>();
				map.put("openid", openid);
				map.put("orderNo", orderNo);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("lotteryDate", format.format(new Date()));
				List<Record> recordList = recordBiz.findList(map);
				// 购买箱数，也是抽奖数
				if (order == null) {
					logger.warn("order is null");
					modelAndView.addObject("error", "对不起，没有查询到您的订单，请先购买后再来抽奖");
					modelAndView.setViewName("pages/error");
					return modelAndView;
				} else {
					Integer rewardTimes = 0; // 抽奖次数
					rewardTimes = Integer.valueOf(order.getWeight()) - recordList.size();
					modelAndView.addObject("rewardTimes", rewardTimes);
					request.getSession().setAttribute("rewardTimes", rewardTimes);
				}
			}

			// 奖品索引
			Integer index = 0;
			// 总抽奖次数
			Double total = 0.0;
			RewardIndex rewardIndex = reWardIndexBiz.findModel(1L);
			if (rewardIndex != null) {
				index = rewardIndex.getCurrentIndex();
				total = Double.valueOf(rewardIndex.getTotalIndex());
			}
			// 应抽中的奖项
			modelAndView.addObject("reward", array[index++]);
			request.getSession().setAttribute("reward", array[index - 1]);
			if (index >= 25) {
				index = 0;
			}

			// 抽奖次数到200,400,600,800,1000的时候奖品为100元红包
			if (total != 0 && (total % 400.0 == 0 || total % 600.0 == 0 || total % 800.0 == 0 || total % 1000.0 == 0)) {
				modelAndView.addObject("reward", 100);
				request.getSession().setAttribute("reward", 100);
			}
			// 500
			if (total != 0 && total % 500.0 == 0) {
				modelAndView.addObject("reward", 500);
				request.getSession().setAttribute("reward", 500);
			}
			// 1000
			if (total != 0 && total % 995.0 == 0) {
				modelAndView.addObject("reward", 1000);
				request.getSession().setAttribute("reward", 1000);
			}
			total++;
			if (rewardIndex == null) {
				rewardIndex = new RewardIndex();
			}
			rewardIndex.setCurrentIndex(index);
			rewardIndex.setTotalIndex(total.intValue());
			reWardIndexBiz.addOrUpdate(rewardIndex);
			modelAndView.addObject("orderNo", request.getParameter("orderNo"));
			modelAndView.setViewName("pages/lottery");
		}
		logger.info("###################################################\r\n\r\n\r\n");

		/**
		 * 分享
		 */
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));

		return modelAndView;
	}

	/**
	 * 滚动数据
	 */
	@RequestMapping("/getScrollList")
	@ResponseBody
	public JSONObject getScrollList(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<Record> recordList = recordBiz.findRecord();
		List<ShareRecord> shareRecordList = shareRecordBiz.findList();
		if (CollectionUtils.isEmpty(recordList)) {
			jsonObject.put("scrollList", jsonArray);
			return jsonObject;
		}
		for (Record record : recordList) {
			if ("500".equals(record.getAward()) || "1000".equals(record.getAward())) {
				jsonArray
						.add("<span style=\"color:rgb(144,128,108);font-weight:bold;font-size:20px;\">恭喜</span><span style=\"color:red;font-weight:bold;font-size:20px;\">"
								+ Base64.getFromBase64(record.getNickname())
								+ "</span><span style=\"color:rgb(144,128,108);font-weight:bold;font-size:20px;\">获得</span><span style=\"color:red;font-weight:bold;font-size:20px;\">"
								+ record.getAward() + "元现金红包</span>");
			} else {
				jsonArray.add("<span style=\"color:rgb(144,128,108);\">恭喜</span><span style=\"color:red;\">"
						+ Base64.getFromBase64(record.getNickname())
						+ "</span><span style=\"color:rgb(144,128,108);\">获得</span><span style=\"color:red;\">"
						+ record.getAward() + "元现金红包</span>");
			}
		}
		for (ShareRecord record : shareRecordList) {
			jsonArray.add("<span style=\"color:rgb(144,128,108);\">恭喜</span><span style=\"color:red;\">"
					+ Base64.getFromBase64(record.getShareNickName())
					+ "</span><span style=\"color:rgb(144,128,108);\">分享后获得来自</span><span style=\"color:red;\">"
					+ Base64.getFromBase64(record.getNickname())
					+ "</span><span style=\"color:rgb(144,128,108);\">的</span><span style=\"color:red;\">"
					+ record.getNumber() * Integer.valueOf(PropertiesUtil.getValue("per_profit")) + "元分润提成</span>");
		}
		jsonObject.put("scrollList", jsonArray);
		return jsonObject;
	}

	/**
	 * 抽奖操作
	 */
	@RequestMapping("/getLotteryResult")
	@ResponseBody
	public String getLotteryResult(HttpServletRequest request) {
		logger.info("开始抽奖发红包");
		FarmOrder order;
		Integer rewardTimes = (Integer) request.getSession().getAttribute("rewardTimes");
		String orderNo = request.getParameter("orderNo");
		// 没有订单号
		if (StringUtils.isEmpty(orderNo)) {
			logger.info("没有订单号");
			return "-2";
		} else {
			order = farmOrderBiz.getOrderBySku(orderNo);
			logger.info("有订单号，查询订单是否存在");
			// 没下订单
			if (order == null) {
				logger.info("没有查询到订单");
				return "-2";
			}
			/*
			 * else { Date orderDate = order.getCreateDate(); //
			 * 距离下单时间已超过60分钟，不能再抽奖 if ((new Date().getTime() -
			 * orderDate.getTime()) / 1000 / 60 > 60) { logger.info("抽奖时间已过");
			 * return "-3"; } }
			 */
		}
		String reward = request.getParameter("reward");
		logger.info("抽到奖金是{}", reward);
		Integer sessionReward = (Integer) request.getSession().getAttribute("reward");
		logger.info("奖金是{}", sessionReward);
		if (reward == null || !reward.equals(String.valueOf(sessionReward))) {
			logger.info("奖金异常");
			return "-4";
		}
		String openid = order.getOpenId();
		String nickname = order.getNickname();
		logger.info("openid is {},nickname is {}的来抽奖啦", openid, nickname);
		if (rewardTimes <= 0) {
			// logger.info("该用户：{}抽奖次数已用完", openid);
			return "-1";
		}
		Map<String, String> map = new HashMap<>();
		map.put("openid", openid);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		map.put("lotteryDate", format.format(new Date()));
		// List<Record> recordList = recordBiz.findList(map);
		// int total = 0;
		// for (Record r : recordList) {
		// total += Integer.valueOf(r.getAward());
		// }
		// // 微信红包次数有限制，每天最多15次
		// if (recordList.size() >= 15 || total >= 3000) {
		// return "-5";
		// }
		if (StringUtils.isEmpty(openid)) {
			logger.info("openid为空还来抽奖");
			return "-4";
		} else {
			logger.info("该用户：{}未曾抽过奖,执行抽奖", openid);
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			logger.info("ip is {}", ip);
			awardBiz.lottery(openid, nickname, reward, orderNo, ip);
			// 抽奖次数减1
			rewardTimes--;
			request.getSession().setAttribute("rewardTimes", rewardTimes);
			logger.info("该用户：nickname:{},openid:{}中的奖为：{}", nickname, openid, reward);
			return reward;
		}
	}

	@RequestMapping("/getIpAddr")
	@ResponseBody
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 提交人员信息
	 */
	@RequestMapping("/pianziSubmitInformation")
	@ResponseBody
	public ModelAndView pianziSubmitInformation(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "tel", required = false) String phone,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/check");
		String openid = (String) request.getSession().getAttribute("openid");
		logger.info("中奖的openid is {}", openid);
		logger.info("名字为：", name);
		logger.info("电话为：", phone);
		logger.info("地址为：", address);
		logger.info("奖品为：", prizeId);
		logger.info("获奖时间为：", lotteryDate);
		if (StringUtils.isNotBlank(openid)) {
			Map<String, String> map = new HashMap<>();
			map.put("openid", openid);
			map.put("award", prizeId);
			map.put("lotteryDate", lotteryDate);
			List<Record> recordList = recordBiz.findList(map);
			if (CollectionUtils.isEmpty(recordList)) {
				logger.info("没有{}中了{}的记录", openid, prizeId);
			} else {
				Record record = recordList.get(0);
				if (record.getDrawStatus() == 1) {
					logger.info("{}已经领取过奖了", openid);
					return modelAndView;
				} else {
					logger.info("信息正确，开始新增！");
					// record.setLotteryName(name);
					// record.setLotteryPhoneNum(phone);
					// record.setLotteryAddress(address);
					record.setDrawStatus(1);
					recordBiz.addOrUpdate(record);
					logger.info("openid is {},信息提交成功！", openid);
				}
			}
		}

		if (prizeId.equals("1")) {
			modelAndView.addObject("text", "iphone6s");
			logger.info("iphone6s");
		}
		if (prizeId.equals("7")) {
			modelAndView.addObject("text", "iwatch");
			logger.info("iwatch");
		}

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		return modelAndView;
	}

	@RequestMapping("/updateState1")
	@ResponseBody
	public String updateState1() {

		String award = "6";
		String lotteryDate = "2016-03-31 04:55:00";
		String openId = "oTg8AuIt1n7M1VQAOHKnUhhqHaHo";

		Map<String, String> map = new HashMap<>();
		map.put("openid", openId);
		map.put("award", award);
		List<Record> recordList = CommonSugar.getTypedDefault(recordBiz.findList(map), new ArrayList<Record>());
		logger.info("然后根据openId:[{}]和抽奖时间查询:[{}]得到的结果数量为:[{}]", award, lotteryDate, recordList.size());
		if (CollectionUtils.isEmpty(recordList)) {
			return "0";
		}
		Record record = recordList.get(0);
		Integer drawStatus = record.getDrawStatus();
		// recordBiz.payMoney(openId, award);
		return "1";
	}

	/**
	 * 修改抽奖状态为已领取
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public String updateState(@RequestParam(value = "award", required = false) String award,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {

		logger.info("Param award is:[{}]", award);
		logger.info("Param lotteryDate is:[{}]", lotteryDate);

		try {

			if (StringUtils.isEmpty(lotteryDate)) {
				lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
			}
			// ModelAndView modelAndView = new ModelAndView();
			String openid = (String) request.getSession().getAttribute("openid");
			if (StringUtils.isNotBlank(openid)) {
				logger.info("首先根据openId:[{}]和抽奖时间查询:[{}]", award, lotteryDate);
				Map<String, String> map = new HashMap<>();
				map.put("openid", openid);
				map.put("award", award);
				map.put("lotteryDate", lotteryDate);
				List<Record> recordList = CommonSugar.getTypedDefault(recordBiz.findList(map), new ArrayList<Record>());
				logger.info("然后根据openId:[{}]和抽奖时间查询:[{}]得到的结果数量为:[{}]", award, lotteryDate, recordList.size());
				if (CollectionUtils.isEmpty(recordList)) {
					logger.info("没有这个条抽奖记录，你是骗子吧");
					return "0";
				}

				Record record = recordList.get(0);
				Integer drawStatus = record.getDrawStatus();
				if (drawStatus == 0) {

					if (award.equalsIgnoreCase("2") || award.equalsIgnoreCase("4") || award.equalsIgnoreCase("6")) {
						String idFlag = getIdFlag(request);
						logger.info("{} $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",
								idFlag);

						if (StringUtils.isEmpty(openid)) {
							logger.info("{} openid is null, stop......", idFlag);
							return "0";
						} else if (StringUtils.isEmpty(idFlag)) {
							logger.info("{} openid is null, stop......", idFlag);
							return "0";
						} else {
							// 发红包
							logger.info("当前中的奖为：{}", award);
							if (recordBiz.offerPrize(openid, award, idFlag)) {
								logger.info("开始发红包啦，恭喜你获得红包！\n\n\n\n\n\n");
								// recordBiz.payMoney(openid, award);
								// 更新奖品领取状态
								record.setDrawStatus(1);
								recordBiz.addOrUpdate(record);
								return "1";
							} else {
								logger.info("两个领取红包的时间间隔小于10秒!", openid);
								return "-1";
							}
						}
					} else {
						// 更新奖品领取状态
						record.setDrawStatus(1);
						recordBiz.addOrUpdate(record);
						logger.info("非现金红包");
						return "1";
					}
				} else {
					logger.info("该奖品已经被领取了，无法再次领取");
					return "2";
				}
			} else {
				logger.info("openid is{}", openid);
				logger.info("OpenId为空");
			}
		} catch (Exception e) {
			logger.info("error is {}", e);
		}
		return "0";
	}

	/**
	 * 获取抽奖记录接口
	 */
	@RequestMapping("/getPagesList")
	@ResponseBody
	public JSONObject getPagesList(HttpServletRequest request) {
		// ModelAndView modelAndView=new ModelAndView();
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			String openid = (String) request.getSession().getAttribute("openid");
			logger.info("openid is {}，正在获取抽奖记录", openid);
			if (StringUtils.isNotBlank(openid)) {
				// Map<String, String> map = new HashMap<>();
				// map.put("openid", openid);
				// List<Record> recordList = recordBiz.findList(map);
				List<Record> recordList = recordBiz.findRecord(openid);
				if (CollectionUtils.isNotEmpty(recordList)) {
					for (Record record : recordList) {
						if (record.getAward().equals("5")) {
						} else {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("lotteryDate", DateTimeUtils.formatDateToString(record.getLotteryDate()));
							jsonObject.put("award", record.getAward());
							if (record.getAward().equals("1") || record.getAward().equals("7")) {
								if (new Date().getTime() - record.getLotteryDate().getTime() > 24 * 60 * 60 * 1000) {
									if (record.getDrawStatus() == 0) {
										record.setDrawStatus(-1);
										recordBiz.addOrUpdate(record);
										jsonObject.put("drawStatus", -1);
									} else {
										jsonObject.put("drawStatus", record.getDrawStatus());
									}
								} else {
									jsonObject.put("drawStatus", record.getDrawStatus());
								}
							} else {
								jsonObject.put("drawStatus", record.getDrawStatus());
							}
							jsonArray.add(jsonObject);
						}
					}
				}
				result.put("recordList", jsonArray);
			} else {
				logger.info("openId为空，获取抽奖记录为空！");
				result.put("recordList", jsonArray);
				return result;
			}
			// modelAndView.addObject("recordList", jsonArray);
		} catch (Exception e) {
			logger.info("error is {}", e);
		}
		// modelAndView.setViewName("pages/record");
		return result;
	}

	/**
	 * 京东
	 */
	@RequestMapping("/jdRecivePrize")
	public ModelAndView jdRecivePrize(@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		if (StringUtils.isEmpty(lotteryDate)) {
			lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prizeId", prizeId);
		modelAndView.addObject("lotteryDate", lotteryDate);
		modelAndView.addObject("text", "10元京东券");

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		// modelAndView.setViewName("redirect:http://coupon.m.jd.com/coupons/show.action?key=0a9efa4926554ff192fb5c904f39a4df&roleId=2604800&to=sale.jd.com/act/ghko2ryduun.html");
		modelAndView.setViewName("pages/jdRecivePrize");

		return modelAndView;
	}

	/**
	 * 红包
	 */
	@RequestMapping("/hbReceivePrize")
	public ModelAndView hbReceivePrize(@RequestParam(value = "reward", required = false) String reward,
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "openid", required = false) String openid, HttpServletRequest request) {
		// String openid = (String) request.getSession().getAttribute("openid");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("reward", reward);
		modelAndView.addObject("orderNo", orderNo);
		modelAndView.addObject("openid", openid);

		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		modelAndView.setViewName("pages/reward");
		return modelAndView;
	}

	/**
	 * iphone
	 */
	@RequestMapping("/tmdpianzi")
	public ModelAndView tmdpianzi(@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		if (StringUtils.isEmpty(lotteryDate)) {
			lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prizeId", prizeId);
		modelAndView.addObject("lotteryDate", lotteryDate);
		logger.info("prizeId  is  {}", prizeId);

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/tmdpianzi1");

		return modelAndView;
	}

	/**
	 * 分享后iphone
	 */
	@RequestMapping("/tmdpianzi1")
	public ModelAndView tmdpianzi1(@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		if (StringUtils.isEmpty(lotteryDate)) {
			lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prizeId", prizeId);
		modelAndView.addObject("lotteryDate", lotteryDate);
		logger.info("prizeId  is  {}", prizeId);

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/tmdpianzi");

		return modelAndView;
	}

	/**
	 * iwatch
	 */
	@RequestMapping("/pianzigun")
	public ModelAndView pianzigun(@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		if (StringUtils.isEmpty(lotteryDate)) {
			lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prizeId", prizeId);
		modelAndView.addObject("lotteryDate", lotteryDate);
		logger.info("prizeId  is  {}", prizeId);

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/pianzigun");

		return modelAndView;
	}

	/**
	 * 分享后iwatch
	 */
	@RequestMapping("/pianzigun1")
	public ModelAndView pianzigun1(@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		if (StringUtils.isEmpty(lotteryDate)) {
			lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prizeId", prizeId);
		modelAndView.addObject("lotteryDate", lotteryDate);
		logger.info("prizeId  is  {}", prizeId);

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/pianzigun1");

		return modelAndView;
	}

	/**
	 * 红包没立即领取页面
	 */
	@RequestMapping("/hasReceivedhbPrize")
	public ModelAndView hasReceivedhbPrize(@RequestParam(value = "prizeId", required = false) String prizeId,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("award", prizeId);

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		if (prizeId.equals("2")) {
			logger.info("领取2元红包成功");
			modelAndView.addObject("text", "2元红包");
		}
		if (prizeId.equals("4")) {
			logger.info("领取5元红包成功");
			modelAndView.addObject("text", "5元红包");
		}
		if (prizeId.equals("6")) {
			logger.info("领取1元红包成功");
			modelAndView.addObject("text", "1元红包");
		}
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/hasReceivedhbPrize");

		return modelAndView;
	}

	/**
	 * messages
	 */
	@RequestMapping("/pianziWriteMessages")
	public ModelAndView pianziWriteMessages(@RequestParam(value = "prizeId", required = false) String prizeId,
			@RequestParam(value = "lotteryDate", required = false) String lotteryDate, HttpServletRequest request) {
		if (StringUtils.isEmpty(lotteryDate)) {
			lotteryDate = DateTimeUtils.formatDateToStringWithTime(new Date());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prizeId", prizeId);
		modelAndView.addObject("lotteryDate", lotteryDate);

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		if (prizeId.equals("1")) {
			modelAndView.addObject("text", "iphone6s");
		}
		if (prizeId.equals("7")) {
			modelAndView.addObject("text", "iwatch");
		}

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/pianziWriteMessages");

		return modelAndView;
	}

	/**
	 * thanks
	 */
	@RequestMapping("/thanks")
	public ModelAndView thanks(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));

		modelAndView.setViewName("pages/thanks");

		return modelAndView;
	}

	/**
	 * check
	 */
	@RequestMapping("/check")
	public ModelAndView check(@RequestParam(value = "prizeId", required = false) String prizeId,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		if (prizeId.equals("1")) {
			modelAndView.addObject("text", "iphone6s");
		}
		if (prizeId.equals("7")) {
			modelAndView.addObject("text", "iwatch");
		}

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		String nickname = (String) request.getSession().getAttribute("nickname");
		modelAndView.addObject("nickname", Base64.getFromBase64(nickname));

		modelAndView.setViewName("pages/check");

		return modelAndView;
	}

	/**
	 * 用于在日志前面加上唯一的ID.
	 */
	private static String genNewIdFlag() {
		return (idFlagSeed++) + "_";
	}

	private static String getIdFlag(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("id_flag");
	}
}
