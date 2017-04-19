package com.f2b.sugar.wxlib.msg;


import net.sf.json.JSONArray;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;
import com.f2b.sugar.wxlib.msg.Resp.Article;

/**
 * 发送消息类
 *
 * @author ivhhs
 * @date 2014.10.16
 */
public class Message {
	// 发送接口
	public static String POST_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

	/**
	 * text消息
	 *
	 * @param touser  UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag   TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param content 消息内容
	 */
	public static String sendTextMsgs(String touser, String toparty, String totag, String agentid, String content) {
		String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"text\",\"agentid\": \"%s\",\"text\": {\"content\": \"%s\"},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid, content);
	}

	/**
	 * text消息
	 *
	 * @param touser  UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag   TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param content 消息内容
	 */
	public static String sendTextMsg(String touser, String toparty, String totag, String agentid, String content) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"text\",\"agentid\": %s,\"text\": {\"content\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid, content);
	}


	/**
	 * image消息
	 *
	 * @param touser   UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty  PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param agentid  企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 */
	public static String sendImageMsg(String touser, String toparty, String agentid, String media_id) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"msgtype\": \"image\",\"agentid\": %s,\"image\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, agentid, media_id);
	}

	/**
	 * voice消息
	 *
	 * @param touser   UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty  PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag    TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid  企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 */
	public static String sendVoiceMsg(String touser, String toparty, String totag, String agentid, String media_id) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"voice\",\"agentid\": %s,\"voice\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid, media_id);
	}

	/**
	 * video消息
	 *
	 * @param touser      UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty     PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag       TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid     企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id    媒体资源文件ID
	 * @param title       视频消息的标题
	 * @param description 视频消息的描述
	 */
	public static String sendVideoMsg(String touser, String toparty, String totag, String agentid, String media_id, String title, String description) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"video\",\"agentid\": %s,\" video\": {\"media_id\": %s,\"title\": %s,\"description\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid, media_id, title, description);
	}

	/**
	 * file消息
	 *
	 * @param touser   UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty  PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag    TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid  企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 */
	public static String sendFileMsg(String touser, String toparty, String totag, String agentid, String media_id) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"file\",\"agentid\": %s,\"file\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid, media_id);
	}

	/**
	 * news消息
	 *
	 * @param touser       UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty      PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag        TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid      企业应用的id，整型。可在应用的设置页面查看
	 * @param articlesList 图文集合
	 */
	public static String sendNewsMsg(String touser, String toparty, String totag, String agentid, String articlesList) {
		String postData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"news\",\"agentid\": \"%s\",\"news\": {\"articles\":%s}}";
		return String.format(postData, touser, toparty, totag, agentid, articlesList);
	}

	/**
	 * mpnews消息
	 *
	 * @param touser       UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送———— "touser": "UserID1|UserID2|UserID3"
	 * @param toparty      PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag        TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param agentid      企业应用的id，整型。可在应用的设置页面查看
	 * @param articlesList mpnews集合
	 */
	public static String sendMpNewsMsg(String touser, String toparty, String totag, String agentid, String articlesList) {
		String postData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"mpnews\",\"agentid\": %s,\"mpnews\": {\"articles\":%s}\"safe\":\"0\"}";
		return String.format(postData, touser, toparty, totag, agentid, articlesList);
	}

	// 示例
	public static void main(String[] args) {
		mainFunc();

	}

	private static void mainFunc() {
		/**
		 * news示例
		 * */
		// 调取凭证
		String access_token = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret).getToken();

		// 新建图文
		Article article1 = new Article();
		article1.setTitle("news消息测试-1");
		article1.setDescription("");
		article1.setPicurl("http://211.149.144.205:6080/GEHC/pages/GEHC/images/logo.png");

		String red_url = URLEncoder.encode("http://211.149.144.205/editor.jsp");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxac9f1336104f190a&redirect_uri="+red_url+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

		article1.setUrl(url);
		Article article2 = new Article();
		article2.setTitle("news消息测试-2");
		article2.setDescription("");
		article2.setPicurl("http://211.149.144.205:6080/GEHC/pages/GEHC/images/logo.png");

		String code = "d77816ba6b267f200418c8691b244525";
		article2.setUrl("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+access_token+"&code="+code+"&agentid=2");
		// 整合图文
		List<Article> list = new ArrayList<Article>();
		list.add(article1);
		list.add(article2);
		// 图文转json
		String articlesList = JSONArray.fromObject(list).toString();
		// Post的数据
		String PostData = sendNewsMsg("@all", "", "", "2", articlesList);
//        String PostData = sendTextMsgs("chjay", "", "", "2", "11月19日测试");
		int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);
		// 打印结果
		if (0 == result) {
			System.out.println("操作成功");
		} else {
			System.out.println("操作失败");
		}
	}
}
