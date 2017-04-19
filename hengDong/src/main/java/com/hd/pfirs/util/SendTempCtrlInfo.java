package com.hd.pfirs.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hd.pfirs.model.TempCompInfo;
import com.hd.pfirs.service.TempCompInfotSerivce;

public class SendTempCtrlInfo {
	
	@Autowired
    private TempCompInfotSerivce TempCompInfo;
	
	private String AddTempCtrlInfo() throws ClientProtocolException, IOException{
		List<TempCompInfo> list = TempCompInfo.getTempCompinfo();
		HttpClient client = new DefaultHttpClient();
	    HttpPost request = new HttpPost(
	        "http://127.0.0.1/FaceService/SendIDCardInfo");
	    JSONObject p = new JSONObject();
	    String result = "";
	    if(list != null && list.size()>0){
	    	for(TempCompInfo tempCompInfo : list){
	    		p.put("tempCompCode", tempCompInfo.getPersonCode());
	    		p.put("pic",Base64.encodeBase64String(tempCompInfo.getPic()));
	    		p.put("name",tempCompInfo.getName());
	    		p.put("idCardNo",tempCompInfo.getIdCardNo());
	    		p.put("sex",tempCompInfo.getSex());
	    		p.put("remark",tempCompInfo.getRemarkDesc());
	    		request.setEntity(new StringEntity(p.toString()));
	    		request.setHeader(HTTP.CONTENT_TYPE,"text/json");
	    		HttpResponse response = client.execute(request);
	    		result = EntityUtils.toString(response.getEntity());
	    	}
	    }
		return result;
	}
}
