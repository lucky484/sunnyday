package com.softtek.mdm.web.institution;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softtek.mdm.dao.SystemWordsDao;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.MobileNetBehaviorLogs;
import com.softtek.mdm.model.NetBehaviorLogInfoModel;
import com.softtek.mdm.service.NetBehaviorLogService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.thread.OptNetBehaviorLogsThread;

/**
 * 
 * 手机上网行为日志控制器 date: Apr 18, 2016 2:45:19 PM
 *
 * @author brave.chen
 */
@Controller
@RequestMapping(value = "/terminal/netbehavior")
public class MobileNetBehaviorLogController
{
	/**
	 * 线程池对象
	 */
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 用户service服务类
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 组织机构服务类
	 */
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 结构服务类
	 */
	@Autowired
	private StructureService structureService;
	
	/**
	 * 手机上网行为服务类
	 */
	@Autowired
	private NetBehaviorLogService netBehaviorLogService;
	
	@Autowired
	private SystemWordsDao systemWordsDao;
	
	/**
	 * 查询网页黑白名单
	 * 
	 * @param params
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receiveNetBehaviorLogs",method=RequestMethod.POST)
	@ResponseBody
	public DeviceResultModel<Object> receiveNetBehaviorLogs(@RequestBody MobileNetBehaviorLogs mobileNetBehaviorLogs,
			HttpServletRequest request, HttpServletResponse response)
	{
 		DeviceResultModel<Object> deviceResultModel = new DeviceResultModel<Object>();
		// 默认接受到即是成功
		deviceResultModel.setStatus(200);
		deviceResultModel.setMsg("success");
          
		//异步线程执行
		List<NetBehaviorLogInfoModel> netBehaviorLogInfoList = mobileNetBehaviorLogs.getNetBehaviorLogInfoList();
		OptNetBehaviorLogsThread optThread = new OptNetBehaviorLogsThread(netBehaviorLogInfoList, userService,
				organizationService, structureService, netBehaviorLogService,systemWordsDao);
		
		taskExecutor.execute(optThread);
		
		return deviceResultModel;
	}
}
