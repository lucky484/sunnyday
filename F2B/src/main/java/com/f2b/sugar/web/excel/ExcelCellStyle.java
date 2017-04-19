package com.f2b.sugar.web.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: 居泽平  Date: 13-9-4, 上午9:26
 */
public class ExcelCellStyle {

	public static Map<String, CellStyle> getPredefinedCellStyle(Workbook wb) {

		Map<String, CellStyle> styleMap = new HashMap<String, CellStyle>();

		// ==================== 设置 head 的 style ============================
		Font fontHead = wb.createFont();
		fontHead.setFontHeightInPoints((short) 10);
		fontHead.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontHead.setFontName("宋体");

		CellStyle cellStyleHead = wb.createCellStyle();
		cellStyleHead.setFont(fontHead);
		cellStyleHead.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHead.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyleHead.setBorderBottom((short) 1);
		cellStyleHead.setBorderLeft((short) 1);
		cellStyleHead.setBorderRight((short) 1);
		cellStyleHead.setBorderTop((short) 1);

		styleMap.put("cellStyleHead", cellStyleHead);
		// ==================== 设置 head 的 style ============================


		// ==================== 设置 main 的 style ============================
		Font fontMain = wb.createFont();
		fontMain.setFontHeightInPoints((short) 10);
		fontMain.setFontName("宋体");

		CellStyle cellStyleMain = wb.createCellStyle();
		cellStyleMain.setFont(fontMain);
		cellStyleMain.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleMain.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyleMain.setBorderBottom((short) 1);
		cellStyleMain.setBorderLeft((short) 1);
		cellStyleMain.setBorderRight((short) 1);
		cellStyleMain.setBorderTop((short) 1);

		styleMap.put("cellStyleMain", cellStyleMain);
		// ==================== 设置 main 的 style ============================

		return styleMap;
	}
}
