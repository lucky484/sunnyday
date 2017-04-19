package com.hd.pfirs.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pfirs.model.Constant;
import com.hd.pfirs.model.FCResultModel;
import com.hd.pfirs.model.FaceComResult;
import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.model.FaceInfoWebModel;
import com.hd.pfirs.model.IdCardCompWarn;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.IdCardInfoStrModel;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;
import com.hd.pfirs.model.T_QQFW_QGCK_ZP_Model;
import com.hd.pfirs.model.Temporary;
import com.hd.pfirs.model.ZaiTaoAJLX;
import com.hd.pfirs.service.ConstantService;
import com.hd.pfirs.service.FaceAndCardResultService;
import com.hd.pfirs.service.FaceComResultService;
import com.hd.pfirs.service.FaceCompService;
import com.hd.pfirs.service.FaceInfoService;
import com.hd.pfirs.service.FugitivesService;
import com.hd.pfirs.service.IdCardInfoService;
import com.hd.pfirs.service.ParamSetService;
import com.hd.pfirs.service.T_QQFW_QGCK_ZP_Service;
import com.hd.pfirs.service.TemporaryService;
import com.hd.pfirs.service.ZaiTaoAJLXService;
import com.hd.pfirs.util.MapBeanUtils;

import sun.misc.BASE64Encoder;

/**
 * @ClassName:
 * @Description:
 * @author
 * @date Dec 16, 2015 10:18:44 AM
 */
@Controller
@RequestMapping("/FaceInfo")
public class FaceInfoController {

	@Autowired
	private FaceInfoService faceInfoService;
	@Autowired
	private IdCardInfoService idCardInfoService;

	@Autowired
	private FaceAndCardResultService FCResult;
	@Autowired
	private FaceComResultService faceComResultService;
	@Autowired
	private FaceCompService faceCompService;
	@Autowired
	private FugitivesService fugitivesService;
	@Autowired
	private ZaiTaoAJLXService ZaiTaoAJLXService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private TemporaryService temporaryService;
	@Autowired
	private ParamSetService paramSetService;
	@Autowired
	private T_QQFW_QGCK_ZP_Service t_qqfw_qgck_zp_service;
	/**
	 * 实时监控身份证全信息消失时间
	 */
	@Value("${push_IdCardMissTime}")
	private String push_IdCardMissTime;
	/**
	 * 预警闪烁时间
	 */
	@Value("${push_warmingTime}")
	private String push_warmingTime;
	/**
	 * 预警定时取数时间
	 */
	@Value("${push_timeOutTime}")
	private String push_timeOutTime;
	/**
	 * 预警闪烁之后长显示时间
	 */
	@Value("${push_displayTime}")
	private String push_displayTime;
	

	@RequestMapping(value = "getRealTimeMonitor")
	public String getRealTimeMonitor(HttpServletRequest request) {
		String deviceCode = request.getParameter("deviceCode");
		String collectSite = request.getParameter("collectSite");
		if (StringUtils.isBlank(collectSite)) {
			collectSite = "";
		}
		ParamSet params = paramSetService.getParamSet();
		request.setAttribute("e_deviceCode", deviceCode);
		request.setAttribute("e_collectSite", collectSite);
		request.setAttribute("m_params", params);
		request.setAttribute("push_IdCardMissTime", push_IdCardMissTime);
		request.setAttribute("push_warmingTime", push_warmingTime);
		request.setAttribute("push_timeOutTime", push_timeOutTime);
		request.setAttribute("push_displayTime", push_displayTime);
		return "RealTimeMonitor";
	}

	@RequestMapping(value = "getRealTimeMonitor_16ID")
	public String getRealTimeMonitor_16ID(HttpServletRequest request) {
		String collectSite = request.getParameter("collectSite");
		if (StringUtils.isBlank(collectSite)) {
			collectSite = "";
		}
		request.setAttribute("e_collectSite", collectSite);
		return "RealTimeMonitor_16ID";
	}

	/**
	 * 得到采集信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryFaceInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFaceInfo(HttpServletRequest request) {
		/**
		 * faceInfo信息
		 */
		String deviceCode = request.getParameter("deviceCode");
		Map<String, Object> map = new HashMap<String, Object>();
		List<FaceInfo> listFaceInfo = faceInfoService.getFaceInfoListWeb(deviceCode);
		List<FaceInfoWebModel> listFaceInfoWebModel = new ArrayList<FaceInfoWebModel>();

		/**
		 * listFaceInfo中数据格式：根据采集时间取得最新的5条人脸数据（islast为1），
		 * 然后根据第一条数据groupcode取得全身数据（islast为1），并且放到数据开头。组成最多6条数据
		 * 判断第一条信息是不是全身的信息，如果不是，设置参数personImage为0，添加一个空对象到listFaceInfoWebModel中，
		 * 将后面信息追加到listFaceInfoWebModel中
		 * 
		 * 前台jsp根据personImage判断，如果为0，说明没有全身像，那么不创建image标签
		 */
		for (int i = 0; i < listFaceInfo.size(); i++) {
			if (i == 0) {
				// 如果第一个对象不是全身像的对象
				if ("0".equals(listFaceInfo.get(0).getPicType())) {
					map.put("personImage", "0");
					listFaceInfoWebModel.add(new FaceInfoWebModel());
				} else {
					map.put("personImage", "1");
				}
			}
			listFaceInfoWebModel.add(getFaceInfoWebModel(listFaceInfo.get(i)));
		}
		// 判断实时监控人脸信息是否有值
		map.put("length", listFaceInfoWebModel.size());
		if (listFaceInfoWebModel.size() > 1) {
			map.put("image1", listFaceInfoWebModel.get(0).getCollectPic());
			map.put("image2", listFaceInfoWebModel.get(1).getCollectPic());
			map.put("time2", listFaceInfoWebModel.get(1).getCollectTime());
		}
		map.put("listFaceInfoWebModel", listFaceInfoWebModel);
		/**
		 * idcard信息
		 */
		List<IdCardInfoStrModel> list = idCardInfoService.getIdCardInfoByCollectTimeList(deviceCode);
		if (list != null && list.size() > 0) {
			IdCardInfoStrModel idCardInfo = list.get(0);
			BASE64Encoder encoder = new BASE64Encoder();
			String imgStr = encoder.encode(idCardInfo.getIdCardPic());
			map.put("imgStr", imgStr);
			map.put("list", list);
		}
		// --------------------------begin---------------------------这边需要时时展示采集次数
		int idCardCount = idCardInfoService.getIdCardInfoCountByTime(deviceCode);
		map.put("idCardCount", idCardCount);
		int faceCount = faceInfoService.getFaceInfoCountByTime(deviceCode);
		map.put("faceCount", faceCount);
		// --------------------------end---------------------------
		return map;
	}

	public FaceInfoWebModel getFaceInfoWebModel(FaceInfo faceInfo) {
		FaceInfoWebModel faceInfoWebModel = new FaceInfoWebModel();
		faceInfoWebModel.setFaceId(faceInfo.getFaceId());
		faceInfoWebModel.setFaceCode(faceInfo.getFaceCode());
		BASE64Encoder encoder = new BASE64Encoder();
		String imgstr = encoder.encode(faceInfo.getCollectPic());
		faceInfoWebModel.setCollectPic(imgstr);
		faceInfoWebModel.setCollectTime(faceInfo.getCollectTime() == null ? "" : faceInfo.getCollectTime().toString());
		faceInfoWebModel.setCollectTimeStamp(faceInfo.getCollectTimeStamp());
		faceInfoWebModel.setCollectTime(faceInfo.getCollectTime().toString());
		faceInfoWebModel.setDeviceCode(faceInfo.getDeviceCode());
		faceInfoWebModel.setCollectSite(faceInfo.getCollectSite());
		faceInfoWebModel.setGroupCode(faceInfo.getGroupCode());
		faceInfoWebModel.setPicType(faceInfo.getPicType());
		faceInfoWebModel.setPicNo(faceInfo.getPicNo());
		faceInfoWebModel.setIsLast(faceInfo.getIsLast());
		faceInfoWebModel.setSpeed(faceInfo.getSpeed());
		faceInfoWebModel.setDirection(faceInfo.getDirection());
		faceInfoWebModel.setRelayFlag(faceInfo.getRelayFlag());
		return faceInfoWebModel;
	}

	/**
	 * 得到预警信息，取消显示标志
	 * @param request
	 * @return
	 * @注释 curry.su
	 */
	@RequestMapping(value = "queryWarningInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWarningInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String warningTime = request.getParameter("warningTime");
		/**
		 * 系统启动，以当前时间查询预警时间
		 */
		if("".equals(warningTime)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			warningTime = sdf.format(new Date());
		}

		/**
		 *  人脸对比,从对比表获取数据
		 */
		String deviceCode = request.getParameter("deviceCode");
		FaceComResult fcr = faceComResultService.getFaceBySimilarity(deviceCode,warningTime);

		/**
		 * 如果当前日期的下一条预警信息为空，则取当日最后一条信息显示
		 */
		if (fcr == null) {
			fcr = faceComResultService.getLastFaceBySimilarity(deviceCode,warningTime);
		} 

		/**
		 * 获取当期人像报警数
		 */
		int faceComWarnNum = faceComResultService.queryFaceComWarnNum(deviceCode);
		map.put("faceComWarnNum", faceComWarnNum);
		/**
		 * 人像预警信息不为空，获取预警对比的辑控信息，比重身份证
		 */
		if (fcr != null) {
			String faceCode = fcr.getFaceCode();
			String groupcode = fcr.getGroupCode();
			String id = fcr.getCtrlBaseID();
			Float similarity = fcr.getSimilarity();
			/**
			 * 辑控图片
			 */
			byte[] photo = fcr.getPhoto();
			FaceInfo fi = faceCompService.getfaceInfo(faceCode);
			/**
			 * 身份证信息，图片
			 */
			IdCardInfoModel ic = faceCompService.getIdCardInfo(groupcode);
			T_QB_RY_ZTRYJBXX zt = fugitivesService.getT_QB_RY_ZTRYJBXX(id);
			String comparebaseid = fcr.getCompareBaseID().trim();
			if (comparebaseid.equals("1")) {
				//在逃人员信息
				if (zt == null) {
					map.put("ajlx", null);
				} else {
					String code = zt.getAjlbdm();
					ZaiTaoAJLX ajlx = ZaiTaoAJLXService.getZaiTaoAJLX(code);
					if (zt.getPhoto() != null) {
						String photoStr = new BASE64Encoder().encode(zt.getPhoto());
						zt.setPhotoStr(photoStr);
					}
					map.put("zt", zt);
					map.put("ajlx", ajlx);
				}
			}
			if (comparebaseid.equals("2")) {
				//常控人员信息
				Constant c = constantService.getConstant(id);
				String str = c.getZdrylbbj();
				
				AnalyzingController analy = new AnalyzingController();
				String ch =  analy.getT_QB_RY_CKRYJBXXBj(str);
				if (c.getPhoto() != null) {
					String photoStr = new BASE64Encoder().encode(c.getPhoto());
					c.setPhotoStr(photoStr);
				}
				map.put("c", c);
				map.put("ch", ch);
			}
			if (comparebaseid.equals("0")) {
				Temporary t = temporaryService.getTemporary(id);
				if (t.getPhoto() != null) {
					String photoStr = new BASE64Encoder().encode(t.getPhoto());
					t.setPhotoStr(photoStr);
				}
				map.put("t", t);
			}
			map.put("comparebaseid", comparebaseid);
			map.put("similarity", similarity);
			map.put("photo", photo);
			map.put("faceCode", faceCode);
			map.put("fi", fi);
			map.put("ic", ic);
		}
		//  --------------------------begin---------------------------这边开始做人证合一
		
		FCResultModel model = FCResult.queryFCResult(warningTime, deviceCode);
		int FCWarnNum = FCResult.queryFCWarning(deviceCode);
		map.put("FCWarnNum", FCWarnNum);
		if (model != null) {
			map.put("FCInfo", model);
		} else {
			map.put("FCInfo", new FCResultModel());
		}

		// --------------------------end---------------------------

		//查身份证比对信息
		ParamSet paramSet = paramSetService.getParamSet();
		IdCardCompWarn idCardCompWarn = idCardInfoService.getIdCardInfoCompResult(warningTime,
				deviceCode);
		if (idCardCompWarn != null) {
			/**
			 * 更新状态
			 */
			idCardInfoService.updateFlag(idCardCompWarn.getCardComprltid());
			if ("T_QB_RY_CKRYJBXX".equals(idCardCompWarn.getTableName())) {
				AnalyzingController analyzingController = new AnalyzingController();
				idCardCompWarn.setAjlx(analyzingController.getT_QB_RY_CKRYJBXXBj(idCardCompWarn.getAjlx()));
			}
		} else {
			idCardCompWarn = idCardInfoService.getLastIdCardInfoCompResult(warningTime,deviceCode);
			if (idCardCompWarn != null) {
				if ("T_QB_RY_CKRYJBXX".equals(idCardCompWarn.getTableName())) {
					AnalyzingController analyzingController = new AnalyzingController();
					idCardCompWarn.setAjlx(analyzingController.getT_QB_RY_CKRYJBXXBj(idCardCompWarn.getAjlx()));
				}
			}
		}
		/**
		 * 获取辑控图片
		 */
		if(idCardCompWarn!=null){
			T_QQFW_QGCK_ZP_Model t_qqfw_qgck_zp_model =  t_qqfw_qgck_zp_service.getPhotoByIdCardNo(idCardCompWarn.getIdCardNo());
			if(t_qqfw_qgck_zp_model!=null){
				idCardCompWarn.setPhoto(t_qqfw_qgck_zp_model.getPhoto());
			}
		}
		// 空判断
		if (paramSet != null && idCardCompWarn != null
				&& paramSet.getFaceCardCompAlarmVal() > idCardCompWarn.getSimilarity()) {
			idCardCompWarn.setCollectTime(null);
			idCardCompWarn.setCollectPic(null);

		}
		map.put("idCardCompWarn", idCardCompWarn);
		int idCardComWarnNum = idCardInfoService.queryIdCardWarnNum(deviceCode);
		map.put("idCardComWarnNum", idCardComWarnNum);
		// --------------------------begin---------------------------这边需要时时展示采集次数
		int idCardCount = idCardInfoService.getIdCardInfoCountByTime(deviceCode);
		map.put("idCardCount", idCardCount);
		int faceCount = faceInfoService.getFaceInfoCountByTime(deviceCode);
		map.put("faceCount", faceCount);
		// --------------------------end---------------------------
		/**
		 * 预警取数后，更新新的取数时间
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String timeStr = sdf.format(new Date());
		map.put("warningTime", timeStr);
		return map;
	}
	
	/**
	 * 主页得到预警信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "indexWarningInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> indexWarningInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<FaceComResult> faceComResult = null;
		List<IdCardCompWarn> idCardComResult = null;
		List<FCResultModel> faceCardComResult = null;
		String warningTime = request.getParameter("warningTime");
		/**
		 * 如果预警的时间为空，则取当日时间
		 */
		if("".equals(warningTime)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			warningTime = sdf.format(new Date());
		}
				
		/**
		 *  人脸对比
		 */
		faceComResult = faceComResultService.indexfaceWarningInfo(warningTime);
		map.put("faceComResult", faceComResult);
		
		/**
		 * 身份证对比
		 */
		idCardComResult = idCardInfoService.indexIdcardWarningInfo(warningTime);
		map.put("idCardComResult", idCardComResult);
		
		/**
		 * 人证对比
		 */
		faceCardComResult = FCResult.indexFaceCardWarningInfo(warningTime);
		map.put("faceCardComResult", faceCardComResult);
		
		/**
		 * 预警取数后，更新新的取数时间
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String timeStr = sdf.format(new Date());
		map.put("timeStr", timeStr);
	
		
		return map;
	}

	@RequestMapping(value = "saveFaceInfo", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String saveFaceInfo(@RequestBody FaceInfo model, HttpServletRequest request) {
		long timeStamp = Long.valueOf(model.getCollectTimeStamp());
		Timestamp tamp = new Timestamp(timeStamp);
		model.setCollectTime(tamp);

		// 设备号转化
		//		String faceCode = model.getFaceCode();
		//
		//		if (!StringUtils.isBlank(faceCode)) {
		//			String[] codes = faceCode.split("_");
		//			if (codes != null || codes.length > 0) {
		//				model.setDeviceCode(codes[0]);
		//			}
		//		}
		return faceInfoService.saveFaceInfo(model);
	}

	@RequestMapping(value = "saveFaceMap", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String saveFaceMap(@RequestBody Map<String, Object> paramsMap, HttpServletRequest request) {
		System.out.println("service接受到的信息: " + paramsMap);
		FaceInfo model = new FaceInfo();
		MapBeanUtils.transMap2Bean2(paramsMap, model);
		long timeStamp = Long.valueOf(model.getCollectTimeStamp());
		Timestamp tamp = new Timestamp(timeStamp);
		model.setCollectTime(tamp);
		return faceInfoService.saveFaceInfo(model);
	}

}
