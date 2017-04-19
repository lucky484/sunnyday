package com.hd.pfirs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.hd.pfirs.model.CollectCountModel;
import com.hd.pfirs.model.FaceWebModel;
import com.hd.pfirs.model.IdCardWebModel;
import com.hd.pfirs.model.IntegratedQueryMessageModel;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.ResponseBean;
import com.hd.pfirs.model.T_QQFW_QGCK_ZP_Model;
import com.hd.pfirs.service.CollectCountLocalService;
import com.hd.pfirs.service.CollectCountService;
import com.hd.pfirs.service.FaceWebModelService;
import com.hd.pfirs.service.IdCardWebService;
import com.hd.pfirs.service.IntegratedQueryMessageService;
import com.hd.pfirs.service.ParamSetService;
import com.hd.pfirs.service.T_QQFW_QGCK_ZP_Service;
import com.hd.pfirs.util.logs.service.LogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author curry.su
 *
 */
@Controller
@RequestMapping("/AnalyzingController")
public class AnalyzingController {

	private Logger logger = Logger.getLogger(AnalyzingController.class);

	@Autowired
	private FaceWebModelService faceWebModelService;

	@Autowired
	private IdCardWebService idCardWebService;

	@Autowired
	private CollectCountService collectCountService;

	@Autowired
	private CollectCountLocalService collectCountLocalService;

	@Autowired
	private IntegratedQueryMessageService IntegratedQueryMessageService;

	@Autowired
	private ParamSetService service;
	@Autowired
	private LogService log;
	@Autowired
	private T_QQFW_QGCK_ZP_Service t_qqfw_qgck_zp_service;

	@Value("${push_face_post_url}")
	private String pushUrl;
	
	private  DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 导航栏：指向采集参数管理
	 * @return
	 */
	@RequestMapping(value = "collectParamManage")
	public String getCollectParamManage() {
		logger.info("goto collectParamManage");
		return "collectParamManage";
	}

	/**
	 * 导航栏：指向研判分析采集次数统计明细
	 * @return
	 */
	@RequestMapping(value = "CollectionCountDetails")
	public String getCollectionCountDetails() {
		logger.info("goto CollectionCount");
		return "CollectionCountDetails";
	}

	/**
	 * 导航栏：指向研判分析采集次数统计
	 * @return
	 */
	@RequestMapping(value = "CollectionCount")
	public String getCollectionCount() {
		logger.info("goto CollectionCount");
		return "CollectionCount";
	}

	/**
	 * 导航栏：指向研判分析身份证对比
	 * @return
	 */
	@RequestMapping(value = "AnalyzingIDCard")
	public String getAnalyzingIDCard(HttpServletRequest request) {
		logger.info("goto AnalyzingIDCard");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("身份证对比模块");
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("collectSite='';idCardNo='';fys=10");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		String collectSite = "";
		String idCardNo = "";
		int fys = 10;
		int count = 0;
		List<IdCardWebModel> listIdCardWeb = null;
		/**
		 * 当身份证不为空时执行不带身份证号码查询，结果集返回listIdCardWeb
		 */
		logger.debug("AnalyzingIDCard 页面表单请求身份证号码为空！");
		count = idCardWebService.getIdCardWebModelCount(collectSite);
		listIdCardWeb = idCardWebService.getIdCardWebModelList(1, collectSite, fys);

		/**
		 * 获得总页数
		 */
		int pages = 0;
		if (count % fys == 0) {
			pages = count / fys;
		} else {
			pages = count / fys + 1;
		}
		/**
		 * 将身份证号吗和采集地点返回前台，保证分页请求的数据和表单请求的数据一致
		 */
		request.setAttribute("flag", "1");
		request.setAttribute("fys", fys);
		request.setAttribute("count", count);
		request.setAttribute("pages", pages);
		request.setAttribute("idCardNo", idCardNo);
		request.setAttribute("collectSite1", collectSite);
		try {
			List<IdCardWebModel> list = new ArrayList<IdCardWebModel>();
			/**
			 * 遍历结果集将图片转码
			 */
			for (IdCardWebModel idCardWebModel : listIdCardWeb) {
				BASE64Encoder encoder = new BASE64Encoder();
				String imgstr = encoder.encode(idCardWebModel.getIdCardPic());
				idCardWebModel.setIdCardPicStr(imgstr);
				list.add(idCardWebModel);
			}
			logger.debug(list);
			request.setAttribute("list", list);
		} catch (Exception e) {
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return "AnalyzingIDCard";
	}

	/**
	 * 导航栏：指向研判分析人脸对比
	 * @return
	 */
	@RequestMapping(value = "AnalyzingFaceinfo")
	public String getAnalyzingFaceinfo() {
		logger.info("goto AnalyzingFaceinfo");
		return "AnalyzingFaceinfo";
	}

	//	/**
	//	 * 导航栏： 指向综合信息查询
	//	 * @return
	//	 */
	//	@RequestMapping(value = "IntegratedQueryMessage")
	//	public String getIntegratedQueryMessage() {
	//		logger.info("goto IntegratedQueryMessage");
	//		return "IntegratedQueryMessage";
	//	}

	/**
	 * 导航栏： 指向设备管理
	 * @return
	 */
	@RequestMapping(value = "EquipmentMonitoring")
	public String getEquipmentMonitoring(HttpServletRequest request) {
		String title = request.getParameter("title");
		if (StringUtils.isBlank(title) || "2".equals(title)) {
			request.setAttribute("e_title", "设备一览");
		} else {
			request.setAttribute("e_title", "设备运行状态监控");
		}
		logger.info("goto EquipmentMonitoring");
		return "EquipmentMonitoring";
	}

	/**
	 * 身份证对比--表单请求
	 * fys 分页控制数
	 * count 返回总行数
	 * pages 总行数/分页控制数得到的总页数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getPhotoCompareMessage", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getPhotoCompareMessage(HttpServletRequest request) {
		logger.info("here： 身份证对比--表单请求");
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("身份证对比模块");

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		String collectSite = request.getParameter("collectSite");
		String idCardNo = request.getParameter("idCardNo");
		int fys = Integer.valueOf(request.getParameter("hiddenfys"));
		int count = 0;
		List<IdCardWebModel> listIdCardWeb = null;
		List<IdCardWebModel> list = null;
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("collectSite=" + collectSite + ";idCardNo=" + idCardNo + ";fys=" + fys);
		try {
			/**
			 * 当身份证不为空时执行不带身份证号码查询，结果集返回listIdCardWeb
			 */
			if (idCardNo == "") {
				logger.debug("AnalyzingIDCard 页面表单请求身份证号码为空！");
				count = idCardWebService.getIdCardWebModelCount(collectSite);
				listIdCardWeb = idCardWebService.getIdCardWebModelList(1, collectSite, fys);
			} else {
				logger.debug("AnalyzingIDCard 页面表单请求身份证号码不为空！");
				count = idCardWebService.getIdCardWebModelCountByidCardNo(idCardNo, collectSite);
				listIdCardWeb = idCardWebService.getIdCardWebModelListByidCardNo(1, idCardNo, collectSite, fys);
			}
			/**
			 * 获得总页数
			 */
			int pages = 0;
			if (count % fys == 0) {
				pages = count / fys;
			} else {
				pages = count / fys + 1;
			}
			/**
			 * 将身份证号吗和采集地点返回前台，保证分页请求的数据和表单请求的数据一致
			 */
			request.setAttribute("flag", "1");
			request.setAttribute("fys", fys);
			request.setAttribute("count", count);
			request.setAttribute("pages", pages);
			request.setAttribute("idCardNo", idCardNo);
			request.setAttribute("collectSite1", collectSite);
			list = new ArrayList<IdCardWebModel>();
			/**
			 * 遍历结果集将图片转码
			 */
			for (IdCardWebModel idCardWebModel : listIdCardWeb) {
				BASE64Encoder encoder = new BASE64Encoder();
				String imgstr = encoder.encode(idCardWebModel.getIdCardPic());
				idCardWebModel.setIdCardPicStr(imgstr);
				list.add(idCardWebModel);
			}
			logger.debug(list);
		} catch (Exception e) {
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return new ModelAndView("AnalyzingIDCard", "list", list);
	}

	/**
	 * 身份证对比查询 ajax异步请求
	 * page 页数
	 * fys 分页控制数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getPageContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPageContent(HttpServletRequest request) {
		logger.info("here： 身份证对比--分页请求");
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("身份证对比模块");

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String collectSite = request.getParameter("collectSite");
		String idCardNo = request.getParameter("idCardNo");
		int page = Integer.valueOf(request.getParameter("page"));
		int fys = Integer.valueOf(request.getParameter("hiddenfys"));
		List<IdCardWebModel> listIdCardWeb = null;
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition(
				"collectSite=" + collectSite + ";idCardNo=" + idCardNo + ";fys=" + fys + ";page=" + page);
		/**
		 * 当身份证不为空时执行不带身份证号码查询，结果集返回listIdCardWeb
		 */
		try {
			if (idCardNo == "") {
				logger.debug("AnalyzingIDCard 页面表单请求身份证号码为空！");
				listIdCardWeb = idCardWebService.getIdCardWebModelList(page, collectSite, fys);
			} else {
				logger.debug("AnalyzingIDCard 页面表单请求身份证号码为空！");
				listIdCardWeb = idCardWebService.getIdCardWebModelListByidCardNo(page, idCardNo, collectSite, fys);
			}
			List<IdCardWebModel> list = new ArrayList<IdCardWebModel>();
			/**
			 * 遍历结果集将图片转码
			 */
			for (IdCardWebModel idCardWebModel : listIdCardWeb) {
				BASE64Encoder encoder = new BASE64Encoder();
				String imgstr = encoder.encode(idCardWebModel.getIdCardPic());
				idCardWebModel.setIdCardPicStr(imgstr);
				list.add(idCardWebModel);
			}
			map.put("list", list);
			logger.debug(list);
		} catch (Exception e) {
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return map;
	}

	/**
	 * 人脸对比查询 --表单请求
	 * dispalycount 页面显示条数
	 * @param request
	 * @return
	 * @author curry.su
	 */
	@RequestMapping(value = "getFaceInfoCompareMessage", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getFaceInfoCompareMessage(HttpServletRequest request) {
		logger.info("here： 照片对比--表单请求");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("照片对比模块");

		request.setAttribute("nullDataFlag", "1");
		/**
		 * 得到上传照片
		 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile upLoadFile = multipartRequest.getFile("upLoadFileName");
		List<FaceWebModel> list = new ArrayList<FaceWebModel>();
		List<FaceWebModel> returnlist = new ArrayList<FaceWebModel>();
		try {
			/**
			 * 取图片流
			 */
			InputStream input = upLoadFile.getInputStream();
			int size = (int) upLoadFile.getSize();
			byte photo[] = new byte[size];
			input.read(photo);
			BASE64Encoder encoder = new BASE64Encoder();
			String imgstr = encoder.encode(photo);
			String collectSite = request.getParameter("collectSite");
			/**
			 * 判断相似度，正常数值时转成float
			 */
			float similarity = 0;
			if (!"".equals(request.getParameter("similarity"))) {
				similarity = Float.valueOf(request.getParameter("similarity"));
			}
			int fys = Integer.valueOf(request.getParameter("fys"));
			int dispalycount = Integer.valueOf(request.getParameter("count"));
			String ctrlBaseId = request.getParameter("ctrlBaseId");

			/**
			 * 得到查询条件
			 */
			operationLogInfo.setOperateCondition("similarity=" + similarity + ";fys=" + fys + ";dispalycount="
					+ dispalycount + ";ctrlBaseId=" + ctrlBaseId + ";collectSite=" + collectSite);
			/**
			 * size 从参数配置表中获取p_ss_paremet 后期实现（未完成）
			 * 调用照片1比N对比接口
			 */
			ResponseBean responseBean = SendComparison(imgstr, ctrlBaseId, similarity, 10);
			//			if(responseBean!=null){
			/**
			 * 如果返回值状态为0，对比服务器成功返回
			 */
			if (responseBean != null) {
				if ("0".equals(responseBean.getState())) {
					/**
					 * 得到1比N数据
					 */
					List<FaceWebModel> faceWebModelList = responseBean.getList();
					/**
					 * 遍历返回值list，如果对比数据的相似度小于前台相似度则不传递到前台
					 */
					for (FaceWebModel faceWebModel : faceWebModelList) {
						/**
						 * 通过对比数据的facecode和前台传递的采集地点查询符合当前采集地点下的数据
						 */
						FaceWebModel model = faceWebModelService.getFaceWebModelByFaceCode(faceWebModel.getPersonID(),
								collectSite);
						
						/**	
						 * 如果model不为空，则得到满足条件数据
						 */
						if (model != null) {
							/**
							 * 添加对比的comparebaseid
							 */
							if(faceWebModel.getCompareBaseID()!=null){
								model.setCompareBaseID(faceWebModel.getCompareBaseID());
							}
							/**
							 * 判断数据的相似度是否满足查询条件
							 */
							if (faceWebModel.getSimilarity() > similarity) {
								/**
								 * 判断人脸比对是否比重
								 */
								ParamSet paramSet = service.getParamSet();
								if (faceWebModel.getSimilarity() > paramSet.getFaceCompAlarmVal()) {
									model.setIsCompareFace("1");
								} else {
									model.setIsCompareFace("0");
								}
								/**
								 * 格式化相似度，转码图片
								 */
								model.setSimilarity((float) Math.round(faceWebModel.getSimilarity() * 100) / 100);
								String collectPicStr = encoder.encode(model.getCollectPic());
								model.setCollectPicStr(collectPicStr);
								list.add(model);
							}

						}
					}
					/**
					 * 根据前台显示条数打回相应数据到前台
					 */
					for (int j = 0; j < dispalycount; j++) {
						if (j >= list.size()) {
							break;
						}
						returnlist.add(list.get(j));
					}
				}
			} else {
				/**
				 * 连接超时报错误页面
				 */
				logger.info("connect timeout");
				operationLogInfo.setErrorCode("1999");
				operationLogInfo.setOperateResult("0");
				/**
				 * 保存日志
				 */
				log.recordOperateLog(operationLogInfo);
				return new ModelAndView("AnalyzingFaceinfo", "list", returnlist);
			}
			/**
			 * 得到总页数和综合数
			 */
			int pages = 0;
			int count = list.size();
			if (count % fys == 0) {
				pages = count / fys;
			} else {
				pages = count / fys + 1;
			}
			request.setAttribute("pages", pages);
			input.close();
		} catch (IOException e) {
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		if (returnlist.size() == 0) {
			request.setAttribute("nullDataFlag", "0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return new ModelAndView("AnalyzingFaceinfo", "list", returnlist);
	}

	/**
	 * 1比N接口
	 * @param photo
	 * @param CompareBaseID
	 * @return
	 */
	public ResponseBean SendComparison(String pic, String compareBaseID, float similarity, int size) {
		logger.info("here：1比N接口");
		List<FaceWebModel> facelist = new ArrayList<FaceWebModel>();
		/**
		 * 请求对比服务器
		 */
		HttpClient client = new DefaultHttpClient();
		// "http://172.16.97.77/FaceService/SendComparisonModel"
		HttpPost request = new HttpPost(pushUrl + "FaceService/SendComparisonModel");
		/**
		 * 设置超时
		 */
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
		request.setConfig(requestConfig);
		JSONObject p = new JSONObject();
		ResponseBean responseBean = null;
		try {
			p.put("Pic", pic);
			p.put("CompareBaseID", compareBaseID);
			p.put("Similarity", similarity);
			p.put("Size", size);

		} catch (JSONException e) {
			logger.error(e);
		}
		try {
			request.setEntity(new StringEntity(p.toString()));
			request.setHeader(HTTP.CONTENT_TYPE, "text/json");
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity());
			logger.debug(json);
			/**
			 * 处理json字符串
			 */
			JSONObject JB = JSONObject.fromObject(json);
			JSONArray JA = JB.getJSONArray("compareResult");
			/**
			 * 得到1比N信息
			 */
			for (int i = 0; i < JA.size(); i++) {
				FaceWebModel faceWebModel = (FaceWebModel) JSONObject.toBean(JA.getJSONObject(i), FaceWebModel.class);
				facelist.add(faceWebModel);
			}
			/**
			 * 得到返回值信息，返回值的数组无法被jsonlib处理，单独处理返回值中的compareResult
			 */
			responseBean = (ResponseBean) JSONObject.toBean(JSONObject.fromObject(json), ResponseBean.class);
			logger.debug(responseBean);
			/**
			 * 如果未返回，返回值状态为2
			 */

			responseBean.setList(facelist);
		} catch (Exception e) {
			logger.error(e);
		}
		return responseBean;
	}

	/**
	 * 身份证对比采集次数
	 * count -- 一共返回多少值
	 * pages -- 一共多少页
	 * flag -- 0:表单提交，1：普通ajax
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getCollectionCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCollectionCountForm(HttpServletRequest request) {
		logger.info("here：身份证对比采集次数统计");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("采集次数统计模块");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String collectSite = request.getParameter("collectSite");
		String idCardNo = request.getParameter("idCardNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int page = Integer.valueOf(request.getParameter("page"));
		String flag = request.getParameter("flag");
		String str = request.getParameter("hiddenfys");
		int fys = Integer.valueOf(str);

		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("collectSite=" + collectSite + ";idCardNo=" + idCardNo + ";startDate="
				+ startDate + ";endDate=" + endDate + ";page=" + page + ";flag=" + flag + ";fys=" + fys);
		int count = 0;
		/**
		 * 0代表表单提交，表单请求查询总数，查询后将flag重新打回页面，下次请求后继续将flag带入
		 */
		try {
			if ("0".equals(flag)) {
				/**
				 * 判断身份证号码是否为空，身份证号码为空时，执行不带身份证号码查询
				 */
				if (idCardNo == "") {
					count = collectCountLocalService.getCollectCountModelCount(collectSite, startDate, endDate);
				} else {
					count = collectCountLocalService.getCollectCountModelCountByidCardNo(idCardNo, collectSite,
							startDate, endDate);
				}
				map.put("count", count);
				int pages = 0;
				if (count % fys == 0) {
					pages = count / fys;
				} else {
					pages = count / fys + 1;
				}
				map.put("pages", pages);
				map.put("flag", "0");
			} else {
				map.put("flag", "1");
			}
			List<CollectCountModel> listCollectCountModel = null;
			/**
			 * 先获得身份信息,身份证为空，全部查询
			 */
			if (idCardNo == "") {
				listCollectCountModel = collectCountLocalService.getCollectCountModellList(page, collectSite, startDate,
						endDate, fys);
			} else {
				listCollectCountModel = collectCountLocalService.getCollectCountModelListByidCardNo(page, idCardNo,
						collectSite, startDate, endDate, fys);
			}
			List<CollectCountModel> list = new ArrayList<CollectCountModel>();
			/**
			 * 比对是否为逃犯，同时得到base64字符串
			 */
			for (CollectCountModel collectCountModel : listCollectCountModel) {
				/**
				 * 如果辑控库id不为空，那么根据CompareBaseID比对到底是哪张表
				 */
				if (collectCountModel.getCompareBaseID() != null) {
					logger.debug(collectCountModel.getIdCardNo() + "\t" + collectCountModel.getCompareBaseID());
					/**
					 * 根据在逃长控零控判断查哪张表,默认为在逃库
					 */
					String colum;
					if ("1".equals(collectCountModel.getCompareBaseID().trim())) {
						collectCountModel.setTableName("T_QB_RY_ZTRYJBXX");
						colum = "ysfzh";
					} else if ("0".equals(collectCountModel.getCompareBaseID().trim())) {
						collectCountModel.setTableName("T_QB_LK_LKBK");
						colum = "bbkrzjhm";
					} else if ("2".equals(collectCountModel.getCompareBaseID().trim())) {
						collectCountModel.setTableName("T_QB_RY_CKRYJBXX");
						colum = "sfzh";
					} else {
						collectCountModel.setTableName("T_QB_RY_ZTRYJBXX");
						colum = "ysfzh";
					}
					/**
					 * 得到该条数据辑控库的条数，正常人idCardInfoCompareResult为0，前台根据idCardInfoCompareResult，tablename判断是那种类型的逃犯
					 */
					int idCardInfoCompareResult = collectCountService.getidCardInfoCompareResult(
							collectCountModel.getIdCardNo(), collectCountModel.getTableName(), colum);
					collectCountModel.setIdCardInfoCompareResult(idCardInfoCompareResult);
				}
				BASE64Encoder encoder = new BASE64Encoder();
				String imgstr = encoder.encode(collectCountModel.getIdCardPic());
				collectCountModel.setIdCardPicStr(imgstr);
				list.add(collectCountModel);
			}
			map.put("idCardNo", idCardNo);
			map.put("collectSite1", collectSite);
			map.put("startDate1", startDate);
			map.put("endDate1", endDate);
			map.put("list", list);
			logger.debug(list);
		} catch (Exception e) {
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return map;
	}

	/**
	 * 根据长控类型转成具体案件
	 * @param str
	 * @return
	 */
	public String getT_QB_RY_CKRYJBXXBj(String str) {
		int index = str.indexOf("1");
		String content;
		switch (index) {
		case 0:
			content = "涉恐人员";
			break;
		case 1:
			content = "涉稳人员";
			break;
		case 2:
			content = "在逃人员";
			break;
		case 3:
			content = "涉毒人员";
			break;
		case 4:
			content = "刑事犯罪";
			break;
		case 5:
			content = "肇事肇案精神病人";
			break;
		case 6:
			content = "重点上访人员";
			break;
		default:
			content = null;
		}
		return content;
	}

	/**
	 * 详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getCollectionCountDetails", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getCollectionCountDetailsForm(HttpServletRequest request) {
		logger.info("here 研判分析--身份证查询明细");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("采集次数统计模块-明细");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String collectSite = request.getParameter("collectSite");
		String idCardNo = request.getParameter("idCardNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String compareBaseID = request.getParameter("compareBaseID");
		request.setAttribute("compareBaseID", compareBaseID);
		String flag = request.getParameter("flag");//0代表正常，1代表在逃人员
		List<CollectCountModel> list = null;
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("collectSite=" + collectSite + ";idCardNo=" + idCardNo + ";startDate="
				+ startDate + ";endDate=" + endDate + ";flag=" + flag);
		//System.out.println("flag="+flag);
		//在逃人员匹配在逃案件
		try {
			if ("1".equals(flag)) {
				
				List<CollectCountModel> typeListjk = collectCountService.getIdCardInfoCompareResultTypeByjk(idCardNo,compareBaseID);
				for (int i = 0; i < typeListjk.size(); i++) {
					if ("T_QB_RY_CKRYJBXX".equals(typeListjk.get(i).getTableName())) {
						typeListjk.get(i).setContent(getT_QB_RY_CKRYJBXXBj(typeListjk.get(i).getContent()));
					}
				}
				request.setAttribute("typeListjk", typeListjk);
				
			}
			request.setAttribute("flag", flag);
			List<CollectCountModel> listCollectCountModel = null;

			//先获得身份详情信息
			listCollectCountModel = collectCountLocalService.getCollectCountModelListByidCardNoDetails(idCardNo,
					collectSite, startDate, endDate,compareBaseID);
			if (listCollectCountModel != null) {
				request.setAttribute("CollectCountModel", listCollectCountModel.get(0));
			} else {
				/**
				 * 显示错误页面
				 */
				logger.error("身份信息为空");
				operationLogInfo.setErrorCode("1999");
				operationLogInfo.setOperateResult("0");
				/**
				 * 保存日志
				 */
				log.recordOperateLog(operationLogInfo);
				return new ModelAndView("CollectionCountDetails", "list", list);
			}
			list = new ArrayList<CollectCountModel>();
			/**
			 * 得到base64字符串
			 */
			for (CollectCountModel collectCountModel : listCollectCountModel) {
				BASE64Encoder encoder = new BASE64Encoder();
				String imgstr = encoder.encode(collectCountModel.getIdCardPic());
				collectCountModel.setIdCardPicStr(imgstr);
				list.add(collectCountModel);
			}
		} catch (Exception e) {
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return new ModelAndView("CollectionCountDetails", "list", list);
	}

	/**
	 * 详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getIntegratedQueryMessage", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getIntegratedQueryMessageForm(HttpServletRequest request) {
		logger.info("goto 身份证接口详情");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("身份证接口详情模块");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * 得到前台参数
		 */
		int flag = Integer.valueOf(request.getParameter("flag"));
		String idCardNo = request.getParameter("idCardNo");
		String cardCode = request.getParameter("cardCode");
		String page = request.getParameter("page");
		String collectSite = request.getParameter("collectSite");
		String comeFrom = request.getParameter("comeFrom");
		String compareBaseID = request.getParameter("compareBaseID");
		request.setAttribute("comeFrom", comeFrom);

		request.setAttribute("flag", flag);

		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("flag=" + flag + ";idCardNo=" + idCardNo + ";cardCode=" + cardCode
				+ ";page=" + page + ";collectSite=" + collectSite + ";comeFrom=" + comeFrom);
				/**
				 * 匹配在逃案件
				 */

		/**
		 * 得到综合信息的身份证信息对象
		 */
		try {
			//CollectCountModel collectCountModel = collectCountLocalService.getCollectCountModel(idCardNo, collectSite, collectTimeStr);
			CollectCountModel collectCountModel = collectCountLocalService.getCollectCountModelByCardCode(cardCode);
			if (collectCountModel != null) {
				BASE64Encoder encoder = new BASE64Encoder();
				String imgstr = encoder.encode(collectCountModel.getIdCardPic());
				collectCountModel.setIdCardPicStr(imgstr);
				//得到身份证信息
				request.setAttribute("collectCountModel", collectCountModel);
			} else {
				/**
				 * 显示错误页面
				 */
				logger.error("身份信息为空");
				operationLogInfo.setErrorCode("1999");
				operationLogInfo.setOperateResult("0");
				/**
				 * 保存日志
				 */
				log.recordOperateLog(operationLogInfo);
				return new ModelAndView("CollectionCountDetails", "list", null);
			}
			/**
			 * 匹配在逃人员案件
			 */
			if (flag == 1) {
				List<CollectCountModel> typeListjk = collectCountService.getIdCardInfoCompareResultTypeByjk(idCardNo,compareBaseID);
				CollectCountModel collectCountModeljk = typeListjk.get(0);
				if (collectCountModeljk != null) {
					if (collectCountModeljk.getIdCardPic() != null) {
						BASE64Encoder encoder = new BASE64Encoder();
						String imgstr = encoder.encode(collectCountModeljk.getIdCardPic());
						collectCountModeljk.setIdCardPicStr(imgstr);
					}
					//得到身份证信息
					request.setAttribute("collectCountModeljk", collectCountModeljk);
				} else {
					/**
					 * 显示错误页面
					 */
					logger.error("未匹配到辑控信息，前后台不一致！");
					operationLogInfo.setErrorCode("1999");
					operationLogInfo.setOperateResult("0");
					/**
					 * 保存日志
					 */
					log.recordOperateLog(operationLogInfo);
					return new ModelAndView("CollectionCountDetails", "list", null);
				}
				/**
				 * 得到案件类型，如果出现多个案件，按照:在逃、长控、临控优先级取最高的案件显示
				 */
				
				/*for (int i = 0; i < typeListjk.size(); i++) {
					if ("T_QB_RY_CKRYJBXX".equals(typeListjk.get(i).getTableName())) {
						typeListjk.get(i).setContent(getT_QB_RY_CKRYJBXXBj(typeListjk.get(i).getContent()));
					}
				}*/
				if ("T_QB_RY_CKRYJBXX".equals(typeListjk.get(0).getTableName())) {
					typeListjk.get(0).setContent(getT_QB_RY_CKRYJBXXBj(typeListjk.get(0).getContent()));
				}
				request.setAttribute("type", typeListjk.get(0));
			}
			/**
			 * 获得人证对比信息为空时，页面传递参数		
			 */
			IntegratedQueryMessageModel integratedQueryMessage = IntegratedQueryMessageService
					.getIntegratedQueryMessageModelByCardCode(cardCode);
			request.setAttribute("isHaveFace", 0);
			/**
			 * 格式化相似度
			 */
			if (integratedQueryMessage != null && !"".equals(integratedQueryMessage.getSimilarity())) {
				integratedQueryMessage
						.setSimilarity((float) Math.round(integratedQueryMessage.getSimilarity() * 100) / 100);
			}
			/**
			 * 如何未查到人证对比数据那么前台显示默认图片
			 */
			if (integratedQueryMessage != null && integratedQueryMessage.getFaceCode() != null) {
				/**
				 * 如果相似度判断不匹配那么人像信息，人脸信息比对不处理
				 */
				ParamSet paramSet = service.getParamSet();

				if (integratedQueryMessage.getSimilarity() > paramSet.getFaceCardCompAlarmVal()) {
					/**
					 * 相似度大于预警值，匹配成功
					 */
					integratedQueryMessage.setFaceAndCodeCompareFlag("1");
				} else {
					integratedQueryMessage.setFaceAndCodeCompareFlag("0");
				}
				/**
				 * 得到人脸图片，一张全身像一张头像一张其他照片
				 */
				List<IntegratedQueryMessageModel> faceList = IntegratedQueryMessageService
						.getIntegratedQueryMessageModelListByFaceCode(integratedQueryMessage.getFaceCode());
				for (int j = 0; j < faceList.size(); j++) {
					BASE64Encoder encoder = new BASE64Encoder();
					String imgstr = encoder.encode(faceList.get(j).getCollectFacePic());
					faceList.get(j).setCollectFacePicStr(imgstr);
				}

				if (faceList != null) {
					//得到人脸采集信息
					request.setAttribute("faceList", faceList);
					if (faceList.get(1).getCollectFacePicStr() != null) {
						//System.out.println(faceList.get(1).getCollectFacePicStr());
						request.setAttribute("collectFacePicStr10", faceList.get(1).getCollectFacePicStr());
					}
					if (faceList.get(0).getFaceCollectTimeStr() != null) {
						request.setAttribute("FaceCollectTimeStr", faceList.get(0).getFaceCollectTimeStr());
					}
					request.setAttribute("isHaveFace", 1);
				}

				/**
				 * 得到人脸比对信息：相似度，逃犯id,人脸编号
				 */
				List<IntegratedQueryMessageModel> compareList = IntegratedQueryMessageService
						.getCompareListByjk(integratedQueryMessage.getFaceCode());

				if (compareList != null) {
					List<IntegratedQueryMessageModel> list = new ArrayList<IntegratedQueryMessageModel>();
					for (int i = 0; i < compareList.size(); i++) {
						/**
						 * 低于预警值的不显示
						 */
						if (paramSet.getFaceCompAlarmVal() <= compareList.get(i).getSimilarity()) {
							if ("T_QB_RY_CKRYJBXX".equals(compareList.get(i).getTableName())) {
								compareList.get(i).setContent(getT_QB_RY_CKRYJBXXBj(compareList.get(i).getContent()));
							}

							BASE64Encoder encoder = new BASE64Encoder();
							if (compareList.get(i).getCollectIdCardPic() != null) {
								String imgstr = encoder.encode(compareList.get(i).getCollectIdCardPic());
								compareList.get(i).setCollectIdCardPicStr(imgstr);

							}
							/**
							 * 获取辑控图片
							 */
							T_QQFW_QGCK_ZP_Model t_qqfw_qgck_zp_model =  t_qqfw_qgck_zp_service.getPhotoByIdCardNo(compareList.get(i).getIdCardNo());
							if(t_qqfw_qgck_zp_model!=null){
								compareList.get(i).setCollectIdCardPic(t_qqfw_qgck_zp_model.getPhoto());
							}
							list.add(compareList.get(i));
						}
					}
					request.setAttribute("compareList", list);
				} else {
					request.setAttribute("isCompare", '0');
				}

				//得到预警信息
				request.setAttribute("IntegratedQueryMessage", integratedQueryMessage);
			}

			request.setAttribute("page", page);
			request.setAttribute("collectSite", collectSite);
		} catch (Exception e) {
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		/**
		 * 保存日志
		 */
		log.recordOperateLog(operationLogInfo);
		return new ModelAndView("IntegratedQueryMessage", "list", null);
	}

	public static void main(String[] args) {
		AnalyzingController a = new AnalyzingController();
		String content = a.getT_QB_RY_CKRYJBXXBj("1000000000000000000000000000000000000000000000000000000000000000");
		System.out.println(content);

	}
}
