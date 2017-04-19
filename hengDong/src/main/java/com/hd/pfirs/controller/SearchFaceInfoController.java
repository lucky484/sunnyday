/**
 * 
 */
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
import com.hd.pfirs.model.Constant;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.SearchFaceInfo;
import com.hd.pfirs.model.Temporary;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;
import com.hd.pfirs.service.ConstantService;
import com.hd.pfirs.service.FugitivesService;
import com.hd.pfirs.service.ParamSetService;
import com.hd.pfirs.service.SearchFaceInfoService;
import com.hd.pfirs.service.TemporaryService;
import com.hd.pfirs.service.ZaiTaoAJLXService;
import com.hd.pfirs.util.logs.service.LogService;

/**
 * @ClassName: SearchFaceInfoController
 * @Description: 人脸信息查询
 * @author light.chen
 * @date Dec 29, 2015 10:25:09 AM
 */
@Controller
@RequestMapping("/SearchFaceInfo")
public class SearchFaceInfoController {
	
	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IdCardInfoController.class);
	
	@Autowired
	private ParamSetService paramSetService;
	
	@Autowired
	private SearchFaceInfoService searchFaceInfoService;
	
	@Autowired
	private ZaiTaoAJLXService ZaiTaoAJLXService;

	@Autowired
	private FugitivesService fugitivesService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private TemporaryService temporaryService;
	@Autowired
	private LogService log;
	
	@RequestMapping(value = "getSearchFaceInfo")
	public String getSearchFaceInfo(){
		logger.info("goto 人脸信息查询");
		return "SearchFaceInfo";
	}
	
	@RequestMapping("getFacecomp")
	@ResponseBody
	public ParamSet getFacecomp(){
		ParamSet ps = searchFaceInfoService.getParamSet();
		return ps;
	}
	
	/**
	 * @注释 curry.su
	 * @param page
	 * @param collectTimeStart
	 * @param collectTimeEnd
	 * @param collectSite
	 * @param faceSimilarity
	 * @return
	 */
	@RequestMapping(value = "getFaceInfoBy" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFaceInfoBy(HttpServletRequest request ,int page,String collectTimeStart,String collectTimeEnd,String collectSite,String faceSimilarity){
		logger.info("here 人脸信息查询");
		/**
		 * 记录日志
		 */
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(request.getSession().getAttribute("username").toString());
		operationLogInfo.setOperateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		operationLogInfo.setOperateType(1);
		operationLogInfo.setOperateResult("1");
		operationLogInfo.setOperateDesc("");
		operationLogInfo.setOperateName("人像信息查询模块");
		/**
		 * 得到查询条件
		 */
		operationLogInfo.setOperateCondition("page="+page
											+";collectTimeStart="+collectTimeStart
											+";collectTimeEnd="+collectTimeEnd
											+";collectSite="+collectSite
											+";faceSimilarity="+faceSimilarity);
		/**
		 * 模糊查询
		 */
		if(collectSite != null && !collectSite.equals("")){
			collectSite = "%"+collectSite+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 获取人脸信息
		 */
		List<SearchFaceInfo> l = searchFaceInfoService.searchFaceInfo(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
		/**
		 * 获取人脸相似度阈值
		 */
		ParamSet ps = searchFaceInfoService.getParamSet();
		/**
		 * 查询信息总数
		 */
		int count = searchFaceInfoService.getCount(collectTimeStart, collectTimeEnd, collectSite, faceSimilarity);
		/**
		 * 获取人脸相似度
		 */
		int faceS = ps.getFaceCompAlarmVal();
		/**
		 * 获取人证合一相似度
		 */
		int faceCS = ps.getFaceCardCompAlarmVal();
		try{
			/**
			 * 缉控库三表判断及查询
			 */
			List<T_QB_RY_ZTRYJBXX> lzt = new ArrayList<T_QB_RY_ZTRYJBXX>();
			List<Constant> lc = new ArrayList<Constant>();
			List<Temporary> lt = new ArrayList<Temporary>();
			List<String> lid = new ArrayList<String>();
			for(int i=0;i<l.size();i++){
				if(null == l.get(i).getCompareBaseID()){
					lid.add(null);
				}else{
					lid.add(l.get(i).getCompareBaseID());
				}
			}
			for(int i=0;i<l.size();i++){
					if(null == l.get(i).getCtrlBaseID()){
						lzt.add(null);
						lc.add(null);
						lt.add(null);
					}else{
						//获取在逃表信息
						String id = l.get(i).getCtrlBaseID();
						T_QB_RY_ZTRYJBXX zt = fugitivesService.getJiKong(id);
						if(zt!=null&&zt.getPhoto()!=null){
							BASE64Encoder encoder = new BASE64Encoder();
							String imgstr = encoder.encode(zt.getPhoto());
							zt.setPhotoStr(imgstr);
						}
						lzt.add(zt);
						//获取常控表信息
						Constant c = constantService.getConstant(id);
						if(c!=null&&c.getPhoto()!=null){
							BASE64Encoder encoder = new BASE64Encoder();
							String imgstr = encoder.encode(c.getPhoto());
							c.setPhotoStr(imgstr);
						}
						lc.add(c);
						//获取临控表信息
						Temporary t = temporaryService.getTemporary(id);
						if(t!=null&&t.getPhoto()!=null){
							BASE64Encoder encoder = new BASE64Encoder();
							String imgstr = encoder.encode(t.getPhoto());
							t.setPhotoStr(imgstr);
						}
						lt.add(t);
				    }
				}
			//计算前台页面总数
			int pages = 0;
			if(count % 10 == 0){
				pages = count/10;
			}else{
				pages = count/10 + 1;
			}
			map.put("l", l);
			map.put("lid", lid);
			map.put("lzt", lzt);
			map.put("lc", lc);
			map.put("lt", lt);
			map.put("faceS", faceS);
			map.put("faceCS", faceCS);
			map.put("count", count);
			map.put("pages", pages);
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
