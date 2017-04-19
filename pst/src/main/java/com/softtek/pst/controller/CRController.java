/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CRController.java
 * @Prject: pst
 * @Package: com.softtek.pst.controller
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 6, 2016 10:21:12 AM
 * @version: V1.0  
 */
package com.softtek.pst.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.softtek.pst.model.CRFile;
import com.softtek.pst.model.CRModel;
import com.softtek.pst.model.CommentModel;
import com.softtek.pst.model.Email;
import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.model.ProjectModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.CRFileService;
import com.softtek.pst.service.CRService;
import com.softtek.pst.service.CommentService;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.ProjectService;
import com.softtek.pst.service.UserService;
import com.softtek.pst.util.GlobalSettingsUtils;
import com.softtek.pst.util.MailSendUtil;
import com.softtek.pst.util.Page;
/**
 * @ClassName: CRController
 * @Description: CR
 * @author: light.chen
 * @date: Apr 6, 2016 10:21:12 AM
 */

@Controller
@RequestMapping("/projectsManagement/projects/Requirement")
public class CRController {

	private Logger logger = Logger.getLogger(CRController.class);

	@Autowired
	private CRService crService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private CRFileService crFileService;
	@Autowired
	private GlobalSettingsUtils globalSettingsUtils;
	@Autowired
	private LoggingService loggingService;
	
	@Autowired
    private UserService userService;

	// 进入cr列表页面
	@RequestMapping("crList")
	public ModelAndView gethead(HttpServletRequest request, long projectId) {
		request.setAttribute("url", request.getServletPath());
		ProjectModel project = projectService.getProjectById(projectId);
		return new ModelAndView("cr/crList", "project", project);
	}

	// 进入增加cr页面
	@RequestMapping("addCRList")
	public String addCRForm(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		return "cr/addCRList";
	}

	// 进入编辑cr页面
	@RequestMapping("editCRList")
	public ModelAndView editCRForm(long crId, HttpServletRequest request) {
		CRModel crm = new CRModel();
		crm = crService.getCR(crId);
		request.setAttribute("url", request.getServletPath());
		List<CRFile> fileList = crFileService.getCRFilebyCrId(crId);
		request.setAttribute("fileList", fileList);
		return new ModelAndView("cr/editCRList", "crm", crm);
	}

	// 进入cr详情页面
	@RequestMapping("viewCRList")
	public ModelAndView viewCRList(long crId, HttpServletRequest request) {
		CRModel crm = crService.getCR(crId);
		List<CommentModel> list = commentService.getCommentByCrId(crId);
		List<CRFile> fileList = crFileService.getCRFilebyCrId(crId);
		request.setAttribute("url", request.getServletPath());
		ModelAndView mav = new ModelAndView("cr/viewCRList");
		mav.addObject("fileList", fileList);
		mav.addObject("crm", crm);
		mav.addObject("list", list);
		return mav;
	}

	// 增加评论功能
	@RequestMapping(value = "addComment")
	@ResponseBody
	public Map<String, Object> addComment(String crrid, String comment, HttpServletRequest request, HttpServletResponse response) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		CommentModel cm = new CommentModel();
		cm.setComment(comment);
		cm.setCritic(user.getChineseName());
		cm.setCrId(Long.parseLong(crrid));
		Map<String, Object> result = new HashMap<>();
		if (commentService.addComment(cm) == 1) {
			// 记录日志
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			CRModel cr = crService.getCR(cm.getCrId());
			ProjectModel pm = projectService.getProjectById(cr.getProjectId());
			loggingModel.setAction(user.getChineseName() + "于 " + datetime + " 对项目【" + pm.getProjectName() + "】中的CR(" + cr.getCrDescribe() + ")进行了评论");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(5);
			loggingModel.setEventName("评论");
			loggingModel.setTables("cr_comment");
			loggingService.addLogging(loggingModel);
			result.put("success", true);
			cm.setCreateTime(new Date());
			result.put("comment", cm);
		} else {
			result.put("success", false);
		}
		return result;
	}

	// 附件下载功能
	@RequestMapping("downloadCR")
	public ResponseEntity<byte[]> download(long crFileId, HttpServletRequest request, HttpServletResponse response) {
		CRFile crf = crFileService.getCRFile(crFileId);
		String attName = crf.getFileName();
		String path = globalSettingsUtils.getFilePath() + crf.getFilePath();
		HttpHeaders headers = new HttpHeaders();
		String fileName;
		try {
			fileName = new String(attName.getBytes("UTF-8"), "iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // 为了解决中文名称乱码问题
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try {
			return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(path)), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 增加CR功能
	@RequestMapping(value = "addCR")
	@ResponseBody
	public Map<String, Object> addCR(long addProjectId, String addCRDescribe, String addEstimatedWorkload, String addPresenter, String addConfirmor, String addEstimateQuotation,
			String addConfirmQuotation, String addRemark, HttpServletResponse response, @RequestParam("file") MultipartFile[] list, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<>();
		CRModel cr = new CRModel();
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		cr.setCreatorId(user.getUserId());
		cr.setProjectId(addProjectId);
		cr.setCrDescribe(addCRDescribe);
		cr.setEstimatedWorkload(Integer.parseInt(addEstimatedWorkload));
		cr.setPresenter(addPresenter);
		cr.setConfirmor(addConfirmor);
		CRFile crf = new CRFile();
		// 报价权限处理
//		if (null == addEstimateQuotation) {
//			cr.setEstimateQuotation(0);
//		} else {
//			cr.setEstimateQuotation(Integer.parseInt(addEstimateQuotation));
//		}
		if (null == addConfirmQuotation) {
			cr.setConfirmQuotation(0);
		} else {
			cr.setConfirmQuotation(Integer.parseInt(addConfirmQuotation));
		}
		cr.setRemark(addRemark);
		long projectId = cr.getProjectId();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Path path = Paths.get(globalSettingsUtils.getFilePath() + "project/" + cr.getProjectId() + "/cr/");
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (crService.addCR(cr) == 1) {
			long crId = cr.getCrId();
			if (list == null) {
				return null;
			} else {
				for (MultipartFile file : list) {
					String fileName = file.getOriginalFilename();
					String data = (df.format(new Date())).toString();
					crf.setFileName(fileName);
					fileName = data + "_" + fileName;
					crf.setContentType(file.getContentType());
					crf.setCrId(crId);
					crf.setFileSize(file.getSize());
					crf.setFilePath("project/" + cr.getProjectId() + "/cr/" + fileName);
					crFileService.addCRFile(crf);
					try {
						FileCopyUtils.copy(file.getBytes(), new FileOutputStream(path.toString() + "/" + fileName));
					} catch (IOException e) {
						logger.info(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			// 记录日志
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			CRModel crModel = crService.getCR(crId);
			ProjectModel pm = projectService.getProjectById(cr.getProjectId());
			crModel.getCrId();
			loggingModel.setAction(user.getChineseName() + "于 " + datetime + " 为项目【" + pm.getProjectName() + "】增加了一条CR(" + crModel.getCrDescribe() + ")");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(1);
			loggingModel.setEventName("增加");
			loggingModel.setTables("cr");
			loggingService.addLogging(loggingModel);
			Email email = new Email();
			List<UserModel> emailList = userService.queryNotificationUser();
			List<String> toList = new ArrayList<String>();
			for(UserModel u : emailList){
				if(u.getEmail() != null){
					toList.add(u.getEmail());
				}
			}
			if(user.getEmail() != null){
				toList.add(user.getEmail());
			}
			HashSet<String> set = new HashSet<String>(toList);
			toList.clear();
			toList.addAll(set);
			email.setTo(toList);
			email.setSubject("CR新增");
			String text = user.getRole().getRoleDescription() + user.getChineseName() + "在" + date.format(new Date()) + "新增了项目:【" + pm.getProjectName() + "】的一条CR(" + crModel.getCrDescribe() + ")";
			email.setContent(text);
			MailSendUtil util = new MailSendUtil();
			util.sendEmail(email);
			result.put("success", true);
			result.put("projectId", projectId);
		} else {
			result.put("success", false);
			result.put("errmsg", "添加失败，请稍候再试或联系管理员");
		}
		return result;
	}

	// 删除CR功能
	@RequestMapping("deleteCR")
	@ResponseBody
	public int deteleCR(long crId, HttpServletRequest request) throws IOException, MessagingException {
		// 记录日志
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		LoggingModel loggingModel = new LoggingModel();
		loggingModel.setOperator(user.getChineseName());
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datetime = (date.format(new Date())).toString();
		CRModel crModel = crService.getCR(crId);
		ProjectModel pm = projectService.getProjectById(crModel.getProjectId());
		loggingModel.setAction(user.getChineseName() + "于 " + datetime + " 删除项目【" + pm.getProjectName() + "】的一条CR(" + crModel.getCrDescribe() + ")");
		loggingModel.setUserId(user.getUserId());
		loggingModel.setEventType(2);
		loggingModel.setEventName("删除");
		loggingModel.setTables("cr");
		int result = crService.deleteCR(crId);
		if (result == 1) {
			loggingService.addLogging(loggingModel);
			Email email = new Email();
			List<UserModel> emailList = userService.queryNotificationUser();
			List<String> toList = new ArrayList<String>();
			for(UserModel u : emailList){
				if(u.getEmail() != null){
					toList.add(u.getEmail());
				}
			}
			if(user.getEmail() != null){
				toList.add(user.getEmail());
			}
			HashSet<String> set = new HashSet<String>(toList);
			toList.clear();
			toList.addAll(set);
			email.setTo(toList);
			email.setSubject("CR删除");
			String text = user.getRole().getRoleDescription() + user.getChineseName() + "在" + date.format(new Date()) + "删除了项目:【" + pm.getProjectName() + "】的一条CR(" + crModel.getCrDescribe() + ")";
			email.setContent(text);
			MailSendUtil util = new MailSendUtil();
			util.sendEmail(email);
			logger.info("删除CR成功");
		} else {
			logger.info("删除CR失败");
		}
		return result;
	}

	// 修改CR功能
	@RequestMapping("updateCR")
	@ResponseBody
	public Map<String, Object> updaCR(CRModel cr, MultipartHttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files) throws IOException, MessagingException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();
		String content = "";
		CRModel model = crService.getCR(cr.getCrId());
		ProjectModel project = projectService.getProjectById(model.getProjectId());
		if(!model.getCrDescribe().equals(cr.getCrDescribe())){
			content += "需求描述" + ",";
		}
		if(model.getEstimatedWorkload() != cr.getEstimatedWorkload()){
			content += "预估(小时)" + ",";
		}
		if(!model.getPresenter().equals(cr.getPresenter())){
			content += "提交人" + ",";
		}
		if(!model.getConfirmor().equals(cr.getConfirmor())){
			content += "确认人" + ",";
		}
		if(model.getConfirmQuotation() != cr.getConfirmQuotation()){
			content += "报价" + ",";
		}
		if(model.getRemark() != null){
			if(!model.getRemark().equals(cr.getRemark())){
				content += "备注" + ",";
			}
		}
		model.setConfirmor(cr.getConfirmor());
		model.setConfirmQuotation(cr.getConfirmQuotation());
		model.setCrDescribe(cr.getCrDescribe());
		model.setEstimatedWorkload(cr.getEstimatedWorkload());
//		model.setEstimateQuotation(cr.getEstimateQuotation());
		model.setPresenter(cr.getPresenter());
		model.setRemark(cr.getRemark());
		int result = crService.updateCR(model);
		if (result == 1) {

			// ----原有文件处理------
			List<CRFile> fileList = crFileService.getCRFilebyCrId(cr.getCrId());
			// 将数据库中所有文件id存放在ids集合中
			List<String> ids = fileList.stream().map(f -> String.valueOf(f.getCrFileId())).collect(Collectors.toList());
			String[] existFileIdsArr = request.getParameterValues("existFileId");
			String[] existDeleteFlagArr = request.getParameterValues("deleteFlag");
			List<String> existIdsArr;
			if (existFileIdsArr != null) {
				existIdsArr = Arrays.stream(existFileIdsArr).collect(Collectors.toList());
				ids.removeAll(existIdsArr);
				// 删除重复的，保留前台已经被删除的id
				// 如果前台有删除原有文件
				if (ids.size() > 0) {
					// 将数据库中所有已在前台删除的原有附件删除
					ids.stream().forEach(i -> {
						CRFile crFile = crFileService.getCRFile(Long.valueOf(i));
						try {
							// 删除磁盘上原有文件
							Files.deleteIfExists(Paths.get(globalSettingsUtils.getFilePath() + crFile.getFilePath()));
						} catch (IOException e) {
							logger.info(e.getMessage());
						}
						crFileService.deleteCRFileById(Long.valueOf(i));
					});
				}
				// 对前台保留的原有附件进行处理
				for (int i = 0; i < existDeleteFlagArr.length; i++) {
					// 前台原有附件框用户点击了相应附件的删除按钮
					if (existDeleteFlagArr[i].equals("1")) {
						if (!existFileIdsArr[i].equals("")) {
							CRFile crFile = crFileService.getCRFile(Long.valueOf(existFileIdsArr[i]));
							try {
								// 删除磁盘上原有文件
								Files.deleteIfExists(Paths.get(globalSettingsUtils.getFilePath() + crFile.getFilePath()));
							} catch (IOException e) {
								logger.info(e.getMessage());
							}
							crFileService.deleteCRFileById(Long.valueOf(existFileIdsArr[i]));
						}
					} else {
						// 尝试获取前台原有附件的input框内容
						MultipartFile updateFile = request.getFile("file" + existFileIdsArr[i]);
						// 能获取到，说明用户对原有附件进行了修改，需要同步修改数据库中的附件
						if (updateFile != null && !updateFile.isEmpty()) {
							String fileName = df.format(new Date()) + "_" + updateFile.getOriginalFilename();
							CRFile file = crFileService.getCRFile(Long.valueOf(existFileIdsArr[i]));
							try {
								// 删除磁盘上原有文件
								Files.deleteIfExists(Paths.get(globalSettingsUtils.getFilePath() + file.getFilePath()));
							} catch (IOException e) {
								logger.info(e.getMessage());
							}
							file.setContentType(updateFile.getContentType());
							file.setFileName(updateFile.getOriginalFilename());
							file.setFilePath("project/" + cr.getProjectId() + "/cr/" + fileName);
							file.setFileSize(updateFile.getSize());
							file.setUpdateDate(new Date());
							crFileService.updateCRFile(file);
							try {
								Path path = Paths.get(globalSettingsUtils.getFilePath() + "project/" + cr.getProjectId() + "/cr/");
								// 如果文件夹不存在则创建
								if (!Files.exists(path)) {
									Files.createDirectories(path);
								}
								FileCopyUtils.copy(updateFile.getBytes(), new FileOutputStream(path.toString() + "/" + fileName));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			// 用户新增的附件处理
			Arrays.stream(files).forEach(f -> {
				String fileName = df.format(new Date()) + "_" + f.getOriginalFilename();
				CRFile file = new CRFile();
				file.setContentType(f.getContentType());
				file.setFileName(f.getOriginalFilename());
				file.setFilePath("project/" + cr.getProjectId() + "/cr/" + fileName);
				file.setFileSize(f.getSize());
				file.setCrId(cr.getCrId());
				crFileService.addCRFile(file);
				try {
					Path path = Paths.get(globalSettingsUtils.getFilePath() + "project/" + cr.getProjectId() + "/cr/");
					// 如果文件夹不存在则创建
					if (!Files.exists(path)) {
						Files.createDirectories(path);
					}
					FileCopyUtils.copy(f.getBytes(), new FileOutputStream(path.toString() + "/" + fileName));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			ProjectModel pm = projectService.getProjectById(cr.getProjectId());
			loggingModel.setAction(user.getChineseName() + "于 " + datetime + " 对项目【" + pm.getProjectName() + "】的CR(" + model.getCrDescribe() + ")执行了修改操作");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(3);
			loggingModel.setEventName("修改");
			loggingModel.setTables("cr");
			loggingService.addLogging(loggingModel);
			if(!content.equals("")){
				Email email = new Email();
				List<UserModel> emailList = userService.queryNotificationUser();
				List<String> toList = new ArrayList<String>();
				for(UserModel u : emailList){
					if(u.getEmail() != null){
						toList.add(u.getEmail());
					}
				}
				if(user.getEmail() != null){
					toList.add(user.getEmail());
				}
				HashSet<String> set = new HashSet<String>(toList);
				toList.clear();
				toList.addAll(set);
				email.setTo(toList);
				email.setSubject("cr修改");
				String text = user.getRole().getRoleDescription() + user.getChineseName() + "在" + date.format(new Date()) + "修改了项目:【" + project.getProjectName() + "】的cr" + content.substring(0,content.length()-1);
				email.setContent(text);
				MailSendUtil util = new MailSendUtil();
				util.sendEmail(email);
			}
			map.put("success", true);
			map.put("projectId", cr.getProjectId());
		} else {
			map.put("success", false);
			map.put("errmsg", "修改失败，请稍候再试或联系管理员");
		}
		return map;
	}

	// 根据datatable所传参数获取所需的CR列表
	@RequestMapping(value = "getAllCR")
	@ResponseBody
	public Page<CRModel> getCRs(long projectId, String column, String dir, String sch, HttpServletRequest request) {
		if (sch != null && !sch.equals("")) {
			sch = "%" + sch + "%";
		}
		Page<CRModel> page = crService.getCRs(projectId, column, dir, sch);
		List<CRModel> list = page.getData();
		String str = (String) request.getSession().getAttribute("authorities");
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		// 没有权限处理获取的crList信息中的报价
		if (!str.contains("cr_price_read")) {
			for (CRModel crm : list) {
				if(user.getUserId() != crm.getCreatorId()){
					crm.setEstimateQuotation(0);
					crm.setConfirmQuotation(0);
				}
			}
		}
		return page;
	}

	// 控制上传附件的大小
	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, HttpServletRequest request) {
		if (ex instanceof MaxUploadSizeExceededException) {
			return "文件大小应不超过" + getFileMB(((MaxUploadSizeExceededException) ex).getMaxUploadSize());
		} else {
			return ex.getMessage();
		}
	}

	// private String getFileKB(long byteFile) {
	// if (byteFile == 0)
	// return "0KB";
	// long kb = 1024;
	// return "" + byteFile / kb + "KB";
	// }

	private String getFileMB(long byteFile) {
		if (byteFile == 0)
			return "0MB";
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}

}
