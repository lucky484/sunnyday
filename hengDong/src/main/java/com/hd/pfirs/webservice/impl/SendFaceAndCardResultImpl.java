package com.hd.pfirs.webservice.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.hd.pfirs.model.CompareResult;
import com.hd.pfirs.service.FaceAndCardResultService;
import com.hd.pfirs.service.FaceComResultService;
import com.hd.pfirs.webservice.SendFaceAndCardResultService;

import net.sf.json.JSONObject;


@WebService(endpointInterface = "com.hd.pfirs.webservice.SendFaceAndCardResultService", serviceName="SendFaceAndCardResultService") 
public class SendFaceAndCardResultImpl implements SendFaceAndCardResultService{
    
	private Logger logger = Logger.getLogger(SendFaceAndCardResultImpl.class);
	@Autowired
	private FaceAndCardResultService faceAndCardResultService;
	
	@Autowired
	private FaceComResultService faceComResultService;
	
	@Override
	public String SendFaceAndCardResult(String cardCode, String deviceCode,
			String faceCode, String groupCode, Float similarity,
			String featureID) {
			int state;
			String remark = "";
			JSONObject p = new JSONObject();
		try {
			faceAndCardResultService.insertFaceAndCardResult(cardCode,deviceCode,faceCode,groupCode,similarity,featureID);
			state = 0;
			remark = "sucess";
		} catch (Exception e) {
			logger.debug("保存失败!");
			state = 1; 
			remark = "fail";
		}
		p.put("state", state);
		p.put("remark", remark);
		String result = p.toString();
		return result;
	}
    
	public String SendFaceCompareResult(String groupCode, String faceCode,
			String deviceCode, String featureID,List<CompareResult> list) {
			JSONObject p = new JSONObject();
			int state;
			String remark = "";
			try {
				faceComResultService.insertFaceComResult(groupCode,faceCode,deviceCode,featureID,list);
				state = 0;
				remark = "success";
			} catch (Exception e) {
				logger.debug("保存失败!");
				state = 1;
				remark = "fail";
			}
			p.put("state", state);
			p.put("remark", remark);
			String result = p.toString();
			return result;
	}    
}
