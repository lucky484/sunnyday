package com.softtek.mdm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.bean.IosPolicyVersionBean;
import com.softtek.mdm.bean.IsNetworkAvailableBean;
import com.softtek.mdm.dao.AndroidBlackListDao;
import com.softtek.mdm.dao.AndroidDeviceDepartmentDao;
import com.softtek.mdm.dao.AndroidDevicePolicyDao;
import com.softtek.mdm.dao.AndroidDeviceUsersDao;
import com.softtek.mdm.dao.AndroidDeviceVirtualGroupDao;
import com.softtek.mdm.dao.AndroidPolicyListDao;
import com.softtek.mdm.dao.AppListDao;
import com.softtek.mdm.dao.ApplicationNameListDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.IosBlackListDao;
import com.softtek.mdm.dao.IosDevicePolicyDao;
import com.softtek.mdm.dao.IosPolicyListDao;
import com.softtek.mdm.dao.IosPolicyUserDao;
import com.softtek.mdm.dao.IosPolicyVirtualDao;
import com.softtek.mdm.dao.IosWifiConfigureDao;
import com.softtek.mdm.dao.IospolicyDepartmentDao;
import com.softtek.mdm.dao.NameListDao;
import com.softtek.mdm.dao.NetBehaviorDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.dao.WifiConfigureDao;
import com.softtek.mdm.model.AndroidBlackList;
import com.softtek.mdm.model.AndroidDeviceDepartment;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.AndroidDeviceUsers;
import com.softtek.mdm.model.AndroidDeviceVirtualGroup;
import com.softtek.mdm.model.AndroidPolicyList;
import com.softtek.mdm.model.AppList;
import com.softtek.mdm.model.ApplicationNameList;
import com.softtek.mdm.model.BlackWhiteListUrl;
import com.softtek.mdm.model.CollectVirtualModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.IosBlackList;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.model.IosPolicyList;
import com.softtek.mdm.model.IosPolicyUser;
import com.softtek.mdm.model.IosPolicyVirtual;
import com.softtek.mdm.model.IosWifiConfigure;
import com.softtek.mdm.model.IospolicyDepartment;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NameList;
import com.softtek.mdm.model.NetBehaviorBlackWhiteList;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.WifiConfigure;
import com.softtek.mdm.queue.MqttQueue;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.DeviceTypeStatus;
import com.softtek.mdm.status.DirectionStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqttPushThread;
import com.softtek.mdm.thread.PushIosVersionThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.MDMProtocolUtils;
import com.softtek.mdm.util.UserPolicyComparable;
import com.softtek.mdm.util.XMLUtil;
import com.softtek.mdm.util.Constant.ElementType;
import com.softtek.mdm.util.Constant.IElementPosition;
import com.softtek.mdm.util.Constant.IEncryptionResult;
import com.softtek.mdm.util.Constant.SplitSymbol;
import com.softtek.mdm.util.Constant.IIosMetadata.IGeneral.IPassCode;
import com.softtek.mdm.util.Constant.IIosMetadata.IGeneral.IWifi;
import com.softtek.mdm.util.Constant.IIosMetadata.IGeneral.IWifiContent;
import com.softtek.mdm.util.Constant.IIosMetadata.IGeneral.IWifi.IEncryptionType;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 设备策略
 * @author jane.hui
 */
@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private static Logger logger = Logger.getLogger(DeviceServiceImpl.class);
	
	@Autowired
	private AndroidDevicePolicyDao androidDevicePolicyDao;
	
	@Autowired
	private AndroidDeviceUsersDao androidDeviceUsersDao;

	@Autowired
	private AndroidDeviceVirtualGroupDao androidDeviceVirtualGroupDao;
	
	@Autowired
	private NameListDao nameListDao;
	
	@Autowired
	private AndroidPolicyListDao androidPolicyListDao;
	
	@Autowired
	private AndroidDeviceDepartmentDao androidDeviceDepartmentDao;
	
	@Autowired
	private NetBehaviorDao netBehaviorDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Autowired
	private ApplicationNameListDao applicationNameListDao;
	
	@Autowired
	private AppListDao appListDao;

	@Autowired
	private AndroidBlackListDao androidBlackListDao;
	
	@Autowired
	private WifiConfigureDao wifiConfigureDao;
	
	@Autowired
	private IosDevicePolicyDao iosDevicePolicyDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IosWifiConfigureDao iosWifiConfigureDao;
	
/*	@Autowired
	private WebclipDao webclipDao;
	
	@Autowired
	private FilterUrlDao filterUrlDao;*/
	
	@Autowired
	private IosPolicyUserDao iosPolicyUserDao;
	
	@Autowired
	private IospolicyDepartmentDao iospolicyDepartmentDao;
	
	@Autowired
	private IosPolicyVirtualDao iosPolicyVirtualDao;
	
	@Autowired
	@Qualifier("iosDeviceServiceNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Autowired
	private IosPolicyListDao iosPolicyListDao;
	
	@Autowired
	private IosBlackListDao iosBlackListDao;
	
	/**
	 * 线程池对象
	 */
	@Autowired
	private TaskExecutor taskExecutor;
	
    /**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSourceService;
	
	/**
	 * 获取设备策略列表数据
	 */
	@Override
	public Page getDevicePolicyList(ManagerModel user,Integer orgId,String policytype,String objName,Integer begin, Integer num) {
		Page p = new Page();
		DataGridModel params = new DataGridModel();
		params.setBegin(begin);
		params.setNum(num);
		params.getParams().put("orgId", orgId);
		if(StringUtil.isNotBlank(objName)){
			params.getParams().put("name", objName);
		}
		params.getParams().put("policytype", policytype);
		params.getParams().put("user", user);
		int total = androidDevicePolicyDao.getDevicePolicySize(params);
		p.setData(androidDevicePolicyDao.getDevicePolicyList(params));
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	/**
	 * 获取设备策略列表数据
	 */
	@Override
	public Page getDeviceListByPolicyId(Integer orgId,String policyId,String type,Integer begin, Integer num) {
		Page p = new Page();
		DataGridModel params = new DataGridModel();
		params.setBegin(begin);
		params.setNum(num);
		params.getParams().put("orgId", orgId);
        params.getParams().put("policyId", policyId);
        int total = 0;
        if(StringUtil.isNotBlank(type)&&Constant.YES.equals(type)){
        	total = deviceBasicInfoDao.getDeviceIosSizeByPolicy(params);
    		p.setData(deviceBasicInfoDao.getIosDeviceListByPolicy(params));
        } else {
        	total = deviceBasicInfoDao.getDeviceSizeByPolicy(params);
    		p.setData(deviceBasicInfoDao.getDeviceListByPolicy(params));
        }
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}
	
	/**
	 * 设备策略删除功能
	 * @throws ParseException 
	 */
	@Override
	public ResultDTO deletePolicy(HttpServletRequest request){
		ResultDTO result = new ResultDTO();
		String id = request.getParameter("id");
			// 表示该策略是否启用或者禁用
			// 目前"1"代表是启用
			String isEnable = Constant.YES;
			// 存放策略id推送到极光服务器
			Set<Integer> policySet = new LinkedHashSet<Integer>();
			HttpSession session = request.getSession();
			Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			if (orgObj == null) {
				result.type = BaseDTO.WARNING;
				result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.invalid.lable",null, LocaleContextHolder.getLocale());
				return result;
			}
			// 判断设备策略主键id存在
			if (StringUtil.isNotBlank(id)) {
				Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
				if (obj == null) {
					result.type = BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.invalid.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
				Integer intId = Integer.valueOf(id);
				AndroidDevicePolicy policy = androidDevicePolicyDao.selectByPrimaryKey(intId);
				if(policy!=null){
					request.setAttribute("name", policy.getName());
					// 判断该测试禁用还是启用
					if(!Constant.YES.equals(policy.getIsEnable())){
						isEnable = Constant.NO;
					}
				}
				OrganizationModel organization = (OrganizationModel)orgObj;
				ManagerModel manager = (ManagerModel) obj;
				// Android设备策略授权用户关联表(已删除)
				List<AndroidDeviceUsers> delDeviceUserList = new ArrayList<AndroidDeviceUsers>();
				// Android设备策略授权用户对象(已删除的)
				AndroidDeviceUsers delAndroidDeviceUsers;
				DataGridModel delParams = null;
				// 获取将删除的用户
				List<Integer> userList = androidDeviceUsersDao.selectUserByIds(intId);
				// 存储当前选中的用户id
				for (Integer userId : userList) {
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("userId", userId);
					// 根据用户id获取以前的策略列表数据
					List<AndroidDeviceUsers> tempUserList = androidDeviceUsersDao.selectPolicyIdByUserId(delParams);
					if(tempUserList.size()>0){// 如果以前策略记录存在，则获取最新一条
						AndroidDeviceUsers tempDeviceUser = tempUserList.get(0);
						delDeviceUserList.add(tempDeviceUser);
						policySet.add(tempDeviceUser.getAndroidDevicePolicyId());
					} else {// 如果以前策略记录不存在，则获取默认策略
						delAndroidDeviceUsers = new AndroidDeviceUsers();
						delAndroidDeviceUsers.setId(CommUtil.getPrimaryKey()+userId);
						delAndroidDeviceUsers.setAndroidDevicePolicyId(0);
						delAndroidDeviceUsers.setCreateDate(new Date());
						delAndroidDeviceUsers.setUpdateDate(getDefaultDate());
						delAndroidDeviceUsers.setUsersId(userId);
						delDeviceUserList.add(delAndroidDeviceUsers);
						policySet.add(0);
					}
				}
				// 删除关联的wifi配置数据
				wifiConfigureDao.updateWifiConfigureByPolicyId(intId);
				// 删除授权用户关联表对象
				androidDeviceUsersDao.updateByUsersId(intId);
				// Android设备策略授权部门关联表(删除的)
				List<AndroidDeviceDepartment> delDeviceDepartmentList = new ArrayList<AndroidDeviceDepartment>();
				// Android设备策略授权部门对象(删除的)
				AndroidDeviceDepartment delAndroidDeviceDepartment = null;
				List<Integer> departIdList = androidDeviceDepartmentDao.selectDepartmentById(intId);
				// 存储要删除的部门id
				List<Integer> delDepartIds = new ArrayList<Integer>();
				for (Integer departId : departIdList) {
					delDepartIds.add(departId);
					delParams = new DataGridModel();
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("id", id);
					delParams.getParams().put("departId", departId);
					List<AndroidDeviceDepartment> tempDepartList = androidDeviceDepartmentDao.selectPolicyByDepartId(delParams);
					if(tempDepartList.size()>0){
						AndroidDeviceDepartment tempDeviceDepartment = tempDepartList.get(0);
						delDeviceDepartmentList.add(tempDeviceDepartment);
						policySet.add(tempDeviceDepartment.getAndroidDevicePolicyId());
					} else {
						delAndroidDeviceDepartment = new AndroidDeviceDepartment();
						delAndroidDeviceDepartment.setAndroidDevicePolicyId(0);
						delAndroidDeviceDepartment.setOrgStructureId(departId);
						delAndroidDeviceDepartment.setCreateDate(new Date());
						delAndroidDeviceDepartment.setUpdateDate(getDefaultDate());
						delDeviceDepartmentList.add(delAndroidDeviceDepartment);
						policySet.add(0);
					}
				}
				androidDeviceDepartmentDao.updateByDepartId(intId);
				// Android设备策略授权虚拟组关联表(删除的)
				List<AndroidDeviceVirtualGroup> delDeviceVirtualGroupList = new ArrayList<AndroidDeviceVirtualGroup>();
				// Android设备策略授权虚拟组对象(删除的)
				AndroidDeviceVirtualGroup delAndroidDeviceVirtualGroup = null;
				// 存储将要删除的虚拟组id
				List<Integer> delVirtualIds = new ArrayList<Integer>();
				List<Integer> virtualIdsList = androidDeviceVirtualGroupDao.selectVirtualById(intId);
				for (Integer virtualId : virtualIdsList) {
					delVirtualIds.add(virtualId);
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("virtualId", virtualId);
					List<AndroidDeviceVirtualGroup> tempVirutalList = androidDeviceVirtualGroupDao.selectPolicyIdByVirtualId(delParams);
					if(tempVirutalList.size()>0){
						AndroidDeviceVirtualGroup tempVirtual = tempVirutalList.get(0);
						delDeviceVirtualGroupList.add(tempVirtual);
						policySet.add(tempVirtual.getAndroidDevicePolicyId());
					} else {
						delAndroidDeviceVirtualGroup = new AndroidDeviceVirtualGroup();
						delAndroidDeviceVirtualGroup.setAndroidDevicePolicyId(0);
						delAndroidDeviceVirtualGroup.setVirtualGroupId(virtualId);
						delAndroidDeviceVirtualGroup.setCreateDate(new Date());
						delAndroidDeviceVirtualGroup.setUpdateDate(getDefaultDate());
						delDeviceVirtualGroupList.add(delAndroidDeviceVirtualGroup);
						policySet.add(0);
					}
				}
				// 更新删除Android虚拟组对象
				androidDeviceVirtualGroupDao.updateByVirtualGroupId(intId);
                // 更新删除黑名单列表
				androidPolicyListDao.updateByNamelistId(intId);
				AndroidDevicePolicy adp = new AndroidDevicePolicy();
				adp.setId(Integer.valueOf(id));
				adp.setDeleteTime(new Date());
				adp.setUpdateDate(new Date());
				adp.setUpdateUser(manager.getId());
				int count = androidDevicePolicyDao.updateByPrimaryKeySelective(adp);
				if (count == 0) {
					result.type = BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.failed.lable",null, LocaleContextHolder.getLocale());
					return result;
				} 
				// 当该策略是启用的时候 需极光推送数据
				if(Constant.YES.equals(isEnable)){	
                    List<AndroidDevicePolicy> jpushList = getJpushList(id, organization.getId(), delDepartIds, delDeviceDepartmentList,
                    		delVirtualIds, policySet, manager.getId(), delDeviceVirtualGroupList, delDeviceUserList);
					// 推送用户策略
    				if(jpushList.size()>0){
    					jpushUserPolicy(null,null,jpushList,null);
    				}
				}
			} else {
				logger.error("device policy parameter id is empty");
				result.type = BaseDTO.DANGER;
				result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.failed.lable",null, LocaleContextHolder.getLocale());
				return result;
			}
		result.type = BaseDTO.SUCCESS;
		result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.success.lable",null, LocaleContextHolder.getLocale());
		return result;
	}

	/**
	 * 设备策略功能
	 * tag 1.启用 0.禁用
	 * @throws ParseException 
	 */
	@Override
	public ResultDTO enablePolicy(HttpServletRequest request){
		ResultDTO result = new ResultDTO();
		String id = request.getParameter("id");
		String tag = request.getParameter("tag");
			// 判断设备策略主键id存在
			if (StringUtil.isNotBlank(id)&&StringUtil.isNotBlank(tag)) {
				Integer intID = Integer.valueOf(id.toString());
				HttpSession session = request.getSession();
				Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
				if (obj == null) {
					result.type = BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.invalid.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
				Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
				if (orgObj == null) {
					result.type = BaseDTO.WARNING;
					result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.invalid.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
				ManagerModel manager = (ManagerModel) obj;
				OrganizationModel organization = (OrganizationModel)orgObj;
				AndroidDevicePolicy adp = new AndroidDevicePolicy();
				adp.setId(intID);
				adp.setIsEnable(tag);
				adp.setUpdateDate(new Date());
				adp.setUpdateUser(manager.getId());
				int count = androidDevicePolicyDao.updateByPrimaryKeySelective(adp);
				if (count == 0) {
					if(Constant.YES.equals(tag)){
						result.type = BaseDTO.DANGER;
						result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
						return result;
					} else {
						result.type =  BaseDTO.DANGER;
						result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
						return result;
					}
				}
				AndroidDevicePolicy policy = androidDevicePolicyDao.selectByPrimaryKey(intID);
				request.setAttribute("name", policy.getName());
				// 启用这条设备策略
				if(Constant.YES.equals(tag)){
					// 存放用户id推送到极光服务器
					Set<Integer> userSet = new LinkedHashSet<Integer>();
					// 根据出了id获取将要启用的授权用户
					List<Integer> userList = androidDeviceUsersDao.selectUserByIds(intID);
					// 根据策略id获取将要启用的授权部门
					List<Integer> departIdList = androidDeviceDepartmentDao.selectDepartmentById(intID);
					// 根据策略id获取将要启用的授权虚拟组
					List<Integer> virtualIdsList = androidDeviceVirtualGroupDao.selectVirtualById(intID);
					// 设置Android授权用户到UserSet
					userSet.addAll(userList);
					List<AndroidDeviceDepartment> departList =new ArrayList<AndroidDeviceDepartment>();
					AndroidDeviceDepartment department = null;
					// 根据部门获取用户id
					for (Integer integer : departIdList) {
						department = new AndroidDeviceDepartment();
						department.setOrgStructureId(integer);
						departList.add(department);
					}
					// 根据虚拟组获取用户id
					List<AndroidDeviceVirtualGroup> virtualList = new ArrayList<AndroidDeviceVirtualGroup>();
					AndroidDeviceVirtualGroup virutalGroup = null;
					for (Integer integer : virtualIdsList) {
						virutalGroup = new AndroidDeviceVirtualGroup();
						virutalGroup.setVirtualGroupId(integer);
						virtualList.add(virutalGroup);
					}
					// 根据部门和虚拟组获取对应的用户设置到userSet
					Set<Integer> getUserSet = getCurrentUserIds(departList, virtualList, organization.getId());
                    userSet.addAll(getUserSet);
					DataGridModel params = new DataGridModel();
					params.getParams().put("policyId", policy.getId());
					policy.setUrlList(netBehaviorDao.selectBWUrlListByIdList(params));
					// 极光推送消息到手机端
					result.userSet = userSet;
					result.currentPolicy = policy;
					jpushUserPolicy(userSet,policy,null,null);
				} else {// 禁用这条设备策略
					Set<Integer> policySet = new LinkedHashSet<Integer>();
					// =====================从AndroidDeviceUsers表捞取以前的对应策略================================
					// Android设备策略授权用户关联表(禁用的)
					List<AndroidDeviceUsers> disableDeviceUserList = new ArrayList<AndroidDeviceUsers>();
					// Android设备策略授权用户对象(禁用的)
					AndroidDeviceUsers disableAndroidDeviceUsers;
					DataGridModel disableParams = null;
					// 获取将禁用的用户
					List<Integer> userList = androidDeviceUsersDao.selectUserByIds(intID);
					// 获取当前策略所对应的授权用户
					for (Integer userId : userList) {
						disableParams = new DataGridModel();
						disableParams.getParams().put("id", id);
						disableParams.getParams().put("orgId", organization.getId());
						disableParams.getParams().put("userId", userId);
						// 根据用户id获取以前的策略列表数据
						List<AndroidDeviceUsers> tempUserList = androidDeviceUsersDao.selectPolicyIdByUserId(disableParams);
						if(tempUserList.size()>0){// 如果以前策略记录存在，则获取最新一条
							AndroidDeviceUsers tempDeviceUser = tempUserList.get(0);
							disableDeviceUserList.add(tempDeviceUser);
							policySet.add(tempDeviceUser.getAndroidDevicePolicyId());
						} else {// 如果以前策略记录不存在，则获取默认策略
							disableAndroidDeviceUsers = new AndroidDeviceUsers();
							disableAndroidDeviceUsers.setId(CommUtil.getPrimaryKey()+userId);
							disableAndroidDeviceUsers.setAndroidDevicePolicyId(0);
							disableAndroidDeviceUsers.setCreateDate(new Date());
							disableAndroidDeviceUsers.setUpdateDate(getDefaultDate());
							disableAndroidDeviceUsers.setUsersId(userId);
							disableDeviceUserList.add(disableAndroidDeviceUsers);
							policySet.add(0); 
						}
					}

					// =====================从AndroidDeviceDepartment表捞取以前的对应策略================================
					// Android设备策略授权部门关联表(禁用的)
					List<AndroidDeviceDepartment> disableDeviceDepartmentList = new ArrayList<AndroidDeviceDepartment>();
					// Android设备策略授权部门对象(禁用的)
					AndroidDeviceDepartment disableAndroidDeviceDepartment = null;
					// 根据策略id获取对应的授权部门
					List<Integer> departIdList = androidDeviceDepartmentDao.selectDepartmentById(intID);
					// 存储要删除的部门id
					List<Integer> delDepartIds = new ArrayList<Integer>();
					for (Integer departId : departIdList) {
						delDepartIds.add(departId);
						disableParams = new DataGridModel();
						disableParams.getParams().put("orgId", organization.getId());
						disableParams.getParams().put("id", id);
						disableParams.getParams().put("departId", departId);
						// 根据部门id获取以前对应策略
						List<AndroidDeviceDepartment> tempDepartList = androidDeviceDepartmentDao.selectPolicyByDepartId(disableParams);
						// 如果记录存在至少一条则采用最近的是贾尼
						if(tempDepartList.size()>0){
							AndroidDeviceDepartment tempDeviceDepartment = tempDepartList.get(0);
							disableDeviceDepartmentList.add(tempDeviceDepartment);
							policySet.add(tempDeviceDepartment.getAndroidDevicePolicyId());
						} else {// 采用默认策略
							disableAndroidDeviceDepartment = new AndroidDeviceDepartment();
							disableAndroidDeviceDepartment.setAndroidDevicePolicyId(0);
							disableAndroidDeviceDepartment.setOrgStructureId(departId);
							disableAndroidDeviceDepartment.setCreateDate(new Date());
							disableAndroidDeviceDepartment.setUpdateDate(getDefaultDate());
							disableDeviceDepartmentList.add(disableAndroidDeviceDepartment);
							policySet.add(0);
						}
					}
					
					// =====================从AndroidDeviceDepartment表捞取以前的对应策略================================
					// Android设备策略授权虚拟组关联表(禁用的)
					List<AndroidDeviceVirtualGroup> disableDeviceVirtualGroupList = new ArrayList<AndroidDeviceVirtualGroup>();
					// Android设备策略授权虚拟组对象(禁用的)
					AndroidDeviceVirtualGroup disableAndroidDeviceVirtualGroup = null;
					// 存储将要删除的虚拟组id
					List<Integer> delVirtualIds = new ArrayList<Integer>();
					List<Integer> virtualIdsList = androidDeviceVirtualGroupDao.selectVirtualById(intID);
					for (Integer virtualId : virtualIdsList) {
						delVirtualIds.add(virtualId);
						disableParams = new DataGridModel();
						disableParams.getParams().put("id", id);
						disableParams.getParams().put("orgId", organization.getId());
						disableParams.getParams().put("virtualId", virtualId);
						List<AndroidDeviceVirtualGroup> tempVirutalList = androidDeviceVirtualGroupDao.selectPolicyIdByVirtualId(disableParams);
						if(tempVirutalList.size()>0){
							AndroidDeviceVirtualGroup tempVirtual = tempVirutalList.get(0);
							disableDeviceVirtualGroupList.add(tempVirtual);
							policySet.add(tempVirtual.getAndroidDevicePolicyId());
						} else {
							disableAndroidDeviceVirtualGroup = new AndroidDeviceVirtualGroup();
							disableAndroidDeviceVirtualGroup.setAndroidDevicePolicyId(0);
							disableAndroidDeviceVirtualGroup.setVirtualGroupId(virtualId);
							disableAndroidDeviceVirtualGroup.setCreateDate(new Date());
							disableAndroidDeviceVirtualGroup.setUpdateDate(getDefaultDate());
							disableDeviceVirtualGroupList.add(disableAndroidDeviceVirtualGroup);
							policySet.add(0);
						}
					}
			        List<AndroidDevicePolicy> jpushList = getJpushList(id, organization.getId(), delDepartIds, disableDeviceDepartmentList, delVirtualIds, policySet, manager.getId(), disableDeviceVirtualGroupList, disableDeviceUserList);
					// 极光推送消息到手机端
					jpushUserPolicy(null,null,jpushList,null);
				}
			} else {
				logger.error("device policy parameter id is empty");
				if(Constant.YES.equals(tag)){
					result.type =  BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
					return result;
				} else {
					result.type =  BaseDTO.DANGER;
				    result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
			}
		if(Constant.YES.equals(tag)){
			result.type =  BaseDTO.SUCCESS;
			result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.success.lable",null, LocaleContextHolder.getLocale());
			return result;
		} else {
			result.type = BaseDTO.SUCCESS;
		    result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.success.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
	}

	/**
	 * 获取用户列表
	 */
	@Override
	public List<UserModel> getUserList(Integer orgId) {
		List<UserModel> list = new ArrayList<UserModel>();
		if(orgId!=null){
			list = androidDeviceUsersDao.getUserList(orgId.toString());
		}
		return list;
	}

	/**
	 * 获取虚拟组(虚拟集合)
	 */
	@Override
	public List<CollectVirtualModel> selectVirtualList(Integer orgId) {
		return androidDeviceVirtualGroupDao.selectVirtualList(orgId);
	}

	/**
	 * 获取名单列表
	 */
	@Override
	public Page selectNameList(ManagerModel user,Integer orgId,String name,String type,Integer start,Integer num) {
		DataGridModel params = new DataGridModel();
		params.getParams().put("type",type);
		params.getParams().put("name", name);
		params.getParams().put("orgId", orgId);
		params.getParams().put("user", user);
		params.setBegin(start);
		params.setNum(num);
		Page p = new Page();	
		int total = nameListDao.selectNameSize(params);
		p.setData(nameListDao.selectNameList(params));
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	/**
	 * 获取默认日期(2000年1月1日)
	 * @return
	 * @throws ParseException 
	 */
	public Date getDefaultDate() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return sdFormat.parse("2000-1-1 1:1:1");
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 保存Android策略配置
	 * @throws ParseException 
	 * @throws Exception 
	 */
	@Override
	public ResultDTO saveAndroidPolicy(HttpServletRequest request,DataGridModel params) throws ParseException{
		ResultDTO result = new ResultDTO();
		HttpSession session = request.getSession();
		// 存放用户id推送到极光服务器
		Set<Integer> userSet = new LinkedHashSet<Integer>();
		// 存放用户id推送到极光服务器
		List<Integer> delUserIds = new ArrayList<Integer>();
		// 存放策略id推送到极光服务器
		Set<Integer> policySet = new LinkedHashSet<Integer>();
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		if (obj == null) {
			result.type = BaseDTO.WARNING;
		    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.invalid.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if (orgObj == null) {
			result.type = BaseDTO.WARNING;
		    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.invalid.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		Object id = params.getParams().get("id");
		Object name = params.getParams().get("name");
		Object description = params.getParams().get("description");
		Object chooseDepartIds = params.getParams().get("chooseDepartIds");
		Object virtualIds = params.getParams().get("virtualIds");
		Object userIds = params.getParams().get("userIds");
		Object passwordLength = params.getParams().get("passwordLength");
		Object passwordComplexity = params.getParams().get("passwordComplexity");
		Object lockLongestTime = params.getParams().get("lockLongestTime");
		Object passwordValidity = params.getParams().get("passwordValidity");
		Object attemptTimes = params.getParams().get("attemptTimes");
		Object deviceEncryption = params.getParams().get("deviceEncryption");
		Object sdEncryption = params.getParams().get("sdEncryption");
		Object allowUseCamera = params.getParams().get("allowUseCamera");
		Object allowUseWifi = params.getParams().get("allowUseWifi");
		Object allowUseBluetooth = params.getParams().get("allowUseBluetooth");
		Object allowMicrophone = params.getParams().get("allowMicrophone");
		Object enableBlacklist = params.getParams().get("enableBlacklist");
		Object chooseEnableIds = params.getParams().get("chooseEnableIds");
		Object enableAppNameList = params.getParams().get("enableAppNameList");
		Object chooseAppEnableIds = params.getParams().get("chooseAppEnableIds");
		Object visitLimit = params.getParams().get("visitLimit");
		Object visitTimeStr = params.getParams().get("visitTimeStr");
		Object allowUseGps = params.getParams().get("allowUseGps");
		String list = params.getList();
		AndroidDevicePolicy androidDevicePolicy = new AndroidDevicePolicy();
		ManagerModel manager = (ManagerModel) obj;
		OrganizationModel organization = (OrganizationModel)orgObj;
		String isEnable = Constant.YES;
		if(id!=null&&!Constant.EMPTY_STR.equals(id)){
			AndroidDevicePolicy policy = androidDevicePolicyDao.selectByPrimaryKey(Integer.valueOf(id.toString()));
			if(!Constant.YES.equals(policy.getIsEnable())){
				isEnable = Constant.NO;
			}
			androidDevicePolicy.setId(Integer.valueOf(id.toString()));
		}
		DataGridModel objParams = new DataGridModel();
		String strName = String.valueOf(name);
		objParams.getParams().put("name", strName);
		objParams.getParams().put("orgId", organization.getId());
		// 新增功能(判断如何存在一个策略名称一样，则提示已存在)
		androidDevicePolicy.setName(strName);
		androidDevicePolicy.setOrgId(organization.getId());
		if(description!=null){
		   androidDevicePolicy.setDescription(String.valueOf(description));
		}
		if(passwordLength!=null){
		   androidDevicePolicy.setPasswordLength(String.valueOf(passwordLength));
		}
		if(passwordComplexity!=null){
			androidDevicePolicy.setPasswordComplexity(String.valueOf(passwordComplexity));
		}
		if(lockLongestTime!=null){
			androidDevicePolicy.setLockLongestTime(String.valueOf(lockLongestTime));
		}
		if(passwordValidity!=null){
			androidDevicePolicy.setPasswordValidity(String.valueOf(passwordValidity));
		}
		if(attemptTimes!=null){
			androidDevicePolicy.setAttemptTimes(String.valueOf(attemptTimes));
		}
		if(deviceEncryption!=null){
			androidDevicePolicy.setDeviceEncryption(String.valueOf(deviceEncryption));
		}
		if(sdEncryption!=null){
			androidDevicePolicy.setSdEncryption(String.valueOf(sdEncryption));
		}
		if(allowUseCamera!=null){
			androidDevicePolicy.setAllowUseCamera(String.valueOf(allowUseCamera));
		}
		if(allowUseWifi!=null){
			androidDevicePolicy.setAllowUseWifi(String.valueOf(allowUseWifi));
		}
		if(allowUseBluetooth!=null){
			androidDevicePolicy.setAllowUseBluetooth(String.valueOf(allowUseBluetooth));
		}
		if(allowMicrophone!=null){
			androidDevicePolicy.setAllowMicrophone(String.valueOf(allowMicrophone));
		}
		if(enableBlacklist!=null){
			androidDevicePolicy.setEnableBlacklist(String.valueOf(enableBlacklist));
		}
		if(enableAppNameList!=null){
			androidDevicePolicy.setEnableAppNameList(String.valueOf(enableAppNameList));
		}
		if(allowUseGps != null) {
			androidDevicePolicy.setAllowUseGps(String.valueOf(allowUseGps));
		}

		if(visitLimit!=null&&!Constant.EMPTY_STR.equals(visitLimit)){
			if(Constant.YES.equals(visitLimit)){
				androidDevicePolicy.setIsNetLimit(Constant.YES);
				if(visitTimeStr!=null){
					androidDevicePolicy.setVisitTimeStr(String.valueOf(visitTimeStr).trim());
				}
			} else {
				androidDevicePolicy.setIsNetLimit(Constant.NO);
			}
	    } 
		
        // 新增Android策略配置保存
		if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))){
			androidDevicePolicy.setIsEnable(Constant.YES);
			androidDevicePolicy.setCreateDate(new Date());
			androidDevicePolicy.setCreateUser(manager.getId());
			androidDevicePolicy.setUpdateDate(new Date());
			androidDevicePolicy.setUpdateUser(manager.getId());
			int size = androidDevicePolicyDao.insertSelective(androidDevicePolicy);
			if(size == 0){
			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
				result.type = BaseDTO.DANGER;
				return result;
			}
		} else {// 更新Android策略配置保存
			androidDevicePolicy.setUpdateDate(new Date());
			androidDevicePolicy.setUpdateUser(manager.getId());
			int size = androidDevicePolicyDao.updateByPrimaryKeySelective(androidDevicePolicy);
			if(size == 0){
			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
				result.type = BaseDTO.DANGER;
				return result;
			}
			// 删除WIFI配置数据
			wifiConfigureDao.updateWifiConfigureByPolicyId(androidDevicePolicy.getId());
		}

		// 插入WIFI配置数据
		List<WifiConfigure> wifiList = new ArrayList<WifiConfigure>();
		WifiConfigure wifi = null;
		if(StringUtil.isNotBlank(list)&&!Constant.EMPTY_ARRAY.equals(list)){
			JSONArray data = JSONArray.fromObject(list);
			for(int i=0;i<data.size();i++){
				JSONObject jobj =  (JSONObject)data.get(i);
				String ssid = jobj.getString("ssid");
				String isAutojoin = jobj.getString("isAutojoin");
				String saftyType = jobj.getString("saftyType");
				String wifikey = jobj.getString("wifikey");
				String eap = jobj.getString("eap");
				String stageAuthen = jobj.getString("stageAuthen");
				String identity = jobj.getString("identity");
				String anonymousIdentity = jobj.getString("anonymousIdentity");
				String ca = jobj.getString("ca");
				String user = jobj.getString("user");
				wifi = new WifiConfigure();
				wifi.setSsid(ssid);
				wifi.setIsAutojoin(isAutojoin);
				wifi.setAndroidDevicePolicyId(androidDevicePolicy.getId());
				wifi.setSecurityType(saftyType);
				wifi.setEapMethod(eap);
				wifi.setAnonymousIdentity(anonymousIdentity);
				wifi.setIdentity(identity);
				wifi.setStageAuthentication(stageAuthen);
				wifi.setPassword(wifikey);
				wifi.setCaCertificate(ca);
				wifi.setUserCertificate(user);	
				wifi.setCreateDate(new Date());
				wifi.setCreateUser(manager.getId());
				wifi.setUpdateDate(new Date());
				wifi.setUpdateUser(manager.getId());
				wifiList.add(wifi);
			}
			wifiConfigureDao.insertBatchWifiConfigure(wifiList);
		}
		
		// Android设备策略授权用户关联表
		List<AndroidDeviceUsers> deviceUserList = new ArrayList<AndroidDeviceUsers>();
		// Android设备策略授权用户对象
		AndroidDeviceUsers androidDeviceUsers = null;
		// Android设备策略授权用户关联表(已删除)
		List<AndroidDeviceUsers> delDeviceUserList = new ArrayList<AndroidDeviceUsers>();
		// Android设备策略授权用户对象(已删除)
		AndroidDeviceUsers delAndroidDeviceUsers = null;
		DataGridModel delParams = null;
		// 当授权用户选择时，该条件至少有一条用户是被选择的
		if(userIds!=null&&!Constant.EMPTY_STR.equals(String.valueOf(userIds))){
			// 存储当前的安卓用户策略
			String tempUserIds = userIds.toString()+SplitSymbol.COMMA_SYMBOL;
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intID = Integer.valueOf(id.toString());
				// 获取将删除的用户
				List<Integer> userList = androidDeviceUsersDao.selectUserByIds(intID);
				// 存储当前选中的用户id
				for (Integer userId : userList) {
					if(tempUserIds.indexOf(userId+SplitSymbol.COMMA_SYMBOL)>=0){
						String oldChar = userId + SplitSymbol.COMMA_SYMBOL;
						tempUserIds = tempUserIds.replace(oldChar, Constant.EMPTY_STR);
					} else {
						delUserIds.add(userId);
						delParams = new DataGridModel();
						delParams.getParams().put("id", id);
						delParams.getParams().put("orgId", organization.getId());
						delParams.getParams().put("userId", userId);
						List<AndroidDeviceUsers>  tempUserList = androidDeviceUsersDao.selectPolicyIdByUserId(delParams);
						// 查询当前用户以前的策略是否存在
						if(tempUserList.size()>0){
							AndroidDeviceUsers tempDeviceUser = tempUserList.get(0);
							delDeviceUserList.add(tempDeviceUser);
							policySet.add(tempDeviceUser.getAndroidDevicePolicyId());
						} else {
							delAndroidDeviceUsers = new AndroidDeviceUsers();
							delAndroidDeviceUsers.setAndroidDevicePolicyId(0);
							delAndroidDeviceUsers.setCreateDate(new Date());
							delAndroidDeviceUsers.setUpdateDate(getDefaultDate());
							delAndroidDeviceUsers.setUsersId(userId);
							delDeviceUserList.add(delAndroidDeviceUsers);
							policySet.add(0);
					    }
					}
				}
				androidDeviceUsersDao.updateByUsersId(intID);
				if(StringUtil.isNotBlank(tempUserIds)){
					tempUserIds = tempUserIds.substring(0,tempUserIds.length()-1);
//						userIds = tempUserIds;
				}
			}
			String users = userIds.toString();
			if(StringUtil.isNotBlank(users)){
				String[] usersList = users.split(SplitSymbol.COMMA_SYMBOL);
				Integer userId = null;
				for (String s : usersList) {
					userId  = Integer.valueOf(s);
					userSet.add(userId);
					androidDeviceUsers = new AndroidDeviceUsers();
					androidDeviceUsers.setAndroidDevicePolicyId(androidDevicePolicy.getId());
					androidDeviceUsers.setUsersId(userId);
					androidDeviceUsers.setCreateDate(new Date());
					androidDeviceUsers.setCreateUser(manager.getId());
					androidDeviceUsers.setUpdateDate(new Date());
					androidDeviceUsers.setUpdateUser(manager.getId());
//						androidDeviceUsersDao.insertSelective(androidDeviceUsers);
					deviceUserList.add(androidDeviceUsers);
				}
				if(deviceUserList.size()>0){
					int deviceUsersSize = androidDeviceUsersDao.insertBatchPolicyUserIds(deviceUserList);
					if(deviceUsersSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
			}
		} else {// 当授权用户选择时，该条件用户全部未选
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intID = Integer.valueOf(id.toString());
				// 获取将删除的用户
				List<Integer> userList = androidDeviceUsersDao.selectUserByIds(intID);
				// 存储当前选中的用户id
			    for (Integer userId : userList) {
					delUserIds.add(userId);
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("userId", userId);
					List<AndroidDeviceUsers>  tempUserList = androidDeviceUsersDao.selectPolicyIdByUserId(delParams);
					// 查询当前用户以前的策略是否存在
					if(tempUserList.size()>0){
						AndroidDeviceUsers tempDeviceUser = tempUserList.get(0);
						delDeviceUserList.add(tempDeviceUser);
						policySet.add(tempDeviceUser.getAndroidDevicePolicyId());
					} else {
						delAndroidDeviceUsers = new AndroidDeviceUsers();
						delAndroidDeviceUsers.setAndroidDevicePolicyId(0);
						delAndroidDeviceUsers.setCreateDate(new Date());
						delAndroidDeviceUsers.setUpdateDate(getDefaultDate());
						delAndroidDeviceUsers.setUsersId(userId);
						delDeviceUserList.add(delAndroidDeviceUsers);
						policySet.add(0);
				     }
				}
				androidDeviceUsersDao.updateByUsersId(intID);

			}
		}
		// Android设备策略授权部门关联表(当前)
		List<AndroidDeviceDepartment> deviceDepartmentList = new ArrayList<AndroidDeviceDepartment>();
		// Android设备策略授权部门对象(当前)
		AndroidDeviceDepartment androidDeviceDepartment = null;
		// Android设备策略授权部门关联表(删除的)
		List<AndroidDeviceDepartment> delDeviceDepartmentList = new ArrayList<AndroidDeviceDepartment>();
		// Android设备策略授权部门对象(删除的)
		AndroidDeviceDepartment delAndroidDeviceDepartment = null;
		List<Integer> delDepartIds = new ArrayList<Integer>();
		// 当保存策略时，选中至少一个部门
		if(chooseDepartIds!=null&&!Constant.EMPTY_STR.equals(chooseDepartIds)){
			// 存储当前保存的安卓部门策略
			String tempDepartIds = chooseDepartIds.toString()+SplitSymbol.COMMA_SYMBOL;
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intID = Integer.valueOf(id.toString());
				List<Integer> departIdsList = androidDeviceDepartmentDao.selectDepartmentById(intID);
				for (Integer departId : departIdsList) {
					if(tempDepartIds.indexOf(departId+SplitSymbol.COMMA_SYMBOL)>=0){
						tempDepartIds.replace(departId+SplitSymbol.COMMA_SYMBOL, Constant.EMPTY_STR);
					} else {
						delDepartIds.add(departId);
						delParams = new DataGridModel();
						delParams.getParams().put("orgId", organization.getId());
						delParams.getParams().put("id", id);
						delParams.getParams().put("departId", departId);
						List<AndroidDeviceDepartment> tempDepartList = androidDeviceDepartmentDao.selectPolicyByDepartId(delParams);
						if(tempDepartList.size()>0){
							AndroidDeviceDepartment tempDeviceDepartment = tempDepartList.get(0);
							delDeviceDepartmentList.add(tempDeviceDepartment);
							policySet.add(tempDeviceDepartment.getAndroidDevicePolicyId());
						} else {
							delAndroidDeviceDepartment = new AndroidDeviceDepartment();
							delAndroidDeviceDepartment.setAndroidDevicePolicyId(0);
							delAndroidDeviceDepartment.setOrgStructureId(departId);
							delAndroidDeviceDepartment.setCreateDate(new Date());
							delAndroidDeviceDepartment.setUpdateDate(getDefaultDate());
							delDeviceDepartmentList.add(delAndroidDeviceDepartment);
							policySet.add(0);
						}
					}
				}
				androidDeviceDepartmentDao.updateByDepartId(intID);
				if(StringUtil.isNotBlank(tempDepartIds)){
					tempDepartIds = tempDepartIds.substring(0,tempDepartIds.length()-1);
					chooseDepartIds = tempDepartIds;
				}
		   }
		   String departIds = chooseDepartIds.toString();
           if(StringUtil.isNotBlank(departIds)){ 
        	   String[] departList = departIds.split(SplitSymbol.COMMA_SYMBOL);
        	   for(String s:departList){
        		   androidDeviceDepartment = new AndroidDeviceDepartment();
        		   androidDeviceDepartment.setAndroidDevicePolicyId(androidDevicePolicy.getId());
        		   androidDeviceDepartment.setOrgStructureId(Integer.valueOf(s));
        		   androidDeviceDepartment.setCreateDate(new Date());
        		   androidDeviceDepartment.setCreateUser(manager.getId());
        		   androidDeviceDepartment.setUpdateDate(new Date());
        		   androidDeviceDepartment.setUpdateUser(manager.getId());
//	        	   androidDeviceDepartmentDao.insertSelective(androidDeviceDepartment);
        		   deviceDepartmentList.add(androidDeviceDepartment);
        	   }
        	   
        	   if(deviceDepartmentList.size()>0){
        		   int departSize = androidDeviceDepartmentDao.insertBatchDeviceDepartment(deviceDepartmentList);
        		   if(departSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
        		   }
        	   }
           }
		} else {// 当保存策略的时候，全部未选择
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intID = Integer.valueOf(id.toString());
				List<Integer> departIdsList = androidDeviceDepartmentDao.selectDepartmentById(intID);
				for (Integer departId : departIdsList) {
						delDepartIds.add(departId);
						delParams = new DataGridModel();
						delParams.getParams().put("orgId", organization.getId());
						delParams.getParams().put("id", id);
						delParams.getParams().put("departId", departId);
						List<AndroidDeviceDepartment> tempDepartList = androidDeviceDepartmentDao.selectPolicyByDepartId(delParams);
						if(tempDepartList.size()>0){
							AndroidDeviceDepartment tempDeviceDepartment = tempDepartList.get(0);
							delDeviceDepartmentList.add(tempDeviceDepartment);
							policySet.add(tempDeviceDepartment.getAndroidDevicePolicyId());
						} else {
							delAndroidDeviceDepartment = new AndroidDeviceDepartment();
							delAndroidDeviceDepartment.setAndroidDevicePolicyId(intID);
							delAndroidDeviceDepartment.setOrgStructureId(departId);
							delAndroidDeviceDepartment.setCreateDate(new Date());
							delAndroidDeviceDepartment.setUpdateDate(getDefaultDate());
							delDeviceDepartmentList.add(delAndroidDeviceDepartment);
							policySet.add(0);
						}
					}
				androidDeviceDepartmentDao.updateByDepartId(intID);
		   }
		}
		// Android设备策略授权虚拟组关联表(当前的)
		List<AndroidDeviceVirtualGroup> deviceVirtualGroupList = new ArrayList<AndroidDeviceVirtualGroup>();
		// Android设备策略授权虚拟组对象(当前的)s
		AndroidDeviceVirtualGroup androidDeviceVirtualGroup = null;
		// Android设备策略授权虚拟组关联表(删除的)
		List<AndroidDeviceVirtualGroup> delDeviceVirtualGroupList = new ArrayList<AndroidDeviceVirtualGroup>();
		// Android设备策略授权虚拟组对象(删除的)
		AndroidDeviceVirtualGroup delAndroidDeviceVirtualGroup = null;
		List<Integer> delVirtualIds = new ArrayList<Integer>();
		// 当保存策略的时候，至少有一条虚拟组是选择的
		if(virtualIds!=null&&!Constant.EMPTY_STR.equals(virtualIds)){
			String tempVirtualIds = virtualIds.toString() + SplitSymbol.COMMA_SYMBOL;
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intID = Integer.valueOf(id.toString());
				List<Integer> virtualIdsList = androidDeviceVirtualGroupDao.selectVirtualById(intID);
				for (Integer virtualId : virtualIdsList) {
					if(tempVirtualIds.indexOf(virtualId+SplitSymbol.COMMA_SYMBOL)>=0){
						tempVirtualIds.replace(virtualId+SplitSymbol.COMMA_SYMBOL, Constant.EMPTY_STR);
					} else {
					delVirtualIds.add(virtualId);
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("virtualId", virtualId);
					List<AndroidDeviceVirtualGroup> tempVirutalList = androidDeviceVirtualGroupDao.selectPolicyIdByVirtualId(delParams);
						if(tempVirutalList.size()>0){
							AndroidDeviceVirtualGroup tempVirtualGroup = tempVirutalList.get(0);
							delDeviceVirtualGroupList.add(tempVirtualGroup);
							policySet.add(tempVirtualGroup.getAndroidDevicePolicyId());
						} else {
							delAndroidDeviceVirtualGroup = new AndroidDeviceVirtualGroup();
							delAndroidDeviceVirtualGroup.setAndroidDevicePolicyId(0);
							delAndroidDeviceVirtualGroup.setVirtualGroupId(virtualId);
							delAndroidDeviceVirtualGroup.setCreateDate(new Date());
							delAndroidDeviceVirtualGroup.setUpdateDate(getDefaultDate());
							delDeviceVirtualGroupList.add(delAndroidDeviceVirtualGroup);
							policySet.add(0);
						}
					}
			    }
				androidDeviceVirtualGroupDao.updateByVirtualGroupId(intID);
				if(StringUtil.isNotBlank(tempVirtualIds)){
					tempVirtualIds = tempVirtualIds.substring(0,tempVirtualIds.length()-1);
					virtualIds = tempVirtualIds;
				}
			}
			String virtualids = virtualIds.toString();
			if(StringUtil.isNotBlank(virtualids)){
				String[] virtualList = virtualids.split(SplitSymbol.COMMA_SYMBOL);
				for (String s : virtualList) {
					androidDeviceVirtualGroup = new AndroidDeviceVirtualGroup();
					androidDeviceVirtualGroup.setAndroidDevicePolicyId(androidDevicePolicy.getId());
					androidDeviceVirtualGroup.setVirtualGroupId(Integer.valueOf(s));
					androidDeviceVirtualGroup.setCreateDate(new Date());
					androidDeviceVirtualGroup.setCreateUser(manager.getId());
					androidDeviceVirtualGroup.setUpdateDate(new Date());
					androidDeviceVirtualGroup.setUpdateUser(manager.getId());
//						androidDeviceVirtualGroupDao.insertSelective(androidDeviceVirtualGroup);
					deviceVirtualGroupList.add(androidDeviceVirtualGroup);
				}
				if(deviceVirtualGroupList.size()>0){
				int virtualSize = androidDeviceVirtualGroupDao.insertBatchPolicyVirtualGroup(deviceVirtualGroupList);
					if(virtualSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
			}
		} else {//当保存策略的时候，虚拟组全部未选
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intID = Integer.valueOf(id.toString());
				List<Integer> virtualIdsList = androidDeviceVirtualGroupDao.selectVirtualById(intID);
				for (Integer virtualId : virtualIdsList) {
					delVirtualIds.add(virtualId);
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("virtualId", virtualId);
					List<AndroidDeviceVirtualGroup> tempVirutalList = androidDeviceVirtualGroupDao.selectPolicyIdByVirtualId(delParams);
						if(tempVirutalList.size()>0){
							AndroidDeviceVirtualGroup tempVirtualGroup = tempVirutalList.get(0);
							delDeviceVirtualGroupList.add(tempVirtualGroup);
							policySet.add(tempVirtualGroup.getAndroidDevicePolicyId());
						} else {
							delAndroidDeviceVirtualGroup = new AndroidDeviceVirtualGroup();
							delAndroidDeviceVirtualGroup.setAndroidDevicePolicyId(0);
							delAndroidDeviceVirtualGroup.setVirtualGroupId(virtualId);
							delAndroidDeviceVirtualGroup.setCreateDate(new Date());
							delAndroidDeviceVirtualGroup.setUpdateDate(getDefaultDate());
							delDeviceVirtualGroupList.add(delAndroidDeviceVirtualGroup);
							policySet.add(0);
						}
					}
				androidDeviceVirtualGroupDao.updateByVirtualGroupId(intID);
			}
		}
		// 存放黑白名单URL
		List<BlackWhiteListUrl> currentList = new ArrayList<BlackWhiteListUrl>();
		// 存放前台选择的黑白名单Id参数
		List<Integer> idList = new ArrayList<Integer>();
		objParams = new DataGridModel();
		// 黑白名单列表
		List<AndroidPolicyList> androidPolicyList = new ArrayList<AndroidPolicyList>();
		AndroidPolicyList androidPolicy = null;
		if(chooseEnableIds!=null&&!Constant.EMPTY_STR.equals(String.valueOf(chooseEnableIds))){
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				androidPolicyListDao.updateByNamelistId(Integer.valueOf(id.toString()));
			}
			String enableIds = chooseEnableIds.toString();
			if(StringUtil.isNotBlank(enableIds)){
				String[] enableList = enableIds.split(SplitSymbol.COMMA_SYMBOL);
				for (String s : enableList) {
					Integer nameId = Integer.valueOf(s);
					androidPolicy = new AndroidPolicyList();
					androidPolicy.setAndroidDevicePolicyId(androidDevicePolicy.getId());
					androidPolicy.setNameListId(nameId);
					androidPolicy.setCreateDate(new Date());
					androidPolicy.setCreateUser(manager.getId());
					androidPolicy.setUpdateDate(new Date());
					androidPolicy.setUpdateUser(manager.getId());
//						androidPolicyListDao.insertSelective(androidPolicy);
					androidPolicyList.add(androidPolicy);
					idList.add(nameId);
				}
				if(enableList.length>0){
					int enableSize = androidPolicyListDao.insertBatchPolicyList(androidPolicyList);
					if(enableSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
				// 根据参数获取黑白名单url
				if(idList.size()>0){
					objParams.getParams().put("list", idList);
					currentList.addAll(netBehaviorDao.selectBWUrlListByIdList(objParams));
					androidDevicePolicy.setUrlList(currentList);
				}
			}
		}
		
		// 黑白名单列表
		List<AndroidBlackList> androidBlackList = new ArrayList<AndroidBlackList>();
		AndroidBlackList blackPolicy = null;
		if(chooseAppEnableIds!=null&&chooseAppEnableIds!=""){
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				androidBlackListDao.updateByAppNamelistId(Integer.valueOf(id.toString()));
			}
			String enableIds = chooseAppEnableIds.toString();
			if(StringUtil.isNotBlank(enableIds)){
				String[] enableList = enableIds.split(SplitSymbol.COMMA_SYMBOL);
				Integer i = 0;
				for (String s : enableList) {
					Integer nameId = Integer.valueOf(s);
					i++;
					blackPolicy = new AndroidBlackList();
					blackPolicy.setAndroidDevicePolicyId(androidDevicePolicy.getId());
					blackPolicy.setNameListId(nameId);
					blackPolicy.setCreateDate(new Date());
					blackPolicy.setCreateUser(manager.getId());
					blackPolicy.setUpdateDate(new Date());
					blackPolicy.setUpdateUser(manager.getId());
					androidBlackList.add(blackPolicy);
				}
				if(enableList.length>0){
					int enableSize = androidBlackListDao.insertBatchBlackPolicyList(androidBlackList);
					if(enableSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
			}
		}
		
		// 根据部门、虚拟组、用户查找以前最近的时间的策略并拼接存放list
		List<AndroidDevicePolicy> jpushList = getJpushList(id,organization.getId(),delDepartIds,delDeviceDepartmentList,delVirtualIds,policySet,manager.getId(),delDeviceVirtualGroupList,delDeviceUserList);
		// 当策略是启用的时候，需要极光推送策略消息
		if(Constant.YES.equals(isEnable)){
			// 获取当前策略的用户
			Set<Integer> getUserSet = getCurrentUserIds(deviceDepartmentList, deviceVirtualGroupList, organization.getId());
			userSet.addAll(getUserSet);
			// 推送用户策略
//			result.userSet = userSet;
//			result.policyList = jpushList;
//			result.currentPolicy = andAroidDevicePolicy;
			jpushUserPolicy(userSet,androidDevicePolicy,jpushList,null);
	    }
	    result.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.success.lable",null, LocaleContextHolder.getLocale());
		result.type = BaseDTO.SUCCESS;
		return result;
	}
	
	/**
	 * 查看Android策略配置
	 */
	@Override
	public Object findPolicy(HttpServletRequest request) {
		String id = request.getParameter("id");
		AndroidDevicePolicy androidDevicePolicy = new AndroidDevicePolicy();
		List<AndroidDeviceDepartment> list = new ArrayList<AndroidDeviceDepartment>();
		List<AndroidDeviceUsers> userList = new ArrayList<AndroidDeviceUsers>();
		List<AndroidDeviceVirtualGroup> virtualList = new ArrayList<AndroidDeviceVirtualGroup>();
		List<NetBehaviorBlackWhiteList> nameList = new ArrayList<NetBehaviorBlackWhiteList>();
		List<WifiConfigure> wifiList = new ArrayList<WifiConfigure>();
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			List<NameList> appNameList = new ArrayList<NameList>();
			androidDevicePolicy = androidDevicePolicyDao.selectByPrimaryKey(intId);
			list = androidDeviceDepartmentDao.selectDeviceDepartmentById(intId);
			userList = androidDeviceUsersDao.selectDeviceUserById(intId);
			virtualList = androidDeviceVirtualGroupDao.selectVirtualGroupById(intId);
			nameList = androidPolicyListDao.selectNameListByPolicyId(intId);
			appNameList = androidBlackListDao.selectNameListByPolicyId(intId);
			wifiList = wifiConfigureDao.selectByPolicyKey(intId);
			String ids = "";
			String names = "";
			for (NetBehaviorBlackWhiteList name : nameList) {
				ids += name.getId()+SplitSymbol.COMMA_SYMBOL;
				names += name.getBlackWhiteName()+SplitSymbol.COMMA_SYMBOL;
			}
			// 截取后面的逗号
			if(StringUtil.isNotEmpty(names)){
				names = names.substring(0, names.length()-1);
			}
			String appIds = "";
			String appNames = "";
			for (NameList name : appNameList) {
				appIds += name.getId()+SplitSymbol.COMMA_SYMBOL;
				appNames += name.getListName()+SplitSymbol.COMMA_SYMBOL;
			}
			if(StringUtil.isNotEmpty(appNames)){
				appNames = appNames.substring(0,appNames.length()-1);
			}
			androidDevicePolicy.setIds(ids);
			androidDevicePolicy.setNames(names);
			androidDevicePolicy.setAppIds(appIds);
			androidDevicePolicy.setAppNames(appNames);
			androidDevicePolicy.setList(list);
            androidDevicePolicy.setUserList(userList);
            androidDevicePolicy.setVirtualList(virtualList);
            androidDevicePolicy.setWifiList(wifiList);
		}
		return androidDevicePolicy;
	}

	/**
	 * 编辑Android策略配置
	 */
	@Override
	public Object editPolicy(HttpServletRequest request) {
		String id = request.getParameter("id");
		AndroidDevicePolicy androidDevicePolicy = new AndroidDevicePolicy();
		List<AndroidDeviceDepartment> list = new ArrayList<AndroidDeviceDepartment>();
		List<AndroidDeviceUsers> userList = new ArrayList<AndroidDeviceUsers>();
		List<AndroidDeviceVirtualGroup> virtualList = new ArrayList<AndroidDeviceVirtualGroup>();
		List<NetBehaviorBlackWhiteList> nameList = new ArrayList<NetBehaviorBlackWhiteList>();
		List<WifiConfigure> wifiList = new ArrayList<WifiConfigure>();
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			List<NameList> appNameList = new ArrayList<NameList>();
			androidDevicePolicy = androidDevicePolicyDao.selectByPrimaryKey(intId);
			list = androidDeviceDepartmentDao.selectDeviceDepartmentById(intId);
			userList = androidDeviceUsersDao.selectDeviceUserById(intId);
			virtualList = androidDeviceVirtualGroupDao.selectVirtualGroupById(intId);
			nameList = androidPolicyListDao.selectNameListByPolicyId(intId);
			appNameList = androidBlackListDao.selectNameListByPolicyId(intId);
			wifiList = wifiConfigureDao.selectByPolicyKey(intId);
			String ids = "";
			String names = "";
			for (NetBehaviorBlackWhiteList name : nameList) {
				ids += name.getId()+SplitSymbol.COMMA_SYMBOL;
				names += name.getBlackWhiteName()+SplitSymbol.COMMA_SYMBOL;
			}
			// 截取后面的逗号
			if(StringUtil.isNotEmpty(names)){
				names = names.substring(0, names.length()-1);
			}
			String appIds = "";
			String appNames = "";
			for (NameList name : appNameList) {
				appIds += name.getId()+SplitSymbol.COMMA_SYMBOL;
				appNames += name.getListName()+SplitSymbol.COMMA_SYMBOL;
			}
			if(StringUtil.isNotEmpty(appNames)){
				appNames = appNames.substring(0,appNames.length()-1);
			}
			androidDevicePolicy.setIds(ids);
			androidDevicePolicy.setNames(names);
			androidDevicePolicy.setAppIds(appIds);
			androidDevicePolicy.setAppNames(appNames);
			androidDevicePolicy.setList(list);
            androidDevicePolicy.setUserList(userList);
            androidDevicePolicy.setVirtualList(virtualList);
            androidDevicePolicy.setWifiList(wifiList);
		}
		return androidDevicePolicy;
	}

	/**
	 * 根据名称查询用户
	 */
	@Override
	public List<UserModel> findUserByName(Integer orgId,String name,String userids,List<Integer> ids) {
		DataGridModel params = new DataGridModel();
		params.getParams().put("orgId", orgId);
		params.getParams().put("name", name);
		params.getParams().put("userids", userids);
		params.getParams().put("groupList", ids);
		if(StringUtil.isNotBlank(userids)){
			userids = userids.substring(0, userids.length()-1);
			String[] array = userids.split(SplitSymbol.COMMA_SYMBOL);
			params.getParams().put("list", array);
		}
		return userDao.findUserByName(params);
	}

	@Override
	public ResultDTO delNameList(HttpServletRequest request) {
		ResultDTO result = new ResultDTO();
		String id = request.getParameter("id");
		// 判断设备策略主键id存在
		if (StringUtil.isNotBlank(id)) {
			HttpSession session = request.getSession();
			Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (obj == null) {
				result.type = BaseDTO.DANGER;
				result.message = messageSourceService.getMessage("web.institution.name.list.delnamelist.invalid.lable",null, LocaleContextHolder.getLocale());
				return result;
			}
			Integer intId = Integer.valueOf(id);
			NameList nameList = nameListDao.selectByPrimaryKey(intId);
			if(nameList!=null){
				request.setAttribute("name", nameList.getListName());
			    nameList.setId(intId);
			    nameList.setDeleteTime(new Date());
				int count = nameListDao.updateByPrimaryKeySelective(nameList);
				if (count == 0) {
					result.type = BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.name.list.delnamelist.failed.lable",null, LocaleContextHolder.getLocale());
					return result;
				} 
			}
		} else {
			result.type = BaseDTO.DANGER;
			result.message = messageSourceService.getMessage("web.institution.name.list.delnamelist.failed.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		result.type = BaseDTO.SUCCESS;
		result.message = messageSourceService.getMessage("web.institution.name.list.delnamelist.success.lable",null, LocaleContextHolder.getLocale());
		return result;
	}

	// 根据部门和虚拟组获取对应的当前用户
	public Set<Integer> getIosCurrentUserIds(List<IospolicyDepartment> deviceDepartmentList,List<IosPolicyVirtual> deviceVirtualGroupList,Integer orgId){
		Set<Integer> userSet = new LinkedHashSet<Integer>();
		// ========================编辑新的策略发布推送到手机端============================
		// 根据部门获取用户id
		List<Integer> departIntegerList = new ArrayList<Integer>();
		if(deviceDepartmentList.size()>0){
			DataGridModel departParams = new DataGridModel();
			departParams.getParams().put("orgId", orgId);
			departParams.getParams().put("list", deviceDepartmentList);
			departIntegerList = iospolicyDepartmentDao.selectDepartUserIds(departParams);
			userSet.addAll(departIntegerList);
		}
		// 根据虚拟组获取用户id
		List<Integer> virtualIntegerList = new ArrayList<Integer>();
		if(deviceVirtualGroupList.size()>0){
			DataGridModel virtualParams = new DataGridModel();
			virtualParams.getParams().put("orgId", orgId);
			virtualParams.getParams().put("list", deviceVirtualGroupList);
			virtualIntegerList = iosPolicyVirtualDao.selectVirtualUserIds(virtualParams);
			userSet.addAll(virtualIntegerList);
	        }
		return userSet;
	}
	
	// 根据部门和虚拟组获取对应的当前用户
	public Set<Integer> getCurrentUserIds(List<AndroidDeviceDepartment> deviceDepartmentList,List<AndroidDeviceVirtualGroup> deviceVirtualGroupList,Integer orgId){
		Set<Integer> userSet = new LinkedHashSet<Integer>();
		// ========================编辑新的策略发布推送到手机端============================
		// 根据部门获取用户id
		List<Integer> departIntegerList = new ArrayList<Integer>();
		if(deviceDepartmentList.size()>0){
			DataGridModel departParams = new DataGridModel();
			departParams.getParams().put("orgId", orgId);
			departParams.getParams().put("list", deviceDepartmentList);
			departIntegerList = androidDeviceDepartmentDao.selectDepartUserIds(departParams);
			userSet.addAll(departIntegerList);
		}
		// 根据虚拟组获取用户id
		List<Integer> virtualIntegerList = new ArrayList<Integer>();
		if(deviceVirtualGroupList.size()>0){
			DataGridModel virtualParams = new DataGridModel();
			virtualParams.getParams().put("orgId", orgId);
			virtualParams.getParams().put("list", deviceVirtualGroupList);
			virtualIntegerList = androidDeviceVirtualGroupDao.selectVirtualUserIds(virtualParams);
			userSet.addAll(virtualIntegerList);
	        }
		return userSet;
	}

	// 获取默认策略
	public IosDevicePolicy getIosDefaultPolicy(){
		IosDevicePolicy defaultPolicy = new IosDevicePolicy();
		defaultPolicy.setId(0);
		defaultPolicy.setName("默认策略");
		defaultPolicy.setDescription("默认策略");
		return defaultPolicy;	
	}
	
	// 获取默认策略
	public AndroidDevicePolicy getDefaultPolicy(){
		AndroidDevicePolicy defaultPolicy = new AndroidDevicePolicy();
		defaultPolicy.setId(0);
		defaultPolicy.setName("默认策略");
		defaultPolicy.setDescription("默认策略");
		defaultPolicy.setIsEnable(Constant.YES);
		defaultPolicy.setPasswordLength(Constant.DEFAULT_VALUE);
		defaultPolicy.setPasswordComplexity(Constant.DEFAULT_VALUE);
		defaultPolicy.setLockLongestTime(Constant.DEFAULT_VALUE);
		defaultPolicy.setPasswordValidity(Constant.DEFAULT_VALUE);
		defaultPolicy.setPasswordHistory(Constant.DEFAULT_VALUE);
		defaultPolicy.setAttemptTimes(Constant.DEFAULT_VALUE);
		defaultPolicy.setDeviceEncryption(Constant.NO);
		defaultPolicy.setSdEncryption(Constant.NO);
		defaultPolicy.setAllowUseCamera(Constant.YES);
		defaultPolicy.setAllowUseWifi(Constant.YES);
		defaultPolicy.setAllowUseBluetooth(Constant.YES);
		defaultPolicy.setAllowMicrophone(Constant.YES);
		defaultPolicy.setIsNetLimit(Constant.NO);
		return defaultPolicy;	
	}

	/**
	 * 根据当前用户获取以前的策略或默认策略
	 * @param id:主键
	 * @param orgId:机构id
	 * @param delDepartIds:要删除的部门id
	 * @param delDeviceDepartmentList:要删除的部门列表list
	 * @param delVirtualIds:要删除的虚拟组ids
	 * @param policySet:android设备策略id
	 * @param managerId:操作者id
	 * @param delDeviceVirtualGroupList：要删除的android虚拟组list
	 * @param delDeviceUserList:要删除的android授权用户列表
	 * @return 返回用户对应的策略列表
	 */
	@SuppressWarnings("unchecked")
	public List<AndroidDevicePolicy> getJpushList(Object id,Integer orgId,List<Integer> delDepartIds,List<AndroidDeviceDepartment> delDeviceDepartmentList,
			List<Integer> delVirtualIds,Set<Integer> policySet,Integer managerId,List<AndroidDeviceVirtualGroup> delDeviceVirtualGroupList,
			List<AndroidDeviceUsers> delDeviceUserList)
	  {
	    List<AndroidDevicePolicy> jpushList = new ArrayList<AndroidDevicePolicy>();
		// 该条策略是否启用(默认是启用)，当该表是启用的时候，该条策略推送极光，如果是禁用，则不推送
		if(id!=null&&!Constant.EMPTY_STR.equals(id)){
			List<AndroidDeviceUsers> allUserPolicy = new ArrayList<AndroidDeviceUsers>();
			// 存储根据部门id获取该下面的用户
			List<AndroidDeviceUsers> delDepartUserList = new ArrayList<AndroidDeviceUsers>();
			if(delDepartIds.size()>0){
				DataGridModel delDepartParams = new DataGridModel();
				delDepartParams.getParams().put("orgId", orgId);
				delDepartParams.getParams().put("list", delDepartIds);
				// 根据部门idlist参数获取用户列表
				delDepartUserList = androidDeviceDepartmentDao.selectDepartUserList(delDepartParams);
			}
			AndroidDeviceUsers users = null;
			int i =0;
				for(AndroidDeviceUsers user:delDepartUserList){
					i++;
					users = new AndroidDeviceUsers();
					users.setId(CommUtil.getPrimaryKey()+i);	
					users.setUsersId(user.getUsersId());
					users.setFatherId(user.getFatherId());
					for(AndroidDeviceDepartment depart:delDeviceDepartmentList){
						if(depart.getOrgStructureId().equals(user.getFatherId())){
							users.setAndroidDevicePolicyId(depart.getAndroidDevicePolicyId());
							users.setUpdateDate(depart.getUpdateDate());
							users.setCreateDate(depart.getCreateDate());
						}
					}
					allUserPolicy.add(users);
			}
			// 根据虚拟组获取该下面的用户(删除的)
			List<AndroidDeviceUsers> delVirtualIdsList = new ArrayList<AndroidDeviceUsers>();
			if(delVirtualIds.size()>0){
				DataGridModel virtualParams = new DataGridModel();
				virtualParams.getParams().put("orgId", orgId);
				virtualParams.getParams().put("list", delVirtualIds);
				// 根据虚拟组参数Id,list获取用户列表id
				delVirtualIdsList = androidDeviceVirtualGroupDao.selectVirtualUserList(virtualParams);
			  }
				for(AndroidDeviceUsers user:delVirtualIdsList){
					i++;
					users = new AndroidDeviceUsers();
					users.setId(CommUtil.getPrimaryKey()+i);
					users.setUsersId(user.getUsersId());
					users.setFatherId(user.getFatherId());
					for(AndroidDeviceVirtualGroup depart:delDeviceVirtualGroupList){
						if(depart.getVirtualGroupId().equals(user.getFatherId())){
						users.setAndroidDevicePolicyId(depart.getAndroidDevicePolicyId());
						users.setUpdateDate(depart.getUpdateDate());
						users.setCreateDate(depart.getCreateDate());
						}
				    }
					allUserPolicy.add(users);
			   }
			if(delDeviceUserList.size()>0){
			   allUserPolicy.addAll(delDeviceUserList);
			}
			// 调用用户策略时间比较器
			List<UserPolicyComparable> comparableList = new ArrayList<UserPolicyComparable>();
			UserPolicyComparable userPolicyComparable = null;
			for (AndroidDeviceUsers user : allUserPolicy) {
				userPolicyComparable = new UserPolicyComparable();
				userPolicyComparable.setId(user.getId());
				userPolicyComparable.setFatherId(user.getFatherId());
				userPolicyComparable.setAndroidDevicePolicyId(user.getAndroidDevicePolicyId());
				userPolicyComparable.setCreateDate(user.getCreateDate());
				userPolicyComparable.setUpdateDate(user.getUpdateDate().getTime());
				userPolicyComparable.setUsersId(user.getUsersId());
				comparableList.add(userPolicyComparable);
			}
			// 按时间从大到小排序用户策略
			Collections.sort(comparableList);
			// 推送用户策略到极光服务器List(去除重复的)
			List<AndroidDeviceUsers> tempDeviceUserList = new ArrayList<AndroidDeviceUsers>();
			AndroidDeviceUsers pushUser = null;
			for (UserPolicyComparable parable : comparableList) {
				int j = 0;
				for (AndroidDeviceUsers deviceUsers : tempDeviceUserList) {
					if(parable.getUsersId().equals(deviceUsers.getUsersId())){
						j++;
					}
				}
				if(j==0){
					pushUser = new AndroidDeviceUsers();
					pushUser.setId(parable.getId());
					pushUser.setAndroidDevicePolicyId(parable.getAndroidDevicePolicyId());
					pushUser.setUsersId(parable.getUsersId());
					pushUser.setFatherId(parable.getFatherId());
					pushUser.setCreateDate(parable.getCreateDate());
					Date date = new Date(parable.getUpdateDate());
					pushUser.setUpdateDate(date);
					tempDeviceUserList.add(pushUser);
				}
			}
			// 将Android设备策略用户设置到设备里面s
			AndroidDevicePolicy tempDevicePolicy = null;
			List<Integer> tempDeviceUsersList = null;
			DataGridModel params = null;
			for(Integer policyId:policySet){
				if(policyId!=null&&!Constant.EMPTY_STR.equals(String.valueOf(policyId))&&policyId==0){
					tempDevicePolicy = getDefaultPolicy();
				} else {
				   tempDevicePolicy = androidDevicePolicyDao.selectByPrimaryKey(policyId);
				   if(tempDevicePolicy!=null){
					   params = new DataGridModel();
					   params.getParams().put("policyId", policyId);
					   tempDevicePolicy.setUrlList(netBehaviorDao.selectBWUrlListByIdList(params));
				   }
				   
				}
				tempDeviceUsersList = new ArrayList<Integer>();
				for (AndroidDeviceUsers user : tempDeviceUserList) {
					if(policyId!=null&&policyId.equals(user.getAndroidDevicePolicyId())){
					    tempDeviceUsersList.add(user.getUsersId());
					}
				}
				if(tempDeviceUsersList.size()>0){
					tempDevicePolicy.setUserIdList(tempDeviceUsersList);
					jpushList.add(tempDevicePolicy);
				}
			}
		}
	   	return jpushList;
	}
	
	/**
	 * 根据当前用户获取以前的Ios设备策略或默认策略
	 * @param id:主键
	 * @param orgId:机构外键
	 * @param delDepartIds:要删除的部门id列表
	 * @param delIosPolicyDepartList:要删除的虚拟组外键
	 * @param delVirtualIds:要删除的虚拟组id
	 * @param policySet:策略id set
	 * @param managerId:操作员外键
	 * @param delIosPolicyVirtualList:要删除的Ios策略虚拟组列表
	 * @param delIosPolicyUserList:要删除的Ios策略用户列表
	 * @return 返回用户对应的策略列表
	 */
	@SuppressWarnings("unchecked")
	public List<IosDevicePolicy> getJpushIosList(Object id,Integer orgId,List<Integer> delDepartIds,List<IospolicyDepartment> delIosPolicyDepartList,
			List<Integer> delVirtualIds,Set<Integer> policySet,Integer managerId,List<IosPolicyVirtual> delIosPolicyVirtualList,
			List<IosPolicyUser> delIosPolicyUserList){
		List<IosDevicePolicy> jpushList = new ArrayList<IosDevicePolicy>();
		// 该条策略是否启用(默认是启用),当该表是启用的时候，该条策略推送极光,如果是禁用，则不推送。
		if(id!=null&&!Constant.EMPTY_STR.equals(id)){
			List<IosPolicyUser> allUserPolicy = new ArrayList<IosPolicyUser>();
			// 存储根据部门id获取该下面的用户
			List<IosPolicyUser> delDepartUserList = new ArrayList<IosPolicyUser>();
			if(delDepartIds.size()>0){
				DataGridModel delDepartParams = new DataGridModel();
				delDepartParams.getParams().put("orgId", orgId);
				delDepartParams.getParams().put("list", delDepartIds);
				// 根据部门id list参数获取用户列表
				delDepartUserList = iospolicyDepartmentDao.selectDepartUserList(delDepartParams);
			}
			IosPolicyUser user = null;
			int i = 0;
			for(IosPolicyUser u:delDepartUserList){
				i++;
				user = new IosPolicyUser();
				user.setId(CommUtil.getPrimaryKey()+i);
				user.setUserId(u.getUserId());
				user.setFatherId(user.getFatherId());
				for(IospolicyDepartment depart:delIosPolicyDepartList){
					if(depart.getOrgStructureId().equals(u.getFatherId())){
						user.setIosDevicePolicyId(depart.getIosDevicePolicyId());
						user.setUpdateDate(depart.getUpdateDate());
						user.setCreateDate(depart.getCreateDate());
					}
				}
				allUserPolicy.add(user);
			}
			// 根据虚拟组获取该下面的用户(删除的)
			List<IosPolicyUser> delVirtualIdsList = new ArrayList<IosPolicyUser>();
			if(delVirtualIds.size()>0){
				DataGridModel virtualParams = new DataGridModel();
				virtualParams.getParams().put("orgId", orgId);
				virtualParams.getParams().put("list", delVirtualIds);
				// 根据虚拟组参数id,list获取用户列表id
				delVirtualIdsList = iosPolicyVirtualDao.selectVirtualUserList(virtualParams);
			}
			for (IosPolicyUser u : delVirtualIdsList) {
				i++;
				user = new IosPolicyUser();
				user.setId(CommUtil.getPrimaryKey()+i);
				user.setUserId(u.getUserId());
				user.setFatherId(u.getFatherId());
				for(IosPolicyVirtual virtual:delIosPolicyVirtualList){
					if(virtual.getVirtualGroupId().equals(u.getFatherId())){
						user.setIosDevicePolicyId(virtual.getIosDevicePolicyId());
						user.setUpdateDate(virtual.getUpdateDate());
						user.setCreateDate(virtual.getCreateDate());
					}
				}
				allUserPolicy.add(user);
			}
			if(delIosPolicyUserList.size()>0){
				allUserPolicy.addAll(delIosPolicyUserList);
			}
			// 调用用户策略时间比较器
			List<UserPolicyComparable> comparableList = new ArrayList<UserPolicyComparable>();
			UserPolicyComparable userPolicyComparable = null;
			for(IosPolicyUser iosUser:allUserPolicy){
				userPolicyComparable = new UserPolicyComparable();
				userPolicyComparable.setId(iosUser.getId());
				userPolicyComparable.setFatherId(iosUser.getFatherId());
				userPolicyComparable.setAndroidDevicePolicyId(iosUser.getIosDevicePolicyId());
				userPolicyComparable.setCreateDate(iosUser.getCreateDate());
				userPolicyComparable.setUpdateDate(iosUser.getUpdateDate().getTime());
				userPolicyComparable.setUsersId(iosUser.getUserId());
				comparableList.add(userPolicyComparable);
			}
			// 按时间从大到小排序用户策略
			Collections.sort(comparableList);
			// 推送用户策略到苹果服务器List(去除重复的)
			List<IosPolicyUser> tempPolicyUserList = new ArrayList<IosPolicyUser>();
			IosPolicyUser iosPolicyUser = null;
			for(UserPolicyComparable comparable:comparableList){
				int j = 0;
				for(IosPolicyUser tempUser:tempPolicyUserList){
					if(comparable.getUsersId().equals(tempUser.getUserId())){
						j++;
					}
				}
				if(j==0){
					iosPolicyUser = new IosPolicyUser();
					iosPolicyUser.setId(comparable.getId());
					iosPolicyUser.setIosDevicePolicyId(comparable.getAndroidDevicePolicyId());
					iosPolicyUser.setUserId(comparable.getUsersId());
					iosPolicyUser.setFatherId(comparable.getFatherId());
					iosPolicyUser.setCreateDate(comparable.getCreateDate());
				    Date date = new Date(comparable.getUpdateDate());
				    iosPolicyUser.setUpdateDate(date);
				    tempPolicyUserList.add(iosPolicyUser);
				}
			}
			// 将Ios设备策略用户设置到设备里面
			IosDevicePolicy tempIosDevicePolicy = null;
			List<Integer> tempUserList = null;
			DataGridModel params = null;
			for(Integer policyId:policySet){
				if(policyId!=null&&!Constant.EMPTY_STR.equals(String.valueOf(policyId))&&policyId==0){
					tempIosDevicePolicy = getIosDefaultPolicy();
				} else {
					tempIosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(policyId);
					if(tempIosDevicePolicy!=null){
						params = new DataGridModel();
						params.getParams().put("policyId", policyId);
					}
				}
				tempUserList = new ArrayList<Integer>();
				for(IosPolicyUser oldUser:tempPolicyUserList){
					if(policyId!=null&&policyId.equals(oldUser.getIosDevicePolicyId())){
					    tempUserList.add(oldUser.getUserId());
					}
				}
				if(tempUserList.size()>0){
					// 根据用户id列表获取设备的device token
					List<String> deviceTokenList = deviceBasicInfoDao.findDeviceTokenListByUserIds(tempUserList);
					tempIosDevicePolicy.setDeviceTokenList(deviceTokenList);
					tempIosDevicePolicy.setUserIdList(tempUserList);
					jpushList.add(tempIosDevicePolicy);
				}
			}
		}
		return jpushList;
	}
	
	/**
	 * 根据机构下获取所有的android策略
	 */
	@Override
	public List<AndroidDevicePolicy> findAll(Integer orgId) {
		return (List<AndroidDevicePolicy>) androidDevicePolicyDao.findAll(orgId);
	}

	@Override
	public AndroidDeviceUsers findOneByEntity(AndroidDeviceUsers entity) {
		return androidDeviceUsersDao.findOneByEntity(entity);
	}

	@Override
	public int insertAndroidDeviceUser(AndroidDeviceUsers entity) {
		return androidDeviceUsersDao.insert(entity);
	}

	@Override
	public int updateAndroidDeviceUser(AndroidDeviceUsers entity) {
		return androidDeviceUsersDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public Map<String, Object> findUserDevicePolicy(Integer userId,Integer orgId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Integer android_device_policyId=androidDeviceUsersDao.findOne(userId, orgId);
		Integer ios_device_policyId = iosPolicyUserDao.findOne(userId, orgId);
		map.put(DeviceTypeStatus.SOFTTEK_ANDROID.toString(), android_device_policyId==null?Constant.NO:android_device_policyId.toString());
		map.put(DeviceTypeStatus.SOFTTEK_IOS.toString(), ios_device_policyId==null?Constant.NO:ios_device_policyId.toString());
		return map;
	}

	@Override
	public AndroidDevicePolicy findOneAndroidDevicePolicy(Integer pKey) {
		return androidDevicePolicyDao.selectByPrimaryKey(pKey);
	}

	/**
	 * 判断策略名称是否存在
	 */
	@Override
	public boolean exists(String name,Integer id,Integer orgId) {
		// 新增功能(判断如何存在一个策略名称一样，则提示已存在)
		DataGridModel objParams = new DataGridModel();
		objParams.getParams().put("orgId", orgId);
		objParams.getParams().put("name", name);
		if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))){
			Integer existSize = androidDevicePolicyDao.selectDevicePolicyByName(objParams);
			if(existSize>0){
				return true;
			}
		} else {
			objParams.getParams().put("id", Integer.valueOf(String.valueOf(id)));
			Integer existSize = androidDevicePolicyDao.selectDevicePolicyByName(objParams);
			// 编辑功能(判断如果存在一个以上策略名称一样，则提示已存在，因为本身在数据库存在一条名称一样)
			if(existSize>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断策略名称是否存在
	 */
	@Override
	public boolean iosExists(String name,Integer id,Integer orgId) {
		// 新增功能(判断如何存在一个策略名称一样，则提示已存在)
		DataGridModel objParams = new DataGridModel();
		objParams.getParams().put("orgId", orgId);
		objParams.getParams().put("name", name);
		if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))){
			Integer existSize = iosDevicePolicyDao.selectDevicePolicyByName(objParams);
			if(existSize>0){
				return true;
			}
		} else {
			objParams.getParams().put("id", Integer.valueOf(String.valueOf(id)));
			Integer existSize = iosDevicePolicyDao.selectDevicePolicyByName(objParams);
			// 编辑功能(判断如果存在一个以上策略名称一样，则提示已存在，因为本身在数据库存在一条名称一样)
			if(existSize>0){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 判断策略名称是否存在
	 */
	@Override
	public boolean isNameListExists(String name,Integer id,Integer orgId) {
		// 新增功能(判断如何存在一个策略名称一样，则提示已存在)
		DataGridModel objParams = new DataGridModel();
		objParams.getParams().put("orgId", orgId);
		objParams.getParams().put("name", name);
		if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))){
			Integer existSize = nameListDao.selectNameListById(objParams);
			if(existSize>0){
				return true;
			}
		} else {
			objParams.getParams().put("id", Integer.valueOf(id.toString()));
			Integer existSize = nameListDao.selectNameListById(objParams);
			// 编辑功能(判断如果存在一个以上策略名称一样，则提示已存在，因为本身在数据库存在一条名称一样)
			if(existSize>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 保存黑白名单
	 */
	@Override
	public ResultDTO saveNameList(HttpServletRequest request,
			DataGridModel params) {
		ResultDTO resultDto = new ResultDTO();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		if (obj == null) {
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.invalid.lable",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if (orgObj == null) {
			resultDto.type = BaseDTO.WARNING;
			resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.invalid.lable",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
			OrganizationModel organization = (OrganizationModel)orgObj;
			ManagerModel manager = (ManagerModel) obj;
			Object id = params.getParams().get("id");
			Object appstr = params.getParams().get("appstr");
			Object nameType = params.getParams().get("nameType");
			Object name = params.getParams().get("listName");
			Object remark = params.getParams().get("remark");
			NameList nameList = new NameList();
			if(name!=null&&!Constant.EMPTY_STR.equals(name)){
				nameList.setListName(String.valueOf(name));
			}
			if(nameType!=null&&!Constant.EMPTY_STR.equals(nameType)){
				nameList.setListType(String.valueOf(nameType));
			}
			if(remark!=null&&!Constant.EMPTY_STR.equals(remark)){
				nameList.setRemark(String.valueOf(remark));
			}
			nameList.setOrgId(organization.getId());
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
		        Integer intId = Integer.valueOf(id.toString());
				nameList.setId(intId);
				nameList.setUpdateDate(new Date());
				nameList.setUpdateUser(manager.getId());
				int size = nameListDao.updateByPrimaryKeySelective(nameList);
				if(size==0){
					resultDto.type = BaseDTO.DANGER;
					resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.failed.lable",null, LocaleContextHolder.getLocale());
					return resultDto;
				}
				// 根据名单id删除相关的应用 不需要返回值判断是否更新
				nameListDao.updateAppListByNameId(intId);
				nameListDao.updateAppNameListByNameId(intId);
			} else {
				nameList.setCreateDate(new Date());
				nameList.setCreateUser(manager.getId());
				nameList.setUpdateDate(new Date());
				nameList.setUpdateUser(manager.getId());
				int size = nameListDao.insertSelective(nameList);
				if(size==0){
					resultDto.type = BaseDTO.DANGER;
					resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.failed.lable",null, LocaleContextHolder.getLocale());
					return resultDto;
				}
			}
			// json字符串转换成javabean对象
			Gson gson = new Gson();
			List<AppList> list = gson.fromJson((String) appstr, new TypeToken<List<AppList>>(){}.getType());
			// 应用名单关联表
			List<ApplicationNameList>  appNameList = new ArrayList<ApplicationNameList>();
			// 应用表
			List<AppList> appList = new ArrayList<AppList>();
			ApplicationNameList appName = null;
			AppList app = null;
			if(CollectionUtils.isNotEmpty(list)) {
				for (AppList temp : list) {
					app = new AppList();
					app.setAppId(temp.getAppId());
					app.setAppType(temp.getAppType());
					app.setAppName(temp.getAppName());
					app.setCreateDate(new Date());
					app.setCreateUser(manager.getId());
					app.setUpdateDate(new Date());
					app.setUpdateUser(manager.getId());
					appList.add(app);
					appListDao.insertSelective(app);
					appName = new ApplicationNameList();
					appName.setAppId(app.getId());
					appName.setCreateDate(new Date());
					appName.setCreateUser(manager.getId());
					appName.setUpdateDate(new Date());
					appName.setUpdateUser(manager.getId());
					appName.setNameListId(nameList.getId());
					applicationNameListDao.insertSelective(appName);
					appNameList.add(appName);
				}
			}
		resultDto.type = BaseDTO.SUCCESS;
		resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.success.lable",null, LocaleContextHolder.getLocale());
		return resultDto;
	}

	@Override
	public int getDevicePolicySize(Integer orgid) {
		return androidDevicePolicyDao.getDevicePolicyCount(orgid);
	}

	/**
	 *  编辑应用
	 * @param id
	 * @return
	 */
	@Override
	public Object editApplication(Integer id) {
		NameList nameList = nameListDao.selectByPrimaryKey(id);
		List<AppList> appLists = nameListDao.selectAppListByNameListId(id);
        nameList.setAppList(appLists);
		return nameList;
	}

	/**
	 * 查询网页黑白名单
	 */
	@Override
	public Page selectNetNameList(ManagerModel user,Integer orgId, String name, String type, Integer begin, Integer num) {
		DataGridModel params = new DataGridModel();
		params.getParams().put("type",type);
		params.getParams().put("orgId", orgId);
/*		params.getParams().put("user", user);*/
		params.setBegin(begin);
		params.setNum(num);
		Page p = new Page();	
		int total = androidDevicePolicyDao.selectNetNameSize(params);
		p.setData(androidDevicePolicyDao.selectNetNameList(params));
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

    /**
     * 根据策略id获取策略
     */
	@Override
	public AndroidDevicePolicy getPolicy(Integer id) {
		AndroidDevicePolicy policy = androidDevicePolicyDao.selectByPrimaryKey(id);
		DataGridModel params = new DataGridModel();
		params.getParams().put("policyId", id);
		if(policy!=null&&policy.getIsNetLimit()!=null&&Constant.YES.equals(policy.getIsNetLimit())){
			String visitTimeStr = policy.getVisitTimeStr();
			if(visitTimeStr!=null&&!Constant.EMPTY_STR.equals(visitTimeStr)){
				boolean result = compareDate(visitTimeStr);
				if(!result){
					policy.setIsNetLimit(Constant.NO);
				}
			}
		}
		List<BlackWhiteListUrl> currentList = new ArrayList<BlackWhiteListUrl>();
		currentList.addAll(netBehaviorDao.selectBWUrlListByIdList(params));
		policy.setUrlList(currentList);
		List<WifiConfigure> wifiList = wifiConfigureDao.selectByPolicyKey(id);
		policy.setWifiList(wifiList);
		List<AppList> appList = androidBlackListDao.selectAppListByIdList(params);
		policy.setAppList(appList);
		logger.info("push policy="+policy.toString());
		return policy;
	}
	
	/**
	 * 开辟一个线程极光推送用户策略
	 * @param userSet:当前策略推送到用户ids
	 * @param androidDevicePolicy：当前策略推送
	 * @param jpushList：推送除本策略时间最近的策略
	 * @param deviceUserList:修改黑白名单推送策略
	 */
	public void jpushUserPolicy(Set<Integer> userSet,AndroidDevicePolicy androidDevicePolicy,List<AndroidDevicePolicy> jpushList,List<AndroidDeviceUsers> deviceUserList){
		// (newFixedThreadPool)创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
		ExecutorService pool = Executors.newFixedThreadPool(100);
		// 推送用户以前最近的策略
		HashMap<String, String> hashMap = null;
		if(CollectionUtils.isNotEmpty(jpushList)){
			hashMap = new HashMap<String, String>();
			List<KeyValue> data = new ArrayList<KeyValue>();
			for (AndroidDevicePolicy policy : jpushList) {
				hashMap = new HashMap<String, String>();
				hashMap.put("oldPolicy", policy.getId().toString());
				// 转换成用线程操作队列
	            List<KeyValue> singleList = MqttQueue.generateDatas(policy.getUserIdList(), hashMap);
	            data.addAll(singleList);
			}
			 MqttPushThread mqttPush = new MqttPushThread(data);
			 taskExecutor.execute(mqttPush);
		}
		// 修改黑白名单推送策略
		if(CollectionUtils.isNotEmpty(deviceUserList)){
			List<KeyValue> data = new ArrayList<KeyValue>();
			for (AndroidDeviceUsers user : deviceUserList) {
				hashMap = new HashMap<String, String>();
				hashMap.put("oldPolicy", user.getAndroidDevicePolicyId().toString());
				String topic = String.valueOf(user.getUsersId());
				data.add(MqttQueue.generateSingleData(topic, hashMap));
			}
            MqttPushThread mqttPush = new MqttPushThread(data);
            taskExecutor.execute(mqttPush);
		}
		// 推送当前策略
		if(CollectionUtils.isNotEmpty(userSet)&&androidDevicePolicy!=null){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("androidDevicePolicy", androidDevicePolicy.getId().toString());
			// 转换成用线程操作队列
			List<Integer> list = new ArrayList<Integer>(userSet);
            List<KeyValue> data = MqttQueue.generateDatas(list, map);
            MqttPushThread mqttPush = new MqttPushThread(data);
            taskExecutor.execute(mqttPush);
/*			for (Integer id : userSet) {
				String topic = String.valueOf(id);
				MqProducerThread thread = new MqProducerThread(topic, null, null, 2, map);
				taskExecutor.execute(thread);
			}*/
		 }

		pool.shutdown();
	}

	/**
	 * 修改黑白名单的时候对应的策略也做相应的修改
	 * @param params
	 * @return
	 */
	@Override
	public ResultDTO jpushPoicyByBWId(Integer bwId){
		ResultDTO resultDTO = new ResultDTO();
		DataGridModel params = new DataGridModel();
		params.getParams().put("bwId", bwId);
		// 根据黑白名单id获取对应的策略列表
		try {
			List<Integer> policyList = androidPolicyListDao.selectPolicyidListByBWID(params);
			List<AndroidDeviceUsers> androidUserList = new ArrayList<AndroidDeviceUsers>();
			String userStr = Constant.EMPTY_STR;
			// 根据策略ids获取用户ids
			if(policyList.size()>0){
				// 根据策略获取关联的授权用户
				List<AndroidDeviceUsers> userList = androidDeviceUsersDao.selectUserIdsByPolicyList(policyList);
				if(userList.size()>0){
					for (AndroidDeviceUsers androidDeviceUsers : userList) {
						String userId = androidDeviceUsers.getUsersId()+SplitSymbol.COMMA_SYMBOL;
						if(userStr.indexOf(userId)<0){
							androidUserList.add(androidDeviceUsers);
						}
						userStr += androidDeviceUsers.getUsersId()+SplitSymbol.COMMA_SYMBOL;
					}
				}
				jpushUserPolicy(null,null,null,androidUserList);
			}
		} catch(Exception e){
			logger.error("something wrong when push device policy by black white id,bwid is "+bwId+"exception message is " + e.getMessage());
			resultDTO.message="修改黑白名单操作失败";
			resultDTO.type = BaseDTO.DANGER;
			return resultDTO;		
		}
		resultDTO.message="修改黑白名单操作成功";
		resultDTO.type = BaseDTO.SUCCESS;
		return resultDTO;
	}

	/**
	 * 用户管理模块查询该机构下所有的android策略
	 */
	@Override
	public List<AndroidDevicePolicy> findAllAndroidDevicePolicyByMap(Map<String, Object> maps) {
		return androidDevicePolicyDao.findAllByMap(maps);
	}

	/**
	 * 保存Ios策略
	 */
	@Override
	public ResultDTO saveIosPolicy(HttpServletRequest request, DataGridModel params){
		ResultDTO result = new ResultDTO();
		HttpSession session = request.getSession();
		// 从session中获取当前登录的管理员
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		if (obj == null) {
			result.type = BaseDTO.WARNING;
		    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.invalid.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		ManagerModel manager = (ManagerModel)obj;
		// 从session中获取当前登录管理员所属机构
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if (orgObj == null) {
			result.type = BaseDTO.WARNING;
		    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.invalid.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		Object enableBlacklist = params.getParams().get("enableBlacklist");
		Object chooseEnableIds = params.getParams().get("chooseEnableIds");
		Object enableAppNameList = params.getParams().get("enableAppNameList");
		Object chooseAppEnableIds = params.getParams().get("chooseAppEnableIds");
		Object visitLimit = params.getParams().get("visitLimit");
		Object visitTimeStr = params.getParams().get("visitTimeStr");
		
		OrganizationModel organization = (OrganizationModel)orgObj;
		String isEnable = Constant.YES;
		IosDevicePolicy iosDevicePolicy = getIosDevicePolicy(params);
		Integer id = iosDevicePolicy.getId();
		if(id!=null&&!Constant.EMPTY_STR.equals(id)){
			if(!Constant.YES.equals(iosDevicePolicy.getIsEnable())){
				isEnable = Constant.NO;
			}
		}
		
		// 获取选择的部门id
		Object chooseDepartIds = params.getParams().get("chooseDepartIds");
		// 获取选择的虚拟组ID
		Object virtualIds = params.getParams().get("virtualIds");
		// 获取选择的用户id
		Object userIds = params.getParams().get("userIds");
		iosDevicePolicy.setIsEnable(isEnable);
		// 是否启用网页黑名单(0.启用白名单 1.启用黑名单)
		if(enableBlacklist!=null){
			iosDevicePolicy.setEnableBlacklist(String.valueOf(enableBlacklist));
		}
		
		// 是否启用应用黑名单(0.启用白名单  1.启用黑名单)
		if(enableAppNameList!=null){
			iosDevicePolicy.setEnableAppNameList(String.valueOf(enableAppNameList));
		}
		iosDevicePolicy.setOrgId(organization.getId());
		if(visitTimeStr!=null){
		    iosDevicePolicy.setVisitTimeStr(String.valueOf(visitTimeStr));
		}
		if(visitLimit!=null){
			iosDevicePolicy.setIsNetLimit(String.valueOf(visitLimit));
		}
		if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))&&id.equals(0)){
			iosDevicePolicy.setIsEnable(Constant.YES);
			iosDevicePolicy.setCreateDate(new Date());
			iosDevicePolicy.setCreateUser(manager.getId());
			iosDevicePolicy.setUpdateDate(new Date());
			iosDevicePolicy.setUpdateUser(manager.getId());
			iosDevicePolicy.setVersion(CommUtil.generate32UUID());
			int size = iosDevicePolicyDao.insertSelective(iosDevicePolicy);
			if(size==0){
			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
				result.type = BaseDTO.DANGER;
				return result;
			}
		} else {
			iosDevicePolicy.setUpdateDate(new Date());
			iosDevicePolicy.setUpdateUser(manager.getId());
			iosDevicePolicy.setVersion(CommUtil.generate32UUID());
			int size = iosDevicePolicyDao.updateByPrimaryKeySelective(iosDevicePolicy);
			if(size == 0){
			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
				result.type = BaseDTO.DANGER;
				return result;
			}
			// 删除WIFI配置数据
			iosWifiConfigureDao.delIosWifiConfigureByPolicyId(iosDevicePolicy.getId());
		}
		
		// 批量插入iosWifi配置
		insertBatchIosWifiConfigure(params.getIosList(), iosDevicePolicy.getId(), manager.getId());
		
		// 存放的是该策略要删除用户关联，用于删除的用户找到上一级策略
		List<Integer> delUserIds = new ArrayList<Integer>();
		// 存放策略id推送到苹果服务器
		Set<Integer> policySet = new LinkedHashSet<Integer>();
		// 当保存策略的时候，选择的授权用户id list
		Set<Integer> userSet = new LinkedHashSet<Integer>();
		// 当保存策略的时候，选择的授权用户数据
		List<IosPolicyUser> iosPolicyUserList = new ArrayList<IosPolicyUser>();
		// ios设备策略授权用户对象
		IosPolicyUser iosPolicyUser = null;
		// 存放的是该条策略删除的用户找到他上一级策略的数据(用户对应策略)
		List<IosPolicyUser> delIosPolicyUserList = new ArrayList<IosPolicyUser>();
		// ios设备策略授权用户关联表
		IosPolicyUser delIosPolicyUser = null;
		
		DataGridModel delParams = null;
		// userIds表示新增或修改策略的时候，选择的授权用户不为空!
		if(userIds!=null&&!Constant.EMPTY_STR.equals(String.valueOf(userIds))){
			// 临时选择的授权用户变量
			String tempUserIds = userIds.toString() + SplitSymbol.COMMA_SYMBOL;
			if(id!=null&&!Constant.EMPTY_STR.equals(iosDevicePolicy.getId())){
				Integer intId = Integer.valueOf(id.toString());
				// 根据策略id获取授权关联的用户(要删除的)
				List<Integer> userList = iosPolicyUserDao.selectUserIdByPolicyId(intId);
				// 存储当前选中的用户
				for( Integer userId:userList){
					// 当编辑策略的时候，将该策略的以前的授权用户是否存在
					// 存在
					if(tempUserIds.indexOf(userId+SplitSymbol.COMMA_SYMBOL)>=0){
						String oldChar = userId + SplitSymbol.COMMA_SYMBOL;
						tempUserIds = tempUserIds.replace(oldChar, Constant.EMPTY_STR);
					} else {// 不存在
						delUserIds.add(userId);
						delParams = new DataGridModel();
						delParams.getParams().put("id", id);
						delParams.getParams().put("orgId", organization.getId());
						delParams.getParams().put("userId", userId);
						// 根据该用户找到它上一级策略，如果存在则采用上级策略，如果不存在则用默认策略
						List<IosPolicyUser> tempUserList = iosPolicyUserDao.selectPolicyIdByUserId(delParams);
						// 如果tempUserList的数据存在则采用上一级策略
						if(tempUserList.size()>0){
							delIosPolicyUser = tempUserList.get(0);
							delIosPolicyUserList.add(delIosPolicyUser);
							policySet.add(delIosPolicyUser.getIosDevicePolicyId());
						} else {// 如果不存在则采用默认策略
							delIosPolicyUser = new IosPolicyUser();
							delIosPolicyUser.setIosDevicePolicyId(0);
							delIosPolicyUser.setCreateDate(new Date());
							delIosPolicyUser.setUpdateDate(getDefaultDate());
							delIosPolicyUser.setUserId(userId);
							delIosPolicyUserList.add(delIosPolicyUser);
							policySet.add(0);
						}
					}
				}
				// 根据策略id删除iosPolicyUser关联数据
				iosPolicyUserDao.deleteByPolicyId(intId);
				if(StringUtil.isNotBlank(tempUserIds)){
					tempUserIds = tempUserIds.substring(0, tempUserIds.length()-1);
	/*				userIds = tempUserIds;*/
				}
			}
			String users = userIds.toString();
			if(StringUtil.isNotBlank(users)){
				String[] usersList = users.split(SplitSymbol.COMMA_SYMBOL);
				Integer userId = null;
				for(String s:usersList){
					userId =  Integer.valueOf(s);
					userSet.add(userId);
					iosPolicyUser = new IosPolicyUser();
					iosPolicyUser.setIosDevicePolicyId(iosDevicePolicy.getId());
					iosPolicyUser.setUserId(userId);
					iosPolicyUser.setCreateDate(new Date());
					iosPolicyUser.setCreateUser(manager.getId());
					iosPolicyUser.setUpdateDate(new Date());
					iosPolicyUser.setUpdateUser(manager.getId());
					iosPolicyUserList.add(iosPolicyUser);
				} 
				if(iosPolicyUserList.size()>0){
					// 批量插入授权用户关联表
					int deviceUserSize = iosPolicyUserDao.insertBatchIosPolicyUser(iosPolicyUserList);
				    if(deviceUserSize==0){
					    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
				    }
				}
			}
		} else {// 当保存策略的时候，未选择全部授权用户并且存在策略之前关联的授权用户
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intId = Integer.valueOf(id.toString());
				// 获取该策略对应之前的设授权用户(要删除的)
				List<Integer> userList = iosPolicyUserDao.selectUserIdByPolicyId(intId);
				// 存储当前选中的用户id
				for(Integer userId:userList){
					delUserIds.add(userId);
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("userId", userId);
					List<IosPolicyUser> tempUserList = iosPolicyUserDao.selectPolicyIdByUserId(delParams);
					// 查询当前用户以前的策略是否存在
					if(tempUserList.size()>0){
						delIosPolicyUser = tempUserList.get(0);
						delIosPolicyUserList.add(delIosPolicyUser);
						policySet.add(delIosPolicyUser.getIosDevicePolicyId());
					} else {// 不存在则用默认策略
						delIosPolicyUser = new IosPolicyUser();
						delIosPolicyUser.setIosDevicePolicyId(0);
						delIosPolicyUser.setUserId(userId);
						delIosPolicyUser.setCreateUser(manager.getId());
						delIosPolicyUser.setCreateDate(new Date());
						delIosPolicyUser.setUpdateUser(manager.getId());
						delIosPolicyUser.setUpdateDate(getDefaultDate());
						delIosPolicyUserList.add(delIosPolicyUser);
						policySet.add(0);
					}
				}
				// 根据策略删除关联的授权用户
				iosPolicyUserDao.deleteByPolicyId(intId);
			}
		}
		
		// 存放的是该策略要删除部门关联，用于删除的部门找到上一级策略
		List<Integer> delDepartIds = new ArrayList<Integer>();
		// 当保存策略的时候，选择的授权部门列表数据
		List<IospolicyDepartment> iosPolicyDepartmentList = new ArrayList<IospolicyDepartment>();
		// Ios设备策略授权部门对象(当前)
		IospolicyDepartment iospolicyDepartment = null;
		// 存放的是该条策略删除的找到他上一级策略的数据(部门对应策略)
		List<IospolicyDepartment> delIosPolicyDepartmentList = new ArrayList<IospolicyDepartment>();
		// Ios设备策略授权部门对象(删除的)
		IospolicyDepartment delIospolicyDepartment = null;

		// chooseDepartIds表示新增或者修改策略的时候,选择的授权部门不为空!
		if(chooseDepartIds!=null&&!Constant.EMPTY_STR.equals(chooseDepartIds)){
			// 临时选择的授权部门变量
			String tempDepartIds = chooseDepartIds.toString() + SplitSymbol.COMMA_SYMBOL;
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intId = Integer.valueOf(id.toString());
				// 根据策略id获取授权关联的部门(要删除的)
				List<Integer> departIdsList = iospolicyDepartmentDao.selectDepartmentById(intId);
				// 存储当前选中的用户
				for (Integer departId : departIdsList) {
					// 当编辑策略的时候,将判断该策略的以前授权部门是否存在
					String oldDepart = departId+SplitSymbol.COMMA_SYMBOL;
					// 存在
					if(tempDepartIds.indexOf(oldDepart)>=0){
						tempDepartIds.replace(oldDepart, Constant.EMPTY_STR);
					} else {// 不存在
						delDepartIds.add(departId);
						delParams = new DataGridModel();
						delParams.getParams().put("orgId", organization.getId());
						delParams.getParams().put("id", id);
						delParams.getParams().put("departId", departId);
						// 根据该用户找到它上一级策略，如果存在则采用上级策略，如果不存在则用默认策略
						List<IospolicyDepartment> tempDepartList = iospolicyDepartmentDao.selectPolicyByDepartId(delParams);
						if(tempDepartList.size()>0){
							IospolicyDepartment tempIospolicyDepartment = tempDepartList.get(0);
							delIosPolicyDepartmentList.add(tempIospolicyDepartment);
							policySet.add(tempIospolicyDepartment.getIosDevicePolicyId());
						} else {// 如果不存在则采用默认策略
							delIospolicyDepartment = new IospolicyDepartment();
							delIospolicyDepartment.setOrgStructureId(departId);
							delIospolicyDepartment.setIosDevicePolicyId(0);
							delIospolicyDepartment.setCreateDate(new Date());
							delIospolicyDepartment.setCreateUser(manager.getId());
							delIospolicyDepartment.setUpdateDate(getDefaultDate());
							delIospolicyDepartment.setUpdateUser(manager.getId());
							delIosPolicyDepartmentList.add(delIospolicyDepartment);
							policySet.add(0);
						}	
					}
				}
				// 根据策略id删除iosPolicyDepartment关联表数据
				iospolicyDepartmentDao.deleteByDepartId(intId);
				if(StringUtil.isNotBlank(tempDepartIds)){
					tempDepartIds = tempDepartIds.substring(0,tempDepartIds.length()-1);
	/*				chooseDepartIds = tempDepartIds;*/
				}
			}
			String departIds = chooseDepartIds.toString();
			if(StringUtil.isNotBlank(departIds)){
				String[] departList = departIds.split(SplitSymbol.COMMA_SYMBOL);
				for(String s:departList){
					iospolicyDepartment = new IospolicyDepartment();
					iospolicyDepartment.setIosDevicePolicyId(iosDevicePolicy.getId());
					iospolicyDepartment.setOrgStructureId(Integer.valueOf(s));
					iospolicyDepartment.setCreateDate(new Date());
					iospolicyDepartment.setCreateUser(manager.getId());
					iospolicyDepartment.setUpdateDate(new Date());
					iospolicyDepartment.setUpdateUser(manager.getId());
					iosPolicyDepartmentList.add(iospolicyDepartment);
				}
				if(iosPolicyDepartmentList.size()>0){
					// 批量插入授权部门关联表
					int departSize = iospolicyDepartmentDao.insertBatchIosDeviceDepartment(iosPolicyDepartmentList);
					   if(departSize==0){
	       			       result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
						   result.type = BaseDTO.DANGER;
						   return result;
	       		   }
				}
		    }
		} else {// 当保存策略的时候，未选择全部授权部门并且存在策略之前关联的授权部门
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intId = Integer.valueOf(id.toString());
				// 获取该策略对应之前的授权部门(要删除的)
				List<Integer> departIdsList = iospolicyDepartmentDao.selectDepartmentById(intId);
				// 存储当前选中的部门id
				for(Integer departId:departIdsList){
					delDepartIds.add(departId);
					delParams = new DataGridModel();
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("id", id);
					delParams.getParams().put("departId", departId);
					List<IospolicyDepartment> tempDepartList = iospolicyDepartmentDao.selectPolicyByDepartId(delParams);
					// 查询当前部门以前的策略是否存在
					if(tempDepartList.size()>0){
						delIospolicyDepartment = tempDepartList.get(0);
						delIosPolicyDepartmentList.add(delIospolicyDepartment);
						policySet.add(delIospolicyDepartment.getIosDevicePolicyId());
					} else {// 不存在则用默认策略
						delIospolicyDepartment = new IospolicyDepartment();
						delIospolicyDepartment.setIosDevicePolicyId(0);
						delIospolicyDepartment.setOrgStructureId(departId);
						delIospolicyDepartment.setCreateDate(new Date());
						delIospolicyDepartment.setCreateUser(manager.getId());
						delIospolicyDepartment.setUpdateDate(getDefaultDate());
						delIospolicyDepartment.setUpdateUser(manager.getId());
						delIosPolicyDepartmentList.add(delIospolicyDepartment);
						policySet.add(0);
					}
				}
				// 根据策略删除关联的授权部门
				iospolicyDepartmentDao.deleteByDepartId(intId);
			}
		}
		
		// Ios设备策略授权虚拟组关联表(当前的)
		List<IosPolicyVirtual> iosPolicyVirtualList = new ArrayList<IosPolicyVirtual>();
		// Ios设备策略授权虚拟组对象(当前的)
		IosPolicyVirtual iosPolicyVirtual = null;
		// Ios设备策略授权虚拟组关联表(删除的)
		List<IosPolicyVirtual> delIosPolicyVirtualList = new ArrayList<IosPolicyVirtual>();
		// Ios设备策略授权虚拟组对象(删除的)
		IosPolicyVirtual delIosPolicyVirtual = null;
		// 当保存设备策略的时候，将获取设备策略已存在的虚拟组id
		List<Integer> delVirtualIds = new ArrayList<Integer>();
		// virtualIds表示新增或者修改策略的时候，选择的授权虚拟组不为空
		if(virtualIds!=null&&!Constant.EMPTY_STR.equals(virtualIds)){
			// 临时选择授权用户变量
			String tempVirtualIds = virtualIds.toString() + SplitSymbol.COMMA_SYMBOL;
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intId = Integer.valueOf(id.toString());
				// 根据策略id获取授权关联的虚拟组(要删除的)
				List<Integer> virtualIdsList = iosPolicyVirtualDao.selectVirtualById(intId);
				// 存储当前选中的用户
				for(Integer virtualId:virtualIdsList){
					String oldChar = virtualId+SplitSymbol.COMMA_SYMBOL;
					// 当编辑策略的时候，将该策略的以前的授权用户是否存在
					// 存在
					if(tempVirtualIds.indexOf(oldChar)>=0){
						tempVirtualIds.replace(oldChar, Constant.EMPTY_STR);						
					} else {// 不存在
						delVirtualIds.add(virtualId);
						delParams = new DataGridModel();
						delParams.getParams().put("id", id);
						delParams.getParams().put("orgId", organization.getId());
						delParams.getParams().put("virtualId", virtualId);
						// 根据该虚拟组找到它上一级策略，如果存在则采用上级策略，如果不存在则用默认策略
						List<IosPolicyVirtual> tempVirtualList = iosPolicyVirtualDao.selectIosPolicyIdByVirtualId(delParams);
						// 如果tempUserList的数据存在则采用上一级策略
						if(tempVirtualList.size()>0){
							delIosPolicyVirtual = tempVirtualList.get(0);
							delIosPolicyVirtualList.add(delIosPolicyVirtual);
							policySet.add(delIosPolicyVirtual.getIosDevicePolicyId());
						} else {// 如果不存在则采用默认策略
							delIosPolicyVirtual = new IosPolicyVirtual();
							delIosPolicyVirtual.setIosDevicePolicyId(0);
							delIosPolicyVirtual.setVirtualGroupId(virtualId);
							delIosPolicyVirtual.setCreateDate(new Date());
							delIosPolicyVirtual.setCreateUser(manager.getId());
							delIosPolicyVirtual.setUpdateDate(getDefaultDate());
							delIosPolicyVirtual.setUpdateUser(manager.getId());
							delIosPolicyVirtualList.add(delIosPolicyVirtual);
							policySet.add(0);
						}
					}
				}
				// 根据策略id删除iosPolicyVirtual关联数据
				iosPolicyVirtualDao.deleteByVirtualGroupId(intId);
				if(StringUtil.isNotBlank(tempVirtualIds)){
					tempVirtualIds = tempVirtualIds.substring(0,tempVirtualIds.length()-1);
//					virtualIds = tempVirtualIds;
				}
			}
			String virIds = virtualIds.toString();
			if(StringUtil.isNotBlank(virIds)){
				String[] virtualList = virIds.split(SplitSymbol.COMMA_SYMBOL);
				for(String s:virtualList){
					iosPolicyVirtual = new IosPolicyVirtual();
					iosPolicyVirtual.setIosDevicePolicyId(iosDevicePolicy.getId());
					iosPolicyVirtual.setVirtualGroupId(Integer.valueOf(s));
					iosPolicyVirtual.setCreateDate(new Date());
					iosPolicyVirtual.setCreateUser(manager.getId());
					iosPolicyVirtual.setUpdateDate(new Date());
					iosPolicyVirtual.setUpdateUser(manager.getId());
					iosPolicyVirtualList.add(iosPolicyVirtual);
				}
				if(iosPolicyVirtualList.size()>0){
					// 批量插入授权虚拟组关联表
					int virtualSize = iosPolicyVirtualDao.insertBatchIosPolicyVirtual(iosPolicyVirtualList);
					if(virtualSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
			}
		} else {// 当保存策略的时候，未选择全部授权虚拟组并且存在策略之前关联的授权虚拟组
			//当保存策略的时候，虚拟组全部未选
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				Integer intId = Integer.valueOf(id.toString());
				// 获取该策略对应之前的设授权用户(要删除的)
				List<Integer> virtualIdsList = iosPolicyVirtualDao.selectVirtualById(intId);
				// 存储当前选中的用户id
				for(Integer virtualId:virtualIdsList){
					delVirtualIds.add(virtualId);
					delParams = new DataGridModel();
					delParams.getParams().put("id", id);
					delParams.getParams().put("orgId", organization.getId());
					delParams.getParams().put("virtualId", virtualId);
					List<IosPolicyVirtual> tempIosVirtualList = iosPolicyVirtualDao.selectIosPolicyIdByVirtualId(delParams);
					if(tempIosVirtualList.size()>0){
						IosPolicyVirtual tempVirtualGroup = tempIosVirtualList.get(0);
						delIosPolicyVirtualList.add(tempVirtualGroup);
						policySet.add(tempVirtualGroup.getIosDevicePolicyId());
					} else {
						delIosPolicyVirtual = new IosPolicyVirtual();
						delIosPolicyVirtual.setIosDevicePolicyId(0);
						delIosPolicyVirtual.setVirtualGroupId(virtualId);
						delIosPolicyVirtual.setCreateDate(new Date());
						delIosPolicyVirtual.setCreateUser(manager.getId());
						delIosPolicyVirtual.setUpdateDate(getDefaultDate());
						delIosPolicyVirtual.setUpdateUser(manager.getId());
						delIosPolicyVirtualList.add(delIosPolicyVirtual);
						policySet.add(0);
					}
				}
				iosPolicyVirtualDao.deleteByVirtualGroupId(intId);
			}
		}
		
		if(visitLimit!=null&&!Constant.EMPTY_STR.equals(visitLimit)){
			if(Constant.YES.equals(visitLimit)){
				iosDevicePolicy.setIsNetLimit(Constant.YES);
				if(visitTimeStr!=null){
					iosDevicePolicy.setVisitTimeStr(String.valueOf(visitTimeStr).trim());
				}
			} else {
				iosDevicePolicy.setIsNetLimit(Constant.NO);
			}
	    } 
		
		// 存放黑白名单URL
		List<BlackWhiteListUrl> currentList = new ArrayList<BlackWhiteListUrl>();
		// 存放前台选择的黑白名单Id参数
		List<Integer> idList = new ArrayList<Integer>();
		DataGridModel objParams = new DataGridModel();
		// 黑白名单列表
		List<IosPolicyList> iosPolicyList = new ArrayList<IosPolicyList>();
		IosPolicyList iosPolicy = null;
		if(chooseEnableIds!=null&&!Constant.EMPTY_STR.equals(String.valueOf(chooseEnableIds))){
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				iosPolicyListDao.updateByNamelistId(Integer.valueOf(id.toString()));
			}
			String enableIds = chooseEnableIds.toString();
			if(StringUtil.isNotBlank(enableIds)){
				String[] enableList = enableIds.split(SplitSymbol.COMMA_SYMBOL);
				for (String s : enableList) {
					Integer nameId = Integer.valueOf(s);
					iosPolicy = new IosPolicyList();
					iosPolicy.setIosDevicePolicyId(iosDevicePolicy.getId());
					iosPolicy.setNameListId(nameId);
					iosPolicy.setCreateDate(new Date());
					iosPolicy.setCreateUser(manager.getId());
					iosPolicy.setUpdateDate(new Date());
					iosPolicy.setUpdateUser(manager.getId());
					iosPolicyList.add(iosPolicy);
					idList.add(nameId);
				}
				if(enableList.length>0){
					int enableSize = iosPolicyListDao.insertBatchPolicyList(iosPolicyList);
					if(enableSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
				// 根据参数获取黑白名单url
				if(idList.size()>0){
					objParams.getParams().put("list", idList);
					currentList.addAll(netBehaviorDao.selectBWUrlListByIdList(objParams));
					iosDevicePolicy.setUrlList(currentList);
				}
			}
		}
		
		// 黑白名单列表
		List<IosBlackList> iosBlackList = new ArrayList<IosBlackList>();
		IosBlackList iosBlackPolicy = null;
		if(chooseAppEnableIds!=null&&chooseAppEnableIds!=""){
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				androidBlackListDao.updateByAppNamelistId(Integer.valueOf(id.toString()));
			}
			String enableIds = chooseAppEnableIds.toString();
			if(StringUtil.isNotBlank(enableIds)){
				String[] enableList = enableIds.split(SplitSymbol.COMMA_SYMBOL);
				Integer i = 0;
				for (String s : enableList) {
					Integer nameId = Integer.valueOf(s);
					i++;
					iosBlackPolicy = new IosBlackList();
					iosBlackPolicy.setIosDevicePolicyId(iosDevicePolicy.getId());
					iosBlackPolicy.setNameListId(nameId);
					iosBlackPolicy.setCreateDate(new Date());
					iosBlackPolicy.setCreateUser(manager.getId());
					iosBlackPolicy.setUpdateDate(new Date());
					iosBlackPolicy.setUpdateUser(manager.getId());
					iosBlackList.add(iosBlackPolicy);
				}
				if(enableList.length>0){
					int enableSize = iosBlackListDao.insertBatchBlackPolicyList(iosBlackList);
					if(enableSize==0){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
						result.type = BaseDTO.DANGER;
						return result;
					}
				}
			}
		}
		
		// 根据部门、虚拟组、用户查找以前最近的时间的策略并拼接存放list
		List<IosDevicePolicy> jpushList = getJpushIosList(iosDevicePolicy.getId(), organization.getId(), delDepartIds, delIosPolicyDepartmentList, delVirtualIds,
				policySet, manager.getId(), delIosPolicyVirtualList, delIosPolicyUserList);
	    // 当策略是启用的时候，需要苹果服务器推送消息。
		if(Constant.YES.equals(isEnable)){
	    	// 获取当前策略的用户
			Set<Integer> getUserSet = getIosCurrentUserIds(iosPolicyDepartmentList, iosPolicyVirtualList, organization.getId());
			userSet.addAll(getUserSet);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userSet", userSet);
			map.put("iosDevicePolicy", iosDevicePolicy);
			map.put("jpushList", jpushList);
			if(userSet.size()>0||jpushList.size()>0||(iosDevicePolicy.getUserIdList()!=null&&iosDevicePolicy.getUserIdList().size()>0)){
				// 获取每个设备对应的版本号列表
				List<IosPolicyVersionBean> list = getDeviceTokenList(userSet, iosDevicePolicy, jpushList);
				if(CollectionUtils.isNotEmpty(list)) {
					PushIosVersionThread thread = new PushIosVersionThread(null, 1, "", list);
					taskExecutor.execute(thread);
				}
				// ios mdm推送
				String status = abstractIosPush.nofity(map);
				if(BaseDTO.ERROR.equals(status)) {
				    result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
					result.type = BaseDTO.DANGER;
					return result;
				}
			}
	    }
		result.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.success.lable",null, LocaleContextHolder.getLocale());
		result.type = BaseDTO.SUCCESS;
		return result;
	}

	/**
	 * 返回每个device token对应的版本号列表
	 * @param userSet:用户ids
	 * @return
	 */
	private List<IosPolicyVersionBean> getDeviceTokenList(Set<Integer> userSet,IosDevicePolicy iosDevicePolicy,List<IosDevicePolicy> jpushList){
		List<IosPolicyVersionBean> list = new ArrayList<IosPolicyVersionBean>();
		IosPolicyVersionBean bean = null;
		// 将用户列表set转换成list
		if(userSet!=null&&userSet.size()>0&&iosDevicePolicy!=null){
			List<Integer> idList = new ArrayList<Integer>(userSet);
			List<String> deviceTokenList = deviceBasicInfoDao.findDeviceTokenListByUserIds(idList);
			for (String token : deviceTokenList) {
				bean = new IosPolicyVersionBean();
				bean.setDeviceToken(token.replace(" ", ""));
				bean.setVersion(iosDevicePolicy.getId()+iosDevicePolicy.getVersion());
			    list.add(bean);
			}
		}
		// 将jpushlist转换成List<IosPolicyVersionBean>方式推送
		if(jpushList!=null&&jpushList.size()>0){
			for (IosDevicePolicy policy : jpushList) {
				for (String token : policy.getDeviceTokenList()) {
					bean = new IosPolicyVersionBean();
					bean.setDeviceToken(token.replace(" ", ""));
					bean.setVersion(policy.getId()+policy.getVersion());
					list.add(bean);
				}
			}
		}
		return list;
	}
	
	/**
	 * 加载策略
	 */
	@Override
	public ResultDTO editIosPolicy(HttpServletRequest request) {
		ResultDTO resultDTO = new ResultDTO();
		String id = request.getParameter("id");
		IosDevicePolicy iosDevicePolicy = new IosDevicePolicy();
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			iosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(intId);
			resultDTO.result = iosDevicePolicy;
		}
		return resultDTO;
	}
	
	// 比较时间
	public boolean compareDate(String limitTimeStr){
		int week=DateTime.now().getDayOfWeek();
		//String limitTimeStr="|12:00-13:00,18:00-19:00|18:00-22:00|12:00-13:00,18:00-21:30|12:00-13:00,18:00-19:00|12:00-13:00,18:00-19:00|12:00-21:30|12:00-21:30|";
		Pattern reg=Pattern.compile("([0-2][0-9]:[0-9][0-9]-[0-2][0-9]:[0-9][0-9],{0,1})+");
		Matcher matcher=reg.matcher(limitTimeStr);
		int index=0;
		while (matcher.find()) {
			if(week==++index){
				String strMatch=matcher.group();
				reg=Pattern.compile("[0-2][0-9]:[0-9][0-9]-[0-2][0-9]:[0-9][0-9]");
				Matcher mat=reg.matcher(strMatch);
				while(mat.find()){
					String str=mat.group();
					if(StringUtils.contains(str, "-")){
						DateTime dTime=DateTime.now();
						String preTime=String.format("%s-%s-%s %s:00", dTime.getYear(),dTime.getMonthOfYear(),dTime.getDayOfMonth(),str.split("-")[0]);
						String afterTime=String.format("%s-%s-%s %s:00", dTime.getYear(),dTime.getMonthOfYear(),dTime.getDayOfMonth(),str.split("-")[1]);
						DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");  
						DateTime temp=DateTime.parse(preTime,format);
						DateTime next=DateTime.parse(afterTime,format);
						if(temp.isBeforeNow()&&next.isAfterNow()){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 根据用户id获取该用户是否可以上网
	 * @param id:用户id
	 * @return 返回是否可以上网(1.上网  0.不上网)
	 */
	@Override
	public String getPolicyByUserId(String id) {
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			UserModel userModel = userService.findOne(intId);
		    OrganizationModel organization = userModel.getOrganization();
		    if(organization!=null){
		    	Integer policyId=androidDeviceUsersDao.findOne(intId, organization.getId());
		    	AndroidDevicePolicy androidDevicePolicy = androidDevicePolicyDao.selectByPrimaryKey(policyId);
				if(androidDevicePolicy!=null&&androidDevicePolicy.getIsNetLimit()!=null&&Constant.YES.equals(androidDevicePolicy.getIsNetLimit())){
					String visitTimeStr = androidDevicePolicy.getVisitTimeStr();
					if(visitTimeStr!=null&&!Constant.EMPTY_STR.equals(visitTimeStr)){
						boolean result = compareDate(visitTimeStr);
						if(result){
							return Constant.YES;
						} 
					}
				} else {
					return Constant.YES;
				}
		    }		
		}
		return Constant.NO;
	}

	/**
	 * 查看ios配置策略
	 */
	@Override
	public Object findIosPolicy(HttpServletRequest request) {
		String id = request.getParameter("id");
		IosDevicePolicy iosDevicePolicy = new IosDevicePolicy();
		List<IospolicyDepartment> iosDepartmentList = new ArrayList<IospolicyDepartment>();
		List<IosPolicyVirtual> iosVirtualList = new ArrayList<IosPolicyVirtual>();
		List<IosPolicyUser> iosUserList = new ArrayList<IosPolicyUser>();
		List<IosWifiConfigure> iosWifiList = new ArrayList<IosWifiConfigure>();
		List<NetBehaviorBlackWhiteList> nameList = new ArrayList<NetBehaviorBlackWhiteList>();
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			iosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(intId);
			iosDepartmentList = iospolicyDepartmentDao.selectIosPolicyDepartById(intId);
			iosVirtualList = iosPolicyVirtualDao.selectIosVirtualGroupById(intId);
			iosUserList = iosPolicyUserDao.selectDeviceUserById(intId) ;
			iosWifiList = iosWifiConfigureDao.selectIosWifiByPolicyId(intId);
			nameList = iosPolicyListDao.selectNameListByPolicyId(intId);
			iosDevicePolicy.setIosDepartmentList(iosDepartmentList);
			iosDevicePolicy.setIosVirtualList(iosVirtualList);
			iosDevicePolicy.setIosUserList(iosUserList);
/*			iosDevicePolicy.setIosUserList(iosUserList);*/
			iosDevicePolicy.setIosWifiList(iosWifiList);
			String ids = "";
			String names = "";
			for (NetBehaviorBlackWhiteList name : nameList) {
				ids += name.getId()+SplitSymbol.COMMA_SYMBOL;
				names += name.getBlackWhiteName()+SplitSymbol.COMMA_SYMBOL;
			}
			iosDevicePolicy.setIds(ids);
			iosDevicePolicy.setNames(names);
		}
		return iosDevicePolicy;
	}

	/**
	 * 删除IOS设备策略
	 */
	@Override
	public ResultDTO deleteIosPolicy(HttpServletRequest request){
		ResultDTO result = new ResultDTO();
		String id = request.getParameter("id");
		// 表示该策略是否启用或者禁用
		// 目前"1"代表是启用
		String isEnable = Constant.YES;
		// 存放策略id推送到苹果服务器
		Set<Integer> policySet = new LinkedHashSet<Integer>();
		HttpSession session = request.getSession();
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if (orgObj == null) {
			result.type = BaseDTO.WARNING;
			result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.invalid.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		// 判断设备策略主键id存在
		if (StringUtil.isNotBlank(id)) {
			Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (obj == null) {
				result.type = BaseDTO.DANGER;
				result.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.invalid.lable",null, LocaleContextHolder.getLocale());
				return result;
			}
			Integer intId = Integer.valueOf(id);
			IosDevicePolicy iosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(intId);
			if(iosDevicePolicy!=null){
				request.setAttribute("name", iosDevicePolicy.getName());
				// 判断该测试禁用还是启用
				if(!Constant.YES.equals(iosDevicePolicy.getIsEnable())){
					isEnable = Constant.NO;
				}
			}
			OrganizationModel organization = (OrganizationModel)orgObj;
			ManagerModel manager = (ManagerModel) obj;
			// Ios设备策略授权用户关联表(已删除)
			List<IosPolicyUser> delDeviceUserList = new ArrayList<IosPolicyUser>();
			// Ios设备策略授权用户对象(已删除的)
			IosPolicyUser delIosPolicyUser;
			DataGridModel delParams = null;
			// 获取将删除的用户
			List<Integer> userList = iosPolicyUserDao.selectUserIdByPolicyId(intId);
			// 存储当前选中的用户id
			for (Integer userId : userList) {
				delParams = new DataGridModel();
				delParams.getParams().put("id", id);
				delParams.getParams().put("orgId", organization.getId());
				delParams.getParams().put("userId", userId);
				// 根据用户id获取以前的策略列表数据
				List<IosPolicyUser> tempUserList = iosPolicyUserDao.selectPolicyIdByUserId(delParams);
				if(tempUserList.size()>0){// 如果以前策略记录存在，则获取最新一条
					IosPolicyUser tempPolicyUser = tempUserList.get(0);
					delDeviceUserList.add(tempPolicyUser);
					policySet.add(tempPolicyUser.getIosDevicePolicyId());
				} else {// 如果以前策略记录不存在，则获取默认策略
					delIosPolicyUser = new IosPolicyUser();
					delIosPolicyUser.setId(CommUtil.getPrimaryKey()+userId);
					delIosPolicyUser.setIosDevicePolicyId(0);
					delIosPolicyUser.setCreateDate(new Date());
					delIosPolicyUser.setUpdateDate(getDefaultDate());
					delIosPolicyUser.setUserId(userId);
					delDeviceUserList.add(delIosPolicyUser);
					policySet.add(0);
				}
			}
			// 删除授权用户关联表对象
			iosPolicyUserDao.updateByUsersId(intId);
			
			// Ios设备策略授权部门关联表(删除的)
			List<IospolicyDepartment> delDeviceDepartmentList = new ArrayList<IospolicyDepartment>();
			// Ios设备策略授权部门对象(删除的)
			IospolicyDepartment delIosDeviceDepartment = null;
			List<Integer> departIdList = iospolicyDepartmentDao.selectDepartmentById(intId);
			// 存储要删除的部门id
			List<Integer> delDepartIds = new ArrayList<Integer>();
			for (Integer departId : departIdList) {
				delDepartIds.add(departId);
				delParams = new DataGridModel();
				delParams.getParams().put("orgId", organization.getId());
				delParams.getParams().put("id", id);
				delParams.getParams().put("departId", departId);
				List<IospolicyDepartment> tempDepartList = iospolicyDepartmentDao.selectPolicyByDepartId(delParams);
				if(tempDepartList.size()>0){
					IospolicyDepartment tempDeviceDepartment = tempDepartList.get(0);
					delDeviceDepartmentList.add(tempDeviceDepartment);
					policySet.add(tempDeviceDepartment.getIosDevicePolicyId());
				} else {
					delIosDeviceDepartment = new IospolicyDepartment();
					delIosDeviceDepartment.setIosDevicePolicyId(0);
					delIosDeviceDepartment.setOrgStructureId(departId);
					delIosDeviceDepartment.setCreateDate(new Date());
					delIosDeviceDepartment.setUpdateDate(getDefaultDate());
					delDeviceDepartmentList.add(delIosDeviceDepartment);
					policySet.add(0);
				}
			}
			// 删除授权部门关联表对象
			iospolicyDepartmentDao.updateByDepartId(intId);
			
			// Android设备策略授权虚拟组关联表(删除的)
			List<IosPolicyVirtual> delDeviceVirtualGroupList = new ArrayList<IosPolicyVirtual>();
			// Android设备策略授权虚拟组对象(删除的)
			IosPolicyVirtual delIosDeviceVirtualGroup = null;
			// 存储将要删除的虚拟组id
			List<Integer> delVirtualIds = new ArrayList<Integer>();
			List<Integer> virtualIdsList = iosPolicyVirtualDao.selectVirtualById(intId);
			for (Integer virtualId : virtualIdsList) {
				delVirtualIds.add(virtualId);
				delParams = new DataGridModel();
				delParams.getParams().put("id", id);
				delParams.getParams().put("orgId", organization.getId());
				delParams.getParams().put("virtualId", virtualId);
				List<IosPolicyVirtual> tempVirutalList = iosPolicyVirtualDao.selectIosPolicyIdByVirtualId(delParams);
				if(tempVirutalList.size()>0){
					IosPolicyVirtual tempVirtual = tempVirutalList.get(0);
					delDeviceVirtualGroupList.add(tempVirtual);
					policySet.add(tempVirtual.getIosDevicePolicyId());
				} else {
					delIosDeviceVirtualGroup = new IosPolicyVirtual();
					delIosDeviceVirtualGroup.setIosDevicePolicyId(0);
					delIosDeviceVirtualGroup.setVirtualGroupId(virtualId);
					delIosDeviceVirtualGroup.setCreateDate(new Date());
					delIosDeviceVirtualGroup.setUpdateDate(getDefaultDate());
					delDeviceVirtualGroupList.add(delIosDeviceVirtualGroup);
					policySet.add(0);
				}
			}
			// 更新删除Android虚拟组对象
			iosPolicyVirtualDao.updateByVirtualGroupId(intId);
            // 更新删除黑名单列表
			
			iosDevicePolicy.setId(intId);
			iosDevicePolicy.setDeleteTime(new Date());
			iosDevicePolicy.setUpdateUser(manager.getId());
			int count = iosDevicePolicyDao.updateByPrimaryKeySelective(iosDevicePolicy);
			if (count == 0) {
				result.type = BaseDTO.DANGER;
				result.message = messageSourceService.getMessage("web.institution.device.policy.deliospolicy.failed.lable",null, LocaleContextHolder.getLocale());
				return result;
			} 
			// 当该策略是启用的时候 需极光推送数据
			if(Constant.YES.equals(isEnable)){	
                List<IosDevicePolicy> jpushList = getJpushIosList(id, organization.getId(), delDepartIds, delDeviceDepartmentList,
                		delVirtualIds, policySet, manager.getId(), delDeviceVirtualGroupList, delDeviceUserList);
    			Map<String, Object> map = new HashMap<String, Object>();
    			if(jpushList.size()>0){
    				// 获取每个设备对应的版本号列表
    				List<IosPolicyVersionBean> list = getDeviceTokenList(null, null, jpushList);
    				if(CollectionUtils.isNotEmpty(list)) {
    					PushIosVersionThread thread = new PushIosVersionThread(null, 1, "", list);
    					taskExecutor.execute(thread);
    				}
    				map.put("jpushList", jpushList);
        			String status = abstractIosPush.nofity(map);
        			if(BaseDTO.ERROR.equals(status)){
        			    result.message = messageSourceService.getMessage("web.institution.device.policy.deliospolicy.failed.lable",null, LocaleContextHolder.getLocale());
        				result.type = BaseDTO.DANGER;
        				return result;
        			}
    			}
			}
		} else {
			logger.error("device policy parameter id is empty");
			result.type = BaseDTO.DANGER;
			result.message = messageSourceService.getMessage("web.institution.device.policy.deliospolicy.failed.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
		result.type = BaseDTO.SUCCESS;
		result.message = messageSourceService.getMessage("web.institution.device.policy.deliospolicy.success.lable",null, LocaleContextHolder.getLocale());
		return result;
	}
	
	/**
	 * 将IOS设备策略的相关属性生成mobileconfig文件格式
	 * @param policyId:策略外键
	 * @return 返回ios设备策略的相关属性生成mobileconfig文件内容
	 */
	@Override
	public String createMobileConfig(Integer policyId,List<IosWifiConfigure> list,TokenUpdateInfo tokenUpdateInfo) {
	    try {
			IosDevicePolicy devicePolicy = iosDevicePolicyDao.selectByPrimaryKey(policyId);
			Map<String,Entry<DirectionStatus, String>> map = createPolicyMap(devicePolicy);
			if(map!=null){
				map.put("CheckInURL", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, String.format("%s?userId=%s&uuid=%s&iosUuid=%s", CommUtil.CHECK_IN_URL,tokenUpdateInfo.getUserId().toString(),tokenUpdateInfo.getUuid(),tokenUpdateInfo.getIosUuid())));
				map.put("ServerURL", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, String.format("%s?userId=%s&uuid=%s&iosUuid=%s", CommUtil.SERVER_URL,tokenUpdateInfo.getUserId().toString(),tokenUpdateInfo.getUuid(),tokenUpdateInfo.getIosUuid())));
				map.put("/plist/dict/PayloadIdentifier", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT,tokenUpdateInfo.getPayloadIdentifier().toString()));
                if(Constant.YES.equals(devicePolicy.getIsEnablePassword())) {
                	map.put(IPassCode._KEY_PAYLOADUUID_STR, new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, UUID.randomUUID().toString().toUpperCase()));
                	String value = "";
                	// 允许简单值
                	if(Constant.YES.equals(devicePolicy.getAllowSimpleValue())) {
                		value = "true";
                	} else {
                		value = "false";
                	}
                	map.put(IPassCode._KEY_ALLOWSIMPLE_BOOL, new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, value));
                	
                	// 允许字符
                	if(Constant.YES.equals(devicePolicy.getLetterDigitValue())) {
                		value = "true";
                	} else {
                		value = "false";
                	}
                	map.put(IPassCode._KEY_FORCEPIN_BOOL, new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, value));
                	
                	// 失败次数
                    if(StringUtil.isNotBlank(devicePolicy.getFailureTimes())){
                    	map.put(IPassCode._KEY_MAXFAILEDATTEMPTS_INT, new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, devicePolicy.getFailureTimes()));
                    }
                	
                	// 最小密码长度
                    if(StringUtil.isNotBlank(devicePolicy.getPasswordLength())){
                    	map.put(IPassCode._KEY_MINLENGTH_INT, new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, devicePolicy.getPasswordLength()));
                    }
                    // 历史记录
                    if(devicePolicy.getPasswordHistory()!=null) {
                    	map.put(IPassCode._KEY_PINHISTORY_INT, new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, devicePolicy.getPasswordHistory().toString()));
                    }
                }
				// 修改MobileConfig元素内容
		        String mobileConfig = MDMProtocolUtils.getMobileConfigWithMap(map);
		        Document document = DocumentHelper.parseText(mobileConfig);
			    return createIosElement(document,list,devicePolicy);
			}
		} catch (Exception e) {
			logger.error("ios device policy create mobile config failed,error message is:"+e.getMessage());
		}
        return null;
	}
	

	/**
	 * 创建推送命令
	 * @param mobileConfig:设备策略相关属性内容
	 * @return 返回推送命令内容
	 */
	@Override
	public String createCommand(String mobileConfig,String commandUUID) {
		try {
		    return MDMProtocolUtils.InstallProfiles(commandUUID, mobileConfig);
		} catch (Exception e){
			logger.error("ios device strategy create plist,exception message is "+ e.getMessage());
			return null;
		}
	}
	
	/**
	 * 设备策略功能
	 * tag 1.启用 0.禁用
	 * @throws ParseException 
	 */
	@Override
	public ResultDTO enableIosPolicy(HttpServletRequest request){
		ResultDTO result = new ResultDTO();
		String id = request.getParameter("id");
		String tag = request.getParameter("tag");
			// 判断设备策略主键id存在
			if (StringUtil.isNotBlank(id)&&StringUtil.isNotBlank(tag)) {
				Integer intID = Integer.valueOf(id.toString());
				HttpSession session = request.getSession();
				Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
				if (obj == null) {
					result.type = BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.invalid.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
				Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
				if (orgObj == null) {
					result.type = BaseDTO.WARNING;
					result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.invalid.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
				ManagerModel manager = (ManagerModel) obj;
				OrganizationModel organization = (OrganizationModel)orgObj;
				IosDevicePolicy iosDevicePolicy = new IosDevicePolicy();
				iosDevicePolicy.setId(intID);
				iosDevicePolicy.setIsEnable(tag);
				iosDevicePolicy.setUpdateDate(new Date());
				iosDevicePolicy.setUpdateUser(manager.getId());
				iosDevicePolicy.setVersion(CommUtil.generate32UUID());
				int count = iosDevicePolicyDao.updateByPrimaryKeySelective(iosDevicePolicy);
				if (count == 0) {
					if(Constant.YES.equals(tag)){
						result.type = BaseDTO.DANGER;
						result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
						return result;
					} else {
						result.type =  BaseDTO.DANGER;
						result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
						return result;
					}
				}
				IosDevicePolicy policy = iosDevicePolicyDao.selectByPrimaryKey(intID);
				request.setAttribute("name", policy.getName());
				// 启用这条设备策略
				if(Constant.YES.equals(tag)){
					// 存放用户id推送到极光服务器
					Set<Integer> userSet = new LinkedHashSet<Integer>();
					// 根据出了id获取将要启用的授权用户
					List<Integer> userList = iosPolicyUserDao.selectUserIdByPolicyId(intID);
					// 根据策略id获取将要启用的授权部门
					List<Integer> departIdList = iospolicyDepartmentDao.selectDepartmentById(intID);
					// 根据策略id获取将要启用的授权虚拟组
					List<Integer> virtualIdsList = iosPolicyVirtualDao.selectVirtualById(intID);
					// 设置Android授权用户到UserSet
					userSet.addAll(userList);
					List<IospolicyDepartment> departList =new ArrayList<IospolicyDepartment>();
					IospolicyDepartment department = null;
					// 根据部门获取用户id
					for (Integer integer : departIdList) {
						department = new IospolicyDepartment();
						department.setOrgStructureId(integer);
						departList.add(department);
					}
					// 根据虚拟组获取用户id
					List<IosPolicyVirtual> virtualList = new ArrayList<IosPolicyVirtual>();
					IosPolicyVirtual virutalGroup = null;
					for (Integer integer : virtualIdsList) {
						virutalGroup = new IosPolicyVirtual();
						virutalGroup.setVirtualGroupId(integer);
						virtualList.add(virutalGroup);
					}
					// 根据部门和虚拟组获取对应的用户设置到userSet
					Set<Integer> getUserSet = getIosCurrentUserIds(departList, virtualList, organization.getId());
                    userSet.addAll(getUserSet);
					DataGridModel params = new DataGridModel();
					params.getParams().put("policyId", policy.getId());
					// 极光推送消息到手机端
/*					result.userSet = userSet;
					result.currentPolicy = policy;
					jpushUserPolicy(userSet,policy,null,null);*/
	    			Map<String,Object> map = new HashMap<String,Object>();
			        if(userSet.size()>0&&policy!=null){
						// 获取每个设备对应的版本号列表
	    				List<IosPolicyVersionBean> list = getDeviceTokenList(userSet, policy, null);
	    				if(CollectionUtils.isNotEmpty(list)) {
	    					// ios push 
	    					PushIosVersionThread thread = new PushIosVersionThread(null, 1, "", list);
	    					taskExecutor.execute(thread);
	    				}
			        	map.put("userSet", userSet);
			        	map.put("iosDevicePolicy", policy);
	        			String status = abstractIosPush.nofity(map);
	        			if(BaseDTO.ERROR.equals(status)){
	    					if(Constant.YES.equals(tag)){
	    						result.type = BaseDTO.DANGER;
	    						result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
	    						return result;
	    					} else {
	    						result.type =  BaseDTO.DANGER;
	    						result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
	    						return result;
	    					}
	        			}
	    			}
				} else {// 禁用这条设备策略
					Set<Integer> policySet = new LinkedHashSet<Integer>();
					// =====================从AndroidDeviceUsers表捞取以前的对应策略================================
					// Android设备策略授权用户关联表(禁用的)
					List<IosPolicyUser> disableDeviceUserList = new ArrayList<IosPolicyUser>();
					// Android设备策略授权用户对象(禁用的)
					IosPolicyUser disableIosDeviceUsers;
					DataGridModel disableParams = null;
					// 获取将禁用的用户
					List<Integer> userList = iosPolicyUserDao.selectUserIdByPolicyId(intID);
					// 获取当前策略所对应的授权用户
					for (Integer userId : userList) {
						disableParams = new DataGridModel();
						disableParams.getParams().put("id", id);
						disableParams.getParams().put("orgId", organization.getId());
						disableParams.getParams().put("userId", userId);
						// 根据用户id获取以前的策略列表数据
						List<IosPolicyUser> tempUserList = iosPolicyUserDao.selectPolicyIdByUserId(disableParams);
						if(tempUserList.size()>0){// 如果以前策略记录存在，则获取最新一条
							disableIosDeviceUsers = tempUserList.get(0);
							disableDeviceUserList.add(disableIosDeviceUsers);
							policySet.add(disableIosDeviceUsers.getIosDevicePolicyId());
						} else {// 如果以前策略记录不存在，则获取默认策略
							disableIosDeviceUsers = new IosPolicyUser();
							disableIosDeviceUsers.setId(CommUtil.getPrimaryKey()+userId);
							disableIosDeviceUsers.setIosDevicePolicyId(0);
							disableIosDeviceUsers.setCreateDate(new Date());
							disableIosDeviceUsers.setUpdateDate(getDefaultDate());
							disableIosDeviceUsers.setUserId(userId);
							disableDeviceUserList.add(disableIosDeviceUsers);
							policySet.add(0); 
						}
					}

					// =====================从IosPolicyDepartment表捞取以前的对应策略================================
					// Ios设备策略授权部门关联表(禁用的)
					List<IospolicyDepartment> disableDeviceDepartmentList = new ArrayList<IospolicyDepartment>();
					// Ios设备策略授权部门对象(禁用的)
					IospolicyDepartment disableAndroidDeviceDepartment = null;
					// 根据策略id获取对应的授权部门
					List<Integer> departIdList = iospolicyDepartmentDao.selectDepartmentById(intID);
					// 存储要删除的部门id
					List<Integer> delDepartIds = new ArrayList<Integer>();
					for (Integer departId : departIdList) {
						delDepartIds.add(departId);
						disableParams = new DataGridModel();
						disableParams.getParams().put("orgId", organization.getId());
						disableParams.getParams().put("id", id);
						disableParams.getParams().put("departId", departId);
						// 根据部门id获取以前对应策略
						List<IospolicyDepartment> tempDepartList = iospolicyDepartmentDao.selectPolicyByDepartId(disableParams);
						// 如果记录存在至少一条则采用最近的是贾尼
						if(tempDepartList.size()>0){
							disableAndroidDeviceDepartment = tempDepartList.get(0);
							disableDeviceDepartmentList.add(disableAndroidDeviceDepartment);
							policySet.add(disableAndroidDeviceDepartment.getIosDevicePolicyId());
						} else {// 采用默认策略
							disableAndroidDeviceDepartment = new IospolicyDepartment();
							disableAndroidDeviceDepartment.setIosDevicePolicyId(0);
							disableAndroidDeviceDepartment.setOrgStructureId(departId);
							disableAndroidDeviceDepartment.setCreateDate(new Date());
							disableAndroidDeviceDepartment.setUpdateDate(getDefaultDate());
							disableDeviceDepartmentList.add(disableAndroidDeviceDepartment);
							policySet.add(0);
						}
					}
					
					// =====================从IosPolicyVirtual表捞取以前的对应策略================================
					// Android设备策略授权虚拟组关联表(禁用的)
					List<IosPolicyVirtual> disableDeviceVirtualGroupList = new ArrayList<IosPolicyVirtual>();
					// Android设备策略授权虚拟组对象(禁用的)
					IosPolicyVirtual disableAndroidDeviceVirtualGroup = null;
					// 存储将要删除的虚拟组id
					List<Integer> delVirtualIds = new ArrayList<Integer>();
					List<Integer> virtualIdsList = iosPolicyVirtualDao.selectVirtualById(intID);
					for (Integer virtualId : virtualIdsList) {
						delVirtualIds.add(virtualId);
						disableParams = new DataGridModel();
						disableParams.getParams().put("id", id);
						disableParams.getParams().put("orgId", organization.getId());
						disableParams.getParams().put("virtualId", virtualId);
						List<IosPolicyVirtual> tempVirutalList = iosPolicyVirtualDao.selectIosPolicyIdByVirtualId(disableParams);
						if(tempVirutalList.size()>0){
							disableAndroidDeviceVirtualGroup = tempVirutalList.get(0);
							disableDeviceVirtualGroupList.add(disableAndroidDeviceVirtualGroup);
							policySet.add(disableAndroidDeviceVirtualGroup.getIosDevicePolicyId());
						} else {
							disableAndroidDeviceVirtualGroup = new IosPolicyVirtual();
							disableAndroidDeviceVirtualGroup.setIosDevicePolicyId(0);
							disableAndroidDeviceVirtualGroup.setVirtualGroupId(virtualId);
							disableAndroidDeviceVirtualGroup.setCreateDate(new Date());
							disableAndroidDeviceVirtualGroup.setUpdateDate(getDefaultDate());
							disableDeviceVirtualGroupList.add(disableAndroidDeviceVirtualGroup);
							policySet.add(0);
						}
					}
			        List<IosDevicePolicy> jpushList = getJpushIosList(id, organization.getId(), delDepartIds, disableDeviceDepartmentList, delVirtualIds, policySet, manager.getId(), disableDeviceVirtualGroupList, disableDeviceUserList);
	    			Map<String,Object> map = new HashMap<String,Object>();
			        if(jpushList.size()>0){
						// 获取每个设备对应的版本号列表
	    				List<IosPolicyVersionBean> list = getDeviceTokenList(null, null, jpushList);
	    				if(CollectionUtils.isNotEmpty(list)) {
	    					PushIosVersionThread thread = new PushIosVersionThread(null, 1, "", list);
	    					taskExecutor.execute(thread);
	    				}
	    				map.put("jpushList", jpushList);
	        			String status = abstractIosPush.nofity(map);
	        			if(BaseDTO.ERROR.equals(status)){
	        			    result.message = messageSourceService.getMessage("web.institution.device.policy.deliospolicy.failed.lable",null, LocaleContextHolder.getLocale());
	        				result.type = BaseDTO.DANGER;
	        				return result;
	        			}
	    			}
				}
			} else {
				logger.error("device policy parameter id is empty");
				if(Constant.YES.equals(tag)){
					result.type =  BaseDTO.DANGER;
					result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
					return result;
				} else {
					result.type =  BaseDTO.DANGER;
				    result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
					return result;
				}
			}
		if(Constant.YES.equals(tag)){
			result.type =  BaseDTO.SUCCESS;
			result.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.success.lable",null, LocaleContextHolder.getLocale());
			return result;
		} else {
			result.type = BaseDTO.SUCCESS;
		    result.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.success.lable",null, LocaleContextHolder.getLocale());
			return result;
		}
	}

	/**
	 * 用户管理模块查询该机构下所有的ios策略
	 */
	@Override
	public List<IosDevicePolicy> findAllIosDevicePolicyByMap(Map<String, Object> maps) {
		return iosDevicePolicyDao.findAllByMap(maps);
	}

	/**
	 * 根据机构下获取所有的ios策略
	 */
	@Override
	public List<IosDevicePolicy> findIosAll(Integer orgId) {
		return (List<IosDevicePolicy>) iosDevicePolicyDao.findAll(orgId);
	}

	/**
	 * 根据参数获取ios设备策略与用户关联表
	 */
	@Override
	public IosPolicyUser findOneByIosEntity(IosPolicyUser entity) {
		return iosPolicyUserDao.findOneByEntity(entity);
	}

	/**
	 * 根据实体更新IOS设备策略
	 */
	@Override
	public int updateIosDeviceUser(IosPolicyUser entity) {
		return iosPolicyUserDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public Map<String, Object> findUserIosDevicePolicy(Integer userId, Integer orgId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Integer android_device_policyId=androidDeviceUsersDao.findOne(userId, orgId);
		Integer ios_device_policyId = iosPolicyUserDao.findOne(userId, orgId);
		map.put(DeviceTypeStatus.SOFTTEK_ANDROID.toString(), android_device_policyId==null?Constant.NO:android_device_policyId.toString());
		map.put(DeviceTypeStatus.SOFTTEK_IOS.toString(), ios_device_policyId==null?Constant.NO:ios_device_policyId.toString());
		return map;
	}

	/**
	 * 根据主键查询IOS设备策略
	 */
	@Override
	public IosDevicePolicy findOneIosDevicePolicy(Integer pKey) {
		return iosDevicePolicyDao.selectByPrimaryKey(pKey);
	}

	/**
	 * 插入IOS设备策略授权用户表
	 */
	@Override
	public int insertIosDeviceUser(IosPolicyUser entity) {
		return iosPolicyUserDao.insertSelective(entity);
	}

	/**
	 * 向document元素中添加wifi模块
	 * @param document
	 * @param list
	 * @return
	 */
	@Override
	public String createIosElement(Document document, List<IosWifiConfigure> list,IosDevicePolicy iosDevicePolicy) {
		try {
			if(list.size()>0){
			    Element arrayElement = (Element)document.selectSingleNode("/plist/dict/array");
			    Element dictElement = null;
			    // 启用密码模块
/*			    if(Constant.YES.equals(iosDevicePolicy.getIsEnablePassword())){
			    	// add dict element
					dictElement = XMLUtil.createElement(ElementType.DICT);
					XMLUtil.addElement(arrayElement, dictElement);
					
					// add PayloadDescription element
					XMLUtil.addElement(dictElement, ElementType.KEY, IPassCode._KEY_PAYLOADDESCRIPTION_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING,"配置与安全相关的项目");
					
					// add PayloadDisplayName element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PAYLOADDISPLAYNAME_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING,"密码");
					
					// add PayloadIdentifier element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PAYLOADIDENTIFIER_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING,"mdm.softtek.com.passcodepolicy");
					
					// add PayloadOrganization element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PAYLOADORGANIZATION_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING,"softtek");
					
					// add PayloadType element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PAYLOADTYPE_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING,"com.apple.mobiledevice.passwordpolicy");
					
					// add PayloadUUID element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PAYLOADUUID_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, UUID.randomUUID().toString().toUpperCase());
					
					// add payloadVersion element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PAYLOADVERSION_INT);
					XMLUtil.addElement(dictElement, ElementType.INTEGER, "1");
					
					// add allowSimple element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_ALLOWSIMPLE_BOOL);
					if(Constant.YES.equals(iosDevicePolicy.getAllowSimpleValue())){
						XMLUtil.addElement(dictElement, ElementType.TRUE);
					} else {
						XMLUtil.addElement(dictElement, ElementType.FALSE);
					}
					
					// add forcePIN element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_FORCEPIN_BOOL);
					if(Constant.YES.equals(iosDevicePolicy.getLetterDigitValue())){
						XMLUtil.addElement(dictElement, ElementType.TRUE);
					} else {
						XMLUtil.addElement(dictElement, ElementType.FALSE);
					}
					
					// add maxFailedAttempts element
					if(iosDevicePolicy.getFailureTimes()!=null){
						XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_MAXFAILEDATTEMPTS_INT);
						XMLUtil.addElement(dictElement, ElementType.INTEGER,iosDevicePolicy.getFailureTimes());
					}	
					
					// add maxGracePeriod element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_MAXGRACEPERIOD_INT);
					XMLUtil.addElement(dictElement, ElementType.INTEGER,"1");
					
					// add maxInactivity element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_MAXINACTIVITY_INT);
					XMLUtil.addElement(dictElement, ElementType.INTEGER,"1");
					
					// add maxPINAgeInDays element
					XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_MAXPINAGEINDAYS_INT);
					XMLUtil.addElement(dictElement, ElementType.INTEGER,"727");
					
					// add maxPINAgeInDays element
					if(iosDevicePolicy.getPasswordLength()!=null){
						XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_MINLENGTH_INT);
						XMLUtil.addElement(dictElement, ElementType.INTEGER,iosDevicePolicy.getPasswordLength());
					}
				
					// add maxPINAgeInDays element
					if(iosDevicePolicy.getPasswordHistory()!=null){
						XMLUtil.addElement(dictElement, ElementType.KEY,IPassCode._KEY_PINHISTORY_INT);
						XMLUtil.addElement(dictElement, ElementType.INTEGER,iosDevicePolicy.getPasswordHistory().toString());
					}
			    }*/
			    for (IosWifiConfigure wifi : list) {
			    	// add dict element
					dictElement = XMLUtil.createElement(ElementType.DICT);
					XMLUtil.addElement(arrayElement, dictElement);
					// add autojoin element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_AUTOJOIN_BOOL);
					XMLUtil.addElement(dictElement, ElementType.TRUE);
					// add security value
					String securityType = wifi.getSecurityType();
					if(IEncryptionResult.WEP.equals(securityType)||IEncryptionResult.WPA.equals(securityType)){
						String securityVal = Constant.EMPTY_STR;
						if(IEncryptionResult.WEP.equals(securityType)){
							securityVal = IEncryptionType._KEY_WEP_STR;
						} else {
							securityVal = IEncryptionType._KEY_WPA_STR;
						}
						XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_ENCRYPTIONTYPE_STR);
						XMLUtil.addElement(dictElement, ElementType.STRING,securityVal);
					}
					// add hidden_network element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_HIDDEN_NETWORK_BOOl);
					XMLUtil.addElement(dictElement, ElementType.FALSE);
	                // add password
					if(IEncryptionResult.WEP.equals(securityType)||IEncryptionResult.WPA.equals(securityType)){
						XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PASSWORD_STR);
						XMLUtil.addElement(dictElement, ElementType.STRING, wifi.getPassword());
					}
					// add payload description element 
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADDESCRIPTION_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING,IWifiContent._KEY_PAYLOADDESCRIPTIONCONTENT_STR);
	
					// add wifi element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADDISPLAYNAME_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, IWifiContent._KEY_PAYLOADDISPLAYNAMECONTENT_STR);
					
					// add payload identifier element 
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADIDENTIFIER_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, IWifiContent._KEY_PAYLOADIDENTIFIERCONTENT_STR);
					
					// add PayloadOrganization element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADORGANIZATION_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, IWifiContent._KEY_PAYLOADORGANIZATIONCONTENT_STR);
					
					// add paylod type element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADTYPE_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, IWifiContent._KEY_PAYLOADTYPE_STR);
	
					// add payload uuid element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADUUID_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, wifi.getId().toString());
					
					// add payload version element
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PAYLOADVERSION_INT);
					XMLUtil.addElement(dictElement, ElementType.INTEGER, IWifiContent._KEY_PAYLOADVERSIONCONTENT_INT);
					
					// add payload proxy type element 
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_PROXYTYPE_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, IWifiContent._KEY_PROXYTYPECONTENT_STR);
					
					// add ssid_str type element 
					XMLUtil.addElement(dictElement, ElementType.KEY, IWifi._KEY_SSID_STR_STR);
					XMLUtil.addElement(dictElement, ElementType.STRING, wifi.getSsid());
				}
			}
		    return document.asXML();
		} catch (Exception e){
			logger.error("Add the WiFi module to the document element,exception message is "+ e.getMessage());
		}
		return null;
	}

	/**
	 * 将IOS设备策略封装成map
	 * @param policy
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Entry<DirectionStatus, String>> createPolicyMap(IosDevicePolicy policy) {
		Map<String,Entry<DirectionStatus, String>> map = new HashMap<String,Entry<DirectionStatus, String>>();
		Entry<DirectionStatus, String> subEntry  = null;
		String value = Constant.EMPTY_STR;
		try {
			// 是否允许安装应用
			if(Constant.YES.equals(policy.getAllowInstallApp())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowAppInstallation, subEntry);
			
			// 是否使用相册
			if(Constant.YES.equals(policy.getAllowUseCamera())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowCamera, subEntry);
			
			// 是否屏幕截屏
			if(Constant.YES.equals(policy.getAllowScreenCatch())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowScreenShot, subEntry);
			// 是否使用YouTuBe
			if(Constant.YES.equals(policy.getAllowUseYoutube())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowYouTube, subEntry);
			// 是否使用iTunes
			if(Constant.YES.equals(policy.getAllowUseItunes())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowiTunes, subEntry);
			// 是否使用safari
			if(Constant.YES.equals(policy.getAllowUseSafari())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowSafari, subEntry);
			// 是否备份
			if(Constant.YES.equals(policy.getAllowBackup())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowCloudBackup, subEntry);
			// 是否文档同步
			if(Constant.YES.equals(policy.getAllowDocumentSynchronization())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowCloudDocumentSync, subEntry);
			// 是否允许照片流
			if(Constant.YES.equals(policy.getAllowPhotoStream())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowPhotoStream, subEntry);

			// 是否允许使用ITunes
			if(Constant.YES.equals(policy.getAllowUseItunes())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowiTunes, subEntry);
			
			// 允许受管控的应用打开不受管控的应用的文档
			if(Constant.YES.equals(policy.getAllowOpenFromManagedToUnmanaged())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowOpenFromManagedToUnmanaged, subEntry);
			
			// 允许不受管控的应用打开受管控的应用的文档
			if(Constant.YES.equals(policy.getAllowOpenFromUnmanagedToManaged())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowOpenFromUnmanagedToManaged, subEntry);
			
			// 是否允许添加游戏好友
			if(Constant.YES.equals(policy.getAllowAddingGameCenterFriends())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowAddingGameCenterFriends, subEntry);

			// 允许iCloud照片图库
			if(Constant.YES.equals(policy.getAllowCloudPhotoLibrary())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowCloudPhotoLibrary, subEntry);

			// 允许iCloud照片共享
			if(Constant.YES.equals(policy.getAllowSharedStream())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowSharedStream, subEntry);

			// 允许指纹解锁
			if(Constant.YES.equals(policy.getAllowFingerprintForUnlock())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowFingerprintForUnlock, subEntry);
			
			// 允许使用handoff
			if(Constant.YES.equals(policy.getAllowActivityContinuation())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowActivityContinuation, subEntry);
			
			// 允许锁屏时显示通知消息
			if(Constant.YES.equals(policy.getAllowLockScreenNotificationsView())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowLockScreenNotificationsView, subEntry);
			
			// 允许锁屏时显示控制中心
			if(Constant.YES.equals(policy.getAllowLockScreenControlCenter())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowLockScreenControlCenter, subEntry);
			
			// 限制广告追踪
			if(Constant.YES.equals(policy.getLimitAdvertTracking())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.SafariForceFraudWarning, subEntry);

			
			// 设备锁定时允许使用Siri
			if(Constant.YES.equals(policy.getAllowAssistantWhileLocked())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowAssistantWhileLocked, subEntry);
			
			// 允许锁屏时显示TodayView的消息
			if(Constant.YES.equals(policy.getAllowLockScreenTodayView())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowLockScreenTodayView, subEntry);
			
			// icloud同步钥匙串
			if(Constant.YES.equals(policy.getAllowCloudKeychainSync())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowCloudKeychainSync, subEntry);
			
			// 允许在漫游时自动同步
			if(Constant.YES.equals(policy.getAllowGlobalBackgroundFetchWhenRoaming())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowGlobalBackgroundFetchWhenRoaming, subEntry);
			
			// 允许多人游戏
			if(Constant.YES.equals(policy.getAllowMultiplayerGaming())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowMultiplayerGaming, subEntry);
			
			// 允许语音拨号
			if(Constant.YES.equals(policy.getAllowVoiceDialing())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowVoiceDialing, subEntry);
			
			// 强制用户为所有购买项目输入iTunes Store密码
			if(Constant.YES.equals(policy.getForceItunesStore())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.ForceITunesStorePasswordEntry, subEntry);
			
			//  允许被管理的应用将数据存储到iCloud
			if(Constant.YES.equals(policy.getForceItunesStore())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowManagedAppsCloudSync, subEntry);
			
			// 允许Siri
			if(Constant.YES.equals(policy.getAllowSiri())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowAssistant, subEntry);
			
			// 允许facetime
			if(Constant.YES.equals(policy.getAllowFaceTime())){
				value = ElementType.TRUE;
			} else {
				value = ElementType.FALSE;
			}
			subEntry = new AbstractMap.SimpleEntry(DirectionStatus.NEXT, value);
			map.put(IElementPosition.AllowVideoConferencing, subEntry);

		} catch (Exception e){
			logger.error("ios device strategy will be packaged into map,exception message is "+ e.getMessage());
		}
		return map;
	}

	@Override
	public List<DeviceBasicInfoModel> getDevicesByUserId(Integer userId) {
		
		return deviceBasicInfoDao.getDevicesByUserId(userId);
	}

	/**
	 * 保存时候根据request获取参数返回IOS设备策略
	 * @param request:request请求
	 * @return 返回ios设备策略
	 */
	@Override
	public IosDevicePolicy getIosDevicePolicy(DataGridModel params) {
		Object id = params.getParams().get("id");
		Object name = params.getParams().get("name");
		Object description = params.getParams().get("description");
		Object isEnablePassword = params.getParams().get("isEnablePassword");
		Object allowSimpleValue = params.getParams().get("allowSimpleValue");
		Object letterDigitValue = params.getParams().get("letterDigitValue");
		Object passwordLength = params.getParams().get("passwordLength");
		Object lockLongestTime = params.getParams().get("lockLongestTime");
		Object graceTime = params.getParams().get("graceTime");
		Object failureTimes = params.getParams().get("failureTimes");
		Object validityPeriod = params.getParams().get("validityPeriod");
		Object passwordHistory = params.getParams().get("passwordHistory");
		Object allowInstallApp = params.getParams().get("allowInstallApp");
		Object allowOpenFromManagedToUnmanaged = params.getParams().get("allowOpenFromManagedToUnmanaged");
		Object allowOpenFromUnmanagedToManaged = params.getParams().get("allowOpenFromUnmanagedToManaged");
		Object allowUseCamera = params.getParams().get("allowUseCamera");
		Object allowFaceTime = params.getParams().get("allowFaceTime");
		Object allowScreenCatch = params.getParams().get("allowScreenCatch");
		Object allowGlobalBackgroundFetchWhenRoaming = params.getParams().get("allowGlobalBackgroundFetchWhenRoaming");
		Object allowSiri = params.getParams().get("allowSiri");
		Object allowAssistantWhileLocked = params.getParams().get("allowAssistantWhileLocked");
		Object allowVoiceDialing = params.getParams().get("allowVoiceDialing");
		Object forceiTunesStore = params.getParams().get("forceiTunesStore");
		Object limitAdvertTracking = params.getParams().get("limitAdvertTracking");
		Object allowLockScreenTodayView = params.getParams().get("allowLockScreenTodayView");
		Object allowCloudKeychainSync = params.getParams().get("allowCloudKeychainSync");
		Object allowLockScreenControlCenter = params.getParams().get("allowLockScreenControlCenter");
		Object allowFingerprintForUnlock = params.getParams().get("allowFingerprintForUnlock");
		Object allowLockScreenNotificationsView = params.getParams().get("allowLockScreenNotificationsView");
		Object allowDisplayPassbook = params.getParams().get("allowDisplayPassbook");
		Object allowManagedAppsCloudSync = params.getParams().get("allowManagedAppsCloudSync");
		Object allowCloudPhotoLibrary = params.getParams().get("allowCloudPhotoLibrary");
		Object allowSharedStream = params.getParams().get("allowSharedStream");
		Object allowActivityContinuation = params.getParams().get("allowActivityContinuation");
		Object allowUseYoutube = params.getParams().get("allowUseYoutube");
		Object allowUseiTunes = params.getParams().get("allowUseiTunes");
		Object allowGameCenter = params.getParams().get("allowGameCenter");
		Object allowAddingGameCenterFriends = params.getParams().get("allowAddingGameCenterFriends");
		Object allowMultiplayerGaming = params.getParams().get("allowMultiplayerGaming");
		Object allowUseSafari = params.getParams().get("allowUseSafari");
		Object allowBackup = params.getParams().get("allowBackup");
		Object allowDocumentSynchronization = params.getParams().get("allowDocumentSynchronization");
		Object allowPhotoStream = params.getParams().get("allowPhotoStream");
		Object safeType = params.getParams().get("safeType");
		IosDevicePolicy iosDevicePolicy = new IosDevicePolicy();
		if(id!=null&&!Constant.EMPTY_STR.equals(id)){
			int intId = Integer.valueOf(id.toString());
			iosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(intId);
			iosDevicePolicy.setId(intId);
			// 删除IOS WIFI配置
			iosWifiConfigureDao.delIosWifiConfigureByPolicyId(intId);
		}
		if(name!=null&&!Constant.EMPTY_STR.equals(name)){
		    iosDevicePolicy.setName(String.valueOf(name));
		}
		if(description!=null&&!Constant.EMPTY_STR.equals(description)){
			iosDevicePolicy.setDescription(String.valueOf(description));
		}
        if(allowSimpleValue!=null&&!Constant.EMPTY_STR.equals(allowSimpleValue)){
        	iosDevicePolicy.setAllowSimpleValue(String.valueOf(allowSimpleValue));
        }
        if(isEnablePassword!=null&&!Constant.EMPTY_STR.equals(isEnablePassword)){
        	iosDevicePolicy.setIsEnablePassword(String.valueOf(isEnablePassword));
        }
        if(letterDigitValue!=null&&!Constant.EMPTY_STR.equals(letterDigitValue)){
        	iosDevicePolicy.setLetterDigitValue(String.valueOf(letterDigitValue));
        }
		if(passwordLength!=null&&!Constant.EMPTY_STR.equals(passwordLength)){
			iosDevicePolicy.setPasswordLength(String.valueOf(passwordLength));
		}
		if(lockLongestTime!=null&&!Constant.EMPTY_STR.equals(lockLongestTime)){
			iosDevicePolicy.setLockLongestTime(String.valueOf(lockLongestTime));
		}
		if(graceTime!=null&&!Constant.EMPTY_STR.equals(graceTime)){
			iosDevicePolicy.setGraceTime(String.valueOf(graceTime));
		}
        if(failureTimes!=null&&!Constant.EMPTY_STR.equals(failureTimes)){
        	iosDevicePolicy.setFailureTimes(String.valueOf(failureTimes));
        }
        if(validityPeriod!=null&&!Constant.EMPTY_STR.equals(validityPeriod)){
        	iosDevicePolicy.setValidityPeriod(Integer.valueOf(validityPeriod.toString()));
        }
		if(passwordHistory!=null&&!Constant.EMPTY_STR.equals(passwordHistory)){
			iosDevicePolicy.setPasswordHistory(Integer.valueOf(passwordHistory.toString()));
		}
		if(allowInstallApp!=null&&!Constant.EMPTY_STR.equals(allowInstallApp)){
			iosDevicePolicy.setAllowInstallApp(String.valueOf(allowInstallApp));
		}
		// 允许受管控的应用打开不受管控的应用的文档
		if(allowOpenFromManagedToUnmanaged!=null&&!Constant.EMPTY_STR.equals(allowOpenFromManagedToUnmanaged)){
			iosDevicePolicy.setAllowOpenFromManagedToUnmanaged(String.valueOf(allowOpenFromManagedToUnmanaged));
		}
		// 允许不受管控的应用打开受管控的应用的文档
		if(allowOpenFromUnmanagedToManaged!=null&&!Constant.EMPTY_STR.equals(allowOpenFromUnmanagedToManaged)){
			iosDevicePolicy.setAllowOpenFromUnmanagedToManaged(String.valueOf(allowOpenFromUnmanagedToManaged));
		}
		if(allowUseCamera!=null&&!Constant.EMPTY_STR.equals(allowUseCamera)){
			iosDevicePolicy.setAllowUseCamera(String.valueOf(allowUseCamera));
		}
		if(allowFaceTime!=null&&!Constant.EMPTY_STR.equals(allowFaceTime)){
			iosDevicePolicy.setAllowFaceTime(String.valueOf(allowFaceTime));
		}
		
		// 允许屏幕捕捉
		if(allowScreenCatch!=null&&!Constant.EMPTY_STR.equals(allowScreenCatch)){
			iosDevicePolicy.setAllowScreenCatch(String.valueOf(allowScreenCatch));
		}

		// 允许在漫游时自动同步
		if(allowGlobalBackgroundFetchWhenRoaming!=null&&!Constant.EMPTY_STR.equals(allowGlobalBackgroundFetchWhenRoaming)){
			iosDevicePolicy.setAllowGlobalBackgroundFetchWhenRoaming(String.valueOf(allowGlobalBackgroundFetchWhenRoaming));
		}
		
		// 允许Siri
		if(allowSiri!=null&&!Constant.EMPTY_STR.equals(allowSiri)){
			iosDevicePolicy.setAllowSiri(String.valueOf(allowSiri));
		}
		
		// 设备锁定时允许使用Siri 
		if(allowAssistantWhileLocked!=null&&!Constant.EMPTY_STR.equals(allowAssistantWhileLocked)){
			iosDevicePolicy.setAllowAssistantWhileLocked(String.valueOf(allowAssistantWhileLocked));
		}
		
		// 允许语音拨号
		if(allowVoiceDialing!=null&&!Constant.EMPTY_STR.equals(allowVoiceDialing)){
			iosDevicePolicy.setAllowVoiceDialing(String.valueOf(allowVoiceDialing));
		}
        
		// 强制用户为所有购买项目输入iTunes Store密码
		if(forceiTunesStore!=null&&!Constant.EMPTY_STR.equals(forceiTunesStore)){
			iosDevicePolicy.setForceItunesStore(String.valueOf(forceiTunesStore));
		}
		
		// 限制广告追踪
		if(limitAdvertTracking!=null&&!Constant.EMPTY_STR.equals(limitAdvertTracking)){
			iosDevicePolicy.setLimitAdvertTracking(String.valueOf(limitAdvertTracking));
		}
		
		// 允许锁屏时显示TodayView的消息
		if(allowLockScreenTodayView!=null&&!Constant.EMPTY_STR.equals(allowLockScreenTodayView)){
			iosDevicePolicy.setAllowLockScreenTodayView(String.valueOf(allowLockScreenTodayView));
		}
		
		// icloud同步钥匙串
		if(allowCloudKeychainSync!=null&&!Constant.EMPTY_STR.equals(allowCloudKeychainSync)){
			iosDevicePolicy.setAllowCloudKeychainSync(String.valueOf(allowCloudKeychainSync));
		}
		
		// 允许锁屏时显示控制中心的消息
		if(allowLockScreenControlCenter!=null&&!Constant.EMPTY_STR.equals(allowLockScreenControlCenter)){
			iosDevicePolicy.setAllowLockScreenControlCenter(String.valueOf(allowLockScreenControlCenter));
		}
		
		// 允许指纹解锁
		if(allowFingerprintForUnlock!=null&&!Constant.EMPTY_STR.equals(allowFingerprintForUnlock)){
			iosDevicePolicy.setAllowFingerprintForUnlock(String.valueOf(allowFingerprintForUnlock));
		}
		
		// 允许锁屏时显示通知消息
		if(allowLockScreenNotificationsView!=null&&!Constant.EMPTY_STR.equals(allowLockScreenNotificationsView)){
			iosDevicePolicy.setAllowLockScreenNotificationsView(String.valueOf(allowLockScreenNotificationsView));
		}
		
		// 允许锁屏时显示Passbook消息
		if(allowDisplayPassbook!=null&&!Constant.EMPTY_STR.equals(allowDisplayPassbook)){
			iosDevicePolicy.setAllowDisplayPassbook(String.valueOf(allowDisplayPassbook));
		}
		
		// 允许被管理的应用将数据存储到iCloud
		if(allowManagedAppsCloudSync!=null&&!Constant.EMPTY_STR.equals(allowManagedAppsCloudSync)){
			iosDevicePolicy.setAllowManagedAppsCloudSync(String.valueOf(allowManagedAppsCloudSync));
		}
		
		// 允许iCloud照片图库
		if(allowCloudPhotoLibrary!=null&&!Constant.EMPTY_STR.equals(allowCloudPhotoLibrary)){
			iosDevicePolicy.setAllowCloudPhotoLibrary(String.valueOf(allowCloudPhotoLibrary));
		}
		
		// 允许iCloud照片共享
		if(allowSharedStream!=null&&!Constant.EMPTY_STR.equals(allowSharedStream)){
			iosDevicePolicy.setAllowSharedStream(String.valueOf(allowSharedStream));
		}
		
		// 允许使用handoff
		if(allowActivityContinuation!=null&&!Constant.EMPTY_STR.equals(allowActivityContinuation)){
			iosDevicePolicy.setAllowActivityContinuation(String.valueOf(allowActivityContinuation));
		}
		
		// 允许使用YouTube
		if(allowUseYoutube!=null&&!Constant.EMPTY_STR.equals(allowUseYoutube)){
			iosDevicePolicy.setAllowUseYoutube(String.valueOf(allowUseYoutube));
		}
		
		// 允许使用iTunes Store
		if(allowUseiTunes!=null&&!Constant.EMPTY_STR.equals(allowUseiTunes)){
			iosDevicePolicy.setAllowUseItunes(String.valueOf(allowUseiTunes));
		}
		
		// 允许使用Game Center
		if(allowGameCenter!=null&&!Constant.EMPTY_STR.equals(allowGameCenter)){
			iosDevicePolicy.setAllowGameCenter(String.valueOf(allowGameCenter));
		}
		// 允许添加 Game Center好友
		if(allowAddingGameCenterFriends!=null&&!Constant.EMPTY_STR.equals(allowAddingGameCenterFriends)){
			iosDevicePolicy.setAllowAddingGameCenterFriends(String.valueOf(allowAddingGameCenterFriends));
		}
		// 允许多人游戏
		if(allowMultiplayerGaming!=null&&!Constant.EMPTY_STR.equals(allowMultiplayerGaming)){
			iosDevicePolicy.setAllowMultiplayerGaming(String.valueOf(allowMultiplayerGaming));
		}
		if(allowUseSafari!=null&&!Constant.EMPTY_STR.equals(allowUseSafari)){
			iosDevicePolicy.setAllowUseSafari(String.valueOf(allowUseSafari));
		}
		if(allowBackup!=null&&!Constant.EMPTY_STR.equals(allowBackup)){
			iosDevicePolicy.setAllowBackup(String.valueOf(allowBackup));
		}
		if(allowDocumentSynchronization!=null&&!Constant.EMPTY_STR.equals(allowDocumentSynchronization)){
			iosDevicePolicy.setAllowDocumentSynchronization(String.valueOf(allowDocumentSynchronization));
		}
		if(allowPhotoStream!=null&&!Constant.EMPTY_STR.equals(allowPhotoStream)){
			iosDevicePolicy.setAllowPhotoStream(String.valueOf(allowPhotoStream));
		}
		if(safeType!=null&&!Constant.EMPTY_STR.equals(safeType)){
			iosDevicePolicy.setSafeType(String.valueOf(safeType));
		}
		return iosDevicePolicy;
	}

	/**
	 * 批量插入Ios wifi配置
	 */
	@Override
	public void insertBatchIosWifiConfigure(String iosList,Integer iosDevicePolicyId,Integer managerId) {
		// 新增或者修改保存WIFI配置数据
		List<IosWifiConfigure> iosWifiList = new ArrayList<IosWifiConfigure>();
		IosWifiConfigure iosWifiConfigure = null;
		if(StringUtil.isNotBlank(iosList)&&!Constant.EMPTY_ARRAY.equals(iosList)){
			JSONArray data = JSONArray.fromObject(iosList);
			for(int i=0;i<data.size();i++){
				JSONObject iosObj = (JSONObject)data.get(i);
				String iosSsid = iosObj.getString("iosSsid");
				String isAutoJoin = iosObj.getString("isAutoJoin");
				String saftyIosType = iosObj.getString("saftyIosType");
				String agent = iosObj.getString("agent");
				String wifiIoskey = iosObj.getString("wifiIoskey");
				String agentServer = iosObj.getString("agentServer");
				String agentPort = iosObj.getString("agentPort");
				String agentAppraisal = iosObj.getString("agentAppraisal");
				String agentPassword = iosObj.getString("agentPassword");
				String agentUrl = iosObj.getString("agentUrl");
				iosWifiConfigure = new IosWifiConfigure();
				iosWifiConfigure.setSsid(iosSsid);
				iosWifiConfigure.setIsAutojoin(isAutoJoin);
				iosWifiConfigure.setSecurityType(saftyIosType);
				iosWifiConfigure.setAgent(agent);
				iosWifiConfigure.setPassword(wifiIoskey);
				iosWifiConfigure.setAgentServer(agentServer);
				iosWifiConfigure.setAgentPort(agentPort);
				iosWifiConfigure.setAgentAppraisal(agentAppraisal);
				iosWifiConfigure.setAgentPassword(agentPassword);
				iosWifiConfigure.setAgentUrl(agentUrl);
				iosWifiConfigure.setIosDevicePolicyId(iosDevicePolicyId);
				iosWifiConfigure.setCreateDate(new Date());
				iosWifiConfigure.setUpdateDate(new Date());
				iosWifiConfigure.setCreateUser(managerId);
				iosWifiConfigure.setUpdateUser(managerId);
				iosWifiList.add(iosWifiConfigure);
			}
			// 批量插入wifi bean数据
			iosWifiConfigureDao.insertBatchIosWifiConfigure(iosWifiList);
		}
	}

	/**
	 * 根据用户id获取最新ios设备策略
	 * @param id:用户id
	 * @return 返回该用户是否可以上网
	 */
	@Override
	public IsNetworkAvailableBean getTimeStrAndUrlList(String id) {
		IsNetworkAvailableBean bean = new IsNetworkAvailableBean();
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			UserModel userModel = userService.findOne(intId);
		    OrganizationModel organization = userModel.getOrganization();
		    if(organization!=null){
		    	Integer policyId = iosPolicyUserDao.findOne(intId, organization.getId());
		    	if(policyId!=null){
		    		IosDevicePolicy iosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(policyId);
		    		// 存放黑白名单URL
		    		bean.setUrlList(netBehaviorDao.selectBWUrlListByPolicyId(policyId));
		    		bean.setEnableBlacklist(iosDevicePolicy.getEnableBlacklist());
		    		if(iosDevicePolicy!=null&&iosDevicePolicy.getIsNetLimit()!=null&&Constant.YES.equals(iosDevicePolicy.getIsNetLimit())){
		    			bean.setVisitTimeStr(iosDevicePolicy.getVisitTimeStr());
		    		}
		    	}
		    }	
		}
		return bean;
	}

	/**
	 * 创建描述文件
	 * @param tokenUpdateInfo:token
	 * @return 返回描述文件
	 */
	@Override
	public String createMobileConfig(TokenUpdateInfo tokenUpdateInfo) {
	    try {
			Map<String,Entry<DirectionStatus, String>> map = new HashMap<String,Entry<DirectionStatus, String>>();
			if(map!=null){
				map.put("CheckInURL", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, String.format("%s?userId=%s&uuid=%s&iosUuid=%s", CommUtil.CHECK_IN_URL,tokenUpdateInfo.getUserId().toString(),tokenUpdateInfo.getPayloadIdentifier(),tokenUpdateInfo.getIosUuid())));
				map.put("ServerURL", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, String.format("%s?userId=%s&uuid=%s&iosUuid=%s", CommUtil.SERVER_URL,tokenUpdateInfo.getUserId().toString(),tokenUpdateInfo.getPayloadIdentifier(),tokenUpdateInfo.getIosUuid())));
				map.put("/plist/dict/PayloadIdentifier", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, tokenUpdateInfo.getPayloadIdentifier().toString()));
		        
				String mobileConfig = MDMProtocolUtils.getMobileConfigWithMap(map);
		        Document document=DocumentHelper.parseText(mobileConfig);
			    return document.asXML();
			}
		} catch (Exception e) {
			logger.error("ios device policy create mobile config failed,error message is:"+e.getMessage());
		}
        return null;
	}
}