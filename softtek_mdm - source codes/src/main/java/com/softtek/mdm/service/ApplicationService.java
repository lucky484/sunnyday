package com.softtek.mdm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.softtek.mdm.bean.AppBean;
import com.softtek.mdm.model.App;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 应用管理
 * @author jane.hui
 *
 */
public interface ApplicationService {

	/**
	 * 应用发布保存方法
	 * @param request
	 * @param params
	 * @return
	 */
	ResultDTO save(HttpServletRequest request,DataGridModel params,HttpSession session);
	
	/**
	 * 应用发布保存方法(iOS)
	 * @param request
	 * @param params
	 * @return
	 */
	ResultDTO saveIosApp(HttpServletRequest request,DataGridModel params,HttpSession session);
	
	/**
	 * 加载应用管理数据
	 * @param request
	 * @param session
	 * @param begin
	 * @param num
	 * @return
	 */
     Page getApplicationList(HttpServletRequest request,HttpSession session,Integer begin, Integer num);
     
     /**
      * 获取应用分发数据
      * @param request
      * @param session
      * @param begin
      * @param num
      * @return
      */
     Page getApplicationDistributeList(HttpServletRequest request,HttpSession session,Integer begin, Integer num);
     
     /**
      * 检索设备应用
      * @param request
      * @param session
      * @param begin
      * @param num
      * @return
      */
     Page queryDevice(HttpServletRequest request,HttpSession session,Integer begin, Integer num);
     
     /**
      * 删除应用
      * @param request
      * @return
      */
     ResultDTO delete(HttpServletRequest request);
     
     /**
      * 上下架
      * @param request
      * @return
      */
     ResultDTO changeState(HttpServletRequest request);
     
     /**
      * 返回编辑页面
      * @param request
      * @return
      */
     App edit(HttpServletRequest request);
     
 	/**
 	 * 更新保存应用
 	 * @param request
 	 * @param params
 	 * @return
 	 * 
 	 */
 	ResultDTO update(HttpServletRequest request,DataGridModel params,HttpSession session);
 	
 	/**
 	 * 根据机构获取对应机构下的应用
 	 * @param params
 	 * @return
 	 */
 	List<App> getApplicationByOrgId(HttpServletRequest request,HttpSession session);
 	
 	/**
 	 * 加载部门虚拟组数据
 	 * @param request
 	 * @return
 	 */
 	ResultDTO loadDepart(HttpServletRequest request,HttpSession session);
 	
 	/**
 	 * 赋予应用级权限
 	 * @param request
 	 * @param params
 	 * @param session
 	 * @return
 	 */
 	ResultDTO grantAppAuth(HttpServletRequest request,DataGridModel params,HttpSession session);
 	
 	/**
 	 * 取消应用级授权
 	 * @param request
 	 * @param session
 	 * @return
 	 */
 	ResultDTO cancelGrantAuth(HttpServletRequest request,HttpSession session);
 	
 	/**
 	 * 获取授权应用列表
 	 * @param request
 	 * @param session
 	 * @return
 	 */
 	List<AppBean> getAppList(HttpServletRequest request,HttpSession session,String type);
 	
 	/**
 	 * 判断AppId是否存在
 	 * @param appId
 	 * @param id
 	 * @param orgId
 	 * @return
 	 */
    boolean exists(String appId,Integer id,Integer orgId);
    
    /**
     * 获取装机数
     * @param request
     * @return
     */
    String getInstalledNumber(HttpServletRequest request,HttpSession session);
    
	/**
	 * 应用发布保存方法
	 * @param request
	 * @param params
	 * @return
	 */
	ResultDTO publish(HttpServletRequest request,DataGridModel params,HttpSession session);
   
	/**
	 * 安装应用
	 * @param request
	 * @return
	 */
	DeviceResultModel<String> installApplication(HttpServletRequest request);
	
	/**
	 * 卸载应用
	 * @param request
	 * @return
	 */
	DeviceResultModel<String> uninstallApplication(HttpServletRequest request);
}
