package com.f2b2c.eco.controller.platform.f2b;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.share.dto.UserDto;
import com.f2b2c.eco.status.MessageType;
import com.f2b2c.eco.util.Pbkdf2Encryption;

@Controller("personalController")
@RequestMapping("/farm/personal")
public class PersonalController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);
	@Autowired
	private FUserService fUserService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	private String index(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
		
		FUserModel fUserModel = (FUserModel) session.getAttribute("USER_INSESSION");
		model.addAttribute("personInfo", fUserModel);
		return "platform/personal";
		
	}
	
	@RequestMapping(value="/updatePass",method=RequestMethod.GET)
	private String password(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
		
		FUserModel fUserModel = (FUserModel) session.getAttribute("USER_INSESSION");
		model.addAttribute("personInfo", fUserModel);
		return "platform/password";
		
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ResponseBody
	private DTOResult update(HttpServletRequest request,HttpServletResponse response,UserDto user){
		
		DTOResult result = new DTOResult();
		FUserModel userModel = fUserService.findOneById(user.getId());
		try {
			if(Pbkdf2Encryption.checkPassword(user.getPassword(), userModel.getPassword())){
				FUserModel newUser = new FUserModel();
				newUser.setId(userModel.getId());
				newUser.setPassword(Pbkdf2Encryption.encode(user.getNewPassword()));
				fUserService.updateFUserModelInfo(newUser);
				result.setType(MessageType.success);
				result.setMessage("修改用户密码成功");
			}else{
				result.setType(MessageType.error);
				result.setMessage("原密码输入错误");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
		return result;
	}
	
}
