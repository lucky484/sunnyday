package com.softtek.mdm.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * excel操作工具类
 * @author jane.hui
 *
 */
public class ExcelUtils {

	/**
	 * 获取excel类别
	 * @param postfix：后缀
	 * @return 返回excel版本
	 */
	public static Integer getExcelVersion(String postfix) {
		if (postfix.endsWith("xls")) {
			return 03;
		}
		if (postfix.endsWith("xlsx")) {
			return 07;
		}
		return null;
	}
	
	/**
	 * 根据文件类别获取对应版本的sheet
	 * @param fileType:文件类别
	 * @return 返回对应版本的sheet
	 */
	public static Sheet getSheet(Integer fileType, Workbook book) {
		if (fileType == 03) {
			return (HSSFSheet) book.getSheetAt(0);
		}
		if (fileType == 07) {
			return (XSSFSheet) book.getSheetAt(0);
		} 
		return null;
	}
}