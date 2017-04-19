package com.f2b2c.eco.controller.market.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FAgentProfitModel;
import com.f2b2c.eco.service.market.BalanceService;

import net.sf.json.JSONObject;

/**
 * 
 * @author color.wu
 *
 */
@Controller(value="bBalanceController")
@RequestMapping(value="api/balance")
public class BalanceController {

	@Autowired
	private BalanceService balanceService;
	/**
	 * 获取当前个人的佣金流水
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	@ResponseBody
	public final Object commision(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map=new HashMap<>();
		
		String temp=StringUtils.trimToEmpty(request.getParameter("userId"));
		if("".equals(temp)){
			return null;
		}
		map.put("userId", Integer.valueOf(temp));
		
		temp=StringUtils.trimToEmpty(request.getParameter("page"));
		int page="".equals(temp)?0:Integer.valueOf(temp);
		temp=StringUtils.trimToEmpty(request.getParameter("pageSize"));
		int size="".equals(temp)?10:Integer.valueOf(temp);
		
		Pageable pageable=new PageRequest(page, size);
		map.put("pageable", pageable);
		map.put("pageNumber", (page-1)*size);
		map.put("pageSize", size);
		Page<FAgentProfitModel> data=balanceService.findWithPagination(map);
		if(null!=data){
			List<JSONObject> list=new ArrayList<>();
			JSONObject jsonObject=new JSONObject();
			if(!CollectionUtils.isEmpty(data.getContent())){
				jsonObject.put("totalCount", data.getTotalElements());
				for (FAgentProfitModel fAgentProfit : data.getContent()) {
					String tip=String.format("%s在%s消费￥%s", 
							fAgentProfit.getBorder()==null?"佚名":fAgentProfit.getBorder().getUser()==null?"佚名":fAgentProfit.getBorder().getUser().getNickName(),
									fAgentProfit.getShop()==null?"佚名店铺":fAgentProfit.getShop().getShopName(),
											fAgentProfit.getBorder()==null?"未知":fAgentProfit.getBorder().getTotal()==null?"未知":fAgentProfit.getBorder().getTotal()/100.00);
					JSONObject jObject=new JSONObject();
					jObject.put("commissionTitle", tip);
					jObject.put("commissionValue", String.valueOf(fAgentProfit.getCommissionAmount()));
					list.add(jObject);
				}
				jsonObject.put("rows", list);
				return new DataToCResultModel<Object>(200,"",jsonObject);
			}
			return new DataToCResultModel<Object>(100,"暂无数据",null);
		}
		return new DataToCResultModel<Object>(500,"参数错误",null);
	}
}
