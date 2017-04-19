package com.f2b.sugar.func.excel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.f2b.sugar.tools.CommonSugar;
import com.f2b.sugar.tools.StringConverters;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: 居泽平  Date: 13-9-4, 上午9:26
 */
@SuppressWarnings("unused")
public class ExcelExport {

	/**
	 * 导出Excel文件
	 *
	 * @param title           导出Excel的名称
	 * @param selectFieldMaps 选中需要导出的Field, title名称
	 * @param agentJSONArray  最终导出数据的JSON对象
	 * @param response        HttpResponse Object.
	 * @throws java.io.IOException
	 */
	public static void exportExcelFile(String title, Map<String, String> selectFieldMaps, JSONArray agentJSONArray, HttpServletResponse response) throws IOException {

		String[] selectFields = new String[selectFieldMaps.size()];
		String[] selectFieldsName = new String[selectFieldMaps.size()];
		selectFieldMaps.keySet().toArray(selectFields);
		selectFieldMaps.values().toArray(selectFieldsName);

		String fileName = new String(title.getBytes("GBK"), "ISO-8859-1") + ".xls";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		response.setHeader("Content-Disposition", headerValue);
		OutputStream out = response.getOutputStream();

		//创建工作区
		Workbook wb = new HSSFWorkbook();
		if (selectFieldMaps.size() == 0) {

			Sheet sheet1 = wb.createSheet("new sheet");
			Row searchRow = sheet1.createRow(0);
			searchRow.setHeight((short) 340);
			Cell searchCell = searchRow.createCell(0);
			searchCell.setCellValue("未选择任何导出字段"); //搜索条件：无

			try {
				wb.write(out);
				out.flush();
				out.close();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//创建工作簿
		Sheet sheet1 = wb.createSheet("new sheet");
		//合并第一行单元格
		//sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, selectFieldsName.length - 1));

		//获取预先定义的样式
		Map<String, CellStyle> styleMap = ExcelExportStyle.getPredefinedCellStyle(wb);
		CellStyle cellStyleHead = styleMap.get("cellStyleHead");
		CellStyle cellStyleMain = styleMap.get("cellStyleMain");

		/*Row searchRow = sheet1.createRow(0);
		searchRow.setHeight((short) 340);
		Cell searchCell = searchRow.createCell(0);
		searchCell.setCellValue(""); //搜索条件：无*/

		Row headRow = sheet1.createRow(0);
		headRow.setHeight((short) 640);
		for (int i = 0; i < selectFieldsName.length; i++) {
			Cell cell = headRow.createCell(i);
			cell.setCellValue(selectFieldsName[i]);
			cell.setCellStyle(cellStyleHead);
			sheet1.setColumnWidth(i, selectFieldsName[i].length() * 720);
		}

		//外层数据行遍历
		int i = 1;
		Iterator<JSONObject> iterator = agentJSONArray.iterator();
		while (iterator.hasNext()) {

			//初始化 Excel Row
			Row row = sheet1.createRow(i);
			row.setHeight((short) 540);

			//遍历的数据行元数据
			JSONObject jsonObject = iterator.next();

			//内层字段遍历
			for (int j = 0; j < selectFields.length; j++) {
				String selectField = selectFields[j];

				//创建一个单元格
				Cell cell = row.createCell(j);
				cell.setCellStyle(cellStyleMain);

				//判断如果当前字段名称在jsonObject中，则进行赋值操作
				if (jsonObject.keySet().toString().indexOf(selectField) > 0) {
					//处理该字段的值
					String selectValue = jsonObject.getString(selectField);
					Double selectValueDouble = StringConverters.ToDouble(selectValue);
					//如果纯数字，那么设置为数字格式
					if (selectValue != null && selectValue.length() < 8 && selectValueDouble != null) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(selectValueDouble);
					} else {
						cell.setCellValue(selectValue);
					}
					//根据内容设定单元格宽度
					int unitWidth = CommonSugar.isContainChinese(selectValue) ? 680 : 300;
					int oldWidth = sheet1.getColumnWidth(j);
					int newWidth = selectValue.length() * unitWidth;
					if (newWidth > oldWidth) {
						newWidth = newWidth > 14000 ? 14000 : newWidth;
						sheet1.setColumnWidth(j, newWidth);
					}
				}
			}

			//预备创建下一 Excel Row
			i++;
		}
		try {
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
