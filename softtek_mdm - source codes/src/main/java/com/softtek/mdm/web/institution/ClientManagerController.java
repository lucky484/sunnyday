package com.softtek.mdm.web.institution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.softtek.apkUtil.entity.ApkInfo;
import com.softtek.apkUtil.utils.ApkUtil;
import com.softtek.apkUtil.utils.IconUtil;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ClientManagerModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.ClientManagerService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.ImageUtil;
import com.softtek.mdm.util.MatrixToImageWriter;

@Controller
@RequestMapping(value = "/institution/clientManager")
public class ClientManagerController {

	@Autowired
	private ClientManagerService clientManagerService;
	
	@Autowired
	private MessageSource messageSource;

	private String filePath;

	@Link(family = "institution", label = "institution.clientConfig.index.label", parent = "institution.home.index.label", belong = "institution.systemManager.index.label")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model,HttpSession session) {
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<OrganizationModel> list = clientManagerService.queryOrgByOrgId(manager.getId());
		model.addAttribute("list", list);
		return "institution/clientManager/index";
	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadZip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadZip(HttpServletRequest request) throws Exception {
		filePath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		// 将文件上传到WEB-INF/resources/download下
		String savePath = CommUtil.APP_NEW_PATH + "apk" + "/";
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			// 如果不存在，创建目录
			file.mkdir();
		}
		// 上传文件
		String fileName = ImageUtil.getUploadFilePath(request, savePath);
		filePath = savePath + fileName;
		String apkFileLocation = filePath;
		// 解析文件
		ApkInfo apkInfo = new ApkUtil().getApkInfo(apkFileLocation, CommUtil.AAPT_PATH);
		ClientManagerModel clientManger = new ClientManagerModel();
		clientManger.setClientIdName(apkInfo.getPackageName());
		clientManger.setClientAppName(apkInfo.getApplicationLable());
		clientManger.setClientVersion(apkInfo.getVersionName());
		clientManger.setSignatureCertificate(IconUtil.getPublicKey(apkFileLocation));
		map.put("clientManger", clientManger);
		return map;
	}

	@RequestMapping(value = "/insertClientManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertClientManager(HttpServletRequest request,HttpSession session, ClientManagerModel clientManager)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<EncodeHintType,Integer> hints = new HashMap<EncodeHintType, Integer>();
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		File file = null;
		String newName = "";
		String newPath = "";
		String orgCodes = "";
		String path = "";
		String ordId = clientManager.getBelongOrg();
		String[] orgArr = ordId.split(",");
		List<String> list = Arrays.asList(orgArr);
		StringBuilder builder=new StringBuilder();
		for(String s : list){	    
			//查询机构编码
			if(StringUtils.isNotEmpty(s)){
				builder.append(clientManagerService.queryOrgCode(Integer.valueOf(s)));
				builder.append("-");
			}
			orgCodes =builder.toString();
		}
		String orgCode = orgCodes.substring(0,orgCodes.length()-1);
		path = CommUtil.APP_NEW_PATH + "android/apk/clientManager/orgs/" + orgCode;
		//根据不同的机构创建不同的目录，生成不同的下载地址
		file = new File(path);
		if(!file.exists() && !file.isDirectory()){
			//如果不存在，创建目录
			file.mkdir();
		}
		newName = clientManager.getClientAppName().substring(0,clientManager.getClientAppName().lastIndexOf("."));
		newPath = path + "/" + newName + "-" + clientManager.getClientVersion() + ".apk"; //复制的源文件的新路径
		CopyFile(filePath,newPath);
		//根据不同机构生成对应的二维码
		MatrixToImageWriter matrix = new MatrixToImageWriter();
		String path1 = path + "/";
		File newFile = new File(path1 + sdf.format(new Date()) + ".png");
		String downloadUrl = CommUtil.DOWNLOAD_URL + "android/apk/clientManager/orgs/" + orgCode + "/" + newName + "-" + clientManager.getClientVersion() + ".apk";
		matrix.encode(downloadUrl, newFile, BarcodeFormat.QR_CODE, 200, 200, hints);
		clientManager.setDownloadUrl(downloadUrl);
		clientManager.setFileName(newName);
		clientManager.setImageUrl(CommUtil.DOWNLOAD_URL + "android/apk/clientManager/orgs/" + orgCode + "/" + sdf.format(new Date()) + ".png");
		clientManager.setClientByte(CommUtil.getFileSize(newPath));
	    String suffix = clientManager.getClientAppName().substring(clientManager.getClientAppName().lastIndexOf("."),
				clientManager.getClientAppName().length());
		if (suffix.equalsIgnoreCase(".apk")) {
			clientManager.setApplyPlatform("Android");
		} else if (suffix.equalsIgnoreCase(".ipa")) {
			clientManager.setApplyPlatform("iOS");
		} else {
			clientManager.setApplyPlatform("");
		}
		String deviceType = messageSource.getMessage("tiles.institution.client.manager.device.mobile", null, LocaleContextHolder.getLocale());
		clientManager.setSupportDevice(deviceType);
		clientManager.setCreateBy(manager.getId());
		clientManager.setUpdateBy(manager.getId());
		Integer result = clientManagerService.insertClientManager(clientManager);
		map.put("result", result);
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
			InputStream inStream = new FileInputStream(filePath); // 读取原文件
			FileOutputStream outPutStream = new FileOutputStream(destFile);
			byte[] b = new byte[1024];
			int byteread = 0;
			while ((byteread = inStream.read(b)) != -1) {
				outPutStream.write(b, 0, byteread);
			}
			outPutStream.close();
			inStream.close();
		}
	}

	@RequestMapping(value = "/queryClientInfoById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryClientInfoById(HttpServletRequest request, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ClientManagerModel clientManager = clientManagerService.queryClientInfoById(Integer.valueOf(id));
		String orgIds = clientManager.getBelongOrg();
		String orgName = "";
		String[] orgIdArr = orgIds.split(",");
		List<OrganizationModel> orgList = clientManagerService.queryAllOrg();
		if (StringUtils.isNotEmpty(orgIds)) {
			String allOrg = messageSource.getMessage("tiles.institution.client.manager.all.org", null, LocaleContextHolder.getLocale());
			clientManager.setBelongOrg(allOrg);
		} else {
			if(CollectionUtils.isNotEmpty(orgList)){
				StringBuilder builder=new StringBuilder();
				for (OrganizationModel organization : orgList) {
					for (int i = 0; i < orgIdArr.length; i++) {
						if(StringUtils.isNotEmpty(orgIdArr[i])){
							if (organization.getId().equals(Integer.valueOf(orgIdArr[i]))) {
								builder.append(organization.getName());
								builder.append(",");
							}
						}
					}
				}
				orgName=builder.toString();
				if(StringUtils.isNotEmpty(orgName)&&orgName.endsWith(",")){
					orgName=orgName.substring(0, orgName.length() - 1);
				}
				clientManager.setBelongOrg(orgName);
			}
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
