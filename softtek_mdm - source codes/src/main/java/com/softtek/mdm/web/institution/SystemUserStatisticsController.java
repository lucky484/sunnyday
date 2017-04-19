package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.service.UserStatisticsService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.web.BaseController;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/institution/systemStatistics/user")
public class SystemUserStatisticsController extends BaseController{

	private static final Logger logger = Logger.getLogger(SystemUserStatisticsController.class);
	
	@Autowired
	private UserStatisticsService userStatisticsService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;
	
    /**
     * 用户统计列表首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.statistics.index.link.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String userIndex(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session) throws IOException{
		return "institution/systemStatistics/user/index";
	}
	
	/**
     * 根据条件查询所有用户的统计信息
     * 
     * @param request
     * @param response
     * @param start
     * @param length
     * @return
     */
	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
 	@RequestMapping(value="/getAllUserStatistics",method=RequestMethod.GET)
 	@ResponseBody
 	public Page getAllUserStatistics(HttpServletRequest request,HttpServletResponse response,HttpSession session,Integer start,Integer length){
 		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
 		String startTime = request.getParameter("timeStart");
 		String endTime = request.getParameter("timeEnd");
 		String searchType = request.getParameter("searchType");
        Map<String, Object> paramMap = new HashMap<String, Object>();
 		List<String> searchDates = getSearchType(searchType);
 		if(CollectionUtils.isNotEmpty(searchDates)){
            if (searchDates.size() == 1) {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(0));
            } else {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(1));
            }
        } else {
            paramMap.put("firstDay", StringUtils.trimToNull(startTime));
            paramMap.put("lastDay", StringUtils.trimToNull(endTime));
 		}
 		start=start==null?0:start;
		length=length==null?10:length;

 		paramMap.put("org_id", organization.getId());
        // 获取部门管理员管理部门list
 		List<Integer> depardIds=getManagerIds(session);
 		paramMap.put("groupId", depardIds);
        Page page = userStatisticsService.getAllUsersListsByMap(paramMap, start, length);
 		return page;
 	}
	
	/**
     * 如果不为null则是所管理部门的id，如果是null，那么表示是机构管理员
     * 
     * @param session
     * @return
     */
	private List<Integer> getManagerIds(HttpSession session){
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
 		if(manager.getUser()!=null){
            // 部门管理员
 			@SuppressWarnings("unchecked")
			List<StructureModel> list = (List<StructureModel>) session
 					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
 			@SuppressWarnings("unchecked")
			List<Integer> depardIds = (List<Integer>) CollectionUtils.collect(list, new Transformer() {
				@Override
				public Object transform(Object input) {
					StructureModel ste =(StructureModel) input;
					return ste.getId();
				}
			});
 			return depardIds;
 		}
 		return null;
	}
	
	private List<String> getSearchType(String searchType) {
		List<String> lists = new ArrayList<String>();
		if(StringUtils.isNotBlank(searchType)){
            // 按天进行统计
			if("1".equals(searchType)){
                // 获取当天的时间
				String today = CommUtil.getCurrentDay();
				lists.add(today);
            } else if ("2".equals(searchType)) {// 按周进行统计
				String firstDay = CommUtil.getFirstOfWeek();
				String lastDay = CommUtil.getLastOfWeek();
				lists.add(firstDay);
				lists.add(lastDay);
            } else {// 按月进行统计
				String firstDay = CommUtil.getFirstOfMonth();
				String lastDay = CommUtil.getLastOfMonth();
				lists.add(firstDay);
				lists.add(lastDay);
			}
		}
		return lists;
	}
	
	 @Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
	 @RequestMapping(value="/chart",method=RequestMethod.GET)
	 @ResponseBody
	 public List<UserModel> getUserStatisChart(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 
		 OrganizationModel organization = (OrganizationModel) request.getSession()
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String timeStart = request.getParameter("timeStart");
		String timeEnd = request.getParameter("timeEnd");
		String searchType = request.getParameter("searchType");
		Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("startTime",StringUtils.trimToNull(timeStart));
	    paramMap.put("endTime", StringUtils.trimToNull(timeEnd));
	    List<String> searchDates = getSearchType(searchType);
		if(CollectionUtils.isNotEmpty(searchDates)){
            if (searchDates.size() == 1) {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(0));
            } else {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(1));
            }
        } else {
            paramMap.put("firstDay", StringUtils.trimToNull(timeStart));
            paramMap.put("lastDay", StringUtils.trimToNull(timeEnd));
		}
	    paramMap.put("org_id", organization.getId());
        // 获取部门管理员管理部门list
        List<Integer> depardIds = getManagerIds(session);
        paramMap.put("groupId", depardIds);
        List<UserModel> lists = userService.getChartUsersListsByMap(paramMap);
	    return lists;
	    
	 }
	
	
	 @Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
	 @RequestMapping(value="/export",method=RequestMethod.POST)
	 public void exportUserStatistics(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 
		 OrganizationModel organization = (OrganizationModel) request.getSession()
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String timeStart = request.getParameter("timeStart");
		String timeEnd = request.getParameter("timeEnd");
		String searchType = request.getParameter("searchType");
		Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("startTime",StringUtils.trimToNull(timeStart));
	    paramMap.put("endTime", StringUtils.trimToNull(timeEnd));
	    List<String> searchDates = getSearchType(searchType);
        if (CollectionUtils.isNotEmpty(searchDates)) {
            if (searchDates.size() == 1) {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(0));
            } else {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(1));
            }
        } else {
            paramMap.put("firstDay", StringUtils.trimToNull(timeStart));
            paramMap.put("lastDay", StringUtils.trimToNull(timeEnd));
        }
	    paramMap.put("org_id", organization.getId());
        // 获取部门管理员管理部门list
        List<Integer> depardIds = getManagerIds(session);
        paramMap.put("groupId", depardIds);
	    List<UserModel> lists = userService.getUsersListsByMap(paramMap);
        // 导出excel文件
		String sheetName = messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics", null,LocaleContextHolder.getLocale());
		String[] headNames = {
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title1", null,LocaleContextHolder.getLocale()),
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title2", null,LocaleContextHolder.getLocale()),
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title3", null,LocaleContextHolder.getLocale()),
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title4", null,LocaleContextHolder.getLocale()),
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title5", null,LocaleContextHolder.getLocale()),
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title6", null,LocaleContextHolder.getLocale()),
				messageSource.getMessage("web.institution.SystemUserStatisticsController.exportUserStatistics.title7", null,LocaleContextHolder.getLocale()),
				};
		exportExcel(sheetName,headNames,lists,response,"0",organization);
	 }

	private void exportExcel(String sheetName, String[] headNames, List<UserModel> lists,
			HttpServletResponse response,String flag,OrganizationModel organization) {
		OutputStream out = null;
		try{  
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(sheetName); // 创建工作表
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);  
            HSSFCell cellTiltle = rowm.createCell(0);  
            // sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
            HSSFCellStyle columnTopStyle = CommUtil.getColumnTopStyle(workbook);// 获取列头样式对象
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (headNames.length-1)));    
            cellTiltle.setCellStyle(columnTopStyle);  
            cellTiltle.setCellValue(sheetName);  
            // 定义所需列数
            int columnNum = headNames.length;  
            HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){  
                HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(headNames[n]);  
                cellRowName.setCellValue(text); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }  
            // 将查询出的数据设置到sheet对应的单元格中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i=0;i<lists.size();i++){  
                HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数
            	UserModel user = lists.get(i);
            	if(StringUtils.endsWith(flag, "0")){
            		HSSFCell cell = row.createCell(0);
                	HSSFCellStyle cellStyle = workbook.createCellStyle();
                	HSSFDataFormat format = workbook.createDataFormat();
                	cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
                	cell.setCellStyle(cellStyle);
                    cell.setCellValue(sdf.format(user.getCreateDate()));
                	row.createCell(1).setCellValue(organization.getName());
                	row.createCell(2).setCellValue(organization.getOrgType());
                	row.createCell(3).setCellValue(user.getTotalUser());
                	row.createCell(4).setCellValue(user.getActiveUser());
                	row.createCell(5).setCellValue(user.getInActiveUser());
                	row.createCell(6).setCellValue(user.getDeleteUser());
            	}else if(StringUtils.endsWith(flag,"1")){
            		HSSFCell cell = row.createCell(0);
                    cell.setCellValue(sdf.format(user.getCreateDate()));
                	HSSFCellStyle cellStyle = workbook.createCellStyle();
                	HSSFDataFormat format = workbook.createDataFormat();
                	cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
                	cell.setCellStyle(cellStyle);
                	row.createCell(1).setCellValue(organization.getName());
                	row.createCell(2).setCellValue(organization.getOrgType());
                	row.createCell(3).setCellValue(user.getRealname());
                	row.createCell(4).setCellValue(user.getUsername());
                	row.createCell(5).setCellValue(user.getDepartName());
                	HSSFCell cell6 = row.createCell(6);
                	cell6.setCellValue(user.getCreateTime());
                	HSSFCellStyle cellStyle6 = workbook.createCellStyle();
                	HSSFDataFormat format6 = workbook.createDataFormat();
                	cellStyle6.setDataFormat(format6.getFormat("yyyy-MM-dd hh:mm:ss"));
                	cell6.setCellStyle(cellStyle6);
            	}
            }  
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {  
                int columnWidth = sheet.getColumnWidth(colNum) / 256;  
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {  
                    HSSFRow currentRow;  
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {  
                        currentRow = sheet.createRow(rowNum);  
                    } else {  
                        currentRow = sheet.getRow(rowNum);  
                    }  
                    if (currentRow.getCell(colNum) != null) {  
                        HSSFCell currentCell = currentRow.getCell(colNum);  
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {  
                            int length = currentCell.getStringCellValue().getBytes().length;  
                            if (columnWidth < length) {  
                                columnWidth = length;  
                            }  
                        }  
                    }  
                }  
                if(colNum == 0){  
                    sheet.setColumnWidth(colNum, (columnWidth) * 256);  
                }else{  
                    sheet.setColumnWidth(colNum, (columnWidth+10) * 256);  
                }  
            }  
            if(workbook !=null){  
                try{  
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";  
                    String headStr = "attachment; filename=\"" + fileName + "\"";  
                    response.setContentType("application/octet-stream");  
                    response.setHeader("Content-Disposition", headStr);  
                    out = response.getOutputStream();  
                    workbook.write(out);
                }  
                catch (IOException e){  
                   logger.error(e.getMessage());
                }finally{
                	if(out!=null){
                		out.close();
                	}
                }  
            }  
        }catch(Exception e){  
        	logger.error(e.getMessage());
        }  
	}

	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
 	@RequestMapping(value="/getAllTotalUserStatistics",method=RequestMethod.GET)
 	@ResponseBody
 	public Page getAllTotalUserStatistics(HttpServletRequest request,HttpServletResponse response,Integer start,Integer length){
	 
 		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
 		String real_name = request.getParameter("real_name");
 		String user_name = request.getParameter("user_name");
 		String search_date = request.getParameter("search_date");
 		Map<String,Object> paramMap = new HashMap<String,Object>();
 		paramMap.put("real_name", StringUtils.trimToNull(real_name));
 		paramMap.put("user_name", StringUtils.trimToNull(user_name));
 		paramMap.put("create_time", StringUtils.trimToNull(search_date));
 		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
	    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
 		paramMap.put("org_id", organization.getId());
 		Page page = userStatisticsService.getAllTotalUsersListsByMap(paramMap);
 		return page;
 	}
	
	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
	 @RequestMapping(value="/total/export",method=RequestMethod.POST)
	 public void exportTotalUserStatistics(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 
		 OrganizationModel organization = (OrganizationModel) request.getSession()
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String username = request.getParameter("user_name");
		String realname = request.getParameter("real_name");
		String create_time = request.getParameter("create_time");
		Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("user_name",StringUtils.trimToNull(username));
	    paramMap.put("real_name", StringUtils.trimToNull(realname));
	    paramMap.put("create_time", StringUtils.trimToNull(create_time));
	    paramMap.put("org_id", organization.getId());
	    List<UserModel> lists = userService.getTotalUsersListsByMap(paramMap);
        // 导出excel文件
	    
		String sheetName = messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics", null, LocaleContextHolder.getLocale());
		String[] headNames = {	messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title1", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title2", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title3", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title4", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title5", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title6", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.SystemUserStatisticsController.userStatistics.title7", null, LocaleContextHolder.getLocale()),
								};
		exportExcel(sheetName,headNames,lists,response,"1",organization);
	 }
	
	
	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
 	@RequestMapping(value="/getAllActiveUserStatistics",method=RequestMethod.GET)
 	@ResponseBody
 	public Page getAllActiveUserStatistics(HttpServletRequest request,HttpServletResponse response,Integer start,Integer length){
	 
 		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
 		String real_name = request.getParameter("real_name");
 		String user_name = request.getParameter("user_name");
 		String search_date = request.getParameter("search_date");
 		Map<String,Object> paramMap = new HashMap<String,Object>();
 		paramMap.put("real_name", StringUtils.trimToNull(real_name));
 		paramMap.put("user_name", StringUtils.trimToNull(user_name));
 		paramMap.put("create_time", StringUtils.trimToNull(search_date));
 		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
	    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
 		paramMap.put("org_id", organization.getId());
 		Page page = userStatisticsService.getAllActiveUsersListsByMap(paramMap);
 		return page;
 	}

	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
 	@RequestMapping(value="/getAllInActiveUserStatistics",method=RequestMethod.GET)
 	@ResponseBody
 	public Page getAllInActiveUserStatistics(HttpServletRequest request,HttpServletResponse response,Integer start,Integer length){
	 
 		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
 		String real_name = request.getParameter("real_name");
 		String user_name = request.getParameter("user_name");
 		String search_date = request.getParameter("search_date");
 		Map<String,Object> paramMap = new HashMap<String,Object>();
 		paramMap.put("real_name", StringUtils.trimToNull(real_name));
 		paramMap.put("user_name", StringUtils.trimToNull(user_name));
 		paramMap.put("create_time", StringUtils.trimToNull(search_date));
 		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
	    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
 		paramMap.put("org_id", organization.getId());
 		Page page = userStatisticsService.getAllInActiveUsersListsByMap(paramMap);
 		return page;
 	}

	
	@Link(family = "institution", label = "web.institution.systemStatistics.user.label", parent = "institution.home.index.label", belong = "web.institution.systemStatistics.user.belong")
 	@RequestMapping(value="/getAllDeleteUserStatistics",method=RequestMethod.GET)
 	@ResponseBody
 	public Page getAllDeleteUserStatistics(HttpServletRequest request,HttpServletResponse response,Integer start,Integer length){
	 
 		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
 		String real_name = request.getParameter("real_name");
 		String user_name = request.getParameter("user_name");
 		String search_date = request.getParameter("search_date");
 		Map<String,Object> paramMap = new HashMap<String,Object>();
 		paramMap.put("real_name", StringUtils.trimToNull(real_name));
 		paramMap.put("user_name", StringUtils.trimToNull(user_name));
 		paramMap.put("create_time", StringUtils.trimToNull(search_date));
 		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
	    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
 		paramMap.put("org_id", organization.getId());
 		Page page = userStatisticsService.getAllDeleteUsersListsByMap(paramMap);
 		return page;
 	}
	
}
