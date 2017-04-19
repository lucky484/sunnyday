package com.softtek.mdm.web.institution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softtek.apkUtil.entity.ApkInfo;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.annotation.Token;
import com.softtek.mdm.bean.AppListBean;
import com.softtek.mdm.dao.AppDao;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.App;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.ApplicationService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.ApkFileUtil;
import com.softtek.mdm.util.Appstore;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.ImageUtil;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 应用管理
 * @author jane.hui 
 * url
 */
@Controller
@RequestMapping(value="/institution/application")
public class ApplicationController {

	private static Logger logger = Logger.getLogger(ApplicationController.class);
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private MessageSource messageSourceService;
	
	@Autowired
	private AppDao appDao;
	 /**
	  * 应用管理首页
	  * @param request
	  * @return
	  */
	 @Token(needSaveToken=true)
	 @Link(family="institution",label="web.institution.application.index.label",parent="web.institution.homecontroller.index.link.label",belong="web.institution.application.index.link.belong")
	 @RequestMapping(method=RequestMethod.GET)
	 public String index(HttpServletRequest request,HttpSession session,Model model){
		 List<App> appList = applicationService.getApplicationByOrgId(request,session);
		 model.addAttribute("appList",appList);
         return "institution/application/index";
	 }
	 
	 /**
	  * 保存应用发布
	  * @param request
	  * @param params
	  * @return
	  */
	 @Token(needRemoveToken=true)
	 @Log(operateType="web.institution.application.operatetype.add",operateContent="web.institution.application.operatecontent.add",args={"params[iosAppName]"})
	 @RequestMapping(value="/saveIosApp")
	 @ResponseBody
	 public Object saveIosApp(HttpServletRequest request,DataGridModel params,HttpSession session){
		 ResultDTO resultDto = new ResultDTO();
		 try {
			 return applicationService.saveIosApp(request, params,session); 
		 } catch(Exception e){
			 logger.error("something wrong when add application publish,exception message is " + e.getMessage());
			 resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
		     resultDto.type = BaseDTO.DANGER;
			 return resultDto;
		 }
	 }
	 
	 /**
	  * 保存应用发布
	  * @param request
	  * @param params
	  * @return
	  */
	 @Token(needRemoveToken=true)
	 @Log(operateType="web.institution.application.operatetype.add",operateContent="web.institution.application.operatecontent.add",args={"params[appName]"})
	 @RequestMapping(value="/save")
	 @ResponseBody
	 public Object save(HttpServletRequest request,DataGridModel params,HttpSession session){
		 ResultDTO resultDto = new ResultDTO();
		 try {
			 return applicationService.save(request, params,session); 
		 } catch(Exception e){
			 logger.error("something wrong when add application publish,exception message is " + e.getMessage());
			 resultDto.message = messageSourceService.getMessage("web.institution.application.save.failed.lable",null, LocaleContextHolder.getLocale());
		     resultDto.type = BaseDTO.DANGER;
			 return resultDto;
		 }
	 }
	 
	 /**
	  * 新发布应用保存
	  * @param request
	  * @param params
	  * @return
	  */
	 @Log(operateType="web.institution.application.operatetype.push",operateContent="web.institution.application.operatecontent.push",args={"params[appName]"})
	 @RequestMapping(value="/publish")
	 @ResponseBody
	 public Object publish(HttpServletRequest request,DataGridModel params,HttpSession session){
		 ResultDTO resultDto = new ResultDTO();
		 try {
			 return applicationService.publish(request, params,session); 
		 } catch(Exception e){
			 logger.error("something wrong when add application publish,exception message is " + e.getMessage());
			 resultDto.message = messageSourceService.getMessage("web.institution.application.publish.failed.lable",null, LocaleContextHolder.getLocale());
		     resultDto.type = BaseDTO.DANGER;
			 return resultDto;
		 }
	 }
	 
	/**
	 * 获取设备策略List数据
	 * @param params
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryAll")
	@ResponseBody
	public Page queryAll(Integer start, Integer length,HttpSession session,HttpServletRequest request){
        return applicationService.getApplicationList(request, session, start, length);
	} 
	 
	/**
	 * 获取应用分发列表数据
	 * @param start
	 * @param length
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryApplicationAll")
	@ResponseBody
	public Page queryApplicationAll(Integer start, Integer length,HttpSession session,HttpServletRequest request){
        return applicationService.getApplicationDistributeList(request, session, start, length);
	} 
	
	/**
	 * 检索设备
	 * @param start
	 * @param length
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryDevice")
	@ResponseBody
	public Page queryDevice(Integer start, Integer length,HttpSession session,HttpServletRequest request){
		return applicationService.queryDevice(request, session, start, length);
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param files
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
    @Log(operateType="web.institution.application.operatetype.upload",operateContent="web.institution.application.operatecontent.upload",args={"appName"})
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(HttpServletRequest request,HttpSession session) throws IllegalStateException, IOException{
		OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ResultDTO resultDto = new ResultDTO();
		App app = new App();
		try {
			// 判读上传文件是否存在
			File file = new File(CommUtil.FILE_SAVE_PATH);
			if(!file.exists()&&!file.isDirectory()){
				// 创建APK保存路径
				file.mkdir();
			}
			// 判断上传临时文件是否存在
			File tempFile = new File(CommUtil.TEMP_PATH);
			if(!tempFile.exists()&&!tempFile.isDirectory()){
				// 创建APK保存路径
				tempFile.mkdir();
			}
            String id = request.getParameter("id");
            Integer intId = null;
            App oldApp = null;
            if(StringUtil.isNotBlank(id)){
               	intId = Integer.valueOf(id);
            	oldApp = new App();
            	oldApp = appDao.selectByPrimaryKey(intId);
            }
            // 上传图片
			String fileName = ImageUtil.getUploadAPKFilePath(request,intId);
			String[] files =  fileName.split(",");
			app.setApkFile(files[1]);
			String apkFile = Constant.EMPTY_STR;
			apkFile = CommUtil.TEMP_PATH+files[0];	
            ApkInfo apkInfo = ApkFileUtil.ApkInfo(apkFile);
            request.setAttribute("appName", apkInfo.getVersionName());
     	   // 当发布新版本
     	   if(oldApp!=null&&apkInfo!=null){
     		   if(!oldApp.getAppId().equals(apkInfo.getPackageName())){
     			   resultDto.type=BaseDTO.WARNING;
     			   resultDto.message = messageSourceService.getMessage("web.institution.application.compare.resultdto.message.failed",null, LocaleContextHolder.getLocale());
     			   return resultDto;
     		   }
     		   // 比较新发布版本是否高于旧版本
     		   boolean result = CommUtil.compareArray(oldApp.getAppVersion(), apkInfo.getVersionName());
     		   String [] args = new String[1];
     		   args[0] = apkInfo.getVersionName();
     	       if(!result){
     	    	   resultDto.type=BaseDTO.WARNING;
     	    	   resultDto.message = messageSourceService.getMessage("web.institution.application.upload.version.resultdto.message.failed",args, LocaleContextHolder.getLocale());
     	    	   resultDto.result = apkInfo.getVersionName();
     	    	   return resultDto;
     	       }
     	   }
            // 判断上传AppId是否存在，如果存在则不能上传，如果不存在则可以上传
            boolean isExists = applicationService.exists(apkInfo.getPackageName(), intId, organization.getId());
            if(isExists){
  			     resultDto.message = messageSourceService.getMessage("web.institution.application.exists.resultdto.message.failed",null, LocaleContextHolder.getLocale());
   		         resultDto.type = BaseDTO.DANGER;
	   			 return resultDto;
            }
            // 新发布应用,发布时,先将文件名已temp开头,等文件相关验证后，再将文件重新
            String sourcePath = CommUtil.TEMP_PATH + files[0];
            String destPath = CommUtil.FILE_SAVE_PATH +files[1];
            CommUtil.copy(sourcePath, destPath);
            app.setApkFile(files[1]);
           if(StringUtil.isNotBlank(id)){
                app.setId(oldApp.getId());
            }
            // 重命名apk文件名
            app.setAppId(apkInfo.getPackageName());
            app.setAppName(apkInfo.getApplicationLable());
            app.setAppVersion(apkInfo.getVersionName());
            app.setIconPath(CommUtil.DISPLAY_ICON_Path + Constant.getModule.APPLICATION + Constant.SplitSymbol.SLASH + apkInfo.getApplicationIcon());
            app.setIcon(apkInfo.getApplicationIcon());
            app.setSignatureCertificate(apkInfo.getSignatureCertificate());
            resultDto.result = app;
		} catch(Exception e){
			 logger.error("something wrong when upload file,exception message is " + e.getMessage());
			 resultDto.message = messageSourceService.getMessage("web.institution.application.uploadfile.resultdto.message.failed",null, LocaleContextHolder.getLocale());;
		     resultDto.type = BaseDTO.DANGER;
			 return resultDto;
		}
		 resultDto.message = messageSourceService.getMessage("web.institution.application.uploadfile.resultdto.message.success",null, LocaleContextHolder.getLocale());;
	     resultDto.type = BaseDTO.SUCCESS;
	     return resultDto;
	}
    
	/**
	 * 删除应用
	 * @param request
	 * @return
	 */
    @Log(operateType="web.institution.application.operatetype.delete",operateContent="web.institution.application.operatecontent.delete",args={"appName"})
    @RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = applicationService.delete(request);
		} catch(Exception e){
			logger.error("something wrong when delete application by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.application.delete.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	
	/**
	 * 上架
	 * @param request
	 * @return
	 */
    @Log(operateType="web.institution.application.operatetype.onshelf",operateContent="web.institution.application.operatecontent.onshelf",args={"appName"})
	@RequestMapping(value="/changeOnState")
	@ResponseBody
	public Object changeOnState(HttpServletRequest request){
	    return applicationService.changeState(request);
	}
	
	/**
	 * 下架
	 * @param request
	 * @return
	 */
    @Log(operateType="web.institution.application.operatetype.offshelf",operateContent="web.institution.application.operatecontent.offshelf",args={"appName"})
	@RequestMapping(value="/changeOffState")
	@ResponseBody
	public Object changeOffState(HttpServletRequest request){
	    return applicationService.changeState(request);
	}
	
	/**
	 * 加载应用数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit(HttpServletRequest request){
	    return applicationService.edit(request);
	}
	
	/**
	 * 更新保存应用
	 * @param request
	 * @return
	 */
    @Log(operateType="web.institution.application.operatetype.update",operateContent="web.institution.application.operatecontent.update",args={"params[appName]"})
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(HttpServletRequest request,DataGridModel params,HttpSession session){
		 ResultDTO resultDto = new ResultDTO();
		 try {
			 return applicationService.update(request, params,session); 
		 } catch(Exception e){ 
			 logger.error("something wrong when add application publish,exception message is " + e.getMessage());
			 resultDto.message = messageSourceService.getMessage("web.institution.application.update.failed.lable",null, LocaleContextHolder.getLocale());
		     resultDto.type = BaseDTO.DANGER;
			 return resultDto;
		 }
	}
    
   /**
    * 加载部门虚拟组数据
    * @param request
    * @return
    */
   @RequestMapping(value="/loadDepart")
   @ResponseBody
   public Object loadDepart(HttpServletRequest request,HttpSession session){
	   return applicationService.loadDepart(request,session);
   }
   
   /**
    * 赋予应用级权限
    * @param request
    * @param params
    * @param session
    * @return
    */
   @Log(operateType="web.institution.application.operatetype.grantapp",operateContent="web.institution.application.operatecontent.grantapp",args={"params[appName]"})
   @RequestMapping(value="/grantAppAuth")
   @ResponseBody
   public Object grantAppAuth(HttpServletRequest request,DataGridModel params,HttpSession session){
	   return applicationService.grantAppAuth(request, params, session);
   }
   
   /**
    * 取消应用级授权
    * @param request
    * @param session
    * @return
    */
   @Log(operateType="web.institution.application.operatetype.cancelgrantapp",operateContent="web.institution.application.operatecontent.cancelgrantapp",args={"appName"})
   @RequestMapping(value="/cancelGrantAuth")
   @ResponseBody
   public Object cancelGrantAuth(HttpServletRequest request,HttpSession session){
	   return applicationService.cancelGrantAuth(request, session);
   }
   
   /**
    * 检索应用加载数据
    * @param request
    * @param session
    * @return
    */
   @RequestMapping(value="/searchAppliction")
   @ResponseBody
   public Object searchAppliction(HttpServletRequest request,HttpSession session){
	   return applicationService.getApplicationByOrgId(request,session);
   }
   
   /**
    * 搜索应用列表
    * @param request
    * @param session
    * @return
    */
   @RequestMapping(value="/searchAppList",method=RequestMethod.GET)
   @ResponseBody
   public Object searchAppList(HttpServletRequest request){
	   String keywords = request.getParameter("keywords");
	   if(StringUtil.isNotEmpty(keywords)){
		   try {
	           String appList = Appstore.get(keywords);
               JSONObject jsonObject = JSONObject.fromObject(appList);
       			Object results = jsonObject.get("results"); 
       			JSONArray jsonArray = JSONArray.fromObject(results);
       			JSONObject element = null;
       			Object trackName = null;
       			Object bundleId = null;
       			Object price = null;
       			Object primaryGenreName = null;
       			Object artworkUrl100 = null;
       			Object artistName = null;
       			Object fileSizeBytes = null;
       			Object version = null;
       			Object description = null;
       			Object trackId = null;
       			Object formattedPrice = null;
       			AppListBean appListBean = null;
       			List<AppListBean> list = new ArrayList<AppListBean>();
       			for (Object object : jsonArray) {
       				element = JSONObject.fromObject(object);
       			    trackName = element.get("trackName");
       			    bundleId = element.get("bundleId");
       			    price = element.get("price");
       			    primaryGenreName = element.get("primaryGenreName");
       			    artworkUrl100 = element.get("artworkUrl100");
       			    artistName = element.get("artistName");
       			    fileSizeBytes = element.get("fileSizeBytes");
       			    version = element.get("version");
       			    description = element.get("description");
       			    trackId = element.get("trackId");
       			    formattedPrice = element.get("formattedPrice");
      			    appListBean = new AppListBean();
       			    appListBean.setBundleId(bundleId==null?"0":bundleId.toString());
       			    // 包名
       			    if(trackName!=null){
       			    	String strTrackName = trackName.toString();
       			    	if(strTrackName.length()>18){
       			    		strTrackName = strTrackName.substring(0,18)+"...";
       			    	}
       			    	appListBean.setDisplayTrackName(strTrackName);
       			    }
       			    appListBean.setTrackName(trackName==null?"0":trackName.toString());
       			    // 价格
       			    appListBean.setPrice(price==null?"0":price.toString());
       			    appListBean.setPrimaryGenreName(primaryGenreName==null?"0":primaryGenreName.toString());
       			    appListBean.setArtworkUrl100(artworkUrl100==null?"0":artworkUrl100.toString());
       			    appListBean.setArtistName(artistName==null?"0":artistName.toString());
       			    appListBean.setFileSizeBytes(fileSizeBytes==null?"0":fileSizeBytes.toString());
       			    appListBean.setVersion(version==null?"0":version.toString());
       			    appListBean.setDescription(description==null?"0":description.toString());
       			    appListBean.setTrackId(trackId==null?"0":trackId.toString());
       			    appListBean.setFormattedPrice(formattedPrice==null?"0":formattedPrice.toString());
       			    list.add(appListBean);
       			}
       			return list;
		   } catch (Exception e) {
			   logger.error("search app list failed,message:"+e.getMessage());
	            return null;
		   }
	   } 
	   return null;
    }
}