package com.softtek.mdm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.dao.UserVirtualGroupDao;
import com.softtek.mdm.model.ExcelVirModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.ExportAndImportExcelService;

@Service
public class ExportAndImportExcelServiceImpl implements ExportAndImportExcelService {

	@Autowired
	private UserVirtualGroupDao userVirtualGroupDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MessageSource messageSourceService;
	@Override
	public Map<String, Object> importvirmember(InputStream ins, List<Integer> idlist, Map<String, Integer> map) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		Integer id = map.get("groupid");
		Integer orgid = map.get("orgid");
		Integer colid = map.get("colId");
		Integer isradio = map.get("isradio");
		Integer filetype = map.get("filetype");
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(ins);
			ins.close();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = null;
		// 3.得到Excel工作表对象
		if (filetype == 03) {
			sheet = (HSSFSheet) wb.getSheetAt(0);
		}
		if (filetype == 07) {
			sheet = (XSSFSheet) wb.getSheetAt(0);
		}
		// Sheet sheet = wb.getSheetAt(0);
		// 总行数
		int trLength = sheet.getLastRowNum();
		// Excel所有用户名
		List<String> excelidlist = new ArrayList<String>();
		//List<ExcelVirMemberModel> excelidlistandrow = new ArrayList<ExcelVirMemberModel>();
		// 虚拟组中存在的用户名
		List<String> exisUsertlist = (List<String>) userVirtualGroupDao.findVirExistMember(map);
		// 所有存在的用户名
		List<String> usertlist = (List<String>) userVirtualGroupDao.findAllMember(map);
		// 所有存在集合的用户名
		List<String> colusertlist = (List<String>) userVirtualGroupDao.findAllColMember(map);
		//String erromessages="";
		StringBuffer sb=new StringBuffer();
		if(trLength>0){
		// 循环获得第二列第二行开始的值
		for (int i = 1; i <= trLength; i++) {
			// 得到Excel工作表的行
			Row row1 = sheet.getRow(i);
			int rownumber = i + 1;
			// 得到Excel工作表指定行的单元格
			Cell cell = row1.getCell(1);
			if (cell != null) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				// 获得用户帐号添加到list
				int deep = 0;
				for (int j = 0; j < usertlist.size(); j++) {
					if (usertlist.get(j).equals(cell.getStringCellValue())) {
						if (isradio == 0) {
							for (int q = 0; q < colusertlist.size(); q++) {
								if (colusertlist.get(q).equals(cell.getStringCellValue())) {
									deep++;
									deep++;
									break;
								}
							}
						} else {
							for (int q = 0; q < exisUsertlist.size(); q++) {
								if (exisUsertlist.get(q).equals(cell.getStringCellValue())) {
									deep++;
									break;
								}
							}
						}
						deep++;
						break;
					}
				}
				 
				if (deep == 0) {
					Object[] objects={rownumber};
					sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip1", objects, LocaleContextHolder.getLocale()));
					//erromessages = erromessages + "第" + rownumber + "用户帐号不存在" + "</br>";
				} else if (deep == 2) {
					Object[] objects={rownumber};
					sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip2", objects, LocaleContextHolder.getLocale()));
					//erromessages = erromessages + "第" + rownumber + "用户帐号已经存在虚拟组" + "</br>";
				} else if (deep == 3) {
					Object[] objects={rownumber};
					sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip3", objects, LocaleContextHolder.getLocale()));
					//erromessages = erromessages + "第" + rownumber + "用户帐号已经存在该集合" + "</br>";
				} else if(deep==1){
					excelidlist.add(cell.getStringCellValue());
				}
			} else {
				Object[] objects={rownumber};
				sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip4", objects, LocaleContextHolder.getLocale()));
				//erromessages = erromessages + "第" + rownumber + "行用户帐号为空" + "</br>";
			}

		}
		
		Set<String> setlist= new HashSet<String>(excelidlist);
		if(setlist.size()<excelidlist.size()){
			sb.setLength(0);
			sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip5", null, LocaleContextHolder.getLocale()));
			String erromessages=sb.toString();
			resultmap.put("messages", erromessages);
		}else{
			if(sb.length()>0){
				resultmap.put("messages", sb.toString());
			}
			else{
				Map<String, Object> findmap = new HashMap<String, Object>();
				findmap.put("usernamelist", excelidlist);
				findmap.put("orgid", orgid);
				List<UserModel> insertmemberids = userDao.findUserIdByUserName(findmap);
				List<ExcelVirModel> insertidlist = new ArrayList<ExcelVirModel>();
				for (int i = 0; i < insertmemberids.size(); i++) {
					ExcelVirModel excelVirModel = new ExcelVirModel();
					excelVirModel.setOrgid(orgid);
					excelVirModel.setVirid(id);
					excelVirModel.setColid(colid);
					excelVirModel.setUserid(insertmemberids.get(i).getId());
					insertidlist.add(excelVirModel);
				}
				userVirtualGroupDao.insertMembersByIdList(insertidlist);
				resultmap.put("success",messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip6", null, LocaleContextHolder.getLocale()));
			}
		}
		}else {
			   resultmap.put("resultnull","1");
		}
		return resultmap;
	}

}
