package com.hd.pfirs.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.service.IdCardInfoService;
import com.hd.pfirs.util.MapBeanUtils;

/**
 * 
 * @author cliff.fan
 *
 */
@Controller
@RequestMapping("/IdCardInfo")
public class IdCardInfoController {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IdCardInfoController.class);

	@Autowired
	private IdCardInfoService idCardInfoService;

	/**
	 * 
	  * @Description: 保存身份证信息
	  * 			       如果有16位id那么就去找全它的所有信息t_pp_idcardrelation
	  * @param      : @RequestBody IdCardInfoModel model, HttpServletRequest request
	  * @return     : String
	  * @throws  
	  * @data       : 2016年1月29日 上午9:39:23   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	@RequestMapping(value = "saveIdCardInfo", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String saveIdCardInfo(@RequestBody IdCardInfoModel model, HttpServletRequest request) {

		String card16 = model.getCardID16();

		// 如果是16位id， 那么找全身份证所有信息
		if (!StringUtils.isBlank(card16)) {
			//TODO 16位信息转全信息
			List<IdCardInfoModel> idCardInfos = idCardInfoService.queryCardIdInfoByCard16(card16);
			if (idCardInfos != null && !idCardInfos.isEmpty()) {
				IdCardInfoModel model2 = idCardInfos.get(0);
				model.setIdCardPic(model2.getIdCardPic());
				model.setIdCardName(model2.getIdCardName());
				model.setIdCardNo(model2.getIdCardNo());
				model.setIdCardAddress(model2.getIdCardAddress());
				model.setIdCardSex(model2.getIdCardSex());
				model.setIdCardBirth(model2.getIdCardBirth());
				model.setIdCardNation(model2.getIdCardNation());
				model.setCardIssueDate(model2.getCardIssueDate());
				model.setCardExpiryDate(model2.getCardExpiryDate());
				model.setCardIssueDate(model2.getCardIssueDate());
				model.setCardUnit(model2.getCardUnit());
			} else {
				return "-4";
			}
		}

		// 转个时间
		long timeStamp = -1;
		if (model.getCollectTimeStamp() == null) {
			timeStamp = System.currentTimeMillis();
		} else {
			timeStamp = Long.valueOf(model.getCollectTimeStamp());
		}
		Timestamp tamp = new Timestamp(timeStamp);
		model.setCollectTime(tamp);

		// 设备号转化
		//		String cardCode = model.getCardCode();
		//
		//		if (!StringUtils.isBlank(cardCode)) {
		//			String[] codes = cardCode.split("_");
		//			if (codes != null || codes.length > 0) {
		//				model.setDeviceCode(codes[0]);
		//			}
		//		}
		try {
			idCardInfoService.saveIdCardInfo(model);
			return "2";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "-1";
		}
	}

	@RequestMapping(value = "saveIdCardMap", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String saveIdCardMap(@RequestBody Map<String, Object> paramsMap, HttpServletRequest request) {
		System.out.println("service接受到的信息: " + paramsMap);
		IdCardInfoModel model = new IdCardInfoModel();
		MapBeanUtils.transMap2Bean2(paramsMap, model);
		long timeStamp = Long.valueOf(model.getCollectTimeStamp());
		Timestamp tamp = new Timestamp(timeStamp);
		model.setCollectTime(tamp);
		try {
			idCardInfoService.saveIdCardInfo(model);
			System.out.println("service接受到的信息model: " + model);
			return "2";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "-1";
		}
	}

	/*@RequestMapping(value = "getIdCardInfoByCollectTimeList")
	public void getIdCardInfoByCollectTimeList(HttpServletRequest request) {
		List<IdCardInfoStrModel> list = idCardInfoService.getIdCardInfoByCollectTimeList();
		IdCardInfoModel idCardInfo = list.get(0);
		BASE64Encoder encoder = new BASE64Encoder();
		String imgStr = encoder.encode(idCardInfo.getIdCardPic());
		request.setAttribute("imgStr", imgStr);
		request.setAttribute("list", list);
		// return new ModelAndView("RealTimeMonitor","list",list);
	}*/
}
