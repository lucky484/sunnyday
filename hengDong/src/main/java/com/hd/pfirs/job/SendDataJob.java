package com.hd.pfirs.job;

import java.util.Date;

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
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.JobModelDao;
import com.hd.pfirs.model.JobModel;
import com.hd.pfirs.model.ResponseBean;
import com.hd.pfirs.service.FaceInfoService;
import com.hd.pfirs.service.IdCardInfoService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 
 * @author curry.su
 *
 */
@Service("sendDataJob")
public class SendDataJob {

	private Logger logger = Logger.getLogger(SendDataJob.class);
	@Autowired
	private JobModelDao jobModelDao;

	@Autowired
	private IdCardInfoService idCardInfoService;

	@Autowired
	private FaceInfoService faceInfoService;

	@Value("${push_ctrlBaseId}")
	private String push_ctrlBaseId;
	
	@Value("${push_face_post_url}")
	private String pushUrl;

	public void sendData() {

		/**
		 * 轮循得到最新数据
		 */
		JobModel jobModel = jobModelDao.getJobModel();
		/**
		 * 如果有最新数据，执行推送服务
		 */
		if (jobModel != null) {
			logger.info("start service!");
			ResponseBean responseBean = null;
			/**
			 * 获得采集信息表的主键
			 */
			Long id = jobModel.getTabId();
			try {
				/**
				 * flag 为1代表推送人像数据
				 */
				if ("1".equals(jobModel.getFlag())) {
					logger.info("Start FaceInfo Service:faceCode=" + jobModel.getCode());
					/**
					 * 首先更新状态为正在发送,更新发送时间，接收时间为空
					 */
					faceInfoService.updateFaceInfo(id, "1",new Date(),null);
				
					/**
					 * 如果该条数据是全身像，发送islast标志
					 */
					if ("0".equals(jobModel.getPicType())) {

						/**
						 * 调用接口，如果超时responseBean为空，如果为空，那么更新状态为发送失败
						 */
						responseBean = SendFaceInfo(jobModel.getPhoto(), jobModel.getCode(), jobModel.getDeviceCode(),
								jobModel.getCollectTimeStamp(), jobModel.getGroupCode(), "0");
						if (responseBean == null) {
							/**
							 * 打印错误信息,responseBean为null代表程序异常终止，需要重新发送,更新状态时更新接受时间
							 */
							faceInfoService.updateFaceInfo(id, "9",null,new Date());
							logger.info("responseBean: is null!");
							return;
						}
					} else {
//						logger.info("faceCode:" + jobModel.getCode() + " is person photo");
//						/**
//						 * 调用接口，如果超时responseBean为空，如果为空，那么更新状态为发送失败
//						 */
//						responseBean = SendFaceInfo(null, jobModel.getCode(), jobModel.getDeviceCode(),
//								jobModel.getCollectTimeStamp(), jobModel.getGroupCode(), "1");
//						if (responseBean == null) {
							/**
							 * 打印错误信息,更新状态时更新接受时间
							 */
							faceInfoService.updateFaceInfo(id, "-1",null,new Date());
							logger.info("this is person image!");
							return;
//						}
//						//return;
					}

					/**
					 * 如果有返回信息，responseBean的状态为0，代表发送成功，更新采集信息表状态为发送成功，否则更新状态为发送失败,更新状态时更新接受时间
					 */
					if ("0".equals(responseBean.getState())) {
						logger.info("success");
						faceInfoService.updateFaceInfo(id, "2",null,new Date());
					} else {
						logger.info(responseBean.getRemark());
						faceInfoService.updateFaceInfo(id, "-1",null,new Date());
					}
				} else {
					/**
					 * 身份证信息推送,更新发送时间，接收时间为空
					 */
					logger.info("Start idCardCode Service:CardCode=" + jobModel.getCode());
					idCardInfoService.updateIdCardInfo(jobModel.getTabId(), "1",new Date(),null);

					responseBean = SendIDCardInfo(jobModel.getPhoto(), jobModel.getCode(), jobModel.getDeviceCode(),
							jobModel.getCollectTimeStamp());
					/**
					 * 如果responseBean不为空，则处理数据，否则做超时处理，更新状态为发送失败
					 */
					if (responseBean != null) {
						/**
						 * 如果返回状态为0则更新发送状态为发送成功
						 */
						if ("0".equals(responseBean.getState())) {
							logger.info("success");
							idCardInfoService.updateIdCardInfo(id, "2",null, new Date());
						} else {
							logger.info(responseBean.getRemark());
							idCardInfoService.updateIdCardInfo(id, "-1",null, new Date());
						}
					} else {
						/**
						 * 超时
						 */
						idCardInfoService.updateIdCardInfo(id, "9",null, new Date());
						logger.info("responseBean: is null!");
					}

				}
			} catch (Exception e) {
				logger.error(e);
			}
		} else {
			logger.info("The database has no data, please wait...");
		}

	}

	public ResponseBean SendIDCardInfo(byte[] idCardPic, String cardCode, String deviceCode, String collectDate) {
		ResponseBean responseBean = null;
		/**
		 * 建立http通讯
		 */
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(pushUrl + "FaceService/SendIDCardInfo");
		/**
		 * 临时测试
		 */
		//HttpPost request = new HttpPost("http://121.42.187.183:5000/SendIDCardInfo");
		/**
		 * 设置超时
		 */
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
		request.setConfig(requestConfig);
		/**
		 * 拼装请求参数
		 */
		JSONObject p = new JSONObject();
		String idCardPicStr = Base64.encodeBase64String(idCardPic);
		try {
			p.put("IDCardPic", idCardPicStr);
			p.put("CardCode", cardCode);
			p.put("DeviceCode", deviceCode);
			p.put("CollectDate", collectDate);
			//p.put("CompareSeconds", 10);
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

	public ResponseBean SendFaceInfo(byte[] facePic, String faceCode, String deviceCode, String collectDate,
			String groupCode, String isLast) {
		ResponseBean responseBean = null;
		/**
		 * 建立连接
		 */
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(pushUrl + "FaceService/SendFaceInfo");
		/**
		 * 设置超时
		 */
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
		request.setConfig(requestConfig);
		/**
		 * 拼装参数
		 */
		JSONObject p = new JSONObject();
		String facePicStr = "";
		if(facePic!=null){
			facePicStr = Base64.encodeBase64String(facePic);
		}
		try {
			p.put("FacePic", facePicStr);
			p.put("FaceCode", faceCode);
			p.put("DeviceCode", deviceCode);
			p.put("CollectDate", collectDate);
			p.put("GroupCode", groupCode);
			p.put("IsLast", isLast);
			p.put("CompareBaseID", push_ctrlBaseId);
			//p.put("CompareSeconds", 10);
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

}
