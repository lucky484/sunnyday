package com.softtek.mdm.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.bean.AppBean;
import com.softtek.mdm.dao.AppDao;
import com.softtek.mdm.dao.AppDepartmentAuthorizationDao;
import com.softtek.mdm.dao.AppVirtualGroupAuthorizationDao;
import com.softtek.mdm.dao.UserAppDao;
import com.softtek.mdm.model.App;
import com.softtek.mdm.model.AppDepartmentAuthorization;
import com.softtek.mdm.model.AppVirtualGroupAuthorization;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserApp;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.ApplicationService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.util.Constant.ReleaseType;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 应用发布
 * @author jane.hui
 *
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

	private static Logger logger = Logger.getLogger(ApplicationServiceImpl.class);
	
	@Autowired
	private AppDao appDao;

	@Autowired
	private MessageSource messageSourceService;
	
	@Autowired
	private AppDepartmentAuthorizationDao appDepartmentAuthorizationDao;
	
	@Autowired
	private AppVirtualGroupAuthorizationDao appVirtualGroupAuthorizationDao;
	
	@Autowired
	private TreeManager treeManager;
	
	@Autowired
	private UserAppDao userAppDao;
	
	@Autowired
	@Qualifier("iosApplicationNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Autowired
	@Qualifier("iosApplicationRemoveNotifyService")
	private AbstractIosPush abstractIosPush2;
	
	/**
	 * 线程池对象
	 */
	@Autowired
	private TaskExecutor taskExecutor; 

	/**
	 * 应用发布功能保存
	 */
	@Override
	public ResultDTO save(HttpServletRequest request, DataGridModel params,HttpSession session) {
		ResultDTO resultDto = new ResultDTO();
        try {
			Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			if (orgObj == null) {
				resultDto.type = BaseDTO.WARNING;
				resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
				return resultDto;
			}
			Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (obj == null) {
				resultDto.type = BaseDTO.DANGER;
				resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
				return resultDto;
			}
			ManagerModel manager = (ManagerModel) obj;
			OrganizationModel organization = (OrganizationModel)orgObj;
			Object releaseType = params.getParams().get("releaseType");
			Object appName = params.getParams().get("appName");
			Object signatureCertificate = params.getParams().get("signatureCertificate");
			Object appVersion = params.getParams().get("appVersion");
			Object author = params.getParams().get("author");
			Object appDescription = params.getParams().get("appDescription");
			Object minimumVersion = params.getParams().get("minimumVersion");
			Object apkFile =  params.getParams().get("apkFile");
			Object appId =  params.getParams().get("appId");
			Object icon =  params.getParams().get("icon");
			App app = new App();
			app.setOrgId(organization.getId());
	        if(releaseType!=null&&!Constant.EMPTY_STR.equals(releaseType)){
	        	app.setReleaseType(String.valueOf(releaseType));
	        }
	        if(appName!=null&&!Constant.EMPTY_STR.equals(appName)){
	        	app.setAppName(String.valueOf(appName));
	        }
	        if(signatureCertificate!=null&&!Constant.EMPTY_STR.equals(signatureCertificate)){
	        	app.setSignatureCertificate(String.valueOf(signatureCertificate));
	        }
	        if(appVersion!=null&&!Constant.EMPTY_STR.equals(appVersion)){
	        	app.setAppVersion(String.valueOf(appVersion));
	        }
	        if(author!=null&&!Constant.EMPTY_STR.equals(author)){
	        	app.setAuthor(String.valueOf(author));
	        }
	        if(appDescription!=null&&!Constant.EMPTY_STR.equals(appDescription)){
	        	app.setAppDescription(String.valueOf(appDescription));
	        }
	        if(minimumVersion!=null&&!Constant.EMPTY_STR.equals(minimumVersion)){
	        	app.setMinimumVersion(String.valueOf(minimumVersion));
	        }
	        if(apkFile!=null&&!Constant.EMPTY_STR.equals(apkFile)){
	        	app.setApkFile(String.valueOf(apkFile));
	        }
	        if(appId!=null&&!Constant.EMPTY_STR.equals(appId)){
	        	app.setAppId(String.valueOf(appId));
	        }
	        if(icon!=null&&!Constant.EMPTY_STR.equals(icon)){
	        	app.setIconPath(String.valueOf(icon));
	        }
	        app.setAppCount(0);
	        app.setState(Constant.YES);
	        app.setCreateDate(new Date());
	        app.setUpdateDate(new Date());
	        app.setCreateUser(manager.getId());
	        app.setUpdateUser(manager.getId());
	        	// 获取文件大小
	        	String fileSize = CommUtil.getFileSize(CommUtil.FILE_SAVE_PATH+app.getApkFile());
				app.setFileSize(fileSize);
	
	        int size = appDao.insertSelective(app);
	        if(size==0){
			   resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
			   resultDto.type = BaseDTO.DANGER;
	        }
		 } catch (IOException e) {
			logger.error("Application release save failed,error message is "+e.getMessage());
		 }
		 resultDto.message = messageSourceService.getMessage("web.institution.application.save.success.lable",null, LocaleContextHolder.getLocale());
	     resultDto.type = BaseDTO.SUCCESS;
		 return resultDto;
	}
	
	/**
	 * 加载应用管理数据
	 */
	@Override
	public Page getApplicationList(HttpServletRequest request, HttpSession session, Integer begin, Integer num) {
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		OrganizationModel organization = (OrganizationModel)orgObj;
		Page p = new Page();
		DataGridModel params = new DataGridModel();
		params.setBegin(begin);
		params.setNum(num);
		params.getParams().put("orgId", organization.getId());
		String releaseType = request.getParameter("releaseType");
		String appName = request.getParameter("appName");
		String appState = request.getParameter("appState");
		if(StringUtil.isNotBlank(releaseType)){
			params.getParams().put("releaseType", releaseType);
		}
		if(StringUtil.isNotBlank(appName)){
			params.getParams().put("appName", appName);
		}
		if(StringUtil.isNotBlank(appState)){
			params.getParams().put("appState", appState);
		}
		List<App> list = appDao.getApplicationListByParams(params);
		int total = appDao.getApplicationCountByParams(params);
		p.setData(list);
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	/**
	 * 删除应用
	 */
	@Override
	public ResultDTO delete(HttpServletRequest request) {
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		if(StringUtil.isNotBlank(id)){
			try {
				Integer intId = Integer.valueOf(id); 
				App app = appDao.selectByPrimaryKey(intId);
				if(app!=null){
				   String path = CommUtil.FILE_SAVE_PATH + app.getApkFile(); 
				   File f = new File(path);
/*				   FileUtils.forceDelete(f);*/
			       boolean result = f.delete();
				   if(!result){
					   System.gc();
					   f.delete();
				   }
				}
				int size = appDao.updateByPrimaryId(intId);
				if(size==0){
					resultDto.message = messageSourceService.getMessage("web.institution.application.delete.failed.lable",null, LocaleContextHolder.getLocale());
					resultDto.type = BaseDTO.DANGER;
					return resultDto;
				}
				List<Integer> userIdList = appDepartmentAuthorizationDao.selectUserByAppIds(intId);
				mqPushliser(userIdList);
				appDepartmentAuthorizationDao.deleteByAppId(intId);
				appVirtualGroupAuthorizationDao.deleteByAppId(intId);	
			} catch(Exception e){
				resultDto.message = messageSourceService.getMessage("web.institution.application.delete.failed.lable",null, LocaleContextHolder.getLocale());
				resultDto.type = BaseDTO.DANGER;
				return resultDto;
			}
		}
		 resultDto.message = messageSourceService.getMessage("web.institution.application.delete.success.lable",null, LocaleContextHolder.getLocale());
	     resultDto.type = BaseDTO.SUCCESS;
		 return resultDto;
	}

	/**
	 * 上下架状态
	 */
	@Override
	public ResultDTO changeState(HttpServletRequest request) {
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		String tag = request.getParameter("tag");
		try {
			if(StringUtil.isNotBlank(id)&&StringUtil.isNotBlank(tag)){
				int intId = Integer.valueOf(id);
				DataGridModel params = new DataGridModel();
				params.getParams().put("id", id);
				params.getParams().put("state", tag);
				int size = appDao.updateStateByPrimaryId(params);
				if(size==0){
					// 启用
					if(Constant.YES.equals(tag)){	
						 resultDto.message = messageSourceService.getMessage("web.institution.application.onself.failed.lable",null, LocaleContextHolder.getLocale());
					     resultDto.type = BaseDTO.DANGER;
						 return resultDto;
					} else {// 禁用
						 resultDto.message = messageSourceService.getMessage("web.institution.application.offself.failed.lable",null, LocaleContextHolder.getLocale());
					     resultDto.type = BaseDTO.DANGER;
						 return resultDto;
					}
				}
				List<Integer> userIdList = appDepartmentAuthorizationDao.selectUserByAppIds(intId);
				mqPushliser(userIdList);
			}
		} catch(Exception e){
			// 启用
			if(Constant.YES.equals(tag)){	
				 resultDto.message = messageSourceService.getMessage("web.institution.application.onself.failed.lable",null, LocaleContextHolder.getLocale());
			     resultDto.type = BaseDTO.DANGER;
				 return resultDto;
			} else {// 禁用
				 resultDto.message = messageSourceService.getMessage("web.institution.application.offself.failed.lable",null, LocaleContextHolder.getLocale());
			     resultDto.type = BaseDTO.DANGER;
				 return resultDto;
			}
		}
		// 启用
		if(Constant.YES.equals(tag)){	
			 resultDto.message = messageSourceService.getMessage("web.institution.application.onself.success.lable",null, LocaleContextHolder.getLocale());
		     resultDto.type = BaseDTO.SUCCESS;
			 return resultDto;
		} else {// 禁用
			 resultDto.message = messageSourceService.getMessage("web.institution.application.offself.success.lable",null, LocaleContextHolder.getLocale());
		     resultDto.type = BaseDTO.SUCCESS;
			 return resultDto;
		}
	}

	/**
	 * 返回编辑页面
	 */
	@Override
	public App edit(HttpServletRequest request) {
		String id = request.getParameter("id");
		if(StringUtil.isNotBlank(id)){
			Integer intId = Integer.valueOf(id);
			App app = appDao.selectByPrimaryKey(intId);
			app.setApkFile(CommUtil.DISPLAY_APP_PATH+app.getApkFile());
			if(ReleaseType.ANDROID.equals(app.getReleaseType())){
			   app.setIconPath(CommUtil.DISPLAY_ICON_Path+Constant.getModule.APPLICATION+Constant.SplitSymbol.SLASH+app.getIconPath());
			} else if(ReleaseType.IOS.equals(app.getReleaseType())){
				app.setIconPath(app.getIconPath());
			}
			return app;
		}
		return null;
	}
	
	/**
	 * 应用发布功能保存
	 */
	@Override
	public ResultDTO update(HttpServletRequest request, DataGridModel params,HttpSession session) {
		ResultDTO resultDto = new ResultDTO();
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		if (obj == null) {
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.application.update.failed.lable",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
		ManagerModel manager = (ManagerModel) obj;
		Object id = params.getParams().get("id");
		Object appDescription = params.getParams().get("appDescription");
		App app = new App();
        if(id!=null&&!Constant.EMPTY_STR.equals(id)){
        	Integer intId = Integer.valueOf(String.valueOf(id));		
        	app.setId(intId);
        }
        if(appDescription!=null&&!Constant.EMPTY_STR.equals(appDescription)){
        	app.setAppDescription(String.valueOf(appDescription));
        }
        app.setUpdateDate(new Date());
        app.setUpdateUser(manager.getId());
        int size = appDao.updateAppDescriptionById(app);
        if(size==0){
		   resultDto.message = messageSourceService.getMessage("web.institution.application.update.failed.lable",null, LocaleContextHolder.getLocale());
		   resultDto.type = BaseDTO.DANGER;
        }
		 resultDto.message = messageSourceService.getMessage("web.institution.application.update.success.lable",null, LocaleContextHolder.getLocale());
	     resultDto.type = BaseDTO.SUCCESS;
		 return resultDto;
	}

	/**
	 * 根据机构ID获取对应机构下的应用
	 */
	@Override
	public List<App> getApplicationByOrgId(HttpServletRequest request,HttpSession session) {
		DataGridModel params = new DataGridModel();
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		OrganizationModel organization = (OrganizationModel)orgObj;
		params.getParams().put("orgId", organization.getId());
		String value = request.getParameter("value");
		if(StringUtil.isNotBlank(value)){
			params.getParams().put("value", value);
		}
		return appDao.getApplicationByOrgId(params);
	}

	/**
	 * 加载部门虚拟组数据
	 */
	@Override
	public ResultDTO loadDepart(HttpServletRequest request,HttpSession session) {
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		OrganizationModel organization = (OrganizationModel)orgObj;
		ResultDTO resultDto = new ResultDTO();
		    // 从前台获取应用id..
		    String id = request.getParameter("id");
		    if(StringUtil.isBlank(id)){
		    	// 当点击应用授权或者取消授权id
		    	Object objId = request.getAttribute("id");
		    	if(objId!=null&&!Constant.EMPTY_STR.equals(objId)){
		    		id = String.valueOf(objId);
		    	}
		    }
	    	DataGridModel params = new DataGridModel();
	    	params.getParams().put("id", id);
	    	params.getParams().put("orgId", organization.getId());
	    	// 已授权部门List
	    	List<AppDepartmentAuthorization> departList = appDepartmentAuthorizationDao.loadDepartAuth(params);
	    	// 已授权虚拟组List
	    	List<AppVirtualGroupAuthorization> virtualList = appVirtualGroupAuthorizationDao.loadVirtualAuth(params);
	        // 未授权虚拟集合
	    	List<AppVirtualGroupAuthorization> firstVirtualList = appVirtualGroupAuthorizationDao.loadFirstVirtualList(params);
	        // 未授权虚拟组
	    	List<AppVirtualGroupAuthorization> secondList = appVirtualGroupAuthorizationDao.loadVirtualGroupList(params);
	    	// 将虚拟集合和虚拟组整合
	    	List<AppVirtualGroupAuthorization> vlist = null;
	    	for(AppVirtualGroupAuthorization connection:firstVirtualList){
	        	for(AppVirtualGroupAuthorization group:secondList){
	        		if(connection.getFatherId().equals(group.getFatherId())){
	        			vlist = connection.getList();
	        			if(vlist==null){
	        				vlist = new ArrayList<AppVirtualGroupAuthorization>();
	        			}
        				vlist.add(group);
	        			connection.setList(vlist);
	        		}
	        	}
	        }
	    	// 如果该虚拟集合下面没有虚拟组则去除
	    	List<AppVirtualGroupAuthorization> virtualGroupList = new ArrayList<AppVirtualGroupAuthorization>();
	    	for(AppVirtualGroupAuthorization connection:firstVirtualList){
	        	if(connection.getList()!=null&&connection.getList().size()!=0){
	        		virtualGroupList.add(connection);
	        	}
	        }
	    	// 获取未授权部门
			@SuppressWarnings("unchecked")
			List<StructureModel> list=(List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			List<StructureModel> structureList = new ArrayList<StructureModel>();
			for(StructureModel sm:list){
				int i = 0;
				for(AppDepartmentAuthorization depart:departList){
					// 判断组织架构部门中和已授权部门id是否相等，如果相等，则去除，获取未授权部门
					if(sm.getId().equals(depart.getOrgStructureId())){
						i=1;
					}
				}
				sm.setIsAuth(String.valueOf(i));
				if(i==0){
					sm.setName(sm.getName()+"("+messageSourceService.getMessage("web.institution.application.upload.version.resultdto.message.notAuthorized",null, LocaleContextHolder.getLocale())+")");
				} else {
					sm.setName(sm.getName()+"("+messageSourceService.getMessage("web.institution.application.upload.version.resultdto.message.alreadyAuthorized",null, LocaleContextHolder.getLocale())+")");
				}
				structureList.add(sm);
			}
			List<NodeModel> nodes=treeManager.buildMoreTree(structureList);
			String jsonStr = "[";
			int i = 1;
			for (NodeModel node : nodes) {
				jsonStr+=JSONObject.fromObject(node).toString();
				if(i!=nodes.size()){
					jsonStr += ",";
				}
				i++;
			}
			jsonStr += "]";
			jsonStr=StringUtil.replace(jsonStr, "\"nodes\":[],", "");
			System.out.println("jsonStr:"+jsonStr);
			resultDto.jsonStr = jsonStr;
	    	resultDto.departList=departList;
	    	resultDto.virtualList = virtualList;
	    	resultDto.firstVirtualList = virtualGroupList;
		    return resultDto;
	}

	/**
	 * 赋予应用级权限
	 */
	@Override
	public ResultDTO grantAppAuth(HttpServletRequest request, DataGridModel params, HttpSession session) {
		ResultDTO resultDto = new ResultDTO();
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		if (obj == null) {
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.application.grantappauth.resultdto.message.failed",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
		ManagerModel manager = (ManagerModel) obj;
		Object id = params.getParams().get("id");
		Object tag = params.getParams().get("tag");
		Object type = params.getParams().get("type");
		Object departIds = params.getParams().get("departIds");
		Object virIds = params.getParams().get("virIds");
		// 存储AppVirtualGroupAuthorization数组
		List<AppVirtualGroupAuthorization> virList = new ArrayList<AppVirtualGroupAuthorization>();
		// 存储AppDepartmentAuthorization数组
		List<AppDepartmentAuthorization> departList = new ArrayList<AppDepartmentAuthorization>();
		if(tag!=null&&!Constant.EMPTY_STR.equals(tag)){
			Integer intId = null;
			if(id!=null&&!Constant.EMPTY_STR.equals(id)){
				request.setAttribute("id", String.valueOf(id));
				intId = Integer.valueOf(String.valueOf(id));
            }
			String strType = "";
			if(type!=null&&!Constant.EMPTY_STR.equals(type)){
				strType = String.valueOf(type);
            }
			if(Constant.YES.equals(tag)){
				if(virIds!=null&&!Constant.EMPTY_STR.equals(virIds)){
					String[] virArray = String.valueOf(virIds).split(",");
					AppVirtualGroupAuthorization appVirtual = null;
					for(String vir : virArray){
						appVirtual = new AppVirtualGroupAuthorization();
						appVirtual.setAppId(intId);
						appVirtual.setVirtualGroupId(Integer.valueOf(vir));
						appVirtual.setIsInstall(strType);
						appVirtual.setCreateDate(new Date());
						appVirtual.setCreateUser(manager.getId());
						appVirtual.setUpdateDate(new Date());
						appVirtual.setUpdateUser(manager.getId());
						virList.add(appVirtual);
					}
				}
				if(virList.size()>0){
					int size = appVirtualGroupAuthorizationDao.insertBatchAppVirtualGroup(virList);
					if(size==0){
						resultDto.type = BaseDTO.DANGER;
						resultDto.message = messageSourceService.getMessage("web.institution.application.grantappauth.resultdto.message.failed",null, LocaleContextHolder.getLocale());
						return resultDto;
					}
				}
			} else {
				if(departIds!=null&&!Constant.EMPTY_STR.equals(departIds)){
					String[] departArray = String.valueOf(departIds).split(",");
					AppDepartmentAuthorization department = null;
					for(String depart : departArray){
						department = new AppDepartmentAuthorization();
						department.setAppId(intId);
						department.setOrgStructureId(Integer.valueOf(depart));
						department.setIsInstall(strType);
						department.setCreateDate(new Date());
						department.setCreateUser(manager.getId());
						department.setUpdateDate(new Date());
						department.setUpdateUser(manager.getId());
						departList.add(department);
					}
				}
				if(departList.size()>0){
					int size = appDepartmentAuthorizationDao.insertBatchAppDepartment(departList);
					if(size==0){
						resultDto.type = BaseDTO.DANGER;
						resultDto.message = messageSourceService.getMessage("web.institution.application.grantappauth.resultdto.message.failed",null, LocaleContextHolder.getLocale());
						return resultDto;
					}
				}
			}
		}
		ResultDTO rDto =  loadDepart(request,session);
		resultDto.jsonStr  = rDto.jsonStr;
		resultDto.departList = rDto.departList;
		resultDto.virtualList = rDto.virtualList;
		resultDto.firstVirtualList = rDto.firstVirtualList;
		resultDto.type = BaseDTO.SUCCESS;
		resultDto.message = messageSourceService.getMessage("web.institution.application.grantappauth.resultdto.message.success",null, LocaleContextHolder.getLocale());
		return resultDto;
	}

	/**
	 * 取消应用级授权
	 */
	@Override
	public ResultDTO cancelGrantAuth(HttpServletRequest request, HttpSession session) {
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("secondId");
		String tag = request.getParameter("tag");
		if(StringUtil.isNotBlank(id)&&StringUtil.isNotBlank(tag)){
			// 删除虚拟组
			if(Constant.YES.equals(tag)){
				AppVirtualGroupAuthorization aVirtualGourp = new AppVirtualGroupAuthorization();
				aVirtualGourp.setId(Integer.valueOf(id));
				aVirtualGourp.setDeleteTime(new Date());
				int size = appVirtualGroupAuthorizationDao.updateByPrimaryKeySelective(aVirtualGourp);
				if(size==0){
					resultDto.type = BaseDTO.DANGER;
					resultDto.message = messageSourceService.getMessage("web.institution.application.cancelappauth.resultdto.message.failed",null, LocaleContextHolder.getLocale());
					return resultDto;
				}
			} else {// 删除部门
				AppDepartmentAuthorization aDepartAuth = new AppDepartmentAuthorization();
				aDepartAuth.setId(Integer.valueOf(id));
				aDepartAuth.setDeleteTime(new Date());
				int size = appDepartmentAuthorizationDao.updateByPrimaryKeySelective(aDepartAuth);
				if(size==0){
					resultDto.type = BaseDTO.DANGER;
					resultDto.message = messageSourceService.getMessage("web.institution.application.cancelappauth.resultdto.message.failed",null, LocaleContextHolder.getLocale());
					return resultDto;
				}
			}
		}
		ResultDTO rDto =  loadDepart(request,session);
		resultDto.jsonStr  = rDto.jsonStr;
		resultDto.departList = rDto.departList;
		resultDto.virtualList = rDto.virtualList;
		resultDto.firstVirtualList = rDto.firstVirtualList;
		resultDto.type = BaseDTO.SUCCESS;
		resultDto.message = messageSourceService.getMessage("web.institution.application.cancelappauth.resultdto.message.success",null, LocaleContextHolder.getLocale());
		return resultDto;
	}

	/**
	 * 检索设备信息
	 */
	@Override
	public Page queryDevice(HttpServletRequest request, HttpSession session, Integer begin, Integer num) {
		String id = request.getParameter("id");
		Page p = new Page();
		if(StringUtil.isNotBlank(id)){
			Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			OrganizationModel organization = (OrganizationModel)orgObj;
			DataGridModel params = new DataGridModel();
			params.setBegin(begin);
			params.setNum(num);
			params.getParams().put("orgId", organization.getId());
			params.getParams().put("id", id);
			String releaseType = request.getParameter("releaseType");
			String appName = request.getParameter("appName");
			if(StringUtil.isNotBlank(releaseType)){
				params.getParams().put("releaseType", releaseType);
			}
			if(StringUtil.isNotBlank(appName)){
				params.getParams().put("appName", appName);
			}
			int total = appDao.queryDeviceCount(params);
			p.setData(appDao.queryDevice(params));
			p.setRecordsTotal(total);
			p.setRecordsFiltered(total);
		}
		return p;
	}

	/**
	 * 获取授权应用列表
	 */
	@Override
	public List<AppBean> getAppList(HttpServletRequest request, HttpSession session,String type) {
		List<AppBean> list = new ArrayList<AppBean>();
		String strUserId = request.getParameter("userId");

		DataGridModel params = new DataGridModel();
		if(StringUtil.isNotBlank(strUserId)){
			Integer userId = Integer.valueOf(strUserId);
		   params.getParams().put("userId", userId);
		}
		params.getParams().put("type", type);
		List<AppBean> departList =  appDao.getAppListByDepartIds(params);
		AppBean appBean = null;
		String s = Constant.EMPTY_STR;
		for(AppBean app:departList){
			String temp = app.getAppId() + app.getUserId();
			appBean = new AppBean();
			BeanUtils.copyProperties(app, appBean);
			appBean.setDownloadUrl(CommUtil.DISPLAY_APP_PATH);
			if(Constant.ReleaseType.ANDROID.toString().equals(type)){
			    appBean.setIconPath(CommUtil.DISPLAY_ICON_Path+Constant.getModule.APPLICATION+Constant.SplitSymbol.SLASH+app.getIconPath());
			} else {
				appBean.setIconPath(app.getIconPath());
			}
			if(app.getUserId()!=null){
				if(Constant.EMPTY_STR.equals(s)){
					list.add(appBean);
				} else if(!s.equals(temp)){
					list.add(appBean);
				}
				s = app.getAppId()+app.getUserId();
			}
		}
		return list;
	}
	
	/**
	 * 判断策略名称是否存在
	 */
	@Override
	public boolean exists(String appId,Integer id,Integer orgId) {
		// 新增功能(判断如何存在一个策略名称一样，则提示已存在)
		DataGridModel objParams = new DataGridModel();
		objParams.getParams().put("orgId", orgId);
		objParams.getParams().put("appId", appId);
		if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))){
			Integer existSize = appDao.selectNameByAppId(objParams);
			if(existSize>0){
				return true;
			}
		}/* else {
			objParams.getParams().put("id", Integer.valueOf(String.valueOf(id)));
			Integer existSize = appDao.selectNameByAppId(objParams);
			// 编辑功能(判断如果存在一个以上策略名称一样，则提示已存在，因为本身在数据库存在一条名称一样)
			if(existSize>0){
				return true;
			}
		}*/
		return false;
	}

	/**
	 * 获取装机数
	 * appId:应用ID
	 * tag(1:加1  0.减1)
	 * userId:用户Id
	 */
	@Override
	public String getInstalledNumber(HttpServletRequest request,HttpSession session) {
		String appId = request.getParameter("appId");
		String tag = request.getParameter("tag");
		String strUserId = request.getParameter("userId");
		if(StringUtil.isNotBlank(appId)&&StringUtil.isNotBlank(tag)&&StringUtil.isNotBlank(strUserId)){
			Integer userId = Integer.valueOf(strUserId);
			App app = new App();
			App oldApp = appDao.selectAppByAppId(appId);
			DataGridModel params = new DataGridModel();
			params.getParams().put("userId", userId);
			if(oldApp != null) {
				params.getParams().put("appId", oldApp.getId());
				app.setId(oldApp.getId());
				int isExist = userAppDao.selectUserAppSizeById(params);
			    // 绑定用户与应用
				if(tag.equals(Constant.YES)) {
					// 更新应用的安装数
				    if(oldApp.getAppCount()==null){
				    	app.setAppCount(1);
				    } else {
				    	app.setAppCount(oldApp.getAppCount() + 1);
				    }
				    app.setUpdateDate(new Date());
					int size = appDao.updateByPrimaryKeySelective(app);
					if(size==0) {
						return BaseDTO.ERROR;
					} 
					// 如果isExist<1表示用户与应用表之间无关联
					if(isExist < 1) {
						UserApp userApp = new UserApp();
						userApp.setUserId(userId);
						userApp.setAppId(oldApp.getId());
						userApp.setState(Constant.YES);
						userApp.setCreateDate(new Date());
						userApp.setUpdateDate(new Date());
						int appSize = userAppDao.insertSelective(userApp);
						if(appSize==0) {	
							return BaseDTO.ERROR;
						} 
					} 
				} else {// 解绑用户与应用关联
					if(oldApp.getAppCount() != null && oldApp.getAppCount() != 0) {
						app.setAppCount(oldApp.getAppCount() - 1);
					}
					app.setUpdateDate(new Date());
					int size = appDao.updateByPrimaryKeySelective(app);
					if(size == 0) {
						return BaseDTO.ERROR;
					}
					if(isExist > 0) {
						userAppDao.removeUserAppBinding(params);
					}
				}
			}
            return BaseDTO.SUCCESS;
		} else {
			return BaseDTO.ERROR;
		}
	}

	/**
	 * 发布新应用
	 */
	@Override
	public ResultDTO publish(HttpServletRequest request, DataGridModel params, HttpSession session) {
		ResultDTO resultDto = new ResultDTO();
        try {
			Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			if (orgObj == null) {
				resultDto.type = BaseDTO.WARNING;
				resultDto.message = messageSourceService.getMessage("web.institution.application.publish.failed.lable",null, LocaleContextHolder.getLocale());
				return resultDto;
			}
			Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (obj == null) {
				resultDto.type = BaseDTO.DANGER;
				resultDto.message = messageSourceService.getMessage("web.institution.application.publish.failed.lable",null, LocaleContextHolder.getLocale());
				return resultDto;
			}
			ManagerModel manager = (ManagerModel) obj;
			OrganizationModel organization = (OrganizationModel)orgObj;
			Object appVersion = params.getParams().get("appVersion");
			Object apkFile =  params.getParams().get("apkFile");
			Object icon =  params.getParams().get("icon");
			Object id =  params.getParams().get("id");
	
			App app = new App();
			app.setOrgId(organization.getId());
	        if(appVersion!=null&&!Constant.EMPTY_STR.equals(appVersion)){
	        	app.setAppVersion(String.valueOf(appVersion));
	        }
	        if(apkFile!=null&&!Constant.EMPTY_STR.equals(apkFile)){
	        	app.setApkFile(String.valueOf(apkFile));
	        }
	        if(icon!=null&&!Constant.EMPTY_STR.equals(icon)){
	        	app.setIconPath(String.valueOf(icon));
	        }
	        if(id!=null&&!Constant.EMPTY_STR.equals(id)){
	        	app.setId(Integer.valueOf(String.valueOf(id)));
	        }
	        app.setCreateUser(manager.getId());
	        app.setUpdateUser(manager.getId());
	        
	/*		String sourcePath = CommUtil.FILE_SAVE_PATH + app.getApkFile();
	        String destPath = CommUtil.FILE_SAVE_PATH + "app-debug.apk";
	    	File oldFile = new File(sourcePath);
	    	File newFile = new File(destPath);
	        oldFile.renameTo(newFile);
	    	oldFile.delete();
	        app.setApkFile("app-debug.apk");*/
	        // 获取文件大小
	        String fileSize = CommUtil.getFileSize(CommUtil.FILE_SAVE_PATH+app.getApkFile());
		    app.setFileSize(fileSize);
	        int size = appDao.updateByPrimaryKeySelective(app);
	        if(size==0){
			   resultDto.message = messageSourceService.getMessage("web.institution.application.publish.failed.lable",null, LocaleContextHolder.getLocale());
			   resultDto.type = BaseDTO.DANGER;
	        }
		 } catch (IOException e) {
			logger.error("release new application failed,error message is "+e.getMessage());
		 }
		 resultDto.message = messageSourceService.getMessage("web.institution.application.publish.success.lable",null, LocaleContextHolder.getLocale());
	     resultDto.type = BaseDTO.SUCCESS;
		 return resultDto;
	}
	// 推送应用
	public void mqPushliser(List<Integer> userIdList){
		if(userIdList.size()>0){
			HashMap<String, String>  hashMap = new HashMap<String,String>();
			hashMap.put("pushApp", BaseDTO.SUCCESS);
			for(Integer userId:userIdList){
				String topic = String.valueOf(userId);
				MqProducerThread thread = new MqProducerThread(topic, null, null, 2, hashMap);
				taskExecutor.execute(thread);
			}
		}
    }

	@Override
	public Page getApplicationDistributeList(HttpServletRequest request, HttpSession session, Integer begin,
			Integer num) {
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		OrganizationModel organization = (OrganizationModel)orgObj;
		Page p = new Page();
		DataGridModel params = new DataGridModel();
		params.setBegin(begin);
		params.setNum(num);
		params.getParams().put("orgId", organization.getId());
		String releaseType = request.getParameter("releaseType");
		String appName = request.getParameter("appName");
		String appState = request.getParameter("appState");
		if(StringUtil.isNotBlank(releaseType)){
			params.getParams().put("releaseType", releaseType);
		}
		if(StringUtil.isNotBlank(appName)){
			params.getParams().put("appName", appName);
		}
		if(StringUtil.isNotBlank(appState)){
			params.getParams().put("appState", appState);
		}
		List<App> list = appDao.getApplicationListByParams(params);
		List<App> newList = new ArrayList<App>();
		DataGridModel params2 = new DataGridModel();
		for (App app : list) {
			params2.getParams().put("appId", app.getId());
			params2.getParams().put("orgId", organization.getId());
			// 获取该应用所授权的用户，如果授权用户为0，则未授权人为0
		    List<Integer> userList = appDao.selectUserIdsByAppId(params2);	
		    if(userList==null||userList.size()==0){
		    	app.setNotAuthCount(0);
		    	newList.add(app);
		    } else {
		    	int size = userList.size();
		    	List<Integer> uList = new ArrayList<>();
		    	for(UserModel user:app.getUserList()){
		    		uList.add(user.getId());
		    	}
		    	// retainAll取的是交集，交集的部门是在授权人数里面存在，
		    	// 减去交集就是未授权设备数，如果不存在授权人数就是未授权设备数目
		    	userList.retainAll(uList);
		        app.setNotAuthCount(size-userList.size());
		    }
		}
		int total = appDao.getApplicationCountByParams(params);
		p.setData(list);
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	/**
	 * 保存ios应用
	 * @param request:请求
	 * @param params：参数
	 * @param session:会话
	 * @return 返回操作结果
	 */
	@Override
	public ResultDTO saveIosApp(HttpServletRequest request, DataGridModel params, HttpSession session) {
		ResultDTO resultDto = new ResultDTO();
        try {
			Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			if (orgObj == null) {
				resultDto.type = BaseDTO.WARNING;
				resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
				return resultDto;
			}
			Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (obj == null) {
				resultDto.type = BaseDTO.DANGER;
				resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
				return resultDto;
			}
			ManagerModel manager = (ManagerModel) obj;
			OrganizationModel organization = (OrganizationModel)orgObj;
			Object releaseType = params.getParams().get("releaseType");
			Object iosAppId = params.getParams().get("iosAppId");
			Object iosIconPath = params.getParams().get("iosIconPath");
			Object iosAppName = params.getParams().get("iosAppName");
			Object iosAppVersion = params.getParams().get("iosAppVersion");
			Object iosAuthorName =  params.getParams().get("iosAuthorName");
			Object iosAppDescription =  params.getParams().get("iosAppDescription");
			Object iosFileSizeBytes = params.getParams().get("iosFileSizeBytes");
			Object iosTrackId = params.getParams().get("iosTrackId");
			Object iosFormattedPrice = params.getParams().get("iosFormattedPrice");
			App app = new App();
			app.setOrgId(organization.getId());
	        if(releaseType!=null&&!Constant.EMPTY_STR.equals(releaseType)){
	        	app.setReleaseType(String.valueOf(releaseType));
	        }
	        if(iosAppId!=null&&!Constant.EMPTY_STR.equals(iosAppId)){
	        	app.setAppId(String.valueOf(iosAppId));
	        }
	        if(iosIconPath!=null&&!Constant.EMPTY_STR.equals(iosIconPath)){
	        	app.setIconPath(String.valueOf(iosIconPath));
	        }
	        if(iosAppName!=null&&!Constant.EMPTY_STR.equals(iosAppName)){
	        	app.setAppName(String.valueOf(iosAppName));
	        }
	        if(iosAppDescription!=null&&!Constant.EMPTY_STR.equals(iosAppDescription)){
	        	app.setAppDescription(String.valueOf(iosAppDescription));
	        }
	        if(iosAppVersion!=null&&!Constant.EMPTY_STR.equals(iosAppVersion)){
	        	app.setAppVersion(String.valueOf(iosAppVersion));
	        }
	        if(iosAuthorName!=null&&!Constant.EMPTY_STR.equals(iosAuthorName)){
	        	app.setAuthor(String.valueOf(iosAuthorName));
	        }
	        if(iosFileSizeBytes!=null&&!Constant.EMPTY_STR.equals(iosFileSizeBytes)){
	        	app.setFileSize(String.valueOf(iosFileSizeBytes));
	        }
	        if(iosTrackId!=null&&!Constant.EMPTY_STR.equals(iosTrackId)){
	        	app.setTrackId(String.valueOf(iosTrackId));
	        }
	        if(iosFormattedPrice!=null&&!Constant.EMPTY_STR.equals(iosFormattedPrice)){
	        	app.setFormattedPrice(String.valueOf(iosFormattedPrice));
	        }
	        app.setAppCount(0);
	        app.setState(Constant.YES);
	        app.setCreateDate(new Date());
	        app.setUpdateDate(new Date());
	        app.setCreateUser(manager.getId());
	        app.setUpdateUser(manager.getId());
	        int size = appDao.insertSelective(app);
	        if(size==0){
			   resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
			   resultDto.type = BaseDTO.DANGER;
	        }
		 } catch (Exception e) {
			logger.error("Application release save failed,error message is "+e.getMessage());
		 }
		 resultDto.message = messageSourceService.getMessage("web.institution.application.save.success.lable",null, LocaleContextHolder.getLocale());
	     resultDto.type = BaseDTO.SUCCESS;
		 return resultDto;
	}

	/**
	 * 安装应用
	 */
	@Override
	public DeviceResultModel<String> installApplication(HttpServletRequest request) {
	    DeviceResultModel<String> result = new DeviceResultModel<String>();
		String userId = request.getParameter("userId");
		// app表的主键id命名为appKey
		String appId = request.getParameter("appKey");
		if(StringUtil.isNotBlank(userId)&&StringUtil.isNotBlank(appId)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("appId", appId);
			List<String> cmdList = new ArrayList<String>();
			cmdList.add(PlistUtil.RequestType.InstallApplication);
			map.put("cmds", cmdList);
			String status = abstractIosPush.nofity(map);
			if(BaseDTO.ERROR.equals(status)){
				result.setStatus(400);
				result.setMsg(messageSourceService.getMessage("web.institution.application.install.failed",null, LocaleContextHolder.getLocale()));
				return result;
			}
			App app = appDao.selectByPrimaryKey(Integer.valueOf(appId));
			if(app != null) {
				app.setAppCount(app.getAppCount() + 1);
				app.setUpdateDate(new Date());
				int size = appDao.updateByPrimaryKeySelective(app);
				if(size == 0) {
					result.setStatus(400);
					result.setMsg(messageSourceService.getMessage("web.institution.application.install.failed",null, LocaleContextHolder.getLocale()));
					return result;
				}
				DataGridModel params = new DataGridModel();
				params.getParams().put("userId", userId);
				params.getParams().put("appId", appId);
				int isExist = userAppDao.selectUserAppSizeById(params);
				if(isExist < 1) {
					UserApp userApp = new UserApp();
					userApp.setUserId(Integer.valueOf(userId));
					userApp.setAppId(app.getId());
					userApp.setState(Constant.YES);
					userApp.setCreateDate(new Date());
					userApp.setUpdateDate(new Date());
					int appSize = userAppDao.insertSelective(userApp);
					if(appSize==0) {	
						result.setStatus(400);
						result.setMsg(messageSourceService.getMessage("web.institution.application.install.failed",null, LocaleContextHolder.getLocale()));
						return result;
					} 
				}
			}
		} else {
			result.setStatus(400);
			result.setMsg(messageSourceService.getMessage("web.institution.application.params.failed",null, LocaleContextHolder.getLocale()));
			return result;
		}
		result.setStatus(200);
		result.setMsg(messageSourceService.getMessage("web.institution.application.install.success",null, LocaleContextHolder.getLocale()));
		return result;
	}

	/**
	 * 卸载应用
	 */
	@Override
	public DeviceResultModel<String> uninstallApplication(HttpServletRequest request) {
	    DeviceResultModel<String> result = new DeviceResultModel<String>();
		String userId = request.getParameter("userId");
		String appId = request.getParameter("appKey");
		if(StringUtil.isNotBlank(userId)&&StringUtil.isNotBlank(appId)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("appId", appId);
			List<String> cmdList = new ArrayList<String>();
			cmdList.add(PlistUtil.RequestType.RemoveApplication);
			map.put("cmds", cmdList);
			String status = abstractIosPush2.nofity(map);
			if(BaseDTO.ERROR.equals(status)){
				result.setStatus(400);
				result.setMsg(messageSourceService.getMessage("web.institution.application.install.failed",null, LocaleContextHolder.getLocale()));
				return result;
			} 
         	App app = appDao.selectByPrimaryKey(Integer.valueOf(appId));
			if(app!=null&&app.getAppCount()!=0){
				app.setAppCount(app.getAppCount() - 1);
				int size = appDao.updateByPrimaryKeySelective(app);
				if(size==0){
					result.setStatus(400);
					result.setMsg(messageSourceService.getMessage("web.institution.application.uninstall.success",null, LocaleContextHolder.getLocale()));
					return result;
				}
			}
			DataGridModel params = new DataGridModel();
			params.getParams().put("userId", userId);
			params.getParams().put("appId", appId);
			int isExist = userAppDao.selectUserAppSizeById(params);
			if(isExist > 0) {
				userAppDao.removeUserAppBinding(params);
			}
			
		}
		result.setStatus(200);
		result.setMsg(messageSourceService.getMessage("web.institution.application.install.success",null, LocaleContextHolder.getLocale()));
		return result;
	}
}