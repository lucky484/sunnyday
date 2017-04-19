package com.f2b.security.action;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class HttpsRequest {

	private static final Logger logger = LoggerFactory.getLogger(HttpsRequest.class);

	public interface ResultListener {


		public void onConnectionPoolTimeoutError();

	}

	//表示请求器是否已经做了初始化工作
	private boolean hasInit = false;

	//连接超时时间，默认10秒
	private int socketTimeout = 10000;

	//传输超时时间，默认30秒
	private int connectTimeout = 30000;

	//请求器的配置
	private RequestConfig requestConfig;

	//HTTP请求器
	private CloseableHttpClient httpClient;

	public HttpsRequest() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		init();
	}

	private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

		KeyStore keyStore = KeyStore.getInstance("PKCS12");

//        System.out.println("00000"+Configure.getCertLocalPath());

		String certFilePath = "C:/apache-tomcat-8.5.4-80/webapps/F2B/cert/apiclient_cert.p12";//"/Users/val/Personal/Workspace/IdeaWorkspace1/YsotekProject/WDM_AWARD/apiclient_cert.p12";
		FileInputStream instream = new FileInputStream(new File(certFilePath));//加载本地的证书进行https加密传输
		try {
			keyStore.load(instream, "1311584701".toCharArray());//设置证书密码
		} catch (CertificateException e) {
			e.printStackTrace();
			logger.info("出错了,[{}]", e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.info("出错了,[{}]", e.getMessage());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, "1311584701".toCharArray())
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[]{"TLSv1"},
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();

		//根据默认超时限制初始化requestConfig
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

		hasInit = true;
	}

	/**
	 * 通过Https往API post xml数据
	 *
	 * @param url    API地址
	 * @param xmlObj 要提交的XML数据对象
	 * @return API回包的实际数据
	 * @throws java.io.IOException
	 * @throws java.security.KeyStoreException
	 *
	 * @throws java.security.UnrecoverableKeyException
	 *
	 * @throws java.security.NoSuchAlgorithmException
	 *
	 * @throws java.security.KeyManagementException
	 *
	 */

	public String sendPost(String url, String xmlObj) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

		if (!hasInit) {
			init();
		}

		String result = null;

		HttpPost httpPost = new HttpPost(url);

		//解决XStream对出现双下划线的bug
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

		//将要提交给API的数据对象转换成XML格式数据Post给API
//        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

//        System.out.println("API，POST过去的数据是：");
//        System.out.println(postDataXML);
//        if(postDataXML.startsWith("<string>"))
//        <string></String>
//
//        修改后的数据是


		//得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		//设置请求器的配置
		httpPost.setConfig(requestConfig);

		System.out.println("executing request" + httpPost.getRequestLine());

		try {
			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (ConnectionPoolTimeoutException e) {
			System.out.println("http get throw ConnectionPoolTimeoutException(wait time out)");

		} catch (ConnectTimeoutException e) {
			System.out.println("http get throw ConnectTimeoutException");

		} catch (SocketTimeoutException e) {
			System.out.println("http get throw SocketTimeoutException");

		} catch (Exception e) {
			System.out.println("http get throw Exception");

		} finally {
			httpPost.abort();
		}

		return result;
	}

	/**
	 * 设置连接超时时间
	 *
	 * @param socketTimeout 连接时长，默认10秒
	 */
	public void setSocketTimeout(int socketTimeout) {
		socketTimeout = socketTimeout;
		resetRequestConfig();
	}

	/**
	 * 设置传输超时时间
	 *
	 * @param connectTimeout 传输时长，默认30秒
	 */
	public void setConnectTimeout(int connectTimeout) {
		connectTimeout = connectTimeout;
		resetRequestConfig();
	}

	private void resetRequestConfig() {
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}

	/**
	 * 允许商户自己做更高级更复杂的请求器配置
	 *
	 * @param requestConfig 设置HttpsRequest的请求器配置
	 */
	public void setRequestConfig(RequestConfig requestConfig) {
		requestConfig = requestConfig;
	}
}
