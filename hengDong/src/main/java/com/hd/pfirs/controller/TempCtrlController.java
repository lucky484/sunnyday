package com.hd.pfirs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hd.pfirs.model.FaceWebModel;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.ResponseBean;
import com.hd.pfirs.model.TempCtrl;
import com.hd.pfirs.service.TempCtrlService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author curry.su
 *
 */
@Controller
@RequestMapping("/TempCtrlController")
public class TempCtrlController {

	@Value("${push_face_post_url}")
	private String pushUrl;
	
	private Logger logger = Logger.getLogger(AnalyzingController.class);
	
	@Autowired
	private TempCtrlService tempCtrlService;
	
	/**
	 * 导航栏：指向临时辑控信息管理
	 * @return
	 */
	@RequestMapping(value = "getTempCtrl")
	public String getTempCtrl(HttpServletRequest request) {
		return "tempCtrl";
	}
	/**
	 * 初始化查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryTempCtrl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryTempCtrl(HttpServletRequest request) {
		logger.info("初始化");
		Map<String, Object> map = new HashMap<String, Object>();
		List<TempCtrl> list = new ArrayList<TempCtrl>();
		
		String idCardName = request.getParameter("IdCardName");
		String sex = request.getParameter("Sex");
		String idCardNo = request.getParameter("IdCardNo");
		int fys = Integer.valueOf(request.getParameter("fys"));
		
		int count = tempCtrlService.getCountTepCtrl(idCardName, sex, idCardNo);
		list = tempCtrlService.queryTempCtrl(idCardName, sex, idCardNo, 1 ,fys);
		
		for(TempCtrl tempCtrl:list){
			BASE64Encoder encoder = new BASE64Encoder();
			String image = encoder.encode(tempCtrl.getPic());
			tempCtrl.setPicStr(image);
		}
	
		int pages = 0;
		if (count % fys == 0) {
			pages = count / fys;
		} else {
			pages = count / fys + 1;
		}
		/**
		 * 将身份证号吗和采集地点返回前台，保证分页请求的数据和表单请求的数据一致
		 */
		map.put("fys", fys);
		map.put("count", count);
		map.put("pages", pages);
		map.put("list",list);
		return map;
	}
	/**
	 * 辑控新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addTempCtrl", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addTempCtrl(HttpServletRequest request) {
		logger.info("here：辑控新增");
		/**
		 * 得到上传照片
		 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile upLoadFile = multipartRequest.getFile("pic");
		List<TempCtrl> list = new ArrayList<TempCtrl>();
		TempCtrl tempCtrl = new TempCtrl();
		String idCardName = request.getParameter("idCardName");
		String sex = request.getParameter("sex");
		String idCardNo = request.getParameter("idCardNo");
		String remark = request.getParameter("remark");
		
		String queryIdCardName = request.getParameter("hiddenIdCardName");
		String querySex = request.getParameter("hiddenSex");
		String queryIdCardNo = request.getParameter("hiddenIdCardNo");
		
		request.setAttribute("queryIdCardName", queryIdCardName);
		request.setAttribute("querySex", querySex);
		request.setAttribute("queryIdCardNo", queryIdCardNo);
		
		String message = "";
		try {
			/**
			 * 取图片流
			 */
			InputStream input = upLoadFile.getInputStream();
			int size = (int) upLoadFile.getSize();
			byte pic[] = new byte[size];
			input.read(pic);
			
			tempCtrl.setIdCardNo(idCardNo);
			tempCtrl.setName(idCardName);
			tempCtrl.setSex(sex);
			tempCtrl.setRemarkDesc(remark);
			tempCtrl.setPic(pic);
			tempCtrl.setTempCompID(tempCtrlService.getTempCompID());
			/**
			 * 保存信息
			 */
			tempCtrlService.insertTempCtrl(tempCtrl);
		}catch(Exception e){
			/**
			 * 保存失败
			 */
			logger.error(e);
			return new ModelAndView("tempCtrl", "message","激活失败");
			
		}
		/**
		 * 注册
		 */
		ResponseBean responseBean = AddTempCtrlInfoModel(tempCtrl.getTempCompID(), "101", tempCtrl.getPic(),tempCtrl.getIdCardNo() ,tempCtrl.getName(), tempCtrl.getSex(), tempCtrl.getRemarkDesc());
		/**
		 * 判断是否注册成功
		 */
		if(responseBean==null){
			tempCtrlService.updateStatus("-1", tempCtrl.getTempCompID());
			return new ModelAndView("tempCtrl", "message","激活失败");
		}
		if("0".equals(responseBean.getState())){
			tempCtrlService.updateStatus("2", tempCtrl.getTempCompID());
			return new ModelAndView("tempCtrl");
		}else{											
			tempCtrlService.updateStatus("-1", tempCtrl.getTempCompID());
			return new ModelAndView("tempCtrl", "message","激活失败");
		}
		
	}
	
	/**
	 * 新增注册
	 * @param tempCompCode
	 * @param compareBaseID
	 * @param pic
	 * @param idCardNo
	 * @param name
	 * @param sex
	 * @param remark
	 * @return
	 */
	public ResponseBean AddTempCtrlInfoModel(long tempCompCode,String compareBaseID,byte[] pic, String idCardNo, String name, String sex,String remark) {
		ResponseBean responseBean = null;
		/**
		 * 建立http通讯
		 */
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(pushUrl + "FaceService/AddTempCtrlInfoModel");
		/**
		 * 设置超时
		 */
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
		request.setConfig(requestConfig);
		/**
		 * 拼装请求参数
		 */
		JSONObject p = new JSONObject();
		String picStr = Base64.encodeBase64String(pic);
		try {
			p.put("TempCompCode", tempCompCode);
			p.put("CompareBaseID", compareBaseID);
			p.put("IDCardNo", idCardNo);
			p.put("Name", name);
			p.put("Pic", picStr);
			p.put("Remark", remark);
			p.put("Sex", sex);
			// p.put("CompareSeconds", 10);
		} catch (JSONException e) {
			logger.error(e);
		}
		try {
			/**
			 * 发送请求
			 */
			request.setEntity(new StringEntity(p.toString()));
			request.setHeader(HTTP.CONTENT_TYPE, "text/json");
			HttpResponse response = client.execute(request);

			/**
			 * 处理返回值
			 */
			String json = EntityUtils.toString(response.getEntity());
			responseBean = (ResponseBean) JSONObject.toBean(JSONObject.fromObject(json), ResponseBean.class);
			logger.info("responseBean:" + responseBean);
		} catch (Exception e) {
			logger.error(e);
		}

		return responseBean;
	}
	
	
	/**
	 * 辑控删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delTempCtrl")
	public ModelAndView delTempCtrl(HttpServletRequest request) {
		logger.info("here：辑控删除");
		
		List<TempCtrl> list = new ArrayList<TempCtrl>();
		
		long tempCompID = Long.valueOf(request.getParameter("tempCompID"));
		String status = request.getParameter("status");
		String queryIdCardName = request.getParameter("IdCardName");
		String querySex = request.getParameter("Sex");
		String queryIdCardNo = request.getParameter("IdCardNo");
		
		request.setAttribute("queryIdCardName", queryIdCardName);
		request.setAttribute("querySex", querySex);
		request.setAttribute("queryIdCardNo", queryIdCardNo);
		if(!"2".equals(status)){
			tempCtrlService.delTempCtrl(tempCompID);
			return new ModelAndView("tempCtrl");
		}
		ResponseBean responseBean = DelTempCtrlInfo(tempCompID);
		/**
		 * 判断删除是否成功
		 */
		if(responseBean==null){
			return new ModelAndView("tempCtrl", "message","删除失败");
		}
		if("0".equals(responseBean.getState())){
			tempCtrlService.delTempCtrl(tempCompID);
			return new ModelAndView("tempCtrl");
		}else{
			return new ModelAndView("tempCtrl", "message","删除失败");
		}
	}
	
	/**
	 * 删除注册
	 * @param tempCompCode
	 * @param compareBaseID
	 * @param pic
	 * @param idCardNo
	 * @param name
	 * @param sex
	 * @param remark
	 * @return
	 */
	public ResponseBean DelTempCtrlInfo(long tempCompCode) {
		ResponseBean responseBean = null;
		/**
		 * 建立http通讯
		 */
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(pushUrl + "FaceService/DelTempCtrlInfo");
		/**
		 * 设置超时
		 */
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
		request.setConfig(requestConfig);
		/**
		 * 拼装请求参数
		 */
		JSONObject p = new JSONObject();
		
		try {
			p.put("TempCompCode", tempCompCode);
		} catch (JSONException e) {
			logger.error(e);
		}
		try {
			/**
			 * 发送请求
			 */
			request.setEntity(new StringEntity(p.toString()));
			request.setHeader(HTTP.CONTENT_TYPE, "text/json");
			HttpResponse response = client.execute(request);

			/**
			 * 处理返回值
			 */
			String json = EntityUtils.toString(response.getEntity());
			responseBean = (ResponseBean) JSONObject.toBean(JSONObject.fromObject(json), ResponseBean.class);
			logger.info("responseBean:" + responseBean);
		} catch (Exception e) {
			logger.error(e);
		}

		return responseBean;
	}

	/**
	 * 分页
	 * @return
	 */
	@RequestMapping(value = "queryTempCtrlData")
	public Map<String,Object> queryTempCtrlData(HttpServletRequest request) {
		logger.info("分页");
		Map<String, Object> map = new HashMap<String, Object>();
		List<TempCtrl> list = new ArrayList<TempCtrl>();
		
		String idCardName = request.getParameter("IdCardName");
		String sex = request.getParameter("Sex");
		String idCardNo = request.getParameter("IdCardNo");
		int page = Integer.valueOf(request.getParameter("page"));
		int fys = Integer.valueOf(request.getParameter("fys"));
		
		
		list = tempCtrlService.queryTempCtrl(idCardName, sex, idCardNo, page ,fys);
		
		for(TempCtrl tempCtrl:list){
			BASE64Encoder encoder = new BASE64Encoder();
			String image = encoder.encode(tempCtrl.getPic());
			tempCtrl.setPicStr(image);
		}
	
		map.put("list",list);
		return map;
	}
}
