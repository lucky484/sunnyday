package com.f2b.sugar.tools;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * 该类主要用于发起HTTPS类型的GET和POST请求
 * Author: ZHANG-JIAN-PING  Time: 14-2-26 10:15.
 */
public class HttpsClient {

	private static final Logger logger = LoggerFactory.getLogger(HttpsClient.class);

	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr     提交的数据
	 * @return JSONObject，返回的数据用JSON封装成键值对
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

		logger.debug("开始访问'{}'，method是'{}'并且数据是'{}'.", requestUrl, requestMethod, outputStr);

		JSONObject respJsonData = null;
		StringBuilder respStrData = new StringBuilder();

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new SimpleTrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			logger.debug("成功连接到服务器。");

			if (HTTP_METHOD_GET.equalsIgnoreCase(requestMethod)) {
				logger.debug("这是一个GET请求。");
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				logger.debug("貌似有一些数据需要提交到服务器。");
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes(CHARACTER_ENCODING_UTF8));
				outputStream.close();
				logger.debug("成功提交数据到服务器。");
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARACTER_ENCODING_UTF8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str;
			while ((str = bufferedReader.readLine()) != null) {
				respStrData.append(str);
			}

			logger.debug("成功从服务器获取返回数据: {}", respStrData.toString());

			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			httpUrlConn.disconnect();

			logger.debug("成功关闭流。");

			respJsonData = JSONObject.fromObject(respStrData.toString());

			logger.debug("成功根据返回数据创建JSON对象: {}", respJsonData);

		} catch (ConnectException ce) {
			logger.error("连接服务器: {}失败。", requestUrl, ce);
		} catch (Exception e) {
			logger.error("异常信息。", e);
		}

		logger.debug("生成的响应JSON数据是: {}", respJsonData);
		return respJsonData;
	}


	public static String httpRequest2(String requestUrl, String requestMethod, String outputStr) {

		logger.debug("开始访问'{}'，method是'{}'并且数据是'{}'.", requestUrl, requestMethod, outputStr);
		StringBuilder respStrData = new StringBuilder();

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			logger.debug("成功连接到服务器。");

			if (HTTP_METHOD_GET.equalsIgnoreCase(requestMethod)) {
				logger.debug("这是一个GET请求。");
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			outputStr = outputStr == null ? "" : outputStr;
			logger.debug("貌似有一些数据需要提交到服务器。");
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes(CHARACTER_ENCODING_UTF8));
			outputStream.close();
			logger.debug("成功提交数据到服务器。");

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARACTER_ENCODING_UTF8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str;
			while ((str = bufferedReader.readLine()) != null) {
				respStrData.append(str);
			}

			logger.debug("成功从服务器获取返回数据: {}", respStrData.toString());

			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			httpUrlConn.disconnect();

			logger.debug("成功关闭流。");
			logger.debug("成功根据返回数据: {}", respStrData.toString());

		} catch (ConnectException ce) {
			logger.error("连接服务器: {}失败。", requestUrl, ce);
		} catch (Exception e) {
			logger.error("异常信息。", e);
		}

		return respStrData.toString();
	}

	/**
	 * 证书信任管理器（用于https请求）
	 * 这个证书管理器的作用就是让它信任我们指定的证书，这里信任所有证书，不管是否权威机构颁发。
	 * Author: ZHANG-JIAN-PING  Time: 14-2-26 10:08.
	 */
	public static class SimpleTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

}
