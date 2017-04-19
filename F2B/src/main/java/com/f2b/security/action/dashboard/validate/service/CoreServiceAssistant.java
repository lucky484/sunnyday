package com.f2b.security.action.dashboard.validate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b.security.business.dao.FarmUserDao;
import com.f2b.security.domain.FarmUser;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;
import com.f2b.sugar.wxlib.msg.MessageUtil;
import com.f2b.sugar.wxlib.msg.Resp.Article;
import com.f2b.sugar.wxlib.msg.Resp.NewsMessage;
import com.f2b.sugar.wxlib.msg.Resp.TextMessage;


/**
 * 核心Service类
 */
@SuppressWarnings("unused")
@Service("coreServiceAssistant")
public class CoreServiceAssistant {

	private static final Logger logger = LoggerFactory.getLogger(CoreServiceAssistant.class);

	@Autowired
	private FarmUserDao farmUserDao;
	
	/**
	 * 处理微信发来的请求
	 */
	public String processRequest2(HttpServletRequest request, String receiveMsg) {

		// xml格式的消息数据
		String respXml = null;

		// 默认返回的文本消息内容
		String respContent = "未知消息类型";

		try {
			Map<String, String> requestMap = MessageUtil.parseXml(receiveMsg);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");
			String content = requestMap.get("Content");

			logger.info("接收到来自[{}]的[{}]类型的消息,内容为:[{}]", fromUserName, msgType, content);

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				logger.info("eventType:[{}]", eventType);
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					logger.info("关注开始");
					logger.info("toUserName:" + toUserName);
                    AccessToken token = WeixinUtil.getAccessTokenBase(ParamesAPI.appId, ParamesAPI.secret);
					textMessage.setContent("秋润元旦如期而至，好享吃迎新年庆元旦！新年好果新气象！！"
							+ "\n\n亲~亲~亲~：阿克苏庆元旦迎接新的一年，祝您在新你一年里有一个新目标。为了这个新目标去奋斗，好享吃祝您身体健康心想事成。多吃苹果多团聚，吃出健康好身体。"
							+ "\n\nPS:您已关注公众号，已收到抵扣的5元红包，下单还有5元红包相送哦！订阅公众号看小果为您精心烹饪的心灵鸡汤吧！下单成功之后还有随机红包大派送哇！（仅限8.5公斤/箱）绝对超值不容错过！");
					respXml = MessageUtil.textMessageToXml(textMessage);
//					List<FarmUser> list = farmUserDao.getByOpenId(fromUserName);
//					if (!list.isEmpty()) {
//						FarmUser user = list.get(0);
//						user.setStatus("1");
//						farmUserDao.update(user);
//					}
					return respXml;
				}
				// 自定义菜单
				else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
					// 处理菜单点击事件
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					logger.info("eventKey:[{}]", eventKey);
					if (eventKey == null || eventKey.endsWith("default_click_history")) {
						String sendUrl = String.format("%s/wap/getPushWxNewsHistory.action?wxUserName=%s", UrlHelp.getUrlPathWithContextNoPort(request), fromUserName);
						Article article = new Article();
						article.setTitle("点击查看历史消息记录");
						article.setUrl(sendUrl);
						// 整合图文
						List<Article> list = new ArrayList<Article>();
						list.add(article);
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(System.currentTimeMillis());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(1);
						newsMessage.setArticles(list);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
						logger.info(respXml);
						return respXml;
					} else {
                        System.out.println("处理button事件");
                    }
				} else {
					respContent = "不支持的消息类型";
				}
			} else {
				respContent = "不支持的消息类型";
			}
			// ################ 设置文本消息的内容 ################
			textMessage.setContent(respContent);
			// ################ 将文本消息对象转换成xml ################
			respXml = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(String.format("返回值打印结果respXml:[%s]", respXml));
		return respXml;
	}
}
