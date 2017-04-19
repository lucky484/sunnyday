package com.f2b.sugar.func.excel;

import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f2b.sugar.tools.StringConverters;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public class ExcelImport {

	private final static Logger logger = LoggerFactory.getLogger(ExcelImport.class);

	public static <T> List<T> readExcel(String filePath, Class<T> clazz, ExcelImportParam excelImportParam) {

		Workbook wb = ExcelImport.getExcelWorkbook(filePath);
		if (wb == null) {
			logger.error("创建Workbook对象未成功");
			return null;
		}

		Sheet sheet = ExcelImport.getSheetByNum(wb, 0);
		if (sheet == null) {
			logger.error("创建Sheet对象未成功");
			return null;
		}

		if (excelImportParam == null) {
			logger.error("传入的配置对象为空");
			return null;
		}

		Map<Integer, String> cellPropertyMap = excelImportParam.getCellPropertyMap();
		Map<String, String> extraParameter = excelImportParam.getExtraParameterMap();
		Map<String, String> dateConvertTypeMap = excelImportParam.getDateConvertTypeMap();

		List<T> tList = new ArrayList<>();

		for (int i = 1; i < sheet.getRows(); i++) {

			T t;
			try {
				t = clazz.newInstance();
			} catch (Exception e) {
				logger.error("实例化泛型对象出错", e);
				continue;
			}

			for (Entry<Integer, String> entry : cellPropertyMap.entrySet()) {
				Integer key = entry.getKey();
				String fieldName = entry.getValue();
				try {
					Field nameFld = clazz.getDeclaredField(fieldName);
					nameFld.setAccessible(true);
					nameFld.set(t, convertByType(nameFld.getType(), fieldName, sheet.getCell(key, i).getContents(), dateConvertTypeMap));

					if (sheet.getCell(key, i).getType() == CellType.DATE) {
						DateCell dc = (DateCell) sheet.getCell(key, i);
						Date date = dc.getDate();
						SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						nameFld.set(t, ds.format(date));
					}
				} catch (Exception e) {
					logger.error("没有找到对应的字段[" + fieldName + "]", e);
				}
				//logger.info("第[" + i + "]行，第[" + key + "]列的数据是：[" + sheet.getCell(key, i).getContents() + ",[" + sheet.getCell(key, i).getType() + "]]");
			}

			for (Entry<String, String> entry : extraParameter.entrySet()) {
				String fieldName = entry.getKey();
				String fieldValue = entry.getValue();
				try {
					Field nameFld = clazz.getDeclaredField(fieldName);
					nameFld.setAccessible(true);
					nameFld.set(t, convertByType(nameFld.getType(), fieldName, fieldValue, dateConvertTypeMap));
				} catch (Exception e) {
					logger.error("没有找到对应的字段[" + fieldName + "]", e);
				}
			}

			tList.add(t);
		}

		return tList;
	}

	public static <T> Object convertByType(Class<T> clazz, String fieldName, String fieldValue, Map<String, String> dateConvertTypeMap) {

		if (clazz.equals(String.class)) {
			return fieldValue.trim();
		}
		if (clazz.equals(Long.class)) {
			return StringConverters.ToLong(fieldValue);
		}
		if (clazz.equals(Double.class)) {
			return StringConverters.ToDouble(fieldValue);
		}
		if (clazz.equals(Integer.class)) {
			return StringConverters.ToInteger(fieldValue);
		}
		if (clazz.equals(Date.class)) {
			for (Entry<String, String> entry : dateConvertTypeMap.entrySet()) {
				if (fieldName.equals(entry.getKey())) {
					return StringConverters.ToDate(fieldValue, entry.getValue(), "excel导入参数");
				}
			}
		}
		return fieldValue;
	}

	/**
	 * 获取excel的Workbook
	 *
	 * @param filePath 文件路径
	 * @return 工作簿对象
	 */
	public static Workbook getExcelWorkbook(String filePath) {

		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("文件不存在，请检查文件路径");
			return null;
		}

		try {
			return Workbook.getWorkbook(file);
		} catch (Exception ex) {
			logger.info("创建Workbook对象异常", ex);
			return null;
		}
	}

	/**
	 * 根据索引 返回Sheet
	 *
	 * @param book   被选择的工作簿对象
	 * @param number 需要获得的工作表序号
	 * @return 工作表
	 */
	public static Sheet getSheetByNum(Workbook book, int number) {

		try {
			return book.getSheet(number);
		} catch (Exception ex) {
			logger.error("获取工作表出错，错误原因：[" + ex.toString() + "]", ex);
			return null;
		}
	}
}