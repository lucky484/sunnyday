package com.f2b.sugar.wxlib.ParamesAPI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.f2b.security.action.SpringContextUtils;
import com.f2b.security.business.dao.AccessTokenDao;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.wxlib.menu.Menu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 请求数据通用类
 *
 * @author ivhhs
 * @date 2014.10.16
 */
@Component
public class WeixinUtil {

	private final static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 发起https请求并获取结果
	 *
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String request, String RequestMethod, String output) {
		// System.out.println("################################httpRequest#####################################");
		System.out.println("###URL:" + request);

		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if (output != null) {
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			// 流处理
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			// 关闭连接、释放资源
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();

			// System.out.println("===================\n"+buffer.toString());

			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	// 获取access_token的接口地址（GET）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 *
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String secret) {
		AccessToken accessToken = new AccessToken();
//		AccessTokenDao accessTokenDao = (AccessTokenDao) SpringContextUtils.getBean("accessTokenDao");
//		com.f2b.security.domain.AccessToken token = accessTokenDao.findModel(1L);
//		if (token != null && token.getUpdateTime() != null
//				&& new Date().getTime() - token.getUpdateTime().getTime() < 7000000) {
//			logger.info("不重新申请。上次Token时间点[{}],当前时间点[{}]",
//					DateTimeUtils.formatDateToStringWithTime(token.getUpdateTime()),
//					DateTimeUtils.formatDateToStringWithTime(new Date()));
//			logger.info("AccessToken:[{}]\r\n", token.getToken());
//			accessToken.setExpiresIn(token.getExpiresIn());
//			accessToken.setToken(token.getToken());
//			accessToken.setUpdateTime(token.getUpdateTime());
//			return accessToken;
//		} else {
			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", secret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					accessToken.setToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
					accessToken.setUpdateTime(new Date());
//					token = new com.f2b.security.domain.AccessToken();
//					// 将token存到数据库，为了和其他项目共用
//					token.setExpiresIn(jsonObject.getInt("expires_in"));
//					token.setToken(jsonObject.getString("access_token"));
//					token.setUpdateTime(new Date());
//					token.setTokenId(1L);
//					accessTokenDao.update(token);
					System.out.println("获取token成功:" + jsonObject.getString("access_token") + "————"
							+ jsonObject.getInt("expires_in"));
				} catch (Exception e) {
					accessToken = null;
					// 获取token失败
					String error = String.format("获取token失败 errcode:%s errmsg:%s", jsonObject.getString("errcode"),
							jsonObject.getString("errmsg"));
					System.out.println(error);
				}
			}
//		}
		return accessToken;
	}

	public static AccessToken getAccessToken(String corpID, String secret, HttpServletRequest request) {
		AccessToken token = (AccessToken) request.getSession().getServletContext().getAttribute("we_cht_access_token");
		if (token != null && token.getUpdateTime() != null
				&& new Date().getTime() - token.getUpdateTime().getTime() < 7000000) {
			logger.info("不重新申请。上次Token时间点[{}],当前时间点[{}]",
					DateTimeUtils.formatDateToStringWithTime(token.getUpdateTime()),
					DateTimeUtils.formatDateToStringWithTime(new Date()));
			logger.info("AccessToken:[{}]\r\n", token.getToken());
			return token;
		}
		logger.info("重新申请Token,当前时间点[{}]", DateTimeUtils.formatDateToStringWithTime(new Date()));
		token = getAccessTokenBase(corpID, secret);
		request.getSession().getServletContext().setAttribute("we_cht_access_token", token);
		return token;
	}

	public static String getJsapiTicket(String token, HttpServletRequest request) {

		String ticket = (String) request.getSession().getServletContext().getAttribute("ticket");
		Date updateTime = (Date) request.getSession().getServletContext().getAttribute("ticket_update_time");
		System.out.println("ticket:" + ticket);
		System.out.println("updateTime:" + updateTime);
		if (ticket != null && updateTime != null && new Date().getTime() - updateTime.getTime() < 7000000) {
			return ticket;
		}

		System.out.println("########################################################################");

		String token_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"
				.replace("ACCESS_TOKEN", token);
		JSONObject result = WeixinUtil.httpRequest(token_url, "GET", "");
		System.out.println("json:" + result);
		String jsapi_ticket = result.getString("ticket");
		request.getSession().getServletContext().setAttribute("ticket", jsapi_ticket);
		request.getSession().getServletContext().setAttribute("ticket_update_time", new Date());

		return jsapi_ticket;
	}

	/**
	 * 获取access_token
	 *
	 * @return
	 */
	public static AccessToken getAccessTokenBase(String corpID, String secret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", corpID).replace("APPSECRET", secret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				accessToken.setUpdateTime(new Date());
				logger.info("获取token成功:[{}]————[{}]\r\n", jsonObject.getString("access_token"),
						jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				e.printStackTrace();
				accessToken = null;
				// 获取token失败
				String error = String.format("获取token失败 errcode:%s errmsg:%s", jsonObject.getString("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return accessToken;
	}

	public static String get_user_info = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static String getUserNickName(String token, String openId) {
		String requestUrl = get_user_info.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			return Base64.getBase64(jsonObject.getString("nickname"));
		} else {
			return Base64.getBase64("无昵称(未关注)");
		}
	}

	// 菜单创建（POST）
	public static String menu_create_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=1";

	/**
	 * 创建菜单
	 *
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				String error = String.format("创建菜单失败 errcode:%s errmsg:%s", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println(error);
			}
		}

		return result;
	}

	public static String URLEncoder(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 *
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	public static String getFileEndWitsh(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType))
			fileEndWitsh = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileEndWitsh = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileEndWitsh = ".amr";
		else if ("video/mp4".equals(contentType))
			fileEndWitsh = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileEndWitsh = ".mp4";
		return fileEndWitsh;
	}

	/**
	 * 数据提交与请求通用方法
	 *
	 * @param access_token
	 *            凭证
	 * @param RequestMt
	 *            请求方式
	 * @param RequestURL
	 *            请求地址
	 * @param outstr
	 *            提交json数据
	 */
	public static int PostMessage(String access_token, String RequestMt, String RequestURL, String outstr) {
		int result = 0;
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = WeixinUtil.httpRequest(RequestURL, RequestMt, outstr);
		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				result = jsonobject.getInt("errcode");
				String error = String.format("操作失败 errcode:%s errmsg:%s", jsonobject.getInt("errcode"),
						jsonobject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return result;
	}

	public static String PostMessage2(String access_token, String RequestMt, String RequestURL, String outstr) {
		String result = null;
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = WeixinUtil.httpRequest(RequestURL, RequestMt, outstr);
		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				String error = String.format("操作失败 errcode:%s errmsg:%s", jsonobject.getInt("errcode"),
						jsonobject.getString("errmsg"));
				System.out.println(jsonobject);
			} else {
				result = jsonobject.toString();
			}
		}
		return result;
	}

	public static JSONObject SubmitMessage(String access_token, String RequestMt, String RequestURL, String PostData) {

		// logger.info("执行操作：[{}]", RequestURL);
		// logger.info("提交数据：[{}]", PostData);

		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);

		JSONObject jsonobject = WeixinUtil.httpRequest(RequestURL, RequestMt, PostData);
		if (jsonobject != null) {
			// logger.info("提交微信服务器返回信息为:[{}]", jsonobject.toString());
			if (0 != jsonobject.getInt("errcode")) {
				String error = String.format("操作失败 errcode:%s errmsg:%s", jsonobject.getInt("errcode"),
						jsonobject.getString("errmsg"));
				// logger.error(error);
			}
		} else {
			// logger.info("同步微信服务器无反馈，可能是网络异常！");
		}

		return jsonobject;
	}

	public static String PostMessage3(String access_token, String RequestMt, String RequestURL, String PostData) {

		String result = "";
		JSONObject jsonobject = SubmitMessage(access_token, RequestMt, RequestURL, PostData);

		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				return jsonobject.getString("errmsg");
			} else {
				JSONArray jsonArray = JSONArray.fromObject(jsonobject.get("department"));
				for (int a = 0; a < jsonArray.size(); a++) {
					if (a == jsonArray.size() - 1) {
						String[] id = jsonArray.get(a).toString().split(",");
						result = result + id[0].split(":")[1];
					} else {
						String[] id = jsonArray.get(a).toString().split(",");
						result = result + id[0].split(":")[1] + ",";
					}
				}
				return result;
			}
		} else {
			return null;
		}
	}

	public static String PostMessage4(String access_token, String RequestMt, String RequestURL, String PostData) {

		JSONObject jsonobject = SubmitMessage(access_token, RequestMt, RequestURL, PostData);

		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				return jsonobject.getString("errmsg");
			} else {
				JSONArray jsonArray = JSONArray.fromObject(jsonobject.get("userlist"));
				return jsonArray.toString();
			}
		} else {
			return null;
		}
	}

}
