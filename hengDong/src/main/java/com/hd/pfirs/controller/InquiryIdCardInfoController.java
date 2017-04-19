package com.hd.pfirs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.pfirs.model.InquiryIdCardInfoModel;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.Page;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.service.InquiryIdCardInfoService;
import com.hd.pfirs.service.ParamSetService;
import com.hd.pfirs.util.logs.service.LogService;

/**
 * 身份证信息查询
 * 
 * @author cliff.fan
 *
 */
@Controller
@RequestMapping("/InquiryIdCardInfo")
public class InquiryIdCardInfoController {

	@Autowired
	private InquiryIdCardInfoService inquiryIdCardInfoService;
	
	@Autowired
	private ParamSetService paramSetService;
	
	private Logger logger = Logger.getLogger(ComplexQueryController.class);
	
	@Autowired
	private LogService log;
	
	/**
	 * @注释 curry.su
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "InquiryIdCardInfo")
	@ResponseBody
	public ModelAndView InquiryIdCardInfo(HttpServletRequest request) {
		logger.info("goto 身份证信息查询");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("身份证信息查询模块");
		/**
		 * 得到参数
		 */
		String page1 = request.getParameter("page");
		String collectTimeStart = request.getParameter("collectTimeStart");
		String collectTimeEnd = request.getParameter("collectTimeEnd");
		String collectSite = request.getParameter("collectSite");
		String idCardName = request.getParameter("idCardName");
		String idCardSex = request.getParameter("sexSelected");
		String idCardNo = request.getParameter("idCardNo");
		String isCardInfo = "1";
		String isFaceAndCard = "";
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("page1="+page1
											+";collectTimeStart="+collectTimeStart
											+";collectTimeEnd="+collectTimeEnd
											+";collectSite="+collectSite
											+";idCardName="+idCardName
											+";idCardNo="+idCardNo
											+";idCardSex="+idCardSex
											+";isCardInfo=1");
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		/**
		 * 得到预警值
		 */
		ParamSet paramSet = paramSetService.getParamSet();
		/**
		 * 求结果集条数
		 */
		int count = inquiryIdCardInfoService.getIdCardInfoCount(
				collectTimeStart, collectTimeEnd, collectSite,
				idCardName, idCardSex, idCardNo,isCardInfo,isFaceAndCard,paramSet.getFaceCardCompAlarmVal());
		Page page = new Page(count, Integer.parseInt(page1));
		/**
		 * 得到结果集数据
		 */
		try{
			List<InquiryIdCardInfoModel> list = inquiryIdCardInfoService
					.getIdCardInfo(page.getStart(), page.getPageSize(),
							collectTimeStart, collectTimeEnd, collectSite, idCardName, idCardSex, idCardNo,isCardInfo,isFaceAndCard,paramSet.getFaceCardCompAlarmVal());
	
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			request.setAttribute("paramSet", paramSet);
		}catch(Exception e){
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
			
		}
		/**
         * 保存日志
         */
        log.recordOperateLog(operationLogInfo);
		return new ModelAndView("InquiryIdCardInfo", "list", null);
	}
	
	/**
	 * @注释 curry.su
	 * @param request
	 * @param isCardInfo
	 * @param isFaceAndCard
	 * @param collectTimeStart
	 * @param collectTimeEnd
	 * @param idCardName
	 * @param sexSelected
	 * @param idCardNo
	 * @param page1
	 * @param collectSite
	 * @return 
	 */
	@RequestMapping(value = "InquiryIdCardInfo1")
	@ResponseBody
	public Map<String,Object> InquiryIdCardInfo1(HttpServletRequest request,String isCardInfo,String isFaceAndCard,String collectTimeStart,
			String collectTimeEnd,String idCardName,String sexSelected,String idCardNo,String page1,String collectSite) {
		logger.info("goto 身份证信息查询");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("身份证信息查询模块");
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("isCardInfo="+isCardInfo
											+";isFaceAndCard="+isFaceAndCard
											+";collectTimeStart="+collectTimeStart
											+";collectTimeEnd="+collectTimeEnd
											+";idCardName="+idCardName
											+";idCardName="+idCardName
											+";sexSelected="+sexSelected
											+";idCardNo="+idCardNo
											+";page1="+page1
											+";collectSite="+collectSite);
		
		Map<String,Object> map = new HashMap<String,Object>();
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		/**
		 * 得到预警值
		 */
		ParamSet paramSet = paramSetService.getParamSet();
		/**
		 * 得到结果集条数
		 */
		int count = inquiryIdCardInfoService.getIdCardInfoCount(
				collectTimeStart, collectTimeEnd, collectSite,
				idCardName, sexSelected, idCardNo,isCardInfo,isFaceAndCard,paramSet.getFaceCardCompAlarmVal());
		Page page = new Page(count, Integer.parseInt(page1));
		try{
		List<InquiryIdCardInfoModel> list = inquiryIdCardInfoService
				.getIdCardInfo(page.getStart(), page.getPageSize(),
						collectTimeStart, collectTimeEnd, collectSite,
						 idCardName, sexSelected, idCardNo,isCardInfo,isFaceAndCard,paramSet.getFaceCardCompAlarmVal());
		map.put("list", list);
		map.put("page", page);
		map.put("paramSet", paramSet);
		}catch(Exception e){
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
}
