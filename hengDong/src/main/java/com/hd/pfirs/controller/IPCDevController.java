package com.hd.pfirs.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hd.pfirs.constant.LogType_Operate;
import com.hd.pfirs.model.DeviceModel;
import com.hd.pfirs.model.IPCModel;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.service.IPCInfoService;
import com.hd.pfirs.util.CommUtil;
import com.hd.pfirs.util.logs.service.LogService;

/**
 * 前端采集设备信息的控制器
 * 
 * @author ligang.yang
 *
 */
@Controller
@RequestMapping("/ipc")
public class IPCDevController {
	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IPCDevController.class);

	@Autowired
	private IPCInfoService ipcSer;

	/**
	 * 记录到数据库日志
	 */
	@Autowired
	private LogService pLog;

	/**
	 * 时间转换
	 */
	private SimpleDateFormat sdf_bean = new SimpleDateFormat("yyyy/MM/dd");

	private DateFormat sdf_log = new SimpleDateFormat("yyyyMMddHHmmss");

	@RequestMapping(value = "equips")
	public String goTodevice(HttpServletRequest request) {

		StringBuilder sbOperCon = new StringBuilder("");
		// 日志记录
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(sdf_log.format(new Date()));
		operationLogInfo.setOperateType(LogType_Operate.type_query);
		operationLogInfo.setOperateName("跳转到设备管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(null);
		pLog.recordOperateLog(operationLogInfo);

		return "DeviceManagement";
	}

	@RequestMapping(value = "getPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countPage(HttpServletRequest request) {
		int intPage = Integer.valueOf(request.getParameter("page"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String deviceCode = (String) request.getParameter("deviceCode");
		String collectSite = (String) request.getParameter("collectSite");

		String errorCode = null;
		List<IPCModel> list = null;
		long totalCount = 0;

		try {
			// 这边开始分页
			PageHelper.startPage(intPage, pageSize);
			list = ipcSer.queryIPCInfo(deviceCode, collectSite);

			totalCount = ((Page) list).getTotal();

		} catch (Exception e) {
			errorCode = "1000";
		}
		// System.out.println(list);
		// request.setAttribute("devices", list);
		// request.setAttribute("total", total);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("currentPage", intPage);
		pageInfo.put("totalCount", totalCount);
		if (totalCount == 0) {
			pageInfo.put("totalPage", 1);
		} else {
			pageInfo.put("totalPage", (totalCount + pageSize - 1) / pageSize);
		}
		resultMap.put("devices", list);
		resultMap.put("pageInfo", pageInfo);

		StringBuilder sbOperCon = new StringBuilder("deviceCode=");
		sbOperCon.append(deviceCode);
		sbOperCon.append(", collectSite=");
		sbOperCon.append(collectSite);
		// 日志记录
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(sdf_log.format(new Date()));
		operationLogInfo.setOperateType(LogType_Operate.type_query);
		operationLogInfo.setOperateName("设备运行状态监控");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		pLog.recordOperateLog(operationLogInfo);
		return resultMap;
	}

	@RequestMapping(value = "info", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String saveIdCardMap(@RequestBody Map<String, Object> paramsMap, HttpServletRequest request) {
		try {
			ipcSer.saveIPCInfo(paramsMap);
			return "2";
		} catch (Exception e) {
			logger.error(e);
			return "-1";
		}
	}

	/**
	  * 
	  * @Description: 查询设备信息
	  * @param TODO
	  * @return Map<String,Object>
	  * @throws 
	  * @author: ligang.yang@softtek.com  
	  * @version: 2016年1月15日 下午4:34:30
	  */
	@RequestMapping(value = "queryEquipsByPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEquipsMap(HttpServletRequest request) {
		int intPage = Integer.valueOf(request.getParameter("page"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String deviceCode = (String) request.getParameter("deviceCode");
		String collectSite = (String) request.getParameter("collectSite");

		long totalCount = 0;
		List<DeviceModel> list = null;

		String errorCode = null;

		try {

			// 这边开始分页
			PageHelper.startPage(intPage, pageSize);
			list = ipcSer.queryDeviceInfo(deviceCode, collectSite);
			totalCount = ((Page) list).getTotal();

			// 时间转换
			for (DeviceModel model : list) {
				model.setInstallDateStr(this.formatDate2Str(model.getInstallDate()));
			}
		} catch (Exception e) {
			errorCode = "1000";
		}
		// 存放数据返回
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("currentPage", intPage);
		pageInfo.put("totalCount", totalCount);
		/*
		 * 算法二：（推荐）
		 * 总记录数：totalRecord
		 * 每页最大记录数：maxResult
		 * totalPage = (totalRecord + maxResult -1) / maxResult;
		 */
		if (totalCount == 0) {
			pageInfo.put("totalPage", 1);
		} else {
			pageInfo.put("totalPage", (totalCount + pageSize - 1) / pageSize);
		}
		resultMap.put("devices", list);
		resultMap.put("pageInfo", pageInfo);

		StringBuilder sbOperCon = new StringBuilder("deviceCode=");
		sbOperCon.append(deviceCode);
		sbOperCon.append(", collectSite=");
		sbOperCon.append(collectSite);
		// 日志记录
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(sdf_log.format(new Date()));
		operationLogInfo.setOperateType(LogType_Operate.type_query);
		operationLogInfo.setOperateName("设备管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		pLog.recordOperateLog(operationLogInfo);

		return resultMap;
	}

	/**
	  * 
	  * @Description: 插入一条设备信息
	  * @param      : DeviceModel model, HttpServletRequest request
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午4:23:00   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	@RequestMapping(value = "insertEquip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertEquip(@RequestBody DeviceModel model, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String deviceCode = model.getDeviceCode();
		String installDateStr = model.getInstallDateStr();

		String errorCode = null;
		try {
			model.setInstallDate(this.formatStr2Date(installDateStr));
		} catch (ParseException e1) {
			errorCode = "1000";
			logger.error("时间解析异常！");
			logger.error(e1);
		}
		try {
			if (StringUtils.isBlank(deviceCode)) {
				resultMap.put("msg", "设备号不能为空！");
				return resultMap;
			}
			Map<String, String> codes = this.getDeviceCodes();
			if (codes.containsKey(deviceCode)) {
				resultMap.put("msg", "设备号不能重复！");
				return resultMap;
			}

			ipcSer.insertEquip(model);
			resultMap.put("status", "1");
			resultMap.put("msg", "ok");
		} catch (Exception e) {
			errorCode = "1000";
			resultMap.put("msg", "error!");
		}

		StringBuilder sbOperCon = new StringBuilder("deviceCode=");
		sbOperCon.append(deviceCode);
		// 日志记录
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(sdf_log.format(new Date()));
		operationLogInfo.setOperateType(LogType_Operate.type_insert);
		operationLogInfo.setOperateName("设备管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		pLog.recordOperateLog(operationLogInfo);
		return resultMap;
	}

	/**
	  * @Description: 远程校验是否有重复数据
	  * @param      : @RequestBody String deviceCode
	  * @return     : Map<String,Object>
	  * @throws  
	  * @data       : 2016年1月26日 上午9:40:08   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	@RequestMapping(value = "queryEquipByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEquipByCode(@RequestParam("deviceCode") String deviceCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = null;
		if (StringUtils.isBlank(deviceCode)) {
			// 表示前端没有传来deviceCode, 将直接返回错误状态码-1
			msg = "-2";
		} else {
			Map<String, String> codesMap = this.getDeviceCodes();
			if (codesMap.containsKey(deviceCode)) {
				// 重复数据, 返回状态码-1
				msg = "-1";
			} else {
				// 表示成功, 该数据没有重复
				msg = "1";
			}
		}
		resultMap.put("msg", msg);
		return resultMap;
	}

	@RequestMapping(value = "queryEquipByCode2u", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEquipByCode2u(@RequestParam("deviceCode") String deviceCode,
			@RequestParam("deviceId") String deviceId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String msg = null;

		if (StringUtils.isBlank(deviceCode)) {
			// 表示前端没有传来deviceCode, 将直接返回错误状态码-1
			msg = "-2";
		} else {
			Map<String, String> codesMap = this.getDeviceCodes();
			if (codesMap.containsKey(deviceCode)) {
				String thisCode = ipcSer.queryDeviceCodeById(deviceId);
				if (deviceCode.equals(thisCode)) {
					msg = "1";
				} else {
					// 重复数据, 返回状态码-1
					msg = "-1";
				}
			} else {
				// 表示成功, 该数据没有重复
				msg = "1";
			}
		}
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**
	  * 
	  * @Description: 更新一条已有的设备信息记录
	  * @param      : DeviceModel model, HttpServletRequest request
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午4:23:00   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	@RequestMapping(value = "updateEquip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEquip(@RequestBody DeviceModel model, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String deviceCode = model.getDeviceCode();
		String deviceId = String.valueOf(model.getDeviceId());
		String installDateStr = model.getInstallDateStr();

		String errorCode = null;
		try {
			model.setInstallDate(this.formatStr2Date(installDateStr));
		} catch (ParseException e1) {
			errorCode = "1000";
			logger.error("时间解析异常！");
			logger.error(e1);
		}
		try {
			if (StringUtils.isBlank(deviceCode) || StringUtils.isBlank(deviceId)) {
				resultMap.put("msg", "设备号不能为空！");
				return resultMap;
			}

			Map<String, String> codes = this.getDeviceCodes();
			String thisCode = ipcSer.queryDeviceCodeById(deviceId);

			if (StringUtils.isBlank(thisCode)) {
				resultMap.put("msg", "没有该设备！");
				return resultMap;
			}
			if (!deviceCode.equals(thisCode) && codes.containsKey(deviceCode)) {
				resultMap.put("msg", "设备号不能重复！");
				return resultMap;
			}

			model.setUpdateName("admin");
			String enable = model.getEnableStatus();
			if (!StringUtils.isBlank(enable) && enable.equals("1")) {
				Map<String, Object> initDevParams = new HashMap<String, Object>();
				initDevParams.put("DeviceCode", deviceCode);
				initDevParams.put("CreateName", CommUtil.getUserName(request));
				ipcSer.saveIPCInfo(initDevParams);
			}
			ipcSer.updateEquip(model);

			resultMap.put("status", "1");
			resultMap.put("msg", "ok");
		} catch (Exception e) {
			errorCode = "1000";
			resultMap.put("msg", "error");
		}

		StringBuilder sbOperCon = new StringBuilder("deviceCode=");
		sbOperCon.append(deviceCode);

		// 日志记录
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(sdf_log.format(new Date()));
		operationLogInfo.setOperateType(LogType_Operate.type_update);
		operationLogInfo.setOperateName("设备管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		pLog.recordOperateLog(operationLogInfo);
		return resultMap;
	}

	/**
	  * @Description: 更新一条设备信息为删除状态
	  * @param      : DeviceModel model
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午4:24:55   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	@RequestMapping(value = "deleteEquip", method = RequestMethod.POST)
	@ResponseBody
	public void deleteEquip(HttpServletRequest request) {
		String deviceId = request.getParameter("deviceId");
		String deleteStatus = "1";
		String updateName = CommUtil.getUserName(request);

		String errorCode = null;
		try {
			ipcSer.deleteEquip(deviceId, deleteStatus, updateName);
		} catch (Exception e) {
			errorCode = "1000";
		}

		StringBuilder sbOperCon = new StringBuilder("deviceId=");
		sbOperCon.append(deviceId);

		// 日志记录
		OperationLogInfo operationLogInfo = new OperationLogInfo();
		operationLogInfo.setUserName(updateName);
		operationLogInfo.setOperateTime(sdf_log.format(new Date()));
		operationLogInfo.setOperateType(LogType_Operate.type_delete);
		operationLogInfo.setOperateName("设备管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		pLog.recordOperateLog(operationLogInfo);
	}

	/**
	  * @Description: 查询所有的辖区编码和辖区域名
	  * @param      : 
	  * @return     : Map<String, Object>
	  * @throws  
	  * @data       : 2016年1月18日 下午4:24:55   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	@RequestMapping(value = "queryRegions", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRegions() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, String>> list = ipcSer.queryRegions();
		resultMap.put("regions", list);
		return resultMap;
	}

	// 看看是否有重复的设备号
	private Map<String, String> getDeviceCodes() {
		List<String> list = ipcSer.queryDeviceCodes();
		Map<String, String> map = new HashMap<String, String>();
		for (String deviceCode : list) {
			map.put(deviceCode, deviceCode);
		}
		return map;
	}

	/**
	  * @Description: 解析字符串为时间(yyyy/MM/dd)
	  * @param      : String str
	  * @return     : Date
	  * @throws  
	  * @data       : 2016年1月28日 上午11:00:56   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	private Date formatStr2Date(String str) throws ParseException {
		if (!StringUtils.isBlank(str)) {
			return this.sdf_bean.parse(str);
		} else {
			return null;
		}
	}

	/**
	  * 
	  * @Description: 将date转为字符串(yyyy/MM/dd)
	  * @param      : Date date
	  * @return     : String
	  * @throws  
	  * @data       : 2016年1月28日 上午11:02:25   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	private String formatDate2Str(Date date) {
		if (date != null) {
			return this.sdf_bean.format(date);
		} else {
			return null;
		}
	}

}
