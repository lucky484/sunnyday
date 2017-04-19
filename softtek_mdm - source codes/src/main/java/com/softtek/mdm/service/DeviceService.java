package com.softtek.mdm.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import com.softtek.mdm.bean.IsNetworkAvailableBean;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.AndroidDeviceUsers;
import com.softtek.mdm.model.CollectVirtualModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.model.IosPolicyUser;
import com.softtek.mdm.model.IosWifiConfigure;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.status.DirectionStatus;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 设备管理
 * @author jane.hui
 *
 */
public interface DeviceService {
	
	/**
	 * 获取设备策略List
	 * @return
	 */
	Page getDevicePolicyList(ManagerModel user,Integer orgId,String policytype,String objName,Integer begin, Integer num);
	
	/**
	 * 根据策略id获取设备数据
	 * @param orgId
	 * @param begin
	 * @param num
	 * @return
	 */
	Page getDeviceListByPolicyId(Integer orgId,String policyId,String type,Integer begin, Integer num);
	
	/**
	 * 策略设备删除功能
	 * @param request
	 * @return
	 */
	ResultDTO deletePolicy(HttpServletRequest request) throws ParseException;
	
	/**
	 * 删除IOS设备策略功能
	 * @param request
	 * @return
	 */
	ResultDTO deleteIosPolicy(HttpServletRequest request);
	
	/**
	 * 名单删除功能
	 * @param request
	 * @return
	 */
	ResultDTO delNameList(HttpServletRequest request);
	
	/**
	 * 策略设备删除功能
	 * @param request
	 * @return
	 */
	ResultDTO enablePolicy(HttpServletRequest request) throws ParseException;
	
	/**
	 * 获取用户列表
	 * @return
	 */
	List<UserModel> getUserList(Integer orgId);
	
	/**
	 * 获取虚拟集合和组
	 * @param orgId
	 * @return
	 */
	List<CollectVirtualModel> selectVirtualList(Integer orgId);
	
	/**
	 * 获取名单列表r
	 * @return
	 */
	Page selectNameList(ManagerModel user,Integer orgId,String name,String type,Integer begin, Integer num);
	
	/**
	 * 保存Android策略配置
	 * @return
	 * @throws Exception 
	 */
	ResultDTO saveAndroidPolicy(HttpServletRequest request,DataGridModel params) throws ParseException;
	
	/**
	 * 查看Android策略配置
	 * @param request
	 * @return
	 */
	Object findPolicy(HttpServletRequest request);
	
	/**
	 * 查看IOS策略配置
	 * @param request
	 * @return
	 */
	Object findIosPolicy(HttpServletRequest request);
	
	/**
	 * 编辑Android策略配置
	 * @param request
	 * @return
	 */
	Object editPolicy(HttpServletRequest request);
	
	/**
	 * 根据名称查询用户
	 * @param name
	 * @return
	 */
	List<UserModel> findUserByName(Integer orgId,String name,String userids,List<Integer> ids);
	
	/**
	 * 获取策略名称是否存在(Android)
	 * @param orgId
	 * @return
	 */
	boolean exists(String name,Integer id,Integer orgId);
	
	/**
	 * 获取策略名称是否存在(iOS)
	 * @param orgId
	 * @return
	 */
	boolean iosExists(String name,Integer id,Integer orgId);
	
	/**
	 * 保存黑白名单
	 * @param request
	 * @param params
	 * @return
	 */
	ResultDTO saveNameList(HttpServletRequest request,DataGridModel params);
	
	/**
	 * 根据机构下获取所有的android策略
	 * @param orgId
	 * @return
	 */
	List<AndroidDevicePolicy> findAll(Integer orgId);
	
	/**
	 * 根据机构下获取所有的ios策略
	 * @param orgId
	 * @return
	 */
	List<IosDevicePolicy> findIosAll(Integer orgId);
	
	AndroidDeviceUsers findOneByEntity(AndroidDeviceUsers entity);
	
	IosPolicyUser findOneByIosEntity(IosPolicyUser entity);
	
	int insertAndroidDeviceUser(AndroidDeviceUsers entity);
	
	int updateAndroidDeviceUser(AndroidDeviceUsers entity);
	
	/**
	 * 根据用户编号查询当前用户使用的设备的设备策略
	 * @param userId
	 * @return
	 */
	Map<String, Object> findUserDevicePolicy(Integer userId,Integer orgId);
	
	
	AndroidDevicePolicy findOneAndroidDevicePolicy(Integer pKey);
	/**
	 * 获得设备策略总数
	 */
	int getDevicePolicySize(Integer orgid);
	
	/**
	 * 编辑应用
	 * @param id
	 * @return
	 */
	Object editApplication(Integer id);
	
	/**
	 * 查询名称是否存在
	 * @param name
	 * @param id
	 * @param orgId
	 * @return
	 */
    boolean isNameListExists(String name,Integer id,Integer orgId);
    
	/**
	 * 查询网页黑白名单列表
	 * @return
	 */
	Page selectNetNameList(ManagerModel user,Integer orgId,String name,String type,Integer begin, Integer num);
	
	/**
	 * 根据策略id获取策略
	 * @param id
	 * @return
	 */
	AndroidDevicePolicy getPolicy(Integer id);
	
	/**
	 * 修改黑白名单的时候对应的策略也做相应的修改
	 * @param params
	 * @return
	 */
    ResultDTO jpushPoicyByBWId(Integer bwId);

    /**
     * 用户管理模块查询该机构下所有的android策略
     * @param maps
     * @return
     */
	List<AndroidDevicePolicy> findAllAndroidDevicePolicyByMap(Map<String, Object> maps);

    /**
     * 用户管理模块查询该机构下所有的ios策略
     * @param maps
     * @return
     */
	List<IosDevicePolicy> findAllIosDevicePolicyByMap(Map<String, Object> maps);
	
	/**
	 * 获取默认策略
	 * @return
	 */
	AndroidDevicePolicy getDefaultPolicy();
	
	/**
	 * 保存Ios策略
	 * @param request
	 * @param params
	 * @return
	 * @throws ParseException
	 */
	ResultDTO saveIosPolicy(HttpServletRequest request,DataGridModel params) throws ParseException;
	
	/**
	 * 初始化Ios策略
	 * @param request
	 * @return
	 */
	ResultDTO editIosPolicy(HttpServletRequest request);
	
    /**
     * 根据用户id获取策略
     * @param id
     * @return
     */
    String getPolicyByUserId(String id);
    
	/**
	 * 将设备策略属性创建成mobileconfig格式的文件
	 * @param policyId:策略id
	 * @return 返回创建mobileconfig格式的文件内容
	 */
	public String createMobileConfig(Integer policyId,List<IosWifiConfigure> list,TokenUpdateInfo tokenUpdateInfo);
	
	/**
	 * 创建推送命令
	 * @param mobileConfig:设备策略相关属性内容
	 * @return 返回推送命令内容
	 */
	public String createCommand(String mobileConfig,String commandUUID);
	
	/**
	 * 启用或者禁用ios设备策略
	 * @param request
	 * @return
	 */
	public ResultDTO enableIosPolicy(HttpServletRequest request);
	
	/**
	 * 更新IOS设备策略与用户关联表
	 * @param entity
	 * @return
	 */
	int updateIosDeviceUser(IosPolicyUser entity);
	
	/**
	 * 根据用户编号查询当前用户使用的设备的Ios设备策略
	 * @param userId
	 * @return
	 */
	Map<String, Object> findUserIosDevicePolicy(Integer userId,Integer orgId);
	
	/**
	 * 根据主键查询设备策略
	 * @param pKey
	 * @return
	 */
	IosDevicePolicy findOneIosDevicePolicy(Integer pKey);
		
	/**
	 * 插入设备策略授权用户关联表
	 * @param entity
	 * @return
	 */
	int insertIosDeviceUser(IosPolicyUser entity);
	
	/**
	 * 在document中插入wifi元素
	 * @param document
	 * @param list
	 * @return
	 */
	String createIosElement(Document document,List<IosWifiConfigure> list,IosDevicePolicy iosDevicePolicy);
	
	/**
	 * 将Ios设备策略的属性值封装成map
	 * @param policy
	 * @return
	 */
	Map<String,Entry<DirectionStatus, String>> createPolicyMap(IosDevicePolicy policy);
	
	/**
	 * 根据用户id查找绑定的设备
	 * @param userId
	 * @return
	 */
	List<DeviceBasicInfoModel> getDevicesByUserId(Integer userId);
	
	/**
	 * 保存时候根据request获取参数返回IOS设备策略
	 * @param params:请求参数
	 * @return 返回ios设备策略
	 */
	IosDevicePolicy getIosDevicePolicy(DataGridModel params);
	
	/**
	 * 批量插入ios wifi配置
	 * @param iosList:前台选择的wifi配置字符串列表
	 * @param iosDevicePolicyId:设备策略id
	 * @param managerId:管理员id
	 */
	void insertBatchIosWifiConfigure(String iosList,Integer iosDevicePolicyId,Integer managerId);
	
	/**
	 * 根据用户id获取url列表
	 * @param id:用户id
	 * @return 返回url列表
	 */
	IsNetworkAvailableBean getTimeStrAndUrlList(String id);
	
	/**
	 * 创建描述文件
	 * @param tokenUpdateInfo
	 * @return 返回描述文件
	 */
    String createMobileConfig(TokenUpdateInfo tokenUpdateInfo);
}