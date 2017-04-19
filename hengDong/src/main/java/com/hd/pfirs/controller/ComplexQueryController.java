package com.hd.pfirs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;
import com.hd.pfirs.model.ComplexQueryModel;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.Page;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.T_QQFW_QGCK_ZP_Model;
import com.hd.pfirs.service.ComplexQueryService;
import com.hd.pfirs.service.ParamSetService;
import com.hd.pfirs.service.T_QQFW_QGCK_ZP_Service;
import com.hd.pfirs.util.logs.service.LogService;

/**
 * 综合查询Controller
 * @author jing.liu
 *
 */
@Controller
@RequestMapping("/complexQuery")
public class ComplexQueryController {
    
	@Autowired
	private ComplexQueryService complexQueryService;
	
	@Autowired
	private ParamSetService paramSetService;
	
	@Autowired
	private LogService log;
	
	@Autowired
	private T_QQFW_QGCK_ZP_Service t_qqfw_qgck_zp_service;
	
	private Logger logger = Logger.getLogger(ComplexQueryController.class);
	
	/**
	 * 综合查询
	 * @param request
	 * @return
	 * @author jing.liu
	 * @注释 curry.su
	 */
	@RequestMapping(value = "getComplexQueryList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView getComplexQueryList(HttpServletRequest request){
		
		return new ModelAndView("IntegratedQuery");
		
	}
	/**
	 * 综合查询详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryComplexById")
	@ResponseBody
	public ModelAndView QueryComplexById(HttpServletRequest request){
		logger.info("here 人像接口详情页面");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("详情页面人像接口模块");
		
		String faceId = request.getParameter("faceId");
		List imgList = new ArrayList();
		String comeFrom = request.getParameter("comeFrom");
		request.setAttribute("comeFrom", comeFrom);
		String compareBaseId = request.getParameter("compareBaseId");
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("faceId="+faceId
											+";comeFrom="+comeFrom+";compareBaseId"+compareBaseId);
		try{
	//		List faceInfoPicList = new ArrayList();
	//		Map<String,Object> map = new HashMap<String,Object>();
			/* 根据ID查询一張最清晰的人臉照片和全身照片和一張不清晰的人臉照片 **/
			List<ComplexQueryModel> list = complexQueryService.queryFaceById(Long.valueOf(faceId));
			for(ComplexQueryModel complexQueryModel : list){
				String img = new BASE64Encoder().encode(complexQueryModel.getCollectPic());
				imgList.add(img);
			}
			String collectTime = list.get(0).getCollectTimeStr();
			String collectSite = list.get(0).getCollectSite();
		
	//		String idCardPic = "";
			/* 根據ID查詢身份張的信息 **/
			ComplexQueryModel complexQueryModel = complexQueryService.queryCardInfoById(Long.valueOf(faceId));

			ParamSet paramSet = paramSetService.getParamSet();
			/**
			 * 如果没有对比库id，则没有比重辑控，所以详情不取辑控信息
			 */
			ComplexQueryModel idCardCompResult = null;
			if(compareBaseId!=""&&compareBaseId!=null&&!"null".equals(compareBaseId)){
				/*身份证信息比对*/
				idCardCompResult = complexQueryService.queryIdCardCompResult(Long.valueOf(faceId),compareBaseId.trim());
				
				/**
				 * 获取辑控图片
				 */
				if(idCardCompResult!=null){
					if(idCardCompResult.getYsfzh()!=null){
						T_QQFW_QGCK_ZP_Model t_qqfw_qgck_zp_model =  t_qqfw_qgck_zp_service.getPhotoByIdCardNo(idCardCompResult.getYsfzh());
						if(t_qqfw_qgck_zp_model!=null){
							idCardCompResult.setIdCardPic(t_qqfw_qgck_zp_model.getPhoto());
						}
					}
				}
			}
			
			/*人像信息比对，显示最新的5条记录*/
			List<ComplexQueryModel> faceInfoCompResultList = complexQueryService.queryFaceInfoCompResult(Long.valueOf(faceId),paramSet.getFaceCompAlarmVal());
			for(ComplexQueryModel model:faceInfoCompResultList){
				/**
				 * 获取辑控图片
				 */
				if(model.getYsfzh()!=null){
					T_QQFW_QGCK_ZP_Model zpmodel =  t_qqfw_qgck_zp_service.getPhotoByIdCardNo(model.getYsfzh());
					if(zpmodel!=null){
						model.setPhoto(zpmodel.getPhoto());
					}
				}
			}
			
			/*人证比对*/
			ComplexQueryModel faceAndCard = complexQueryService.queryFaceAndCardResult(Long.valueOf(faceId));
	//		request.setAttribute("idCardPic",idCardPic);
	//		request.setAttribute("faceInfoPicList",faceInfoPicList);
			request.setAttribute("imgList", imgList);
			request.setAttribute("paramSet", paramSet);
			request.setAttribute("faceAndCard",faceAndCard);
			request.setAttribute("faceInfoCompResultList",faceInfoCompResultList);
			request.setAttribute("idCardCompResult",idCardCompResult);
			request.setAttribute("complexQueryModel", complexQueryModel);
			request.setAttribute("collectTime",collectTime);
			request.setAttribute("collectSite",collectSite);
		    request.setAttribute("list",list);
		}catch(Exception e){
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");			
		}
		/**
         * 保存日志
         */
        log.recordOperateLog(operationLogInfo);
		return new ModelAndView("FaceAndCardDetail");
		
	}
	
	/**
	 * 综合查询异步请求
	 */
	/**
	 * @author jing liu
	 * @注释 curry.su
	 * @param request
	 * @param faceComp
	 * @param isControlled
	 * @param faceAndIdCardComp
	 * @param collectTimeStart
	 * @param collectTimeEnd
	 * @param collectSite
	 * @param idCardName
	 * @param idCardNo
	 * @param faceCompName
	 * @param faceCompNo
	 * @param idCardSex
	 * @param sexComp
	 * @param cardCompName
	 * @param cardSexComp
	 * @param cardCompNo
	 * @param page1
	 * @return
	 */
	@RequestMapping(value = "getComplexQueryListSearch",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getComplexQueryListSearch(HttpServletRequest request,String faceComp,
    		String isControlled,String faceAndIdCardComp,String collectTimeStart,String collectTimeEnd,String collectSite,String idCardName,
    		String idCardNo,String faceCompName,String faceCompNo,String idCardSex,String sexComp,String cardCompName,String cardSexComp,String cardCompNo,String page1){
		logger.info("here 综合信息查询");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateName("综合查询");
		operationLogInfo.setOperateCondition("faceComp="+faceComp
				+";isControlled="+isControlled
				+";faceAndIdCardComp="+faceAndIdCardComp
				+";collectTimeStart="+collectTimeStart
				+";collectTimeEnd="+collectTimeEnd
				+";collectSite="+collectSite
				+";idCardName="+idCardName
				+";idCardNo="+idCardNo
				+";faceCompName="+faceCompName
				+";faceCompNo="+faceCompNo
				+";idCardSex="+idCardSex
				+";sexComp="+sexComp
				+";cardCompName="+cardCompName
				+";cardSexComp="+cardSexComp
				+";cardCompNo="+cardCompNo
				+";page1="+page1);
		operationLogInfo.setOperateDesc("");
		
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 得到预警值
		 */
		ParamSet paramSet = paramSetService.getParamSet();
		/**
		 * 得到结果集条数
		 */
		int totalCount = complexQueryService.getComplexQueryCount(collectTimeStart,collectTimeEnd,collectSite,
	    		idCardName,idCardSex,idCardNo,faceCompName,sexComp,faceCompNo,faceComp,isControlled,faceAndIdCardComp,cardCompName,cardSexComp,cardCompNo,paramSet.getFaceCardCompAlarmVal(),paramSet.getFaceCompAlarmVal());
		if(page1 == null || page1 == ""){
			page1 = "1";
		}
		Page page = new Page(totalCount,Integer.parseInt(page1));
		/**
		 * 获得结果集数据
		 */
		List<ComplexQueryModel> list = null;
		try{
	    list = complexQueryService.getComplexQueryList(page.getStart(), page.getPageSize(),collectTimeStart,collectTimeEnd,collectSite,
	    		idCardName,idCardSex,idCardNo,faceCompName,sexComp,faceCompNo,faceComp,isControlled,faceAndIdCardComp,cardCompName,cardSexComp,cardCompNo,paramSet.getFaceCardCompAlarmVal(),paramSet.getFaceCompAlarmVal());
	    for(ComplexQueryModel complexQueryModel:list){
			/**
			 * 获取辑控图片
			 */
	    	String idcardNo = null;
	    	switch(complexQueryModel.getCompareBaseID()){
		    	case "1":
		    		idcardNo = complexQueryModel.getYsfzh();
		    		break;
		    	case "0":
		    		idcardNo = complexQueryModel.getBbkrzjhm();
		    		break;
		    	case "2":
		    		idcardNo = complexQueryModel.getT_QB_RY_CKRYJBXXsfzh();
		    		break;
		    	
	    	}
	    	if(complexQueryModel.getIdCardNo()!=null){
				T_QQFW_QGCK_ZP_Model t_qqfw_qgck_zp_model =  t_qqfw_qgck_zp_service.getPhotoByIdCardNo(idcardNo);
				if(t_qqfw_qgck_zp_model!=null){
					complexQueryModel.setPhoto(t_qqfw_qgck_zp_model.getPhoto());
				}
	    	}
		}
		}catch(Exception e){
			logger.error(e);
			operationLogInfo.setErrorCode("1999");
			operationLogInfo.setOperateResult("0");
		}
		map.put("list", list);
        map.put("page", page);
        map.put("paramSet", paramSet);
        /**
         * 保存记录
         */
        log.recordOperateLog(operationLogInfo);
      return map;		
	}

}
