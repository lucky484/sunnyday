package com.softtek.mdm.web.device;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.NewsModel;

@Controller
@RequestMapping(value = "/terminal/news")
public class GetNewsController {

	  @RequestMapping(value="/getNews",method=RequestMethod.POST)
	  @ResponseBody
	  public DeviceResultModel<List<NewsModel>> getNews(){
		  DeviceResultModel<List<NewsModel>> deviceResult = new DeviceResultModel<List<NewsModel>>();
		  String url = "http://news.cctv.com/data/index.json";
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setHeader(HTTP.CONTENT_TYPE, "text/json");
			try {
				HttpResponse response = httpclient.execute(post);
				String json = EntityUtils.toString(response.getEntity());
				JSONObject str = JSONObject.fromObject(json);
				JSONArray arr = JSONArray.fromObject(str.get("rollData"));
				@SuppressWarnings("unchecked")
				List<NewsModel> list = (List<NewsModel>) JSONArray.toCollection(JSONArray.fromObject(arr), NewsModel.class);
				deviceResult.setData(list.subList(1,10));
				deviceResult.setMsg("success");
				deviceResult.setStatus(200);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		  return deviceResult;
	  }
	  
}
