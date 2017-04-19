package com.f2b2c.eco.controller.platform.f2b;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.platform.FFreightModel;
import com.f2b2c.eco.model.platform.FLogisticsModel;
import com.f2b2c.eco.model.platform.FProtocolModel;
import com.f2b2c.eco.service.market.BUserService;
import com.f2b2c.eco.service.market.CUserService;
import com.f2b2c.eco.service.platform.FFreightService;
import com.f2b2c.eco.service.platform.FGoodsService;
import com.f2b2c.eco.service.platform.FLogisticsService;
import com.f2b2c.eco.service.platform.FProtocolService;
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.ResultStatus;

/**
 * @author mozzie.chu
 * @version 2016/09/09
 */
@Controller(value = "fHomeStatisticController")
@RequestMapping(value = "/farm/home")
public class FHomeController {
	
	/**
	 * 日志工具类
	 */
	private static final Logger logger = LoggerFactory.getLogger(FHomeController.class);
	
	/*主页统计*/
	@Autowired
	private FGoodsService fGoodsService;
	@Autowired
	private CUserService cUserService;
	@Autowired
	private BUserService bUserService;
	
	@Autowired
	private FProtocolService fProtocolService;
	@Autowired
	private FLogisticsService fLogisticsService;
	@Autowired
	private FFreightService fFreightService;
	
	/**
	 * 主页-统计
	 * @param request
	 * @param response
	 * @return 统计页
	 */
	@RequestMapping(value="/homestatistic",method=RequestMethod.GET)
	public final String homeStatistic(HttpServletRequest request,HttpServletResponse response,Model model) {
		//商品数
		int goodscount = fGoodsService.findAllFGoodsCount();
		model.addAttribute("goodscount",goodscount);
		
		//C端用户数
		int cusercount = cUserService.findAllCUserCount();
		model.addAttribute("cusercount", cusercount);
		
		//B端用户数
		int busercount = bUserService.findAllBUserCount();
		model.addAttribute("busercount", busercount);
		return "platform/home/home";
	}

	/**
	 * 主页-担保协议
	 * @param request
	 * @param response
	 * @return 担保协议页
	 */
	@RequestMapping(value="/warrantorprotocol",method=RequestMethod.GET)
	public final String warrantorProtocol(HttpServletRequest request,HttpServletResponse response, Model model,String type){
        FProtocolModel fProtocolModel = fProtocolService.queryFProtocolByType("0");
        model.addAttribute("protocol", fProtocolModel);
		return "platform/home/warrantor";
	}
	
	/**
	 * 手机-担保协议
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/warrantor", method = RequestMethod.GET)
	public final String warrantor(HttpServletRequest request,HttpServletResponse response, Model model,String type) {
		FProtocolModel fProtocolModel = fProtocolService.queryFProtocolByType("0");
        model.addAttribute("protocol", fProtocolModel);
        return "platform/warrantor/warrantor";
	}
	
	/**
	 * 主页-注册协议
	 * @param request
	 * @param response
	 * @return 注册协议页
	 */
	@RequestMapping(value="/registerprotocol",method=RequestMethod.GET)
	public final String registerProtocol(HttpServletRequest request,HttpServletResponse response, Model model,String type){
		FProtocolModel fProtocolModel = fProtocolService.queryFProtocolByType("1");
        model.addAttribute("protocol", fProtocolModel);
		return "platform/home/register";
	}
	
	/**
	 * 手机-注册协议
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public final String register(HttpServletRequest request,HttpServletResponse response, Model model,String type) {
		FProtocolModel fProtocolModel = fProtocolService.queryFProtocolByType("1");
        model.addAttribute("protocol", fProtocolModel);
        return "platform/register/register";
	}
	
	/**
	 * 协议-填写/修改协议
	 * @param proModel
	 * @return
	 */
	@RequestMapping(value="/insertprotocol",method=RequestMethod.POST)
	public void insert(FProtocolModel proModel){
		if(proModel.getId() !=null && proModel.getId() > 0){
			this.fProtocolService.updateFProtocolContent(proModel);
		}else {
			this.fProtocolService.insert(proModel);			
		}
	}
	
	/**
	 * 主页-运费设置
	 * @param request
	 * @param response
	 * @return 运费设置页
	 */
	@RequestMapping(value="/freight",method=RequestMethod.GET)
	public final String freight(HttpServletRequest request,HttpServletResponse response,Model model,Integer id){
		FFreightModel fFreightModel = fFreightService.select(1);
//		model.addAttribute("id", fFreightModel);
//		model.addAttribute("freemoney", fFreightModel);
		model.addAttribute("fFreightModel", fFreightModel);
		return "platform/home/freight";
	}
	
	/**
	 * 运费设置-添加/修改
	 * @param model
	 * @return 提交按钮
	 */
	@RequestMapping(value="/insert-freight", method=RequestMethod.POST)
	public void insertOr(FFreightModel model){
		if(model.getId() != null && model.getId() > 0){
			this.fFreightService.update(model);
		}else {			
			this. fFreightService.insert(model);
		}
	}
	
	
	/**
	 * 主页-物流方式
	 * @param request
	 * @param response
	 * @return 物流方式页
	 */
	@RequestMapping(value="/logistics",method=RequestMethod.GET)
	public final String logistics(HttpServletRequest request,HttpServletResponse response){		
		return "platform/home/logistics";
	}
	
	/**
	 * 查物流方式
	 * @param pageable
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/querylogisticsmodel", method = RequestMethod.GET)
	@ResponseBody
	public final Page<FLogisticsModel> queryLogisticsModel(final Pageable pageable, HttpServletRequest request, 
			HttpServletResponse response){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Page<FLogisticsModel> pages = fLogisticsService.queryLogisticsModel(pageable, paramMap);
		return pages;
		
	}
	
	/**
	 * 物流方式-修改页面
	 * @param request
	 * @param response
	 * @return 修改页
	 */
	@RequestMapping(value="/update-logistics",method=RequestMethod.GET)
	public String updatelogistics(HttpServletRequest request,HttpServletResponse response,Model model,
			Integer id){		
		FLogisticsModel fLogisticsModel = fLogisticsService.queryLogisticsById(id);
		model.addAttribute("logistics", fLogisticsModel);
		return "platform/home/logistics/update";
	}
	
	/**
	 * 物流方式-修改页面-修改功能
	 * @param request
	 * @param response
	 * @param model
	 * @return 修改功能
	 */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public final int update(FLogisticsModel model) {
		
		return fLogisticsService.update(model);
	}
	
	/**
	 * 物流方式-添加页面
	 * @param request
	 * @param response
	 * @return 修改页
	 */
	@RequestMapping(value="/add-logistics",method=RequestMethod.GET)
	public final String addlogistics(HttpServletRequest request,HttpServletResponse response){		
		return "platform/home/logistics/add";
	}
	
	/**
	 * 物流方式-添加页面-添加功能
	 * @param id
	 * @param name
	 * @return 添加功能
	 */
	@RequestMapping(value="/addlist-logistics",method=RequestMethod.POST)
	@ResponseBody
	public int insert(FLogisticsModel model){
		return fLogisticsService.insert(model);	
	}
	
	/**
	 * 物流方式-删除功能
	 * @param id
	 * @return
	 */
//	@RequestMapping(value = "/del_logs/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public final DTOResult del(HttpServletRequest request,HttpServletResponse response,@PathVariable Integer id){
//		DTOResult result = new DTOResult();
//		int count = fLogisticsService.delete(String.valueOf(id));
//    	if(count > 0){
//    		result.setResultStatus(ResultStatus.SUCESS);
//    	}
//    	return result;
//    }
	 @RequestMapping(value = "/del", method = RequestMethod.POST)
	    @ResponseBody
	    public final int del(String id){
	    	return fLogisticsService.delete(id);
	    }
}
