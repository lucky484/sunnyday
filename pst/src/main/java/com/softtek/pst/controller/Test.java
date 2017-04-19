package com.softtek.pst.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Test {
  
	    public void test() throws ClientProtocolException, IOException{
			/**
			 * 建立http通讯
			 */
	    	HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://172.16.97.84:8080/pst/projectsManagement/website/queryProjectsList.do");
			List<NameValuePair> nvps = new ArrayList <NameValuePair>();
			post.setHeader(HTTP.CONTENT_TYPE, "text/json");
			HttpResponse response = client.execute(post);
			String json = EntityUtils.toString(response.getEntity());
			System.out.println(json);
	    }
}
