package com.f2b2c.eco.controller.platform.f2b;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.platform.FMenuModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.platform.FMenuService;
import com.f2b2c.eco.status.StorageStatus;

@Controller
@RequestMapping(value="/farm/fMenu")
public class FMenuController {
       
	   @Autowired
	   private FMenuService fMenuService;
	   
	   /**
	    * 根据用户id查询出该用户有哪些菜单权限,参数从session获取，用户在登录时将信息绑定到session中
	    * @param request
	    * @param session
	    * @return
	    */
	   @RequestMapping(value="/getMenuByUserId",method=RequestMethod.POST)
	   @ResponseBody
	   public Map<String,Object> getMenuByUserId(HttpServletRequest request,HttpSession session){
		   Map<String,Object> map = new HashMap<String, Object>();
		   FUserModel user = (FUserModel) session.getAttribute(StorageStatus.USER_INSESSION.name());
		   if(user != null){
			   List<FMenuModel> list = fMenuService.queryMenuByUserId(Integer.valueOf(user.getId()));
			   map.put("list", list);
		   }
		   return map;
	   }
}
