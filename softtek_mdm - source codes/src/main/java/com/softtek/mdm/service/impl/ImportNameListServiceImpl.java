package com.softtek.mdm.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import com.softtek.mdm.abs.AbstractImportExcel;
import com.softtek.mdm.service.ImportNameListService;
import com.softtek.mdm.util.Constant.NameAppType;
import com.softtek.mdm.util.Constant.NameAppTypeDesc;
import com.softtek.mdm.web.http.BaseDTO;
import jodd.util.StringUtil;

/**
 * 黑白名单(导入)
 * @author jane.hui
 *
 */
@Service("importNameListService")
public class ImportNameListServiceImpl extends AbstractImportExcel implements ImportNameListService {

	@Autowired
	private MessageSource messageSourceService;
	
	@Override
	public String addCellDataToList(JsonObject json, int i, int j, Cell cell) {
		String key = "";
    	Object[] args = { i + 1 };
	    if(j == 0) {
	    	key = "appId";
	    	if(cell!=null&&StringUtil.isNotBlank(cell.getStringCellValue().trim())) {
	    		cell.setCellType(Cell.CELL_TYPE_STRING);
	    		String cValue = cell.getStringCellValue().trim();
	    		json.addProperty(key, cValue); 
	    	} else {
				 String message = messageSourceService.getMessage(
						"web.institution.namelistcontroller.excel.null.error.appid", args,
						LocaleContextHolder.getLocale());
				 return message;
	    	}
	    }
	    if(j == 1) {
	    	key = "appName";
	    	if(cell!=null&&StringUtil.isNotBlank(cell.getStringCellValue().trim())) {
	    		cell.setCellType(Cell.CELL_TYPE_STRING);
	    		json.addProperty(key, cell.getStringCellValue().trim());
	    	} else {
				return messageSourceService.getMessage(
						"web.institution.namelistcontroller.excel.null.error.appname", args,
						LocaleContextHolder.getLocale());
	    	}
	    }
	    if(j == 2) {
	    	key = "appType";
	    	if(cell!=null) {
	    		String appType = cell.getStringCellValue();
	    		cell.setCellType(Cell.CELL_TYPE_STRING);
	    		if(NameAppTypeDesc.ANDROID.toString().equals(appType.toLowerCase())) {
	    			json.addProperty(key, NameAppType.ANDROID);
	    		} else if(NameAppTypeDesc.IOS.toString().equals(appType.toLowerCase())){
	    			json.addProperty(key, NameAppType.IOS);
	    		} else {
	    			return messageSourceService.getMessage(
							"web.institution.namelistcontroller.excel.app.type.error", args,
							LocaleContextHolder.getLocale());
	    		}
	    	} else {
				return messageSourceService.getMessage(
						"web.institution.namelistcontroller.excel.null.error.apptype", args,
						LocaleContextHolder.getLocale());
	    	}
	    }
		return BaseDTO.SUCCESS;
	}
}