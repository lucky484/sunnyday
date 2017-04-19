package com.softtek.mdm.web.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.service.PushMobileMsgService;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

@Controller
@RequestMapping("/terminal/message")
public class TerminalMessageController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(TerminalMessageController.class);
	@Autowired
	private PushMobileMsgService pushMobileMsgService;
	
	@RequestMapping(value="/{id}",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getMessage(HttpServletRequest request,HttpServletResponse response,@PathVariable Integer id){
		
		JsonResult jsonResult = null;
		try {
			PushMobileMsgModel pushMobileMsgModel = pushMobileMsgService.queryPushMsgById(id.longValue());
			if(pushMobileMsgModel!=null){
				jsonResult=new JsonResult(MessageType.success, "获取图文消息成功", pushMobileMsgModel);
			}else{
				jsonResult=new JsonResult(MessageType.danger, "获取图文消息失败", "");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResult=new JsonResult(MessageType.danger, "获取图文消息失败", "");
		}
		return jsonResult;
	}
	
    /**
     * 根据MSGID返回html视图
     * 
     * @param request
     * @param response
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/ios/getMsgAndPic", method = RequestMethod.GET)
    public String show(HttpServletRequest request, HttpServletResponse response, Integer messageId, Model model) {
        PushMobileMsgModel pushMobileMsgModel = pushMobileMsgService.queryPushMsgById((long) messageId);
        model.addAttribute("data", pushMobileMsgModel);
        return "messageDetails/index";
    }

}
