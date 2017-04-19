package com.softtek.mdm.web.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ClientManagerModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.ClientManagerService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.status.TipKey;
import com.softtek.mdm.util.AppPackageUtil;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.FileUtils;
import com.softtek.mdm.util.ZXingCodeUtil;

@Controller
@RequestMapping(value = "/admin/clientManager")
public class ClientManagerAdminController{

	@Autowired
	private ClientManagerService clientManagerService;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("iosPackageAppUpdateNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Link(family = "admin", label = "web.admin.clientmanager.manager.label")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		List<OrganizationModel> list = clientManagerService.queryAllOrg();
		model.addAttribute("list", list);
		return "admin/clientManager/index";
	}

	/**
	 * 上传APK或者IPA文件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadZip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadZip(HttpServletRequest request,MultipartFile clientfile) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(clientfile==null){
			return map;
		}
		//保存文件的根目录
		String storePath=CommUtil.APP_NEW_PATH;
		String fileName=StringUtils.trimToEmpty(clientfile.getOriginalFilename());
		Boolean isApk=null;
		String fileTempName=CommUtil.generate32UUID();
		//文件后缀
		if(AppPackageUtil.isAPK(fileName)){
			isApk=true;
			fileTempName+=".apk";
			storePath=String.format("%sapk/%s", storePath,fileTempName);
		}else if(AppPackageUtil.isIPA(fileName)){
			isApk=false;
			fileTempName+=".ipa";
			storePath=String.format("%sipa/%s", storePath,fileTempName);
		}else{
			return map;
		}
		
		FileUtils.saveUploadFile(clientfile.getInputStream(), storePath);
		
		Map<String, Object> infoMap=new HashMap<String, Object>();
		// 解析文件
		if(isApk==Boolean.TRUE){
			infoMap=AppPackageUtil.extratAPK(storePath);
		}else if(isApk==Boolean.FALSE){
			infoMap=AppPackageUtil.extratIPA(storePath);
		}
		
		ClientManagerModel clientManger = new ClientManagerModel();
		clientManger.setClientIdName(String.valueOf(infoMap.get(TipKey.TIP_PACKAGENAME.name())));
		clientManger.setClientAppName(String.valueOf(infoMap.get(TipKey.TIP_LABEL.name())));
		clientManger.setClientVersion(String.valueOf(infoMap.get(TipKey.TIP_VERSION.name())));
		
		map.put("clientManger", clientManger);
		//包存放的名称
		map.put("temp", fileTempName);
		return map;
	}

	/**
	 * 发布本地客户端包，确定提交
	 * @param request
	 * @param session
	 * @param clientManager
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/insertClientManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertClientManager(HttpServletRequest request,HttpSession session, ClientManagerModel clientManager)
			throws IOException {
		//安装包文件的名称含后缀
		String temp=StringUtils.trimToEmpty(request.getParameter("temp"));
		//安装包的存放路径
		if(temp.length()==0){
			return new HashMap<>();
		}
		
		//文件后缀:ipa/apk
		String type=temp.substring(temp.lastIndexOf(".")+1).toLowerCase();
		//获取上传文件存储的绝对位置.../../xxx.apk或者.ipa
		String storePath=String.format("%s%s/%s", StringUtils.trimToEmpty(CommUtil.APP_NEW_PATH),type,temp);
		String downloadUrl=null,path=null;
		
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		
		String plitPath=FileUtils.getWebInfoPath()+"/classes/manifest.plist";
		
		/**
		 * 用于做强制推送
		 */
		Map<String, Object> updateMap=new HashMap<String,Object>();
		updateMap.put("belongOrgs", StringUtils.trimToNull(clientManager.getBelongOrg()));
		updateMap.put("version", clientManager.getClientVersion());
		
		if(StringUtils.isNotEmpty(clientManager.getBelongOrg())){
			//../../app/ipa|apk/clientManaager/orgs/xxx.apk|ipa
			path = String.format("%sapp/%s/clientManager/orgs/%s", CommUtil.APP_NEW_PATH,type,temp);
			FileUtils.copy(storePath, path);
			
			//e.g.:.../../xxx.png
			String imgPath=String.format("%spng", path.substring(0, path.lastIndexOf(".")+1));
			String downloadLink =String.format("%sapp/%s/clientManager/orgs/%s", CommUtil.MANIFESTFILE_URL,type,temp);
			downloadUrl=AppPackageUtil.ipaManifestDownloadUri(plitPath, downloadLink, clientManager.getClientIdName(), clientManager.getClientVersion(), clientManager.getClientAppName(), null, null);
			ZXingCodeUtil.createQrcode(imgPath, downloadUrl);
			
			updateMap.put("downloadUrl", downloadUrl);
			
			clientManager.setImageUrl(String.format("%sapp/%s/clientManager/orgs/%s.png", CommUtil.MANIFESTFILE_URL,type,temp.substring(0, temp.lastIndexOf("."))));
		}else{
			//适用所有机构的包，则先删除原选的的适用所有机构的包
			clientManagerService.deleteAllOrgClient(manager.getId());
			
			//覆盖文件,named:unified.apk/unified-vxx.ipa
			String fileNameStr=String.format("unified-v%s.%s", clientManager.getClientVersion(),type);
			path=String.format("%sapp/%s/clientManager/company/orgAll/%s", StringUtils.trimToEmpty(CommUtil.APP_NEW_PATH),type,fileNameStr);
			FileUtils.copy(storePath, path);
			
			String imgPath=String.format("%sapp/%s/clientManager/company/orgAll/all-org.png", storePath,type);
			String downloadLink =String.format("%sapp/%s/clientManager/company/orgAll/%s", CommUtil.MANIFESTFILE_URL,type,fileNameStr);
			downloadUrl=AppPackageUtil.ipaManifestDownloadUri(plitPath, downloadLink, clientManager.getClientIdName(), clientManager.getClientVersion(), clientManager.getClientAppName(), null, null);
			ZXingCodeUtil.createQrcode(imgPath, downloadUrl);
			
			updateMap.put("downloadUrl", downloadUrl);
			
			clientManager.setImageUrl(String.format("%sapp/%s/clientManager/company/orgAll/all-org.png", CommUtil.MANIFESTFILE_URL,type));
		}
		
		clientManager.setDownloadUrl(downloadUrl);
		clientManager.setFileName(path);
		clientManager.setClientByte(CommUtil.getFileSize(path));
		clientManager.setApplyPlatform("apk".equals(type)?"android":"ipa".equals(type)?"ios":"");
		clientManager.setCreateBy(manager.getId());
		clientManager.setUpdateBy(manager.getId());
		clientManager.setSupportDevice(messageSource.getMessage("tiles.institution.client.manager.device.mobile", null,"手机", LocaleContextHolder.getLocale()));
		
		int result = clientManagerService.insertClientManager(clientManager);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		
		if(clientManager.getIsUpgrade()==(Integer)1){
			abstractIosPush.nofity(updateMap);
		}
		
		return map;
	}
	

	/**
	 * 发布本地客户端的分页查询
	 */
	@RequestMapping(value = "/queryAll")
	@ResponseBody
	public Page queryAll(Integer start, Integer length, HttpServletRequest request,HttpSession session) {
		Page page = null;
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		page = clientManagerService.queryAll(start, length,manager);
		return page;
	}

	/**
	 * 复制上传的文件到新的目录下
	 * 
	 * @param filePath
	 * @param newPath
	 * @throws IOException
	 */
	public void CopyFile(String filePath, String newPath) throws IOException {
		boolean overlay = true;
		File sourceFile = new File(filePath);
		File destFile = new File(newPath);
		 if (destFile.exists()) {  
	            // 如果目标文件存在并允许覆盖  
	            if (overlay) {  
	                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
	                new File(newPath).delete();  
	            }  
	        } else {  
	            // 如果目标文件所在目录不存在，则创建目录  
	            if (!destFile.getParentFile().exists()) {  
	                // 目标文件所在目录不存在  
	                if (!destFile.getParentFile().mkdirs()) {  
	                    // 复制文件失败：创建目标文件所在目录失败  
	                    return;  
	                }  
	            }  
	        }  
	  
		if (sourceFile.exists()) { // 源文件存在时
			@SuppressWarnings("unused")
			int bytesum = 0;
			int byteread = 0;
			InputStream inStream = new FileInputStream(filePath); // 读取原文件
			FileOutputStream outPutStream = new FileOutputStream(destFile);
			byte[] b = new byte[1024];
			while ((byteread = inStream.read(b)) != -1) {
				bytesum = +byteread;
				outPutStream.write(b, 0, byteread);
			}
			inStream.close();
			outPutStream.close();
		}
	}

	@RequestMapping(value = "/queryClientInfoById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryClientInfoById(HttpServletRequest request,String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ClientManagerModel clientManager = clientManagerService.queryClientInfoById(Integer.valueOf(id));
		String orgIds = clientManager.getBelongOrg();
		String orgName = "";
		String[] orgIdArr = orgIds.split(",");
		List<OrganizationModel> orgList = clientManagerService.queryAllOrg();
		if (orgIds.equals("") || orgIds == null) {
			String allOrg = messageSource.getMessage("tiles.institution.client.manager.all.org", null, LocaleContextHolder.getLocale());
			clientManager.setBelongOrg(allOrg);
		} else {
			for (OrganizationModel organization : orgList) {
				for (int i = 0; i < orgIdArr.length; i++) {
					if (organization.getId().equals(Integer.valueOf(orgIdArr[i]))) {
						orgName += organization.getName() + ",";
					}
				}
			}
			clientManager.setBelongOrg(orgName.substring(0, orgName.length() - 1));
		}
		map.put("clientManager", clientManager);
		return map;
	}
	
	@RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteClient(HttpServletRequest request,HttpSession session,String id){
		Map<String,Object> map = new HashMap<String, Object>();
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		ClientManagerModel clientManager = new ClientManagerModel();
		clientManager.setId(Integer.valueOf(id));
		clientManager.setUpdateBy(manager.getId());
		Integer result = clientManagerService.deleteClient(clientManager);
		map.put("result", result);
		return map;
	}
}
