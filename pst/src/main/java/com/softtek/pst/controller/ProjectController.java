package com.softtek.pst.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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

import com.softtek.pst.model.CRModel;
import com.softtek.pst.model.ChanceManageModel;
import com.softtek.pst.model.CustomerModel;
import com.softtek.pst.model.Email;
import com.softtek.pst.model.ImplementManagerModel;
import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.model.ProjectComment;
import com.softtek.pst.model.ProjectFile;
import com.softtek.pst.model.ProjectManagerModel;
import com.softtek.pst.model.ProjectModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.CRService;
import com.softtek.pst.service.ChanceManageService;
import com.softtek.pst.service.CustomerService;
import com.softtek.pst.service.ImplementManagerService;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.ProjectCommentService;
import com.softtek.pst.service.ProjectFileService;
import com.softtek.pst.service.ProjectManagerService;
import com.softtek.pst.service.ProjectService;
import com.softtek.pst.service.UserService;
import com.softtek.pst.util.GlobalSettingsUtils;
import com.softtek.pst.util.MailSendUtil;
import com.softtek.pst.util.Page;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * @ClassName: ProjectController
 * @Description:
 * @author: cliff.fan
 * @date: Apr 6, 2016 9:59:13 AM
 */
@Controller
@RequestMapping("/projectsManagement/projects")
public class ProjectController {

	private Logger logger = Logger.getLogger(ProjectController.class);
	private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
	private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列

	@Autowired
	private ProjectService projectService;
	@Autowired
	private CRService crService;
	@Autowired
	private ProjectFileService projectFileService;
	@Autowired
	private GlobalSettingsUtils globalSettingsUtils;
	@Autowired
	private ProjectCommentService projectCommentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LoggingService loggingService;
	
	@Autowired
    private ChanceManageService chanceManageService;
	
    @Autowired
    private ProjectManagerService projectManagerService;
    
    @Autowired
    private ImplementManagerService implementManagerService;
    
    @Autowired
    private UserService userService;
    
	/**
	 * 
	 * @Title: download
	 * @Description: 附件下载
	 * @param projectId
	 * @param request
	 * @param response
	 * @return
	 * @return: ResponseEntity<byte[]>
	 */
	@RequestMapping("download")
	public ResponseEntity<byte[]> download(long projectFileId, HttpServletRequest request,
			HttpServletResponse response) {
		ProjectFile pf = projectFileService.getProjectFile(projectFileId);
		String attName = pf.getFileName();
		String path = globalSettingsUtils.getFilePath() + pf.getFilePath();
		HttpHeaders headers = new HttpHeaders();
		String fileName;
		try {
			fileName = new String(attName.getBytes("UTF-8"), "iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileName);
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
		} // 为了解决中文名称乱码问题
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try {
			return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(path)), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "projectsList")
	public String getprojectList(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		List<ProjectManagerModel> list = projectManagerService.queryAllProjectManager();
		request.setAttribute("list",list);
		return "project/projectsList";
	}
	
	@RequestMapping(value = "getprojectForFinshList")
	public String getprojectForFinshList(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		List<ProjectManagerModel> list = projectManagerService.queryAllProjectManager();
		request.setAttribute("list",list);
		return "project/projectsForFinshList";
	}

	@RequestMapping(value = "addProject")
	public ModelAndView add(HttpServletRequest request) {
		String chanceManageId = request.getParameter("chanceManageId");
		ChanceManageModel chanceManage = new ChanceManageModel();
		if(chanceManageId != null){
		   chanceManage = chanceManageService.queryDetailById(Long.valueOf(chanceManageId));	
		}
		request.setAttribute("url", request.getServletPath());
		List<ProjectManagerModel> list1 = projectManagerService.queryAllProjectManager();
		List<CustomerModel> list = customerService.getAllCustomer();
		List<ImplementManagerModel> list2 = implementManagerService.queryAllImplementManager();
		ModelAndView mav = new ModelAndView("project/addProject");
		mav.addObject("customers", list);
		mav.addObject("chanceManage", chanceManage);
		mav.addObject("list", list1);
		mav.addObject("list2", list2);
		return mav;
	}

	@RequestMapping(value = "edit")
	public ModelAndView edit(long projectId, HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		ProjectModel pm = projectService.getProjectById(projectId);
		List<ProjectFile> pf = projectFileService.getProjectFileByProjectId(projectId);
		List<CustomerModel> lcm = customerService.getAllCustomer();
		List<ProjectManagerModel> list1 = projectManagerService.queryAllProjectManager();
		List<ImplementManagerModel> list2 = implementManagerService.queryAllImplementManager();
		ModelAndView mav = new ModelAndView("project/editProject");
		mav.addObject("pm", pm);
		mav.addObject("pf", pf);
		mav.addObject("lcm", lcm);
		mav.addObject("list1", list1);
		mav.addObject("list2", list2);
		return mav;
	}

	@RequestMapping("viewProject")
	public ModelAndView viewProject(long projectId, HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		List<ProjectFile> pf = projectFileService.getProjectFileByProjectId(projectId);
		List<ProjectComment> list = projectCommentService.getProjectCommentByProjectId(projectId);
		ProjectModel pm = projectService.getProjectById(projectId);
		ModelAndView mav = new ModelAndView("project/viewProject");
		mav.addObject("list", list);
		mav.addObject("pm", pm);
		mav.addObject("pf", pf);
		return mav;
	}

	// 增加评论
	@RequestMapping(value = "addProjectComment")
	@ResponseBody
	public Map<String, Object> addComment(String projectId, String comment, HttpServletRequest request,
			HttpServletResponse response) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		ProjectComment pc = new ProjectComment();
		pc.setComment(comment);
		pc.setCritic(user.getChineseName());
		pc.setProjectId(Long.parseLong(projectId));
		Map<String, Object> result = new HashMap<>();
		if (projectCommentService.addProjectComment(pc) == 1) {
			// 记录日志
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			ProjectModel pm = new ProjectModel();
			pm.getProjectName();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "对【" + pm.getProjectName() + "】" + "进行了评论");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(5);
			loggingModel.setEventName("评论");
			loggingModel.setTables("project_comment");
			loggingService.addLogging(loggingModel);
			result.put("success", true);
			pc.setCreateTime(new Date());
			result.put("comment", pc);
		} else {
			result.put("success", false);
		}
		return result;
	}

	@RequestMapping("/getProjectInfo")
	@ResponseBody
	public ProjectModel getProject(long projectId) {
		return projectService.getProjectById(projectId);
	}

	@RequestMapping("/deleteProject")
	@ResponseBody
	public int deleteProject(long projectId, HttpServletRequest request) throws IOException, MessagingException {
		// 记录日志
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		LoggingModel loggingModel = new LoggingModel();
		loggingModel.setOperator(user.getChineseName());
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datetime = (date.format(new Date())).toString();
		ProjectModel projectModel = projectService.getProjectById(projectId);
		projectModel.getProjectName();
		loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除项目【" + projectModel.getProjectName() + "】");
		loggingModel.setUserId(user.getUserId());
		loggingModel.setEventType(2);
		loggingModel.setEventName("删除");
		loggingModel.setTables("project");
		int result = projectService.deleteProject(projectId);
		if (result == 1) {
			logger.info("成功删除一条项目,ID:" + projectId);
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
			email.setSubject("项目删除");
			String text = user.getRole().getRoleDescription() + user.getChineseName() + "在" + date.format(new Date()) + "删除了项目:【" + projectModel.getProjectName() + "】";
			email.setContent(text);
			MailSendUtil util = new MailSendUtil();
			util.sendEmail(email);
		} else {
			logger.info("项目删除失败,ID:" + projectId);
		}
		return result;
	}

	/**
	 * 
	 * @Title: updateProject
	 * @Description: 编辑
	 * @param project
	 * @param request
	 * @param file
	 * @return
	 * @return: ModelAndView
	 * @throws ParseException 
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	@RequestMapping("/updateProject")
	@ResponseBody
	public Map<String, Object> updateProject(ProjectModel project, MultipartHttpServletRequest request,
			@RequestParam("file") MultipartFile[] files) throws ParseException, MessagingException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String content = "";
		String medialTime = request.getParameter("medial_time");
		String leadTime = request.getParameter("lead_time");
		String startTime = request.getParameter("start_time");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		ProjectModel existProject = projectService.getProjectById(project.getProjectId());
		if(!existProject.getProjectCode().equals(project.getProjectCode())){
			content += "项目编号" + ",";
		}
		if(!existProject.getProjectName().equals(project.getProjectName())){
			content += "项目名称" + ",";
		}
		if(!existProject.getProjectManager().equals(project.getProjectManager())){
			content += "项目经理" + ",";
		}
		if(!existProject.getImplementManager().equals(project.getImplementManager())){
			content += "实施经理" + ",";
		}
		if(!format.parse(startTime).equals(existProject.getStartTime())){
			content += "开始时间" + ",";
		}
		if(!format.parse(medialTime).equals(existProject.getMedialTime())){
			content += "内测时间" + ",";
		}
		if(!format.parse(leadTime).equals(existProject.getLeadTime())){
			content += "交付时间" + ",";
		}
		if(existProject.getProjectNature() != project.getProjectNature()){
			content += "性质" + ",";
		}
		if(project.getProjectNature() == 1){
			if(existProject.getOutsourcingQuotation() != project.getOutsourcingQuotation()){
				content += "外包价格" + ",";	
			}
		}
		if(existProject.getProjectQuotation() != project.getProjectQuotation()){
			content += "项目报价" + ",";
		}
		if(!existProject.getProjectStatus().equals(project.getProjectStatus())){
			content += "项目状态" + ",";
		}
		if(existProject.getCustomerId() != project.getCustomerId()){
			content += "客户名称" + ",";
		}
		if(!existProject.getRemark().equals(project.getRemark())){
			content += "备注" + ",";
		}
		existProject.setProjectCode(project.getProjectCode());
		existProject.setProjectName(project.getProjectName());
		existProject.setProjectManager(project.getProjectManager());
		existProject.setImplementManager(project.getImplementManager());
		CustomerModel cm = customerService.getCustomerById(project.getCustomerId());
		existProject.setCustomerName(cm.getCustomerNameShort());
		existProject.setCustomerId(cm.getCustomerId());
		existProject.setStartTime(format.parse(startTime));
		existProject.setMedialTime(format.parse(medialTime));
		existProject.setLeadTime(format.parse(leadTime));
		existProject.setProjectQuotation(project.getProjectQuotation());
		existProject.setUpdateTime(project.getUpdateTime());
		existProject.setProjectStatus(project.getProjectStatus());
		existProject.setProjectType(project.getProjectType());
		existProject.setProjectStatusNumber(project.getProjectStatusNumber());
		existProject.setProjectTypeNumber(project.getProjectTypeNumber());
		existProject.setRemark(project.getRemark());
		existProject.setProjectNature(project.getProjectNature());
		existProject.setOutsourcingQuotation(project.getOutsourcingQuotation());
		int result = projectService.updateProject(existProject);
		if (result == 1) {
			// ----原有文件处理------
			List<ProjectFile> fileList = projectFileService.getProjectFileByProjectId(project.getProjectId());
			// 将数据库中所有文件id存放在ids集合中
			List<String> ids = fileList.stream().map(f -> String.valueOf(f.getProjectFileId()))
					.collect(Collectors.toList());
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
						ProjectFile projectFile = projectFileService.getProjectFile(Long.valueOf(i));
						try {
							// 删除磁盘上原有文件
							Files.deleteIfExists(
									Paths.get(globalSettingsUtils.getFilePath() + projectFile.getFilePath()));
						} catch (IOException e) {
							logger.info(e.getMessage());
						}
						projectFileService.deleteProjectFileById(Long.valueOf(i));
					});
				}
				// 对前台保留的原有附件进行处理
				for (int i = 0; i < existDeleteFlagArr.length; i++) {
					// 前台原有附件框用户点击了相应附件的删除按钮
					if (existDeleteFlagArr[i].equals("1")) {
						if (!existFileIdsArr[i].equals("")) {
							ProjectFile projectFile = projectFileService
									.getProjectFile(Long.valueOf(existFileIdsArr[i]));
							try {
								// 删除磁盘上原有文件
								Files.deleteIfExists(
										Paths.get(globalSettingsUtils.getFilePath() + projectFile.getFilePath()));
							} catch (IOException e) {
								logger.info(e.getMessage());
							}
							projectFileService.deleteProjectFileById(Long.valueOf(existFileIdsArr[i]));
						}
					} else {
						// 尝试获取前台原有附件的input框内容
						MultipartFile updateFile = request.getFile("file" + existFileIdsArr[i]);
						// 能获取到，说明用户对原有附件进行了修改，需要同步修改数据库中的附件
						if (updateFile != null && !updateFile.isEmpty()) {
							String fileName = df.format(new Date()) + "_" + updateFile.getOriginalFilename();
							ProjectFile file = projectFileService.getProjectFile(Long.valueOf(existFileIdsArr[i]));
							try {
								// 删除磁盘上原有文件
								Files.deleteIfExists(Paths.get(globalSettingsUtils.getFilePath() + file.getFilePath()));
							} catch (IOException e) {
								logger.info(e.getMessage());
							}
							file.setContentType(updateFile.getContentType());
							file.setFileName(updateFile.getOriginalFilename());
							file.setFilePath("project/" + project.getProjectId() + "/" + fileName);
							file.setFileSize(updateFile.getSize());
							file.setUpdateDate(new Date());
							projectFileService.updateProjectFile(file);
							try {
								Path path = Paths.get(
										globalSettingsUtils.getFilePath() + "project/" + project.getProjectId() + "/");
								// 如果文件夹不存在则创建
								if (!Files.exists(path)) {
									Files.createDirectories(path);
								}
								FileCopyUtils.copy(updateFile.getBytes(),
										new FileOutputStream(path.toString() + "/" + fileName));
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
				ProjectFile file = new ProjectFile();
				file.setContentType(f.getContentType());
				file.setFileName(f.getOriginalFilename());
				file.setFilePath("project/" + project.getProjectId() + "/" + fileName);
				file.setFileSize(f.getSize());
				file.setProjectId(project.getProjectId());
				projectFileService.addProjectFile(file);
				try {
					Path path = Paths
							.get(globalSettingsUtils.getFilePath() + "project/" + project.getProjectId() + "/");
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
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改项目【" + project.getProjectName() + "】");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(3);
			loggingModel.setEventName("修改");
			loggingModel.setTables("project");
			loggingService.addLogging(loggingModel);
			if(!content.equals("")){
				Email email = new Email();
				List<UserModel> list = userService.queryNotificationUser();
				List<String> toList = new ArrayList<String>();
				for(UserModel u : list){
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
				email.setSubject("项目修改");
				String text = user.getRole().getRoleDescription() + user.getChineseName() + "在" + date.format(new Date()) + "修改了项目:【" + existProject.getProjectName() + "】的" + content.substring(0,content.length()-1);
				email.setContent(text);
				MailSendUtil util = new MailSendUtil();
				util.sendEmail(email);
			}
			map.put("success", true);
		} else {
			map.put("success", false);
			map.put("errmsg", "修改失败，请稍候再试或联系管理员");
			logger.info("修改项目失败，名称：" + project.getProjectName());
		}
		return map;
	}

	/**
	 * 
	 * @Title: insert
	 * @Description: 新增
	 * @param project
	 * @param request
	 * @param file
	 * @return
	 * @return: ModelAndView
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(ProjectModel project, HttpServletRequest request,@RequestParam("file")MultipartFile[] list) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String chanceManageId = request.getParameter("chanceManageId");
		String outsourcingQuotation = request.getParameter("outsourcingQuotation");
		if(!outsourcingQuotation.equals("")){
			project.setOutsourcingQuotation(Integer.valueOf(outsourcingQuotation));
		}else{
			project.setOutsourcingQuotation(Integer.valueOf(0));
		}
		String medialTime = request.getParameter("medial_time");
		String leadTime = request.getParameter("lead_time");
		String startTime = request.getParameter("start_time");
		String projectNatureStr = request.getParameter("projectNature");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			project.setStartTime(format.parse(startTime));
			project.setMedialTime(format.parse(medialTime));
			project.setLeadTime(format.parse(leadTime));
			project.setProjectNature(Integer.valueOf(projectNatureStr));
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			project.setCreatorId(user.getUserId());
			CustomerModel cm = customerService.getCustomerById(project.getCustomerId());
			project.setCustomerName(cm.getCustomerNameShort());
		} catch (ParseException e1) {
			logger.info(e1.getMessage());
		}
		ProjectFile projectFile = new ProjectFile();
		if (projectService.addProject(project) == 1) {
			if(chanceManageId != null && !chanceManageId.equals("")){
				chanceManageService.deleteChanceManage(Long.valueOf(chanceManageId));
			}
			long projectId = project.getProjectId();
			for (MultipartFile mpf : list) {
				if (!mpf.isEmpty()) {
					String fileName = mpf.getOriginalFilename();
					String data = (sdf.format(new Date())).toString();
					projectFile.setFileName(fileName);
					fileName = data + "_" + fileName;
					projectFile.setFilePath("project/" + project.getProjectId() + "/" + fileName);
					projectFile.setContentType(mpf.getContentType());
					projectFile.setFileSize(mpf.getSize());
					projectFile.setProjectId(projectId);
					projectFileService.addProjectFile(projectFile);
					try {
						Path path = Paths
								.get(globalSettingsUtils.getFilePath() + "project/" + project.getProjectId() + "/");
						// 如果文件夹不存在则创建
						if (!Files.exists(path)) {
							Files.createDirectories(path);
						}
						FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(path.toString() + "/" + fileName));
					} catch (FileNotFoundException e) {
						logger.info(e.getMessage());
					} catch (IOException e) {
						logger.info(e.getMessage());
					}
				}
			}
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增项目:【" + project.getProjectName() + "】");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(1);
			loggingModel.setEventName("增加");
			loggingModel.setTables("project");
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
			email.setSubject("项目新增");
			String text = user.getRole().getRoleDescription() + user.getChineseName() + "在" + date.format(new Date()) + "新增了项目:【" + project.getProjectName() + "】";
			email.setContent(text);
			MailSendUtil util = new MailSendUtil();
			util.sendEmail(email);
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("errmsg", "添加失败，请稍候再试或联系管理员");
		}
		return result;
	}

	@RequestMapping(value = "getProjects")
	@ResponseBody
	public Page<ProjectModel> getProjects(Integer start, Integer length, String column, String dir, String sch,
			HttpServletRequest request) throws ParseException {
		if (sch != null && !sch.equals("")) {
			sch = "%" + sch + "%";
		} else {
			sch = "%%";
		}
//		String type = request.getParameter("columns[10][search][value]");
//		String status = request.getParameter("columns[9][search][value]");
		String projectNumber = request.getParameter("projectNumber");
		String projectName = request.getParameter("projectName");
		String projectManager = request.getParameter("projectManager");
		String startTime = request.getParameter("startTime");
		String medialTime = request.getParameter("medialTime");
		String leadTime = request.getParameter("leadTime");
		if (projectNumber != null && !projectNumber.equals("")) {
			projectNumber = "%" + projectNumber + "%";
		} else {
			projectNumber = "%%";
		}
		if (projectName != null && !projectName.equals("")) {
			projectName = "%" + projectName + "%";
		} else {
			projectName = "%%";
		}
		Page<ProjectModel> page = projectService.getProjects(start, length, column, dir, sch, projectNumber, projectName,projectManager,startTime,medialTime,leadTime);
		List<ProjectModel> list = page.getData();
		String str = (String) request.getSession().getAttribute("authorities");
		// 没有权限处理获取的projectList信息中的报价
		if (!str.contains("projects_price_read")) {
			for (ProjectModel pm : list) {
				pm.setProjectQuotation(0);
			}
		}
		return page;
	}

	/**
	 * 
	 * @Title: export
	 * @Description: 导出项目列表,CR详情
	 * @param request
	 * @param response
	 * @return: void
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		List<ProjectModel> list = projectService.getProjects(null, null, "create_time", "desc", "%%", "%%", "%%",null,null,null,null)
				.getData();
		WritableWorkbook wb = null;
		OutputStream stream = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
		String fileName = "未完成的项目列表" + format.format(new Date()) + ".xls";
		try {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
			stream = response.getOutputStream();
		} catch (IOException e1) {
			logger.info(e1.getMessage());
		}
		int i = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 标题单元格定义
			wcf_title.setAlignment(jxl.format.Alignment.CENTRE);
			WritableCellFormat wcf = new WritableCellFormat(wfont);
			WritableCellFormat wcf1 = new WritableCellFormat(wfont1);
			wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置标题居中对齐
			wcf1.setAlignment(jxl.format.Alignment.CENTRE); // 设置内容居中对齐
			wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 设置边框
			wcf1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 设置边框
			// 创建workbook
			wb = Workbook.createWorkbook(stream);
			WritableSheet sheet = null;
			List<CRModel> crList;
			Label label;
			// 项目导出
			for (ProjectModel project : list) {
				// 创建sheet列表，名为ProjetName
				sheet = wb.createSheet(project.getProjectName(), i++);
				// crList = project.getCrList();
				crList = crService.getCRs(project.getProjectId(), "create_time", "desc", null).getData();
				label = new Label(ORIGIN_Y, ORIGIN_X, "项目详情", wcf_title);
				sheet.addCell(label);
				// sheet.mergeCells(0, 0, 8, 0);//合并单元格
				sheet.setColumnView(ORIGIN_Y, 21);

				label = new Label(ORIGIN_Y, ORIGIN_X + 1, "项目编号", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y, ORIGIN_X + 2, project.getProjectCode(), wcf1);
				sheet.addCell(label);

				label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "项目名称", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + 2, project.getProjectName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(1, 18);

				label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "项目经理", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + 2, project.getProjectManager(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 2, 18);
				
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "客户经理", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + 2, project.getImplementManager(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 3, 18);

				label = new Label(ORIGIN_Y + 4, ORIGIN_X + 1, "开始时间", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 4, ORIGIN_X + 2, sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 4, 18);

				label = new Label(ORIGIN_Y + 5, ORIGIN_X + 1, "内测时间", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 5, ORIGIN_X + 2, sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 5, 18);

				label = new Label(ORIGIN_Y + 6, ORIGIN_X + 1, "交付时间", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 6, ORIGIN_X + 2, sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 6, 18);

				label = new Label(ORIGIN_Y + 7, ORIGIN_X + 1, "项目报价", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 7, ORIGIN_X + 2, String.valueOf(project.getProjectQuotation()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 7, 18);

				label = new Label(ORIGIN_Y + 8, ORIGIN_X + 1, "客户", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 8, ORIGIN_X + 2, project.getCustomerName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 8, 18);

				label = new Label(ORIGIN_Y + 9, ORIGIN_X + 1, "备注", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 9, ORIGIN_X + 2, project.getRemark(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 9, 18);

				label = new Label(ORIGIN_Y + 10, ORIGIN_X + 1, "项目状态", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 10, ORIGIN_X + 2, project.getProjectStatus(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 10, 18);

				label = new Label(ORIGIN_Y + 11, ORIGIN_X + 1, "变更数量", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 11, ORIGIN_X + 2, String.valueOf(project.getCrTotal()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 11, 18);

				if (crList != null && crList.size() > 0) {
					// cr导出
					label = new Label(ORIGIN_Y, ORIGIN_X + 4, "需求变更详情", wcf_title);
					sheet.addCell(label);
					// sheet.mergeCells(0, 4, 7, 0);//合并单元格
					label = new Label(ORIGIN_Y, ORIGIN_X + 5, "需求描述", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 1, ORIGIN_X + 5, "预估(小时)", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 2, ORIGIN_X + 5, "提交人", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 3, ORIGIN_X + 5, "确认人", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 4, ORIGIN_X + 5, "报价", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 5, ORIGIN_X + 5, "备注", wcf);
					sheet.addCell(label);
					int j = ORIGIN_X + 6;
					for (CRModel cr : crList) {
						label = new Label(ORIGIN_Y, j, cr.getCrDescribe(), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 1, j, Double.toString(cr.getEstimatedWorkload()), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 2, j, cr.getPresenter(), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 3, j, cr.getConfirmor(), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 4, j, Double.toString(cr.getConfirmQuotation()), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 5, j, cr.getRemark(), wcf1);
						sheet.addCell(label);
						j++;
					}
					// 计算estimatedWorkload,estimateQuotation,confirmQuotation总值
					double estimatedWorkload = 0;
//					double estimateQuotation = 0;
					double confirmQuotation = 0;
					for (CRModel cr : crList) {
						estimatedWorkload = estimatedWorkload + cr.getEstimatedWorkload();
//						estimateQuotation = estimateQuotation + cr.getEstimateQuotation();
						confirmQuotation = confirmQuotation + cr.getConfirmQuotation();
					}
					label = new Label(ORIGIN_Y, j, "总计", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 1, j, Double.toString(estimatedWorkload), wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 2, j, "", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 3, j, "", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 4, j, Double.toString(confirmQuotation), wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 5, j, "", wcf);
					sheet.addCell(label);
				}
			}
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了未完成的项目");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(4);
			loggingModel.setEventName("导出");
			loggingModel.setTables("project");
			loggingService.addLogging(loggingModel);
			wb.write();
			wb.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response, Writer writer) {
		String errmsg = null;
		if (ex instanceof MaxUploadSizeExceededException) {
			errmsg = "文件大小应不超过" + getFileMB(((MaxUploadSizeExceededException) ex).getMaxUploadSize());
		} else {
			errmsg = ex.getMessage();
		}
		try {
			writer.write(String.format("{\"error\":{\"message\":\"%s\"}}", errmsg));
		} catch (IOException e) {
			e.printStackTrace();
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
	
	@RequestMapping(value="/checkProjectCode")
	@ResponseBody
	public boolean checkProjectCode(HttpServletRequest request){
		String projectCode = request.getParameter("projectCode");
		int result = projectService.queryProjectCodeIsExist(projectCode);
		if(result > 0){
			return false;
		}else{
			return true;
		}
	}
	
	@RequestMapping(value="/checkEditProjectCode")
	@ResponseBody
	public boolean checkEditProjectCode(HttpServletRequest request){
		String projectCode = request.getParameter("projectCode");
		String projectId = request.getParameter("projectId");
		ProjectModel project = projectService.getProjectById(Integer.valueOf(projectId));
		boolean checkNum = false;
		if(projectCode.equals(project.getProjectCode())){
			checkNum = true;
		}else{
			int result = projectService.checkProjectCode(projectCode,Long.valueOf(projectId));
			if(result > 0){
				checkNum = false;
			}else{
				checkNum = true;
			}
		}
		return checkNum;
	}
	
	/**
	 * 已完成的项目
	 */
	@RequestMapping(value = "getProjectsForFinsh")
	@ResponseBody
	public Page<ProjectModel> getProjectsForFinsh(Integer start, Integer length, String column, String dir, String sch,
			HttpServletRequest request) throws ParseException {
		if (sch != null && !sch.equals("")) {
			sch = "%" + sch + "%";
		} else {
			sch = "%%";
		}
		String projectNumber = request.getParameter("projectNumber");
		String projectName = request.getParameter("projectName");
		String projectManager = request.getParameter("projectManager");
		if (projectNumber != null && !projectNumber.equals("")) {
			projectNumber = "%" + projectNumber + "%";
		} else {
			projectNumber = "%%";
		}
		if (projectName != null && !projectName.equals("")) {
			projectName = "%" + projectName + "%";
		} else {
			projectName = "%%";
		}
		Page<ProjectModel> page = projectService.getProjectsForFinsh(start, length, column, dir, sch, projectNumber, projectName,projectManager);
		List<ProjectModel> list = page.getData();
		String str = (String) request.getSession().getAttribute("authorities");
		// 没有权限处理获取的projectList信息中的报价
		if (!str.contains("projects_price_read")) {
			for (ProjectModel pm : list) {
				pm.setProjectQuotation(0);
			}
		}
		return page;
	}
	
	/**
	 * 
	 * @Title: export
	 * @Description: 导出项目列表,CR详情
	 * @param request
	 * @param response
	 * @return: void
	 */
	@RequestMapping("/exportForFinsh")
	public void exportForFinsh(HttpServletRequest request, HttpServletResponse response) {
		List<ProjectModel> list = projectService.getProjectsForFinsh(null, null, "create_time", "desc", "%%", "%%", "%%",null)
				.getData();
		WritableWorkbook wb = null;
		OutputStream stream = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
		String fileName = "已完成的项目列表" + format.format(new Date()) + ".xls";
		try {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
			stream = response.getOutputStream();
		} catch (IOException e1) {
			logger.info(e1.getMessage());
		}
		int i = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 标题单元格定义
			wcf_title.setAlignment(jxl.format.Alignment.CENTRE);
			WritableCellFormat wcf = new WritableCellFormat(wfont);
			WritableCellFormat wcf1 = new WritableCellFormat(wfont1);
			wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置标题居中对齐
			wcf1.setAlignment(jxl.format.Alignment.CENTRE); // 设置内容居中对齐
			wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 设置边框
			wcf1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 设置边框
			// 创建workbook
			wb = Workbook.createWorkbook(stream);
			WritableSheet sheet = null;
			List<CRModel> crList;
			Label label;
			// 项目导出
			for (ProjectModel project : list) {
				// 创建sheet列表，名为ProjetName
				sheet = wb.createSheet(project.getProjectName(), i++);
				// crList = project.getCrList();
				crList = crService.getCRs(project.getProjectId(), "create_time", "desc", null).getData();
				label = new Label(ORIGIN_Y, ORIGIN_X, "项目详情", wcf_title);
				sheet.addCell(label);
				// sheet.mergeCells(0, 0, 8, 0);//合并单元格
				sheet.setColumnView(ORIGIN_Y, 21);

				label = new Label(ORIGIN_Y, ORIGIN_X + 1, "项目编号", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y, ORIGIN_X + 2, project.getProjectCode(), wcf1);
				sheet.addCell(label);

				label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "项目名称", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + 2, project.getProjectName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(1, 18);

				label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "项目经理", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + 2, project.getProjectManager(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 2, 18);
				
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "客户经理", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + 2, project.getImplementManager(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 3, 18);

				label = new Label(ORIGIN_Y + 4, ORIGIN_X + 1, "开始时间", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 4, ORIGIN_X + 2, sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 4, 18);

				label = new Label(ORIGIN_Y + 5, ORIGIN_X + 1, "内测时间", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 5, ORIGIN_X + 2, sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 5, 18);

				label = new Label(ORIGIN_Y + 6, ORIGIN_X + 1, "交付时间", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 6, ORIGIN_X + 2, sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 6, 18);

				label = new Label(ORIGIN_Y + 7, ORIGIN_X + 1, "项目报价", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 7, ORIGIN_X + 2, String.valueOf(project.getProjectQuotation()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 7, 18);

				label = new Label(ORIGIN_Y + 8, ORIGIN_X + 1, "客户", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 8, ORIGIN_X + 2, project.getCustomerName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 8, 18);

				label = new Label(ORIGIN_Y + 9, ORIGIN_X + 1, "备注", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 9, ORIGIN_X + 2, project.getRemark(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 9, 18);

				label = new Label(ORIGIN_Y + 10, ORIGIN_X + 1, "项目状态", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 10, ORIGIN_X + 2, project.getProjectStatus(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 10, 18);

				label = new Label(ORIGIN_Y + 11, ORIGIN_X + 1, "变更数量", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 11, ORIGIN_X + 2, String.valueOf(project.getCrTotal()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 11, 18);

				if (crList != null && crList.size() > 0) {
					// cr导出
					label = new Label(ORIGIN_Y, ORIGIN_X + 4, "需求变更详情", wcf_title);
					sheet.addCell(label);
					// sheet.mergeCells(0, 4, 7, 0);//合并单元格
					label = new Label(ORIGIN_Y, ORIGIN_X + 5, "需求描述", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 1, ORIGIN_X + 5, "预估(小时)", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 2, ORIGIN_X + 5, "提交人", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 3, ORIGIN_X + 5, "确认人", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 4, ORIGIN_X + 5, "报价", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 5, ORIGIN_X + 5, "备注", wcf);
					sheet.addCell(label);
					int j = ORIGIN_X + 6;
					for (CRModel cr : crList) {
						label = new Label(ORIGIN_Y, j, cr.getCrDescribe(), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 1, j, Double.toString(cr.getEstimatedWorkload()), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 2, j, cr.getPresenter(), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 3, j, cr.getConfirmor(), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 4, j, Double.toString(cr.getConfirmQuotation()), wcf1);
						sheet.addCell(label);

						label = new Label(ORIGIN_Y + 5, j, cr.getRemark(), wcf1);
						sheet.addCell(label);
						j++;
					}
					// 计算estimatedWorkload,estimateQuotation,confirmQuotation总值
					double estimatedWorkload = 0;
//					double estimateQuotation = 0;
					double confirmQuotation = 0;
					for (CRModel cr : crList) {
						estimatedWorkload = estimatedWorkload + cr.getEstimatedWorkload();
//						estimateQuotation = estimateQuotation + cr.getEstimateQuotation();
						confirmQuotation = confirmQuotation + cr.getConfirmQuotation();
					}
					label = new Label(ORIGIN_Y, j, "总计", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 1, j, Double.toString(estimatedWorkload), wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 2, j, "", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 3, j, "", wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 4, j, Double.toString(confirmQuotation), wcf);
					sheet.addCell(label);
					label = new Label(ORIGIN_Y + 5, j, "", wcf);
					sheet.addCell(label);
				}
			}
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了已完成的项目");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(4);
			loggingModel.setEventName("导出");
			loggingModel.setTables("project");
			loggingService.addLogging(loggingModel);
			wb.write();
			wb.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
}
