package com.softtek.mdm.web.institution;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ClientConfigModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.service.ClientConfigService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;

@Controller
@RequestMapping(value = "/institution/clientConfig")
public class ClientConfigController {
       
	   @Autowired
	   private ClientConfigService clientConfigService;
	   
	   @Autowired
	   private TaskExecutor taskExecutor;
	   
	   private final String topic = "androidAll";
	   
	   /**
	    * 
	    * @param request
	    * @param session
	    * @param model
	    * @return
	    */
	   @Link(family = "institution", label = "institution.clientConfig.index.label", parent = "web.institution.homecontroller.index.link.label", belong="web.institution.system.index.link.belong")
	   @RequestMapping(method = RequestMethod.GET)
	   public String index(HttpServletRequest request,HttpSession session,Model model){
		   OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		   ClientConfigModel clientConfig = clientConfigService.queryClientConfig(organization.getId());
		   model.addAttribute("clientConfig", clientConfig);
		   return "institution/clientConfig/index";
	   }
	   
	   /**
	    * 
	    * @param request
	    * @param clientConfig
	    * @param session
	    * @return
	    */
	   @RequestMapping(value="/insertClientConfig" ,method=RequestMethod.POST)
	   @ResponseBody
	   public Map<String,Object> insertClientConfig(HttpServletRequest request,ClientConfigModel clientConfig,HttpSession session){
		   Map<String,Object> map = new HashMap<String, Object>();
           Map<String,String> extra = new HashMap<String, String>();
		   OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		   ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		   clientConfig.setCreateBy(manager.getId());
		   clientConfig.setUpdateBy(manager.getId());
		   clientConfig.setOrgId(organization.getId());
		   int result = 0;
		   if(clientConfig.getId() == null){
			   result = clientConfigService.insertClientConfig(clientConfig);
		   }else{
			   result = clientConfigService.updateClientConfig(clientConfig);
		   }
		   if(result>0){
			   extra.put("clientConfig",JSONObject.fromObject(clientConfig).toString());
			   MqProducerThread mqProducerThread = new MqProducerThread(topic, null, null, 2, extra);
			   taskExecutor.execute(mqProducerThread);
		   }
		   map.put("result", result);
		   return map;
	   }
}
