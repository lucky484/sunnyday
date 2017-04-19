package com.softtek.mdm.web.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.DeviceLegalRecordModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.DeviceRuleItemRecordModel;
import com.softtek.mdm.model.DeviceRuleItemRelationModel;
import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRecordModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.DeviceNetworkStatusService;
import com.softtek.mdm.service.DeviceRuleItemRelationService;
import com.softtek.mdm.service.DeviceRuleOperationItemRelationService;
import com.softtek.mdm.service.DeviceRuleService;
import com.softtek.mdm.service.EmailSendService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.NameListService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.status.DeviceRuleStatus;
import com.softtek.mdm.status.DeviceRuleValueStatus;

/**
 * 用于处理和客户端规则有关的信息
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/terminal/rule")
public class DeviceRuleOperationController {

	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private DeviceRuleService deviceRuleService;
	@Autowired
	private DeviceRuleItemRelationService deviceRuleItemRelationService;
	@Autowired
	private NameListService nameListService;
	@Autowired
	private DeviceRuleOperationItemRelationService deviceRuleOperationItemRelationService;
	@Autowired
	private DeviceManagerService deviceManagerService;
	@Autowired
	private DeviceNetworkStatusService deviceNetworkStatusService;
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private EmailSendService emailSendService;
	
	private Logger logger=Logger.getLogger(DeviceRuleOperationController.class);

	/**
	 * 上报设备信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public @ResponseBody void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String deviceLegalStr = StringUtils.trimToEmpty(request.getParameter("deviceLegal"));
		String deviceLegalRecordListStr =  StringUtils.trimToEmpty(request.getParameter("deviceLegalRecordList"));
		DeviceLegalListModel deviceLegalList = (DeviceLegalListModel) JSONObject
				.toBean(JSONObject.fromObject(deviceLegalStr), DeviceLegalListModel.class);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<DeviceLegalRecordModel> deviceLegalRecordList = (List<DeviceLegalRecordModel>) JSONArray
				.toList(JSONArray.fromObject(deviceLegalRecordListStr), DeviceLegalRecordModel.class);
		DeviceBasicInfoModel basicInfo = null;
		if("ios".equals(deviceLegalList.getDeviceBasicInfo().getDevice_type().toLowerCase())){
			basicInfo = deviceBasicInfoService.findByIosUuid(deviceLegalList.getDeviceBasicInfo().getIosUuid());
		}else{
			// 保存设备规则检测结果
			basicInfo = deviceBasicInfoService.findBySN(deviceLegalList.getDeviceBasicInfo().getSn());
		}
		
		if(deviceLegalList.getDeviceRule()!=null){
			List<DeviceRuleOperationItemRelationModel> deviceRuleOperationList= deviceRuleOperationItemRelationService.findAllByRuleId(deviceLegalList.getDeviceRule().getId());
			if(CollectionUtils.isNotEmpty(deviceRuleOperationList)){
				//查看是否有用邮件发送
				String cusContent=null;
				for (int i=0;i<deviceRuleOperationList.size();i++) {
					DeviceRuleOperationItemRelationModel deviceRuleOperationItemRelation =deviceRuleOperationList.get(i);
					if(deviceRuleOperationItemRelation.getDeviceRuleOperationRecord().getType()==DeviceRuleStatus.INFORM.getDisplayValue()&&
							deviceRuleOperationItemRelation.getDeviceRuleOperationRecord().getCondition()==DeviceRuleValueStatus.EMAIL_INFORM.getDisplayValue()){
						cusContent=deviceRuleOperationItemRelation.getDeviceRuleOperationRecord().getValue();
						break;
					}
				}
				if(cusContent!=null){
					//发送邮件给系统管理员
					DeviceRuleModel deviceRule=deviceRuleService.findOne(deviceLegalList.getDeviceRule().getId());
					if(null!=deviceRule){
						ManagerModel manager=managerService.findOne(deviceRule.getUpdateBy());
						if(basicInfo!=null && manager != null && StringUtils.isNotEmpty(manager.getEmail())){
							List<String> strList=new ArrayList<String>();
							try {
								String email = manager.getEmail();
								strList.add(email);
								emailSendService.sendAgainstRuleMail(basicInfo.getUser().getUsername(), basicInfo.getDevice_name(), deviceRule.getName(), null, cusContent, strList);
							} catch (AddressException e) {
								logger.error("Method sendAgainstRuleMail invoked by emailSendService cause AddressException error and the reason is: " + e.getMessage());
							} catch (MessagingException e) {
								logger.error("Method sendAgainstRuleMail invoked by emailSendService cause MessagingException error and the reason is: " + e.getMessage());
							}
						}
					}
				}
			}
		}
		
		Map<String, Object> maps=new HashMap<String, Object>();
		if (basicInfo != null) {
			deviceLegalList.getDeviceBasicInfo().setId(basicInfo.getId());
			DeviceBasicInfoModel temp=new DeviceBasicInfoModel();
			temp.setId(basicInfo.getId());
			temp.setLast_collection_time(new Date());
			temp.setIrr_status(deviceLegalList.getStatus()!=null?deviceLegalList.getStatus()>0?1:0:0);
			maps.put("deviceBasicInfo", temp);
		}
		maps.put("deviceLegalList", deviceLegalList);

		if (CollectionUtils.isNotEmpty(deviceLegalRecordList)) {
			for (int i = 0; i < deviceLegalRecordList.size(); i++) {
				deviceLegalRecordList.get(i).getDeviceBasicInfo().setId(basicInfo.getId());
				deviceLegalRecordList.get(i).setExtra(deviceLegalRecordList.get(i).getDeviceRuleItemRecord().getValue());
			}
			maps.put("deviceLegalRecordList", deviceLegalRecordList);
			// 查询该设备规则对于的操作
			List<DeviceRuleOperationItemRelationModel> operationList = deviceRuleOperationItemRelationService
					.findAllByRuleId(deviceLegalRecordList.get(0).getDeviceRule().getId());
			maps.put("operationList", operationList);
		}
		try {
			terminalService.report(maps);
		} catch (Exception e) {
			logger.error("Method report invoked by terminalService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 获取testing
	 * 
	 * @param rid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/testing", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> testing(Integer rid, String sn, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		DeviceRuleModel test_rule = deviceRuleService.findOne(rid);
		data.put("deviceRule", JSONObject.fromObject(test_rule).toString());

		List<DeviceRuleItemRelationModel> itemRelationlist = null;
		// 保存规则（黑白名单列表 json化字符串）
		try {
			itemRelationlist = deviceRuleItemRelationService.findAllByRuleId(rid);
		} catch (Exception e) {
			logger.error("Method report invoked by terminalService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (itemRelationlist != null && itemRelationlist.size() > 0) {
			for (int i = 0; i < itemRelationlist.size(); i++) {
				DeviceRuleItemRecordModel item = itemRelationlist.get(i).getDeviceRuleItemRecord();
				if (item.getType().equals(DeviceRuleStatus.APPLICATION_BLACK_AND_WHITE_LIST.getDisplayValue())) {
					// 应用黑白名单
					String appVals = nameListService.selectByName(item.getCondition(), test_rule.getOrganization().getId(),
							item.getValue());
					item.setValue(appVals);
				}
				if (item.getType().equals(DeviceRuleStatus.IMSI_VERIFY.getDisplayValue())) {
					DeviceBasicInfoModel basicInfo = deviceBasicInfoService.findBySN(sn);
					if (basicInfo != null) {
						DeviceNetworkStatusModel net = deviceNetworkStatusService.findOne(basicInfo.getId());
						item.setValue(net == null ? "" : net.getSim_number());
					}

				}
			}
			data.put("deviceRuleItemRelationList", JSONArray.fromObject(itemRelationlist).toString());
		}

		// 保存操作（策略列表 json化字符串）
		List<DeviceRuleOperationItemRelationModel> operationItemRelationlist =null;
		try {
			operationItemRelationlist = deviceRuleOperationItemRelationService
					.findAllByRuleId(rid);
		} catch (Exception e) {
			logger.error("Method findAllByRuleId invoked by deviceRuleOperationItemRelationService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (operationItemRelationlist != null && operationItemRelationlist.size() > 0) {
			for (int i = 0; i < operationItemRelationlist.size(); i++) {
				DeviceRuleOperationItemRecordModel item = operationItemRelationlist.get(i)
						.getDeviceRuleOperationRecord();
				if (item.getType().equals(DeviceRuleStatus.STRATEGY_CHANGE.getDisplayValue())) {
					if (item.getCondition().equals(DeviceRuleValueStatus.OS_ANDROID.getDisplayValue())) {
						AndroidDevicePolicy android = deviceManagerService
								.findPolicyByName(test_rule.getOrganization().getId(), item.getValue());
						if (android != null) {
							String androidStr = JSONObject.fromObject(android).toString();
							item.setValue(androidStr);
						}
					}
				}
			}
			data.put("deviceRuleOperationItemRelationList", JSONArray.fromObject(operationItemRelationlist).toString());
		}
		return data;
	}
	
	/**
	 * 从服务器获取设备对应的设备规则id集合
	 * @param sn
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public @ResponseBody DeviceResultModel<List<Integer>> findRules(String sn, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		DeviceResultModel<List<Integer>> deviceResult=new DeviceResultModel<List<Integer>>();
		if(null!=sn){
			DeviceBasicInfoModel device=deviceBasicInfoService.findBySN(sn);
			if(null!=device){
				List<Integer> list=deviceRuleService.findRuleIdsByDeviceId(device.getId());
				if(list!=null){
					deviceResult.setStatus(200);
					deviceResult.setData(list);
					return deviceResult;
				}
				else
					return deviceResult;
			}
		}
		return deviceResult;
	}
}
